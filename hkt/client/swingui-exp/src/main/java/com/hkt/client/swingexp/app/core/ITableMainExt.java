package com.hkt.client.swingexp.app.core;

import java.util.Date;

public interface ITableMainExt extends ITableMain {

  public void loadDataByTime(Date startDate, Date endDate);
  
  public Date getDateStart();
  
  public Date getDateEnd();

}
