ScriptRunner.require("robotExp.js");
function CreateCreditTransaction(frameui) {

	var dlMain = frameui.dialog("dlScreenOften");

	var pnMain = dlMain.panel("ScreenOften");

	// THÊM MỚI QUẦY
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

	// THÊM MỚI BÀN VÀO QUẦY VỪA TẠO
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

	// CHỌN BÀN HKT1 ĐỂ THÊM MÓN
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

	var dlPartner = frameui.dialog("dlPaymentbyPoint");
	var pnPartner = dlPartner.panel("PaymentbyPoint");

	// CHỌN KH TRONG DS
	frameui.showDialogTest("Chọn khách hàng trong DS");
	frameui.delay();
	var tbPartner = pnPartner.table("table");
	tbPartner.doubleClickRow("Tên ĐT HktTest1");

	//TẠO SỐ TIỀN TRẢ TRƯỚC 3000000
	frameui.showDialogTest("Tạo số tiền trả trước");
	frameui.delay();
	dlMain.buttonClick("Thoát");

	var buttonMenu = frameui.button("menuRightKhachHangDoiTac");
	buttonMenu.mouseClick();
	var button = frameui.button("SuDungTraTruoc");
	button.mouseClick();
	var dlMain = frameui.dialog("dlSuDungTraTruoc");
	var pnMain = dlMain.panel("SuDungTraTruoc");
	
	var comboBoxPartner = pnMain.comboBox("cbDoiTac");
	comboBoxPartner.selectItem("Tên ĐT HktTest1");
	pnMain.fieldSet("txtTienSD", "3000000");
	var comboBoxType = pnMain.comboBox("cbLoai");
	comboBoxType.selectItem("Giảm đi");
	comboBoxType.selectItem("Cộng thêm");
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
	
	//XEM SỐ TIỀN TRẢ TRƯỚC CỦA KH VÀ TRỪ TRÊN HĐ
	frameui.showDialogTest("Thanh toán và trừ trên số tiền trả trước của KH");
	frameui.delay();
	var chbCredit = pnMain.checkBox("chbCredit");
	chbCredit.mouseClick();
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
	
	var dlMain = frameui.dialog("dlScreenOften");
	var pnMain = dlMain.panel("ScreenOften");
	pnMain.deleteDataTest();
	dlMain.buttonClick("Thoát");
	
}
var robot = new Robot();
robot.add("TEST CreditTransaction ", "", CreateCreditTransaction);
console.clear();
robot.run();
robot.report();