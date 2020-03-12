package com.wnmap.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wnmap.bean.Host;
import com.wnmap.bean.HostTask;

public interface HostTaskRepository extends JpaRepository<HostTask, Long> {
	
	public Page<HostTask> findAll(Pageable pageable);
	public void deleteById(Long id);
	public HostTask findByIp(String ip);

	public long count();
}
