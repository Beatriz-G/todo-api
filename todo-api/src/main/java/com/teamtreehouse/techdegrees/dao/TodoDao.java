package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.exc.DaoException;
import com.teamtreehouse.techdegrees.model.Todo;

import java.util.List;

public interface TodoDao {
    void create(Todo todo) throws DaoException;
    void update(int id, String name, boolean isCompleted);
    void delete(int id);
    Todo findById(int id);
    List<Todo> findAll();
}
