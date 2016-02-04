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

import com.hkt.client.swingexp.app.banhang.screen.often.PanelListPartnerOrganization.Panel_CENTER;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelListPartnerOrganization.Panel_START;
import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.TableFillterSorter;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.khachhang.PartnerIsOrganization;
import com.hkt.client.swingexp.app.khachhang.PartnerIsUser;
import com.hkt.client.swingexp.app.khachhang.list.TableManagePartner;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.partner.customer.entity.Customer;

public class PanelListPartnerOrganizationWork extends JPanel implements IDialogResto {
	private Customer customer;
	private TableManagePartner table;
	private TableFillterSorter tableFillterSorter;
	private ExtendJTextField txtSearch;
	private ExtendJComboBox cbWith;
	private List<Customer> customers;
	public Customer getCustomer() {
		return customer;
	}

	private void searchData() {
		tableFillterSorter.fillter(txtSearch.getText(), cbWith.getSelectedItem().toString());
	}

	public PanelListPartnerOrganizationWork() {
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

			btnAddPartnerOrganization = new ExtendJButton("Thêm đối tác DN");
			
			btnAddPartnerOrganization.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						PartnerIsOrganization partner1 = new PartnerIsOrganization(null, true);
						partner1.setSize(100, 100);
						partner1.setName("PartnerIsOrganization");
						partner1.setPreferredSize(new Dimension(100, 100));
						DialogMain dialog = new DialogMain(partner1, true);
						dialog.showButton(true);
						dialog.setName("dlPartnerIsOrganization");
						dialog.setTitle("Khách hàng doanh nghiệp");
						dialog.setIcon("doanhnghiep1.png");
						dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);
						try {
							customers = CustomerModelManager.getInstance().getCustomers(false);
						} catch (Exception e1) {
							customers = new ArrayList<Customer>();
						}
						table.setCustomers(customers);
					} catch (Exception e2) {
					}

				}
			});
			
			btnPartnerPersonal = new ExtendJButton("Thêm đối tác CN");
			btnPartnerPersonal.setName("btnPartnerPersonal");
			btnPartnerPersonal.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						PartnerIsUser partner = new PartnerIsUser(null, true);
						partner.setPreferredSize(new Dimension(100, 100));
						partner.setName("PartnerIsUser");
						DialogMain dialog = new DialogMain(partner, true);
						dialog.showButton(true);
						dialog.setTitle("Khách hàng cá nhân");
						dialog.setIcon("doanhnghiep1.png");
						dialog.setName("dlPartnerIsUser");
						dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);
						try {
							customers = CustomerModelManager.getInstance().getCustomers(false);
						} catch (Exception e1) {
							customers = new ArrayList<Customer>();
						}
						table.setCustomers(customers);
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
				customers = CustomerModelManager.getInstance().getCustomers(false);
			} catch (Exception e) {
				customers = new ArrayList<Customer>();
			}
			table = new TableManagePartner(customers);
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
						customer = table.getSelectedBean();
					try {
						
					} catch (Exception e2) {
					}	
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
		customer = table.getSelectedBean();
		((Dialog) getRootPane().getParent()).dispose();
	}

	public TableManagePartner getTable() {
		return table;
	}
}

