package com.chatapp.dao;

import java.util.List;

import com.chatapp.model.Messages;
// import com.chatapp.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MessagesRepo extends CrudRepository <Messages, Integer> {
    @Query(value = "select * from messages where (user_to_userid = ? and user_from_userid = ?) or (user_to_userid = ? and user_from_userid = ?) order by id asc limit 20", nativeQuery = true)
    List<Messages> getLatestMessages(int user1, int user2, int user3, int user4);
}
