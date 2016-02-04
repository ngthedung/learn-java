package com.hkt.client.swingexp.app.hethong;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.ExtendJCheckBox;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.entity.ManageCodes;
import com.hkt.module.accounting.entity.ManageCodes.ManageType;
import com.hkt.module.accounting.entity.ManageCodes.RotationType;

@SuppressWarnings("serial")
public class PaneSetDate extends JPanel implements IDialogResto {
	private JComboBox cboRoInvoice_BH, cboRoInvoice_TC, cboRoInvoice_DH, cboRoInvoiceMH, cboRoInvoiceCN, cboRoXNKho;
	private ExtendJLabel lbObject, lbRotationType, lbDesciption;
	private JPanel jPanel1, jPanel4, jPanel5;
	private JTextField txtDesInvoice_BH, txtDesInvoice_TC, txtDesInvoice_DH, txtDesInvoiceMH, txtDesInvoiceCN,
	    txtDesXNKho;
	private ExtendJCheckBox chbInvoice_BH, chbInvoice_TC, chbInvoice_DH, chbInvoiceMH, chbInvoiceCN, chbXNKho;
	public static String DateInvoiceBH = "Date_Invoice_BH";
	public static String DateInvoiceTC = "Date_Invoice_TC";
	public static String DateInvoiceDMH = "Date_Invoice_DMH";
	public static String DateInvoiceTH = "DateInvoiceTH";
	public static String DateInvoiceCN = "DateInvoiceCN";
	public static String DateXNKho = "DateXuatNhapKho";
	private HashMap<String, String> hashMap1 = new HashMap<String, String>();

	public PaneSetDate() {
		initComponents();
		setOpaque(false);

		hashMap1.put("Quản lý hóa đơn", DateInvoiceBH);
		hashMap1.put("Danh sách thu chi", DateInvoiceTC);
		hashMap1.put("Quản lý đơn đặt hàng", DateInvoiceDMH);
		hashMap1.put("Quản lý đơn trả hàng", DateInvoiceTH);
		hashMap1.put("Danh sách công nợ", DateInvoiceCN);
		hashMap1.put("Quản lý xuất nhập kho", DateXNKho);
		try {
			addAction(chbInvoiceCN, cboRoInvoiceCN, txtDesInvoiceCN);
			addAction(chbInvoice_DH, cboRoInvoice_DH, txtDesInvoice_DH);
			addAction(chbInvoice_BH, cboRoInvoice_BH, txtDesInvoice_BH);
			addAction(chbInvoice_TC, cboRoInvoice_TC, txtDesInvoice_TC);
			addAction(chbInvoiceMH, cboRoInvoiceMH, txtDesInvoiceMH);
			addAction(chbXNKho, cboRoXNKho, txtDesXNKho);

			addItemL(cboRoInvoiceCN, txtDesInvoiceCN);
			addItemL(cboRoInvoice_DH, txtDesInvoice_DH);
			addItemL(cboRoInvoice_BH, txtDesInvoice_BH);
			addItemL(cboRoInvoice_TC, txtDesInvoice_TC);
			addItemL(cboRoInvoiceMH, txtDesInvoiceMH);
			addItemL(cboRoXNKho, txtDesXNKho);
		} catch (Exception e) {
		}

		setObject(chbInvoiceCN, cboRoInvoiceCN, txtDesInvoiceCN);
		setObject(chbInvoice_DH, cboRoInvoice_DH, txtDesInvoice_DH);
		setObject(chbInvoice_BH, cboRoInvoice_BH, txtDesInvoice_BH);
		setObject(chbInvoice_TC, cboRoInvoice_TC, txtDesInvoice_TC);
		setObject(chbInvoiceMH, cboRoInvoiceMH, txtDesInvoiceMH);
		setObject(chbXNKho, cboRoXNKho, txtDesXNKho);
	}

	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jPanel1.setOpaque(false);
		lbObject = new ExtendJLabel("Đối tượng");

		chbInvoice_BH = new ExtendJCheckBox("Quản lý hóa đơn");
		chbInvoice_TC = new ExtendJCheckBox("Danh sách thu chi");
		chbInvoice_DH = new ExtendJCheckBox("Quản lý đơn đặt hàng");
		chbInvoiceMH = new ExtendJCheckBox("Quản lý đơn trả hàng");
		chbInvoiceCN = new ExtendJCheckBox("Danh sách công nợ");
		chbXNKho = new ExtendJCheckBox("Quản lý xuất nhập kho");

		jPanel4 = new javax.swing.JPanel();
		jPanel4.setOpaque(false);
		lbRotationType = new ExtendJLabel("Thời gian lọc");
		cboRoInvoice_BH = new ExtendJComboBox();
		cboRoInvoice_TC = new ExtendJComboBox();
		cboRoInvoice_DH = new ExtendJComboBox();
		cboRoInvoiceMH = new ExtendJComboBox();
		cboRoInvoiceCN = new ExtendJComboBox();
		cboRoXNKho = new ExtendJComboBox();

