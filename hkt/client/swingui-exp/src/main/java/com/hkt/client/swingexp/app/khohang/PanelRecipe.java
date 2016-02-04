package com.hkt.client.swingexp.app.khohang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swing.widget.GridBagLayoutPanel;
import com.hkt.client.swingexp.app.banhang.PanelProductPriceType;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelProduct;
import com.hkt.client.swingexp.app.banhang.screen.often.TableEat;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.GridLabelLayoutPabel;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.IMyObserver;
import com.hkt.client.swingexp.app.core.IScreenSales;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.hethong.PanelCurrency;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.khohang.list.TableColumn0;
import com.hkt.client.swingexp.app.khohang.list.TableModelRecipeIngredient;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.config.locale.Currency;
import com.hkt.module.config.locale.ProductUnit;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.restaurant.entity.Recipe;
import com.hkt.module.restaurant.entity.RecipeIngredient;
import com.hkt.module.warehouse.entity.Warehouse;

@SuppressWarnings("serial")
public class PanelRecipe extends MyPanel implements IMyObserver,IDialogMain,IScreenSales {

  public static String                   permission;
  private List<ProductTag>               tagsCheckBox;
  private JTextField                     txtQuantityProduct, txtName;
  private TextPopup<Product>             txtProductDL, txtProductKB;
  private PanelProduct                    panelListProduct;
  @SuppressWarnings("rawtypes")
  private JComboBox                      comboGroup, comboMoney, comboGia;
  private List<ProductTag>               listProductGroup = new ArrayList<ProductTag>();
  private JScrollPane                    scrollPaneProduct;
  private JTextArea                      txtDescription;
  private List<RecipeIngredient>         recipeIngredients;
  private JTable                         table;
  private TableColumn0                   tableColumn0;
  private JLabel                         labelError;
  private Recipe                         recipe;
  private GridBagLayoutPanel bagLayoutPanel;
  @SuppressWarnings("unused")
private boolean                        flagEdit         = false;
  private boolean reset= false;

