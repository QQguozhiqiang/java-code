package com.test;

import com.doman.Customer;
import com.util.JpaUtils;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;

/**
 * @ClassName JpaTest
 * @Description TODO
 * @Author gzq
 * @Date 2020/6/9 23:03
 * @Version 1.0
 */
public class JpaTest {
    @Test
    public void addCustomer(){
        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("myJpa");
        EntityManager entityManager =
                factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = new Customer();
        customer.setCustName("国信");
        customer.setCustIndustry("政务");
        entityManager.persist(customer);
        transaction.commit();
        entityManager.close();
        factory.close();
    }
    @Test
    public void saveCustomer(){
        EntityManager entityManager = JpaUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = new Customer();
        customer.setCustName("国信");
        customer.setCustIndustry("政务");
        entityManager.persist(customer);
        transaction.commit();
        entityManager.close();
    }
    @Test
    public void find(){
        EntityManager entityManager = JpaUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = entityManager.find(Customer.class, 1l);
        System.out.println(customer);
        transaction.commit();
        entityManager.close();
    }
    @Test
    public void testRemove(){
        EntityManager entityManager = JpaUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(entityManager.find(Customer.class, 1l));
        transaction.commit();
        entityManager.close();

    }
    @Test
    public void testUpdate(){
        EntityManager entityManager = JpaUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = entityManager.find(Customer.class, 2l);
        customer.setCustAddress("北京市");
        entityManager.merge(customer);
        transaction.commit();
        entityManager.close();
    }

    @Test
    public void findAll(){
        EntityManager entityManager = JpaUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Query query = entityManager.createQuery("from com.doman.Customer");
        List<Customer> resultList = query.getResultList();
        for (Customer customer:
             resultList) {
            System.out.println(customer);
        }
        transaction.commit();
        entityManager.close();
    }
    @Test
    public void testOrder(){
        EntityManager entityManager = JpaUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Query query = entityManager.createQuery("from com.doman.Customer order by custId desc");
        List<Customer> resultList = query.getResultList();
        for (Customer customer:
                resultList) {
            System.out.println(customer);
        }
        transaction.commit();
        entityManager.close();
    }
    @Test
    public void testPages(){
        EntityManager entityManager = JpaUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Query query = entityManager.createQuery("from com.doman.Customer");
        query.setFirstResult(0);
        query.setMaxResults(1);
        List<Customer> resultList = query.getResultList();
        for (Customer customer:
                resultList) {
            System.out.println(customer);
        }
        transaction.commit();
        entityManager.close();
    }
    @Test
    public void testGroup(){
        EntityManager entityManager = JpaUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Query query = entityManager.createQuery("select count(custId) from com.doman.Customer");
        List<Long> resultList = query.getResultList();
        for (Long customer:
                resultList) {
            System.out.println(customer);
        }
        transaction.commit();
        entityManager.close();
    }
}
