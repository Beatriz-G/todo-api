package com.teamtreehouse.techdegrees;

import com.google.gson.Gson;
import com.teamtreehouse.techdegrees.dao.Sql2oTodoDao;
import com.teamtreehouse.techdegrees.dao.TodoDao;
import com.teamtreehouse.techdegrees.exc.ApiError;
import com.teamtreehouse.techdegrees.model.Todo;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Api {

    public static void main(String[] args) {
        String datasource = "jdbc:h2:~/reviews.db";
        if (args.length > 0) {
            if (args.length !=2) {
                System.out.println("java Api <port> <datasource>");
                System.exit(0);
            }

            port(Integer.parseInt(args[0]));
            datasource = args[1];
        }


        Sql2o sql2o = new Sql2o(
                String.format("%;INIT=RUNSCRIPT from 'classpath:db/init.sql'",
                        datasource, "", ""));
        TodoDao todoDao = new Sql2oTodoDao(sql2o);
        Gson gson = new Gson();


        post("/api/v1/todos", "application/json", (req, res) -> {
            Todo todo = gson.fromJson(req.body(), Todo.class);
            todoDao.create(todo);
            res.status(201);
            //res.type("application/json");
            return todo;
        }, gson::toJson);


        get("/api/v1/todos", "application/json",
                (req, res) -> todoDao.findAll(), gson::toJson);


        get("/api/v1/todos:id", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Todo todo = todoDao.findById(id);
            if (todo == null) {
                throw new ApiError(404, "Could not find course with id" + id);
            }
            return todo;
        }, gson::toJson);


        after((req, res) -> {
            res.type("application/json");
        });


        exception(ApiError.class, (exc, req, res) -> {
            ApiError err = (ApiError) exc;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatus());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatus());
            res.body(gson.toJson(jsonMap));
        }) ;


    }




}
