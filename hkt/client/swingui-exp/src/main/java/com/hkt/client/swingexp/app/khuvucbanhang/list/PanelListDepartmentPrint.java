package com.hkt.client.swingexp.app.khuvucbanhang.list;
/**
 * author duy khanh
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialogPlus;
import com.hkt.client.swingexp.app.khuvucbanhang.PrintMachineDepartment;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.account.entity.AccountGroup;

@SuppressWarnings("serial")
public class PanelListDepartmentPrint extends JPanel implements IDialogResto{
	private JPanel panelLeft, panelRight, panelCenter;
	private JPanel panelButton;
	private JList<AccountGroup> listAccGroupLeft, listAccGroupRight;
	private ExtendJButton btnAddOne, btnAddAll, btnRemoveOne, btnRemoveAll;
	private JScrollPane scrollListLeft, scrollListRight;
	private DefaultListModel<AccountGroup> listModelAccountGroup, listSelectedAccountGroup;
	private AccountGroup accountGroup;
	private List<AccountGroup> listSelected = new ArrayList<AccountGroup>();
	private IDialogResto iDialogResto;
	private String str;
	private AccountGroup groupDepartment;
	private AccountGroup groupHKT;
	
	public PanelListDepartmentPrint() {
		this.setLayout(new BorderLayout(6, 0));
		setOpaque(false);	
		init();
		setModelListView();		
		
		
		listAccGroupLeft.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				accountGroup = listModelAccountGroup.get(listAccGroupLeft.getSelectedIndex());
				str = "left";
			}
			
		});
		
		listAccGroupRight.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				accountGroup = listSelectedAccountGroup.get(listAccGroupRight.getSelectedIndex());
				str = "right";
			}
			
		});
		
		btnAddOne.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				addProduct();
			}
		});
		
		btnAddAll.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				addAllProducts();
			}
		});
		
		btnRemoveOne.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				removeProduct();
			}
		});
		
		btnRemoveAll.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAllProducts();
			}
		});
	}

	private void init() {
		panelLeft = new JPanel();
		panelRight = new JPanel();
		panelCenter = new JPanel();
		panelButton = new JPanel();
		
		panelCenter.setOpaque(false);
		panelButton.setOpaque(false);

		panelLeft.setLayout(new BorderLayout(0, 0));
		panelCenter.setLayout(new GridLayout(3, 0, 50, -50));
		panelRight.setLayout(new BorderLayout(0, 0));
		panelButton.setLayout(new GridLayout(4, 0, 0, 8));

		panelCenter.setPreferredSize(new Dimension(80, 100));

		btnAddOne = new ExtendJButton(">");
		btnAddAll = new ExtendJButton(">>");
		btnRemoveOne = new ExtendJButton("<");
		btnRemoveAll = new ExtendJButton("<<");
		btnAddOne.setName("btnAddOne");
		btnAddAll.setName("btnAddAll");
		btnRemoveOne.setName("btnRemoveOne");
		btnRemoveAll.setName("btnRemoveAll");
		
		btnAddAll.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
		btnAddOne.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
		btnRemoveOne.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
		btnRemoveAll.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));

		listAccGroupLeft = new JList<AccountGroup>();
		listAccGroupRight = new JList<AccountGroup>();
		listAccGroupLeft.setName("listLeft");
		listAccGroupRight.setName("listRight");

		scrollListLeft = new JScrollPane();
		scrollListRight = new JScrollPane();
		scrollListLeft.setViewportView(listAccGroupLeft);
		scrollListRight.setViewportView(listAccGroupRight);

		if  (Toolkit.getDefaultToolkit().getScreenSize().width <= 1024) {
			scrollListLeft.setPreferredSize(new Dimension(370, 80));
			scrollListRight.setPreferredSize(new Dimension(370, 80));
			} else {
				scrollListLeft.setPreferredSize(new Dimension(430, 80));
				scrollListRight.setPreferredSize(new Dimension(430, 80));
				}

		panelCenter.add(panelButton);
		panelButton.add(btnAddAll);
		panelButton.add(btnAddOne);
		panelButton.add(btnRemoveOne);
		panelButton.add(btnRemoveAll);

		panelRight.add(scrollListRight, BorderLayout.CENTER);
		panelLeft.add(scrollListLeft, BorderLayout.CENTER);

		this.add(panelLeft, BorderLayout.LINE_START);
		this.add(panelCenter, BorderLayout.CENTER);
		this.add(panelRight, BorderLayout.LINE_END);
	}

	private void setModelListView(){
		try {
			
			try {
				groupHKT = AccountModelManager.getInstance().getRootGroupDetail()
						.getChildren().get(0);

				groupDepartment = new AccountGroup();
				groupDepartment.setLabel("Nhóm Phòng ban");
				groupDepartment.setName(AccountModelManager.Department);
				groupDepartment.setOwner("hkt");
				groupDepartment.setParent(groupHKT);
				groupDepartment.setDescription("Nhóm Phòng ban group hkt");

				if (AccountModelManager.getInstance().getGroupByPath(
						groupHKT.getPath() + "/" + AccountModelManager.Department) == null) {
					groupDepartment = AccountModelManager.getInstance().saveGroup(
							groupDepartment);
				} else {
					groupDepartment = AccountModelManager.getInstance()
							.getGroupByPath(groupDepartment.getPath());
				}
			} catch (Exception ex) {
			}

			listModelAccountGroup = new DefaultListModel<AccountGroup>();
			listSelectedAccountGroup = new DefaultListModel<AccountGroup>();
			List<AccountGroup> list =  AccountModelManager.getInstance()
					.getAllChildrensByPath(groupDepartment.getPath());
			for (AccountGroup acc : list) {
				listModelAccountGroup.addElement(acc);
			}
			listAccGroupLeft.setModel(listModelAccountGroup);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addProduct(){		
		if(accountGroup != null && str == "left")
		listSelectedAccountGroup.addElement(accountGroup);
		
		listAccGroupRight.setModel(listSelectedAccountGroup);
		
		listModelAccountGroup.removeElement(accountGroup);
		
		accountGroup = null;
		
	}
	
	private void addAllProducts(){
		if(listModelAccountGroup.getSize() != 0){
			for(int i = 0; i < listModelAccountGroup.getSize(); i++)	
			listSelectedAccountGroup.addElement(listModelAccountGroup.getElementAt(i));
			listAccGroupRight.setModel(listSelectedAccountGroup);
			listModelAccountGroup.removeAllElements();		
		}
	}
	
	private void removeProduct(){
		if(accountGroup != null && str == "right"){	
			listModelAccountGroup.addElement(accountGroup);
			
			listAccGroupLeft.setModel(listModelAccountGroup);
			
			listSelectedAccountGroup.removeElement(accountGroup);
			accountGroup = null;	
		}
	}
	
	private void removeAllProducts(){
		if(listSelectedAccountGroup.getSize() != 0){
			for(int i = 0; i < listSelectedAccountGroup.getSize(); i++)	
				listModelAccountGroup.addElement(listSelectedAccountGroup.getElementAt(i));
			listAccGroupLeft.setModel(listModelAccountGroup);
			listSelectedAccountGroup.removeAllElements();	
		}
	}	
	
	public void setParentForm(IDialogResto iDialogResto){
		this.iDialogResto = iDialogResto;
	}
	
	public void getData(List<AccountGroup> accountGroup){
		this.listSelected = accountGroup;
		for(AccountGroup acc : listSelected){
			listSelectedAccountGroup.addElement(acc);
		}
		listAccGroupRight.setModel(listSelectedAccountGroup);
		
		List<AccountGroup> listTableCurrent;
		try {
			listTableCurrent =  AccountModelManager.getInstance().getAllChildrensByPath(groupDepartment.getPath());
			if(listSelected.size() != listTableCurrent.size()){
				for(AccountGroup acc : listSelected){
					for(int i = 0; i < listTableCurrent.size(); i++){
						if(listTableCurrent.get(i).getId() == acc.getId())
							listTableCurrent.remove(i);
					}
				}
			} else listTableCurrent.clear();
			listModelAccountGroup.removeAllElements();
			for(AccountGroup t : listTableCurrent){
				listModelAccountGroup.addElement(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		listAccGroupLeft.setModel(listModelAccountGroup);
	}
	
	
	@Override
	public void Save() throws Exception {
		if(listSelectedAccountGroup.getSize() != 0){
			listSelected.clear();
			for(int i = 0; i < listSelectedAccountGroup.getSize(); i++)
				listSelected.add(listSelectedAccountGroup.getElementAt(i));
		} else {
			listSelected = new ArrayList<AccountGroup>();
		}
		((PrintMachineDepartment)iDialogResto).loadTable(listSelected);
		((JDialog)getRootPane().getParent()).dispose();
		
	}
}
