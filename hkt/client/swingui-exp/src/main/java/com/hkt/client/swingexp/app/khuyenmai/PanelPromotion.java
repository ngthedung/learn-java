package com.hkt.client.swingexp.app.khuyenmai;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.ExtendJCheckBox;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJRadioButton;
import com.hkt.client.swingexp.app.component.ExtendJTextArea;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.AreaJComboBox;
import com.hkt.client.swingexp.app.core.CustomerGroupJComboBox;
import com.hkt.client.swingexp.app.core.CustomerJComboBox;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialogPlus;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.core.ProductJComboBox;
import com.hkt.client.swingexp.app.core.ProductTagJComboBox;
import com.hkt.client.swingexp.app.core.TableJComboBox;
import com.hkt.client.swingexp.app.core.UnitMoneyJComboBox;
import com.hkt.client.swingexp.app.hethong.PanelOption;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.hethong.TimeOptions;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.GenericOptionModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.config.generic.Option;
import com.hkt.module.config.locale.Currency;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.promotion.entity.CustomerTarget;
import com.hkt.module.promotion.entity.CustomerTarget.CustomerTargetType;
import com.hkt.module.promotion.entity.LocationTarget;
import com.hkt.module.promotion.entity.LocationTarget.LocationTargetType;
import com.hkt.module.promotion.entity.ProductTarget;
import com.hkt.module.promotion.entity.ProductTarget.ProductTargetType;
import com.hkt.module.promotion.entity.Promotion.Status;
import com.hkt.module.promotion.entity.PromotionDetail;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.util.text.DateUtil;

@SuppressWarnings("serial")
public class PanelPromotion extends MyPanel implements IDialogMain {

  public static String permission;
  private PromotionDetail promotion;
  private DateFormat dfTime = new SimpleDateFormat("HH:mm");

  private JPanel container1, container2;
  private JPanel margin1, margin2;
  private ButtonGroup buttonGroup1;
  private AreaJComboBox cboArea;
  private CustomerGroupJComboBox cboGroupPartner;
  private ProductTagJComboBox cboGroupProduct;
  private ExtendJComboBox cboTypeStore;
  private ExtendJComboBox cboStore;
  private CustomerJComboBox cboPartner;
  private ProductJComboBox cboProduct;
  private TableJComboBox cboTable;
  private UnitMoneyJComboBox cboUnitMoney;
  private UnitMoneyJComboBox cboUnitMoney1;
  private UnitMoneyJComboBox cboUnitMoney2;
  private ExtendJCheckBox chbArea;
  private ExtendJCheckBox chbGroupPartner;
  private ExtendJCheckBox chbGroupProduct;
  private ExtendJCheckBox chbPartner;
  private ExtendJCheckBox chbProduct;
  private ExtendJCheckBox chbTable;
  private ExtendJCheckBox chbCumulative;
  private ExtendJCheckBox chbTypeStore;
  private ExtendJCheckBox chbStore;
  private MyJDateChooser dcEndDate;
  private MyJDateChooser dcStartDate;
  private ExtendJLabel jLabel1;
  private ExtendJLabel jLabel10;
  private ExtendJLabel jLabel2;
  private ExtendJLabel jLabel3;
  private ExtendJLabel jLabel4;
  private ExtendJLabel jLabel5;
  private ExtendJLabel jLabel6;
  private ExtendJButton btnSetupTime;
  private ExtendJLabel jLabel8;
  private ExtendJLabel jLabel9;
  private ExtendJLabel lbMessager;
  private ExtendJRadioButton rbtMoney;
  private ExtendJRadioButton rbtDiscount;
  private JScrollPane scrollPaneArea;
  private ExtendJTextArea txtArea;
  private ExtendJTextField txtHEnd;
  private ExtendJTextField txtHStart;
  private ExtendJTextField txtMoney;
  private ExtendJTextField txtName;
  private ExtendJTextField txtDiscount;
  private ExtendJTextField txtQuantity;
  private ExtendJTextField txtValueMax;
  private ExtendJTextField txtValueMin;
  protected long timeOptionId = 0;
  private boolean reset = false;

