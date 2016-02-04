package com.hkt.client.swingexp.app.khuvucbanhang;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import com.hkt.client.swingexp.app.banhang.screen.often.ScreenOften;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextArea;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.AreaJComboBox;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.hethong.PanelRecybin;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.khohang.PanelPatternProduction;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.util.text.DateUtil;

@SuppressWarnings("serial")
public class PanelLocalTable extends MyPanel implements IDialogMain {

	private ExtendJLabel lbTableName, lbGroup, lbCode, lbArea, lbDescription,
			lbMessenger;
	private ExtendJTextField txtTableName, txtCode;
	private AreaJComboBox cbArea;
	private ExtendJComboBox cbGroup;
	private ExtendJTextArea txtDescription;
	private JScrollPane scrollPaneArea;
	private List<AccountGroup> responsibleGroups;
	private Table table;
	private DateFormat dfCode = new SimpleDateFormat("yyyyMMddHHmmss");
	private boolean isNew = true;
	public static String permission;
	private boolean reset = false;

	// Hàm tạo đối tượng PanelLocalTable
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PanelLocalTable() {
		table = new Table();
		init();
		try {
			responsibleGroups = AccountModelManager.getInstance()
					.getChildrensByPath(
							ManagerAuthenticate.getInstance().getLoginId());
			cbGroup.setModel(new DefaultComboBoxModel(responsibleGroups
					.toArray()));
		} catch (Exception ex) {
		}
	}

	// Khởi tạo và sắp xếp vị trí các components trên giao diện
	public void init() {
		createBeginTest();
		lbTableName = new ExtendJLabel("Tên bàn");
		lbGroup = new ExtendJLabel("Nhóm QL");
		lbCode = new ExtendJLabel("Mã bàn");
		lbArea = new ExtendJLabel("Khu vực");
		lbDescription = new ExtendJLabel("Miêu tả");
		lbMessenger = new ExtendJLabel("");
		lbMessenger.setForeground(Color.red);

		cbGroup = new ExtendJComboBox();
		cbArea = new AreaJComboBox();
		cbArea.removeIndex(0);

		txtTableName = new ExtendJTextField();
		txtCode = new ExtendJTextField();
		txtDescription = new ExtendJTextArea();
		scrollPaneArea = new JScrollPane();
		scrollPaneArea.setViewportView(txtDescription);

		txtTableName.setName("txtTenBan");
		txtCode.setName("txtMaBan");
		txtCode.setText(DateUtil.asCompactDateTimeId(new Date()));
		txtDescription.setName("txtMieuTa");
		cbArea.setName("cbKhuVuc");
		cbGroup.setName("cbNhomQuanLy");

		UIManager.put("ComboBox.background", Color.WHITE);
		UIManager.put("ComboBox.disabledBackground", Color.WHITE);
		UIManager.put("ComboBox.disabledForeground", new Color(51, 51, 51));
		UIManager.put("TextField.background", Color.WHITE);
		UIManager.put("TextField.inactiveBackground", Color.WHITE);
		UIManager.put("TextField.inactiveForeground", new Color(51, 51, 51));

		MyGroupLayout layout = new MyGroupLayout(this);
		layout.add(0, 0, lbCode);
		layout.add(0, 1, txtCode);
		layout.add(0, 2, lbTableName);
		layout.add(0, 3, txtTableName);
		layout.add(1, 0, lbGroup);
		layout.add(1, 1, cbGroup);
		layout.add(1, 2, lbArea);
		layout.add(1, 3, cbArea);
		layout.add(2, 0, lbDescription);
		layout.add(2, 1, scrollPaneArea);
		layout.addMessenger(lbMessenger);
		layout.updateGui();
	}

	// Thiết lập có khả năng edit components hay không?
	private void setEnableCompoment(boolean value) {
		txtTableName.setEnabled(value);
		txtDescription.setEnabled(value);
		cbGroup.setEnabled(value);
		cbArea.setEnabled(value);
	}

