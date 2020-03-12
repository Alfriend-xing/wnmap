package com.wnmap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wnmap.bean.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByuserName(String userName);
	
}
