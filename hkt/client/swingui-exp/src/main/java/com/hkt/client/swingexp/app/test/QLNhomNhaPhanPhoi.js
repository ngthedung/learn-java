ScriptRunner.require("robotExp.js");

function CreateGroupSupplier(frameui) {
	/**
	 * @ author: Hạnh
	 */
	frameui.showDialogTest("Chương trình TEST Quản lý nhóm NPP");

	var buttonMenu = frameui.button("menuRightKhachHangDoiTac");
	buttonMenu.mouseClick();

	var button = frameui.button("QLNhomNhaPhanPhoi");
	button.mouseClick();

	var dlMain = frameui.dialog("dialogGroupSupplier");
	var pnChild = dlMain.panel("QLNhomNhaPhanPhoi");
	
	// TEST LƯU NHẬP ĐÚNG DỮ LIỆU CÁC TRƯỜNG
	frameui.showDialogTest("TEST chức năng Lưu");
	frameui.delay();
	pnChild.fieldSet("txtName", "Mã NNPP HktTest1");
	pnChild.fieldSet("txtLabel", "Tên NNPP HktTest1");
	pnChild.fieldSet("txtDescription", "Miêu tả NNPP HktTest1");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest(" Lưu thành công !");
	frameui.delay();
	
	// BỎ TRỐNG CÁC TRƯỜNG VÀ LƯU
	if (frameui
			.showDialogTest("Lỗi chưa nhập dữ liệu các trường")) {
		frameui.delay();
		pnChild.fieldSet("txtName", "");
		dlMain.buttonClick("Lưu");
	}
	
	// BỎ TRỐNG TRƯỜNG MÃ VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa nhập Mã NNPP")) {
		frameui.delay();
		pnChild.fieldSet("txtName", "");
		pnChild.fieldSet("txtLabel", "Tên NNPP HktTest2");
		pnChild.fieldSet("txtDescription", "Miêu tả NNPP HktTest2");
		dlMain.buttonClick("Lưu");
	}
	
	// NHẬP TRÙNG MÃ VÀ LƯU
	if (frameui.showDialogTest("Lỗi nhập trùng Mã NNPP")) {
		frameui.delay();
		pnChild.fieldSet("txtName", "Mã NNPP HktTest1");
		pnChild.fieldSet("txtLabel", "Tên NNPP HktTest2");
		pnChild.fieldSet("txtDescription", "Miêu tả NNPP HktTest2");
		dlMain.buttonClick("Lưu");
	}
	
	// BỎ TRỐNG TRƯỜNG TÊN VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa nhập Tên NNPP")) {
		frameui.delay();
		pnChild.fieldSet("txtName", "Mã NNPP HktTest2");
		pnChild.fieldSet("txtLabel", "");
		pnChild.fieldSet("txtDescription", "Miêu tả NNPP HktTest2");
		dlMain.buttonClick("Lưu");
	}
	
	// TEST XEM LẠI: sửa giá trị ban đầu từ 1 thành 2 và xem lại
	frameui.showDialogTest("TEST chức năng Xem lại");
	frameui.delay();
	var table = pnChild.table("accountGroupTable");
	table.doubleClickRow("Mã NNPP HktTest1");
	dlMain.buttonClick("Sửa");
	pnChild.fieldSet("txtLabel", "Tên NNPP HktTest2");
	pnChild.fieldSet("txtDescription", "Miêu tả NNPP HktTest2");
	dlMain.buttonClick("Xem lại");

	// TEST SỬA: sửa giá trị ban đầu từ 1 thành 2 và lưu
	frameui.showDialogTest("TEST chức năng Sửa");
	frameui.delay();
	var table = pnChild.table("accountGroupTable");
	table.doubleClickRow("Mã NNPP HktTest1");
	dlMain.buttonClick("Sửa");
	pnChild.fieldSet("txtLabel", "Tên NNPP HktTest2");
	pnChild.fieldSet("txtDescription", "Miêu tả NNPP HktTest2");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Sửa thành công!");
	frameui.delay();

	// TEST VIẾT LẠI: reset các trường
	frameui.showDialogTest("TEST chức năng Viết lại")
	frameui.delay();
	table.doubleClickRow("Mã NNPP HktTest1");
	dlMain.buttonClick("Viết lại");

	// TEST THÊM MỚI 1 NNPP SO SÁNH VỚI NNPP BAN ĐẦU
	frameui.showDialogTest("TEST chức năng Thêm mới")
	frameui.delay();
	pnChild.fieldSet("txtName", "Mã NNPP HktTest3");
	pnChild.fieldSet("txtLabel", "Tên NNPP HktTest3");
	pnChild.fieldSet("txtDescription", "Miêu tả NNPP HktTest3");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest(" Lưu thành công !");
	frameui.delay();

	// TEST THAY ĐỔI VỊ TRÍ ƯU TIÊN
	frameui.showDialogTest("TEST chức năng Vị trí");
	frameui.delay();
	table.selectRow("Mã NNPP HktTest3");
	dlMain.buttonClickByName("btnUp")
	pnChild.buttonClick("Cập nhật")
	button.mouseClick();

	// TEST XÓA
	frameui.showDialogTest("TEST chức năng Xóa");
	frameui.delay();
	table.doubleClickRow("Mã NNPP HktTest1");
	dlMain.buttonClick("Sửa");
	dlMain.buttonClick("Xóa");
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");
	frameui.showDialogTest("Xóa thành công!");
	frameui.delay();

	// THOÁT KHỎI GIAO DIỆN TEST VÀ XÓA DỮ LIỆU SAU KHI TEST
	frameui
			.showDialogTest("Chương trình TEST Quản lý Nhóm NPP kết thúc!");
	frameui.delay();
	pnChild.deleteDataTest();
	dlMain.buttonClick("Thoát");
}

var robot = new Robot();
robot.add("TEST Quản lý nhóm NPP", "", CreateGroupSupplier);
console.clear();
robot.run();
robot.report();