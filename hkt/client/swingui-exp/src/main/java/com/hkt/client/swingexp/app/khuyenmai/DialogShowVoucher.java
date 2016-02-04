package com.hkt.client.swingexp.app.khuyenmai;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.MultiSplitLayout;

import com.hkt.client.swing.widget.GridBagLayoutPanel;
import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJRadioButton;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.IMyObserver;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialogPlus;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.hethong.PanelCurrency;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.khohang.CatalogProductTag;
import com.hkt.client.swingexp.app.khohang.IPanelListProduct;
import com.hkt.client.swingexp.app.khohang.PanelListProduct;
import com.hkt.client.swingexp.app.khuyenmai.list.TableModelMenuItemRef;
import com.hkt.client.swingexp.app.khuyenmai.list.TableModelMenuItemVoucher;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.config.locale.Currency;
import com.hkt.module.config.locale.ProductUnit;
import com.hkt.module.core.entity.AbstractPersistable.State;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductAttribute;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.promotion.entity.Menu;
import com.hkt.module.promotion.entity.Menu.Status;
import com.hkt.module.promotion.entity.Menu.Type;
import com.hkt.module.promotion.entity.MenuItem;
import com.hkt.module.promotion.entity.MenuItem.MenuItemType;
import com.hkt.module.promotion.entity.MenuItemRef;
import com.hkt.module.warehouse.entity.Warehouse;

public class DialogShowVoucher extends MyPanel implements IPanelListProduct, IDialogMain,IMyObserver {
	
  private PanelShowMenu             panelShowMenu;
  private IPanelListProduct         parentPanel      = this;
  private PanelListProduct               panelListProduct;
  private JScrollPane               scrollSanPham;
  private JComboBox                 comboGroup       = new JComboBox();
  private List<ProductTag>          tagsCheckBox;
  private List<ProductTag>          listProductGroup = new ArrayList<ProductTag>();
  private List<Currency>            currencies;
  private List<MenuItem>            menuItems;
  private ExtendJRadioButton        checkbCoDinh, checkbTuyChon;
  private JTable                    tableSPchinh;
  private JButton                   btnClick;
  private IDialogMain               iDialogMain      = this;
  private JTable                    tableSPtuychon;
  private JTextField                txtTen, txtTienBan;
  private JComboBox                 cbUnitMoney;
  private JLabel                    labelErr;
  private TextPopup<Product>        txtSearch;
  private ExtendJTextField          txtQuantityProduct;
  private Menu                      menu;
  private TableModelMenuItemVoucher modelMenuItem;
  private TableModelMenuItemRef     modelProductMenuItemRef;
  public static String permission;
  public boolean reset = false;

  public DialogShowVoucher() {
    createBeginTest();
    menuItems = new ArrayList<MenuItem>();
    setOpaque(false);
    panelShowMenu = new PanelShowMenu();
    setLayout(new BorderLayout());
    add(panelShowMenu, BorderLayout.CENTER);
    setSize(Toolkit.getDefaultToolkit().getScreenSize());
    txtSearch.addObserver(this);
  }

  public DialogShowVoucher(Menu menu) {
    this.menu = menu;
    menuItems = menu.getMenuItems();
    setOpaque(false);
    panelShowMenu = new PanelShowMenu();
    setLayout(new BorderLayout());
    add(panelShowMenu, BorderLayout.CENTER);
    setSize(Toolkit.getDefaultToolkit().getScreenSize());
    displayed();
    txtSearch.addObserver(this);
  }

  public class PanelShowMenu extends JPanel {
    public PanelShowMenu() {
      setOpaque(false);
      setLayout(new BorderLayout());
      add(new PanelSanPham(), BorderLayout.LINE_START);
      add(new PanelMenuSP(), BorderLayout.CENTER);
      
    }
  }

