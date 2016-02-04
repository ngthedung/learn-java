package com.hkt.client.swingexp.app.banhang;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.hethong.PanelRecybin;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.khohang.ProductPriceTypeTable;

import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.util.text.DateUtil;

/** GIAO DIỆN ::: QUẢN LÝ BẢNG GIÁ */
@SuppressWarnings("serial")
public class PanelProductPriceType extends MyPanel implements IDialogMain {
	private ExtendJLabel lbType, lbName, lbDescription, lbMessenger;
	private ExtendJTextField txtType, txtName, txtDescription;
	private ProductPriceTypeTable priceTypeTable;
	private JScrollPane scrollPaneTable, scrollIndex;
	public static String permission;
	private ProductPriceType priceType = new ProductPriceType();
	private boolean restore = true;

	public PanelProductPriceType() {
		init();
		reset();
	}

	private void init() {
		lbMessenger = new ExtendJLabel("");
		lbMessenger.setForeground(Color.red);
		scrollPaneTable = new JScrollPane();
		scrollPaneTable.setOpaque(false);
		scrollPaneTable.getViewport().setOpaque(false);
		scrollPaneTable.setBorder(null);

		priceTypeTable = new ProductPriceTypeTable(getListData());
		priceTypeTable.setPreferredScrollableViewportSize(new Dimension(200, 290));
		scrollPaneTable.setViewportView(priceTypeTable);
		priceTypeTable.setName("tbpriceTypeTable");

		priceTypeTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				try {
					tableOptionsMouseClicked(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		lbType = new ExtendJLabel("Mã");
		lbName = new ExtendJLabel("Tên bảng giá");
		lbDescription = new ExtendJLabel("Miêu tả");

		txtType = new ExtendJTextField();
		txtType.setHorizontalAlignment(JTextField.RIGHT);
		txtType.setText(DateUtil.asCompactDateTimeId(new Date()));
		txtType.setName("txtType");

		txtName = new ExtendJTextField();
		txtName.setName("txtName");

		txtDescription = new ExtendJTextField();
		txtDescription.setName("txtDescription");

		MyGroupLayout layout = new MyGroupLayout(this);
		layout.add(0, 0, lbType);
		layout.add(0, 1, txtType);
		layout.add(0, 2, lbName);
		layout.add(0, 3, txtName);

		layout.add(1, 0, lbDescription);
		layout.add(1, 1, txtDescription);
		layout.addMessenger(lbMessenger);

		// Thêm button cập nhật index
		scrollIndex = new JScrollPane();
		scrollIndex.setOpaque(false);
		scrollIndex.getViewport().setOpaque(false);
		scrollIndex.setBorder(null);
		scrollIndex.setViewportView(priceTypeTable.getPanleButton());

		// Giao diện button cập nhật index
		layout.add(2, 0, scrollIndex);
		layout.add(2, 1, scrollPaneTable);
		layout.updateGui();
	}

	protected void tableOptionsMouseClicked(MouseEvent event) throws Exception {
		int click = 2;
		priceType = priceTypeTable.getSelectedBean();
		if (priceType.getType().indexOf("HktTest") > 0 && event.getButton() == 3) {
			click = 1;
		}
		if (event.getClickCount() >= click) {
			setData();
			setEnableCompoment(false);
			((DialogMain) getRootPane().getParent()).showButton(false);
		}
		refresh();
	}

	public List<ProductPriceType> getListData() {
		List<ProductPriceType> productPriceTypes = null;
		try {
			productPriceTypes = ProductModelManager.getInstance().getProductPriceTypes();
		} catch (Exception e) {
		}
		return productPriceTypes;
	}

	public void setProductPriceType(ProductPriceType object) throws Exception {
		this.priceType = object;
		if (object == null) {
			priceType = new ProductPriceType();
		}
		setData();
	}

	private ProductPriceType getData() {
		restore = true;
		if (priceType == null) {
			priceType = new ProductPriceType();
		}
		priceType.setType(txtType.getText());
		priceType.setName(txtName.getText());
		priceType.setDescription(txtDescription.getText());
		priceType.setPriority((getListData().size() + 1));
		return priceType;
	}

	private void setData() {
		try {
			txtType.setText(priceType.getType());
			txtName.setText(priceType.getName());
			txtDescription.setText(priceType.getDescription());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean checkData() {
		boolean check = true;

		/** Kiểm tra mã code */
		if (txtType.getText().isEmpty()) {
			lbType.setForeground(Color.red);
			check = false;
		} else {
			lbType.setForeground(Color.black);
		}

		if (txtName.getText().isEmpty()) {
			lbName.setForeground(Color.red);
			check = false;
		} else {
			lbName.setForeground(Color.black);
		}

		// Kiểm tra mã code đã tồn tại chưa?
		if (txtType.isEnabled()) {
			try {
				ProductPriceType productPriceType = ProductModelManager.getInstance().getProductPriceTypeByType(
				    txtType.getText());
				if (productPriceType != null) {
					check = false;
					lbType.setForeground(Color.red);
					if (productPriceType.isRecycleBin()) {

						PanelRecybin panel = new PanelRecybin("Mã đã bị xóa trước đó!",
						    " Chọn ĐỒNG Ý để lấy lại thông tin ban đầu hoặc THOÁT và nhập mã khác!");
						panel.setName("Xoa");
						DialogResto dialog = new DialogResto(panel, false, 0, 120);
						dialog.setName("dlXoa");
						dialog.setTitle("Quản lý bảng giá");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);
						if (panel.isDelete()) {
							restore = false;
							productPriceType.setRecycleBin(false);
							ProductModelManager.getInstance().saveProductPriceType(productPriceType);
							lbType.setForeground(Color.black);
							return true;
						}
					}
				}
			} catch (Exception e) {
			}

			/** Thông báo hiển thị lỗi */

		}
		if (!check) {
			lbMessenger.setText("Kiểm tra lỗi báo đỏ!");
			lbMessenger.setForeground(Color.red);
			lbMessenger.setVisible(true);
			return false;
		} else {
			lbMessenger.setText("");
			return true;
		}
	}

	private void setEnableCompoment(boolean value) {
		txtType.setEnabled(value);
		txtName.setEnabled(value);
		txtDescription.setEnabled(value);
	}

	@Override
	public void save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {
			// Kiểm tra các trường dữ liệu hợp lệ?
			if (checkData()) {
				if (restore) {
					getData();
				}

				if (priceType != null) {
					ProductModelManager.getInstance().saveProductPriceType(priceType);

				}
				reset();
			}
		}

	}

	@Override
	public void edit() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {
			setEnableCompoment(true);
			txtType.setEnabled(false);
		}
	}

	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {
			String str = "Xóa bảng giá ";
			PanelChoise pnPanel = new PanelChoise(str + txtName.getText() + "?");
			pnPanel.setName("Xoa");
			DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
			dialog1.setName("dlXoa");
			dialog1.setTitle("Xóa bảng giá");
			dialog1.setLocationRelativeTo(null);
			dialog1.setModal(true);
			dialog1.setVisible(true);

			if (pnPanel.isDelete()) {
				ProductModelManager.getInstance().deleteProductPriceType(priceType);
				lbMessenger.setText("");
				reset();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		}
	}

	@Override
	public void reset() {
		txtName.setText("");
		txtDescription.setText("");
		txtType.setText(DateUtil.asCompactDateTimeId(new Date()));
		lbType.setForeground(Color.black);
		lbName.setForeground(Color.black);
		lbMessenger.setText(" ");
		priceType = new ProductPriceType();
		loadTable();
		setEnableCompoment(true);
	}

	@Override
	public void refresh() throws Exception {
		setData();
		setEnableCompoment(false);
		lbType.setForeground(Color.black);
		lbName.setForeground(Color.black);
		lbMessenger.setText(" ");
	}

	private void loadTable() {
		priceTypeTable.setProductPriceTypes(getListData());
	}

	@Override
	public void deleteDataTest() {
		if (!PanelTestAll.runAll) {
			DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
			if (DialogTest.getInstance().isTest()) {
				try {
					ProductModelManager.getInstance().deleteTest("HktTest");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
