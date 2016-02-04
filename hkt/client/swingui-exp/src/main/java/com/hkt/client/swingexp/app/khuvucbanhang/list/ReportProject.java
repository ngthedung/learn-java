package com.hkt.client.swingexp.app.khuvucbanhang.list;

import java.io.File;
import java.util.HashMap;

import javax.print.PrintService;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.core.HKTFile;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelProject;
import com.hkt.client.swingexp.app.print.ReportManager;

public class ReportProject extends JPanel{
	private String fileNamePath;
	private HashMap hashMap;
	private String size = "A4";
	private boolean flagExtension;
	private JTextField txtBenA1, txtBenA2, txtA1, txtA2, txtDiachiA1, txtDiachiA2, txtDCA1, txtDCA2, txtBenB1, txtBenB2, txtB1, txtB2, txtDiachiB1, txtDiachiB2, txtDCB1, txtDCB2,
						txtResto0,  txtCafe0, txtMarket0, txtKara0, txtSoft0, txtCoban0, txtCobanCong0, txtNangcao0, txtNangcaoCong0, txtSale0, txtChucnangle0,
						txt1Co0, txt2Co0, txt3Co0, txt4Co0, txt5Co0, txt6Co0, txt7Co0, txt8Co0, txt9Co0, txt1Ko0, txt2Ko0, txt3Ko0, txt4Ko0, txt5Ko0, txt6Ko0,
						txtTenKhachHang1, txtTenKhachHang2, txtDiachiKhachHang1, txtDiachiKhachHang2, txtSoCMTND, txtNgayCap, txtNoiCap, txtTelKhachHang, txtFaxKhachHang, txtEmail, txtMSTKhachHang, txtTaiKhoan, txtNganHang, txtChiNhanh, txtDaiDienKhachHang, 
						txtTen1, txtTen2, txtDiaChi1, txtDiaChi2, txtFax, txtTel, txtDaiDien, txtMST, txtSL1, txtSL2, txtSL3, txtSL4, txtSL5, txtDonGia1, txtDonGia2, txtDonGia3, txtDonGia4, txtGiamgia1, txtGiamgia2, txtGiamgia3, txtGiamgia4, txtThanhTien1, txtThanhTien2, txtThanhTien3, txtThanhTien4,
						txtTongcongSo, txtTongcongChu, txtLanTT, txtTenCD, txtNgayGiaoSP, txtTienThuLao, txtBangChu, txtDodai;
	private JTable tableDataReport;
	private String[] headerReport;
	private PrintService printService;
	private JLabel lbImageReport;
	
	public ReportProject(){
		initComponents();
	}
	
