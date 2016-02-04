ScriptRunner.require("robotExp.js");
function CreatePaymentbyPoint(frameui) {
	
//	frameui.showDialogTest("Chương trình TEST Bán hàng - Thanh toán bằng Điểm dùng);
//	frameui.delay();
	var dlMain = frameui.dialog("dlScreenOften");
		
	var pnMain = dlMain.panel("ScreenOften");

	//THÊM MỚI QUẦY
	frameui.showDialogTest("Thêm mới quầy HktTest");
	frameui.delay();
	var dlMain = frameui.dialog("dlScreenOften");
	var pnMain = dlMain.panel("ScreenOften");
	pnMain.buttonClickByName("ScreenOften_OpenTableArea");
	var dlChild = frameui.dialog("dlScreenOften_OpenTableArea");
	var pnChild = dlChild.panel("ScreenOften_OpenTableArea");
	var rdAddLocation = pnChild.radioButton("rdAddLocation");
	rdAddLocation.mouseClick();
	pnChild.fieldSet("txtNameLocation", "Tầng HktTest");
	dlChild.buttonClick("Đồng ý");
	dlChild.buttonClick("Thoát");
	
	//THÊM MỚI BÀN VÀO QUẦY VỪA TẠO
	frameui.showDialogTest("Thêm mới 5 bàn vào quầy HktTest");
	frameui.delay();
	pnMain.buttonClickByName("ScreenOften_OpenTableArea");
	var dlChild = frameui.dialog("dlScreenOften_OpenTableArea");
	var pnChild = dlChild.panel("ScreenOften_OpenTableArea");
	var rdAddTable = pnChild.radioButton("rdAddTable");
	rdAddTable.mouseClick();
	var chbLocationOther = pnChild.checkBox("chbLocationOther");
	chbLocationOther.mouseClick();
	var cbLocationOther = pnChild.comboBox("cbLocationOther");
	cbLocationOther.selectItem("Tầng HktTest");
	pnChild.fieldSet("txtCountTable", "5");
	pnChild.fieldSet("txtLabelTable", "HktTest");
	dlChild.buttonClick("Đồng ý");
	dlChild.buttonClick("Thoát");
	
	//CHỌN BÀN HKT1 ĐỂ THÊM MÓN
	frameui.showDialogTest("Chọn bàn để gọi món");
	frameui.delay();
	var pnMain = dlMain.panel("ScreenOften");
	var labelEat = pnMain.label("HktTest1"); 
	labelEat.mouseClick();
	
	// THÊM MỚI 3 SP
	frameui.showDialogTest("Chọn 3 SP");
	frameui.delay();
	pnMain.buttonClickByName("btnThemSP");
	var dlSP = frameui.dialog("dlThemHangHoaMoi");
	var pnSP = dlSP.panel("ThemHangHoaMoi");
	dlSP.buttonClick("Thoát");
	
	// TẠO MỚI KH
	frameui.showDialogTest("Tạo mới tên khách hàng");
	frameui.delay();
	pnMain.textClick("txtPartner");
	pnMain.textClick("txtPartner");

	var dllistPartner = frameui.dialog("dlPaymentbyPoint");
	var pnlistPartner = dllistPartner.panel("PaymentbyPoint");
	dllistPartner.buttonClickByName("btnPartnerPersonal");
	
	var dlPartner = frameui.dialog("dlPartnerIsUser");
	var pnPartner = dlPartner.panel("PartnerIsUser");
	pnPartner.createDataTest();
	dlPartner.buttonClickByName("btnExit");
	
	var dllistPartner = frameui.dialog("dlPaymentbyPoint");
	var pnlistPartner = dllistPartner.panel("PaymentbyPoint");
	
	// CHỌN KH TRONG DS
	frameui.showDialogTest("Chọn khách hàng trong DS");
	frameui.delay();
	var tbPartner = pnlistPartner.table("table");
	tbPartner.doubleClickRow("Tên ĐT HktTest1");
	
	//LẬP CƠ CHẾ ĐIỂM DÙNG MỚI
	frameui.showDialogTest("Lập cơ chế điểm dùng mới");
	frameui.delay();
	dlMain.buttonClick("Thoát");
	
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac");
	buttonMenu.mouseClick();
	var buttonMenu = frameui.button("LapCoCheDiem");
	buttonMenu.mouseClick();
	
	var dlMain = frameui.dialog("dlQuanLyDiemKH");
	var pnChild = dlMain.panel("LapCoCheDiem");
	pnChild.fieldSet("txtTenTyLe","Tỷ lệ HktTest1");
	pnChild.fieldSet("txtDoanhThuKH","1000000");
	var Unit1 = frameui.dialog("dlQuanLyDiemKH");
	var comboBoxChoose = Unit1.comboBox("cbMoneyUnit1");
	comboBoxChoose.selectItem("VND");
	pnChild.fieldSet("txtDiemTuongUng","1");
	var Unit2 = frameui.dialog("dlQuanLyDiemKH");
	var comboBoxChoose = Unit2.comboBox("cbMoneyUnit2");
	comboBoxChoose.selectItem("VND");
	pnChild.fieldSet("txtTienKMTuongUng","10000");
	pnChild.fieldSet("txtDiemToiThieu","5");
	dlMain.buttonClick("Lưu");
	dlMain.buttonClick("Thoát");
	
	//THÊM 100 ĐIỂM DÙNG CHO KH
	frameui.showDialogTest("Thêm 100 điểm dùng trước cho KH");
	frameui.delay();
	var button = frameui.button("DungDiemTrucTiep");
	button.mouseClick();
	var dlMain = frameui.dialog("dlDungDiemTrucTiep");
	var pnMain = dlMain.panel("DungDiemTrucTiep");
	
	var comboBoxPartner = pnMain.comboBox("cbDoiTac");
	comboBoxPartner.selectItem("Tên ĐT HktTest1");
	pnMain.fieldSet("txtDiemDung", "100");
	var comboBoxType = pnMain.comboBox("cbLoai");
	comboBoxType.selectItem("Giảm đi");
	comboBoxType.selectItem("Tăng thêm");
	dlMain.buttonClick("Lưu");
	dlMain.buttonClick("Thoát");
	
	//QUAY LẠI MÀN HÌNH BÁN HÀNG VỚI KH ĐÃ CHỌN
	frameui.showDialogTest("Bán hàng cho KH vừa tạo mới điểm dùng");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightBanHang");
	buttonMenu.mouseClick();
	var button = frameui.button("ScreenOften");
	button.mouseClick();
	var dlMain = frameui.dialog("dlScreenOften");
	var pnMain = dlMain.panel("ScreenOften");
	
	//CHỌN MÓN VÀO BÀN
	frameui.showDialogTest("Chọn món vào bàn");
	frameui.delay();
	var labelHH = pnMain.label("lbHH"); 
	labelHH.mouseClick();
	var tbProduct = pnMain.table("tbProduct");
	tbProduct.doubleClickRow("Sản phẩm HktTest1");
	tbProduct.doubleClickRow("Sản phẩm HktTest2");
	tbProduct.doubleClickRow("Sản phẩm HktTest3");
	
	//ĐỔI GIÁ TỪNG SP ĐÃ CHỌN
	frameui.showDialogTest("Đổi giá từng món");
	frameui.delay();
	var tbSale = pnMain.table("tbSale");
	tbSale.selectRow("Sản phẩm HktTest1");
	pnMain.buttonClickByName("btnChangePrice");
	var dlPrice = frameui.dialog("dlchoosePrice");
	var pnPrice = dlPrice.panel("choosePrice");
	pnPrice.fieldSet("txtPriceNew", "1500000");
	dlPrice.buttonClick("Đồng ý");
	
	tbSale.selectRow("Sản phẩm HktTest2");
	pnMain.buttonClickByName("btnChangePrice");
	var dlPrice = frameui.dialog("dlchoosePrice");
	var pnPrice = dlPrice.panel("choosePrice");
	pnPrice.fieldSet("txtPriceNew", "2500000");
	dlPrice.buttonClick("Đồng ý");
	
	tbSale.selectRow("Sản phẩm HktTest3");
	pnMain.buttonClickByName("btnChangePrice");
	var dlPrice = frameui.dialog("dlchoosePrice");
	var pnPrice = dlPrice.panel("choosePrice");
	pnPrice.fieldSet("txtPriceNew", "500000");
	dlPrice.buttonClick("Đồng ý");
	
	
	//XEM SỐ ĐIỂM DÙNG KH ĐANG CÓ VÀ SỬ DỤNG ĐỂ TRỪ HĐ KHI THANH TOÁN
	frameui.showDialogTest("Thanh toán HĐ và trừ trên số điểm quy đổi tiền KH đang có");
	frameui.delay();
	var chbPoint = pnMain.checkBox("chbPoint");
	chbPoint.mouseClick();
	
	pnMain.buttonClickByName("btnPaymen");
	var dlPaymen = frameui.dialog("dlPayment");
	var pnPaymen = dlPaymen.panel("panelPayment");
	dlPaymen.buttonClick("Đồng ý");
	
	var dlKho = frameui.dialog("dlKho");
	var pnKho = dlKho.panel("Kho");
	var chbKho = pnKho.checkBox("chbdelete");
//	chbKho.mouseClick();
	dlKho.buttonClick("Đồng ý");
	var dlKho = frameui.dialog("dlKho");
	dlKho.buttonClick("Đồng ý");
	var dlKho = frameui.dialog("dlKho");
	dlKho.buttonClick("Đồng ý");
	
	//VÀO LẠI CÁC GD ĐỂ XÓA DL TẠO MẪU
	pnMain.buttonClickByName("btnThemSP");
	var dlSP = frameui.dialog("dlThemHangHoaMoi");
	var pnSP = dlSP.panel("ThemHangHoaMoi");
	pnSP.deleteDataTest();
	dlSP.buttonClick("Thoát");
	
	pnMain.textClick("txtPartner");
	pnMain.textClick("txtPartner");

	var dllistPartner = frameui.dialog("dlPaymentbyPoint");
	var pnlistPartner = dllistPartner.panel("PaymentbyPoint");
	dllistPartner.buttonClickByName("btnPartnerPersonal");
	
	var dlPartner = frameui.dialog("dlPartnerIsUser");
	var pnPartner = dlPartner.panel("PartnerIsUser");
	pnPartner.deleteDataTest();
	dlPartner.buttonClickByName("btnExit");
	dllistPartner.buttonClick("Thoát");
	
	frameui.showDialogTest("Chương trình TEST Bán hàng - Thanh toán HĐ bằng điểm kết thúc!");
	frameui.delay();
	pnMain.deleteDataTest();
	dlMain.buttonClick("Thoát");
	
	
}
var robot = new Robot();
robot.add("TEST PaymentbyPoint ", "", CreatePaymentbyPoint);
console.clear();
robot.run();
robot.report();