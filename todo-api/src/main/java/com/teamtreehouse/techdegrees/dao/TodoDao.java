package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.exc.DaoException;
import com.teamtreehouse.techdegrees.model.Todo;

import java.util.List;

public interface TodoDao {
    void create(Todo todo) throws DaoException;
    void delete(int id);
    Todo findById(int id);
    List<Todo> findAll();
    void update(Todo todo);
}

