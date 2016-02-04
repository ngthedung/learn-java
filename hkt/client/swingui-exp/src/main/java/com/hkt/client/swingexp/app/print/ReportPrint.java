package com.hkt.client.swingexp.app.print;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.hkt.client.swingexp.app.banhang.screen.often.TableModelInvoiceItem;
import com.hkt.client.swingexp.app.component.ImageTool;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.GoogleMail;
import com.hkt.client.swingexp.app.core.HKTFile;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.khachhang.OrganizationBasic;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.PrintMachineModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.print.entity.PrintMachine;
import com.hkt.module.product.entity.Product;
import com.hkt.module.restaurant.entity.Table;

public class ReportPrint {
	private static ReportPrint reportPrint;

	public static ReportPrint getInstance() {
		if (reportPrint == null) {
			reportPrint = new ReportPrint();
		}
		return reportPrint;
	}

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private String fileNamePath;
	private HashMap hashMap;
	private PrintService printService;
	private ImageIcon logo = new ImageIcon();
	private String size;
	private boolean flagExtension;
	private TableModel tableModel;
	private String lable[], lable1[];
	private List<String> list1 = new ArrayList<String>();
	private List<String> list2 = new ArrayList<String>();
	private String prMoneyCongGop = "", prTextMoney = "";
	private String strNameForder = "TemplatePrint";
	private String prSoDiem = "";
	private String prSoTien = "";
	private String prTienTraTruocConLai = "";
	private String prLienHoaDon = "";
	private String prSoLanInHoaDon = "";
	private String prTongSoDiem = "";
	private String prTongSoDiemTien = "";
	private String prSoDiemConLai = "";
	private String prDiemTienConLai = "";
	private String prTienTraTruoc = "";
	private String prTienTraTruocDungTrongHD = "";
	private String prNgayHenConPhaiThanhToan = "";
	private String prSoTienKhachDua = "";
	private String prSoTienTraKhach = "";
	private String prTienTraNgay = "", prDiaChiKH = "", prSDT = "";
	private String prSoKhach = "", lienHoaDon = "";
	private String prNVOrder = "";
	private String prMuaHo1, prMuaHo2, prMuaHo3, prMoneyMh1, prMoneyMh2, prMoneyMh3;
	private String[] valuesKitchen;
	private Profile profile;

	public ReportPrint() {
		txtKhuvuc = new javax.swing.JTextField();
		txtGiovao = new javax.swing.JTextField();
		dcNgayLap = new MyJDateChooser();
		txtSophieu = new javax.swing.JTextField();
		txtBanAn = new javax.swing.JTextField();
		txtNhanvien = new javax.swing.JTextField();
		txtTongtien = new javax.swing.JTextField();
		txtGiamgia = new javax.swing.JTextField();
		txtThue = new javax.swing.JTextField();
		txtTongcong = new javax.swing.JTextField();
		txtDathanhtoan = new javax.swing.JTextField();
		txtTenDoanhNghiep = new javax.swing.JTextField();
		txtDiaChi = new javax.swing.JTextField();
		txtGioIn = new javax.swing.JTextField();
		dcNgayIn = new MyJDateChooser();
		txtMessenger = new javax.swing.JLabel();
		txtTel = new javax.swing.JTextField();
		txtFax = new javax.swing.JTextField();
		txtConphaithanhtoan = new javax.swing.JTextField();
		txtPTChietkhau = new javax.swing.JTextField();
		txtPTThue = new javax.swing.JTextField();
		txtUnitMoney = new javax.swing.JTextField();
		txtPhiDV = new javax.swing.JTextField();
		txtTyGia = new javax.swing.JTextField();
		txtQuyDoi = new javax.swing.JTextField();
		txtPTPhiDV = new javax.swing.JTextField();
		txtTenKhachHang = new javax.swing.JTextField();
		String aa[] = { " ", " ", " " };
		list1.add(" ");
		list1.add(" ");
		list1.add(" ");
		lable = aa;
		String bb[] = { " ", " ", " " };
		list2.add(" ");
		list2.add(" ");
		list2.add(" ");
		lable1 = bb;
		dcNgayLap.setDate(new Date());
		dcNgayIn.setDate(new Date());
		profile = AccountModelManager.getInstance().getProfileConfigAdmin();
	}

	public void setStrNameForder(String strNameForder) {
		this.strNameForder = strNameForder;
	}

	public void setTableModel(TableModel tableModel) {
		this.tableModel = tableModel;
	}

	public void setValuesKitchen(String[] valuesKitchen) {
		this.valuesKitchen = valuesKitchen;
	}

