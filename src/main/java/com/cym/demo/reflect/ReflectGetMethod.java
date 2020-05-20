package com.cym.demo.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 获取操作成员方法
 */
public class ReflectGetMethod {

    public ReflectGetMethod() {
        System.out.println("public无参构造器：");
    }

    public String show() {
        System.out.println("调用shaow方法");
        return "123";
    }
    private String show1() {
        System.out.println("show1方法");
        return "123";
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        //获取class对象
        Class<?> aClass = Class.forName("com.cym.demo.reflect.ReflectGetMethod");
        //1.获取所有公共的方法（包含父类）
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            System.out.println(method);
        }
        //2.获取所有公共指定的方法（包含父类）
        Method method = aClass.getMethod("show");
        //3.获取实例
        ReflectGetMethod reflectGetMethod = (ReflectGetMethod)aClass.getConstructor().newInstance();
        //调用方法
        Object invoke = method.invoke(reflectGetMethod);
        System.out.println(invoke);
    }
}
