package com.week17.Week17Graded.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.week17.Week17Graded.model.Roles;
import com.week17.Week17Graded.repo.RoleRepo;

@Service
public class RoleService {

	@Autowired
	RoleRepo repo;
	
	public void addRoles(Roles role)
	{
		repo.save(role);
	}
	
	public void deleteRole(int id)
	{
		repo.deleteById(id);
	}
	
	public void deleteByName(String name)
	{
		repo.deleteByName(name);
	}
	
	 public Set<String> getAllRoleNames() {
	        List<Roles> roles = repo.findAll();
	        return roles.stream().map(Roles::getName).collect(Collectors.toSet());
	    }
}
