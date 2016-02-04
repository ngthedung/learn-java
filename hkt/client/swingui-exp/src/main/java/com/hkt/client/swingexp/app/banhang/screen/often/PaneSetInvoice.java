package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.ExtendJCheckBox;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.module.accounting.entity.ManageCodes;
import com.hkt.module.accounting.entity.ManageCodes.ManageType;
import com.hkt.module.accounting.entity.ManageCodes.RotationType;

@SuppressWarnings("serial")
public class PaneSetInvoice extends JPanel implements IDialogResto {
	private JComboBox cboManageInvoice_BH, cboManageInvoice_TC, cboManageInvoice_DH, cboManageCustomer, cboManageProduct,
	    cboManageProject, cboRoInvoice_BH, cboRoInvoice_TC, cboRoInvoice_DH, cboRoCustomer, cboRoProduct, cboRoSupplier,
	    cboRoProject, cboManageSupplier;
	private ExtendJLabel lbObject, lbInvoice_BH, lbInvoice_TC, lbInvoice_DH, lbCustomer, lbProduct, lbProject,
	    lbNameCode, lbManageType, lbRotationType, lbDesciption, lbNote;
	private JPanel jPanel1, jPanel2, jPanel3, jPanel4, jPanel5, jPanel6;
	private JScrollPane jScrollPane1, jScrollPane2, jScrollPane3, jScrollPane4, jScrollPane5, jScrollPane6, jScrollPane7;
	private JTextArea txtDesInvoice_BH, txtDesInvoice_TC, txtDesInvoice_DH, txtDesCustomer, txtDesProduct,
	    txtDesSupplier, txtDesProject;
	private JTextField txtInvoice_BH, txtInvoice_TC, txtInvoice_DH, txtCustomer, txtProduct, txtSupplier, txtProject;
	private ExtendJCheckBox chbInvoice_BH, chbInvoice_TC, chbInvoice_DH, chbCustomer, chbProduct, chbSupplier,
	    chbProject;
	public static String InvoiceBH = "Invoice_BH";
	public static String InvoiceTC = "Invoice_TC";
	public static String InvoiceDMH = "Invoice_DMH";
	public static String KhachHang = "KhachHang";
	public static String NhaCungCap = "NhaCungCap";
	public static String SanPham = "SanPham";
	public static String DuAn = "DuAn";
	private HashMap<String, String> hashMap1 = new HashMap<String, String>();

	public PaneSetInvoice() {
		initComponents();
		setOpaque(false);

		hashMap1.put("Hóa đơn bán hàng", InvoiceBH);
		hashMap1.put("Phiếu thu chi", InvoiceTC);
		hashMap1.put("Phiếu đặt mua hàng", InvoiceDMH);
		hashMap1.put("Khách hàng", KhachHang);
		hashMap1.put("Nhà cung cấp", NhaCungCap);
		hashMap1.put("Sản phẩm", SanPham);
		hashMap1.put("Dự án", DuAn);
		setObject(chbInvoice_BH, cboManageInvoice_BH, cboRoInvoice_BH, txtInvoice_BH, txtDesInvoice_BH);
		setObject(chbCustomer, cboManageCustomer, cboRoCustomer, txtCustomer, txtDesCustomer);
		setObject(chbInvoice_DH, cboManageInvoice_DH, cboRoInvoice_DH, txtInvoice_DH, txtDesInvoice_DH);
		setObject(chbInvoice_TC, cboManageInvoice_TC, cboRoInvoice_TC, txtInvoice_TC, txtDesInvoice_TC);
		setObject(chbProject, cboManageProject, cboRoProject, txtProject, txtDesProject);
		setObject(chbProduct, cboManageProduct, cboRoProduct, txtProduct, txtDesProduct);
		setObject(chbSupplier, cboManageSupplier, cboRoSupplier, txtSupplier, txtDesSupplier);

		try {
			addAction(chbInvoice_BH, cboManageInvoice_BH, cboRoInvoice_BH, txtInvoice_BH, txtDesInvoice_BH, false);
			addAction(chbCustomer, cboManageCustomer, cboRoCustomer, txtCustomer, txtDesCustomer, true);
			addAction(chbInvoice_DH, cboManageInvoice_DH, cboRoInvoice_DH, txtInvoice_DH, txtDesInvoice_DH, false);
			addAction(chbInvoice_TC, cboManageInvoice_TC, cboRoInvoice_TC, txtInvoice_TC, txtDesInvoice_TC, false);
			addAction(chbProject, cboManageProject, cboRoProject, txtProject, txtDesProject, false);
			addAction(chbProduct, cboManageProduct, cboRoProduct, txtProduct, txtDesProduct, true);
			addAction(chbSupplier, cboManageSupplier, cboRoSupplier, txtSupplier, txtDesSupplier, true);
		} catch (Exception e) {
		}

	}

	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jPanel1.setOpaque(false);
		lbObject = new ExtendJLabel("Đối tượng");
		lbInvoice_BH = new ExtendJLabel("Hóa đơn bán hàng");
		lbInvoice_TC = new ExtendJLabel("Phiếu thu chi");
		lbInvoice_DH = new ExtendJLabel("Phiếu đặt mua hàng");
		lbCustomer = new ExtendJLabel("Khách hàng");
		lbProduct = new ExtendJLabel("Sản phẩm");
		lbProject = new ExtendJLabel("Dự án");

