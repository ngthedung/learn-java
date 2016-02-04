//Edit: Khanhdd thêm if vào các test lỗi
ScriptRunner.require("robotExp.js");

function CreateThanhPho(frameui) {

	frameui.showDialogTest("Chương trình TEST giao diện Thành Phố");

	// Click vào menu Hệ thống
	var buttonMenu = frameui.button("menuRightHeThong");
	buttonMenu.mouseClick();

	// Click vào button Tiền tệ
	var button = frameui.button("ThanhPho");
	button.mouseClick();

	var dlMain = frameui.dialog("dlThanhPho");
	var pnChild = dlMain.panel("ThanhPho");

	// Nhập đúng dữ liệu cho các trường trường 
	frameui.showDialogTest("[Lưu] Khi Dữ Liệu các trường nhập đúng !");
	pnChild.fieldSet("txtCode", "Mã thành phố HktTest1");
	pnChild.fieldSet("txtName", "Tên thành phố HktTest1");
	var comboBoxChoose = pnChild.comboBox("cbCountry");
	comboBoxChoose.selectItem("Việt Nam");
	pnChild.fieldSet("txtDescription", "Miêu tả thành phố HktTest1");
	dlMain.buttonClick("Lưu");
	frameui.delay();
	frameui.showDialogTest(" Lưu thành công !");
	
	// Kiểm tra validate các trường đơn vị và tỷ lệ trống
	if(frameui.showDialogTest("Lỗi Khi Dữ Liệu các trường để trống !")){
		dlMain.buttonClick("Lưu");
		frameui.delay();
		frameui.delay();
		frameui.delay();
	} 
	
	
	// Kiểm tra validate các trường dữ liệu khi trường mã trống
	if(frameui.showDialogTest("Lỗi Khi Dữ Liệu trường mã trống !")){
		dlMain.buttonClick("Viết lại");
		pnChild.fieldSet("txtCode", "");
		pnChild.fieldSet("txtName", "Tên thành phố HktTest2");
		var comboBoxChoose = pnChild.comboBox("cbCountry");
		comboBoxChoose.selectItem("Việt Nam");
		frameui.delay();
		pnChild.fieldSet("txtDescription", "Miêu tả thành phố HktTest2");
		frameui.delay();
		dlMain.buttonClick("Lưu");
		frameui.delay();
		frameui.delay();
	}
	
	//Lưu khi trường tên thành phố trống
	if(frameui.showDialogTest("Lỗi Khi Dữ Liệu trường tên trống !")){
		dlMain.buttonClick("Viết lại");
		pnChild.fieldSet("txtCode", "Mã thành phố HktTest2");
		pnChild.fieldSet("txtName", "");
		var comboBoxChoose = pnChild.comboBox("cbCountry");
		comboBoxChoose.selectItem("Việt Nam");
		pnChild.fieldSet("txtDescription", "Miêu tả thành phố HktTest2");
		dlMain.buttonClick("Lưu");
		frameui.delay();
		frameui.delay();
		frameui.delay();
	}
	
	
	//Lưu khi mã nhập trùng 
	if(frameui.showDialogTest("Lỗi Khi Dữ Liệu trường mã trùng!")){
		dlMain.buttonClick("Viết lại");
		pnChild.fieldSet("txtCode", "Mã thành phố HktTest1");
		pnChild.fieldSet("txtName", "Tên thành phố HktTest3");
		var comboBoxChoose = pnChild.comboBox("cbCountry");
		comboBoxChoose.selectItem("Việt Nam");
		pnChild.fieldSet("txtDescription", "Miêu tả thành phố HktTest2");
		dlMain.buttonClick("Lưu");
		frameui.delay();
		frameui.delay();
		frameui.delay();
	}
	
	// chạy thử chức năng xem lại
	frameui.showDialogTest("Chạy thử chức năng xem lại ");
	var table = pnChild.table("tbcityTable");
	table.doubleClickRow("Mã thành phố HktTest1");
	frameui.delay();
	frameui.delay();
	dlMain.buttonClick("Sửa");
	frameui.delay();
	frameui.delay();
	
	// Sửa lại các giá trị ban đầu từ 1 thành 2
	pnChild.fieldSet("txtName", "Tên thành phố HktTest2");
	frameui.delay();
	pnChild.fieldSet("txtDescription", "Miêu tả thành phố HktTest2");
	frameui.delay();
	dlMain.buttonClick("Xem lại");
	frameui.delay();
	frameui.delay();
	
	// Chạy chức năng reset lại giao diện
	table.doubleClickRow("Mã thành phố HktTest1");
	frameui.delay();
	dlMain.buttonClick("Viết lại");
	frameui.delay();
	
	// Chạy chức năng sửa
	frameui.showDialogTest("Chạy thử chức năng chỉnh sửa");
	var table = pnChild.table("tbcityTable");
	table.doubleClickRow("Mã thành phố HktTest1");
	dlMain.buttonClick("Sửa");
	pnChild.fieldSet("txtName", "Tên thành phố HktTest123");
	var comboBoxChoose = pnChild.comboBox("cbCountry");
	comboBoxChoose.selectItem("Việt Nam");
	pnChild.fieldSet("txtDescription", "Miêu tả thành phố HktTest123");
	frameui.delay();
	frameui.delay();
	dlMain.buttonClick("Lưu");
	frameui.delay();
	frameui.delay();
	frameui.showDialogTest("Sửa thành công !");
	
	//Thêm mới 1 bản ghi
	dlMain.buttonClick("Viết lại");
	frameui.showDialogTest("[Lưu] Khi Dữ Liệu các trường nhập đúng !");
	pnChild.fieldSet("txtCode", "Mã thành phố HktTest5");
	pnChild.fieldSet("txtName", "Tên thành phố HktTest5");
	var comboBoxChoose = pnChild.comboBox("cbCountry");
	comboBoxChoose.selectItem("Việt Nam");
	pnChild.fieldSet("txtDescription", "Miêu tả thành phố HktTest5");
	dlMain.buttonClick("Lưu");
	frameui.delay();
	frameui.showDialogTest(" Lưu thành công !");
	
	// chạy thử chức năng thay đổi vị trí
	frameui.showDialogTest("Chạy thử chức năng vị trí");
	table.selectRow("Mã thành phố HktTest1");
	frameui.delay();
	dlMain.buttonClickByName("btnUp")
	frameui.delay();
	pnChild.buttonClick("Cập nhật")
	frameui.delay();

	// Chạy chức năng Xóa
	frameui.showDialogTest("Chạy thử chức năng xóa bản ghi");
	table.doubleClickRow("Mã thành phố HktTest1");
	frameui.delay();
	dlMain.buttonClick("Sửa");
	frameui.delay();
	dlMain.buttonClick("Xóa");
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");
	frameui.delay();

	// Thoat. oke Test finish!
	frameui.showDialogTest("Chương trình TEST giao diện Thành phố thành công !");
	pnChild.deleteDataTest();
	dlMain.buttonClick("Thoát");
}
var robot = new Robot();
robot.add("TEST Thanh Pho ", "", CreateThanhPho);
console.clear();
robot.run();
robot.report();
