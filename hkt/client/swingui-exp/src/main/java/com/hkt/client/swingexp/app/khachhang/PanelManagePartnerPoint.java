package com.hkt.client.swingexp.app.khachhang;

/*
 * @author: Phan Anh
 * @editDate: 31/10/2014
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.customer.entity.Point;
import com.hkt.module.partner.customer.entity.PointTransaction;

public class PanelManagePartnerPoint extends JPanel implements IDialogResto {
	private JScrollPane							scrollPaneTree, scrollPaneTable;
	private CustomModelTable				tableModel;
	private DefaultMutableTreeNode	rootNode;
	private DefaultMutableTreeNode	parentNode;
	private DefaultMutableTreeNode	childNode;
	private JTree										tree;
	private DefaultTreeModel				treeModel;
	private JTable									table;
	private List<AccountGroup>			groupPatners;
	private List<Customer>					customersTemp;
	private Point										point;
	private List<Customer>					listCustomers;
	private List<PointTransaction>	pointTransactions;
	private List<CustomerUsePoint>	customerUsePoints;

	public PanelManagePartnerPoint() {
		try {
			String nameOrganization = AccountModelManager.getInstance().getNameByLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
			rootNode = new DefaultMutableTreeNode(nameOrganization);
		} catch (Exception e1) {
			rootNode = new DefaultMutableTreeNode();
			e1.printStackTrace();
		}
		treeModel = new DefaultTreeModel(rootNode);

		groupPatners = new ArrayList<AccountGroup>();
		try {
			List<AccountGroup> accountGroups = AccountModelManager.getInstance().getGroupDetailByPath(CustomerModelManager.getInstance().getRootGroupCustomer().getPath()).getChildren();
			if (accountGroups != null)
				groupPatners.addAll(accountGroups);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		try {
			AccountGroup accountGroupCustomer = CustomerModelManager.getInstance().getRootGroupCustomer();
			List<Customer> customersAll = CustomerModelManager.getInstance().getCustomers(false);
			customersTemp = new ArrayList<Customer>();
			for (Customer c : customersAll) {
				boolean flag = false;
				List<AccountMembership> accountMemberships = AccountModelManager.getInstance().findMembershipByAccountLoginId(c.getLoginId());
				for (AccountMembership accMembership : accountMemberships) {
					if (accMembership.getGroupPath().contains(accountGroupCustomer.getPath())) {
						flag = true;
						break;
					}
				}
				if (!flag)
					customersTemp.add(c);
			}
		} catch (Exception e3) {
			e3.printStackTrace();
		}

		for (int i = 0; i < groupPatners.size(); i++) {
			parentNode = new DefaultMutableTreeNode(groupPatners.get(i));
			try {
				List<Customer> customersInGroup = CustomerModelManager.getInstance().findCustomersByAccountGroup(groupPatners.get(i));
				if(customersInGroup != null){
					for(int j = 0; j < customersInGroup.size(); j++){
						childNode = new DefaultMutableTreeNode(customersInGroup.get(j));
						parentNode.insert(childNode, j);
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
			rootNode.insert(parentNode, i);
		}

		for (int i = 0; i < customersTemp.size(); i++) {
			int index = rootNode.getChildCount();
			childNode = new DefaultMutableTreeNode(customersTemp.get(i));
			rootNode.insert(childNode, index);
			index = +1;
		}
		customerUsePoints = new ArrayList<CustomerUsePoint>();

		tree = new JTree(treeModel);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);
		tree.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tree.setCellRenderer(new MyTreeCellRenderer());

		scrollPaneTree = new JScrollPane();
		scrollPaneTree.setViewportView(tree);

		tableModel = new CustomModelTable(new ArrayList<CustomerUsePoint>());
		table = new JTable();
		table.setModel(tableModel);
		table.setRowHeight(23);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

		scrollPaneTable = new JScrollPane();
		scrollPaneTable.setViewportView(table);
		scrollPaneTable.getViewport().setBackground(Color.white);

		getContentPanel();

		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				actionMouseClickNodeTree();
				tableModel.setData(customerUsePoints);
			}
		});
		
		table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer(){
			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus, int row,
					int column) {
				JLabel label = new JLabel();
				label.setText(value.toString());
				label.setFont(new Font("Tahoma", Font.PLAIN, 14));
				label.setHorizontalAlignment(JLabel.RIGHT);
				if(isSelected){
				  label.setOpaque(true);
				  label.setBackground(table.getSelectionBackground());
				}
				return label;
				}
	    });
		
		table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer(){
			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus, int row,
					int column) {
				JLabel label = new JLabel();
				label.setText(value.toString());
				label.setFont(new Font("Tahoma", Font.PLAIN, 14));
				label.setHorizontalAlignment(JLabel.RIGHT);
				if(isSelected){
				  label.setOpaque(true);
				  label.setBackground(table.getSelectionBackground());
				}
				return label;
				}
	    });
		
		table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer(){
			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus, int row,
					int column) {
				JLabel label = new JLabel();
				label.setText(value.toString());
				label.setFont(new Font("Tahoma", Font.PLAIN, 14));
				label.setHorizontalAlignment(JLabel.RIGHT);
				if(isSelected){
				  label.setOpaque(true);
				  label.setBackground(table.getSelectionBackground());
				}
				return label;
				}
	    });
	}

	private void actionMouseClickNodeTree() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null)
			return;
		Object nodeInfo = node.getUserObject();
		if (node != null && nodeInfo != null) {
			customerUsePoints.clear();
			if(node.isRoot()){
				listCustomers = getPartnersInGroup(null);
				for (int i = 0; i < listCustomers.size(); i++) {
					double totalPoint = 0;
					double usedPoint = 0;
					try {
						String nameCustomer = listCustomers.get(i).getName();
						point = CustomerModelManager.getInstance().getPointByCustomerId(listCustomers.get(i).getId());
						if(point != null){
							pointTransactions = CustomerModelManager.getInstance().findPointTransactionByPointId(point.getId());							
							for (PointTransaction pt : pointTransactions) {
							  if(pt.getPoint()>0){
	                totalPoint += pt.getPoint();
	                }else {
	                  usedPoint += pt.getPoint()*(-1);
	                }
							}
						}
						CustomerUsePoint customerUsePoint = new CustomerUsePoint(listCustomers.get(i).getLoginId(), nameCustomer, totalPoint, usedPoint, totalPoint-usedPoint);
						customerUsePoints.add(customerUsePoint);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			} else {
				if(nodeInfo instanceof AccountGroup){
					if (!node.isLeaf()) {
						AccountGroup accountGroup = (AccountGroup) nodeInfo;
						listCustomers = getPartnersInGroup(accountGroup);
						for (int i = 0; i < listCustomers.size(); i++) {
							double totalPoint = 0;
							double usedPoint = 0;
							try {
								String nameCustomer = listCustomers.get(i).getName();
								point = CustomerModelManager.getInstance().getPointByCustomerId(listCustomers.get(i).getId());
								if(point != null){
									pointTransactions = CustomerModelManager.getInstance().findPointTransactionByPointId(point.getId());							
									for (PointTransaction pt : pointTransactions) {
									  if(pt.getPoint()>0){
			                totalPoint += pt.getPoint();
			                }else {
			                  usedPoint += pt.getPoint()*(-1);
			                }
									}
								}
								CustomerUsePoint customerUsePoint = new CustomerUsePoint(listCustomers.get(i).getLoginId(), nameCustomer, totalPoint, usedPoint, totalPoint-usedPoint);
								customerUsePoints.add(customerUsePoint);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				} else {
					if(nodeInfo instanceof Customer){
						Customer customer = (Customer) nodeInfo;
						double totalPoint = 0;
						double usedPoint = 0;
						try {
							point = CustomerModelManager.getInstance().getPointByCustomerId(customer.getId());
							if(point != null){
								pointTransactions = CustomerModelManager.getInstance().findPointTransactionByPointId(point.getId());							
								for (PointTransaction pt : pointTransactions) {
								  if(pt.getPoint()>0){
								  totalPoint += pt.getPoint();
								  }else {
	                  usedPoint += pt.getPoint()*(-1);
	                }
								}
							}
							CustomerUsePoint customerUsePoint = new CustomerUsePoint(customer.getLoginId(), customer.getName(), totalPoint, usedPoint, totalPoint-usedPoint);
							customerUsePoints.add(customerUsePoint);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}
	}

	private List<Customer> getPartnersInGroup(AccountGroup accountGroup) {
		List<Customer> customers = new ArrayList<Customer>();
		if(accountGroup == null){
			try {
				AccountGroup accountGroupCustomer = CustomerModelManager.getInstance().getRootGroupCustomer();
				List<Customer> customersAll = CustomerModelManager.getInstance().getCustomers(false);
				for (Customer c : customersAll) {
					boolean flag = false;
					List<AccountMembership> accountMemberships = AccountModelManager.getInstance().findMembershipByAccountLoginId(c.getLoginId());
					for (AccountMembership accMembership : accountMemberships) {
						if (accMembership.getGroupPath().contains(accountGroupCustomer.getPath())) {
							flag = true;
							break;
						}
					}
					if (!flag)
						customers.add(c);
				}
			} catch (Exception e3) {
			}
		} else {
			try {
				List<AccountMembership> accountMemberships = AccountModelManager.getInstance().findMembershipByGroupPath(accountGroup.getPath());
				if (accountMemberships.size() > 0)
					for (AccountMembership am : accountMemberships) {
						Customer c = CustomerModelManager.getInstance().getBydLoginId(am.getLoginId());
						if (c != null) {
							customers.add(c);
						}
					}
			} catch (Exception ex) {
			}
		}
		return customers;
	}
	
	private void getContentPanel() {
		setOpaque(false);
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING,
				layout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createSequentialGroup().addComponent(scrollPaneTree, GroupLayout.PREFERRED_SIZE, 327, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(scrollPaneTable, GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(scrollPaneTree, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
										.addComponent(scrollPaneTable, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)).addContainerGap()));

	}

	@Override
	public void Save() throws Exception {
		//TODO: Save PanelManagerPartnerPoint nothing ...
	}

	/*
	 * Model Table
	 */

	protected class CustomModelTable extends DefaultTableModel {
		private String[]	header	= { "Mã đối tác", "Đối tác", "Tổng điểm", "Điểm đã dùng", "Điểm còn lại" };

		public CustomModelTable(List<CustomerUsePoint> listPointTransaction) {
			dataVector = convertToVector(listPointTransaction.toArray());
		}
		
		public void setData(List<CustomerUsePoint> listPointTransaction){
			dataVector = convertToVector(listPointTransaction.toArray());
			fireTableDataChanged();
		}

		@Override
		public int getColumnCount() {
			return header == null ? 0 : header.length;
		}

		@Override
		public String getColumnName(int column) {
			return header[column];
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}

		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
				case 0:
					try {
						return ((CustomerUsePoint) dataVector.get(row));
					} catch (Exception e) {
						return null;
					}
				case 1:
					try {
						return ((CustomerUsePoint) dataVector.get(row)).getNameCustomer();
					} catch (Exception e) {
						return "";
					}

				case 2:
					try {
						return MyDouble.valueOf(((CustomerUsePoint) dataVector.get(row)).getTotalPoint());
					} catch (Exception e) {
						return "0";
					}

				case 3:
					try {
						return MyDouble.valueOf(((CustomerUsePoint) dataVector.get(row)).getUsedPoint());
					} catch (Exception e) {
						return "0";
					}

				case 4:
					try {
						return MyDouble.valueOf(((CustomerUsePoint) dataVector.get(row)).getCurrentPoint());
					} catch (Exception e) {
						return "0";
					}

				default:
					return null;
			}
		}
	}
	
	/*
	 * Entity object model
	 */

	protected class CustomerUsePoint {
		private String	loginIdCustomer;
		private String	nameCustomer;
		private double	totalPoint;
		private double	usedPoint;
		private double	currentPoint;

		public CustomerUsePoint() {
		}

		public CustomerUsePoint(String loginIdCustomer, String nameCustomer, double totalPoint, double usedPoint, double currentPoint) {
			this.loginIdCustomer = loginIdCustomer;
			this.nameCustomer = nameCustomer;
			this.totalPoint = totalPoint;
			this.usedPoint = usedPoint;
			this.currentPoint = currentPoint;
		}

		public String getLoginIdCustomer() {
			return loginIdCustomer;
		}

		public void setLoginIdCustomer(String loginIdCustomer) {
			this.loginIdCustomer = loginIdCustomer;
		}

		public String getNameCustomer() {
			return nameCustomer;
		}

		public void setNameCustomer(String nameCustomer) {
			this.nameCustomer = nameCustomer;
		}

		public double getTotalPoint() {
			return totalPoint;
		}

		public void setTotalPoint(double totalPoint) {
			this.totalPoint = totalPoint;
		}

		public double getUsedPoint() {
			return usedPoint;
		}

		public void setUsedPoint(double usedPoint) {
			this.usedPoint = usedPoint;
		}

		public double getCurrentPoint() {
			return currentPoint;
		}

		public void setCurrentPoint(double currentPoint) {
			this.currentPoint = currentPoint;
		}

		@Override
		public String toString() {
			return loginIdCustomer.toString();
		}
	}
	
	/*
	 * Override CellRender set font when mouse click
	 */
	
	protected class MyTreeCellRenderer extends DefaultTreeCellRenderer {
	  private ImageIcon iconRoot;
	  private ImageIcon iconParent;
	  private ImageIcon iconLeaf;
	  
		public MyTreeCellRenderer() {
	    iconRoot = new ImageIcon(HomeScreen.class.getResource("icon/icon1.png"));
	    iconParent = new ImageIcon(HomeScreen.class.getResource("icon/usernhom4.png"));
	    iconLeaf = new ImageIcon(HomeScreen.class.getResource("icon/user.png"));
		}
		
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

				DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) value;
        if (tree.getModel().getRoot().equals(nodo)) {
            setIcon(iconRoot);
        } else if (nodo.getChildCount() > 0) {
            setIcon(iconParent);
        } else {
            setLeafIcon(iconLeaf);
        }

			setBackgroundSelectionColor(UIManager.getColor("Panel.background"));
			setTextSelectionColor(UIManager.getColor("Panel.foreground"));

			if (selected) {
				setFont(getFont().deriveFont(Font.BOLD));
			} else
				setFont(getFont().deriveFont(Font.PLAIN));
			return this;
		}
	}
}