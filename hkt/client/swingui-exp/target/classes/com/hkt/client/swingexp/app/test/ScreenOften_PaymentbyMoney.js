ScriptRunner.require("robotExp.js");
function CreatePaymenbyMoney(frameui) {

	var dlMain = frameui.dialog("dlScreenOften");

	var pnMain = dlMain.panel("ScreenOften");

	// THÊM MỚI QUẦY
	frameui.showDialogTest("Thêm mới quầy HktTest");
	frameui.delay();
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
	pnSP.deleteDataTest();
	
	//CHỌN MÓN VÀO BÀN
	frameui.showDialogTest("Chọn món vào bàn");
	frameui.delay();
	var labelHH = pnMain.label("lbHH"); 
	labelHH.mouseClick();
	var tbProduct = pnMain.table("tbProduct");
	tbProduct.doubleClickRow("Sản phẩm HktTest1");
	tbProduct.doubleClickRow("Sản phẩm HktTest2");
	tbProduct.doubleClickRow("Sản phẩm HktTest3");
	
	///ĐỔI GIÁ TỪNG SP ĐÃ CHỌN
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

	// THANH TOÁN LẺ TRƯỚC 200000VND
	frameui.showDialogTest("Thanh toán lẻ 1 món");
	frameui.delay();
	pnMain.buttonClickByName("btnPayOnce");
	var dlChild = frameui.dialog("dialogPrintReceipt");
	dlChild.buttonClickByName("btnBangTien");

	var dlPaymentSP = frameui.dialog("dlScreenOften_PaymenbyMoney");
	var pnPaymentSP = dlPaymentSP.panel("ScreenOften_PaymentbyMoney");
	pnPaymentSP.fieldSet("txtBeforPayment", "200000");
	dlPaymentSP.buttonClick("Đồng ý");
	
	//TRẢ SAU NHỮNG MÓN CÒN LẠI
	frameui.showDialogTest("Thanh toán bằng cách trả sau");
	frameui.delay();
	pnMain.buttonClickByName("btnTraSau");
	dlMain.buttonClick("Thoát");
	
	//XEM DS CÔNG NỢ SP TRẢ SAU Ở BÀN HKTTEST1
	frameui.showDialogTest("Xem ds công nợ sp trả sau");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightBanHang");
	buttonMenu.mouseClick();
	var button = frameui.button("btnSearchInvoice");
	button.mouseClick();
	var dlInvoice = frameui.dialog("dlPanelSearchInvoice");
	var pnInvoice= dlInvoice.panel("PanelSearchInvoice");
	dlInvoice.buttonClick("Đồng ý");
	
	//CLICK XEM CHI TIẾT TỪNG KHOẢN NỢ
	var dlInvoice1 = frameui.dialog("dialog");
	var tbInvoice = dlInvoice1.table("tbInvoice");
	tbInvoice.doubleClickRow("HktTest1");
	
	//THANH TOÁN HẾT CÔNG NỢ
	frameui.showDialogTest("Thanh toán hết công nợ");
	frameui.delay();
	pnMain.buttonClickByName("btnPaymen");
	var dlPaymen = frameui.dialog("dlPayment");
	var pnPaymen = dlPaymen.panel("panelPayment");
	dlPaymen.buttonClick("Đồng ý");
	var dlKho = frameui.dialog("dlKho");
	var pnKho = dlKho.panel("Kho");
//	var chbKho = pnKho.checkBox("chbdelete");
////	chbKho.mouseClick();
	dlKho.buttonClick("Đồng ý");
	var dlKho = frameui.dialog("dlKho");
	dlKho.buttonClick("Đồng ý");
	var dlKho = frameui.dialog("dlKho");
	dlKho.buttonClick("Đồng ý");
	
	var dlMain = frameui.dialog("dlScreenOften");
	var pnMain = dlMain.panel("ScreenOften");
	pnMain.deleteDataTest();
	dlMain.buttonClick("Thoát");
	dlInvoice1.buttonClick("Thoát");
	

}
var robot = new Robot();
robot.add("TEST PaymenbyMoney ", "", CreatePaymenbyMoney);
console.clear();
robot.run();
robot.report();