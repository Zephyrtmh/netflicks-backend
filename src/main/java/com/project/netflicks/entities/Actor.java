package com.project.netflicks.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="actors")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="actorId")
    private int actorId;
    @Column(name="actorFirstName", nullable=false)
    private String actorFirstName;
    @Column(name="actorLastName", nullable=false)
    private String actorLastName;

    @Column(name="gender", nullable=false)
    private String gender;

    @Column(name="age", nullable=false)
    private int age;

    @ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE},mappedBy = "actors")
    @JsonIgnore
    private Set<Movie> movies = new HashSet<>();


    public Actor() {

    }

    public Actor(String actorFirstName, String actorLastName, String gender, int age) {
        this.actorFirstName = actorFirstName;
        this.actorLastName = actorLastName;
        this.gender = gender;
        this.age = age;
    }
    public Actor(int actorId, String actorFirstName, String actorLastName, String gender, int age, Set<Movie> movies) {
        this.actorId = actorId;
        this.actorFirstName = actorFirstName;
        this.actorLastName = actorLastName;
        this.gender = gender;
        this.age = age;
        this.movies = movies;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public String getActorFirstName() {
        return actorFirstName;
    }

    public void setActorFirstName(String actorFirstName) {
        this.actorFirstName = actorFirstName;
    }

    public String getActorLastName() {
        return actorLastName;
    }

    public void setActorLastName(String actorLastName) {
        this.actorLastName = actorLastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
