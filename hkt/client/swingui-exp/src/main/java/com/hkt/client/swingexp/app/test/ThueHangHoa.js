ScriptRunner.require("robotExp.js");

function CreateThueHangHoa(frameui) {
	/**
	 * @ author: Hạnh
	 */
	frameui.showDialogTest("Chương trình TEST Thuế hàng hóa");
	frameui.delay();
	
	//KỊCH BẢN CHUẨN
	var buttonMenu = frameui.button("menuRightBanHang");
	buttonMenu.mouseClick();
	var button = frameui.button("ThueHangHoa");
	button.mouseClick();
	var dlMain = frameui.dialog("dlThueHangHoa");
	var pnChild = dlMain.panel("ThueHangHoa");
	
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Thiếu các thông tin quan trọng");
	frameui.delay();
	dlMain.buttonClick("Viết lại");
	
	frameui.showDialogTest("Các bước thông tin chuẩn cần điền");
	frameui.delay();
	pnChild.fieldSet("txtCode", "Mã HktTest1");
	pnChild.fieldSet("txtName", "Thuế HktTest1");
	pnChild.fieldSet("txtPercent", "1");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest1");
	dlMain.buttonClick("Lưu");
	
	frameui.showDialogTest("Chương trình chạy thử chức năng xem và xem lại");
	frameui.delay();
	var table = pnChild.table("tbtaxTable");
	table.doubleClickRow("Thuế HktTest1");
	dlMain.buttonClick("Sửa");
	pnChild.fieldSet("txtName", "Thuế HktTest2");
	pnChild.fieldSet("txtPercent", "2");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest2");
	dlMain.buttonClick("Xem lại");
	
	frameui.showDialogTest("Chương trình chạy thử chức năng chỉnh sửa Thuế HktTest1 thành Thuế HktTest2");
	frameui.delay();
	dlMain.buttonClick("Sửa");
	pnChild.fieldSet("txtName", "Thuế HktTest2");
	pnChild.fieldSet("txtPercent", "2");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest2");
	dlMain.buttonClick("Lưu");
	
	frameui.showDialogTest("Chương trình Thêm mới một thuế hàng hóa");
	frameui.delay();
	dlMain.buttonClick("Viết lại");
	pnChild.fieldSet("txtCode", "Mã HktTest3");
	pnChild.fieldSet("txtName", "Thuế HktTest3");
	pnChild.fieldSet("txtPercent", "3");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest3");
	dlMain.buttonClick("Lưu");
	
	frameui.showDialogTest("Chương trình chạy thử chức năng xóa bản ghi Thuế HktTest3 vừa tạo");
	frameui.delay();
	table.doubleClickRow("Thuế HktTest3");
	dlMain.buttonClick("Sửa");
	dlMain.buttonClick("Xóa");
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");
	
	frameui.showDialogTest("Chương trình chạy thử chức năng vị trí");
	frameui.delay();
	var dlMain = frameui.dialog("dlThueHangHoa");
	var pnChild = dlMain.panel("ThueHangHoa");
	pnChild.createDataTest();
	dlMain.buttonClick("Thoát");
	var button = frameui.button("ThueHangHoa");
	button.mouseClick();
	var dlMain = frameui.dialog("dlThueHangHoa");
	var pnChild = dlMain.panel("ThueHangHoa");
	var table = pnChild.table("tbtaxTable");
	table.doubleClickRow("Thuế HktTest25");
	dlMain.buttonClickByName("btnTop")
	pnChild.buttonClick("Cập nhật")
	table.doubleClickRow("Thuế HktTest2");
	dlMain.buttonClickByName("btnBottom")
	pnChild.buttonClick("Cập nhật")
	frameui.showDialogTest("HktTest25 đã lên đầu tiên và HktTest2 đã xuống cuối cùng");
	frameui.delay();
	pnChild.deleteDataTest();
	dlMain.buttonClick("Thoát");
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//
//	// TEST LƯU NHẬP ĐÚNG DỮ LIỆU CÁC TRƯỜNG
//	frameui.showDialogTest("TEST chức năng Lưu");
//	frameui.delay();
//	pnChild.fieldSet("txtCode", "Mã thuế HktTest1");
//	pnChild.fieldSet("txtName", "Tên thuế HktTest1");
//	pnChild.fieldSet("txtPercent", "1");
//	pnChild.fieldSet("txtDescription", "Miêu tả Thuế HktTest1");
//	dlMain.buttonClick("Lưu");
//	frameui.showDialogTest("Lưu thành công !");
//	frameui.delay();
//
//	// BỎ TRỐNG CÁC TRƯỜNG VÀ LƯU
//	if (frameui
//			.showDialogTest("Lỗi chưa nhập dữ liệu các trường")) {
//		frameui.delay();
//		pnChild.fieldSet("txtCode", "");
//		dlMain.buttonClick("Lưu");
//		frameui.delay();
//	}
//
//	// BỎ TRỐNG TRƯỜNG MÃ VÀ LƯU
//	if (frameui
//			.showDialogTest("Lỗi chưa nhập Mã thuế")) {
//		frameui.delay()
//		pnChild.fieldSet("txtCode", "");
//		pnChild.fieldSet("txtName", "Tên thuế HktTest1");
//		pnChild.fieldSet("txtPercent", "1");
//		pnChild.fieldSet("txtDescription", "Miêu tả Thuế HktTest1");
//		dlMain.buttonClick("Lưu");
//		frameui.delay();
//	}
//
//	// NHẬP TRÙNG MÃ VÀ LƯU
//	if (frameui
//			.showDialogTest("Lỗi nhập trùng Mã thuế")) {
//		frameui.delay();
//		pnChild.fieldSet("txtCode", "Mã thuế HktTest1");
//		pnChild.fieldSet("txtName", "Tên thuế HktTest2");
//		pnChild.fieldSet("txtPercent", "2");
//		pnChild.fieldSet("txtDescription", "Miêu tả Thuế HktTest2");
//		dlMain.buttonClick("Lưu");
//		frameui.showDialogTest("Mã bị trùng");
//		frameui.delay();
//	}
//
//	// BỎ TRỐNG TRƯỜNG TÊN VÀ LƯU
//	if (frameui
//			.showDialogTest("Lỗi chưa nhập Tên Thuế")) {
//		frameui.delay();
//		pnChild.fieldSet("txtCode", "Mã thuế HktTest1");
//		pnChild.fieldSet("txtName", "");
//		pnChild.fieldSet("txtPercent", "1");
//		pnChild.fieldSet("txtDescription", "Miêu tả Thuế HktTest1");
//		dlMain.buttonClick("Lưu");
//		frameui.delay();
//	}
//
//	// BỎ TRỐNG TRƯỜNG TỶ TỆ VÀ LƯU
//	if (frameui
//			.showDialogTest("Lỗi chưa nhập Tỷ lệ thuế")) {
//		frameui.delay();
//		pnChild.fieldSet("txtCode", "Mã thuế HktTest1");
//		pnChild.fieldSet("txtName", "Tên thuế HktTest1");
//		pnChild.fieldSet("0txtPercent", "");
//		pnChild.fieldSet("txtDescription", "Miêu tả Thuế HktTest1");
//		dlMain.buttonClick("Lưu");
//		frameui.delay();
//	}
//
//	// NHẬP SAI ĐỊNH DẠNG TỶ LỆ VÀ LƯU
//	if (frameui
//			.showDialogTest("Lỗi nhập sai định dạng Tỷ lệ thuế")) {
//		frameui.delay();
//		pnChild.fieldSet("txtCode", "Mã thuế HktTest1");
//		pnChild.fieldSet("txtName", "Tên thuế HktTest1");
//		pnChild.fieldSet("txtPercent", "Tỷ lệ");
//		pnChild.fieldSet("txtDescription", "Miêu tả Thuế HktTest1");
//		dlMain.buttonClick("Lưu");
//		frameui.delay();
//	}
//
//	// TEST XEM LẠI: sửa giá trị ban đầu từ 1 thành 2 và xem lại
//	frameui.showDialogTest("TEST chức năng Xem lại");
//	frameui.delay();
//	var table = pnChild.table("tbtaxTable");
//	table.doubleClickRow("Mã thuế HktTest1");
//	dlMain.buttonClick("Sửa");
//	frameui.delay();
//	pnChild.fieldSet("txtName", "Tên thuế HktTest2");
//	pnChild.fieldSet("txtDescription", "Miêu tả Thuế HktTest2");
//	dlMain.buttonClick("Xem lại");
//	frameui.delay();
//
//	// TEST SỬA: sửa giá trị ban đầu từ 1 thành 2 và lưu
//	frameui.showDialogTest("TEST chức năng Sửa");
//	frameui.delay();
//	var table = pnChild.table("tbtaxTable");
//	table.doubleClickRow("Mã thuế HktTest1");
//	dlMain.buttonClick("Sửa");
//	pnChild.fieldSet("txtName", "Tên thuế HktTest2");
//	pnChild.fieldSet("txtPercent", "2");
//	pnChild.fieldSet("txtDescription", "Miêu tả Thuế HktTest2");
//	dlMain.buttonClick("Lưu");
//	frameui.showDialogTest("Sửa thành công");
//	frameui.delay();
//
//	// TEST VIẾT LẠI: reset các trường
//	frameui.showDialogTest("TEST chức năng Viết lại")
//	frameui.delay();
//	table.doubleClickRow("Mã thuế HktTest1");
//	dlMain.buttonClick("Viết lại");
//	frameui.delay();
//
//	// TEST THÊM MỚI 1 THUẾ HÀNG HÓA SO SÁNH VỚI THUẾ HÀNG HÓA BAN ĐẦU
//	frameui.showDialogTest("TEST chức năng Thêm mới")
//	frameui.delay();
//	pnChild.fieldSet("txtCode", "Mã thuế HktTest3");
//	pnChild.fieldSet("txtName", "Tên thuế HktTest3");
//	pnChild.fieldSet("txtPercent", "3");
//	pnChild.fieldSet("txtDescription", "Miêu tả Thuế HktTest3");
//	dlMain.buttonClick("Lưu");
//	frameui.showDialogTest("Thêm mới thành công !");
//	frameui.delay();
//
//	// TEST THAY ĐỔI VỊ TRÍ ƯU TIÊN
//	frameui.showDialogTest("TEST chức năng Vị trí");
//	frameui.delay();
//	dlMain.buttonClick("Viết lại");
//	table.selectRow("Mã thuế HktTest3");
//	dlMain.buttonClickByName("btnUp")
//	pnChild.buttonClick("Cập nhật")
//	frameui.delay();
//
//	// TEST XÓA
//	frameui.showDialogTest("TEST chức năng Xóa");
//	frameui.delay();
//	table.doubleClickRow("Mã thuế HktTest1");
//	dlMain.buttonClick("Sửa");
//	dlMain.buttonClick("Xóa");
//	var dlDelete = frameui.dialog("dlXoa");
//	var pnDelete = dlDelete.panel("Xoa");
//	dlDelete.buttonClick("Đồng ý");
//	frameui.showDialogTest("Xóa thành công!");
//	frameui.delay();
//
//	// THOÁT KHỎI GIAO DIỆN TEST VÀ XÓA DỮ LIỆU SAU KHI TEST
//	frameui
//			.showDialogTest("Chương trình TEST giao diện Thuế hàng hóa kết thúc!");
//	frameui.delay();
//	pnChild.deleteDataTest();
//	dlMain.buttonClick("Thoát");
}

var robot = new Robot();
robot.add("Tạo thuế hàng hóa", "", CreateThueHangHoa);
console.clear();
robot.run();
robot.report();
