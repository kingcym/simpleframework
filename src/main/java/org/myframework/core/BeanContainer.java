package org.myframework.core;

import org.myframework.core.annotation.MyComponent;
import org.myframework.core.annotation.MyController;
import org.myframework.core.annotation.MyRepository;
import org.myframework.core.annotation.MyService;
import org.myframework.core.util.Classutil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BeanContainer {
    private static final Logger log = LoggerFactory.getLogger(BeanContainer.class);

    //保存所有bean对象
    private final static Map<Class<?>, Object> beanMap = new ConcurrentHashMap<>();
    private static List<Class<? extends Annotation>> BEAN_ANNOTATION
            = Arrays.asList(MyComponent.class, MyController.class, MyRepository.class, MyService.class);


    private BeanContainer() {
    }

    public static BeanContainer getInstance() {
        return ContainerHolder.HOLDER.instance;
    }



    private enum ContainerHolder {
        HOLDER;
        private BeanContainer instance;

        ContainerHolder() { //枚举被加载，就实例化
            instance = new BeanContainer();
        }
    }

    public static void main(String[] args) {
        loadBeans("com.cym");
        System.out.println(beanMap);
    }

    /**
     * 扫描加载所有符合的bean
     *
     * @param packageName 包路径
     */
    public static synchronized void loadBeans(String packageName) {
        //获取包下所有class对象
        Set<Class<?>> classSet = Classutil.extractPacketClass(packageName);
        if (classSet == null || classSet.isEmpty()) {
            log.warn("获取{}包下所有class对象为null", packageName);
        } else {
            for (Class<?> clazz : classSet) {
                for (Class<? extends Annotation> annotation : BEAN_ANNOTATION) {
                    //过滤符合条件的class
                    if (clazz.isAnnotationPresent(annotation)) {
                        //放入beanMap
                        beanMap.put(clazz, Classutil.newInstance(clazz, true));
                    }
                }
            }
            log.info("---扫描加载所有符合的bean完成---");
        }
    }

    /**
     * 通过接口或者父类获取实现类或者子类的class集合，不包括本身
     *
     * @param interfaceOrClass 接口或者父类Class
     * @return
     */
    public Set<Class<?>> getClassesBySuper(Class<?> interfaceOrClass) {
        Set<Class<?>> set = new HashSet<>();
        //获取所有class对象
        Set<Class<?>> keyset = getClasses();
        if (keyset == null || keyset.size() == 0) {
            return null;
        }
        for (Class<?> aClass : keyset) {
            //判断aClass 是否是interfaceOrClass的子类
            if (interfaceOrClass.isAssignableFrom(aClass) && interfaceOrClass != aClass) {
                set.add(aClass);
            }
        }
        return set.size() > 0 ? set : null;
    }

    /**
     * 通过接口或者父类获取实现类或者子类的class集合，不包括本身
     *
     * @param clazz 接口或者父类Class;自身class
     * @return
     */
    public Object getBeanOrInterfaceImplBean(Class<?> clazz,String name) throws Exception {
        Object bean = getBean(clazz);
        if (bean == null) { //可能是父类
            Set<Class<?>> beans = getClassesBySuper(clazz);
            if (beans != null && !beans.isEmpty()){
                for (Class<?> aClass : beans) {
                    //获取简单类名
                    String simpleName = aClass.getSimpleName();
                    if (simpleName.equalsIgnoreCase(name)) {
                        return aClass;
                    }
                }
            }
            throw new Exception("没有对应实例bean,class: "+ clazz + ",beanName: " + name);
        } else {
            return bean;
        }
    }

    /**
     * 获取所有class对象
     *
     * @return
     */
    public Set<Class<?>> getClasses() {
        return beanMap.keySet();
    }

    /**
     * 获取所有Bean
     *
     * @return
     */
    public Set<Object> getBeans() {
        return (Set<Object>) beanMap.values();
    }

    public Object getBean(Class<?> clazz) {
        return beanMap.get(clazz);
    }


}
