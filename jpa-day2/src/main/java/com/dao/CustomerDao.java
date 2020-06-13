package com.dao;

import com.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CustomerDao extends JpaRepository<Customer,Long>, JpaSpecificationExecutor<Customer> {

    /**
     * 根据客户名称查询
     */
    @Query(value = "from Customer where custName like ?")
    public Customer findJpql(String custName);
    @Query("from Customer where custName = ? and custId = ?")
    public Customer findCustNameAndId(String custName,Long custId);
    @Query("update Customer set custName = ?1 where custId = ?2")
    @Modifying
    public int updateCustomer(String custName,Long id);
    @Query(value = "select * from cst_customer",nativeQuery = true)
    public List<Object[]> findSql();

    public List<Customer> findByCustNameLike(String custName);
}
