package com.wnmap.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wnmap.HostScanRunnable;
import com.wnmap.InitTask;
import com.wnmap.WnmapApplication;
import com.wnmap.bean.HostTask;
import com.wnmap.repository.HostTaskRepository;

@Service
@Transactional
public class HostTaskService {

    final SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
    
    @Autowired
    HostTaskRepository hostTaskRepository;
    @Autowired
    ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public Page<HostTask> getHostTaskByPage(Integer page, Integer size) {
    	Pageable getPage = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
    	Page<HostTask> getPageData = 
    			hostTaskRepository.findAll(getPage);
    	return getPageData;
    }
    
    @PreAuthorize("hasAnyRole('admin', 'root')")
    public void deleteById(Long id) throws Exception {
    	hostTaskRepository.deleteById(id);
    	ScheduledFuture future = WnmapApplication.hostTaskMap.get(id.toString());
    	if (future != null) {
            future.cancel(true);
        }
    }
    
    @PreAuthorize("hasAnyRole('admin', 'root')")
    public void addTask(HostTask hostTask) throws Exception {
    	Date date = new Date();
    	Long timeLong = date.getTime();
    	String timeFormat = ft.format(new Date(timeLong));
    	hostTask.setAddtime(timeFormat);
    	hostTaskRepository.save(hostTask);

    	HostTask newHostTask = hostTaskRepository.findByIp(hostTask.getIp());
		Long id = newHostTask.getId();
		ScheduledFuture future = threadPoolTaskScheduler.schedule(new HostScanRunnable(newHostTask), 
				new Trigger() {
	    	            @Override
	    	            public Date nextExecutionTime(TriggerContext triggerContext){
	    	            	String frequence = hostTaskRepository.findById(id).get().getFrequence();
	    	                return new PeriodicTrigger(InitTask.getPeriod(frequence)).nextExecutionTime(triggerContext);
	    	            }});
        WnmapApplication.hostTaskMap.put(id.toString(), future);
    }
    @PreAuthorize("hasAnyRole('admin', 'root')")
    public void updateTask(Long id, String ip, String frequence) throws Exception {
    	HostTask hostTask = hostTaskRepository.findById(id).get();
    	hostTask.setIp(ip);
    	hostTask.setFrequence(frequence);
    	hostTaskRepository.save(hostTask);
    }
}
