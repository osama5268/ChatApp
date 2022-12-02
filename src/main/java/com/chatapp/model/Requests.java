package com.chatapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class Requests {
	@Id @GeneratedValue
	private int id;
	@ManyToOne(targetEntity=User.class)
	private User userFrom;
	@ManyToOne(targetEntity=User.class)
	private User userTo;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUserFrom() {
		return userFrom;
	}
	public void setUserFrom(User user_from) {
		this.userFrom = user_from;
	}
	public User getUserTo() {
		return userTo;
	}
	public void setUserTo(User user_to) {
		this.userTo = user_to;
	}
	@Override
	public String toString() {
		return "Requests [id=" + id + ", user_from=" + userFrom + ", user_to=" + userTo + "]";
	}
	
}
