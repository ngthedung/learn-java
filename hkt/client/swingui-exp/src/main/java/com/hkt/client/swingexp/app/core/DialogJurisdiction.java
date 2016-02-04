package com.hkt.client.swingexp.app.core;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import com.hkt.client.swingexp.app.component.PanelBackground;
import com.hkt.client.swingexp.app.component.PanelTrans;
import com.hkt.client.swingexp.homescreen.HomeScreen;

public class DialogJurisdiction  extends JDialog{
	private JLabel label;
	private JLabel labelTitle;
	private PanelBackground panelBackground1;
	private JScrollPane btnExt1, btnExt2, btnExt3;
	private JButton         btnExit;
	private JLabel jLabel30;
	
	private static DialogJurisdiction dialogJurisdiction;
	
	public static DialogJurisdiction getInstace(){
		if(dialogJurisdiction == null)
			dialogJurisdiction = new DialogJurisdiction();
		return dialogJurisdiction;
	}
	
	public DialogJurisdiction()
	{
		 initComponents();
		 this.setLocationRelativeTo(null);
		 this.setModal(true);
		 this.dispose();
		 this.setUndecorated(true);
	}

	private void initComponents() {
		 btnExit = new JButton("Thoát");
		  btnExit.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        btnExitActionPerformed(e);
		      }
		    });
		    btnExit.setName("btnExit");
		    btnExit.setFont(new Font("Tahoma", 1, 14));
		    btnExit.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/iconBack.png")));
		    btnExt1 = new JScrollPane();
		    btnExt1.setBorder(null);
		    btnExt2 = new JScrollPane();
		    btnExt2.setBorder(null);
		    btnExt3 = new JScrollPane();
		    btnExt3.setBorder(null);
		    btnExt1.setOpaque(false);
		    btnExt1.getViewport().setOpaque(false);
		    btnExt2.setOpaque(false);
		    btnExt2.getViewport().setOpaque(false);
		    btnExt3.setOpaque(false);
		    btnExt3.getViewport().setOpaque(false);
		
		label = new JLabel("Bạn chưa được phân quyền này!");
		label.setFont(new Font("Tahoma", 1, 14));
		label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		
		labelTitle = new JLabel("Phân Quyền");
		labelTitle.setFont(new java.awt.Font("Tahoma", 1, 14));
		labelTitle.setForeground(new java.awt.Color(126, 0, 0));
		
		 jLabel30 = new JLabel();
		    jLabel30.setText("");
		
		 // Giao diện chính panel2 add giao diện chính jPanel1 và tạo khoảng cách các bên
	    PanelTrans panel2 = new PanelTrans();
	    panel2.setOpaque(false);
	    panel2.setLayout(new BorderLayout());
	    panel2.add(label,BorderLayout.CENTER);
	    
	 // Panel trong suốt thứ 2 add giao diện chính panel2
	    PanelTrans panel = new PanelTrans();
	    panel.setOpaque(false);
	    GroupLayout panelBackgroundResto1Layout = new GroupLayout(panel);
	    panel.setLayout(panelBackgroundResto1Layout);
	    panelBackgroundResto1Layout.setHorizontalGroup(panelBackgroundResto1Layout
	        .createParallelGroup(GroupLayout.Alignment.LEADING)
	        .addGroup(
	            panelBackgroundResto1Layout.createSequentialGroup().addGap(28, 28, 28)
	                .addComponent(labelTitle, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
	               .addGap(45, 45, 45))
	        .addGroup(
	            panelBackgroundResto1Layout.createSequentialGroup().addGap(25, 25, 25)
	                .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE).addGap(25, 25, 25))
	        .addGroup(
	            panelBackgroundResto1Layout.createSequentialGroup()
	                .addGap(4, 4, 4).addComponent(jLabel30, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE).addGap(4, 4, 4)
	                .addComponent(btnExt3, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
	                .addComponent(btnExt2, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
	                .addComponent(btnExt1, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(4, 4, 4)
	                .addComponent(btnExit).addGap(22, 22, 22)));
	    panelBackgroundResto1Layout.setVerticalGroup(panelBackgroundResto1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
	        panelBackgroundResto1Layout
	            .createSequentialGroup().addGap(6, 6, 6)
	            .addGroup(
	                panelBackgroundResto1Layout
	                    .createParallelGroup(GroupLayout.Alignment.LEADING)
	                    .addGroup(
	                        panelBackgroundResto1Layout.createSequentialGroup().addGap(11, 11, 11)
	                            .addComponent(labelTitle, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
	                            ))
	            .addGap(6, 6, 6)
	            .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
	            .addGap(11, 11, 11)
	            .addGroup(
	                panelBackgroundResto1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	                    .addComponent(jLabel30, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
	                    .addComponent(btnExt3, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
	                    .addComponent(btnExt2, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
	                    .addComponent(btnExt1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
	                    .addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)).addGap(11, 11, 11)));

	// Panel thứ nhất add panel trong suốt thứ 2
	panelBackground1 = new PanelBackground();
    panelBackground1.setLayout(new BorderLayout());
    panelBackground1.add(panel,BorderLayout.CENTER);
	
	// Giao diện dialog add Panel thứ nhất
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(panelBackground1,
        GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(panelBackground1,
        GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
    
	pack();
	}
	
	private void btnExitActionPerformed(ActionEvent e) {
	    this.dispose();
	  }

}
