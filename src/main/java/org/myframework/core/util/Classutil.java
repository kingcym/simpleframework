package org.myframework.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class Classutil {
    public static final String FILE = "file";
    private static final Logger log = LoggerFactory.getLogger(Classutil.class);


    public static void main(String[] args) {
        Set<Class<?>> classSet = extractPacketClass("org.myframework.core");
        System.out.println(classSet);
    }

    /**
     * 获取包下类的集合
     *
     * @param packetName 包的相对路径
     * @return
     */
    public static Set<Class<?>> extractPacketClass(final String packetName) {
        //  1.获取类加载器
        ClassLoader classLoader = getClassLoader();
        //2.通过类加载器获取到加载资源
        URL url = classLoader.getResource(packetName.replace(".", "/"));
        if (url == null) {
            log.warn("****{}包路径下resource为null****", packetName);
            return null;
        }
        // 3.依据不同的资源类型，采用不同方式获取资源的集合
        Set<Class<?>> classSet = null;
        //过滤出文件类型的资源
        if (url.getProtocol().equalsIgnoreCase(FILE)) {
            classSet = new HashSet<>();
            File file = new File(url.getPath());
            extractClassFile(classSet, file, packetName);
        }


        return classSet;
    }


    /**
     * 递归获取packet下所有class文件（包含子目录）
     *
     * @param classSet 装载目标类的集合
     * @param file     文件/目录
     * @param packet   包名
     */
    private static void extractClassFile(Set<Class<?>> classSet, File file, String packet) {
        //非文件夹
        if (!file.isDirectory()) return;
        //如果是文件夹，则调用listFiles获取所有文件及文件夹
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.isDirectory()) {
                    return true;
                } else {
                    //获取绝对路径
                    String absolutePath = pathname.getAbsolutePath();
                    //过滤出.class结尾的文件
                    if (absolutePath.endsWith(".class")) {
                        //若是class文件,则加载到set
                        try {
                            addToClassSet(absolutePath);
                        } catch (Exception e) {
                            log.error("加载class异常：", e);
                        }
                    }
                }
                return false;
            }

            //根据class文件的绝对值路径，生成class对象，放入classSet
            private void addToClassSet(String absolutePath) throws ClassNotFoundException {
                //从绝对路径截取包含packet的类名路径
                //File.separator表示不同操作系统，对应的路径分割符（/或\）
                absolutePath = absolutePath.replace(File.separator, ".");
                String className = absolutePath.substring(absolutePath.indexOf(packet));
                className = className.substring(0, className.lastIndexOf("."));
                //通过反射获取class对象
                Class<?> aClass = Class.forName(className);
                classSet.add(aClass);
            }
        });
        if (files != null) {
            for (File f : files) {
                extractClassFile(classSet, f, packet);
            }
        }
    }

    /**
     * 获取 ClassLoader
     *
     * @return
     */
    private static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 根据class生成对象
     *
     * @param clazz
     * @param accessible
     * @param <T>
     * @return
     */
    public static <T> T newInstance(Class<?> clazz, boolean accessible) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(accessible);
            return (T) constructor.newInstance();
        } catch (Exception e) {
            log.error("newInstance error", e);
            throw new RuntimeException(e);
        }

    }


    /**
     * 设置类的成员变量属性
     * @param field        成员变量
     * @param targetBaen   类实例
     * @param value        成员变量值
     * @param accessible   是否允许设置私有变量
     */
    public static void setFile(Field field, Object targetBaen, Object value, boolean accessible) {
        try {
            field.setAccessible(accessible);
            field.set(targetBaen,value);
        } catch (Exception e) {
            log.error("setFile error", e);
            throw new RuntimeException(e);
        }
    }
}
