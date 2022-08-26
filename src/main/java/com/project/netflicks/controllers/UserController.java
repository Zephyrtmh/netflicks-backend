package com.project.netflicks.controllers;

import com.project.netflicks.entities.User;
import com.project.netflicks.repositeries.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="add")
    public User createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        savedUser.setPassword("");
        return savedUser;
    }

    @GetMapping(path="get/{id}")
    public User getUser(@PathVariable(value="id") Integer id) {
        return userRepository.findById(id).get();
    }

    @GetMapping(path="get/all")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping(path="login")
    public Object loginUser(@RequestBody User user) {
        System.out.println("login");
        User retrievedUser = userRepository.findByUsername(user.getUsername());

        if(retrievedUser != null) {
            System.out.println(retrievedUser);
            if (user.getPassword().equals(retrievedUser.getPassword())) {
                retrievedUser.setPassword("");
                return retrievedUser;
            }
            //password incorrect
            else {
                return false;
            }
        }
        //user not found
        else {
            return false;
        }

    }

    @PostMapping(path="register")
    public boolean registerUser(@RequestBody User user) {
        System.out.println("registering");
        //check if user exists
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser instanceof User) {
            System.out.println("user exists.");
            return false;
        }
        else {
            System.out.println("user does not exist and will be created.");
            User userToAdd = new User(user.getUsername(), user.getPassword(), user.getPermissions());
            userRepository.save(userToAdd);
            return true;
        }
    }

//    @DeleteMapping(path="delete/{id}")
//    public User deleteUser(@PathVariable(value="id") Integer id) {
//        User userToDelete = null;
//        try {
//            userToDelete = userRepository.findById(id).get();
//            userRepository.deleteById(id);
//        }
//        catch (Exception e) {
//            System.out.println("user does not exist");
//        }
//        return userToDelete;
//    }

    @DeleteMapping(path="delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value="id") Integer id) {
        User userToDelete = null;
        try {
            userToDelete = userRepository.findById(id).get();
            userRepository.deleteById(id);
        }
        catch (Exception e) {
            System.out.println("user does not exist");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(userToDelete);
    }

    @PutMapping(path="edit/{id}")
    public ResponseEntity<User> editUser(@PathVariable(value="id") Integer id, @RequestBody User user) {
        try {
            System.out.println("editing user " + id);
            User userToEdit = userRepository.findById(id).get();
            userToEdit.setUsername(user.getUsername());
            userToEdit.setPermissions(user.getPermissions());
            userRepository.save(userToEdit);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println("user does not exist");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
