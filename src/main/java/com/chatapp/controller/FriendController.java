package com.chatapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.chatapp.dao.FriendsRepo;
import com.chatapp.dao.RequestsRepo;
import com.chatapp.dao.UserRepo;
import com.chatapp.model.Friends;
import com.chatapp.model.Requests;
import com.chatapp.model.User;

@Controller
public class FriendController {
	@Autowired
	RequestsRepo requestsrepo;
	@Autowired
	UserRepo userrepo;
	@Autowired
	FriendsRepo friendsrepo;
	@RequestMapping(value="/sendrequest", method=RequestMethod.POST)
	public RedirectView sendFriendRequest(HttpSession session, @RequestParam("username") String user1) {
		if(session.getAttribute("isLoggedIn") == null) {
			return new RedirectView("/");
		}
		String user2 = session.getAttribute("username").toString();
		Requests request = new Requests();
		
		User userTo = userrepo.findByUsername(user1);
		User userFrom = userrepo.findByUsername(user2);
		
		request.setUserFrom(userFrom);
		request.setUserTo(userTo);
		requestsrepo.save(request);
		return new RedirectView("/chat");
		
	}
	@RequestMapping(value="requests/accept/{requestid}", method=RequestMethod.POST)
	public RedirectView acceptRequest(@PathVariable("requestid") int reqid,HttpSession session) {
		if(session.getAttribute("isLoggedIn") == null) {
			return new RedirectView("/");
		}
		Requests request = requestsrepo.findById(reqid).orElse(null);
		if(request == null) {
			return new RedirectView("/chat");
		}
		
		Friends friends = new Friends();
		friends.setUser1(request.getUserFrom());
		friends.setUser2(request.getUserTo());
		friendsrepo.save(friends);
		requestsrepo.delete(request);
		return new RedirectView("/chat");
		
	}
	@RequestMapping(value="requests/reject/{requestid}", method=RequestMethod.POST)
	public RedirectView rejectRequest(@PathVariable("requestid") int reqid,HttpSession session) {
		if(session.getAttribute("isLoggedIn") == null) {
			return new RedirectView("/");
		}
		Requests request = requestsrepo.findById(reqid).orElse(null);
		if(request == null) {
			return new RedirectView("/chat");
		}
		requestsrepo.delete(request);
		return new RedirectView("/chat");
		
	}
}
