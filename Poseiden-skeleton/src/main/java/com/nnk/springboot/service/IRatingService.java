package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.exceptions.NonExistantRatingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IRatingService {

    Rating getRatingById(int id) throws NonExistantRatingException;
    List<Rating> getAllRatings();
    void addRating(Rating rating);
    void updateRating(Rating rating);
    void deleteRating(Rating rating);
}
