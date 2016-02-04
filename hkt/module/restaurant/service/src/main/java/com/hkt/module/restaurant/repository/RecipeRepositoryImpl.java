package com.hkt.module.restaurant.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.restaurant.entity.Recipe;

class RecipeRepositoryImpl extends JdbcDaoSupport implements RecipeRepositoryCustom {
  @PersistenceContext
  private EntityManager em;

  @Autowired
  public RecipeRepositoryImpl(DataSource dataSource) {
    setDataSource(dataSource);
  }

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public FilterResult<Recipe> search(FilterQuery query) {
    FilterResult<Recipe> result = new FilterResult<Recipe>(query) ;
    
    CriteriaQuery<Recipe> criteriaQuery = query.createCriteriaQuery(em, Recipe.class);
    TypedQuery<Recipe> tquery = em.createQuery(criteriaQuery) ;
    
    tquery.setMaxResults(query.getMaxReturn()) ;
    result.setData(tquery.getResultList()) ;
    return result ;
  }

}
