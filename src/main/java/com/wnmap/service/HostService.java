package com.wnmap.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wnmap.bean.Host;
import com.wnmap.repository.HostRepository;

@Service
@Transactional
public class HostService {
	final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    HostRepository hostRepository;
    
    public Page<Host> getHostByPage(Integer page, Integer size) {
    	Pageable getPage = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
    	Page<Host> getPageData = 
    			hostRepository.findAll(getPage);
    	return getPageData;
    }
    public Page<Host> getHostByPage(Integer page, Integer size, Sort sort) {
    	Pageable getPage = PageRequest.of(page, size, sort);
    	Page<Host> getPageData = 
    			hostRepository.findAll(getPage);
    	return getPageData;
    }
    public Page<Host> getHostByIpLike(String ip, Integer page, Integer size) {
    	Pageable getPage = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
    	Page<Host> getPageData = 
    			hostRepository.findByIpLike("%"+ip+"%", getPage);
    	return getPageData;
    }
    
    public long getHostSum() {
		return hostRepository.count();
	}
    
	public Map<String, Long> countByOstype() {
		Map<String, Long> ostypeMap = new HashMap<String, Long>();
		ostypeMap.put("windows", hostRepository.countByOstype("windows"));
		ostypeMap.put("linux", hostRepository.countByOstype("linux"));
		ostypeMap.put("macos", hostRepository.countByOstype("macos"));
		ostypeMap.put("other", hostRepository.countByOstype("other"));
		return ostypeMap;
	}
    
    public long getOnlineSum() {
		return hostRepository.countByState(true);
	}
    
    public long getPortSum() {
    	try {
    		return hostRepository.getPortSum();
		} catch (AopInvocationException e) {
			// TODO: handle exception
			return 0L;
		}
	}
    
    public long getHourSum() {
    	try {
    		return hostRepository.getHourSum();
		} catch (AopInvocationException e) {
			// TODO: handle exception
			return 0L;
		}
	}

}
