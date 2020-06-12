package com.lambda.demo.controllers;


import com.lambda.demo.model.Todos;
import com.lambda.demo.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodosController {
    @Autowired
    TodoService todoService;



    @GetMapping(value = "/todos",
            produces = {"application/json"})
    public ResponseEntity<?> listRoles()
    {
        List<Todos> allRoles = todoService.findAll();
        return new ResponseEntity<>(allRoles,
                HttpStatus.OK);
    }


    @GetMapping(value = "/role/{todoId}",
            produces = {"application/json"})
    public ResponseEntity<?> getTodoById(@PathVariable Long todoId)
    {
        Todos t = todoService.findTodoById(todoId);
        return new ResponseEntity<>(t,
                HttpStatus.OK);
    }


    @GetMapping(value = "/role/name/{roleName}",
            produces = {"application/json"})
    public ResponseEntity<?> getRoleByName(
            @PathVariable String todoName)
    {
        Todos t = todoService.findByName(todoName);
        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    @PostMapping(value = "/todo",
            consumes = {"application/json"})
    public ResponseEntity<?> addNewRole(@Valid @RequestBody Todos newTodo)
    {
        // ids are not recognized by the Post method
        newTodo.setTodoid(0);
        newTodo = (Todos) todoService.save(newTodo);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newRoleURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{todoId}")
                .buildAndExpand(newTodo.getTodoid())
                .toUri();
        responseHeaders.setLocation(newRoleURI);

        return new ResponseEntity<>(null,
                responseHeaders,
                HttpStatus.CREATED);
    }

}
