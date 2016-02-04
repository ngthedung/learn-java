ScriptRunner.require("robotExp.js");

function createReceipt(frameui) {
	frameui.showDialogTest("Chương trình bắt đầu chạy thử giao diện [Phiếu Thu Tiền]");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightThuChiMuaHang");
	buttonMenu.mouseClick();
	var button = frameui.button("PhieuThuTien");
	button.mouseClick()

	var dlMain = frameui.dialog("dlPhieuThuTien");
	var pnChild = dlMain.panel("PhieuThuTien");

	// Kiểm tra validate các trường dữ liệu bằng cách để trống hoặc đánh sai
	// kiểu dữ liệu
	frameui.showDialogTest("Chạy thử chức năng [Lưu]");
	frameui.delay();
	frameui.showDialogTest("[Lỗi] chưa nhập dữ liệu");
	frameui.delay();
	dlMain.buttonClick("Đồng ý");
	frameui.delay();

	frameui.showDialogTest("[Lỗi] chưa chọn Tên Nghiệp vụ");
	pnChild.fieldSet("txtNghiepVu", "");
	pnChild.fieldSet("txtTongTien", "1000000");
	frameui.delay();
	dlMain.buttonClick("Đồng ý");
	frameui.delay();

	frameui.showDialogTest("[Lỗi] chưa nhập Tổng tiền");
	frameui.delay();
	pnChild.fieldSet("txtNghiepVu", "Nghiệp vụ HktTest1");
	pnChild.fieldSet("txtTongTien", "");
	dlMain.buttonClick("Đồng ý");
	frameui.delay();

	frameui.showDialogTest("[Lỗi] nhập sai định dạng Tổng tiền");
	frameui.delay();
	pnChild.fieldSet("txtNghiepVu", "Nghiệp vụ HktTest1");
	pnChild.fieldSet("txtTongTien", "HktTest1");
	dlMain.buttonClick("Đồng ý");
	frameui.delay();

	// Sét hết giá trị và lưu lại
	pnChild.fieldSet("txtNghiepVu", "Nghiệp vụ HktTest1");
	pnChild.fieldSet("txtTongTien", "1000000");
	frameui.delay();
	dlMain.buttonClickByName("btnSave");
	frameui.showDialogTest("[Lưu] thành công");
	frameui.delay();
}

