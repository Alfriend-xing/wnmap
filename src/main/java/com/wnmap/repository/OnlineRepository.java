package com.wnmap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wnmap.bean.Online;

public interface OnlineRepository extends JpaRepository<Online, Long> {
	public List<Online> findByTimeGreaterThan(Long time);
}
