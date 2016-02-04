package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.Dialog;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.ExtendJDateChooser;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.PanelTrans;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ManagerConvert;
import com.hkt.module.account.entity.Profile;
import com.toedter.calendar.JDateChooser;

public class PanelTextMoneyPayment extends JPanel implements IDialogResto {

	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.JCheckBox chbReturn;
	private javax.swing.JCheckBox chbPrint;
	private javax.swing.JCheckBox chbUnnitMoney;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JRadioButton jRadioButton1;
	private javax.swing.JRadioButton jRadioButton2;
	private javax.swing.JRadioButton jRadioButton3;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JLabel lable1;
	private javax.swing.JLabel lable2;
	private javax.swing.JLabel lable3;
	private javax.swing.JLabel lable;
	private javax.swing.JLabel lbUnitMoney1;
	private javax.swing.JLabel lbUnitMoney2;
	private javax.swing.JLabel lbUnitMoney3;
	private javax.swing.JTextField txt1;
	private javax.swing.JTextField txt2;
	private javax.swing.JTextField txt3;
	private String sumMoney;
	private boolean flagPayment;
	private static String sotienKD, sotienTK;
	
	

	public static void setSotienKD(String sotienKD) {
		PanelTextMoneyPayment.sotienKD = sotienKD;
	}

	public static void setSotienTK(String sotienTK) {
		PanelTextMoneyPayment.sotienTK = sotienTK;
	}

	public boolean isFlagPayment() {
		return flagPayment;
	}

	public void setFlagPayment(boolean flagPayment) {
		this.flagPayment = flagPayment;
	}

	public PanelTextMoneyPayment() {
		init();
		Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
		if (profile.get("InHoaDon") != null) {
			try {
				jTextField1.setText(profile.get("InHoaDon").toString());
			} catch (Exception e) {
				jTextField1.setText("1");
			}
		}
	}

