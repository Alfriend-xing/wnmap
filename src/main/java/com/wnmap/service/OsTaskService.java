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
import com.wnmap.OsScanRunnable;
import com.wnmap.WnmapApplication;
import com.wnmap.bean.OsTask;
import com.wnmap.repository.OsTaskRepository;

@Service
@Transactional
public class OsTaskService {

    final SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
    @Autowired
    OsTaskRepository osTaskRepository;
    @Autowired
    ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public Page<OsTask> getOsTaskByPage(Integer page, Integer size) {
    	Pageable getPage = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
    	Page<OsTask> getPageData = 
    			osTaskRepository.findAll(getPage);
    	return getPageData;
    }
    
    @PreAuthorize("hasAnyRole('admin', 'root')")
    public void deleteById(Long id) throws Exception {
    	osTaskRepository.deleteById(id);
    	ScheduledFuture future = WnmapApplication.osTaskMap.get(id.toString());
    	if (future != null) {
            future.cancel(true);
        }
    }
    
    @PreAuthorize("hasAnyRole('admin', 'root')")
    public void addTask(OsTask osTask) throws Exception {
    	Date date = new Date();
    	Long timeLong = date.getTime();
    	String timeFormat = ft.format(new Date(timeLong));
    	osTask.setAddtime(timeFormat);
    	osTaskRepository.save(osTask);

    	OsTask newOsTask = osTaskRepository.findByIp(osTask.getIp());
		Long id = newOsTask.getId();
		ScheduledFuture future = threadPoolTaskScheduler.schedule(new OsScanRunnable(newOsTask), 
				new Trigger() {
	    	            @Override
	    	            public Date nextExecutionTime(TriggerContext triggerContext){
	    	            	String frequence = osTaskRepository.findById(id).get().getFrequence();
	    	                return new PeriodicTrigger(InitTask.getPeriod(frequence)).nextExecutionTime(triggerContext);
	    	            }});
        WnmapApplication.osTaskMap.put(id.toString(), future);
    }
    
    @PreAuthorize("hasAnyRole('admin', 'root')")
    public void updateTask(Long id, String ip, String frequence) throws Exception {
    	OsTask osTask = osTaskRepository.findById(id).get();
    	osTask.setIp(ip);
    	osTask.setFrequence(frequence);
    	osTaskRepository.save(osTask);
    }

}
