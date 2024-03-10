package com.week17.Week17Graded.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.week17.Week17Graded.model.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer>{

}
