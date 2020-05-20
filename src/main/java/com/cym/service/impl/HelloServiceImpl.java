package com.cym.service.impl;

import com.cym.service.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public String getHelloName() {
        return "hello word";
    }
}
