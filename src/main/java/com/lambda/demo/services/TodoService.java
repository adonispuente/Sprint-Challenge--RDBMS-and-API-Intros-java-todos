package com.lambda.demo.services;

import com.lambda.demo.model.Todos;

import java.util.List;

public interface TodoService {
    /**
     * Returns a list of all Role objects
     *
     * @return list of all Role object
     */
    List<Todos> findAll();


    Todos findTodoById(long id);


    Object save(Todos todo);


    Todos findByName(String name);
}
