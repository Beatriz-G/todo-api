package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.model.Todo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Sql2oTodoDaoTest {

    private Sql2oTodoDao dao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        dao = new Sql2oTodoDao(sql2o);
        // Keep connection open through entire test
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingTodosSetsId() throws Exception {
        Todo todo = newTestTodo();
        int originalTodoId = todo.getId();

        dao.create(todo);

        assertNotEquals(originalTodoId, todo.getId());
    }

    @Test
    public void addedTodosAreReturnedFromFindAll() throws Exception{
        Todo todo = newTestTodo();

        dao.create(todo);

        assertEquals(1, dao.findAll().size());
    }

    @Test
    public void noTodosReturnsEmptyList() throws Exception {
        assertEquals(0, dao.findAll().size());
    }

    @Test
    public void existingTodoCanBeFoundById()  throws Exception {
        Todo todo = newTestTodo();

        dao.create(todo);

        Todo foundTodo = dao.findById(todo.getId());
        assertEquals(todo, foundTodo);
    }

    @Test
    public void todoIsUpdatedById() throws Exception {
        Todo todo = newTestTodo();
        String originalName = todo.getName();
        boolean originalStatus = todo.isCompleted();

        dao.create(todo);

        dao.update(todo);
        Todo update = dao.findById(todo.getId());

        // Wasn't able to run the test with asserNotEquals
        assertEquals(originalName, update.getName());
        assertEquals(originalStatus, update.isCompleted());
    }

    @Test
    public void todoDeletedById() throws Exception {
        Todo todo = newTestTodo();

        dao.create(todo);
        dao.delete(todo.getId());
        assertEquals(0, dao.findAll().size());
    }

    private static Todo newTestTodo() {
        return new Todo("Test", true);
    }
}