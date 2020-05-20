package com.cym.pattern.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 枚举模式的单例模式，可以防止反射和序列化攻击
 */
public class EnumStarvingSingletonTest {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        System.out.println(EnumStarvingSingleton.getInstance());
        Class<EnumStarvingSingleton> aClass = EnumStarvingSingleton.class;
        Constructor<EnumStarvingSingleton> constructor = aClass.getDeclaredConstructor();
        constructor.setAccessible(true);

        EnumStarvingSingleton enumStarvingSingleton = constructor.newInstance();
        System.out.println(enumStarvingSingleton);
        EnumStarvingSingleton enumStarvingSingleton2 = constructor.newInstance();
        System.out.println(enumStarvingSingleton2);
        System.out.println(enumStarvingSingleton.getInstance());
    }
}
