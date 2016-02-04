ScriptRunner.require("robotExp.js");

function CreateTienTe(frameui) {
	/**
	 * @ edit: Hạnh: Thêm if vào Test Lỗi
	 */
	frameui.showDialogTest("Chương trình TEST Tiền tệ");
	frameui.delay();

	var buttonMenu = frameui.button("menuRightHeThong");
	buttonMenu.mouseClick();
	var button = frameui.button("TienTe");
	button.mouseClick();
	var dlMain = frameui.dialog("dlTienTe");
	var pnChild = dlMain.panel("TienTe");

	// TEST LƯU NHẬP ĐÚNG DỮ LIỆU CÁC TRƯỜNG
	frameui.showDialogTest("TEST chức năng Lưu");
	frameui.delay();
	pnChild.fieldSet("txtCode", "Mã TT HktTest1");
	pnChild.fieldSet("txtName", "ĐV TT HktTest1");
	pnChild.fieldSet("txtRate", "1");
	pnChild.fieldSet("txtDescription", "Miêu tả TT HktTest1");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Lưu thành công");
	frameui.delay();
	
	// BỎ TRỐNG CÁC TRƯỜNG VÀ LƯU
	if(frameui.showDialogTest("Lỗi chưa nhập dữ liệu các trường")){
		pnChild.fieldSet("txtCode", "");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
	
	// BỎ TRỐNG TRƯỜNG MÃ VÀ LƯU
	if(frameui.showDialogTest("Lỗi chưa nhập Mã TT")){
		frameui.delay();
		pnChild.fieldSet("txtCode", "");
		pnChild.fieldSet("txtName", "ĐV TT HktTest1");
		pnChild.fieldSet("txtRate", "1");
		pnChild.fieldSet("txtDescription", "Miêu tả TT HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
	
	// NHẬP TRÙNG MÃ VÀ LƯU
	if(frameui.showDialogTest("Lỗi nhập trùng Mã TT")){	
		frameui.delay();
		pnChild.fieldSet("txtCode", "Mã TT HktTest1");
		pnChild.fieldSet("txtName", "ĐV TT HktTest2");
		pnChild.fieldSet("txtRate", "2");
		pnChild.fieldSet("txtDescription", "Miêu tả TT HktTest2");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
	
	// BỎ TRỐNG TRƯỜNG ĐV TIỀN VÀ LƯU
	if(frameui.showDialogTest("Lỗi chưa nhập ĐV tiền")){
		frameui.delay();
		pnChild.fieldSet("txtCode", "Mã TT HktTest2");
		pnChild.fieldSet("txtName", "");
		pnChild.fieldSet("txtRate", "2");
		pnChild.fieldSet("txtDescription", "Miêu tả TT HktTest2");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
	
	// BỎ TRỐNG TRƯỜNG TỶ LỆ VÀ LƯU
	if(frameui.showDialogTest("Lỗi chưa nhập Tỷ lệ tiền")){
		frameui.delay();
		pnChild.fieldSet("txtCode", "Mã TT HktTest2");
		pnChild.fieldSet("txtName", "ĐV TT HktTest2");
		pnChild.fieldSet("txtRate", "");
		pnChild.fieldSet("txtDescription", "Miêu tả TT HktTest2");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
	
	// NHẬP SAI ĐỊNH DẠNG TỶ LỆ TIỀN VÀ LƯU
	if(frameui.showDialogTest("Lỗi nhập định dạng Tỷ lệ tiền")){
		frameui.delay();
		pnChild.fieldSet("txtCode", "Mã TT HktTest2");
		pnChild.fieldSet("txtName", "ĐV TT HktTest2");
		pnChild.fieldSet("txtRate", "HktTest1");
		pnChild.fieldSet("txtDescription", "Miêu tả TT HktTest2");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
	

	// TEST XEM LẠI: sửa giá trị ban đầu từ 1 thành 2 và xem lại
	frameui.showDialogTest("TEST chức năng Xem lại");
	frameui.delay();
	var table = pnChild.table("tbcurrrencyTable");
	table.doubleClickRow("Mã TT HktTest1");
	dlMain.buttonClick("Sửa");
	pnChild.fieldSet("txtName", "ĐV TT HktTest2");
	pnChild.fieldSet("txtRate", "2");
	pnChild.fieldSet("txtDescription", "Miêu tả TT HktTest2");
	dlMain.buttonClick("Xem lại");
	frameui.delay();
	
	// TEST SỬA: sửa giá trị ban đầu từ 1 thành 2 và lưu
	frameui.showDialogTest("TEST chức năng Sửa");
	frameui.delay();
	var table = pnChild.table("tbcurrrencyTable");
	table.doubleClickRow("Mã TT HktTest1");
	dlMain.buttonClick("Sửa");
	pnChild.fieldSet("txtName", "ĐV TT HktTest2");
	pnChild.fieldSet("txtRate", "2");
	pnChild.fieldSet("txtDescription", "Miêu tả TT HktTest2");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Sửa thành công");
	frameui.delay();
	
	// TEST VIẾT LẠI: reset các trường
	frameui.showDialogTest("TEST chức năng Viết lại")
	frameui.delay();
	table.doubleClickRow("Mã TT HktTest1");
	dlMain.buttonClick("Viết lại");
	frameui.delay();
	
	// TEST THÊM MỚI 1 TT SO SÁNH VỚI TT BAN ĐẦU
	frameui.showDialogTest("TEST chức năng Thêm mới")
	frameui.delay();
	pnChild.fieldSet("txtCode", "Mã TT HktTest3");
	pnChild.fieldSet("txtName", "ĐV TT HktTest3");
	pnChild.fieldSet("txtRate", "3");
	pnChild.fieldSet("txtDescription", "Miêu tả TT HktTest3");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Thêm mới thành công");
	frameui.delay();

	// TEST THAY ĐỔI VỊ TRÍ ƯU TIÊN
	frameui.showDialogTest("TEST chức năng Vị trí");
	frameui.delay();
	table.selectRow("Mã TT HktTest3");
	dlMain.buttonClickByName("btnUp")
	pnChild.buttonClick("Cập nhật")

	// TEST XÓA
	frameui.showDialogTest("TEST chức năng Xóa");
	frameui.delay();
	table.doubleClickRow("Mã TT HktTest1");
	dlMain.buttonClick("Sửa");
	dlMain.buttonClick("Xóa");
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");
	frameui.delay();

	// Thoat. oke Test finish!
	frameui.showDialogTest("Chương trình TEST Tiền tệ kết thúc!");
	frameui.delay();
	pnChild.deleteDataTest();
	dlMain.buttonClick("Thoát");

}
var robot = new Robot();
robot.add("TEST TienTe ", "", CreateTienTe);
console.clear();
robot.run();
robot.report();