  // Hàm khởi tạo
  @SuppressWarnings({ "unchecked", "rawtypes" })
public PanelPromotion() {
    initComponents();
    try {
      reset();
    } catch (Exception e) {
    	e.printStackTrace();
    }
    // Kiểm tra khu vực có checkBox hay không? Nếu có thì disable comboBox
    // bàn
    chbArea.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (chbArea.isSelected()) {
          try {
            cboArea.getSelectedLocation().getId();
            lbMessager.setText("");
          } catch (Exception ex) {
            lbMessager.setText("Danh sách bạn chọn đang rỗng");
            chbArea.setSelected(false);
            return;
          }
          chbTable.setEnabled(false);
          cboTable.setEnabled(false);
          chbTable.setSelected(false);
        } else {
          chbTable.setEnabled(true);
          cboTable.setEnabled(true);
        }
      }
    });

    // Kiểm tra nhóm đối tác có checkBox hay không? Nếu có thì disable comboBox
    // đối tác
    chbGroupPartner.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (chbGroupPartner.isSelected()) {
          try {
            cboGroupPartner.getSelectedGroupCustomer().getId();
            lbMessager.setText("");
          } catch (Exception ex) {
            lbMessager.setText("Danh sách bạn chọn đang rỗng");
            chbGroupPartner.setSelected(false);
            return;
          }
          chbPartner.setEnabled(false);
          cboPartner.setEnabled(false);
          chbPartner.setSelected(false);
        } else {
          chbPartner.setEnabled(true);
          cboPartner.setEnabled(true);
        }
      }
    });

    // Kiểm tra nhóm sản phẩm có checkBox hay không? Nếu có thì disable
    // comboBox sản phẩm
    chbGroupProduct.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (chbGroupProduct.isSelected()) {
          try {
            cboGroupProduct.getSelectedProductTag().getId();
            lbMessager.setText("");
          } catch (Exception ex) {
            lbMessager.setText("Danh sách bạn chọn đang rỗng");
            chbGroupProduct.setSelected(false);
            return;
          }
          chbProduct.setEnabled(false);
          cboProduct.setEnabled(false);
          chbProduct.setSelected(false);
        } else {
          chbProduct.setEnabled(true);
          cboProduct.setEnabled(true);
        }
      }
    });

    chbPartner.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        try {
          cboPartner.getSelectedCustomer().getId();
          lbMessager.setText("");
        } catch (Exception ex) {
          lbMessager.setText("Danh sách bạn chọn đang rỗng");
          chbPartner.setSelected(false);
        }
      }
    });

    chbProduct.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        try {
          cboProduct.getSelectedProduct().getId();
          lbMessager.setText("");
        } catch (Exception ex) {
          lbMessager.setText("Danh sách bạn chọn đang rỗng");
          chbProduct.setSelected(false);
        }
      }
    });

    chbTable.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        try {
          cboTable.getSelectedTable().getId();
          lbMessager.setText("");
        } catch (Exception ex) {
          lbMessager.setText("Danh sách bạn chọn đang rỗng");
          chbTable.setSelected(false);
        }
      }
    });

    // Kiểm tra định dạng ngày giờ đổi về đúng dạng
    txtHEnd.addCaretListener(new CaretListener() {
      @Override
      public void caretUpdate(CaretEvent e) {
        try {
          dfTime.parse(txtHEnd.getText());
          txtHEnd.setForeground(Color.BLACK);
        } catch (ParseException e1) {
          txtHEnd.setForeground(Color.red);
        }
      }
    });

    // Kiểm tra định dạng ngày giờ đổi về đúng dạng
    txtHStart.addCaretListener(new CaretListener() {
      @Override
      public void caretUpdate(CaretEvent e) {
        try {
          dfTime.parse(txtHStart.getText());
          txtHStart.setForeground(Color.BLACK);
        } catch (ParseException e1) {
          txtHStart.setForeground(Color.red);
        }
      }
    });

    // Nếu chọn KM tiền thì disable textField %KM hoặc ngược lại
    rbtMoney.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (rbtMoney.isSelected()) {
          txtDiscount.setText("");
          txtDiscount.setEnabled(false);
          txtMoney.setEnabled(true);
          cboUnitMoney.setEnabled(true);
          rbtDiscount.setForeground(new Color(51, 51, 51));
          lbMessager.setText("");
        } else {
          txtMoney.setText("");
          txtMoney.setEnabled(false);
          cboUnitMoney.setEnabled(false);
          txtDiscount.setEnabled(true);
          rbtMoney.setForeground(new Color(51, 51, 51));
          lbMessager.setText("");
        }
      }
    });

    // Chọn nhóm sản phẩm cha (cboGroupProduct) thì hiện các sản phẩm con trên
    // (cboProduct)
    cboGroupProduct.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (!chbGroupProduct.isSelected()) {
          try {
            List<Product> listProducts = ProductModelManager.getInstance().findByProductTag(
                cboGroupProduct.getSelectedProductTag().getTag());
            cboProduct.setData(listProducts);
          } catch (Exception e1) {
            e1.printStackTrace();
          }
        }
      }
    });

    // Chọn nhóm đối tác cha (cboGroupPartner) thì hiện các đối tác con
    // trong nhóm đó trên (cboPartner)
    cboGroupPartner.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (!chbGroupPartner.isSelected()) {
          try {
            List<AccountMembership> listMembership = AccountModelManager.getInstance().findMembershipByGroupPath(
                cboGroupPartner.getSelectedGroupCustomer().getPath());
            List<Customer> listCus = new ArrayList<Customer>();
            for (AccountMembership accMem : listMembership) {
            	Customer c = CustomerModelManager.getInstance().getBydLoginId(accMem.getLoginId());
							if (c != null) {
								listCus.add(c);
							}
            }
            cboPartner.setData(listCus);
          } catch (Exception e1) {
            e1.printStackTrace();
          }
        }
      }
    });

    // Chọn khu vực (cboArea) thì hiện các bàn trong khu vực đó trên
    // (cboTable)
    cboArea.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (!chbArea.isSelected()) {
          try {
            List<Table> listArea = RestaurantModelManager.getInstance().findTableByLocation(
                cboArea.getSelectedLocation().getCode());
            cboTable.setData(listArea);
          } catch (Exception e1) {
            e1.printStackTrace();
          }
        }
      }
    });

    // Hiện form cài đặt chi tiết
    btnSetupTime.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          TimeOptions options = new TimeOptions(timeOptionId);
          DialogMain dialogMain = new DialogMain(options, 250, 600);
          dialogMain.setTitle("Thiết lập");
          dialogMain.setModal(true);
          dialogMain.setLocationRelativeTo(null);
          dialogMain.setVisible(true);
          if (options.getTimeOptionId() != 0) {
            timeOptionId = options.getTimeOptionId();
          }
        } catch (Exception e2) {
          e2.printStackTrace();
        }
      }
    });

    // Kiểm tra định dạng số khi nhập
    txtMoney.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
          try {
            Double.parseDouble(txtMoney.getText());
            rbtMoney.setForeground(new Color(51, 51, 51));
            txtMoney.setForeground(Color.black);
            lbMessager.setText("");
          } catch (Exception ex) {
            txtMoney.setForeground(Color.red);
            lbMessager.setText("Vui lòng nhập giá trị số");
          }
        }
      }
    });

    // Kiểm tra định dạng số khi nhập
    txtDiscount.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
          rbtDiscount.setSelected(true);
          txtMoney.setText("");
          txtMoney.setEnabled(false);
          cboUnitMoney.setEnabled(false);
          rbtMoney.setForeground(new Color(51, 51, 51));
          try {
            Double.parseDouble(txtDiscount.getText());
            rbtDiscount.setForeground(new Color(51, 51, 51));
            txtDiscount.setForeground(Color.black);
            lbMessager.setText("");
          } catch (Exception ex) {
            txtDiscount.setForeground(Color.red);
            lbMessager.setText("Vui lòng nhập giá trị số");
          }
        }

      }
    });
    try {
      cboStore.setModel(new DefaultComboBoxModel(GenericOptionModelManager.getInstance().
          getOptionGroup("restaurant", "RestaurantService", "store").getOptions().toArray()));
    } catch (Exception e) {
    }
   
    //Kiểm tra khuyến mại cửa hàng nếu chọn tất cả thì enabled
    
    //combobox loại nhà hàng
    cboStore.addMouseListener(new MouseAdapter() {
    	@SuppressWarnings("unused")
		public void mouseClicked(MouseEvent e) {
    		if(e.getButton() == MouseEvent.BUTTON3)
    		{
				try {
					PanelOption pnOption = new PanelOption("restaurant", "RestaurantService", "store");
					DialogMain dialog = new DialogMain(new PanelOption("restaurant", "RestaurantService", "store"));
					dialog.setIcon("doanhnghiep1.png");
					dialog.setTitle("Quản lý của hàng");
					dialog.setModal(true);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
					 try {
				      cboStore.setModel(new DefaultComboBoxModel(GenericOptionModelManager.getInstance().
				          getOptionGroup("restaurant", "RestaurantService", "store").getOptions().toArray()));
				    } catch (Exception e1) {
				    }
				} catch (Exception e1) {
					e1.printStackTrace();
				}
    		}}
    		});
    chbTypeStore.addItemListener(new ItemListener() {	
		@Override
		public void itemStateChanged(ItemEvent e) {	
		    cboTypeStore.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(cboTypeStore.getSelectedIndex()==0){
					chbStore.setEnabled(false);chbStore.setEnabled(false);
					cboStore.setEnabled(false);
					cboStore.setEnabled(false);
				}else {
					chbStore.setEnabled(true);
					cboStore.setEnabled(true);
				}	
			}
		});
			
		}
	});


  }

  // Vẽ components tạo giao diện
  @SuppressWarnings("unchecked")
