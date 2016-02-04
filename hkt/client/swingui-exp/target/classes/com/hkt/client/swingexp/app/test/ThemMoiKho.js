ScriptRunner.require("robotExp.js");
function CreateKho(frameui) {
	/**
	 * @ author: Hạnh
	 */
	frameui.showDialogTest("Chương trình TEST Thêm mới kho");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightKhoHangHoa");
	buttonMenu.mouseClick();
	var button = frameui.button("ThemMoiKho");
	button.mouseClick()
	var dlMain = frameui.dialog("dlThemMoiKho");
	var pnChild = dlMain.panel("ThemMoiKho");
	
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Thiếu các thông tin quan trọng");
	frameui.delay();
	dlMain.buttonClick("Viết lại");
	
	frameui.showDialogTest("Các bước thông tin chuẩn cần điền");
	frameui.delay();
	pnChild.fieldSet("txtCode", "Mã HktTest1");
	pnChild.fieldSet("txtLabel", "Kho HktTest1");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest1");
	dlMain.buttonClick("Lưu");
	
	frameui.showDialogTest("Chương trình chạy thử chức năng xem và xem lại");
	frameui.delay();
	var table = pnChild.table("tbWarehouse");
	table.doubleClickRow("Mã HktTest1");
	dlMain.buttonClick("Sửa");
	pnChild.fieldSet("txtLabel", "Kho HktTest2");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest2");
	dlMain.buttonClick("Xem lại");
	
	frameui.showDialogTest("Chương trình chạy thử chức năng chỉnh sửa Kho HktTest1 thành Kho HktTest2");
	frameui.delay();
	dlMain.buttonClick("Sửa");
	pnChild.fieldSet("txtLabel", "Kho HktTest2");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest2");
	dlMain.buttonClick("Lưu");
	
	frameui.showDialogTest("Chương trình Thêm mới một kho");
	frameui.delay();
	dlMain.buttonClick("Viết lại");
	pnChild.fieldSet("txtCode", "Mã HktTest3");
	pnChild.fieldSet("txtLabel", "Kho HktTest3");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest3");
	dlMain.buttonClick("Lưu");
	
	frameui.showDialogTest("Chương trình chạy thử chức năng xóa bản ghi Thuế HktTest3 vừa tạo");
	frameui.delay();
	table.doubleClickRow("Kho HktTest3");
	dlMain.buttonClick("Sửa");
	dlMain.buttonClick("Xóa");
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");
	
	frameui.showDialogTest("Chương trình chạy thử các kịch bản đặc biệt: test xóa ẩn");
	frameui.delay();
	pnChild.fieldSet("txtCode", "Mã HktTest3");
	pnChild.fieldSet("txtLabel", "Kho HktTest3");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest3");
	dlMain.buttonClick("Lưu");
	var dlDelete = frameui.dialog("dlRecybin");
	var pnDelete = dlDelete.panel("Recybin");
	dlDelete.buttonClick("Thoát");
	frameui.showDialogTest("Trùng mã HktTest3 vừa xóa");
	frameui.delay();
	
	frameui.showDialogTest("Chương trình chạy thử các kịch bản đặc biệt: test trùng mã");
	frameui.delay();
	dlMain.buttonClick("Viết lại");
	pnChild.fieldSet("txtCode", "Mã HktTest1");
	pnChild.fieldSet("txtLabel", "Kho HktTest1");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest1");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Trùng mã HktTest1 đã có");
	frameui.delay();
	
	frameui.showDialogTest("Chương trình chạy thử các kịch bản đặc biệt: test để trống các trường");
	frameui.delay();
	dlMain.buttonClick("Viết lại");
	pnChild.fieldSet("txtCode", "");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Nhập thiếu mã kho");
	frameui.delay();
	pnChild.fieldSet("txtCode", "Mã HktTest4");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Nhập thiếu tên kho");
	frameui.delay();
	pnChild.fieldSet("txtLabel", "Kho HktTest4");
	dlMain.buttonClick("Lưu");
	pnChild.deleteDataTest();
	
	frameui.showDialogTest("Chương trình chạy thử Tạo kho hàng đã hoàn thành!");
	frameui.delay();
	frameui.showDialogTest("Chúc quý khách sử dụng thành công phần mềm!");
	frameui.delay();
	dlMain.buttonClick("Thoát");
	
	
	
	
	

