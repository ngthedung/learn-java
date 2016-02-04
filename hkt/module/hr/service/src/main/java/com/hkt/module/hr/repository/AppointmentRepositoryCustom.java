package com.hkt.module.hr.repository;

import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.hr.entity.Appointment;

interface AppointmentRepositoryCustom {
  public FilterResult<Appointment> search(FilterQuery query) ;
}
