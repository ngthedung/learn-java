package com.hkt.module.school.repository;

import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.school.entity.CourseSessionRegistration;

interface CourseSessionRegistrationRepositoryCustom {
  public FilterResult<CourseSessionRegistration> search(FilterQuery query);
}
