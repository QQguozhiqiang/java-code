package com.dao;

import com.domain.Linkman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName LinkmanDao
 * @Description TODO
 * @Author gzq
 * @Date 2020/6/11 15:36
 * @Version 1.0
 */
public interface LinkmanDao extends JpaRepository<Linkman,Long>, JpaSpecificationExecutor<Linkman> {
}
