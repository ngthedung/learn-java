package com.hkt.client.swingexp.app.banhang.list;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.hkt.client.swingexp.app.banhang.screen.often.PanelRounding;
import com.hkt.client.swingexp.app.component.ExtendJDateChooser;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.ManagerConvert;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.accounting.entity.InvoiceTransaction.TransactionType;
import com.toedter.calendar.JDateChooser;

public class PanelUpfrontPayment extends JPanel implements IDialogResto {
	private JLabel lbtotalmoney, lbmoneyTT, lbmoneyCL, jLabel1, jLabel2, jLabel3;
	private JTextField txtSumMoney, txtAfterPayment, txtBeforPayment;
	private boolean flagPayment;
	private InvoiceDetail invoiceDetail;
	private ExtendJDateChooser dc;

	public boolean isFlagPayment() {
		return flagPayment;
	}

	public JLabel getLabel() {
		ExtendJLabel lb = new ExtendJLabel("Ngày thanh toán");
		return lb;
	}

	public JDateChooser getJDateChooser() {
		dc = new ExtendJDateChooser();
		dc.setDate(new Date());
		return dc;
	}

	private double rounding;

	public PanelUpfrontPayment() {
		setOpaque(false);
		init();
		Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
		if (profile.get(PanelRounding.TronLen) != null) {
			rounding = MyDouble.valueOf(profile.get(PanelRounding.TronLen).toString()).doubleValue();
		} else {
			if (profile.get(PanelRounding.TronPhay) != null) {
				rounding = MyDouble.valueOf(profile.get(PanelRounding.TronPhay).toString()).doubleValue() * -1;
			}
		}
		txtBeforPayment.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e1) {
				MyDouble money = new MyDouble(txtSumMoney.getText());
				MyDouble money1 = new MyDouble(txtBeforPayment.getText());

				ManagerConvert convertMoney = new ManagerConvert();
				String strMoney = txtBeforPayment.getText();
				String str = "";
				for (int i = 0; i < strMoney.length(); i++) {
					String s1 = String.valueOf(strMoney.charAt(i));
					if (!s1.trim().isEmpty()) {
						str = str + s1;
					}
				}

				try {
					String moneyText = convertMoney.readNumber(str);
					jLabel2.setText(moneyText);
					if (moneyText == "") {
						jLabel2.setText("không");
					}
				} catch (Exception e) {
					jLabel2.setText("không");
				}

				if (money1.doubleValue() < money.doubleValue()) {
					MyDouble money2 = MyDouble.valueOf(money.doubleValue() - money1.doubleValue());
					money2.setNumDot(0);
					txtAfterPayment.setText(money2.toString());
					String strMoney1 = txtAfterPayment.getText();
					String str2 = "";
					for (int i = 0; i < strMoney1.length(); i++) {
						String s1 = String.valueOf(strMoney1.charAt(i));
						if (!s1.trim().isEmpty()) {
							str2 = str2 + s1;
						}
					}
					try {
						String moneyText2 = convertMoney.readNumber(str2);
						jLabel3.setText(moneyText2);
					} catch (Exception e) {
						jLabel3.setText("không");
					}

				} else {
					txtAfterPayment.setText("0");
					jLabel3.setText("không");
				}

			}
		});
	}

	private void init() {
		lbtotalmoney = new javax.swing.JLabel();
		lbmoneyTT = new javax.swing.JLabel();
		lbmoneyCL = new javax.swing.JLabel();
		txtSumMoney = new javax.swing.JTextField();
		txtSumMoney.setEditable(false);
		txtAfterPayment = new javax.swing.JTextField();
		txtAfterPayment.setName("txtAfterPayment");
		txtBeforPayment = new javax.swing.JTextField();
		txtBeforPayment.setName("txtBeforPayment");
		txtAfterPayment.setEditable(false);
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();

		setBackground(new java.awt.Color(255, 255, 255));

		lbtotalmoney.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lbtotalmoney.setText("Tổng tiền phải trả");

		lbmoneyTT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lbmoneyTT.setText("Thanh toán trước");

		lbmoneyCL.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lbmoneyCL.setText("Còn lại");

		txtSumMoney.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

		txtAfterPayment.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

		txtBeforPayment.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

		jLabel1.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
		jLabel1.setText("Không");

		jLabel2.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
		jLabel2.setText("Không");

		jLabel3.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
		jLabel3.setText("Không");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
		    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        layout
		            .createSequentialGroup()
		            .addGroup(
		                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(lbtotalmoney)
		                    .addComponent(lbmoneyTT).addComponent(lbmoneyCL))
		            .addGap(22, 22, 22)
		            .addGroup(
		                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(txtSumMoney, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
		                    .addComponent(txtBeforPayment).addComponent(txtAfterPayment)))
		    .addGroup(
		        layout
		            .createSequentialGroup()
		            .addGap(179, 179, 179)
		            .addGroup(
		                layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    layout
		        .createSequentialGroup()
		        .addContainerGap()
		        .addGroup(
		            layout
		                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                .addComponent(lbtotalmoney)
		                .addComponent(txtSumMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                    javax.swing.GroupLayout.PREFERRED_SIZE))
		        .addGap(18, 18, 18)
		        .addComponent(jLabel1)
		        .addGap(16, 16, 16)
		        .addGroup(
		            layout
		                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                .addComponent(lbmoneyTT)
		                .addComponent(txtBeforPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                    javax.swing.GroupLayout.PREFERRED_SIZE))
		        .addGap(18, 18, 18)
		        .addComponent(jLabel2)
		        .addGap(16, 16, 16)
		        .addGroup(
		            layout
		                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                .addComponent(lbmoneyCL)
		                .addComponent(txtAfterPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                    javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18).addComponent(jLabel3)
		        .addGap(16, 16, 16).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

	}

	public InvoiceDetail getInvoiceDetail() {
		return invoiceDetail;
	}

	@Override
	public void Save() throws Exception {
		String dateTime = "";
		List<String> mang = new ArrayList<String>();
		if(dc==null){
			dc = new ExtendJDateChooser();
			dc.setDate(new Date());
		}
		StringTokenizer st = new StringTokenizer(new SimpleDateFormat("dd-MM-yyyy").format(dc.getDate()), "-");
		while (st.hasMoreTokens()) {
			mang.add(st.nextToken());
		}
		if (mang.get(0).length() == 1) {
			dateTime = dateTime + "0" + mang.get(0);
		} else {
			dateTime = dateTime + mang.get(0);
		}
		if (mang.get(1).length() == 1) {
			dateTime = dateTime + "0" + mang.get(1);
		} else {
			dateTime = dateTime + mang.get(1);
		}
		if (mang.get(2).length() == 2) {
			dateTime = dateTime + "20" + mang.get(2);
		} else {
			dateTime = dateTime + mang.get(2);
		}
		if (invoiceDetail != null) {
			List<String> mang1 = new ArrayList<String>();
			StringTokenizer st1 = new StringTokenizer(
			    new SimpleDateFormat("dd-MM-yyyy").format(invoiceDetail.getStartDate()), "-");
			while (st1.hasMoreTokens()) {
				mang1.add(st1.nextToken());
			}
			String dateTime1 = "";
			if (mang1.get(0).length() == 1) {
				dateTime1 = dateTime1 + "0" + mang1.get(0);
			} else {
				dateTime1 = dateTime1 + mang1.get(0);
			}
			if (mang1.get(1).length() == 1) {
				dateTime1 = dateTime1 + "0" + mang1.get(1);
			} else {
				dateTime1 = dateTime1 + mang1.get(1);
			}
			if (mang1.get(2).length() == 2) {
				dateTime1 = dateTime1 + "20" + mang1.get(2);
			} else {
				dateTime1 = dateTime1 + mang1.get(2);
			}

			if (new SimpleDateFormat("ddMMyyyy").parse(dateTime).before(new SimpleDateFormat("ddMMyyyy").parse(dateTime1))) {
				ChooseDelProduct panel = new ChooseDelProduct("Ngày biên lai thanh toán không thể trước ngày tạo hóa đơn");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setTitle("Thông báo");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				return;
			}
			MyDouble money = new MyDouble(txtSumMoney.getText());
			MyDouble money1 = new MyDouble(txtBeforPayment.getText());
			if (money1.doubleValue() <= money.doubleValue()) {
				flagPayment = true;
				InvoiceTransaction transaction = new InvoiceTransaction();
				transaction.setTransactionType(TransactionType.Cash);
				transaction.setCreatedBy(ManagerAuthenticate.getInstance().getLoginId());
				transaction.setCurrencyUnit("VND");
				transaction.setTotal(MyDouble.parseDouble(txtBeforPayment.getText()));
				Calendar c = Calendar.getInstance();
				c.setTime(dc.getDate());
				c.set(Calendar.HOUR_OF_DAY, Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
				c.set(Calendar.MINUTE, Calendar.getInstance().get(Calendar.MINUTE));
				transaction.setTransactionDate(c.getTime());
				invoiceDetail.add(transaction);
				((JDialog) getRootPane().getParent()).dispose();
			}
		}
	}

	public JTextField getTxtBeforPayment() {
		return txtBeforPayment;
	}

	private MyDouble getMoney(double moneyMissing1) {
		MyDouble a = new MyDouble(moneyMissing1);
		if (rounding != 0) {
			if (rounding > 0) {
				moneyMissing1 = moneyMissing1 / rounding;
				MyDouble b = new MyDouble(moneyMissing1);
				b.setNumDot(0);

				if (moneyMissing1 > new MyDouble(b.toString()).doubleValue()) {
					moneyMissing1 = moneyMissing1 + 1;
				}
				MyDouble d = new MyDouble(moneyMissing1);
				d.setNumDot(0);
				MyDouble c = new MyDouble(d.toString());
				moneyMissing1 = c.doubleValue() * rounding;
				a = new MyDouble(moneyMissing1);
				a.setNumDot(0);
				moneyMissing1 = a.doubleValue();
			} else {
				try {
					int k = MyDouble.valueOf(rounding).intValue() * (-1);
					a = new MyDouble(moneyMissing1);
					a.setNumDot(k);
				} catch (Exception e) {
				}
			}
		}
		return a;
	}

	public void initEvent(InvoiceDetail invoiceDetail) {
		this.invoiceDetail = invoiceDetail;
		String strMoney = getMoney(invoiceDetail.getFinalCharge() - invoiceDetail.getTotalPaid()).toString();
		txtSumMoney.setText(strMoney);
		txtAfterPayment.setText("0");
		txtBeforPayment.setText(strMoney);
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
			jLabel1.setText(moneyText);
		} catch (Exception e) {
			jLabel1.setText("Không");
		}

	}

}
