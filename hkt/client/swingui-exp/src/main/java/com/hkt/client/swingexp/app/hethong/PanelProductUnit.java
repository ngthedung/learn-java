package com.hkt.client.swingexp.app.hethong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.config.locale.ProductUnit;
import com.hkt.util.text.DateUtil;

@SuppressWarnings("serial")
public class PanelProductUnit extends MyPanel implements IDialogMain {

  private JLabel lbCode, lbName, lbRate, lbDescription, lbMessenger;
  private JTextField txtCode, txtName, txtRate, txtDescription;
  private JScrollPane scrollPaneTable, scrollIndex;
  private ProductUnitTable productUnitTable;
  private ProductUnit productUnit = new ProductUnit();
  @SuppressWarnings("unused")
  private int index = 0;
  public static String permission;
  private boolean restore = true;

  public PanelProductUnit() throws Exception {
    init();
    setOpaque(false);
    reset();
  }

  // Hàm tạo giao diện
  @SuppressWarnings("static-access")
  private void init() throws Exception {

    lbMessenger = new JLabel("");
    scrollPaneTable = new JScrollPane();
    scrollPaneTable.setOpaque(false);
    scrollPaneTable.getViewport().setOpaque(false);
    scrollPaneTable.setBorder(null);

    productUnitTable = new ProductUnitTable();
    productUnitTable.setName("tbproductUnitTable");

    productUnitTable.setProductUnits(getObjects());
    productUnitTable.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent event) {
        try {
          tableOptionsMouseClicked(event);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    productUnitTable.setPreferredScrollableViewportSize(new Dimension(200, 290));
    scrollPaneTable.setViewportView(productUnitTable);

    lbCode = new JLabel("Mã");
    txtCode = new JTextField();
    txtCode.setName("txtCode");
    txtCode.setDisabledTextColor(Color.black);

    lbName = new JLabel("Đơn vị");
    txtName = new JTextField();
    txtName.setName("txtName");
    txtName.setDisabledTextColor(Color.black);

    lbRate = new JLabel("Tỷ lệ");
    txtRate = new JTextField();
    txtRate.setName("txtRate");
    txtRate.setText("1");
    txtRate.setHorizontalAlignment(txtRate.RIGHT);
    txtRate.setDisabledTextColor(Color.black);

    lbDescription = new JLabel("Miêu tả");
    txtDescription = new JTextField();
    txtDescription.setPreferredSize(new Dimension(250, 53));
    txtDescription.setName("txtDescription");
    txtDescription.setDisabledTextColor(Color.black);

    MyGroupLayout layout = new MyGroupLayout(this);
    layout.add(0, 0, lbCode);
    layout.add(0, 1, txtCode);

    layout.add(0, 2, lbName);
    layout.add(0, 3, txtName);

    layout.add(1, 0, lbRate);
    layout.add(1, 1, txtRate);

    layout.add(1, 2, lbDescription);
    layout.add(1, 3, txtDescription);
    layout.addMessenger(lbMessenger);

    // Thêm button cập nhật index
    scrollIndex = new JScrollPane();
    scrollIndex.setOpaque(false);
    scrollIndex.getViewport().setOpaque(false);
    scrollIndex.setBorder(null);
    scrollIndex.setViewportView(productUnitTable.getPanleButton());

    // Giao diện Button cập nhật index đi cùng với scrollPaneTable
    layout.add(2, 0, scrollIndex);
    layout.add(2, 1, scrollPaneTable);
    layout.updateGui();

  }

  protected void tableOptionsMouseClicked(MouseEvent event) throws Exception {
    productUnit = productUnitTable.getSelectedBean();
    int click = 2;
    if (productUnit.getCode().indexOf("HktTest") > 0 && event.getButton() == 3) {
      click = 1;
    }
    if (event.getClickCount() >= click) {
      setData();
      index = productUnitTable.getSelectedRow();
      setEnableCompoment(false);
      ((DialogMain) getRootPane().getParent()).showButton(false);
      refresh();
    }

  }

  private ProductUnit getData() {
    restore = true;
    try {
      if (productUnit == null) {
        productUnit = new ProductUnit();
        productUnit.setPriority(getObjects().size() + 1);
      } else {
        if (productUnitTable.isEdit()) {
          productUnit.setPriority(productUnitTable.getBeans().indexOf(productUnit) + 1);
        }
      }
      productUnit.setCode(txtCode.getText());
      productUnit.setName(txtName.getText());
      if (txtRate.getText().length() > 0) {
        productUnit.setRate(Double.parseDouble(txtRate.getText()));
      } else {
        lbRate.setForeground(Color.red);
      }
      productUnit.setDescription(txtDescription.getText());
      lbMessenger.setText(" ");
      return productUnit;
    } catch (Exception e) {

      if (productUnit.getCode() == null | productUnit.getCode().isEmpty() | productUnit.getName() == null
          | productUnit.getName().isEmpty() | productUnit.getRate() == 0d) {

        if (productUnit.getCode().isEmpty()) {
          lbCode.setForeground(Color.red);
        } else {
          lbCode.setForeground(Color.black);
        }
        if (productUnit.getName().isEmpty()) {
          lbName.setForeground(Color.red);
        } else {
          lbName.setForeground(Color.black);
        }
        if (productUnit.getRate() == 0d) {
          lbRate.setForeground(Color.red);
        } else {
          lbRate.setForeground(Color.black);
        }

        lbMessenger.setText("Dữ liệu vùng đỏ bị lỗi");
        lbMessenger.setForeground(Color.red);
        lbMessenger.setVisible(true);
        return new ProductUnit();
      }
      lbMessenger.setVisible(true);
      lbMessenger.setText("Lỗi định dạng dữ liệu");
      lbMessenger.setForeground(Color.red);
      if (lbMessenger.getText().equals("Lỗi định dạng dữ liệu")) {
        lbRate.setForeground(Color.red);
      }
      e.printStackTrace();
      return new ProductUnit();
    }
  }

  private void setData() {
    try {
      txtCode.setText(productUnit.getCode());
      txtName.setText(productUnit.getName());
      txtRate.setText(String.valueOf(productUnit.getRate()));
      txtDescription.setText(productUnit.getDescription());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private boolean checkData() {
    boolean check = true;
    if (txtCode.getText().equals("")) {
      lbCode.setForeground(Color.red);
      check = false;
    } else {
      lbCode.setForeground(Color.black);
    }

    if (txtName.getText().equals("")) {
      lbName.setForeground(Color.red);
      check = false;
    } else {
      lbName.setForeground(Color.black);
    }

    if ((txtRate.getText() != null && txtRate.getText().isEmpty())
        && (txtRate.getText() != null || productUnit.getRate() == 0d)) {
      lbRate.setForeground(Color.red);
      check = false;
    } else {
      lbRate.setForeground(Color.black);
    }

    // Kiểm tra xem có bị trùng code hay không
    if (txtCode.isEnabled()) {
      try {
        ProductUnit p = LocaleModelManager.getInstance().getProductUnitByCode(txtCode.getText());
        if (p != null) {
          check = false;
          lbCode.setForeground(Color.red);
          if (p.isRecycleBin()) {
            PanelRecybin panel = new PanelRecybin("Mã đã bị xóa trước đó!",
                " Chọn ĐỒNG Ý để lấy lại thông tin ban đầu hoặc THOÁT và nhập mã khác!");
            panel.setName("Xoa");
            DialogResto dialog = new DialogResto(panel, false, 0, 120);
            dialog.setName("dlXoa");
            dialog.setTitle("Đơn vị sản phẩm");
            dialog.setLocationRelativeTo(null);
            dialog.setModal(true);
            dialog.setVisible(true);
            if (panel.isDelete()) {
              restore = false;
              p.setRecycleBin(false);
              LocaleModelManager.getInstance().saveProductUnit(p);
              return true;
            }
          }
        }
      } catch (Exception e) {
      }

      if (!check) {
        lbMessenger.setText("Dữ liệu vùng đỏ bị lỗi");
        lbMessenger.setForeground(Color.red);
        lbMessenger.setVisible(true);
        return false;
      } else {
        lbMessenger.setText(" ");
        return true;
      }
    }
    return check;
  }

  @Override
  public void save() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
    } else {

      // Lấy dữ liệu đổ vào đối tượng
      if (checkData()) {
        if (restore)
          getData();
        if (productUnit != null) {
          LocaleModelManager.getInstance().saveProductUnit(productUnit);
        }
        reset();
      }
    }
  }

  @Override
  public void edit() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
    } else {
      setEnableCompoment(true);
      txtCode.setEnabled(false);
    }

  }

  @Override
  public void delete() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {

      String str = "Xóa đơn vị ";
      PanelChoise pnPanel = new PanelChoise(str + productUnit + "?");
      pnPanel.setName("Xoa");
      DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
      dialog1.setName("dlXoa");
      dialog1.setTitle("Xóa đơn vị sản phẩm");
      dialog1.setLocationRelativeTo(null);
      dialog1.setModal(true);
      dialog1.setVisible(true);

      if (pnPanel.isDelete()) {
        LocaleModelManager.getInstance().deleteProductUnit(productUnit);
        lbMessenger.setText("");
        reset();
      } else if (pnPanel.isDelete() == false) {
        reset();
      } else {
        lbMessenger.setText("Hãy chắc chắn là bạn đã chọn 1 đơn vị sản phẩm để xóa");
        lbMessenger.setVisible(true);
        return;
      }
    } else {
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
    }

  }

  @Override
  public void reset() throws Exception {
    setEnableCompoment(true);
    txtCode.setText(DateUtil.asCompactDateTimeId(new Date()));
    txtName.setText("");
    txtRate.setText("1");
    txtDescription.setText("");

    lbCode.setForeground(Color.black);
    lbName.setForeground(Color.black);
    lbRate.setForeground(Color.black);
    lbDescription.setForeground(Color.black);
    lbMessenger.setText(" ");
    // productUnit = new ProductUnit();
    productUnit = null;
    index = -1;
    loadTable();
  }

  @Override
  public void refresh() throws Exception {
    setData();
    setEnableCompoment(false);
    lbCode.setForeground(Color.black);
    lbName.setForeground(Color.black);
    lbRate.setForeground(Color.black);
    lbDescription.setForeground(Color.black);
    lbMessenger.setText(" ");
  }

  private void setEnableCompoment(boolean value) {
    txtCode.setEnabled(value);
    txtName.setEnabled(value);
    txtRate.setEnabled(value);
    txtDescription.setEnabled(value);
  }

  private void loadTable() throws Exception {
    productUnitTable.setProductUnits(getObjects());
  }

  public List<ProductUnit> getObjects() throws Exception {
    return LocaleModelManager.getInstance().getProductUnits();
  }

  @Override
  public void deleteDataTest() {
    if (!PanelTestAll.runAll) {
      DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
      if (DialogTest.getInstance().isTest()) {
        try {
          LocaleModelManager.getInstance().deleteTest("HktTest");
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

}
