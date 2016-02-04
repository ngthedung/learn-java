package com.hkt.client.swingexp.app.banhang;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.hkt.client.swingexp.app.banhang.list.TableListProductPriceType;
import com.hkt.client.swingexp.app.core.DialogList;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.khohang.list.TableListProduct;
import com.hkt.client.swingexp.app.thuchi.CheckBoxProductGroup;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.product.entity.ProductTagList;

@SuppressWarnings("serial")
public class PanelFilterGroupProduct extends JPanel implements IDialogResto {

  private String name;
  private List<CheckBoxProductGroup> listCheckBox = new ArrayList<CheckBoxProductGroup>();
  private List<ProductTag> listProductTags = new ArrayList<ProductTag>();
  private JPanel panel = new JPanel();
  private JScrollPane scrollPane = new JScrollPane();
  private boolean flagSort = false;
  private JCheckBox checkBox = new JCheckBox("Tất cả");
  private int h = 12;
  int y = 0;

  public PanelFilterGroupProduct(String name) {
    this.name = name;
    setOpaque(false);
    checkBox.setOpaque(false);
    checkBox.setName("chbTatCa");
    panel.setLayout(null);

    List<ProductTag> list;
    try {
      list = ProductModelManager.getInstance().getProductTags();
    } catch (Exception e) {
      list = new ArrayList<ProductTag>();
    }

    checkBox.setFont(new Font("Tahoma", Font.BOLD, 14));

    checkBox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        JCheckBox jcb = (JCheckBox) e.getSource();
        if (jcb.isSelected()) {
          for (int i = 0; i < listCheckBox.size(); i++) {
            listCheckBox.get(i).setSelected(true);
          }
        } else {
          for (int i = 0; i < listCheckBox.size(); i++) {
            listCheckBox.get(i).setSelected(false);
          }
        }
      }
    });
    panel.add(checkBox);
    checkBox.setBounds(54, y, 381, 23);
    y = y + 40;
    boolean flagCheckBox = false;
    int index = 0;
    for (ProductTag bean : list) {
      CheckBoxProductGroup checkBoxProductGroup = new CheckBoxProductGroup(bean);
      checkBoxProductGroup.setName(checkBoxProductGroup.getText());
      checkBoxProductGroup.setOpaque(false);
      checkBoxProductGroup.addItemListener(new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
          CheckBoxProductGroup jcb = (CheckBoxProductGroup) e.getSource();
          if (jcb.isSelected()) {
            listProductTags.add(jcb.getProductGroup());
          } else {
            for (int i = 0; i < listProductTags.size(); i++) {
              if (listProductTags.get(i).getId() == jcb.getProductGroup().getId()) {
                listProductTags.remove(i);
                break;
              }
            }
          }
        }
      });
      listCheckBox.add(checkBoxProductGroup);
      panel.add(checkBoxProductGroup);
      index++;
      if (!flagCheckBox) {
        if (index < 10) {
          h = h + 50;
        } else {
          if (index == 11) {
            h = h + 15;
          }
        }
        checkBoxProductGroup.setBounds(54, y, 180, 23);
      } else {
        checkBoxProductGroup.setBounds(255, y, 180, 23);
        y = y + 40;
      }
      flagCheckBox = !flagCheckBox;

    }
    if (listCheckBox.size() > 12) {
      setLayout(new GridLayout(1, 1));
      add(scrollPane);
      panel.setOpaque(false);
      scrollPane.getViewport().setOpaque(false);
      scrollPane.setOpaque(false);
      panel.setSize(new Dimension(getWidth() - 50,  y));
      panel.setPreferredSize(new Dimension(getWidth() - 50, y));
      scrollPane.setViewportView(panel);
    } else {
      setLayout(new GridLayout(1, 1));
      add(panel);
      panel.setOpaque(false);

    }

  }

  public int getH() {
    return h + 200;
  }

  public boolean isFlagSort() {
    return flagSort;
  }

  @Override
  public void Save() throws Exception {
    ProductTagList productTagList = new ProductTagList();
    productTagList.setTags(listProductTags);
    if (checkBox.isSelected()) {
      productTagList = null;
    }
    if (name.equals("BangGiaHienHanh")) {
      TableListProductPriceType product = new TableListProductPriceType(productTagList);
      product.setName("Bảng giá hiện hành");
      DialogList dialog = new DialogList(product);
      dialog.setIcon("banggia1.png");
      dialog.setTitle("Bảng giá hiện hành");
      dialog.setVisible(true);
    }
    if (name.equals("QuanLyHangHoa")) {
      TableListProduct product = new TableListProduct(productTagList);
      product.setName("Quản lý hàng hóa");
      DialogList dialog = new DialogList(product);
      dialog.setIcon("dssp1.png");
      dialog.setName("dlQuanLyHangHoa");
      dialog.setTitle("Danh sách hàng hóa");
      dialog.setVisible(true);
    }
    ((JDialog) this.getRootPane().getParent()).dispose();
  }

}
