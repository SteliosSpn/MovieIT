package com.movieit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieit.models.Rating;

public interface RatingRepository extends JpaRepository<Rating,Rating> {

}
