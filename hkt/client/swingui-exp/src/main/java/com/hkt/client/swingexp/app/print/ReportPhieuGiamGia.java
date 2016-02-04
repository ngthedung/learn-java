package com.hkt.client.swingexp.app.print;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.print.PrintService;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.hkt.client.swingexp.app.core.HKTFile;

public class ReportPhieuGiamGia extends JPanel {
  private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
  private String fileNamePath;
  private HashMap hashMap;
  private PrintService printService;
  private String size;
  private int templeteType;
  private boolean flagExtension;
  private ImageIcon logo;

  public ReportPhieuGiamGia() {
    initComponents();
  }

  private void initComponents() {

    jScrollPane1 = new javax.swing.JScrollPane();
    tableDataReport = new javax.swing.JTable();
    lbImageReport = new javax.swing.JLabel();
    jLabel1 = new javax.swing.JLabel();
    txtMoneyOrPercent = new javax.swing.JTextField();
    txtUnitMoney = new javax.swing.JTextField();
    txtNameEnterprise = new javax.swing.JLabel();
    txtPhone = new javax.swing.JTextField();
    txtWebsite = new javax.swing.JTextField();
    txtAddress = new javax.swing.JTextField();
    txtFinishPromotion = new javax.swing.JTextField();
    txtStartPromotion = new javax.swing.JTextField();
    jLabel2 = new javax.swing.JLabel();
    txtBarCode = new javax.swing.JTextField();

    tableDataReport.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { { null, null, null, null },
        { null, null, null, null }, { null, null, null, null }, { null, null, null, null } }, new String[] { "Title 1",
        "Title 2", "Title 3", "Title 4" }));
    jScrollPane1.setViewportView(tableDataReport);

    lbImageReport.setText(""); // NOI18N
    lbImageReport.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

    jLabel1.setText(""); // NOI18N

    txtMoneyOrPercent.setText(""); // NOI18N

    txtUnitMoney.setText(""); // NOI18N

    txtNameEnterprise.setText(""); // NOI18N
    txtNameEnterprise.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

    txtPhone.setText(""); // NOI18N

    txtWebsite.setText(""); // NOI18N

    txtAddress.setText(""); // NOI18N

    txtFinishPromotion.setText(""); // NOI18N

    txtStartPromotion.setText(""); // NOI18N

    jLabel2.setText(""); // NOI18N

    txtBarCode.setText(""); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout
        .setHorizontalGroup(layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addGroup(
                        layout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(
                                layout
                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(
                                        javax.swing.GroupLayout.Alignment.LEADING,
                                        layout
                                            .createSequentialGroup()
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 157,
                                                Short.MAX_VALUE).addGap(269, 269, 269))
                                    .addGroup(
                                        javax.swing.GroupLayout.Alignment.LEADING,
                                        layout
                                            .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                layout
                                                    .createSequentialGroup()
                                                    .addGroup(
                                                        layout
                                                            .createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                            .addComponent(txtUnitMoney,
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(txtMoneyOrPercent,
                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, 139,
                                                                Short.MAX_VALUE))
                                                    .addPreferredGap(
                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED, 190,
                                                        Short.MAX_VALUE)
                                                    .addComponent(txtWebsite, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                layout
                                                    .createSequentialGroup()
                                                    .addGroup(
                                                        layout
                                                            .createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                            .addComponent(lbImageReport,
                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(jLabel1,
                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, 147,
                                                                Short.MAX_VALUE))
                                                    .addGap(182, 182, 182)
                                                    .addGroup(
                                                        layout
                                                            .createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addComponent(txtPhone,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 97,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(txtAddress,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 97,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(
                                javax.swing.GroupLayout.Alignment.TRAILING,
                                layout
                                    .createSequentialGroup()
                                    .addComponent(txtStartPromotion, javax.swing.GroupLayout.PREFERRED_SIZE, 97,
                                        javax.swing.GroupLayout.PREFERRED_SIZE).addGap(59, 59, 59)))
                    .addGroup(
                        layout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addGap(111, 111, 111)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 168,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addGap(26, 26, 26)
                                    .addComponent(txtFinishPromotion, javax.swing.GroupLayout.PREFERRED_SIZE, 97,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtBarCode, javax.swing.GroupLayout.PREFERRED_SIZE, 145,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))).addGap(20, 20, 20))
            .addGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                    javax.swing.GroupLayout.Alignment.TRAILING,
                    layout
                        .createSequentialGroup()
                        .addContainerGap(328, Short.MAX_VALUE)
                        .addComponent(txtNameEnterprise, javax.swing.GroupLayout.PREFERRED_SIZE, 367,
                            javax.swing.GroupLayout.PREFERRED_SIZE).addGap(40, 40, 40))));
    layout
        .setVerticalGroup(layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                layout
                    .createSequentialGroup()
                    .addGroup(
                        layout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(
                                        layout
                                            .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lbImageReport, javax.swing.GroupLayout.PREFERRED_SIZE, 98,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(
                                        layout
                                            .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel1)
                                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(
                                        layout
                                            .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtMoneyOrPercent, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtWebsite, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(
                                        layout
                                            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(
                                                layout
                                                    .createSequentialGroup()
                                                    .addGap(4, 4, 4)
                                                    .addComponent(txtUnitMoney, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(
                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33,
                                                        Short.MAX_VALUE)
                                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        89, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(31, 31, 31))
                                            .addGroup(
                                                layout
                                                    .createSequentialGroup()
                                                    .addGap(31, 31, 31)
                                                    .addGroup(
                                                        layout
                                                            .createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(txtFinishPromotion,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(txtStartPromotion,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGap(48, 48, 48)
                                                    .addComponent(txtBarCode, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addGap(78, 78, 78)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 83,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))).addGap(34, 34, 34))
            .addGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                    layout
                        .createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(txtNameEnterprise, javax.swing.GroupLayout.PREFERRED_SIZE, 39,
                            javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(312, Short.MAX_VALUE))));
  }// </editor-fold>
  // Variables declaration - do not modify

  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JLabel lbImageReport;
  private javax.swing.JTable tableDataReport;
  private javax.swing.JTextField txtAddress;
  private javax.swing.JTextField txtBarCode;
  private javax.swing.JTextField txtFinishPromotion;
  private javax.swing.JTextField txtMoneyOrPercent;
  private javax.swing.JLabel txtNameEnterprise;
  private javax.swing.JTextField txtPhone;
  private javax.swing.JTextField txtStartPromotion;
  private javax.swing.JTextField txtUnitMoney;
  private javax.swing.JTextField txtWebsite;

  // End of variables declaration

  private void printReport(boolean view_print) {
    fileNamePath = getFileNamePath();
    // In báo cáo, Nhập giá trị tương ứng với key
    String[] keys = new String[] { "prMoneyOrPercent", "prUnitMoney", "prTenCongTy", "prDienThoai", "prDiaChi",
        "prWebsite", "prBatDauKhuyenMai", "prKetThucKhuyenMai", "prMaBarCode" };
    String unitMoney;
    if (txtMoneyOrPercent.getText().endsWith("% Giá trị hóa đơn")) {
      unitMoney = "";
    } else {
      unitMoney = txtUnitMoney.getText();
    }
    String[] values = new String[] { txtMoneyOrPercent.getText(), unitMoney, txtNameEnterprise.getText(),
        txtPhone.getText(), txtAddress.getText(), txtWebsite.getText(), txtStartPromotion.getText(),
        txtFinishPromotion.getText(), txtBarCode.getText() };
    hashMap = new HashMap();
    if (keys.length == values.length) {
      for (int i = 0; i < keys.length; i++) {
        hashMap.put(keys[i], values[i]);
      }
    }
    /** Cho ảnh logo công ty vào */
    hashMap.put("prImageLogoCity", logo.getImage());
    /** Cho ảnh nền của phiếu giảm giá */
    hashMap.put("prImageBackground", getImgaeBrackgroud());
    reportExport(fileNamePath, hashMap, tableDataReport.getModel(), view_print);
  }

  private String getFileNamePath() {
    String str = null;
    switch (templeteType) {
    case 1:
      File file1 = HKTFile.getFile("TemplatePrint", "PromotionTemplate1.jasper");
      if (file1.exists()) {
        str = file1.getAbsolutePath();
        flagExtension = true;
      } else {
        str = "template/PromotionTemplate1.jasper";
        flagExtension = false;
      }
      break;
    case 2:
      File file2 = HKTFile.getFile("TemplatePrint", "PromotionTemplate2.jasper");
      if (file2.exists()) {
        str = file2.getAbsolutePath();
        flagExtension = true;
      } else {
        str = "template/PromotionTemplate2.jasper";
        flagExtension = false;
      }
      break;
    case 3:
      File file3 = HKTFile.getFile("TemplatePrint", "PromotionTemplate3.jasper");
      if (file3.exists()) {
        str = file3.getAbsolutePath();
        flagExtension = true;
      } else {
        str = "template/PromotionTemplate3.jasper";
        flagExtension = false;
      }
      break;
    case 4:
      File file4 = HKTFile.getFile("TemplatePrint", "PromotionTemplate4.jasper");
      if (file4.exists()) {
        str = file4.getAbsolutePath();
        flagExtension = true;
      } else {
        str = "template/PromotionTemplate4.jasper";
        flagExtension = false;
      }
      break;
    case 5:
      File file5 = HKTFile.getFile("TemplatePrint", "PromotionTemplate5.jasper");
      if (file5.exists()) {
        str = file5.getAbsolutePath();
        flagExtension = true;
      } else {
        str = "template/PromotionTemplate5.jasper";
        flagExtension = false;
      }
      break;
    }
    return str;
  }

  private String getImgaeBrackgroud() {
    String str = null;
    switch (templeteType) {
    case 1:
      File file1 = HKTFile.getFile("TemplatePrint", "ImageTemplate1.png");
      if (file1.exists()) {
        str = file1.getAbsolutePath();
      } else {
        str = "template/ImageTemplate1.png";
        URL url = getClass().getResource(str);
        str = url.getPath();
      }
      break;
    case 2:
      File file2 = HKTFile.getFile("TemplatePrint", "ImageTemplate2.png");
      if (file2.exists()) {
        str = file2.getAbsolutePath();
      } else {
        str = "template/ImageTemplate2.png";
        URL url = getClass().getResource(str);
        str = url.getPath();
      }
      break;
    case 3:
      File file3 = HKTFile.getFile("TemplatePrint", "ImageTemplate3.png");
      if (file3.exists()) {
        str = file3.getAbsolutePath();
      } else {
        str = "template/ImageTemplate3.png";
        URL url = getClass().getResource(str);
        str = url.getPath();
      }
      break;
    case 4:
      File file4 = HKTFile.getFile("TemplatePrint", "ImageTemplate4.png");
      if (file4.exists()) {
        str = file4.getAbsolutePath();
      } else {
        str = "template/ImageTemplate4.png";
        URL url = getClass().getResource(str);
        str = url.getPath();
      }
      break;
    case 5:
      File file5 = HKTFile.getFile("TemplatePrint", "ImageTemplate5.png");
      if (file5.exists()) {
        str = file5.getAbsolutePath();
      } else {
        str = "template/ImageTemplate5.png";
        URL url = getClass().getResource(str);
        str = url.getPath();
      }
      break;
    }

    return str;
  }

  private void reportExport(String filePathResource, HashMap hashMap, TableModel model, boolean view_print) {
    if (!view_print) {
      ReportManager.getInstance().viewReport(filePathResource, hashMap, model, flagExtension);
    } else {
      ReportManager.getInstance().printReportBarcode(filePathResource, hashMap, model, printService, flagExtension, -1);
    }
  }

  public void setTableModel(DefaultTableModel tableModel) {
    // headerReport = new String[tableModel.getColumnCount()];
    // for (int i = 0; i < headerReport.length; i++) {
    // headerReport[i] = tableModel.getColumnName(i);
    // }
    // tableDataReport.setModel(tableModel);
  }

  public void setTypeTempletePromotion(int templete) {
    try {
      this.templeteType = templete;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setParameterPersent(String... strings) {
    try {
      txtMoneyOrPercent.setText(strings[0]);

      txtNameEnterprise.setText(strings[1]);
      txtPhone.setText(strings[2]);
      txtAddress.setText(strings[3]);
      txtWebsite.setText(strings[4]);

      txtStartPromotion.setText(strings[5]);
      txtFinishPromotion.setText(strings[6]);

      txtBarCode.setText(strings[7]);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setParameterTotal(String... strings) {
    try {
      txtMoneyOrPercent.setText(strings[0]);
      txtUnitMoney.setText(strings[1]);

      txtNameEnterprise.setText(strings[2]);
      txtPhone.setText(strings[3]);
      txtAddress.setText(strings[4]);
      txtWebsite.setText(strings[5]);

      txtStartPromotion.setText(strings[6]);
      txtFinishPromotion.setText(strings[7]);

      txtBarCode.setText(strings[8]);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void printReportPromotion() {
    printReport(false);
  }

  public void setPrintService(PrintService printService) {
    this.printService = printService;
  }

  public void setLogoEnterprise(ImageIcon image) {
    try {
      if (image == null) {
        this.logo = new ImageIcon();
      } else {
        this.logo = image;
      }
      lbImageReport.setIcon(logo);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
