//Duy Khanh
ScriptRunner.require("robotExp.js");

function PromotionAndChangePrice(frameui) {
	frameui.showDialogTest(" Chương trình TEST Bán hàng - Mở KV Bàn/Quầy "); 
	frameui.delay();
	
	var dMain = frameui.dialog("dlScreenOften");
	var pnMain = dMain.panel("ScreenOften"); 
	
	//Thêm mới bàn quầy
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
	
	//THÊM MỚI 3 SP
	frameui.showDialogTest("Thêm mới 4 SP");
	frameui.delay();
	
	pnMain.buttonClickByName("btnThemSP");
	var dlSP = frameui.dialog("dlThemHangHoaMoi");
	var pnSP = dlSP.panel("ThemHangHoaMoi");
	
	pnSP.fieldSet("txtName", "HH HktTest1");
	pnSP.fieldSet("txtCode", "Mã SP HktTest1");
	pnSP.fieldSet("txtBarCode", "Mã BarCode HktTest1");
	pnSP.fieldSet("txtNameOther", "Ngôn ngữ HktTest1");
	var cbUnitProduct = pnSP.comboBox("cbUnitProduct");
	cbUnitProduct.selectItem("ĐVSP HktTest1");
	var chbWarehousing = pnSP.checkBox("chbWarehousing");
	chbWarehousing.mouseClick();
	var chbProductPriceType = pnSP.checkBox("BG HktTest1");
//	chbProductPriceType.mouseClick();
	pnSP.fieldSet("BG HktTest1", "110000");
	var cbUnitProductPriceType = pnSP.comboBox("cbUnitProductPriceType1");
	cbUnitProductPriceType.selectItem("TT HktTest1");
	var chbProductTag = pnSP.checkBox("NHH HktTest1");
	chbProductTag.mouseClick();
	dlSP.buttonClick("Lưu");
	
	pnSP.fieldSet("txtName", "HH HktTest2");
	pnSP.fieldSet("txtCode", "Mã SP HktTest2");
	pnSP.fieldSet("txtBarCode", "Mã BarCode HktTest2");
	pnSP.fieldSet("txtNameOther", "Ngôn ngữ HktTest2");
	var cbUnitProduct = pnSP.comboBox("cbUnitProduct");
	cbUnitProduct.selectItem("ĐVSP HktTest1");
	var chbWarehousing = pnSP.checkBox("chbWarehousing");
	chbWarehousing.mouseClick();
	var chbProductPriceType = pnSP.checkBox("BG HktTest2");
//	chbProductPriceType.mouseClick();
	pnSP.fieldSet("BG HktTest1", "250000");
	var cbUnitProductPriceType = pnSP.comboBox("cbUnitProductPriceType1");
	cbUnitProductPriceType.selectItem("TT HktTest1");
	var chbProductTag = pnSP.checkBox("NHH HktTest1");
	chbProductTag.mouseClick();
	dlSP.buttonClick("Lưu");
	
	pnSP.fieldSet("txtName", "HH HktTest3");
	pnSP.fieldSet("txtCode", "Mã SP HktTest3");
	pnSP.fieldSet("txtBarCode", "Mã BarCode HktTest3");
	pnSP.fieldSet("txtNameOther", "Ngôn ngữ HktTest3");
	var cbUnitProduct = pnSP.comboBox("cbUnitProduct");
	cbUnitProduct.selectItem("ĐVSP HktTest1");
	var chbWarehousing = pnSP.checkBox("chbWarehousing");
	chbWarehousing.mouseClick();
	var chbProductPriceType = pnSP.checkBox("BG HktTest3");
//	chbProductPriceType.mouseClick();
	pnSP.fieldSet("BG HktTest1", "350000");
	var cbUnitProductPriceType = pnSP.comboBox("cbUnitProductPriceType1");
	cbUnitProductPriceType.selectItem("TT HktTest1");
	var chbProductTag = pnSP.checkBox("NHH HktTest1");
	chbProductTag.mouseClick();
	dlSP.buttonClick("Lưu");
	
	
	pnSP.fieldSet("txtName", "HH HktTest4");
	pnSP.fieldSet("txtCode", "Mã SP HktTest4");
	pnSP.fieldSet("txtBarCode", "Mã BarCode HktTest4");
	pnSP.fieldSet("txtNameOther", "Ngôn ngữ HktTest4");
	var cbUnitProduct = pnSP.comboBox("cbUnitProduct");
	cbUnitProduct.selectItem("ĐVSP HktTest1");
	var chbWarehousing = pnSP.checkBox("chbWarehousing");
	chbWarehousing.mouseClick();
	var chbProductPriceType = pnSP.checkBox("BG HktTest4");
//	chbProductPriceType.mouseClick();
	pnSP.fieldSet("BG HktTest1", "250000");
	var cbUnitProductPriceType = pnSP.comboBox("cbUnitProductPriceType1");
	cbUnitProductPriceType.selectItem("TT HktTest1");
	var chbProductTag = pnSP.checkBox("NHH HktTest1");
	chbProductTag.mouseClick();
	dlSP.buttonClick("Lưu");
	
	dlSP.buttonClick("Thoát");
	//Thêm đối tác***-----------------------------------------------------
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

	dlMain.buttonClick("Thoát");
	
//***Lập khuyến mại cho một nhóm sản phẩm vừa tạo***----------------------------------------------------
//	frameui.showDialogTest("Lập khuyến mại cho nhóm sản phẩm NHH HktTest1");
	frameui.delay();
//	dlMain.buttonClick("Thoát");
	
	var buttonMenu = frameui.button("menuRightKhuyenMai");
	buttonMenu.mouseClick();
	var buttonMenu = frameui.button("LapKhuyenMai");
	buttonMenu.mouseClick();
	
	var dlMain = frameui.dialog("dlLapKhuyenMai");
	var pnMain = dlMain.panel("LapKhuyenMai");
//	
////	// **chọn nhóm sp
//	var comboBoxGroupProduct = pnMain.comboBox("cbNhomSanPham");
//	comboBoxGroupProduct.selectItem("NHH HktTest1");
////	// **tick chọn nhóm sp
//	var checkBoxGroupProduct = pnMain.checkBox("chkNhomSanPham");
//	checkBoxGroupProduct.mouseClick();
////	//**Tên, ..etc
//	pnMain.fieldSet("txtTenKM", "Khuyến mại nhóm sp HktTest1");
//	pnMain.fieldSet("txtSoLanSuDung", "100");
//	pnMain.fieldSet("txtThoiGianBatDau", "00:01");
//	pnMain.fieldSet("txtThoiGianKetThuc", "23:59");
//	
////	//Kiểu khuyến mại
//	var radioButton2 = pnMain.radioButton("rdKMPhanTram");
//	radioButton2.mouseClick();
//	pnMain.fieldSet("txtKMPhanTram", "5");
//	pnMain.fieldSet("txtApDungTu", "500");
//	
////	var comboBox1 = pnMain.comboBox("cbDonViTien");
////	comboBox1.selectItem("TT HktTest1");
//
//	var comboBox2 = pnMain.comboBox("cbDonViTien1");
//	comboBox2.selectItem("TT HktTest1");
//	pnMain.fieldSet("txtApDungDen", "1000");
//	
//	var comboBox3 = pnMain.comboBox("cbDonViTien2");
//	comboBox3.selectItem("TT HktTest1");
//	pnMain.fieldSet("txtGhiChu", "Ghi chú HktTest1");
//	dlMain.buttonClick("Lưu");
//	frameui.showDialogTest("Lưu thành công !");
//	frameui.delay();
	
	//***Lập khuyến mại cho 1 sản phẩm------------------------------------------------***
//	frameui.showDialogTest("Lập khuyến mại cho 1 sản phẩm với kiểu KM là tiền");
//	
//	// **chọn nhóm sp
//	var comboBoxGroupProduct = pnMain.comboBox("cbNhomSanPham");
//	comboBoxGroupProduct.selectItem("NHH HktTest1");
//	
//	//chọn cb sản phẩm
//	var comboBoxProduct = pnMain.comboBox("cboSanPham");
////	comboBoxProduct.selectItem("HH HktTest4");
//	comboBoxProduct.selectItem("A");
//	// tick chọn sản phẩm
//	var checkBoxProduct = pnMain.checkBox("chbSanPham");
//	checkBoxProduct.mouseClick();
//	//Tên, ..etc
//	pnMain.fieldSet("txtTenKM", "Khuyến mại sản phẩm HktTest1");
//	pnMain.fieldSet("txtSoLanSuDung", "50");
//	pnMain.fieldSet("txtThoiGianBatDau", "00:01");
//	pnMain.fieldSet("txtThoiGianKetThuc", "23:59");
//	
//	//Kiểu khuyến mại
//	var radioButton2 = pnMain.radioButton("rdKMTien");
//	radioButton2.mouseClick();
//	pnMain.fieldSet("txtKMTien", "10");
//	pnMain.fieldSet("txtApDungTu", "100");
//	
//	var comboBox1 = pnMain.comboBox("cbDonViTien");
//	comboBox1.selectItem("TT HktTest1");
//
//	var comboBox2 = pnMain.comboBox("cbDonViTien1");
//	comboBox2.selectItem("TT HktTest1");
//	pnMain.fieldSet("txtApDungDen", "400");
//	
//	var comboBox3 = pnMain.comboBox("cbDonViTien2");
//	comboBox3.selectItem("TT HktTest1");
//	pnMain.fieldSet("txtGhiChu", "Ghi chú HktTest1");
//	dlMain.buttonClick("Lưu");
//	frameui.showDialogTest("Lưu thành công !");
//	frameui.delay();
//	
//	// KẾT THÚC GIAO DIỆN LẬP KM
//	frameui.showDialogTest("Thoát khỏi giao diện Lập KM");
//	frameui.delay();
//	dlMain.buttonClick("Thoát");
	
	//Lập khuyến mại cho nhóm đối tác
	//***Lập khuyến mại Nhóm đối tác cn và dn------------------------------------------------***
	frameui.showDialogTest("Bán hàng cho KH với khuyến mãi cho 1 đối tác");
	
	//Taoj khuyến mại cho khách hàng
	frameui.showDialogTest("Lập khuyến mại cho 1 sản phẩm với kiểu KM là tiền");
	
	// **chọn nhóm sp
	var comboBoxGroupProduct = pnMain.comboBox("cbNhomSanPham");
	comboBoxGroupProduct.selectItem("NHH HktTest1");
	//tick chọn nhóm hhoa
	var checkBoxGroupProduct = pnMain.checkBox("chkNhomSanPham");
	checkBoxGroupProduct.mouseClick();
	//chọn cb đối tác
	var comboBoxPartner = pnMain.comboBox("cboPartner");
//	comboBoxProduct.selectItem("HH HktTest4");
	comboBoxPartner.selectItem("Tên ĐT HktTest1");
	// tick chọn đối tác
	var checkBoxPartner = pnMain.checkBox("chbPartner");
	checkBoxPartner.mouseClick();
	//Tên, ..etc
	pnMain.fieldSet("txtTenKM", "Khuyến mại đối tác HktTest1");
	pnMain.fieldSet("txtSoLanSuDung", "50");
	pnMain.fieldSet("txtThoiGianBatDau", "00:01");
	pnMain.fieldSet("txtThoiGianKetThuc", "23:59");
	
	//Kiểu khuyến mại
	var radioButton2 = pnMain.radioButton("rdKMTien");
	radioButton2.mouseClick();
	pnMain.fieldSet("txtKMTien", "200");
	pnMain.fieldSet("txtApDungTu", "1000");
	
	var comboBox1 = pnMain.comboBox("cbDonViTien");
	comboBox1.selectItem("TT HktTest1");

	var comboBox2 = pnMain.comboBox("cbDonViTien1");
	comboBox2.selectItem("TT HktTest1");
	pnMain.fieldSet("txtApDungDen", "40000");
	
	var comboBox3 = pnMain.comboBox("cbDonViTien2");
	comboBox3.selectItem("TT HktTest1");
	pnMain.fieldSet("txtGhiChu", "Ghi chú HktTest1");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Lưu thành công !");
	frameui.delay();
	
	// KẾT THÚC GIAO DIỆN LẬP KM
	frameui.showDialogTest("Thoát khỏi giao diện Lập KM");
	frameui.delay();
	dlMain.buttonClick("Thoát");
	
	//QUAY LẠI MÀN HÌNH BÁN HÀNG VỚI KH ĐÃ CHỌN---------------------------------------
	frameui.showDialogTest("Bán hàng cho KH với khuyến mãi của 1 nhóm sản phẩm");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightBanHang");
	buttonMenu.mouseClick();
	var button = frameui.button("ScreenOften");
	button.mouseClick();
	var dlMain = frameui.dialog("dlScreenOften");
	var pnMain = dlMain.panel("ScreenOften");
	
	//CHỌN Món VÀO BÀN
//	frameui.showDialogTest("Chọn món vào bàn");
//	frameui.delay();
//	var labelHH = pnMain.label("lbHH"); 
//	labelHH.mouseClick();
//	var tbProduct = pnMain.table("tbProduct");
//	tbProduct.doubleClickRow("HH HktTest1");
//	tbProduct.doubleClickRow("HH HktTest2");
//	tbProduct.doubleClickRow("HH HktTest3");
	
//	ĐỔI GIÁ TỪNG SP ĐÃ CHỌN
//	frameui.showDialogTest("Đổi giá từng món");
//	frameui.delay();
//	var tbSale = pnMain.table("tbSale");
//	tbSale.selectRow("HH HktTest1");
//	pnMain.buttonClickByName("btnChangePrice");
//	var dlPrice = frameui.dialog("dlchoosePrice");
//	var pnPrice = dlPrice.panel("choosePrice");
//	pnPrice.fieldSet("txtPriceNew", "700");
//	dlPrice.buttonClick("Đồng ý");
//	
//	tbSale.selectRow("HH HktTest2");
//	pnMain.buttonClickByName("btnChangePrice");
//	var dlPrice = frameui.dialog("dlchoosePrice");
//	var pnPrice = dlPrice.panel("choosePrice");
//	pnPrice.fieldSet("txtPriceNew", "850");
//	dlPrice.buttonClick("Đồng ý");
//	
//	tbSale.selectRow("HH HktTest3");
//	pnMain.buttonClickByName("btnChangePrice");
//	var dlPrice = frameui.dialog("dlchoosePrice");
//	var pnPrice = dlPrice.panel("choosePrice");
//	pnPrice.fieldSet("txtPriceNew", "600");
//	dlPrice.buttonClick("Đồng ý");
//	
//	pnMain.buttonClickByName("btnPaymen");
//	var dlPaymen = frameui.dialog("dlPayment");
//	var pnPaymen = dlPaymen.panel("panelPayment");
//	dlPaymen.buttonClick("Đồng ý");
//	
//	var dlKho = frameui.dialog("dlKho");
//	var pnKho = dlKho.panel("Kho");
//	var chbKho = pnKho.checkBox("chbdelete");
//	dlKho.buttonClick("Đồng ý");
//	var dlKho = frameui.dialog("dlKho");
//	dlKho.buttonClick("Đồng ý");
//	var dlKho = frameui.dialog("dlKho");
//	dlKho.buttonClick("Đồng ý");
	
	//***Test khuyến mại cho 1 sản phâm***
//	frameui.showDialogTest("Bán hàng cho KH với khuyến mãi của 1 sản phẩm");
//	//CHỌN Món VÀO BÀN
//	frameui.showDialogTest("Chọn món vào bàn");
//	frameui.delay();
//	var labelHH = pnMain.label("lbHH"); 
//	labelHH.mouseClick();
//	var tbProduct = pnMain.table("tbProduct");
//	tbProduct.doubleClickRow("HH HktTest4");
//	
//	tbSale = pnMain.table("tbSale");
//	tbSale.selectRow("HH HktTest4");
//	pnMain.buttonClickByName("btnChangePrice");
//	var dlPrice = frameui.dialog("dlchoosePrice");
//	var pnPrice = dlPrice.panel("choosePrice");
//	pnPrice.fieldSet("txtPriceNew", "100");
//	dlPrice.buttonClick("Đồng ý");
//	
//	pnMain.buttonClickByName("btnPaymen");
//	var dlPaymen = frameui.dialog("dlPayment");
//	var pnPaymen = dlPaymen.panel("panelPayment");
//	dlPaymen.buttonClick("Đồng ý");
//	
//	var dlKho = frameui.dialog("dlKho");
//	var pnKho = dlKho.panel("Kho");
//	var chbKho = pnKho.checkBox("chbdelete");
//	dlKho.buttonClick("Đồng ý");
	
	
// Test KM cho đối tác
	frameui.showDialogTest("Bán hàng cho đối tác với khuyến mại của nhóm sản phẩm");
	//CHỌN Món VÀO BÀN
	frameui.showDialogTest("Chọn món vào bàn");
	frameui.delay();
	var labelHH = pnMain.label("lbHH"); 
	labelHH.mouseClick();
	var tbProduct = pnMain.table("tbProduct");
	tbProduct.doubleClickRow("HH HktTest4");
	
	tbSale = pnMain.table("tbSale");
	tbSale.selectRow("HH HktTest4");
	pnMain.buttonClickByName("btnChangePrice");
	var dlPrice = frameui.dialog("dlchoosePrice");
	var pnPrice = dlPrice.panel("choosePrice");
	pnPrice.fieldSet("txtPriceNew", "2000");
	dlPrice.buttonClick("Đồng ý");
	
	pnMain.buttonClickByName("btnPaymen");
	var dlPaymen = frameui.dialog("dlPayment");
	var pnPaymen = dlPaymen.panel("panelPayment");
	dlPaymen.buttonClick("Đồng ý");
	
	var dlKho = frameui.dialog("dlKho");
	var pnKho = dlKho.panel("Kho");
	var chbKho = pnKho.checkBox("chbdelete");
	dlKho.buttonClick("Đồng ý");
	
	
	//
	frameui.showDialogTest("Chương trình TEST Bán hàng - Test khuyến mãi và đổi giá!");
	frameui.delay();
//	pnMain.deleteDataTest();
	dlMain.buttonClick("Thoát");
	


}



var robot = new Robot();
robot.add("TEST Khuyến mại, hủy và sửa giá sản phẩm", " ", PromotionAndChangePrice);
console.clear();
robot.run();
robot.report();
