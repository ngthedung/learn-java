package com.hkt.client.swingexp.app.khuyenmai;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.core.ProductTagJComboBox;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.product.entity.Product;
import com.hkt.module.promotion.entity.PromotionGiveaways;
import com.hkt.util.text.DateUtil;

@SuppressWarnings("serial")
public class PanelGiveProduct extends MyPanel implements IDialogMain {
	private JLabel lbNhomSP, lbTenSP, lbTuNgay, lbDenNgay, lbSoLuongTang, lbSoLuongMua, lbMieuTa, lbMessenger;
	private ProductTagJComboBox cbNhom;
	private TextPopup<Product> txtProduct;
	private MyJDateChooser txtTuNgay, txtDenNgay;
	private JTextField txtSoLuongTang, txtSoLuongMua, txtMieuTa;
	@SuppressWarnings("unused")
	private DateFormat df = new SimpleDateFormat("yyyyMMddHHssmm");
	private GiveProductTable tbPromotionGiveProduct;
	private PromotionGiveaways giveaways;
	private JScrollPane scrollPaneTable, scrollIndex;
	@SuppressWarnings("unused")
	private int index = 0;
	public static String permission;
	private boolean reset = false;

	public PanelGiveProduct() throws Exception {
		init();

		setOpaque(false);
		reset();
	}

	@SuppressWarnings("static-access")
	private void init() throws Exception {
		createBeginTest();
		lbMessenger = new JLabel("");
		scrollPaneTable = new JScrollPane();
		scrollPaneTable.setOpaque(false);
		scrollPaneTable.getViewport().setOpaque(false);
		scrollPaneTable.setBorder(null);

		tbPromotionGiveProduct = new GiveProductTable();
		tbPromotionGiveProduct.setName("TbPromotionGiveProduct");

		tbPromotionGiveProduct.setPromotionGiveaways(getObjects());
		tbPromotionGiveProduct.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				try {
					giveawaysListMouseClicked(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		tbPromotionGiveProduct.setPreferredScrollableViewportSize(new Dimension(200, 290));
		scrollPaneTable.setViewportView(tbPromotionGiveProduct);

		lbNhomSP = new JLabel("Nhóm");
		lbTenSP = new JLabel("Sản phẩm");
		lbTuNgay = new JLabel("Từ ngày");
		lbDenNgay = new JLabel("Đến ngày");
		lbSoLuongMua = new JLabel("Mua");
		lbSoLuongTang = new JLabel("Tặng");
		lbMieuTa = new JLabel("Miêu tả");
		cbNhom = new ProductTagJComboBox();
		cbNhom.setName("cbGroupProduct");
		cbNhom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cbNhom.getSelectedProductTag() != null) {
					try {
						List<Product> products = ProductModelManager.getInstance().findByProductTag(cbNhom.getSelectedProductTag().getTag());
						txtProduct.setData(products);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} 
				else {
					try {
						txtProduct.setData(ProductModelManager.getInstance().filterProduct());
					} catch (Exception e2) {
					}
				}
			}
		});

		txtProduct = new TextPopup<Product>(Product.class);
		try {
			txtProduct.setData(ProductModelManager.getInstance().findAllProducts());
		} catch (Exception e) {
		}
		txtProduct.setName("txtProduct");
		txtTuNgay = new MyJDateChooser();
		txtTuNgay.setName("txtTuNgay");
		txtTuNgay.setDate(new Date());

		txtDenNgay = new MyJDateChooser();
		txtDenNgay.setName("txtDenNgay");
		txtDenNgay.setDate(new Date());

		txtSoLuongTang = new JTextField();
		txtSoLuongTang.setName("txtSoLuongTang");
		txtSoLuongTang.setHorizontalAlignment(txtSoLuongTang.RIGHT);

		txtSoLuongMua = new JTextField();
		txtSoLuongMua.setName("txtSoLuongMua");
		txtSoLuongMua.setHorizontalAlignment(txtSoLuongMua.RIGHT);

		txtMieuTa = new JTextField();
		txtMieuTa.setName("txtMieuTa");

		MyGroupLayout layout = new MyGroupLayout(this);

		layout.add(0, 0, lbNhomSP);
		layout.add(0, 1, cbNhom);
		layout.add(0, 2, lbTenSP);
		layout.add(0, 3, txtProduct);

		layout.add(1, 0, lbTuNgay);
		layout.add(1, 1, txtTuNgay);
		layout.add(1, 2, lbDenNgay);
		layout.add(1, 3, txtDenNgay);

