package com.chatapp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.chatapp.model.Requests;
import com.chatapp.model.User;

public interface RequestsRepo extends CrudRepository<Requests, Integer> {
	List<Requests> findByuserTo(User user); 
	List<Requests> findByuserFrom(User user);
}
