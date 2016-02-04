package com.hkt.client.swingexp.app.banhang.list;

import java.awt.Color;
import java.awt.Dialog;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.module.accounting.entity.InvoiceItem;

@SuppressWarnings("serial")
public class PanelChooseQuantity extends JPanel implements IDialogResto {
	private JLabel lbquantityht, lbquantitymd;
	private JTextField txtquantityproductht, txtquantityproductmd;
	private boolean edit;
	public boolean isEdit(){
	  return edit;
	}
	private InvoiceItem invoiceItem;

	public PanelChooseQuantity(InvoiceItem invoiceItem) {
	  this.invoiceItem = invoiceItem;
		setOpaque(false);
		this.invoiceItem = invoiceItem;
		lbquantityht = new javax.swing.JLabel();
		lbquantitymd = new javax.swing.JLabel();
		txtquantityproductht = new javax.swing.JTextField(invoiceItem.getItemName());
		txtquantityproductht.setEnabled(false);
		txtquantityproductmd = new javax.swing.JTextField();
		txtquantityproductmd.addCaretListener(new CaretListener() {
      
      @Override
      public void caretUpdate(CaretEvent e) {
       try {
        Double.parseDouble(txtquantityproductmd.getText());
        lbquantitymd.setForeground(Color.black);
      } catch (Exception e2) {
        lbquantitymd.setForeground(Color.RED);
      }
        
      }
    });
		lbquantityht.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lbquantityht.setText("Tên sản phẩm");

		lbquantitymd.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lbquantitymd.setText("Số lượng muốn bán");

		txtquantityproductht.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

		txtquantityproductmd.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
		txtquantityproductmd.setHorizontalAlignment(JTextField.RIGHT);

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
														txtquantityproductmd))));
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
								.addGap(4, 4, 4)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														txtquantityproductmd,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														23,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(lbquantitymd))));
	}

	@Override
	public void Save() throws Exception {
	  if(lbquantitymd.getForeground()==Color.red){
	    return;
	  }
		edit = true;
		invoiceItem.setQuantity(MyDouble.parseDouble(txtquantityproductmd.getText()));
		((Dialog)getRootPane().getParent()).dispose();
	}
}
