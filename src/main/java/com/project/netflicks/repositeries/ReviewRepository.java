package com.project.netflicks.repositeries;

import com.project.netflicks.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query(value="SELECT * FROM reviews r WHERE r.mr_fk = ?1", nativeQuery = true)
    public List<Review> findReviewByMovieId (int movieId);

    @Query(value="SELECT * FROM reviews", nativeQuery = true)
    public List<Review> findAllReviews();


}
