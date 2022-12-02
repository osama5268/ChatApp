package com.chatapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.chatapp.model.Friends;
import com.chatapp.model.User;

public interface FriendsRepo extends CrudRepository<Friends,Integer> {
	List<Friends> findByuser1(User user1);
	List<Friends> findByuser2(User user2);
	@Query(value="select * from friends where user1 = ?", nativeQuery=true)
	Friends findByUser(User user);
}
