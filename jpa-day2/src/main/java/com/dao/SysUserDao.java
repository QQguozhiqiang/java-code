package com.dao;

import com.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SysUserDao extends JpaRepository<SysUser,Long>, JpaSpecificationExecutor<SysUser> {
}
