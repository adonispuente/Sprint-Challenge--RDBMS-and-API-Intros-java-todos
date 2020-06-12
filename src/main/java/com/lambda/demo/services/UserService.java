package com.lambda.demo.services;

import com.lambda.demo.model.User;
import com.lambda.demo.views.UserCountTodos;

import java.util.List;

/**
 * The Service that works with User Model.
 * <p>
 * Note: Emails are added through the add user process
 * Roles are added through the add user process
 * No way to delete an assigned role
 */
public interface UserService
{





    User findUserById(long id);

    User save(User user);

    List<User> findAllUsers();

    void delete(long id);

    List<UserCountTodos> getCountUserTodos();


//    User update(User user, long id);
//
//    void deleteUserTodo(long userid, long todid);
//
//    void addUserTodo(long userid, long todoid);
}