//	// TEST LƯU NHẬP ĐÚNG DỮ LIỆU CÁC TRƯỜNG
//	frameui.showDialogTest("TEST chức năng Lưu");
//	frameui.delay();
//	pnChild.fieldSet("txtCode", "Mã Kho HktTest1");
//	pnChild.fieldSet("txtLabel", "Kho HktTest1");
//	pnChild.fieldSet("txtDescription", "Miêu tả HktTest1");
//	dlMain.buttonClick("Lưu");
//	frameui.showDialogTest("Lưu thành công");
//	frameui.delay();
//
//	// BỎ TRỐNG CÁC TRƯỜNG VÀ LƯU
//	if (frameui.showDialogTest("Lỗi chưa nhập dữ liệu các trường")) {
//		pnChild.fieldSet("txtCode", "");
//		dlMain.buttonClick("Lưu");
//		frameui.delay();
//	}
//
//	// BỎ TRỐNG TRƯỜNG MÃ VÀ LƯU
//	if (frameui.showDialogTest("Lỗi chưa nhập Mã Kho")) {
//		frameui.delay();
//		pnChild.fieldSet("txtCode", "");
//		pnChild.fieldSet("txtLabel", "Kho HktTest2");
//		pnChild.fieldSet("txtDescription", "Miêu tả HktTest2");
//		dlMain.buttonClick("Lưu");
//	}
//
//	// NHẬP TRÙNG MÃ VÀ LƯU
//	if (frameui.showDialogTest("Lỗi nhập trùng Mã Kho")) {
//		frameui.delay();
//		pnChild.fieldSet("txtCode", "Mã Kho HktTest1");
//		pnChild.fieldSet("txtLabel", "Kho HktTest2");
//		pnChild.fieldSet("txtDescription", "Miêu tả HktTest2");
//		dlMain.buttonClick("Lưu");
//	}
//	
//	// BỎ TRỐNG TRƯỜNG TÊN  VÀ LƯU
//	if (frameui.showDialogTest("Lỗi chưa nhập Tên Kho")) {
//		frameui.delay();
//		pnChild.fieldSet("txtCode", "Mã Kho HktTest2");
//		pnChild.fieldSet("txtLabel", "");
//		pnChild.fieldSet("txtDescription", "Miêu tả HktTest2");
//		dlMain.buttonClick("Lưu");
//	}
//
//	// TEST XEM LẠI: sửa giá trị ban đầu từ 1 thành 2 và xem lại
//	frameui.showDialogTest("TEST chức năng Xem lại");
//	frameui.delay();
//	var table = pnChild.table("tbWarehouse");
//	table.doubleClickRow("Mã Kho HktTest1");
//	dlMain.buttonClick("Sửa");
//	pnChild.fieldSet("txtLabel", "Kho HktTest2");
//	pnChild.fieldSet("txtDescription", "Miêu tả HktTest2");
//	dlMain.buttonClick("Xem lại");
//	frameui.delay();
//
//	// TEST SỬA: sửa giá trị ban đầu từ 1 thành 2 và lưu
//	frameui.showDialogTest("TEST chức năng Sửa");
//	frameui.delay();
//	dlMain.buttonClick("Sửa");
//	pnChild.fieldSet("txtLabel", "Kho HktTest2");
//	pnChild.fieldSet("txtDescription", "Miêu tả HktTest2");
//	dlMain.buttonClick("Lưu");
//	frameui.showDialogTest("Sửa thành công");
//	frameui.delay();
//	
//	// TEST VIẾT LẠI: reset các trường
//	frameui.showDialogTest("TEST chức năng Viết lại")
//	frameui.delay();
//	table.doubleClickRow("Mã Kho HktTest1");
//	dlMain.buttonClick("Viết lại");
//	frameui.delay();
//
//	// TEST THÊM MỚI 1 KHO SO SÁNH VỚI KHO BAN ĐẦU
//	frameui.showDialogTest("TEST chức năng Thêm mới")
//	frameui.delay();
//	pnChild.fieldSet("txtCode", "Mã Kho HktTest3");
//	pnChild.fieldSet("txtLabel", "Kho HktTest3");
//	pnChild.fieldSet("txtDescription", "Miêu tả HktTest3");
//	dlMain.buttonClick("Lưu");
//	frameui.showDialogTest("Thêm mới thành công");
//	frameui.delay();
//
//	// TEST XÓA
//	frameui.showDialogTest("TEST chức năng Xóa");
//	frameui.delay();
//	table.doubleClickRow("Mã Kho HktTest1");
//	dlMain.buttonClick("Sửa");
//	dlMain.buttonClick("Xóa");
//	var dlDelete = frameui.dialog("dlXoa");
//	var pnDelete = dlDelete.panel("Xoa");
//	dlDelete.buttonClick("Đồng ý");
//	frameui.showDialogTest("Xóa thành công");
//	frameui.delay();
//
//	// THOÁT KHỎI GIAO DIỆN TEST VÀ XÓA DỮ LIỆU SAU KHI TEST
//	frameui
//			.showDialogTest("Chương trình TEST Thêm mới kho kết thúc!");
//	frameui.delay();
//	pnChild.deleteDataTest();
//	dlMain.buttonClick("Thoát");
}

var robot = new Robot();
robot.add("TEST ThemMoiKho ", "", CreateKho);
console.clear();
robot.run();
robot.report();