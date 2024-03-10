package com.week17.Week17Graded.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.week17.Week17Graded.model.Roles;

public interface RoleRepo extends JpaRepository<Roles, Integer>{
	
	Optional<Roles> findByName(String name);
	void deleteByName(String name); 
}
