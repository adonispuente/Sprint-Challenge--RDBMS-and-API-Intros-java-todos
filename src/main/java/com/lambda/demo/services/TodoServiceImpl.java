package com.lambda.demo.services;


import com.lambda.demo.model.Todos;
import com.lambda.demo.model.User;
import com.lambda.demo.repositories.TodoRepository;
import com.lambda.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "todoService")
public class TodoServiceImpl implements TodoService{

    @Autowired
    TodoRepository todorepos;

    @Autowired
    UserService userService;

    @Transactional
    @Override
    public Todos update(long todoid)
    {

            Todos currentTodo = todorepos.findById(todoid)
                    .orElseThrow(()-> new EntityNotFoundException("Todo " + todoid + " Not Found"));

            currentTodo.setCompleted(true);
            return todorepos.save(currentTodo);


    }


    @Transactional
    @Override
    public Todos save(
            long userid,
            String description)
    {
        User currentUser = userService.findUserById(userid);

        Todos newTodo = new Todos(currentUser,
                description);
        return todorepos.save(newTodo);
    }

    public Todos findTodoById(long id) throws EntityNotFoundException
    {
        return todorepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo id " + id + " not found!"));
    }

}
