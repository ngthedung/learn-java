package com.hkt.module.hr.repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.hr.entity.Appointment;

class AppointmentRepositoryImpl extends JdbcDaoSupport implements AppointmentRepositoryCustom {
  private DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

  @PersistenceContext
  private EntityManager em;

  @Autowired
  public AppointmentRepositoryImpl(DataSource dataSource) {
    setDataSource(dataSource);
  }

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public FilterResult<Appointment> search(FilterQuery query) {
    FilterQuery.Expression typeExpStatus = query.getField("status");
    if (typeExpStatus != null) {
      typeExpStatus.setValue(Appointment.Status.valueOf((String) typeExpStatus.getValue()));
    }
    try {
      List<FilterQuery.Expression> date = query.getFields("dateStart");
      if (date != null) {
        for (FilterQuery.Expression e : date) {
          e.setValue(df.parse((String) e.getValue()));
        }
      }
    } catch (Exception e) {
    }
    FilterResult<Appointment> result = new FilterResult<Appointment>(query);
    CriteriaQuery<Appointment> criteriaQuery = query.createCriteriaQuery(em, Appointment.class);
    TypedQuery<Appointment> tquery = em.createQuery(criteriaQuery);
    result.setData(tquery.getResultList());

    return result;
  }

}
