package com.movieit.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Tag {

	@Id
	private String name;
	@ManyToMany(mappedBy = "tags")
	private List<Movie> movies;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	public Tag(String name, List<Movie> movies) {
		this.name = name;
		this.movies = movies;
	}

	public Tag() {
	}

	public Tag(String name) {
		this.name = name;
	}

}