private void initComponents() {
    createBeginTest();
    // Khởi tạo các components
    setOpaque(false);
    container1 = new JPanel();
    container2 = new JPanel();
    margin1 = new JPanel();
    margin2 = new JPanel();
    scrollPaneArea = new JScrollPane();

    buttonGroup1 = new ButtonGroup();
    chbGroupProduct = new ExtendJCheckBox("Nhóm SP");
    chbGroupPartner = new ExtendJCheckBox("Nhóm ĐT");
    chbGroupPartner.setName("chbGroupPartner");
    chbProduct = new ExtendJCheckBox("Sản phẩm");
    chbPartner = new ExtendJCheckBox("Đối tác");
    chbArea = new ExtendJCheckBox("Khu vực");
    chbTable = new ExtendJCheckBox("Bàn/Quầy");
    chbCumulative = new ExtendJCheckBox("Cộng dồn");
    chbTypeStore = new ExtendJCheckBox("Loại");
    chbTypeStore.setName("chbTypeStore");
    chbStore = new ExtendJCheckBox("Cửa hàng");
    cboGroupProduct = new ProductTagJComboBox();
    cboGroupPartner = new CustomerGroupJComboBox();
    cboProduct = new ProductJComboBox();
    cboPartner = new CustomerJComboBox();
    cboArea = new AreaJComboBox();
    
    cboTable = new TableJComboBox();
    
    cboUnitMoney = new UnitMoneyJComboBox();
    cboUnitMoney1 = new UnitMoneyJComboBox();
    cboUnitMoney2 = new UnitMoneyJComboBox();
    cboTypeStore = new ExtendJComboBox();
    cboTypeStore.addItem("Tất cả");
    cboTypeStore.addItem("Cửa hàng lẻ");
    cboStore = new ExtendJComboBox();
    
    chbStore.setEnabled(false);
	cboStore.setEnabled(false);
    
    cboGroupProduct.removeIndex(0);
    cboGroupPartner.removeIndex(0);
    cboProduct.removeIndex(0);
    cboPartner.removeIndex(0);
    cboArea.removeIndex(0);
    cboTable.removeIndex(0);
    cboArea.dataMarket();
    cboTable.dataMarket();
    jLabel1 = new ExtendJLabel("Tên KM");
    jLabel2 = new ExtendJLabel("Lần SD");
    jLabel3 = new ExtendJLabel("Từ ngày");
    jLabel4 = new ExtendJLabel("Đến ngày");
    jLabel5 = new ExtendJLabel("TG từ");
    jLabel6 = new ExtendJLabel("Đến");
    jLabel8 = new ExtendJLabel("AD từ");
    jLabel9 = new ExtendJLabel("Đến");
    jLabel10 = new ExtendJLabel("Ghi chú");
    lbMessager = new ExtendJLabel("");
    lbMessager.setForeground(Color.red);

    txtName = new ExtendJTextField();
    txtName.setText(DateUtil.asCompactDateTimeId(new Date()));
    txtQuantity = new ExtendJTextField();
    txtHStart = new ExtendJTextField();
    txtHEnd = new ExtendJTextField();
    txtMoney = new ExtendJTextField();
    txtDiscount = new ExtendJTextField();
    txtValueMin = new ExtendJTextField();
    txtValueMax = new ExtendJTextField();

    txtArea = new ExtendJTextArea();
    scrollPaneArea.setViewportView(txtArea);
    btnSetupTime = new ExtendJButton("Thiết lập");
    btnSetupTime.setPreferredSize(new Dimension(95, 22));
    btnSetupTime.addMouseListener(new MouseEventClickButtonDialogPlus(null,null, null));
    dcStartDate = new MyJDateChooser();
    dcEndDate = new MyJDateChooser();
    dcStartDate.setDateFormatString("dd/MM/yyyy");
    dcEndDate.setDateFormatString("dd/MM/yyyy");

    rbtMoney = new ExtendJRadioButton("KM tiền");
    rbtDiscount = new ExtendJRadioButton("KM %");
    buttonGroup1.add(rbtMoney);
    buttonGroup1.add(rbtDiscount);
    rbtDiscount.setSelected(true);

    // Set trong suốt
    container1.setOpaque(false);
    container2.setOpaque(false);
    margin1.setOpaque(false);
    margin2.setOpaque(false);
    chbGroupProduct.setOpaque(false);
    chbGroupPartner.setOpaque(false);
    chbProduct.setOpaque(false);
    chbPartner.setOpaque(false);
    chbArea.setOpaque(false);
    chbTable.setOpaque(false);
    rbtMoney.setOpaque(false);
    rbtDiscount.setOpaque(false);

    // Set name dùng cho hàm test
    chbCumulative.setName("chbCongDon");
    chbGroupProduct.setName("chkNhomSanPham");
    cboGroupProduct.setName("cbNhomSanPham");
    chbProduct.setName("chbSanPham");
    cboProduct.setName("cboSanPham");
    chbPartner.setName("chkDoiTac");
    cboPartner.setName("cbDoiTac");
    chbArea.setName("ckKhuVuc");
    cboArea.setName("cbKhuVuc");
    chbTable.setName("chbBan");
    cboTable.setName("cboBan");
    txtName.setName("txtTenKM");
    txtQuantity.setName("txtSoLanSuDung");
    txtHStart.setName("txtThoiGianBatDau");
    txtHEnd.setName("txtThoiGianKetThuc");
    txtMoney.setName("txtKMTien");
    txtDiscount.setName("txtKMPhanTram");
    rbtMoney.setName("rdKMTien");
    rbtDiscount.setName("rdKMPhanTram");
    cboUnitMoney.setName("cbDonViTien");
    txtValueMin.setName("txtApDungTu");
    txtValueMax.setName("txtApDungDen");
    cboUnitMoney1.setName("cbDonViTien1");
    cboUnitMoney2.setName("cbDonViTien2");
    txtArea.setName("txtGhiChu");
    txtArea.setRows(5);

    // Set căn phải
    txtDiscount.setHorizontalAlignment(JTextField.RIGHT);
    txtQuantity.setHorizontalAlignment(JTextField.RIGHT);
    txtValueMax.setHorizontalAlignment(JTextField.RIGHT);
    txtValueMin.setHorizontalAlignment(JTextField.RIGHT);
    txtMoney.setHorizontalAlignment(JTextField.RIGHT);
    ((JTextField) dcStartDate.getDateEditor()).setHorizontalAlignment(JTextField.RIGHT);
    ((JTextField) dcEndDate.getDateEditor()).setHorizontalAlignment(JTextField.RIGHT);

    /*
     * Panel giao diện phần 1
     */

    container1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, null,
        new java.awt.Color(204, 204, 204), null, null), "Lựa chọn KM", TitledBorder.DEFAULT_JUSTIFICATION,
        TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(104, 0, 0))); // NOI18N

    MyGroupLayout layout1 = new MyGroupLayout(margin1);

    layout1.add(0, 0, chbTypeStore);
    layout1.add(0, 1, cboTypeStore);
    layout1.add(0, 2, chbStore);
    layout1.add(0, 3, cboStore);
    
    layout1.add(1, 0, chbGroupProduct);
    layout1.add(1, 1, cboGroupProduct);
    layout1.add(1, 2, chbProduct);
    layout1.add(1, 3, cboProduct);
    layout1.add(2, 0, chbGroupPartner);
    layout1.add(2, 1, cboGroupPartner);
    layout1.add(2, 2, chbPartner);
    layout1.add(2, 3, cboPartner);
    layout1.add(3, 0, chbArea);
    layout1.add(3, 1, cboArea);
    layout1.add(3, 2, chbTable);
    layout1.add(3, 3, cboTable);
    
    layout1.updateGui();
    margin1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    container1.setLayout(new BorderLayout(0, 0));
    container1.add(margin1, BorderLayout.CENTER);

    /*
     * Panel giao diện phần 2
     */
    container2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, null,
        new java.awt.Color(204, 204, 204), null, null), "Chi tiết KM", TitledBorder.DEFAULT_JUSTIFICATION,
        TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(104, 0, 0))); // NOI18N

    PanelContainer panel1 = new PanelContainer(txtMoney, cboUnitMoney);
    PanelContainer panel2 = new PanelContainer(txtDiscount, chbCumulative);
    PanelContainer panel3 = new PanelContainer(txtValueMin, cboUnitMoney1);
    PanelContainer panel4 = new PanelContainer(txtValueMax, cboUnitMoney2);
    PanelContainer panel5 = new PanelContainer(txtHEnd, btnSetupTime);

    MyGroupLayout layout2 = new MyGroupLayout(margin2);
    layout2.add(0, 0, jLabel1);
    layout2.add(0, 1, txtName);
    layout2.add(0, 2, jLabel2);
    layout2.add(0, 3, txtQuantity);
    layout2.add(1, 0, jLabel3);
    layout2.add(1, 1, dcStartDate);
    layout2.add(1, 2, jLabel4);
    layout2.add(1, 3, dcEndDate);
    layout2.add(2, 0, jLabel5);
    layout2.add(2, 1, txtHStart);
    layout2.add(2, 2, jLabel6);
    layout2.add(2, 3, panel5);
    layout2.add(3, 0, rbtMoney);
    layout2.add(3, 1, panel1);
    layout2.add(3, 2, rbtDiscount);
    layout2.add(3, 3, panel2);
    layout2.add(4, 0, jLabel8);
    layout2.add(4, 1, panel3);
    layout2.add(4, 2, jLabel9);
    layout2.add(4, 3, panel4);
    layout2.add(5, 0, jLabel10);
    layout2.add(5, 1, scrollPaneArea);

    layout2.updateGui();
    margin2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    container2.setLayout(new BorderLayout(0, 0));
    container2.add(margin2, BorderLayout.CENTER);

    GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(container2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(container1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(lbMessager, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
        layout.createSequentialGroup()
            .addComponent(container1, GroupLayout.DEFAULT_SIZE, 130, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(container2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(lbMessager, GroupLayout.DEFAULT_SIZE, 22, GroupLayout.PREFERRED_SIZE)));
  }

  // Panel thêm các component con
  protected class PanelContainer extends JPanel {
    public PanelContainer(Component comp1, Component comp2) {
      this.setLayout(new BorderLayout(0, 5));
      this.setOpaque(false);

      this.add(comp1, BorderLayout.CENTER);
      this.add(comp2, BorderLayout.LINE_END);
    }
  }

  // Phương thức cho nút [Lưu]
  @Override
  public void save() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này!");
      return;
    }
    if (!checkData())
      return;
    isEdit(true);
    LocationTarget locationTarget = null;
    CustomerTarget customerTarget = null;
    ProductTarget productTarget = null;
    if (promotion == null) {
      promotion = new PromotionDetail();
    } else {
      try {
        locationTarget = promotion.getLocationTargets().get(0);
      } catch (Exception e) {
        locationTarget = null;
      }
      try {
        customerTarget = promotion.getCustomerTargets().get(0);
      } catch (Exception e) {
        customerTarget = null;
      }
      try {
        productTarget = promotion.getProductTargets().get(0);
      } catch (Exception e) {
        productTarget = null;
      }
    }
    promotion.setTimeOptionId(timeOptionId);
    promotion.setName(txtName.getText());
    promotion.setCurrencyUnit(cboUnitMoney.getSelectedCurrency().getCode());
    promotion.setDescription(txtArea.getText());
    try {
      promotion.setDiscount(Double.parseDouble(txtMoney.getText()));
    } catch (Exception e) {
      promotion.setDiscount(0);
    }
    try {
      promotion.setDiscountRate(Double.parseDouble(txtDiscount.getText()));
    } catch (Exception e) {
      promotion.setDiscountRate(0);
    }

    if (chbCumulative.isSelected())
      promotion.setCumulative(true);
    else
      promotion.setCumulative(false);

    Calendar c = Calendar.getInstance();
    c.setTime(dcStartDate.getDate());
    if (txtHStart.getText().trim().length() == 5){
      c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(txtHStart.getText().substring(0, 2)));
      c.set(Calendar.MINUTE, Integer.parseInt(txtHStart.getText().substring(3)));
      c.set(Calendar.SECOND, Integer.parseInt(txtHStart.getText().substring(3)));
    } else if (txtHStart.getText().trim().length() == 4){
      c.set(Calendar.HOUR, Integer.parseInt(txtHStart.getText().substring(0, 1)));
      c.set(Calendar.MINUTE, Integer.parseInt(txtHStart.getText().substring(2)));
      c.set(Calendar.SECOND, Integer.parseInt(txtHStart.getText().substring(2)));
    }
    promotion.setFromDate(c.getTime());
    if (dcEndDate.getDate() != null) {
      Calendar c1 = Calendar.getInstance();
      c1.setTime(dcEndDate.getDate());
      if (txtHEnd.getText().trim().length() == 5)
        c1.set(Calendar.HOUR, Integer.parseInt(txtHEnd.getText().substring(0, 2)));
      else if (txtHEnd.getText().trim().length() == 4)
        c1.set(Calendar.HOUR, Integer.parseInt(txtHEnd.getText().substring(0, 1)));
      c1.set(Calendar.SECOND, Integer.parseInt(txtHEnd.getText().substring(3)));
      promotion.setToDate(c1.getTime());
    } else {
      promotion.setToDate(null);
    }

    try {
      promotion.setMaxUsePerCustomer(Integer.parseInt(txtQuantity.getText()));
    } catch (Exception e) {
      promotion.setMaxUsePerCustomer(Integer.MAX_VALUE);
    }
    try {
      if (txtValueMax.getText().trim().isEmpty()) {
        promotion.setMaxExpense(Double.MAX_VALUE);
      } else {
        promotion.setMaxExpense(Double.parseDouble(txtValueMax.getText()));
      }
    } catch (Exception e) {
      promotion.setMaxExpense(Double.MAX_VALUE);
    }
    try {
      promotion.setAppliedToMinExpense(Double.parseDouble(txtValueMin.getText()));
    } catch (Exception e) {
      promotion.setAppliedToMinExpense(0);
    }

    // Kiểm tra xem các checkBox được chọn
    if (chbGroupProduct.isSelected()) {
      if (productTarget == null) {
        productTarget = new ProductTarget();
        promotion.add(productTarget);
      }
      productTarget.setProductTargetType(ProductTargetType.ByProductTag);
      productTarget.setProductIdentifierId(cboGroupProduct.getSelectedProductTag().getTag());

    } else {
      if (chbProduct.isSelected()) {
        if (productTarget == null) {
          productTarget = new ProductTarget();
          promotion.add(productTarget);
        }
        productTarget.setProductIdentifierId(cboProduct.getSelectedProduct().getCode());
        productTarget.setProductTargetType(ProductTargetType.ByProduct);
      } else {
        if (productTarget != null) {
          promotion.getProductTargets().remove(0);
        }
      }
    }
    if (chbArea.isSelected()) {
      if (locationTarget == null) {
        locationTarget = new LocationTarget();
        promotion.add(locationTarget);
      }
      locationTarget.setLocationTargetType(LocationTargetType.ByArea);
      locationTarget.setLocation(cboArea.getSelectedLocation().getCode());
    } else {
      if (chbTable.isSelected()) {
        if (locationTarget == null) {
          locationTarget = new LocationTarget();
          promotion.add(locationTarget);
        }
        locationTarget.setLocationTargetType(LocationTargetType.ByTable);
        locationTarget.setLocation(cboTable.getSelectedTable().getCode());
      } else {
        if (locationTarget != null) {
          promotion.getLocationTargets().remove(0);
        }
      }
    }
    if (chbGroupPartner.isSelected()) {
      if (customerTarget == null) {
        customerTarget = new CustomerTarget();
        promotion.add(customerTarget);
      }
      customerTarget.setCustomerTargetType(CustomerTargetType.ByGroup);
      customerTarget.setCustomerGroup(cboGroupPartner.getSelectedGroupCustomer().getPath());
    } else {
      if (chbPartner.isSelected()) {
        if (customerTarget == null) {
          customerTarget = new CustomerTarget();
          promotion.add(customerTarget);
        }
        customerTarget.setCustomerTargetType(CustomerTargetType.ByCustomer);
        customerTarget.setCustomerGroup(cboPartner.getSelectedCustomer().getLoginId());
      } else {
        if (customerTarget != null) {
          promotion.getCustomerTargets().remove(0);
        }
      }
    }
    if(chbStore.isSelected()&&cboStore.isEnabled()&& cboStore.getSelectedItem()!=null){
      promotion.setStoreCode(((Option)cboStore.getSelectedItem()).getCode());
    }else {
      promotion.setStoreCode(null);
    }
    promotion.setStatus(Status.Active);
    promotion.setTypePromotion("KM Hóa Đơn");
    PromotionModelManager.getInstance().savePromotion(promotion);
    reset();
    if(reset == true)
    	((DialogMain) getRootPane().getParent()).dispose();
  }

  // Phương thức cho nút [Sửa]
  @Override
  public void edit() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này!");
      return;
    }
    reset = true;
    isEdit(true);
    if (promotion.getCustomerTargets().size() > 0) {
      CustomerTarget c = promotion.getCustomerTargets().get(0);
      if (c.getCustomerTargetType().equals(CustomerTargetType.ByGroup)) {
        chbPartner.setEnabled(false);
        cboPartner.setEnabled(false);
      }
    }
    if (promotion.getProductTargets().size() > 0) {
      ProductTarget c = promotion.getProductTargets().get(0);
      if (c.getProductTargetType().equals(ProductTargetType.ByProductTag)) {
        chbProduct.setEnabled(false);
        cboProduct.setEnabled(false);
      }
    }
    if (promotion.getLocationTargets().size() > 0) {
      LocationTarget c = promotion.getLocationTargets().get(0);
      if (c.getLocationTargetType().equals(LocationTargetType.ByArea)) {
        chbTable.setEnabled(false);
        cboTable.setEnabled(false);
      }
    }
    
  }

  // Phương thức cho nút [Xóa]
  @Override
  public void delete() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {

      String str = "Xóa Khuyến Mại ";
      PanelChoise pnPanel = new PanelChoise(str + promotion.getName() + "?");
      pnPanel.setName("Xoa");
      DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
      dialog1.setName("dlXoa");
      dialog1.setTitle("Xóa khuyến mại");
      dialog1.setLocationRelativeTo(null);
      dialog1.setModal(true);
      dialog1.setVisible(true);

      if (pnPanel.isDelete()) {
        PromotionModelManager.getInstance().deletePromotion(promotion);
        reset();
    	((DialogMain) getRootPane().getParent()).dispose();
      } else if (pnPanel.isDelete() == false) {
        reset();
      }
  
    } else {
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này!");
    }
  }

  // Phương thức cho nút [Viết lại]
  @Override
  public void reset() throws Exception {
    chbArea.setSelected(false);
    chbGroupPartner.setSelected(false);
    chbGroupProduct.setSelected(false);
    chbPartner.setSelected(false);
    chbProduct.setSelected(false);
    chbTable.setSelected(false);
    chbCumulative.setSelected(false);
    chbTypeStore.setSelected(false);
    chbStore.setSelected(false);
    txtHEnd.setText("23:59");
    txtHStart.setText("00:01");
    txtMoney.setText("");
    txtName.setText(DateUtil.asCompactDateTimeId(new Date()));
    txtDiscount.setText("");
    txtQuantity.setText("");
    txtValueMax.setText("");
    txtValueMin.setText("");
    txtArea.setText("");
    dcStartDate.setDate(new Date());
    dcEndDate.setDate(null);

    jLabel1.setForeground(new Color(51, 51, 51));
    jLabel3.setForeground(new Color(51, 51, 51));
    rbtDiscount.setForeground(new Color(51, 51, 51));
    rbtDiscount.setSelected(true);
    rbtMoney.setForeground(new Color(51, 51, 51));
    lbMessager.setText("");

    promotion = null;
    isEdit(true);
    
  }

  // Phương thức cho nút [Xem lại]
  @Override
  public void refresh() throws Exception {

    if (promotion != null) {
      timeOptionId = promotion.getTimeOptionId();
      txtName.setText(promotion.getName());
      txtQuantity.setText(String.valueOf(promotion.getMaxUsePerCustomer()));
      txtHStart.setText(dfTime.format(promotion.getFromDate()));
      if (promotion.getToDate() != null) {
        txtHEnd.setText(dfTime.format(promotion.getToDate()));
      }
      dcStartDate.setDate(promotion.getFromDate());
      dcEndDate.setDate(promotion.getToDate());
      if (promotion.getDiscountRate() != 0) {
        rbtDiscount.setSelected(true);
        txtDiscount.setText(String.valueOf(promotion.getDiscountRate()));
      } else {
        rbtMoney.setSelected(true);
        txtMoney.setText(String.valueOf(promotion.getDiscount()));
      }
      txtValueMax.setText(String.valueOf(promotion.getMaxExpense()));
      txtValueMin.setText(String.valueOf(promotion.getAppliedToMinExpense()));
      txtQuantity.setText(String.valueOf(promotion.getMaxUsePerCustomer()));
      txtArea.setText(promotion.getDescription());
      if (promotion.isCumulative())
        chbCumulative.setSelected(true);
      else
        chbCumulative.setSelected(false);
      // Kiểm tra CustomerTarget trong đối tượng, nếu có lấy ra đối tượng
      // vị trí thứ 0
      if (promotion.getCustomerTargets().size() > 0) {
        CustomerTarget c = promotion.getCustomerTargets().get(0);
        // Nếu có kiểu GroupPartner thì tích chọn chbGroupPartner, nếu
        // ko bỏ tích
        if (c.getCustomerTargetType().equals(CustomerTargetType.ByGroup)) {
          chbGroupPartner.setSelected(true);
          chbPartner.setSelected(false);
          for (int i = 0; i < cboGroupPartner.getItemCount(); i++) {
            if (cboGroupPartner.getItemAt(i).getPath().equals(c.getCustomerGroup())) {
              cboGroupPartner.setSelectedIndex(i);
              break;
            }
          }
        } else
          chbGroupPartner.setSelected(false);
        // Nếu có kiểu Partner thì tích chọn chbPartner, nếu ko bỏ tích
        if (c.getCustomerTargetType().equals(CustomerTargetType.ByCustomer)) {
          chbPartner.setSelected(true);
          chbGroupPartner.setSelected(false);
          for (int i = 0; i < cboPartner.getItemCount(); i++) {
            if (cboPartner.getItemAt(i).getLoginId().equals(c.getCustomerGroup())) {
              cboPartner.setSelectedIndex(i);
              break;
            }
          }
        } else
          chbPartner.setSelected(false);
      } else {
        chbGroupPartner.setSelected(false);
        chbPartner.setSelected(false);
      }

      // Kiểm tra ProductTarget trong đối tượng, nếu có thì lấy đối tượng
      // thứ 0
      if (promotion.getProductTargets().size() > 0) {
        ProductTarget c = promotion.getProductTargets().get(0);
        // Nếu có kiểu GroupProductTag thì tích chọn, ngược lại bỏ tích
        if (c.getProductTargetType().equals(ProductTargetType.ByProductTag)) {
          chbGroupProduct.setSelected(true);
          chbProduct.setSelected(false);
          for (int i = 0; i < cboGroupProduct.getItemCount(); i++) {
            if (cboGroupProduct.getItemAt(i).getTag().equals(c.getProductIdentifierId())) {
              cboGroupProduct.setSelectedIndex(i);
              break;
            }
          }
        } else
          chbGroupProduct.setSelected(false);
        // Nếu có kiểu ProductTag thì tích chọn, ngược lại bỏ tích
        if (c.getProductTargetType().equals(ProductTargetType.ByProduct)) {
          chbProduct.setSelected(true);
          chbGroupProduct.setSelected(false);
          for (int i = 0; i < cboProduct.getItemCount(); i++) {
            if (cboProduct.getItemAt(i).getCode().equals(c.getProductIdentifierId())) {
              cboProduct.setSelectedIndex(i);
              break;
            }
          }
        } else
          chbProduct.setSelected(false);
      } else {
        chbGroupProduct.setSelected(false);
        chbProduct.setSelected(false);
      }

      // Kiểm tra LocationTarget trong đối tượng, nếu có thì lấy đối tượng
      // thứ 0
      if (promotion.getLocationTargets().size() > 0) {
        LocationTarget c = promotion.getLocationTargets().get(0);
        // Nếu có kiểu Area thì tích chọn, ngược lại bỏ tích
        if (c.getLocationTargetType().equals(LocationTargetType.ByArea)) {
          chbArea.setSelected(true);
          chbTable.setSelected(false);
          for (int i = 0; i < cboArea.getItemCount(); i++) {
            if (cboArea.getItemAt(i).getCode().equals(c.getLocation())) {
              cboArea.setSelectedIndex(i);
              break;
            }
          }
        } else
          chbArea.setSelected(false);
        // Nếu có kiểu Table thì tích chọn, ngược lại bỏ tích
        if (c.getLocationTargetType().equals(LocationTargetType.ByTable)) {
          chbTable.setSelected(true);
          chbArea.setSelected(false);
          for (int i = 0; i < cboTable.getItemCount(); i++) {
            if (cboTable.getItemAt(i).getCode().equals(c.getLocation())) {
              cboTable.setSelectedIndex(i);
              break;
            }
          }
        } else
          chbTable.setSelected(false);
      } else {
        chbArea.setSelected(false);
        chbTable.setSelected(false);
      }
      if(promotion.getStoreCode()!=null){
        chbTypeStore.setSelected(true);
        cboTypeStore.setSelectedIndex(1);
        chbStore.setSelected(true);
        for(int i = 0; i< cboStore.getItemCount(); i++){
          if(((Option)cboStore.getItemAt(i)).getCode().equals(promotion.getStoreCode())){
            cboStore.setSelectedIndex(i);
            break;
          }
        }
      }
      if(promotion.getMaxExpense()==Double.MAX_VALUE){
        txtValueMax.setText("0");
      }
      if(promotion.getMaxUsePerCustomer()==Integer.MAX_VALUE){
        txtQuantity.setText("0");
      }
      // Đây là kiểu xem lại nên disable các chức năng input giao diện
      isEdit(false);
    }
  }

  // Thiết lập trạng thái nhập của người dùng
  private void isEdit(boolean edit) {
    txtDiscount.setEnabled(edit);
    txtHEnd.setEnabled(edit);
    txtHStart.setEnabled(edit);
    txtMoney.setEnabled(edit);
    txtName.setEnabled(edit);
    txtQuantity.setEnabled(edit);
    txtValueMax.setEnabled(edit);
    txtValueMin.setEnabled(edit);
    txtArea.setEnabled(edit);
    chbCumulative.setEnabled(edit);
    chbArea.setEnabled(edit);
    chbGroupPartner.setEnabled(edit);
    chbGroupProduct.setEnabled(edit);
    chbPartner.setEnabled(edit);
    chbProduct.setEnabled(edit);
    chbTable.setEnabled(edit);
    cboArea.setEnabled(edit);
    cboGroupProduct.setEnabled(edit);
    cboGroupPartner.setEnabled(edit);
    cboPartner.setEnabled(edit);
    cboProduct.setEnabled(edit);
    cboTable.setEnabled(edit);
    cboUnitMoney.setEnabled(edit);
    cboUnitMoney1.setEnabled(edit);
    cboUnitMoney2.setEnabled(edit);
    rbtDiscount.setEnabled(edit);
    rbtMoney.setEnabled(edit);
    dcStartDate.setEnabled(edit);
    dcEndDate.setEnabled(edit);
    btnSetupTime.setEnabled(edit);
    chbTypeStore.setEnabled(edit);
    chbStore.setEnabled(edit);
    cboStore.setEnabled(edit);
    cboTypeStore.setEnabled(edit);
    if(rbtDiscount.isSelected()){
    	txtMoney.setEnabled(false);
    	cboUnitMoney.setEnabled(false);
    }
  }

  // Kiểm tra dữ liệu trước khi lưu
  private boolean checkData() {
    // Nếu phát sinh lỗi bất kỳ sẽ trả về false

    // Kiểm tra đã checkBox 1 trong 6 checkBox chưa?
    if (chbGroupProduct.isSelected() || chbGroupPartner.isSelected() || chbArea.isSelected() || chbProduct.isSelected()
        || chbPartner.isSelected() || chbTable.isSelected() || chbTypeStore.isSelected() || chbStore.isSelected()) {
      lbMessager.setText("");
    } else {
      lbMessager.setText("Vui lòng check(v) \"Lựa chọn khuyến mại\"");
      return false;
    }

    // Kiểm tra tên KM khác trống
    if (txtName.getText().equals("")) {
      jLabel1.setForeground(Color.red);
      lbMessager.setText("Vui lòng kiểm tra dòng báo đỏ");
      return false;
    } else {
      jLabel1.setForeground(new Color(51, 51, 51));
      lbMessager.setText("");
    }

    // Kiểm tra ngày bắt đầu khác rỗng
    if (dcStartDate.getDate() != null) {
      jLabel3.setForeground(new Color(51, 51, 51));
      lbMessager.setText("");
    } else {
      jLabel3.setForeground(Color.RED);
      lbMessager.setText("Vui lòng kiểm tra dòng báo đỏ");
      return false;
    }

    // Kiểm tra giá trị số ô KM%
    try {
      if (rbtDiscount.isSelected() && txtDiscount.getText().equals("")) {
        rbtDiscount.setForeground(Color.RED);
        lbMessager.setText("Vui lòng không để trống giá trị");
        return false;
      } else {
        rbtDiscount.setForeground(new Color(51, 51, 51));
        lbMessager.setText("");
        if (txtDiscount.getText().trim().length() > 0) {
          Double.parseDouble(txtDiscount.getText());
          txtDiscount.setForeground(new Color(51, 51, 51));
        }
      }
    } catch (Exception e) {
      txtDiscount.setForeground(Color.red);
      lbMessager.setText("Vui lòng kiểm tra dòng báo đỏ");
      return false;
    }

    // Kiểm tra giá trị số ô KM tiền
    try {
      if (rbtMoney.isSelected() && txtMoney.getText().equals("")) {
        rbtMoney.setForeground(Color.RED);
        lbMessager.setText("Vui lòng không để trống giá trị");
        return false;
      } else {
        rbtMoney.setForeground(new Color(51, 51, 51));
        lbMessager.setText("");
        if (txtMoney.getText().trim().length() > 0) {
          Double.parseDouble(txtMoney.getText());
          txtMoney.setForeground(new Color(51, 51, 51));
        }
      }
    } catch (Exception e) {
      txtMoney.setForeground(Color.red);
      lbMessager.setText("Vui lòng kiểm tra dòng báo đỏ");
      return false;
    }

    // Nếu không có lỗi sẽ trả về true
    return true;
  }

  // Lấy dữ liệu ra giao diện
  public void setData(PromotionDetail promotion) {
    this.promotion = promotion;
    try {
      refresh();
    } catch (Exception e) {
    }
  }
