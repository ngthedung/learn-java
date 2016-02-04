package com.hkt.client.swingexp.app.khohang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.hkt.client.swingexp.app.banhang.screen.pos.DialogScreenOftenPos;
import com.hkt.client.swingexp.app.component.ExtendJCheckBox;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextArea;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.ImageTool;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.core.UnitMoneyJComboBox;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelProductUnit;
import com.hkt.client.swingexp.app.hethong.PanelRecybin;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelManageCodes;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.accounting.DefaultInvoiceCalculator;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.accounting.entity.Invoice.Status;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceItemAttribute;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.accounting.entity.InvoiceTransaction.TransactionType;
import com.hkt.module.config.locale.Currency;
import com.hkt.module.config.locale.ProductUnit;
import com.hkt.module.core.entity.AbstractPersistable;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductAttribute;
import com.hkt.module.product.entity.ProductImage;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.restaurant.entity.Recipe;
import com.hkt.module.restaurant.entity.RecipeIngredient;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.IdentityProduct.ExportType;
import com.hkt.module.warehouse.entity.IdentityProduct.ImportType;
import com.hkt.module.warehouse.entity.IdentityProductAttribute;
import com.hkt.module.warehouse.entity.Warehouse;
import com.hkt.util.text.DateUtil;

public class PanelRepositoryProducts2 extends MyPanel implements IDialogMain {
	private ExtendJLabel lblName, lblCode, lblBarCode, lblNameOther, lblUnit, lblDescription, lblQuantityBegin,
	    lblUnitPrice, lblMessager;
	private JLabel lbAvatar;
	private ExtendJTextField txtName, txtCode, txtBarCode, txtNameOther, txtQuantityBegin, txtUnitPriceRepository,
	    txtUnitPriceProductPriceType;
	private ExtendJTextField txtDescription;
	private JPanel container1, container2, panelAvatar;
	private ExtendJComboBox cbUnitProduct, cbRepository;
	private ExtendJCheckBox chbWarehousing, chbUnchangedInput, chbSetCalculateReport;
	private UnitMoneyJComboBox cbUnitProductPriceType;
	private SimpleDateFormat fomatDate = new SimpleDateFormat("yyyyMMddHHmmssSS");
	private List<ProductTag> listProductTagSelected = new ArrayList<ProductTag>();
	private List<CheckBoxProductTag> listCheckBoxProductTag = new ArrayList<CheckBoxProductTag>();
	private List<CheckBoxProductPriceType> listProductPriceTypeSelected = new ArrayList<CheckBoxProductPriceType>();
	private List<CheckBoxProductPriceType> listCheckBox = new ArrayList<CheckBoxProductPriceType>();
	private Product product;
	private ProductImage productimage;
	private ProductPrice productPrice;
	private List<ProductPrice> listProductPrice;
	private boolean edit = false;
	private List<Component> components;
	private boolean restore = true;
	public static String permission;
	private boolean reset = false;
	private JCheckBox chbPrice,chbCode;
	private JTextField txtEndDate;

	/**
	 * Create the PanelRepositoryProducts
	 */

