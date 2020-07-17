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
import java.net.URISyntaxException;
import java.util.List;
@RestController
@RequestMapping("/todos")
public class TodosController
{

    @Autowired
    TodoService todoService;


    //POST http://localhost:2021/todos/user/4
    @PostMapping(value = "/user/{userid}")
    public ResponseEntity<?> addNewTodo(
            @PathVariable
                    long userid,
            @RequestBody
                    Todos todo) throws URISyntaxException
    {
        Todos newTodo = todoService.save(userid,
                todo.getDescription());

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newTodoURI = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("/todos/user/{userid}")
                .buildAndExpand(newTodo.getTodoid())
                .toUri();
        responseHeaders.setLocation(newTodoURI);

        return new ResponseEntity<>(null,
                responseHeaders,
                HttpStatus.CREATED);
    }
//PATCH http://localhost:2021/todos/todo/7 grdsfreafrdgregrewwafefeafrdfrda
    @PatchMapping(value = "/todo/{todoid}")
    public ResponseEntity<?> updateTodo(@PathVariable long todoid)
    {
        todoService.update(todoid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}