//  public void setData(Promotion promotion) {
//	    this.promotionAll = promotion;
//	    try {
//	      refresh();
//	    } catch (Exception e) {
//	    }
//	  }

  // Tạo dữ liệu mẫu liên quan
  @Override
  public void createBeginTest() {
    if (MyPanel.isTest) {
      // Tạo nhóm hàng hóa
      ProductTag productTag = new ProductTag();
      productTag.setCode("Mã SP HktTest1");
      productTag.setTag("Thẻ HktTest1");
      productTag.setLabel("NSP HktTest1");
      try {
        ProductModelManager.getInstance().saveProductTag(productTag);
      } catch (Exception e) {
        e.printStackTrace();
      }
      // Tạo nhóm đối tác
      try {
        AccountGroup root = AccountModelManager.getInstance().getRootGroupDetail().getChildren().get(0);
        AccountGroup parent = new AccountGroup(root, AccountModelManager.Customer);
        parent.setLabel(AccountModelManager.Customer);
        if (AccountModelManager.getInstance().getGroupByPath(root.getPath() + "/" + AccountModelManager.Customer) == null) {
          parent = AccountModelManager.getInstance().saveGroup(parent);
        } else {
          parent = AccountModelManager.getInstance().getGroupByPath(parent.getPath());
        }
        AccountGroup accountGroup = new AccountGroup();
        accountGroup.setName("Mã ĐT HktTest123");
        accountGroup.setLabel("NĐT HktTest123");
        accountGroup.setParent(parent);

        AccountModelManager.getInstance().saveGroup(accountGroup);
      } catch (Exception e2) {
        e2.printStackTrace();
      }
//      // Tạo đối tác
//      Account account = new Account();
//      account.setLoginId("quy");
//      account.setPassword("quy");
//      account.setEmail("quy@gmail.com");
//      try {
//        AccountModelManager.getInstance().saveAccount(account);
//      } catch (Exception e1) {
//        e1.printStackTrace();
//      }
//      Customer customer = new Customer();
//      customer.setLoginId("quy");
//      customer.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
//      customer.setCode("quyquy");
//      customer.setName("quyquyquy");
//      try {
//        CustomerModelManager.getInstance().saveCustomer(customer);
//      } catch (Exception e1) {
//        e1.printStackTrace();
//      }
      
      // Tạo khu vực
      Location location = new Location();
      location.setCode("Mã KV HktTest1");
      location.setName("KV HktTest1");
      location.setOrganizationLoginId("HktTest1");
      try {
        RestaurantModelManager.getInstance().saveLocation(location);
      } catch (Exception e) {
        e.printStackTrace();
      }
      // Tạo sản phẩm
      Product product = new Product();
      product.setCode("Mã HH HktTest1");
      product.setName("HH HktTest1");
      product.setMaker("HktTest1");
      try {
        ProductModelManager.getInstance().saveProduct(product);
      } catch (Exception e) {
        e.printStackTrace();
      }
      // Tạo bàn trong khu vực
      Table table = new Table();
      table.setName("Bàn HktTest1");
      table.setLocationCode("Mã KV HktTest1");
      table.setCode("Mã Bàn HktTest1");
      table.setOrganizationLoginId("HktTest1");
      table.setResponsibleGroup("hkt/Khách hàng/Mã ĐT HktTest1");
      try {
        RestaurantModelManager.getInstance().saveTable(table);
      } catch (Exception e) {
        e.printStackTrace();
      }
      // Tạo đơn vị tiền tệ
      Currency currency = new Currency();
      currency.setCode("Mã TT HktTest1");
      currency.setName("TT HktTest1");
      currency.setRate(1);
      try {
        LocaleModelManager.getInstance().saveCurrency(currency);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    super.createBeginTest();
  }

  // Tạo dữ liệu mẫu test phân trang
  @Override
  public void createDataTest() {
    SimpleDateFormat sdfTest = new SimpleDateFormat("dd/M/yyyy");
    for (int i = 4; i <= 50; i++) {
      promotion = new PromotionDetail();
      promotion.setCode("Mã HktTest" + i);
      promotion.setTimeOptionId(timeOptionId);
      promotion.setName("Khuyến mại HktTest" + i);
      promotion.setCurrencyUnit("Mã TT HktTest1");
      promotion.setDescription("Chi chú HktTest" + i);
      promotion.setDiscount(i);
      promotion.setDiscountRate(i);
      promotion.setMaxUsePerCustomer(i);
      promotion.setMaxExpense(i + 2);
      promotion.setAppliedToMinExpense(i);

      ProductTarget productTarget = new ProductTarget();
      promotion.add(productTarget);
      productTarget.setProductTargetType(ProductTargetType.ByProductTag);
      productTarget.setProductIdentifierId("Mã SP HktTest1");

      LocationTarget locationTarget = new LocationTarget();
      promotion.add(locationTarget);
      locationTarget.setLocationTargetType(LocationTargetType.ByArea);
      locationTarget.setLocation("Mã KV HktTest1");

      promotion.setStatus(Status.Active);
      try {
        promotion.setFromDate(sdfTest.parse("01/08/2014"));
        promotion.setToDate(sdfTest.parse("01/08/2015"));
        PromotionModelManager.getInstance().savePromotion(promotion);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  // Xóa toàn bộ dữ liệu Test
  @Override
  public void deleteDataTest() {
    if (!PanelTestAll.runAll) {
      DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
      if (DialogTest.getInstance().isTest()) {
        try {
          ProductModelManager.getInstance().deleteTest("HktTest");
          AccountModelManager.getInstance().deleteTest("HktTest");
          CustomerModelManager.getInstance().deleteTest("HktTest");
          RestaurantModelManager.getInstance().deleteTest("HktTest");
          LocaleModelManager.getInstance().deleteTest("HktTest");
          PromotionModelManager.getInstance().deleteTest("HktTest");
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
}