		chbInvoice_BH = new ExtendJCheckBox("Hóa đơn bán hàng");
		chbInvoice_TC = new ExtendJCheckBox("Phiếu thu chi");
		chbInvoice_DH = new ExtendJCheckBox("Phiếu đặt mua hàng");
		chbCustomer = new ExtendJCheckBox("Khách hàng");
		chbProduct = new ExtendJCheckBox("Sản phẩm");
		chbProject = new ExtendJCheckBox("Dự án");
		chbSupplier = new ExtendJCheckBox("Nhà cung cấp");

		jPanel2 = new javax.swing.JPanel();
		jPanel2.setOpaque(false);
		lbNameCode = new ExtendJLabel("Kiểu đặt mã");
		txtInvoice_BH = new ExtendJTextField();
		txtInvoice_TC = new ExtendJTextField();
		txtInvoice_DH = new ExtendJTextField();
		txtCustomer = new ExtendJTextField();
		txtProduct = new ExtendJTextField();
		txtProject = new ExtendJTextField();
		txtSupplier = new ExtendJTextField();
		lbNote = new ExtendJLabel();

		Date date = new Date();
		SimpleDateFormat df1 = new SimpleDateFormat("ddMMyyyy");
		String d1 = df1.format(date);
		SimpleDateFormat df2 = new SimpleDateFormat("ddMM");
		String d2 = df2.format(date);
		SimpleDateFormat df3 = new SimpleDateFormat("MMyyyy");
		String d3 = df3.format(date);
		SimpleDateFormat df4 = new SimpleDateFormat("yyyyMMdd");
		String d4 = df4.format(date);
		SimpleDateFormat df5 = new SimpleDateFormat("MMdd");
		String d5 = df5.format(date);
		SimpleDateFormat df6 = new SimpleDateFormat("yyyyMM");
		String d6 = df6.format(date);

		String str = "Ví dụ kiểu đặt mã ddMMyyyy: " + d1 + "; " + "ddMM: " + d2 + "; " + "MMyyyy: " + d3 + "; "
		    + "yyyyMMdd: " + d4 + "; " + "MMdd: " + d5 + "; " + "yyyyMM: " + d6 + ".";
		lbNote.setText(str);

		jPanel3 = new javax.swing.JPanel();
		jPanel3.setOpaque(false);
		lbManageType = new ExtendJLabel("Loại quản lý");

		cboManageInvoice_BH = new ExtendJComboBox();
		cboManageInvoice_TC = new ExtendJComboBox();
		cboManageInvoice_DH = new ExtendJComboBox();
		cboManageCustomer = new ExtendJComboBox();
		cboManageProduct = new ExtendJComboBox();
		cboManageProject = new ExtendJComboBox();
		cboManageSupplier = new ExtendJComboBox();

