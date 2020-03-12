package com.wnmap.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wnmap.bean.Host;

public interface HostRepository extends JpaRepository<Host, Long> {
	
	public Page<Host> findAll(Pageable pageable);

	public long count();
	
	public Host findByIp(String ip);
	
	public long countByOstype(String ostype);
	
	public long countByState(Boolean state);
	
	public Page<Host> findByIpLike(String ip,Pageable pageable);
	
	@Query(value = "SELECT sum(portsum) from Host", nativeQuery = true)
    public long getPortSum();

	@Query(value = "SELECT sum(totaltime) from Host", nativeQuery = true)
    public long getHourSum();

}
