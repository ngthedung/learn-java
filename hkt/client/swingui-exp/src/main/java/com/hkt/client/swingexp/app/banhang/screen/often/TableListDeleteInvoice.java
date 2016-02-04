package com.hkt.client.swingexp.app.banhang.screen.often;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableMain;

@SuppressWarnings("serial")
public class TableListDeleteInvoice extends JTable implements ITableMain {
	private TableModelDeleteInvoice mdTbMenu;
	public TableListDeleteInvoice() {

		mdTbMenu = new TableModelDeleteInvoice();
		setModel(mdTbMenu);
		
	}

	@Override
	public void click() {
	
	}

	@Override
	public int getListSize() {
		return mdTbMenu.getListBankAccount().size();
	}

	@Override
	public List<Integer> getColumnSum() {
		return null;
	}

	@Override
	public DefaultTableModel loadTable(int index, int pageSize) {
		return mdTbMenu;

	}

  @Override
  public boolean delete() {
	 
    return false;
  }
  
  @Override
  public boolean isDelete() {
    return false;
  }

}
