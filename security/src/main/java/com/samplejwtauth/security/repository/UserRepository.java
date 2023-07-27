package com.samplejwtauth.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.samplejwtauth.security.model.User;

public interface UserRepository extends 
//CrudRepository<User,Integer>{
JpaRepository<User,Integer>{

	@Query(value = "SELECT * FROM _user where email = ?1", nativeQuery = true)
	Optional<User> findByEmail(String email);
}
