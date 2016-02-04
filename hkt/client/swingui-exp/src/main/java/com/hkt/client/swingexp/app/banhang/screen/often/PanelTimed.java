package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.invoke.MethodHandles.Lookup;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.restaurant.entity.Project;

public class PanelTimed extends JPanel implements IDialogResto {
	private JPanel PanelStart, Panelend;
	private JLabel lbStartday, lbStarttime, lbstartconlon, lbendconlon, lbfinishday, lbending;
	private JTextField txtMBigin, txtHBigin, txtHEnd, txtMEnd;
	private MyJDateChooser dcBegin, dcEnd;
	private JButton btnupdatestart, btnupdateend;

	public PanelTimed() {
		setOpaque(false);
		PanelStart = new javax.swing.JPanel();
		PanelStart.setOpaque(false);
		lbStartday = new javax.swing.JLabel();
		lbStarttime = new javax.swing.JLabel();
		dcBegin = new MyJDateChooser();
		txtMBigin = new javax.swing.JTextField();
		btnupdatestart = new javax.swing.JButton();
		lbstartconlon = new javax.swing.JLabel();
		txtHBigin = new javax.swing.JTextField();
		Panelend = new javax.swing.JPanel();
		lbfinishday = new javax.swing.JLabel();
		lbending = new javax.swing.JLabel();
		dcEnd = new MyJDateChooser();
		txtHEnd = new javax.swing.JTextField();
		btnupdateend = new javax.swing.JButton();
		lbendconlon = new javax.swing.JLabel();
		txtMEnd = new javax.swing.JTextField();

		PanelStart.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		lbStartday.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lbStartday.setText("Ngày bắt đầu");

		lbStarttime.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lbStarttime.setText("Giờ bắt đầu");

		btnupdatestart.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		btnupdatestart.setText("Cập nhật");
		btnupdatestart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				project.setStartDate(new Date());
				dcBegin.setDate(new Date());
				txtHBigin.setText(dateFormatH.format(new Date()));
				txtMBigin.setText(dateFormatM.format(new Date()));

			}
		});

		lbstartconlon.setText(":");

		javax.swing.GroupLayout PanelStartLayout = new javax.swing.GroupLayout(PanelStart);
		PanelStart.setLayout(PanelStartLayout);
		PanelStartLayout
		    .setHorizontalGroup(PanelStartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		        .addGroup(
		            PanelStartLayout
		                .createSequentialGroup()
		                .addContainerGap()
		                .addGroup(
		                    PanelStartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                        .addComponent(lbStartday).addComponent(lbStarttime))
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                .addGroup(
		                    PanelStartLayout
		                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
		                        .addGroup(
		                            javax.swing.GroupLayout.Alignment.LEADING,
		                            PanelStartLayout
		                                .createSequentialGroup()
		                                .addComponent(txtHBigin, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
		                                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                                .addComponent(lbstartconlon, javax.swing.GroupLayout.PREFERRED_SIZE, 7,
		                                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                                .addGap(12, 12, 12)
		                                .addComponent(txtMBigin, javax.swing.GroupLayout.PREFERRED_SIZE, 43,
		                                    javax.swing.GroupLayout.PREFERRED_SIZE))
		                        .addComponent(dcBegin, javax.swing.GroupLayout.Alignment.LEADING,
		                            javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		                            Short.MAX_VALUE)
		                        .addComponent(btnupdatestart, javax.swing.GroupLayout.Alignment.LEADING,
		                            javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		                            Short.MAX_VALUE)).addContainerGap(40, Short.MAX_VALUE)));
		PanelStartLayout.setVerticalGroup(PanelStartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        PanelStartLayout
		            .createSequentialGroup()
		            .addGap(21, 21, 21)
		            .addGroup(
		                PanelStartLayout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(dcBegin, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(lbStartday))
		            .addGap(12, 12, 12)
		            .addGroup(
		                PanelStartLayout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                    .addComponent(lbStarttime)
		                    .addComponent(txtHBigin, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(lbstartconlon, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(txtMBigin, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
		            .addComponent(btnupdatestart, javax.swing.GroupLayout.PREFERRED_SIZE, 37,
		                javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));

		Panelend.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		Panelend.setOpaque(false);
		lbfinishday.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lbfinishday.setText("Ngày kết thúc");

		lbending.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lbending.setText("Giờ kết thúc");

		btnupdateend.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		btnupdateend.setText("Cập nhật");
		btnupdateend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				project.setModifiedTime(new Date());
				dcEnd.setDate(new Date());
				txtHEnd.setText(dateFormatH.format(new Date()));
				txtMEnd.setText(dateFormatM.format(new Date()));

			}
		});

		lbendconlon.setText(":");

		javax.swing.GroupLayout PanelendLayout = new javax.swing.GroupLayout(Panelend);
		Panelend.setLayout(PanelendLayout);
		PanelendLayout
		    .setHorizontalGroup(PanelendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		        .addGroup(
		            PanelendLayout
		                .createSequentialGroup()
		                .addContainerGap()
		                .addGroup(
		                    PanelendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                        .addComponent(lbfinishday).addComponent(lbending))
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                .addGroup(
		                    PanelendLayout
		                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
		                        .addGroup(
		                            javax.swing.GroupLayout.Alignment.LEADING,
		                            PanelendLayout
		                                .createSequentialGroup()
		                                .addComponent(txtHEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
		                                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                                .addComponent(lbendconlon, javax.swing.GroupLayout.PREFERRED_SIZE, 7,
		                                    javax.swing.GroupLayout.PREFERRED_SIZE)
		                                .addGap(12, 12, 12)
		                                .addComponent(txtMEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 43,
		                                    javax.swing.GroupLayout.PREFERRED_SIZE))
		                        .addComponent(dcEnd, javax.swing.GroupLayout.Alignment.LEADING,
		                            javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		                            Short.MAX_VALUE)
		                        .addComponent(btnupdateend, javax.swing.GroupLayout.Alignment.LEADING,
		                            javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		                            Short.MAX_VALUE)).addContainerGap(40, Short.MAX_VALUE)));
		PanelendLayout.setVerticalGroup(PanelendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(
		        PanelendLayout
		            .createSequentialGroup()
		            .addGap(21, 21, 21)
		            .addGroup(
		                PanelendLayout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(dcEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(lbfinishday))
		            .addGap(12, 12, 12)
		            .addGroup(
		                PanelendLayout
		                    .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                    .addComponent(lbending)
		                    .addComponent(txtHEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(lbendconlon, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(txtMEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
		                        javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
		                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		            .addComponent(btnupdateend, javax.swing.GroupLayout.PREFERRED_SIZE, 37,
		                javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    layout
		        .createSequentialGroup()
		        .addContainerGap()
		        .addComponent(PanelStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		            Short.MAX_VALUE)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		        .addComponent(Panelend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		            javax.swing.GroupLayout.DEFAULT_SIZE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    layout
		        .createSequentialGroup()
		        .addContainerGap()
		        .addGroup(
		            layout
		                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
		                .addComponent(PanelStart, javax.swing.GroupLayout.DEFAULT_SIZE,
		                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                .addComponent(Panelend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		                    Short.MAX_VALUE)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

	}

	private double sl = 0.01;
	private DateFormat dateFormatH = new SimpleDateFormat("HH");
	private DateFormat dateFormatM = new SimpleDateFormat("mm");
	private DateFormat df = new SimpleDateFormat("yyyyMMdd");
	private InvoiceDetail project;
	private boolean flagUpdate, date;
	private String pricingType, locationCode, tableCode;
	private HashMap<Long, MyDouble> hashMap = new HashMap<Long, MyDouble>();
	private HashMap<Long, MyDouble> hashMap1 = new HashMap<Long, MyDouble>();

	public HashMap<Long, MyDouble> getHashMap1() {
		return hashMap1;
	}
	
	public void setInfo(String type, String location, String table) {
	  this.pricingType = type;
	  this.locationCode = location;
	  this.tableCode = table;
  }

	public JButton getBtnPrint() {
		JButton btnEdit = new JButton();
		btnEdit = new JButton("Giá theo giờ");
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEdit.setMargin(new Insets(0, 0, 0, 0));
		btnEdit.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/iconOk.png")));
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PanelTimeKara panel = new PanelTimeKara();
					DialogMain dialog = new DialogMain(panel);
					dialog.setTitle("Giá theo giờ");
					dialog.setLocationRelativeTo(null);
					dialog.setModal(true);
					dialog.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		return btnEdit;
	}
	
	private boolean checkProductPrice(ProductPrice p,String type, String locationCode, String table){
		boolean a = true;
		if(!p.getProductPriceType().getType().equals(pricingType)){
			a= false;
		}
		
		if(p.getDescription()!=null && !p.getDescription().trim().isEmpty()){
			if(!p.getDescription().equals(table)){
				a =false;
			}
		}else {
			if(p.getOrganizationLoginId()!=null && !p.getOrganizationLoginId().trim().isEmpty()){
				if(!p.getOrganizationLoginId().equals(locationCode)){
					a = false;
				}
			}
		}
		return a;
	}

	@Override
	public void Save() throws Exception {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(dcBegin.getDate());
		c1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(txtHBigin.getText()));
		c1.set(Calendar.MINUTE, Integer.parseInt(txtMBigin.getText()));
		project.setStartDate(c1.getTime());
		Calendar c2 = Calendar.getInstance();
		c2.setTime(dcEnd.getDate());
		c2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(txtHEnd.getText()));
		c2.set(Calendar.MINUTE, Integer.parseInt(txtMEnd.getText()));
		project.setModifiedTime(c2.getTime());
		AccountingModelManager.getInstance().saveInvoice(project);
		flagUpdate = true;
		List<ProductPrice> list = ProductModelManager.getInstance().getProductPriceByProductCode(
		    ProductModelManager.getInstance().getProductKara().getCode());
		for (int i = 0; i < list.size();) {
			if (checkProductPrice(list.get(i), pricingType, locationCode, tableCode)) {
				i++;
			} else {
				list.remove(i);
			}
		}
		String gio = txtHEnd.getText();
		String phut = txtMEnd.getText();
		Date d = dcEnd.getDate();
		if (!list.isEmpty()) {
			if (!df.format(dcBegin.getDate()).equals(df.format(dcEnd.getDate()))) {
				txtHEnd.setText("23");
				txtMEnd.setText("59");
				dcEnd.setDate(dcBegin.getDate());
				loadKara(list);
				date = true;
				txtHBigin.setText("00");
				txtMBigin.setText("01");
				txtHEnd.setText(gio);
				txtMEnd.setText(phut);
				dcBegin.setDate(d);
				dcEnd.setDate(d);
				list = ProductModelManager.getInstance().getProductPriceByProductCode(
				    ProductModelManager.getInstance().getProductKara().getCode());

				loadKara(list);
			} else {
				loadKara(list);
			}
		}
		save1();
		flagUpdate = true;
		((JDialog) getRootPane().getParent()).dispose();
	}

	public boolean isFlagUpdate() {
		return flagUpdate;
	}

	public void initEvent(InvoiceDetail project) {

		this.project = project;

		if (this.project.getModifiedTime() != null) {
			dcBegin.setDate(this.project.getStartDate());
			txtHBigin.setText(dateFormatH.format(this.project.getStartDate()));
			txtMBigin.setText(dateFormatM.format(this.project.getStartDate()));
		} else {
			this.project.setStartDate(new Date());
			dcBegin.setDate(new Date());
			txtHBigin.setText(dateFormatH.format(new Date()));
			txtMBigin.setText(dateFormatM.format(new Date()));
		}
		if (this.project.getModifiedTime() != null) {
			dcEnd.setDate(this.project.getModifiedTime());
			txtHEnd.setText(dateFormatH.format(this.project.getModifiedTime()));
			txtMEnd.setText(dateFormatM.format(this.project.getModifiedTime()));
		} else {
			this.project.setModifiedTime(new Date());
			dcEnd.setDate(new Date());
			txtHEnd.setText(dateFormatH.format(new Date()));
			txtMEnd.setText(dateFormatM.format(new Date()));
		}
	}

	private void save1() {
		if (hashMap.values().isEmpty()) {
			initEvent(project);
			Calendar c1 = Calendar.getInstance();
			c1.setTime(dcBegin.getDate());
			c1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(txtHBigin.getText()));
			c1.set(Calendar.MINUTE, Integer.parseInt(txtMBigin.getText()));
			Calendar c2 = Calendar.getInstance();
			c2.setTime(dcEnd.getDate());
			c2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(txtHEnd.getText()));
			c2.set(Calendar.MINUTE, Integer.parseInt(txtMEnd.getText()));
			long time1 = c1.getTimeInMillis();
			long time2 = c2.getTimeInMillis();
			long sl1 = time2 - time1;
			sl = new MyDouble(String.valueOf(sl1)).doubleValue() / new MyDouble(String.valueOf("3600000")).doubleValue();
			if (sl > 0) {
				hashMap.put(0l, new MyDouble(sl));
			} else {
				hashMap.put(0l, new MyDouble("0.01"));
			}
		}
	}

	private void loadKara(List<ProductPrice> list) {
		Calendar sta = Calendar.getInstance();
		sta.setTime(dcEnd.getDate());
		sta.set(Calendar.HOUR_OF_DAY, Integer.parseInt(txtHBigin.getText()));
		sta.set(Calendar.MINUTE, Integer.parseInt(txtMBigin.getText()));
		Calendar now = Calendar.getInstance();
		now.setTime(dcEnd.getDate());
		now.set(Calendar.HOUR_OF_DAY, Integer.parseInt(txtHEnd.getText()));
		now.set(Calendar.MINUTE, Integer.parseInt(txtMEnd.getText()));
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getCreatedBy() != null) {
				try {
					List<String> mang = new ArrayList<String>();
					StringTokenizer st = new StringTokenizer(list.get(i).getCreatedBy(), ":");
					while (st.hasMoreTokens()) {
						mang.add(st.nextToken());
					}
					Calendar c = Calendar.getInstance();
					c.setTime(dcEnd.getDate());
					c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mang.get(0)));
					c.set(Calendar.MINUTE, Integer.parseInt(mang.get(1)));
					List<String> mang1 = new ArrayList<String>();
					StringTokenizer st1 = new StringTokenizer(list.get(i).getModifiedBy(), ":");
					while (st1.hasMoreTokens()) {
						mang1.add(st1.nextToken());
					}
					Calendar c1 = Calendar.getInstance();
					c1.setTime(dcEnd.getDate());
					c1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mang1.get(0)));
					c1.set(Calendar.MINUTE, Integer.parseInt(mang1.get(1)));
					if (sta.getTimeInMillis() > c.getTimeInMillis()) {
						if (now.getTimeInMillis() <= c1.getTimeInMillis()) {
							loadSL(sta, now, list.get(i).getId());
						} else {
							loadSL(sta, c1, list.get(i).getId());
						}
					} else {
						if (c1.getTimeInMillis() <= now.getTimeInMillis()) {
							loadSL(c, c1, list.get(i).getId());

						} else {
							loadSL(c, now, list.get(i).getId());

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// System.out.println("reset  " + reset);
		// if (reset) {
		// loadKara(list);
		// }

	}

	public HashMap<Long, MyDouble> getHashMap() {
		return hashMap;
	}

	private void loadSL(Calendar c1, Calendar c2, long id) {
		long time1 = c1.getTimeInMillis();
		long time2 = c2.getTimeInMillis();
		long sl1 = time2 - time1;
		double a = new MyDouble(String.valueOf(sl1)).doubleValue() / new MyDouble(String.valueOf("3600000")).doubleValue();
		if (a > 0) {
			if (date) {
				hashMap1.put(id, new MyDouble(a));
			} else {
				hashMap.put(id, new MyDouble(a));
			}

		} else {
			if (a == 0) {
				hashMap.put(id, new MyDouble("0.01"));
			}
		}
	}

}
