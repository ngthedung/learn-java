package com.hkt.module.restaurant.repository;

import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.restaurant.entity.Recipe;

interface RecipeRepositoryCustom {
  public FilterResult<Recipe> search(FilterQuery query);

}
