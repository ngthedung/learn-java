package com.hkt.client.swingexp.app.khuyenmai;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJRadioButton;
import com.hkt.client.swingexp.app.component.ExtendJTextArea;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.core.UnitMoneyJComboBox;
import com.hkt.client.swingexp.app.hethong.PanelRecybin;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.config.locale.Currency;
import com.hkt.module.promotion.entity.Coupon;

public class PanelCoupon extends MyPanel implements IDialogMain {
	private ExtendJLabel lbTuNgay, lbTenPhieu, lbGiaTri, lbLoaiKhuyenMai,
			lbSoCT, lbSoLanDung, lbMaPhieu, lbDenNgay, lbMieuTa, lbThongBao;
	private ExtendJTextField txtTenPhieu, txtGiaTri, txtSoLanDung, txtMaPhieu,
			txtSoCT;
	private ExtendJTextArea txtMieuTa;
	private MyJDateChooser txtTuNgay, txtDenNgay;
	private UnitMoneyJComboBox cbDonViTien;
	private ExtendJRadioButton rdoPhanTram, rdoTienMat;
	private JScrollPane scrollPane;
	private Coupon coupon;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private boolean isNew = true;
	private boolean restore = true;
	public static String permission;
	private boolean reset  = false;

	// Hàm tạo Class
	public PanelCoupon() throws Exception {
		coupon = new Coupon();
		init();
		reset();
	}

	// Khởi tạo các components và sắp xếp trên giao diện
	private void init() {
		createBeginTest();
		Date dateTime = new Date();
		lbTenPhieu = new ExtendJLabel("Tên phiếu");
		lbTuNgay = new ExtendJLabel("Từ ngày");
		lbGiaTri = new ExtendJLabel("Giá trị");
		lbSoLanDung = new ExtendJLabel("SL dùng");
		lbMaPhieu = new ExtendJLabel("Mã phiếu");
		lbDenNgay = new ExtendJLabel("Đến ngày");
		lbLoaiKhuyenMai = new ExtendJLabel("Loại");
		lbSoCT = new ExtendJLabel("Số CT");
		lbMieuTa = new ExtendJLabel("Miêu tả");
		lbThongBao = new ExtendJLabel("");
		lbThongBao.setForeground(Color.RED);

		txtTenPhieu = new ExtendJTextField();
		txtGiaTri = new ExtendJTextField();
		txtTuNgay = new MyJDateChooser();
		txtDenNgay = new MyJDateChooser();
		txtSoLanDung = new ExtendJTextField();
		txtMaPhieu = new ExtendJTextField();
		txtMieuTa = new ExtendJTextArea();
		txtSoCT = new ExtendJTextField();
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(txtMieuTa);

		cbDonViTien = new UnitMoneyJComboBox();

		rdoTienMat = new ExtendJRadioButton("Tiền mặt");
		rdoTienMat.setOpaque(false);
		rdoTienMat.addActionListener(new RadioListener());

		rdoPhanTram = new ExtendJRadioButton("%");
		rdoPhanTram.setOpaque(false);
		rdoPhanTram.addActionListener(new RadioListener());

		ButtonGroup groupRd = new ButtonGroup();
		groupRd.add(rdoPhanTram);
		groupRd.add(rdoTienMat);
		rdoTienMat.setSelected(true);

		txtTenPhieu.setName("txtTenPhieu");
		txtGiaTri.setName("txtGiaTri");
		txtSoLanDung.setName("txtSoLanDung");
		txtMaPhieu.setName("txtMaPhieu");
		txtMieuTa.setName("txtMieuTa");
		txtSoCT.setName("txtSoCT");
		cbDonViTien.setName("cbDonViTien");
		rdoTienMat.setName("rdoTienMat");
		rdoPhanTram.setName("rdoPhanTram");

		txtTuNgay.setDate(new Date());
		txtMaPhieu.setText(sdf.format(dateTime));
		txtSoCT.setText("1");

		((JTextField) txtTuNgay.getDateEditor())
				.setHorizontalAlignment(JTextField.RIGHT);
		((JTextField) txtDenNgay.getDateEditor())
				.setHorizontalAlignment(JTextField.RIGHT);
		txtGiaTri.setHorizontalAlignment(JTextField.RIGHT);
		txtSoLanDung.setHorizontalAlignment(JTextField.RIGHT);
		txtSoCT.setHorizontalAlignment(JTextField.RIGHT);

		UIManager.put("RadioButton.disabledText", new Color(51, 51, 51));
		UIManager.put("RadioButton.foreground", new Color(51, 51, 51));
		UIManager.put("TextField.inactiveBackground", Color.WHITE);
		UIManager.put("TextField.inactiveForeground", new Color(51, 51, 51));
		UIManager.put("TextArea.inactiveForeground", new Color(51, 51, 51));
		UIManager.put("TextArea.inactiveBackground", Color.WHITE);

		PanelContainer panelGiaTri = new PanelContainer(txtGiaTri, cbDonViTien);
		PanelContainer panelLoai = new PanelContainer(rdoPhanTram, rdoTienMat);

		MyGroupLayout layout = new MyGroupLayout(this);
		layout.add(0, 0, lbTenPhieu);
		layout.add(0, 1, txtTenPhieu);
		layout.add(0, 2, lbMaPhieu);
		layout.add(0, 3, txtMaPhieu);
		layout.add(1, 0, lbTuNgay);
		layout.add(1, 1, txtTuNgay);
		layout.add(1, 2, lbDenNgay);
		layout.add(1, 3, txtDenNgay);
		layout.add(2, 0, lbGiaTri);
		layout.add(2, 1, panelGiaTri);
		layout.add(2, 2, lbLoaiKhuyenMai);
		layout.add(2, 3, panelLoai);
		layout.add(3, 0, lbSoLanDung);
		layout.add(3, 1, txtSoLanDung);
		layout.add(3, 2, lbSoCT);
		layout.add(3, 3, txtSoCT);
		layout.add(4, 0, lbMieuTa);
		layout.add(4, 1, scrollPane);
		layout.addMessenger(lbThongBao);
		layout.updateGui();
	}

