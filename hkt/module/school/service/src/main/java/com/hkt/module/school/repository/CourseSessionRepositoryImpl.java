package com.hkt.module.school.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.school.entity.CourseSession;

class CourseSessionRepositoryImpl extends JdbcDaoSupport implements CourseSessionRepositoryCustom {
  @PersistenceContext
  EntityManager em;
  
  @Autowired
  public CourseSessionRepositoryImpl(DataSource dataSource) {
    setDataSource(dataSource);
  }
  
  public void setEntityManager(EntityManager em) {
    this.em = em;
  }
  
  @Override
  public FilterResult<CourseSession> search(FilterQuery query) {
    FilterResult<CourseSession> result = new FilterResult<CourseSession>(query);
    CriteriaQuery<CourseSession> criteriaQuery = query.createCriteriaQuery(em, CourseSession.class);
    TypedQuery<CourseSession> tquery = em.createQuery(criteriaQuery);
    tquery.setMaxResults(query.getMaxReturn());
    result.setData(tquery.getResultList());
    return result;
  }
  
}
