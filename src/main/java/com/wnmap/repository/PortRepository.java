package com.wnmap.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wnmap.bean.Host;
import com.wnmap.bean.Port;

public interface PortRepository extends JpaRepository<Port, Long> {
	public Page<Port> findAll(Pageable pageable);
	public Port findByNumber(String number);
	
}
