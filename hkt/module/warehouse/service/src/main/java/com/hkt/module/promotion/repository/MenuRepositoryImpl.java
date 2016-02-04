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
import com.hkt.module.promotion.entity.Menu;


public class MenuRepositoryImpl extends JdbcDaoSupport implements MenuRepositoryCustom {

  @PersistenceContext
  private EntityManager em;
  
  @Autowired
  public MenuRepositoryImpl(DataSource dataSource) {
    setDataSource(dataSource);
  }
  
  public void setEntityManager(EntityManager em) {
    this.em = em;
  }
  
  @Override
  public FilterResult<Menu> search(FilterQuery query) {
    FilterResult<Menu> result = new FilterResult<Menu>(query) ;
    CriteriaQuery<Menu> criteriaQuery = query.createCriteriaQuery(em, Menu.class);
    TypedQuery<Menu> tquery = em.createQuery(criteriaQuery) ;
    tquery.setMaxResults(query.getMaxReturn()) ;
    result.setData(tquery.getResultList()) ;
    return result ;
  }
}
