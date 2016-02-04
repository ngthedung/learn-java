ScriptRunner.require("robotExp.js");
function CreateEditInvoice(frameui) {
	
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

	//CHỌN MÓN VÀO BÀN
	frameui.showDialogTest("Chọn món vào bàn");
	frameui.delay();
	var labelHH = pnMain.label("lbHH"); 
	labelHH.mouseClick();
	var tbProduct = pnMain.table("tbProduct");
	tbProduct.doubleClickRow("Sản phẩm HktTest1");
	tbProduct.doubleClickRow("Sản phẩm HktTest2");
	
	//ĐỔI GIÁ TỪNG SP ĐÃ CHỌN
	frameui.showDialogTest("Đổi giá từng món");
	frameui.delay();
	var tbSale = pnMain.table("tbSale");
	tbSale.selectRow("Sản phẩm HktTest1");
	pnMain.buttonClickByName("btnChangePrice");
	var dlPrice = frameui.dialog("dlchoosePrice");
	var pnPrice = dlPrice.panel("choosePrice");
	pnPrice.fieldSet("txtPriceNew", "150000");
	dlPrice.buttonClick("Đồng ý");
	
	tbSale.selectRow("Sản phẩm HktTest2");
	pnMain.buttonClickByName("btnChangePrice");
	var dlPrice = frameui.dialog("dlchoosePrice");
	var pnPrice = dlPrice.panel("choosePrice");
	pnPrice.fieldSet("txtPriceNew", "250000");
	dlPrice.buttonClick("Đồng ý");
	
	//IN HÓA ĐƠN THANH TOÁN
	frameui.showDialogTest("In hóa đơn thanh toán");
	frameui.delay();
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
	dlMain.buttonClick("Thoát");
	
	//SỬA HĐ LẦN 1
	frameui.showDialogTest("Xem HĐ vừa thanh toán");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightBanHang");
	buttonMenu.mouseClick();
	var button = frameui.button("PanelSearchInvoice");
	button.mouseClick();

	var dlMain = frameui.dialog("dlPanelSearchInvoice");
	var pnChild = dlMain.panel("PanelSearchInvoice");
	dlMain.buttonClick("Đồng ý");
	
	//THÊM 1 MÓN MỚI VÀO HÓA ĐƠN
	frameui.showDialogTest("Thêm 1 món vào HĐ và thanh toán");
	frameui.delay();
	var dlInvoice = frameui.dialog("dlinvoice3");
	var tbInvoice = dlInvoice.table("tbinvoice3");
	tbInvoice.doubleClickRow("HktTest1");
	
	var labelEat = pnMain.label("HktTest1"); 
	labelEat.mouseClick();
	var labelHH = pnMain.label("lbHH"); 
	labelHH.mouseClick();
	var tbProduct = pnMain.table("tbProduct");
	tbProduct.doubleClickRow("Sản phẩm HktTest3");
	tbSale.selectRow("Sản phẩm HktTest3");
	pnMain.buttonClickByName("btnChangePrice");
	var dlPrice = frameui.dialog("dlchoosePrice");
	var pnPrice = dlPrice.panel("choosePrice");
	pnPrice.fieldSet("txtPriceNew", "500000");
	dlPrice.buttonClick("Đồng ý");
	
	pnMain.buttonClickByName("btnPaymen");
	var dlPaymen = frameui.dialog("dlPayment");
	var pnPaymen = dlPaymen.panel("panelPayment");
	dlPaymen.buttonClick("Đồng ý");
	var dlKho = frameui.dialog("dlKho");
	var pnKho = dlKho.panel("Kho");
	var chbKho = pnKho.checkBox("chbdelete");
//	chbKho.mouseClick();
	dlKho.buttonClick("Đồng ý");
	
	var dlMain = frameui.dialog("dlScreenOften");
	var pnMain = dlMain.panel("ScreenOften");
	dlMain.buttonClick("Thoát");
	var dlInvoice = frameui.dialog("dlinvoice3");
	dlInvoice.buttonClick("Thoát");
	
	//SỬA HĐ LẦN 2
	var buttonMenu = frameui.button("menuRightBanHang");
	buttonMenu.mouseClick();

	var button = frameui.button("PanelSearchInvoice");
	button.mouseClick();

	var dlMain = frameui.dialog("dlPanelSearchInvoice");
	var pnChild = dlMain.panel("PanelSearchInvoice");
	dlMain.buttonClick("Đồng ý");
	
	var dlInvoice = frameui.dialog("dlinvoice3");
	var tbInvoice = dlInvoice.table("tbinvoice3");
	tbInvoice.doubleClickRow("HktTest1");
	
	//XÓA 2 MÓN KHỎI HĐ
	frameui.showDialogTest("Xóa 2 món trên HĐ và thanh toán");
	frameui.delay();
	tbSale.selectRow("Sản phẩm HktTest1");
	pnMain.buttonClickByName("btnDelete");
	var dlXoa = frameui.dialog("dlXoa");
	dlXoa.buttonClick("Đồng ý");
	
	tbSale.selectRow("Sản phẩm HktTest2");
	pnMain.buttonClickByName("btnDelete");
	var dlXoa = frameui.dialog("dlXoa");
	dlXoa.buttonClick("Đồng ý");
	
	pnMain.buttonClickByName("btnPaymen");
	var dlPaymen = frameui.dialog("dlPayment");
	var pnPaymen = dlPaymen.panel("panelPayment");
	dlPaymen.buttonClick("Đồng ý");
	
	//VÀO LẠI CÁC GD ĐỂ XÓA DL TẠO MẪU
	pnMain.buttonClickByName("btnThemSP");
	var dlSP = frameui.dialog("dlThemHangHoaMoi");
	var pnSP = dlSP.panel("ThemHangHoaMoi");
	pnSP.deleteDataTest();
	dlSP.buttonClick("Thoát");
	
	frameui.showDialogTest("Chương trình TEST Bán hàng - Sửa HĐ bán hàng kết thúc!");
	frameui.delay();
	var dlMain = frameui.dialog("dlScreenOften");
	var pnMain = dlMain.panel("ScreenOften");
	pnMain.deleteDataTest();
	dlMain.buttonClick("Thoát");
	dlInvoice.buttonClick("Thoát");
	
}
var robot = new Robot();
robot.add("TEST EditInvoice ", "", CreateEditInvoice);
console.clear();
robot.run();
robot.report();