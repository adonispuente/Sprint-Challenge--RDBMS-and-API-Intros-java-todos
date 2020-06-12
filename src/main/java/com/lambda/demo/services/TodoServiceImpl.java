package com.lambda.demo.services;


import com.lambda.demo.model.Todos;
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
     UserRepository userrepos;

    @Override
    public List<Todos> findAll() {
        List<Todos> list = new ArrayList<>();
        todorepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Todos findTodoById(long id) {
        return todorepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role id " + id + " not found!"));    }

    @Override
    public Object save(Todos todo) {
        if (todo.getUsers()
                .size() > 0)
        {
            throw new EntityExistsException("User Todos are not updated through Todo.");
        }

        return todorepos.save(todo);
    }

    @Override
    public Todos findByName(String name) {
        Todos td = todorepos.findByNameIgnoreCase(name);

        if (td != null)
        {
            return td;
        } else
        {
            throw new EntityNotFoundException(name);
        }    }
}
