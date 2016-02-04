ScriptRunner.require("robotExp.js");
function CreateSetGrossTable(frameui) {
	
	var dlMain = frameui.dialog("dlScreenOften");
	
	var pnMain = dlMain.panel("ScreenOften");
	
	//THÊM MỚI QUẦY
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
	
	//ĐẶT TRƯỚC 3 BÀN HKTTEST1,2,3
	frameui.showDialogTest("Đặt trước 3 bàn");
	frameui.delay();
	var pnMain = dlMain.panel("ScreenOften");
	var labelEat1 = pnMain.label("HktTest1"); 
	labelEat1.mouseClick();
	pnMain.buttonClickByName("ScreenOften_SetGrossTable");
	var dlSet = frameui.dialog("dlScreenOften_SetGrossTable");
	var pnSet = dlSet.panel("ScreenOften_SetGrossTable");
	dlSet.buttonClick("Đồng ý");
	
	var labelEat2 = pnMain.label("HktTest2"); 
	labelEat2.mouseClick();
	pnMain.buttonClickByName("ScreenOften_SetGrossTable");
	var dlSet = frameui.dialog("dlScreenOften_SetGrossTable");
	var pnSet = dlSet.panel("ScreenOften_SetGrossTable");
	dlSet.buttonClick("Đồng ý");
	
	var labelEat3 = pnMain.label("HktTest3"); 
	labelEat3.mouseClick();
	pnMain.buttonClickByName("ScreenOften_SetGrossTable");
	var dlSet = frameui.dialog("dlScreenOften_SetGrossTable");
	var pnSet = dlSet.panel("ScreenOften_SetGrossTable");
	dlSet.buttonClick("Đồng ý");
	
//	pnMain.buttonClickByName("btnManagerSetTable");
//	var dlManage = frameui.dialog("dlQLDatbanQuay");
//	var pnManage = dlManage.panel("pnQLDatbanQuay");
//	dlManage.buttonClick("Thoát");
	
	//GỘP 3 BÀN VỪA ĐẶT TRƯỚC VÀO HKTTEST1
	frameui.showDialogTest("Gộp 3 bàn vừa đặt trước");
	frameui.delay();
	pnMain.buttonClickByName("btnTableGross");
	var dlGross = frameui.dialog("dlTableGross");
	var pnGross = dlGross.panel("panelTableGross");
	var tableL = pnGross.table("tableL");
	tableL.selectRow("HktTest1");
	dlGross.buttonClick(">");
	tableL.selectRow("HktTest2");
	dlGross.buttonClick(">");
	tableL.selectRow("HktTest3");
	dlGross.buttonClick(">");
	dlGross.buttonClick("Đồng ý");
	var dltbGross = frameui.dialog("dlTableGroup");
	var pntbGross = dltbGross.panel("panelTableGroup");
	dltbGross.buttonClick("Đồng ý");
	
	//CHỌN BÀN HKT1 ĐỂ THÊM MÓN
	frameui.showDialogTest("Chọn bàn để gọi món");
	frameui.delay();
	var pnMain = dlMain.panel("ScreenOften");
	var labelEat = pnMain.label("HktTest1"); 
	labelEat.mouseClick();
	
	/// THÊM MỚI 3 SP
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
	
	//CHUYỂN TẤT CẢ MÓN CHƯA THANH TOÁN TỪ BÀN HKTTEST1 SANG BÀN HKTTEST5
	frameui.showDialogTest("Chuyển các món chưa thanh toán sang bàn khác");
	frameui.delay();
	var pnMain = dlMain.panel("ScreenOften");
	var labelEat1 = pnMain.label("HktTest1"); 
	labelEat1.mouseClick();
	
	pnMain.buttonClickByName("btnMove");
	var dlMove= frameui.dialog("dlMoveTable");
	var pnMove= dlMove.panel("panelMoveTable");
	var cbLocation = pnMove.comboBox("cbLocation");
	cbLocation.selectItem("Tầng HktTest");
	var cbTable = pnMove.comboBox("cbTable");
	cbTable.selectItem("HktTest5");
	pnMove.buttonClickByName(">>");
	dlMove.buttonClick("Đồng ý");
	
	//IN HÓA ĐƠN THANH TOÁN CHO HKTTEST5
	frameui.showDialogTest("In hóa đơn thanh toán");
	frameui.delay();
	var pnMain = dlMain.panel("ScreenOften");
	var labelEat5 = pnMain.label("HktTest5"); 
	labelEat5.mouseClick();
	
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

	pnMain.deleteDataTest();
	dlMain.buttonClick("Thoát");
	
}
var robot = new Robot();
robot.add("TEST SetGrossTable ", "", CreateSetGrossTable);
console.clear();
robot.run();
robot.report();