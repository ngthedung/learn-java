package com.hkt.client.swingexp.app.banhang.list;

import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.module.accounting.entity.InvoiceItem;

public class ChooseDelProduct extends JPanel implements IDialogResto {
	private JLabel lbchooseDelProduct;
	private boolean delete;

	public boolean isDelete() {
		return delete;
	}

	public ChooseDelProduct(String invoiceItem) {
		setOpaque(false);
		lbchooseDelProduct = new javax.swing.JLabel();
		lbchooseDelProduct.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lbchooseDelProduct.setText(invoiceItem);
		lbchooseDelProduct.setHorizontalAlignment(JLabel.CENTER);
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(
				lbchooseDelProduct, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(3, 3, 3)
				.addComponent(
				lbchooseDelProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
				javax.swing.GroupLayout.PREFERRED_SIZE)
				.addGap(3, 3, 3));
	}

	@Override
	public void Save() throws Exception {
		delete = true;
		((JDialog) getRootPane().getParent()).dispose();
	}
}
