package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialogPlus;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;

@SuppressWarnings("serial")
public class DialogPayOnce extends JPanel implements IDialogResto {

	private JButton btnBanSao, btnBangTien;
	private int index = -1;
	private InvoiceDetail invoiceDetail;

	public DialogPayOnce(InvoiceDetail invoiceDetail1) {
		this.invoiceDetail = invoiceDetail1;
		init();
	}

	private void init() {
		btnBanSao = new JButton();
		btnBanSao.setName("ScreenOften_PaymenOdd");
		btnBanSao.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
		btnBanSao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (btnBanSao.getText().equals("Hủy TT lẻ")) {
					index = 2;
					for (int i = 0; i < invoiceDetail.getTransactions().size();) {
						invoiceDetail.getTransactions().remove(i);
					}
					try {
						AccountingModelManager.getInstance().saveInvoice(invoiceDetail);
					} catch (Exception e) {

					}
					disposeForm();
				} else {
					index = 0;
					disposeForm();
				}

			}
		});
		btnBangTien = new JButton();
		btnBangTien.setName("btnBangTien");
		btnBangTien.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
		btnBangTien.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				index = 1;
				disposeForm();
			}
		});

		btnBanSao.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		btnBanSao.setText("Bằng SP");

		btnBangTien.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		btnBangTien.setText("Bằng tiền");

		for (InvoiceItem operation : invoiceDetail.getItems()) {
			if (operation.getStatus().equals(AccountingModelManager.isPayment)) {
				btnBanSao.setText("Bằng SP");
				btnBangTien.setEnabled(false);
				break;
			} else {
				if (invoiceDetail.getTransactions().size() > 0) {
					btnBanSao.setText("Hủy TT lẻ");
				}
			}
		}

		setOpaque(false);
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);

		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addGap(10, 10, 10)
						.addComponent(btnBangTien, GroupLayout.PREFERRED_SIZE, 119, Short.MAX_VALUE).addGap(50, 50, 50)
						.addComponent(btnBanSao, GroupLayout.PREFERRED_SIZE, 119, Short.MAX_VALUE)
						.addGap(10, 10, 10)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(10, 10, 10)
								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(btnBanSao, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(btnBangTien, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
														javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(36, Short.MAX_VALUE)));

	}


	public void disposeForm() {
		(((JDialog) getRootPane().getParent())).dispose();
	}

	public int getReceipt() {
		return index;
	}

	@Override
	public void Save() throws Exception {
		(((JDialog) getRootPane().getParent())).dispose();
		
	}
}