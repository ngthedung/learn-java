package com.hkt.client.swingexp.app.hethong;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.license.LicenseManager;
import com.hkt.client.swingexp.app.license.ProductId;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.account.entity.Profile;

@SuppressWarnings("serial")
public class DialogInfo extends JPanel implements IDialogResto {

	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel12;
	private javax.swing.JLabel jLabel13;
	private javax.swing.JLabel jLabel14;
	private javax.swing.JLabel jLabel15;
	private javax.swing.JLabel jLabel16;
	private javax.swing.JLabel jLabel17;
	private javax.swing.JLabel jLabel18;
	private javax.swing.JLabel jLabel19;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel20;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;

	private javax.swing.JLabel jLabel9;

	public DialogInfo() {

		this.setBackground(new Color(255, 255, 255));
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		jLabel10 = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		jLabel12 = new javax.swing.JLabel();
		jLabel13 = new javax.swing.JLabel();
		jLabel14 = new javax.swing.JLabel();
		jLabel15 = new javax.swing.JLabel();
		jLabel16 = new javax.swing.JLabel();
		jLabel17 = new javax.swing.JLabel();
		jLabel18 = new javax.swing.JLabel();
		jLabel19 = new javax.swing.JLabel();
		jLabel20 = new javax.swing.JLabel();

		jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel1.setText("Phiên bản:");

		jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12));
		jLabel2.setText("Version 4.0 " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

		jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		jLabel3.setText("Bản quyền:");

		jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12));
		jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		jLabel4.setText("Cty CP Tư vấn Quản trị HKT (HKT Consultant)");

		jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel5.setText("Tên phần mềm:");

		jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12));
		jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		jLabel6.setText("HKT Software");

		jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel7.setText("Website:");

		jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12));
		jLabel8.setForeground(new java.awt.Color(0, 51, 255));
		jLabel8.setText("www.HktConsultant.com");

		jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		jLabel9.setText("Email:");

		jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12));
		jLabel10.setForeground(new java.awt.Color(0, 51, 255));
		jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		jLabel10.setText("HktConsultant@gmail.com"); // NOI18N

		jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		jLabel11.setText("Hỗ trợ kỹ thuật:");

		jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12));
		jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		jLabel12.setText("+84 437 925 191");

		jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		jLabel13.setText("        Ghi chú: Phần mềm có bản quyền theo số đăng ký");

		jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel14.setText("0105149688");

		jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		jLabel15.setText("        Quý khách đã chấp nhận điều khoản sử dụng và bản quyền của sản phẩm.");

		jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12));
		jLabel16.setForeground(new java.awt.Color(126, 0, 0));
		jLabel16.setText("        Mã bản quyền sản phẩm:");

		jLabel18.setText("      Phiên bản dùng thử");
		jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12));
		jLabel18.setForeground(new java.awt.Color(126, 0, 0));

		jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12));
		jLabel17.setForeground(new java.awt.Color(126, 0, 0));

		jLabel20.setForeground(new java.awt.Color(126, 0, 0));
		jLabel20.setFont(new java.awt.Font("Tahoma", 10, 20));
		jLabel20.setText("HKT CONSULTANT");

		getContentPanel();
		checkKeyActive();
		addKeyBindings(this);
	}

	private void getContentPanel() {
		setOpaque(false);
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    javax.swing.GroupLayout.Alignment.TRAILING,
		    layout.createSequentialGroup().addGroup(
		        layout
		            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		            .addGroup(
		                javax.swing.GroupLayout.Alignment.TRAILING,
		                layout
		                    .createSequentialGroup()
		                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137,
		                        javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel18).addGap(350, 350, 350))
		            .addGroup(
		                layout.createSequentialGroup().addGroup(
		                    layout
		                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                        .addGap(34, 34, 34)
		                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 384,
		                            javax.swing.GroupLayout.PREFERRED_SIZE)
		                        .addComponent(jLabel19)
		                        .addComponent(jLabel16)
		                        .addComponent(jLabel17)
		                        .addComponent(jLabel15)
		                        .addGroup(layout.createSequentialGroup().addGap(134, 134, 134).addComponent(jLabel14))
		                        .addGroup(
		                            layout
		                                .createSequentialGroup()
		                                .addGap(34, 34, 34)
		                                .addGroup(
		                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                        .addComponent(jLabel1).addComponent(jLabel3).addComponent(jLabel5)
		                                        .addComponent(jLabel7).addComponent(jLabel9).addComponent(jLabel11))
		                                .addGap(30, 30, 30)
		                                .addGroup(
		                                    layout
		                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                        .addComponent(jLabel20)
		                                        .addGroup(
		                                            layout
		                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                                                .addComponent(jLabel12)
		                                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE,
		                                                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                                                .addComponent(jLabel8)
		                                                .addComponent(jLabel6)
		                                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE,
		                                                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                                                .addComponent(jLabel2)))))))));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		    layout
		        .createSequentialGroup()
		        .addGap(9, 9, 9)
		        .addComponent(jLabel20)
		        .addGap(18, 18, 18)
		        .addGroup(
		            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel1)
		                .addComponent(jLabel2))
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		        .addGroup(
		            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel3)
		                .addComponent(jLabel4))
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		        .addGroup(
		            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel5)
		                .addComponent(jLabel6))
		        .addGap(18, 18, 18)
		        .addGroup(
		            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel7)
		                .addComponent(jLabel8))
		        .addGap(18, 18, 18)
		        .addGroup(
		            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel9)
		                .addComponent(jLabel10))
		        .addGap(18, 18, 18)
		        .addGroup(
		            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel11)
		                .addComponent(jLabel12)).addGap(18, 18, 18).addComponent(jLabel13)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel14)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel15)
		        .addGap(18, 18, 18).addComponent(jLabel16).addGap(18, 18, 18).addComponent(jLabel17).addGap(18, 18, 18)
		        .addComponent(jLabel18).addGap(18, 18, 18).addComponent(jLabel19).addGap(18, 18, 18)));

	}

	private void checkKeyActive() {
		String key = LicenseManager.getInstance().getLience();
		int time = 0;

		time = LicenseManager.getInstance().getTime() * (-1);
		String str = "        Thời gian dùng thử còn: " + time + " NGÀY";
		if (time <= 0) {
			str = "        Thời gian dùng thử đã: HẾT HẠN ";
		}
		if (key.trim().isEmpty()) {
			jLabel16.setText("         Phiên bản dùng thử");
			jLabel16.setHorizontalAlignment(JLabel.CENTER);
			jLabel17.setText(str);
			jLabel18.setText("        Mời quý khách liên hệ với công ty để đăng ký bản quyền!");
		} else {// Mã bản quyền gắn liền với 01 thiết bị khách hàng đăng ký cài
			// đặt
			jLabel16.setText(jLabel16.getText() + " 	: " + key);
			jLabel17.setText("        Mã bản quyền gắn liền với 01 thiết bị khách hàng đăng ký cài đặt");
			jLabel18.setText("        Xin quý khách vui lòng lưu lại cho những lần cài đặt sau!");
			jLabel19.setText("");
		}
	}

	@Override
	public void Save() throws Exception {

		((JDialog) getRootPane().getParent()).dispose();
	}

	public void addKeyBindings(JComponent jc) {

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0, false), "F12");
		jc.getActionMap().put("F12", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				Profile p = AccountModelManager.getInstance().getProfileConfigAdmin();
				if (p.get("LicenseDateMore") == null) {
					PanelChoise pnPanel = new PanelChoise("Gia hạn thêm 1 tháng?");
					DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
					dialog1.setTitle("Gia hạn phần mềm");
					dialog1.setLocationRelativeTo(null);
					dialog1.setModal(true);
					dialog1.setVisible(true);
					if (pnPanel.isDelete()) {
						DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
						Calendar c = Calendar.getInstance();
						c.add(Calendar.DAY_OF_MONTH, 30);
						String keyMachine = ProductId.getInstance().getSerialWindow();
						p.put(keyMachine, df.format(c.getTime()));
						p.put("LicenseDateMore", df.format(c.getTime()));
						AccountModelManager.getInstance().saveProfileConfig(
						    ManagerAuthenticate.getInstance().getOrganizationLoginId(), p);
					}
				}
			}
		});
	}
}
