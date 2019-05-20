package com.movieit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.movieit.models.Tag;

public interface TagRepository extends JpaRepository <Tag,Tag> {

	@Query("SELECT tag_name FROM Tag WHERE movie_id=:movie_id")
	public List <String> findTags(@Param("movie_id") Integer movie_id);
}
