package com.hkt.client.swingexp.app.hethong;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialogPlus;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.module.promotion.BackupRestoreData;
import com.hkt.module.promotion.CheckOS;

@SuppressWarnings("serial")
public class PanelRestoreData extends JPanel implements IDialogResto {
	private BackupRestoreData backupRestoreData;
	private Processing processing;
	 public static String permission;

	public PanelRestoreData() {
		initComponents();
		backupRestoreData = new BackupRestoreData();
		processing = new Processing();
		try {
			if (CheckOS.isWindows()) {
				Runtime.getRuntime().exec("taskkill /F /IM HKTServer.exe");
			}
		} catch (Exception e) {
		}
	}
 

	private void initComponents() {
		setOpaque(false);

		jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        jButton1.setText("Phục hồi Dữ liệu");
        jButton1 = new JButton("Phục hồi Dữ liệu");
        jButton1.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/iconOk.png")));
        jButton1.setFocusPainted(false);
        jButton1.addMouseListener(new MouseEventClickButtonDialogPlus("iconOk.png", "iconOk.png", "iconOk.png"));
        jButton1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//
//				JOptionPane.showConfirmDialog(panelBackground, "Dự liệu hiện tại sẽ bị xóa và không thể khôi phục lại được, bạn chắc chắn tiếp tục phục hồi dữ liệu không?", "Thông Báo", 2);
//				restoreAll();//
				PanelOKRestore panel = new PanelOKRestore(
						" Dự liệu hiện tại sẽ bị xóa và không thể khôi phục lại được!",
						"Bạn có chắc chắn muốn tiếp tục phục hồi dữ liệu không?");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 120);
				dialog.setName("dlXoa");
				dialog.setTitle("Phục hồi dữ liệu");
				dialog.setTextBtnSave("Tiếp tục");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);	
				dialog.setVisible(true);
				if (panel.isDelete()) {
					restoreAll();
				}
				
			}
		});

        jLabel1.setText("Bước 1. Click button Phục hồi Dữ liệu");
        jLabel1.setFont(new Font("Tahoma", Font.BOLD, 14));

        jLabel3.setText("Bước 2. Tìm chọn file dữ liệu đã sao lưu cần phục hồi");
        jLabel3.setFont(new Font("Tahoma", Font.BOLD, 14));

        jLabel2.setText("Bước 3. Click Save để bắt đầu qui trình phục hồi dữ liệu");
        jLabel2.setFont(new Font("Tahoma", Font.BOLD, 14));
        
        jLabel6.setText("Chú ý: Dữ liệu bị xóa sẽ không thể khôi phục lại!");
        jLabel6.setFont(new Font("Tahoma", Font.BOLD, 14));
        jLabel6.setForeground(new Color(124, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6))))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
//                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(25, 25, 25)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel6)
//                .addContainerGap(20, Short.MAX_VALUE)
                )
        );
    }// </editor-fold>
    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    // End of variables declaration

	private void restoreAll() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setAcceptAllFileFilterUsed(false);
		int i = fileChooser.showSaveDialog(this);
		if (i == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			final String fileURL = file.getAbsolutePath();
			try {
				processing.setVisible(true);
				new SwingWorker<Void, Void>() {

					@SuppressWarnings("static-access")
					@Override
					protected Void doInBackground() throws Exception {
						CheckOS checkOS = new CheckOS();
						if (checkOS.isWindows()) {
							backupRestoreData.restoreDBWin("hktdb", "root", "root", fileURL);
						} else if (checkOS.isUnix()) {
							backupRestoreData.restoreDBLinux("hktdb", "root", "root", fileURL);
						}
						// backupRestoreData.restoreDB("hktdb", "root",
						// "123456", fileURL);
						return null;
					};

					@Override
					protected void done() {
						processing.setVisible(false);
						PanelOKRestore panel = new PanelOKRestore(
								"Dự liệu đã phục hồi thành công!",
								"Bạn vui lòng khởi động lại phần mềm!");
						panel.setName("Xoa");
						DialogResto dialog = new DialogResto(panel, false, 0, 120);
						dialog.setName("dlXoa");
						dialog.setTitle("Phục hồi dữ liệu");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);	
						dialog.setVisible(true);
						if (panel.isDelete()) {
							exit();
						}
						
					};
				}.execute();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Lỗi, không thực hiện được!" + ex.toString());
			}
		}
	}

	private void exit() {
		try {
			if (CheckOS.isWindows()) {
				Runtime.getRuntime().exec("taskkill /F /IM HKTServer.exe");
				Runtime.getRuntime().exec("taskkill /F /IM java.exe");
				System.exit(0);
			}
		} catch (Exception e) {
		}
	}


	@Override
	public void Save() throws Exception {
		(((JDialog) getRootPane().getParent())).dispose();
	}

}