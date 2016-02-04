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
import com.hkt.module.product.entity.ProductPrice;

class ProductPriceRepositoryImpl extends JdbcDaoSupport implements ProductPriceRepositoryCustom {
  @PersistenceContext
  private EntityManager em;

  @Autowired
  public ProductPriceRepositoryImpl(DataSource dataSource) {
    setDataSource(dataSource);
  }

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public FilterResult<ProductPrice> search(FilterQuery query) {
    FilterResult<ProductPrice> result = new FilterResult<ProductPrice>(query) ;
    
    CriteriaQuery<ProductPrice> criteriaQuery = query.createCriteriaQuery(em, ProductPrice.class);
    TypedQuery<ProductPrice> tquery = em.createQuery(criteriaQuery) ;
    
    tquery.setMaxResults(query.getMaxReturn()) ;
    result.setData(tquery.getResultList()) ;
    return result ;
  }

}
