package com.hkt.module.account.repository;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.hkt.module.account.entity.AccountGroup;

class AccountGroupRepositoryImpl extends JdbcDaoSupport implements AccountGroupRepositoryCustom {
  @Autowired
  public AccountGroupRepositoryImpl(DataSource dataSource) {
    setDataSource(dataSource);
  }
  
  @Override
  public int cascadeDelete(AccountGroup group) {
    String query = "delete from AccountGroup WHERE path LIKE ?" ;
    String path = group.getPath() + "%";
    int count = getJdbcTemplate().update(query, path);
    return count ;
  }
}