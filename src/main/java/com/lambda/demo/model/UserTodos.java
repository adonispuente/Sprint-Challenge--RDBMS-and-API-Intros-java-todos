//package com.lambda.demo.model;
//
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.Objects;
//
//@Entity
//@Table(name = "usertodos", uniqueConstraints = {@UniqueConstraint(columnNames = {"userid", "todoid"})})
//public class UserTodos extends Auditable implements Serializable {
//
//    @Id
//    @ManyToOne
//    @JoinColumn(name = "userid")
//    @JsonIgnoreProperties(value = "todos")
//    private User user;
//
//    @Id
//    @ManyToOne
//    @JoinColumn(name = "todoid")
//    @JsonIgnoreProperties(value = "users")
//    private Todos todo;
//    private String todos;
//
//    public UserTodos() {
//    }
//    public UserTodos(User user, String todos) {
//        this.user= user;
//        this.todos=todos;
//
//    }
//
//    public UserTodos(User user, Todos todo) {
//        this.user = user;
//        this.todo = todo;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Todos getTodo() {
//        return todo;
//    }
//
//    public void setTodo(Todos todo) {
//        this.todo = todo;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(user, todo);    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//        {
//            return true;
//        }
//        if (obj == null || getClass() != obj.getClass())
//        {
//            return false;
//        }
//        UserTodos userTodos = (UserTodos) obj;
//        return user.equals(userTodos.user) &&
//                todo.equals(userTodos.todo);
//
//
//
//
//    }
//}
