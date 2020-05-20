package com.cym.pattern.singleton;

/**
 * 枚举模式的单例模式，可以防止反射和序列化攻击
 */
public class EnumStarvingSingleton {

    private EnumStarvingSingleton() {
    }

    public static EnumStarvingSingleton getInstance() {
        return ContainerHolder.HOLDER.instance;
    }

    private enum ContainerHolder {
        HOLDER;
        private EnumStarvingSingleton instance;

        ContainerHolder() { //枚举被加载，就实例化
            instance = new EnumStarvingSingleton();
        }
    }
}