	// Panel chứa 2 components bên trong
	protected class PanelContainer extends JPanel {
		public PanelContainer(Component comp1, Component comp2) {
			this.setLayout(new BorderLayout(0, 0));
			this.setOpaque(false);
			this.add(comp1, BorderLayout.CENTER);
			this.add(comp2, BorderLayout.LINE_END);
		}
	}

	// Hàm lấy dữ liệu người nhập cho vào đối tượng
	private void getData() {
		restore = true;
		coupon.setName(txtTenPhieu.getText());
		coupon.setCode(txtMaPhieu.getText());
		coupon.setFromDate(txtTuNgay.getDate());
		coupon.setToDate(txtDenNgay.getDate());
		coupon.setDescription(txtMieuTa.getText());
		if (rdoPhanTram.isSelected()) {
			coupon.setDiscountRate(Double.parseDouble(txtGiaTri.getText()));
			coupon.setDiscount(0);
			coupon.setCurrencyUnit("%");
		}
		if (rdoTienMat.isSelected()) {
			coupon.setDiscount(Double.parseDouble(txtGiaTri.getText()));
			coupon.setDiscountRate(0);
			coupon.setCurrencyUnit(cbDonViTien.getSelectedCurrency().getCode());
		}
		if (!txtSoLanDung.getText().equals(""))
			coupon.setMaxUsePerCustomer(Integer.parseInt(txtSoLanDung.getText()));
	}

	// Hàm hiển thị dữ liệu ra giao diện
	public void setData(Coupon _coupon) throws ParseException {
		this.coupon = _coupon;
		txtTenPhieu.setText(coupon.getName());
		txtMaPhieu.setText(coupon.getCode());
		txtTuNgay.setDate(coupon.getFromDate());
		txtDenNgay.setDate(coupon.getToDate());
		txtGiaTri.setText(Double.toString(coupon.getDiscount()));
		if (coupon.getDiscountRate() == 0) {
			cbDonViTien.setSelectedCurrency(coupon.getCurrencyUnit());
			rdoTienMat.setSelected(true);
			rdoPhanTram.setSelected(false);
		} else {
			txtGiaTri.setText(Double.toString(coupon.getDiscountRate()));
			cbDonViTien.setEnabled(false);
			rdoTienMat.setSelected(false);
			rdoPhanTram.setSelected(true);
		}
		txtSoLanDung.setText(String.valueOf(coupon.getMaxUsePerCustomer()));
		txtMieuTa.setText(coupon.getDescription());
		setEnableCompoment(false);
	}