function createListPayment(frameui) {
	
	frameui.showDialogTest("Chạy thử chức năng [Xem] phiếu thu tiền khi lưu thành công");
	frameui.delay();
	var button = frameui.button("DSThuchi");
	button.mouseClick()
	var dlMain = frameui.dialog("dlDSThuchi");
	var pnChild = dlMain.panel("DSThuchi");
	dlMain.buttonClick("Đồng ý");
	frameui.delay();
	var dlMain = frameui.dialog("dlinvoice");
	var TableInvoiceThuChi = dlMain.table("tbinvoice");
	TableInvoiceThuChi.doubleClickRow("Nghiệp vụ HktTest1");
	dlMain.buttonClick("Xem");
	frameui.delay();
	
	// Chạy chức năng sửa
	frameui.showDialogTest("Chạy thử chức năng [Sửa]");
	frameui.delay();
	var dlMain = frameui.dialog("dlpanelPaymentReceipt");
	var pnChild = dlMain.panel("pnpanelPaymentReceipt");
	dlMain.buttonClick("Sửa");
	frameui.delay();
	var cboTypeInvoice = pnChild.comboBox("cboTypeInvoice");
	cboTypeInvoice.selectItem("Thu Chi");
	pnChild.fieldSet("txtNghiepVu", "Nghiệp vụ HktTest2");
	pnChild.fieldSet("txtTongTien", "2530000");
	dlMain.buttonClick("Đồng ý");
	frameui.delay();
	frameui.showDialogTest("[Sửa] thành công");
	frameui.delay();
	
	var dlMain = frameui.dialog("dlinvoice");
	var TableInvoiceThuChi = dlMain.table("tbinvoice");
	dlMain.buttonClick("Thoát");
}
	
	// Thêm mới 1 bản ghi
	function createReceipt1(frameui) {
		frameui.showDialogTest("[Thêm mới] 1 Phiếu thu tiền");
		frameui.delay();
		var buttonMenu = frameui.button("menuRightThuChiMuaHang");
		buttonMenu.mouseClick();
		var button = frameui.button("PhieuThuTien");
		button.mouseClick()

		var dlMain = frameui.dialog("dlPhieuThuTien");
		var pnChild = dlMain.panel("PhieuThuTien");
		
		pnChild.fieldSet("txtNghiepVu", "Nghiệp vụ HktTest3");
		pnChild.fieldSet("txtTongTien", "2000000");
		frameui.delay();
		dlMain.buttonClickByName("btnSave");
		frameui.showDialogTest("[Thêm mới] thành công");
		frameui.delay();
	}

	//Thêm 50 bản ghi vào danh sách TEST chuyển trang
	function createListPayment1(frameui) {
		frameui.showDialogTest("Thêm 50 bản ghi để [TEST] chuyển trang");
		frameui.delay();
		var button = frameui.button("DSThuchi");
		button.mouseClick()
		var dlMain = frameui.dialog("dlDSThuchi");
		var pnChild = dlMain.panel("DSThuchi");
		dlMain.buttonClick("Đồng ý");
		frameui.delay();
		
		var dlMain = frameui.dialog("dlinvoice");
		var TableInvoiceThuChi = dlMain.table("tbinvoice");
		TableInvoiceThuChi.doubleClickRow("Nghiệp vụ HktTest3");
		dlMain.buttonClick("Xem");
		frameui.delay();
		
		var dlMain = frameui.dialog("dlpanelPaymentReceipt");
		var pnChild = dlMain.panel("pnpanelPaymentReceipt");
		pnChild.createDataTest();
		dlMain.buttonClick("Thoát");
		
		//Test chuyển trang
		frameui.showDialogTest("Sang trang 2");
		var dlMain = frameui.dialog("dlinvoice");
		var TableInvoiceThuChi = dlMain.table("tbinvoice");
		dlMain.buttonClick("2");
		frameui.delay();
		frameui.showDialogTest("Trở về trang đầu tiên");
		dlMain.buttonClick("<<");
		frameui.delay();
		
		//Test tìm kiếm 
		frameui.showDialogTest("[TEST] tìm kiếm -> tìm theo mã nghiệp vụ");
		var comboBox = dlMain.comboBox("cbSearch");
		comboBox.selectItem("Mã nghiệp vụ");
		dlMain.fieldSet("txtSearch", "3");
		frameui.delay();
		
		//Xóa bản ghi với bản ghi
		frameui.showDialogTest("Chạy thử chức năng [Xóa]");
		var dlMain = frameui.dialog("dlinvoice");
		var TableInvoiceThuChi = dlMain.table("tbinvoice");
		TableInvoiceThuChi.doubleClickRow("Mã Nghiệp vụ HktTest3");
		var dlChild = frameui.dialog("dlpanelPaymentReceipt");
		var pnChild = dlChild.panel("pnpanelPaymentReceipt");
		dlChild.buttonClick("Xóa");
		frameui.showDialogTest("[Xóa] thành công");	
		frameui.delay();
		TableInvoiceThuChi.doubleClickRow("Mã Nghiệp vụ HktTest1");
		var dlChild = frameui.dialog("dlpanelPaymentReceipt");
		var pnChild = dlChild.panel("pnpanelPaymentReceipt");
		pnChild.deleteDataTest();
		dlChild.buttonClick("Thoát");
		frameui.showDialogTest("Chương trình TEST [Phiếu Thu Tiền] kết thúc!");
		frameui.delay();
		var dlMain = frameui.dialog("dlinvoice");
		dlMain.buttonClick("Thoát");
	}

var robot = new Robot();
robot.add("TEST PhieuThuTien ", "", createReceipt);
robot.add("TEST DSThuChi ", "", createListPayment);
robot.add("TEST PhieuThuTien ", "", createReceipt1);
robot.add("TEST DSThuChi ", "", createListPayment1);
console.clear();
robot.run();
robot.report();