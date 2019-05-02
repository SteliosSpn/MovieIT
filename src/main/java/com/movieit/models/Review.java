package com.movieit.models;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="review")
@IdClass(Review.class)
public class Review implements Serializable{

@Id
private Integer movie_id;

@Id
private String user_email;

private String review_body;
private Date review_date;

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
public String getReview_body() {
	return review_body;
}
public void setReview_body(String review_body) {
	this.review_body = review_body;
}
public Date getReview_date() {
	return review_date;
}
public void setReview_date(Date review_date) {
	this.review_date = review_date;
}
	
}
