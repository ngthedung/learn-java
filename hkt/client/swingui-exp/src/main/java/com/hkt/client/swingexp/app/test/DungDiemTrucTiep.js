//Edit: Khanhdd thêm if vào các test lỗi
ScriptRunner.require("robotExp.js");

function CreateDungDiemTrucTiep(frameui) {
	//Khởi tạo cho các biến 
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac") ;
	buttonMenu.mouseClick();
	var button = frameui.button("DungDiemTrucTiep");
	button.mouseClick();
	
	var dlMain = frameui.dialog("dlDungDiemTrucTiep");
	var pnMain = dlMain.panel("DungDiemTrucTiep");
	
	/*
	 * KỊCH BẢN TEST ĐẦU
	 */
	//Điền 1 thông tin thì click Lưu -> Sau mỗi lần click thông báo lỗi hoặc mất lỗi
	frameui.showDialogTest("Chương trình TEST giao diện Dùng điểm trực tiếp!");
	frameui.delay();
	if(frameui.showDialogTest("Lỗi Không có điểm dùng")){
		frameui.delay();
		var comboBoxPartner = pnMain.comboBox("cbDoiTac");
		comboBoxPartner.selectItem("HktTest1");
		var comboBoxType = pnMain.comboBox("cbLoai");
		comboBoxType.selectItem("Tăng thêm");
		pnMain.fieldSet("txtDiemDung", "");
		pnMain.fieldSet("txtGhiChu", "Ghi chú HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
		//Làm mới các ô nhập bằng nút [Viết lại]
		frameui.showDialogTest("[Viết lại] Xóa trắng các ô nhập");
		frameui.delay();
		dlMain.buttonClick("Viết lại");
		frameui.delay();
	}
	
	/*
	 * KỊCH BẢN TEST CHI TIẾT
	 */
	//Thêm mới hàng hóa HktTest1
	frameui.showDialogTest("[Thêm mới] điểm dùng của đối tác HktTest1");
	frameui.delay();
	var comboBoxPartner = pnMain.comboBox("cbDoiTac");
	comboBoxPartner.selectItem("HktTest1");
	pnMain.fieldSet("txtDiemDung", "100");
	var comboBoxType = pnMain.comboBox("cbLoai");
	comboBoxType.selectItem("Giảm đi");
	comboBoxType.selectItem("Tăng thêm");
	pnMain.fieldSet("txtGhiChu", "Ghi chú HktTest1");
	dlMain.buttonClick("Lưu");
	frameui.delay();
	frameui.showDialogTest("[Thêm mới] thành công điểm dùng của đối tác HktTest1");
	frameui.delay();
	frameui.showDialogTest("[Thoát] khỏi giao diện dùng điểm trực tiếp");
	frameui.delay();
	dlMain.buttonClick("Thoát");
	//Kết thúc giao diện dùng điểm trực tiếp.
}

function CreateDanhSachDiemDung(frameui) {
	//Khởi tạo các giá trị cần thiết
	frameui.showDialogTest("[Xem lại] Chương trình chạy thử chức năng");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac") ;
	buttonMenu.mouseClick();
	var button = frameui.button("DanhSachDiemDung");
	button.mouseClick();
	var dlMain = frameui.dialog("dlDanhSachDiemDung");
	var tableQuanLyHangHoa = dlMain.table("tbDanhSachDiemDung");
	
	//Click dòng vừa thêm có giá trị code = Mã HktTest1
	frameui.showDialogTest("Chi tiết điểm dùng của đối tác HktTest1");
	frameui.delay();
	tableQuanLyHangHoa.doubleClickRow("HktTest1");
	frameui.delay();
	
	var dlChild = frameui.dialog("dlDungDiemTrucTiep");
	var pnChild = dlChild.panel("DungDiemTrucTiep");
	
	//Chỉnh sửa các giá trị thành HktTest2
	frameui.showDialogTest("Chỉnh sửa các giá trị thành HktTest2");
	frameui.delay();
	dlChild.buttonClick("Sửa");
	frameui.delay();
	var comboBoxPartner = pnChild.comboBox("cbDoiTac");
	comboBoxPartner.selectItem("HktTest1");
	pnChild.fieldSet("txtDiemDung", "200");
	var comboBoxType = pnChild.comboBox("cbLoai");
	comboBoxType.selectItem("Giảm đi");
	comboBoxType.selectItem("Tăng thêm");
	pnChild.fieldSet("txtGhiChu", "Ghi chú HktTest2");
	
	//Ấn nút [Xem lại] trở về các giá trị gốc
	frameui.showDialogTest("[Xem lại] để trở về các giá trị ban đầu");
	frameui.delay();
	dlChild.buttonClick("Xem lại");
	frameui.delay();
	
	//Sửa lại các thông tin và lưu lại
	frameui.showDialogTest("[Sửa] điểm dùng HktTest1");
	frameui.delay();
	dlChild.buttonClick("Sửa");
	comboBoxPartner.selectItem("HktTest1");
	pnChild.fieldSet("txtDiemDung", "200");
	comboBoxType.selectItem("Giảm đi");
	comboBoxType.selectItem("Tăng thêm");
	pnChild.fieldSet("txtGhiChu", "Ghi chú HktTest2");
	dlChild.buttonClick("Lưu");
	frameui.delay();
	frameui.showDialogTest("[Lưu sửa] thành công điểm dùng của đối tác HktTest1");
	frameui.delay();
	
	//Click viết mới và tạo hàng hóa mới HktTest3
	dlChild.buttonClick("Viết lại");
	comboBoxPartner.selectItem("HktTest1");
	pnChild.fieldSet("txtDiemDung", "100");
	comboBoxType.selectItem("Giảm đi");
	pnChild.fieldSet("txtGhiChu", "Ghi chú HktTest3");
	dlChild.buttonClick("Lưu");
	frameui.showDialogTest("Thêm xong điểm dùng của đối tác HktTest3");
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
	frameui.showDialogTest("[TEST] tìm kiếm -> tìm theo số điểm dùng");
	frameui.delay();
	var comboBox = dlMain.comboBox("cbSearch");
	comboBox.selectItem("Số điểm dùng");
	dlMain.fieldSet("txtSearch", "-100");
	frameui.delay();
	
	//xóa điểm dùng 
	frameui.showDialogTest("[Xóa] theo số điểm dùng");
	frameui.delay();
	tableQuanLyHangHoa.doubleClickRow("HktTest1");
	var dlChild = frameui.dialog("dlDungDiemTrucTiep");
	dlChild.buttonClick("Sửa");
	dlChild.buttonClick("Xóa");
	var dlDelete = frameui.dialog("dlXoa");
	dlDelete.buttonClick("Đồng ý");
	
	frameui.showDialogTest("Xóa thành công điểm dùng của đối tác HktTest3");
	frameui.delay();
	pnChild.deleteDataTest();
	dlChild.buttonClick("Thoát");
	frameui.showDialogTest("Hoàn thành test giao diện dùng điểm trực tiếp");
	frameui.delay();
	dlMain.buttonClickByName("btnExit");
}

var robot = new Robot();
robot.add("TEST DungDiemTrucTiep ", "", CreateDungDiemTrucTiep);
robot.add("TEST DanhSachDiemDung ", "", CreateDanhSachDiemDung);
console.clear();
robot.run();
robot.report();