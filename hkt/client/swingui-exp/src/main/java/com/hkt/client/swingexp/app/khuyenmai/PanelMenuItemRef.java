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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swing.widget.GridBagLayoutPanel;
import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.GridLabelLayoutPabel;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.IMyObserver;
import com.hkt.client.swingexp.app.khohang.CatalogProductTag;
import com.hkt.client.swingexp.app.khohang.IPanelListProduct;
import com.hkt.client.swingexp.app.khohang.PanelListProduct;
import com.hkt.client.swingexp.app.khohang.PanelRepositoryProducts2;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.promotion.entity.MenuItemRef;

@SuppressWarnings("serial")
public class PanelMenuItemRef extends JPanel implements IPanelListProduct, IDialogMain, IMyObserver {
  private List<ProductTag> tagsCheckBox;
  private TextPopup<Product> txtSearch;
  private JScrollPane scrollSanPham;
  private ExtendJTextField txtNameMenuItemRef, txtQuantity;
  private IPanelListProduct parentPanel = this;
  private PanelListProduct panelListProduct;
  @SuppressWarnings("rawtypes")
private JComboBox comboGroup;
  private List<ProductTag> listProductGroup = new ArrayList<ProductTag>();
  private JScrollPane scrollPaneProduct;
  private JTable table;
  private JLabel labelError;
  private MenuItemRef menuItemRef;
  private IDialogMain dialogMain;
  private TableModelProductMenuItemRef taItemRef;

