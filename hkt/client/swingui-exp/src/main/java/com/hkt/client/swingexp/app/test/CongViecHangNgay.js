ScriptRunner.require("robotExp.js");

function CreateCongViecHangNgay(frameui) {
	//Khởi tạo cho các biến 
	var buttonMenu = frameui.button("menuRightNhanSu");
	buttonMenu.mouseClick();
	var button = frameui.button("CongViecHangNgay");
	button.mouseClick();

	var dlMain = frameui.dialog("dlCongViecHangNgay");
	var pnMain = dlMain.panel("CongViecHangNgay");
	
	/*
	 * KỊCH BẢN TEST ĐẦU
	 */
	//Điền 1 thông tin thì click Lưu -> Sau mỗi lần click thông báo lỗi hoặc mất lỗi
	frameui.showDialogTest("KỊCH BẢN TEST ĐẦU");
	/*
	 * KỊCH BẢN TEST CHI TIẾT
	 */
	//Thêm mới hàng hóa HktTest1
	frameui.showDialogTest("[Thêm mới] công việc HktTest1");
	if (frameui.showDialogTest("[LỖI] Không có tên công việc")) {
    frameui.delay();
    dlMain.buttonClick("Lưu");
    frameui.delay();
  }
	frameui.delay();
	pnMain.fieldSet("txtCongViec", "Công việc HktTest1");
	pnMain.fieldSet("txtNoiDung", "Nội dung CV HktTest1");
	pnMain.fieldSet("txtCongTy", "Công ty HktTest1");
	pnMain.fieldSet("txtMieuTa", "Miêu tả CV HktTest1");
	pnMain.fieldSet("txtDoiTac", "Đối tác HktTest1");
	var comboBoxTrangThai = pnMain.comboBox("cbTrangThai");
	comboBoxTrangThai.selectItem("Đang làm");
	var comboBoxNhanVien = pnMain.comboBox("cbNhanVien");
	comboBoxNhanVien.selectItem("HktTest1");
	dlMain.buttonClick("Lưu");
	frameui.delay();
	frameui.showDialogTest("[Thêm mới] thành công công việc HktTest1");
	frameui.delay();
	frameui.showDialogTest("[Thoát] khỏi giao diện tạo công việc hàng ngày");
	frameui.delay();
	dlMain.buttonClick("Thoát");
	//Kết thúc giao diện tạo công việc hàng ngày.	
}

