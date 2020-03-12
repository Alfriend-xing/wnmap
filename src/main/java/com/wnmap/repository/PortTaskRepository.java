package com.wnmap.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wnmap.bean.HostTask;
import com.wnmap.bean.PortTask;

public interface PortTaskRepository extends JpaRepository<PortTask, Long> {
	public Page<PortTask> findAll(Pageable pageable);
	public void deleteById(Long id);
	public PortTask findByIp(String ip);

	public long count();
}
