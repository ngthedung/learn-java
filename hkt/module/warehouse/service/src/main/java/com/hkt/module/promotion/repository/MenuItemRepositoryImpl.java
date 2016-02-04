package com.hkt.module.promotion.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.promotion.entity.MenuItem;


public class MenuItemRepositoryImpl extends JdbcDaoSupport implements MenuItemRepositoryCustom {

  @PersistenceContext
  private EntityManager em;
  
  @Autowired
  public MenuItemRepositoryImpl(DataSource dataSource) {
    setDataSource(dataSource);
  }
  
  public void setEntityManager(EntityManager em) {
    this.em = em;
  }
  
  @Override
  public FilterResult<MenuItem> search(FilterQuery query) {
    FilterResult<MenuItem> result = new FilterResult<MenuItem>(query) ;
    CriteriaQuery<MenuItem> criteriaQuery = query.createCriteriaQuery(em, MenuItem.class);
    TypedQuery<MenuItem> tquery = em.createQuery(criteriaQuery) ;
    tquery.setMaxResults(query.getMaxReturn()) ;
    result.setData(tquery.getResultList()) ;
    return result ;
  }
}
