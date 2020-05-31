package org.myframework.inject.annotation;


import com.cym.controller.HelloController;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.myframework.core.BeanContainer;

import static org.junit.jupiter.api.Assertions.*;

public class DependenyInjectorTest {

    public static void main(String[] args) {

    }

    @Test
    public void doIoc() throws Exception {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.cym");
        Object bean = beanContainer.getBean(HelloController.class);
        System.out.println(bean instanceof HelloController);
        assertEquals(true,bean instanceof HelloController);
        HelloController helloController = (HelloController)bean;
        assertEquals(null,helloController.getHelloService());
        //注入属性值
        new DependenyInjector().doIoc();
        assertNotEquals(null,helloController.getHelloService());

    }

}
