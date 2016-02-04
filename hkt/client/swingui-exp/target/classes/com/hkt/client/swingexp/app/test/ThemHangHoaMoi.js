ScriptRunner.require("robotExp.js");

function CreateThemHangHoaMoi(frameui) {
	/**
	 * @ edit: Hạnh: Thêm if vào Test Lỗi
	 */
	frameui.showDialogTest("Chương trình TEST Thêm hàng hóa mới");
	frameui.delay();
	// Khởi tạo cho các biến
	var buttonMenu = frameui.button("menuRightKhoHangHoa");
	buttonMenu.mouseClick();
	var button = frameui.button("ThemHangHoaMoi");
	button.mouseClick();

	var dlMain = frameui.dialog("dlThemHangHoaMoi");
	var pnMain = dlMain.panel("ThemHangHoaMoi");

	// TEST LƯU NHẬP ĐÚNG DỮ LIỆU CÁC TRƯỜNG
	frameui.showDialogTest("TEST chức năng Lưu");
	frameui.delay();
	pnMain.fieldSet("txtName", "HH HktTest1");
	pnMain.fieldSet("txtCode", "Mã SP HktTest1");
	pnMain.fieldSet("txtBarCode", "Mã BarCode HktTest1");
	pnMain.fieldSet("txtNameOther", "Ngôn ngữ HktTest1");
	var cbUnitProduct = pnMain.comboBox("cbUnitProduct");
	cbUnitProduct.selectItem("ĐVSP HktTest1");
	var chbWarehousing = pnMain.checkBox("chbWarehousing");
	chbWarehousing.mouseClick();
	var chbProductPriceType = pnMain.checkBox("BG HktTest1");
	chbProductPriceType.mouseClick();
	pnMain.fieldSet("BG HktTest1", "1");
	var cbUnitProductPriceType = pnMain.comboBox("cbUnitProductPriceType1");
	cbUnitProductPriceType.selectItem("TT HktTest1");
	var chbProductTag = pnMain.checkBox("Nhóm SP HktTest1");
	chbProductTag.mouseClick();
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Lưu thành công!");
	frameui.delay();

	// BỎ TRỐNG CÁC TRƯỜNG VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa nhập dữ liệu các trường")) {
		pnMain.fieldSet("txtCode", "");
		pnMain.fieldSet("txtBarCode", "");
		dlMain.buttonClick("Lưu");
	}

	// BỎ TRỐNG TRƯỜNG TÊN VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa nhập Tên SP")) {
		frameui.delay();
		pnMain.fieldSet("txtName", "");
		pnMain.fieldSet("txtCode", "Mã SP HktTest2");
		pnMain.fieldSet("txtBarCode", "Mã Barcode HktTest2");
		pnMain.fieldSet("txtNameOther", "Ngôn ngữ HktTest2");
		var cbUnitProduct = pnMain.comboBox("cbUnitProduct");
		cbUnitProduct.selectItem("ĐVSP HktTest1");
		var chbWarehousing = pnMain.checkBox("chbWarehousing");
		chbWarehousing.mouseClick();
		dlMain.buttonClick("Lưu");
	}
	
	// BỎ TRỐNG TRƯỜNG MÃ SP VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa nhập Mã SP")) {
		frameui.delay();
		pnMain.fieldSet("txtName", "HH HktTest2");
		pnMain.fieldSet("txtCode", "");
		pnMain.fieldSet("txtBarCode", "Mã Barcode HktTest2");
		pnMain.fieldSet("txtNameOther", "Ngôn ngữ HktTest2");
		var cbUnitProduct = pnMain.comboBox("cbUnitProduct");
		cbUnitProduct.selectItem("ĐVSP HktTest1");
		var chbWarehousing = pnMain.checkBox("chbWarehousing");
		chbWarehousing.mouseClick();
		dlMain.buttonClick("Lưu");
	}
	
	// NHẬP TRÙNG MÃ VÀ LƯU
	if (frameui
			.showDialogTest("Lỗi nhập trùng Mã SP")) {
		frameui.delay();
		pnMain.fieldSet("txtName", "HH HktTest2");
		pnMain.fieldSet("txtCode", "Mã SP HktTest1");
		pnMain.fieldSet("txtBarCode", "Mã Barcode HktTest2");
		pnMain.fieldSet("txtNameOther", "Ngôn ngữ HktTest2");
		var cbUnitProduct = pnMain.comboBox("cbUnitProduct");
		cbUnitProduct.selectItem("ĐVSP HktTest1");
		var chbWarehousing = pnMain.checkBox("chbWarehousing");
		chbWarehousing.mouseClick();
		dlMain.buttonClick("Lưu");
	}
	
	// BỎ TRỐNG TRƯỜNG MÃ BARCODE VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa nhập Mã Barcode")) {
		frameui.delay();
		pnMain.fieldSet("txtName", "HH HktTest2");
		pnMain.fieldSet("txtCode", "Mã SP HktTest2");
		pnMain.fieldSet("txtBarCode", "");
		pnMain.fieldSet("txtNameOther", "Ngôn ngữ HktTest2");
		var cbUnitProduct = pnMain.comboBox("cbUnitProduct");
		cbUnitProduct.selectItem("ĐVSP HktTest1");
		var chbWarehousing = pnMain.checkBox("chbWarehousing");
		chbWarehousing.mouseClick();
		dlMain.buttonClick("Lưu");
	}
	
	// KẾT THÚC GIAO DIỆN THH
	frameui.showDialogTest("Thoát khỏi giao diện Thêm mới hàng hóa");
	frameui.delay();
	dlMain.buttonClick("Thoát");

}

