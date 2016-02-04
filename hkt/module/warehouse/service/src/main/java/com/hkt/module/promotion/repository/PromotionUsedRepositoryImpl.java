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
import com.hkt.module.promotion.entity.PromotionUsed;

public class PromotionUsedRepositoryImpl extends JdbcDaoSupport implements PromotionUsedRepositoryCustom {
  @PersistenceContext
  private EntityManager em;
  
  @Autowired
  public PromotionUsedRepositoryImpl(DataSource dataSource) {
    setDataSource(dataSource);
  }
  
  public void setEntityManager(EntityManager em) {
    this.em = em;
  }
  
  @Override
  public FilterResult<PromotionUsed> search(FilterQuery query) {
    FilterResult<PromotionUsed> result = new FilterResult<PromotionUsed>(query);
    
    CriteriaQuery<PromotionUsed> criteriaQuery = query.createCriteriaQuery(em, PromotionUsed.class);
    
    TypedQuery<PromotionUsed> tquery = em.createQuery(criteriaQuery);
    tquery.setMaxResults(query.getMaxReturn());
    result.setData(tquery.getResultList());
    return result;
  }
}
