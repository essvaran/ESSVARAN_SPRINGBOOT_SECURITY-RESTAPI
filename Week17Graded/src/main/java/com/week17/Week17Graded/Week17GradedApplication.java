package com.week17.Week17Graded;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.week17.Week17Graded.model.Roles;
import com.week17.Week17Graded.model.UserCredentials;
import com.week17.Week17Graded.service.RoleService;
import com.week17.Week17Graded.service.UserCredService;

@SpringBootApplication
public class Week17GradedApplication implements CommandLineRunner{

	
	@Autowired
	UserCredService uservice;
	
	@Autowired
	RoleService rservice;
	
	public static void main(String[] args) {
		SpringApplication.run(Week17GradedApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Set<String> authAdmin = new HashSet<String>();
		authAdmin.add("admin");
		
		//Adding the admin role to the DB
		rservice.addRoles(new Roles(1, "admin"));
		
		//Create encode object
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		
		//Creating a admin user and password for their initial login purpose
		UserCredentials appAdmin = new UserCredentials(1, "admin", "admin", encoder.encode("adminPassword"), authAdmin);
		uservice.add(appAdmin);	

}
}
