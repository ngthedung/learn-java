//Duy Khanh
ScriptRunner.require("robotExp.js");

function CreateDuan(frameui) {

	frameui.showDialogTest("Chương trình TEST giao diện Dự án");
	frameui.delay();
	// Click vào menu Hệ thống
	var buttonMenu = frameui.button("menuRightKhuVucBanHang");
	buttonMenu.mouseClick();

	// Click vào button du an
	var button = frameui.button("Duan");
	button.mouseClick();

	var dlMain = frameui.dialog("dlThemMoiDuAn");
	var pnChild = dlMain.panel("Duan");
	
	/*
	 * KỊCH BẢN TEST ĐẦU
	 */
	// 1.Nhập đúng dữ liệu đúng cho 1 bản ghi
	frameui
			.showDialogTest("Test chức năng [Lưu] Khi Dữ Liệu các trường nhập đúng !");
	frameui.delay();
	pnChild.fieldSet("txtNameProject", "Tên dự án HktTest1");
	pnChild.fieldSet("txtCodeProject", "Mã dự án HktTest1");
	var comboboxDepartment = pnChild.comboBox("cbDepartments");
	comboboxDepartment.selectItem("Mã phòng ban HktTest1");
	var comboboxStatus = pnChild.comboBox("cbStatus");
	comboboxStatus.selectItem("Đang hoạt động");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest1");
	dlMain.buttonClick("Lưu");
	frameui.delay();
	// 2.thêm 1 dự án đủ các dữ liệu và có dự án cha
	frameui
			.showDialogTest("[Lưu] khi dự án có dự án cha !");
	frameui.delay();
	dlMain.buttonClick("Viết lại");
	pnChild.fieldSet("txtNameProject", "Tên dự án HktTest2");
	pnChild.fieldSet("txtCodeProject", "Mã dự án HktTest2");
	var comboboxDepartment = pnChild.comboBox("cbDepartments");
	comboboxDepartment.selectItem("Mã phòng ban HktTest1");
	var comboboxParentProject = pnChild.comboBox("cbParentProject");
	comboboxParentProject.selectItem("Tên dự án HktTest1");
	var comboboxStatus = pnChild.comboBox("cbStatus");
	comboboxStatus.selectItem("Đang hoạt động");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest2");
	dlMain.buttonClick("Lưu");
	frameui.delay();

	//3.Thêm 1 dự án để test chuyển
	frameui.showDialogTest("[Lưu] Thêm 1 bản ghi để kiểm tra chức năng chuyển dự án");
	frameui.delay();
	dlMain.buttonClick("Viết lại");

	pnChild.fieldSet("txtNameProject", "Tên dự án HktTest5");
	pnChild.fieldSet("txtCodeProject", "Mã dự án HktTest5");
	var comboboxDepartment = pnChild.comboBox("cbDepartments");
	comboboxDepartment.selectItem("Mã phòng ban HktTest1");
	var comboboxStatus = pnChild.comboBox("cbStatus");
	comboboxStatus.selectItem("Đang hoạt động");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest5");
	dlMain.buttonClick("Lưu");
	frameui.delay();

	// 4.Thêm 1 dự án có dự án thiếu mã.
	if(frameui.showDialogTest("Test chức năng [Lưu] Khi trường mã dự án lỗi nhập thiếu !")){

		frameui.delay();
		dlMain.buttonClick("Viết lại");
		pnChild.fieldSet("txtNameProject", "Tên dự án HktTest3");
		pnChild.fieldSet("txtCodeProject", "");

		var comboboxDepartment = pnChild.comboBox("cbDepartments");
		comboboxDepartment.selectItem("Mã phòng ban HktTest1");

		var comboboxStatus = pnChild.comboBox("cbStatus");
		comboboxStatus.selectItem("Đang hoạt động");

		pnChild.fieldSet("txtDescription", "Miêu tả HktTest1");

		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
	
    //5.Thêm 1 dự án trùng mã
	if(frameui.showDialogTest("[Lưu] Khi trường mã dự án lỗi nhập trùng !")){
		frameui.delay();
		dlMain.buttonClick("Viết lại");
		pnChild.fieldSet("txtNameProject", "Tên dự án HktTest4");
		pnChild.fieldSet("txtCodeProject", "Mã dự án HktTest1");

		var comboboxDepartment = pnChild.comboBox("cbDepartments");
		comboboxDepartment.selectItem("Mã phòng ban HktTest1");

		var comboboxStatus = pnChild.comboBox("cbStatus");
		comboboxStatus.selectItem("Đang hoạt động");

		pnChild.fieldSet("txtDescription", "Miêu tả HktTest4");

		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
	
	// 6.Thêm 1 dự án thiểu tên.
	if(frameui.showDialogTest("[Lưu] Khi trường tên dự án lỗi nhập thiếu !")){
		frameui.delay();
		pnChild.fieldSet("txtNameProject", "");
		pnChild.fieldSet("txtCodeProject", "Mã dự án HktTest4");

		var comboboxDepartment = pnChild.comboBox("cbDepartments");
		comboboxDepartment.selectItem("Mã phòng ban HktTest1");

		var comboboxStatus = pnChild.comboBox("cbStatus");
		comboboxStatus.selectItem("Đang hoạt động");

		pnChild.fieldSet("txtDescription", "Miêu tả HktTest4");

		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
	
	
	//Thoat. oke Test finish!
	frameui.showDialogTest("Chương trình chạy thử Dự án thành công !");
	frameui.delay();
	dlMain.buttonClick("Thoát");

}

function CreateDanhSachDuAn(frameui) {

	frameui.showDialogTest("[Xem lại] Chương trình chạy thử chức năng");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightKhuVucBanHang");
	buttonMenu.mouseClick();
	var button = frameui.button("DanhSachDuAn");
	button.mouseClick();
	var dlMain = frameui.dialog("dlDanhSachDuAn");
	var tableQuanLyDuAn = dlMain.table("tbDanhSachDuAn");

	// Click dòng vừa thêm có giá trị code = Mã HktTest1
	frameui.showDialogTest("Chi tiết dự án HktTest1");
	frameui.delay();
	tableQuanLyDuAn.doubleClickRow("Mã dự án HktTest1");
	frameui.delay();

	var dlChild = frameui.dialog("dlThemMoiDuAn");
	var pnChild = dlChild.panel("Duan");

	// Chỉnh sửa các giá trị thành HktTest2
	frameui.showDialogTest("Chỉnh sửa các giá trị thành HktTest2");
	frameui.delay();
	dlChild.buttonClick("Sửa");
	frameui.delay();

	pnChild.fieldSet("txtNameProject", "Tên dự án HktTest2");
	var comboboxDepartment = pnChild.comboBox("cbDepartments");
	comboboxDepartment.selectItem("Mã phòng ban HktTest1");
	var comboboxStatus = pnChild.comboBox("cbStatus");
	comboboxStatus.selectItem("Ngừng hoạt động");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest2");

	// Ấn nút [Xem lại] trở về các giá trị gốc
	frameui.showDialogTest("[Xem lại] để trở về các giá trị ban đầu");
	frameui.delay();
	dlChild.buttonClick("Xem lại");
	frameui.delay();

	// 1.Sửa lại các thông tin và lưu lại
	frameui.showDialogTest("[Sửa] Dự án HktTest1");
	frameui.delay();
	dlChild.buttonClick("Sửa");
	pnChild.fieldSet("txtNameProject", "Tên dự án HktTest6");
	var comboboxDepartment = pnChild.comboBox("cbDepartments");
	comboboxDepartment.selectItem("Mã phòng ban HktTest1");
	var comboboxStatus = pnChild.comboBox("cbStatus");
	comboboxStatus.selectItem("Ngừng hoạt động");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest2");
	dlChild.buttonClick("Lưu");
	frameui.showDialogTest("[Lưu sửa] thành công dự án HktTest6");
	frameui.delay();

	// 2.Click viết mới và tạo sự án mới HktTest7
	dlChild.buttonClick("Viết lại");
	pnChild.fieldSet("txtNameProject", "Tên dự án HktTest7");
	pnChild.fieldSet("txtCodeProject", "Mã dự án HktTest7");
	var comboboxDepartment = pnChild.comboBox("cbDepartments");
	comboboxDepartment.selectItem("Mã phòng ban HktTest1");
	var comboboxStatus = pnChild.comboBox("cbStatus");
	comboboxStatus.selectItem("Đang hoạt động");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest7");
	dlChild.buttonClick("Lưu");
	frameui.showDialogTest("Thêm xong dự án HktTest7");
	frameui.delay();

	// dlChild.buttonClick("Thoát");
	// Thêm 50 bản ghi vào danh sách TEST chuyển trang
	frameui.showDialogTest("Thêm 50 bản ghi để [TEST] chuyển trang");
	frameui.delay();
	pnChild.createDataTest();
	dlChild.buttonClick("Thoát");

	// Test chuyển trang
	frameui.showDialogTest("Sang trang 2");
	frameui.delay();
	dlMain.buttonClick("2");
	frameui.delay();
	frameui.showDialogTest("Trở về trang đầu tiên");
	frameui.delay();
	dlMain.buttonClick("<<");
	frameui.delay();

	// 3.Test tìm kiếm
	frameui.showDialogTest("[TEST] tìm kiếm -> tìm theo mã dự án");
	frameui.delay();
	var comboBox = dlMain.comboBox("cbSearch");
	comboBox.selectItem("Mã dự án");
	dlMain.fieldSet("txtSearch", "Mã dự án HktTest7");
	frameui.delay();
	frameui.delay();
	
	dlMain.fieldSet("txtSearch", "");
	frameui.delay();
	frameui.delay();
	// 4.Xóa dự án
	frameui.showDialogTest("[XÓA] dự án không có dự án con");
	frameui.delay();
	tableQuanLyDuAn.doubleClickRow("Mã dự án HktTest7");

	var dlChild = frameui.dialog("dlThemMoiDuAn");
	dlChild.buttonClick("Sửa");
	dlChild.buttonClick("Xóa");
	var dlDelete = frameui.dialog("dlXoa");
	dlDelete.buttonClick("Đồng ý");
	frameui.showDialogTest("Xóa thành công dự án HktTest7");
	frameui.delay();

	frameui.showDialogTest("[XÓA] dự án có dự án con");
	frameui.delay();
	tableQuanLyDuAn.doubleClickRow("Mã dự án HktTest1");
	var dlChild = frameui.dialog("dlThemMoiDuAn");
	dlChild.buttonClick("Sửa");
	dlChild.buttonClick("Xóa");
	frameui.delay();

	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");

	var dlRemove = frameui.dialog("dlXoaDuAn");
	var pnRemove = dlRemove.panel("XoaDuAn");
	frameui.showDialogTest("Chuyển tất cả dự án sang KV HktTest5");
	frameui.delay();
	dlRemove.buttonClick(">>");
	var comboBox = dlRemove.comboBox("cbDelete");
	comboBox.selectItem("Tên dự án HktTest5");
	dlRemove.buttonClick("Đồng ý");

	frameui.delay();
	frameui.showDialogTest("Hoàn thành test giao diện dự án");
	frameui.delay();
	var dlMain = frameui.dialog("dlDanhSachDuAn");
	dlMain.buttonClickByName("btnExit");

	pnChild.deleteDataTest();

}

var robot = new Robot();
robot.add("TEST dự án ", "", CreateDuan);
robot.add("TEST DanhSachDuAn ", "", CreateDanhSachDuAn);
console.clear();
robot.run();
robot.report();
