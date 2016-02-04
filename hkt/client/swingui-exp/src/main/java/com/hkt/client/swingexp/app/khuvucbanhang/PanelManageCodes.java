package com.hkt.client.swingexp.app.khuvucbanhang;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextArea;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.hethong.PanelRecybin;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.khuvucbanhang.list.TableManageCodes;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.accounting.entity.ManageCodes;
import com.hkt.module.accounting.entity.ManageCodes.ManageType;
import com.hkt.module.accounting.entity.ManageCodes.RotationType;
import com.hkt.module.config.generic.Option;
import com.hkt.module.restaurant.entity.Shift;
import com.hkt.util.text.DateUtil;

@SuppressWarnings("serial")
public class PanelManageCodes extends JPanel implements IDialogMain {
  public static String InvoiceBH = "Invoice_BH";
  public static String InvoiceTC = "Invoice_TC";
  public static String InvoiceDMH = "Invoice_DMH";
  public static String NhaCungCap = "NhaCungCap";
  public static String KhachHang = "KhachHang";
  public static String SanPham = "SanPham";
  public static String DuAn = "DuAn";
  private JLabel lbCode, lbName, lbManageType, lbRotationType, lbDesciption, lbMessenger;
  private JTextField txtName;
  private JComboBox cboCode;
  private JTextArea txtDescription;
  private JComboBox cboManageType, cboRotationType;
  private JScrollPane scrollPaneTable;
  private TableManageCodes table;
  private ManageCodes codes = new ManageCodes();
  private int index = 0;
  public static String permission;
  private boolean restore = true;
  private HashMap<String, String> hashMap = new HashMap<String, String>();
  private HashMap<String, String> hashMap1 = new HashMap<String, String>();

  public PanelManageCodes() throws Exception {
    init();
    setOpaque(false);
    reset();
  }

  private void init() throws Exception {
    lbCode = new ExtendJLabel("Đối tượng");
    lbName = new ExtendJLabel("Kiểu đặt mã");
    lbManageType = new ExtendJLabel("Loại quản lý");
    lbRotationType = new ExtendJLabel("Quay vòng");
    lbDesciption = new ExtendJLabel("Miêu tả");
    lbMessenger = new JLabel("");
    lbMessenger.setForeground(Color.RED);
    hashMap.put(InvoiceBH, "Hóa đơn bán hàng");
    hashMap.put(InvoiceTC, "Phiếu thu chi");
    hashMap.put(InvoiceDMH, "Phiếu đặt mua hàng");
    hashMap.put(KhachHang, "Khách hàng");
    hashMap.put(SanPham, "Sản phẩm");
    hashMap.put(DuAn, "Dự án");

    hashMap1.put("Hóa đơn bán hàng", InvoiceBH);
    hashMap1.put("Phiếu thu chi", InvoiceTC);
    hashMap1.put("Phiếu đặt mua hàng", InvoiceDMH);
    hashMap1.put("Khách hàng", KhachHang);
    hashMap1.put("Sản phẩm", SanPham);
    hashMap1.put("Dự án", DuAn);

    cboCode = new JComboBox();
    cboCode.setModel(new DefaultComboBoxModel(new String[] { "Hóa đơn bán hàng", "Phiếu thu chi", "Phiếu đặt mua hàng",
        "Khách hàng", "Sản phẩm", "Dự án" }));
    txtName = new ExtendJTextField();
    txtDescription = new ExtendJTextArea();
    // cboCode.addItemListener(new ItemListener() {
    //
    // @Override
    // public void itemStateChanged(ItemEvent e) {
    // try {
    // codes =
    // AccountingModelManager.getInstance().getCodesByCode(hashMap1.get(cboCode.getSelectedItem().toString()));
    // if(codes!=null){
    // setData();
    // index = table.getSelectedRow();
    // setEnableCompoment(false);
    // }else {
    // codes = new ManageCodes();
    // }
    // } catch (Exception e2) {
    // codes = new ManageCodes();
    // }
    // }
    // });
    cboManageType = new ExtendJComboBox();
    cboManageType.setModel(new DefaultComboBoxModel(new String[] { "Quay vòng", "Tăng dần đều" }));

    cboRotationType = new ExtendJComboBox();
    cboRotationType.setModel(new DefaultComboBoxModel(new String[] { "", "Theo ngày", "Theo tháng", "Theo năm" }));
    cboRotationType.setSelectedIndex(1);
    cboManageType.addItemListener(new ItemListener() {

      @Override
      public void itemStateChanged(ItemEvent e) {
        if (cboManageType.getSelectedItem().equals("Quay vòng")) {
          cboRotationType.setEnabled(true);
          cboRotationType.setSelectedIndex(1);
        } else {
          cboRotationType.setEnabled(false);
          cboRotationType.setSelectedIndex(0);
        }

      }
    });

    table = new TableManageCodes(getCodes());
    table.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent event) {
        try {
          tbManageCodesMouseClicked(event);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

    });

    scrollPaneTable = new JScrollPane();
    scrollPaneTable.setOpaque(false);
    scrollPaneTable.getViewport().setOpaque(false);
    scrollPaneTable.setBorder(null);
    table.setPreferredScrollableViewportSize(new Dimension(300, 390));
    scrollPaneTable.setViewportView(table);

