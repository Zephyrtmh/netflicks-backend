package com.project.netflicks.controllers;

import com.project.netflicks.entities.Movie;
import com.project.netflicks.entities.Review;
import com.project.netflicks.repositeries.MovieRepository;
import com.project.netflicks.repositeries.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path="/review")
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping(path="/get/all")
    public List<Review> getAllReviews() {
        return reviewRepository.findAllReviews();
    }

    @GetMapping(path="/get/movie/{movieId}")
    public List<Review> getReviewsByMovie(@PathVariable(value="movieId") int movieId) {
        return reviewRepository.findReviewByMovieId(movieId);
    }

    @PutMapping(path="/edit/{reviewId}")
    public Review editReview(@PathVariable(value="reviewId") int reviewId, @RequestBody Review editedReview) {
        Review reviewToEdit = reviewRepository.findById(reviewId).get();
        reviewRepository.save(editedReview);
        return editedReview;
    }

    @PostMapping(path="/add")
    public Review addReview(@RequestBody Review reviewToAdd) {
        return reviewRepository.save(reviewToAdd);
    }

    @GetMapping(path="get/movieId/{reviewId}")
    public int findMovieIdbyReviewId(@PathVariable(value="reviewId") int reviewId) {
        return reviewRepository.findMovieIdbyReviewId(reviewId);
    }

    //add review to existing movie
    @PutMapping(path="/{movieId}/addReview")
    public ResponseEntity<Review> addReview(@PathVariable(value="movieId") int movieId, @RequestBody Review reviewToAdd) {
        try {
            Movie movieToEdit = movieRepository.findById(movieId).get();
            Set<Review> currentReviews = movieToEdit.getReviews();
            currentReviews.add(reviewToAdd);
            movieToEdit.setReviews(currentReviews);
            movieRepository.save(movieToEdit);
            return new ResponseEntity<>(reviewToAdd, HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(reviewToAdd, HttpStatus.NOT_FOUND);
        }
    }


}
