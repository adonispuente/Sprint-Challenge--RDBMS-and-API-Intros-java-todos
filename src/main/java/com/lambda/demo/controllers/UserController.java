package com.lambda.demo.controllers;

import com.lambda.demo.model.User;
import com.lambda.demo.services.UserService;
import com.lambda.demo.views.UserCountTodos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{

    @Autowired
    private UserService userService;

//    http://localhost:2021/users/users GET
    @GetMapping(value = "/users", produces = {"application/json"})
    public ResponseEntity<?> getAllUsers() {
        List<User> allUsers = userService.findAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

//    http://localhost:2021/users/user/4 GET
    @GetMapping(value = "/user/{userid}", produces = {"application/json"})
    public ResponseEntity<?> getUserById(@PathVariable long userid) {
        User user = userService.findUserById(userid);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//  http://localhost:2021/users/user POST
    @PostMapping(value = "/user", consumes = {"application/json"})
    public ResponseEntity<?> addNewUser(@Valid @RequestBody User newuser) throws URISyntaxException {
        newuser.setUserid(0);
        newuser = userService.save(newuser);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(newuser.getUserid())
                .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }
//    DELETE http://localhost:2021/users/userid/10
@DeleteMapping(value = "/userid/{userid}")
public ResponseEntity<?> deleteCustomersById(@PathVariable long userid)
{
    userService.delete(userid);
    return new ResponseEntity<>(HttpStatus.OK);
}


//http://localhost:2021/users/users/todos MISSING THIS GET gtdsgrgtsfgrstghtsrgrtsgregreaga
@GetMapping(value = "/users/todos", produces = {"application/json"})
public ResponseEntity<?> getUserCountTodos()
{
    List<UserCountTodos> myUsers = userService.getCountUserTodos();
    return new ResponseEntity<>(myUsers, HttpStatus.OK);
}

}