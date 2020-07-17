package com.lambda.demo.services;

import com.lambda.demo.model.Todos;

import java.util.List;

public interface TodoService {
    Todos save(long userid, String description);

    Todos update(long id);

    Todos findTodoById(long id);
}
