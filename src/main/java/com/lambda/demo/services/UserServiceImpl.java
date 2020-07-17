package com.lambda.demo.services;

import com.lambda.demo.model.Todos;
import com.lambda.demo.model.User;

import com.lambda.demo.repositories.UserRepository;
import com.lambda.demo.views.UserCountTodos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements UserService Interface
 */
@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService
{
    /**
     * Connects this service to the User table.
     */
    @Autowired
    private UserRepository userrepos;


//    @Autowired
//    private TodoService todoService;

    @Autowired
    private UserAuditing userAuditing;


    public User findUserById(long id) throws EntityNotFoundException
    {
        return userrepos.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User id " + id + " not found!"));
    }




    @Transactional
    @Override
    public void delete(long id)
    {
        userrepos.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User id " + id + " not found!"));
        userrepos.deleteById(id);
    }



    @Transactional
    @Override
    public User save(User user) {
        User newUser = new User();

        newUser.setUsername(user.getUsername().toLowerCase());
        newUser.setPrimaryemail(user.getPrimaryemail());
        newUser.setPassword(user.getPassword());

        newUser.getTodos().clear();
        for (Todos t : user.getTodos()) {
            newUser.getTodos().add(new Todos(newUser, t.getDescription()));
        }

        return userrepos.save(newUser);
    }

    @Override
    public List<User> findAllUsers() {
        List<User> allUsers = new ArrayList<>();

        userrepos
                .findAll()
                .iterator()
                .forEachRemaining(allUsers::add);
        return allUsers;    }

    @Override
    public List<UserCountTodos> getCountUserTodos() {
        return userrepos.getUserTodos();
    }

}
