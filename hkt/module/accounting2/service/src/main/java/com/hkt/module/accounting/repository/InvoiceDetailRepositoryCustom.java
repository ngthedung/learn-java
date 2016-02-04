package com.hkt.module.accounting.repository;

import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.ReportTableProcessor;
import com.hkt.module.core.entity.SQLSelectQuery;

interface InvoiceDetailRepositoryCustom {
  public FilterResult<InvoiceDetail> search(FilterQuery query) ;
  public FilterResult<Invoice> searchInvoices(FilterQuery query) ;
  public FilterResult<InvoiceDetail> query(FilterQuery fquery) ;
  public ReportTable report(FilterQuery fquery, ReportTableProcessor<InvoiceDetail> processor) ;
  public ReportTable[] reportQuery(SQLSelectQuery[] query) ;
  public FilterResult<InvoiceItem> queryInvoiceItems(FilterQuery fquery) ;
  public int count(Class<?> type) ;
  public void drop(Class<?> type) ;
  public void drop(String tableName) ;
  public void executeSQL(String sql);
}
