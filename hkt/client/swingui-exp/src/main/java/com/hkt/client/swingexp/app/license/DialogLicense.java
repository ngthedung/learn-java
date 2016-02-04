package com.hkt.client.swingexp.app.license;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.hethong.PanelRecybin;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.license.LicenseManager.module;

@SuppressWarnings("serial")
public class DialogLicense extends JPanel implements IDialogResto {

	JLabel jLabel1 = new javax.swing.JLabel();
	JTextField txtKeyMachine = new javax.swing.JTextField();
	JTextField txtKeyInstall = new javax.swing.JTextField();
	JLabel jLabel2 = new javax.swing.JLabel();
	private String keyMachine;
	private boolean result = false;

	public DialogLicense() {
		initComponents();
		initKeyMachine();
	}
	


	private void initComponents() {
		setOpaque(false);

		jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14));
		jLabel1.setText("KeyMachine"); // NOI18N
		jLabel1.setPreferredSize(new java.awt.Dimension(82, 30));

		txtKeyMachine.setFont(new java.awt.Font("Tahoma", 0, 14));
		txtKeyMachine.setPreferredSize(new java.awt.Dimension(6, 30));

		txtKeyInstall.setFont(new java.awt.Font("Tahoma", 0, 14));
		txtKeyInstall.setPreferredSize(new java.awt.Dimension(6, 30));

		jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14));
		jLabel2.setText("KeyActivate"); // NOI18N
		jLabel2.setPreferredSize(new java.awt.Dimension(82, 30));

		
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtKeyInstall, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE)
                    .addComponent(txtKeyMachine, javax.swing.GroupLayout.PREFERRED_SIZE,javax.swing.GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtKeyMachine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(8, 8, 8)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtKeyInstall, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
	}

	private void initKeyMachine() {
		try {

			keyMachine = ProductId.getInstance().getserialNumber();
		} catch (Exception e) {
			keyMachine = ProductId.getInstance().getSerialWindow();
		}
		if (keyMachine == null) {
			keyMachine = ProductId.getInstance().getSerialWindow();
		}
		txtKeyMachine.setText(keyMachine);
	}

	/**
	 * kiểm tra kí tự theo chuẩn base32
	 * 
	 * @param c
	 * @return
	 */
	private boolean checkChar(char c) {
		if ((c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
			return true;
		}
		return false;
	}

	/**
	 * kiểm tra chuỗi nhập đúng định dạng chuẩn ký tự Base32
	 * 
	 * @param string
	 * @return
	 */
	private boolean checkString(String string) {
		for (int i = 0; i < string.length(); i++) {
			if (!checkChar(string.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	// kiểm tra dữ liệu đầu vào

	private boolean checkFormatInput() {
		String keyInstall = txtKeyInstall.getText();
		if (keyInstall.isEmpty()) {

			String str = "Keyactive trống!";
	        PanelChoise panel = new PanelChoise(str);
	        panel.setName("Xoa");
	        DialogResto dialog = new DialogResto(panel, false, 600, 300);
	        dialog.setName("dlXoa");
	        dialog.setTitle("Bản quyền");
	        dialog.setLocationRelativeTo(null);
	        dialog.setModal(true);
	        dialog.setVisible(true);
			return false;
		}
		if (!checkString(keyInstall)) {

			String str = "Keyactive đã sử dụng hoặc không đúng bản quyền";
	        PanelChoise panel = new PanelChoise(str);
	        panel.setName("Xoa");
	        DialogResto dialog = new DialogResto(panel, false, 600, 300);
	        dialog.setName("dlXoa");
	        dialog.setTitle("Bản quyền");
	        dialog.setLocationRelativeTo(null);
	        dialog.setModal(true);
	        dialog.setVisible(true);
			return false;
		}
		return true;
	}

	private void activate() {
		// TODO thực hiện việc đăng ký quyền sử dụng
		if (checkFormatInput()) {
			DecryptAES daes = new DecryptAES(txtKeyMachine.getText(), txtKeyInstall.getText());
			String keyActivate = daes.decrypt();
			if (!analyst(keyActivate)) {

				PanelRecybin panel = new PanelRecybin(
						"Key đăng ký không đúng chuẩn!",
						"Hãy xem lại key hoặc liên hệ với nhà cung cấp!");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false,
						650, 300);
				dialog.setName("dlXoa");
				dialog.setTitle("Bản quyền");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				this.result = false;
			} else {
				DialogNotice.getInstace().setVisible(true);
				this.result = true;
				((JDialog) getRootPane().getParent()).dispose();
			}
		}
	}

	private boolean analyst(String keyActivate) {
		try {
			String admin = keyActivate.substring(0, 1);
			String mayTram = keyActivate.substring(1, 2);
			DateFormat df = new SimpleDateFormat("ddMMyy");
			String dateString = keyActivate.substring(2, 8);
			String numberMonth = keyActivate.substring(8, 14);
			String allModule = keyActivate.substring(14, 15);
			Date dateStart = df.parse(dateString);
			Date dateEnd = null;
			Calendar caStart = Calendar.getInstance();
			caStart.setTime(dateStart);
			Calendar caEnd = Calendar.getInstance();
			if ("      ".equals(numberMonth)) {
				caEnd.set(Calendar.YEAR, caEnd.getActualMaximum(Calendar.YEAR));
			} else {
				dateEnd = df.parse(numberMonth);
				caEnd.setTime(dateEnd);
			}
			String[] modules = null;
			List<String> moduleCodes = new ArrayList<String>();
			if ("A".equals(allModule)) {
				String[] module1 = { module.MenuVoucher.name(), module.Point.name(), module.Promotion.name(), module.Report.name(),
						module.Restaurant.name(), module.Warehouse.name() };
				modules = module1;
				for (int i = 0; i < modules.length; i++) {
					moduleCodes.add(modules[i]);
				}
			} else {
				modules = keyActivate.substring(14, keyActivate.length()).split("/");
				for (int i = 0; i < modules.length; i++) {
					String str = "";
					if (modules[i].toString().equals("B")) {
						str = module.Restaurant.name();
					}
					if (modules[i].toString().equals("K")) {
						str = module.Promotion.name();
					}
					if (modules[i].toString().equals("Q")) {
						str = module.Warehouse.name();
					}
					if (modules[i].toString().equals("T")) {
						str = module.Report.name();
					}
					if (modules[i].toString().equals("M")) {
						str = module.MenuVoucher.name();
					}
					if (modules[i].toString().equals("Đ")) {
						str = module.Point.name();
					}
					moduleCodes.add(str);
				}
			}

			KeyActivateLicense kal = new KeyActivateLicense();
			kal.setEndDate(caEnd.getTime());
			kal.setStartDate(caStart.getTime());
			kal.setLicense(txtKeyInstall.getText());
			kal.setKey(txtKeyMachine.getText());
			kal.setModuleCodes(moduleCodes);
			if (admin.equals("A")) {
				kal.setAdmin(true);
			} else {
				kal.setAdmin(false);
			}

			if (mayTram.equals("C")) {
				kal.setServer(true);
			} else {
				kal.setServer(false);
			}
			HashMap<String, KeyActivateLicense> keys;
			try {
			  keys = LicenseManager.getInstance().getKeyActivateLicenses();
      } catch (Exception e) {
        keys = new HashMap<String, KeyActivateLicense>();
      }
			
			keys.put(txtKeyMachine.getText(), kal);

			LicenseManager.getInstance().saveKeyActivate(keys);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public boolean isResult() {
		return result;
	}

	@Override
	public void Save() throws Exception {
		activate();
		
	}
}
