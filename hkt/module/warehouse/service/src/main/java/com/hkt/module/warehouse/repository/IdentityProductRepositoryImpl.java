package com.hkt.module.warehouse.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.ReportTableProcessor;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.core.entity.SQLSelectQuery.Field;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.util.BeanInspector;

public class IdentityProductRepositoryImpl extends JdbcDaoSupport implements
		IdentityProductRepositoryCustom {
	static String IDENTITY_PRODUCT_SELECT = "SELECT identity FROM IdentityProduct identity ";

	@PersistenceContext
	EntityManager em;

	@Autowired
	public IdentityProductRepositoryImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	public FilterResult<IdentityProduct> query(FilterQuery fquery) {
		BeanInspector<IdentityProduct> typeInspector = BeanInspector
				.get(IdentityProduct.class);
		StringBuilder b = new StringBuilder(IDENTITY_PRODUCT_SELECT);
		b.append(fquery.createQueryWhere(typeInspector));
		Query query = em.createQuery(b.toString());
		FilterResult<IdentityProduct> result = new FilterResult<IdentityProduct>(
				fquery);
		result.setData(query.getResultList());
		return result;
	}

	public ReportTable report(FilterQuery fquery,
			ReportTableProcessor<IdentityProduct> processor) {
		BeanInspector<IdentityProduct> typeInspector = BeanInspector
				.get(IdentityProduct.class);
		StringBuilder b = new StringBuilder(IDENTITY_PRODUCT_SELECT);
		b.append(fquery.createQueryWhere(typeInspector));
		int offset = 0;
		List<IdentityProduct> identityProducts;
		ReportTable reportTable = new ReportTable();
		processor.onStart(reportTable);
		// em.getTransaction().begin();
		do {
			Query query = em.createQuery(b.toString());
			// query.setHint(hintName, value)
			query.setFirstResult(offset);
			query.setMaxResults(fquery.getMaxReturn());
			identityProducts = query.getResultList();
			for (int i = 0; i < identityProducts.size(); i++) {
				IdentityProduct identityProduct = identityProducts.get(i);
				processor.process(reportTable, identityProduct);
			}
			offset += identityProducts.size();
		} while (identityProducts.size() == fquery.getMaxReturn());
		processor.onFinish(reportTable);
		return reportTable;
	}

	public int count(Class<?> type) {
		JdbcTemplate tmpl = getJdbcTemplate();
		int count = tmpl.queryForObject(
				"SELECT count(*) FROM " + type.getSimpleName(), Integer.class);
		return count;
	}

  @Override
  public ReportTable[] reportQuery(SQLSelectQuery[] query) {
    ReportTable[] reportTable = new ReportTable[query.length];
    for (int i = 0; i < query.length; i++) {
      reportTable[i] = new ReportTable();
      reportTable[i].setQuery(query[i]);
      JdbcTemplate tmpl = getJdbcTemplate();
      MapMapper mapper = new MapMapper(query[i]);
      String sqlQuery = query[i].createSQLQuery();
      System.out.println("Report Query: \n" + sqlQuery);
      List<Map<String, Object>> records = tmpl.query(sqlQuery, mapper);
      reportTable[i].setRecords(records);
    }
    return reportTable;
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