	private void init() {
		buttonGroup1 = new javax.swing.ButtonGroup();
		lable3 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel("Tiền khách đưa");
		jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14));
		lable1 = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel("Tiền phải trả");
		jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14));
		txt1 = new javax.swing.JTextField();
		txt1.setEditable(false);
		lable2 = new javax.swing.JLabel();
		txt3 = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel("Tiền trả lại");
		jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14));
		txt2 = new javax.swing.JTextField();
		chbUnnitMoney = new javax.swing.JCheckBox("Quy đổi sang đơn vị mặc định");
		chbUnnitMoney.setFont(new java.awt.Font("Tahoma", 1, 14));
		lbUnitMoney2 = new javax.swing.JLabel();
		lbUnitMoney3 = new javax.swing.JLabel();
		lbUnitMoney1 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel("Số lần in");
		jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14));
		jTextField1 = new javax.swing.JTextField("1");
		jTextField1.setHorizontalAlignment(JTextField.RIGHT);
		jLabel10 = new javax.swing.JLabel("Loại thanh toán");
		jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14));
		jRadioButton1 = new javax.swing.JRadioButton("Tiền mặt");
		jRadioButton2 = new javax.swing.JRadioButton("Thẻ");
		jRadioButton3 = new javax.swing.JRadioButton("Tiếp khách");
		chbReturn = new javax.swing.JCheckBox("Trả hàng");
		chbPrint = new javax.swing.JCheckBox("In ngôn ngữ khác");
		buttonGroup1.add(jRadioButton1);
		buttonGroup1.add(jRadioButton2);
		buttonGroup1.add(jRadioButton3);
		jRadioButton1.setFont(new java.awt.Font("Tahoma", 0, 14));
		jRadioButton1.setSelected(true);
		jRadioButton1.setOpaque(false);
		jRadioButton2.setFont(new java.awt.Font("Tahoma", 0, 14));
		jRadioButton2.setOpaque(false);
		jRadioButton3.setFont(new java.awt.Font("Tahoma", 0, 14));
		jRadioButton3.setOpaque(false);
		txt1.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbUnnitMoney.setOpaque(false);
		txt3.setFont(new java.awt.Font("Tahoma", 0, 14));
		txt3.setEditable(false);
		txt2.setFont(new java.awt.Font("Tahoma", 0, 14));
		lable1.setFont(new java.awt.Font("Tahoma", 0, 14));
		lable2.setFont(new java.awt.Font("Tahoma", 0, 14));
		lable3.setFont(new java.awt.Font("Tahoma", 0, 14));

		lable = new javax.swing.JLabel();
		lable.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbReturn.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbReturn.setOpaque(false);
		chbPrint.setFont(new java.awt.Font("Tahoma", 0, 14));
		chbPrint.setOpaque(false);

		txt2.addCaretListener(new javax.swing.event.CaretListener() {
			public void caretUpdate(javax.swing.event.CaretEvent evt) {
				txt2CaretUpdate(evt);
			}
		});

		chbUnnitMoney.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				chbUnnitMoneyItemStateChanged(evt);
			}
		});

		jRadioButton1.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				jRadioButton1ItemStateChanged(evt);
			}
		});

		jRadioButton2.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				jRadioButton2ItemStateChanged(evt);
			}
		});

		jRadioButton3.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				jRadioButton3ItemStateChanged(evt);
			}
		});

		chbPrint.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				chbReturn1ItemStateChanged(evt);
			}
		});
		setOpaque(false);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout
		    .setHorizontalGroup(layout
		        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		        .addGroup(
		            layout
		                .createSequentialGroup()
		                // .addGap(15, 15, 15)
		                .addGroup(
		                    layout
		                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                        .addComponent(jLabel1)
		                        .addGroup(
		                            javax.swing.GroupLayout.Alignment.TRAILING,
		                            layout
		                                .createSequentialGroup()
		                                // .addGap(0, 0, Short.MAX_VALUE)
		                                .addGroup(
		                                    layout
		                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                        .addComponent(jLabel3)
		                                        .addComponent(jLabel2)
		                                        .addGroup(
		                                            layout
		                                                .createSequentialGroup()
		                                                .addGroup(
		                                                    layout.createParallelGroup(
		                                                        javax.swing.GroupLayout.Alignment.TRAILING).addComponent(
		                                                        jLabel10))
		                                                .addGap(20, 20, 20)
		                                                .addGroup(
		                                                    layout
		                                                        .createParallelGroup(
		                                                            javax.swing.GroupLayout.Alignment.LEADING)
		                                                        .addComponent(txt2, javax.swing.GroupLayout.DEFAULT_SIZE,
		                                                            javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                                                        .addComponent(txt1, javax.swing.GroupLayout.DEFAULT_SIZE,
		                                                            javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                                                        .addComponent(lable2,
		                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
		                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
		                                                            javax.swing.GroupLayout.PREFERRED_SIZE)
		                                                        .addComponent(lable1,
		                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
		                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
		                                                            javax.swing.GroupLayout.PREFERRED_SIZE)
		                                                        .addGroup(
		                                                            layout
		                                                                .createParallelGroup(
		                                                                    javax.swing.GroupLayout.Alignment.TRAILING)
		                                                                .addGroup(
		                                                                    layout.createSequentialGroup()
		                                                                        .addComponent(jRadioButton1)
		                                                                        .addGap(51, 51, 51)
		                                                                        .addComponent(jRadioButton2)
		                                                                        .addGap(34, 34, 34)
		                                                                        .addComponent(jRadioButton3)
		                                                                        .addGap(116, 116, 116))
		                                                                .addGroup(
		                                                                    layout
		                                                                        .createSequentialGroup()
		                                                                        .addGroup(
		                                                                            layout
		                                                                                .createParallelGroup(
		                                                                                    javax.swing.GroupLayout.Alignment.TRAILING)
		                                                                                .addGroup(
		                                                                                    layout.createSequentialGroup()
		                                                                                        .addComponent(jLabel5)
		                                                                                        .addGap(18, 18, 18))
		                                                                                .addGroup(
		                                                                                    javax.swing.GroupLayout.Alignment.LEADING,
		                                                                                    layout
		                                                                                        .createSequentialGroup()
		                                                                                        .addComponent(chbReturn)
		                                                                                        .addGap(49, 49, 49)
		                                                                                        .addComponent(chbPrint)
		                                                                                        .addPreferredGap(
		                                                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
		                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
		                                                                                            Short.MAX_VALUE)))
		                                                                        .addComponent(jTextField1,
		                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
		                                                                            52,
		                                                                            javax.swing.GroupLayout.PREFERRED_SIZE)))
		                                                        .addComponent(txt3, javax.swing.GroupLayout.DEFAULT_SIZE,
		                                                            javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                                                        .addComponent(lable,
		                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
		                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
		                                                            javax.swing.GroupLayout.PREFERRED_SIZE))))))
		                .addGap(18, 18, 18)
		                .addGroup(
		                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                        .addComponent(lbUnitMoney2).addComponent(lbUnitMoney1).addComponent(lbUnitMoney3))));

		layout
		    .setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		        .addGroup(
		            layout
		                .createSequentialGroup()
		                .addGap(15, 15, 15)
		                .addGroup(
		                    layout
		                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                        .addComponent(jLabel1)
		                        .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE,
		                            javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                        .addComponent(lbUnitMoney1))
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(lable1, javax.swing.GroupLayout.PREFERRED_SIZE, 21,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                .addGroup(
		                    layout
		                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                        .addComponent(txt2, javax.swing.GroupLayout.PREFERRED_SIZE,
		                            javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                        .addComponent(jLabel2).addComponent(lbUnitMoney2))
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                .addComponent(lable2, javax.swing.GroupLayout.PREFERRED_SIZE, 21,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                .addGroup(
		                    layout
		                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                        .addComponent(txt3, javax.swing.GroupLayout.PREFERRED_SIZE,
		                            javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                        .addComponent(lbUnitMoney3).addComponent(jLabel3))
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(lable, javax.swing.GroupLayout.PREFERRED_SIZE, 21,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addGap(23, 23, 23)
		                .addGroup(
		                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel10)
		                        .addComponent(jRadioButton1).addComponent(jRadioButton2).addComponent(jRadioButton3))
		                .addGroup(
		                    layout
		                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                        .addGroup(
		                            layout
		                                .createSequentialGroup()
		                                .addGap(9, 9, 9)
		                                .addGroup(
		                                    layout
		                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                                        .addComponent(jLabel5)
		                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
		                                            javax.swing.GroupLayout.DEFAULT_SIZE,
		                                            javax.swing.GroupLayout.PREFERRED_SIZE)))
		                        .addGroup(
		                            layout
		                                .createSequentialGroup()
		                                .addGap(27, 27, 27)
		                                .addGroup(
		                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                                        .addComponent(chbReturn).addComponent(chbPrint))))
		                .addGap(0, 30, Short.MAX_VALUE)));
	}

	public void initEvent(String strMoney, String unitMoney) {
		this.sumMoney = strMoney;
		txt1.setText(MyDouble.valueOf(strMoney).toString());
		txt2.setText(MyDouble.valueOf(strMoney).toString());
		txt3.setText("0");
		String name = "VND";
		try {
			name = LocaleModelManager.getInstance().getCurrencyByCode(unitMoney).getName();
		} catch (Exception e) {
		}
		lbUnitMoney1.setText(name);
		lbUnitMoney2.setText(name);
		lbUnitMoney3.setText(name);
		if (unitMoney == "VND") {
			chbUnnitMoney.setVisible(false);
		}
		ManagerConvert convertMoney = new ManagerConvert();
		String str = "";
		for (int i = 0; i < strMoney.length(); i++) {
			String s1 = String.valueOf(strMoney.charAt(i));
			if (!s1.trim().isEmpty()) {
				str = str + s1;
			}
		}
		try {
			String moneyText = convertMoney.readNumber(str);
			lable1.setText(moneyText);
		} catch (Exception e) {
		}
	}
	
	public void initEventPrint(String strMoney,String strMoney1, String unitMoney) {
		this.sumMoney = strMoney;
		txt1.setText(MyDouble.valueOf(strMoney).toString());
		txt2.setText(MyDouble.valueOf(strMoney1).toString());
		txt3.setText("0");
		String name = "VND";
		try {
			name = LocaleModelManager.getInstance().getCurrencyByCode(unitMoney).getName();
		} catch (Exception e) {
		}
		lbUnitMoney1.setText(name);
		lbUnitMoney2.setText(name);
		lbUnitMoney3.setText(name);
		if (unitMoney == "VND") {
			chbUnnitMoney.setVisible(false);
		}
		ManagerConvert convertMoney = new ManagerConvert();
		String str = "";
		for (int i = 0; i < strMoney.length(); i++) {
			String s1 = String.valueOf(strMoney.charAt(i));
			if (!s1.trim().isEmpty()) {
				str = str + s1;
			}
		}
		try {
			String moneyText = convertMoney.readNumber(str);
			lable1.setText(moneyText);
		} catch (Exception e) {
		}
	}

	public boolean isTV() {
		return !chbPrint.isSelected();
	}

	@Override
	public void Save() throws Exception {
		try {
			AccountingModelManager.print = Integer.parseInt(jTextField1.getText());
		} catch (Exception e) {
			AccountingModelManager.print = 0;
		}
		AccountingModelManager.isVN = !chbPrint.isSelected();
		Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
		profile.put("InHoaDon", String.valueOf(AccountingModelManager.print));
		AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(),
		    profile);
		MyDouble money = new MyDouble(txt1.getText());
		MyDouble money1 = new MyDouble(txt2.getText());
		//if (money1.doubleValue() >= money.doubleValue()) {
			flagPayment = true;
			sotienKD = txt2.getText();
			sotienTK = txt3.getText();
			if (chbReturn.isSelected()) {
				try {
					// IDialogReturnProduct dialogSales =
					// Lookup.getDefault().lookup(IDialogReturnProduct.class).getClass().newInstance();
					// dialogSales.setProject(project, idEnter);
					// dialogSales.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			((Dialog) getRootPane().getParent()).dispose();
//		} else {
//			JOptionPane.showMessageDialog(null, "Không đủ số tiền cần thanh toán");
//		}

	}

	private void txt2CaretUpdate(javax.swing.event.CaretEvent evt) {
		MyDouble money = new MyDouble(txt1.getText());
		MyDouble money1 = new MyDouble(txt2.getText());
		ManagerConvert convertMoney = new ManagerConvert();
		String strMoney = txt2.getText();
		String str = "";
		for (int i = 0; i < strMoney.length(); i++) {
			String s1 = String.valueOf(strMoney.charAt(i));
			if (!s1.trim().isEmpty()) {
				str = str + s1;
			}
		}
		try {
			String moneyText = convertMoney.readNumber(str);
			lable2.setText(moneyText);
		} catch (Exception e) {
		}

		if (money1.doubleValue() > money.doubleValue()) {
			MyDouble money2 = new MyDouble(money1.doubleValue() - money.doubleValue());
			money2.setNumDot(0);
			txt3.setText(money2.toString());
			String strMoney1 = txt3.getText();
			String str2 = "";
			for (int i = 0; i < strMoney1.length(); i++) {
				String s1 = String.valueOf(strMoney1.charAt(i));
				if (!s1.trim().isEmpty()) {
					str2 = str2 + s1;
				}
			}
			try {
				String moneyText2 = convertMoney.readNumber(str2);
				lable.setText(moneyText2);
			} catch (Exception e) {
			}

		} else {
			if (money1.doubleValue() < money.doubleValue()) {
				txt3.setText("-");
				lable.setText("Không đủ số tiền cần thanh toán");
			} else {
				txt3.setText("0");
				lable.setText("Không");
			}
		}

	}

	private void chbUnnitMoneyItemStateChanged(java.awt.event.ItemEvent evt) {
		String strMoney;
		if (chbUnnitMoney.isSelected()) {
			// MyDouble sum = MyDouble.mul(new MyDouble(sumMoney),
			// unitMoney.getRatioConversionWithVND());
			// if (sum.toString().endsWith(".00")) {
			// sum.setNumDot(0);
			// }
			// strMoney = sum.toString();
			// lbUnitMoney1.setText(UnitMoneySystem.getInstance().getUnitMoneyDefault().toString());
			// lbUnitMoney2.setText(UnitMoneySystem.getInstance().getUnitMoneyDefault().toString());
			// lbUnitMoney3.setText(UnitMoneySystem.getInstance().getUnitMoneyDefault().toString());
		} else {
			// MyDouble sum = MyDouble.div(new MyDouble(txt1.getText()),
			// unitMoney.getRatioConversionWithVND());
			// if (sum.toString().endsWith(".00")) {
			// sum.setNumDot(0);
			// }
			// strMoney = sum.toString();
			// lbUnitMoney1.setText(unitMoney.toString());
			// lbUnitMoney2.setText(unitMoney.toString());
			// lbUnitMoney3.setText(unitMoney.toString());
		}
		// txt1.setText(strMoney);
		// txt3.setText("0");
		// txt2.setText(strMoney);
		// ConvertMoney convertMoney = new ConvertMoney();
		// String str = "";
		// for (int i = 0; i < strMoney.length(); i++) {
		// String s1 = String.valueOf(strMoney.charAt(i));
		// if (!s1.trim().isEmpty()) {
		// str = str + s1;
		// }
		// }
		// try {
		// String moneyText = convertMoney.readNumber(str);
		// lable1.setText(moneyText);
		// } catch (Exception e) {
		// }
	}

	public javax.swing.JRadioButton getjRadioButton2() {
		return jRadioButton2;
	}

	private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {
		try {

			Save();
		} catch (Exception e) {
		}

	}

	private void jRadioButton1ItemStateChanged(java.awt.event.ItemEvent evt) {
		if (jRadioButton1.isSelected()) {
			AccountingModelManager.pay = "Tiền mặt";
		}
	}

	private void jRadioButton2ItemStateChanged(java.awt.event.ItemEvent evt) {
		if (jRadioButton2.isSelected()) {
			AccountingModelManager.pay = "Thẻ";
		}
	}

	private void jRadioButton3ItemStateChanged(java.awt.event.ItemEvent evt) {
		if (jRadioButton3.isSelected()) {
			AccountingModelManager.pay = "Tiếp khách";
		}
	}

	private void chbReturn1ItemStateChanged(java.awt.event.ItemEvent evt) {
		if (chbPrint.isSelected()) {
			// u.setDescription("TA");
			// operationStatusDAO.update(u);
		} else {
			// u.setDescription("TV");
			// operationStatusDAO.update(u);
		}
	}

	/** Lấy thông tin ở trên giao diện số tiền khách đưa */
	public static String getSoTienKhachDua() {
		return sotienKD;
	}

	/** lấy thông tin ở trên giao diện số tiền lẻ trả lại khách */
	public static String getSoTienLeTraKhach() {
		return sotienTK;
	}

}
