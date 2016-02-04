ScriptRunner.require("robotExp.js");

function CreateQuanLyBangGia(frameui) {
	/**
	 * @ author: Hạnh
	 */
	frameui.showDialogTest("Chương trình TEST Quản lý bảng giá");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightBanHang");
	buttonMenu.mouseClick();

	var button = frameui.button("QuanLyBangGia");
	button.mouseClick();

	var dlMain = frameui.dialog("dlQuanLyBangGia");
	var pnChild = dlMain.panel("QuanLyBangGia");

	// TEST LƯU NHẬP ĐÚNG DỮ LIỆU CÁC TRƯỜNG
	frameui.showDialogTest("TEST chức năng Lưu");
	frameui.delay();
	pnChild.fieldSet("txtType", "Mã BG HktTest1");
	pnChild.fieldSet("txtName", "Tên BG HktTest1");
	pnChild.fieldSet("txtDescription", "Miêu tả BG HktTest1");
	dlMain.buttonClick("Lưu");
	frameui.delay();
	frameui.showDialogTest("Lưu thành công !");

	// BỎ TRỐNG CÁC TRƯỜNG VÀ LƯU
	if (frameui
			.showDialogTest("Lỗi chưa nhập dữ liệu các trường")) {
		frameui.delay();
		pnChild.fieldSet("txtType", "");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// BỎ TRỐNG TRƯỜNG MÃ VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa nhập Mã BG")) {
		frameui.delay();
		pnChild.fieldSet("txtType", "");
		pnChild.fieldSet("txtName", "Tên BG HktTest1");
		pnChild.fieldSet("txtDescription", "Miêu tả bảng giá HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// NHẬP TRÙNG MÃ VÀ LƯU
	if (frameui.showDialogTest("Lỗi nhập trùng Mã BG")) {
		frameui.delay();
		pnChild.fieldSet("txtType", "Mã BG HktTest1");
		pnChild.fieldSet("txtName", "Tên BG HktTest2");
		pnChild.fieldSet("txtDescription", "Miêu tả BG HktTest2");
		dlMain.buttonClick("Lưu");
		frameui.showDialogTest("Mã bị trùng");
		frameui.delay();
	}

	// BỎ TRỐNG TRƯỜNG TÊN VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa nhập Tên BG")) {
		frameui.delay();
		pnChild.fieldSet("txtType", "Mã BG HktTest1");
		pnChild.fieldSet("txtName", "");
		pnChild.fieldSet("txtDescription", "Miêu tả BG HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// TEST XEM LẠI: sửa giá trị ban đầu từ 1 thành 2 và xem lại
	frameui.showDialogTest("TEST chức năng Xem lại");
	frameui.delay();
	var table = pnChild.table("tbpriceTypeTable");
	table.doubleClickRow("Mã BG HktTest1");
	dlMain.buttonClick("Sửa");
	pnChild.fieldSet("txtName", "Tên BG HktTest2");
	pnChild.fieldSet("txtDescription", "Miêu tả BG HktTest2");
	dlMain.buttonClick("Xem lại");
	frameui.delay();

	// TEST SỬA: sửa giá trị ban đầu từ 1 thành 2 và lưu
	frameui.showDialogTest("TEST chức năng Sửa");
	frameui.delay();
	var table = pnChild.table("tbpriceTypeTable");
	table.doubleClickRow("Mã BG HktTest1");
	dlMain.buttonClick("Sửa");
	pnChild.fieldSet("txtName", "Tên BG HktTest2");
	pnChild.fieldSet("txtDescription", "Miêu tả BGHktTest2");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Sửa thành công !");
	frameui.delay();

	// TEST VIẾT LẠI: reset các trường
	frameui.showDialogTest("TEST chức năng Viết lại")
	frameui.delay();
	table.doubleClickRow("Mã BG HktTest1");
	dlMain.buttonClick("Viết lại");

	// TEST THÊM MỚI 1 BẢNG GIÁ SO SÁNH VỚI BẢNG GIÁ BAN ĐẦU
	frameui.showDialogTest("TEST chức năng Thêm mới")
	frameui.delay();
	pnChild.fieldSet("txtType", "Mã BG HktTest3");
	pnChild.fieldSet("txtName", "Tên BG HktTest3");
	pnChild.fieldSet("txtDescription", "Miêu tả BG HktTest3");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Thêm mới thành công !");
	frameui.delay();

	// TEST THAY ĐỔI VỊ TRÍ ƯU TIÊN
	frameui.showDialogTest("TEST chức năng Vị trí");
	frameui.delay();
	dlMain.buttonClick("Viết lại");
	table.selectRow("Mã BG HktTest3");
	dlMain.buttonClickByName("btnUp")
	pnChild.buttonClick("Cập nhật")
	frameui.delay();

	// TEST XÓA
	frameui.showDialogTest("TEST chức năng Xóa");
	frameui.delay();
	table.doubleClickRow("Mã BG HktTest1");
	dlMain.buttonClick("Sửa");
	dlMain.buttonClick("Xóa");
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");
	frameui.showDialogTest("Xóa thành công!");
	frameui.delay();

	// THOÁT KHỎI GIAO DIỆN TEST VÀ XÓA DỮ LIỆU SAU KHI TEST
	frameui
			.showDialogTest("Chương trình TEST Quản lý bảng giá kết thúc!");
	frameui.delay();
	pnChild.deleteDataTest();
	dlMain.buttonClick("Thoát");

}
var robot = new Robot();
robot.add("Quản lý bảng giá", "", CreateQuanLyBangGia);
console.clear();
robot.run();
robot.report();
