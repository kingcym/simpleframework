package com.cym.demo.reflect;

/**
 * 获取class对象
 */
public class ReflectGetClass {
    public static void main(String[] args) throws ClassNotFoundException {
        //第一种方式获取class对象
        ReflectGetClass reflectTarget = new ReflectGetClass();
        Class<? extends ReflectGetClass> aClass1 = reflectTarget.getClass();
        System.out.println(aClass1.getName());

        //第二种方式获取class对象
        Class<ReflectGetClass> aClass2 = ReflectGetClass.class;
        System.out.println(aClass2.getName());

        //第三种方式获取class对象
        Class<?> aClass3 = Class.forName("com.cym.demo.reflect.ReflectGetClass");
        System.out.println(aClass3.getName());
        System.out.println(aClass3.getSimpleName());
        System.out.println(aClass3.getTypeName());

        System.out.println(aClass1==aClass2);
        System.out.println(aClass1==aClass3);
        System.out.println(aClass2==aClass3);
    }
}