    MyGroupLayout layout = new MyGroupLayout(this);
    layout.add(0, 0, lbCode);
    layout.add(0, 1, cboCode);
    layout.add(0, 2, lbName);
    layout.add(0, 3, txtName);
    layout.add(1, 0, lbManageType);
    layout.add(1, 1, cboManageType);
    layout.add(1, 2, lbRotationType);
    layout.add(1, 3, cboRotationType);
    layout.add(2, 0, lbDesciption);
    layout.add(2, 1, txtDescription);
    layout.addMessenger(lbMessenger);
    layout.add(3, 0, new JLabel());
    layout.add(3, 1, scrollPaneTable);
    layout.updateGui();
  }

  protected void tbManageCodesMouseClicked(MouseEvent event) {
    codes = table.getSelectedBean();

    int click = 2;
    if (event.getClickCount() >= click) {
      codes = table.getSelectedBean();
      setData();
      index = table.getSelectedRow();

      setEnableCompoment(false);
      ((DialogMain) getRootPane().getParent()).showButton(false);
    }

  }

  private void setEnableCompoment(boolean value) {
    txtName.setEnabled(value);
    cboCode.setEnabled(value);
    txtDescription.setEnabled(value);

  }

  private void setData() {
    try {
      if (codes != null) {

        String str = hashMap.get(codes.getCode());
        for (int i = 0; i < cboCode.getItemCount(); i++) {
          if (cboCode.getItemAt(i).toString().equals(str)) {
            cboCode.setSelectedIndex(i);
          }
        }
        txtName.setText(codes.getTypeCode());
        txtDescription.setText(codes.getDescription());
        if (cboManageType.getSelectedItem() != null) {

          if (codes.getManageType().equals(ManageType.Circle)) {
            cboManageType.setSelectedIndex(0);

          } else {
            cboManageType.setSelectedIndex(1);

          }
        }

        if (cboRotationType.isEnabled() == true) {
          if (cboRotationType.getSelectedItem() != null) {
            if (codes.getRotationType().equals(RotationType.ByDay)) {
              cboRotationType.setSelectedIndex(1);
            } else if (codes.getRotationType().equals(RotationType.ByMonth)) {
              cboRotationType.setSelectedIndex(2);
            } else if (codes.getRotationType().equals(RotationType.ByYear)) {
              cboRotationType.setSelectedIndex(3);
            } 
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private List<ManageCodes> getCodes() throws Exception {
    return AccountingModelManager.getInstance().getAllCodes();

  }

  @Override
  public void save() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
      return;
    } else {

      codes = getData();
      if (AccountingModelManager.getInstance().getCodesByCode(codes.getCode()) != null) {
        if(codes.getId() == null){
          if (!AccountingModelManager.getInstance().getCodesByCode(codes.getCode()).isRecycleBin() ) {
            lbMessenger.setText("Đối tượng đã được thiết lập, click đúp dưới danh sách để chỉnh sửa");
            return;
          } else {
            codes = AccountingModelManager.getInstance().getCodesByCode(codes.getCode());
            codes = getData();
            codes.setPriority(1);
            codes.setRecycleBin(false);
          }
        }
       
      }

      if (restore) {
        if (codes != null) {
          codes = AccountingModelManager.getInstance().saveCodes(codes);
        }

      }
      reset();
    }

  }

  private ManageCodes getData() {

    restore = true;
    try {
      if (codes == null) {
        codes = new ManageCodes();
      }

      codes.setCode(hashMap1.get(cboCode.getSelectedItem().toString()));
      codes.setTypeCode(txtName.getText());
      if (cboManageType.getSelectedItem() != null) {

        if (cboManageType.getSelectedItem().toString().equals("Quay vòng")) {
          codes.setManageType(ManageType.Circle);
          if (cboRotationType.getSelectedItem().toString().equals("Theo ngày")) {

            codes.setRotationType(RotationType.ByDay);
          } else if (cboRotationType.getSelectedItem().toString().equals("Theo tháng")) {
            codes.setRotationType(RotationType.ByMonth);

          } else if (cboRotationType.getSelectedItem().toString().equals("Theo năm")) {
            codes.setRotationType(RotationType.ByYear);
          }

        } else {
          codes.setManageType(ManageType.Increase);
        }
      }

      codes.setDescription(txtDescription.getText());
      lbMessenger.setText(" ");
      return codes;
    } catch (Exception e) {
      lbMessenger.setVisible(true);
      lbMessenger.setText("Lỗi định dạng dữ liệu");
      e.printStackTrace();
      return new ManageCodes();
    }
  }

  @Override
  public void edit() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
      return;
    } else {
      setEnableCompoment(true);

    }

  }

  @Override
  public void delete() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {
      String str = "Xóa quản lý mã ";
      PanelChoise pnPanel = new PanelChoise(str + codes + "?");
      pnPanel.setName("Xoa");
      DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
      dialog1.setName("dlXoa");
      dialog1.setTitle("Xóa quản lý mã");
      dialog1.setLocationRelativeTo(null);
      dialog1.setModal(true);
      dialog1.setVisible(true);

      if (pnPanel.isDelete()) {
        AccountingModelManager.getInstance().deleteCodes(codes);
        loadTable();
        reset();
      } else if (pnPanel.isDelete() == false) {
        reset();
      }
    } else {
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
    }

  }

  private void loadTable() throws Exception {
    codes.getCode();
    codes.getTypeCode();
    codes.getDescription();

    table.setManadeCodes(getCodes());

  }

  @Override
  public void reset() throws Exception {
    setEnableCompoment(true);
    txtName.setText("");
    cboCode.setSelectedIndex(0);
    txtDescription.setText("");
    lbName.setForeground(new Color(51, 51, 51));
    lbCode.setForeground(new Color(51, 51, 51));
    lbDesciption.setForeground(new Color(51, 51, 51));
    lbMessenger.setText(" ");
    cboManageType.setSelectedIndex(0);
    cboRotationType.setSelectedIndex(1);
    codes = new ManageCodes();
    loadTable();

  }

  @Override
  public void refresh() throws Exception {
    setData();
    setEnableCompoment(false);
    lbName.setForeground(Color.black);
    lbCode.setForeground(Color.black);
    lbMessenger.setText(" ");

  }

}
