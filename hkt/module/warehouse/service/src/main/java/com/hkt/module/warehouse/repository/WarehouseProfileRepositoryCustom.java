package com.hkt.module.warehouse.repository;

import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;

interface WarehouseProfileRepositoryCustom {
  
  public ReportTable[] reportQuery(SQLSelectQuery[] query) ;
 
}
