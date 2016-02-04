package com.hkt.module.promotion.repository;

import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.promotion.entity.Menu;

interface MenuRepositoryCustom {
  public FilterResult<Menu> search(FilterQuery query) ;
}
