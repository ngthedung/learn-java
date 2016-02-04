//Edit: Khanhdd thêm if vào các test lỗi
ScriptRunner.require("robotExp.js");

function CreateSuDungTraTruoc(frameui) {
	//Khởi tạo cho các biến 
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac") ;
	buttonMenu.mouseClick();
	var button = frameui.button("SuDungTraTruoc");
	button.mouseClick();
	
	var dlMain = frameui.dialog("dlSuDungTraTruoc");
	var pnMain = dlMain.panel("SuDungTraTruoc");
	
//	pnMain.deleteDataTest();
	/*
	 * KỊCH BẢN TEST ĐẦU
	 */
//	Điền 1 thông tin thì click Lưu -> Sau mỗi lần click thông báo lỗi hoặc mất lỗi
	frameui.showDialogTest("KỊCH BẢN TEST ĐẦU");
	if(frameui.showDialogTest("Lỗi không có giá trị tiền sử dụng")){
		var comboBoxPartner = pnMain.comboBox("cbDoiTac");
		comboBoxPartner.selectItem("HktTest1");
		var comboBoxType = pnMain.comboBox("cbLoai");
		comboBoxType.selectItem("Cộng thêm");
		pnMain.fieldSet("txtTienSD", "");
		pnMain.fieldSet("txtGhiChu", "Ghi chú HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
		//Làm mới các ô nhập bằng nút [Viết lại]
		frameui.showDialogTest("[Viết lại] Xóa trắng các ô nhập");
		dlMain.buttonClick("Viết lại");
		frameui.delay();
	}
	
	/*
	 * KỊCH BẢN TEST CHI TIẾT
	 */
	//Thêm mới hàng hóa HktTest1
	frameui.showDialogTest("[Thêm mới] tiền sử dụng của đối tác HktTest1");
	var comboBoxPartner = pnMain.comboBox("cbDoiTac");
	comboBoxPartner.selectItem("HktTest1");
	pnMain.fieldSet("txtTienSD", "1100000");
	var comboBoxType = pnMain.comboBox("cbLoai");
	comboBoxType.selectItem("Giảm đi");
	comboBoxType.selectItem("Cộng thêm");
	pnMain.fieldSet("txtGhiChu", "Ghi chú HktTest1");
	dlMain.buttonClick("Lưu");
	frameui.delay();
	frameui.showDialogTest("[Thêm mới] thành công tiền SD của đối tác HktTest1");
	frameui.delay();
	frameui.showDialogTest("[Thoát] khỏi giao diện sử dụng trả trước");
	dlMain.buttonClick("Thoát");
	//Kết thúc giao diện sử dụng trả trước.
}

function CreateDanhSachTraTruoc(frameui) {
	//Khởi tạo các giá trị cần thiết
	frameui.showDialogTest("[Xem lại] Chương trình chạy thử chức năng");
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac") ;
	buttonMenu.mouseClick();
	var button = frameui.button("DanhSachTraTruoc");
	button.mouseClick();
	var dlMain = frameui.dialog("dlDanhSachTraTruoc");
	var tableQuanLyTraTruoc = dlMain.table("tbDanhSachTraTruoc");
	
	//Click dòng vừa thêm có giá trị code = Mã HktTest1
	frameui.showDialogTest("Chi tiết tiền SD của đối tác HktTest1");
	tableQuanLyTraTruoc.doubleClickRow("HktTest1");
	frameui.delay();
	
	var dlChild = frameui.dialog("dlSuDungTraTruoc");
	var pnChild = dlChild.panel("SuDungTraTruoc");
	
	//Chỉnh sửa các giá trị thành HktTest2
	frameui.showDialogTest("Chỉnh sửa các giá trị thành HktTest2");
	dlChild.buttonClick("Sửa");
	frameui.delay();
	var comboBoxPartner = pnChild.comboBox("cbDoiTac");
	comboBoxPartner.selectItem("HktTest1");
	pnChild.fieldSet("txtTienSD", "220000");
	var comboBoxType = pnChild.comboBox("cbLoai");
	comboBoxType.selectItem("Giảm đi");
	comboBoxType.selectItem("Cộng thêm");
	pnChild.fieldSet("txtGhiChu", "Ghi chú HktTest2");
	//Ấn nút [Xem lại] trở về các giá trị gốc
	frameui.showDialogTest("[Xem lại] để trở về các giá trị ban đầu");
	dlChild.buttonClick("Xem lại");
	frameui.delay();
	
	//Sửa lại các thông tin và lưu lại
	frameui.showDialogTest("[Sửa] tiền sử dụng HktTest1");
	dlChild.buttonClick("Sửa");
	comboBoxPartner.selectItem("HktTest1");
	pnChild.fieldSet("txtTienSD", "22000");
	comboBoxType.selectItem("Giảm đi");
	comboBoxType.selectItem("Cộng thêm");
	pnChild.fieldSet("txtGhiChu", "Ghi chú HktTest2");
	dlChild.buttonClick("Lưu");
	frameui.delay();
	frameui.showDialogTest("[Lưu sửa] thành công tiền SD của đối tác HktTest1");
	
	//Click viết mới và tạo hàng hóa mới HktTest3
	dlChild.buttonClick("Viết lại");
	comboBoxPartner.selectItem("HktTest1");
	pnChild.fieldSet("txtTienSD", "3000");
	comboBoxType.selectItem("Cộng thêm");
	comboBoxType.selectItem("Giảm đi");
	pnChild.fieldSet("txtGhiChu", "Ghi chú HktTest3");
	dlChild.buttonClick("Lưu");
	frameui.delay();
	frameui.showDialogTest("Thêm xong tiền SD của đối tác HktTest3");
	
	//Thêm 50 bản ghi vào danh sách TEST chuyển trang
	frameui.showDialogTest("Thêm 50 bản ghi để [TEST] chuyển trang");
	pnChild.createDataTest();
	dlChild.buttonClick("Thoát");
	
	//Test chuyển trang
	frameui.showDialogTest("Sang trang 2");
	dlMain.buttonClick("2");
	frameui.delay();
	frameui.showDialogTest("Trở về trang đầu tiên");
	dlMain.buttonClick("<<");
	frameui.delay();
	
	//Test tìm kiếm 
	frameui.showDialogTest("[TEST] tìm kiếm -> tìm theo tiền sử dụng");
	var comboBox = dlMain.comboBox("cbSearch");
	comboBox.selectItem("Số tiền SD");
	dlMain.fieldSet("txtSearch", "-3000");
	frameui.delay();
	
	frameui.showDialogTest("[Xóa] theo số điểm dùng");
	frameui.delay();
	frameui.delay();
	tableQuanLyTraTruoc.doubleClickRow("HktTest1");
	var dlChild = frameui.dialog("dlSuDungTraTruoc");
	dlChild.buttonClick("Sửa");
	dlChild.buttonClick("Xóa");
	
	var dlDelete = frameui.dialog("dlXoa");
	dlDelete.buttonClick("Đồng ý");
	
	frameui.delay();
	frameui.showDialogTest("Xóa thành công tiền SD của đối tác HktTest3");	
	
	pnChild.deleteDataTest();
	frameui.delay();
	dlChild.buttonClick("Thoát");
	frameui.delay();
	frameui.showDialogTest("Hoàn thành test giao diện sử dụng trả trước");	
	dlMain.buttonClickByName("btnExit");
}

var robot = new Robot();
robot.add("TEST SuDungTraTruoc ", "", CreateSuDungTraTruoc);
robot.add("TEST DanhSachTraTruoc ", "", CreateDanhSachTraTruoc);
console.clear();
robot.run();
robot.report();