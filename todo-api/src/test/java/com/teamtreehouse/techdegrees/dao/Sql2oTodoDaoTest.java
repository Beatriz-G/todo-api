package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.exc.DaoException;
import com.teamtreehouse.techdegrees.model.Todo;
import junit.framework.TestCase;
import org.eclipse.jetty.util.IO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.io.IOException;

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
    public void addingTodoSetsId() throws Exception {
        Todo todo = newTestCourse();
        int originalTodoId = todo.getId();

        dao.create(todo);

        assertNotEquals(originalTodoId, todo.getId());
    }

    @Test
    public void addedTodosAreReturnedFromFindAll() throws Exception{
        Todo todo = newTestCourse();

        dao.create(todo);

        assertEquals(1, dao.findAll().size());
    }

    @Test
    public void noTodosReturnsEmptyList() throws IOException {
        assertEquals(0, dao.findAll().size());
    }

    @Test
    public void existingTodosCanBeFoundById()  throws IOException, DaoException {
        Todo todo = newTestCourse();

        dao.create(todo);

        Todo foundTodo = dao.findById(todo.getId());
        assertEquals(todo, foundTodo);
    }

    private static Todo newTestCourse() {
        return new Todo("Test", true);
        //http://test.com
    }



}