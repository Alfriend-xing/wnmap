package com.wnmap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wnmap.bean.Info;
import com.wnmap.bean.Online;

public interface InfoRepository extends JpaRepository<Info, Long> {
	public List<Info> findByTimeGreaterThan(Long time);

	public Info findByTime(Long time);

}