	private void initComponents() {
		
		txtBenA1 = new ExtendJTextField();
		txtBenA2 = new ExtendJTextField();
		txtA1 = new ExtendJTextField();
		txtA2 = new ExtendJTextField();
		txtDiachiA1 = new ExtendJTextField();
		txtDiachiA2 = new ExtendJTextField();
		txtDCA1 = new ExtendJTextField();
		txtDCA2 = new ExtendJTextField();
		txtBenB1 = new ExtendJTextField();
		txtBenB2 = new ExtendJTextField();
		txtB1 = new ExtendJTextField();
		txtB2 = new ExtendJTextField();
		txtDiachiB1 = new ExtendJTextField();
		txtDiachiB2 = new ExtendJTextField();
		txtDCB1 = new ExtendJTextField();
		txtDCB2 = new ExtendJTextField();
		
		txtResto0 = new ExtendJTextField();
		txtCafe0 = new ExtendJTextField();
		txtMarket0 = new ExtendJTextField();
		txtSale0 = new ExtendJTextField();
		txtSoft0 = new ExtendJTextField();
		txtKara0 = new ExtendJTextField();
		txtCoban0 = new ExtendJTextField();
		txtCobanCong0 = new ExtendJTextField();
		txtNangcao0 = new ExtendJTextField();
		txtNangcaoCong0 = new ExtendJTextField();
		txtChucnangle0 = new ExtendJTextField();
		
		txt1Co0 = new ExtendJTextField();
		txt2Co0 = new ExtendJTextField();
		txt3Co0 = new ExtendJTextField();
		txt4Co0 = new ExtendJTextField();
		txt5Co0 = new ExtendJTextField();
		txt6Co0 = new ExtendJTextField();
		txt7Co0 = new ExtendJTextField();
		txt8Co0 = new ExtendJTextField();
		txt9Co0 = new ExtendJTextField();
		txt1Ko0 = new ExtendJTextField();
		txt2Ko0 = new ExtendJTextField();
		txt3Ko0 = new ExtendJTextField();
		txt4Ko0 = new ExtendJTextField();
		txt5Ko0 = new ExtendJTextField();
		txt6Ko0 = new ExtendJTextField();
		
		txtTenKhachHang1 = new ExtendJTextField();
		txtTenKhachHang2 = new ExtendJTextField();
		txtDiachiKhachHang1 = new ExtendJTextField();
		txtDiachiKhachHang2 = new ExtendJTextField();
		txtSoCMTND = new ExtendJTextField();
		txtNgayCap = new ExtendJTextField();
		txtNoiCap = new ExtendJTextField();
		txtEmail = new ExtendJTextField();
		txtTaiKhoan = new ExtendJTextField();
		txtNganHang = new ExtendJTextField();
		txtChiNhanh = new ExtendJTextField();
		txtTelKhachHang = new ExtendJTextField();
		txtFaxKhachHang = new ExtendJTextField();
		txtMSTKhachHang = new ExtendJTextField();
		txtDaiDienKhachHang = new ExtendJTextField();
		txtTen1 = new ExtendJTextField();
		txtTen2 = new ExtendJTextField();
		txtDaiDien = new ExtendJTextField();
		txtDiaChi1 = new ExtendJTextField();
		txtDiaChi2 = new ExtendJTextField();
		txtMST = new ExtendJTextField();
		txtFax = new ExtendJTextField();
		txtTel = new ExtendJTextField();
		txtSL1 = new ExtendJTextField();
		txtSL2 = new ExtendJTextField();
		txtSL3 = new ExtendJTextField();
		txtSL4 = new ExtendJTextField();
		txtSL5 = new ExtendJTextField();
		txtDonGia1 = new ExtendJTextField();
		txtDonGia2 = new ExtendJTextField();
		txtDonGia3 = new ExtendJTextField();
		txtDonGia4 = new ExtendJTextField();
		txtGiamgia1 = new ExtendJTextField();
		txtGiamgia2 = new ExtendJTextField();
		txtGiamgia3 = new ExtendJTextField();
		txtGiamgia4 = new ExtendJTextField();
		txtThanhTien1 = new ExtendJTextField();
		txtThanhTien2 = new ExtendJTextField();
		txtThanhTien3 = new ExtendJTextField();
		txtThanhTien4 = new ExtendJTextField();
		txtLanTT = new ExtendJTextField();
		txtTongcongSo = new ExtendJTextField();
		txtTongcongChu = new ExtendJTextField();
		txtTenCD = new ExtendJTextField();
		txtTienThuLao = new ExtendJTextField();
		txtBangChu = new ExtendJTextField();
		txtNgayGiaoSP = new ExtendJTextField();
		txtDodai = new ExtendJTextField();
		
		tableDataReport = new JTable();
		tableDataReport.setModel(new javax.swing.table.DefaultTableModel(
		        new Object [][] {
		        		{null, null, null, null},
		        		
		        },
		        new String [] {
		            "Title 1", "Title 2", "Title 3", "Title 4"
		        }
		    ));
	}
	
