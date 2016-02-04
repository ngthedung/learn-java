package com.hkt.client.swingexp.app.khohang;

/**
 * author Duy Khanh
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialogPlus;
import com.hkt.client.swingexp.app.core.WarehousesJComboBox;
import com.hkt.client.swingexp.app.khuvucbanhang.list.TableModelLocation;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.LocationAttribute;
import com.hkt.module.warehouse.entity.Warehouse;


@SuppressWarnings("serial")
public class PanelInstituteWarehouse extends JPanel implements IDialogResto {

	public static String permission;
	private JPanel panelLeft, panelRight, panelCenter, panelTop, panelRightTop, panelLeftTop;
	private JPanel panelButton;
	private ExtendJButton btnAddOne, btnAddAll, btnRemoveOne, btnRemoveAll;
	private JScrollPane scrollListLeft, scrollListRight;
	private TableModelLocation pnTableLeft, pnTableRight;
	private Location location;
	private List<Location> listLocationLeft = new ArrayList<Location>();
	private List<Location> listLocationRight = new ArrayList<Location>();
//	private List<Employee> listEmployee = new ArrayList<Employee>();
	private IDialogResto iDialogResto;
	private javax.swing.JTable table1;
	private javax.swing.JTable table2;
	private javax.swing.JScrollPane jScrollPane1 =  new javax.swing.JScrollPane();;
	private javax.swing.JScrollPane jScrollPane2 =  new javax.swing.JScrollPane();;
	private javax.swing.JLabel lbWareHouseLeft, lbWareHouseRight;
	private WarehousesJComboBox cboWareHouseleft, cboWareHouseRight;
	private Warehouse wareHouse, wareHouseLeft, wareHouseRight ;
	private List<Warehouse> listWarehouse, listWarehouseLeft, listWarehouseRight;
	private List<Location> listLocationTempLeft, listLocationTempRight, listLocationDisplay, listLocationRemove ;
	private int flagMessage =1;
	public PanelInstituteWarehouse() {
		this.setLayout(new BorderLayout(6, 10));
		setOpaque(false);	
		init();		
		listLocationTempLeft = new ArrayList<Location>();
		listLocationTempRight = new ArrayList<Location>();
		wareHouse = new Warehouse();
		wareHouseLeft = new Warehouse();
		wareHouseRight = new Warehouse();

//		lấy danh sách warehouse đổ vào cboLeft	
		try {
			listWarehouseLeft = new ArrayList<Warehouse>();
			listWarehouseLeft =WarehouseModelManager.getInstance().findWarehouses();
			listWarehouseLeft.add(0, null);
			
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}	
		//lấy danh sách warehouse đổ vào cho cboRight
		try {
			listWarehouseRight = new ArrayList<Warehouse>();
			listWarehouseRight =WarehouseModelManager.getInstance().findWarehouses();
			
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}		
		//lấy ra danh sách location với dkien có locaAtt giống với cboWareHouseRight được chọn
		wareHouseRight = (Warehouse)cboWareHouseRight.getSelectedItem();	
		 try {
			listLocationRight = RestaurantModelManager.getInstance().findByAttribute("Warehouse",
			            ((Warehouse) cboWareHouseRight.getSelectedItem()).getWarehouseId());
			pnTableRight =new TableModelLocation(listLocationRight);
			table2 = new JTable(pnTableRight);
			table2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			table2.setRowHeight(23);
			table2.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14)); 
			table2.getColumnModel().getColumn(0).setMaxWidth(50);
			jScrollPane2.setViewportView(table2);
		} catch (Exception e1) {
		}
			
		//load tất cả location cho tableleft với giá tri mặc định của cboleft
		wareHouseLeft = (Warehouse)cboWareHouseleft.getSelectedItem();
			if (cboWareHouseleft.getSelectedIndex() == -1) {	              
		        try {
				listLocationLeft = RestaurantModelManager.getInstance().getLocations();
			   } catch (Exception e) {				
					e.printStackTrace();
				}
		        // Đẩy danh sách listLocation vào TableModelLocation	
		        listLocationRemove = new ArrayList<Location>();
		        for(int i =0;i<listLocationLeft.size();i++){
		        	
		        	Location loca = listLocationLeft.get(i);
		        	for(int j = 0;j<listWarehouseRight.size();j++){
			        	try {
			        		
			        		listLocationDisplay = new ArrayList<Location>();
			                
							listLocationDisplay = RestaurantModelManager.getInstance().findByAttribute("Warehouse", listWarehouseRight.get(j).getWarehouseId());
							for(int k=0;k < listLocationDisplay.size();k++){
								if(loca.getCode().equals(listLocationDisplay.get(k).getCode())){
									listLocationRemove.add(listLocationDisplay.get(k));
								}
							    }
						} catch (Exception e1) {							
							e1.printStackTrace();
						}
		        	}    	        	
		        }
		        
		        	for(int lc = 0;lc <listLocationRemove.size();lc++){
		        		listLocationLeft.remove(listLocationRemove.get(lc));
		        	}
		        	pnTableLeft = new TableModelLocation(listLocationLeft);
		        		            
		            table1 = new JTable(pnTableLeft);
		            table1.setFont(new Font("Tahoma", Font.PLAIN, 14));
				    table1.setRowHeight(23);
				    table1.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
				    table1.getColumnModel().getColumn(0).setMaxWidth(50);
		            jScrollPane1.setViewportView(table1);
		          }
		
		//Bỏ 1 khu vực của kho 
		btnRemoveOne.addActionListener(new java.awt.event.ActionListener() {
			@Override  
			public void actionPerformed(java.awt.event.ActionEvent e) {	        
		        buttonRemove();
		      }
		    });
		//Thêm 1 khu vực cho nhân viên
		btnAddOne.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				addProduct();
			}
		});
		//Thêm toàn bộ khu vực
		btnAddAll.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				addAllProducts();
			}
		});
		//Bỏ toàn bộ khu vực
		btnRemoveAll.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAllProducts();
			}
		});
	}
	private void init() {
		
		panelTop = new JPanel();
		panelLeft = new JPanel();
		panelRight = new JPanel();
		panelCenter = new JPanel();
		panelButton = new JPanel();
		
		panelRightTop = new JPanel();
		panelLeftTop = new JPanel();
		
		panelCenter.setOpaque(false);
		panelButton.setOpaque(false);
		panelTop.setOpaque(false);
	
		table1 = new JTable();
		table1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table1.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14)); 
	    table1.setRowHeight(23);

	    table2 = new JTable();
	    table2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table2.setRowHeight(23);
		table2.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14)); 
		
//		panelTop.setLayout(new GridLayout(1,0,50,10));
		panelLeftTop.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelRightTop.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		panelTop.setLayout(new BorderLayout());
		panelLeft.setLayout(new BorderLayout(0, 0));
		panelCenter.setLayout(new GridLayout(2, 1, 5, 0));
		panelRight.setLayout(new BorderLayout(0, 0));
		panelButton.setLayout(new GridLayout(4, 0, 0, 8));

		panelCenter.setPreferredSize(new Dimension(80, 100));
		panelTop.setPreferredSize(new Dimension(700,35));
		
		lbWareHouseRight = new JLabel("Kho");
		lbWareHouseRight.setPreferredSize(new Dimension(70,25));
		lbWareHouseRight.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cboWareHouseRight = new WarehousesJComboBox();
		cboWareHouseRight.setPreferredSize(new Dimension(269,25));
		cboWareHouseRight.setFont(new Font("Tahoma", Font.PLAIN, 14));
		listLocationRight = new ArrayList<Location>();
		
		lbWareHouseLeft = new JLabel("Kho");
		lbWareHouseLeft.setPreferredSize(new Dimension(70,25));
		lbWareHouseLeft.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cboWareHouseleft = new WarehousesJComboBox();
		cboWareHouseleft.setPreferredSize(new Dimension(269,25));
		cboWareHouseleft.setFont(new Font("Tahoma", Font.PLAIN, 14));
		listLocationLeft = new ArrayList<Location>();
		
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

		cboWareHouseleft.addItemListener(new java.awt.event.ItemListener() {
		      public void itemStateChanged(java.awt.event.ItemEvent evt) {
		    	  listLocationRemove = new ArrayList<Location>();
		    	  listLocationTempLeft = new ArrayList<Location>();
		    	  wareHouseLeft = (Warehouse)cboWareHouseleft.getSelectedItem();


		    	  if (cboWareHouseleft.getSelectedIndex() == -1) {
		              
		              try {
						listLocationLeft = RestaurantModelManager.getInstance().getLocations();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		              
		              for(int i =0;i<listLocationLeft.size();i++){
				        	
				        	Location loca = listLocationLeft.get(i);
				        	for(int j = 0;j<listWarehouseRight.size();j++){
					        	try {
					        		
					        		listLocationDisplay = new ArrayList<Location>();				                
									listLocationDisplay = RestaurantModelManager.getInstance().findByAttribute("Warehouse", listWarehouseRight.get(j).getWarehouseId());								
									for(int k=0;k < listLocationDisplay.size();k++){
																								
										if(loca.getCode().equals(listLocationDisplay.get(k).getCode())){
											listLocationRemove.add(listLocationDisplay.get(k));
										}
									    }
									} catch (Exception e1) {							
									e1.printStackTrace();
									}
				        		}    	        	
				   			}
				        	         
				        	for(int lc = 0;lc <listLocationRemove.size();lc++){
				        		listLocationRemove.get(lc).getCode();
				        		listLocationLeft.remove(listLocationRemove.get(lc));
				        	}
				        	pnTableLeft = new TableModelLocation(listLocationLeft);
				        		            
				            table1 = new JTable(pnTableLeft);
				            table1.setFont(new Font("Tahoma", Font.PLAIN, 14));
				            table1.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
						    table1.setRowHeight(23);  
						    table1.getColumnModel().getColumn(0).setMaxWidth(50);
				            jScrollPane1.setViewportView(table1);
		                            
		           } else {	   
		        	   
				   		 try {
				   			listLocationLeft = RestaurantModelManager.getInstance().findByAttribute("Warehouse",
				   					wareHouseLeft.getWarehouseId());
				   		} catch (Exception e) {
				   			e.printStackTrace();
				   		}
				   		pnTableLeft =new TableModelLocation(listLocationLeft);
				   		table1 = new JTable(pnTableLeft);
				   		table1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			            table1.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
					    table1.setRowHeight(23);  
					    table1.getColumnModel().getColumn(0).setMaxWidth(50);
				   		jScrollPane1.setViewportView(table1);
				   				   		
		   	      }
		    	  //load lại table right
		    	  try {
					listLocationRight = RestaurantModelManager.getInstance().findByAttribute("Warehouse",
							  wareHouseRight.getWarehouseId());
				  } catch (Exception e) {				
					e.printStackTrace();
				  }
		    	  pnTableRight =new TableModelLocation(listLocationRight);
			   		table2 = new JTable(pnTableRight);
			   		table2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			   		table2.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
					table2.setRowHeight(23);
					table2.getColumnModel().getColumn(0).setMaxWidth(50);
			   		jScrollPane2.setViewportView(table2);
		      }
		});

		cboWareHouseRight.addItemListener(new java.awt.event.ItemListener() {
		      public void itemStateChanged(java.awt.event.ItemEvent evt) {
		    	  listLocationTempRight = new ArrayList<Location>();
		    	  wareHouseRight =  ((Warehouse) cboWareHouseRight.getSelectedItem());
		   		 try {
		   			listLocationRight = RestaurantModelManager.getInstance().findByAttribute("Warehouse",
		   			            ((Warehouse) cboWareHouseRight.getSelectedItem()).getWarehouseId());
		   		} catch (Exception e) {
		   			e.printStackTrace();
		   		}
		   		pnTableRight =new TableModelLocation(listLocationRight);
		   		table2 = new JTable(pnTableRight);
		   		table2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		   		table2.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
				table2.setRowHeight(23);
				table2.getColumnModel().getColumn(0).setMaxWidth(50);
		   		jScrollPane2.setViewportView(table2);
		   		
		   		
		     	//load lại location cho table left	
		   	
		   		if(cboWareHouseleft.getSelectedIndex() == -1){
		   			
		   		 try {
						listLocationLeft = RestaurantModelManager.getInstance().getLocations();
					   } catch (Exception e) {				
							e.printStackTrace();
						}
				        // Đẩy danh sách listLocation vào TableModelLocation	
		   		listLocationRemove = new ArrayList<Location>();
				        for(int i =0;i<listLocationLeft.size();i++){
				        	
				        	Location loca = listLocationLeft.get(i);
				        	for(int j = 0;j<listWarehouseRight.size();j++){
					        	try {
					        		
					        		listLocationDisplay = new ArrayList<Location>();
					                
									listLocationDisplay = RestaurantModelManager.getInstance().findByAttribute("Warehouse", listWarehouseRight.get(j).getWarehouseId());
									for(int k=0;k < listLocationDisplay.size();k++){
										if(loca.getCode().equals(listLocationDisplay.get(k).getCode())){
											listLocationRemove.add(listLocationDisplay.get(k));
										}
									    }
								} catch (Exception e1) {							
									e1.printStackTrace();
								}
				        	}    	        	
				        }
				        
				        	for(int lc = 0;lc <listLocationRemove.size();lc++){
				        		listLocationLeft.remove(listLocationRemove.get(lc));
				        	}
				        	pnTableLeft = new TableModelLocation(listLocationLeft);
				        		            
				            table1 = new JTable(pnTableLeft);
				            table1.setFont(new Font("Tahoma", Font.PLAIN, 14));
				            table1.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
						    table1.setRowHeight(23);  
						    table1.getColumnModel().getColumn(0).setMaxWidth(50);
				            jScrollPane1.setViewportView(table1);
				                      
		   		}else {
		   			wareHouseLeft = ((Warehouse) cboWareHouseleft.getSelectedItem());
		   			try {
			   			listLocationLeft = RestaurantModelManager.getInstance().findByAttribute("Warehouse",
			   					wareHouseLeft.getWarehouseId());
			   		} catch (Exception e) {
			   			e.printStackTrace();
			   		}
	   				pnTableLeft = new TableModelLocation(listLocationLeft);
	   				table1 = new JTable(pnTableLeft);
		            table1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			   		table1.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
				    table1.setRowHeight(23);
				    table1.getColumnModel().getColumn(0).setMaxWidth(50);
		            jScrollPane1.setViewportView(table1);
				}
		   	 }

		    });

		jScrollPane1.setPreferredSize(new Dimension(400, 80));
		jScrollPane2.setPreferredSize(new Dimension(400, 80));

		panelCenter.add(panelButton);
		panelButton.add(btnAddAll);
		panelButton.add(btnAddOne);
		panelButton.add(btnRemoveOne);
		panelButton.add(btnRemoveAll);
		
		panelRight.add(jScrollPane2, BorderLayout.CENTER);
		panelLeft.add(jScrollPane1, BorderLayout.CENTER);
		
		panelRightTop.add(lbWareHouseRight);
		panelRightTop.add(cboWareHouseRight);
		panelRightTop.setOpaque(false);
		panelLeftTop.add(lbWareHouseLeft);
		panelLeftTop.add(cboWareHouseleft);
		panelLeftTop.setOpaque(false);
		
		panelTop.add(panelLeftTop, BorderLayout.LINE_START);
		panelTop.add(panelRightTop, BorderLayout.LINE_END);
		
		this.add(panelTop, BorderLayout.NORTH);
		this.add(panelLeft, BorderLayout.LINE_START);
		this.add(panelCenter, BorderLayout.CENTER);
		this.add(panelRight, BorderLayout.LINE_END);
	}

	private void buttonRemove() {
		if(wareHouseLeft==null){			
				try{
					Location row = (Location) table2.getValueAt(table2.getSelectedRow(), 2);					
						if(listLocationRight.size()>0){
							boolean check =true;
							for(int i=0;i<listLocationLeft.size();i++){
					        	Location loca = listLocationLeft.get(i);		        	
					        	if(loca.getCode().equals(row.getCode())){
					        		check = false;	
					        		break;
					        	}   
					        }
							if(check){
								listLocationRight.remove(row);
						        listLocationLeft.add(row);
				        		
						        for(int lrl =0;lrl<listLocationTempLeft.size();lrl++){
				        			if(listLocationTempLeft.get(lrl).getCode().equals(row.getCode())){
				        			  listLocationTempLeft.remove(row);
				        			}
				        		}					
						      //lrr location remove right kiem tra xem co bao nhiêu location bên phải chuyên đi để xóa
						        listLocationTempRight.add(row);						        
//						        JOptionPane.showMessageDialog(null,"Danh sach xoa ben phai" +listLocationTempRight.size());
//						        JOptionPane.showMessageDialog(null,"Danh sach xoa ben trai" + listLocationTempLeft.size());					        
				        	}
							loadTable(listLocationLeft, listLocationRight);		
						}
				} catch(Exception e){
			    }			
		}else {
			if (!wareHouseRight.getWarehouseId().equals(wareHouseLeft.getWarehouseId())) {	
				Location row = (Location) table2.getValueAt(table2.getSelectedRow(), 2);
		        listLocationRight.remove(row);
		        listLocationLeft.add(row);  
		        //Bên phải chuyển xang kiểm tra trong list loction của bên trái để xóa đi nếu trùng 
		        for(int lrl =0;lrl<listLocationTempLeft.size();lrl++){
        			if(listLocationTempLeft.get(lrl).getCode().equals(row.getCode())){
        			  listLocationTempLeft.remove(row);
        			}
        		}
		       //lrr location remove right kiem tra xem co bao nhiêu location bên phải chuyên đi để xóa
		        listLocationTempRight.add(row);		        
//		        JOptionPane.showMessageDialog(null,"Danh sach xoa ben phai" +listLocationTempRight.size());
//		        JOptionPane.showMessageDialog(null,"Danh sach xoa ben trai" + listLocationTempLeft.size());
		        loadTable(listLocationLeft, listLocationRight);
		    }else {
				JOptionPane.showMessageDialog(null, "Hai kho trùng nhau!!!");
			}
		}
	  }
	
	private void addProduct(){	
		
		if(wareHouseLeft==null){		
				if(cboWareHouseRight.getSelectedIndex() >=0){
				try{
					Location row = (Location) table1.getValueAt(table1.getSelectedRow(), 2);					
						if(listLocationRight.size()>0){
							boolean check =true;
							for(int i=0;i<listLocationRight.size();i++){
					        	Location loca = listLocationRight.get(i);		        	
					        	if(loca.getCode().equals(row.getCode())){
					        		check = false;	
					        		break;
					        	}   
					        }
							if(check){
				        		listLocationRight.add(row);
				        		listLocationLeft.remove(row);
				        	}
							loadTable(listLocationLeft, listLocationRight);
						}else {
							listLocationRight.add(row);
							listLocationLeft.remove(row);
							loadTable(listLocationLeft, listLocationRight);
						}
						
				} catch(Exception e){
			    }
				}

		}else {
			String a = wareHouseRight.getWarehouseId();
			String b = wareHouseLeft.getWarehouseId();
			if (!wareHouseRight.getWarehouseId().equals(wareHouseLeft.getWarehouseId())) {			
				try{
					Location row = (Location) table1.getValueAt(table1.getSelectedRow(), 2);					
						if(listLocationRight.size()>0){
							boolean check =true;
							for(int i=0;i<listLocationRight.size();i++){
					        	Location loca = listLocationRight.get(i);		        	
					        	if(loca.getCode().equals(row.getCode())){
					        		check = false;	
					        		break;
					        	}   
					        }
							if(check){
				        		listLocationRight.add(row);
				        		listLocationLeft.remove(row);
				        		//bên trái chuyển đi
				        		//lrr location remove right kiem tra xem co bao nhiêu location chuyển qua có trùng với cái nào chuyển đi ko 
				        		for(int lrr =0;lrr<listLocationTempRight.size();lrr++){
				        			if(listLocationTempRight.get(lrr).getCode().equals(row.getCode())){
				        				listLocationTempRight.remove(row);
				        			}
				        		}
				        		
				        		if(listLocationTempLeft.size()==0){
				        			listLocationTempLeft.add(row);
				        		}else {
				        			boolean checkLrl = true;
									for(int lrl=0; lrl <listLocationTempLeft.size();lrl++){
										if(listLocationTempLeft.get(lrl).getCode().equals(row.getCode())){
											checkLrl =false;
										}
									}
									
									if(checkLrl){
										listLocationTempLeft.add(row);
									}
								}
//				        		JOptionPane.showMessageDialog(null,"Danh sach xoa ben trai" + listLocationTempLeft.size());
//				        		JOptionPane.showMessageDialog(null,"Danh sach xoa ben phai" +listLocationTempRight.size());
				        	}
							loadTable(listLocationLeft, listLocationRight);
						}else {
							listLocationRight.add(row);
							listLocationLeft.remove(row);
							//lrr location remove right kiem tra xem co bao nhiêu location bên phải chuyên đi để xóa
							for(int lrr =0;lrr<listLocationTempRight.size();lrr++){
			        			if(listLocationTempRight.get(lrr).getCode().equals(row.getCode())){
			        				listLocationTempRight.remove(row);
			        			}
			        		}
							
							if(listLocationTempLeft.size()==0){
			        			listLocationTempLeft.add(row);
			        		}else {
			        			boolean checkLrl = true;
								for(int lrl=0; lrl <listLocationTempLeft.size();lrl++){
									if(listLocationTempLeft.get(lrl).getCode().equals(row.getCode())){
										checkLrl =false;
									}
								}
								
								if(checkLrl){
									listLocationTempLeft.add(row);
								}
							}
//							JOptionPane.showMessageDialog(null,"Danh sach xoa ben trai" + listLocationTempLeft.size());
//			        		JOptionPane.showMessageDialog(null,"Danh sach xoa ben phai" +listLocationTempRight.size());
							loadTable(listLocationLeft, listLocationRight);
						}
						
				} catch(Exception e){
			    }
				
			}else {
			JOptionPane.showMessageDialog(null, "Hai kho trùng nhau!!!");
		   }
		}

	}
	
	private void addAllProducts(){
			
	  if(wareHouseLeft==null){		
			if(cboWareHouseRight.getSelectedIndex() >=0){
			try{						
					
				for(int i=0;i<listLocationLeft.size();i++){
					listLocationRight.add(listLocationLeft.get(i));
				}
			        		
			     listLocationLeft.removeAll(listLocationLeft);
			     loadTable(listLocationLeft, listLocationRight);
					
			} catch(Exception e){
		    }
			}

	   }else {
		   
		 String a = wareHouseRight.getWarehouseId();
		 String b = wareHouseLeft.getWarehouseId();
		 if (!wareHouseRight.getWarehouseId().equals(wareHouseLeft.getWarehouseId())) {			
			try{
				
				for(int i =0;i<listLocationLeft.size();i++){
					boolean check =true;
					for(int j=0;j<listLocationRight.size();j++){
						
						if(listLocationRight.get(j).getCode().equals(listLocationLeft.get(i).getCode())){
							check= false;
						}
					}
					
					if(check){
						listLocationRight.add(listLocationLeft.get(i));
					}
				}
				listLocationLeft.removeAll(listLocationLeft);
				loadTable(listLocationLeft, listLocationRight);						
			} catch(Exception e){
		    }
			
		}else {
		JOptionPane.showMessageDialog(null, "Hai kho trùng nhau!!!");
	    }
	}
		
	}
	
	private void removeAllProducts(){
	
		if(wareHouseLeft==null){
			try{						
				
				for(int i=0;i<listLocationRight.size();i++){
					listLocationLeft.add(listLocationRight.get(i));
				}
			        		
				listLocationRight.removeAll(listLocationRight);
			    loadTable(listLocationLeft, listLocationRight);
					
			} catch(Exception e){
		    }
		}else {
			if(cboWareHouseRight.getSelectedIndex() >=0){
				if(!wareHouseLeft.getWarehouseId().equals(wareHouseRight.getWarehouseId())){
					for(int i =0;i<listLocationRight.size();i++){
						boolean check = true;
						for(int j=0;j<listLocationLeft.size();j++){
							if(listLocationLeft.get(j)!=null){
								if(listLocationLeft.get(j).getCode().equals(listLocationRight.get(i).getCode())){
									check =false;
								}
							}
						}
						if(check){
							listLocationLeft.add(listLocationRight.get(i));
						}
					}
					listLocationRight.removeAll(listLocationRight);
					loadTable(listLocationLeft, listLocationRight);
				}else {
					JOptionPane.showMessageDialog(null, "Hai kho trùng nhau!!!");
				}
				
			}
		}
		
		
	}	
	
	public void setParentForm(IDialogResto iDialogResto){
		this.iDialogResto = iDialogResto;
	}
	
	public void loadTable(List<Location> listTableLeft, List<Location> listTableRight){
		pnTableLeft = new TableModelLocation(listTableLeft);
		pnTableRight = new TableModelLocation(listTableRight);
		table1.setModel(pnTableLeft);
		table2.setModel(pnTableRight);
		table1.getColumnModel().getColumn(0).setMaxWidth(50);
		table2.getColumnModel().getColumn(0).setMaxWidth(50);
	}
	
	
	@Override
	public void Save() throws Exception {
		try {
			if(listLocationRight.size()>0){
				if(cboWareHouseleft.getSelectedIndex() ==-1){
					for(int i=0;i< listLocationRight.size();i++){
						//Kho dc chọn
						wareHouseRight = (Warehouse)cboWareHouseRight.getSelectedItem();
						//danh sách 1 vị trí được chọn
						Location loca = listLocationRight.get(i);
						//danh sách các listAttribute của 1 Location
						List<LocationAttribute> listLocalAttribute = loca.getLocationAttributes();
						boolean check = false;
						int flag =1;
						
						List<Location> listLocationFindByAttribute = new ArrayList<Location>();
						listLocationFindByAttribute = RestaurantModelManager.getInstance().findByAttribute("Warehouse", wareHouseRight.getWarehouseId());
							for(int lc=0; lc<listLocationFindByAttribute.size();lc++){
								if(listLocationFindByAttribute.get(lc).getCode().equals(loca.getCode())){
									flag=0;
								}
								check = true;
							}
						
						if(check && flag==1){
							LocationAttribute locationtAtt = new LocationAttribute();
							locationtAtt.setName("Warehouse");
							locationtAtt.setValue(wareHouseRight.getWarehouseId());
							listLocalAttribute.add(locationtAtt);				
							loca.setLocationAttributes(listLocalAttribute);
							
							location = RestaurantModelManager.getInstance().saveLocation(loca);
							if(location ==null){
								flagMessage =0;
							}
						}
						
						if(!check){
							LocationAttribute locationtAtt = new LocationAttribute();
							locationtAtt.setName("Warehouse");
							locationtAtt.setValue(wareHouseRight.getWarehouseId());
							if(listLocalAttribute.size()==0){
								listLocalAttribute = new ArrayList<LocationAttribute>();
							}
							listLocalAttribute.add(locationtAtt);				
							loca.setLocationAttributes(listLocalAttribute);
							//kiểm tra xem có lưu dc không
							location = RestaurantModelManager.getInstance().saveLocation(loca);
							if(location ==null){
								flagMessage =0;
							}
						}											
					 }	
					//đẩy các vị trí bên phải qua trái
					if(listLocationTempRight.size()>0){
						 
						 wareHouseRight = (Warehouse)cboWareHouseRight.getSelectedItem();
						 
							for(int i=0;i< listLocationTempRight.size();i++){
								Location loca = listLocationTempRight.get(i);
						
								List<LocationAttribute> listAtt = loca.getLocationAttributes();
								
								for(int j =0;j<listAtt.size();j++){
									LocationAttribute locationAtt =listAtt.get(j);
									if(locationAtt!=null){
										String nameEmp= locationAtt.getName();
										String d = locationAtt.getValue();
										String Id = wareHouseRight.getWarehouseId();
										if(locationAtt.getName().equals("Warehouse") && locationAtt.getValue().equals(Id)){
											listAtt.remove(locationAtt);
										}
									}					
								}
								loca.setLocationAttributes(listAtt);
								//kiểm tra xem có lưu dc không
								location = RestaurantModelManager.getInstance().saveLocation(loca);
								if(location ==null){
									flagMessage =0;
								}
//								RestaurantModelManager.getInstance().saveLocation(loca);
							}
						 
					 }
//					((JDialog)getRootPane().getParent()).dispose();
				  }else {
					  for(int i=0;i< listLocationRight.size();i++){
						//CboRight dc chọn
							wareHouseRight = (Warehouse)cboWareHouseRight.getSelectedItem();
						//danh sách 1 vị trí được chọn
						   Location loca = listLocationRight.get(i);
						//danh sách các listAttribute của Location
						 List<LocationAttribute> listLocalAttribute = loca.getLocationAttributes();
						boolean check = false;
						int flag =1;
						
						List<Location> listLocationFindByAttribute = new ArrayList<Location>();
						listLocationFindByAttribute = RestaurantModelManager.getInstance().findByAttribute("Warehouse", wareHouseRight.getWarehouseId());
							for(int lc=0; lc<listLocationFindByAttribute.size();lc++){
								if(listLocationFindByAttribute.get(lc).getCode().equals(loca.getCode())){
									flag=0;
								}
								check = true;
							}
						
						if(check && flag==1){
							LocationAttribute locationtAtt = new LocationAttribute();
							locationtAtt.setName("Warehouse");
							locationtAtt.setValue(wareHouseRight.getWarehouseId());
							listLocalAttribute.add(locationtAtt);				
							loca.setLocationAttributes(listLocalAttribute);
							//kiểm tra xem có lưu dc không
							location = RestaurantModelManager.getInstance().saveLocation(loca);
							if(location ==null){
								flagMessage =0;
							}
//							RestaurantModelManager.getInstance().saveLocation(loca);
						}
						
						if(!check){
							LocationAttribute locationtAtt = new LocationAttribute();
							locationtAtt.setName("Warehouse");
							locationtAtt.setValue(wareHouseRight.getWarehouseId());
							if(listLocalAttribute.size()==0){
								listLocalAttribute = new ArrayList<LocationAttribute>();
							}
							listLocalAttribute.add(locationtAtt);				
							loca.setLocationAttributes(listLocalAttribute);
							//kiểm tra xem có lưu dc không
							location = RestaurantModelManager.getInstance().saveLocation(loca);
							if(location ==null){
								flagMessage =0;
							}
//							RestaurantModelManager.getInstance().saveLocation(loca);
							
						}			
					  }
					 for(int j=0;j<listLocationLeft.size();j++){
						//CboLeft dc chọn
							wareHouseLeft = (Warehouse)cboWareHouseleft.getSelectedItem();
							//danh sách 1 vị trí được chọn
							   Location loca = listLocationLeft.get(j);
							//danh sách các listAttribute của Location
							List<LocationAttribute> listLocalAttribute = loca.getLocationAttributes();
							boolean check = false;
							int flag =1;
							
							List<Location> listLocationFindByAttribute = new ArrayList<Location>();
							listLocationFindByAttribute = RestaurantModelManager.getInstance().findByAttribute("Warehouse", wareHouseLeft.getWarehouseId());
								for(int lc=0; lc<listLocationFindByAttribute.size();lc++){
									if(listLocationFindByAttribute.get(lc).getCode().equals(loca.getCode())){
										flag=0;
									}
									check = true;
								}
							
							if(check && flag==1){
								LocationAttribute locationtAtt = new LocationAttribute();
								locationtAtt.setName("Warehouse");
								locationtAtt.setValue(wareHouseLeft.getWarehouseId());
								listLocalAttribute.add(locationtAtt);				
								loca.setLocationAttributes(listLocalAttribute);
								//kiểm tra xem có lưu dc không
								location = RestaurantModelManager.getInstance().saveLocation(loca);
								if(location ==null){
									flagMessage =0;
								}
//								RestaurantModelManager.getInstance().saveLocation(loca);
							}
							
							if(!check){
								LocationAttribute locationtAtt = new LocationAttribute();
								locationtAtt.setName("Warehouse");
								locationtAtt.setValue(wareHouseLeft.getWarehouseId());
								if(listLocalAttribute.size()==0){
									listLocalAttribute = new ArrayList<LocationAttribute>();
								}
								listLocalAttribute.add(locationtAtt);				
								loca.setLocationAttributes(listLocalAttribute);
								//kiểm tra xem có lưu dc không
								location = RestaurantModelManager.getInstance().saveLocation(loca);
								if(location ==null){
									flagMessage =0;
								}
//								RestaurantModelManager.getInstance().saveLocation(loca);
								
							}				
					 }
					 
					 //xóa location khi mà có sự thay đổi chuyển qua lại cho cboRight
					 if(listLocationTempRight.size()>0){
						 
						 wareHouseRight = (Warehouse)cboWareHouseRight.getSelectedItem();
						 
							for(int i=0;i< listLocationTempRight.size();i++){
								Location loca = listLocationTempRight.get(i);
						
								List<LocationAttribute> listAtt = loca.getLocationAttributes();
								
								for(int j =0;j<listAtt.size();j++){
									LocationAttribute locationAtt =listAtt.get(j);
									if(locationAtt!=null){
										String nameEmp= locationAtt.getName();
										String d = locationAtt.getValue();
										String Id = wareHouseRight.getWarehouseId();
										if(locationAtt.getName().equals("Warehouse") && locationAtt.getValue().equals(Id)){
											listAtt.remove(locationAtt);
										}
									}					
								}
								loca.setLocationAttributes(listAtt);
								//kiểm tra xem có lưu dc không
								location = RestaurantModelManager.getInstance().saveLocation(loca);
								if(location ==null){
									flagMessage =0;
								}
//								RestaurantModelManager.getInstance().saveLocation(loca);
							}					 
					 }
					 //xóa location của kho trái khi đã chuyển qua kho phải
					 	if(listLocationTempLeft.size()>0){						 
						 wareHouseLeft = (Warehouse)cboWareHouseleft.getSelectedItem();					 
							for(int i=0;i< listLocationTempLeft.size();i++){
								Location loca = listLocationTempLeft.get(i);
						
								List<LocationAttribute> listAtt = loca.getLocationAttributes();
								
								for(int j =0;j<listAtt.size();j++){
									LocationAttribute locationAtt =listAtt.get(j);
									if(locationAtt!=null){
										String nameEmp= locationAtt.getName();
										String d = locationAtt.getValue();
										String Id = wareHouseLeft.getWarehouseId();
										if(locationAtt.getName().equals("Warehouse") && locationAtt.getValue().equals(Id)){
											listAtt.remove(locationAtt);
										}
									}					
								}
								loca.setLocationAttributes(listAtt);
								//kiểm tra xem có lưu dc không
								location = RestaurantModelManager.getInstance().saveLocation(loca);
								if(location ==null){
									flagMessage =0;
								}
//								RestaurantModelManager.getInstance().saveLocation(loca);
							}						 
					 }					 
				  }
				}else {
					//trường hợp xóa tất khu vực của Kho
					if(cboWareHouseleft.getSelectedIndex() ==-1){
						wareHouseRight = (Warehouse)cboWareHouseRight.getSelectedItem();
						
						for(int i=0;i< listLocationLeft.size();i++){
							Location loca = listLocationLeft.get(i);
					
							List<LocationAttribute> listAtt = loca.getLocationAttributes();
							for(int j =0;j<listAtt.size();j++){
								LocationAttribute locationAtt =listAtt.get(j);
								if(locationAtt!=null){
									String nameEmp= locationAtt.getName();
									String d = locationAtt.getValue();
									String Id = wareHouseRight.getWarehouseId();
									if(locationAtt.getName().equals(nameEmp) && locationAtt.getValue().equals(Id)){
										listAtt.remove(locationAtt);
									}
								}					
							}
							loca.setLocationAttributes(listAtt);
							//kiểm tra xem có lưu dc không
							location = RestaurantModelManager.getInstance().saveLocation(loca);
							if(location ==null){
								flagMessage =0;
							}
//							RestaurantModelManager.getInstance().saveLocation(loca);
						}						
					}else {
						//xóa bên phải
						wareHouseRight = (Warehouse)cboWareHouseRight.getSelectedItem();
						
						for(int i=0;i< listLocationLeft.size();i++){
							Location loca = listLocationLeft.get(i);
					
							List<LocationAttribute> listAtt = loca.getLocationAttributes();
							for(int j =0;j<listAtt.size();j++){
								LocationAttribute locationAtt =listAtt.get(j);
								if(locationAtt!=null){
									String nameEmp= locationAtt.getName();
									String d = locationAtt.getValue();
									String Id = wareHouseRight.getWarehouseId();
									if(locationAtt.getName().equals(nameEmp) && locationAtt.getValue().equals(Id)){
										listAtt.remove(locationAtt);
									}
								}					
							}
							loca.setLocationAttributes(listAtt);
							//kiểm tra xem có lưu dc không
							location = RestaurantModelManager.getInstance().saveLocation(loca);
							if(location ==null){
								flagMessage =0;
							}
//							RestaurantModelManager.getInstance().saveLocation(loca);
						}
						//thêm cho kho trái
						wareHouseLeft = (Warehouse)cboWareHouseleft.getSelectedItem();
						
						for(int i=0;i< listLocationLeft.size();i++){
							Location loca = listLocationLeft.get(i);
							List<LocationAttribute> listLocalAttribute = loca.getLocationAttributes();
							boolean check = false;
							int flag =1;
							
							List<Location> listLocationFindByAttribute = new ArrayList<Location>();
							listLocationFindByAttribute = RestaurantModelManager.getInstance().findByAttribute("Warehouse", wareHouseLeft.getWarehouseId());
								for(int lc=0; lc<listLocationFindByAttribute.size();lc++){
									if(listLocationFindByAttribute.get(lc).getCode().equals(loca.getCode())){
										flag=0;
									}
									check = true;
								}
							
							if(check && flag==1){
								LocationAttribute locationtAtt = new LocationAttribute();
								locationtAtt.setName("Warehouse");
								locationtAtt.setValue(wareHouseLeft.getWarehouseId());
								listLocalAttribute.add(locationtAtt);				
								loca.setLocationAttributes(listLocalAttribute);
								//kiểm tra xem có lưu dc không
								location = RestaurantModelManager.getInstance().saveLocation(loca);
								if(location ==null){
									flagMessage =0;
								}
//								RestaurantModelManager.getInstance().saveLocation(loca);
							}
							
							if(!check){
								LocationAttribute locationtAtt = new LocationAttribute();
								locationtAtt.setName("Warehouse");
								locationtAtt.setValue(wareHouseLeft.getWarehouseId());
								if(listLocalAttribute.size()==0){
									listLocalAttribute = new ArrayList<LocationAttribute>();
								}
								listLocalAttribute.add(locationtAtt);				
								loca.setLocationAttributes(listLocalAttribute);
								//kiểm tra xem có lưu dc không
								location = RestaurantModelManager.getInstance().saveLocation(loca);
								if(location ==null){
									flagMessage =0;
								}
//								RestaurantModelManager.getInstance().saveLocation(loca);
								
							}	
						}
					}	
				}
			
			//khi trái hết
			
			if(listLocationLeft.size()==0){
			  if(wareHouseLeft!=null){
				wareHouseLeft = (Warehouse)cboWareHouseleft.getSelectedItem();
				
				for(int i=0;i< listLocationRight.size();i++){
					Location loca = listLocationRight.get(i);
			
					List<LocationAttribute> listAtt = loca.getLocationAttributes();
					for(int j =0;j<listAtt.size();j++){
						LocationAttribute locationAtt =listAtt.get(j);
						if(locationAtt!=null){
							String nameEmp= locationAtt.getName();
							String d = locationAtt.getValue();
							String Id = wareHouseLeft.getWarehouseId();
							if(locationAtt.getName().equals(nameEmp) && locationAtt.getValue().equals(Id)){
								listAtt.remove(locationAtt);
							}
						}					
					}
					loca.setLocationAttributes(listAtt);
					//kiểm tra xem có lưu dc không
					location = RestaurantModelManager.getInstance().saveLocation(loca);
					if(location ==null){
						flagMessage =0;
					}
//					RestaurantModelManager.getInstance().saveLocation(loca);
				}
			  }
				
			}			
			if(flagMessage==1){
				DialogNotice.getInstace().setVisible(true);
			}else {
				JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi, thử lại");
			}
		 } catch (Exception e) {
	          e.printStackTrace();
	     }
	}
}
