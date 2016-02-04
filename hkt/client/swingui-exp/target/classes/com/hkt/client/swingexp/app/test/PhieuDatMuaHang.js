ScriptRunner.require("robotExp.js");

function CreatePhieuDatMuaHang(frameui) {
	var buttonMenu = frameui.button("menuRightThuChiMuaHang");
	buttonMenu.mouseClick();
	var button = frameui.button("PhieuDatMuaHang");
	button.mouseClick();

	var dlMain = frameui.dialog("dlPhieuDatMuaHang");
	var pnMain = dlMain.panel("PhieuDatMuaHang");
	/*
	 * KỊCH BẢN TEST ::: PHIẾU ĐẶT MUA HÀNG VÀ DANH SÁCH
	 */
	frameui.showDialogTest("KỊCH BẢN TEST PHIẾU ĐẶT MUA HÀNG");
	frameui.delay();
	//Tạo invoice01 với 4SP HktTest1 và 5SP HktTest2 trên phiếu đặt mua hàng
	frameui.showDialogTest("Tạo invoice01 với 4SP HktTest1 và 5SP HktTest2");
	frameui.delay();
	//Chọn nhóm sản phẩm
	var checkBoxNhomSP1 = pnMain.checkBox("chbNhomSanPham0");
	checkBoxNhomSP1.mouseClick();
	checkBoxNhomSP1.mouseClick();
	var checkBoxNhomSP2 = pnMain.checkBox("chbNhomSanPham3");
	checkBoxNhomSP2.mouseClick();
	checkBoxNhomSP2.mouseClick();
	var checkBoxNhomSP3 = pnMain.checkBox("chbNhomSanPham6");
	checkBoxNhomSP3.mouseClick();
	checkBoxNhomSP3.mouseClick();
	//Chọn nhóm hàng 
	var comboBoxNhomSP = pnMain.comboBox("cbNhomSanPham");
	comboBoxNhomSP.selectItem("Nhóm SP HktTest11");
	//Chọn bảng giá và tiền tệ cho SP1
	var comboBoxBangGia = pnMain.comboBox("cbBangGia");
	comboBoxBangGia.selectItem("BG HktTest1");
	var comboBoxDonViTien = pnMain.comboBox("cbDonViTien");
	comboBoxDonViTien.selectItem("TT HktTest1");
	pnMain.fieldSet("txtNghiepVu", "Phiếu đặt mua hàng HktTest1 invoice01");
	//Nhấn nút + mở rộng các nhóm hàng
	var labelNut = pnMain.label("lblNut0"); 
	labelNut.mouseClick();
	
	var tableDanhSachSanPham = pnMain.table("tbDanhSachSanPham");
	//Chọn 4SP HktTest1 trong bảng danh sách
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest1");
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest1");
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest1");
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest1");
	//Chọn bảng giá và tiền tệ cho SP2
	comboBoxBangGia.selectItem("BG HktTest2");
	comboBoxDonViTien.selectItem("TT HktTest2");
	//Chọn 5SP HktTest2
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest2");
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest2");
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest2");
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest2");
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest2");
	
	frameui.showDialogTest("Lưu phiếu đặt hàng và thoát");
	var buttonLuu = pnMain.button("btnLuu");
	buttonLuu.mouseClick();

	//Ấn nút thoát
	var buttonThoat = pnMain.button("btnThoat");
	buttonThoat.mouseClick();
	//
	//Kết thúc giao diện lưu phiếu đặt mua hàng
	//
	
	/*
	 * VÀO DANH SÁCH
	 */
	
	//Xem lại phiếu đặt hàng và xóa 1 SP
	frameui.showDialogTest("Xem lại phiếu đặt hàng đã lưu và xóa 1 SP");
	var buttonMenu = frameui.button("menuRightThuChiMuaHang");
	buttonMenu.mouseClick();
	var button = frameui.button("QuanLyDonDatHang");
	button.mouseClick();
	var dlLoc = frameui.dialog("dlLocQuanLyDonDatHang");
	var pnLoc = dlLoc.panel("QuanLyDonDatHang");
	dlLoc.buttonClick("Đồng ý");
	//Chọn dòng STT = 1
	frameui.showDialogTest("Xem lại phiếu đặt hàng vừa tạo");
	var dlMain = frameui.dialog("dlQuanLyDonDatHang");
	var table = dlMain.table("tbQuanLyDonDatHang");
	table.doubleClickRow("Phiếu đặt mua hàng HktTest1 invoice01");
	//Click vào 1 sản phẩm item trong bảng hàng hóa
	var dlChild = frameui.dialog("dlPhieuDatMuaHang");
	var pnChild = dlChild.panel("pnPhieuDatMuaHang");
	var tableSale = pnChild.table("tbSale");
	tableSale.doubleClickRow("Mã SP HktTest1");
	var dlSuaGia = frameui.dialog("dlChinhSuaGia");
	var pnSuaGia = dlSuaGia.panel("pnChinhSuaGia");
	dlSuaGia.buttonClick("Thoát");
	//Xóa 1 SP 
	var buttonXoa = pnChild.button("btnXoa");
	buttonXoa.mouseClick();
	
	
	//Chọn bảng giá cho sản phẩm HktTest3
	var comboBoxNhomSP = pnChild.comboBox("cbNhomSanPham");
	comboBoxNhomSP.selectItem("Nhóm SP HktTest11");
	var comboBoxBangGia = pnChild.comboBox("cbBangGia");
	comboBoxBangGia.selectItem("BG HktTest3");
	var comboBoxDonViTien = pnChild.comboBox("cbDonViTien");
	comboBoxDonViTien.selectItem("TT HktTest3");
	//Click dấu + mở rộng danh sách trong nhóm hàng
	var labelNut = pnChild.label("lblNut0"); 
	labelNut.mouseClick();
	
	var tableDanhSachSanPham = pnChild.table("tbDanhSachSanPham");
	//Chọn 3SP HktTest3
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest3");
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest3");
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest3");
	
	//Ấn nút nhập kho
	var buttonNhapKho = pnChild.button("btnNhapKho");
	buttonNhapKho.mouseClick();
	
	//Ấn nút thoát
	var buttonThoat = pnChild.button("btnThoat");
	buttonThoat.mouseClick();
	//
	//Kết thúc giao diện xem lại và sửa. Nhập kho thành công
	//
	
	/*
	 * GIAO DIỆN PHIẾU KHÁCH ĐẶT HÀNG
	 */
	//Khách đặt hàng 1 SP3
	frameui.showDialogTest("Vào giao diện phiếu khách đặt hàng");
	frameui.delay();
	frameui.showDialogTest("Khách đặt hàng 1 SP3 HktTest3");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightThuChiMuaHang");
	buttonMenu.mouseClick();
	var button = frameui.button("PhieuKhachDatHang");
	button.mouseClick();

	var dlMain = frameui.dialog("dlPhieuKhachDatHang");
	var pnMain = dlMain.panel("PhieuKhachDatHang");
	
	frameui.showDialogTest("Chọn sản phẩm khách hàng đặt HktTest3");
	frameui.delay();
	//Chọn nhóm sản phẩm
	var checkBoxNhomSP1 = pnMain.checkBox("chbNhomSanPham0");
	checkBoxNhomSP1.mouseClick();
	checkBoxNhomSP1.mouseClick();
	var checkBoxNhomSP2 = pnMain.checkBox("chbNhomSanPham3");
	checkBoxNhomSP2.mouseClick();
	checkBoxNhomSP2.mouseClick();
	var checkBoxNhomSP3 = pnMain.checkBox("chbNhomSanPham6");
	checkBoxNhomSP3.mouseClick();
	checkBoxNhomSP3.mouseClick();
	//Chọn nhóm hàng 
	var comboBoxNhomSP = pnMain.comboBox("cbNhomSanPham");
	comboBoxNhomSP.selectItem("Nhóm SP HktTest22");
	//Chọn bảng giá và tiền tệ cho SP1
	var comboBoxBangGia = pnMain.comboBox("cbBangGia");
	comboBoxBangGia.selectItem("BG HktTest2");
	var comboBoxDonViTien = pnMain.comboBox("cbDonViTien");
	comboBoxDonViTien.selectItem("TT HktTest2");
	pnMain.fieldSet("txtNghiepVu", "Phiếu khách đặt hàng HktTest1 invoice01");
	//Nhấn nút + mở rộng các nhóm hàng
	var labelNut = pnMain.label("lblNut0"); 
	labelNut.mouseClick();
	
	var tableDanhSachSanPham = pnMain.table("tbDanhSachSanPham");
	//Chọn 1SP HktTest2 trong bảng danh sách
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest2");
	
	comboBoxBangGia.selectItem("BG HktTest1");
	comboBoxDonViTien.selectItem("TT HktTest1");
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest1");
	var dlMessager = frameui.dialog("dlMessager");
	dlMessager.buttonClick("Đồng ý");
	frameui.showDialogTest("Hàng hóa HktTest1 đã hết hàng");
	frameui.delay();
	
	//Ấn nút thanh toán
	var buttonThanhToan = pnMain.button("btnThanhToan");
	buttonThanhToan.mouseClick();
	//Ấn nút thoát
	var buttonThoat = pnMain.button("btnThoat");
	buttonThoat.mouseClick();
	
	//Tạo invoice02 với 4SP HktTest1 và 5SP HktTest2 trên phiếu đặt mua hàng
	var buttonMenu = frameui.button("menuRightThuChiMuaHang");
	buttonMenu.mouseClick();
	var button = frameui.button("PhieuDatMuaHang");
	button.mouseClick();

	var dlMain = frameui.dialog("dlPhieuDatMuaHang");
	var pnMain = dlMain.panel("PhieuDatMuaHang");

	frameui.showDialogTest("KỊCH BẢN TEST PHIẾU ĐẶT MUA HÀNG");
	frameui.delay();

	frameui.showDialogTest("Tạo invoice01 với 4SP HktTest1 và 5SP HktTest2");
	frameui.delay();
	//Chọn nhóm sản phẩm
	var checkBoxNhomSP1 = pnMain.checkBox("chbNhomSanPham0");
	checkBoxNhomSP1.mouseClick();
	checkBoxNhomSP1.mouseClick();
	var checkBoxNhomSP2 = pnMain.checkBox("chbNhomSanPham3");
	checkBoxNhomSP2.mouseClick();
	checkBoxNhomSP2.mouseClick();
	var checkBoxNhomSP3 = pnMain.checkBox("chbNhomSanPham6");
	checkBoxNhomSP3.mouseClick();
	checkBoxNhomSP3.mouseClick();
	//Chọn nhóm hàng 
	var comboBoxNhomSP = pnMain.comboBox("cbNhomSanPham");
	comboBoxNhomSP.selectItem("Nhóm SP HktTest11");
	//Chọn bảng giá và tiền tệ cho SP1
	var comboBoxBangGia = pnMain.comboBox("cbBangGia");
	comboBoxBangGia.selectItem("BG HktTest1");
	var comboBoxDonViTien = pnMain.comboBox("cbDonViTien");
	comboBoxDonViTien.selectItem("TT HktTest1");
	pnMain.fieldSet("txtNghiepVu", "Phiếu đặt mua hàng HktTest2 invoice02");
	//Nhấn nút + mở rộng các nhóm hàng
	var labelNut = pnMain.label("lblNut0"); 
	labelNut.mouseClick();
	
	var tableDanhSachSanPham = pnMain.table("tbDanhSachSanPham");
	//Chọn 4SP HktTest1 trong bảng danh sách
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest1");
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest1");
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest1");
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest1");
	//Chọn bảng giá và tiền tệ cho SP2
	comboBoxBangGia.selectItem("BG HktTest2");
	comboBoxDonViTien.selectItem("TT HktTest2");
	//Chọn 5SP HktTest2
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest2");
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest2");
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest2");
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest2");
	tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest2");
	
	var tableSale = pnMain.table("tbSale");
	tableSale.doubleClickRow("Mã SP HktTest1");
	
	var dlChinhSuaGia = frameui.dialog("dlChinhSuaGia");
	var pnChinhSuaGia = dlChinhSuaGia.panel("pnChinhSuaGia");
	pnChinhSuaGia.fieldSet("txtPrice", "333330");
	
	
	dlChinhSuaGia.buttonClick("Đồng ý");
	
	frameui.showDialogTest("Lưu phiếu đặt hàng và thoát");
	var buttonLuu = pnMain.button("btnLuu");
	buttonLuu.mouseClick();
	pnMain.deleteDataTest();

	//Ấn nút thoát
	var buttonThoat = pnMain.button("btnThoat");
	buttonThoat.mouseClick();
	
	frameui.showDialogTest("Chương trình TEST giao diện Phiếu đặt mua hàng kết thúc");
	frameui.delay();
}

var robot = new Robot();
robot.add("TEST PhieuDatMuaHang", "", CreatePhieuDatMuaHang);
console.clear();
robot.run();
robot.report();