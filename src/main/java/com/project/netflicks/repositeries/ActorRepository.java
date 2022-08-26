package com.project.netflicks.repositeries;

import com.project.netflicks.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

    @Modifying
    @Query(value = "DELETE FROM movie_actor ma WHERE ma.actor_id=:actorId", nativeQuery = true)
    public void deleteByActorId(@Param(value="actorId") String actorId);

    @Query(value= "SELECT FROM movie_actor ma WHERE ma.actor_id=:actorId AND ma.movie_id=:movieId", nativeQuery = true)
    public Actor checkIfMovieContainsActor(@Param(value="actorId") String actorId, @Param(value="movieId") String movieId);
}
