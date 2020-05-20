package com.cym.controller;

import com.cym.service.HelloService;
import org.myframework.core.annotation.MyController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MyController
public class HelloController {
    private HelloService helloService;
    public HelloController() {}

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    public String helloDemo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String helloName = helloService.getHelloName();
        return helloName;
    }
}