		cboManageInvoice_BH.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cboManageInvoice_BH.getSelectedItem().equals("Quay vòng")) {
					cboRoInvoice_BH.setEnabled(true);
					cboRoInvoice_BH.setSelectedIndex(1);
				} else {
					cboRoInvoice_BH.setEnabled(false);
					cboRoInvoice_BH.setSelectedIndex(0);
				}

			}
		});

		cboManageSupplier.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cboManageSupplier.getSelectedItem().equals("Quay vòng")) {
					cboRoSupplier.setEnabled(true);
					cboRoSupplier.setSelectedIndex(1);
				} else {
					cboRoSupplier.setEnabled(false);
					cboRoSupplier.setSelectedIndex(0);
				}

			}
		});

		cboManageInvoice_TC.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cboManageInvoice_TC.getSelectedItem().equals("Quay vòng")) {
					cboRoInvoice_TC.setEnabled(true);
					cboRoInvoice_TC.setSelectedIndex(1);
				} else {
					cboRoInvoice_TC.setEnabled(false);
					cboRoInvoice_TC.setSelectedIndex(0);
				}

			}
		});

		cboManageInvoice_DH.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cboManageInvoice_DH.getSelectedItem().equals("Quay vòng")) {
					cboRoInvoice_DH.setEnabled(true);
					cboRoInvoice_DH.setSelectedIndex(1);
				} else {
					cboRoInvoice_DH.setEnabled(false);
					cboRoInvoice_DH.setSelectedIndex(0);
				}

			}
		});

		cboManageCustomer.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cboManageCustomer.getSelectedItem().equals("Quay vòng")) {
					cboRoCustomer.setEnabled(true);
					cboRoCustomer.setSelectedIndex(1);
				} else {
					cboRoCustomer.setEnabled(false);
					cboRoCustomer.setSelectedIndex(0);
				}

			}
		});

		cboManageProduct.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cboManageProduct.getSelectedItem().equals("Quay vòng")) {
					cboRoProduct.setEnabled(true);
					cboRoProduct.setSelectedIndex(1);
				} else {
					cboRoProduct.setEnabled(false);
					cboRoProduct.setSelectedIndex(0);
				}

			}
		});

		cboManageProject.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cboManageProject.getSelectedItem().equals("Quay vòng")) {
					cboRoProject.setEnabled(true);
					cboRoProject.setSelectedIndex(1);
				} else {
					cboRoProject.setEnabled(false);
					cboRoProject.setSelectedIndex(0);
				}

			}
		});

		cboManageInvoice_BH.setModel(new DefaultComboBoxModel(new String[] { "Quay vòng", "Tăng dần đều" }));
		cboManageInvoice_TC.setModel(new DefaultComboBoxModel(new String[] { "Quay vòng", "Tăng dần đều" }));
		cboManageInvoice_DH.setModel(new DefaultComboBoxModel(new String[] { "Quay vòng", "Tăng dần đều" }));
		cboManageCustomer.setModel(new DefaultComboBoxModel(new String[] { "Quay vòng", "Tăng dần đều" }));
		cboManageProduct.setModel(new DefaultComboBoxModel(new String[] { "Quay vòng", "Tăng dần đều" }));
		cboManageProject.setModel(new DefaultComboBoxModel(new String[] { "Quay vòng", "Tăng dần đều" }));
		cboManageSupplier.setModel(new DefaultComboBoxModel(new String[] { "Quay vòng", "Tăng dần đều" }));

		jPanel4 = new javax.swing.JPanel();
		jPanel4.setOpaque(false);
		lbRotationType = new ExtendJLabel("Loại quay vòng");
		cboRoInvoice_BH = new ExtendJComboBox();
		cboRoInvoice_TC = new ExtendJComboBox();
		cboRoInvoice_DH = new ExtendJComboBox();
		cboRoCustomer = new ExtendJComboBox();
		cboRoProduct = new ExtendJComboBox();
		cboRoProject = new ExtendJComboBox();
		cboRoSupplier = new ExtendJComboBox();

		cboRoInvoice_BH.setModel(new DefaultComboBoxModel(new String[] { "", "Theo ngày", "Theo tháng", "Theo năm" }));
		cboRoInvoice_BH.setSelectedIndex(1);
		cboRoInvoice_TC.setModel(new DefaultComboBoxModel(new String[] { "", "Theo ngày", "Theo tháng", "Theo năm" }));
		cboRoInvoice_TC.setSelectedIndex(1);
		cboRoInvoice_DH.setModel(new DefaultComboBoxModel(new String[] { "", "Theo ngày", "Theo tháng", "Theo năm" }));
		cboRoInvoice_DH.setSelectedIndex(1);
		cboRoCustomer.setModel(new DefaultComboBoxModel(new String[] { "", "Theo ngày", "Theo tháng", "Theo năm" }));
		cboRoCustomer.setSelectedIndex(1);
		cboRoProduct.setModel(new DefaultComboBoxModel(new String[] { "", "Theo ngày", "Theo tháng", "Theo năm" }));
		cboRoProduct.setSelectedIndex(1);
		cboRoProject.setModel(new DefaultComboBoxModel(new String[] { "", "Theo ngày", "Theo tháng", "Theo năm" }));
		cboRoProject.setSelectedIndex(1);
		cboRoSupplier.setModel(new DefaultComboBoxModel(new String[] { "", "Theo ngày", "Theo tháng", "Theo năm" }));
		cboRoSupplier.setSelectedIndex(1);

		jPanel5 = new javax.swing.JPanel();
		jPanel5.setOpaque(false);
		jPanel6 = new javax.swing.JPanel();
		// jPanel5.setOpaque(false);
		lbDesciption = new ExtendJLabel("Miêu tả");
		jScrollPane1 = new javax.swing.JScrollPane();
		txtDesInvoice_BH = new JTextArea();
		jScrollPane1.setPreferredSize(new Dimension(330, 100));

		jScrollPane2 = new javax.swing.JScrollPane();
		txtDesInvoice_TC = new javax.swing.JTextArea();
		jScrollPane3 = new javax.swing.JScrollPane();
		txtDesInvoice_DH = new javax.swing.JTextArea();
		jScrollPane4 = new javax.swing.JScrollPane();
		txtDesCustomer = new javax.swing.JTextArea();
		jScrollPane5 = new javax.swing.JScrollPane();
		txtDesProduct = new javax.swing.JTextArea();
		jScrollPane6 = new javax.swing.JScrollPane();
		txtDesProject = new javax.swing.JTextArea();
		jScrollPane7 = new javax.swing.JScrollPane();
		txtDesSupplier = new javax.swing.JTextArea();

		jScrollPane1.setViewportView(txtDesInvoice_BH);
		jScrollPane2.setViewportView(txtDesInvoice_TC);
		jScrollPane3.setViewportView(txtDesInvoice_DH);
		jScrollPane4.setViewportView(txtDesCustomer);
		jScrollPane5.setViewportView(txtDesProduct);
		jScrollPane6.setViewportView(txtDesProject);
		jScrollPane7.setViewportView(txtDesSupplier);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel1Layout.createSequentialGroup().addGroup(
		            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                .addGroup(jPanel1Layout.createSequentialGroup().addGap(38, 38, 38).addComponent(lbObject))
		                .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(chbInvoice_BH))
		                .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(chbProject))
		                .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(chbInvoice_TC))
		                .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(chbInvoice_DH))
		                .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(chbCustomer))
		                .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(chbSupplier))
		                .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(chbProduct)))));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel1Layout.createSequentialGroup().addComponent(lbObject).addGap(18, 18, 18).addComponent(chbInvoice_BH)
		            .addGap(15, 15, 15).addComponent(chbInvoice_TC).addGap(15, 15, 15).addComponent(chbInvoice_DH)
		            .addGap(15, 15, 15).addComponent(chbCustomer).addGap(15, 15, 15).addComponent(chbSupplier)
		            .addGap(15, 15, 15).addComponent(chbProduct).addGap(15, 15, 15).addComponent(chbProject)));

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout
		    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(jPanel2Layout.createSequentialGroup().addGap(28, 28, 28).addComponent(lbNameCode))
		    .addComponent(txtInvoice_BH, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
		    .addComponent(txtInvoice_TC, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
		    .addComponent(txtInvoice_DH, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
		    .addComponent(txtCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
		    .addComponent(txtSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
		    .addComponent(txtProduct, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE,
		        173, Short.MAX_VALUE).addComponent(txtProject, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel2Layout
		            .createSequentialGroup()
		            .addComponent(lbNameCode)
		            .addGap(18, 18, 18)
		            .addComponent(txtInvoice_BH, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(txtInvoice_TC, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(txtInvoice_DH, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(txtCustomer, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(txtSupplier, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(txtProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(txtProject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		                javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(52, Short.MAX_VALUE)));

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout
		    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel3Layout
		            .createSequentialGroup()
		            .addGap(18, 18, 18)
		            .addGroup(
		                jPanel3Layout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
		                    .addComponent(cboManageInvoice_BH, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(cboManageInvoice_TC, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(cboManageInvoice_DH, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(cboManageCustomer, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(cboManageSupplier, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(cboManageProduct, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(cboManageProject, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
		    .addGroup(jPanel3Layout.createSequentialGroup().addGap(70, 70, 70).addComponent(lbManageType)));

		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel3Layout
		            .createSequentialGroup()
		            .addComponent(lbManageType)
		            .addGap(18, 18, 18)
		            .addComponent(cboManageInvoice_BH, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(cboManageInvoice_TC, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(cboManageInvoice_DH, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(cboManageCustomer, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(cboManageSupplier, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(cboManageProduct, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(cboManageProject, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));

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
		                    .addComponent(cboRoCustomer, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(cboRoSupplier, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(cboRoProduct, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(cboRoProject, javax.swing.GroupLayout.PREFERRED_SIZE,
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
		            .addComponent(cboRoCustomer, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(cboRoSupplier, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(cboRoProduct, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(cboRoProject, javax.swing.GroupLayout.PREFERRED_SIZE,
		                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));

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
		                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
		                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
		                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
		                        .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
		                        .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING)
		                        .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING)
		                        .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.LEADING)))));

		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        jPanel5Layout
		            .createSequentialGroup()
		            .addComponent(lbDesciption)
		            .addGap(18, 18, 18)
		            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(18, 18, 18)
		            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addGap(18, 18, 18)
		            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                javax.swing.GroupLayout.PREFERRED_SIZE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    layout.createSequentialGroup().addGroup(
		        layout
		            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		            .addGroup(
		                layout
		                    .createSequentialGroup()
		                    .addContainerGap()
		                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addGap(18, 18, 18)
		                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addGap(18, 18, 18)
		                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addGap(18, 18, 18)
		                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE,
		                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addGap(42, 42, 42)
		                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 400,
		                        javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addGroup(
		                layout
		                    .createSequentialGroup()
		                    .addGap(25, 25, 25)
		                    .addComponent(lbNote, javax.swing.GroupLayout.PREFERRED_SIZE, 1202,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)))));
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
		                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
		                                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
		                                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
		                                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap()))
		        // .addGap(10, 10, 10)
		        .addComponent(lbNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		            javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(271, Short.MAX_VALUE)));
	}// </editor-fold>

	private void saveObject(JCheckBox chb, JComboBox cbo, JComboBox cboRo, JTextField txt, JTextArea txtDes)
	    throws Exception {
		if (chb.isSelected()) {
			ManageCodes codes = AccountingModelManager.getInstance().getCodesByCode(hashMap1.get(chb.getText()));
			if (codes == null) {
				codes = new ManageCodes();
			} else {
				codes.setRecycleBin(false);
			}

			codes.setCode(hashMap1.get(chb.getText()));
			codes.setTypeCode(txt.getText());
			if (cbo.getSelectedItem() != null) {

				if (cbo.getSelectedItem().toString().equals("Quay vòng")) {
					codes.setManageType(ManageType.Circle);
					if (cboRo.getSelectedItem().toString().equals("Theo ngày")) {

						codes.setRotationType(RotationType.ByDay);
					} else if (cboRo.getSelectedItem().toString().equals("Theo tháng")) {
						codes.setRotationType(RotationType.ByMonth);

					} else if (cboRo.getSelectedItem().toString().equals("Theo năm")) {
						codes.setRotationType(RotationType.ByYear);
					}

				} else {
					codes.setManageType(ManageType.Increase);
				}
			}

			codes.setDescription(txtDes.getText());

			if (codes != null) {
				codes = AccountingModelManager.getInstance().saveCodes(codes);
			}
		} else {
			ManageCodes codes = AccountingModelManager.getInstance().getCodesByCode(hashMap1.get(chb.getText()));
			if (codes != null) {
				AccountingModelManager.getInstance().deleteCodes(codes);
			}
		}
	}

	private void setObject(JCheckBox chb, JComboBox cboManageType, JComboBox cboRotationType, JTextField txtName,
	    JTextArea txtDescription) {
		ManageCodes codes = AccountingModelManager.getInstance().getCodesByCode(hashMap1.get(chb.getText()));
		if (codes != null) {
			chb.setSelected(true);
			txtName.setText(codes.getTypeCode());
			txtDescription.setText(codes.getDescription());
			if (cboManageType.getSelectedItem() != null) {

				if (codes.getManageType().equals(ManageType.Circle)) {
					cboManageType.setSelectedIndex(0);

				} else {
					cboManageType.setSelectedIndex(1);

				}
			}

			if (cboRotationType.isEnabled() == true) {
				if (cboRotationType.getSelectedItem() != null) {
					if (codes.getRotationType().equals(RotationType.ByDay)) {
						cboRotationType.setSelectedIndex(1);
					} else if (codes.getRotationType().equals(RotationType.ByMonth)) {
						cboRotationType.setSelectedIndex(2);
					} else if (codes.getRotationType().equals(RotationType.ByYear)) {
						cboRotationType.setSelectedIndex(3);
					}
				}
			}
		} else {
			chb.setSelected(false);
		}

	}

	private void addAction(JCheckBox chb, final JComboBox cbo, final JComboBox cboRo, final JTextField txt,
	    final JTextArea txtDes, final boolean isRo) throws Exception {
		chb.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				JCheckBox chb = (JCheckBox) e.getSource();
				if (!chb.isSelected()) {
					cbo.setSelectedIndex(0);
					cboRo.setSelectedIndex(1);
					txt.setText("");
					txtDes.setText("");
				}else {
					if(isRo){
						cbo.setSelectedIndex(1);
					}
				}
			}
		});
	}

	@Override
	public void Save() throws Exception {
		saveObject(chbInvoice_BH, cboManageInvoice_BH, cboRoInvoice_BH, txtInvoice_BH, txtDesInvoice_BH);
		saveObject(chbCustomer, cboManageCustomer, cboRoCustomer, txtCustomer, txtDesCustomer);
		saveObject(chbInvoice_DH, cboManageInvoice_DH, cboRoInvoice_DH, txtInvoice_DH, txtDesInvoice_DH);
		saveObject(chbInvoice_TC, cboManageInvoice_TC, cboRoInvoice_TC, txtInvoice_TC, txtDesInvoice_TC);
		saveObject(chbProject, cboManageProject, cboRoProject, txtProject, txtDesProject);
		saveObject(chbProduct, cboManageProduct, cboRoProduct, txtProduct, txtDesProduct);
		saveObject(chbSupplier, cboManageSupplier, cboRoSupplier, txtSupplier, txtDesSupplier);
		DialogNotice.getInstace().setVisible(true);
	}
}