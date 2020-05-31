package org.myframework.inject.annotation;

import com.google.common.collect.Collections2;
import org.myframework.core.BeanContainer;
import org.myframework.core.util.Classutil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Set;

/**
 * 依赖注入
 */
public class DependenyInjector {
    private static final Logger log = LoggerFactory.getLogger(DependenyInjector.class);

    //bean容器
    private final BeanContainer beanContainer;

    public DependenyInjector() {
        this.beanContainer = BeanContainer.getInstance();
    }

    /**
     * 执行IOC
     */
    public void doIoc() throws Exception {
        Set<Class<?>> classes = beanContainer.getClasses();
        if (classes != null && !classes.isEmpty()) {
            //1.遍历bean容器所有class对象
            for (Class<?> clazz : classes) {
                //2.遍历class对象所有成员变量
                Field[] fields = clazz.getDeclaredFields();
                if (fields != null && fields.length > 0) {
                    //3.找出被@MyAutowired标记的成员变量
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(MyAutowired.class)) {
                            //4.获取成员变量类型
                            Class<?> fieldClass = field.getType();
                            //成员变量名
                            String name = field.getName();
                            //5.获取成员变量类型在容器中对应的实例
                            Object fileBean = beanContainer.getBeanOrInterfaceImplBean(fieldClass, name);
                            //6.通过反射将成员变量注入
                            Object targetBaen = beanContainer.getBean(clazz);
                            Classutil.setFile(field, targetBaen, fileBean, true);
                        }
                    }
                }
            }
            log.info("----IOC注入属性bean完成----");
        } else {
            log.warn("------容器中bean为null------");
        }
    }
}
