package com.hkt.module.partner.customer.repository;

import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.partner.customer.entity.Credit;

interface CreditRepositoryCustom {
  public FilterResult<Credit> search(FilterQuery query) ;

}
