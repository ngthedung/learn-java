package com.hkt.client.swingexp.app.khohang.list;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.print.ReportBarcode;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.product.entity.Product;

public class TableListBarcode extends JTable implements ITableMain {
  private List<Product> listProduct;
  private TableModelBarcode mdTbBarcode;
  private String pricingType, currency;
  private int page;
  private int size;

  public TableListBarcode(String pricingType, String currency) {
    this.pricingType = pricingType;
    this.currency = currency;
    try {
      listProduct = ProductModelManager.getInstance().filterProduct();
    } catch (Exception e) {
      e.printStackTrace();
      listProduct = new ArrayList<Product>();
    }
    mdTbBarcode = new TableModelBarcode(new ArrayList<Product>(), pricingType, currency);
    setModel(mdTbBarcode);
    
  }

  public JButton getBtnPrint() {
    JButton btnEdit = new JButton();
    btnEdit = new JButton("Barcode");
    btnEdit.setFont(new Font("Tahoma", Font.BOLD, 14));
    btnEdit.setMargin(new Insets(0, 0, 0, 0));
    btnEdit.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/print1.png")));
    btnEdit.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          int[] a = getSelectedRows();
          if (a.length > 0) {
            List<Integer> listI = new ArrayList<Integer>();
            for (int i = 0; i < a.length; i++) {
              listI.add(a[i]);
            }
            int k = 0;
            for (int i = 0; i < mdTbBarcode.getRowCount();) {
              if (!listI.contains(k)) {
                mdTbBarcode.removeRow(i);
              } else {
                i++;
              }
              k++;
            }
          }
          System.out.println(mdTbBarcode.getRowCount()+"   "+mdTbBarcode.getColumnCount());
          ReportBarcode barcode = new ReportBarcode();
          barcode.setTableModel(mdTbBarcode);
          barcode.setVisible(true);
          loadTable(page, size);
        } catch (Exception e2) {
          e2.printStackTrace();
        }
      }
    });

    return btnEdit;
  }

  public void click() {

  }

  @Override
  public int getListSize() {
    return listProduct.size();
  }

  @Override
  public List<Integer> getColumnSum() {
    return null;
  }

  @Override
  public DefaultTableModel loadTable(int currentPage, int pageSize) {
    this.page = currentPage;
    this.size = pageSize;
    try {
      mdTbBarcode.setData((listProduct.subList(currentPage, pageSize)));
    } catch (Exception ex) {
      mdTbBarcode = new TableModelBarcode(new ArrayList<Product>(), pricingType, currency);
    }
    return mdTbBarcode;

  }

  @Override
  public boolean delete() {
    return false;
  }

  @Override
  public boolean isDelete() {
    return false;
  }
}
