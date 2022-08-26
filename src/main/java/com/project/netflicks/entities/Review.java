package com.project.netflicks.entities;

import javax.persistence.*;

@Entity
@Table(name="reviews")
public class Review {
    @Id
    @Column(name="reviewId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;
    @Column(name="dateOfReview", nullable = false)
    private String dateOfReview;
    @Column(name="reviewAuthor", nullable = false)
    private String reviewAuthor;

    @Column(name="rating", nullable = false)
    private float rating;

    @Column(name="reviewContent", columnDefinition = "LONGTEXT", nullable = false)
    private String reviewContent;

    public Review() {

    }

    public Review(String dateOfReview, String reviewAuthor, float rating, String reviewContent) {
        this.dateOfReview = dateOfReview;
        this.reviewAuthor = reviewAuthor;
        this.rating = rating;
        this.reviewContent = reviewContent;
    }

    public Review(int reviewId, String dateOfReview, String reviewAuthor, float rating, String reviewContent) {
        this.reviewId = reviewId;
        this.dateOfReview = dateOfReview;
        this.reviewAuthor = reviewAuthor;
        this.rating = rating;
        this.reviewContent = reviewContent;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getDateOfReview() {
        return dateOfReview;
    }

    public void setDateOfReview(String dateOfReview) {
        this.dateOfReview = dateOfReview;
    }

    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public void setReviewAuthor(String reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

}
