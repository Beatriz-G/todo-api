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

    @Override
    public void add(Todo todo) throws DaoException {
        String sql = "INSERT INTO todo(name, url) VALUES (:name, :ulr)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(todo)
                    .executeUpdate()
                    .getKey();
            todo.setId(id);
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Problem adding course");
        }
    }

    @Override
    public List<Todo> findAll() {
        return List.of();
    }
}
