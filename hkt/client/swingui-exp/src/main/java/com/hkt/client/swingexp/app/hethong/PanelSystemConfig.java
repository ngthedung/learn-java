package com.hkt.client.swingexp.app.hethong;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.GridLabelLayoutPabel;
import com.hkt.client.swingexp.app.component.PanelBackground;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.HKTFile;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.homescreen.HomeScreen;

public class PanelSystemConfig extends JPanel implements IDialogResto {
	private static PanelSystemConfig systemConfig;

	public static PanelSystemConfig getInstance() {
		if (systemConfig == null) {
			systemConfig = new PanelSystemConfig();
		}
		return systemConfig;
	}

	private JComboBox comboBox;
	private JTextField txtServerName;

	public PanelSystemConfig() {
		setOpaque(false);
		setLayout(new BorderLayout());

		InputPanel inputPanel = new InputPanel();
		inputPanel.setOpaque(false);
		add(inputPanel, BorderLayout.CENTER);
		try {
			Runtime.getRuntime().exec("taskkill /F /IM HKTSoft.exe");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private class InputPanel extends JPanel {
		public InputPanel() {
			MyGroupLayout groupLayout = new MyGroupLayout(this);
			groupLayout.add(0, 0, new JLabel("Cài đặt trên"));
			String[] cbHeThong = { "Máy chủ", "Máy trạm", "Máy trạm online" };
			comboBox = new JComboBox(cbHeThong);
			groupLayout.add(0, 1, comboBox);

			groupLayout.add(1, 0, new JLabel("Tên máy chủ"));
			txtServerName = new JTextField();
			txtServerName.setFont(new Font("Tahoma", Font.PLAIN, 14));
			txtServerName.setText("localhost");
			groupLayout.add(1, 1, txtServerName);
			groupLayout.updateGui();
			comboBox.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					if (comboBox.getSelectedItem().toString().equals("Máy chủ")) {
						txtServerName.setText("localhost");
					} else {
						txtServerName.setText("");
						if (comboBox.getSelectedItem().toString().equals("Máy trạm online")) {
							txtServerName.setEnabled(false);
						} else {
							txtServerName.setEnabled(true);
						}
					}

				}
			});
			try {
				String str = readData();
				String str1 = str.split("/")[2].split(":")[0];
				if (!str1.equals("localhost")) {
					comboBox.setSelectedIndex(1);
					txtServerName.setText(str1);
				}
			} catch (Exception e) {

			}

		}
	}

	private void writeData(String str) {
		try {
			FileOutputStream fi = new FileOutputStream(HKTFile.getFile("Database", "rest"));
			ObjectOutputStream of = new ObjectOutputStream(fi);
			of.writeObject(str);
			of.reset();
			of.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}
	
	private void writeDataOnline(String str) {
		try {
			FileOutputStream fi = new FileOutputStream(HKTFile.getFile("Database", "online"));
			ObjectOutputStream of = new ObjectOutputStream(fi);
			of.writeObject(str);
			of.reset();
			of.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	private String readData() {
		try {
			FileInputStream fi = new FileInputStream(HKTFile.getFile("Database", "rest"));
			ObjectInputStream of = new ObjectInputStream(fi);
			String str = of.readObject().toString();
			of.close();
			return str;
		} catch (Exception e) {
			return "";
		}
	}

	public void showConfig() {
		try {
			String str = readData();
			if (str.trim().isEmpty()) {
				PanelBackground.backgroundIcon = new ImageIcon(HomeScreen.class.getResource("icon/KaraBackgroud.jpg"));
				PanelSystemConfig systemConfig = new PanelSystemConfig();
				DialogResto dialog = new DialogResto(systemConfig, false, 0, 110);
				dialog.setLocationRelativeTo(null);
				dialog.setTitle("Cấu hình hệ thống");
				dialog.setVisible(true);
			}
		} catch (Exception e) {
			PanelSystemConfig systemConfig = new PanelSystemConfig();
			DialogResto dialog = new DialogResto(systemConfig, false, 0, 110);
			dialog.setLocationRelativeTo(null);
			dialog.setTitle("Cấu hình hệ thống");
			dialog.setVisible(true);

		}
	}

	public boolean runSystemCommand(String command) {
		boolean flag = false;
		String OS = System.getProperty("os.name").toUpperCase();
		try {

			Process p = Runtime.getRuntime().exec("ping " + command);
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String s = "";
			// reading output stream of the command
			while ((s = inputStream.readLine()) != null) {
				System.out.println(OS);
				if (OS.contains("NUX")) {
					System.out.println(s);
				if(s.indexOf("ttl=")>0){
					return true;
				}
			}else {
				try {
					if (Double.parseDouble(s.substring(s.indexOf("TTL=") + 4, (s.length()))) <= 255) {
						flag = true;
					}
				} catch (Exception e) {
				}
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public void Save() throws Exception {
		try {
			String str1 = "http://" + txtServerName.getText() + ":7080/rest";
			if (!txtServerName.getText().equals("localhost")) {
				if (!txtServerName.isEnabled()) {
					writeDataOnline("online");
					try {
						String str = this.getClass().getResource("").getPath().substring(5);
						File file = new File(str.substring(0, str.indexOf("lib")) + "bin\\HKTSoft.exe");
						String aa = file.getAbsolutePath().replaceAll("%20", " ");
						String[] processCommand = { "cmd", "/c", aa };
						Runtime.getRuntime().exec(processCommand);

						System.exit(0);

					} catch (Exception e1) {
					}
				}
				System.out.println(runSystemCommand(txtServerName.getText()));
				if (runSystemCommand(txtServerName.getText())) {
						writeData(str1);
						writeDataOnline("false");
					// ((JDialog) getRootPane().getParent()).dispose();
					try {
						String str = this.getClass().getResource("").getPath().substring(5);
						File file = new File(str.substring(0, str.indexOf("lib")) + "bin\\HKTSoft.exe");
						String aa = file.getAbsolutePath().replaceAll("%20", " ");
						String[] processCommand = { "cmd", "/c", aa };
						Runtime.getRuntime().exec(processCommand);

						System.exit(0);

					} catch (Exception e1) {
					}

				} else {
					JOptionPane.showMessageDialog(null, "Không thể kết nối đến " + txtServerName.getText());
				}
			} else {
					writeData(str1);
				
				// ((JDialog) getRootPane().getParent()).dispose();
				try {
					String str = this.getClass().getResource("").getPath().substring(5);
					File file = new File(str.substring(0, str.indexOf("lib")) + "bin\\HKTSoft.exe");

					String aa = file.getAbsolutePath().replaceAll("%20", " ");
					String[] processCommand = { "cmd", "/c", aa };

					Runtime.getRuntime().exec(processCommand);
					System.out.println("buc minh");

					System.exit(0);

				} catch (Exception e1) {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
