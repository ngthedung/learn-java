package com.hkt.client.swingexp.app.component;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.hkt.module.core.entity.AbstractPersistable;

public class DefaultTableModelCommon extends DefaultTableModel {
  protected List<AbstractPersistable> data = new ArrayList<AbstractPersistable>();
  private String[] header = new String[]{"name","code"};

  public DefaultTableModelCommon(List<AbstractPersistable> data) {
      setData(data);
  }

  public List<AbstractPersistable> getData() {
      return data;
  }
  
  public AbstractPersistable getItem(int row){
    return data.get(row);
  }
  
  @Override
  public int getColumnCount() {
      return header.length;
  }
  
  @Override
  public String getColumnName(int column) {
      return header[column];
  }

  public void setData(List<AbstractPersistable> data) {
      this.data = data;
      fireTableDataChanged();
  }

  @Override
  public Object getValueAt(int row, int column){
  	if(column==0){
    return getData().get(row);
  	}else {
  		 return ((AbstractPersistable)getData().get(row)).getCode();
		}
  }

  @Override
  public int getRowCount() {
      return data==null?0:data.size();
  }

  @Override
  public boolean isCellEditable(int row, int column) {
      return false;
  }
  
  
}

