package com.hkt.module.warehouse.repository;

import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.warehouse.entity.IdentityProduct;

public interface IdentityProductRepositoryCustom {
  public FilterResult<IdentityProduct> query(FilterQuery fquery) ;
  public ReportTable[] reportQuery(SQLSelectQuery[] query) ;

}
