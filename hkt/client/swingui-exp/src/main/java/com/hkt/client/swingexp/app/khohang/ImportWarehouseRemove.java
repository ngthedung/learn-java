package com.hkt.client.swingexp.app.khohang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.hkt.client.swing.widget.GridBagLayoutPanel;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.config.locale.Currency;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.warehouse.entity.Warehouse;

public class ImportWarehouseRemove extends JPanel {
  private List<ProductTag>   tagsCheckBox;
  private long               idPricingTableType;
  private JTextField         txtQuantityProduct = new JTextField(5);
  private JTextField         txtPriceProduct    = new JTextField(10);
  private TextPopup<Product> textPopup;
  private Product            product;
  private JPanel             parentPanel        = this;
  private ListProductImport  panelListProduct;
  private JComboBox          comboGroup;
  private List<ProductTag>   listProductGroup   = new ArrayList<ProductTag>();
  private JScrollPane        scrollPaneProduct;

  public ImportWarehouseRemove() {
    setBackground(Color.WHITE);
    setLayout(new BorderLayout());
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(1, 2));
    panel.add(new LeftPanel());
    panel.add(new RightPanel());
    add(panel, BorderLayout.CENTER);
    add(new ButtonPanel(), BorderLayout.PAGE_END);

  }

  class ButtonPanel extends JPanel {
    public ButtonPanel() {
      setBackground(Color.WHITE);
      setLayout(new FlowLayout(FlowLayout.RIGHT));
      JButton button1 = new JButton("Điền mới");
      button1.setIcon(new ImageIcon("src/icon/kho/reset1.png"));
      JButton button2 = new JButton("Thêm");
      button2.setIcon(new ImageIcon("src/icon/kho/save1.png"));
      JButton button3 = new JButton("Sửa");
      button3.setIcon(new ImageIcon("src/icon/kho/modify1.png"));
      JButton button4 = new JButton("Xóa");
      button4.setIcon(new ImageIcon("src/icon/kho/delete1.png"));
      JButton button5 = new JButton("Thoát");
      button5.setIcon(new ImageIcon("src/icon/kho/cancel1.png"));
      add(button1);
      add(button2);
      add(button3);
      add(button4);
      add(button5);
    }
  }

  class LeftPanel extends JPanel {
    public LeftPanel() {
      setBackground(Color.WHITE);
      setLayout(new BorderLayout());
      JPanel panel = new JPanel();
      panel.setBackground(Color.WHITE);
      panel.setLayout(new FlowLayout());
      panel.add(createLabel("Tìm kiếm"));
      textPopup = new TextPopup<Product>(Product.class);
      textPopup.setPreferredSize(new Dimension(250, 23));
      try {
        List<Product> list1 = ProductModelManager.getInstance().filterProduct();
        textPopup.setData(list1);
        for (Product e : list1) {
          textPopup.setItem(e);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      panel.add(textPopup);
      panel.add(createLabel("Số lượng"));
      panel.add(txtQuantityProduct);
      panel.add(createLabel("Đơn giá"));
      panel.add(txtPriceProduct);
      add(panel, BorderLayout.PAGE_START);

      JPanel panel2 = new JPanel();
      panel2.setLayout(new GridLayout());
      panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
      add(panel2, BorderLayout.CENTER);
      add(new JLabel("    "), BorderLayout.LINE_START);

      panel2.add(new SelectiveProduct());

      panel2.add(new ProductList());
    }

    class SelectiveProduct extends JPanel {
      public SelectiveProduct() {
        try {
          List<ProductTag> productTags = ProductModelManager.getInstance().getProductTags();
          tagsCheckBox = new ArrayList<ProductTag>();
          tagsCheckBox.add(0, null);
          setBackground(Color.white);
          setLayout(new BorderLayout());

          com.hkt.client.swing.widget.GridBagLayoutPanel panelTop = new com.hkt.client.swing.widget.GridBagLayoutPanel();
          panelTop.setBackground(Color.WHITE);
          panelTop.add(0, 0, createLabel(" "), 2);
          panelTop.add(1, 0, createLabel("Nhóm sản phẩm :"));
          comboGroup = new JComboBox(tagsCheckBox.toArray());
          panelTop.add(1, 1, comboGroup);
          panelTop.add(2, 0, createLabel("Bảng giá :"));
          // Load product khi thay doi item
          comboGroup.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
              ProductTag productTag = (ProductTag) comboGroup.getSelectedItem();
              List<ProductTag> list = new ArrayList<ProductTag>();
              list.add(productTag);
              if (productTag != null) {
                panelListProduct = new ListProductImport(parentPanel);
                panelListProduct.setListProductTag(list);
                panelListProduct.loadGui();
                scrollPaneProduct.setViewportView(panelListProduct);
              } else {
                try {
                  List<ProductTag> tags = ProductModelManager.getInstance().getProductTags();
                  List<ProductTag> listProductG = new ArrayList<ProductTag>();
                  for (ProductTag productTag1 : tags) {
                    if (productTag1.getTag().split(":").length == 2) {
                      listProductG.add(productTag1);
                    }
                  }
                  panelListProduct = new ListProductImport(parentPanel);
                  panelListProduct.setListProductTag(listProductG);
                  panelListProduct.loadGui();
                  scrollPaneProduct.setViewportView(panelListProduct);
                } catch (Exception e2) {
                  e2.printStackTrace();
                }
              }
            }
          });

          List<ProductPriceType> productPrices = ProductModelManager.getInstance().getProductPriceTypes();
          productPrices.add(0, null);
          JComboBox comboGia = new JComboBox(productPrices.toArray());
          panelTop.add(2, 1, comboGia);
          panelTop.add(3, 0, createLabel("Đơn vị tiền :"));
          List<Currency> currencies = LocaleModelManager.getInstance().getCurrencies();
          JComboBox comboMoney = new JComboBox(currencies.toArray());
          panelTop.add(3, 1, comboMoney);
          panelTop.add(4, 0, createLabel(" "), 2);
          add(panelTop, BorderLayout.PAGE_START);

          JPanel panelBot = new JPanel();
          panelBot.setBackground(Color.WHITE);
          panelBot.setBorder(BorderFactory.createTitledBorder("Nhóm sản phẩm"));
          add(panelBot, BorderLayout.CENTER);
          for (final ProductTag productTag : productTags) {
            if (productTag != null) {
              String tg = productTag.getTag();
              if (!tg.contains(":")) {
                final JCheckBox checkBox = new JCheckBox(productTag.getLabel());
                checkBox.setSelected(true);
                // load CB Group sau khi co data
                loadProductTagByCheckBox(productTag);
                comboGroup.setModel(new DefaultComboBoxModel(tagsCheckBox.toArray()));
                checkBox.setBackground(Color.white);
                checkBox.setFont(new Font("Tahoma", Font.BOLD, 14));
                panelBot.add(checkBox);
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
                      panelListProduct = new ListProductImport(parentPanel);
                      panelListProduct.setListProductTag(tagsCheckBox);
                      panelListProduct.loadGui();
                      scrollPaneProduct.setViewportView(panelListProduct);
                    } else {
                      // remove khi unchecked
                      removeProductTagByCheckBox(productTag);
                      comboGroup.setModel(new DefaultComboBoxModel(tagsCheckBox.toArray()));
                      if (tagsCheckBox != null)
                        if (tagsCheckBox.size() > 0)
                          if (tagsCheckBox.get(0) == null) {
                            tagsCheckBox.remove(0);
                          }
                      panelListProduct = new ListProductImport(parentPanel);
                      panelListProduct.setListProductTag(tagsCheckBox);
                      panelListProduct.loadGui();
                      scrollPaneProduct.setViewportView(panelListProduct);
                    }
                  }
                });
              }
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    class ProductList extends JPanel {
      public ProductList() {
        try {
          setBackground(Color.WHITE);
          setLayout(new BorderLayout());
          panelListProduct = new ListProductImport(parentPanel);
          List<ProductTag> tags = ProductModelManager.getInstance().getProductTags();
          List<ProductTag> listProductG = new ArrayList<ProductTag>();
          for (ProductTag productTag : tags) {
            if (productTag.getTag().split(":").length == 2) {
              listProductG.add(productTag);
            }
          }
          panelListProduct.setListProductTag(listProductG);
          panelListProduct.loadGui();
          setBorder(BorderFactory.createTitledBorder("Product list"));
          scrollPaneProduct = new JScrollPane();
          add(scrollPaneProduct, BorderLayout.CENTER);
          scrollPaneProduct.setViewportView(panelListProduct);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void loadProductTagByCheckBox(ProductTag productTagRoot) {
    try {
      String tagRoot = productTagRoot.getTag();
      List<ProductTag> productTags = ProductModelManager.getInstance().findProductTagRoot(tagRoot + ":");
      for (ProductTag productTag : productTags) {
        String tag = productTag.getTag();
        String[] tagSp = tag.split(":");
        if (tagSp.length == 2) {
          String tagChild = tagSp[0] + ":" + tagSp[1];
          ProductTag productTagGet = ProductModelManager.getInstance().getProductTagByTag(tagChild);
          tagsCheckBox.add(tagsCheckBox.size(), productTagGet);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void removeProductTagByCheckBox(ProductTag productTagRoot) {
    try {
      String tagRoot = productTagRoot.getTag();
      List<ProductTag> productTags = ProductModelManager.getInstance().findProductTagRoot(tagRoot + ":");
      for (ProductTag productTag : productTags) {
        String tag = productTag.getTag();
        String[] tagSp = tag.split(":");
        if (tagSp.length > 1) {
          String tagChild = tagSp[0] + ":" + tagSp[1];
          ProductTag productTagGet = ProductModelManager.getInstance().getProductTagByTag(tagChild);
          tagsCheckBox.remove(productTagGet);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  class RightPanel extends JPanel {
    public RightPanel() {
      try {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
        setLayout(new BorderLayout());
        add(new Infomation(), BorderLayout.PAGE_START);

        JScrollPane scrollPane = new JScrollPane();
        String[] columnNames = { "Product", "Quantity", "Price", "Total", "Unit Money" };
        Object[][] data = { { "Fried chicken", new Integer(1), new Integer(10), new Integer(10), "USD" },
            { "Teriyaki", new Integer(1), new Integer(10), new Integer(10), "USD" },
            { "Walnut ricotta", new Integer(1), new Integer(10), new Integer(10), "USD" },
            { "Sandwich", new Integer(1), new Integer(10), new Integer(10), "USD" },
            { "Vodka", new Integer(1), new Integer(15), new Integer(15), "USD" }, };
        JTable table = new JTable(data, columnNames);
        scrollPane.setViewportView(table);
        add(scrollPane, BorderLayout.CENTER);
        add(new JLabel("  "), BorderLayout.LINE_START);
        add(new JLabel("    "), BorderLayout.LINE_END);

        JPanel panelMoney = new JPanel();
        panelMoney.setBackground(Color.WHITE);
        panelMoney.setLayout(new FlowLayout(FlowLayout.LEADING));
        panelMoney.add(createLabel("Tổng tiền"));
        JTextField field = new JTextField(42);
        field.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panelMoney.add(field);
        List<Currency> currencies;
        currencies = LocaleModelManager.getInstance().getCurrencies();
        JComboBox comboMoney = new JComboBox(currencies.toArray());
        panelMoney.add(comboMoney);
        add(panelMoney, BorderLayout.PAGE_END);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    class Infomation extends GridBagLayoutPanel {

      public Infomation() {
        setBackground(Color.WHITE);
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.WHITE);
        panel1.setLayout(new BorderLayout());
        panel1.add(createLabel("  Mã phiếu "), BorderLayout.LINE_START);
        JTextField textField1 = new JTextField(48);
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        textField1.setText(dateFormat.format(new Date()).toString());
        textField1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel1.add(textField1, BorderLayout.CENTER);
        panel1.add(new JLabel("   "), BorderLayout.LINE_END);
        add(0, 0, panel1);

        setBackground(Color.WHITE);
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.WHITE);
        panel2.setLayout(new BorderLayout());
        panel2.add(createLabel("  Tên NV    "), BorderLayout.LINE_START);
        JTextField textField2 = new JTextField(48);
        textField2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel2.add(textField2, BorderLayout.CENTER);
        panel2.add(new JLabel("   "), BorderLayout.LINE_END);
        add(1, 0, panel2);

        setBackground(Color.WHITE);
        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.WHITE);
        panel3.setLayout(new BorderLayout());
        panel3.add(createLabel("  Kho          "), BorderLayout.LINE_START);
        List<Warehouse> warehouses = new ArrayList<Warehouse>();
        try {
           warehouses = WarehouseModelManager.getInstance().findWarehouses();
        } catch (Exception e) {
          
          e.printStackTrace();
        }
        JComboBox comboBox = new JComboBox(warehouses.toArray());
        comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel3.add(comboBox, BorderLayout.CENTER);
        panel3.add(new JLabel("   "), BorderLayout.LINE_END);
        add(2, 0, panel3);

        setBackground(Color.WHITE);
        JPanel panel4 = new JPanel();
        panel4.setBackground(Color.WHITE);
        panel4.setLayout(new BorderLayout());
        panel4.add(createLabel("  Nhân viên"), BorderLayout.LINE_START);
        List<Employee> employees= new ArrayList<Employee>();
        try {
          employees = HRModelManager.getInstance().getEmployees();
        } catch (Exception e) {
          e.printStackTrace();
        }
        JComboBox comboBoxNV = new JComboBox(employees.toArray());
        comboBoxNV.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel4.add(comboBoxNV, BorderLayout.CENTER);
        panel4.add(new JLabel("   "), BorderLayout.LINE_END);
        add(3, 0, panel4);

        setBackground(Color.WHITE);
        JPanel panel5 = new JPanel();
        panel5.setBackground(Color.WHITE);
        panel5.setLayout(new BorderLayout());
        panel5.add(createLabel("  Ngày        "), BorderLayout.LINE_START);
        MyJDateChooser dateChooser = new MyJDateChooser();
        dateChooser.setDate(new Date());
        dateChooser.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel5.add(dateChooser, BorderLayout.CENTER);
        panel5.add(new JLabel("   "), BorderLayout.LINE_END);
        add(4, 0, panel5);
      }
    }
  }

  public JLabel createLabel(String name) {
    JLabel label = new JLabel(name);
    label.setFont(new Font("Tahoma", Font.BOLD, 14));
    return label;
  }

  public JTextField createTextField() {
    JTextField field = new JTextField();
    field.setFont(new Font("Tahoma", Font.PLAIN, 14));
    return field;
  }

  public void addProduct(Product product) {
    if (!textPopup.isEnabled()) {
      return;
    }
    this.product = product;
    JOptionPane.showConfirmDialog(null, "product  " + product.getName());
    txtPriceProduct.setText("");
    txtQuantityProduct.setText("");
    textPopup.requestFocus();
    textPopup.setText("");
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

}