function CreateTheoDoiCongViecHangNgay(frameui) {
	//Khởi tạo các giá trị cần thiết
	frameui.showDialogTest("[Xem lại] Chương trình chạy thử chức năng");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightNhanSu");
	buttonMenu.mouseClick();
	var button = frameui.button("TheoDoiCongViecHangNgay");
	button.mouseClick();
	var dlFilter = frameui.dialog("dlTheoDoiCongViecHangNgay");
	var pnFilter = dlFilter.panel("TheoDoiCongViecHangNgay");
	
	dlFilter.buttonClick("Đồng ý");
	
	var dlMain = frameui.dialog("dlCongViecHangNgay");
	var tableQuanLyHangHoa = dlMain.table("tbCongViecHangNgay");
	
	//Click dòng vừa thêm có giá trị = HktTest1
	frameui.showDialogTest("Chi tiết công việc HktTest1");
	frameui.delay();
	tableQuanLyHangHoa.doubleClickRow("Công việc HktTest1");
	frameui.delay();
	
	var dlChild = frameui.dialog("dlCongViecHangNgay1");
	var pnChild = dlChild.panel("CongViecHangNgay1");
	
	//Chỉnh sửa các giá trị thành HktTest2
	frameui.showDialogTest("Chỉnh sửa các giá trị thành HktTest2");
	frameui.delay();
	dlChild.buttonClick("Sửa");
	frameui.delay();
	pnChild.fieldSet("txtCongViec", "Công việc HktTest2");
	pnChild.fieldSet("txtNoiDung", "Nội dung CV HktTest2");
	pnChild.fieldSet("txtCongTy", "Công ty HktTest2");
	pnChild.fieldSet("txtMieuTa", "Miêu tả CV HktTest2");
	pnChild.fieldSet("txtDoiTac", "Đối tác HktTest2");
	var comboBoxTrangThai = pnChild.comboBox("cbTrangThai");
	comboBoxTrangThai.selectItem("Đang làm");
	var comboBoxNhanVien = pnChild.comboBox("cbNhanVien");
	comboBoxNhanVien.selectItem("HktTest2");
	
	//Ấn nút [Xem lại] trở về các giá trị gốc
	frameui.showDialogTest("[Xem lại] để trở về các giá trị ban đầu");
	frameui.delay();
	dlChild.buttonClick("Xem lại");
	frameui.delay();
	
	//Sửa lại các thông tin và lưu lại
	frameui.showDialogTest("[Sửa] công việc HktTest1");
	frameui.delay();
	dlChild.buttonClick("Sửa");
	pnChild.fieldSet("txtCongViec", "Công việc HktTest2");
	pnChild.fieldSet("txtNoiDung", "Nội dung CV HktTest2");
	pnChild.fieldSet("txtCongTy", "Công ty HktTest2");
	pnChild.fieldSet("txtMieuTa", "Miêu tả CV HktTest2");
	pnChild.fieldSet("txtDoiTac", "Đối tác HktTest2");
	comboBoxTrangThai.selectItem("Đang làm");
	comboBoxNhanVien.selectItem("HktTest2");
	dlChild.buttonClick("Lưu");
	frameui.delay();
	frameui.showDialogTest("[Lưu sửa] thành công công việc HktTest1");
	frameui.delay();
	
	//Click viết mới và tạo công việc HktTest3
	dlChild.buttonClick("Viết lại");
	pnChild.fieldSet("txtCongViec", "Công việc HktTest3");
	pnChild.fieldSet("txtNoiDung", "Nội dung CV HktTest3");
	pnChild.fieldSet("txtCongTy", "Công ty HktTest3");
	pnChild.fieldSet("txtMieuTa", "Miêu tả CV HktTest3");
	pnChild.fieldSet("txtDoiTac", "Đối tác HktTest3");
	comboBoxTrangThai.selectItem("Chưa làm");
	comboBoxNhanVien.selectItem("HktTest3");
	dlChild.buttonClick("Lưu");
	frameui.delay();
	frameui.showDialogTest("Thêm xong công việc HktTest3");
	frameui.delay();
	
	//Thêm 50 bản ghi vào danh sách TEST chuyển trang
	frameui.showDialogTest("Thêm 50 bản ghi để [TEST] chuyển trang");
	frameui.delay();
	pnChild.createDataTest();
	dlChild.buttonClick("Thoát");
	
	//Test chuyển trang
	frameui.showDialogTest("Sang trang 2");
	frameui.delay();
	dlMain.buttonClick("2");
	frameui.delay();
	frameui.showDialogTest("Trở về trang đầu tiên");
	frameui.delay();
	dlMain.buttonClickByName("btnPrevIndex");
	frameui.delay();
	
	//Test tìm kiếm 
	frameui.showDialogTest("[TEST] tìm kiếm -> tìm theo tên công việc");
	frameui.delay();
	var comboBox = dlMain.comboBox("cbSearch");
	comboBox.selectItem("Công việc");
	dlMain.fieldSet("txtSearch", "Công việc HktTest3");
	frameui.delay();
	
	//xóa 1 bản ghi
	frameui.showDialogTest("[Xóa] theo tên công việc");
	frameui.delay();
	tableQuanLyHangHoa.doubleClickRow("Công việc HktTest3");
	var dlChild = frameui.dialog("dlCongViecHangNgay1");
	dlChild.buttonClick("Sửa");
	dlChild.buttonClick("Xóa");
	
	var dlDelete = frameui.dialog("dlXoa");
	dlDelete.buttonClick("Đồng ý");
	
	frameui.delay();
	frameui.delay();
	frameui.showDialogTest("Xóa thành công công việc HktTest3");	
	frameui.delay();
	pnChild.deleteDataTest();
	frameui.delay();
	dlChild.buttonClick("Thoát");
	frameui.delay();
	frameui.showDialogTest("Hoàn thành test giao diện công việc hàng ngày");
	frameui.delay();
	dlMain.buttonClickByName("btnExit");
}

var robot = new Robot();
robot.add("TEST CongViecHangNgay", "", CreateCongViecHangNgay);
robot.add("TEST TheoDoiCongViecHangNgay", "", CreateTheoDoiCongViecHangNgay);
console.clear();
robot.run();
robot.report();