	// Thiết lập thuộc tính có chỉnh sửa hay không?
	private void setEnableCompoment(boolean value) {
		txtTenPhieu.setEnabled(value);
		txtMaPhieu.setEnabled(value);
		txtTuNgay.setEnabled(value);
		txtDenNgay.setEnabled(value);
		txtGiaTri.setEnabled(value);
		txtSoLanDung.setEnabled(value);
		txtSoCT.setEnabled(value);
		txtMieuTa.setEnabled(value);
		cbDonViTien.setEnabled(value);
		rdoPhanTram.setEnabled(value);
		rdoTienMat.setEnabled(value);
	}

	// Kiểm tra dữ liệu trước khi lưu
	private boolean checkData() {
		boolean check = true;
		boolean codeError = false;
		// Kiểm tra tên ko được bỏ trống
		if (txtTenPhieu.getText().equals("")) {
			lbTenPhieu.setForeground(Color.RED);
			check = false;
		} else
			lbTenPhieu.setForeground(new Color(51, 51, 51));
		// Kiểm tra mã không bỏ trống
		if (txtMaPhieu.getText().equals("")) {
			lbMaPhieu.setForeground(Color.RED);
			check = false;
		} else {
			lbMaPhieu.setForeground(new Color(51, 51, 51));
			if (isNew) {
				// Kiểm tra trùng mã hay không?
				try {
					Coupon c = PromotionModelManager.getInstance()
							.getCouponByCode(txtMaPhieu.getText());
					if (c != null) {
						check = false;
						codeError = true;
						lbMaPhieu.setForeground(Color.RED);
						if (c.isRecycleBin()) {
							PanelRecybin panel = new PanelRecybin(
									"Mã đã bị xóa trước đó!",
									" Chọn ĐỒNG Ý để lấy lại thông tin ban đầu hoặc THOÁT và nhập mã khác!");
							panel.setName("Xoa");
							DialogResto dialog = new DialogResto(panel, false, 0, 120);
							dialog.setName("dlXoa");
							dialog.setTitle("Lập phiếu giảm giá");
							dialog.setLocationRelativeTo(null);
							dialog.setModal(true);
							dialog.setVisible(true);
							if (panel.isDelete()) {
								restore = false;
								c.setRecycleBin(false);
								PromotionModelManager.getInstance().saveCoupon(
										c);
								lbMaPhieu.setForeground(Color.black);
								reset();
								return true;
							}

						}
					}
				} catch (Exception ex) {
					return false;
				}
			}
		}
		// Kiểm tra ngày bắt đầu khác rỗng
		if (txtTuNgay.getDate() == null) {
			check = false;
			lbTuNgay.setForeground(Color.RED);
		} else
			lbTuNgay.setForeground(new Color(51, 51, 51));
		// Kiểm tra giá trị khác rỗng và phải là số
		if (txtGiaTri.getText().equals("")) {
			check = false;
			lbGiaTri.setForeground(Color.red);
		} else {
			lbGiaTri.setForeground(new Color(51, 51, 51));
			try {
				Double.parseDouble(txtGiaTri.getText());
			} catch (Exception ex) {
				check = false;
				lbGiaTri.setForeground(Color.red);
			}
		}
		// Kiểm tra SL dùng phải là số khác kí tự
		if (!txtSoLanDung.getText().equals("")) {
			try {
				Double.parseDouble(txtSoLanDung.getText());
				lbSoLanDung.setForeground(new Color(51, 51, 51));
			} catch (Exception ex) {
				check = false;
				lbSoLanDung.setForeground(Color.red);
			}
		}
		// Kiểm tra số CT phải là số khác kí tự
		if (!txtSoCT.getText().equals("")) {
			try {
				Double.parseDouble(txtSoCT.getText());
				lbSoCT.setForeground(new Color(51, 51, 51));
			} catch (Exception ex) {
				check = false;
				lbSoCT.setForeground(Color.red);
			}
		}
		// Kiểm tra toàn bộ và thông báo lỗi nếu có
		if (!check) {
			lbThongBao.setText("Kiểm tra lỗi báo đỏ !");
			if (codeError)
				lbThongBao.setText("Lỗi trùng mã khuyến mại");
			return false;
		} else {
			lbThongBao.setText("");
			return true;
		}
	}

