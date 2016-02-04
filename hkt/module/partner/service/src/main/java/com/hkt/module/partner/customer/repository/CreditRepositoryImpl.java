package com.hkt.module.partner.customer.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.partner.customer.entity.Credit;

class CreditRepositoryImpl extends JdbcDaoSupport implements CreditRepositoryCustom {
  
  @PersistenceContext
  private EntityManager em;

  @Autowired
  public CreditRepositoryImpl(DataSource dataSource) {
    setDataSource(dataSource);
  }

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  @Override
  public FilterResult<Credit> search(FilterQuery query) {
    FilterResult<Credit> result = new FilterResult<Credit>(query) ;
    CriteriaQuery<Credit> criteriaQuery = query.createCriteriaQuery(em, Credit.class);
    TypedQuery<Credit> tquery = em.createQuery(criteriaQuery) ;
    tquery.setMaxResults(query.getMaxReturn()) ;
    result.setData(tquery.getResultList()) ;
    return result ;
  }
}