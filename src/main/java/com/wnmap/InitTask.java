package com.wnmap;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import com.wnmap.bean.HostTask;
import com.wnmap.bean.OsTask;
import com.wnmap.bean.PortTask;
import com.wnmap.repository.HostTaskRepository;
import com.wnmap.repository.OsTaskRepository;
import com.wnmap.repository.PortTaskRepository;

@Component
public class InitTask {
    @Autowired
    HostTaskRepository hostTaskRepository;
    @Autowired
    PortTaskRepository portTaskRepository;
    @Autowired
    OsTaskRepository osTaskRepository;
    @Autowired
    ThreadPoolTaskScheduler threadPoolTaskScheduler;
    
	public void init() {
		Iterable<HostTask> hosttasks = hostTaskRepository.findAll();
		Iterable<PortTask> porttasks = portTaskRepository.findAll();
		Iterable<OsTask> ostasks = osTaskRepository.findAll();
		
		for (HostTask hostTask : hosttasks) {
			Long id = hostTask.getId();
			ScheduledFuture future = threadPoolTaskScheduler.schedule(new HostScanRunnable(hostTask), 
					new Trigger() {
		    	            @Override
		    	            public Date nextExecutionTime(TriggerContext triggerContext){
		    	            	String frequence = hostTaskRepository.findById(id).get().getFrequence();
		    	                return new PeriodicTrigger(getPeriod(frequence)).nextExecutionTime(triggerContext);
		    	            }});
	        WnmapApplication.hostTaskMap.put(id.toString(), future);
	        try {
		        Thread.sleep(20000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		for (PortTask portTask : porttasks) {
			Long id = portTask.getId();
			ScheduledFuture future = threadPoolTaskScheduler.schedule(new PortScanRunnable(portTask), 
					new Trigger() {
		    	            @Override
		    	            public Date nextExecutionTime(TriggerContext triggerContext){
		    	            	String frequence = portTaskRepository.findById(id).get().getFrequence();
		    	                return new PeriodicTrigger(getPeriod(frequence)).nextExecutionTime(triggerContext);
		    	            }});
	        WnmapApplication.hostTaskMap.put(id.toString(), future);
	        try {
		        Thread.sleep(20000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		for (OsTask osTask : ostasks) {
			Long id = osTask.getId();
			ScheduledFuture future = threadPoolTaskScheduler.schedule(new OsScanRunnable(osTask), 
					new Trigger() {
		    	            @Override
		    	            public Date nextExecutionTime(TriggerContext triggerContext){
		    	            	String frequence = osTaskRepository.findById(id).get().getFrequence();
		    	                return new PeriodicTrigger(getPeriod(frequence)).nextExecutionTime(triggerContext);
		    	            }});
	        WnmapApplication.hostTaskMap.put(id.toString(), future);
	        try {
		        Thread.sleep(20000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public static Long getPeriod(String frequence) {
		Long period;
		switch(frequence){
		    case "5分钟" :
		    	period = 1000L*60*5;
		       break;
		    case "10分钟" :
		    	period = 1000L*60*10;
		       break;
		    case "15分钟" :
		    	period = 1000L*60*15;
		       break;
		    case "30分钟" :
		    	period = 1000L*60*30;
		       break;
		    case "1小时" :
		    	period = 1000L*60*60;
		       break;
		    case "3小时" :
		    	period = 1000L*60*60*3;
		       break;
		    case "12小时" :
		    	period = 1000L*60*60*12;
		       break;
		    case "1天" :
		    	period = 1000L*60*60*24;
		       break;
		    case "3天" :
		    	period = 1000L*60*60*24*3;
		       break;
		    case "1周" :
		    	period = 1000L*60*60*24*7;
		       break;
		    case "1月" :
		    	period = 1000L*60*60*24*30;
		       break;
		    default :
		    	period = 1000L*60*5;
		}
		return period;
	}
}
