//Edit: Khanhdd thêm if vào các test lỗi
ScriptRunner.require("robotExp.js");

function ListPartnerPoint(frameui){
	
	frameui.showDialogTest("Chương trình TEST giao diện Lập cơ chế điểm");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac");
	buttonMenu.mouseClick();
	
	var buttonMenu = frameui.button("LapCoCheDiem");
	buttonMenu.mouseClick();
	
	var dlMain = frameui.dialog("dlQuanLyDiemKH");
	var pnChild = dlMain.panel("LapCoCheDiem");

	// Kiểm tra validate các trường đơn vị và tỷ lệ trống 
	if(frameui.showDialogTest("[Lưu] Khi Dữ Liệu các trường lỗi để trống !")){
		frameui.delay();
		dlMain.buttonClick("Lưu");
		frameui.delay();
		frameui.showDialogTest("Thiếu các thông tin quan trọng");
		frameui.delay();
	}
	
	
	// Kiểm tra validate trường Tỷ lệ nhập sai định dạng
	if(frameui.showDialogTest("Lỗi Khi Tỷ lệ nhập sai định dạng !")){
		frameui.delay();
		pnChild.fieldSet("txtTenTyLe","Tỷ lệ HktTest2");
		pnChild.fieldSet("txtDoanhThuKH","DTKH HktTest2");
		var Unit1 = frameui.dialog("dlQuanLyDiemKH");
		var comboBoxChoose = Unit1.comboBox("cbMoneyUnit1");
		comboBoxChoose.selectItem("VND");
		pnChild.fieldSet("txtDiemTuongUng","ĐTU HktTest2");
		var Unit2 = frameui.dialog("dlQuanLyDiemKH");
		var comboBoxChoose = Unit2.comboBox("cbMoneyUnit2");
		comboBoxChoose.selectItem("VND");
		pnChild.fieldSet("txtTienKMTuongUng","KM HktTest2");
		pnChild.fieldSet("txtDiemToiThieu","ĐTT HktTest2");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
	// Nhập đúng dữ liệu cho các trường
	frameui.showDialogTest("[Lưu] khi dữ liệu các trường nhập đúng !");
	frameui.delay();
	pnChild.fieldSet("txtTenTyLe","Tỷ lệ HktTest1");
	pnChild.fieldSet("txtDoanhThuKH","100");
	var Unit1 = frameui.dialog("dlQuanLyDiemKH");
	var comboBoxChoose = Unit1.comboBox("cbMoneyUnit1");
	comboBoxChoose.selectItem("VND");
	pnChild.fieldSet("txtDiemTuongUng","1");
	var Unit2 = frameui.dialog("dlQuanLyDiemKH");
	var comboBoxChoose = Unit2.comboBox("cbMoneyUnit2");
	comboBoxChoose.selectItem("VND");
	pnChild.fieldSet("txtTienKMTuongUng","1");
	pnChild.fieldSet("txtDiemToiThieu","1");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Lưu thành công !");
	frameui.delay();	

	// chạy thử chức năng xem lại
	frameui.showDialogTest("Chạy thử chức năng xem lại ");
	frameui.delay();
	dlMain.buttonClick("Viết lại");
	var table = pnChild.table("tbtableList");
	table.doubleClickRow("Tỷ lệ HktTest1");	
	dlMain.buttonClick("Sửa");	
	frameui.delay();
	
	// sửa lại giá trị 
	pnChild.fieldSet("txtTenTyLe","Tỷ lệ HktTest1 Xem lại");
	dlMain.buttonClick("Xem lại");
	frameui.delay();
	
	// Chạy chức năng sửa và lưu lại thông tin đã chỉnh sửa
	frameui.showDialogTest("Chạy chức năng [Sửa]");
	frameui.delay();
	dlMain.buttonClick("Viết lại");
	var table =pnChild.table("tbtableList");
	table.doubleClickRow("Tỷ lệ HktTest1");
	dlMain.buttonClick("Sửa");
	frameui.delay();
	pnChild.fieldSet("txtDoanhThuKH","10");
	var Unit1 = frameui.dialog("dlQuanLyDiemKH");
	var comboBoxChoose = Unit1.comboBox("cbMoneyUnit1");
	comboBoxChoose.selectItem("USD");
	pnChild.fieldSet("txtDiemTuongUng","2");
	var Unit2 = frameui.dialog("dlQuanLyDiemKH");
	var comboBoxChoose = Unit2.comboBox("cbMoneyUnit2");
	comboBoxChoose.selectItem("USD");
	pnChild.fieldSet("txtTienKMTuongUng","2");
	pnChild.fieldSet("txtDiemToiThieu","2");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Sửa thành công !");
	frameui.delay();
	
	// Chạy chức năng Xóa
	frameui.showDialogTest("Chạy chức năng [Xóa]");
	frameui.delay();
	table.doubleClickRow("Tỷ lệ HktTest1");
	dlMain.buttonClick("Sửa");
	dlMain.buttonClick("Xóa");
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");
	frameui.showDialogTest("Xóa thành công");
	frameui.delay();
	 
	 //Thoat. oke Test finish!
	frameui.showDialogTest("Chương trình TEST Cơ chế điểm thành công !");
	frameui.delay();
	pnChild.deleteDataTest();
	dlMain.buttonClick("Thoát");
}

var robot = new Robot() ;
robot.add("TEST Quan lu diem KH ", "", ListPartnerPoint);
console.clear() ;
robot.run() ;
robot.report() ;