package com.movieit.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name="movie")
public class Movie{

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer movie_id;

private String name;
private Integer release_year;
private String description;
private Integer total_score;
private Integer total_votes;
private String image_url;
private String trailer_url;


public Integer getMovie_id() {
	return movie_id;
}
public void setMovie_id(Integer movie_id) {
	this.movie_id = movie_id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Integer getRelease_year() {
	return release_year;
}
public void setRelease_year(Integer release_year) {
	this.release_year = release_year;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public Integer getTotal_score() {
	return total_score;
}
public void setTotal_score(Integer total_score) {
	this.total_score = total_score;
}
public Integer getTotal_votes() {
	return total_votes;
}
public void setTotal_votes(Integer total_votes) {
	this.total_votes = total_votes;
}
public String getImage_url() {
	return image_url;
}
public void setImage_url(String image_url) {
	this.image_url = image_url;
}
public String getTrailer_url() {
	return trailer_url;
}
public void setTrailer_url(String trailer_url) {
	this.trailer_url = trailer_url;
}
	
}
