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
    public ResponseEntity<List<Review>> getAllReviews() {
        try {
            List<Review> reviews = reviewRepository.findAllReviews();
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(path="/get/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable(value="reviewId") int reviewId) {
        try {
            Review review = reviewRepository.findById(reviewId).get();
            return new ResponseEntity<>(review, HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path="/get/movie/{movieId}")
    public ResponseEntity<List<Review>> getReviewsByMovie(@PathVariable(value="movieId") int movieId) {
        try {
            List<Review> reviews = reviewRepository.findReviewByMovieId(movieId);
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        }
        catch(Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path="/edit/{reviewId}")
    public ResponseEntity<Review> editReview(@PathVariable(value="reviewId") int reviewId, @RequestBody Review editedReview) {
        try {
            Review reviewToEdit = reviewRepository.findById(reviewId).get();
            reviewRepository.save(editedReview);
            return new ResponseEntity<>(editedReview, HttpStatus.OK);
        }
        catch(Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path="/add")
    public Review addReview(@RequestBody Review reviewToAdd) {
        return reviewRepository.save(reviewToAdd);
    }


    //add review to existing movie
    @PutMapping(path="/addReview/{movieId}")
    public ResponseEntity<Review> addReview(@PathVariable(value="movieId") int movieId, @RequestBody Review reviewToAdd) {
        try {
            //get movie to add review into
            Movie movieToEdit = movieRepository.findById(movieId).get();
            Set<Review> currentReviews = movieToEdit.getReviews();
            //add review to existing reviews
            currentReviews.add(reviewToAdd);
            movieToEdit.setReviews(currentReviews);
            movieRepository.save(movieToEdit);
            return new ResponseEntity<>(reviewToAdd, HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path="delete/{reviewId}")
    public ResponseEntity<Review> deleteReview(@PathVariable(value="reviewId") int reviewId) {
        try {
            Review reviewToDelete = reviewRepository.findById(reviewId).get();
            reviewRepository.deleteById(reviewId);
            return new ResponseEntity<>(reviewToDelete, HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


}
