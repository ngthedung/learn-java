ScriptRunner.require("robotExp.js");
function CreateOpenTableArea(frameui) {
	
//	frameui.showDialogTest("Chương trình TEST Màn hình bán hàng");
//	frameui.delay();
//	var buttonMenu = frameui.button("menuRightBanHang");
//	buttonMenu.mouseClick();
//	var dlMain = frameui.dialog("dlScreenOften");
//	pnMain.buttonClickByName("ScreenOften");

	//THÊM MỚI QUẦY
	frameui.showDialogTest("Thêm mới quầy HktTest");
	frameui.delay();
	var dlMain = frameui.dialog("dlScreenOften");
	var pnMain = dlMain.panel("panelTable");
	dlMain.buttonClickByName("ScreenOften_OpenTableArea");
	var dlChild = frameui.dialog("dlScreenOften_OpenTableArea");
	var pnChild = dlChild.panel("ScreenOften_OpenTableArea");
	var rdAddLocation = pnChild.radioButton("rdAddLocation");
	rdAddLocation.mouseClick();
	pnChild.fieldSet("txtNameLocation", "Tầng HktTest");
	dlChild.buttonClick("Đồng ý");
	frameui.delayOK();
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
	frameui.delayOK();
	dlChild.buttonClick("Thoát");
	
	//CHỌN BÀN HKT1 ĐỂ THÊM MÓN
	frameui.showDialogTest("Chọn bàn để gọi món");
	frameui.delay();
	var dlMain = frameui.dialog("dlScreenOften");
	var labelEat = dlMain.label("HktTest1"); 
	labelEat.mouseClick();
	
	// THÊM MỚI 3 SP
	frameui.showDialogTest("Chọn 3 SP");
	frameui.delay();
	dlMain.buttonClickByName("btnThemSP");
	var dlSP = frameui.dialog("dlThemHangHoaMoi");
	var pnSP = dlSP.panel("ThemHangHoaMoi");
	dlSP.buttonClick("Thoát");
	pnSP.deleteDataTest();
	
	//CHỌN MÓN VÀO BÀN
	frameui.showDialogTest("Chọn món vào bàn");
	frameui.delay();
	var labelHH = dlMain.label("lbHH"); 
	labelHH.mouseClick();
	var tbProduct = dlMain.table("tbProduct");
	tbProduct.doubleClickRow("Sản phẩm HktTest1");
	tbProduct.doubleClickRow("Sản phẩm HktTest2");
	tbProduct.doubleClickRow("Sản phẩm HktTest3");
	
	///ĐỔI GIÁ TỪNG SP ĐÃ CHỌN
	frameui.showDialogTest("Đổi giá từng món");
	frameui.delay();
	var tbSale = dlMain.table("tbSale");
	tbSale.selectRow("Sản phẩm HktTest1");
	dlMain.buttonClickByName("btnChangePrice");
	var dlPrice = frameui.dialog("dlchoosePrice");
	var pnPrice = dlPrice.panel("choosePrice");
	pnPrice.fieldSet("txtPriceNew", "1500000");
	dlPrice.buttonClick("Đồng ý");
	
	tbSale.selectRow("Sản phẩm HktTest2");
	dlMain.buttonClickByName("btnChangePrice");
	var dlPrice = frameui.dialog("dlchoosePrice");
	var pnPrice = dlPrice.panel("choosePrice");
	pnPrice.fieldSet("txtPriceNew", "2500000");
	dlPrice.buttonClick("Đồng ý");
	
	tbSale.selectRow("Sản phẩm HktTest3");
	dlMain.buttonClickByName("btnChangePrice");
	var dlPrice = frameui.dialog("dlchoosePrice");
	var pnPrice = dlPrice.panel("choosePrice");
	pnPrice.fieldSet("txtPriceNew", "500000");
	dlPrice.buttonClick("Đồng ý");
	
	//IN HÓA ĐƠN THANH TOÁN
	frameui.showDialogTest("In hóa đơn thanh toán");
	frameui.delay();
	dlMain.buttonClickByName("btnPaymen");
	var dlPaymen = frameui.dialog("dlPayment");
	var pnPaymen = dlPaymen.panel("panelPayment");
	dlPaymen.buttonClick("Đồng ý");
	frameui.delay();
//	var dlKho = frameui.dialog("dlKho");
//	var pnKho = dlKho.panel("Kho");
//	var chbKho = pnKho.checkBox("chbdelete");
//	chbKho.mouseClick();
//	dlKho.buttonClick("Đồng ý");
//	var dlKho = frameui.dialog("dlKho");
//	dlKho.buttonClick("Đồng ý");
//	var dlKho = frameui.dialog("dlKho");
//	dlKho.buttonClick("Đồng ý");
	
	var dlMain = frameui.dialog("dlScreenOften");
//	dlMain.deleteDataTest();
	dlMain.buttonClick("Thoát");
}
var robot = new Robot();
robot.add("TEST OpenTableArea ", "", CreateOpenTableArea);
console.clear();
robot.run();
robot.report();