package com.hkt.client.swingexp.app.core;

import java.util.List;

import javax.swing.table.DefaultTableModel;


public interface ITableMain{
  
  public void click() ;
  
  public int getListSize();
  
  public List<Integer> getColumnSum();
  
  public DefaultTableModel loadTable(int currentPage, int pageSize);
  
  public boolean delete();
  
  public boolean isDelete();
 

}