		cboRoInvoice_BH.setModel(new DefaultComboBoxModel(new String[] { "1 ngày", "1 tuần", "1 tháng", "khác" }));
		cboRoInvoice_TC.setModel(new DefaultComboBoxModel(new String[] { "1 ngày", "1 tuần", "1 tháng", "khác" }));
		cboRoInvoice_DH.setModel(new DefaultComboBoxModel(new String[] { "1 ngày", "1 tuần", "1 tháng", "khác" }));
		cboRoInvoiceMH.setModel(new DefaultComboBoxModel(new String[] { "1 ngày", "1 tuần", "1 tháng", "khác" }));
		cboRoInvoiceCN.setModel(new DefaultComboBoxModel(new String[] { "1 ngày", "1 tuần", "1 tháng", "khác" }));
		cboRoXNKho.setModel(new DefaultComboBoxModel(new String[] { "1 ngày", "1 tuần", "1 tháng", "khác" }));

		jPanel5 = new javax.swing.JPanel();
		jPanel5.setOpaque(false);
		lbDesciption = new ExtendJLabel("Số ngày lọc");
		txtDesInvoice_BH = new JTextField();
		txtDesInvoice_BH.setPreferredSize(new Dimension(200, 100));

		txtDesInvoice_TC = new javax.swing.JTextField();
		txtDesInvoice_DH = new javax.swing.JTextField();
		txtDesInvoiceMH = new javax.swing.JTextField();
		txtDesInvoiceCN = new javax.swing.JTextField();
		txtDesInvoice_BH.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtDesInvoice_TC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtDesInvoice_DH.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtDesInvoiceMH.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtDesInvoiceCN.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtDesXNKho = new javax.swing.JTextField();
		txtDesXNKho.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtDesXNKho.setHorizontalAlignment(JTextField.RIGHT);
		txtDesInvoice_BH.setHorizontalAlignment(JTextField.RIGHT);
		txtDesInvoice_TC.setHorizontalAlignment(JTextField.RIGHT);
		txtDesInvoice_DH.setHorizontalAlignment(JTextField.RIGHT);
		txtDesInvoiceMH.setHorizontalAlignment(JTextField.RIGHT);
		txtDesInvoiceCN.setHorizontalAlignment(JTextField.RIGHT);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel1Layout.createSequentialGroup().addGroup(
		            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                .addGroup(jPanel1Layout.createSequentialGroup().addGap(38, 38, 38).addComponent(lbObject))
		                .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(chbInvoice_BH))
		                .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(chbXNKho))
		                .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(chbInvoice_TC))
		                .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(chbInvoice_DH))
		                .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(chbInvoiceMH))
		                .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(chbInvoiceCN)))));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel1Layout.createSequentialGroup().addComponent(lbObject).addGap(18, 18, 18).addComponent(chbInvoice_BH)
		            .addGap(15, 15, 15).addComponent(chbInvoice_TC).addGap(15, 15, 15).addComponent(chbInvoice_DH)
		            .addGap(15, 15, 15).addComponent(chbInvoiceMH).addGap(15, 15, 15).addComponent(chbInvoiceCN)
		            .addGap(15, 15, 15).addComponent(chbXNKho)));

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout
		    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        javax.swing.GroupLayout.Alignment.TRAILING,
		        jPanel4Layout
		            .createSequentialGroup()
		            .addGap(28, 28, 28)
		            .addGroup(
		                jPanel4Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
		                    .addComponent(cboRoInvoice_BH, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(cboRoInvoice_TC, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(cboRoInvoice_DH, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(cboRoInvoiceMH, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(cboRoInvoiceCN, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(cboRoXNKho, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
		    .addGroup(jPanel4Layout.createSequentialGroup().addGap(80, 80, 80).addComponent(lbRotationType)));

		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel4Layout
		            .createSequentialGroup()
		            .addComponent(lbRotationType)
		            .addGap(18, 18, 18)
		            .addComponent(cboRoInvoice_BH, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(cboRoInvoice_TC, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(cboRoInvoice_DH, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(cboRoInvoiceMH, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(cboRoInvoiceCN, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(cboRoXNKho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		                javax.swing.GroupLayout.PREFERRED_SIZE)));

		javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel5Layout.createSequentialGroup().addGroup(
		            jPanel5Layout
		                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                .addGroup(jPanel5Layout.createSequentialGroup().addGap(115, 115, 115).addComponent(lbDesciption))
		                .addGroup(
		                    jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
		                        .addComponent(txtDesInvoice_BH, javax.swing.GroupLayout.Alignment.LEADING)
		                        .addComponent(txtDesInvoice_TC, javax.swing.GroupLayout.Alignment.LEADING)
		                        .addComponent(txtDesInvoice_DH, javax.swing.GroupLayout.Alignment.LEADING)
		                        .addComponent(txtDesInvoiceMH, javax.swing.GroupLayout.Alignment.LEADING)
		                        .addComponent(txtDesInvoiceCN, javax.swing.GroupLayout.Alignment.LEADING)
		                        .addComponent(txtDesXNKho, javax.swing.GroupLayout.Alignment.LEADING)))));

		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel5Layout
		            .createSequentialGroup()
		            .addComponent(lbDesciption)
		            .addGap(18, 18, 18)
		            .addComponent(txtDesInvoice_BH, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(txtDesInvoice_TC, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(txtDesInvoice_DH, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(txtDesInvoiceMH, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(txtDesInvoiceCN, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(txtDesXNKho, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                javax.swing.GroupLayout.PREFERRED_SIZE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        layout
		            .createSequentialGroup()
		            .addContainerGap()
		            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(42, 42, 42)
		            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 400,
		                javax.swing.GroupLayout.PREFERRED_SIZE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    layout
		        .createSequentialGroup()
		        .addContainerGap()
		        .addGroup(
		            layout
		                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                .addGroup(
		                    layout
		                        .createSequentialGroup()
		                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE,
		                            javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                        .addContainerGap())
		                .addGroup(
		                    layout
		                        .createSequentialGroup()
		                        .addGroup(
		                            layout
		                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE,
		                                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
		                                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap()))));
	}// </editor-fold>

	private void saveObject(JCheckBox chb, JComboBox cboRo, JTextField txtDes) throws Exception {
		Profile p = AccountModelManager.getInstance().getProfileConfigAdmin();
		if (chb.isSelected()) {

			if (cboRo.getSelectedItem().toString().equals("1 ngày")) {
				p.put(hashMap1.get(chb.getText()), 0);
			} else if (cboRo.getSelectedItem().toString().equals("1 tuần")) {
				p.put(hashMap1.get(chb.getText()), 7);

			} else if (cboRo.getSelectedItem().toString().equals("1 tháng")) {
				p.put(hashMap1.get(chb.getText()), 30);
			}else {
				p.put(hashMap1.get(chb.getText()), txtDes.getText());
			}

		} else {
			p.put(hashMap1.get(chb.getText()), 1);
		}
		AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(), p);
	}

	private void setObject(JCheckBox chb,JComboBox cboRotationType,  JTextField txtDescription)
	{
		Profile p = AccountModelManager.getInstance().getProfileConfigAdmin();
		if(p.get(hashMap1.get(chb.getText()))!=null ){
			chb.setSelected(true);
			if(((p.get(hashMap1.get(chb.getText())) instanceof String))){
				cboRotationType.setSelectedIndex(3);
				txtDescription.setText(p.get(hashMap1.get(chb.getText())).toString());
				txtDescription.setEnabled(true);
			}else {
				txtDescription.setEnabled(false);
				if(((p.get(hashMap1.get(chb.getText())).toString().equals("0")))){
					cboRotationType.setSelectedIndex(0);
				}else {
					if(((p.get(hashMap1.get(chb.getText())).toString().equals("7")))){
						cboRotationType.setSelectedIndex(1);
					}else {
						cboRotationType.setSelectedIndex(2);
					}
				}
			}
		}

	}

	private void addAction(JCheckBox chb, final JComboBox cboRo, final JTextField txtDes) throws Exception {
		chb.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				JCheckBox chb = (JCheckBox) e.getSource();
				if (!chb.isSelected()) {
					cboRo.setSelectedIndex(0);
					txtDes.setText("");
					txtDes.setEnabled(false);
				}
			}
		});
	}

	private void addItemL(final JComboBox cboRo, final JTextField txtDes) throws Exception {
		cboRo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				JComboBox chb = (JComboBox) e.getSource();
				if (chb.getSelectedItem().toString().equals("khác")) {
					txtDes.setEnabled(true);
				} else {
					txtDes.setEnabled(false);
				}
			}
		});
	}

	@Override
	public void Save() throws Exception {
		saveObject(chbInvoiceCN, cboRoInvoiceCN, txtDesInvoiceCN);
		saveObject(chbInvoice_DH, cboRoInvoice_DH, txtDesInvoice_DH);
		saveObject(chbInvoice_BH, cboRoInvoice_BH, txtDesInvoice_BH);
		saveObject(chbInvoice_TC, cboRoInvoice_TC, txtDesInvoice_TC);
		saveObject(chbInvoiceMH, cboRoInvoiceMH, txtDesInvoiceMH);
		saveObject(chbXNKho, cboRoXNKho, txtDesXNKho);
		DialogNotice.getInstace().setVisible(true);
	}
}