function CreateQuanLyHangHoa(frameui) {

	// Vào giao diện Quản lý hàng hóa và click xem chi tiết
	frameui.showDialogTest("Xem chi tiết HH vừa thêm mới");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightKhoHangHoa");
	buttonMenu.mouseClick();
	var button = frameui.button("btnQuanLyHangHoa");
	button.mouseClick();

	var dlLocSP = frameui.dialog("dlLocSanPham");
	var pnLocSP = dlLocSP.panel("pnLocSanPham");
	var checkBoxTatCa = pnLocSP.checkBox("chbTatCa");
	checkBoxTatCa.mouseClick();
	dlLocSP.buttonClick("Đồng ý");

	var dlMain = frameui.dialog("dlQuanLyHangHoa");
	var tableQuanLyHangHoa = dlMain.table("Quản lý hàng hóa");

	// CLICK VÀO HH VỪA TẠO
	tableQuanLyHangHoa.doubleClickRow("Mã SP HktTest1");
	frameui.delay();

	var dlChild = frameui.dialog("dlChiTietHangHoa");
	var pnChild = dlChild.panel("ChiTietHangHoa");

	// TEST XEM LẠI: sửa giá trị ban đầu từ 1 thành 2 và xem lại
	frameui.showDialogTest("TEST chức năng Xem lại");
	frameui.delay();
	dlChild.buttonClick("Sửa");
	pnChild.fieldSet("txtName", "HH HktTest2");
	pnChild.fieldSet("txtNameOther", "Ngôn ngữ HktTest2");
	var cbUnitProduct = pnChild.comboBox("cbUnitProduct");
	cbUnitProduct.selectItem("ĐVSP HktTest1");
	var chbWarehousing = pnChild.checkBox("chbWarehousing");
	chbWarehousing.mouseClick();
	dlChild.buttonClick("Xem lại");
	frameui.delay();

	// TEST SỬA: sửa giá trị ban đầu từ 1 thành 2 và lưu
	frameui.showDialogTest("TEST chức năng Sửa");
	frameui.delay();
	dlChild.buttonClick("Sửa");
	pnChild.fieldSet("txtName", "HH HktTest2");
	pnChild.fieldSet("txtNameOther", "Ngôn ngữ HktTest2");
	var cbUnitProduct = pnChild.comboBox("cbUnitProduct");
	cbUnitProduct.selectItem("ĐVSP HktTest1");
	var chbWarehousing = pnChild.checkBox("chbWarehousing");
	chbWarehousing.mouseClick();
	dlChild.buttonClick("Lưu");
	frameui.delay();
	frameui.showDialogTest("Sửa thành công!");
	frameui.delay();
	dlChild.buttonClick("Thoát");
	
	// TEST VIẾT LẠI: reset các trường
	frameui.showDialogTest("TEST chức năng Viết lại")
	frameui.delay();
	tableQuanLyHangHoa.doubleClickRow("Mã SP HktTest1");
	var dlChild = frameui.dialog("dlChiTietHangHoa");
	var pnChild = dlChild.panel("ChiTietHangHoa");
	dlChild.buttonClick("Viết lại");
	
	// TEST THÊM MỚI 1 HH SO SÁNH VỚI HH BAN ĐẦU
	frameui.showDialogTest("TEST chức năng Thêm mới")
	frameui.delay();
	pnChild.fieldSet("txtName", "HH HktTest3");
	pnChild.fieldSet("txtCode", "Mã SP HktTest3");
	pnChild.fieldSet("txtNameOther", "Ngôn ngữ HktTest3");
	var cbUnitProduct = pnChild.comboBox("cbUnitProduct");
	cbUnitProduct.selectItem("ĐVSP HktTest1");
	var chbWarehousing = pnChild.checkBox("chbWarehousing");
	chbWarehousing.mouseClick();
	frameui.delay();
//	var cbUnitProductPriceType = pnChild.comboBox("BG HktTest1");	
//	cbUnitProductPriceType.selectItem("TT HktTest1");
//	pnChild.fieldSet("BG HktTest1", "3");
	var chbProductTag = pnChild.checkBox("Nhóm SP HktTest1");
	chbProductTag.mouseClick();
	dlChild.buttonClick("Lưu");
	frameui.showDialogTest("Thêm mới thành công!");

	// THÊM 50 BẢN GHI TEST CHUYỂN TRANG
	frameui.showDialogTest(" TEST chức năng Chuyển trang");
	frameui.delay();
	pnChild.createDataTest();
	dlChild.buttonClick("Thoát");

	// CLICK SANG TRANG 2
	frameui.showDialogTest("Sang trang 2");
	frameui.delay();
	dlMain.buttonClick("2");
	frameui.showDialogTest("Trở về trang đầu tiên");
	dlMain.buttonClick("<<");
	frameui.delay();

	// TEST TÌM KIẾM
	frameui.showDialogTest("TEST chức năng Tìm kiếm -> tìm theo mã");
	frameui.delay();
	var comboBox = dlMain.comboBox("cbSearch");
	comboBox.selectItem("Mã sản phẩm");
	dlMain.fieldSet("txtSearch", "3");
	frameui.delay();

	// TEST XÓA
	frameui.showDialogTest("TEST chức năng Xóa");
	frameui.delay();
	tableQuanLyHangHoa.doubleClickRow("SP HktTest3");
	var dlChild = frameui.dialog("dlChiTietHangHoa");
	dlChild.buttonClick("Sửa");
	dlChild.buttonClick("Xóa");
	frameui.delay();
	frameui.showDialogTest("Xóa thành công!");
	frameui.delay();
	
	// THOÁT KHỎI GIAO DIỆN TEST VÀ XÓA DỮ LIỆU SAU KHI TEST
	frameui
			.showDialogTest("Chương trình TEST Thêm mới hàng hóa kết thúc!");
	frameui.delay();
	pnChild.deleteDataTest();
	frameui.delay();
	dlChild.buttonClick("Thoát");
	dlMain.buttonClickByName("btnExit");
}

var robot = new Robot();
robot.add("TEST ThemHangHoaMoi ", "", CreateThemHangHoaMoi);
robot.add("TEST QuanLyHangHoa ", "", CreateQuanLyHangHoa);
console.clear();
robot.run();
robot.report();