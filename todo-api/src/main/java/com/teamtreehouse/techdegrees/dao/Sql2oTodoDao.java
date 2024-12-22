package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.exc.DaoException;
import com.teamtreehouse.techdegrees.model.Todo;

import java.util.List;

public class Sql2oTodoDao implements TodoDao {
    @Override
    public void add(Todo todo) throws DaoException {
        
    }

    @Override
    public List<Todo> findAll() {
        return List.of();
    }
}
