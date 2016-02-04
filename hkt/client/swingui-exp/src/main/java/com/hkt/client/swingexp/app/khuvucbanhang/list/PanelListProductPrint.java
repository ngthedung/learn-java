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
import com.hkt.client.swingexp.app.khuvucbanhang.PrintMachineProducts;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.product.entity.ProductTag;

@SuppressWarnings("serial")
public class PanelListProductPrint extends JPanel implements IDialogResto {

	private JPanel panelLeft, panelRight, panelCenter;
	private JPanel panelButton;
	private JList<ProductTag> listProductsLeft, listProductsRight;
	private ExtendJButton btnAddOne, btnAddAll, btnRemoveOne, btnRemoveAll;
	private JScrollPane scrollListLeft, scrollListRight;
	private DefaultListModel<ProductTag> listModelProducts, listSelectedProducts;
	private ProductTag productTag;
	private List<ProductTag> listSelected = new ArrayList<ProductTag>();
	private IDialogResto iDialogResto;
	private String str;

	/**
	 * Create the panel.
	 */
	public PanelListProductPrint() {
		this.setLayout(new BorderLayout(6, 0));
		setOpaque(false);
		
		init();
		setModelListView();		
		
		listProductsLeft.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				productTag = listModelProducts.get(listProductsLeft.getSelectedIndex());
				str = "left";
			}
			
		});
		
		listProductsRight.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				productTag = listSelectedProducts.get(listProductsRight.getSelectedIndex());
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

		listProductsLeft = new JList<ProductTag>();
		listProductsRight = new JList<ProductTag>();
		listProductsLeft.setName("listLeft");
		listProductsRight.setName("listRight");

		scrollListLeft = new JScrollPane();
		scrollListRight = new JScrollPane();
		scrollListLeft.setViewportView(listProductsLeft);
		scrollListRight.setViewportView(listProductsRight);

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
			listModelProducts = new DefaultListModel<ProductTag>();
			listSelectedProducts = new DefaultListModel<ProductTag>();
			List<ProductTag> list = ProductModelManager.getInstance().getProductTags();
			for (ProductTag proTag : list) {
				listModelProducts.addElement(proTag);
			}
			listProductsLeft.setModel(listModelProducts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addProduct(){		
		if(productTag != null && str == "left")
			listSelectedProducts.addElement(productTag);
		listProductsRight.setModel(listSelectedProducts);
		listModelProducts.removeElement(productTag);
		productTag = null;
	}
	
	private void addAllProducts(){
		if(listModelProducts.getSize() != 0){
			for(int i = 0; i < listModelProducts.getSize(); i++)	
				listSelectedProducts.addElement(listModelProducts.getElementAt(i));
			listProductsRight.setModel(listSelectedProducts);
			listModelProducts.removeAllElements();		
		}
	}
	
	private void removeProduct(){
		if(productTag != null && str == "right")
			listModelProducts.addElement(productTag);
		listProductsLeft.setModel(listModelProducts);
		listSelectedProducts.removeElement(productTag);
		productTag = null;
	}
	
	private void removeAllProducts(){
		if(listSelectedProducts.getSize() != 0){
			for(int i = 0; i < listSelectedProducts.getSize(); i++)	
				listModelProducts.addElement(listSelectedProducts.getElementAt(i));
			listProductsLeft.setModel(listModelProducts);
			listSelectedProducts.removeAllElements();	
		}
	}	
	
	public void setParentForm(IDialogResto iDialogResto){
		this.iDialogResto = iDialogResto;
	}
	
	public void getData(List<ProductTag> listProTag){
		this.listSelected = listProTag;
		for(ProductTag proTag : listSelected){
			listSelectedProducts.addElement(proTag);
		}
		listProductsRight.setModel(listSelectedProducts);
		
		List<ProductTag> listProTagCurrent;
		try {
			listProTagCurrent = ProductModelManager.getInstance().getProductTags();
			if(listSelected.size() != listProTagCurrent.size()){
				for(ProductTag proTag : listSelected){
					for(int i = 0; i < listProTagCurrent.size(); i++){
						if(listProTagCurrent.get(i).getId() == proTag.getId())
							listProTagCurrent.remove(i);
					}
				}
			} else listProTagCurrent.clear();
			listModelProducts.removeAllElements();
			for(ProductTag proTag : listProTagCurrent){
				listModelProducts.addElement(proTag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		listProductsLeft.setModel(listModelProducts);
	}
	
	@Override
	public void Save() throws Exception {
		if(listSelectedProducts.getSize() != 0){
			listSelected.clear();
			for(int i = 0; i < listSelectedProducts.getSize(); i++)
				listSelected.add(listSelectedProducts.getElementAt(i));
		} else {
			listSelected = new ArrayList<ProductTag>();
		}
		((PrintMachineProducts)iDialogResto).loadTable(listSelected);
		((JDialog)getRootPane().getParent()).dispose();
	}

}
