package com.hkt.client.swingexp.app.khachhang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.core.UnitMoneyJComboBox;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.partner.customer.entity.PointConversionRule;

@SuppressWarnings("serial")
public class PanelListPartnerPoint extends MyPanel implements IDialogMain {

	private ExtendJLabel lblTenTyLe, lblDoanhThuTuKH, lblDiemTuongUng, lblTienKMTuongUng, lblDiemToiThieu, lblTuNgay;
	private ExtendJLabel lblDenNgay, lblMessage;
	private ExtendJTextField txtTenTyLe, txtDoanhThuKH, txtDiemTuongUng, txtTienKMTuongUng, txtDiemToiThieu;
	private MyJDateChooser txtTuNgay, txtDenNgay;
	private UnitMoneyJComboBox cbMoneyUnit1, cbMoneyUnit2;
	private JScrollPane scrollPaneTable;
	private TableListPartnerPoint tableList;
	private PointConversionRule point = new PointConversionRule();
	public static String permission;
	
	public PanelListPartnerPoint() throws Exception {
		init();
		setOpaque(false);
		reset();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void init() throws Exception {
		 
		lblMessage = new ExtendJLabel("");
		scrollPaneTable = new JScrollPane();
		scrollPaneTable.setOpaque(false);
		scrollPaneTable.getViewport().setOpaque(false);
		scrollPaneTable.setBorder(null);

		tableList = new TableListPartnerPoint();
		tableList.setName("tbtableList");
		tableList.setListPartnerPoint(getObjects());

		tableList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				try {
					tableOptionsMouseClicked(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		tableList.setPreferredScrollableViewportSize(new Dimension(100, 290));
		scrollPaneTable.setViewportView(tableList);

		lblTenTyLe = new ExtendJLabel("Tỷ lệ");
		lblDoanhThuTuKH = new ExtendJLabel("Doanh thu");
		lblDiemTuongUng = new ExtendJLabel("Thành điểm");
		lblTienKMTuongUng = new ExtendJLabel("Tiền KM");
		lblDiemToiThieu = new ExtendJLabel("Điểm tối thiểu");
		lblTuNgay = new ExtendJLabel("Từ ngày");
		lblDenNgay = new ExtendJLabel("Đến ngày");

		txtTenTyLe = new ExtendJTextField();
		txtTenTyLe.setName("txtTenTyLe");

		txtDoanhThuKH = new ExtendJTextField();
		txtDoanhThuKH.setName("txtDoanhThuKH");
		txtDoanhThuKH.setHorizontalAlignment(JTextField.RIGHT);

		txtDiemTuongUng = new ExtendJTextField();
		txtDiemTuongUng.setName("txtDiemTuongUng");
		txtDiemTuongUng.setHorizontalAlignment(JTextField.RIGHT);

		txtDiemToiThieu = new ExtendJTextField();
		txtDiemToiThieu.setName("txtDiemToiThieu");
		txtDiemToiThieu.setHorizontalAlignment(JTextField.RIGHT);

		txtTienKMTuongUng = new ExtendJTextField();
		txtTienKMTuongUng.setName("txtTienKMTuongUng");
		txtTienKMTuongUng.setHorizontalAlignment(JTextField.RIGHT);

		txtTuNgay = new MyJDateChooser();
		txtTuNgay.setName("txtTuNgay");

		txtDenNgay = new MyJDateChooser();
		txtDenNgay.setName("txtDenNgay");

		cbMoneyUnit1 = new UnitMoneyJComboBox();
		cbMoneyUnit1.setName("cbMoneyUnit1");
		cbMoneyUnit1.setPreferredSize(new Dimension(55, 23));
		
		try {
			cbMoneyUnit1.setModel(new DefaultComboBoxModel(LocaleModelManager.getInstance().getCurrencies().toArray()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		cbMoneyUnit2 = new UnitMoneyJComboBox();
		cbMoneyUnit2.setName("cbMoneyUnit2");
		cbMoneyUnit2.setPreferredSize(new Dimension(55, 23));

		try {
			cbMoneyUnit2.setModel(new DefaultComboBoxModel(LocaleModelManager.getInstance().getCurrencies().toArray()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PanelContainer panelDoanhThu = new PanelContainer(txtDoanhThuKH, cbMoneyUnit1);
		PanelContainer panelTienKM = new PanelContainer(txtTienKMTuongUng, cbMoneyUnit2);

		MyGroupLayout layout = new MyGroupLayout(this);

		layout.add(0, 0, lblTenTyLe);
		layout.add(0, 1, txtTenTyLe);

		layout.add(1, 0, lblDoanhThuTuKH);
		layout.add(1, 1, panelDoanhThu);

		layout.add(1, 2, lblDiemTuongUng);
		layout.add(1, 3, txtDiemTuongUng);

		layout.add(2, 0, lblTienKMTuongUng);
		layout.add(2, 1, panelTienKM);

		layout.add(2, 2, lblDiemToiThieu);
		layout.add(2, 3, txtDiemToiThieu);

		layout.add(3, 0, lblTuNgay);
		layout.add(3, 1, txtTuNgay);
		layout.add(3, 2, lblDenNgay);
		layout.add(3, 3, txtDenNgay);

		// // Thêm button cập nhật index
		// scrollIndex = new JScrollPane();
		// scrollIndex.setOpaque(false);
		// scrollIndex.getViewport().setOpaque(false);
		// scrollIndex.setBorder(null);
		// scrollIndex.setViewportView(tableList.getPanleButton());

		// Giao diện Button cập nhật index đi cùng với scrollPaneTable
		// layout.add(4, 0, scrollIndex);
		layout.add(4, 1, scrollPaneTable);
		layout.addMessenger(lblMessage);
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

	public List<PointConversionRule> getObjects() throws Exception {
		return CustomerModelManager.getInstance().getPointConversionRules();
	}

	private PointConversionRule getData() {
		try {
			if (point == null) {
				point = new PointConversionRule();
			}
			
			txtTenTyLe.setText(point.getName());
			txtDoanhThuKH.setText(Double.toString(point.getTotal()));
			txtDiemTuongUng.setText(Double.toString(point.getPointToCreditRatio()));
			txtDiemToiThieu.setText(Double.toString(point.getMinPointToTrigger()));
			txtTienKMTuongUng.setText(Double.toString(point.getCreditToPointRatio()));
			txtTuNgay.setDate(point.getDateFrom());
			txtDenNgay.setDate(point.getDateTo());
			lblMessage.setText(" ");
			return point;
		} catch (Exception e) {
			lblMessage.setText("Lỗi định dạng dữ liệu");
			lblMessage.setForeground(Color.red);
			lblMessage.setVisible(true);
			return new PointConversionRule();
		}
	}

	private void setData(PointConversionRule _pointConversionRule) {
		this.point = _pointConversionRule;
		
		point.setName(txtTenTyLe.getText());
		point.setDateFrom(txtTuNgay.getDate());
		point.setDateTo(txtDenNgay.getDate());
		point.setTotal(MyDouble.parseDouble(txtDoanhThuKH.getText()));
		point.setMinPointToTrigger(MyDouble.parseDouble(txtDiemToiThieu.getText()));
		point.setPointToCreditRatio(MyDouble.parseDouble(txtDiemTuongUng.getText()));
		point.setCreditToPointRatio(MyDouble.parseDouble(txtTienKMTuongUng.getText()));
	}

	private boolean checkData() {
		int countError = 0;
		if (!txtTenTyLe.getText().equals("") && txtTenTyLe.getText() != null) {
			countError++;
			lblTenTyLe.setForeground(Color.black);
		} else {
			lblTenTyLe.setForeground(Color.RED);
		}
		if (!txtDiemToiThieu.getText().equals("") && !txtTienKMTuongUng.getText().equals("")
		    && !txtDiemTuongUng.getText().equals("") && !txtDoanhThuKH.getText().equals("")) {
			try {
				Double.parseDouble(txtDiemToiThieu.getText());
				Double.parseDouble(txtTienKMTuongUng.getText());
				Double.parseDouble(txtDiemTuongUng.getText());
				Double.parseDouble(txtDoanhThuKH.getText());
				countError++;
			} catch (Exception ex) {
				ex.printStackTrace();
				lblDoanhThuTuKH.setForeground(Color.RED);
				lblDiemTuongUng.setForeground(Color.RED);
				lblTienKMTuongUng.setForeground(Color.RED);
				lblDiemToiThieu.setForeground(Color.RED);
			}
		} else {
			lblDoanhThuTuKH.setForeground(Color.RED);
			lblDiemTuongUng.setForeground(Color.RED);
			lblTienKMTuongUng.setForeground(Color.RED);
			lblDiemToiThieu.setForeground(Color.RED);
		}

		if (countError == 2) {
			lblMessage.setText("");
			lblTenTyLe.setForeground(Color.black);
			lblDoanhThuTuKH.setForeground(Color.black);
			lblDiemTuongUng.setForeground(Color.black);
			lblTienKMTuongUng.setForeground(Color.black);
			lblDiemToiThieu.setForeground(Color.black);
			return true;
		} else {
			lblMessage.setText("Kiểm tra lại dòng báo đỏ");
			lblMessage.setForeground(Color.red);
			return false;
		}
	}

	protected void tableOptionsMouseClicked(MouseEvent event) {
		try {
			point = tableList.getSelectedBean();
			int click = 2;
			if (point.getName().indexOf("HktTest") > 0 && event.getButton() == 3) {
				click = 1;
			}
			if (event.getClickCount() >= click) {
				getData();
				setEnabledCompoment(false);
				((DialogMain) getRootPane().getParent()).showButton(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setObject(PointConversionRule object) throws Exception {
		this.point = object;
		if (object == null) {
			point = new PointConversionRule();
		}
		getData();
	}

	private void setEnabledCompoment(boolean value) {
		txtTenTyLe.setEnabled(value);
		txtDoanhThuKH.setEnabled(value);
		txtDiemTuongUng.setEnabled(value);
		txtDiemToiThieu.setEnabled(value);
		txtDenNgay.setEnabled(value);
		txtTuNgay.setEnabled(value);
		txtTienKMTuongUng.setEnabled(value);
		cbMoneyUnit1.setEnabled(value);
		cbMoneyUnit2.setEnabled(value);
	}

	private void loadTable() throws Exception {
		tableList.setListPartnerPoint(getObjects());
	}

	@Override
	public void save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return;
		} else {
			if (checkData()) {
				setData(point);
				CustomerModelManager.getInstance().savePointConversionRatio(point);
				reset();
			}
		}
	}

	@Override
	public void edit() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return;
		} else {
			setEnabledCompoment(true);
		}
	}

	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {
			
			String str = "Xóa cơ chế điểm ";
			PanelChoise pnPanel = new PanelChoise(str + point + "?");
			pnPanel.setName("Xoa");
			DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
			dialog1.setName("dlXoa");
			dialog1.setTitle("Xóa cơ chế điểm");
			dialog1.setLocationRelativeTo(null);
			dialog1.setModal(true);
			dialog1.setVisible(true);
			
			if (pnPanel.isDelete()) {
				CustomerModelManager.getInstance().deletePointConversionRule(point);
				reset();
	        }else if (pnPanel.isDelete()==false) {
		      reset();
	        }
		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return;
		}
	}

	@Override
	public void reset() throws Exception {
		txtTenTyLe.setText("");
		txtDoanhThuKH.setText("");
		txtDiemTuongUng.setText("");
		txtDiemToiThieu.setText("");
		txtTuNgay.setDate(new Date());
		txtTienKMTuongUng.setText("");
		txtDenNgay.setDate(null);
		lblMessage.setText("");
		lblTenTyLe.setForeground(Color.black);
		lblDoanhThuTuKH.setForeground(Color.black);
		lblDiemTuongUng.setForeground(Color.black);
		lblTienKMTuongUng.setForeground(Color.black);
		lblDiemToiThieu.setForeground(Color.black);
		lblTuNgay.setForeground(Color.black);
		lblDenNgay.setForeground(Color.black);
		setEnabledCompoment(true);
		loadTable();
	}

	@Override
	public void refresh() throws Exception {
		setData(point);
		setEnabledCompoment(false);
	}

	@Override
	public void deleteDataTest() {
		if (!PanelTestAll.runAll) {
			DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
			if (DialogTest.getInstance().isTest()) {
				try {
					CustomerModelManager.getInstance().deleteTest("HktTest");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
