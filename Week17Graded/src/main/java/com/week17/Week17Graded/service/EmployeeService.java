package com.week17.Week17Graded.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.week17.Week17Graded.model.Employee;
import com.week17.Week17Graded.repo.EmployeeRepo;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepo repo;
	
	public void addEmployee(Employee emp)
	{
		repo.save(emp);
	}
	
	public void updateEmployee(Employee emp)
	{
		repo.save(emp);
	}
	
	public void deleteEmployeeById(int id)
	{
		repo.deleteById(id);
	}
	
	public List<Employee> getAllEmployee()
	{
		return repo.findAll();
	}
	
	public Employee getEmployeeById(int id)
	{
		Optional<Employee> empO = repo.findById(id);
		if(empO.isPresent())
		{
			return empO.get();
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Method to find employee by their last name
	 * @param searchKey
	 * @return
	 */
	public List<Employee> filterByFirstName(String searchKey)
	{
		Employee dummy = new Employee();
		dummy.setFirstName(searchKey);
		
		//Where with exampleMatcher
		ExampleMatcher em = ExampleMatcher.matching().withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.contains()).withIgnorePaths("id","lastName","email");
		
		// Combining dummy with where
		Example<Employee> example = Example.of(dummy,em);
		
		return repo.findAll(example);
	}
	
	/**
	 * Method to sort by first name
	 * @param dir 
	 * @return the sorted list of employee by first name
	 */
	public List<Employee> getBySort(Direction dir)
	{
		return repo.findAll(Sort.by(dir,"firstName"));
	}
}
