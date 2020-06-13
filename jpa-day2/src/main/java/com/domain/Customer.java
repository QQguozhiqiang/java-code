package com.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName Customer
 * @Description TODO
 * @Author gzq
 * @Date 2020/6/10 21:40
 * @Version 1.0
 */
@Entity
@Table(name = "cst_customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Long custId;//客户主键
    @Column(name = "cust_name")
    private String custName;//客户名称
    @Column(name = "cust_source")
    private String custSource;//客户来源
    @Column(name = "cust_level")
    private String custLevel;//客户等级
    @Column(name = "cust_industry")
    private String custIndustry;//客户所属行业
    @Column(name = "cust_phone")
    private String custPhone;//客户联系方式
    @Column(name = "cust_address")
    private String custAddress;//客户地址
    /*@OneToMany(targetEntity = Linkman.class)
    @JoinColumn(name = "lkm_cust_id",referencedColumnName = "cust_id")*/
    @OneToMany(mappedBy = "customer")
    private Set<Linkman> linkmanSet = new HashSet<Linkman>();

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustSource() {
        return custSource;
    }

    public void setCustSource(String custSource) {
        this.custSource = custSource;
    }

    public String getCustLevel() {
        return custLevel;
    }

    public void setCustLevel(String custLevel) {
        this.custLevel = custLevel;
    }

    public String getCustIndustry() {
        return custIndustry;
    }

    public void setCustIndustry(String custIndustry) {
        this.custIndustry = custIndustry;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public Set<Linkman> getLinkmanSet() {
        return linkmanSet;
    }

    public void setLinkmanSet(Set<Linkman> linkmanSet) {
        this.linkmanSet = linkmanSet;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", custName='" + custName + '\'' +
                ", custSource='" + custSource + '\'' +
                ", custLevel='" + custLevel + '\'' +
                ", custIndustry='" + custIndustry + '\'' +
                ", custPhone='" + custPhone + '\'' +
                ", custAddress='" + custAddress + '\'' +
                ", linkmanSet=" + linkmanSet +
                '}';
    }
}
