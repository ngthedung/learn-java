package com.hkt.module.product.repository;

import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.product.entity.ProductPrice;

interface ProductPriceRepositoryCustom {
  public FilterResult<ProductPrice> search(FilterQuery query);

}
