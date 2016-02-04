package com.hkt.module.school.repository;

import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.school.entity.CourseSession;

interface CourseSessionRepositoryCustom {
  public FilterResult<CourseSession> search(FilterQuery query);
}
