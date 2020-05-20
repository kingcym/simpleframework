package com.cym.demo.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 获取操作构造函数
 */
public class ReflectGetConstructor {


    public ReflectGetConstructor() {
        System.out.println("public无参构造器：");
    }

    public ReflectGetConstructor(char name) {
        System.out.println("public有参构造器：");
    }

    public ReflectGetConstructor(String str, Integer index) {
        System.out.println("public多个参数构造器：" + str + "--" + index);
    }

    ReflectGetConstructor(String str) {
        System.out.println("默认级别构造器：" + str);
    }


    protected ReflectGetConstructor(Boolean s) {
        System.out.println("protected有参构造器：" + s);
    }

    private ReflectGetConstructor(Integer s) {
        System.out.println("private有参构造器：" + s);
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //获取class对象
        Class<?> aClass = Class.forName("com.cym.demo.reflect.ReflectGetConstructor");
        //1.获取所有公共的构造器
        System.out.println("********************所有公共的构造器*******************************");
        Constructor<?>[] constructors = aClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println(constructor);
        }
        //2.获取所有的构造器
        System.err.println("********************获取所有的构造器*******************************");
        Constructor<?>[] constructors2 = aClass.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors2) {
            System.out.println(constructor);
        }
        //3.获取公共的指定构造器
        System.out.println("********************获取公共的指定构造器*******************************");
        Constructor<?> constructor1 = aClass.getConstructor(char.class);
        System.out.println(constructor1);
        System.out.println(aClass.getConstructor(null));
        //4.获取公共的指定构造器
        System.out.println("********************获取所有的指定构造器*******************************");
        Constructor<?> constructor2 = aClass.getDeclaredConstructor(Integer.class);
        System.out.println(constructor2);
        //调用私有构造方法，创建实例
        System.out.println("********************调用私有构造方法，创建实例*******************************");
        constructor2.setAccessible(true);
        ReflectGetConstructor o = (ReflectGetConstructor)constructor2.newInstance(1);
    }
}
