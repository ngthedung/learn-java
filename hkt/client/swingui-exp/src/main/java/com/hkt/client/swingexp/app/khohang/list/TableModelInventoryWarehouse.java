package com.hkt.client.swingexp.app.khohang.list;

import java.awt.Dialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.ITableModel;
import com.hkt.client.swingexp.app.core.ITableModelExt;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductAttribute;
import com.hkt.module.product.entity.ProductTag;

@SuppressWarnings("serial")
public class TableModelInventoryWarehouse extends DefaultTableModel {
	private String[] header = { "Mã","Sản phẩm", "Kho hàng", "Nhóm", "SL hiện còn", "SL thực tế", "Đơn vị" };

	public TableModelInventoryWarehouse() {

	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if (column == 2 || column == 5) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getColumnCount() {
		return header == null ? 0 : header.length;
	}

	@Override
	public String getColumnName(int column) {
		return header[column];
	}

}
