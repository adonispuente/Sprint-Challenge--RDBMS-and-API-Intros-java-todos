package com.lambda.demo.repositories;

import com.lambda.demo.model.Todos;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository <Todos, Long>{

    Todos findByNameIgnoreCase(String name);
}
