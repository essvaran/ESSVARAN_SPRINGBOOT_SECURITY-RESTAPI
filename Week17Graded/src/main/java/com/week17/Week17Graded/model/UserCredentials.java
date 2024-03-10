package com.week17.Week17Graded.model;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentials {

	@Id
	private int id;
	@Column(unique = true)
	private String name;
	private String emaill;
	private String password;
	
	//This is to create a seprate table for this element
	@ElementCollection (fetch = FetchType.EAGER)
	@CollectionTable(name="user_authorities" , joinColumns = @JoinColumn(name= "user_id"))
	private Set<String> authorities;
}
