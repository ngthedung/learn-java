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
import com.hkt.module.school.entity.Student;

class StudentRepositoryImpl extends JdbcDaoSupport implements StudentRepositoryCustom {
  @PersistenceContext
  EntityManager em;
  
  @Autowired
  public StudentRepositoryImpl(DataSource dataSource) {
    setDataSource(dataSource);
  }
  
  public void setEntityManager(EntityManager em) {
    this.em = em;
  }
  
  @Override
  public FilterResult<Student> search(FilterQuery query) {
    FilterResult<Student> result = new FilterResult<Student>(query);
    CriteriaQuery<Student> criteriaQuery = query.createCriteriaQuery(em, Student.class);
    TypedQuery<Student> tquery = em.createQuery(criteriaQuery);
    tquery.setMaxResults(query.getMaxReturn());
    result.setData(tquery.getResultList());
    return result;
  }
  
}
