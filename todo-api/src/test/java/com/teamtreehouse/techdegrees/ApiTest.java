package com.teamtreehouse.techdegrees;

import com.google.gson.Gson;
import com.teamtreehouse.techdegrees.dao.Sql2oTodoDao;
import com.teamtreehouse.techdegrees.dao.Sql2oTodoDaoTest;
import com.teamtreehouse.techdegrees.model.Todo;
import com.teamtreehouse.testing.ApiClient;
import com.teamtreehouse.testing.ApiResponse;
import junit.framework.TestCase;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ApiTest  {

    public static final String Port = "4568";
    public static final String TEST_DATASOURCE = "jdbc:h2:mem:testing";
    private Connection conn;
    private ApiClient client;
    private Gson gson;
    private Sql2oTodoDao todoDao;
    private ApiClient todo;

    @BeforeClass
    public static void startServer() {
        String[] args = {Port, TEST_DATASOURCE};
        Api.main(args);

    }

    @AfterClass
    public static void stopServer() {
        Spark.stop();
    }


    @Before
    public void setUp() throws Exception {
        Sql2o sql2o = new Sql2o(TEST_DATASOURCE + ";INIT=RUNSCRIPT from 'classpath:db/init.sql'", "", "");
        todoDao = new Sql2oTodoDao(sql2o);
        conn = sql2o.open();
        client = new ApiClient("http://localhost:" + Port);
        gson = new Gson();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingTodosReturnsCreatedStatus() throws Exception {
        Map<String, String> values = new HashMap<>();
        values.put("name", "Test");
        values.put("url", "http://test.com");

        ApiResponse res = client.request("POST", "/api/v1/todos", gson.toJson(values));

        assertEquals(201, res.getStatus());
    }

    @Test
    public void todosCanBeAccessedById() throws Exception {
        Todo todo = newTestTodo();
        todoDao.create(todo );

        ApiResponse res = client.request("GET",
                "/api/v1/todos/" + todo.getId());
        Todo retrieved = gson.fromJson(res.getBody(), Todo.class);

        assertEquals(todo, retrieved);
    }

    @Test
    public void missingTodosReturnNotFoundStatus() throws Exception {
        ApiResponse res = todo.request("GET", "/api/v1/todos/42");

        assertEquals(404, res.getStatus());
    }

    private static Todo newTestTodo() {
        return new Todo("Test", true);
    }

}

