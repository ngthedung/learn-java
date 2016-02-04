package com.hkt.client.swingexp.app.khohang;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.hkt.client.swingexp.app.banhang.screen.often.ScreenOften;
import com.hkt.client.swingexp.app.banhang.screen.pos.DialogScreenOftenPos;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.core.ProductTagJComboBox;
import com.hkt.client.swingexp.app.hethong.PanelManageDelete;
import com.hkt.client.swingexp.app.hethong.PanelRecybin;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.hethong.list.GroupProductJComboBoxDelete;
import com.hkt.client.swingexp.app.hethong.list.ProductTagJComboBoxDelete;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceItemAttribute;
import com.hkt.module.core.entity.AbstractPersistable;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductAttribute;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.util.text.DateUtil;

public class CatalogProductTag extends MyPanel implements IDialogMain {

	private static final long serialVersionUID = 1L;

	public static String permission;
	private JLabel lbCompany, lbParentGroup, lbNameGroup, lbCodeGroup, lbDescription, lbMessenger;
	private JTextField txtCompany, txtNameGroup, txtCodeGroup, txtDescription;
	private ProductTagJComboBox cboParentGroup;
	private PanelTableProducTag pnTable;
	private ProductTag productTag = new ProductTag();
	private JScrollPane scrollPaneTable, scrollIndex;
	@SuppressWarnings("unused")
	private int index = 0;
	private String nameProduct;
	private boolean edit = false;
	private List<ProductTag> listProductTag;
	private List<Product> listProduct;
	private String parentTag = null;
	private boolean restore = true;

	public CatalogProductTag() throws Exception {
		init();
		setOpaque(false);
		reset();
	}