	private void printReportKitchen(boolean view_print) {
		fileNamePath = getFileNamePathKitchen();
		String[] keys = new String[] { "prTenDoanhNghiep", "prDiaChi", "prTel", "prFax", "prTenBaoCao", "prKhuVuc",
		    "prBan", "prGioVao", "prNhanVien", "prNgayIn", "prGioIn" };

		hashMap = new HashMap();
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			hashMap.put("prH" + i, tableModel.getColumnName(i));
		}
		if (keys.length == valuesKitchen.length) {
			for (int i = 0; i < keys.length; i++) {
				hashMap.put(keys[i], valuesKitchen[i]);
			}
		}
		hashMap.put("logo", logo.getImage());
		reportExport(fileNamePath, hashMap, tableModel, view_print);
	}

	private String getFileNamePathKitchen() {
		String str = null;
		if (size.equals("K57")) {
			File file = HKTFile.getFile("TemplatePrint", "PhieuCheBienK57.jasper");
			if (file.exists()) {
				str = file.getAbsolutePath();
				flagExtension = true;
			} else {
				str = "template/PhieuCheBienK57.jasper";
				flagExtension = false;
			}
		}
		if (size.equals("A6")) {
			File file = HKTFile.getFile("TemplatePrint", "PhieuCheBienA6.jasper");
			if (file.exists()) {
				str = file.getAbsolutePath();
				flagExtension = true;
			} else {
				str = "template/PhieuCheBienA6.jasper";
				flagExtension = false;
			}
		}
		if (size.equals("A5")) {
			File file = HKTFile.getFile("TemplatePrint", "PhieuCheBienA5.jasper");
			if (file.exists()) {
				str = file.getAbsolutePath();
				flagExtension = true;
			} else {
				str = "template/PhieuCheBienA5.jasper";
				flagExtension = false;
			}
		}
		if (size.equals("K80")) {
			File file = HKTFile.getFile("TemplatePrint", "PhieuCheBienK80.jasper");
			if (file.exists()) {
				str = file.getAbsolutePath();
				flagExtension = true;
			} else {
				str = "template/PhieuCheBienK80.jasper";
				flagExtension = false;
			}
		}

		return str;
	}

	private void reportExport(String filePathResource, HashMap hashMap, TableModel model, boolean view_print) {
		try {
			if (!view_print) {
				ReportManager.getInstance().viewReport(filePathResource, hashMap, model, flagExtension);
			} else {
				ReportManager.getInstance().printReport(filePathResource, hashMap, model, printService, flagExtension);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setPrintService(PrintService printService) {
		this.printService = printService;
	}

	public void setLogoEnterprise(ImageIcon image) {
		if (image == null) {
			this.logo = new ImageIcon(getClass().getResource("template/logo.jpg"));
		} else {
			this.logo = image;
		}

	}

	public void setPaperSize(String str) {
		this.size = str;
	}

	private void printReportPayment(boolean view_print, String name) {
		try {
			if (!AccountingModelManager.isVN) {
				strNameForder = "NgonNguKhac";
			}
			fileNamePath = getFileNamePathPayment();
			if (!flagExtension) {
				strNameForder = "TemplatePrint";
				fileNamePath = getFileNamePathPayment();
			}
			String moneyGiamGia = txtUnitMoney.getText();
			String prMoney2 = txtUnitMoney.getText();
			String prMoney3 = txtUnitMoney.getText();
			String prNoiDungGhiChu = "";
			String prLbGhiChu = "";
			String lbMuaHo = "";
			String prCongGop = "Cộng Gộp:";
			String lbPd = "CK %:", lbPt = "Thuế %:", lbMd = "Giảm giá:", lbMt = "Thuế:", lbTongCong = "Tổng cộng:", lbDatra = "Đã trả:", lbPhaiTra = "Phải trả:", money1 = txtUnitMoney
			    .getText(), money2 = txtUnitMoney.getText(), lbPhiDV = "Phí DV:", lbTyGia = "Tỷ giá:", lbTyGia1 = "", lbTyGia2 = "", lbQuyDoi = "Quy đổi:", tygia1 = "", tygia2 = "", moneyQuyDoi = "VND", lbPTPhiDV = "DV %", money3 = txtUnitMoney
			    .getText();
			String lbSodiem = "Số điểm", lbTien = "Số tiền", money4 = txtUnitMoney.getText();
			for (int i = 0; i < lable.length; i++) {
				list1.set(i, lable[i]);
				list2.set(i, lable1[i]);
			}
			prMuaHo1 = list1.get(0);
			prMuaHo2 = list1.get(1);
			prMuaHo3 = list1.get(2);
			prMoneyMh1 = list2.get(0);
			prMoneyMh2 = list2.get(1);
			prMoneyMh3 = list2.get(2);

			if (!prMuaHo1.trim().isEmpty() || !prMuaHo2.trim().isEmpty() || !prMuaHo3.trim().isEmpty()) {
				lbMuaHo = "Mua hộ khách hàng:";
			}
			if (prMuaHo1.equals("") || prMuaHo1 == null || prMuaHo1.length() == 0) {
				prMoneyMh1 = "";
			} else {
				if (prMuaHo1.trim().length() != 0) {
					prMuaHo1 = prMuaHo1 + " :";
				}
			}
			if (prMuaHo2.equals("") || prMuaHo2 == null || prMuaHo2.length() == 0) {
				prMoneyMh2 = "";
			} else {
				if (prMuaHo2.trim().length() != 0) {
					prMuaHo2 = prMuaHo2 + " :";
				}
			}
			if (prMuaHo3.equals("") || prMuaHo3 == null || prMuaHo3.length() == 0) {
				prMoneyMh3 = "";
			} else {
				if (prMuaHo3.trim().length() != 0) {
					prMuaHo3 = prMuaHo3 + " :";
				}
			}

			if (lbMuaHo.isEmpty() || lbMuaHo == null || lbMuaHo.length() == 0) {
				prCongGop = "";
				prMoneyCongGop = "";
			}
			if (txtGiamgia.getText().isEmpty() || txtGiamgia.getText().equals("0")) {
				txtGiamgia.setText("");
				lbMd = "";
				moneyGiamGia = "";
			}
			if (txtPTChietkhau.getText().isEmpty() || txtPTChietkhau.getText().equals("0")) {
				txtPTChietkhau.setText("");
				lbPd = "";
			}
			if (prSoDiem.isEmpty() || prSoDiem.equals("0")) {
				lbSodiem = "";
				lbTien = "";
				money4 = "";
				prSoDiem = "";
				prSoTien = "";
			}
			if (txtThue.getText().isEmpty() || txtThue.getText().equals("0")) {
				txtThue.setText("");
				prMoney2 = "";
				txtPTThue.setText("");
				lbPt = "";
				lbMt = "";
			}
			if (txtPhiDV.getText().isEmpty() || txtPhiDV.getText().equals("0")) {
				txtPhiDV.setText("");
				prMoney3 = "";
				lbPhiDV = "";
				lbPTPhiDV = "";
				txtPTPhiDV.setText("");
			}
			if (txtTongcong.getText().isEmpty() || txtTongcong.getText().equals("0")) {
				txtTongcong.setText("");
				lbTongCong = "";
				money3 = "";
			}
			if (txtDathanhtoan.getText().isEmpty() || txtDathanhtoan.getText().equals("0")) {
				txtDathanhtoan.setText("");
				lbDatra = "";
				// txtTyGia.setText("");
				lbTyGia1 = "";
				money1 = "";
			}
			if (txtConphaithanhtoan.getText().isEmpty()) {
				txtConphaithanhtoan.setText("");
				tygia2 = "";
				lbPhaiTra = "";
				lbTyGia2 = "";
				money2 = "";
				prNgayHenConPhaiThanhToan = "";
			}
			if (txtQuyDoi.getText().isEmpty() || txtQuyDoi.getText().equals("0")) {
				txtQuyDoi.setText("");
				moneyQuyDoi = "";
				lbQuyDoi = "";
				txtTyGia.setText("");
				lbTyGia = "";
			} else {

			}
			if (prLienHoaDon.equals("0")) {
				prLienHoaDon = "";
			}
			if (prTienTraTruocConLai.equals("0")) {
				prTienTraTruocConLai = "";
			}
			if (prTongSoDiem.equals("0")) {
				prTongSoDiem = "";
			}
			if (prTongSoDiemTien.equals("0")) {
				prTongSoDiemTien = "";
			}
			if (prSoDiemConLai.equals("0")) {
				prSoDiemConLai = "";
			}
			if (prDiemTienConLai.equals("0")) {
				prDiemTienConLai = "";
			}
			if (prTienTraTruoc.equals("0")) {
				prTienTraTruoc = "";
			}
			if (prTienTraTruocDungTrongHD.equals("0")) {
				prTienTraTruocDungTrongHD = "";
			}
			if (prTienTraTruocDungTrongHD.equals("0")) {
				prTienTraTruocDungTrongHD = "";
			}
			String[] keys = new String[] { "prTenDoanhNghiep", "prDiaChi", "prTel", "prFax", "prNgayLap", "prSoPhieu",
			    "prKhuVuc", "prBan", "prGioVao", "prNhanVien", "prTongTien", "prChieuKhau%", "prGiamGia", "prThue%",
			    "prThue", "prTongCong", "prDaThanhToan", "prConPhaiThanhToan", "prStt", "prNgayIn", "prGioIn", "money",
			    "prLbCK%", "prLbThue%", "prLbGiamGia", "prLbTienThue", "prLbTongCong", "prLbDaTra", "prLbPhaiTra", "money1",
			    "money2", "prPhiDV", "prTyGia", "lbPhiDV", "lbTyGia", "prTyGia1", "prTyGia2", "prQuyDoi", "lbTyGia1",
			    "lbTyGia2", "lbQuyDoi", "moneyQuyDoi", "prPhiDV%", "lbPhiDV%", "money3", "prMoney1", "prMoney2", "prMoney3",
			    "prNoiDungGhiChu", "prLbGhiChu", "prLbMuaHo", "prMuaHo1", "prMuaHo2", "prMuaHo3", "prMoneyMH1", "prMoneyMH2",
			    "prMoneyMH3", "prCongGop", "prMoneyCongGop", "prTextMoney", "prSoKhach", "prNhanVienBan", "prlbSoDiem",
			    "prlbDiemTien", "prSoDiem", "prDiemTien", "prMoney4", "prTienTraTruocConLai", "prLienHoaDon",// Liên
			                                                                                                 // hóa
			                                                                                                 // đơn
			    "prSoLanInHoaDon",// số lần in hóa đơn
			    "prTongSoDiem",// Tổng số điểm
			    "prTongSoDiemTien",// Tổng số điêm quy ra tiền
			    "prSoDiemConLai",// Số điểm còn lại
			    "prDiemTienConLai",// điểm còn lại quy ra tiền
			    "prTienTraTruoc",// Tiền tra trước
			    "prTienTraTruocDungTrongHD",// Tiền tra trước dùng trong HD
			    "prNgayHenConPhaiThanhToan",// Ngày hẹn còn phải thanh toán
			    "prSoTienKhachDua",// Số tiền khách đưa
			    "prSoTienTraKhach",// Số tiền trả khách
			    "prTienTraNgay",// Số tiền trả ngày hôm đó
			    "prDiaChiKH", "prSDT" };

			String[] values = new String[] { txtTenDoanhNghiep.getText(), txtDiaChi.getText(), txtTel.getText(),
			    txtFax.getText(), df.format(dcNgayLap.getDate()), txtSophieu.getText(), txtKhuvuc.getText(),
			    txtBanAn.getText(), txtGiovao.getText(), txtNhanvien.getText(), txtTongtien.getText(),
			    txtPTChietkhau.getText(), txtGiamgia.getText(), txtPTThue.getText(), txtThue.getText(),
			    txtTongcong.getText(), txtDathanhtoan.getText(), txtConphaithanhtoan.getText(), txtMessenger.getText(),
			    df.format(dcNgayIn.getDate()), txtGioIn.getText(), txtUnitMoney.getText(), lbPd, lbPt, lbMd, lbMt,
			    lbTongCong, lbDatra, lbPhaiTra, money1, money2, txtPhiDV.getText(), txtTyGia.getText(), lbPhiDV, lbTyGia,
			    tygia1, tygia2, txtQuyDoi.getText(), lbTyGia1, lbTyGia2, lbQuyDoi, moneyQuyDoi, txtPTPhiDV.getText(),
			    lbPTPhiDV, money3, moneyGiamGia, prMoney2, prMoney3, prNoiDungGhiChu, prLbGhiChu, lbMuaHo, prMuaHo1,
			    prMuaHo2, prMuaHo3, prMoneyMh1, prMoneyMh2, prMoneyMh3, prCongGop, prMoneyCongGop, prTextMoney, prSoKhach,
			    prNVOrder, lbSodiem, lbTien, prSoDiem, prSoTien, money4, prTienTraTruocConLai, prLienHoaDon// Liên
			                                                                                               // hóa
			                                                                                               // đơn
			    , prSoLanInHoaDon// Số lần in hóa đơn
			    , prTongSoDiem// Tổng số điểm
			    , prTongSoDiemTien// Tổng số điêm quy ra tiền
			    , prSoDiemConLai // Số điểm còn lại
			    , prDiemTienConLai// điểm còn lại quy ra tiền
			    , prTienTraTruoc// Tiền tra trước
			    , prTienTraTruocDungTrongHD// Tiền tra trước dùng trong HD
			    , prNgayHenConPhaiThanhToan// Ngày hẹn còn phải thanh toán
			    , prSoTienKhachDua// Số tiền khách đưa
			    , prSoTienTraKhach// Số tiền trả khách
			    , prTienTraNgay// Số tiền trả ngày hôm đó
			    , prDiaChiKH, prSDT };

			hashMap = new HashMap();
			for (int i = 0; i < tableModel.getColumnCount(); i++) {
				hashMap.put("prH" + i, tableModel.getColumnName(i));
			}
			hashMap.put("prH" + tableModel.getColumnCount(), "");
			if (keys.length == values.length) {
				for (int i = 0; i < keys.length; i++) {
					hashMap.put(keys[i], values[i]);
				}
			}

			hashMap.put("prTenKhachHang", txtTenKhachHang.getText());
			hashMap.put("prSoLanInHoaDon", String.valueOf(AccountingModelManager.print));
			if (!prLoaiHinh.isEmpty()) {
				hashMap.put("prLoaiHinh", prLoaiHinh);
			}
			if (name != null) {
				if (profile.get("Exel") != null) {
					hashMap.put("logo", new ImageIcon().getImage());
					ReportManager.getInstance().exelReport(fileNamePath, hashMap, tableModel, flagExtension, name);
				}
				if (profile.get("ExelMail") != null) {
					hashMap.put("logo", new ImageIcon().getImage());
					name= name.replace(":", "_");
					ReportManager.getInstance().exelReportMailPayment(fileNamePath, hashMap, tableModel, flagExtension, name);
					String str = this.getClass().getResource("").getPath().substring(5);
					File file = new File(str.substring(0, str.indexOf("lib")) + "bin/mail/MailInvoice.jar");
          String aa= file.getAbsolutePath().replaceAll("%20", " ");
					ProcessBuilder pb = new ProcessBuilder("java", "-jar", aa);
					pb.start();
				}
				
			} else {
				try {
					Account account = AccountModelManager.getInstance().getAccountByLoginId(
					    ManagerAuthenticate.getInstance().getOrganizationLoginId());
					String image = (String) account.getProfiles().getBasic().get(OrganizationBasic.LOGOURL);
					if (image != null && !image.trim().isEmpty()) {
						ImageIcon icon = ImageTool.getInstance().decodeToImage(image);
						hashMap.put("logo", icon.getImage());
					} else {
						hashMap.put("logo", logo.getImage());
					}
				} catch (Exception e) {
					hashMap.put("logo", new ImageIcon().getImage());
				}

				if (view_print) {
					for (int i = 0; i < AccountingModelManager.print; i++) {
						reportExport(fileNamePath, hashMap, tableModel, view_print);
					}
				} else {
					reportExport(fileNamePath, hashMap, tableModel, view_print);
				}

			}
			AccountingModelManager.isVN = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String getFileNamePathPayment() {
		String str = null;
		if (size.equals("A6")) {
			File file = HKTFile.getFile(strNameForder, "PhieuThanhToanA6.jasper");
			if (file.exists()) {
				str = file.getAbsolutePath();
				flagExtension = true;
			} else {
				str = "template/PhieuThanhToanA6.jasper";
				flagExtension = false;
			}
		}
		if (size.equals("A5")) {
			File file = HKTFile.getFile(strNameForder, "PhieuThanhToanA5.jasper");
			if (file.exists()) {
				str = file.getAbsolutePath();
				flagExtension = true;
			} else {
				str = "template/PhieuThanhToanA5.jasper";
				flagExtension = false;
			}
		}
		if (size.equals("A4")) {
			File file = HKTFile.getFile(strNameForder, "PhieuThanhToanA4.jasper");
			if (file.exists()) {
				str = file.getAbsolutePath();
				flagExtension = true;
			} else {
				str = "template/PhieuThanhToanA4.jasper";
				flagExtension = false;
			}
		}
		if (size.equals("K80")) {
			File file = HKTFile.getFile(strNameForder, "PhieuThanhToanK80.jasper");
			if (file.exists()) {
				str = file.getAbsolutePath();
				flagExtension = true;
			} else {
				str = "template/PhieuThanhToanK80.jasper";
				flagExtension = false;
			}
		}
		if (size.equals("K57")) {
			File file = HKTFile.getFile(strNameForder, "PhieuThanhToanK57.jasper");
			if (file.exists()) {
				str = file.getAbsolutePath();
				flagExtension = true;
			} else {
				str = "template/PhieuThanhToanK57.jasper";
				flagExtension = false;
			}
		}
		return str;
	}

	private javax.swing.JTextField txtBanAn;
	private javax.swing.JTextField txtConphaithanhtoan;
	private javax.swing.JTextField txtDathanhtoan;
	private javax.swing.JTextField txtDiaChi;
	private javax.swing.JTextField txtFax;
	private javax.swing.JTextField txtGiamgia;
	private javax.swing.JTextField txtGioIn;
	private javax.swing.JTextField txtGiovao;
	private javax.swing.JTextField txtKhuvuc;
	private javax.swing.JLabel txtMessenger;
	private javax.swing.JTextField txtNhanvien;
	private javax.swing.JTextField txtPTChietkhau;
	private javax.swing.JTextField txtPTPhiDV;
	private javax.swing.JTextField txtPTThue;
	private javax.swing.JTextField txtPhiDV;
	private javax.swing.JTextField txtQuyDoi;
	private javax.swing.JTextField txtSophieu;
	private javax.swing.JTextField txtTel;
	private javax.swing.JTextField txtTenDoanhNghiep;
	private javax.swing.JTextField txtTenKhachHang;
	private javax.swing.JTextField txtThue;
	private javax.swing.JTextField txtTongcong;
	private javax.swing.JTextField txtTongtien;
	private javax.swing.JTextField txtTyGia;
	private javax.swing.JTextField txtUnitMoney;
	private MyJDateChooser dcNgayIn;
	private MyJDateChooser dcNgayLap;
	private boolean isTv;
	private String prLoaiHinh = "";

	public void setParameter(String... strings) {
		try {
			txtTenDoanhNghiep.setText(strings[0]);
			txtDiaChi.setText(strings[1]);
			txtTel.setText(strings[2]);
			txtFax.setText(strings[3]);
			//
			dcNgayLap.setDate(df.parse(strings[4]));
			txtSophieu.setText(strings[5]);
			txtKhuvuc.setText(strings[6]);
			txtBanAn.setText(strings[7]);
			txtGiovao.setText(strings[8]);
			txtNhanvien.setText(strings[9]);
			//
			txtTongtien.setText(strings[10]);
			txtPTChietkhau.setText(strings[11]);
			txtGiamgia.setText(strings[12]);
			txtPTThue.setText(strings[13]);
			txtThue.setText(strings[14]);
			txtTongcong.setText(strings[15]);
			txtDathanhtoan.setText(strings[16]);
			txtConphaithanhtoan.setText(strings[17]);
			txtMessenger.setText(strings[18]);
			dcNgayIn.setDate(df.parse(strings[19]));
			txtGioIn.setText(strings[20]);
			txtUnitMoney.setText(strings[21]);
			txtPhiDV.setText(strings[22]);
			txtTyGia.setText(strings[23]);
			txtQuyDoi.setText(strings[24]);
			txtPTPhiDV.setText(strings[25]);
			prTextMoney = "(" + strings[26] + " đồng chẵn)";
			prSoDiem = strings[27];
			prSoTien = strings[28];
			txtTenKhachHang.setText(strings[29]);
			prSoKhach = strings[30];
			prNVOrder = strings[31];
			prTienTraTruocConLai = strings[32];
			prLienHoaDon = strings[33];
			prSoLanInHoaDon = strings[34];
			prTongSoDiem = strings[35];
			prSoDiemConLai = strings[36];
			prTongSoDiemTien = strings[37];
			prDiemTienConLai = strings[38];
			prTienTraTruoc = strings[39];
			prTienTraTruocDungTrongHD = strings[40];
			prNgayHenConPhaiThanhToan = strings[41];
			prSoTienKhachDua = strings[42];
			prSoTienTraKhach = strings[43];
			prDiaChiKH = strings[44];
			prSDT = strings[45];
			prLoaiHinh = strings[46];
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public boolean printReceipt(Table table, TableModelInvoiceItem tableModel, DefaultTableModel tableModelPromotion,
	    String[] str, boolean view, String name) {
		try {
			String[] headerReport = new String[] { "Tên SP", "a", "a", "Số lượng", "a", "a", "a" };
			String path = "";
			try {
				path = tableModel.getInvoiceDetail().getContributorByRole(AccountModelManager.Department).get(0)
				    .getIdentifierId();
			} catch (Exception e) {
				path = "";
			}
			List<PrintMachine> printMachines = PrintMachineModelManager.getInstance().getPrintMachinesOfTable(
			    table.getCode(), table.getLocationCode(), path);
			List<InvoiceItem> invoiceItems = AccountingModelManager.getInstance().getInvoiceItemPrint(
			    tableModel.getInvoiceDetail());
			if (profile.get("ForRent") != null) {
				invoiceItems = tableModel.getInvoiceDetail().getItems();
			}
			DefaultTableModel model = new DefaultTableModel(null, headerReport);
			for (InvoiceItem o : invoiceItems) {
				Product product = ProductModelManager.getInstance().getProductByCode(o.getProductCode());
				String unitProduct = "";
				try {
					unitProduct = LocaleModelManager.getInstance().getProductUnitByCode(product.getUnit()).toString();
				} catch (Exception e) {
				}
				if (product != null) {
					String name1 = product.getName();
					if (o.getStatus() != null && o.getStatus().equals(AccountingModelManager.isForRent)) {
						name1 = "(Cho thuê) " + name1;
					}
					String nameOther = product.getNameOther();
					if (table.getDescription() != null && table.getDescription().equals("Market")) {
						nameOther = o.getProductCode();
					}
					if (!AccountingModelManager.isVN) {
						name1 = product.getNameOther();
						if (name1 == null) {
							name1 = product.getName();
						}
					}
					Object[] row = { name1, MyDouble.valueOf(o.getDiscountRate()).toString(), unitProduct,
					    MyDouble.valueOf(o.getQuantity()).toString(), MyDouble.valueOf(o.getUnitPrice()).toString(),
					    MyDouble.valueOf(o.getTotal() - o.getDiscount()).toString(), nameOther };
					model.addRow(row);
				}

			}
			boolean ok = false;
			for (PrintMachine printMachine : printMachines) {
				PrintService printService = PrintMachineModelManager.getInstance().getPrintService(printMachine);
				setTableModel(model);
				setLangueges(true);
				setPaperSize(printMachine.getTemplate());
				setPrintService(printService);
				setParameter(str);
				setLogoEnterprise(null);
				try {
					printReportPayment(view, null);
					ok = true;
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			if (name != null) {
				setTableModel(model);
				setLangueges(true);
				setPaperSize("A4");
				setPrintService(printService);
				setParameter(str);
				setLogoEnterprise(null);
				try {
					printReportPayment(view, name);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			if (ok) {
				DialogNotice.getInstace().setVisible(true);
			}
			AccountingModelManager.pay = "Tiền mặt";
			return ok;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean printReceiptSlip(Table table, TableModelInvoiceItem tableModel, List<InvoiceItem> invoiceItems1,
	    String[] str, boolean view) throws Exception {
		String[] headerReport = new String[] { "Tên SP", "a", "a", "Số lượng", "a", "a", "a" };
		String path = "";
		try {
			path = tableModel.getInvoiceDetail().getContributorByRole(AccountModelManager.Department).get(0)
			    .getIdentifierId();
		} catch (Exception e) {
			path = "";
		}
		List<PrintMachine> printMachines = PrintMachineModelManager.getInstance().getPrintMachinesOfTable(table.getCode(),
		    table.getLocationCode(), path);
		List<InvoiceItem> invoiceItems = invoiceItems1;
		DefaultTableModel model = new DefaultTableModel(null, headerReport);
		for (InvoiceItem o : invoiceItems) {
			Product product = ProductModelManager.getInstance().getProductByCode(o.getProductCode());
			String unitProduct = "";
			try {
				unitProduct = LocaleModelManager.getInstance().getProductUnitByCode(product.getUnit()).toString();
			} catch (Exception e) {
			}
			if (product != null) {
				String nameOther = product.getNameOther();
				if (table.getDescription() != null && table.getDescription().equals("Market")) {
					nameOther = o.getProductCode();
				}
				String name1 = product.getName();
				if (!AccountingModelManager.isVN) {
					name1 = product.getNameOther();
					if (name1 == null) {
						name1 = product.getName();
					}
				}
				Object[] row = { name1, MyDouble.valueOf(o.getDiscountRate()).toString(), unitProduct,
				    MyDouble.valueOf(o.getQuantity()).toString(), MyDouble.valueOf(o.getUnitPrice()).toString(),
				    MyDouble.valueOf(o.getTotal() - o.getDiscount()).toString(), nameOther };
				model.addRow(row);
			}

		}
		boolean ok = false;
		for (PrintMachine printMachine : printMachines) {
			PrintService printService = PrintMachineModelManager.getInstance().getPrintService(printMachine);
			setTableModel(model);
			setLangueges(true);
			setPaperSize(printMachine.getTemplate());
			setPrintService(printService);
			setParameter(str);
			setLogoEnterprise(null);
			printReportPayment(view, null);
			ok = true;
		}

		if (ok) {
			DialogNotice.getInstace().setVisible(true);
		}
		return ok;
	}

	public boolean printOder(String location, String path, InvoiceDetail invoiceDetail, String[] str, boolean view)
	    throws Exception {
		String[] headerReport = new String[] { "Tên SP", "a", "a", "Số lượng", "a", "a", "a" };

		List<PrintMachine> printMachines = PrintMachineModelManager.getInstance().getPrintMachinesOfTable("", location,
		    path);
		List<InvoiceItem> invoiceItems = AccountingModelManager.getInstance().getInvoiceItemPrint(invoiceDetail);
		DefaultTableModel model = new DefaultTableModel(null, headerReport);
		for (InvoiceItem o : invoiceItems) {
			Product product = ProductModelManager.getInstance().getProductByCode(o.getProductCode());
			String unitProduct = "";
			try {
				unitProduct = LocaleModelManager.getInstance().getProductUnitByCode(product.getUnit()).toString();
			} catch (Exception e) {
			}
			Object[] row = { product.getName(), MyDouble.valueOf(o.getDiscountRate()).toString(), unitProduct,
			    MyDouble.valueOf(o.getQuantity()).toString(), MyDouble.valueOf(o.getUnitPrice()).toString(),
			    MyDouble.valueOf(o.getTotal() - o.getDiscount()).toString(), product.getNameOther() };
			model.addRow(row);
		}
		boolean ok = false;

		for (PrintMachine printMachine : printMachines) {
			PrintService printService = PrintMachineModelManager.getInstance().getPrintService(printMachine);
			setTableModel(model);
			setLangueges(true);
			setPaperSize(printMachine.getTemplate());
			setPrintService(printService);
			setParameter(str);
			setLogoEnterprise(null);
			printReportPayment(view, null);
			ok = true;
			DialogNotice.getInstace().setVisible(true);
		}
		return ok;
	}

	public void printCheBien(DefaultTableModel tableModel, String[] str) throws Exception {
		String[] headerReport = new String[] { "Tên món", "Số lượng", " Ghi chú" };
		HashMap<PrintMachine, DefaultTableModel> hashMap = new HashMap<PrintMachine, DefaultTableModel>();
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			InvoiceItem o = (InvoiceItem) tableModel.getValueAt(i, 1);
			if (o.getStatus().equals(AccountingModelManager.isRecord)) {
				List<PrintMachine> printMachines = PrintMachineModelManager.getInstance().getPrintMachinesOfProduct(
				    o.getProductCode());
				for (PrintMachine printMachine : printMachines) {
					DefaultTableModel model = hashMap.get(printMachine);
					if (model == null) {
						model = new DefaultTableModel(null, headerReport);
					}
					if (o.getDescription() == null) {
						o.setDescription("");
					}
					Object[] row = { o.getItemName(), MyDouble.valueOf(o.getQuantity()).toString(), o.getDescription() };
					model.addRow(row);
					hashMap.put(printMachine, model);
				}
				o.setStatus(AccountingModelManager.isKitchen);
			}
		}

		for (PrintMachine printMachine : hashMap.keySet()) {
			PrintService printService = PrintMachineModelManager.getInstance().getPrintService(printMachine);
			setTableModel(hashMap.get(printMachine));
			setLangueges(true);
			setPaperSize(printMachine.getTemplate());
			setPrintService(printService);
			setValuesKitchen(str);
			setLogoEnterprise(null);
			printReportKitchen(true);
		}
	}

	public void printHuyMon(DefaultTableModel tableModel, String[] str) throws Exception {
		String[] headerReport = new String[] { "Tên món", "Số lượng", " Ghi chú" };
		HashMap<PrintMachine, DefaultTableModel> hashMap = new HashMap<PrintMachine, DefaultTableModel>();
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			InvoiceItem o = (InvoiceItem) tableModel.getValueAt(i, 1);
			if (o.getStatus().equals(AccountingModelManager.isCance)) {
				List<PrintMachine> printMachines = PrintMachineModelManager.getInstance().getPrintMachinesOfProduct(
				    o.getProductCode());
				for (PrintMachine printMachine : printMachines) {
					DefaultTableModel model = hashMap.get(printMachine);
					if (model == null) {
						model = new DefaultTableModel(null, headerReport);
					}
					Object[] row = { o.getItemName(), MyDouble.valueOf(o.getQuantity()).toString(), o.getDescription() };
					model.addRow(row);
					hashMap.put(printMachine, model);
				}
			}
		}

		for (PrintMachine printMachine : hashMap.keySet()) {
			PrintService printService = PrintMachineModelManager.getInstance().getPrintService(printMachine);
			setTableModel(hashMap.get(printMachine));
			setLangueges(true);
			setPaperSize(printMachine.getTemplate());
			setPrintService(printService);
			setValuesKitchen(str);
			printReportKitchen(true);
		}
	}

	public void setLangueges(boolean tV) {
		this.isTv = tV;
	}
}