	public PanelRepositoryProducts2() {
		product = new Product();

		init();
		this.setPreferredSize(new Dimension(300, 250));

		DefaultComboBoxModel<ProductUnit> modelProductUnits = new DefaultComboBoxModel(getProductUnits().toArray());
		cbUnitProduct.setModel(modelProductUnits);
		loadCode();
		txtCode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtCode.selectAll();
			}
		});
	}

	public PanelRepositoryProducts2(Product product) {
		this.product = product;
		edit = true;
		try {
			this.listProductTagSelected.addAll(product.getProductTags());
			this.listProductPrice = ProductModelManager.getInstance().getProductPriceByProductCode(product.getCode());
		} catch (Exception e) {
			listProductPrice = new ArrayList<ProductPrice>();
			listProductTagSelected = new ArrayList<ProductTag>();
			e.printStackTrace();
		}

		init();
		this.setPreferredSize(new Dimension(300, 250));

		DefaultComboBoxModel<ProductUnit> modelProductUnits = new DefaultComboBoxModel(getProductUnits().toArray());
		cbUnitProduct.setModel(modelProductUnits);

		setData();

		// txtBarCode.setEditable(false);
		txtCode.setEditable(false);

	}

	public void init() {
		createBeginTest();
		this.setLayout(new BorderLayout(0, 10));
		setOpaque(false);
		Panel_PAGESTART panelPAGESTART = new Panel_PAGESTART();
		Panel_PAGECENTER panel_PAGECENTER = new Panel_PAGECENTER();
		this.add(panelPAGESTART, BorderLayout.PAGE_START);
		this.add(panel_PAGECENTER, BorderLayout.CENTER);
		components = getAllComponents(panel_PAGECENTER);
		loadCode();
		txtCode.addCaretListener(new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) {
				 if(chbCode.isSelected()){
					 txtBarCode.setText(txtCode.getText());
				 }
				
			}
		});
		
	}

	// Khởi tạo và vẽ các component phần đầu Panel_PAGESTART
	protected class Panel_PAGESTART extends JPanel {

		public Panel_PAGESTART() {
			init();

			chbWarehousing.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if (chbWarehousing.isSelected()) {
						enableRepository(true);
					} else
						enableRepository(false);
				}
			});

			txtName.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					if (!txtName.getText().equals("")) {
						lblName.setForeground(new Color(54, 54, 54));
						lblMessager.setText("");
					}
				}
			});

			cbUnitProduct.addMouseListener(new MouseAdapter() {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON3) {
						try {
							PanelProductUnit pnlProductUnit = new PanelProductUnit();
							pnlProductUnit.setName("DonViSanPham");
							DialogMain dialog = new DialogMain(pnlProductUnit);
							dialog.setTitle("Chỉnh sửa đơn vị sản phẩm");
							dialog.setName("dlDonViSanPham");
							dialog.setVisible(true);
						} catch (Exception e1) {
							e1.printStackTrace();
						} finally {
							DefaultComboBoxModel<ProductUnit> modelProductUnits = new DefaultComboBoxModel(getProductUnits()
							    .toArray());
							cbUnitProduct.setModel(modelProductUnits);
						}
					}
				}
			});
		}

		public void init() {
			setOpaque(false);
			lblName = new ExtendJLabel("Tên SP");
			lblCode = new ExtendJLabel("Mã SP");
			lblBarCode = new ExtendJLabel("Mã Barcode");
			lblNameOther = new ExtendJLabel("Ngoại ngữ");
			lblUnit = new ExtendJLabel("Đơn vị");
			lblDescription = new ExtendJLabel("Giá TK");
			lblQuantityBegin = new ExtendJLabel("SL/Đơn giá");
			lblUnitPrice = new ExtendJLabel("Hạn sử dụng");
			lblMessager = new ExtendJLabel("");
			lblMessager.setForeground(Color.RED);

			txtName = new ExtendJTextField();
			txtCode = new ExtendJTextField();
			txtEndDate = new ExtendJTextField();
			txtBarCode = new ExtendJTextField();
			txtNameOther = new ExtendJTextField();
			txtQuantityBegin = new ExtendJTextField();
			txtUnitPriceRepository = new ExtendJTextField();
			txtDescription = new ExtendJTextField();

			txtName.setName("txtName");
			txtCode.setName("txtCode");
			txtBarCode.setName("txtBarCode");
			txtNameOther.setName("txtNameOther");

			cbUnitProduct = new ExtendJComboBox();
			cbRepository = new ExtendJComboBox();
			try {
				cbRepository.setModel(new DefaultComboBoxModel(WarehouseModelManager.getInstance().findWarehouses().toArray()));
			} catch (Exception e) {
			}
			cbRepository.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON3) {
						PanelWarehouse add;
						try {
							add = new PanelWarehouse();
							add.setName("ThemMoiKho");
							DialogMain dialog = new DialogMain(add);
							dialog.setName("dlThemMoiKho");
							dialog.setTitle("Tạo kho hàng");
							dialog.setResizable(false);
							dialog.setModal(true);
							dialog.setLocationRelativeTo(null);
							dialog.setVisible(true);
							try {
								cbRepository.setModel(new DefaultComboBoxModel(WarehouseModelManager.getInstance().findWarehouses()
								    .toArray()));
							} catch (Exception e1) {
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			chbUnchangedInput = new ExtendJCheckBox("Giữ nguyên giá trị nhập ");
			chbSetCalculateReport = new ExtendJCheckBox("Tính vào thống kê kho ");
			chbSetCalculateReport.setSelected(true);
			chbWarehousing = new ExtendJCheckBox("Nhập kho");

			chbWarehousing.setSelected(false);
			cbUnitProduct.setName("cbUnitProduct");
			chbWarehousing.setName("chbWarehousing");

			txtQuantityBegin.setHorizontalAlignment(JTextField.RIGHT);
			txtUnitPriceRepository.setHorizontalAlignment(JTextField.RIGHT);

			MyGroupLayout layout = new MyGroupLayout(this);
			panelAvatar = new JPanel();
			panelAvatar.setOpaque(false);
			panelAvatar.setLayout(new GridLayout());
			lbAvatar = new JLabel("Chọn ảnh", SwingConstants.CENTER);
			lbAvatar.setBorder(BorderFactory.createEtchedBorder());
			panelAvatar.add(lbAvatar);
			lbAvatar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (txtName.isEnabled())
						if (e.getClickCount() >= 2) {
							ImageIcon image = ImageTool.getInstance().getImage();
							if (image != null) {
								lbAvatar.setText("");
								image = ImageTool.getInstance().resize(image, new Dimension(150, 100));
								lbAvatar.setIcon(image);
							} else {
								lbAvatar.setIcon(null);
								lbAvatar.setText("Chọn ảnh");
							}
						}
				}
			});

			layout.addImage(panelAvatar);
			layout.add(0, 0, lblName);
			layout.add(0, 1, txtName);
			layout.add(1, 0, lblCode);
			JPanel p1 = new JPanel(new BorderLayout());
			p1.setOpaque(false);
			chbCode = new JCheckBox();
			chbCode.setOpaque(false);
			chbCode.setSelected(true);
			p1.add(txtCode, BorderLayout.CENTER);
			p1.add(chbCode, BorderLayout.EAST);
			layout.add(1, 1, p1);
			layout.add(2, 0, lblBarCode);
			layout.add(2, 1, txtBarCode);
			layout.add(3, 0, lblNameOther);
			layout.add(3, 1, txtNameOther);
			layout.add(3, 2, chbWarehousing);
			layout.add(3, 3, cbRepository);
			JPanel p2 = new JPanel(new GridLayout(1, 2,5,5));
			p2.setOpaque(false);
			p2.add(txtQuantityBegin);
			p2.add(txtUnitPriceRepository);
			layout.add(4, 0, lblUnit);
			layout.add(4, 1, cbUnitProduct);
			layout.add(4, 2, lblQuantityBegin);
			layout.add(4, 3, p2);

			JPanel p = new JPanel(new BorderLayout());
			p.setOpaque(false);
			chbPrice = new JCheckBox("Tự cập nhật");
			chbPrice.setSelected(true);
			chbPrice.setOpaque(false);
			p.add(txtDescription, BorderLayout.CENTER);
			p.add(chbPrice, BorderLayout.EAST);
			layout.add(5, 0, lblDescription);
			layout.add(5, 1, p);
			layout.add(5, 2, lblUnitPrice);
			layout.add(5, 3, txtEndDate);

			enableRepository(false);
			layout.updateGui();
		}

		private void enableRepository(boolean value) {
			cbRepository.setEnabled(value);
			lblQuantityBegin.setEnabled(value);
			txtQuantityBegin.setEnabled(value);
			txtUnitPriceRepository.setEnabled(value);
		}
	}

	// Khởi tạo và vẽ các component phần trung tâm Panel_PAGECENTER
	protected class Panel_PAGECENTER extends JPanel {
		private JPanel panelPrice, CENTER;
		private ExtendJLabel lblPriceProduct, lblAmount;
		private CheckBoxProductTag chbProductTag;
		private CheckBoxProductPriceType chbProductPriceType;
		private JScrollPane scrollPanel;

		public Panel_PAGECENTER() {
			init();
		}

		public void init() {
			CENTER = new JPanel();
			this.setOpaque(false);
			this.setLayout(new BorderLayout(0, 0));

			CENTER.setOpaque(false);
			CENTER.setLayout(new GridLayout(2, 1, 0, 10));

			/**
			 * Vẽ component bảng giá sản phẩm
			 */
			scrollPanel = new JScrollPane();
			scrollPanel.setOpaque(false);
			scrollPanel.setBorder(null);
			container1 = new JPanel();
			container1.setOpaque(false);

			MyGroupLayout layout1 = new MyGroupLayout(container1);
			for (int i = 1; i <= getProductPriceTypes().size(); i++) {
				lblPriceProduct = new ExtendJLabel("Bảng giá");
				chbProductPriceType = new CheckBoxProductPriceType(getProductPriceTypes().get(i - 1));
				chbProductPriceType.setName(chbProductPriceType.getText());
				chbProductPriceType.setSelected(false);
				chbProductPriceType.setOpaque(false);
				listCheckBox.add(chbProductPriceType);
				lblAmount = new ExtendJLabel("Đơn giá");
				txtUnitPriceProductPriceType = new ExtendJTextField();
				txtUnitPriceProductPriceType.setName(chbProductPriceType.getText());
				txtUnitPriceProductPriceType.setHorizontalAlignment(JTextField.RIGHT);
				cbUnitProductPriceType = new UnitMoneyJComboBox();
				cbUnitProductPriceType.setName("cbUnitProductPriceType" + i);
				chbProductPriceType.setTextField(txtUnitPriceProductPriceType);
				chbProductPriceType.setComboBox(cbUnitProductPriceType);

				panelPrice = new JPanel();
				panelPrice.setLayout(new BorderLayout());
				panelPrice.add(txtUnitPriceProductPriceType, BorderLayout.CENTER);
				panelPrice.add(cbUnitProductPriceType, BorderLayout.LINE_END);

				int row = i - 1;
				layout1.add(row, 0, lblPriceProduct);
				layout1.add(row, 1, chbProductPriceType);
				layout1.add(row, 2, lblAmount);
				layout1.add(row, 3, panelPrice);

				// Kiểm tra danh sách truyền vào để checkBox vào Bảng giá
				if (listProductPrice != null && listProductPrice.size() > 0) {
					List<Long> listId = new ArrayList<Long>();
					for (ProductPrice p : listProductPrice) {
						if (p.getProductPriceType().getId().equals(getProductPriceTypes().get(i - 1).getId())
						    && product.getId() != null && p.getProduct().getId().equals(product.getId())) {
							chbProductPriceType.setSelected(true);
							for (int k = 0; k < cbUnitProductPriceType.getItemCount(); k++) {
								if (((Currency) cbUnitProductPriceType.getItemAt(k)).getCode().equals(p.getCurrencyUnit())) {
									cbUnitProductPriceType.setSelectedIndex(k);
									break;
								}
							}
							if(listId.indexOf(Long.parseLong(String.valueOf(p.getProductPriceType().getId())))>=0){
								try {
									ProductModelManager.getInstance().deleteProductPrice(p);
                } catch (Exception e) {
                }
								
							}else {
								listId.add(Long.parseLong(String.valueOf(p.getProductPriceType().getId())));
								txtUnitPriceProductPriceType.setText(new MyDouble(p.getPrice()).toString());
								listProductPriceTypeSelected.add(chbProductPriceType);
							}
							
						}
					}
				}
				// Thêm hoặc xóa bớt trong danh sách khi checkBox vào Bảng giá
				chbProductPriceType.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						CheckBoxProductPriceType chb = (CheckBoxProductPriceType) e.getSource();
						if (chb.isSelected() == true) {
							listProductPriceTypeSelected.add(chb);
						} else {
							listProductPriceTypeSelected.remove(chb);
						}
					}
				});
			}
			layout1.updateGui();
			scrollPanel.setViewportView(container1);
			scrollPanel.getViewport().setOpaque(false);
			CENTER.add(scrollPanel);

			/**
			 * Vẽ component nhóm hàng hóa
			 */
			scrollPanel = new JScrollPane();
			scrollPanel.setOpaque(false);
			scrollPanel.setBorder(null);
			container2 = new JPanel();
			container2.setOpaque(false);

			MyGroupLayout layout3 = new MyGroupLayout(container2);
			List<ProductTag> listPro = getProductTags();
			for (int i = 1; i <= listPro.size(); i++) {
				lblAmount = new ExtendJLabel("Nhóm " + i);
				lblAmount.setPreferredSize(new Dimension(100, 22));
				chbProductTag = new CheckBoxProductTag(listPro.get(i - 1));
				chbProductTag.setName(chbProductTag.getText());
				listCheckBoxProductTag.add(chbProductTag);
				chbProductTag.setSelected(false);
				chbProductTag.setOpaque(false);

				if ((i - 1) % 2 == 0) {
					layout3.add((i - 1) / 2, 0, lblAmount);
					layout3.add((i - 1) / 2, 1, chbProductTag);
				} else {
					layout3.add((i - 1) / 2, 2, lblAmount);
					layout3.add((i - 1) / 2, 3, chbProductTag);
				}
				// Kiểm tra danh sách truyền vào để checkBox vào giao diện [Nhóm
				// hàng hóa]
				if (listProductTagSelected.size() > 0) {
					for (ProductTag p : listProductTagSelected) {
						if (p.getId().equals(listPro.get(i - 1).getId())) {
							chbProductTag.setSelected(true);
						}
					}
				}
				// Thêm hoặc xóa trong danh sách khi checkBox vào hàng hóa
				chbProductTag.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						CheckBoxProductTag chb = (CheckBoxProductTag) e.getSource();
						if (chb.isSelected() == true) {
							listProductTagSelected.add(chb.getProductTag());
						} else {
							listProductTagSelected.remove(chb.getProductTag());
						}
					}
				});
			}
			layout3.updateGui();
			scrollPanel.setViewportView(container2);
			scrollPanel.getViewport().setOpaque(false);
			CENTER.add(scrollPanel);

			this.add(CENTER, BorderLayout.CENTER);
			JPanel panelCheckbox = new JPanel();
			panelCheckbox.setLayout(new GridLayout(1, 2));
			panelCheckbox.setOpaque(false);
			panelCheckbox.add(lblMessager);
			JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			jPanel.setOpaque(false);
			jPanel.add(chbSetCalculateReport);
			jPanel.add(chbUnchangedInput);
			panelCheckbox.add(jPanel);
			chbUnchangedInput.setHorizontalAlignment(JLabel.RIGHT);
			this.add(panelCheckbox, BorderLayout.PAGE_END);
		}
	}

	// Phương thức lấy ra danh sách ProductUnits
	private List<ProductUnit> getProductUnits() {
		List<ProductUnit> list = new ArrayList<ProductUnit>();
		try {
			list.addAll(LocaleModelManager.getInstance().getProductUnits());
			return list;
		} catch (Exception e) {
			return list;
		}
	}

	// Phương thức lấy ra danh sách ProductTag
	private List<ProductTag> getProductTags() {
		try {
			return ProductModelManager.getInstance().getProductTags();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ProductTag>();
		}
	}

	// Phương thức lấy ra danh sách ProductPriceType
	private List<ProductPriceType> getProductPriceTypes() {
		try {
			return ProductModelManager.getInstance().getProductPriceTypes();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ProductPriceType>();
		}
	}

	// Phương thức lấy ra toàn bộ component có trong Container
	private List<Component> getAllComponents(final Container c) {
		Component[] comps = c.getComponents();
		List<Component> compList = new ArrayList<Component>();
		for (Component comp : comps) {
			compList.add(comp);
			if (comp instanceof Container) {
				compList.addAll(getAllComponents((Container) comp));
			}
		}
		return compList;
	}

	// Phương thức lấy dữ liệu cho ra giao diện
	public void setData() {
		txtName.setText(product.getName());
		txtCode.setText(product.codeView());
		// if (count > 0) {
		// for (int i = 0; i < count; i++) {
		// if
		// (product.getProductAttributes().get(i)!=null&&product.getProductAttributes().get(i).getName().equals("BarCode"))
		// {
		// txtBarCode.setText(product.getProductAttributes().get(i).getValue());
		// }
		// }
		// } else {
		// if (product.getProductAttributes().get(0).getName().equals("BarCode")) {
		// txtBarCode.setText(product.getProductAttributes().get(0).getValue());
		// }
		// }
		txtNameOther.setText(product.getNameOther());
		txtDescription.setText(new MyDouble(product.getPrice()).toString());
		if (product.isUpdatePrice()) {
			chbPrice.setSelected(true);
		} else {
			chbPrice.setSelected(false);
		}
		txtBarCode.setText(product.getDescription());
		txtEndDate.setText(product.getModifiedBy());
		chbSetCalculateReport.setSelected(product.isCalculateReport());
		for (int i = 0; i < getProductUnits().size(); i++) {
			if (getProductUnits().get(i).getCode().equals(product.getUnit())) {
				cbUnitProduct.setSelectedIndex(i);
			}
		}
		productimage = product.getProImage();
		if (productimage != null) {
			try {
				String image = product.getProImage().get("image").toString();
				ImageIcon icon = ImageTool.getInstance().decodeToImage(image);
				lbAvatar.setText("");
				lbAvatar.setIcon(icon);
			} catch (Exception e) {
				lbAvatar.setIcon(null);
				lbAvatar.setText("Chọn ảnh");
			}
		}

		setEnabledCompoment(false);
	}

	// Phương thức đổ dữ liệu từ giao diện vào đối tượng
	private void getData(Product pro) {
		restore = true;
		pro.setName(txtName.getText());
		if (txtCode.isEnabled()) {
			// String code =
			// AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.SanPham);
			// if (code != null) {
			// pro.setCode(code);
			// } else {
			pro.setCode(txtCode.getText());
			// }
		}
		pro.setCreatedTime(new Date());
		pro.setModifiedBy(txtEndDate.getText());
		pro.setNameOther(txtNameOther.getText());
		pro.setDescription(txtBarCode.getText());
		pro.setProductTags(listProductTagSelected);
		pro.setCalculateReport(chbSetCalculateReport.isSelected());
		pro.setUpdatePrice(chbPrice.isSelected());
		try {
			pro.setPrice(MyDouble.parseDouble(txtDescription.getText()));
		} catch (Exception e) {
			pro.setPrice(0);
		}
		try {
			pro.setUnit(((ProductUnit) cbUnitProduct.getSelectedItem()).getCode());
		} catch (Exception e) {
		}
		pro.setMaker("HKT");

		if (productimage == null) {
			productimage = new ProductImage();
		}

		ImageIcon image = (ImageIcon) lbAvatar.getIcon();
		try {
			if (image != null) {
				String urlImage = ImageTool.getInstance().encodeToString(image.getImage());
				productimage.put("image", urlImage);
				pro.setProImage(productimage);
			} else {
				productimage.put("image", null);
				pro.setProImage(productimage);
			}
		} catch (Exception exception) {
		}
	}

	// Phương thức kiểm tra dữ liệu đầu vào
	private boolean checkData() {
		// Biến đếm số lỗi phát sinh -> tăng lên khi gặp lỗi
		int countError = 0;
		String str = "Kiểm tra mã bị trùng - lỗi báo đỏ";
		// Kiểm tra ô tên hàng hóa khác trống
		if (!txtName.getText().equals("") && txtName.getText() != null) {
			lblName.setForeground(new Color(51, 51, 51));
		} else {
			lblName.setForeground(Color.RED);
			countError++;
		}
		if (!edit) {
			List<Product> products = ProductModelManager.getInstance().getProductByBarcode(txtBarCode.getText());
			if (products.size() > 0) {
				countError++;
				str = "Trùng mã Barcode";
			}
		}
		// Kiểm tra ô mã hàng hóa khác trống và trùng mã
		if (!txtCode.getText().equals("") && !txtCode.getText().isEmpty()) {
			lblCode.setForeground(new Color(51, 51, 51));
			if (!edit) {
				try {
					Product pr = ProductModelManager.getInstance().getProductByCode(txtCode.getText());
					if (pr != null) {
						lblCode.setForeground(Color.RED);
						countError++;
						str = "Trùng mã sản phẩm";
						if (pr.isRecycleBin()) {
							PanelRecybin panel = new PanelRecybin("Mã đã bị xóa trước đó!",
							    " Chọn ĐỒNG Ý để lấy lại thông tin ban đầu hoặc THOÁT và nhập mã khác!");
							panel.setName("Xoa");
							DialogResto dialog = new DialogResto(panel, false, 0, 120);
							dialog.setName("dlXoa");
							dialog.setTitle("Thêm hàng hóa mới");
							dialog.setLocationRelativeTo(null);
							dialog.setModal(true);
							dialog.setVisible(true);
							if (panel.isDelete()) {
								restore = false;
								pr.setRecycleBin(false);
								ProductModelManager.getInstance().saveProduct(pr);
								reset();
								return true;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			lblCode.setForeground(Color.RED);
			countError++;
		}
		// Kiểm tra đơn giá của bảng giá khác rỗng hoặc kí tự (Bắt buộc là số)

		// Kiểm tra đơn vị trống
		if (cbUnitProduct.getSelectedItem() != null) {
			lblUnit.setForeground(new Color(51, 51, 51));
		} else {
			lblUnit.setForeground(Color.RED);
			countError++;
			str = "Chưa có đơn vị sản phẩm";
		}

		// Kết thúc kiểm tra hiện thông báo nếu có lỗi
		if (countError > 0) {
			lblMessager.setText(str);
			return false;
		} else {
			lblMessager.setText("");
			return true;
		}
	}

	private void loadCode() {
		if (product == null || product.getId() == null) {
			try {
				String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.SanPham, null, new Date(),
				    false);
				if (!code.isEmpty()) {
					txtCode.setText(code);
					txtCode.setEnabled(false);
					txtBarCode.setText(code);
				} else {
					txtCode.setText(DateUtil.asCompactDateTimeId(new Date()));
					txtBarCode.setText(txtCode.getText());
				}
			} catch (Exception e) {
				txtCode.setText(DateUtil.asCompactDateTimeId(new Date()));
				txtBarCode.setText(txtCode.getText());
			}
		}

	}

	// Hàm lưu dữ liệu cho nút [Thêm]
	@Override
	public void save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này!");
			return;
		}
		if (checkData()) {
			if (restore) {
				getData(product);
				product.setName(txtName.getText());
				if (product.getId() == null) {
					String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.SanPham, null, new Date(),
					    true);
					if (code != null) {
						product.setCode(code);
					} else {
						product.setCode(txtCode.getText());
					}
				}
				System.out.println(listProductTagSelected);
				product.setMaker("HKT");
				product.setModifiedBy(txtEndDate.getText());
				product.setImage(txtDescription.getText());
				product.setDescription(txtBarCode.getText());
				product.setProductTags(listProductTagSelected);
				product.setCalculateReport(chbSetCalculateReport.isSelected());
				product.setUpdatePrice(chbPrice.isSelected());
				try {
					product.setPrice(MyDouble.parseDouble(txtDescription.getText()));
				} catch (Exception e) {
					product.setPrice(0);
				}
				List<ProductAttribute> productAttributes = new ArrayList<ProductAttribute>();
				if (listProductTagSelected.size() > 0) {
					for (ProductTag tag : listProductTagSelected) {
						ProductAttribute a = new ProductAttribute();
						a.setName(AccountingModelManager.product);
						a.setValue(tag.getTag());
						productAttributes.add(a);
					}
				} else {
					ProductAttribute a = new ProductAttribute();
					a.setName(AccountingModelManager.product);
					a.setValue(null);
					productAttributes.add(a);
				}

				product.setProductAttributes(productAttributes);
				try {
					product.setUnit(((ProductUnit) cbUnitProduct.getSelectedItem()).getCode());
				} catch (Exception e) {
				}

				AbstractPersistable.State statusProduct;
				if (product.getId() == null) {
					statusProduct = AbstractPersistable.State.NEW;
				} else {
					statusProduct = AbstractPersistable.State.MODIFIED;
				}
				Product p = ProductModelManager.getInstance().saveProduct(product);
				p.setPersistableState(statusProduct);

				if (listProductPrice != null) {
					for (int i = 0; i < listProductPrice.size();) {
						boolean flat = false;
					
						for (int j = 0; j < listProductPriceTypeSelected.size(); j++) {
							if (listProductPrice.get(i).getProductPriceType().getId() == listProductPriceTypeSelected.get(j)
							    .getProductPriceType().getId()) {
								flat = true;
								break;
							}
						}
						if (!flat) {
							ProductModelManager.getInstance().deleteProductPrice(listProductPrice.get(i));
							listProductPrice.remove(i);
						} else {
							i++;
						}
					}

					for (int i = 0; i < listProductPriceTypeSelected.size(); i++) {
						boolean flat = false;
						for (int j = 0; j < listProductPrice.size(); j++) {
							if (listProductPriceTypeSelected.get(i).getProductPriceType().getId() == listProductPrice.get(j)
							    .getProductPriceType().getId()
							    && p.getId().equals(listProductPrice.get(j).getProduct().getId())) {
								double price = MyDouble.parseDouble(listProductPriceTypeSelected.get(i).getTextField().getText());
								listProductPrice.get(j).setPrice(price);
								listProductPrice.get(j).setUnit(((ProductUnit) cbUnitProduct.getSelectedItem()).getCode());
								listProductPrice.get(j).setCurrencyUnit(
								    listProductPriceTypeSelected.get(i).getComboBox().getSelectedCurrency().getCode());
								ProductModelManager.getInstance().saveProductPrice(listProductPrice.get(j));
								flat = true;
								break;
							}
						}
						if (!flat) {
							productPrice = new ProductPrice();
							productPrice.setProduct(p);
							productPrice.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
							productPrice.setProductPriceType(listProductPriceTypeSelected.get(i).getProductPriceType());
							productPrice.setUnit(((ProductUnit) cbUnitProduct.getSelectedItem()).getCode());
							productPrice.setPrice(MyDouble.parseDouble(listProductPriceTypeSelected.get(i).getTextField().getText()));
							productPrice.setCurrencyUnit(listProductPriceTypeSelected.get(i).getComboBox().getSelectedCurrency()
							    .getCode());
							productPrice.setDescription(p.getName());
							ProductModelManager.getInstance().saveProductPrice(productPrice);
						}
					}

				} else {
					// Trường hợp thêm mới hàng hóa thì tạo ra các bảng giá mới và lưu lại
					if (listProductPriceTypeSelected.size() > 0) {
						for (CheckBoxProductPriceType ppt : listProductPriceTypeSelected) {
							productPrice = new ProductPrice();
							productPrice.setProduct(p);
							productPrice.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
							productPrice.setProductPriceType(ppt.getProductPriceType());
							productPrice.setUnit(((ProductUnit) cbUnitProduct.getSelectedItem()).getCode());
							productPrice.setPrice(MyDouble.parseDouble(ppt.getTextField().getText()));
							productPrice.setCurrencyUnit(ppt.getComboBox().getSelectedCurrency().getCode());
							ProductModelManager.getInstance().saveProductPrice(productPrice);
						}
					}
				}

				if (chbWarehousing.isSelected()) {
					try {
						saveWarehouse();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (!chbUnchangedInput.isSelected())
					reset();
				else
					clearText();

				// Cap nhat lai giao dien ban hang POS nếu screenOften1 tồn tại
				if (DialogScreenOftenPos.screenOften1 != null) {
					List<Product> ps = new ArrayList<Product>();
					ps.add(p);
					DialogScreenOftenPos.getInstance().loadGuiProduct(ps);
				}
			}
			if (reset == true) {
				((DialogMain) getRootPane().getParent()).dispose();
			}
		}

	}

	private void saveWarehouse() throws Exception {
		InvoiceDetail invoice = new InvoiceDetail(true);
		invoice.setInvoiceCode(DateUtil.asCompactDateTimeId(new Date()));
		invoice.setActivityType(ActivityType.Payment);
		invoice.setType(AccountingModelManager.typeSanXuat);
		invoice.setCurrencyUnit("VND");
		invoice.setStartDate(new Date());
		invoice.setDiscount(0);
		invoice.setInvoiceName("");

		invoice.setDepartmentCode(HRModelManager.getInstance().getDepartmentOther().getPath());
		Employee em = HRModelManager.getInstance().getBydLoginId(ManagerAuthenticate.getInstance().getLoginId());
		if (em != null) {
			invoice.setEmployeeCode(em.getCode());
		}
		invoice.setInvoiceName("Nhập kho sản phẩm " + product.getName());

		InvoiceItem invoiceItem = new InvoiceItem();
		invoiceItem.setItemName(product.getName());
		invoiceItem.setItemCode(DateUtil.asCompactDateTimeId(new Date()));

		invoiceItem.setQuantity(MyDouble.parseDouble(txtQuantityBegin.getText()));
		invoiceItem.setUnitPrice(MyDouble.parseDouble(txtUnitPriceRepository.getText()));
		invoiceItem.setTotal(invoiceItem.getQuantity() * invoiceItem.getUnitPrice());
		invoiceItem.setCurrencyUnit("VND");
		invoiceItem.setStatus(AccountingModelManager.isRecord);

		invoiceItem.setStartDate(new Date());
		invoiceItem.setActivityType(InvoiceItem.ActivityType.Payment);
		invoiceItem.setProductCode(product.getCode());
		invoiceItem.setStatus(AccountingModelManager.isPayment);
		invoice.add(invoiceItem);

		invoice.setStatus(Status.Paid);
		// transaction
		invoice = invoice.calculate(new DefaultInvoiceCalculator());
		if (invoice.getFinalCharge() != invoice.getTotalPaid()) {
			InvoiceTransaction transaction = new InvoiceTransaction();
			transaction.setTransactionType(TransactionType.Cash);
			transaction.setCreatedBy(ManagerAuthenticate.getInstance().getLoginId());
			transaction.setCurrencyUnit("VND");
			transaction.setTotal(invoice.getFinalCharge() - invoice.getTotalPaid());
			transaction.setTransactionDate(new Date());
			invoice.add(transaction);
			invoice = invoice.calculate(new DefaultInvoiceCalculator());
		}

		invoice.setCreatedBy(AccountModelManager.getInstance().getNameByLoginId(
		    ManagerAuthenticate.getInstance().getLoginId()));
		invoice = AccountingModelManager.getInstance().saveInvoiceDetail(invoice);

		List<InvoiceItemAttribute> attributes1 = invoiceItem.getReferences();
		String productCode = product.getCode();
		String provider = "";
		Date dateMenufacture = null;
		Date dateExpiry = null;
		double quantity = invoiceItem.getQuantity() - invoiceItem.getQuantityReal();
		for (InvoiceItemAttribute invoiceItemAttribute : attributes1) {
			if (invoiceItemAttribute.getName().equals(WarehouseModelManager.PROVIDER)) {
				provider = invoiceItemAttribute.getValue();
			}
			if (invoiceItemAttribute.getName().equals("dateMenufacture")) {
				dateMenufacture = new SimpleDateFormat("dd/MM/yyyy").parse(invoiceItemAttribute.getValue());
			}
			if (invoiceItemAttribute.getName().equals("dateExpiry")) {
				dateExpiry = new SimpleDateFormat("dd/MM/yyyy").parse(invoiceItemAttribute.getValue());
			}
		}
		Date shipmentImportCode = new Date();
		invoiceItem = invoice.getItems().get(0);
		if (quantity > 0) {
			Warehouse wH = (Warehouse) cbRepository.getSelectedItem();

			for (int j = 0; j < quantity; j++) {
				IdentityProduct identityProduct = new IdentityProduct();
				identityProduct.setInvoiceCode(invoice.getInvoiceCode());
				identityProduct.setInvoiceItemIdImport(invoiceItem.getId());
				if (wH != null) {
					identityProduct.setWarehouseCode(wH.getWarehouseId());
				} else {
					identityProduct.setWarehouseCode(null);
				}

				identityProduct.setProductCode(productCode);
				identityProduct.setProvider(provider);
				identityProduct.setDateExpiry(dateExpiry);
				identityProduct.setDateMenufacture(dateMenufacture);
				identityProduct.setPrice(invoiceItem.getUnitPrice());
				List<IdentityProductAttribute> productAttributes = new ArrayList<IdentityProductAttribute>();

				if (wH != null) {
					IdentityProductAttribute productAttribute = new IdentityProductAttribute();
					productAttribute.setName(WarehouseModelManager.WAREHOUSE);
					productAttribute.setValue(((Warehouse) cbRepository.getSelectedItem()).getWarehouseId());
					productAttribute.setStartDate(shipmentImportCode);
					productAttributes.add(productAttribute);
					identityProduct.setIdentityProductAttributes(productAttributes);
				}

				SimpleDateFormat fomatCode = new SimpleDateFormat("ddMMyyyyHHmmssSSS");
				identityProduct.setShipmentImportCode(fomatCode.format(shipmentImportCode));
				identityProduct.setImportDate(shipmentImportCode);
				identityProduct.setImportType(ImportType.Import);
				identityProduct.setExportType(ExportType.NotExport);

				identityProduct = WarehouseModelManager.getInstance().saveIdentityProduct(identityProduct);
				double quantityUpdate = invoiceItem.getQuantityReal() + 1;
				invoiceItem.setQuantityReal(quantityUpdate);
			}

		}

		invoice.setEndDate(new Date());
		AccountingModelManager.getInstance().saveInvoiceDetail(invoice);
	}

	// Hàm chỉnh sửa cho nút [Sửa]
	@Override
	public void edit() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này!");
			return;
		}
		if (PromotionModelManager.getInstance().getMenuByCode(product.getCode()) != null) {
			List<Component> c = getAllComponents(container1);
			for (Component com : c) {
				com.setEnabled(true);
			}
			//cbUnitProduct.setEnabled(true);
		} else {
			reset = true;
			setEnabledCompoment(true);
			((DialogMain) getRootPane().getParent()).showButton(true);
		}

	}

	public boolean isRestore() {
		return restore;
	}

	// Hàm xóa cho nút [Xóa]
	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {
			try {
				String str = "Xóa hàng hóa ";
				PanelChoise panel = new PanelChoise(str + product.getName() + " ?");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				dialog.setTitle("Xóa hàng hóa");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					Recipe recipe = RestaurantModelManager.getInstance().getRecipeByProductCode(product.getCode());
					if (recipe != null && !recipe.isRecycleBin()) {
						PanelChoise panel1 = new PanelChoise("Sản phẩm có định lượng ko thể xóa!");
						DialogResto dialog1 = new DialogResto(panel1, false, 0, 80);
						dialog1.setName("dlXoa");
						dialog1.setTitle("Xóa hàng hóa");
						dialog1.setLocationRelativeTo(null);
						dialog1.setModal(true);
						dialog1.setVisible(true);
						return;
					}
					product.setPersistableState(AbstractPersistable.State.DELETED);
					ProductModelManager.getInstance().deleteProductByCode(product.getCode());
					((DialogMain) getRootPane().getParent()).dispose();
				}
				// Cap nhat lai giao dien ban hang POS
				if (DialogScreenOftenPos.screenOften1 != null) {
					List<Product> ps = new ArrayList<Product>();
					ps.add(product);
					DialogScreenOftenPos.getInstance().loadGuiProduct(ps);
				}
			} catch (Exception ex) {
			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return;
		}
	}

	// Hàm làm mới các ô nhập cho nút [Viết lại]
	@Override
	public void reset() throws Exception {
		txtName.setText("");
		txtDescription.setText("0");
		txtNameOther.setText("");
		lblName.setForeground(new Color(51, 51, 51));
		lblCode.setForeground(new Color(51, 51, 51));
		lbAvatar.setIcon(null);
		lbAvatar.setText("Chọn ảnh");
		txtCode.setEditable(true);
		txtBarCode.setEditable(true);
		chbWarehousing.setSelected(false);

		txtQuantityBegin.setText("");
		txtUnitPriceRepository.setText("");
		lblMessager.setText("");
		try {
			cbUnitProduct.setSelectedIndex(0);
		} catch (Exception ex) {
			cbUnitProduct.setModel(new DefaultComboBoxModel<ProductUnit>());
		}
		for (Component c : components) {
			if (c instanceof JCheckBox) {
				((JCheckBox) c).setSelected(false);
			}
			if (c instanceof JTextField) {
				((JTextField) c).setText("");
			}
			if (c instanceof UnitMoneyJComboBox) {
				try {
					((UnitMoneyJComboBox) c).setSelectedIndex(0);
				} catch (Exception ex) {
					((UnitMoneyJComboBox) c).setSelectedItem("");
					ex.printStackTrace();
				}
			}
		}
		chbSetCalculateReport.setSelected(true);
		edit = false;
		product = new Product();
		listProductPriceTypeSelected.clear();
		listProductTagSelected.clear();
		listProductPrice = new ArrayList<ProductPrice>();
		setEnabledCompoment(true);
		chbWarehousing.setEnabled(true);
		loadCode();
	}

	private void clearText() {
		txtName.setText("");
		txtNameOther.setText("");
		lblName.setForeground(new Color(51, 51, 51));
		lblCode.setForeground(new Color(51, 51, 51));
		txtCode.setEditable(true);
		txtBarCode.setEditable(true);
		lblMessager.setText("");
		setEnabledCompoment(true);
		product = new Product();
		loadCode();
	}

	// Phương thức thiết lập cho component có khả năng chỉnh sửa hay không?
	private void setEnabledCompoment(boolean value) {
		txtName.setEnabled(value);
		txtDescription.setEnabled(value);
		chbPrice.setEnabled(value);
		txtNameOther.setEnabled(value);
		cbUnitProduct.setEnabled(value);
		txtQuantityBegin.setEnabled(value);
		txtUnitPriceRepository.setEnabled(value);
		cbRepository.setEnabled(value);
		txtEndDate.setEnabled(value);
		chbWarehousing.setEnabled(false);
		for (Component c : components) {
			if (c instanceof JCheckBox || c instanceof JTextField || c instanceof JComboBox) {
				c.setEnabled(value);
			}
		}

	}

	// Hàm xem lại cho nút [Xem lại]
	@Override
	public void refresh() throws Exception {
		setData();
		int i = 0;
		listProductPriceTypeSelected = new ArrayList<CheckBoxProductPriceType>();
		for (CheckBoxProductPriceType chb : listCheckBox) {
			boolean check = false;
			for (ProductPrice p : listProductPrice) {
				if (p.getProductPriceType().getId().equals(getProductPriceTypes().get(i).getId())) {
					chb.setSelected(true);
					chb.getComboBox().setSelectedItem(p.getCurrencyUnit());
					chb.getTextField().setText(Double.toString(p.getPrice()));
					listProductPriceTypeSelected.add(chb);
					check = true;
				}
			}
			if (!check) {
				chb.setSelected(false);
				chb.getTextField().setText("");
			}
			i++;
		}

		i = 0;
		listProductTagSelected.clear();
		for (CheckBoxProductTag chb : listCheckBoxProductTag) {
			boolean check = false;
			if (product != null && product.getProductTags() != null) {
				for (ProductTag p : product.getProductTags()) {
					if (p.getId().equals(getProductTags().get(i).getId())) {
						chb.setSelected(true);
						listProductTagSelected.add(p);
						check = true;
					}
				}
			}

			if (!check) {
				chb.setSelected(false);
			}
			i++;
		}
		setEnabledCompoment(false);
	}

	// Tạo dữ liệu mẫu liên quan
	@Override
	public void createBeginTest() {
		if (MyPanel.isTest) {

			// Tạo nhóm hàng hóa
			List<ProductTag> productTags = new ArrayList<ProductTag>();
			for (int i = 1; i <= 3; i++) {
				// Nhóm cha 1
				ProductTag productTag1 = new ProductTag();
				productTag1.setCode("Mã NSP HktTest" + i);
				productTag1.setLabel("Nhóm SP HktTest" + i);
				productTag1.setTag("Mã NSP HktTest" + i);
				try {
					productTag1 = ProductModelManager.getInstance().saveProductTag(productTag1);
					productTags.add(productTag1);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				// Tạo đơn vị sản phẩm
				ProductUnit productUnit = new ProductUnit();
				productUnit.setCode("Mã ĐVSP HktTest" + i);
				productUnit.setName("ĐVSP HktTest" + i);
				productUnit.setRate(20);
				try {
					LocaleModelManager.getInstance().saveProductUnit(productUnit);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// Tạo đơn vị tiền tệ
				Currency currency = new Currency();
				currency.setCode("Mã TT HktTest" + i);
				currency.setName("TT HktTest" + i);
				try {
					LocaleModelManager.getInstance().saveCurrency(currency);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// Tạo hàng hóa
			for (int i = 1; i <= 3; i++) {
				try {
					Product product = new Product();
					product.setName("Sản phẩm HktTest" + i);
					product.setCode("SP HktTest" + i);
					product.setCreatedTime(new Date());
					product.setNameOther("Ngôn ngữ HktTest" + i);
					product.setProductTags(productTags);
					product.setUnit("Mã ĐVSP HktTest");
					product.setMaker(ManagerAuthenticate.getInstance().getOrganizationLoginId());
					product = ProductModelManager.getInstance().saveProduct(product);

					// Tạo bảng giá sản phẩm
					ProductPriceType priceType = new ProductPriceType();
					priceType.setType("BG HktTest" + i);
					priceType.setName("BG HktTest" + i);
					priceType.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
					priceType = ProductModelManager.getInstance().saveProductPriceType(priceType);

					// Tạo giá sản phẩm
					ProductPrice productPrice = new ProductPrice();
					productPrice.setProduct(product);
					productPrice.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
					productPrice.setProductPriceType(priceType);
					productPrice.setUnit("Mã ĐVSP HktTest");
					productPrice.setPrice(i * 111000);
					productPrice.setCurrencyUnit("Mã TT HktTest" + i);
					ProductModelManager.getInstance().saveProductPrice(productPrice);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		super.createBeginTest();
	}

	// Tạo dữ liệu tự động test phân trang
	@Override
	public void createDataTest() {
		for (int i = 4; i <= 50; i++) {
			try {
				product = new Product();
				product.setName("San Pham HktTest" + i);
				product.setCode("Mã SP HktTest" + i);
				product.setCreatedTime(new Date());
				product.setNameOther("Ngôn ngữ HktTest" + i);
				product.setProductTags(new ArrayList<ProductTag>());
				product.setUnit("ĐVSP HktTest1");
				product.setMaker(ManagerAuthenticate.getInstance().getOrganizationLoginId());
				ProductModelManager.getInstance().saveProduct(product);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteDataTest() {
		// if (!PanelTestAll.runAll) {
		// DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
		// if (DialogTest.getInstance().isTest()) {
		try {
			ProductModelManager.getInstance().deleteTest("HktTest");
			LocaleModelManager.getInstance().deleteTest("HktTest");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// }
		// }
	}

}
