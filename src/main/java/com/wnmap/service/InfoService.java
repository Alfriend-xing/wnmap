package com.wnmap.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wnmap.bean.Info;
import com.wnmap.bean.Msg;
import com.wnmap.repository.HostRepository;
import com.wnmap.repository.HostTaskRepository;
import com.wnmap.repository.InfoRepository;
import com.wnmap.repository.OsTaskRepository;
import com.wnmap.repository.PortTaskRepository;

@Service
@Transactional
public class InfoService {

    @Autowired
    InfoRepository infoRepository;
    @Autowired
    PortTaskRepository portTaskRepository;
    @Autowired
    HostTaskRepository hostTaskRepository;
    @Autowired
    OsTaskRepository osTaskRepository;
    @Autowired
    HostRepository hostRepository;
    
    public List<Info> getInfo(){
    	Date date = new Date();
    	List<Info> info= infoRepository.findByTimeGreaterThan(date.getTime() - 2*24*3600000);
    	return info;
    }
    public Info getRealInfo(){
    	Info info= infoRepository.findByTime(0L);
    	return info;
    }
    
    public Msg getServerInfo() {
		HashMap<String,Long> res = new HashMap<String, Long>();
		Long taskSum = portTaskRepository.count()+
				hostTaskRepository.count()+osTaskRepository.count();
		Long onlineTime = 0L;
		for (Info info:infoRepository.findAll()) {
			if (!info.getTime().equals(0L)) {
				onlineTime = info.getTime();
				break;
			}
		}
		Long hostSum = hostRepository.count();
		res.put("task", taskSum);
		res.put("time", onlineTime);
		res.put("host", hostSum);
		return new Msg(true,"success",res);
	}

}
