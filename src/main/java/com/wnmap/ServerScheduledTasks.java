package com.wnmap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wnmap.bean.Host;
import com.wnmap.bean.Info;
import com.wnmap.bean.Online;
import com.wnmap.bean.Port;
import com.wnmap.repository.HostRepository;
import com.wnmap.repository.InfoRepository;
import com.wnmap.repository.OnlineRepository;
import com.wnmap.repository.PortRepository;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;


@Component
public class ServerScheduledTasks {
    final SimpleDateFormat ft = new SimpleDateFormat ("MM-dd HH:mm");
    private SystemInfo systemInfo = new SystemInfo();
    
    @Autowired
    OnlineRepository onlineRepository;
    
    @Autowired
    HostRepository hostRepository;
    @Autowired
    PortRepository portRepository;
    @Autowired
    InfoRepository infoRepository;

    @Scheduled(fixedRate = 5*60*1000)
    public void logOnlineSum() {
    	Online online = new Online();
    	Long onlineSumLong = hostRepository.countByState(true);
    	Date date = new Date();
    	online.setTime(date.getTime());
    	online.setFormat(ft.format(date));
    	online.setSum(onlineSumLong.intValue());
    	onlineRepository.save(online);
    	saveInfo(date.getTime());
    }
    
    @Scheduled(fixedRate = 5*60*1000)
    public void logOpenPorts() {
    	Iterable<Host> hosts = hostRepository.findAll();
    	HashMap<String, Integer> portMap = new HashMap<String, Integer>();
    	for (Host host:hosts) {
    		String portString = host.getOpenports();
    		if (portString==null || portString.equals("")) {
    			continue;
    		}
//    		System.out.println("portString"+portString);
    		String[] portList = portString.split(",");
    		for (String portstring:portList){
//    			int portnum = Integer.parseInt(portstring);
    			if (portMap.containsKey(portstring)) {
    				portMap.put(portstring, portMap.get(portstring)+1);
    			} else {
    				portMap.put(portstring, 1);
    			}
    		}
    	}
    	for(String port:portMap.keySet()) {
    		Port dbPort = portRepository.findByNumber(port);
    		if (dbPort!=null) {
    			dbPort.setSum(portMap.get(port));
    		} else {
        		portRepository.save(new Port(port,portMap.get(port)));
    		}
    	}
    }
    @Scheduled(fixedRate = 5*1000)
    public void logUseage() {
    	saveInfo(0L);
    }
    
    private void saveInfo(Long time) {
    	Integer cpu = getCpuUsage();
    	Integer mem = getMemoryUsage();
//    	System.out.println("cpu"+cpu.toString()+"mem"+mem.toString());
    	Info info = infoRepository.findByTime(time);
    	if (info==null) {
    		infoRepository.save(new Info(time,ft.format(new Date(time)), cpu,mem));
    	} else {
        	info.setCpu(cpu);
        	info.setMem(mem);
        	infoRepository.save(info);
    	}
    }
    private Integer getMemoryUsage() {
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        GlobalMemory memory = hal.getMemory();
        Long available = memory.getAvailable();
        Long total = memory.getTotal();
    	Double useRate = ((total-available)/(double)total)*100;
        return useRate.intValue();
    }
    private Integer getCpuUsage() {
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        CentralProcessor processor = hal.getProcessor();
        Double useRate = processor.getSystemCpuLoadBetweenTicks()*100;
        return useRate.intValue();
    }
}
