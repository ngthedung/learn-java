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
import com.hkt.module.partner.customer.entity.Point;

class PointRepositoryImpl extends JdbcDaoSupport implements PointRepositoryCustom {
  
  @PersistenceContext
  private EntityManager em;

  @Autowired
  public PointRepositoryImpl(DataSource dataSource) {
    setDataSource(dataSource);
  }

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  @Override
  public FilterResult<Point> search(FilterQuery query) {
    FilterResult<Point> result = new FilterResult<Point>(query) ;
    CriteriaQuery<Point> criteriaQuery = query.createCriteriaQuery(em, Point.class);
    TypedQuery<Point> tquery = em.createQuery(criteriaQuery) ;
    tquery.setMaxResults(query.getMaxReturn()) ;
    result.setData(tquery.getResultList()) ;
    return result ;
  }
}