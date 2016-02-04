package com.hkt.module.product.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.product.entity.Product;

class ProductRepositoryImpl extends JdbcDaoSupport implements ProductRepositoryCustom {
  @PersistenceContext
  private EntityManager em;

  @Autowired
  public ProductRepositoryImpl(DataSource dataSource) {
    setDataSource(dataSource);
  }

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public FilterResult<Product> search(FilterQuery query) {
    FilterResult<Product> result = new FilterResult<Product>(query) ;
    
    CriteriaQuery<Product> criteriaQuery = query.createCriteriaQuery(em, Product.class);
    TypedQuery<Product> tquery = em.createQuery(criteriaQuery) ;
    
    tquery.setMaxResults(query.getMaxReturn()) ;
    result.setData(tquery.getResultList()) ;
    return result ;
  }

}
