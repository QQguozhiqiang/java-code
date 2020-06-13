package com.test;

import com.dao.CustomerDao;
import com.dao.LinkmanDao;
import com.dao.SysRoleDao;
import com.dao.SysUserDao;
import com.domain.Customer;
import com.domain.Linkman;
import com.domain.SysRole;
import com.domain.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @ClassName CustomerDaoTest
 * @Description TODO
 * @Author gzq
 * @Date 2020/6/10 21:51
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CustomerDaoTest {
    @Autowired
    CustomerDao customerDao;
    @Autowired
    LinkmanDao linkmanDao;
    @Autowired
    SysUserDao sysUserDao;
    @Autowired
    SysRoleDao sysRoleDao;
    @Test
    public void testFindOne(){
        Customer customer = customerDao.findOne(3l);
        System.out.println(customer);
    }

    @Test
    public void testSave(){
        Customer customer = new Customer();
        customer.setCustId(4l);
        customer.setCustAddress("天津1");
        customer.setCustName("文思");
        customerDao.save(customer);
    }
    @Test
    public void testDelete(){
        customerDao.delete(4l);
    }
    @Test
    public void testCount(){
        //customerDao.count();
        System.out.println(customerDao.count());
    }
    @Test
    public void testExists(){
        System.out.println(customerDao.exists(3l));
    }
    @Test
    @Transactional
    public void testGetOne(){
        System.out.println(customerDao.getOne(3l));
    }
    @Test
    public void testJPQL(){
        System.out.println(customerDao.findJpql("文思%"));
    }
    @Test
    public void testJPQL2(){
        System.out.println(customerDao.findCustNameAndId("文思",5l));
    }
    @Test
    @Transactional
    @Rollback(false)
    public void testUpdate(){
        System.out.println(customerDao.updateCustomer("我去",5l));
    }
    @Test
    public void testSQL(){
        System.out.println(customerDao.findSql());
    }
    @Test
    public void testCustName(){
        System.out.println(customerDao.findByCustNameLike("国信"));
    }
    @Test
    public void testfind(){
        Specification<Customer> specification = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.<String>get("custName"),"我去%");
            }
        };
        Customer customer = customerDao.findOne(specification);
        System.out.println(customer);
    }
    @Test
    public void testPageing(){
        Specification<Customer> specification = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.<String>get("custName"),"国信%");
            }
        };
        Pageable pageable = new PageRequest(1, 1);
        Page<Customer> page = customerDao.findAll(specification, pageable);
        System.out.println(page.getTotalPages());
        System.out.println(page.getSize());
        for (Customer customer:
             page.getContent()) {
            System.out.println(customer.toString());
        }
    }
    @Test
    @Transactional
    @Rollback(false)
    public void  testAdd(){
        Customer c = new Customer();
        c.setCustName("TBD云集中心");
        c.setCustLevel("VIP客户");
        c.setCustSource("网络");
        c.setCustIndustry("商业办公");
        c.setCustAddress("昌平区北七家镇");
        c.setCustPhone("010-84389340");

        Linkman l = new Linkman();
        l.setLkmName("TBD联系人");
        l.setLkmGender("male");
        l.setLkmMobile("13811111111");
        l.setLkmPhone("010-34785348");
        l.setLkmEmail("98354834@qq.com");
        l.setLkmPosition("老师");
        l.setLkmMemo("还行吧");

        c.getLinkmanSet().add(l);
        l.setCustomer(c);
        customerDao.save(c);
        linkmanDao.save(l);
    }
    @Test
    @Transactional  //开启事务
    @Rollback(false)//设置为不回滚
    public void test1(){
        //创建对象
        SysUser u1 = new SysUser();
        u1.setUserName("用户1");
        SysRole r1 = new SysRole();
        r1.setRoleName("角色1");
        //建立关联关系
        u1.getRoles().add(r1);
        r1.getUsers().add(u1);
        //保存
        sysRoleDao.save(r1);
        sysUserDao.save(u1);
    }
}