	// Phương thức cho nút [Lưu]
	@Override
	public void save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {
			if (checkData()) {
				if (restore) {
					getData();

					// int SoCT = Integer.parseInt(txtSoCT.getText());
					// if (SoCT > 1)
					// for (int i = 0; i < SoCT; i++) {
					// coupon.setCode(txtMaPhieu.getText() + i);
					// PromotionModelManager.getInstance().saveCoupon(coupon);
					// }
					// else
					PromotionModelManager.getInstance().saveCoupon(coupon);
					reset();
				} else
					return;
			}
			if(reset == true)
				((DialogMain) getRootPane().getParent()).dispose();
		}
	}

	// Phương thức cho nút [Sửa]
	@Override
	public void edit() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {
			reset = true;
		setEnableCompoment(true);
		txtMaPhieu.setEnabled(false);
		txtSoCT.setEnabled(false);
		isNew = false;
		}
	}

	// Phương thức cho nút [Xóa]
	@Override
	public void delete() throws Exception {

		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {

			String str = "Xóa Phiếu giảm giá ";
			PanelChoise pnPanel = new PanelChoise(str + coupon + "?");
			pnPanel.setName("Xoa");
			DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
			dialog1.setName("dlXoa");
			dialog1.setTitle("Xóa phiếu giảm giá");
			dialog1.setLocationRelativeTo(null);
			dialog1.setModal(true);
			dialog1.setVisible(true);

			if (pnPanel.isDelete()) {
				PromotionModelManager.getInstance().deleteCoupon(coupon);
				reset();
				((DialogMain) getRootPane().getParent()).dispose();
			} else if (pnPanel.isDelete() == false) {
				reset();
			}
			
			} else {
			JOptionPane.showMessageDialog(null,
					"Bạn chưa được phân quyền này !");
		}

	}

	// Phương thức cho nút [Viết lại]
	@Override
	public void reset() throws Exception {
		txtTenPhieu.setText("");
		txtMaPhieu.setText(sdf.format(new Date()));
		txtTuNgay.setDate(new Date());
		txtDenNgay.setDate(null);
		txtGiaTri.setText("");
		txtSoLanDung.setText("");
		txtMieuTa.setText("");
		txtSoCT.setText("1");
		lbThongBao.setText("");

		try {
			cbDonViTien.setSelectedIndex(0);
		} catch (Exception ex) {
		}

		lbTenPhieu.setForeground(new Color(51, 51, 51));
		lbTuNgay.setForeground(new Color(51, 51, 51));
		lbMaPhieu.setForeground(new Color(51, 51, 51));
		lbGiaTri.setForeground(new Color(51, 51, 51));
		lbSoLanDung.setForeground(new Color(51, 51, 51));
		lbSoCT.setForeground(new Color(51, 51, 51));

		setEnableCompoment(true);
		coupon = new Coupon();
		isNew = true;
	}

	// Phương thức cho nút [Xem lại]
	@Override
	public void refresh() throws Exception {
		setData(coupon);
	}

	// Phương thức cho sự kiện RadioButton
	class RadioListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JRadioButton rd = (JRadioButton) e.getSource();
			if (rd.getText().equals("%"))
				cbDonViTien.setEnabled(false);
			else if (rd.getText().equals("Tiền mặt"))
				cbDonViTien.setEnabled(true);
			else
				cbDonViTien.setEnabled(false);
		}
	}

	// Tạo dữ liệu mẫu liên quan
	@Override
	public void createBeginTest() {
		if (isTest) {
			// Tạo đơn vị tiền tệ
			Currency currency = new Currency();
			currency.setCode("Mã TT HktTest1");
			currency.setName("TT HktTest1");
			try {
				LocaleModelManager.getInstance().saveCurrency(currency);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Tạo dữ liệu sinh tự động test chuyển trang
	@Override
	public void createDataTest() {
		for (int i = 4; i <= 50; i++) {
			Coupon co = new Coupon();
			co.setName("Tên khuyến mại HktTest" + i);
			co.setCode("Mã khuyến mại HktTest" + i);
			co.setFromDate(new Date());
			co.setToDate(new Date());
			co.setDescription("Miêu tả khuyến mại HktTest" + i);
			co.setDiscount(1000 * i);
			co.setDiscountRate(0);
			co.setMaxUsePerCustomer(i);
			try {
				PromotionModelManager.getInstance().saveCoupon(co);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Xóa tất cả dữ liệu mẫu tự sinh
	@Override
	public void deleteDataTest() {
		if (!PanelTestAll.runAll) {
			DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
			if (DialogTest.getInstance().isTest()) {
				try {
					LocaleModelManager.getInstance().deleteTest("HktTest");
					PromotionModelManager.getInstance().deleteTest("HktTest");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
