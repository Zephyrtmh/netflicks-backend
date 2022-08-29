package com.project.netflicks.controllers;

import com.project.netflicks.entities.Actor;
import com.project.netflicks.entities.Movie;
import com.project.netflicks.entities.Review;
import com.project.netflicks.repositeries.MovieRepository;
import com.project.netflicks.repositeries.ReviewRepository;
import com.project.netflicks.util.GenerateMovies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path="/movie")
@CrossOrigin(origins = "http://localhost:4200")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping(path="get/movies")
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @GetMapping(path="get/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable(value="id") int movieId) {
        try {
            Movie movie = movieRepository.findById(movieId).get();
            return new ResponseEntity<>(movie, HttpStatus.OK);
        }
        catch(Exception e) {
            System.out.println("Movie does not exist");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }



    @PutMapping(path="edit/{id}")
    public ResponseEntity<Movie> editMovie(@PathVariable(value="id") Integer id, @RequestBody Movie newMovie) {
        try {
            Movie movieToEdit = movieRepository.findById(id).get();
            movieRepository.save(newMovie);
            return new ResponseEntity<>(newMovie, HttpStatus.OK);
        }
        catch(Exception e) {
            System.out.println("Movie does not exist");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @DeleteMapping(path="delete/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable(value="id") Integer movieId) {
        try {
            Movie movieToDelete = movieRepository.findById(movieId).get();
            //delete movie from map table
            movieRepository.deleteMovie(Integer.toString(movieId));
            //delete movie from movie table
            movieRepository.deleteById(movieId);
            //delete reviews linked to movie from review table
            List<Review> reviewsToDelete = reviewRepository.findReviewByMovieId(movieId);
            for (Review review: reviewsToDelete) {
                reviewRepository.delete(review);
            }
            return new ResponseEntity<>(movieToDelete, HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println("movie does not exist");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    //Get movie Id of certain review
    @GetMapping(path="get/reviewId/{reviewId}")
    public ResponseEntity<Movie> findMovieIdbyReviewId(@PathVariable(value="reviewId") int reviewId) {
        //check if reviewId exists
        try {
            int movieId = movieRepository.findMovieByReviewId(reviewId);
            Movie movie = movieRepository.findById(movieId).get();
            return new ResponseEntity<>(movie, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path="get/actorId/{actorId}")
    public ResponseEntity<List<Movie>> findMovieIdbyActorId(@PathVariable(value="actorId") int actorId) {
        //check if reviewId exists
        try {
            List<Movie> movies = movieRepository.findMoviesByActorId(actorId);
            return new ResponseEntity<>(movies, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path="add")
    public Movie addMovie(@RequestBody Movie movieToAdd) {
        return movieRepository.save(movieToAdd);
    }

    @GetMapping(path="addFakes")
    public void addMovie() {
        List<Movie> moviesToAdd = GenerateMovies.generate();
        for(Movie movie: moviesToAdd) {
            System.out.println(movie.getReviews());
            movieRepository.save(movie);
        }
    }
}
