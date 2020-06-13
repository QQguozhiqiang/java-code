package com.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @ClassName JpaUtils
 * @Description TODO
 * @Author gzq
 * @Date 2020/6/9 23:41
 * @Version 1.0
 */
public class JpaUtils {
    private static EntityManagerFactory factory;
    static {
        factory = Persistence.createEntityManagerFactory("myJpa");
    }
    public static EntityManager getEntityManager(){
        return factory.createEntityManager();
    }
}
