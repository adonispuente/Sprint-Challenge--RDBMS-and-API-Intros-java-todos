package com.lambda.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "todos")
public class Todos extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long todoid;

    @Column(nullable = false,
            unique = true)
    private String name;

    @OneToMany(mappedBy = "todos", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "todo")
    private List<UserTodos> users = new ArrayList<>();

    public Todos() {
    }
    public Todos(String name){
        this.name = name.toUpperCase();
    }

    public Todos(User u1, String give_joe_access_rights) {
    }

    public long getTodoid() {
        return todoid;
    }

    public void setTodoid(long todoid) {
        this.todoid = todoid;
    }

    public String getName() {

        if (name == null)
        {
            return null;
        } else
        {
            return name.toUpperCase();
        }
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public List<UserTodos> getUsers() {
        return users;
    }

    public void setUsers(List<UserTodos> users) {
        this.users = users;
    }
}
