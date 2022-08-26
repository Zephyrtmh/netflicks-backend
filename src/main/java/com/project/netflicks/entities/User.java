package com.project.netflicks.entities;

import javax.persistence.*;

@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userId", unique=true)
    private int userId;
    @Column(name="username", unique=true, nullable = false)
    private String username;
    @Column(name="password", nullable = false)
    private String password;
    @Column(name="permissions")
    private String permissions;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.permissions = "normal";
    }

    public User(String username, String password, String permissions) {
        this.username = username;
        this.password = password;
        this.permissions = permissions;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
