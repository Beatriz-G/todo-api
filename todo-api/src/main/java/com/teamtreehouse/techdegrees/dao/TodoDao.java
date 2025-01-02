package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.exc.DaoException;
import com.teamtreehouse.techdegrees.model.Todo;

import java.util.List;

public interface TodoDao {
    void create(Todo todo) throws DaoException;
    void update(Todo todo);
    void delete(int id);
    List<Todo> findAll();
    Todo findById(int id);
}