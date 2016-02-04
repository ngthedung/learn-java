package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.hkt.client.swingexp.app.component.GroupLayoutPanel;
import com.hkt.client.swingexp.app.core.IScreenSales;
import com.hkt.client.swingexp.app.khohang.TableModelProduct;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductAttribute;
import com.hkt.module.product.entity.ProductTag;

public class PanelProduct extends JPanel {
  private List<JLabel> listP;
  private List<JTable> listT;
  private List<ProductTag> listProductTags;
  private List<Product> listProducts;
  private boolean flag = true;
  private int size = 0;
  private JScrollPane scrollPane;
  private IScreenSales iScreenSales;
  private String pricingType="";
  private String curency="";
  private boolean price = false;

  public PanelProduct(IScreenSales iScreenSales) {
    this.iScreenSales = iScreenSales;
    this.setLayout(new GridLayout());
    scrollPane = new JScrollPane();
    this.add(scrollPane);
    
    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        if (flag) {
          try {
            loadGui();
          } catch (Exception e2) {
        	  //e2.printStackTrace();
          }
        }
      }
    });
  }

  public void loadGui() throws Exception {
	
	Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
    try {
	  pricingType = profile.get(DialogConfig.ProductPriceType).toString();
	} catch (Exception e) {
	}
	try {
	  curency = profile.get(DialogConfig.Currency).toString();
	} catch (Exception e) {
	}
	try {
	  if(profile.get("Product")!=null && profile.get("Product").equals("true")){
        if(iScreenSales instanceof ScreenOften){
		  price = true;
		}
	  }
	} catch (Exception e) {
	}
    if (listProductTags == null) {
      listProductTags = new ArrayList<ProductTag>();
      if (profile != null) {
        List<String> strs = (List<String>) profile.get(DialogConfig.ProductTag);
        if (strs == null || strs.isEmpty()) {
          listProductTags = ProductModelManager.getInstance().getRootTags();
        } else {
          for (String str : strs) {
            List<ProductTag> productTags = ProductModelManager.getInstance().findProductTagRoot(str);
            if (productTags != null) {
              listProductTags.addAll(productTags);
            }
          }
          if (PromotionModelManager.getInstance().searchMenu().size() > 0) {
            listProductTags.add(0, ProductModelManager.getInstance().getProductTagMenu());
          }
        }

      } else {
        listProductTags = ProductModelManager.getInstance().getRootTags();
      }
    }
		Collections.sort(listProductTags, new Comparator<ProductTag>() {
			@Override
			public int compare(ProductTag it1, ProductTag it2) {
				if(it1==null || it2==null){
					return 0;
				}
				if (it1.getPriority() > it2.getPriority()) {
					return 1;
				} else {
					if (it1.getPriority() < it2.getPriority()) {
						return -1;
					} else {
						return 0;
					}
				}

			}
		});

    listProducts = new ArrayList<Product>();
    listP = new ArrayList<JLabel>();
    listT = new ArrayList<JTable>();
    GroupLayoutPanel panel = new GroupLayoutPanel(); 
    panel.setPreferredSize(new Dimension(scrollPane.getWidth() - 5, scrollPane.getHeight() - 5));
    panel.setBackground(Color.white);
    size = 0; 
    for (int i = 0; i < listProductTags.size(); i++) {
      if (listProductTags.get(i) != null) {
        JPanel panel2 = new JPanel(new BorderLayout());
        final JLabel label3 = new JLabel("+");
        label3.setOpaque(true);
        label3.setBackground(Color.PINK);
        label3.setPreferredSize(new Dimension(10, 25));
        panel2.add(label3, BorderLayout.WEST);
        listP.add(label3);
        JLabel label = new JLabel(" " + listProductTags.get(i).getLabel());
        if (label.getText().equals(" Nhóm SP HktTest1")) {
          label3.setName("lbHH");
        }
        panel2.setOpaque(false);
        panel2.add(label, BorderLayout.CENTER);
        label.setFont(new Font("Tahoma", Font.BOLD, 14));
        label.setForeground(Color.black);
        panel.addComponent(panel2);
        List<Product> list1 = null;
        if (listProductTags.get(i).getId() > 0) {
          list1 = ProductModelManager.getInstance().findByProductTag(listProductTags.get(i).getTag());
        }
        if (!listProducts.containsAll(list1)) {
          listProducts.addAll(list1);
        }
        final JTable table;
        if(price){
        	table = new JTable(new TableModelProduct(listProductTags.get(i).getTag(), pricingType, curency));
        	table.getColumnModel().getColumn(2).setMaxWidth(70);
            table.getColumnModel().getColumn(2).setMinWidth(70);
        }else {
        	table = new JTable(new TableModelProduct(list1));
		}
        
        table.setName("tbProduct");
        table.setVisible(false);
        table.setRowHeight(23);
        table.setForeground(Color.black);
        table.getColumnModel().getColumn(1).setMaxWidth(50);
        table.getColumnModel().getColumn(1).setMinWidth(50);
        
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.addMouseListener(new MouseAdapter() {

          @Override
          public void mouseClicked(MouseEvent e) {
            JTable table = (JTable) e.getSource();
            for (int i = 0; i < listT.size(); i++) {
              if (!listT.get(i).toString().equals(table.toString())) {
                listT.get(i).clearSelection();
              }
            }
            int click = 2;
            JTable ta = (JTable) e.getSource();
            String value = ta.getValueAt(ta.getSelectedRow(), ta.getSelectedColumn()).toString();
            if (value.indexOf("HktTest") >= 0 && e.getButton() == 3) {
              click = 1;
            }
            if (e.getClickCount() >= click) {
              if (table.getSelectedRow() >= 0) {
                Product product = (Product) table.getValueAt(table.getSelectedRow(), 0);
                iScreenSales.addProduct(product);
              }
            }
            label3.requestFocus();
          }

          @Override
          public void mouseDragged(MouseEvent e) {
            JTable table = (JTable) e.getSource();
            for (int i = 0; i < listT.size(); i++) {
              if (!listT.get(i).toString().equals(table.toString())) {
                listT.get(i).clearSelection();
              }
            }
          }

          @Override
          public void mousePressed(MouseEvent e) {
            JTable table = (JTable) e.getSource();
            for (int i = 0; i < listT.size(); i++) {
              if (!listT.get(i).toString().equals(table.toString())) {
                listT.get(i).clearSelection();
              }
            }
          }
        });
        listT.add(table);
        panel.addComponent(table);
        List<ProductTag> listPG = ProductModelManager.getInstance().findProductTagRoot(listProductTags.get(i).getTag());
        JPanel list2 = getPanel(listPG);
        final JPanel aa = new JPanel(new BorderLayout());
        aa.setBackground(Color.white);
        aa.add(new JLabel("  "), BorderLayout.WEST);
        aa.add(list2, BorderLayout.CENTER);
        panel.addComponent(aa);
        aa.setVisible(false);
        size = size + panel2.getPreferredSize().height + table.getPreferredSize().height
            + list2.getPreferredSize().height;
        label.addMouseListener(new MouseAdapter() {

          @Override
          public void mouseClicked(MouseEvent e) {
            if (label3.getText().equals("+")) {
              table.setVisible(true);
              label3.setText("-");
            } else {
              table.setVisible(false);
              label3.setText("+");
            }
          }
        });
        label3.addMouseListener(new MouseAdapter() {

          @Override
          public void mouseClicked(MouseEvent e) {
            if (label3.getText().equals("+")) {
              table.setVisible(true);
              label3.setText("-");
            } else {
              table.setVisible(false);
              label3.setText("+");
            }
          }
        });
        table.addComponentListener(new ComponentAdapter() {

          @Override
          public void componentHidden(ComponentEvent e) {
            aa.setVisible(false);
          }

          @Override
          public void componentShown(ComponentEvent e) {
            if (label3.getText().equals("-")) {
              aa.setVisible(true);
            }
          }
        });
      }
    }
    panel.setPreferredSize(new Dimension(260, size));
    JPanel aa = new JPanel(new BorderLayout());
    aa.setBackground(Color.white);
    aa.add(new JLabel(" "), BorderLayout.WEST);
    aa.add(new JLabel(" "), BorderLayout.NORTH);
    aa.add(panel, BorderLayout.CENTER);
    scrollPane.setViewportView(aa);
    flag = false;
    iScreenSales.setListProduct(listProducts);
  }

  private JPanel getPanel(List<ProductTag> list) throws Exception {
    GroupLayoutPanel panel = new GroupLayoutPanel();
    panel.setBackground(Color.WHITE);
    int h = 0;
    for (int i = 0; i < list.size(); i++) {
      JPanel panel2 = new JPanel(new BorderLayout());
      final JLabel label3 = new JLabel("+");
      label3.setOpaque(true);
      label3.setBackground(Color.PINK);
      label3.setPreferredSize(new Dimension(10, 25));
      panel2.add(label3, BorderLayout.WEST);
      listP.add(label3);
      JLabel label = new JLabel(" " + list.get(i).toString());
      panel2.setOpaque(false);
      panel2.add(label, BorderLayout.CENTER);
      label.setFont(new Font("Tahoma", Font.BOLD, 14));
      label.setForeground(Color.black);
      panel.addComponent(panel2);
      List<Product> list1 = ProductModelManager.getInstance().findByProductTag(list.get(i).getTag());
      if (!listProducts.containsAll(list1)) {
        listProducts.addAll(list1);
      }
      final JTable table;
      if(price){
      	table = new JTable(new TableModelProduct(list.get(i).getTag(), pricingType, curency));
      	table.getColumnModel().getColumn(2).setMaxWidth(70);
        table.getColumnModel().getColumn(2).setMinWidth(70);
      }else {
      	table = new JTable(new TableModelProduct(list1));
		}
      table.setRowHeight(23);
      table.getColumnModel().getColumn(1).setMaxWidth(50);
      table.getColumnModel().getColumn(1).setMinWidth(50);
      
      table.setFont(new Font("Tahoma", Font.PLAIN, 14));
      table.setForeground(Color.black);
      table.addMouseListener(new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
          for (int i = 0; i < listT.size(); i++) {
            if (!listT.get(i).toString().equals(table.toString())) {
              listT.get(i).clearSelection();
            }
          }
          if (e.getClickCount() >= 2) {
            if (table.getSelectedRow() >= 0) {
            	if(label3.isEnabled()){
              Product product = (Product) table.getValueAt(table.getSelectedRow(), 0);
              iScreenSales.addProduct(product);
            	}
            }
          }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
          for (int i = 0; i < listT.size(); i++) {
            if (!listT.get(i).toString().equals(table.toString())) {
              listT.get(i).clearSelection();
            }
          }
        }

        @Override
        public void mousePressed(MouseEvent e) {
          for (int i = 0; i < listT.size(); i++) {
            if (!listT.get(i).toString().equals(table.toString())) {
              listT.get(i).clearSelection();
            }
          }
        }
      });
      listT.add(table);
      panel.addComponent(table);
      table.setVisible(false);
      List<ProductTag> listPG = ProductModelManager.getInstance().findProductTagRoot(list.get(i).getTag());
      final JPanel list2 = getPanel(listPG);
      h = h + panel2.getPreferredSize().height + table.getPreferredSize().height + 15 + list2.getPreferredSize().height;
      panel.addComponent(list2);
      list2.setVisible(false);
      label.addMouseListener(new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
          if (label3.getText().equals("+")) {
            table.setVisible(true);
            label3.setText("-");
          } else {
            table.setVisible(false);
            label3.setText("+");
          }
        }
      });
      label3.addMouseListener(new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
          if (label3.getText().equals("+")) {
            table.setVisible(true);
            label3.setText("-");
          } else {
            table.setVisible(false);
            label3.setText("+");
          }
        }
      });
      table.addComponentListener(new ComponentAdapter() {

        @Override
        public void componentHidden(ComponentEvent e) {
          list2.setVisible(false);
          label3.setText("+");
        }

        @Override
        public void componentShown(ComponentEvent e) {
          if (label3.getText().equals("-")) {
            list2.setVisible(true);
          }
        }
      });
      panel.addComponentListener(new ComponentAdapter() {

        @Override
        public void componentHidden(ComponentEvent e) {
          table.setVisible(false);
          label3.setText("+");
        }

        @Override
        public void componentShown(ComponentEvent e) {
          if (label3.getText().equals("-")) {
            table.setVisible(true);
          }
        }
      });

    }
    panel.setPreferredSize(new Dimension(260, h));
    return panel;
  }

  public void opendAll(boolean flag) {
    if (!flag) {
      for (int i = 0; i < listP.size(); i++) {
        listP.get(i).setText("+");
      }
      for (int i = 0; i < listT.size(); i++) {
        listT.get(i).setVisible(false);
      }
    } else {
      for (int i = 0; i < listP.size(); i++) {
        listP.get(i).setText("-");
      }
      for (int i = 0; i < listT.size(); i++) {
        listT.get(i).setVisible(true);
      }
    }
  }

  public void setListProductTag(List<ProductTag> list) {
    this.listProductTags = list;

  }
}