	private void init() throws Exception {

		lbMessenger = new JLabel("");
		scrollPaneTable = new JScrollPane();
		scrollPaneTable.setOpaque(false);
		scrollPaneTable.getViewport().setOpaque(false);
		scrollPaneTable.setBorder(null);

		pnTable = new PanelTableProducTag();
		pnTable.setName("tbpnTable");

		pnTable.setData(getProductTags());
		pnTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				try {
					tableOptionsMouseClicked(event);
				} catch (Exception e) {
				}
			}
		});

		pnTable.setPreferredScrollableViewportSize(new Dimension(200, 290));
		scrollPaneTable.setViewportView(pnTable);

		lbCompany = new JLabel("Công ty");
		lbParentGroup = new JLabel("Nhóm cha");
		lbNameGroup = new JLabel("Tên nhóm");
		lbCodeGroup = new JLabel("Mã nhóm");
		lbDescription = new JLabel("Miêu tả");

		txtCompany = new JTextField();
		txtCompany.setName("txtCongTy");
		txtCompany.setText("Công ty HKT ");
		txtCompany.setEnabled(false);
		txtCompany.setDisabledTextColor(Color.black);

		cboParentGroup = new ProductTagJComboBox();
		cboParentGroup.setName("cboNhomCha");
		cboParentGroup.setBackground(Color.white);

		// chọn giá trị trong ComboBox
		cboParentGroup.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				try {
					if (cboParentGroup.getSelectedIndex() == -1) {
						parentTag = null;
					} else {
						parentTag = cboParentGroup.getSelectedProductTag().getTag();
					}
				} catch (Exception ex) {
					// ex.printStackTrace();
				}
			}
		});

		// Set màu khi disable comboBox
		UIManager.put("ComboBox.disabledBackground", Color.WHITE);
		UIManager.put("ComboBox.disabledForeground", new Color(51, 51, 51));

		txtNameGroup = new JTextField();
		txtNameGroup.setName("txtTenNhom");
		txtNameGroup.setDisabledTextColor(Color.black);

		txtCodeGroup = new JTextField();
		txtCodeGroup.setName("txtMaNhom");
		txtCodeGroup.setDisabledTextColor(Color.black);

		txtDescription = new JTextField();
		txtDescription.setName("txtMieuTa");
		txtDescription.setDisabledTextColor(Color.black);

		MyGroupLayout layout = new MyGroupLayout(this);

		layout.add(0, 0, lbCompany);
		layout.add(0, 1, txtCompany);
		layout.add(0, 2, lbCodeGroup);
		layout.add(0, 3, txtCodeGroup);

		layout.add(1, 0, lbParentGroup);
		layout.add(1, 1, cboParentGroup);
		layout.add(1, 2, lbNameGroup);
		layout.add(1, 3, txtNameGroup);

		layout.add(2, 0, lbDescription);
		layout.add(2, 1, txtDescription);
		layout.addMessenger(lbMessenger);

		// Thêm button cập nhật index
		scrollIndex = new JScrollPane();
		scrollIndex.setOpaque(false);
		scrollIndex.getViewport().setOpaque(false);
		scrollIndex.setBorder(null);
		scrollIndex.setViewportView(pnTable.getPanleButton());

		// Giao diện Button cập nhật index đi cùng với scrollPaneTable
		layout.add(3, 0, scrollIndex);
		layout.add(3, 1, scrollPaneTable);
		layout.updateGui();

	}

	protected void tableOptionsMouseClicked(MouseEvent event) throws Exception {
		productTag = pnTable.getSelectedBean();

		int click = 2;
		if (productTag.getLabel().indexOf("HktTest") > 0 && event.getButton() == 3) {
			click = 1;
		} else {
			productTag = null;
		}
		if (event.getClickCount() >= click) {
			productTag = pnTable.getSelectedBean();
			setData();
			index = pnTable.getSelectedRow();
			setEnableCompoment(false);
			((DialogMain) getRootPane().getParent()).showButton(false);
		}
		refresh();
	}

	private ProductTag getData() {
		restore = true;
		try {
			if (productTag == null || productTag.getId() == null) {
				productTag = new ProductTag();
				productTag.setPriority((getProductTags().size() + 1));
			}
			productTag.setCode(txtCodeGroup.getText());
			productTag.setLabel(txtNameGroup.getText());
			productTag.setDescription(txtDescription.getText());
			String tag = txtCodeGroup.getText();
			if (cboParentGroup.getSelectedProductTag() != null) {
				tag = ((ProductTag) cboParentGroup.getSelectedProductTag()).getTag() + ":" + txtCodeGroup.getText();
			}
			System.out.println(parentTag);
			productTag.setParentTag(parentTag);
			productTag.setTag(tag);

			lbMessenger.setText(" ");

			return productTag;
		} catch (Exception e) {
			lbMessenger.setVisible(true);
			lbMessenger.setText("Lỗi định dạng dữ liệu");
			lbMessenger.setForeground(Color.red);
			e.printStackTrace();
			return new ProductTag();
		}
	}

	private void setData() {
		try {
			if (productTag != null) {
				txtNameGroup.setText(productTag.getLabel());
				txtCodeGroup.setText(productTag.getCode());
				txtDescription.setText(productTag.getDescription());
				cboParentGroup.setSelectedIndex(0);
				for (int i = 1; i < cboParentGroup.getItemCount(); i++) {
					try {
						if (cboParentGroup.getItemAt(i) != null)
							if (cboParentGroup.getItemAt(i).getTag().equals(productTag.getParentTag())) {
								cboParentGroup.setSelectedIndex(i);
								break;
							}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private boolean checkData() {
		boolean check = true;
		boolean codeError = false;
		if (productTag.getCode().isEmpty()) {
			lbCodeGroup.setForeground(Color.red);
			check = false;
		} else {
			lbCodeGroup.setForeground(Color.black);
		}
		if (productTag.getLabel().isEmpty()) {
			lbNameGroup.setForeground(Color.red);
			check = false;
		} else {
			lbNameGroup.setForeground(Color.black);
		}
		// Kiểm tra xem có bị trùng code hay không
		if (txtCodeGroup.isEnabled()) {
			try {
				ProductTag pr = ProductModelManager.getInstance().getProductTagByCode(txtCodeGroup.getText());
				if (pr != null) {
					check = false;
					lbCodeGroup.setForeground(Color.red);
					if (pr.isRecycleBin()) {
						PanelRecybin panel = new PanelRecybin("Mã đã bị xóa trước đó!",
						    " Chọn ĐỒNG Ý để lấy lại thông tin ban đầu hoặc THOÁT và nhập mã khác!");
						panel.setName("Xoa");
						DialogResto dialog = new DialogResto(panel, false, 0, 120);
						dialog.setName("dlXoa");
						dialog.setTitle("Xóa nhóm hàng hóa");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);
						if (panel.isDelete()) {
							restore = false;
							pr.setRecycleBin(false);
							ProductModelManager.getInstance().saveProductTag(pr);
							reset();
							return true;
						}
					}
				}
			} catch (Exception e) {
			}
		}

		// Kiểm tra Xem nhóm con có làm cha của nhóm cha hay không.
		if (edit == true) {
			if (cboParentGroup.getSelectedIndex() != -1) {
				String[] NamGroup = cboParentGroup.getSelectedItem().toString().trim().split("/");
				for (int i = 0; i < NamGroup.length; i++) {
					if (NamGroup[i].equals(nameProduct)) {
						check = false;
						lbParentGroup.setForeground(Color.red);
						break;
					}
				}
			}
		}

		if (!check) {
			lbMessenger.setText("Dữ liệu vùng đỏ bị lỗi");
			lbMessenger.setForeground(Color.red);
			lbMessenger.setVisible(true);
			if (codeError)
				lbMessenger.setText("Lỗi trùng mã nhóm hàng!");
			return false;
		} else {
			lbMessenger.setText(" ");
			return true;
		}
	}

	@Override
	public void save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return;
		} else {
			// Lấy dữ liệu đổ vào đối tượng
			productTag = getData();

			// Kiểm tra các trường dữ liệu sau khi đổ dữ liệu vào
			if (!checkData()) {
				return;
			}

			AbstractPersistable.State status;
			if (productTag.getId() == null)
				status = AbstractPersistable.State.NEW;
			else
				status = AbstractPersistable.State.MODIFIED;

			if (productTag != null) {
				ProductTag pr = ProductModelManager.getInstance().getProductTagByTag(productTag.getTag());
				if(pr!=null && !String.valueOf(pr.getId()).equals(String.valueOf(productTag.getId()))){
					pr.setTag("delete1");
					ProductModelManager.getInstance().deleteProductTag(pr);
				}
				productTag = ProductModelManager.getInstance().saveProductTag(productTag);
				productTag.setPersistableState(status);
				if (DialogScreenOftenPos.screenOften1 != null)
					DialogScreenOftenPos.getInstance().loadGuiProductTag(productTag);

				Thread t = new Thread() {
					public void run() {
						try {
							if (ScreenOften.screenOften1 != null)
								ScreenOften.getInstance().setResetProduct(true);
						} catch (Exception e) {
							System.out.println("===LƯU NHÓM SẢN PHẨM=== Lỗi load lại giao diện");
						}
					};
				};
				t.start();
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
			setEnableCompoment(true);
			txtCodeGroup.setEnabled(false);
			edit = true;
		}
	}

	public boolean showCatalog() throws Exception {
		if (productTag.getCode().trim().isEmpty()) {
			return ProductModelManager.getInstance().deleteProductTag(productTag);
		} else {
			String nameproduct = txtNameGroup.getText();

			// Lấy ra danh sách productTag(cấp n-1) của ProductTag (cấp n);
			listProductTag = ProductModelManager.getInstance().findProductTagRoot(productTag.getTag());

			// lấy ra danh sách Product của ProductTag
			listProduct = ProductModelManager.getInstance().findByProductTag(productTag.getTag());

			if (listProductTag.isEmpty() || listProductTag == null) {
				if (listProduct.isEmpty() || listProduct == null) {
					boolean flag = ProductModelManager.getInstance().deleteProductTag(productTag);
					loadTable();
					reset();
					return flag;
				} else {
					try {
						PanelManageDelete<Product> panel = new PanelManageDelete<Product>(nameproduct, listProduct,
						    new GroupProductJComboBoxDelete(productTag));
						DialogResto dialog = new DialogResto(panel, true, 0, 80);
						dialog.setName("dlQuanLyNhomHang");
						dialog.dispose();
						dialog.setUndecorated(true);
						dialog.setTitle("Quản lý xóa nhóm sản phẩm");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);
					} catch (Exception e2) {
						e2.printStackTrace();
					}

					if ((ProductModelManager.getInstance().findByProductTag(productTag.getTag()).isEmpty())
					    || (ProductModelManager.getInstance().findByProductTag(productTag.getTag()) == null)) {
						boolean flag = ProductModelManager.getInstance().deleteProductTag(productTag);
						loadTable();
						reset();
						return flag;
					} else {
						return false;
					}
				}
			} else {
				try {
					PanelManageDelete<ProductTag> panel = new PanelManageDelete<ProductTag>(nameproduct, listProductTag,
					    new ProductTagJComboBoxDelete(productTag));
					DialogResto dialog = new DialogResto(panel, true, 0, 80);
					dialog.setName("dlQuanLyNhomHang");
					dialog.dispose();
					dialog.setUndecorated(true);
					dialog.setTitle("Quản lý xóa nhóm sản phẩm");
					dialog.setLocationRelativeTo(null);
					dialog.setModal(true);
					dialog.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				if ((ProductModelManager.getInstance().findProductTagRoot(productTag.getTag()).isEmpty())
				    || (ProductModelManager.getInstance().findProductTagRoot(productTag.getTag()) == null)) {
					boolean flag = ProductModelManager.getInstance().deleteProductTag(productTag);
					loadTable();
					reset();
					return flag;
				} else {
					loadTable();
					reset();
					return false;
				}
			}
		}
	}

	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {
			try {
				String str = "Xóa nhóm ";
				PanelChoise panel = new PanelChoise(str + productTag + "?");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				dialog.setTitle("Xóa nhóm sản phẩm");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					// Sao chep doi tuong xoa
					ProductTag pt = new ProductTag();
					pt.setCode(productTag.getCode());
					pt.setTag(productTag.getTag());
					pt.setParentTag(productTag.getParentTag());
					pt.setPersistableState(AbstractPersistable.State.DELETED);

					// Xoa trong database
					boolean deleteResult = showCatalog();

					// Thuc hien xoa tren man hinh POS
					if (DialogScreenOftenPos.screenOften1 != null && deleteResult)
						DialogScreenOftenPos.getInstance().loadGuiProductTag(pt);

					Thread t = new Thread() {
						public void run() {
							try {
								if (ScreenOften.screenOften1 != null)
									ScreenOften.getInstance().setResetProduct(true);
							} catch (Exception e) {
								System.out.println("===XÓA NHÓM SẢN PHẨM==: Lỗi load lại giao diện");
							}
						};
					};
					t.start();
				}
			} catch (Exception e) {
			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		}
	}

	@Override
	public void reset() throws Exception {

		setEnableCompoment(true);
		txtCodeGroup.setText(DateUtil.asCompactDateTimeId(new Date()));
		txtNameGroup.setText("");
		txtDescription.setText("");

		lbCompany.setForeground(Color.black);
		lbParentGroup.setForeground(Color.black);
		lbCodeGroup.setForeground(Color.black);
		lbNameGroup.setForeground(Color.black);
		lbDescription.setForeground(Color.black);
		lbMessenger.setText(" ");
		productTag = new ProductTag();
		loadTable();

		List<ProductTag> productTags = ProductModelManager.getInstance().getProductTags();
		cboParentGroup.setData(productTags);
		edit = false;
	}

	@Override
	public void refresh() throws Exception {
		setData();
		setEnableCompoment(false);
		lbCompany.setForeground(Color.black);
		lbParentGroup.setForeground(Color.black);
		lbCodeGroup.setForeground(Color.black);
		lbNameGroup.setForeground(Color.black);
		lbMessenger.setText(" ");
	}

	private void setEnableCompoment(boolean value) {
		txtNameGroup.setEnabled(value);
		txtCodeGroup.setEnabled(value);
		cboParentGroup.setEnabled(value);
		txtDescription.setEnabled(value);
	}

	public List<ProductTag> getProductTags() throws Exception {
		return ProductModelManager.getInstance().getProductTags();
	}

	public List<com.hkt.module.product.entity.Product> findByProductTag(String tag) throws Exception {
		return ProductModelManager.getInstance().findByProductTag(tag);
	}

	public List<ProductTag> findProductTagRoot(String tagRoot) throws Exception {
		return ProductModelManager.getInstance().findProductTagRoot(tagRoot);
	}

	public void loadTable() throws Exception {
		pnTable.setData(getProductTags());
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
