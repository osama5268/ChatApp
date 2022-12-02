package com.chatapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chatapp.dao.FriendsRepo;
import com.chatapp.dao.MessagesRepo;
import com.chatapp.dao.RequestsRepo;
import com.chatapp.dao.UserRepo;
import com.chatapp.model.Friends;
import com.chatapp.model.Messages;
import com.chatapp.model.Requests;
import com.chatapp.model.User;

@Controller
public class ChatPageController {
	@Autowired
	UserRepo userrepo;
	@Autowired
	FriendsRepo friendsrepo;
	@Autowired
	RequestsRepo requestsrepo;
	@Autowired
	MessagesRepo messagesRepo;

	@RequestMapping(value = "chat", method = RequestMethod.GET)
	public ModelAndView chatPage(HttpSession session) {
		ModelAndView mv;
		if (session.getAttribute("isLoggedIn") == null) {
			mv = new ModelAndView("redirect:/");
			return mv;
		}
		mv = new ModelAndView("chat1.html");
		String username = session.getAttribute("username").toString();
		User user = userrepo.findByUsername(username);

		List<Friends> l1 = friendsrepo.findByuser1(user);
		List<Friends> l2 = friendsrepo.findByuser2(user);
		// System.out.println("l1: " + l1);
		// System.out.println("l2: " + l2);
		List<User> friends = new ArrayList<User>();
		for (Friends friend : l1) {
			if (friend.getUser1().getUsername().equals(username)) {
				friends.add(friend.getUser2());
			} else {
				friends.add(friend.getUser1());
			}
		}

		for (Friends friend : l2) {
			if (friend.getUser1().getUsername().equals(username)) {
				friends.add(friend.getUser2());
			} else {
				friends.add(friend.getUser1());
			}
		}

		List<Requests> requests = requestsrepo.findByuserTo(user);
		mv.addObject("friends", friends);
		mv.addObject("requests", requests);
		// HashMap<String, List> messages = new HashMap<>();
		// for(User friendUser: friends){
		// 	List<Messages> m = messagesRepo.getLatestMessages(user.getUserid(), friendUser.getUserid(), friendUser.getUserid(), user.getUserid());
		// 	messages.put(friendUser.getUsername(), m);
		// }
		
		// mv.addObject("messages", messages);
		return mv;
	}

	// public HashMap retrieveMessages(String friendUsername){

	// }
}
