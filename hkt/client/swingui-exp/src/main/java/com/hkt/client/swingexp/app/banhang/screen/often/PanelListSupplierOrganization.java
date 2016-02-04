package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.TableFillterSorter;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.khachhang.list.TableManageSupplier;
import com.hkt.client.swingexp.app.nhansu.SupplierIsOrganization;
import com.hkt.client.swingexp.app.nhansu.SupplierIsUser;
import com.hkt.client.swingexp.model.SupplierModelManager;
import com.hkt.module.partner.supplier.entity.Supplier;

public class PanelListSupplierOrganization extends JPanel implements IDialogResto {
	private Supplier Supplier;
	private TableManageSupplier table;
	private TableFillterSorter tableFillterSorter;
	private ExtendJTextField txtSearch;
	private ExtendJComboBox cbWith;
	private List<Supplier> Suppliers;
	private JTable jTable;
	public Supplier getSupplier() {
		return Supplier;
	}

	private void searchData() {
		tableFillterSorter.fillter(txtSearch.getText(), cbWith.getSelectedItem().toString());
	}

	public PanelListSupplierOrganization() {
		init();
	}

	public void init() {
		Panel_START START = new Panel_START();
		Panel_CENTER CENTER = new Panel_CENTER();

		this.setLayout(new BorderLayout(0, 10));
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(500, 300));
		this.add(START, BorderLayout.PAGE_START);
		this.add(CENTER, BorderLayout.CENTER);
	}

	protected class Panel_START extends JPanel {
		private JPanel PANEL1, PANEL2;
		private ExtendJLabel lblSearch, lblWith;

		private ExtendJButton btnAddPartnerOrganization, btnPartnerPersonal;

		public Panel_START() {
			init();
		}

		public void init() {
			PANEL1 = new JPanel();
			PANEL2 = new JPanel();

			lblSearch = new ExtendJLabel("Tìm kiếm");
			lblWith = new ExtendJLabel("Theo");

			txtSearch = new ExtendJTextField();
			txtSearch.setName("txtSearch");
			txtSearch.addCaretListener(new CaretListener() {
				@Override
				public void caretUpdate(CaretEvent e) {
					searchData();
				}
			});
			
			cbWith = new ExtendJComboBox();

			btnAddPartnerOrganization = new ExtendJButton("Thêm NCC DN");
			
			btnAddPartnerOrganization.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
					  SupplierIsOrganization supplier = new SupplierIsOrganization(null, true);
	          supplier.setSize(100, 100);
	          supplier.setName("SupplierIsOrganization");
	          supplier.setPreferredSize(new Dimension(100, 100));
	          DialogMain dialog = new DialogMain(supplier, true);
	          dialog.showButton(true);
	          dialog.setIcon("usernhom1.png");
	          dialog.setName("dlSupplierIsOrganization");
	          dialog.setTitle("Nhà cung cấp doanh nghiệp");
	          dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
	          dialog.setLocationRelativeTo(null);
	          dialog.setVisible(true);
						try {
							Suppliers = SupplierModelManager.getInstance().getAllSuppliers();
						} catch (Exception e1) {
							Suppliers = new ArrayList<Supplier>();
						}
						table.setSuppliers(Suppliers,1);
					} catch (Exception e2) {
					}

				}
			});
			
			btnPartnerPersonal = new ExtendJButton("Thêm NCC CN");
			btnPartnerPersonal.setName("btnPartnerPersonal");
			btnPartnerPersonal.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
					  SupplierIsUser panel = new SupplierIsUser(null, true);
	          panel.setSize(100, 100);
	          panel.setName("SupplierIsUser");
	          panel.setPreferredSize(new Dimension(100, 100));
	          DialogMain dialog = new DialogMain(panel, true);
	          dialog.showButton(true);
	          dialog.setIcon("usernhom1.png");
	          dialog.setName("dlSupplierIsUser");
	          dialog.setTitle("Nhà cung cấp cá nhân");
	          dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
	          dialog.setLocationRelativeTo(null);
	          dialog.setVisible(true);
						try {
							Suppliers = SupplierModelManager.getInstance().getAllSuppliers();
						} catch (Exception e1) {
							Suppliers = new ArrayList<Supplier>();
						}
						table.setSuppliers(Suppliers,1);
					} catch (Exception e2) {
					}

				}
			});
			
			btnAddPartnerOrganization.setPreferredSize(new Dimension(150, 25));
			btnPartnerPersonal.setPreferredSize(new Dimension(150, 25));

			Box boxHorizontal = Box.createHorizontalBox();
			boxHorizontal.add(lblSearch);
			boxHorizontal.add(txtSearch);
			boxHorizontal.add(Box.createHorizontalStrut(30));
			boxHorizontal.add(lblWith);
			boxHorizontal.add(cbWith);
			PANEL1.setLayout(new FlowLayout(FlowLayout.LEFT));
			PANEL1.setOpaque(false);
			PANEL1.add(boxHorizontal);

			PANEL2.setLayout(new FlowLayout(FlowLayout.RIGHT));
			PANEL2.setOpaque(false);
			PANEL2.setPreferredSize(new Dimension(400, 35));
			PANEL2.add(btnAddPartnerOrganization);
			PANEL2.add(btnPartnerPersonal);

			this.setLayout(new BorderLayout(0, 0));
			this.setOpaque(false);
			this.add(PANEL1, BorderLayout.CENTER);
			this.add(PANEL2, BorderLayout.LINE_END);
		}
	}

	protected class Panel_CENTER extends JScrollPane {

		public Panel_CENTER() {
			init();
		}

		public void init() {
			try {
				Suppliers = SupplierModelManager.getInstance().getAllSuppliers();
			} catch (Exception e) {
				Suppliers = new ArrayList<Supplier>();
			}
			table = new TableManageSupplier(Suppliers,1);
			table.setName("table");
			table.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					
					int click = 2;
			          JTable ta = (JTable) e.getSource();
			          String value = ta.getValueAt(ta.getSelectedRow(), ta.getSelectedColumn()).toString();
			          if (value.indexOf("HktTest") >= 0 && e.getButton() == 3) {
			            click = 1;
			          }
					
					if (e.getClickCount() >= click) {
						Supplier = table.getSelectedBean();
						((Dialog) getRootPane().getParent()).dispose();
					}

				}

			});
			tableFillterSorter = new TableFillterSorter(table);
			tableFillterSorter.createTableSorter();
			tableFillterSorter.createTableFillter();
			cbWith.setModel(new DefaultComboBoxModel(tableFillterSorter.getHashtable().keySet().toArray()));
			this.setViewportView(table);
			this.getViewport().setBackground(Color.WHITE);
		}
	}

	@Override
	public void Save() throws Exception {
		Supplier = table.getSelectedBean();
		((Dialog) getRootPane().getParent()).dispose();
	}

	public TableManageSupplier getTable() {
		return table;
	}

}
