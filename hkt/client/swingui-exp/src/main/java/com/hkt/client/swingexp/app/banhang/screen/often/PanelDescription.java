package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.Color;
import java.awt.Dialog;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.module.accounting.entity.InvoiceItem;

@SuppressWarnings("serial")
public class PanelDescription extends JPanel implements IDialogResto {
	private JLabel lbquantityht, lbquantitymd;
	private JTextField txtquantityproductht;
	private JTextArea txtquantityproductmd;
	private boolean edit;
	public boolean isEdit(){
	  return edit;
	}
	private InvoiceItem invoiceItem;

	public PanelDescription(InvoiceItem invoiceItem) {
	  this.invoiceItem = invoiceItem;
		setOpaque(false);
		this.invoiceItem = invoiceItem;
		lbquantityht = new javax.swing.JLabel();
		lbquantitymd = new javax.swing.JLabel();
		txtquantityproductht = new javax.swing.JTextField(invoiceItem.getItemName());
		txtquantityproductht.setEnabled(false);
		txtquantityproductmd = new javax.swing.JTextArea(invoiceItem.getDescription());
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(txtquantityproductmd);
		lbquantityht.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lbquantityht.setText("Tên sản phẩm");

		lbquantitymd.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lbquantitymd.setText("Ghi chú");

		txtquantityproductht.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

		txtquantityproductmd.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(lbquantitymd)
												.addComponent(lbquantityht))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														txtquantityproductht,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														304, Short.MAX_VALUE)
												.addComponent(
												    scrollPane))));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(lbquantityht)
												.addComponent(
														txtquantityproductht,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														23,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(10, 10, 10)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
												    scrollPane,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														100,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(lbquantitymd))));
	}

	@Override
	public void Save() throws Exception {
	  if(lbquantitymd.getForeground()==Color.red){
	    return;
	  }
		edit = true;
		invoiceItem.setDescription(txtquantityproductmd.getText());
		((Dialog)getRootPane().getParent()).dispose();
	}
}
