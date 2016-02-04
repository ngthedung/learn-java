package com.hkt.client.swingexp.app.banhang;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

import com.hkt.client.swingexp.app.banhang.list.TableInvoicePayment;
import com.hkt.client.swingexp.app.banhang.list.TableInvoicePaymentItem;
import com.hkt.client.swingexp.app.banhang.list.TableInvoiceTransaction;
import com.hkt.client.swingexp.app.component.ComboPopup;
import com.hkt.client.swingexp.app.component.ExtendJCheckBox;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.core.DialogList;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.HRJComboBox;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.hethong.PanelCurrency;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.GenericOptionModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.config.generic.Option;

@SuppressWarnings("serial")
public class PanelSearchInvoice extends JPanel implements IDialogResto {
	private JLabel lbStartDate, lbEndDate, lbEmployee, lbUnitMoney, lbLH, lbType;
	private MyJDateChooser txtStartDate, txtEndDate;
	@SuppressWarnings("rawtypes")
	private JComboBox cbUnitMoney, cboLH;
	private ComboPopup<Option> cboType;
	private HRJComboBox cbEmployee;
	private JCheckBox checkbPayment, checkbReceipt, checkbInvoice;
	private boolean save;
	public static String permission;

	public boolean isSave() {
		return save;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PanelSearchInvoice() {

		this.setBackground(new Color(255, 255, 255));

		lbStartDate = new ExtendJLabel("Từ ngày");
		lbEndDate = new ExtendJLabel("Đến ngày");
		lbEmployee = new ExtendJLabel("Nhân viên");
		lbUnitMoney = new ExtendJLabel("ĐV tiền");
		lbLH = new ExtendJLabel("Loại hình");
		lbType = new ExtendJLabel("Loại HĐ");
		lbType.setMinimumSize(new Dimension(69, 23));
		cboLH = new ExtendJComboBox();
		cboType = new ComboPopup();
		cboType.setFont(new Font("Tahoma", 0, 14));
		cboType.setOpaque(false);
		cboType.setSize(new Dimension(200, 23));
		cboType.setPreferredSize(new Dimension(200, 23));
		cboType.setMinimumSize(new Dimension(200, 23));
		List<Option> options = new ArrayList<Option>();
		try {
			options = GenericOptionModelManager.getInstance()
			    .getOptionGroup("accounting", "AccountingService", "type_invoice").getOptions();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Option o = new Option();
		o.setLabel("Tất cả");
		options.add(0, o);
		cboType.setData(options);
		txtStartDate = new MyJDateChooser();
		txtStartDate.setDateFormatString("dd/MM/yyyy");
		txtStartDate.setDate(new Date());
		txtEndDate = new MyJDateChooser();
		txtEndDate.setDateFormatString("dd/MM/yyyy");
		txtEndDate.setDate(new Date());

		cbEmployee = new HRJComboBox();
		cbUnitMoney = new ExtendJComboBox();

		checkbPayment = new ExtendJCheckBox("Thu");
		checkbPayment.setSelected(true);
		checkbPayment.setOpaque(false);

		checkbReceipt = new ExtendJCheckBox("Chi");
		checkbReceipt.setSelected(true);
		checkbReceipt.setOpaque(false);
		try {
			cbUnitMoney.setModel(new DefaultComboBoxModel(LocaleModelManager.getInstance().getCurrencies().toArray()));
		} catch (Exception e) {
			cbUnitMoney.setModel(new DefaultComboBoxModel());
		}
		String[] str = { "Tiền mặt", "Thẻ", "Tiếp khách" };
		cboLH.setModel(new DefaultComboBoxModel(str));

		checkbInvoice = new ExtendJCheckBox("Hóa đơn bán hàng");
		checkbInvoice.setSelected(true);
		checkbInvoice.setOpaque(false);

		getContentPanel();
		setSize(500, 300);

		cbUnitMoney.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					PanelCurrency pnCurrencyr;
					try {
						pnCurrencyr = new PanelCurrency();
						pnCurrencyr.setName("TienTe");
						DialogMain dialog = new DialogMain(pnCurrencyr);
						dialog.setIcon("tien1.png");
						dialog.setName("dlTienTe");
						dialog.setTitle("Tiền tệ");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);
						try {
							cbUnitMoney
							    .setModel(new DefaultComboBoxModel(LocaleModelManager.getInstance().getCurrencies().toArray()));
						} catch (Exception e1) {
							cbUnitMoney.setModel(new DefaultComboBoxModel());
						}
					} catch (Exception e1) {

						e1.printStackTrace();
					}
				}
			}
		});
	}

	private void getContentPanel() {
		setOpaque(false);
		GroupLayout panelBackgroundRestoLayout = new GroupLayout(this);
		this.setLayout(panelBackgroundRestoLayout);
		panelBackgroundRestoLayout.setHorizontalGroup(panelBackgroundRestoLayout.createParallelGroup(
		    GroupLayout.Alignment.LEADING).addGroup(
		    GroupLayout.Alignment.TRAILING,
		    panelBackgroundRestoLayout
		        .createSequentialGroup()
		        .addGroup(
		            panelBackgroundRestoLayout
		                .createParallelGroup(GroupLayout.Alignment.LEADING)
		                .addGroup(
		                    panelBackgroundRestoLayout
		                        .createSequentialGroup()
		                        .addComponent(lbStartDate, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
		                        .addGap(9, 9, 9)
		                        .addComponent(txtStartDate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
		                            Short.MAX_VALUE)
		                        .addGap(9, 9, 9)
		                        .addComponent(lbEndDate, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
		                        .addGap(9, 9, 9)
		                        .addComponent(txtEndDate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
		                            Short.MAX_VALUE))
		                .addGroup(
		                    panelBackgroundRestoLayout.createSequentialGroup().addComponent(lbEmployee).addGap(4, 4, 4)
		                        .addComponent(cbEmployee, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		                .addGroup(
		                    panelBackgroundRestoLayout.createSequentialGroup().addComponent(lbType).addGap(4, 4, 4)
		                        .addComponent(cboType, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		                .addGroup(
		                    panelBackgroundRestoLayout
		                        .createSequentialGroup()
		                        .addComponent(lbUnitMoney, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
		                        .addGap(4, 4, 4)
		                        .addGroup(
		                            panelBackgroundRestoLayout
		                                .createParallelGroup(GroupLayout.Alignment.LEADING)
		                                .addComponent(cbUnitMoney, GroupLayout.Alignment.TRAILING, 0,
		                                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                                .addGroup(
		                                    GroupLayout.Alignment.TRAILING,
		                                    panelBackgroundRestoLayout.createSequentialGroup().addGroup(
		                                        panelBackgroundRestoLayout.createParallelGroup(
		                                            GroupLayout.Alignment.TRAILING).addGroup(
		                                            GroupLayout.Alignment.LEADING,
		                                            panelBackgroundRestoLayout
		                                                .createSequentialGroup()
		                                                .addComponent(checkbPayment, GroupLayout.PREFERRED_SIZE, 160,
		                                                    GroupLayout.PREFERRED_SIZE)
		                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
		                                                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                                                .addComponent(checkbReceipt, GroupLayout.PREFERRED_SIZE, 130,
		                                                    GroupLayout.PREFERRED_SIZE))))))).addGap(9, 9, 9)));
		panelBackgroundRestoLayout.setVerticalGroup(panelBackgroundRestoLayout.createParallelGroup(
		    GroupLayout.Alignment.LEADING).addGroup(
		    panelBackgroundRestoLayout
		        .createSequentialGroup()
		        // .addGap(9, 9, 9)
		        .addGroup(
		            panelBackgroundRestoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                .addComponent(lbStartDate, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
		                .addComponent(txtStartDate, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
		                .addComponent(lbEndDate, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
		                .addComponent(txtEndDate, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
		        .addGap(9, 9, 9)
		        .addGroup(
		            panelBackgroundRestoLayout
		                .createParallelGroup(GroupLayout.Alignment.LEADING)
		                .addComponent(lbEmployee, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
		                .addGroup(
		                    panelBackgroundRestoLayout
		                        .createSequentialGroup()
		                        .addGap(1, 1, 1)
		                        .addComponent(cbEmployee, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
		                            GroupLayout.PREFERRED_SIZE)))
		        .addGap(9, 9, 9)
		        .addGroup(
		            panelBackgroundRestoLayout
		                .createParallelGroup(GroupLayout.Alignment.LEADING)
		                .addComponent(lbType, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
		                .addGroup(
		                    panelBackgroundRestoLayout
		                        .createSequentialGroup()
		                        .addGap(1, 1, 1)
		                        .addComponent(cboType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
		                            GroupLayout.PREFERRED_SIZE)))
		        .addGap(9, 9, 9)
		        .addGroup(
		            panelBackgroundRestoLayout
		                .createParallelGroup(GroupLayout.Alignment.LEADING)
		                .addComponent(lbUnitMoney, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
		                .addGroup(
		                    panelBackgroundRestoLayout
		                        .createSequentialGroup()
		                        .addGap(1, 1, 1)
		                        .addComponent(cbUnitMoney, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
		                            GroupLayout.PREFERRED_SIZE)))
		        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		        .addGroup(
		            panelBackgroundRestoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		                .addComponent(checkbPayment).addComponent(checkbReceipt)).addContainerGap()));
	}

	public void setPermissionEmployee(String name) {
		if (name.equals("Quản lý hóa đơn")) {
			lbUnitMoney.setText("Loại TT");
			String[] str = { "", "Tiền mặt", "Thẻ", "Tiếp khách" };
			cbUnitMoney.setModel(new DefaultComboBoxModel(str));
			for (int i = 0; i < cboType.getData().size(); i++) {
				if (cboType.getData().get(i).getCode().equals(AccountingModelManager.typeBanHang)) {
					String[] items = { cboType.getData().get(i).toString() };
					cboType.setModel(new DefaultComboBoxModel(items));
					break;
				}
			}
		}
		if (name.equals("Q.Lý đơn đặt hàng")) {
			for (int i = 0; i < cboType.getData().size(); i++) {
				if (cboType.getData().get(i).getCode().equals(AccountingModelManager.typeDatHang)) {
					String[] items = { cboType.getData().get(i).toString() };
					cboType.setModel(new DefaultComboBoxModel(items));
					break;
				}
			}
			cboType.setEnabled(false);
		}

		if (name.equals("DS thu chi tiền")) {
			for (int i = 0; i < cboType.getData().size(); i++) {
				if (cboType.getData().get(i).getCode().equals(AccountingModelManager.typeThuChi)) {
					String[] items = { cboType.getData().get(i).toString() };
					cboType.setModel(new DefaultComboBoxModel(items));
					break;
				}
			}
		}

		if (name.equals("Quản lý trả hàng")) {
			for (int i = 0; i < cboType.getData().size(); i++) {
				if (cboType.getData().get(i).getCode().equals(AccountingModelManager.typeTraHang)) {
					String[] items = { cboType.getData().get(i).toString() };
					cboType.setModel(new DefaultComboBoxModel(items));
					break;
				}
			}
			cboType.setEnabled(false);
		}
		Profile p = AccountModelManager.getInstance().getProfileConfigAdmin();
		String str = ManagerAuthenticate.getInstance().getLoginId() + name;
		if (p.get(str) != null && p.get(str).equals("true")) {
			for (int i = 0; i < cbEmployee.getItemCount(); i++) {
				if (cbEmployee.getItemAt(i) != null
				    && cbEmployee.getItemAt(i).getLoginId().equals(ManagerAuthenticate.getInstance().getLoginId())) {
					cbEmployee.setSelectedIndex(i);
					cbEmployee.setEnabled(false);
					break;
				}
			}
		} else {
			try {
				String str1 = HRModelManager.getInstance().getBydLoginId(ManagerAuthenticate.getInstance().getLoginId())
				    .getPermissionGroup()
				    + name;
				if (p.get(str1) != null && p.get(str1).equals("true")) {
					for (int i = 0; i < cbEmployee.getItemCount(); i++) {
						if (cbEmployee.getItemAt(i) != null
						    && cbEmployee.getItemAt(i).getLoginId().equals(ManagerAuthenticate.getInstance().getLoginId())) {
							cbEmployee.setSelectedIndex(i);
							cbEmployee.setEnabled(false);
							break;
						}
					}
				}
			} catch (Exception e) {
			}

		}
	}

	@Override
	public void Save() throws Exception {
		save = true;
		((JDialog) getRootPane().getParent()).dispose();
	}

	public void createTablePayment() {
		ActivityType activityType = null;
		if (checkbPayment.isSelected()) {
			if (!checkbReceipt.isSelected()) {
				activityType = ActivityType.Receipt;
			}
		} else {
			if (checkbReceipt.isSelected()) {
				activityType = ActivityType.Payment;
			}
		}

		List<String> types = new ArrayList<String>();
		for (Option o : cboType.getListCheck()) {
			types.add(o.getCode());
		}
		final TableInvoicePayment pninvoice = new TableInvoicePayment(txtStartDate.getDate(), txtEndDate.getDate(),
		    cbEmployee.getSelectedEmployee(), cbUnitMoney.getSelectedItem().toString(), activityType, types,
		    "Hóa đơn bán hàng");
		pninvoice.setName("tbinvoice3");
		DialogList dialog = new DialogList(pninvoice);
		dialog.setTitle("Danh sách hóa đơn");
		dialog.setComponent1(pninvoice.getBtnPayment());
		cboType.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {

			}

			@Override
			public void focusGained(FocusEvent e) {
				List<String> types = new ArrayList<String>();
				for (Option o : cboType.getListCheck()) {
					types.add(o.getCode());
				}
				if (!types.equals(pninvoice.getTypes())) {
					pninvoice.setTypes(types);
					pninvoice.loadDataByTime(pninvoice.getDateStart(), pninvoice.getDateEnd());
				}
			}
		});
		dialog.setIcon("invoiceds1.png");
		dialog.setComponent4(cboType, "Loại");
		dialog.setName("dlinvoice3");
		dialog.setVisible(true);
	}

	public void createTablePaymentItem() {
		ActivityType activityType = null;
		if (checkbPayment.isSelected()) {
			if (!checkbReceipt.isSelected()) {
				activityType = ActivityType.Receipt;
			}
		} else {
			if (checkbReceipt.isSelected()) {
				activityType = ActivityType.Payment;
			}
		}

		List<String> types = new ArrayList<String>();
		for (Option o : cboType.getListCheck()) {
			types.add(o.getCode());
		}
		final TableInvoicePaymentItem pninvoice = new TableInvoicePaymentItem(txtStartDate.getDate(), txtEndDate.getDate(),
		    cbEmployee.getSelectedEmployee(), cbUnitMoney.getSelectedItem().toString(), activityType, types,
		    "Hóa đơn bán hàng");
		pninvoice.setName("tbinvoice3");
		DialogList dialog = new DialogList(pninvoice);
		dialog.setTitle("Danh sách hóa đơn");
		dialog.setComponent1(pninvoice.getBtnPayment());
		cboType.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {

			}

			@Override
			public void focusGained(FocusEvent e) {
				List<String> types = new ArrayList<String>();
				for (Option o : cboType.getListCheck()) {
					types.add(o.getCode());
				}
				if (!types.equals(pninvoice.getTypes())) {
					pninvoice.setTypes(types);
					pninvoice.loadDataByTime(pninvoice.getDateStart(), pninvoice.getDateEnd());
				}
			}
		});
		dialog.setIcon("invoiceds1.png");
		dialog.setComponent4(cboType, "Loại");
		dialog.setName("dlinvoice3");
		dialog.setVisible(true);
	}

	public void loadDate(String str) {
		Profile p = AccountModelManager.getInstance().getProfileConfigAdmin();
		if (p.get(str) != null) {
			Calendar c = Calendar.getInstance();
			int i = 0;
			try {
				i = Integer.parseInt(p.get(str).toString()) * -1;
			} catch (Exception e) {
				i = 0;
			}
			c.add(Calendar.DAY_OF_MONTH, i);
			txtStartDate.setDate(c.getTime());
		}
	}

	public void createTableOrder(String type) {
		ActivityType activityType = null;
		if (checkbPayment.isSelected()) {
			if (!checkbReceipt.isSelected()) {
				activityType = ActivityType.Receipt;
			}
		} else {
			if (checkbReceipt.isSelected()) {
				activityType = ActivityType.Payment;
			}
		}
		List<String> types = new ArrayList<String>();
		types.add(type);
		TableInvoicePayment table = new TableInvoicePayment(txtStartDate.getDate(), txtEndDate.getDate(),
		    cbEmployee.getSelectedEmployee(), cbUnitMoney.getSelectedItem().toString(), activityType, types,
		    "Đơn đặt trả hàng");
		table.setName("tbQuanLyDonDatHang");
		DialogList dialog = new DialogList(table);
		dialog.setIcon("dsorder1.png");
		dialog.setComponent4(table.getPanelCheckBox(), "Lọc");
		dialog.setComponent1(table.getBtnPayment());
		table.loadDataByTime(txtStartDate.getDate(), txtEndDate.getDate());
		dialog.setTitle("Danh sách đơn hàng");
		dialog.setName("dlQuanLyDonDatHang");
		dialog.setVisible(true);
		((JDialog) getRootPane().getParent()).dispose();
	}

	public void createTableReceipt() {
		ActivityType activityType = null;
		if (checkbPayment.isSelected()) {
			if (!checkbReceipt.isSelected()) {
				activityType = ActivityType.Receipt;
			}
		} else {
			if (checkbReceipt.isSelected()) {
				activityType = ActivityType.Payment;
			}
		}

		List<String> types = new ArrayList<String>();
		for (Option o : cboType.getListCheck()) {
			types.add(o.getCode());
		}
		final TableInvoicePayment pninvoice = new TableInvoicePayment(txtStartDate.getDate(), txtEndDate.getDate(),
		    cbEmployee.getSelectedEmployee(), cbUnitMoney.getSelectedItem().toString(), activityType, types,
		    "Hóa đơn thu chi");
		pninvoice.setName("tbinvoice1");
		DialogList dialog = new DialogList(pninvoice);
		dialog.setTitle("Danh sách thu chi");
		dialog.setIcon("datban1.png");
		dialog.setComponent1(pninvoice.getBtnPayment());
		cboType.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {

			}

			@Override
			public void focusGained(FocusEvent e) {
				List<String> types = new ArrayList<String>();
				for (Option o : cboType.getListCheck()) {
					types.add(o.getCode());
				}
				if (!types.equals(pninvoice.getTypes())) {
					pninvoice.setTypes(types);
					pninvoice.loadDataByTime(pninvoice.getDateStart(), pninvoice.getDateEnd());
				}
			}
		});
		dialog.setComponent4(cboType, "Loại");
		dialog.setName("dlinvoice1");
		dialog.setVisible(true);
	}

	public void createTableDebt() {
		ActivityType activityType = null;
		if (checkbPayment.isSelected()) {
			if (!checkbReceipt.isSelected()) {
				activityType = ActivityType.Receipt;
			}
		} else {
			if (checkbReceipt.isSelected()) {
				activityType = ActivityType.Payment;
			}
		}
		List<String> types = new ArrayList<String>();
		for (Option o : cboType.getListCheck()) {
			types.add(o.getCode());
		}
		final TableInvoicePayment table = new TableInvoicePayment(txtStartDate.getDate(), txtEndDate.getDate(),
		    cbEmployee.getSelectedEmployee(), cbUnitMoney.getSelectedItem().toString(), activityType, types,
		    "Danh sách công nợ");
		table.setName("tbInvoice");
		DialogList dialog = new DialogList(table);
		dialog.setTitle("Danh sách công nợ");
		dialog.setIcon("invoiceds1.png");
		cboType.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {

			}

			@Override
			public void focusGained(FocusEvent e) {
				List<String> types = new ArrayList<String>();
				for (Option o : cboType.getListCheck()) {
					types.add(o.getCode());
				}
				if (!types.equals(table.getTypes())) {
					table.setTypes(types);
					table.loadDataByTime(table.getDateStart(), table.getDateEnd());
				}
			}
		});
		dialog.setComponent4(cboType, "Loại");
		dialog.setComponent1(table.getBtnPayment());
		dialog.setName("dialog");
		dialog.setVisible(true);
	}

	public void createTableTransaction() {
		InvoiceTransaction.ActivityType activityType = null;
		if (checkbPayment.isSelected()) {
			if (!checkbReceipt.isSelected()) {
				activityType = InvoiceTransaction.ActivityType.Receipt;
			}
		} else {
			if (checkbReceipt.isSelected()) {
				activityType = InvoiceTransaction.ActivityType.Payment;
			}
		}

		TableInvoiceTransaction table = new TableInvoiceTransaction(txtStartDate.getDate(), txtEndDate.getDate(),
		    cbEmployee.getSelectedEmployee(), cbUnitMoney.getSelectedItem().toString(), activityType);
		table.setName("tbInvoice");
		DialogList dialog = new DialogList(table);
		dialog.setTitle("Danh sách biên lai");
		dialog.setIcon("invoiceds1.png");
		dialog.setComponent4(cboType, "Loại");
		dialog.setName("dialog");
		dialog.setVisible(true);
	}
}
