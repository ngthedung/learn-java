package com.hkt.client.swingexp.app.hethong;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hkt.client.swingexp.app.core.IDialogResto;

@SuppressWarnings("serial")
public class PanelOKRestore   extends JPanel implements IDialogResto{
	private JLabel lbdelete, lbdelete1;
	private boolean delete;
	
	public boolean isDelete() {
		return delete;
	}
	
	public PanelOKRestore(String str, String str1){
		setOpaque(false);
		lbdelete = new javax.swing.JLabel();
		lbdelete.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lbdelete.setText(str);
		lbdelete.setHorizontalAlignment(JLabel.CENTER);
		lbdelete1 = new javax.swing.JLabel();	
		lbdelete1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lbdelete1.setText(str1);
		lbdelete1.setHorizontalAlignment(JLabel.CENTER);
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(layout.createSequentialGroup()
	                    		.addGap(60,60, 60)
	                        .addComponent(lbdelete1)
	                        )
	                    .addGroup(layout.createSequentialGroup()
	                    		 .addGap(60,60, 60)
	                        .addComponent(lbdelete)))));
		layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(lbdelete)
	                .addGap(18, 18, 18)
	                .addComponent(lbdelete1)
	                .addContainerGap(75, Short.MAX_VALUE))
	        );
	}

	@Override
	public void Save() throws Exception {
		delete = true;
		((JDialog) getRootPane().getParent()).dispose();
		
	}

}
