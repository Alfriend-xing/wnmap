package com.wnmap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wnmap.bean.Host;
import com.wnmap.bean.Port;
import com.wnmap.repository.PortRepository;

@Service
@Transactional
public class PortService {

    @Autowired
    PortRepository portRepository;
    

    public Page<Port> getPortByPage(Integer page, Integer size, Sort sort) {
    	Pageable getPage = PageRequest.of(page, size, sort);
    	Page<Port> getPageData = 
    			portRepository.findAll(getPage);
    	return getPageData;
    }
}