  public PanelMenuItemRef(IDialogMain iDialogMain) {
    this.dialogMain = iDialogMain;
    table = new JTable();
    menuItemRef = new MenuItemRef();
    menuItemRef.setProducts(new ArrayList<Product>());
    menuItemRef.setQuantityMax(1);
    taItemRef = new TableModelProductMenuItemRef(new ArrayList<Product>());
    table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
    table.setRowHeight(23);
    table.setFont(new Font("Tahoma", 0, 14));
    table.setModel(taItemRef);
    table.addKeyListener(new KeyAdapter() {

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
          try {
            menuItemRef.getProducts().remove(table.getSelectedRow());
            loadTable();
          } catch (Exception e2) {

          }
        }
      }

    });
    labelError = new JLabel();
    labelError.setFont(new Font("Tahoma", Font.BOLD, 14));
    labelError.setForeground(Color.red);
    setBackground(Color.WHITE);
    setOpaque(false);
    setLayout(new BorderLayout(10, 10));
    add(new LeftPanel(), BorderLayout.LINE_START);
    add(new RightPanel(), BorderLayout.CENTER);
    setSize(Toolkit.getDefaultToolkit().getScreenSize());
    txtSearch.addObserver(this);
    txtSearch.setClearText(true);
  }

  public void setData(MenuItemRef menuItemRef) {
    this.menuItemRef = menuItemRef;
    taItemRef.setData(String.valueOf(menuItemRef.getQuantityMax()), this.menuItemRef.getProducts());
    txtNameMenuItemRef.setName("txtNameMenuItemRef");
    txtNameMenuItemRef.setText(menuItemRef.getName());
    txtQuantity.setText(String.valueOf(menuItemRef.getQuantityMax()));
    canceEdit(false);
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

  class LeftPanel extends JPanel {
    public LeftPanel() {
      setBackground(Color.WHITE);
      setOpaque(false);
      setLayout(new BorderLayout(5, 5));
      JPanel panel = new JPanel();
      panel.setOpaque(false);
      panel.setBackground(Color.WHITE);
      panel.setLayout(new BorderLayout());
      panel.add(createLabel("Tìm kiếm      "), BorderLayout.WEST);
      txtSearch = new TextPopup<Product>(Product.class);
      txtSearch.setPreferredSize(new Dimension(365, 23));
      try {
        List<Product> list1 = ProductModelManager.getInstance().filterProduct();
        txtSearch.setData(list1);
      } catch (Exception e) {
        e.printStackTrace();
      }
      panel.add(txtSearch, BorderLayout.CENTER);
      add(panel, BorderLayout.NORTH);

      JPanel panel2 = new JPanel();
      panel2.setOpaque(false);
      panel2.setLayout(new BorderLayout(5, 5));
      add(panel2, BorderLayout.CENTER);

      panel2.add(new PanelNhomSP(), BorderLayout.LINE_START);

      panel2.add(new ProductList(), BorderLayout.LINE_END);
    }

    public class PanelNhomSP extends JPanel {
      public PanelNhomSP() {
        setOpaque(false);
        setLayout(new BorderLayout());
        add(new PanelNhomSP_START(), BorderLayout.PAGE_START);
        add(new PanelNhomSP_CENTER(), BorderLayout.CENTER);
      }
    }


    private class PanelNhomSP_START extends JPanel {
      private JLabel lbNhomSP;

      @SuppressWarnings({ "rawtypes", "unchecked" })
	public PanelNhomSP_START() {
        try {
          tagsCheckBox = new ArrayList<ProductTag>();
          tagsCheckBox.add(0, null);
          setOpaque(false);
          lbNhomSP = new ExtendJLabel("Nhóm SP:");
          comboGroup = new JComboBox();
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
  						dialog.setIcon("nhomsp1.png");
  						dialog.setName("dlNhomhang");
  						dialog.setTitle("Quản lý nhóm hàng");
  						dialog.setVisible(true);
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
      @SuppressWarnings({ "rawtypes", "unchecked" })
	public PanelNhomSP_CENTER() {
        List<ProductTag> productTags = new ArrayList<ProductTag>();
        setOpaque(false);
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createBevelBorder(BevelBorder.RAISED, null, new Color(204, 204, 204), null, null),
            "Nhóm sản phẩm", TitledBorder.LEFT, TitledBorder.TOP, new Font("Tahoma", 1, 14), new Color(126, 0, 0)));

        try {
          productTags = ProductModelManager.getInstance().getProductTags();
        } catch (Exception e) {
          e.printStackTrace();
        }
        //JPanel panelBot = new JPanel();
        //panelBot.setOpaque(false);
        //add(panelBot, BorderLayout.CENTER);
        setOpaque(false);
        setLayout(new BorderLayout());
        GridBagLayoutPanel bagLayoutPanel = new GridBagLayoutPanel();
        bagLayoutPanel.setOpaque(false);
        //for (final ProductTag productTag : productTags) {
  			for (int i = 0; i < productTags.size(); i++) {
  				final ProductTag productTag = productTags.get(i);
          if (productTag != null) {
            String tg = productTag.getTag();
            if (!tg.contains(":")) {
              final JCheckBox checkBox = new JCheckBox(productTag.getLabel());
              checkBox.setSelected(true);
              // load CB Group sau khi co data
              loadProductTagByCheckBox(productTag);
              comboGroup.setModel(new DefaultComboBoxModel(tagsCheckBox.toArray()));
              checkBox.setOpaque(false);
              checkBox.setFont(new Font("Tahoma", Font.BOLD, 14));
              //panelBot.add(checkBox);
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

    class ProductList extends JPanel {
      public ProductList() {
        try {
          setOpaque(false);
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

  class RightPanel extends JPanel {
    public RightPanel() {
      try {
        setOpaque(false);
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(new Infomation(), BorderLayout.PAGE_START);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        add(scrollPane, BorderLayout.CENTER);
        add(labelError, BorderLayout.PAGE_END);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    class Infomation extends GridLabelLayoutPabel {

      public Infomation() {
        setOpaque(false);
        setBackground(Color.WHITE);
        add(0, 0, createLabel("Tên tùy chọn"));
        txtNameMenuItemRef = new ExtendJTextField();
        txtNameMenuItemRef.setName("txtNameMenuItemRef");
        add(0, 1, txtNameMenuItemRef);
        add(1, 0, createLabel("SL tối đa"));
        txtQuantity = new ExtendJTextField();
        txtQuantity.setName("txtQuantity");
        txtQuantity.setText("1");
        txtQuantity.addCaretListener(new CaretListener() {

          @Override
          public void caretUpdate(CaretEvent e) {
            try {
              Integer.parseInt(txtQuantity.getText());
            } catch (Exception e2) {
              return;
            }
            loadTable();
          }
        });
        add(1, 1, txtQuantity);
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

    boolean flag = true;
    for (int i = 0; i < menuItemRef.getProducts().size(); i++) {
      if (menuItemRef.getProducts().get(i).getCode().equals(product.getCode())) {
        labelError.setText("Sản phẩm đã tồn tại");
        flag = false;
        break;
      }
    }
    if (flag) {
      labelError.setText("");
      product.setNameOther("1");
      menuItemRef.getProducts().add(product);
      loadTable();
    }

  }

  public void loadTable() {
    taItemRef.setData(txtQuantity.getText(), menuItemRef.getProducts());
    table.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer(){

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
  }

  @SuppressWarnings("unused")
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

  @Override
  public void save() throws Exception {
    if (txtNameMenuItemRef.getText().trim().length() == 0) {
      JOptionPane.showMessageDialog(null, "Nhập tên tùy chọn");
      return;
    }
    if (menuItemRef.getProducts().size() == 0) {
      JOptionPane.showMessageDialog(null, "Không có sản phẩm tùy chọn");
      return;
    }
    try {
      double quantity = Double.valueOf(txtQuantity.getText());
      if(quantity <= 0){
        JOptionPane.showMessageDialog(null, "Số lượng sản phẩm phải lớn hơn 0");
        return;
      }
    } catch (Exception e) {
      labelError.setText("Lỗi định dạng dữ liệu");
      return;
    }
   
    if (menuItemRef.getCode() == null) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyddMMHHmmss");
      menuItemRef.setCode(dateFormat.format(new Date()));
    }
    menuItemRef.setName(txtNameMenuItemRef.getText());
    menuItemRef.setQuantityMax(Double.valueOf(txtQuantity.getText()));
    menuItemRef = PromotionModelManager.getInstance().saveMenuItemRef(menuItemRef);
    if (dialogMain instanceof DialogShowMenu) {
      ((DialogShowMenu) dialogMain).setMenuItemRef(menuItemRef);
      reset();
    } else if (dialogMain instanceof DialogShowVoucher) {
      ((DialogShowVoucher) dialogMain).setMenuItemRef(menuItemRef);
     reset();
    }
  }

  @Override
  public void edit() throws Exception {
    canceEdit(true);
  }

  @Override
  public void delete() throws Exception {
    PromotionModelManager.getInstance().deleteMenuItemRef(menuItemRef);
    reset();

  }

  @Override
  public void reset() throws Exception {
    canceEdit(true);
    menuItemRef = new MenuItemRef();
    menuItemRef.setProducts(new ArrayList<Product>());
    menuItemRef.setQuantityMax(1);
    txtQuantity.setText("1");
    txtQuantity.setHorizontalAlignment(JTextField.RIGHT);
    txtNameMenuItemRef.setText("");
    loadTable();
  }

  @Override
  public void refresh() throws Exception {
    setData(menuItemRef);
  }

  @Override
  public void update(Object o, Object arg) {
    if (arg instanceof Product) {
      Product p = (Product)arg;
      addProduct(p);
    }
    if(arg.equals(TextPopup.LOAD)){
      panelListProduct.loadGui();
    }

  }

}
