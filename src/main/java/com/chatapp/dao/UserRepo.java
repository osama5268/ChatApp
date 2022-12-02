package com.chatapp.dao;

import org.springframework.data.repository.CrudRepository;

import com.chatapp.model.User;

public interface UserRepo extends CrudRepository<User,Integer> {
	User findByUsername(String username);
	User findByEmail(String email);
}
