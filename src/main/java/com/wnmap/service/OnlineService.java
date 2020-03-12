package com.wnmap.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wnmap.bean.Online;
import com.wnmap.repository.OnlineRepository;

@Service
@Transactional
public class OnlineService {

    @Autowired
    OnlineRepository onlineRepository;
    
    public List<Online> getOnline(){
    	Date date = new Date();
    	List<Online> online = onlineRepository.findByTimeGreaterThan(date.getTime() - 7*24*3600000);
    	return online;
    }

}
