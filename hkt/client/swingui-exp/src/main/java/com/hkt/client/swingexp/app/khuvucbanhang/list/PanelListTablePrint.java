package com.hkt.client.swingexp.app.khuvucbanhang.list;

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
import com.hkt.client.swingexp.app.khuvucbanhang.PrintMachineTable;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.restaurant.entity.Table;

@SuppressWarnings("serial")
public class PanelListTablePrint extends JPanel implements IDialogResto {

	private JPanel panelLeft, panelRight, panelCenter;
	private JPanel panelButton;
	private JList<Table> listTablesLeft, listTablesRight;
	private ExtendJButton btnAddOne, btnAddAll, btnRemoveOne, btnRemoveAll;
	private JScrollPane scrollListLeft, scrollListRight;
	private DefaultListModel<Table> listModelTables, listSelectedTables;
	private Table table;
	private List<Table> listSelected = new ArrayList<Table>();
	private IDialogResto iDialogResto;
	private String str;

	/**
	 * Create the PanelListTablePrint.
	 */
	public PanelListTablePrint() {
		this.setLayout(new BorderLayout(6, 0));
		setOpaque(false);
		
		init();
		setModelListView();		
		
		listTablesLeft.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table = listModelTables.get(listTablesLeft.getSelectedIndex());
				str = "left";
			}
			
		});
		
		listTablesRight.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table = listModelTables.get(listTablesRight.getSelectedIndex());
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
		panelCenter.setLayout(new GridLayout(3, 1, 10, -50));
		panelRight.setLayout(new BorderLayout(0, 0));
		panelButton.setLayout(new GridLayout(4, 30, 0, 10));

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

		listTablesLeft = new JList<Table>();
		listTablesRight = new JList<Table>();
		listTablesLeft.setName("listLeft");
		listTablesRight.setName("listRight");

		scrollListLeft = new JScrollPane();
		scrollListRight = new JScrollPane();
		scrollListLeft.setViewportView(listTablesLeft);
		scrollListRight.setViewportView(listTablesRight);

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
			listModelTables = new DefaultListModel<Table>();
			listSelectedTables = new DefaultListModel<Table>();
			List<Table> list = RestaurantModelManager.getInstance().getTables();
			for (Table t : list) {
				listModelTables.addElement(t);
			}
			listTablesLeft.setModel(listModelTables);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addProduct(){		
		if(table != null && str == "left")
			listSelectedTables.addElement(table);
		listTablesRight.setModel(listSelectedTables);
		listModelTables.removeElement(table);
		table = null;
	}
	
	private void addAllProducts(){
		if(listModelTables.getSize() != 0){
			for(int i = 0; i < listModelTables.getSize(); i++)	
				listSelectedTables.addElement(listModelTables.getElementAt(i));
			listTablesRight.setModel(listSelectedTables);
			listModelTables.removeAllElements();		
		}
	}
	
	private void removeProduct(){
		if(table != null && str == "right")
			listModelTables.addElement(table);
		listTablesLeft.setModel(listModelTables);
		listSelectedTables.removeElement(table);
		table = null;
	}
	
	private void removeAllProducts(){
		if(listSelectedTables.getSize() != 0){
			for(int i = 0; i < listSelectedTables.getSize(); i++)	
				listModelTables.addElement(listSelectedTables.getElementAt(i));
			listTablesLeft.setModel(listModelTables);
			listSelectedTables.removeAllElements();	
		}
	}	
	
	public void setParentForm(IDialogResto iDialogResto){
		this.iDialogResto = iDialogResto;
	}
	
	public void getData(List<Table> listTable){
		this.listSelected = listTable;
		for(Table t : listSelected){
			listSelectedTables.addElement(t);
		}
		listTablesRight.setModel(listSelectedTables);
		
		List<Table> listTableCurrent;
		try {
			listTableCurrent = RestaurantModelManager.getInstance().getTables();
			if(listSelected.size() != listTableCurrent.size()){
				for(Table t : listSelected){
					for(int i = 0; i < listTableCurrent.size(); i++){
						if(listTableCurrent.get(i).getId() == t.getId())
							listTableCurrent.remove(i);
					}
				}
			} else listTableCurrent.clear();
			listModelTables.removeAllElements();
			for(Table t : listTableCurrent){
				listModelTables.addElement(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		listTablesLeft.setModel(listModelTables);
	}
	
	@Override
	public void Save() throws Exception {
		if(listSelectedTables.getSize() != 0){
			listSelected.clear();
			for(int i = 0; i < listSelectedTables.getSize(); i++)
				listSelected.add(listSelectedTables.getElementAt(i));
		} else {
			listSelected = new ArrayList<Table>();
		}
		((PrintMachineTable)iDialogResto).loadTable(listSelected);
		((JDialog)getRootPane().getParent()).dispose();
	}

}
