package com.cym.controller;

import com.cym.service.HelloService;
import org.myframework.core.annotation.MyController;
import org.myframework.inject.annotation.MyAutowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.*;

@MyController
public class HelloController {

    @MyAutowired
    private HelloService helloService;

    public HelloController() {}

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    public String helloDemo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String helloName = helloService.getHelloName();
        return helloName;
    }

    public HelloService getHelloService() {
        return helloService;
    }

    public static void main(String[] args) throws Exception {

    }

}
