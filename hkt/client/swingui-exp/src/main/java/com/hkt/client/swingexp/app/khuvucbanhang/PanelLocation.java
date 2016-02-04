package com.hkt.client.swingexp.app.khuvucbanhang;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import com.hkt.client.swingexp.app.banhang.screen.pos.PanelScreenSaleLocationPos;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextArea;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.hethong.PanelManageDelete;
import com.hkt.client.swingexp.app.hethong.PanelRecybin;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.hethong.list.AreaJComboBoxDelete;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.core.entity.AbstractPersistable;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Table;

@SuppressWarnings("serial")
public class PanelLocation extends MyPanel implements IDialogMain {
	public static String	permission;
	private ExtendJLabel	lbNameArea, lbCodeArea, lbDescription, lbMessenger;
	private DateFormat		ds	= new SimpleDateFormat("yyyyMMddHHmmss");
	private ExtendJTextField	txtNameArea, txtCodeArea, txtIndex;
	private ExtendJTextArea		txtDescription;
	private JScrollPane				scrollPaneArea;
	private Location					location;
	private boolean						isNew	= true;
	private boolean reset = false;

	// Hàm tạo class
	public PanelLocation() throws Exception {
		location = new Location();
		init();
	}

	// Khởi tạo các components và trình bày vị trí trên giao diện
	public void init() {
		lbNameArea = new ExtendJLabel("Khu vực");
		lbCodeArea = new ExtendJLabel("Mã");
		txtIndex= new ExtendJTextField(String.valueOf(RestaurantModelManager.getInstance().getLocations().size()+1));

		lbDescription = new ExtendJLabel("Miêu tả");
		lbMessenger = new ExtendJLabel("");
		lbMessenger.setForeground(Color.RED);

		txtNameArea = new ExtendJTextField();
		txtCodeArea = new ExtendJTextField();

		txtDescription = new ExtendJTextArea();

		txtCodeArea.setText(ds.format(new Date()));

		scrollPaneArea = new JScrollPane();
		scrollPaneArea.setViewportView(txtDescription);

		txtNameArea.setName("txtKhuVuc");
		txtCodeArea.setName("txtMa");

		txtDescription.setName("txtMieuTa");

		MyGroupLayout layout = new MyGroupLayout(this);
		layout.add(0, 0, lbNameArea);
		layout.add(0, 1, txtNameArea);
		layout.add(0, 2, lbCodeArea);
		layout.add(0, 3, txtCodeArea);
		layout.add(2, 0, lbDescription);
		layout.add(2, 1, scrollPaneArea);
		layout.add(1, 0, new JLabel("Vị trí"));
		layout.add(1, 1, txtIndex);
		layout.addMessenger(lbMessenger);
		layout.updateGui();
	}

	// Lấy dữ liệu từ giao diện đổ vào đối tượng
	private void getData() {
		location.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
		location.setName(txtNameArea.getText());
		location.setCode(txtCodeArea.getText());
		location.setPriority(Integer.parseInt(txtIndex.getText()));
		// location.setPriority(0);
		location.setDescription(txtDescription.getText());
	}

	// Lấy dữ liệu hiển thị trên giao diện
	public void setData(Location prolocation) {
		this.location = prolocation;
		txtNameArea.setText(prolocation.getName());
		txtCodeArea.setText(prolocation.getCode());
		txtDescription.setText(prolocation.getDescription());
		txtIndex.setText(String.valueOf(location.getPriority()));
		setEnableCompoment(false);
		txtCodeArea.setEnabled(false);
	}

	// Thiết lập thuộc tính cho chỉnh sửa hay không?
	private void setEnableCompoment(boolean value) {
		txtNameArea.setEnabled(value);
		txtDescription.setEnabled(value);
		txtIndex.setEnabled(value);
	}

	// Kiểm tra dữ liệu trước khi lưu
	public boolean checkData() {
		boolean check = true;
		boolean codeError = false;
		// Kiểm tra trường Mã khác rỗng
		if (txtCodeArea.getText().toString().trim().equals("")) {
			lbCodeArea.setForeground(Color.red);
			check = false;
		} else {
			lbCodeArea.setForeground(new Color(51, 51, 51));

			// Kiểm tra ấn nút edit thì mới kiểm tra
			if (isNew) {
				// Kiểm tra trùng mã hay không?
				try {
					Location loca = RestaurantModelManager.getInstance().getLocationByCode(txtCodeArea.getText());

					if (loca != null) {
						check = false;
						codeError = true;
						lbCodeArea.setForeground(Color.red);
						if (loca.isRecycleBin()) {
							System.out.println(loca + "local");
							PanelRecybin panel = new PanelRecybin("Mã đã bị xóa trước đó!", " Chọn ĐỒNG Ý để lấy lại thông tin ban đầu hoặc THOÁT và nhập mã khác!");
							panel.setName("Xoa");
							DialogResto dialog = new DialogResto(panel, false, 0, 120);
							dialog.setName("dlXoa");
							dialog.setTitle("Khu vực mới");
							dialog.setLocationRelativeTo(null);
							dialog.setModal(true);
							dialog.setVisible(true);
							if (panel.isDelete()) {
								loca.setRecycleBin(false);
								RestaurantModelManager.getInstance().saveLocation(loca);
								return true;
							}

						}
					}
				} catch (Exception e) {
					return false;
				}
			}
		}

		// Kiểm tra trường Tên khác rỗng
		if (txtNameArea.getText().equals("")) {
			check = false;
			lbNameArea.setForeground(Color.red);
		} else
			lbNameArea.setForeground(new Color(51, 51, 51));

		// Hiển thị thông báo nếu có lỗi
		if (!check) {
			lbMessenger.setText("Kiểm tra lỗi báo đỏ !");
			if (codeError)
				lbMessenger.setText("Lỗi trùng mã khu vực!");
			return false;
		} else {
			lbMessenger.setText("");
			return true;
		}
	}

