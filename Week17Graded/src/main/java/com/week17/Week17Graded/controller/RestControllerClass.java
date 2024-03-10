package com.week17.Week17Graded.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.week17.Week17Graded.model.Employee;
import com.week17.Week17Graded.model.Roles;
import com.week17.Week17Graded.model.UserCredentials;
import com.week17.Week17Graded.service.EmployeeService;
import com.week17.Week17Graded.service.RoleService;
import com.week17.Week17Graded.service.UserCredService;

@RestController("/app")
class RestControllerClass {

	@Autowired
	UserCredService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	EmployeeService empService;
	
	//for encoding the password while storing in to the database
	PasswordEncoder encode = new BCryptPasswordEncoder();

	/** 
	 * This method is to verify the role of the user
	 * 
	 * @param role
	 * @return it returns true is the role is present and false if the role is not
	 *         present
	
	public static boolean checkAuthorization(Authentication authentication, SecurityContextHolder auth, String role) {
		boolean roleFound = false;

		// who is currently logged in
		System.out.println("Currently logged in by : " + authentication.getName());

		// find the role of the person who have logged in
		Collection<? extends GrantedAuthority> grantedRoles = auth.getContext().getAuthentication().getAuthorities();

		for (int i = 0; i < grantedRoles.size(); i++) {
			String roleAccepted = grantedRoles.toArray()[i].toString();
			System.out.println("My role : " + role);

			if (roleAccepted.equalsIgnoreCase(role)) {
				roleFound = true;
			}
		}

		return roleFound;
	}

	 */
	
	/**
	 * 
	 * Endpoint to add role in the database
	 * 
	 * @param id
	 * @param role
	 * @param authentication
	 * @param auth
	 * @return
	 */
	@PreAuthorize("hasAuthority('admin')")
	@PostMapping("/add/role")
	public String addRole(@RequestParam int id, @RequestParam String role, Authentication authentication,
			SecurityContextHolder auth) {
		/*
		boolean status = checkAuthorization(authentication, auth, "admin");
		System.out.println(status + " status");
		if (status == true) {
			roleService.addRoles(new Roles(id, role));
			return "added successfully";
		} else {
			return "Access denied";
		}
		*/
		System.out.println(authentication.getAuthorities() + authentication.getName());
		roleService.addRoles(new Roles(id, role));
		return "added successfully";
	}
	
	/**
	 * 
	 * This endpoint is to add user
	 * 
	 * @param id
	 * @param username
	 * @param email
	 * @param password
	 * @param authorities getting it in String array because there can be many roles for a single user
	 * @return
	 */
	@PostMapping("/add/user")
	@PreAuthorize("hasAuthority('admin')")
	public String addUser(@RequestParam int id , @RequestParam String username , @RequestParam String email , @RequestParam String password , @RequestParam String[] authorities)
	{
		//As the type of authority is Set we need to convert a String array to a Set
		List<String> list = Arrays.asList(authorities);
		Set<String> roles = new HashSet<String>(list);

		/*
		 * Checking whether the roles as available in the table
		 * I have a role table in which I store the roles 
		 * If the inputted role is not available in that table  I should not able to add the user
		 */
		
		Set<String> roleFromTable = roleService.getAllRoleNames();
		
		//This is to check whether the given roles in ipput is also available in role table that is defined by the user
		boolean allElementsInTable = roleFromTable.containsAll(roles);
		
		//Only add user when the roles match otherwise don't add
		if(allElementsInTable)
		{
		userService.add(new UserCredentials(id, username, email, encode.encode(password), roles));
		return "user added";
		}
		else
		{
			return "There is no such role available in the table. The roles available are : "+roleFromTable;
		}
	}
	
	/**
	 * 
	 * Endpoint to add employee 
	 * 
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @return
	 */
	@PostMapping("/add/employee")
	@PreAuthorize("hasAuthority('admin')")
	public String addEmployee(@RequestParam int id , @RequestParam String firstName , @RequestParam String lastName , @RequestParam String email)
	{
		Employee emp = empService.getEmployeeById(id);
		if(emp == null)
		{
		empService.addEmployee(new Employee(id, firstName, lastName, email));
		return "Employee Added successfully";
		}
		else
		{
			return "Record already exist";
		}
	}
	
	/**
	 * Endpoint to show all employee
	 * @return
	 */
	@GetMapping("/show-all/employee")
	public List<Employee> getAllEmployee()
	{
		return empService.getAllEmployee();
	}
	
	/**
	 * 
	 * Endpoint to delete the employee by ID
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("delete-emp-by-id")
	@PreAuthorize("hasAuthority('admin')")
	public String deleteById(@RequestParam int id)
	{
		Employee emp = empService.getEmployeeById(id);
		if(emp != null)
		{
			empService.deleteEmployeeById(id);
			return "deleted successfully";
		}
		else
		{
			return "No Employee available with id "+id;
		}
	}

	/**
	 * 
	 * Endpoint to update the employee
	 * 
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @return
	 */
	@PutMapping("/update/employee")
	@PreAuthorize("hasAuthority('admin')")
	public String updateEmployee(@RequestParam int id , @RequestParam String firstName, @RequestParam String lastName , @RequestParam String email)
	{
		Employee  emp = empService.getEmployeeById(id);
		if(emp != null)
		{
			empService.updateEmployee(new Employee(id, firstName, lastName, email));
			return "updated successfully";
		}
		else
		{
			return "No record found with the id "+id;
		}
	}
	
	/**
	 *
	 * End point to show the employee by id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("show/employee-by-id")
	public Employee getById(@RequestParam  int id)
	{
		Employee emp = empService.getEmployeeById(id);
		if(emp != null)
		{
		return empService.getEmployeeById(id);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 
	 * Filter the employee record by the first name
	 * 
	 * @param name
	 * @return
	 */
	@GetMapping("search/employee-by-firstname")
	public List<Employee> searchByName(@RequestParam String name)
	{
		return empService.filterByFirstName(name);
	}
	
	/**
	 * Sorting the list by the  first name
	 * @param dir
	 * @return
	 */
	@GetMapping("sort/employee-by-name")
	public List<Employee> sortByName(@RequestParam String dir)
	{
		Direction d = null;
		if(dir.equalsIgnoreCase("desc") || dir.equalsIgnoreCase("descending"))
		{
			d= Direction.DESC;
		}
		else
		{
			d=Direction.ASC;
		}
		return empService.getBySort(d);
	}
}

