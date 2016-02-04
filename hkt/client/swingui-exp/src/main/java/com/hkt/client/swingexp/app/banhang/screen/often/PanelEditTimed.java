package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;

public class PanelEditTimed extends JPanel implements IDialogResto {
	private JPanel PanelStart, Panelend;
	private JLabel lbStartday, lbStarttime, lbstartconlon, lbendconlon, lbfinishday, lbending;
	private JTextField txtMBigin, txtHBigin, txtHEnd, txtMEnd;
	private MyJDateChooser dcBegin, dcEnd;
	private JButton btnupdatestart, btnupdateend;
	private boolean save;
	
	

	public boolean isSave() {
		return save;
	}



	public PanelEditTimed() {
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

	private DateFormat dateFormatH = new SimpleDateFormat("HH");
	private DateFormat dateFormatM = new SimpleDateFormat("mm");
	private DateFormat df = new SimpleDateFormat("yyyyMMdd");
	private InvoiceDetail project;
	
	


	public InvoiceDetail getInvoice() {
		return project;
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
		project.setEndDate(c2.getTime());
		for(InvoiceItem invoiceItem: project.getItems()){
			invoiceItem.setStartDate(project.getStartDate());
		}
		project=AccountingModelManager.getInstance().saveInvoice(project);
		save = true;
		((JDialog) getRootPane().getParent()).dispose();
	}



	public void initEvent(InvoiceDetail project) {

		this.project = project;

		if (this.project.getEndDate() != null) {
			dcBegin.setDate(this.project.getStartDate());
			txtHBigin.setText(dateFormatH.format(this.project.getStartDate()));
			txtMBigin.setText(dateFormatM.format(this.project.getStartDate()));
		} else {
			this.project.setStartDate(new Date());
			dcBegin.setDate(new Date());
			txtHBigin.setText(dateFormatH.format(new Date()));
			txtMBigin.setText(dateFormatM.format(new Date()));
		}
		if (this.project.getEndDate() != null) {
			dcEnd.setDate(this.project.getEndDate());
			txtHEnd.setText(dateFormatH.format(this.project.getEndDate()));
			txtMEnd.setText(dateFormatM.format(this.project.getEndDate()));
		} else {
			this.project.setEndDate(new Date());
			dcEnd.setDate(new Date());
			txtHEnd.setText(dateFormatH.format(new Date()));
			txtMEnd.setText(dateFormatM.format(new Date()));
		}
	}
}
