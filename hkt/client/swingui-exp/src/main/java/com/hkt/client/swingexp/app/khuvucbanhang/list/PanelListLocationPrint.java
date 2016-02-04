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
import com.hkt.client.swingexp.app.khuvucbanhang.PrintMachineLocation;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.restaurant.entity.Location;

@SuppressWarnings("serial")
public class PanelListLocationPrint extends JPanel implements IDialogResto {

	private JPanel panelLeft, panelRight, panelCenter;
	private JPanel panelButton;
	private JList<Location> listLocationLeft, listLocationRight;
	private ExtendJButton btnAddOne, btnAddAll, btnRemoveOne, btnRemoveAll;
	private JScrollPane scrollListLeft, scrollListRight;
	private DefaultListModel<Location> listModelLocation, listSelectedLocation;
	private Location location;
	private List<Location> listSelected = new ArrayList<Location>();
	private IDialogResto iDialogResto;
	private String str;

	/**
	 * Create the panel.
	 */
	public PanelListLocationPrint() {
		this.setLayout(new BorderLayout(6, 0));
		setOpaque(false);
		
		init();
		setModelListView();		
		
		listLocationLeft.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				location = listModelLocation.get(listLocationLeft.getSelectedIndex());
				str = "left";
			}
			
		});
		
		listLocationRight.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				location = listSelectedLocation.get(listLocationRight.getSelectedIndex());
				str = "right";
			}
			
		});
		
		btnAddOne.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				addLocation();
			}
		});
		
		btnAddAll.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				addAllLocations();
			}
		});
		
		btnRemoveOne.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				removeLocation();
			}
		});
		
		btnRemoveAll.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAllLocations();
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
		btnAddAll.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
		btnAddOne.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
		btnRemoveOne.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
		btnRemoveAll.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));

		listLocationLeft = new JList<Location>();
		listLocationRight = new JList<Location>();
		listLocationLeft.setName("listLeft");
		listLocationRight.setName("listRight");
		
		scrollListLeft = new JScrollPane();
		scrollListRight = new JScrollPane();
		scrollListLeft.setViewportView(listLocationLeft);
		scrollListRight.setViewportView(listLocationRight);

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
			listModelLocation = new DefaultListModel<Location>();
			listSelectedLocation = new DefaultListModel<Location>();
			List<Location> list = RestaurantModelManager.getInstance().getLocations();
			list.add(RestaurantModelManager.getInstance().getLocationOther());
			list.add(RestaurantModelManager.getInstance().getLocationPaymentAfter());
			for (Location loca : list) {
				listModelLocation.addElement(loca);
			}
			listLocationLeft.setModel(listModelLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addLocation(){		
		if(location != null && str == "left")
			listSelectedLocation.addElement(location);
		listLocationRight.setModel(listSelectedLocation);
		listModelLocation.removeElement(location);
		location = null;
	}
	
	private void addAllLocations(){
		if(listModelLocation.getSize() != 0){
			for(int i = 0; i < listModelLocation.getSize(); i++)	
				listSelectedLocation.addElement(listModelLocation.getElementAt(i));
			listLocationRight.setModel(listSelectedLocation);
			listModelLocation.removeAllElements();		
		}
	}
	
	private void removeLocation(){
		if(location != null && str == "right")
			listModelLocation.addElement(location);
		listLocationLeft.setModel(listModelLocation);
		listSelectedLocation.removeElement(location);
		location = null;
	}
	
	private void removeAllLocations(){
		if(listSelectedLocation.getSize() != 0){
			for(int i = 0; i < listSelectedLocation.getSize(); i++)	
				listModelLocation.addElement(listSelectedLocation.getElementAt(i));
			listLocationLeft.setModel(listModelLocation);
			listSelectedLocation.removeAllElements();	
		}
	}

	public void getData(List<Location> listLocas){
		this.listSelected = listLocas;
		for(Location loca : listSelected){
			listSelectedLocation.addElement(loca);
		}
		listLocationRight.setModel(listSelectedLocation);
		
		List<Location> listLocaCurrent;
		try {
			listLocaCurrent = RestaurantModelManager.getInstance().getLocations();
			listLocaCurrent.add(RestaurantModelManager.getInstance().getLocationOther());
			listLocaCurrent.add(RestaurantModelManager.getInstance().getLocationPaymentAfter());
			if(listSelected.size() != listLocaCurrent.size()){
				for(Location loca : listSelected){
					for(int i = 0; i < listLocaCurrent.size(); i++){
						if(listLocaCurrent.get(i).getId() == loca.getId())
							listLocaCurrent.remove(i);
					}
				}
			} else listLocaCurrent.clear();
			listModelLocation.removeAllElements();
			for(Location loca : listLocaCurrent){
				listModelLocation.addElement(loca);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		listLocationLeft.setModel(listModelLocation);
	}
	
	public void setParentForm(IDialogResto iDialogResto){
		this.iDialogResto = iDialogResto;
	}
	
	@Override
	public void Save() throws Exception {
		if(listSelectedLocation.getSize() != 0){
			listSelected.clear();
			for(int i = 0; i < listSelectedLocation.getSize(); i++)
				listSelected.add(listSelectedLocation.getElementAt(i));			
		} else {
			listSelected = new ArrayList<Location>();
		}
		((PrintMachineLocation)iDialogResto).loadTable(listSelected);
		((JDialog)getRootPane().getParent()).dispose();
	}
}