		layout.add(2, 0, lbSoLuongMua);
		layout.add(2, 1, txtSoLuongMua);
		layout.add(2, 2, lbSoLuongTang);
		layout.add(2, 3, txtSoLuongTang);

		layout.add(3, 0, lbMieuTa);
		layout.add(3, 1, txtMieuTa);
		layout.addMessenger(lbMessenger);

		// Thêm button cập nhật index
		scrollIndex = new JScrollPane();
		scrollIndex.setOpaque(false);
		scrollIndex.getViewport().setOpaque(false);
		scrollIndex.setBorder(null);
		scrollIndex.setViewportView(tbPromotionGiveProduct.getPanleButton());

		// Giao diện Button cập nhật index đi cùng với scrollPaneTable
		layout.add(4, 0, scrollIndex);
		layout.add(4, 1, scrollPaneTable);
		layout.updateGui();
	}

	protected void giveawaysListMouseClicked(MouseEvent event) {

		giveaways = tbPromotionGiveProduct.getSelectedBean();
		int click = 2;
		if (giveaways.getName().indexOf("HktTest") > 0 && event.getButton() == 3) {
			click = 1;
		}
		if (event.getClickCount() >= click) {
			setData();
			index = tbPromotionGiveProduct.getSelectedRow();
			setEnableCompoment(false);
			((DialogMain) getRootPane().getParent()).showButton(false);
		}

	}

	public void setObject(PromotionGiveaways object) throws Exception {
		this.giveaways = object;
		if (object == null) {
			giveaways = new PromotionGiveaways();
		}
		setData();
	}

	private List<PromotionGiveaways> getObjects() {
		try {
			return PromotionModelManager.getInstance().getPromotionGiveaways();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<PromotionGiveaways>();
		}
	}

	private void loadTable() throws Exception {
		tbPromotionGiveProduct.setPromotionGiveaways(getObjects());

	}

	@Override
	public void save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này!");
			return;
		}
		if (!checkData()) {
			return;
		}
		getData();
		PromotionModelManager.getInstance().savePromotionGiveaways(giveaways);
		if(reset == true)
			((JDialog) getRootPane().getParent()).dispose();
		reset();
	}

	@Override
	public void edit() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này!");
			return;
		}
		if (!checkData()) {
			return;
		}
		reset = true;
		cbNhom.setEnabled(false);
		txtProduct.setEnabled(false);
		setEnableCompoment(true);
	}

	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) != Capability.ADMIN) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này!");
			return;
		} else {

			String str = "Xóa ";
			PanelChoise pnPanel = new PanelChoise(str + giveaways + "?");
			pnPanel.setName("Xoa");
			DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
			dialog1.setName("dlXoa");
			dialog1.setTitle("Xóa khuyến mãi tặng sản phẩm");
			dialog1.setLocationRelativeTo(null);
			dialog1.setModal(true);
			dialog1.setVisible(true);

			if (pnPanel.isDelete()) {
				PromotionModelManager.getInstance().deletePromotionGiveaways(giveaways);
				((JDialog) getRootPane().getParent()).dispose();
				reset();
			} else {
				reset();
			}

		}
	}

	@Override
	public void reset() throws Exception {
		txtSoLuongMua.setText("1");
		txtSoLuongTang.setText("1");
		txtMieuTa.setText("");
		txtTuNgay.setDate(new Date());
		txtDenNgay.setDate(null);

		lbNhomSP.setForeground(Color.black);
		lbTenSP.setForeground(Color.black);
		lbTuNgay.setForeground(Color.black);
		lbDenNgay.setForeground(Color.black);
		lbSoLuongMua.setForeground(Color.black);
		lbSoLuongTang.setForeground(Color.black);
		lbMessenger.setForeground(Color.black);
//		cbTenSP.setSelectedIndex(0);
		txtProduct.setItem(null);
		lbMessenger.setText(" ");
		giveaways = new PromotionGiveaways();
		loadTable();
		setEnableCompoment(true);

	}

	@Override
	public void refresh() throws Exception {
		try {
			setObject(tbPromotionGiveProduct.getSelectedBean());
			
		} catch (Exception e) {
			giveaways = new PromotionGiveaways();
		}
		setData();

	}

	@SuppressWarnings("unused")
	private boolean checkData() {
		int count = 0;
		Product product;

		if (txtProduct.getItem() != null) {
			product = (Product) txtProduct.getItem();

			if (!txtSoLuongMua.getText().isEmpty()) {
				try {
					Double.parseDouble(txtSoLuongMua.getText());
					lbSoLuongMua.setForeground(Color.BLACK);
				} catch (Exception e2) {
					lbSoLuongMua.setForeground(Color.RED);
					count++;
				}
				lbSoLuongMua.setForeground(Color.BLACK);
			} else {
				lbSoLuongMua.setForeground(Color.RED);
				count++;
			}

			if (!txtSoLuongTang.getText().isEmpty()) {
				try {
					Double.parseDouble(txtSoLuongTang.getText());
					lbSoLuongTang.setForeground(Color.BLACK);
				} catch (Exception e1) {
					lbSoLuongTang.setForeground(Color.RED);
					count++;
				}
				lbSoLuongTang.setForeground(Color.BLACK);
			} else {
				lbSoLuongTang.setForeground(Color.RED);
				count++;
			}

			if (count == 0) {
				lbSoLuongTang.setForeground(Color.black);
				lbSoLuongMua.setForeground(Color.black);
				lbMessenger.setText("");
				return true;
			} else {
				if (count == 2) {
					lbSoLuongTang.setForeground(Color.RED);
					lbSoLuongMua.setForeground(Color.RED);
				}
				return false;
			}

		} else {
			product = null;
			lbTenSP.setForeground(Color.RED);
			lbMessenger.setText("Kiểm tra lỗi báo đỏ !");
			lbMessenger.setForeground(Color.red);
			return false;
		}
	}

	private void setData() {
		try {
			txtTuNgay.setDate(giveaways.getFromDate());
			txtDenNgay.setDate(giveaways.getToDate());
			txtSoLuongMua.setText(String.valueOf(giveaways.getQuantityBuy()));
			txtSoLuongTang.setText(String.valueOf(giveaways.getQuantityGive()));
			txtMieuTa.setText(giveaways.getDescription());
			Product product = ProductModelManager.getInstance().getProductByCode(giveaways.getProductCode());
			txtProduct.setObject(product);
			txtProduct.setText(product.toString());
			txtProduct.setObject(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//setdata khi lấy dữ liệu bên tổng hợp các khuyến mại
	public void setData(PromotionGiveaways promotionGiveaways) {
		giveaways = promotionGiveaways;
		try {
			txtTuNgay.setDate(giveaways.getFromDate());
			txtDenNgay.setDate(giveaways.getToDate());
			txtSoLuongMua.setText(String.valueOf(giveaways.getQuantityBuy()));
			txtSoLuongTang.setText(String.valueOf(giveaways.getQuantityGive()));
			txtMieuTa.setText(giveaways.getDescription());
			Product product = ProductModelManager.getInstance().getProductByCode(giveaways.getProductCode());
			txtProduct.setObject(product);
			txtProduct.setText(product.toString());
			txtProduct.setObject(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//---------------------------------------------------

	private void getData() {
		giveaways.setCode(DateUtil.asCompactDateTimeId(new Date()));
		giveaways.setName("KM SP " + txtProduct.getItem().getName());
		giveaways.setProductCode(txtProduct.getItem().getCode());
		giveaways.setFromDate(txtTuNgay.getDate());
		giveaways.setToDate(txtDenNgay.getDate());
		giveaways.setQuantityBuy(Double.parseDouble(txtSoLuongMua.getText()));
		giveaways.setQuantityGive(Double.parseDouble(txtSoLuongTang.getText()));
		giveaways.setDescription(txtMieuTa.getText());
		giveaways.setTypePromotion("KM tặng SP");
	}

	public void setEnableCompoment(boolean value) {
		cbNhom.setEnabled(value);
		txtProduct.setEnabled(value);
		txtSoLuongMua.setEnabled(value);
		txtSoLuongTang.setEnabled(value);
		txtMieuTa.setEnabled(value);
		cbNhom.setEnabled(value);
		txtTuNgay.setEnabled(value);
		txtDenNgay.setEnabled(value);

	}

	// Tạo dữ liệu mẫu liên quan
	@Override
	public void createBeginTest() {
		if (MyPanel.isTest) {
			// Tạo nhóm hàng hóa
			for (int i = 1; i <= 3; i++) {
				// Tạo sản phẩm
				Product product = new Product();
				product.setCode("Mã HH HktTest" + i);
				product.setName("HH HktTest" + i);
				product.setMaker("HktTest" + i);
				try {
					ProductModelManager.getInstance().saveProduct(product);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void deleteDataTest() {
		if (!PanelTestAll.runAll) {
			DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
			if (DialogTest.getInstance().isTest()) {
				try {
					PromotionModelManager.getInstance().deleteTest("HktTest");
					ProductModelManager.getInstance().deleteTest("HktTest");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