	// Phương thức cho nút [Lưu]
	@Override
	public void save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {
			if (checkData()) {
				getData();
				location = RestaurantModelManager.getInstance().saveLocation(location);
				if(location != null){
					reset();
				}				
			} else {
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
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {
			reset = true;
			setEnableCompoment(true);
			txtCodeArea.setEnabled(false);
			isNew = false;
		}
	}

	// Phương thức cho nút [Xóa]
	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {
			try {
				String str = "Xóa khu vực ";
				PanelChoise panel = new PanelChoise(str + location + " ?");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				dialog.setTitle("Xóa khu vực");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					showLocation();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
	}

	public void showLocation() throws Exception {
		if (RestaurantModelManager.getInstance().findTableByLocation(txtCodeArea.getText()) == null || RestaurantModelManager.getInstance().findTableByLocation(txtCodeArea.getText()).isEmpty()) {
			RestaurantModelManager.getInstance().deleteLocation(location);
			((JDialog) getRootPane().getParent()).dispose();
		} else {
			try {
				PanelManageDelete<Table> panel = new PanelManageDelete<Table>(txtNameArea.getText(), RestaurantModelManager.getInstance().findTableByLocation(txtCodeArea.getText()), new AreaJComboBoxDelete(
						location));
				panel.setName("XoaKhuVuc");
				DialogResto dialog = new DialogResto(panel, true, 600, 100);
				dialog.setName("dlXoaKhuVuc");
				dialog.dispose();
				dialog.setUndecorated(true);
				dialog.setTitle("Quản lý xóa đối tượng");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
			} catch (Exception e2) {
				e2.printStackTrace();
			}

			if (RestaurantModelManager.getInstance().findTableByLocation(txtCodeArea.getText()) == null || RestaurantModelManager.getInstance().findTableByLocation(txtCodeArea.getText()).isEmpty()) {
				RestaurantModelManager.getInstance().deleteLocation(location);
				((JDialog) getRootPane().getParent()).dispose();
			}
		}
	}

	// Phương thức cho nút [Viết lại]
	@Override
	public void reset() throws Exception {
		location = new Location();
		txtNameArea.setText("");
		txtCodeArea.setText(ds.format(new Date()));
		txtCodeArea.setEnabled(true);
		txtDescription.setText("");
		lbMessenger.setText(" ");
		lbNameArea.setForeground(new Color(51, 51, 51));
		lbCodeArea.setForeground(new Color(51, 51, 51));
		lbDescription.setForeground(new Color(51, 51, 51));
		txtIndex.setText(String.valueOf(RestaurantModelManager.getInstance().getLocations().size()+1));
		setEnableCompoment(true);
		isNew = true;
	}

	// Phương thức cho nút [Làm mới]
	@Override
	public void refresh() throws Exception {
		setData(location);
	}

	// Tạo dữ liệu mẫu sinh tự động test chuyển trang
	@Override
	public void createDataTest() {
		for (int i = 4; i <= 50; i++) {
			Location loca = new Location();
			loca.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
			loca.setName("Tên KV HktTest" + i);
			loca.setCode("Mã KV HktTest" + i);
			loca.setDescription("Miêu tả KV HktTest" + i);
			Table ta = new Table();
			ta.setName("Tên bàn HktTest" + i);
			ta.setCode("Mã bàn HktTest" + i);
			ta.setOrganizationLoginId(ManagerAuthenticate.getInstance().getLoginId());
			ta.setLocationCode("Mã KV HktTest3");
			ta.setDescription("Miêu tả HktTest" + i);
			try {
				RestaurantModelManager.getInstance().saveLocation(loca);
				Table table1 = RestaurantModelManager.getInstance().saveTable(ta);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Xóa tất cả dữ liệu mẫu tự động sinh
	@Override
	public void deleteDataTest() {
		if (!PanelTestAll.runAll) {
			DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
			if (DialogTest.getInstance().isTest()) {
				try {
					RestaurantModelManager.getInstance().deleteTest("HktTest");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
