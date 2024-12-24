package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.exc.DaoException;
import com.teamtreehouse.techdegrees.model.Todo;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oTodoDao implements TodoDao {
    private final Sql2o sql2o;

    public Sql2oTodoDao(Sql2o sql2o) {
       this.sql2o = sql2o;
   }

   // Create
    @Override
    public void create(Todo todo)  throws DaoException {
        String sql = "INSERT INTO todos(name, isCompleted) VALUES(:name, :isCompleted)";

        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .addParameter("name", todo.getName())
                    .addParameter("isCompleted", todo.isCompleted())
                    .executeUpdate()
                    .getKey();
            todo.setId(id);
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Problem creating todo");
        }
    }

    // Delete
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM todos WHERE id = :id";

        try(Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    // Find by Id
    @Override
    public Todo findById(int id) {
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM todos WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Todo.class);
        }
    }

    // findAll
    @Override
    public List<Todo> findAll() {
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM todos")
                    .executeAndFetch(Todo.class);
        }
    }

    // Update
    @Override
    public void update(Todo todo) {
        String sql = "UPDATE todos SET name = :name, isCompleted = :isCompleted WHERE id = :id";

        try(Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", todo.getId())
                    .addParameter("name", todo.getName())
                    .addParameter("isCompleted", todo.isCompleted())
                    .executeUpdate();
        }
    }
}