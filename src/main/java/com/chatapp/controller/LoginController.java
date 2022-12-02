package com.chatapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.chatapp.dao.UserRepo;
import com.chatapp.model.User;

@Controller
public class LoginController {
	@Autowired
	UserRepo userrepo;
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public RedirectView login(@RequestParam("username")String username,@RequestParam("password") String password, HttpSession session){
		RedirectView rv;
		User user = userrepo.findByUsername(username);
		if(user == null) {
			System.out.println("user not found");
			rv = new RedirectView("/");
		}
		if(user.getPassword().equals(password)) {
			session.setAttribute("isLoggedIn", true);
			session.setAttribute("username", username);
			rv = new RedirectView("chat");
		}
		else {
			System.out.println("incorrect password");
			rv = new RedirectView("/");
		}
		return rv;
	}
	@RequestMapping(value="logout", method=RequestMethod.GET)
	public RedirectView logout(HttpSession session) {
		RedirectView rv = new RedirectView("/");
		session.removeAttribute("username");
		session.removeAttribute("isLoggedIn");
		return rv;
	}
	
}