	public void setParameter(String... strings) {
		  try {
			  txtBenA1.setText(strings[0]);
		      txtA1.setText(strings[1]);
		      txtTenKhachHang1.setText(strings[2]);
		      txtBenA2.setText(strings[3]);
		      txtA2.setText(strings[4]);
		      txtTenKhachHang2.setText(strings[5]);
		      txtDiachiA1.setText(strings[6]);
		      txtDCA1.setText(strings[7]);
		      txtDiachiKhachHang1.setText(strings[8]);
		      txtDiachiA2.setText(strings[9]);
		      txtDCA2.setText(strings[10]);
		      txtDiachiKhachHang2.setText(strings[11]);
		      txtSoCMTND.setText(strings[12]);
		      txtNgayCap.setText(strings[13]);
		      txtNoiCap.setText(strings[14]);
		      txtTelKhachHang.setText(strings[15]);
		      txtFaxKhachHang.setText(strings[16]);
		      txtEmail.setText(strings[17]);
		      txtMSTKhachHang.setText(strings[18]);
		      txtTaiKhoan.setText(strings[19]);
		      txtNganHang.setText(strings[20]);
		      txtChiNhanh.setText(strings[21]);
		      txtDaiDienKhachHang.setText(strings[22]);
		      
		      txtBenB1.setText(strings[23]);
		      txtB1.setText(strings[24]);
		      txtTen1.setText(strings[25]);
		      txtBenB2.setText(strings[26]);
		      txtB2.setText(strings[27]);
		      txtTen2.setText(strings[28]);
		      txtDiachiB1.setText(strings[29]);
		      txtDCB1.setText(strings[30]);
		      txtDiaChi1.setText(strings[31]);
		      txtDiachiB2.setText(strings[32]);
		      txtDCB2.setText(strings[33]);
		      txtDiaChi2.setText(strings[34]);
		      txtTel.setText(strings[35]);
		      txtFax.setText(strings[36]);
		      txtMST.setText(strings[37]);
		      txtDaiDien.setText(strings[38]);
		      
		      txtResto0.setText(strings[39]);
		      txtCafe0.setText(strings[40]);
		      txtSoft0.setText(strings[41]);
		      txtMarket0.setText(strings[42]);
		      txtKara0.setText(strings[43]);
		      txtCoban0.setText(strings[44]);
		      txtNangcao0.setText(strings[45]);
		      txtSale0.setText(strings[46]);
		      txtCobanCong0.setText(strings[47]);
		      txtNangcaoCong0.setText(strings[48]);
		      txtChucnangle0.setText(strings[49]);
		      
		      txt1Co0.setText(strings[50]);
		      txt2Co0.setText(strings[51]);
		      txt1Ko0.setText(strings[52]);
		      txt3Co0.setText(strings[53]);
		      txt2Ko0.setText(strings[54]);
		      txt4Co0.setText(strings[55]);
		      txt3Ko0.setText(strings[56]);
		      txt5Co0.setText(strings[57]);
		      txt4Ko0.setText(strings[58]);
		      txt6Co0.setText(strings[59]);
		      txt5Ko0.setText(strings[60]);
		      txt7Co0.setText(strings[61]);
		      txt6Ko0.setText(strings[62]);
		      txt8Co0.setText(strings[63]);
		      txt9Co0.setText(strings[64]);
		      
		      txtSL1.setText(strings[65]);
		      txtDonGia1.setText(strings[66]);
		      txtGiamgia1.setText(strings[67]);
		      txtThanhTien1.setText(strings[68]);
		      txtSL2.setText(strings[69]);
		      txtDonGia2.setText(strings[70]);
		      txtGiamgia2.setText(strings[71]);
		      txtThanhTien2.setText(strings[72]);
		      txtSL3.setText(strings[73]);
		      txtDonGia3.setText(strings[74]);
		      txtGiamgia3.setText(strings[75]);
		      txtThanhTien3.setText(strings[76]);
		      txtSL4.setText(strings[77]);
		      txtSL5.setText(strings[78]);
		      txtDonGia4.setText(strings[79]);
		      txtGiamgia4.setText(strings[80]);
		      txtThanhTien4.setText(strings[81]);
		      txtTongcongSo.setText(strings[82]);
		      txtTongcongChu.setText(strings[83]);
		      txtLanTT.setText(strings[84]);
		      txtTenCD.setText(strings[85]);
		      txtNgayGiaoSP.setText(strings[86]);
		      txtTienThuLao.setText(strings[87]);
		      txtBangChu.setText(strings[88]);
		      txtDodai.setText(strings[89]);
		      
		  } catch (Exception e) {
			  e.printStackTrace();
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
		fileNamePath = getFileNamePath();
		
		String txtprBenA1 = txtBenA1.getText();
		String txtprBenA2 = txtBenA2.getText();
		String txtprA1 = txtA1.getText();
		String txtprA2 = txtA2.getText();
		String txtprDiachiA1 = txtDiachiA1.getText();
		String txtprDiachiA2 = txtDiachiA2.getText();
		String txtprDCA1 = txtDCA1.getText();
		String txtprDCA2 = txtDCA2.getText();
		String txtprBenB1 = txtBenB1.getText();
		String txtprBenB2 = txtBenB2.getText();
		String txtprB1 = txtB1.getText();
		String txtprB2 = txtB2.getText();
		String txtprDiachiB1 = txtDiachiB1.getText();
		String txtprDiachiB2 = txtDiachiB2.getText();
		String txtprDCB1 = txtDCB1.getText();
		String txtprDCB2 = txtDCB2.getText();
		
		String txtprTenKhachHang1 = txtTenKhachHang1.getText();
		String txtprTenKhachHang2 = txtTenKhachHang2.getText();
		String txtprDiachiKhachHang1 = txtDiachiKhachHang1.getText();
		String txtprDiachiKhachHang2 = txtDiachiKhachHang2.getText();
		String txtprCMTND = txtSoCMTND.getText();
		String txtprNgayCap = txtNgayCap.getText();
		String txtprNoiCap = txtNoiCap.getText();
		String txtprTelKhachHang = txtTelKhachHang.getText();
		String txtprFaxKhachHang= txtFaxKhachHang.getText();
		String txtprEmailKhachHang= txtEmail.getText();
		String txtprDaiDienKhachHang = txtDaiDienKhachHang.getText();
		String txtprTaiKhoan = txtTaiKhoan.getText();
		String txtprNganHang = txtNganHang.getText();
		String txtprChiNhanh = txtChiNhanh.getText();
		String txtprMSTKhachHang = txtMSTKhachHang.getText();
		  
		String txtprDiaChi1 = txtDiaChi1.getText();
		String txtprDiaChi2 = txtDiaChi2.getText();
		String txtprTen1 = txtTen1.getText();
		String txtprTen2 = txtTen2.getText();
		String txtprMST = txtMST.getText();
		String txtprDaiDien= txtDaiDien.getText();
		String txtprFax = txtFax.getText();
		String txtprTel = txtTel.getText();

		String txtprResto0 = txtResto0.getText();
		String txtprMarket0 = txtMarket0.getText();
		String txtprCafe0 = txtCafe0.getText();
		String txtprKara0 = txtKara0.getText();
		String txtprSoft0 = txtSoft0.getText();
		String txtprCoban0 = txtCoban0.getText();
		String txtprCobanCong0 = txtCobanCong0.getText();
		String txtprNangcao0 = txtNangcao0.getText();
		String txtprNangcaoCong0 = txtNangcaoCong0.getText();
		String txtprSale0 = txtSale0.getText();
		String txtprChucnangle0 = txtChucnangle0.getText();

		String txtpr1Co0 = txt1Co0.getText();
		String txtpr2Co0 = txt2Co0.getText();
		String txtpr3Co0 = txt3Co0.getText();
		String txtpr4Co0 = txt4Co0.getText();
		String txtpr5Co0 = txt5Co0.getText();
		String txtpr6Co0 = txt6Co0.getText();
		String txtpr7Co0 = txt7Co0.getText();
		String txtpr8Co0 = txt8Co0.getText();
		String txtpr9Co0 = txt9Co0.getText();
		String txtpr1Ko0 = txt1Ko0.getText();
		String txtpr2Ko0 = txt2Ko0.getText();
		String txtpr3Ko0 = txt3Ko0.getText();
		String txtpr4Ko0 = txt4Ko0.getText();
		String txtpr5Ko0 = txt5Ko0.getText();
		String txtpr6Ko0 = txt6Ko0.getText();
		
		String txtprSL1 = txtSL1.getText();
		String txtprSL2 = txtSL2.getText();
		String txtprSL3 = txtSL3.getText();
		String txtprSL4 = txtSL4.getText();
		String txtprSL5 = txtSL5.getText();
		
		String txtprDonGia1 = txtDonGia1.getText();
		String txtprDonGia2 = txtDonGia2.getText();
		String txtprDonGia3 = txtDonGia3.getText();
		String txtprDonGia4 = txtDonGia4.getText();
		
		String txtprGiamgia1 = txtGiamgia1.getText();
		String txtprGiamgia2 = txtGiamgia2.getText();
		String txtprGiamgia3 = txtGiamgia3.getText();
		String txtprGiamgia4 = txtGiamgia4.getText();
		
		String txtprThanhTien1 = txtThanhTien1.getText();
		String txtprThanhTien2 = txtThanhTien2.getText();
		String txtprThanhTien3 = txtThanhTien3.getText();
		String txtprThanhTien4 = txtThanhTien4.getText();
		
		String txtprTongcongSo = txtTongcongSo.getText();
		String txtprTongcongChu = txtTongcongChu.getText();
		String txtprLanTT = txtLanTT.getText();
		
		String txtprTenCD = txtTenCD.getText();
		String txtprNgayGiaoSP = txtNgayGiaoSP.getText();
		String txtprTienThuLao = txtTienThuLao.getText();
		String txtprBangChu = txtBangChu.getText();
		String txtprDodai = txtDodai.getText();

		 //In báo cáo, Nhập giá trị tương ứng với key 
		  String[] keys = new String[]{
				  "prBenA1", "PrA1", "prTenKhachHang1", "prBenA2", "PrA2","prTenKhachHang2", "prDiaChiA1", "prDCA1", "prDiaChiKhachHang1", "prDiaChiA2", "prDCA2", "prDiaChiKhachHang2","prCMTND",
		      "prNgayCap", "prNoiCap", "prTelKhachHang",
		      "prFaxKhachHang", "prMSTKhachHang", "prDaiDienKhachHang",
		      "prTaiKhoan", "prNganHang", "prChiNhanh",
		      "prBenB1", "PrB1","prTen1", "prBenB2", "PrB2","prTen2", "prDiaChiB1", "prDCB1","prDiaChi1", "prDiaChiB2", "prDCB2","prDiaChi2", "prTel",
		      "prFax", "prEmail","prMST", "prDaiDien",
		      "prResto0", "prCafe0", "prSoft0", "prMarket0", "prKara0", "prCoban0", "prNangcao0", "prSale0", "prCobanCong0", "prNangcaoCong0",  "prChucnangle0",
		      "pr1Co0", "pr2Co0", "pr1Ko0", "pr3Co0", "pr2Ko0", "pr4Co0", "pr3Ko0", "pr5Co0", "pr4Ko0", "pr6Co0", "pr5Ko0", "pr7Co0", "pr6Ko0", "pr8Co0", "pr9Co0", 
		      "prSL1", "prDonGia1","prGiamgia1", "prThanhTien1",
		      "prSL2", "prDonGia2","prGiamgia2", "prThanhTien2",
		      "prSL3", "prDonGia3","prGiamgia3", "prThanhTien3", "prSL4",
		      "prSL5", "prDonGia4","prGiamgia4", "prThanhTien4",
		      "prTongcongSo", "prTongcongChu", "prLanTT", "prTenChuyenDe", "prNgayGiaoSP", "prTienThuLao", "prTienThuLaoChu", "prDoDai"
		  };
		  String[] values = new String[]{
				  txtprBenA1, txtprA1, txtprTenKhachHang1, txtprBenA2, txtprA2, txtprTenKhachHang2, txtprDiachiA1, txtprDCA1, txtprDiachiKhachHang1,  txtprDiachiA2, txtprDCA2, txtprDiachiKhachHang2, txtprCMTND,
				  txtprNgayCap, txtprNoiCap, txtprTelKhachHang,
				  txtprFaxKhachHang, txtprMSTKhachHang, txtprDaiDienKhachHang,
				  txtprTaiKhoan, txtprNganHang, txtprChiNhanh,
				  txtprBenB1, txtprB1, txtprTen1, txtprBenB2, txtprB2, txtprTen2, txtprDiachiB1, txtprDCB1, txtprDiaChi1, txtprDiachiB2, txtprDCB2, txtprDiaChi2, txtprTel,
				  txtprFax, txtprEmailKhachHang, txtprMST, txtprDaiDien,
				  txtprResto0, txtprCafe0, txtprSoft0, txtprMarket0, txtprKara0, txtprCoban0, txtprNangcao0, txtprSale0,
				  txtprCobanCong0, txtprNangcaoCong0, txtprChucnangle0,
				  txtpr1Co0, txtpr2Co0, txtpr1Ko0, txtpr3Co0, txtpr2Ko0, txtpr4Co0, txtpr3Ko0,
				  txtpr5Co0, txtpr4Ko0, txtpr6Co0, txtpr5Ko0, txtpr7Co0, txtpr6Ko0, txtpr8Co0, txtpr9Co0,
				  txtprSL1, txtprDonGia1, txtprGiamgia1, txtprThanhTien1,
				  txtprSL2, txtprDonGia2, txtprGiamgia2, txtprThanhTien2,
				  txtprSL3, txtprDonGia3, txtprGiamgia3, txtprThanhTien3, txtprSL4,
				  txtprSL5, txtprDonGia4, txtprGiamgia4, txtprThanhTien4,
				  txtprTongcongSo, txtprTongcongChu, txtprLanTT, txtprTenCD, txtprNgayGiaoSP, txtprTienThuLao, txtprBangChu, txtprDodai 
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
			  if (PanelProject.choose){
				  File file1 = HKTFile.getFile("TemplatePrint", "HD1.jasper");
			      if (file1.exists()) {
			          str = file1.getAbsolutePath();
			          flagExtension = true;
			      } else {
			        str = "template/HD1.jasper";
			          flagExtension = false;
			      }
			  } else {
				  File file2 = HKTFile.getFile("TemplatePrint", "HD2.jasper");
			      if (file2.exists()) {
			          str = file2.getAbsolutePath();
			          flagExtension = true;
			      } else {
			        str = "template/HD2.jasper";
			          flagExtension = false;
			      }
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
