package com.project.netflicks.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="movieId")
    private int movieId;
    @Column(name="movieName", nullable = false)
    private String movieName;
    @Column(name="yearOfRelease", nullable = false)
    private int yearOfRelease;
    @Column(name="imgUrl", nullable = false)
    private String imgUrl;
    @Column(name="rentalCost", nullable = false)
    private int rentalCost;

//    targetEntity = Review.class ,
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="mr_fk", referencedColumnName = "movieId")
    private Set<Review> reviews;

//    @OneToMany(cascade=CascadeType.ALL, mappedBy = "mr_fk")
//    private List<Review> reviews;

    @ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(
            name="movie_actor",
            joinColumns = { @JoinColumn(name="movieId") },
            inverseJoinColumns = { @JoinColumn(name="actorId")}
    )
    private Set<Actor> actors = new HashSet<>();

    public Movie() {

    }

    public Movie(String movieName, int yearOfRelease, String imgUrl, int rentalCost, Set<Review> reviews, Set<Actor> actors) {
        this.movieName = movieName;
        this.yearOfRelease = yearOfRelease;
        this.imgUrl = imgUrl;
        this.rentalCost = rentalCost;
        this.reviews = reviews;
        this.actors = actors;
    }

    public Movie(int movieId, String movieName, int yearOfRelease, String imgUrl, int rentalCost, Set<Review> reviews, Set<Actor> actors) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.yearOfRelease = yearOfRelease;
        this.imgUrl = imgUrl;
        this.rentalCost = rentalCost;
        this.reviews = reviews;
        this.actors = actors;
    }

    public Movie(String movieName, int yearOfRelease, String imgUrl, int rentalCost) {
        this.movieName = movieName;
        this.yearOfRelease = yearOfRelease;
        this.imgUrl = imgUrl;
        this.rentalCost = rentalCost;
    }



    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getRentalCost() {
        return rentalCost;
    }

    public void setRentalCost(int rentalCost) {
        this.rentalCost = rentalCost;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }
}
