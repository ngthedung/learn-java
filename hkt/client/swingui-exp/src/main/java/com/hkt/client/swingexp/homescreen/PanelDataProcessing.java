package com.hkt.client.swingexp.homescreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.convertdata.excel.PanelLiquidation;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDown;
import com.hkt.client.swingexp.app.hethong.PanelAutoBackupData;
import com.hkt.client.swingexp.app.hethong.PanelBackupData;
import com.hkt.client.swingexp.app.hethong.PanelRestoreData;
import com.hkt.client.swingexp.model.UIConfigModelManager;

@SuppressWarnings("serial")
public class PanelDataProcessing extends JPanel implements IDialogResto{
	private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    public static String permission;
	public PanelDataProcessing(){
		initComponents();
	}
	
	
	  private void initComponents() {
		  	setOpaque(false);
	        jButton1 = new ExtendJButton("Sao lưu dữ liệu");
	        jButton2 = new ExtendJButton("Phục hồi dữ liệu");
	        jButton3 = new ExtendJButton("Sao lưu tự động");
	        jButton4 = new ExtendJButton("Thanh lý dữ liệu");

	        jButton1.setText("Sao lưu dữ liệu");
	        jButton1.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/saoluuhihi.png")));
	        jButton1.addMouseListener(new MouseEventClickButtonDown("saoluuhihi.png", "saoluuhihi.png", "saoluuhihi.png"));
	        jButton1.setIconTextGap(11);
	        jButton1.setFocusPainted(false);
	        PanelBackupData.permission = (UIConfigModelManager.getInstance().getPlainText(PanelDataProcessing.permission));
	        jButton1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						PanelBackupData backupDataDialog = new PanelBackupData();
						DialogResto dialog = new DialogResto(backupDataDialog, false, 50, 150);
						dialog.setTextBtnSave("Sao lưu");
						dialog.setIcon("saoluu1.png");
						dialog.setName("dlInfo");
						dialog.setLocationRelativeTo(null);
						dialog.setTitle("Sao lưu dữ liệu");
						dialog.setVisible(true);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					disposeForm();
				}
			});

	        jButton2.setText("Phục hồi dữ liệu");
	        jButton2.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/phuchoihihi.png")));
	        jButton2.addMouseListener(new MouseEventClickButtonDown("phuchoihihi.png", "phuchoihihi.png", "phuchoihihi.png"));
	        jButton2.setIconTextGap(11);
	        jButton2.setFocusPainted(false);
	        PanelRestoreData.permission = (UIConfigModelManager.getInstance().getPlainText(PanelDataProcessing.permission));
	        jButton2.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						PanelRestoreData restoreDataDialog = new PanelRestoreData();
						DialogResto dialog = new DialogResto(restoreDataDialog, false, 0, 240);
						dialog.setBtnSave(false);
						dialog.setName("dlInfo");
						dialog.setIcon("phuchoi1.png");
						dialog.setLocationRelativeTo(null);
						dialog.setTitle("Phục hồi dữ liệu");
						dialog.setVisible(true);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					disposeForm();
				}
			});
	        
	        jButton3.setText("Sao lưu tự động");
	        jButton3.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/saoluuhihi.png")));
	        jButton3.addMouseListener(new MouseEventClickButtonDown("saoluuhihi.png", "saoluuhihi.png", "saoluuhihi.png"));
	        jButton3.setIconTextGap(11);
	        jButton3.setFocusPainted(false);
	        PanelAutoBackupData.permission = (UIConfigModelManager.getInstance().getPlainText(PanelDataProcessing.permission));
	        jButton3.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						PanelAutoBackupData autoBackupData = new PanelAutoBackupData();
						DialogMain dialogMain = new DialogMain(autoBackupData, 245, 160);
						dialogMain.setModal(true);
						dialogMain.setIcon("saoluu1.png");
						dialogMain.setTitle("Sao lưu tự động");
						dialogMain.setLocationRelativeTo(null);
						dialogMain.setVisible(true);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					disposeForm();
				}
			});

	        jButton4.setText("Thanh lý dữ liệu");
	        jButton4.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/phuchoihihi.png")));
	        jButton4.addMouseListener(new MouseEventClickButtonDown("phuchoihihi.png", "phuchoihihi.png", "phuchoihihi.png"));
	        jButton4.setIconTextGap(11);
	        jButton4.setFocusPainted(false);
	        PanelLiquidation.permission = (UIConfigModelManager.getInstance().getPlainText(PanelDataProcessing.permission));
	        jButton4.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						PanelLiquidation importFromExcelPanel = new PanelLiquidation();
						DialogResto dialogResto = new DialogResto(importFromExcelPanel, false, 750, 300);
						dialogResto.setBtnSave(false);
						dialogResto.setModal(true);
						dialogResto.setIcon("phuchoi1.png");
						dialogResto.setTitle("Thanh lý dữ liệu");
						dialogResto.setLocationRelativeTo(null);
						dialogResto.setVisible(true);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					disposeForm();
				}
			});

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
	        this.setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	            		.addGap(26, 26, 26)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                    .addComponent(jButton3, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
	                    .addComponent(jButton1, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))
	                .addGap(53, 53, 53)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                    .addComponent(jButton4, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
	                    .addComponent(jButton2, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, Short.MAX_VALUE)	
	                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, Short.MAX_VALUE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, Short.MAX_VALUE)
	                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, Short.MAX_VALUE)))
	        );
	        
	       
	    }
	
	protected void disposeForm() {
		(((JDialog) getRootPane().getParent())).dispose();
		
	}


	@Override
	public void Save() throws Exception {
		(((JDialog) getRootPane().getParent())).dispose();
		
	}

}
