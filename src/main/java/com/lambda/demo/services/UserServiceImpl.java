package com.lambda.demo.services;

import com.lambda.demo.model.Todos;
import com.lambda.demo.model.User;
import com.lambda.demo.model.UserTodos;
import com.lambda.demo.model.Useremail;
import com.lambda.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
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


    @Autowired
    private TodoService todoService;

    @Autowired
    private UserAuditing userAuditing;

    public User findUserById(long id) throws EntityNotFoundException
    {
        return userrepos.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User id " + id + " not found!"));
    }

    @Override
    public List<User> findByNameContaining(String username)
    {
        return userrepos.findByUsernameContainingIgnoreCase(username.toLowerCase());
    }

    @Override
    public List<User> findAll()
    {
        List<User> list = new ArrayList<>();
        /*
         * findAll returns an iterator set.
         * iterate over the iterator set and add each element to an array list.
         */
        userrepos.findAll()
            .iterator()
            .forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        userrepos.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User id " + id + " not found!"));
        userrepos.deleteById(id);
    }

    @Override
    public User findByName(String name)
    {
        User uu = userrepos.findByUsername(name.toLowerCase());
        if (uu == null)
        {
            throw new EntityNotFoundException("User name " + name + " not found!");
        }
        return uu;
    }

    @Transactional
    @Override
    public User save(User user)
    {
        User newUser = new User();

        if (user.getUserid() != 0)
        {
            User oldUser = userrepos.findById(user.getUserid())
                .orElseThrow(() -> new EntityNotFoundException("User id " + user.getUserid() + " not found!"));

            for (UserTodos ut : oldUser.getTodos())
            {
                deleteUserTodo(ut.getUser().getUserid(), ut.getTodo().getTodoid());
            }
            newUser.setUserid(user.getUserid());
        }

        newUser.setUsername(user.getUsername()
            .toLowerCase());
        newUser.setPassword(user.getPassword());
        newUser.setPrimaryemail(user.getPrimaryemail()
            .toLowerCase());

        newUser.getTodos()
            .clear();
        if (user.getUserid() == 0)
        {
            for (UserTodos t : user.getTodos())
            {
                Todos newTodo = todoService.findTodoById(t.getTodo().getTodoid());

                newUser.addTodo(newTodo);
            }
        } else
        {
            for (UserTodos ut : user.getTodos())
            {
                addUserTodo(ut.getUser().getUserid(), ut.getTodo().getTodoid());
            }
        }

        newUser.getUseremails()
            .clear();
        for (Useremail ue : user.getUseremails())
        {
            newUser.getUseremails()
                .add(new Useremail(newUser,
                    ue.getUseremail()));
        }

        return userrepos.save(newUser);
    }

    @Transactional
    @Override
    public User update(
        User user,
        long id)
    {
        User currentUser = findUserById(id);

        if (user.getUsername() != null)
        {
            currentUser.setUsername(user.getUsername()
                .toLowerCase());
        }

        if (user.getPassword() != null)
        {
            currentUser.setPassword(user.getPassword());
        }

        if (user.getPrimaryemail() != null)
        {
            currentUser.setPrimaryemail(user.getPrimaryemail()
                .toLowerCase());
        }

        if (user.getTodos()
            .size() > 0)
        {
            for (UserTodos ut : currentUser.getTodos())
            {
                deleteUserTodo(ut.getUser()
                                       .getUserid(),
                               ut.getTodo()
                                       .getTodoid());
            }

            // add the new roles for the user we are replacing
            for (UserTodos ur : user.getTodos())
            {
                addUserTodo(currentUser.getUserid(),
                            ur.getTodo()
                                    .getTodoid());
            }
        }

        if (user.getUseremails()
            .size() > 0)
        {
            currentUser.getUseremails()
                .clear();
            for (Useremail ue : user.getUseremails())
            {
                currentUser.getUseremails()
                    .add(new Useremail(currentUser,
                        ue.getUseremail()));
            }
        }

        return userrepos.save(currentUser);
    }

    @Transactional
    @Override
    public void deleteUserTodo(long userid, long todoid)
    {
        userrepos.findById(userid)
                .orElseThrow(() -> new EntityNotFoundException("User id " + userid + " Not found"));

        todoService.findTodoById(todoid);

        if (userrepos.checkUserRolesCombo(userid, todoid).getCount() > 0)
        {
            userrepos.deleteUserRoles(userid, todoid);
        } else
        {
            throw new EntityNotFoundException("Role and User Combination Does not exits");
        }
    }

    @Transactional
    @Override
    public void addUserTodo(
            long userid,
            long todoid)
    {
        userrepos.findById(userid)
                .orElseThrow(() -> new EntityNotFoundException("User id " + userid + " not found!"));
        todoService.findTodoById(todoid);

        if (userrepos.checkUserRolesCombo(userid,
                                          todoid)
                .getCount() <= 0)
        {
            userrepos.insertUserRoles(userAuditing.getCurrentAuditor()
                                              .get(),
                                      userid,
                                      todoid);
        } else
        {
            throw new EntityExistsException("Todo and User Combination Already Exists");
        }
    }
}