  public class PanelSanPham extends JPanel {
    public PanelSanPham() {
      setOpaque(false);
      setLayout(new BorderLayout());
      JPanel panel = new JPanel();
      panel.setOpaque(false);
      panel.setLayout(new BorderLayout());
      JPanel panel2 = new JPanel();
      panel2.setOpaque(false);
      panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
      JLabel label = new JLabel("Tìm kiếm");
      label.setFont(new Font("Tahoma", Font.BOLD, 14));
      label.setPreferredSize(new Dimension(77, 23));
      panel2.add(label);
      panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
      txtSearch = new TextPopup<Product>(Product.class);
      txtSearch.setPreferredSize(new Dimension(300, 23));
      try {
        List<Product> list1 = ProductModelManager.getInstance().filterProduct();
        txtSearch.setData(list1);
      } catch (Exception e) {
        e.printStackTrace();
      }
      txtSearch.setPreferredSize(new Dimension(300, 23));
      panel2.add(txtSearch);
      ExtendJLabel label2 = new ExtendJLabel("Số lượng");
      panel2.add(label2);
      txtQuantityProduct = new ExtendJTextField();
      txtQuantityProduct.setPreferredSize(new Dimension(100, 23));
      txtQuantityProduct.setHorizontalAlignment(JTextField.RIGHT);
      txtQuantityProduct.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
          try {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
              if (txtQuantityProduct.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Nhập số lượng sản phẩm !");
                return;
              }
              double quantity = Double.parseDouble(txtQuantityProduct.getText().trim().toString());
              Product product = txtSearch.getItem();
              for (int i = 0; i < quantity; i++) {
                addProduct(product);
              }
            }
          } catch (Exception e2) {
            e2.printStackTrace();
          }
        }
      });
      panel2.add(txtQuantityProduct);
      panel.add(new PanelNhomSP(), BorderLayout.LINE_START);
      panel.add(new PanelListSP(), BorderLayout.LINE_END);
      add(panel2, BorderLayout.PAGE_START);
      add(panel, BorderLayout.CENTER);
      txtSearch.setComponentFocus(txtQuantityProduct);
    }
  }

  public class PanelNhomSP extends JPanel {
    public PanelNhomSP() {
      setOpaque(false);
      setLayout(new BorderLayout());
      add(new PanelNhomSP_START(), BorderLayout.PAGE_START);
      add(new PanelNhomSP_CENTER(), BorderLayout.CENTER);
    }
  }

  private void loadProductTagByCheckBox(ProductTag productTagRoot) {
    try {
      String tagRoot = productTagRoot.getTag();
      List<ProductTag> productTags = ProductModelManager.getInstance().findProductTagRoot(tagRoot);
      for (ProductTag productTag : productTags) {
        String tag = productTag.getTag();
        ProductTag productTagGet = ProductModelManager.getInstance().getProductTagByTag(tag);
        tagsCheckBox.add(tagsCheckBox.size(), productTagGet);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void removeProductTagByCheckBox(ProductTag productTagRoot) {
    try {
      String tagRoot = productTagRoot.getTag();
      List<ProductTag> productTags = ProductModelManager.getInstance().findProductTagRoot(tagRoot);
      for (ProductTag productTag : productTags) {
        String tag = productTag.getTag();
        ProductTag productTagGet = ProductModelManager.getInstance().getProductTagByTag(tag);
        tagsCheckBox.remove(productTagGet);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public class PanelNhomSP_START extends JPanel {
    private JLabel lbNhomSP;

    public PanelNhomSP_START() {
      try {
        tagsCheckBox = new ArrayList<ProductTag>();
        tagsCheckBox.add(0, null);
        setOpaque(false);
        lbNhomSP = new ExtendJLabel("  Nhóm SP:");
        comboGroup.setName("comboGroup");
        comboGroup.setPreferredSize(new Dimension(191, 23));
        setLayout(new BorderLayout());
        add(lbNhomSP, BorderLayout.LINE_START);
        add(comboGroup, BorderLayout.CENTER);
        comboGroup.setModel(new DefaultComboBoxModel(tagsCheckBox.toArray()));
        
        comboGroup.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		if(e.getButton() == MouseEvent.BUTTON3)
        		{
        			CatalogProductTag hh;
    				try {
						hh = new CatalogProductTag();
						hh.setName("QuanLyNhomHang_KhoHangHoa");
						DialogMain dialog = new DialogMain(hh);
						dialog.setName("dlNhomhang");
						dialog.setIcon("nhomsp1.png");
						dialog.setTitle("Quản lý nhóm hàng");
						dialog.setVisible(true);
						try {
							comboGroup.setModel(new DefaultComboBoxModel(ProductModelManager.getInstance().getProductTags().toArray()));
						} catch (Exception e1) {
							comboGroup.setModel(new DefaultComboBoxModel());
						}
						
    				} catch (Exception e1) {
    					e1.printStackTrace();
    				}
        		}}
        		});
        // Load product khi thay doi item
        comboGroup.addItemListener(new ItemListener() {

          @Override
          public void itemStateChanged(ItemEvent e) {
            ProductTag productTag = (ProductTag) comboGroup.getSelectedItem();
            List<ProductTag> list = new ArrayList<ProductTag>();
            list.add(productTag);
            if (productTag != null) {
              panelListProduct = new PanelListProduct(parentPanel);
              panelListProduct.setListProductTag(list);
              panelListProduct.loadGui();
              scrollSanPham.setViewportView(panelListProduct);
            } else {
              try {
                List<ProductTag> tags = ProductModelManager.getInstance().getProductTags();
                List<ProductTag> listProductG = new ArrayList<ProductTag>();
                for (ProductTag productTag1 : tags) {
                  if (productTag1.getTag().split(":").length == 2) {
                    listProductG.add(productTag1);
                  }
                }
                panelListProduct = new PanelListProduct(parentPanel);
                panelListProduct.setListProductTag(listProductG);
                panelListProduct.loadGui();
                scrollSanPham.setViewportView(panelListProduct);
              } catch (Exception e2) {
                e2.printStackTrace();
              }
            }
          }
        });
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public class PanelNhomSP_CENTER extends JPanel {
    public PanelNhomSP_CENTER() {
      List<ProductTag> productTags = new ArrayList<ProductTag>();
      setOpaque(false);
      setBorder(BorderFactory.createTitledBorder(
          BorderFactory.createBevelBorder(BevelBorder.RAISED, null, new Color(204, 204, 204), null, null),
          "Nhóm sản phẩm", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", 1, 14), new Color(126, 0, 0)));

      try {
        productTags = ProductModelManager.getInstance().getProductTags();
      } catch (Exception e) {
        e.printStackTrace();
      }
      // JPanel panelBot = new JPanel();
      // panelBot.setOpaque(false);
      // add(panelBot, BorderLayout.CENTER);
      setOpaque(false);
      setLayout(new BorderLayout());
      GridBagLayoutPanel bagLayoutPanel = new GridBagLayoutPanel();
      bagLayoutPanel.setOpaque(false);

      for (int i = 0; i < productTags.size(); i++) {
        final ProductTag productTag = productTags.get(i);
        // for (final ProductTag productTag : productTags) {
        if (productTag != null) {
          String tg = productTag.getTag();
          if (!tg.contains(":")) {
            final JCheckBox checkBox = new JCheckBox(productTag.getLabel());
            checkBox.setName("ckNhomSP" + i);
            checkBox.setSelected(true);
            // load CB Group sau khi co data
            loadProductTagByCheckBox(productTag);
            comboGroup.setModel(new DefaultComboBoxModel(tagsCheckBox.toArray()));
            checkBox.setOpaque(false);
            checkBox.setFont(new Font("Tahoma", Font.BOLD, 14));
            // panelBot.add(checkBox);
            bagLayoutPanel.add(i, 0, checkBox);
            checkBox.addActionListener(new ActionListener() {

              @Override
              public void actionPerformed(ActionEvent e) {
                if (checkBox.isSelected()) {
                  // load khi checked
                  loadProductTagByCheckBox(productTag);
                  comboGroup.setModel(new DefaultComboBoxModel(tagsCheckBox.toArray()));
                  if (tagsCheckBox != null)
                    if (tagsCheckBox.size() > 0)
                      if (tagsCheckBox.get(0) == null) {
                        tagsCheckBox.remove(0);
                      }
                  panelListProduct = new PanelListProduct(parentPanel);
                  panelListProduct.setListProductTag(tagsCheckBox);
                  panelListProduct.loadGui();
                  scrollSanPham.setViewportView(panelListProduct);
                } else {
                  // remove khi unchecked
                  removeProductTagByCheckBox(productTag);
                  comboGroup.setModel(new DefaultComboBoxModel(tagsCheckBox.toArray()));
                  if (tagsCheckBox != null)
                    if (tagsCheckBox.size() > 0)
                      if (tagsCheckBox.get(0) == null) {
                        tagsCheckBox.remove(0);
                      }
                  panelListProduct = new PanelListProduct(parentPanel);
                  panelListProduct.setListProductTag(tagsCheckBox);
                  panelListProduct.loadGui();
                  scrollSanPham.setViewportView(panelListProduct);
                }
              }
            });
          }
        }
      }
      add(bagLayoutPanel, BorderLayout.PAGE_START);
    }
  }

  private void loadListProductByCBB() {
    try {
      ProductTag productGroups = (ProductTag) comboGroup.getSelectedItem();
      List<ProductTag> listProductG = new ArrayList<ProductTag>();
      listProductG.add(productGroups);
      if (productGroups != null) {
        panelListProduct.setListProductTag(listProductG);
        panelListProduct.loadGui();
      } else {
        panelListProduct.setListProductTag(listProductGroup);
        panelListProduct.loadGui();
      }
    } catch (Exception e) {
      panelListProduct.setListProductTag(listProductGroup);
      panelListProduct.loadGui();
    }
  }


  public class PanelListSP extends JPanel {

    public PanelListSP() {
      try {
        setOpaque(false);
        setLayout(new BorderLayout());
        scrollSanPham = new JScrollPane();
        add(scrollSanPham, BorderLayout.CENTER);
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        panelListProduct = new PanelListProduct(parentPanel);
        List<ProductTag> tags = ProductModelManager.getInstance().getProductTags();
        List<ProductTag> listProductG = new ArrayList<ProductTag>();
        for (ProductTag productTag : tags) {
          if (productTag.getTag().split(":").length == 2) {
            listProductG.add(productTag);
          }
        }
        panelListProduct.setListProductTag(listProductG);
        panelListProduct.loadGui();
        scrollSanPham = new JScrollPane();
        add(scrollSanPham, BorderLayout.CENTER);
        scrollSanPham.setViewportView(panelListProduct);
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
  }

  public class PanelMenuSP extends JPanel {
    public PanelMenuSP() {
      setOpaque(false);
      setLayout(new BorderLayout());
      add(new PanelMenuSP_START(), BorderLayout.PAGE_START);
      add(new PanelMenuSP_CENTER(), BorderLayout.CENTER);
    }
  }

  public class PanelMenuSP_START extends GridBagLayoutPanel {
    private JLabel lbTen, lbTienBan, lbLoaiNhom, lbKieu;

    public PanelMenuSP_START() {
      setOpaque(false);
      lbTen = new ExtendJLabel("Tên Voucher");
      lbTienBan = new ExtendJLabel("Tiền bán");
      lbLoaiNhom = new ExtendJLabel("Loại nhóm");
      lbKieu = new ExtendJLabel("Kiểu");

      txtTen = new ExtendJTextField();
      txtTen.setName("txtTen");
      comboGroup.setName("comboGroup");
      txtTienBan = new ExtendJTextField();
      txtTienBan.setName("txtTienBan");
      txtTienBan.setHorizontalAlignment(JTextField.RIGHT);

      checkbCoDinh = new ExtendJRadioButton("Cố định");
      checkbCoDinh.setName("checkbCoDinh");
      checkbCoDinh.setSelected(true);
      checkbCoDinh.addItemListener(new ItemListener() {

        @Override
        public void itemStateChanged(ItemEvent e) {
          if (checkbCoDinh.isSelected()) {
            btnClick.setEnabled(false);
            loadTableSPTuyChon(null);
            btnClick.setText("Thêm tùy chọn");
            tableSPchinh.clearSelection();
          }
        }
      });

      checkbTuyChon = new ExtendJRadioButton("Tùy chọn");
      checkbTuyChon.setName("checkbTuyChon");
      checkbTuyChon.addItemListener(new ItemListener() {

        @Override
        public void itemStateChanged(ItemEvent e) {
          btnClick.setEnabled(true);
        }
      });
      ButtonGroup menuItemTypeUI = new ButtonGroup();
      menuItemTypeUI.add(checkbCoDinh);
      menuItemTypeUI.add(checkbTuyChon);

      cbUnitMoney = new JComboBox();
      cbUnitMoney.setPreferredSize(new Dimension(200, 23));
      try {
        currencies = LocaleModelManager.getInstance().getCurrencies();
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (currencies != null) {
        cbUnitMoney = new JComboBox(currencies.toArray());
      } else {
        cbUnitMoney = new JComboBox(new ArrayList<Currency>().toArray());
      }
      btnClick = new ExtendJButton("Thêm tùy chọn");
      btnClick.setName("btnOption");
      btnClick.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
      btnClick.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          try {
            PanelMenuItemRef dialogMain = new PanelMenuItemRef(iDialogMain);
            dialogMain.setName("panelOption");
            if (btnClick.getText().equals("Sửa tùy chọn")) {
              MenuItem menuItem = (MenuItem) tableSPchinh.getValueAt(tableSPchinh.getSelectedRow(), 0);
              MenuItemRef menuItemRef = PromotionModelManager.getInstance().getMenuItemRefByCode(
                  menuItem.getIdentifierId());
              dialogMain.setData(menuItemRef);
            }
            DialogMain dialog = new DialogMain(dialogMain, true);
            dialog.setName("dlThemTuyChon");
            dialog.dispose();
            dialog.setUndecorated(true);
            dialog.setTitle("Sản phẩm tùy chọn");
            dialog.setIcon("voucher1.png");
            dialog.setModal(true);
            dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            dialog.setLocationRelativeTo(null);
            if (btnClick.getText().equals("Sửa tùy chọn")) {
              dialog.showButton(false);
            }
            dialog.setVisible(true);
          } catch (Exception e2) {
            e2.printStackTrace();
          }
        }
      });
      
      cbUnitMoney.addMouseListener(new MouseAdapter() {
      	public void mouseClicked(MouseEvent e) {
        		if(e.getButton() == MouseEvent.BUTTON3)
        		{
    				PanelCurrency pnCurrencyr;
    				try {
    					pnCurrencyr = new PanelCurrency();
    					pnCurrencyr.setName("TienTe");
    					DialogMain dialog = new DialogMain(pnCurrencyr);
    					dialog.setName("dlTienTe");
    					dialog.setIcon("tien1.png");
    					dialog.setTitle("Tiền tệ");
    					dialog.setModal(true);
    					dialog.setLocationRelativeTo(null);
    					dialog.setVisible(true);
    					try {
    						cbUnitMoney.setModel(new DefaultComboBoxModel(LocaleModelManager.getInstance().getCurrencies().toArray()));
    					} catch (Exception e1) {
    						cbUnitMoney.setModel(new DefaultComboBoxModel());
    					}
    				} catch (Exception e1) {
    					e1.printStackTrace();
    				}
        		}}
        		});

      add(0, 0, lbTen, 1);
      add(0, 1, txtTen, 3);

      add(1, 0, lbTienBan, 1);
      add(1, 1, txtTienBan, 2);
      add(1, 3, cbUnitMoney, 1);

      add(2, 0, lbKieu);
      add(2, 1, checkbCoDinh);
      add(2, 2, checkbTuyChon);
      add(2, 3, btnClick);

      if (checkbCoDinh.isSelected() == true) {
        btnClick.setEnabled(false);
      }
    }

  }

  public class PanelMenuSP_CENTER extends JPanel {

    public PanelMenuSP_CENTER() {
      setOpaque(false);
      setLayout(new BorderLayout());

      JXMultiSplitPane msp = new JXMultiSplitPane();
      msp.setOpaque(false);
      setOpaque(false);
      String layoutDef = "(COLUMN " + "  (LEAF name=top  weight=0.5)" + "  (LEAF name=down weight=0.5)" + ")";
      MultiSplitLayout.Node modelRoot = MultiSplitLayout.parseModel(layoutDef);
      msp.getMultiSplitLayout().setModel(modelRoot);
      msp.add(new PanelMenuSP_CENTER_1(), "top");
      msp.add(new PanelMenuSP_CENTER_2(), "down");

      add(msp, BorderLayout.CENTER);
      labelErr = new ExtendJLabel();
      labelErr.setFont(new Font("Tahoma", Font.PLAIN, 14));
      labelErr.setForeground(Color.red);
      labelErr.setText("");
      add(labelErr, BorderLayout.PAGE_END);
    }

    public class PanelMenuSP_CENTER_1 extends JPanel {
      private JScrollPane scrollSPchinh;

      public PanelMenuSP_CENTER_1() {
        setOpaque(false);
        scrollSPchinh = new JScrollPane();
        scrollSPchinh.getViewport().setBackground(Color.white);

        tableSPchinh = new JTable();
        tableSPchinh.setName("tableSPchinh");
        tableSPchinh.addKeyListener(new KeyAdapter() {
          @Override
          public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_DELETE) {
              int reply = JOptionPane.showConfirmDialog(null, "Bạn muốn xóa ?", "Xóa voucher",
                  JOptionPane.YES_NO_OPTION);
              if (reply == JOptionPane.YES_OPTION) {
                MenuItem item = (MenuItem) tableSPchinh.getValueAt(tableSPchinh.getSelectedRow(), 0);
                if (item.getId() == null) {
                  menuItems.remove(item);
                } else {
                  item.setPersistableState(State.DELETED);
                }
                loadTableMenuItem();
                loadTableProductOptionDelete();
              }
            }
          }
        });
        modelMenuItem = new TableModelMenuItemVoucher(new ArrayList<MenuItem>());
        tableSPchinh.setModel(modelMenuItem);
        tableSPchinh.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            JTable table = (JTable) e.getSource();
            if (table.getSelectedRow() < 0) {
              return;
            }
            MenuItem menuItem = (MenuItem) table.getValueAt(table.getSelectedRow(), 0);
            if (menuItem.getMenuItemType() == MenuItemType.ByMenuItemRef) {
              loadTableSPTuyChon(menuItem.getIdentifierId());
            } else {
              loadTableSPTuyChon(null);
            }
            MenuItemType itemType = menuItem.getMenuItemType();
            if (itemType == MenuItemType.ByProduct) {
              checkbCoDinh.setSelected(true);
              btnClick.setText("Thêm tùy chọn");
            } else {
              btnClick.setText("Sửa tùy chọn");
              checkbTuyChon.setSelected(true);
            }
          }
        });
        tableSPchinh.setRowHeight(23);
        tableSPchinh.setFont(new Font("Tahoma", 0, 14));
        tableSPchinh.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
        tableSPchinh.getColumnModel().getColumn(2).setMaxWidth(100);
        tableSPchinh.getColumnModel().getColumn(2).setMinWidth(100);
        ((DefaultTableCellRenderer) tableSPchinh.getTableHeader().getDefaultRenderer())
            .setHorizontalAlignment(JLabel.CENTER);
        tableSPchinh.setPreferredScrollableViewportSize(new Dimension(200, 320));
        scrollSPchinh.setViewportView(tableSPchinh);
        setBorder(BorderFactory.createTitledBorder(null, "Danh sách các SP chính", TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Tahoma", 1, 14), new Color(126, 0, 0)));
        setOpaque(false);
        setLayout(new BorderLayout());
        add(scrollSPchinh, BorderLayout.CENTER);
      }
    }

    public class PanelMenuSP_CENTER_2 extends JPanel {
      private JScrollPane scrollSPtuychon;

      public PanelMenuSP_CENTER_2() {
        setOpaque(false);
        scrollSPtuychon = new JScrollPane();
        scrollSPtuychon.getViewport().setBackground(Color.white);

        tableSPtuychon = new JTable();
        modelProductMenuItemRef = new TableModelMenuItemRef(null);
        tableSPtuychon.setModel(modelProductMenuItemRef);
        tableSPtuychon.setRowHeight(23);
        tableSPtuychon.setFont(new Font("Tahoma", 0, 14));
        tableSPtuychon.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
        tableSPtuychon.getColumnModel().getColumn(2).setMaxWidth(100);
        tableSPtuychon.getColumnModel().getColumn(2).setMinWidth(100);
        ((DefaultTableCellRenderer) tableSPtuychon.getTableHeader().getDefaultRenderer())
            .setHorizontalAlignment(JLabel.CENTER);
        tableSPtuychon.setPreferredScrollableViewportSize(new Dimension(200, 320));
        scrollSPtuychon.setViewportView(tableSPtuychon);

        setBorder(BorderFactory.createTitledBorder(null, "Danh sách các SP tùy chọn", TitledBorder.LEFT,
            TitledBorder.TOP, new Font("Tahoma", 1, 14), new Color(126, 0, 0)));
        setOpaque(false);
        setLayout(new BorderLayout());
        add(scrollSPtuychon, BorderLayout.CENTER);
      }
    }
  }

  @Override
  public void addProduct(Product product) {
    boolean flag = false;
    for (int i = 0; i < menuItems.size(); i++) {
      if (menuItems.get(i).getIdentifierId().equals(product.getCode())) {
        menuItems.get(i).setQuantity(menuItems.get(i).getQuantity() + 1);
        flag = true;
        break;
      }
    }
    if (!flag) {
      MenuItem menuItem = new MenuItem();
      menuItem.setMenuItemType(MenuItemType.ByProduct);
      menuItem.setIdentifierId(product.getCode());
      menuItem.setName(product.getName());
      menuItem.setQuantity(1);
      menuItems.add(menuItem);
    }
    loadTableMenuItem();
  }

  private void loadTableMenuItem() {
	  tableSPchinh.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer(){

			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus, int row,
					int column) {
				JLabel label = new JLabel();
				label.setText(value.toString());
				label.setHorizontalAlignment(JLabel.RIGHT);
				if(isSelected){
					  label.setOpaque(true);
					  label.setBackground(table.getSelectionBackground());
				}
				return label;
				}
			
	    	
	    });
    modelMenuItem.setData(menuItems);
    for (int i = 0; i < modelMenuItem.getRowCount(); i++) {
      MenuItem item = (MenuItem) modelMenuItem.getValueAt(i, 0);
      if (item.getPersistableState() == State.DELETED) {
        modelMenuItem.removeRow(i);
        i--;
      }
    }
  }

  public void setMenuItemRef(MenuItemRef menuItemRef) {
    boolean flag = true;
    for (int i = 0; i < menuItems.size(); i++) {
      if (menuItems.get(i) != null)
        if (menuItems.get(i).getIdentifierId().equals(menuItemRef.getCode())) {
          flag = false;
          break;
        }
    }
    if (flag) {
      MenuItem menuItem = new MenuItem();
      menuItem.setMenuItemType(MenuItemType.ByMenuItemRef);
      menuItem.setIdentifierId(menuItemRef.getCode());
      menuItem.setName(menuItemRef.getName());
      menuItem.setQuantity(1);
      menuItems.add(menuItem);
    }
    loadTableMenuItem();
  }

  public void loadTableSPTuyChon(String itemRefCode) {
	  
	  tableSPtuychon.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer(){

			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus, int row,
					int column) {
				JLabel label = new JLabel();
				label.setText(value.toString());
				label.setHorizontalAlignment(JLabel.RIGHT);
				if(isSelected){
					  label.setOpaque(true);
					  label.setBackground(table.getSelectionBackground());
					}
				return label;
				}
			
	    	
	    });
    try {
      MenuItemRef menuItemRef = PromotionModelManager.getInstance().getMenuItemRefByCode(itemRefCode);
      modelProductMenuItemRef.setData(menuItemRef);
    } catch (Exception e) {
      modelProductMenuItemRef.setData(null);
    }
  }

  public void loadTableProductOptionDelete() {
    modelProductMenuItemRef.setData(null);
  }

  public void displayed() {
    canceEdit(false);
    loadTableMenuItem();
    txtTen.setText(menu.getName());
    txtTienBan.setText(MyDouble.valueOf(menu.getTotal()).toString());
    if (menu.getStatus() == Status.Fixed) {
      checkbCoDinh.setSelected(true);
    } else {
      checkbCoDinh.setSelected(false);
    }
  }

  @Override
  public void save() throws Exception {
	  if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {
    if (check()) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
      if (menu == null) {
        menu = new Menu();
        menu.setCode(dateFormat.format(new Date()));
      }
      menu.setMenuItems(menuItems);
      menu.setName(txtTen.getText());
      menu.setTotal(MyDouble.parseDouble(txtTienBan.getText().trim()));
      menu.setCurrencyUnit(((Currency) cbUnitMoney.getSelectedItem()).getCode());
      menu.setType(Type.Voucher);
      Status status = Status.Fixed;
      for (int i = 0; i < menuItems.size(); i++) {
        if (menuItems.get(i).getMenuItemType() == MenuItemType.ByMenuItemRef) {
          status = Status.Options;
          break;
        }
      }
      menu.setStatus(status);
      PromotionModelManager.getInstance().saveMenu(menu);

      saveProduct();
      reset();
    }
    if(reset == true)
    	((DialogMain) getRootPane().getParent()).dispose();
		}
  }

  public void saveProduct() throws Exception {

    Product pro = ProductModelManager.getInstance().getProductByCode(menu.getCode());
    if (pro == null) {
      pro = new Product();
    }
    pro.setName(menu.getName());
    pro.setCode(menu.getCode());
    pro.setCreatedTime(new Date());
    List<ProductTag> productTags = new ArrayList<ProductTag>();
    productTags.add(ProductModelManager.getInstance().getProductTagMenu());
    pro.setProductTags(productTags);
    pro.setUnit("menu");
    pro.setMaker("HKT");
    List<ProductAttribute> productAttributes = new ArrayList<ProductAttribute>();
    for (ProductTag p : productTags) {
      ProductAttribute pa = new ProductAttribute();
      pa.setName(AccountingModelManager.product);
      pa.setValue(p.getTag());
      productAttributes.add(pa);
    }

    ProductAttribute pa = new ProductAttribute();
    pa.setName("BarCode");
    pa.setValue(pro.getCode());
    productAttributes.add(pa);
    pro.setProductAttributes(productAttributes);
    pro = ProductModelManager.getInstance().saveProduct(pro);
    List<ProductPriceType> list = ProductModelManager.getInstance().getProductPriceTypes();
    for (ProductPriceType p : list) {
      ProductPrice productPrice = ProductModelManager.getInstance().getByProductAndProductPriceType(pro.getCode(),
          p.getType(),((Currency) cbUnitMoney.getSelectedItem()).getCode());
      if (productPrice == null) {
        productPrice = new ProductPrice();
      }
      productPrice.setProduct(pro);
      productPrice.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
      productPrice.setProductPriceType(p);
      productPrice.setUnit("menu");
      productPrice.setPrice(MyDouble.parseDouble(txtTienBan.getText()));
      productPrice.setCurrencyUnit(((Currency) cbUnitMoney.getSelectedItem()).getCode());
      ProductModelManager.getInstance().saveProductPrice(productPrice);
    }
  }

  public boolean check() {
    if (txtTen.getText().trim().length() == 0) {
      labelErr.setText("Nhập tên voucher");
      return false;
    }
    if (txtTienBan.getText().trim().length() == 0) {
      labelErr.setText("Nhập tiền bán voucher");
      return false;
    }
    if (menuItems.size() == 0) {
      labelErr.setText("Voucher không có sản phẩm");
      return false;
    }
    try {
      MyDouble.parseDouble(txtTienBan.getText().trim());
    } catch (Exception e) {
      labelErr.setText("Tiền sai định dạng");
      return false;
    }
    labelErr.setText("");
    return true;
  }

  @Override
  public void edit() throws Exception {
	  if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {
			reset = true;
    canceEdit(true);
		}
  }

  private void canceEdit(boolean edit) {
    List<Component> list = getAllComponents(this);
    for (Component c : list) {
      if (!(c instanceof JButton)) {
        c.setEnabled(edit);
      }
    }
  }

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

  @Override
  public void delete() throws Exception {
	  if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {
    if (menu != null) {

      String str = "Xóa Voucher ";
      PanelChoise pnPanel = new PanelChoise(str + menu + "?");
      pnPanel.setName("Xoa");
      DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
      dialog1.setName("dlXoa");
//      dialog1.setSize(500, 280);
//      dialog1.setPreferredSize(new Dimension(550, 230));
      dialog1.setTitle("Xóa Voucher");
      dialog1.setLocationRelativeTo(null);
      dialog1.setModal(true);
      dialog1.setVisible(true);

      if (pnPanel.isDelete()) {
        PromotionModelManager.getInstance().deleteMenu(menu);
        ProductModelManager.getInstance().deleteProductByCode(menu.getCode());
        reset();
        ((DialogMain) getRootPane().getParent()).dispose();
      }
    }
	
	  } else {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		}
  }

  @Override
  public void reset() throws Exception {
    txtTen.setText("");
    txtTienBan.setText("");
    menuItems = new ArrayList<MenuItem>();
    loadTableMenuItem();
    txtQuantityProduct.setText("");
    txtSearch.setText("");
    modelProductMenuItemRef = new TableModelMenuItemRef(null);
    tableSPtuychon.setModel(modelProductMenuItemRef);
    tableSPtuychon.getColumnModel().getColumn(2).setMaxWidth(100);
    tableSPtuychon.getColumnModel().getColumn(2).setMinWidth(100);
    menu = null;

  }

  @Override
  public void refresh() throws Exception {
    displayed();

  }

  @Override
  public void loadTable() {
    
  }
  
  public void createBeginTest() {
    if (MyPanel.isTest) {
      // Tạo đơn vị tiền tệ (3)
      for (int i = 1; i <= 3; i++) {
        Currency currency = new Currency();
        currency.setCode("Mã TT HktTest" + i);
        currency.setName("TT HktTest" + i);
        try {
          LocaleModelManager.getInstance().saveCurrency(currency);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      // Tạo nhóm hàng hóa
      List<ProductTag> productTags = new ArrayList<ProductTag>();
      for (int i = 1; i <= 3; i++) {
        // Nhóm cha 1
        ProductTag productTag1 = new ProductTag();
        productTag1.setCode("Mã NSP HktTest" + i);
        productTag1.setLabel("Nhóm SP HktTest" + i);
        productTag1.setTag("Mã NSP HktTest" + i);
        // Nhóm con 1 - 1
        ProductTag productTag11 = new ProductTag();
        productTag11.setCode("Mã NSP HktTest" + (i + i * 10));
        productTag11.setLabel("Nhóm SP HktTest" + (i + i * 10));
        productTag11.setTag(productTag1.getTag() + ":Mã NSP HktTest" + (i + i * 10));
        // Nhóm con 1 - 1 - 1
        ProductTag productTag111 = new ProductTag();
        productTag111.setCode("Mã NSP HktTest" + (i + i * 110));
        productTag111.setLabel("Nhóm SP HktTest" + (i + i * 110));
        productTag111.setTag(productTag11.getTag() + ":Mã NSP HktTest" + (i + i * 110));
        try {
          ProductModelManager.getInstance().saveProductTag(productTag1);
          ProductModelManager.getInstance().saveProductTag(productTag11);
          productTag111 = ProductModelManager.getInstance().saveProductTag(productTag111);
          productTags.add(productTag111);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
      // Tạo đơn vị sản phẩm
      ProductUnit productUnit = new ProductUnit();
      productUnit.setCode("Mã ĐVSP HktTest1");
      productUnit.setName("ĐVSP HktTest1");
      productUnit.setRate(20);
      try {
        LocaleModelManager.getInstance().saveProductUnit(productUnit);
      } catch (Exception e) {
        e.printStackTrace();
      }
      // Tạo hàng hóa
      for (int i = 1; i <= 3; i++) {
        try {
          Product product = new Product();
          product.setName("Sản phẩm HktTest" + i);
          product.setCode("Mã SP HktTest" + i);
          product.setCreatedTime(new Date());
          product.setNameOther("Ngôn ngữ HktTest" + i);
          product.setProductTags(productTags);
          product.setUnit("ĐVSP HktTest1");
          product.setMaker(ManagerAuthenticate.getInstance().getOrganizationLoginId());
          product = ProductModelManager.getInstance().saveProduct(product);

          // Tạo bảng giá sản phẩm
          ProductPriceType priceType = new ProductPriceType();
          priceType.setType("Kiểu BG HktTest" + i);
          priceType.setName("BG HktTest" + i);
          priceType.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
          priceType = ProductModelManager.getInstance().saveProductPriceType(priceType);

          // Tạo giá sản phẩm
          ProductPrice productPrice = new ProductPrice();
          productPrice.setProduct(product);
          productPrice.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
          productPrice.setProductPriceType(priceType);
          productPrice.setUnit("Mã ĐVSP HktTest" + i);
          productPrice.setPrice(i * 111000);
          productPrice.setCurrencyUnit("Mã TT HktTest" + i);
          ProductModelManager.getInstance().saveProductPrice(productPrice);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      // Tạo nhân viên
      Account account = new Account();
      account.setLoginId("HktTest1");
      account.setPassword("HktTest1");
      account.setEmail("HktTest1@gmail.com");
      try {
        AccountModelManager.getInstance().saveAccount(account);
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      Employee employee = new Employee();
      employee.setLoginId("HktTest1");
      employee.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
      employee.setCode("Mã NV HktTest1");
      employee.setName("NV HktTest1");
      try {
        HRModelManager.getInstance().saveEmployee(employee);
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      // Tạo kho hàng
      Warehouse warehouse = new Warehouse();
      warehouse.setWarehouseId("Mã kho HktTest1");
      warehouse.setName("Kho HktTest1");
      try {
        WarehouseModelManager.getInstance().saveWarehouse(warehouse);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    // super.createBeginTest();
  }

  public void createDataTest() {
    try {
     
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void deleteDataTest() {
    if (!PanelTestAll.runAll) {
      DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
      if (DialogTest.getInstance().isTest()) {
        try {
          LocaleModelManager.getInstance().deleteTest("HktTest");
          AccountModelManager.getInstance().deleteTest("HktTest");
          HRModelManager.getInstance().deleteTest("HktTest");
          PromotionModelManager.getInstance().deleteTest("HktTest");
          ProductModelManager.getInstance().deleteTest("HktTest");
          WarehouseModelManager.getInstance().deleteTest("HktTest");
          AccountingModelManager.getInstance().deleteTest("HktTest");
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  public void update(Object o, Object arg) {
    if(arg.equals(TextPopup.LOAD)){
      panelListProduct.loadGui();
    }
  }
}
