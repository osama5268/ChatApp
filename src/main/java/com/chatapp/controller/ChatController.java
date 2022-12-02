package com.chatapp.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.pretty.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
// import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chatapp.dao.MessagesRepo;
import com.chatapp.dao.UserRepo;
import com.chatapp.model.ChatMessage;
import com.chatapp.model.Messages;
import com.chatapp.model.User;

@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private MessagesRepo messagesRepo;
    @Autowired
    private UserRepo userRepo;
    @MessageMapping("/send/{usernameTo}")
    // @SendTo("/receive")
    public void sendMessage(@DestinationVariable String usernameTo, ChatMessage message,
            SimpMessageHeaderAccessor headerAccessor) {
        try {
            System.out.println("\nmessage " + message.getMessage() + " sent to: " + usernameTo);
            // return message;
            message.setTime(LocalDateTime.now());
            String usernameFrom = headerAccessor.getSessionAttributes().get("username").toString();
            message.setSentFrom(usernameFrom);
            message.setSentTo(usernameTo);

            //making database object
            User sentTo = userRepo.findByUsername(usernameTo);
            User sentFrom = userRepo.findByUsername(usernameFrom);
            Messages messages = new Messages();
            messages.setMessage(message.getMessage());
            messages.setTime(message.getTime());
            messages.setUserFrom(sentFrom);
            messages.setUserTo(sentTo);
            messagesRepo.save(messages);


            simpMessagingTemplate.convertAndSendToUser(usernameTo, "/reply", message);
        } catch (Exception e) {
            System.out.println("error encountered in chat controller!");
            System.out.println(e);
        }
    }

    //this method serves the client to get the latest messages
    @RequestMapping(value = "/chat/getHistory", method = RequestMethod.GET)
    @ResponseBody
    public HashMap getLatestMessages(String username1, HttpSession session){
        // String username2= headerAccessor.getSessionAttributes().get("username").toString();
        String username2 = session.getAttribute("username").toString();
        User user1 = userRepo.findByUsername(username1);
        User user2 = userRepo.findByUsername(username2);
        List<Messages> messages = messagesRepo.getLatestMessages(user1.getUserid(), user2.getUserid(), user2.getUserid(), user1.getUserid());
        HashMap<String, List> m = new HashMap<>();
        m.put("messages", messages);
        return m;
    }
}
