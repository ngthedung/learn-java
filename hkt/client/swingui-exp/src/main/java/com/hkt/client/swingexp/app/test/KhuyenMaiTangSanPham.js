ScriptRunner.require("robotExp.js");
function CreateKhuyenMaiTangSanPham(frameui) {
	/**
	 * @ edit: Hạnh: Thêm if vào Test Lỗi
	 */
	frameui.showDialogTest("Chương trình TEST Khuyến mãi tặng sản phẩm");
	frameui.delay();
	
	var buttonMenu = frameui.button("menuRightKhuyenMai");
	buttonMenu.mouseClick();
	var button = frameui.button("KhuyenMaiTangSanPham");
	button.mouseClick();

	var dlMain = frameui.dialog("dlKhuyenMaiTangSanPham");
	var pnMain = dlMain.panel("KhuyenMaiTangSanPham");
	
	// TEST LƯU NHẬP ĐÚNG DỮ LIỆU CÁC TRƯỜNG
	frameui.showDialogTest("TEST chức năng Lưu");
	frameui.delay();
	pnMain.fieldSet("txtProduct", "HH HktTest1");
	pnMain.fieldSet("txtSoLuongMua", "10");
	pnMain.fieldSet("txtSoLuongTang", "2");
	pnMain.fieldSet("txtMieuTa", "Miêu tả SP HktTest1");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Lưu thành công !");
	frameui.delay();

	// BỎ TRỐNG CÁC TRƯỜNG VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa nhập dữ liệu các trường")){
		frameui.delay();
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
	
	// BỎ TRỐNG TRƯỜNG TÊN SP VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa chọn Tên SP")) {
		frameui.delay();
		comboBoxTenSP.selectItem("");
		pnMain.fieldSet("txtSoLuongMua", "10");
		pnMain.fieldSet("txtSoLuongTang", "2");
		pnMain.fieldSet("txtMieuTa", "Miêu tả SP HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// BỎ TRỐNG TRƯỜNG SL MUA  VÀ LƯU
	if (frameui
			.showDialogTest("Lỗi chưa nhập SL mua")) {
		frameui.delay();
		pnMain.fieldSet("txtProduct", "HH HktTest1");
		pnMain.fieldSet("txtSoLuongMua", "");
		pnMain.fieldSet("txtSoLuongTang", "2");
		pnMain.fieldSet("txtMieuTa", "Miêu tả SP HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// BỎ TRỐNG TRƯỜNG SL TẶNG VÀ LƯU
	if (frameui
			.showDialogTest("Lỗi chưa nhập SL tặng")) {
		frameui.delay();
		pnMain.fieldSet("txtProduct", "HH HktTest1");
		pnMain.fieldSet("txtSoLuongMua", "10");
		pnMain.fieldSet("txtSoLuongTang", "");
		pnMain.fieldSet("txtMieuTa", "Miêu tả SP HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
	
	// NHẬP SAI ĐỊNH DẠNG SL MUA VÀ LƯU
	if (frameui
			.showDialogTest("Lỗi nhập sai định dạng SL mua")) {
		frameui.delay();
		pnMain.fieldSet("txtProduct", "HH HktTest1");
		pnMain.fieldSet("txtSoLuongMua", "HktTest1");
		pnMain.fieldSet("txtSoLuongTang", "2");
		pnMain.fieldSet("txtMieuTa", "Miêu tả SP HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// NHẬP SAI ĐỊNH DẠNG SL TẶNG VÀ LƯU
	if (frameui
			.showDialogTest("Lỗi nhập sai định dạng SL tặng")) {
		frameui.delay();
		pnMain.fieldSet("txtProduct", "HH HktTest1");
		pnMain.fieldSet("txtSoLuongMua", "10");
		pnMain.fieldSet("txtSoLuongTang", "HktTest1");
		pnMain.fieldSet("txtMieuTa", "Miêu tả SP HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
	
	// TEST XEM LẠI: sửa giá trị ban đầu từ 1 thành 2 và xem lại
	frameui.showDialogTest("TEST chức năng Xem lại");
	frameui.delay();
	var table = pnMain.table("TbPromotionGiveProduct");
	table.doubleClickRow("KM SP HH HktTest1");
	dlMain.buttonClick("Sửa");
	pnMain.fieldSet("txtSoLuongMua", "20");
	pnMain.fieldSet("txtSoLuongTang", "5");
	pnMain.fieldSet("txtMieuTa", "Miêu tả SP HktTest2");
	dlMain.buttonClick("Xem lại");
	frameui.delay();
	
	// TEST SỬA: sửa giá trị ban đầu từ 1 thành 2 và lưu
	frameui.showDialogTest("TEST chức năng Sửa");
	frameui.delay();
	var table = pnMain.table("TbPromotionGiveProduct");
	table.doubleClickRow("KM SP HH HktTest1");
	dlMain.buttonClick("Sửa");
	pnMain.fieldSet("txtSoLuongMua", "20");
	pnMain.fieldSet("txtSoLuongTang", "5");
	pnMain.fieldSet("txtMieuTa", "Miêu tả SP HktTest2");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Sửa thành công !");
	frameui.delay();
	
	// TEST VIẾT LẠI: reset các trường
	frameui.showDialogTest("TEST chức năng Viết lại")
	frameui.delay();
	table.doubleClickRow("KM SP HH HktTest1");
	dlMain.buttonClick("Viết lại");
	
	// TEST THÊM MỚI 1 KM SO SÁNH VỚI KM BAN ĐẦU
	frameui.showDialogTest("TEST chức năng Thêm mới")
	frameui.delay();
	var comboBoxTenSP = pnMain.comboBox("cbTenSP");
	comboBoxTenSP.selectItem("HH HktTest3");
	pnMain.fieldSet("txtSoLuongMua", "50");
	pnMain.fieldSet("txtSoLuongTang", "15");
	pnMain.fieldSet("txtMieuTa", "Miêu tả SP HktTest3");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Thêm mới thành công!");
	frameui.delay();
	
	// TEST XÓA
	frameui.showDialogTest("TEST chức năng Xóa");
	frameui.delay();
	table.doubleClickRow("KM SP HH HktTest1");
	dlMain.buttonClick("Sửa");
	dlMain.buttonClick("Xóa");
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");
	frameui.showDialogTest("Xóa thành công!");
	frameui.delay();

	// THOÁT KHỎI GIAO DIỆN TEST VÀ XÓA DỮ LIỆU SAU KHI TEST
	frameui
			.showDialogTest("Chương trình TEST Khuyến mại tặng sản phẩm kết thúc!");
	frameui.delay();
	pnMain.deleteDataTest();
	dlMain.buttonClick("Thoát");

}

var robot = new Robot();
robot.add("TEST KhuyenMaiTangSP ", "", CreateKhuyenMaiTangSanPham);
console.clear();
robot.run();
robot.report();