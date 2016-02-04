package com.hkt.client.swingexp.app.khachhang;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.component.TableFillterSorter;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialog;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.khachhang.list.TableListModel;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.SupplierModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.supplier.entity.Supplier;

@SuppressWarnings("serial")
public class PanelManageProvider extends MyPanel implements IDialogResto {

	private ExtendJLabel			 lblTypeLeft, lblTypeRight;
	private ExtendJComboBox		cbTypeLeft, cbTypeRight;
	//private JScrollPane				scrollPane;
	@SuppressWarnings("rawtypes")
	private List							listDataLeft, listDataRight, listTemp;
	@SuppressWarnings("rawtypes")
//	private TableModel				tableModel;
//	private JTable						tableLeft, tableRight;
	private TableListModel tableLeft, tableRight;
	private Customer					customer;
	private Employee					employee;
	private Supplier					supplier;

	/**
	 * Create the PanelManagePartner
	 */

	public PanelManageProvider() {
		createBeginTest();

		this.setLayout(new BorderLayout(10, 10));
		this.setOpaque(false);
		
//		this.add(new Panel_PAGESTART(), BorderLayout.PAGE_START);
		this.add(new Panel_LEFT(), BorderLayout.LINE_START);
		this.add(new Panel_CENTER(), BorderLayout.CENTER);
		this.add(new Panel_RIGHT(), BorderLayout.LINE_END);

	}

//	protected class Panel_PAGESTART extends JPanel {
//
//		public Panel_PAGESTART() {
//			init();
//			txtOwner.setText(AccountModelManager.getInstance().getNameByLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId()));
//		}
//
//		public void init() {
//			this.setLayout(new BorderLayout(20, 0));
//			this.setOpaque(false);
//
//			lblOwner = new ExtendJLabel("Doanh nghiệp");
//			lblOwner.setPreferredSize(new Dimension(100, 22));
//			txtOwner = new ExtendJTextField();
//			txtOwner.setEnabled(false);
//
//			this.add(lblOwner, BorderLayout.LINE_START);
//			this.add(txtOwner, BorderLayout.CENTER);
//		}
//	}

