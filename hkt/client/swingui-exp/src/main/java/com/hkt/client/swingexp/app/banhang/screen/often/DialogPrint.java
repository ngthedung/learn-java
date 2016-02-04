package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialogPlus;

@SuppressWarnings("serial")
public class DialogPrint extends JPanel implements IDialogResto {
	private JButton btnhoadon, btnbansao, btnxemphieuin;
	private int print = 0;

	public int getPrint() {
		return print;
	}

	private String sumMoney, unitMoney;

	public DialogPrint(String strMoney, String unitMoney1) {
		
		this.sumMoney = strMoney;
		this.unitMoney = unitMoney1;
		
		
		btnhoadon = new javax.swing.JButton();
		btnhoadon.setName("btnhoadon");
		btnbansao = new javax.swing.JButton();
		btnbansao.setVisible(false);
		btnxemphieuin = new javax.swing.JButton();
		btnxemphieuin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				print = 3;
				disposeForm();
			}
		});
		btnhoadon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PanelTextMoneyPayment panel = new PanelTextMoneyPayment();
				panel.setName("hoadon");
				panel.initEventPrint(sumMoney,"0", unitMoney);
				DialogResto dialog = new DialogResto(panel, false, 400, 350);
				dialog.setName("dlhoadon");
				dialog.setTitle("Thanh toán");
				// dialog.setSize(790, 507);
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isFlagPayment()) {
					print = 1;
				}else{
					print =0;
				}
				disposeForm();
			}
		});

		btnbansao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PanelTextMoneyPayment panel = new PanelTextMoneyPayment();
				panel.initEvent(unitMoney, unitMoney);
				DialogResto dialog = new DialogResto(panel, false, 790, 480);
				dialog.setTitle("Thanh toán");
//				dialog.setSize(790, 507);
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isFlagPayment()) {
					print = 2;
				}else{
					print =0;
				}
				disposeForm();

			}
		});

		btnhoadon.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		btnhoadon.setText("Hóa đơn");
		btnhoadon.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));

		btnbansao.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		btnbansao.setText("Bản sao");
		btnbansao.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));

		btnxemphieuin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		btnxemphieuin.setText("Xem phiếu In");
		btnxemphieuin.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));

		setOpaque(false);
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);

		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addComponent(btnhoadon, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
						.addGap(27, 27, 27)
						.addComponent(btnbansao, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
						.addGap(29, 29, 29)
						.addComponent(btnxemphieuin, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
						));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addGap(10, 10, 10)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(btnbansao, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnhoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnxemphieuin, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
												javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(39, Short.MAX_VALUE)));
	
	}

	public void disposeForm() {
		(((JDialog) getRootPane().getParent())).dispose();
	}

	@Override
	public void Save() throws Exception {
		(((JDialog) getRootPane().getParent())).dispose();
		
	}
}
