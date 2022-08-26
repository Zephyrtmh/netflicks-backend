package com.project.netflicks.controllers;

import com.project.netflicks.entities.Actor;
import com.project.netflicks.entities.Movie;
import com.project.netflicks.entities.User;
import com.project.netflicks.repositeries.ActorRepository;
import com.project.netflicks.repositeries.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path="/actor")
@CrossOrigin(origins = "http://localhost:4200")
public class ActorController {
    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping(path="/get/movie")
    public ResponseEntity<Set<Actor>> getActorsByMovieId(@RequestParam(value="movieId") int movieId) {
        try {
            Movie movie = movieRepository.findById(movieId).get();
            Set<Actor> actors = movie.getActors();
            return new ResponseEntity<Set<Actor>>(actors, HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path="/get/{actorId}")
    public ResponseEntity<Actor> getActor(@PathVariable(value="actorId") int actorId) {
        try {
            Actor actor  = actorRepository.findById(actorId).get();
            return new ResponseEntity<Actor>(actor, HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path="/get/all")
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    @PutMapping(path="edit/{actorId}")
    public ResponseEntity<Actor> editActor(@PathVariable(value="actorId") int actorId, @RequestBody Actor newActor) {
        //check if actor exists
        Actor actor = null;
        try {
            actor = actorRepository.findById(actorId).get();
        }
        catch(Exception e) {
            System.out.println("something's wrong");
            return new ResponseEntity<Actor>(actor, HttpStatus.NOT_FOUND);
        }
        actor = actorRepository.save(newActor);
        System.out.println(newActor.getActorFirstName());
        return new ResponseEntity<Actor>(newActor, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping(path="delete/{actorId}")
    public ResponseEntity<Actor> deleteActor(@PathVariable(value="actorId") int actorId) {
        //check if actor exists
        Actor actor = null;
        try {
            actor = actorRepository.findById(actorId).get();
        }
        catch(Exception e) {
            return new ResponseEntity<Actor>(actor, HttpStatus.NOT_FOUND);
        }
        actorRepository.deleteByActorId(String.valueOf(actorId));
        actorRepository.deleteById(actorId);
        return new ResponseEntity<Actor>(actor, HttpStatus.OK);
    }

    //add actor to existing movie
    @PutMapping(path="/addNewActor/{movieId}")
    public ResponseEntity<Actor> addActor(@PathVariable(value="movieId") int movieId,@RequestBody Actor actorToAdd) {
        try {
            //get existing actors in movie of interest
            Movie movieToEdit = movieRepository.findById(movieId).get();
            Set<Actor> currentActors = movieToEdit.getActors();
            // add actor to existing actors
            currentActors.add(actorToAdd);
            movieToEdit.setActors(currentActors);
            Movie updatedMovie = movieRepository.save(movieToEdit);

            //Get the new actor with auto-generated ID
            Set<Actor> newActors = updatedMovie.getActors();
            System.out.println(newActors);
            System.out.println(currentActors);
            newActors.removeAll(currentActors);
            Actor[] actors = newActors.toArray(new Actor[0]);

            return new ResponseEntity<>(actors[0], HttpStatus.OK);
        }
        catch(Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    //add existing actor to existing movie
    @PutMapping(path="/addExistingActor")
    public ResponseEntity<Actor> addExistingActor(@RequestParam(value="movieId") int movieId, @RequestParam(value="actorId") int actorId) {
        Actor actorToAdd = actorRepository.findById(actorId).get();
        Movie movieToAddTo = movieRepository.findById(movieId).get();
        //check if actor and movie exists
        if (actorToAdd != null && movieToAddTo !=null) {
            Set<Actor> existingActors = movieToAddTo.getActors();
            //check if actor already exists in actors
            if(existingActors.contains(actorToAdd)) {
                //actor already exists in movie
                return new ResponseEntity<Actor>(actorToAdd, HttpStatus.NOT_MODIFIED);
            }
            else {
                //add actor to movie
                existingActors.add(actorToAdd);
                movieToAddTo.setActors(existingActors);
                movieRepository.save(movieToAddTo);
                return new ResponseEntity<Actor>(actorToAdd, HttpStatus.OK);
            }

        }
        else {
            //either movie or actor does not exist
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
