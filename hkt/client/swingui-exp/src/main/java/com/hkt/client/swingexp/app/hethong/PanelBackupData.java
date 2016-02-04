package com.hkt.client.swingexp.app.hethong;

import java.awt.Font;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.module.promotion.BackupRestoreData;
import com.hkt.module.promotion.CheckOS;

@SuppressWarnings("serial")
public class PanelBackupData extends JPanel implements IDialogResto {
	private BackupRestoreData backupRestoreData;
	private Processing processing;
	public static String permission;

	public PanelBackupData() {

		initComponents();
		backupRestoreData = new BackupRestoreData();
		processing = new Processing();
	}

	private void initComponents() {
		setOpaque(false);

		jLabel1 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();

		jLabel1.setText("Bước 1. Click button Sao lưu");
		jLabel1.setFont(new Font("Tahoma", Font.BOLD, 14));

		jLabel4.setText("Bước 2. Chọn nơi lưu dữ liệu");
		jLabel4.setFont(new Font("Tahoma", Font.BOLD, 14));

		jLabel8.setText("Bước 3. Click button OK");
		jLabel8.setFont(new Font("Tahoma", Font.BOLD, 14));


		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
														.addGap(150, 150, 150)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(jLabel4)
																				.addComponent(jLabel1)
																				.addComponent(jLabel8))))));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						
						.addGap(9, 9, 9).addComponent(jLabel1)
						.addGap(18, 18, 18).addComponent(jLabel4)
						.addGap(18, 18, 18).addComponent(jLabel8)
//						.addGap(34, 34, 34)
						));
	}// </editor-fold>
		// Variables declaration - do not modify

	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel8;

	// End of variables declaration

	private void backupAll() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Data Documents", ".sql");
		fileChooser.setFileFilter(filter);
		int i = fileChooser.showSaveDialog(this);
		if (i == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			String extension = filter.getExtensions()[0];
			final String fileURL = file.getAbsolutePath() + extension;
			try {
				processing.setVisible(true);
				new SwingWorker<Void, Void>() {

					@SuppressWarnings("static-access")
					@Override
					protected Void doInBackground() throws Exception {
						// backupRestoreData.tbBackup("hktdb", "root", "123456",
						// fileURL);
						CheckOS checkOS = new CheckOS();
						if (checkOS.isWindows()) {
							backupRestoreData.tbBackupWin("hktdb", "root",
									"root", fileURL);
						} else if (checkOS.isUnix()) {
							backupRestoreData.tbBackupLinux("hktdb", "root",
									"root", fileURL);
						}
						return null;
					};

					@Override
					protected void done() {
						processing.setVisible(false);
						exit();
					};
				}.execute();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null,
						"Lỗi, không thực hiện được!" + ex.toString());
			}
		}
	}

	private void exit() {
		(((JDialog) getRootPane().getParent())).dispose();
	}

	@Override
	public void Save() throws Exception {
		backupAll();

	}
}
