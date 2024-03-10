package com.week17.Week17Graded.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.week17.Week17Graded.model.UserCredentials;

public interface UserCredentialsRepo extends JpaRepository<UserCredentials, Integer>{

	Optional<UserCredentials> findByName(String name);
}
