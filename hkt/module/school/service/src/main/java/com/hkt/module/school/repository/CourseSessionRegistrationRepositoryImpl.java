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
import com.hkt.module.school.entity.CourseSessionRegistration;

class CourseSessionRegistrationRepositoryImpl extends JdbcDaoSupport implements
    CourseSessionRegistrationRepositoryCustom {
  @PersistenceContext
  EntityManager em;
  
  @Autowired
  public CourseSessionRegistrationRepositoryImpl(DataSource dataSource) {
    setDataSource(dataSource);
  }
  
  public void setEntityManager(EntityManager em) {
    this.em = em;
  }
  
  @Override
  public FilterResult<CourseSessionRegistration> search(FilterQuery query) {
    FilterQuery.Expression typeExp = query.getField("status");
    if (typeExp != null) {
      typeExp.setValue(CourseSessionRegistration.Status.valueOf((String) typeExp.getValue()));
    }
    
    FilterResult<CourseSessionRegistration> result = new FilterResult<CourseSessionRegistration>(query);
    CriteriaQuery<CourseSessionRegistration> criteriaQuery = query.createCriteriaQuery(em,
        CourseSessionRegistration.class);
    TypedQuery<CourseSessionRegistration> tquery = em.createQuery(criteriaQuery);
    tquery.setMaxResults(query.getMaxReturn());
    result.setData(tquery.getResultList());
    return result;
  }
  
}