  public PanelRecipe(boolean flagEdit) throws Exception {
    this.flagEdit = flagEdit;
    if (flagEdit == false) {
      createBeginTest();
    }
    recipe = new Recipe();
    recipeIngredients = new ArrayList<RecipeIngredient>();
    table = new JTable();
    table.setFont(new Font("Tahoma", Font.PLAIN, 14));
    table.setRowHeight(23);
    table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));

    labelError = new JLabel();
    labelError.setFont(new Font("Tahoma", Font.BOLD, 14));
    labelError.setForeground(Color.red);
    txtQuantityProduct = new JTextField(10);
    txtQuantityProduct.setName("txtQuantityProduct");
    txtName = new JTextField();
    txtName.setName("txtName");
    setBackground(Color.WHITE);
    setOpaque(false);
    setLayout(new BorderLayout(0, 0));
    JPanel panel = new JPanel();
    panel.setOpaque(false);
    panel.setLayout(new GridLayout(1, 2, 5, 5));
    panel.add(new LeftPanel(), BorderLayout.LINE_START);
    panel.add(new RightPanel(), BorderLayout.CENTER);
    add(panel, BorderLayout.CENTER);
    txtProductDL.addObserver(this);
  }

  public PanelRecipe(Recipe recipe) throws Exception {
    this.recipe = recipe;
    recipeIngredients = new ArrayList<RecipeIngredient>();
    table = new JTable();
    labelError = new JLabel();
    labelError.setFont(new Font("Tahoma", Font.BOLD, 14));
    labelError.setForeground(Color.red);
    txtQuantityProduct = new JTextField(10);
    txtQuantityProduct.setName("txtQuantityProduct");
    txtQuantityProduct.setHorizontalAlignment(JTextField.RIGHT);
    txtName = new JTextField();
    setBackground(Color.WHITE);
    setOpaque(false);
    setLayout(new BorderLayout());
    JPanel panel = new JPanel();
    panel.setOpaque(false);
    panel.setLayout(new GridLayout(1, 2, 5, 5));
    panel.add(new LeftPanel());
    panel.add(new RightPanel());
    add(panel, BorderLayout.CENTER);
    refresh();
    txtProductDL.addObserver(this);
  }

  
  class LeftPanel extends JPanel {
    public LeftPanel() throws Exception {
    	
    	setOpaque(false);
        setLayout(new BorderLayout(10, 10));
        add(new LEFT_PAGESTART_PanelSearch(), BorderLayout.PAGE_START);
        JPanel main = new JPanel(new GridLayout(1, 2, 5, 0));
        main.setOpaque(false);
        main.add(new SelectiveProduct());
        main.add(new ProductList());
        
        add(main, BorderLayout.CENTER);

    }
  }
  
  private class LEFT_PAGESTART_PanelSearch extends JPanel {
	  private JLabel lbTimKiem, lbSoLuong;
	    public LEFT_PAGESTART_PanelSearch() {
	    	setOpaque(false);
	        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
	        
	        lbTimKiem = new ExtendJLabel("Sản phẩm");
	        lbTimKiem.setPreferredSize(new Dimension(80, 23));
	        
	        lbSoLuong = new ExtendJLabel("Số lượng");
	        lbSoLuong.setHorizontalAlignment(JLabel.CENTER);
	        lbSoLuong.setPreferredSize(new Dimension(80, 23));
	        
	        txtProductDL = new TextPopup<Product>(Product.class);
	        txtProductDL.setName("txtProductDL");
	        txtProductDL.setPreferredSize(new Dimension(353, 23));
	        
	        txtQuantityProduct = new ExtendJTextField(5);
	        txtQuantityProduct.setHorizontalAlignment(JTextField.RIGHT);
	        txtQuantityProduct.setName("txtQuantityProduct");
	        txtProductDL.setComponentFocus(txtQuantityProduct);
	        
	        add(lbTimKiem);
	        add(txtProductDL);
	        add(lbSoLuong);
	        add(txtQuantityProduct);
	    }
  }

	class SelectiveProduct extends JPanel {
	public SelectiveProduct() throws Exception {
    	  this.setOpaque(false);
          this.setLayout(new BorderLayout(0, 10));
          this.add(new LLS_PageStart_ChoiceComboBox(), BorderLayout.NORTH);
          this.add(new PanelNhomSP_CENTER(), BorderLayout.CENTER);
      }
      
      private class LLS_PageStart_ChoiceComboBox extends GridBagLayoutPanel {
    	  private JLabel lbNhomSP, lbBangGia, lbDVTien;
    	  
    	  public LLS_PageStart_ChoiceComboBox() throws Exception {
    	        setOpaque(false);
    	        
    	        try {
    	            List<ProductTag> productTags = ProductModelManager.getInstance().getProductTags();
    	            tagsCheckBox = new ArrayList<ProductTag>();
    	            tagsCheckBox.add(0, null);
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	          }
    	        
    	        lbNhomSP = new ExtendJLabel("Nhóm SP ");
    	        lbNhomSP.setPreferredSize(new Dimension(55, 23));

    	        lbBangGia = new ExtendJLabel("Bảng giá ");
    	        lbBangGia.setPreferredSize(new Dimension(55, 23));

    	        lbDVTien = new ExtendJLabel("ĐV Tiền ");
    	        lbDVTien.setPreferredSize(new Dimension(55, 23));
    	        
    	        comboGroup = new JComboBox(tagsCheckBox.toArray());
    	        comboGroup.setPreferredSize(new Dimension(200, 23));
    	          comboGroup.setName("comboGroup");
    	          comboGroup.addItemListener(new ItemListener() {

    	              @Override
    	              public void itemStateChanged(ItemEvent e) {
    	                ProductTag productTag = (ProductTag) comboGroup.getSelectedItem();
    	                List<ProductTag> list = new ArrayList<ProductTag>();
    	                list.add(productTag);
    	                if (productTag != null) {
    	                  panelListProduct.setListProductTag(list);
    	                  try {
    	                    panelListProduct.loadGui();
    	                  } catch (Exception e2) {
    	                  }
    	                } else {
    	                  try {
    	                    List<ProductTag> tags = ProductModelManager.getInstance().getProductTags();
    	                    List<ProductTag> listProductG = new ArrayList<ProductTag>();
    	                    for (ProductTag productTag1 : tags) {
    	                      if (productTag1.getTag().split(":").length == 2) {
    	                        listProductG.add(productTag1);
    	                      }
    	                    }
    	                    
    	                    panelListProduct.setListProductTag(listProductG);
    	                    try {
    	                      panelListProduct.loadGui();
    	                    } catch (Exception e2) {
    	                    }
    	                    
    	                  } catch (Exception e2) {
    	                    e2.printStackTrace();
    	                  }
    	                }
    	              }
    	            });
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
    	      						
    	      						tagsCheckBox.clear();
    	  							loadlistTags();
    	  							panelListProduct.setListProductTag(tagsCheckBox);
    	  					try {
    	  						panelListProduct.loadGui();
    	  					} catch (Exception e1) {
    	  						e1.printStackTrace();
    	  					}
    	          				} catch (Exception e1) {
    	          					e1.printStackTrace();
    	          				}
    	              		}}
    	              		});
    	            
    	            List<Currency> currencies = LocaleModelManager.getInstance().getCurrencies();
    	            comboMoney = new JComboBox(currencies.toArray());
    	            comboMoney.addMouseListener(new MouseAdapter() {
    					public void mouseClicked(MouseEvent e)
    					{
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
    									comboMoney.setModel(new DefaultComboBoxModel(LocaleModelManager.getInstance().getCurrencies().toArray()));
    								} catch (Exception e1) {
    									comboMoney.setModel(new DefaultComboBoxModel());
    								}
    							} catch (Exception e1) {
    								e1.printStackTrace();
    							}
    						}
    					}
    				});
    	            
    	            List<ProductPriceType> productPrices = ProductModelManager.getInstance().getProductPriceTypes();
    	            productPrices.add(0, null);
    	            comboGia = new JComboBox(productPrices.toArray());
    	            comboGia.addMouseListener(new MouseAdapter() {
    	      	    	public void mouseClicked(MouseEvent e) {
    	      	    		if(e.getButton() == MouseEvent.BUTTON3)
    	      	    		{
    	      	    			PanelProductPriceType pnProductPriceType;
    	      					try {
    	      						pnProductPriceType = new PanelProductPriceType();
    	      						pnProductPriceType.setName("QuanLyBangGia");
    	      						DialogMain dialog = new DialogMain(pnProductPriceType);
    	      						dialog.setName("dlQuanLyBangGia");
    	      						dialog.setIcon("price1.png");
    	      						dialog.setTitle("Quản lý bảng giá");
    	      						dialog.setVisible(true);
    	      						
    	      						try {
    	    							comboGia.setModel(new DefaultComboBoxModel(ProductModelManager.getInstance().getProductPriceTypes().toArray()));
    	    						} catch (Exception e1) {
    	    							comboGia.setModel(new DefaultComboBoxModel());
    	    						}
    	      					} catch (Exception e1) {
    	      						e1.printStackTrace();
    	      					}
    	      	    		}}
    	      	    		});
    	            
    	            MyGroupLayout layout = new MyGroupLayout(this);
    	            
    	            add(0, 0, lbNhomSP);
    	            add(0, 1, comboGroup);
    	            add(1, 0, lbBangGia);
    	            add(1, 1, comboGia);
    	            add(2, 0, lbDVTien);
    	            add(2, 1, comboMoney);
    	  }
      }

    // Create Panel GroupProductTag
	public class PanelNhomSP_CENTER extends JPanel {

	public PanelNhomSP_CENTER() {
       
        setOpaque(false);
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createBevelBorder(BevelBorder.RAISED, null, new Color(204, 204, 204), null, null),
            "Nhóm sản phẩm", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", 1, 14), new Color(126, 0, 0)));

        setOpaque(false);
        setLayout(new BorderLayout());
        bagLayoutPanel = new GridBagLayoutPanel();
        bagLayoutPanel.setOpaque(false);
        loadlistTags();
        add(bagLayoutPanel, BorderLayout.PAGE_START);
      }
    }
	}
	
	private void loadlistTags(){
		 List<ProductTag> productTags = new ArrayList<ProductTag>();

	        try {
	          productTags = ProductModelManager.getInstance().getProductTags();
	        } catch (Exception e) {
	          e.printStackTrace();
	        }
        for (int i = 0; i < productTags.size(); i++) {
          final ProductTag productTag = productTags.get(i);
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
                    panelListProduct.setListProductTag(tagsCheckBox);
                    try {
                      panelListProduct.loadGui();
                    } catch (Exception e2) {
                    }
                  } else {
                    // remove khi unchecked
                    removeProductTagByCheckBox(productTag);
                    comboGroup.setModel(new DefaultComboBoxModel(tagsCheckBox.toArray()));
                    if (tagsCheckBox != null)
                      if (tagsCheckBox.size() > 0)
                        if (tagsCheckBox.get(0) == null) {
                          tagsCheckBox.remove(0);
                        }
                    panelListProduct.setListProductTag(tagsCheckBox);
                    try {
                      panelListProduct.loadGui();
                    } catch (Exception e2) {
                    }
                  }
                }
              });
            }
          }
        }
	}
    
	private class ProductList extends JPanel {
      public ProductList() {
        try {
          setOpaque(false);
          setBackground(Color.WHITE);
          setLayout(new BorderLayout());
          panelListProduct = new PanelProduct(PanelRecipe.this);
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
      if(tagsCheckBox.get(0)!=null){
        tagsCheckBox.add(0, null);
      }
    } catch (Exception e) {
      tagsCheckBox.add(0, null);
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

  private class RightPanel extends JPanel {
    public RightPanel() {
      try {
        setOpaque(false);
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(new Infomation(), BorderLayout.PAGE_START);
        JScrollPane scrollPane = new JScrollPane();
        loadTable();
        scrollPane.setViewportView(table);
        add(scrollPane, BorderLayout.CENTER);
        add(labelError, BorderLayout.PAGE_END);
        table.getColumnModel().getColumn(0).setMaxWidth(50);

        //
        table.addKeyListener(new KeyAdapter() {
          @Override
          public void keyPressed(KeyEvent evt) {
            try {
              if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
                tableColumn0.showColumn0();
                RecipeIngredient recipeIngredient = (RecipeIngredient) table.getValueAt(table.getSelectedRow(), 0);
                tableColumn0.hideColumn0();
                if (!recipeIngredient.getProductCode().equals("")) {
                  recipeIngredients.remove(recipeIngredient);
                }
              }
              loadTable();
            } catch (Exception e) {
              e.printStackTrace();
              return;
            }
          }
        });
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    private class Infomation extends GridLabelLayoutPabel {

      public Infomation() {
        setOpaque(false);
        setBackground(Color.WHITE);
        add(0, 0, createLabel("Sp khai báo"));
        txtProductKB = new TextPopup<Product>(Product.class);
        txtProductKB.setName("txtProductKB");
        try {
          List<Product> list1 = ProductModelManager.getInstance().filterProduct();
          txtProductKB.setData(list1);
        } catch (Exception e) {
        }
        add(0, 1, txtProductKB);

        add(1, 0, createLabel("Tên định lượng"));
        add(1, 1, txtName);

        add(2, 0, createLabel("Mô tả"));
        txtDescription = new JTextArea(3, 20);
        txtDescription.setBorder(BorderFactory.createEtchedBorder());
        add(2, 1, txtDescription);
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
    RecipeIngredient recipeIngredient = new RecipeIngredient();
    recipeIngredient.setProductCode(product.getCode());
    recipeIngredient.setCreatedBy(new MyDouble(product.getPrice()).toString());
    if (txtQuantityProduct.getText().trim().length() > 0) {
      recipeIngredient.setQuantity(Double.parseDouble(txtQuantityProduct.getText().trim()));
    } else {
      recipeIngredient.setQuantity(1);
    }
    recipeIngredient.setUnit(product.getUnit());
    if (recipeIngredients.size() == 0) {
      recipeIngredients.add(recipeIngredient);
    } else {
      boolean gross = true;
      for (int i = 0; i < recipeIngredients.size(); i++) {
        RecipeIngredient ingredient = recipeIngredients.get(i);
        if (ingredient.getProductCode().equals(recipeIngredient.getProductCode())) {
          ingredient.setQuantity(ingredient.getQuantity() + recipeIngredient.getQuantity());
          gross = false;
        }
      }
      if (gross == true) {
        recipeIngredients.add(recipeIngredient);
      }
    }
    loadTable();
    txtQuantityProduct.setText("1");
    txtProductDL.requestFocus();
    txtProductDL.setText("");
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
      try {
        panelListProduct.setListProductTag(listProductGroup);
        panelListProduct.loadGui();
      } catch (Exception e2) {
      }
     
    }
  }

  private boolean check() {
	  
	  if(recipe.getProductCode() ==null){
		  if (txtProductKB.getItem() == null ) {
		      labelError.setText("Chọn sản phẩm cần định lượng !");
		      return false;
		    }
		    if (txtProductKB.getText().trim().length() == 0) {
		      labelError.setText("Chọn sản phẩm cần định lượng !");
		      return false;
		    }
		    if (recipeIngredients.size() == 0) {
		      labelError.setText("Không có sản phẩm định lượng !");
		      return false;
		    }
		    labelError.setText("");
		    return true;
	  }else {
		  if (recipe.getProductCode().equals("") ) {
		      labelError.setText("Chọn sản phẩm cần định lượng !");
		      return false;
		    }
		    if (txtProductKB.getText().trim().length() == 0) {
		      labelError.setText("Chọn sản phẩm cần định lượng !");
		      return false;
		    }
		    if (recipeIngredients.size() == 0) {
		      labelError.setText("Không có sản phẩm định lượng !");
		      return false;
		    }
		    labelError.setText("");
		    return true;
	}
    
  }

  public void setData1(Recipe recipe) {

    this.recipe = recipe;
    try {
      Product p = ProductModelManager.getInstance().getProductByCode(recipe.getProductCode());
      txtProductKB.setObject(p);
      txtProductKB.setItem(p);
      txtProductKB.setText(p.toString());
      txtProductKB.setObject(null);
      txtName.setText(recipe.getName());
      txtDescription.setText(recipe.getDescription());

      for (int i = 0; i < recipe.getRecipeIngredients().size(); i++) {
        recipeIngredients.add(recipe.getRecipeIngredients().get(i));
      }
      loadTable();
      setEnableCompoment(false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void save() throws Exception {

    if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này!");
      return;
    }
    try {
      if (check()) {
        if (recipe.getProductCode() == null) {
          recipe.setProductCode(txtProductKB.getItem().getCode());

        } else {
          recipe.setProductCode(recipe.getProductCode());
        }
        recipe.setName(txtName.getText());
        recipe.setDescription(txtDescription.getText());
        recipe.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
        recipe.setRecipeIngredients(recipeIngredients);
        recipe = RestaurantModelManager.getInstance().saveRecipe(recipe);
        
        Product p = ProductModelManager.getInstance().getProductByCode(recipe.getProductCode());
        p.setRecipe(true);
        ProductModelManager.getInstance().saveProduct(p);
        if(reset == true)
      	  ((DialogMain) getRootPane().getParent()).dispose();
        else
        {
        	if (recipe != null)
          reset();
        }
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void edit() throws Exception {

    if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này!");
      return;
    }

    try {
      if (recipe == null) {
        JOptionPane.showMessageDialog(null, "Không có sản phẩm cần định lượng");
        return;
      }
      setEnableCompoment(true);
      reset = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void delete() throws Exception {

    if (UIConfigModelManager.getInstance().getPermission(permission) != Capability.ADMIN) {
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này!");
      return;
    }
    try {

      String str = "Xóa ";
      PanelChoise pnPanel = new PanelChoise(str + recipe + "?");
      pnPanel.setName("Xoa");
      DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
      dialog1.setName("dlXoa");
//      dialog1.setSize(500, 280);
//      dialog1.setPreferredSize(new Dimension(550, 230));
      dialog1.setTitle("Xóa định lượng");
      dialog1.setLocationRelativeTo(null);
      dialog1.setModal(true);
      dialog1.setVisible(true);

      if (pnPanel.isDelete()) {
      	Product p = ProductModelManager.getInstance().getProductByCode(recipe.getProductCode());
        p.setRecipe(false);
        ProductModelManager.getInstance().saveProduct(p);
        RestaurantModelManager.getInstance().deleteRecipe(recipe);
        ((DialogMain) getRootPane().getParent()).dispose();
//        reset();
      } else if (pnPanel.isDelete() == false) {
        refresh();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void reset() throws Exception {
    txtProductKB.setItem(null);
    txtProductKB.setObject(null);
    recipe = new Recipe();
    txtProductKB.setText("");
    txtName.setText("");
    txtDescription.setText("");
    recipeIngredients = new ArrayList<RecipeIngredient>();
    loadTable();
    setEnableCompoment(true);
    txtProductKB.setEnabled(true);
  }

  private void setEnableCompoment(boolean value) {
    txtProductKB.setEnabled(false);
    txtName.setEnabled(value);
    txtDescription.setEnabled(value);
    table.setEnabled(value);
  }

  @Override
  public void refresh() throws Exception {
    if (recipe != null) {
      txtName.setText(recipe.getName());
      txtDescription.setText(recipe.getDescription());
      Product p = ProductModelManager.getInstance().getProductByCode(recipe.getProductCode());
      txtProductKB.setObject(p);
      txtProductKB.setItem(p);
      txtProductKB.setText(p.toString());
      recipeIngredients = recipe.getRecipeIngredients();
      labelError.setText("");
      loadTable();
      setEnableCompoment(false);
    }
  }

  public void loadTable() {
    TableModelRecipeIngredient modelRecipeIngredient = new TableModelRecipeIngredient(recipeIngredients);
    
    table.setModel(modelRecipeIngredient);
    
    tableColumn0 = new TableColumn0(table);
    tableColumn0.hideColumn0();
    table.getColumnModel().getColumn(0).setMaxWidth(50);

    table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer(){

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			JLabel label = new JLabel();
			label.setText(value.toString());
			label.setFont(new Font("Tahoma", Font.PLAIN, 14));
			label.setHorizontalAlignment(JLabel.RIGHT);
			if(isSelected){
			  label.setOpaque(true);
			  label.setBackground(table.getSelectionBackground());
			}
			return label;
			}
    });
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
  public void setTable(TableEat tableEat) {
  }

  @Override
  public void update(Object o, Object arg) {
    if (arg instanceof String || arg instanceof Product) {
      addProduct(txtProductDL.getItem());
    }
    
  }

  @Override
  public void setListProduct(List<Product> list) {
    txtProductDL.setData(list);
    
  }
}
