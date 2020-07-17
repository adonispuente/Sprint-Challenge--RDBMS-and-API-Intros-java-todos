package com.lambda.demo.repositories;


import com.lambda.demo.model.User;
import com.lambda.demo.views.UserCountTodos;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The CRUD repository connecting User to the rest of the application
 */
public interface UserRepository extends CrudRepository<User, Long>
{
//    List<UserCountTodos> getCountUserTodos();


@Query(value = "SELECT u.username as username, count(t.todoid) as counttodos FROM users u JOIN todos t ON u.userid = t.userid WHERE NOT t.completed GROUP BY u.username",
        nativeQuery = true)
    List<UserCountTodos> getUserTodos();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM todos WHERE userid = :userid",
            nativeQuery = true)
    void deleteUserTodos(long userid);

}