	// Kiểm tra dữ liệu trước khi lưu
	private boolean checkData() {

		boolean check = true;
		boolean codeError = false;
		if (txtCode.getText().equals("")) {
			lbCode.setForeground(Color.red);
			check = false;
		} else {
			lbCode.setForeground(new Color(51, 51, 51));
			if (isNew) {
				try {
					Table tb = RestaurantModelManager.getInstance()
							.getTableByCode(txtCode.getText());
					if (tb != null) {
						check = false;
						codeError = true;
						lbCode.setForeground(Color.red);
						if (tb.isRecycleBin()) {
							PanelRecybin panel = new PanelRecybin(
									"Mã đã bị xóa trước đó!",
									" Chọn ĐỒNG Ý để lấy lại thông tin ban đầu hoặc THOÁT và nhập mã khác!");
							panel.setName("Xoa");
							DialogResto dialog = new DialogResto(panel, false,
									0, 120);
							dialog.setName("dlXoa");
							dialog.setTitle("Thêm mới bàn/quầy");
							dialog.setLocationRelativeTo(null);
							dialog.setModal(true);
							dialog.setVisible(true);
							if (panel.isDelete()) {
								tb.setRecycleBin(false);
								RestaurantModelManager.getInstance().saveTable(tb);
								lbCode.setForeground(Color.black);
								return true;
							}
						}
					}
				} catch (Exception e) {
					return false;
				}
			}
		}
		
			try {
				Location location = cbArea.getSelectedLocation();
				lbArea.setForeground(new Color(51, 51, 51));
				if (location == null) {
					check = false;
					lbArea.setForeground(Color.RED);
				}
			} catch (Exception ex) {
				check = false;
				lbArea.setForeground(Color.RED);
			}

			if (txtTableName.getText().equals("")) {
				check = false;
				lbTableName.setForeground(Color.RED);
			} else
				lbTableName.setForeground(new Color(51, 51, 51));

			if (!check) {
				lbMessenger.setText("Kiểm tra lỗi báo đỏ !");
				if (codeError)
					lbMessenger.setText("Lỗi trùng mã bàn/quầy!");
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
	        JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
	        return;
		} else {
			if (checkData()) {
				getData();
				RestaurantModelManager.getInstance().saveTable(table);
				Thread t = new Thread(){
				  public void run() {
				    try {
				      ScreenOften.getInstance().getPanelTable().refeshGui();
            } catch (Exception e) {
              System.out.println("loi load Table");
            }
				   
				  };
				};
				t.start();
		    
				reset();
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
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {
			reset = true;
			setEnableCompoment(true);
			isNew = false;
		}
	}

	// Phương thức cho nút [Xóa]
	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {

			String str = "Xóa bàn ";
			PanelChoise pnPanel = new PanelChoise(str + table + "?");
			pnPanel.setName("Xoa");
			DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
			dialog1.setName("dlXoa");
			dialog1.setTitle("Xóa bàn quầy");
			dialog1.setLocationRelativeTo(null);
			dialog1.setModal(true);
			dialog1.setVisible(true);

			if (pnPanel.isDelete()) {
				RestaurantModelManager.getInstance().deleteTable(table);
				reset();
				((DialogMain) getRootPane().getParent()).dispose();
			} else if (pnPanel.isDelete() == false) {
				reset();
			}
			Thread t = new Thread(){
        public void run() {
          try {
            ScreenOften.getInstance().getPanelTable().refeshGui();
          } catch (Exception e) {
            System.out.println("loi load Table");
          }
         
        };
      };
      t.start();
		} else {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		}
	}

	// Phương thức cho nút [Viết lại]
	@Override
	public void reset() throws Exception {
		table = new Table();
		setEnableCompoment(true);
		txtCode.setText(DateUtil.asCompactDateTimeId(new Date()));
		txtTableName.setText("");
		txtDescription.setText("");
		lbMessenger.setText("");
		lbTableName.setForeground(new Color(51, 51, 51));
		lbArea.setForeground(new Color(51, 51, 51));
		try {
			cbGroup.setSelectedIndex(0);
		} catch (Exception e) {
		}
		try {
			cbArea.setSelectedIndex(0);
		} catch (Exception e) {
		}
		txtCode.setEnabled(true);
		setEnableCompoment(true);
		isNew = true;
	}

	// Phương thức cho nút [Xem lại]
	@Override
	public void refresh() throws Exception {
		if (table != null)
			setData(table);
	}

	// Dữ liệu từ giao diện cho vào đối tượng
	private void getData() {
		table.setName(txtTableName.getText());
		table.setCode(dfCode.format(new Date()));
		table.setOrganizationLoginId(ManagerAuthenticate.getInstance()
				.getLoginId());
		table.setLocationCode(cbArea.getSelectedLocation().getCode());
		table.setDescription(txtDescription.getText());
		table.setCode(txtCode.getText());
		String path = "";
		try {
			path = ((AccountGroup) cbGroup.getSelectedItem()).getPath();
		} catch (Exception ex) {
		}
		table.setResponsibleGroup(path);
	}

	// Lấy dữ liệu hiển thị ra giao diện
	public void setData(Table table) {
		this.table = table;
		try {
			txtTableName.setText(table.getName());
			txtCode.setText(table.getCode());
			for (int i = 0; i < responsibleGroups.size(); i++) {
				if (responsibleGroups.get(i).getPath()
						.equals(table.getResponsibleGroup())) {
					cbGroup.setSelectedIndex(i);
					break;
				}
			}

			cbArea.setSelectedLocation(table.getLocationCode());
			txtDescription.setText(table.getDescription());
			txtCode.setEnabled(false);
			setEnableCompoment(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Tạo dữ liệu mẫu liên quan
	@Override
	public void createBeginTest() {
		if (isTest) {
			for (int j = 1; j <= 3; j++) {
				// Tạo mới khu vực
				Location loca = new Location();
				loca.setOrganizationLoginId(ManagerAuthenticate.getInstance()
						.getOrganizationLoginId());
				loca.setName("KV HktTest" + j);
				loca.setCode("Mã KV HktTest" + j);
				loca.setDescription("Miêu tả KV HktTest" + j);
				try {
					RestaurantModelManager.getInstance().saveLocation(loca);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Tạo dữ liệu tự động test chuyển trang
	@SuppressWarnings("unused")
	@Override
	public void createDataTest() {
		for (int i = 0; i <= 50; i++) {
			Table ta = new Table();
			ta.setName("Tên bàn HktTest" + i);
			ta.setCode("Mã bàn HktTest" + i);
			ta.setOrganizationLoginId(ManagerAuthenticate.getInstance()
					.getLoginId());
			ta.setLocationCode("Mã KV HktTest1");
			ta.setDescription("Miêu tả HktTest" + i);
			try {
				Table table1 = RestaurantModelManager.getInstance().saveTable(
						ta);
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
