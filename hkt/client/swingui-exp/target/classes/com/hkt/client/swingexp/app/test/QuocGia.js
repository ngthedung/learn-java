//Edit: Khanhdd thêm if vào các test lỗi
ScriptRunner.require("robotExp.js");

function CreateQuocGia(frameui) {

	frameui.showDialogTest("Chương trình TEST Quốc gia");
	frameui.delay();

	var buttonMenu = frameui.button("menuRightHeThong");
	buttonMenu.mouseClick();

	var button = frameui.button("QuocGia");
	button.mouseClick();

	var dlMain = frameui.dialog("dlQuocGia");
	var pnChild = dlMain.panel("QuocGia");
	
	// TEST LƯU NHẬP ĐÚNG DỮ LIỆU CÁC TRƯỜNG
	frameui.showDialogTest("TEST chức năng Lưu");
	frameui.delay();
	pnChild.fieldSet("txtCode", "Mã QG HktTest1");
	pnChild.fieldSet("txtName", "Tên QG HktTest1");
	pnChild.fieldSet("txtFlag", "Quốc tịch HktTest1");
	pnChild.fieldSet("txtDescription", "Miêu tả QG HktTest1");
	dlMain.buttonClick("Lưu");
	frameui.delay();
	frameui.showDialogTest("Lưu thành công !");
	
	// BỎ TRỐNG CÁC TRƯỜNG VÀ LƯU
	if(frameui.showDialogTest("Lỗi chưa nhập dữ liệu các trường")){
		pnChild.fieldSet("txtCode", "");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
		
	// BỎ TRỐNG TRƯỜNG MÃ VÀ LƯU
	if(frameui.showDialogTest("Lỗi chưa nhập Mã QG")){
		frameui.delay();
		pnChild.fieldSet("txtCode", "");
		pnChild.fieldSet("txtName", "Tên QG HktTest1");
		pnChild.fieldSet("txtFlag", "Quốc tịch HktTest1");
		pnChild.fieldSet("txtDescription", "Miêu tả QG HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// NHẬP TRÙNG MÃ VÀ LƯU
	if(frameui.showDialogTest("Lỗi nhập trùng Mã QG")){	
		frameui.delay();
		pnChild.fieldSet("txtCode", "Mã QG HktTest1");
		pnChild.fieldSet("txtName", "Tên QG HktTest2");
		pnChild.fieldSet("txtFlag", "Quốc tịch HktTest2");
		pnChild.fieldSet("txtDescription", "Miêu tả QG HktTest2");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// BỎ TRỐNG TRƯỜNG TÊN VÀ LƯU
	if(frameui.showDialogTest("Lỗi chưa nhập Tên QG")){
		frameui.delay();
		pnChild.fieldSet("txtCode", "Mã QG HktTest3");
		pnChild.fieldSet("txtName", "");
		pnChild.fieldSet("txtFlag", "Quốc tịch HktTest3");
		pnChild.fieldSet("txtDescription", "Miêu tả quốc gia HktTest3");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
		
	// TEST XEM LẠI: sửa giá trị ban đầu từ 1 thành 2 và xem lại
	frameui.showDialogTest("TEST chức năng Xem lại");
	frameui.delay();
	var table = pnChild.table("tbcountryTable");
	table.doubleClickRow("Mã QG HktTest1");
	dlMain.buttonClick("Sửa");
	pnChild.fieldSet("txtName", "Tên QG HktTest2");
	pnChild.fieldSet("txtFlag", "Quốc tịch HktTest2");
	pnChild.fieldSet("txtDescription", "Miêu tả QG HktTest2");
	dlMain.buttonClick("Xem lại");
	frameui.delay();

	// TEST SỬA: sửa giá trị ban đầu từ 1 thành 2 và lưu
	frameui.showDialogTest("TEST chức năng Sửa");
	frameui.delay();
	var table = pnChild.table("tbcountryTable");
	table.doubleClickRow("Mã QG HktTest1");
	dlMain.buttonClick("Sửa");
	pnChild.fieldSet("txtName", "Tên QG HktTest2");
	pnChild.fieldSet("txtFlag", "Quốc tịch QG HktTest2");
	pnChild.fieldSet("txtDescription", "Miêu tả QG HktTest2");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Sửa thành công !");
	frameui.delay();

	// TEST VIẾT LẠI: reset các trường
	frameui.showDialogTest("TEST chức năng Viết lại")
	frameui.delay();
	table.doubleClickRow("Mã QG HktTest1");
	dlMain.buttonClick("Viết lại");
	
	// TEST THÊM MỚI 1 QG SO SÁNH VỚI QG BAN ĐẦU
	frameui.showDialogTest("TEST chức năng Thêm mới")
	frameui.delay();
	pnChild.fieldSet("txtCode", "Mã QG HktTest3");
	pnChild.fieldSet("txtName", "Tên QG HktTest3");
	pnChild.fieldSet("txtFlag", "Quốc tịch HktTest3");
	pnChild.fieldSet("txtDescription", "Miêu tả QG HktTest3");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Lưu thành công !");
	frameui.delay();

	// TEST THAY ĐỔI VỊ TRÍ ƯU TIÊN
	frameui.showDialogTest("TEST chức năng Vị trí");
	frameui.delay();
	table.selectRow("Mã QG HktTest3");
	dlMain.buttonClickByName("btnUp")
	pnChild.buttonClick("Cập nhật")
	frameui.delay();

	// TEST XÓA
	frameui.showDialogTest("TEST chức năng Xóa");
	frameui.delay();
	table.doubleClickRow("Mã QG HktTest1");
	dlMain.buttonClick("Sửa");
	dlMain.buttonClick("Xóa");
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");
	frameui.delay();

	// Thoat. oke Test finish!
	frameui.showDialogTest("Chương trình TEST Quốc gia kết thúc!");
	frameui.delay();
	pnChild.deleteDataTest();
	dlMain.buttonClick("Thoát");
}
var robot = new Robot();
robot.add("TEST Quoc Gia ", "", CreateQuocGia);
console.clear();
robot.run();
robot.report();
