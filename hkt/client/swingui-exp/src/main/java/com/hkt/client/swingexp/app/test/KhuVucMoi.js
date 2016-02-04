//Edit: Khanhdd thêm if vào các test lỗi
ScriptRunner.require("robotExp.js");

function CreateKhuVucMoi(frameui) {
	//Khởi tạo cho các biến 
	var buttonMenu = frameui.button("menuRightKhuVucBanHang") ;
	buttonMenu.mouseClick();
	var button = frameui.button("KhuVucMoi");
	button.mouseClick();
	
	var dlMain = frameui.dialog("dlKhuVucMoi");
	var pnMain = dlMain.panel("KhuVucMoi");
	
	/*
	 * KỊCH BẢN TEST ĐẦU
	 * 
	 */
	//Điền 1 thông tin thì click Lưu -> Sau mỗi lần click thông báo lỗi hoặc mất lỗi
	frameui.showDialogTest("Chương trình TEST giao dien Khu vực mới");
	frameui.delay();
	frameui.showDialogTest("KỊCH BẢN TEST ĐẦU");
	frameui.delay();
	if(frameui.showDialogTest("Lỗi Không có tên khu vực")){
		frameui.delay();
		pnMain.fieldSet("txtKhuVuc", "");
		pnMain.fieldSet("txtMa", "Mã KV HktTest1");
		pnMain.fieldSet("txtMieuTa", "Miêu tả KV HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
	
	
	
	//thêm 1 vị trí thiếu mã
	
	if(frameui.showDialogTest("Lỗi Không có mã khu vực")){
		
		frameui.delay();
		pnMain.fieldSet("txtKhuVuc", "Tên KV HktTest1");
		pnMain.fieldSet("txtMa", "");
		pnMain.fieldSet("txtMieuTa", "Miêu tả KV HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
	
	
	//Làm mới các ô nhập bằng nút [Viết lại]
	frameui.showDialogTest("[Viết lại] Xóa trắng các ô nhập");
	frameui.delay();
	dlMain.buttonClick("Viết lại");
	frameui.delay();
	
	/*
	 * KỊCH BẢN TEST CHI TIẾT
	 */
	//Thêm mới khu vực HktTest1
	frameui.showDialogTest("[Thêm mới] khu vực HktTest1");
	frameui.delay();
	pnMain.fieldSet("txtKhuVuc", "Tên KV HktTest1");
	pnMain.fieldSet("txtMa", "Mã KV HktTest1");
	pnMain.fieldSet("txtMieuTa", "Miêu tả KV HktTest1");
	dlMain.buttonClick("Lưu");
	frameui.delay();
	frameui.showDialogTest("[Thêm mới] thành công KV HktTest1");
	frameui.delay();
	frameui.showDialogTest("[Thoát] khỏi giao diện thêm mới khu vực");
	frameui.delay();
	dlMain.buttonClick("Thoát");
	//Kết thúc giao diện thêm mới khu vực.
}

function CreateDanhSachKhuvuc(frameui) {
	//Khởi tạo các giá trị cần thiết
	frameui.showDialogTest("[Xem lại] Chương trình chạy thử chức năng");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightKhuVucBanHang") ;
	buttonMenu.mouseClick();
	var button = frameui.button("DanhSachKhuvuc");
	button.mouseClick();
	var dlMain = frameui.dialog("dlDanhSachKhuvuc");
	var tableQuanLyHangHoa = dlMain.table("tbDanhSachKhuvuc");
	
	//Click dòng vừa thêm có giá trị code = Mã HktTest1
	frameui.showDialogTest("Chi tiết khu vực HktTest1");
	frameui.delay();
	tableQuanLyHangHoa.doubleClickRow("Mã KV HktTest1");
	frameui.delay();
	
	var dlChild = frameui.dialog("dlKhuVucMoi");
	var pnChild = dlChild.panel("KhuVucMoi");

	//Chỉnh sửa các giá trị thành HktTest2
	frameui.showDialogTest("Chỉnh sửa các giá trị thành HktTest2");
	frameui.delay();
	dlChild.buttonClick("Sửa");
	frameui.delay();
	pnChild.fieldSet("txtKhuVuc", "Tên KV HktTest2");
	pnChild.fieldSet("txtMieuTa", "Miêu tả KV HktTest2");
	
	//Ấn nút [Xem lại] trở về các giá trị gốc
	frameui.showDialogTest("[Xem lại] để trở về các giá trị ban đầu");
	frameui.delay();
	dlChild.buttonClick("Xem lại");
	frameui.delay();
	
	//Sửa lại các thông tin và lưu lại
	frameui.showDialogTest("[Sửa] khu vực HktTest1");
	frameui.delay();
	dlChild.buttonClick("Sửa");
	pnChild.fieldSet("txtKhuVuc", "Tên KV HktTest2");
	pnChild.fieldSet("txtMieuTa", "Miêu tả KV HktTest2");
	dlChild.buttonClick("Lưu");
	frameui.delay();
	frameui.showDialogTest("[Lưu sửa] thành công khu vực HktTest2");
	frameui.delay();
	
	//Click viết mới và tạo khu vực mới HktTest3
	dlChild.buttonClick("Viết lại");
	pnChild.fieldSet("txtKhuVuc", "Tên KV HktTest3");
	pnChild.fieldSet("txtMa", "Mã KV HktTest3");
	pnChild.fieldSet("txtMieuTa", "Miêu tả KV HktTest3");
	dlChild.buttonClick("Lưu");
	frameui.delay();
	frameui.showDialogTest("Thêm xong khu vực HktTest3");
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
	dlMain.buttonClick("<<");
	frameui.delay();
	
	//Test tìm kiếm 
	frameui.showDialogTest("[TEST] tìm kiếm -> tìm theo mã khu vực");
	frameui.delay();
	var comboBox = dlMain.comboBox("cbSearch");
	comboBox.selectItem("Mã khu vực");
	dlMain.fieldSet("txtSearch", "Mã KV HktTest3");
	frameui.delay();
	
	//Xóa khu vực
	frameui.showDialogTest("[Xóa] theo Mã HktTest3 có Bàn quầy");
	frameui.delay();	
	tableQuanLyHangHoa.doubleClickRow("Mã KV HktTest3");
	var dlChild = frameui.dialog("dlKhuVucMoi");
	dlChild.buttonClick("Sửa");
	dlChild.buttonClick("Xóa");
	frameui.delay();
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");
	var dlRemove = frameui.dialog("dlXoaKhuVuc");
	var pnRemove = dlRemove.panel("XoaKhuVuc");
	frameui.showDialogTest("Chuyển tất cả bàn quầy sang KV HktTest2");
	frameui.delay();
	dlRemove.buttonClick(">>");
	var comboBox = dlRemove.comboBox("cbDelete");
	comboBox.selectItem("Tên KV HktTest2");
	frameui.delay();
	dlRemove.buttonClick("Đồng ý");
	
	frameui.showDialogTest("[Xóa] theo Mã HktTest4 không có Bàn quầy");
	frameui.delay();	
	tableQuanLyHangHoa.doubleClickRow("Mã KV HktTest4");
	var dlChild = frameui.dialog("dlKhuVucMoi");
	dlChild.buttonClick("Sửa");
	dlChild.buttonClick("Xóa");
	var dlDelete = frameui.dialog("dlXoa");
	dlDelete.buttonClick("Đồng ý");
	frameui.delay();		
	frameui.showDialogTest("Xóa thành công khu vực HktTest4");
	frameui.delay();
	pnChild.deleteDataTest();	
	frameui.showDialogTest("Hoàn thành test giao diện khu vực");
	frameui.delay();
	var dlMain = frameui.dialog("dlDanhSachKhuvuc");
	dlMain.buttonClickByName("btnExit");
}

var robot = new Robot();
robot.add("TEST KhuVucMoi ", "", CreateKhuVucMoi);
robot.add("TEST DanhSachKhuvuc ", "", CreateDanhSachKhuvuc);
console.clear();
robot.run();
robot.report();