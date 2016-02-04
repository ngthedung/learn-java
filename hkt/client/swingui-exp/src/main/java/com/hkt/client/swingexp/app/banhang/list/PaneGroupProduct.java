package com.hkt.client.swingexp.app.banhang.list;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;

import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJRadioButton;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DialogList;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.UnitMoneyJComboBox;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Customer;

public class PaneGroupProduct extends JPanel implements IDialogResto {
	private JLabel lbTuNgay, lbDenNgay, lbTinhTrang, lbNhanVien, lbDoiTac, lbHienThi, lbDVTien, lbLoaiSP;
	private JComboBox cbStatus;
	private UnitMoneyJComboBox cbUnitMoney;
	private MyJDateChooser firtDate, endDate;
	private TextPopup<Employee> txtEmployee;
	private TextPopup<Customer> txtCustomer;
	private JRadioButton rdGopSP, rdTachSP, rdMua, rdBan;
	private ButtonGroup buttonGroup1, buttonGroup2;
	private List<Employee> listEmployee;
	private List<Customer> listCustomer;
	private HashMap<String, String> tranfer = new HashMap<String, String>();
	public static String permission; 

	public PaneGroupProduct() {
		addKeyBindings(this);
		listEmployee = new ArrayList<Employee>();
		listCustomer = new ArrayList<Customer>();

		// Khởi tạo giao diện các components
		init();

		// Tạo model dữ liệu comboBox trạng thái (cbStatus)
		try {
			String[] arrayStatus = { "Tất cả", AccountingModelManager.isCance, AccountingModelManager.isKitchen,
			    AccountingModelManager.isPayment, AccountingModelManager.isPromotion, AccountingModelManager.isRecord};
			DefaultComboBoxModel modelStatus = new DefaultComboBoxModel(arrayStatus);
			cbStatus.setModel(modelStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Lấy ra danh sách nhân viên
		try {
			listEmployee = HRModelManager.getInstance().getEmployees();
			txtEmployee.setData(listEmployee);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Lấy ra danh sách đối tác
		try {
			listCustomer = CustomerModelManager.getInstance().getCustomers(false);
			txtCustomer.setData(listCustomer);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void init() {
		setBackground(new java.awt.Color(255, 255, 255));
		setOpaque(false);

		lbTuNgay = new ExtendJLabel("Từ ngày");
		firtDate = new MyJDateChooser();
		lbDenNgay = new ExtendJLabel("Đến ngày");
		endDate = new MyJDateChooser();
		lbTinhTrang = new ExtendJLabel("Tình trạng");
		cbStatus = new ExtendJComboBox();
		lbNhanVien = new ExtendJLabel("Nhân viên");
		txtEmployee = new TextPopup<Employee>(Employee.class);
		lbDoiTac = new ExtendJLabel("Khách hàng");
		txtCustomer = new TextPopup<Customer>(Customer.class);
		lbDVTien = new ExtendJLabel("ĐV tiền");
		cbUnitMoney = new UnitMoneyJComboBox();
		lbHienThi = new ExtendJLabel("Hiển thị");
		lbLoaiSP = new ExtendJLabel("Loại SP");

		rdGopSP = new ExtendJRadioButton("Gộp sản phẩm");
		rdTachSP = new ExtendJRadioButton("Tách sản phẩm");
		rdBan = new ExtendJRadioButton("Bán");
		rdMua = new ExtendJRadioButton("Mua");
		buttonGroup1 = new ButtonGroup();
		buttonGroup2 = new ButtonGroup();
		buttonGroup1.add(rdGopSP);
		buttonGroup1.add(rdTachSP);
		rdGopSP.setSelected(true);

		buttonGroup2.add(rdBan);
		buttonGroup2.add(rdMua);
		rdBan.setSelected(true);

		rdGopSP.setOpaque(false);
		rdTachSP.setOpaque(false);
		rdMua.setOpaque(false);
		rdBan.setOpaque(false);

		txtEmployee.setFont(new java.awt.Font("Tahoma", 0, 14));
		txtCustomer.setFont(new java.awt.Font("Tahoma", 0, 14));
		cbStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
		firtDate.setFont(new java.awt.Font("Tahoma", 0, 12));
		endDate.setFont(new java.awt.Font("Tahoma", 0, 12));

		GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(lbTinhTrang).addComponent(lbNhanVien).addComponent(lbDoiTac).addComponent(lbDVTien).addComponent(lbTuNgay).addComponent(lbHienThi))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup().addComponent(firtDate, javax.swing.GroupLayout.DEFAULT_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(lbDenNgay)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(endDate, javax.swing.GroupLayout.DEFAULT_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addComponent(cbUnitMoney, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(txtCustomer)
												.addComponent(txtEmployee)
												.addComponent(cbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addGroup(
														javax.swing.GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(rdGopSP).addComponent(rdBan))
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addGroup(
																		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(rdMua).addContainerGap())
																				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(rdTachSP).addGap(14, 14, 14))))))
				.addGroup(layout.createSequentialGroup().addComponent(lbLoaiSP).addGap(305, 387, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
//						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(firtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(lbTuNgay).addComponent(lbDenNgay))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lbTinhTrang).addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(txtEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(lbNhanVien))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lbDoiTac).addComponent(txtCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lbDVTien).addComponent(cbUnitMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(lbHienThi)
										.addGroup(layout.createSequentialGroup().addGap(4, 4, 4).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(rdGopSP).addComponent(rdTachSP)))).addGap(14, 14, 14)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lbLoaiSP).addComponent(rdBan).addComponent(rdMua)).addContainerGap()));
	}

	@Override
	public void Save() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateStart = firtDate.getDate();
		Date dateEnd = endDate.getDate();

		Calendar calendarStart = Calendar.getInstance();
		if (dateStart != null) {
			calendarStart.setTime(dateStart);
			calendarStart.set(Calendar.HOUR_OF_DAY, 00);
			calendarStart.set(Calendar.MINUTE, 00);
			calendarStart.set(Calendar.SECOND, 00);
		}
		Calendar calendarEnd = Calendar.getInstance();
		if (dateEnd != null) {
			calendarEnd.setTime(dateEnd);
			calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
			calendarEnd.set(Calendar.MINUTE, 59);
			calendarEnd.set(Calendar.SECOND, 59);
		}

		if (firtDate.getDate() == null)
			tranfer.put("firtDate", "");
		else
			tranfer.put("firtDate", dateFormat.format(calendarStart.getTime()));

		if (firtDate.getDate() == null)
			tranfer.put("endDate", "");
		else
			tranfer.put("endDate", dateFormat.format(calendarEnd.getTime()));

		if (txtEmployee.getItem() != null)
			tranfer.put("employee", txtEmployee.getItem().getLoginId());
		else
			tranfer.put("employee", "");

		if (txtCustomer.getItem() != null)
			tranfer.put("customer", txtCustomer.getItem().getLoginId());
		else
			tranfer.put("customer", "");

		tranfer.put("status", (String) cbStatus.getSelectedItem());
		tranfer.put("moneyUnit", cbUnitMoney.getSelectedCurrency().getCode());

		if (rdBan.isSelected())
			tranfer.put("type", "Receipt");
		else if (rdMua.isSelected())
			tranfer.put("type", "Payment");

		if (rdGopSP.isSelected() && (rdBan.isSelected() || rdMua.isSelected())) {
			TableListGrossProduct tableListGrossProduct = new TableListGrossProduct(tranfer);
			DialogList dialog = new DialogList(tableListGrossProduct);
			dialog.setIcon("dssp1.png");
			dialog.setTitle("Danh sách sản phẩm");
			dialog.setVisible(true);
			((JDialog) getRootPane().getParent()).dispose();
		} else {
			if (rdTachSP.isSelected() && (rdBan.isSelected() || rdMua.isSelected())) {
				TableListSplitProduct tableListSplitProduct = new TableListSplitProduct(tranfer);
				DialogList dialog = new DialogList(tableListSplitProduct);
				dialog.setVisible(true);
				((JDialog) getRootPane().getParent()).dispose();
			} else
				JOptionPane.showConfirmDialog(null, "Vui lòng click vào lựa chọn !");
		}
	}
	
	private void addKeyBindings(JComponent jc) {

	    jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "ESC");
	    jc.getActionMap().put("ESC", new AbstractAction() {
	      @Override
	      public void actionPerformed(ActionEvent ae) {
	       (((JDialog) getRootPane().getParent())).dispose();
	    }
	    });
	}

}
