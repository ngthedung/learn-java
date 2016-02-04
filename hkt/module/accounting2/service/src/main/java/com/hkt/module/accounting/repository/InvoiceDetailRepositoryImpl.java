package com.hkt.module.accounting.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.ReportTableProcessor;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.core.entity.SQLSelectQuery.Field;
import com.hkt.util.BeanInspector;

public class InvoiceDetailRepositoryImpl extends JdbcDaoSupport implements
		InvoiceDetailRepositoryCustom {
	static String INVOICE_DETAIL_SELECT = "SELECT DISTINCT invoice FROM InvoiceDetail invoice "
			+ "  LEFT JOIN FETCH invoice.items item LEFT JOIN FETCH item.references reference"
			+ "  LEFT JOIN FETCH invoice.transactions transaction"
			+ "  LEFT JOIN FETCH invoice.attributes attribute"
			+ "  LEFT JOIN FETCH invoice.contributors contributor";
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	@PersistenceContext
	EntityManager em;

	@Autowired
	public InvoiceDetailRepositoryImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	public FilterResult<InvoiceDetail> query(FilterQuery fquery) {
		BeanInspector<InvoiceDetail> typeInspector = BeanInspector
				.get(InvoiceDetail.class);
		StringBuilder b = new StringBuilder(INVOICE_DETAIL_SELECT);
		b.append(fquery.createQueryWhere(typeInspector)).append(" ORDER BY invoice.id DESC");
		Query query = em.createQuery(b.toString());
		FilterResult<InvoiceDetail> result = new FilterResult<InvoiceDetail>(
				fquery);
		result.setData(query.getResultList());
		return result;
	}

	public ReportTable report(FilterQuery fquery,
			ReportTableProcessor<InvoiceDetail> processor) {
		BeanInspector<InvoiceDetail> typeInspector = BeanInspector
				.get(InvoiceDetail.class);
		StringBuilder b = new StringBuilder(INVOICE_DETAIL_SELECT);
		b.append(fquery.createQueryWhere(typeInspector));
		int offset = 0;
		List<InvoiceDetail> invoices;
		ReportTable reportTable = new ReportTable();
		processor.onStart(reportTable);
		// em.getTransaction().begin();
		do {
			Query query = em.createQuery(b.toString());
			// query.setHint(hintName, value)
			query.setFirstResult(offset);
			query.setMaxResults(fquery.getMaxReturn());
			invoices = query.getResultList();
			for (int i = 0; i < invoices.size(); i++) {
				InvoiceDetail invoice = invoices.get(i);
				processor.process(reportTable, invoice);
			}
			offset += invoices.size();
		} while (invoices.size() == fquery.getMaxReturn());
		processor.onFinish(reportTable);
		return reportTable;
	}

	public FilterResult<InvoiceItem> queryInvoiceItems(FilterQuery fquery) {
		String INVOICE_DETAIL_SELECT = "SELECT DISTINCT item FROM InvoiceItem item "
				+ "  LEFT JOIN FETCH item.references reference";
		BeanInspector<InvoiceItem> typeInspector = BeanInspector
				.get(InvoiceItem.class);
		StringBuilder b = new StringBuilder(INVOICE_DETAIL_SELECT);
		b.append(fquery.createQueryWhere(typeInspector));
		Query query = em.createQuery(b.toString());
		query.setMaxResults(fquery.getMaxReturn());
		FilterResult<InvoiceItem> result = new FilterResult<InvoiceItem>(fquery);
		result.setData(query.getResultList());
		return result;
	}

	public FilterResult<InvoiceDetail> search(FilterQuery query) {
	  try {
	    List<FilterQuery.Expression> date = query.getFields("startDate");
	    if (date != null){
	      for(FilterQuery.Expression e: date){
	        e.setValue(df.parse((String)e.getValue()));
	      }
	    }
    } catch (Exception e) {
    }
	  

		FilterQuery.Expression typeExpType = query.getField("activityType");
		if (typeExpType != null)
			typeExpType.setValue(Invoice.ActivityType
					.valueOf((String) typeExpType.getValue()));
		FilterQuery.Expression typeExpStatus = query.getField("status");
		if (typeExpStatus != null)
			typeExpStatus.setValue(Invoice.Status
					.valueOf((String) typeExpStatus.getValue()));
		FilterResult<InvoiceDetail> result = new FilterResult<InvoiceDetail>(
				query);
		CriteriaQuery<InvoiceDetail> criteriaQuery = query.createCriteriaQuery(
				em, InvoiceDetail.class);
		TypedQuery<InvoiceDetail> tquery = em.createQuery(criteriaQuery);
		result.setData(tquery.getResultList());

		return result;
	}

	public FilterResult<Invoice> searchInvoices(FilterQuery query) {
		long startTime = System.currentTimeMillis();
		JdbcTemplate tmpl = getJdbcTemplate();
		FilterResult<Invoice> result = new FilterResult<Invoice>(query);
		BeanInspector<InvoiceDetail> typeInspector = BeanInspector
				.get(InvoiceDetail.class);
		String nativeQuery = query.createNativeQuery(typeInspector);
		System.out.println("query = " + nativeQuery);
		List<Invoice> invoices = tmpl.query(nativeQuery, INVOICE_MAPPER);
		result.setData(invoices);
		return result;
	}

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

	public int count(Class<?> type) {
		JdbcTemplate tmpl = getJdbcTemplate();
		int count = tmpl.queryForObject(
				"SELECT count(*) FROM " + type.getSimpleName(), Integer.class);
		return count;
	}
	
	public void drop(Class<?> type){
    JdbcTemplate tmpl = getJdbcTemplate();
    tmpl.execute("DROP TABLE "+type.getSimpleName());
	}
	
	public void executeSQL(String sql){
    JdbcTemplate tmpl = getJdbcTemplate();
    tmpl.execute(sql);
	}
	
	public void drop(String tableName){
    JdbcTemplate tmpl = getJdbcTemplate();
    tmpl.execute("DROP TABLE "+tableName);
  }

	final static public ParameterizedRowMapper<Invoice> INVOICE_MAPPER = new ParameterizedRowMapper<Invoice>() {
		public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				Invoice invoice = new Invoice();
				invoice.setId(rs.getLong("id"));
				invoice.setInvoiceCode(rs.getString("invoiceCode"));
				try {
					invoice.setNote(rs.getString("note"));
				} catch (Exception e) {
					invoice.setNote("");
				}
				try {
          invoice.setInvoiceName(rs.getString("invoiceName"));
        } catch (Exception e) {
          invoice.setInvoiceName("");
        }
				try {
          invoice.setDescription(rs.getString("description"));
        } catch (Exception e) {
          invoice.setDescription("");
        }
				try {
          invoice.setCreatedBy(rs.getString("createdBy"));
        } catch (Exception e) {
          invoice.setCreatedBy("");
        }
				try {
					invoice.setCurrencyUnit(rs.getString("currencyUnit"));
				} catch (Exception e) {
					invoice.setCurrencyUnit("");
				}
				try {
          invoice.setEmployeeCode(rs.getString("employeeCode"));
        } catch (Exception e) {
          invoice.setEmployeeCode("");
        }
				try {
          invoice.setCustomerCode(rs.getString("customerCode"));
        } catch (Exception e) {
          invoice.setCustomerCode("");
        }
				try {
          invoice.setDepartmentCode(rs.getString("departmentCode"));
        } catch (Exception e) {
          invoice.setDepartmentCode("");
        }
				try {
          invoice.setProjectCode(rs.getString("projectCode"));
        } catch (Exception e) {
          invoice.setProjectCode("");
        }
				try {
          invoice.setSupplierCode(rs.getString("supplierCode"));
        } catch (Exception e) {
          invoice.setSupplierCode("");
        }
				try {
					invoice.setDiscount(rs.getDouble("discount"));
				} catch (Exception e) {
					invoice.setDiscount(0);
				}
				try {
          invoice.setGuest(rs.getInt("guest"));
        } catch (Exception e) {
          invoice.setGuest(0);
        }
				try {
					invoice.setTotal(rs.getDouble("total"));
				} catch (Exception e) {
					invoice.setTotal(0);
				}
				try {
					invoice.setFinalCharge(rs.getDouble("finalCharge"));
				} catch (Exception e) {
					invoice.setFinalCharge(0);
				}
				try {
					invoice.setType(rs.getString("type"));
				} catch (Exception e) {
					invoice.setType("");
				}
				
				try {
					invoice.setCode(rs.getString("code"));
				} catch (Exception e) {
					invoice.setCode("");
				}
				try {
					invoice.setTotalTax(rs.getDouble("totalTax"));
				} catch (Exception e) {
					invoice.setTotalTax(0);
				}
				try {
					invoice.setService(rs.getDouble("service"));
				} catch (Exception e) {
					invoice.setService(0);
				}
				
				try {
					invoice.setServiceRate(rs.getDouble("serviceRate"));
				} catch (Exception e) {
					invoice.setServiceRate(0);
				}
				try {
					invoice.setTotalPaid(rs.getDouble("totalPaid"));
				} catch (Exception e) {
					invoice.setTotalPaid(0);
				}
				try {
					invoice.setStartDate(new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("startDate")));
				} catch (Exception e) {
				  e.printStackTrace();
					invoice.setStartDate(new Date());
				}
				try {
					invoice.setEndDate(new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("endDate")));
				} catch (Exception e) {
					invoice.setEndDate(new Date());
				}
				invoice.setStatus(Invoice.Status.valueOf(rs.getString("status")));
				invoice.setActivityType(Invoice.ActivityType.valueOf(rs
						.getString("activityType")));
				return invoice;
			} catch (Throwable t) {
				throw new SQLException(t);
			}
		}
	};

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
					if(name.startsWith("concat")){
						String val = rs.getString(i + 1);
						map.put(name, val);
					}else {
						Object val = rs.getObject(i + 1);
						map.put(name, val);
					}
					
				}
				return map;
			} catch (Throwable t) {
				throw new SQLException(t);
			}
		}
	}
}
