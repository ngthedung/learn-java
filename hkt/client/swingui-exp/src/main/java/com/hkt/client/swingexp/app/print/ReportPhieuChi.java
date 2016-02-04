package com.hkt.client.swingexp.app.print;

import java.io.File;
import java.util.HashMap;

import javax.print.PrintService;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.hkt.client.swingexp.app.core.HKTFile;

public class ReportPhieuChi extends JPanel{
  
  private String fileNamePath;
  private HashMap hashMap;
  private String[] headerReport;
  private PrintService printService;
  private ImageIcon logo;
  private String size = "A4";
  private boolean flagExtension;
  private String loai, boPhan, maDonVi, duAn, maKH, nguoiLap;
  
  public ReportPhieuChi(){
    initComponents();
  }

  private void initComponents() {

    jPanel1 = new javax.swing.JPanel();
    txtDonVi = new javax.swing.JTextField();
    txtDiaChi = new javax.swing.JTextField();
    txtNgay = new javax.swing.JTextField();
    txtThang = new javax.swing.JTextField();
    txtNam = new javax.swing.JTextField();
    txtQuyenSo = new javax.swing.JTextField();
    txtNo = new javax.swing.JTextField();
    txtSo = new javax.swing.JTextField();
    txtCo = new javax.swing.JTextField();
    txtNguoiNhanTien = new javax.swing.JTextField();
    txtDiaChiNguoiNhanTien = new javax.swing.JTextField();
    txtLyDoChi = new javax.swing.JTextField();
    txtSoTienChi = new javax.swing.JTextField();
    txtVietBangChu = new javax.swing.JTextField();
    txtKemTheo = new javax.swing.JTextField();
    txtThangKi = new javax.swing.JTextField();
    txtNgayKi = new javax.swing.JTextField();
    txtNamKi = new javax.swing.JTextField();
    txtTyGiaNgoaiTe = new javax.swing.JTextField();
    txtSoTienQuyDoi = new javax.swing.JTextField();
    jScrollPane1 = new javax.swing.JScrollPane();
    tableDataReport = new javax.swing.JTable();
    jLabel1 = new javax.swing.JLabel();
    lbImageReport = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    txtDonViTien = new javax.swing.JTextField();

    txtDonVi.setFont(new java.awt.Font("Tahoma", 0, 14));
    txtDonVi.setText(""); // NOI18N

    txtDiaChi.setFont(new java.awt.Font("Tahoma", 0, 14));
    txtDiaChi.setText(""); // NOI18N

    txtNgay.setFont(new java.awt.Font("Tahoma", 0, 14));
    txtNgay.setText(""); // NOI18N

    txtThang.setFont(new java.awt.Font("Tahoma", 0, 14));
    txtThang.setText(""); // NOI18N

    txtNam.setFont(new java.awt.Font("Tahoma", 0, 14));
    txtNam.setText(""); // NOI18N

    txtQuyenSo.setFont(new java.awt.Font("Tahoma", 0, 14));
    txtQuyenSo.setText(""); // NOI18N

    txtNo.setFont(new java.awt.Font("Tahoma", 0, 14));
    txtNo.setText(""); // NOI18N

    txtSo.setFont(new java.awt.Font("Tahoma", 0, 14));
    txtSo.setText(""); // NOI18N

    txtCo.setFont(new java.awt.Font("Tahoma", 0, 14));
    txtCo.setText(""); // NOI18N

    txtNguoiNhanTien.setFont(new java.awt.Font("Tahoma", 0, 14));
    txtNguoiNhanTien.setText(""); // NOI18N

    txtDiaChiNguoiNhanTien.setFont(new java.awt.Font("Tahoma", 0, 14));
    txtDiaChiNguoiNhanTien.setText(""); // NOI18N

    txtLyDoChi.setFont(new java.awt.Font("Tahoma", 0, 14));
    txtLyDoChi.setText(""); // NOI18N

    txtSoTienChi.setFont(new java.awt.Font("Tahoma", 0, 14));
    txtSoTienChi.setText(""); // NOI18N

    txtVietBangChu.setFont(new java.awt.Font("Tahoma", 0, 14));
    txtVietBangChu.setText(""); // NOI18N

    txtKemTheo.setFont(new java.awt.Font("Tahoma", 0, 14));
    txtKemTheo.setText(""); // NOI18N

    txtThangKi.setFont(new java.awt.Font("Tahoma", 0, 14));

    txtNgayKi.setFont(new java.awt.Font("Tahoma", 0, 14));

    txtNamKi.setFont(new java.awt.Font("Tahoma", 0, 14));

    txtTyGiaNgoaiTe.setFont(new java.awt.Font("Tahoma", 0, 14));

    txtSoTienQuyDoi.setFont(new java.awt.Font("Tahoma", 0, 14));

    tableDataReport.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        },
        new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4"
        }
    ));
    jScrollPane1.setViewportView(tableDataReport);

    jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14));
    jLabel1.setText(""); // NOI18N

    lbImageReport.setText(""); // NOI18N

    jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14));
    jLabel2.setText(""); // NOI18N

    txtDonViTien.setFont(new java.awt.Font("Tahoma", 0, 14));
    txtDonViTien.setText(""); // NOI18N

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(89, 89, 89)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayKi, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtThangKi, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(33, 33, 33)
                            .addComponent(txtNamKi, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(17, 17, 17))
                        .addComponent(txtVietBangChu, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSoTienChi, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtLyDoChi, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDiaChiNguoiNhanTien, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtSoTienQuyDoi, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTyGiaNgoaiTe, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDonViTien, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 162, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(txtNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(txtThang, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(33, 33, 33)
                                            .addComponent(txtNam, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(50, 50, 50)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lbImageReport)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtQuyenSo, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                                    .addComponent(txtSo)
                                    .addComponent(txtNo)
                                    .addComponent(txtCo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtNguoiNhanTien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtKemTheo, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(51, 51, 51)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtDiaChi, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtDonVi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE))))
            .addContainerGap(96, Short.MAX_VALUE))
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addGap(293, 293, 293)
            .addComponent(jLabel1)
            .addContainerGap(369, Short.MAX_VALUE))
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(txtDonVi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(16, 16, 16)
            .addComponent(jLabel1)
            .addGap(18, 18, 18)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(39, 39, 39)
                    .addComponent(lbImageReport)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtQuyenSo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(7, 7, 7)
                    .addComponent(txtSo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtCo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGap(18, 18, 18)
            .addComponent(txtNguoiNhanTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(4, 4, 4)
            .addComponent(txtDiaChiNguoiNhanTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(txtLyDoChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(txtSoTienChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(txtVietBangChu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(txtKemTheo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel2)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtThangKi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtNamKi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtNgayKi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addComponent(txtDonViTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(txtTyGiaNgoaiTe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(txtSoTienQuyDoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(61, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 726, Short.MAX_VALUE)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 627, Short.MAX_VALUE)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)))
    );
}// </editor-fold>                        
// Variables declaration - do not modify                     
private javax.swing.JLabel jLabel1;
private javax.swing.JLabel jLabel2;
private javax.swing.JPanel jPanel1;
private javax.swing.JScrollPane jScrollPane1;
private javax.swing.JLabel lbImageReport;
private javax.swing.JTable tableDataReport;
private javax.swing.JTextField txtCo;
private javax.swing.JTextField txtDiaChi;
private javax.swing.JTextField txtDiaChiNguoiNhanTien;
private javax.swing.JTextField txtDonVi;
private javax.swing.JTextField txtDonViTien;
private javax.swing.JTextField txtKemTheo;
private javax.swing.JTextField txtLyDoChi;
private javax.swing.JTextField txtNam;
private javax.swing.JTextField txtNamKi;
private javax.swing.JTextField txtNgay;
private javax.swing.JTextField txtNgayKi;
private javax.swing.JTextField txtNguoiNhanTien;
private javax.swing.JTextField txtNo;
private javax.swing.JTextField txtQuyenSo;
private javax.swing.JTextField txtSo;
private javax.swing.JTextField txtSoTienChi;
private javax.swing.JTextField txtSoTienQuyDoi;
private javax.swing.JTextField txtThang;
private javax.swing.JTextField txtThangKi;
private javax.swing.JTextField txtTyGiaNgoaiTe;
private javax.swing.JTextField txtVietBangChu;


public void setParameter(String... strings) {
  try {
      txtDonVi.setText(strings[0]);
      txtDiaChi.setText(strings[1]);
      /**/
      txtQuyenSo.setText(strings[2]);
      txtSo.setText(strings[3]);
      txtNo.setText(strings[4]);
      txtCo.setText(strings[5]);
      /**/
      txtNguoiNhanTien.setText(strings[6]);
      txtDiaChiNguoiNhanTien.setText(strings[7]);
      txtLyDoChi.setText(strings[8]);
      txtSoTienChi.setText(strings[9]);
      txtVietBangChu.setText(strings[10]);
      txtKemTheo.setText(strings[11]);
      /**/
      txtNgayKi.setText(strings[12]);
      txtThangKi.setText(strings[13]);
      txtNamKi.setText(strings[14]);
      /**/
      txtDonViTien.setText(strings[15]);
      txtTyGiaNgoaiTe.setText(strings[16]);
      txtSoTienQuyDoi.setText(strings[17]);
      loai = (strings[18]);
      boPhan = (strings[19]);
      maDonVi = (strings[20]);
      duAn = (strings[21]);
      maKH = (strings[22]);
      nguoiLap = (strings[23]);
      /**/
  } catch (Exception e) {
  }
}


public void setTableModel(DefaultTableModel tableModel) {
  headerReport = new String[tableModel.getColumnCount()];
  for (int i = 0; i < headerReport.length; i++) {
      headerReport[i] = tableModel.getColumnName(i);
  }
  tableDataReport.setModel(tableModel);
}


public void printReport() {
  printReport(false);
}


public void setPrintService(PrintService printService) {
  this.printService = printService;
}


public void setLogoEnterprise(ImageIcon image) {

  lbImageReport.setIcon(new ImageIcon());
}


public void setPaperSize(String str) {
  this.size = str;
}

private void printReport(boolean view_print) {
  /**/
  fileNamePath = getFileNamePath();
  String txtprDonVi = txtDonVi.getText();
  String txtprDiaChi = txtDiaChi.getText();
  /**/
  String txtprQuyenSo = txtQuyenSo.getText();
  String txtprSo = txtSo.getText();
  String txtprNo = txtNo.getText();
  String txtprCo = txtCo.getText();
  /**/
  String txtprNguoiNhanTien = txtNguoiNhanTien.getText();
  String txtprDiachiNguoiNhanTien = txtDiaChiNguoiNhanTien.getText();
  String txtprLyDoChi = txtLyDoChi.getText();
  String txtprSoTienChi = txtSoTienChi.getText();
  String txtprVietBangChu = txtVietBangChu.getText();
  String txtprKemTheo = txtKemTheo.getText();
  String txtprDonViTien = txtDonViTien.getText();
  /**/
  String txtprNgayKi = txtNgayKi.getText();
  String txtprThangKi = txtThangKi.getText();
  String txtprNamKi = txtNamKi.getText();
  /**/
  String txtprTyGiaNgoaiTe = txtTyGiaNgoaiTe.getText();
  String txtprSoTienQuyDoi = txtSoTienQuyDoi.getText();
  //In báo cáo, Nhập giá trị tương ứng với key 
  String[] keys = new String[]{
      "prDonVi", "prDiaChi",
      "prQuyenSo", "prSo", "prNo", "prCo",
      "prNguoiNopTien", "prDiaChiNguoiNopTien", "prLyDoNop", "prSoTien", "prVietBangChu", "prKemTheo",
      "prNgayKi", "prThangKi", "prNamKi", "prDonViTien",
      "prTyGiaNgoaiTe", "prSoTienQuyDoi", "prLoai","prBoPhan","prMaDonVi","prDuAn",
      "prMaKH", "prNguoiLap"
  };
  String[] values = new String[]{
      txtprDonVi, txtprDiaChi,
      txtprQuyenSo, txtprSo, txtprNo, txtprCo,
      txtprNguoiNhanTien, txtprDiachiNguoiNhanTien, txtprLyDoChi, txtprSoTienChi, txtprVietBangChu, txtprKemTheo,
      txtprNgayKi, txtprThangKi, txtprNamKi, txtprDonViTien,
      txtprTyGiaNgoaiTe, txtprSoTienQuyDoi, loai,boPhan, maDonVi, duAn,maKH, nguoiLap
  };

  hashMap = new HashMap();
  if (keys.length == values.length) {
      for (int i = 0; i < keys.length; i++) {
          hashMap.put(keys[i], values[i]);
      }
  }
  reportExport(fileNamePath, hashMap, tableDataReport.getModel(), view_print);
}

private String getFileNamePath() {
  String str = null;
  if (size.equals("A4")) {
      File file = HKTFile.getFile("TemplatePrint", "PhieuThuA4.jasper");
      if (file.exists()) {
          str = file.getAbsolutePath();
          flagExtension = true;
      } else {
        str = "template/PhieuThuA4.jasper";
          flagExtension = false;
      }
  } else {
	  File file = HKTFile.getFile("TemplatePrint", "PhieuThuA5.jasper");
      if (file.exists()) {
          str = file.getAbsolutePath();
          flagExtension = true;
      } else {
        str = "template/PhieuThuA5.jasper";
          flagExtension = false;
      }
  }

  return str;
}

private void reportExport(String filePathResource, HashMap hashMap, TableModel model, boolean view_print) {
  if (!view_print) {
      ReportManager.getInstance().viewReport(filePathResource, hashMap, model, flagExtension);
  } else {
      ReportManager.getInstance().printReport(filePathResource, hashMap, model, printService, flagExtension);
  }
}
}
