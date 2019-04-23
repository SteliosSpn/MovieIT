package com.movieit.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="favourite")
@IdClass(Favourite.class)
public class Favourite implements Serializable {

@Id
private Integer movie_id;
@Id
private String user_email;
public Integer getMovie_id() {
	return movie_id;
}
public void setMovie_id(Integer movie_id) {
	this.movie_id = movie_id;
}
public String getUser_email() {
	return user_email;
}
public void setUser_email(String user_email) {
	this.user_email = user_email;
}
}
