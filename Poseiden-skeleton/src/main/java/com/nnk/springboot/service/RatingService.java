package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.exceptions.NonExistantRatingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService implements IRatingService {

    @Autowired
    private RatingRepository ratingRepository;
    Logger logger  = LoggerFactory.getLogger(RatingService.class);


    @Override
    public Rating getRatingById(int id) throws NonExistantRatingException {
        if (ratingRepository.existsById(id) == false) {
            throw new NonExistantRatingException("Rating id: " + id + " doesn't have a match in db");
        }
        logger.debug("Get Rating with id: " + id);
        Rating rating = ratingRepository.findById(id).get();
        return rating;
    }

    @Override
    public List<Rating> getAllRatings() {
        logger.debug("Get all ratings");
        List<Rating> ratings = ratingRepository.findAll();
        return ratings;
    }

    @Override
    public void addRating(Rating rating) {
        logger.debug("Save new rating");
        ratingRepository.save(rating);
    }

    @Override
    public void updateRating(Rating rating) {
        logger.debug("Update new rating");
        ratingRepository.save(rating);
    }

    @Override
    public void deleteRating(Rating rating) {
        logger.debug("Delete rating from db");
        ratingRepository.delete(rating);
    }
}
