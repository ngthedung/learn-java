package com.hkt.client.swingexp.app.banhang.list;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.module.accounting.entity.InvoiceItem;

public class PanelChoosePrice extends JPanel implements IDialogResto {
	private ExtendJLabel lblPriceOld, lblPriceNew;
	private ExtendJTextField txtPriceOld, txtPriceNew;
	private InvoiceItem invoiceItem;
	private boolean edit;

	public PanelChoosePrice(InvoiceItem _invoiceItem) {
		lblPriceOld = new ExtendJLabel("Giá cũ");
		lblPriceNew = new ExtendJLabel("Giá mới");
		txtPriceOld = new ExtendJTextField();
		txtPriceOld.setHorizontalAlignment(JTextField.RIGHT);
		txtPriceNew = new ExtendJTextField();
		txtPriceNew.setHorizontalAlignment(JTextField.RIGHT);
		txtPriceNew.setName("txtPriceNew");
		init();
		
		this.invoiceItem = _invoiceItem;
		getData();
	}
	
	public void init(){
		this.setOpaque(false);
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(lblPriceOld)
												.addComponent(lblPriceNew))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														txtPriceOld,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														304, Short.MAX_VALUE)
												.addComponent(txtPriceNew))));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(lblPriceOld)
												.addComponent(
														txtPriceOld,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														23,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(4, 4, 4)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														lblPriceNew,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														23,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(txtPriceNew))));
		txtPriceOld.setEditable(false);
	}

	public void getData(){
		txtPriceOld.setText(MyDouble.valueOf(Double.toString(invoiceItem.getUnitPrice())).toString());
	}
	
	@Override
	public void Save() throws Exception {
		invoiceItem.setUnitPrice(Double.parseDouble(txtPriceNew.getText()));
		edit = true;
		((JDialog)getRootPane().getParent()).dispose();
	}

  public boolean isEdit() {
   return edit;
  }
}
