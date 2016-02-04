package com.hkt.client.swingexp.app.thuchi;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.hkt.client.swingexp.app.banhang.screen.often.TableModelPayment;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceTransaction;

public class PanelTablePayment extends JPanel implements IDialogResto{
	
	public PanelTablePayment(InvoiceDetail invoiceDetail){
		setOpaque(false);
		this.setLayout(new GridLayout());
		JScrollPane scrollPane = new JScrollPane();
		
		JTable table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		table.setRowHeight(23);
		TableModelPayment modelPayment = new TableModelPayment(new ArrayList<InvoiceTransaction>());
		modelPayment.setData(invoiceDetail);
		table.setModel(modelPayment);
		scrollPane.setViewportView(table);
		this.add(scrollPane);
	}

	@Override
  public void Save() throws Exception {
	  ((JDialog)getRootPane().getParent()).dispose();
	  
  }

}