	protected class Panel_LEFT extends JPanel {
		private TableFillterSorter tableFillterSorter;
		private ExtendJTextField txtSearch;
		@SuppressWarnings("unchecked")
		public Panel_LEFT() {
			
			listDataLeft = new ArrayList<Employee>();
			try {
				listDataLeft = CustomerModelManager.getInstance().getCustomers(false);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			init();

			cbTypeLeft.setSelectedItem(AccountModelManager.Customer);

			cbTypeLeft.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String selectItem = cbTypeLeft.getSelectedItem().toString();
					if (selectItem.equals(AccountModelManager.Department)) {
						listDataLeft = new ArrayList<Employee>();
						try {
							listDataLeft = HRModelManager.getInstance().getEmployees();
							tableLeft.setData(listDataLeft);
//							tableLeft.setModel(tableModel);
							tableLeft.getColumnModel().getColumn(0).setMaxWidth(50);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					if (selectItem.equals(AccountModelManager.Customer)) {
						listDataLeft = new ArrayList<Customer>();
						try {
							listDataLeft = CustomerModelManager.getInstance().getCustomers(false);
							tableLeft.setData(listDataLeft);
							tableLeft.getColumnModel().getColumn(0).setMaxWidth(50);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					if (selectItem.equals(AccountModelManager.Supplier)) {
						listDataLeft = new ArrayList<Supplier>();
						try {
							listDataLeft = SupplierModelManager.getInstance().getAllSuppliers();
							tableLeft.setData(listDataLeft);
							tableLeft.getColumnModel().getColumn(0).setMaxWidth(50);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			});
		}

		@SuppressWarnings("unchecked")
		public void init() {
			this.setLayout(new BorderLayout(0, 10));
			this.setPreferredSize(new Dimension(410, 100));
			this.setOpaque(false);
			txtSearch = new ExtendJTextField();
   	      	txtSearch.addCaretListener(new CaretListener() {
   				public void caretUpdate(CaretEvent evt) {
   					txtSearchCaretUpdate(evt);
   				}
   			});
			cbTypeLeft = new ExtendJComboBox();
			cbTypeLeft.setName("cbTrai");
			lblTypeLeft = new ExtendJLabel("Nhóm danh sách lựa chọn");
			PanelComboBox<String> panelComboBox = new PanelComboBox<String>(cbTypeLeft, lblTypeLeft);
			panelComboBox.setModelComboBox(setItemsComboBox());

			tableLeft = new TableListModel<Customer>(listDataLeft);
			tableLeft.setName("tbTrai");
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setViewportView(tableLeft);

			tableLeft.setRowHeight(23);
			tableLeft.setFont(new Font("Tahoma", 0, 14));
			tableLeft.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
			((DefaultTableCellRenderer) tableLeft.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
			tableLeft.getColumnModel().getColumn(0).setMaxWidth(50);
			tableFillterSorter = new TableFillterSorter(tableLeft);
   	  	  	tableFillterSorter.createTableSorter();
   	  	  	tableFillterSorter.createTableFillter();
			this.add(panelComboBox, BorderLayout.PAGE_START);
			this.add(txtSearch, BorderLayout.CENTER);
			this.add(scrollPane, BorderLayout.SOUTH);
		}
		
		private void txtSearchCaretUpdate(CaretEvent evt) {
			searchData();
			
		}
		  private void searchData() {
			  tableFillterSorter.fillter(txtSearch.getText(), "Tên đối tác");
			
		}
	}

	protected class Panel_CENTER extends JPanel {

		private ExtendJButton	btnAddOne, btnAddAll, btnRemoveOne, btnRemoveAll;

		public Panel_CENTER() {
			init();

			btnAddOne.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					buttonAdd();
				}
			});

			btnAddAll.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					buttonAddAll();
				}
			});

			btnRemoveOne.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					buttonRemoveOne();
				}
			});

			btnRemoveAll.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					buttonRemoveAll();
				}
			});
		}

		public void init() {
			this.setLayout(new GridLayout(10, 1, 10, 14));
			this.setPreferredSize(new Dimension(50, 150));
			this.setOpaque(false);

			btnAddOne = new ExtendJButton(">");
			btnAddAll = new ExtendJButton(">>");
			btnRemoveOne = new ExtendJButton("X");
			btnRemoveAll = new ExtendJButton("XX");
			JLabel label1 = new JLabel("");
			label1.setPreferredSize(new Dimension(50, 50));
			JLabel label2 = new JLabel("");
			label2.setPreferredSize(new Dimension(50, 50));
			
			btnAddOne.addMouseListener(new MouseEventClickButtonDialog("", "", ""));
			btnAddAll.addMouseListener(new MouseEventClickButtonDialog("", "", ""));
			btnRemoveOne.addMouseListener(new MouseEventClickButtonDialog("", "", ""));
			btnRemoveAll.addMouseListener(new MouseEventClickButtonDialog("", "", ""));
			
			btnAddOne.setName(">");
			btnAddAll.setName(">>");
			btnRemoveOne.setName("X");
			btnRemoveAll.setName("XX");

			this.add(label1);
			this.add(label2);
			this.add(btnAddAll);
			this.add(btnAddOne);
			this.add(btnRemoveOne);
			this.add(btnRemoveAll);
		}
	}

	protected class Panel_RIGHT extends JPanel {
		private TableFillterSorter tableFillterSorter;
		private ExtendJTextField txtSearch;
		@SuppressWarnings("unchecked")
		public Panel_RIGHT() {
			listDataRight = new ArrayList<Employee>();
			try {
				listTemp = new ArrayList<Employee>();
				listDataRight = HRModelManager.getInstance().getEmployees();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			init();
			cbTypeRight.setSelectedItem(AccountModelManager.Department);
			cbTypeRight.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String selectedItem = cbTypeRight.getSelectedItem().toString();
					if (selectedItem.equals(AccountModelManager.Department)) {
						listTemp = new ArrayList<Employee>();
						listDataRight = new ArrayList<Employee>();
						try {
							listDataRight = HRModelManager.getInstance().getEmployees();
							tableRight.setData(listDataRight);							
							tableRight.getColumnModel().getColumn(0).setMaxWidth(50);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					if (selectedItem.equals(AccountModelManager.Customer)) {
						listTemp = new ArrayList<Employee>();
						listDataRight = new ArrayList<Customer>();
						try {
							listDataRight = CustomerModelManager.getInstance().getCustomers(false);
							tableRight.setData(listDataRight);
							tableRight.getColumnModel().getColumn(0).setMaxWidth(50);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					if (selectedItem.equals(AccountModelManager.Supplier)) {
						listTemp = new ArrayList<Employee>();
						listDataRight = new ArrayList<Supplier>();
						try {
							listDataRight = SupplierModelManager.getInstance().getAllSuppliers();
							tableRight.setData(listDataRight);
							tableRight.getColumnModel().getColumn(0).setMaxWidth(50);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			});
		}

		@SuppressWarnings("unchecked")
		public void init() {
			this.setLayout(new BorderLayout(0, 10));
			this.setPreferredSize(new Dimension(410, 100));
			this.setOpaque(false);
			txtSearch = new ExtendJTextField();
   	      	txtSearch.addCaretListener(new CaretListener() {
   				public void caretUpdate(CaretEvent evt) {
   					txtSearchCaretUpdate(evt);
   				}
   			});
			lblTypeRight = new ExtendJLabel("Nhóm danh sách cập nhật");
			cbTypeRight = new ExtendJComboBox();
			cbTypeRight.setName("cbPhai");
			PanelComboBox<String> panelComboBox = new PanelComboBox<String>(cbTypeRight, lblTypeRight);
			panelComboBox.setModelComboBox(setItemsComboBox());
			tableRight = new TableListModel<Customer>(listDataRight);
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setViewportView(tableRight);
		

			tableRight.setRowHeight(23);
			tableRight.setFont(new Font("Tahoma", 0, 14));
			tableRight.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
			((DefaultTableCellRenderer) tableRight.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
			tableRight.getColumnModel().getColumn(0).setMaxWidth(50);
			tableFillterSorter = new TableFillterSorter(tableRight);
   	  	  	tableFillterSorter.createTableSorter();
   	  	  	tableFillterSorter.createTableFillter();
			this.add(panelComboBox, BorderLayout.PAGE_START);
			this.add(txtSearch, BorderLayout.CENTER);
			this.add(scrollPane, BorderLayout.SOUTH);
		}
		
		private void txtSearchCaretUpdate(CaretEvent evt) {
			searchData();
			
		}
		  private void searchData() {
			  tableFillterSorter.fillter(txtSearch.getText(), "Tên đối tác");
			
		}
	}

	protected class PanelComboBox<E> extends JPanel {
		private ExtendJComboBox	cbBox;
		private ExtendJLabel		lblName;

		public PanelComboBox(ExtendJComboBox comboBox, ExtendJLabel label) {
			this.cbBox = comboBox;
			this.lblName = label;

			this.setLayout(new GridLayout(2, 1, 0, 0));
			this.setOpaque(false);

			this.add(lblName);
			this.add(cbBox);
		}

		@SuppressWarnings("unchecked")
		public void setModelComboBox(List<E> list) {
			DefaultComboBoxModel<E> modelCb = new DefaultComboBoxModel<E>();
			for (E e : list) {
				modelCb.addElement(e);
			}
			cbBox.setModel(modelCb);
		}
	}

	private List<String> setItemsComboBox() {
		List<String> items = new ArrayList<String>();
		items.add(AccountModelManager.Department);
		items.add(AccountModelManager.Customer);
		items.add(AccountModelManager.Supplier);
		return items;
	}

	@SuppressWarnings("unchecked")
	private void buttonAdd() {
		if (listDataLeft.size() > 0 && cbTypeLeft.getSelectedIndex() != cbTypeRight.getSelectedIndex()) {
			Object obj = null;
			try{
				 obj = tableLeft.getValueAt(tableLeft.getSelectedRow(), 1);
			}catch(ArrayIndexOutOfBoundsException e){
				return;
			}
			
			// Kiểm tra đối tượng click trong bảng thuộc loại nào?

			// Nếu là
			// employee---------------------------------------------------------------------------
			if (obj instanceof Employee) {
				Employee emp = ((Employee) tableLeft.getValueAt(tableLeft.getSelectedRow(), 1));
				boolean result = false; // Biến kiểm tra đối tượng tồn tại hay chưa?
				// Kiểm tra đối tượng đang chọn bên Customer đã tồn tại chưa
				if ((cbTypeRight.getSelectedItem().toString()).equals(AccountModelManager.Customer)) {
					for (int i = 0; i < listDataRight.size(); i++) {
						if (emp.getLoginId().equals(((Customer) listDataRight.get(i)).getLoginId())) {
							result = true;
							showPanelMessageBox("Khách hàng đã tồn tại!");
							break;
						}
					}
				}
				// Kiểm tra đối tượng đang chọn bên Supplier đã tồn tại chưa
				else {
					if ((cbTypeRight.getSelectedItem().toString()).equals(AccountModelManager.Supplier)) {
						for (int i = 0; i < listDataRight.size(); i++) {
							if (emp.getLoginId().equals(((Supplier) listDataRight.get(i)).getLoginId())) {
								result = true;
								showPanelMessageBox("Nhà cung cấp đã tồn tại!");
								break;
							}
						}
					}
				}
				// Nếu chưa tồn tại thì tạo mới đối tượng bên Customer hoặc Supplier
				if (!result) {
					// Tạo mới đối tượng bên Customer nếu chọn comboBox là khách hàng
					if ((cbTypeRight.getSelectedItem().toString()).equals(AccountModelManager.Customer)) {
						customer = new Customer();
						customer.setLoginId(emp.getLoginId());
						customer.setOrganizationLoginId(emp.getOrganizationLoginId());
						customer.setCode(emp.getCode());
						customer.setName(emp.getName());
						listTemp.add(customer);
						listDataRight.add(customer);
//						tableRight.setData(listDataRight);;
					}
					// Hoặc tạo mới đối tượng bên Supplier nếu chọn comboBox là nhà phân
					// phối
					else {
						if ((cbTypeRight.getSelectedItem().toString()).equals(AccountModelManager.Supplier)) {
							supplier = new Supplier();
							supplier.setLoginId(emp.getLoginId());
							supplier.setOrganizationLoginId(emp.getOrganizationLoginId());
							supplier.setCode(emp.getCode());
							supplier.setName(emp.getName());
							listTemp.add(supplier);
							listDataRight.add(supplier);
							
						}
					}
					tableRight.setData(listDataRight);
//					tableRight.setModel(tableModel);
					tableRight.getColumnModel().getColumn(0).setMaxWidth(50);
				}
			}
			// Nếu là
			// Customer-----------------------------------------------------------------------
			else {
				if (obj instanceof Customer) {
					Customer cus = ((Customer) tableLeft.getValueAt(tableLeft.getSelectedRow(), 1));
					boolean result = false; // Biến kiểm tra đối tượng tồn tại hay chưa?
					// Kiểm tra đối tượng đang chọn bên Employee đã tồn tại chưa
					if ((cbTypeRight.getSelectedItem().toString()).equals(AccountModelManager.Department)) {
						for (int i = 0; i < listDataRight.size(); i++) {
							if (cus.getLoginId().equals(((Employee) listDataRight.get(i)).getLoginId())) {
								result = true;
								showPanelMessageBox("Nhân viên đã tồn tại!");
								break;
							}
						}
					}
					// Kiểm tra đối tượng đang chọn bên Supplier đã tồn tại chưa
					else {
						if ((cbTypeRight.getSelectedItem().toString()).equals(AccountModelManager.Supplier)) {
							for (int i = 0; i < listDataRight.size(); i++) {
								if (cus.getLoginId().equals(((Supplier) listDataRight.get(i)).getLoginId())) {
									result = true;
									showPanelMessageBox("Nhà cung cấp đã tồn tại!");
									break;
								}
							}
						}
					}
					// Nếu chưa tồn tại thì tạo mới đối tượng bên Employee hoặc Supplier
					if (!result) {

						// Tạo mới đối tượng bên Employee nếu chọn comboBox là nhân viên
						if ((cbTypeRight.getSelectedItem().toString()).equals(AccountModelManager.Department)) {
							employee = new Employee();
							employee.setLoginId(cus.getLoginId());
							employee.setOrganizationLoginId(cus.getOrganizationLoginId());
							employee.setCode(cus.getCode());
							employee.setName(cus.getName());
							listTemp.add(employee);
							listDataRight.add(employee);
							tableRight.setData(listDataRight);
						}
						// Hoặc tạo mới đối tượng bên Supplier nếu chọn comboBox là nhà phân
						// phối
						else {
							if ((cbTypeRight.getSelectedItem().toString()).equals(AccountModelManager.Supplier)) {
								supplier = new Supplier();
								supplier.setLoginId(cus.getLoginId());
								supplier.setOrganizationLoginId(cus.getOrganizationLoginId());
								supplier.setCode(cus.getCode());
								supplier.setName(cus.getName());
								listTemp.add(supplier);
								listDataRight.add(supplier);
//								tableRight = new TableListTestRight<Supplier>(listDataRight);
							}
						}
						tableRight.setData(listDataRight);
//						tableRight.setModel(tableModel);
						tableRight.getColumnModel().getColumn(0).setMaxWidth(50);
					}
				}
				// Nếu là Supplier----------------------------------------------------------------------------------
				else {
					if (obj instanceof Supplier) {
						Supplier sup;
						try{
							sup = ((Supplier) tableLeft.getValueAt(tableLeft.getSelectedRow(), 1));
						}catch(ArrayIndexOutOfBoundsException e){
							return;
						}
						boolean result = false; // Biến kiểm tra đối tượng tồn tại hay chưa?
						// Kiểm tra đối tượng đang chọn bên Employee đã tồn tại chưa
						if ((cbTypeRight.getSelectedItem().toString()).equals(AccountModelManager.Department)) {
							for (int i = 0; i < listDataRight.size(); i++) {
								if (sup.getLoginId().equals(((Employee) listDataRight.get(i)).getLoginId())) {
									result = true;
									showPanelMessageBox("Nhân viên đã tồn tại!");
									break;
								}
							}
						}
						// Kiểm tra đối tượng đang chọn bên Customer đã tồn tại chưa
						else {
							if ((cbTypeRight.getSelectedItem().toString()).equals(AccountModelManager.Customer)) {
								for (int i = 0; i < listDataRight.size(); i++) {
									if (sup.getLoginId().equals(((Customer) listDataRight.get(i)).getLoginId())) {
										result = true;
										showPanelMessageBox("Khách hàng đã tồn tại!");
										break;
									}
								}
							}
						}
						// Nếu chưa tồn tại thì tạo mới đối tượng bên Employee hoặc Customer
						if (!result) {
							// Tạo mới đối tượng bên Employee nếu chọn comboBox là nhân viên
							if ((cbTypeRight.getSelectedItem().toString()).equals(AccountModelManager.Department)) {
								employee = new Employee();
								employee.setLoginId(sup.getLoginId());
								employee.setOrganizationLoginId(sup.getOrganizationLoginId());
								employee.setCode(sup.getCode());
								employee.setName(sup.getName());
								listTemp.add(employee);
								listDataRight.add(employee);
								tableRight.setData(listDataRight);
							}
							// Hoặc tạo mới đối tượng bên Customer nếu chọn comboBox là khách hàng
							else {
								if ((cbTypeRight.getSelectedItem().toString()).equals(AccountModelManager.Customer)) {
									customer = new Customer();
									customer.setLoginId(sup.getLoginId());
									customer.setOrganizationLoginId(sup.getOrganizationLoginId());
									customer.setCode(sup.getCode());
									customer.setName(sup.getName());
									listTemp.add(customer);
									listDataRight.add(customer);
//									tableRight = new TableListTestRight<Customer>(listDataRight);
								}
							}
							tableRight.setData(listDataRight);
//							tableRight.setModel(tableModel);
							tableRight.getColumnModel().getColumn(0).setMaxWidth(50);
						}
					}
					// Kết thúc kiểm tra
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void buttonAddAll() {
		// Kiểm tra danh sách bên trái phải có đối tượng > 0 và 2 lựa chọn comboBox là 2 nhóm khác nhau
		if (listDataLeft.size() > 0 && cbTypeLeft.getSelectedIndex() != cbTypeRight.getSelectedIndex()) {
			// Nếu chọn Employee để chuyển sang (comboBox trái chọn 'phòng ban')
			if ((cbTypeLeft.getSelectedItem().toString()).equals(AccountModelManager.Department)) {
				// Trường hợp chuyển sang danh sách là customer (ComboBox phải chọn 'khách hàng')
				if ((cbTypeRight.getSelectedItem().toString()).equals(AccountModelManager.Customer)) {
					boolean flag = false;
					for (int j = 0; j < listDataLeft.size(); j++) {
						for (int i = 0; i < listDataRight.size(); i++) {
							if (((Employee) listDataLeft.get(j)).getLoginId().equals(((Customer) listDataRight.get(i)).getLoginId())) {
								flag = true;
								break;
							}
						}
						// Nếu không có thì tạo đối tượng
						if (!flag) {
							customer = new Customer();
							customer.setLoginId(((Employee) listDataLeft.get(j)).getLoginId());
							customer.setOrganizationLoginId(((Employee) listDataLeft.get(j)).getOrganizationLoginId());
							customer.setCode(((Employee) listDataLeft.get(j)).getCode());
							customer.setName(((Employee) listDataLeft.get(j)).getName());
							listTemp.add(customer);
							listDataRight.add(customer);
						}
					}
					tableRight.setData(listDataRight);
//					tableRight.setModel(tableModel);
					tableRight.getColumnModel().getColumn(0).setMaxWidth(50);
				}
				// Trường hợp chuyển sang danh sách là supplier (ComboBox phải chọn 'nhà phân phối')
				else {
					if ((cbTypeRight.getSelectedItem().toString()).equals(AccountModelManager.Supplier)) {
						boolean flag = false;
						for (int j = 0; j < listDataLeft.size(); j++) {
							for (int i = 0; i < listDataRight.size(); i++) {
								if (((Employee) listDataLeft.get(j)).getLoginId().equals(((Supplier) listDataRight.get(i)).getLoginId())) {
									flag = true;
									break;
								}
							}
							// Nếu không có thì tạo đối tượng
							if (!flag) {
								supplier = new Supplier();
								supplier.setLoginId(((Employee) listDataLeft.get(j)).getLoginId());
								supplier.setOrganizationLoginId(((Employee) listDataLeft.get(j)).getOrganizationLoginId());
								supplier.setCode(((Employee) listDataLeft.get(j)).getCode());
								supplier.setName(((Employee) listDataLeft.get(j)).getName());
								listTemp.add(supplier);
								listDataRight.add(supplier);
							}
						}
						tableRight.setData(listDataRight);
//						tableRight.setModel(tableModel);
						tableRight.getColumnModel().getColumn(0).setMaxWidth(50);
					}
				}
			}
			// Nếu chọn Customer để chuyển sang (comboBox trái chọn 'khách hàng')
			else {
				if ((cbTypeLeft.getSelectedItem().toString()).equals(AccountModelManager.Customer)) {
					// Trường hợp chuyển sang danh sách là employee (ComboBox phải chọn 'nhân viên')
					if ((cbTypeRight.getSelectedItem().toString()).equals(AccountModelManager.Department)) {
						boolean flag = false;
						for (int j = 0; j < listDataLeft.size(); j++) {
							for (int i = 0; i < listDataRight.size(); i++) {
								if (((Customer) listDataLeft.get(j)).getLoginId().equals(((Employee) listDataRight.get(i)).getLoginId())) {
									flag = true;
									break;
								}
							}
							// Nếu không có thì tạo đối tượng
							if (!flag) {
								employee = new Employee();
								employee.setLoginId(((Customer) listDataLeft.get(j)).getLoginId());
								employee.setOrganizationLoginId(((Customer) listDataLeft.get(j)).getOrganizationLoginId());
								employee.setCode(((Customer) listDataLeft.get(j)).getCode());
								employee.setName(((Customer) listDataLeft.get(j)).getName());
								listTemp.add(employee);
								listDataRight.add(employee);
							}
						}
						tableRight.setData(listDataRight);
//						tableRight.setModel(tableModel);
						tableRight.getColumnModel().getColumn(0).setMaxWidth(50);
					}
					// Trường hợp chuyển sang danh sách là supplier (ComboBox phải chọn 'nhà phân phối')
					else {
						if ((cbTypeRight.getSelectedItem().toString()).equals(AccountModelManager.Supplier)) {
							boolean flag = false;
							for (int j = 0; j < listDataLeft.size(); j++) {
								for (int i = 0; i < listDataRight.size(); i++) {
									if (((Customer) listDataLeft.get(j)).getLoginId().equals(((Supplier) listDataRight.get(i)).getLoginId())) {
										flag = true;
										break;
									}
								}
								// Nếu không có thì tạo đối tượng
								if (!flag) {
									supplier = new Supplier();
									supplier.setLoginId(((Customer) listDataLeft.get(j)).getLoginId());
									supplier.setOrganizationLoginId(((Customer) listDataLeft.get(j)).getOrganizationLoginId());
									supplier.setCode(((Customer) listDataLeft.get(j)).getCode());
									supplier.setName(((Customer) listDataLeft.get(j)).getName());
									listTemp.add(supplier);
									listDataRight.add(supplier);
								}
							}
							tableRight.setData(listDataRight);
//							tableRight.setModel(tableModel);
							tableRight.getColumnModel().getColumn(0).setMaxWidth(50);
						}
					}
				}
				// Nếu chọn Supplier để chuyển sang (comboBox trái chọn 'nhà phân phối')
				else {
					if ((cbTypeLeft.getSelectedItem().toString()).equals(AccountModelManager.Supplier)) {
						// Trường hợp chuyển sang danh sách là employee (ComboBox phải chọn 'nhân viên')
						if ((cbTypeRight.getSelectedItem().toString()).equals(AccountModelManager.Department)) {
							boolean flag = false;
							for (int j = 0; j < listDataLeft.size(); j++) {
								for (int i = 0; i < listDataRight.size(); i++) {
									if (((Supplier) listDataLeft.get(j)).getLoginId().equals(((Employee) listDataRight.get(i)).getLoginId())) {
										flag = true;
										break;
									}
								}
								// Nếu không có thì tạo đối tượng
								if (!flag) {
									employee = new Employee();
									employee.setLoginId(((Supplier) listDataLeft.get(j)).getLoginId());
									employee.setOrganizationLoginId(((Supplier) listDataLeft.get(j)).getOrganizationLoginId());
									employee.setCode(((Supplier) listDataLeft.get(j)).getCode());
									employee.setName(((Supplier) listDataLeft.get(j)).getName());
									listTemp.add(employee);
									listDataRight.add(employee);
								}
							}
							tableRight.setData(listDataRight);
//							tableRight.setModel(tableModel);
							tableRight.getColumnModel().getColumn(0).setMaxWidth(50);
						}
						// Trường hợp chuyển sang danh sách là customer (ComboBox phải chọn 'khách hàng')
						else {
							if ((cbTypeRight.getSelectedItem().toString()).equals(AccountModelManager.Customer)) {
								boolean flag = false;
								for (int j = 0; j < listDataLeft.size(); j++) {
									for (int i = 0; i < listDataRight.size(); i++) {
										if (((Supplier) listDataLeft.get(j)).getLoginId().equals(((Customer) listDataRight.get(i)).getLoginId())) {
											flag = true;
											break;
										}
									}
									// Nếu không có thì tạo đối tượng
									if (!flag) {
										customer = new Customer();
										customer.setLoginId(((Supplier) listDataLeft.get(j)).getLoginId());
										customer.setOrganizationLoginId(((Supplier) listDataLeft.get(j)).getOrganizationLoginId());
										customer.setCode(((Supplier) listDataLeft.get(j)).getCode());
										customer.setName(((Supplier) listDataLeft.get(j)).getName());
										listTemp.add(customer);
										listDataRight.add(customer);
									}
								}
								tableRight.setData(listDataRight);
//								tableRight.setModel(tableModel);
								tableRight.getColumnModel().getColumn(0).setMaxWidth(50);
							}
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void buttonRemoveOne() {
		Object obj = null;
		try{
			 obj = tableRight.getValueAt(tableRight.getSelectedRow(), 1);
		}catch(ArrayIndexOutOfBoundsException e){
			return;
		}
		
		if (obj instanceof Employee) {
			Employee emp = (Employee) obj;
			if (emp.getId() == null) {
				listDataRight.remove(emp);
				listTemp.remove(emp);
			}
//			tableRight = new TableListTestRight<Employee>(listDataRight);
		} else {
			if (obj instanceof Customer) {
				Customer cus = (Customer) obj;
				if (cus.getId() == null) {
					listDataRight.remove(cus);
					listTemp.remove(cus);
				}
//				tableRight = new TableListTestRight<Customer>(listDataRight);
			} else {
				if (obj instanceof Supplier) {
					Supplier sup = (Supplier) obj;
					if (sup.getId() == null) {
						listDataRight.remove(sup);
						listTemp.remove(sup);
					}
					System.out.println(listDataRight);
					
				}
			}
		}
		tableRight.setData(listDataRight);
//		tableRight.setModel(tableModel);
		tableRight.getColumnModel().getColumn(0).setMaxWidth(50);
	}

	@SuppressWarnings("unchecked")
	private void buttonRemoveAll() {
		for (int i = 0; i < listDataRight.size();) {
			if (cbTypeRight.getSelectedItem().toString().equals(AccountModelManager.Department)) {
				if (((Employee) listDataRight.get(i)).getId() == null) {
					listDataRight.remove(i);					
				} else					
					i++;
			} else {
				if (cbTypeRight.getSelectedItem().toString().equals(AccountModelManager.Customer)) {
					if (((Customer) listDataRight.get(i)).getId() == null) {
						listDataRight.remove(i);						
					} else						
						i++;
				} else {
					if (cbTypeRight.getSelectedItem().toString().equals(AccountModelManager.Supplier)) {
						if (((Supplier) listDataRight.get(i)).getId() == null) {
							listDataRight.remove(i);							
						} else							
							i++;
					}
				}
			}
		}
		tableRight.setData(listDataRight);
//		tableRight.setModel(tableModel);
		tableRight.getColumnModel().getColumn(0).setMaxWidth(50);
		listTemp.clear();
	}

	@Override
	public void Save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(getToolTipText()) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {
			if ((cbTypeRight.getSelectedItem().toString()).equals(AccountModelManager.Department)) {
				for (int i = 0; i < listTemp.size(); i++) {
					HRModelManager.getInstance().saveEmployee((Employee) listTemp.get(i));
				}
			} else {
				if ((cbTypeRight.getSelectedItem().toString()).equals(AccountModelManager.Customer)) {
					for (int i = 0; i < listTemp.size(); i++) {
						CustomerModelManager.getInstance().saveCustomer((Customer) listTemp.get(i));
					}
				} else {
					if ((cbTypeRight.getSelectedItem().toString()).equals(AccountModelManager.Supplier)) {
						for (int i = 0; i < listTemp.size(); i++) {
							SupplierModelManager.getInstance().saveSupplier((Supplier) listTemp.get(i));
						}
					}
				}
			}
			DialogNotice.getInstace().setVisible(true);
		}
	}

	@Override
	public void createBeginTest() {
		if (MyPanel.isTest) {
			// Tạo nhân viên
			for (int i = 1; i <= 9; i++) {
				Account account = new Account();
				account.setLoginId("HktTest" + i);
				account.setPassword("HktTest" + i);
				account.setEmail("HktTest" + i + "@gmail.com");
				try {
					AccountModelManager.getInstance().saveAccount(account);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				Employee employee = new Employee();
				employee.setLoginId("HktTest" + i);
				employee.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
				employee.setCode("HktTest" + i);
				employee.setName("HktTest" + i);
				try {
					HRModelManager.getInstance().saveEmployee(employee);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		super.createBeginTest();
	}
	
	private void showPanelMessageBox(String showMessage) {
		  PanelChoise pnPanel = new PanelChoise(showMessage);
		  DialogResto dialog1 = new DialogResto(pnPanel, false, 500, 240);
		  dialog1.setTitle("Thông báo");
		  dialog1.setLocationRelativeTo(null);
		  dialog1.setModal(true);
		  dialog1.setVisible(true);
		  if (pnPanel.isDelete()) {
		   return;
		  }
		 }
	@Override
	public void deleteDataTest() {
		if (!PanelTestAll.runAll) {
			DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
			if (DialogTest.getInstance().isTest()) {
				try {
					AccountModelManager.getInstance().deleteTest("HktTest");
					HRModelManager.getInstance().deleteTest("HktTest");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

}
