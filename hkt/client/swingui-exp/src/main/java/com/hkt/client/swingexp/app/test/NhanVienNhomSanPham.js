ScriptRunner.require("robotExp.js");
function CreateNV(frameui) {
	/**
	 * @ author: Hạnh
	 */
	frameui.showDialogTest("Chương trình TEST Nhân viên nhóm sản phẩm");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightNhanSu");
	buttonMenu.mouseClick();
	var button = frameui.button("NhanVienNhomSanPham");
	button.mouseClick()

	var dlMain = frameui.dialog("dlNhanVienNhomSanPham");
	var pnChild = dlMain.panel("NhanVienNhomSanPham");
	frameui.delay();
	
	// TEST LƯU NHẬP ĐÚNG DỮ LIỆU CÁC TRƯỜNG
	frameui.showDialogTest("TEST chức năng Lưu");
	frameui.delay();
	pnChild.fieldSet("txtProduct", "Mã NHH HktTest1");
	pnChild.fieldSet("txtEmployee", "Mã NV HktTest1");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest1");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Lưu thành công");
	frameui.delay();
	
	// BỎ TRỐNG CÁC TRƯỜNG VÀ LƯU
	if (frameui
			.showDialogTest("Lỗi chưa nhập dữ liệu các trường")) {
		frameui.delay();
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// BỎ CHỌN TRƯỜNG NHÓM SP VÀ LƯU
	if (frameui
			.showDialogTest("Lỗi chưa chọn Nhóm SP")) {
		frameui.delay();
		pnChild.fieldSet("txtEmployee", "Mã NV HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// BỎ CHỌN TRƯỜNG NV VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa chọn NV")) {
		frameui.delay();
		pnChild.fieldSet("txtProduct", "Mã NHH HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// TEST XEM LẠI: sửa giá trị ban đầu từ 1 thành 2 và xem lại
	frameui.showDialogTest("TEST chức năng Xem lại");
	frameui.delay();
	var table = pnChild.table("tbGroup");
	table.doubleClickRow("Mã NHH HktTest1");
	dlMain.buttonClick("Sửa");
	pnChild.fieldSet("txtEmployee", "Mã NV HktTest2");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest2");
	dlMain.buttonClick("Xem lại");
	frameui.delay();

	// TEST SỬA: sửa giá trị ban đầu từ 1 thành 2 và lưu
	frameui.showDialogTest("TEST chức năng Sửa");
	frameui.delay();
	var table = pnChild.table("tbGroup");
	table.doubleClickRow("Mã NHH HktTest1");
	dlMain.buttonClick("Sửa");
	pnChild.fieldSet("txtEmployee", "Mã NV HktTest2");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest2");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Sửa thành công !");
	frameui.delay();

	// TEST VIẾT LẠI: reset các trường
	frameui.showDialogTest("TEST chức năng Viết lại")
	frameui.delay();
	table.doubleClickRow("Mã NHH HktTest1");
	dlMain.buttonClick("Viết lại");

	// TEST THÊM MỚI 1 BẢNG GIÁ SO SÁNH VỚI BẢNG GIÁ BAN ĐẦU
	frameui.showDialogTest("TEST chức năng Thêm mới")
	frameui.delay();
	pnChild.fieldSet("txtProduct", "Mã NHH HktTest3");
	pnChild.fieldSet("txtEmployee", "Mã NV HktTest3");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest3");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Thêm mới thành công !");
	frameui.delay();

	// TEST XÓA
	frameui.showDialogTest("TEST chức năng Xóa");
	frameui.delay();
	table.doubleClickRow("Mã NHH HktTest1");
	dlMain.buttonClick("Sửa");
	dlMain.buttonClick("Xóa");
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");
	frameui.showDialogTest("Xóa thành công!");
	frameui.delay();

	// THOÁT KHỎI GIAO DIỆN TEST VÀ XÓA DỮ LIỆU SAU KHI TEST
	frameui
			.showDialogTest("Chương trình TEST Nhân viên nhóm sản phẩm kết thúc!");
	frameui.delay();
	pnChild.deleteDataTest();
	dlMain.buttonClick("Thoát");
	
}
var robot = new Robot();
robot.add("TEST NhanVienNhomSanPham", "", CreateNV);
console.clear();
robot.run();
robot.report();