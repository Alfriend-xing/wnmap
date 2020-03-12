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

import com.wnmap.InitTask;
import com.wnmap.PortScanRunnable;
import com.wnmap.WnmapApplication;
import com.wnmap.bean.PortTask;
import com.wnmap.repository.PortTaskRepository;

@Service
@Transactional
public class PortTaskService {

    final SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
    @Autowired
    PortTaskRepository portTaskRepository;
    @Autowired
    ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public Page<PortTask> getPortTaskByPage(Integer page, Integer size) {
    	Pageable getPage = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
    	Page<PortTask> getPageData = 
    			portTaskRepository.findAll(getPage);
    	return getPageData;
    }
    
    @PreAuthorize("hasAnyRole('admin', 'root')")
    public void deleteById(Long id) throws Exception {
    	portTaskRepository.deleteById(id);
    	ScheduledFuture future = WnmapApplication.portTaskMap.get(id.toString());
    	if (future != null) {
            future.cancel(true);
        }
    }
    
    @PreAuthorize("hasAnyRole('admin', 'root')")
    public void addTask(PortTask portTask) throws Exception {
    	Date date = new Date();
    	Long timeLong = date.getTime();
    	String timeFormat = ft.format(new Date(timeLong));
    	portTask.setAddtime(timeFormat);
    	portTaskRepository.save(portTask);

    	PortTask newPortTask = portTaskRepository.findByIp(portTask.getIp());
		Long id = newPortTask.getId();
		ScheduledFuture future = threadPoolTaskScheduler.schedule(new PortScanRunnable(newPortTask), 
				new Trigger() {
	    	            @Override
	    	            public Date nextExecutionTime(TriggerContext triggerContext){
	    	            	String frequence = portTaskRepository.findById(id).get().getFrequence();
	    	                return new PeriodicTrigger(InitTask.getPeriod(frequence)).nextExecutionTime(triggerContext);
	    	            }});
        WnmapApplication.portTaskMap.put(id.toString(), future);
    }
    
    @PreAuthorize("hasAnyRole('admin', 'root')")
    public void updateTask(Long id, String ip, String frequence) throws Exception {
    	PortTask portTask = portTaskRepository.findById(id).get();
    	portTask.setIp(ip);
    	portTask.setFrequence(frequence);
    	portTaskRepository.save(portTask);
    }

}
