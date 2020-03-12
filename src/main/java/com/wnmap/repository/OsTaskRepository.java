package com.wnmap.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wnmap.bean.HostTask;
import com.wnmap.bean.OsTask;
import com.wnmap.bean.Port;

public interface OsTaskRepository extends JpaRepository<OsTask, Long> {
	public Page<OsTask> findAll(Pageable pageable);
	public void deleteById(Long id);
	public OsTask findByIp(String ip);

	public long count();
}
