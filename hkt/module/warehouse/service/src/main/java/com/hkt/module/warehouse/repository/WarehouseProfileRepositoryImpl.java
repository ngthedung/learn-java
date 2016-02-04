package com.hkt.module.warehouse.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.core.entity.SQLSelectQuery.Field;

public class WarehouseProfileRepositoryImpl extends JdbcDaoSupport implements WarehouseProfileRepositoryCustom {
  @PersistenceContext
  private EntityManager em;
  
  @Autowired
  public WarehouseProfileRepositoryImpl(DataSource dataSource) {
    setDataSource(dataSource);
  }
  
  public void setEntityManager(EntityManager em) {
    this.em = em;
  }
  
  
  @Override
  public ReportTable[] reportQuery(SQLSelectQuery[] query) {
    ReportTable[] reportTable = new ReportTable[query.length] ;
    for(int i = 0; i < query.length; i++) {
      reportTable[i] = new ReportTable() ;
      reportTable[i].setQuery(query[i]) ;
      JdbcTemplate tmpl = getJdbcTemplate() ;
      MapMapper mapper = new MapMapper(query[i]) ;
      String sqlQuery = query[i].createSQLQuery() ;
      System.out.println("Report Query: \n" + sqlQuery);
      List<Map<String, Object>> records = tmpl.query(sqlQuery, mapper) ;
      reportTable[i].setRecords(records) ;
    }
    return reportTable ;
  }
  
  static public class MapMapper implements
  ParameterizedRowMapper<Map<String, Object>> {
private SQLSelectQuery query;

public MapMapper(SQLSelectQuery query) {
  this.query = query;
}

public Map<String, Object> mapRow(ResultSet rs, int rowNum)
    throws SQLException {
  try {
    List<Field> fields = query.getFields();
    Map<String, Object> map = new HashMap<String, Object>();
    for (int i = 0; i < fields.size(); i++) {
      String name = fields.get(i).getAlias();
      Object val = rs.getObject(i + 1);
      map.put(name, val);
    }
    return map;
  } catch (Throwable t) {
    throw new SQLException(t);
  }
}
}
  
}
