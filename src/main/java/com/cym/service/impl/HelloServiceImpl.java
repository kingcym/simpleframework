package com.cym.service.impl;

import com.cym.service.HelloService;
import org.myframework.core.annotation.MyService;

@MyService
public class HelloServiceImpl implements HelloService {
    @Override
    public String getHelloName() {
        return "hello word";
    }
}
