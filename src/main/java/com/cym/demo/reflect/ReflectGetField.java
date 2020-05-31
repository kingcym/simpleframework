package com.cym.demo.reflect;

import com.cym.service.impl.HelloServiceImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * 获取操作成员变量
 */
public class ReflectGetField {
    public String name;
    private String age;
    public ReflectGetField() {
        System.out.println("public无参构造器：");
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        //获取class对象
        Class<?> aClass = Class.forName("com.cym.demo.reflect.ReflectGetField");
        //1.获取所有公共的字段
        System.out.println("********************获取所有公共的字段*******************************");
        Field[] fields = aClass.getFields();
        for (Field field : fields) {
            System.out.println(field);
        }
        //2.获取所有字段
        System.out.println("********************获取所有的字段*******************************");
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.println(field);
        }
        //2.获取所有指定字段
        System.out.println("********************获取所有指定字段*******************************");
        Field name = aClass.getDeclaredField("name");
        System.out.println(name);

        //3.给属性赋值
        ReflectGetField reflectGetField = (ReflectGetField)aClass.getConstructor().newInstance();
        name.set(reflectGetField,"wwwwwww");
        //4.验证
        System.out.println(reflectGetField.name);

        Class<?> aClass1 = Class.forName("com.cym.controller.HelloController");
        Field field = aClass1.getDeclaredField("helloService");
        field.setAccessible(true);
        field.set(aClass1.getConstructor().newInstance(),new HelloServiceImpl());



    }
}
