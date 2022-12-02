package com.chatapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Friends {
	@Id @GeneratedValue
	private int id;
	@ManyToOne(targetEntity=User.class)
	private User user1;
	@ManyToOne(targetEntity=User.class)
	private User user2;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser1() {
		return user1;
	}
	public void setUser1(User user1) {
		this.user1 = user1;
	}
	public User getUser2() {
		return user2;
	}
	public void setUser2(User user2) {
		this.user2 = user2;
	}
	@Override
	public String toString() {
		return "Friends [id=" + id + ", user1=" + user1 + ", user2=" + user2 + "]";
	}
	
	
}
