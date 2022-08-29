package com.project.netflicks.repositeries;

import com.project.netflicks.entities.Movie;
import com.project.netflicks.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query(value="SELECT * FROM movies", nativeQuery = true)
    public List<Movie> findAllMovies ();

    @Modifying
    @Query(value="DELETE FROM movie_actor ma WHERE ma.movie_id=:movieId", nativeQuery = true)
    public void deleteMovie(@Param(value="movieId") String movieId);

    @Query(value="SELECT mr_fk FROM reviews r WHERE r.review_id = ?1", nativeQuery = true)
    public int findMovieByReviewId (int reviewId);

    @Query(value="SELECT * FROM movies m WHERE m.movie_id IN (SELECT movie_id FROM movie_actor ma WHERE ma.actor_id=?1)", nativeQuery = true)
    public List<Movie> findMoviesByActorId(int actorId);
}
