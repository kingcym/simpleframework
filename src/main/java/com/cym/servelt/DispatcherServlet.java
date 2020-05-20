package com.cym.servelt;

import com.cym.controller.HelloController;
import com.cym.service.HelloService;
import com.cym.service.impl.HelloServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")  //接收所有请求
public class DispatcherServlet extends HttpServlet {
    private Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    /**
     * 重写service方法，用于url转发到不同的处理类
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("请求路径：{}",req.getServletPath());
        log.info("请求方法类型：{}",req.getMethod());
        if (req.getServletPath().equals("/hello")) {
            HelloController helloController = new HelloController(new HelloServiceImpl());
            String helloDemo = helloController.helloDemo(req, resp);
            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().print(helloDemo);
        }
    }

}
