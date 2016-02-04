//Edit: Khanhdd thêm if vào các test lỗi
ScriptRunner.require("robotExp.js");
function CreateDiem(frameui) {
	frameui.showDialogTest("Chương trình bắt đầu chạy thử giao diện [Lập điểm nhóm đối tác]");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac");
	buttonMenu.mouseClick();
	var button = frameui.button("LapDiemNhomDoiTac");
	button.mouseClick()

	var dlMain = frameui.dialog("dlLapDiemNhomDoiTac");
	var pnChild = dlMain.panel("LapDiemNhomDoiTac");

	// Kiểm tra validate các trường dữ liệu bằng cách để trống hoặc đánh sai
	// kiểu dữ liệu
	if(frameui.showDialogTest("Lỗi chưa nhập dữ liệu")){
		frameui.showDialogTest("Chạy thử chức năng [Lưu]");
		frameui.delay();
		frameui.delay();
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
	
	if(frameui.showDialogTest("Lỗi chưa chọn Nhóm Đối Tác")){
		frameui.delay();
		pnChild.fieldSet("txtMucDiem", "123");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
	
	if(frameui.showDialogTest("Lỗi chưa nhập Điểm cho Nhóm Đối Tác")){
		frameui.delay();
		var cbNhomDT = pnChild.comboBox("cbNhomDT");
		cbNhomDT.selectItem("NĐT HktTest1");
		pnChild.fieldSet("txtMucDiem", "");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
	
	// Nhập đúng các trường và lưu lại
	var cbNhomDT = pnChild.comboBox("cbNhomDT");
	cbNhomDT.selectItem("NĐT HktTest1");
	pnChild.fieldSet("txtMucDiem", "123");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest1");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("[Lưu] thành công");
	frameui.delay();

	// Chạy thử chức năng xem lại
	frameui.showDialogTest("Chạy thử chức năng [Xem lại]");
	frameui.delay();
	var table = pnChild.table("tbMark");
	table.doubleClickRow("NĐT HktTest1");
	dlMain.buttonClick("Sửa");
	frameui.delay();

	// Sửa lại các giá trị ban đầu từ  1 thành 2
	pnChild.fieldSet("txtMucDiem", "321");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest2");
	dlMain.buttonClick("Xem lại");
	frameui.delay();
	
	// Chạy chức năng sửa
	frameui.showDialogTest("Chạy thử chức năng [Sửa]");
	frameui.delay();
	dlMain.buttonClick("Sửa");
	frameui.delay();
	pnChild.fieldSet("txtMucDiem", "321");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest2");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("[Sửa] thành công");
	frameui.delay();

	// Chạy chức năng reset lại giao diện
	frameui.showDialogTest("Chạy thử chức năng [Viết lại]")
	frameui.delay();
	table.doubleClickRow("NĐT HktTest1");
	frameui.delay();
	dlMain.buttonClick("Viết lại");
	frameui.delay();
	
	// Thêm mới 1 bản ghi sau khi reset lại
	frameui.showDialogTest("[Thêm mới] điểm một Nhóm Đối Tác");	
	frameui.delay();
	var cbNhomDT = pnChild.comboBox("cbNhomDT");
	cbNhomDT.selectItem("NĐT HktTest2");
	pnChild.fieldSet("txtMucDiem", "456");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest2");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("[Thêm mới] thành công");
	frameui.delay();

	// Chạy chức năng xóa
	frameui.showDialogTest("Chạy thử chức năng [Xóa]");
	frameui.delay();
	table.doubleClickRow("NĐT HktTest1");
	frameui.delay();
	dlMain.buttonClick("Sửa");
	dlMain.buttonClick("Xóa");
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");
	frameui.showDialogTest("[Xóa] thành công");
	frameui.delay();
	frameui.showDialogTest("Chương trình TEST giao diện [Lập điểm nhóm đối tác] kết thúc!");
	frameui.delay();
	pnChild.deleteDataTest();
	dlMain.buttonClick("Thoát");

}
var robot = new Robot();
robot.add("TEST LapDiemNhomDoiTac", "", CreateDiem);
console.clear();
robot.run();
robot.report();