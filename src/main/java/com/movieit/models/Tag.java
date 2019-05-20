package com.movieit.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="tag")
@IdClass(Tag.class)
public class Tag implements Serializable {

@Id
private Integer movie_id;
@Id
private String tag_name;
public Integer getMovie_id() {
	return movie_id;
}
public void setMovie_id(Integer movie_id) {
	this.movie_id = movie_id;
}
public String getTag_name() {
	return tag_name;
}
public void setTag_name(String tag_name) {
	this.tag_name = tag_name;
}

public Tag() {
	
}

public Tag(String tag_name) {
	this.tag_name = tag_name;
}

}
