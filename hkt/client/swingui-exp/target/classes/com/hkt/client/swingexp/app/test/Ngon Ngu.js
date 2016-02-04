ScriptRunner.require("robotExp.js");

function CreateNgonNgu(frameui) {
	/**
	 * @ edit: Hạnh: Thêm if vào Test Lỗi
	 */
	frameui.showDialogTest("Chương trình TEST Ngôn Ngữ");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightHeThong");
	buttonMenu.mouseClick();
	var button = frameui.button("Ngon Ngu");
	button.mouseClick();

	var dlMain = frameui.dialog("dlNgonNgu");
	var pnChild = dlMain.panel("Ngon Ngu");

	// TEST LƯU NHẬP ĐÚNG DỮ LIỆU CÁC TRƯỜNG
	frameui.showDialogTest("TEST chức năng Lưu");
	frameui.delay();
	pnChild.fieldSet("txtLabel", "NN HktTest1");
	pnChild.fieldSet("txtCode", "Mã NN HktTest1");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest1");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Lưu thành công");
	frameui.delay();
	
	// BỎ TRỐNG CÁC TRƯỜNG VÀ LƯU
	if(frameui.showDialogTest("Lỗi chưa nhập dữ liệu các trường")){
		pnChild.fieldSet("txtCode", "");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// BỎ TRỐNG TRƯỜNG MÃ VÀ LƯU
	if(frameui.showDialogTest("Lỗi chưa nhập Mã NN")){
		frameui.delay();
		pnChild.fieldSet("txtLabel", "NN HktTest1");
		pnChild.fieldSet("txtCode", "");
		pnChild.fieldSet("txtDescription", "Miêu tả HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
	
	// NHẬP TRÙNG MÃ VÀ LƯU
	if(frameui.showDialogTest("Lỗi nhập trùng Mã NN")){	
		frameui.delay();
		pnChild.fieldSet("txtLabel", "NN HktTest2");
		pnChild.fieldSet("txtCode", "Mã NN HktTest1");
		pnChild.fieldSet("txtDescription", "Miêu tả HktTest2");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}
	
	// BỎ TRỐNG TRƯỜNG TÊN VÀ LƯU
	if(frameui.showDialogTest("Lỗi chưa nhập Tên NN")){
		frameui.delay();
		pnChild.fieldSet("txtLabel", "");
		pnChild.fieldSet("txtCode", "Mã NN HktTest2");
		pnChild.fieldSet("txtDescription", "Miêu tả HktTest2");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// TEST XEM LẠI: sửa giá trị ban đầu từ 1 thành 2 và xem lại
	frameui.showDialogTest("TEST chức năng Xem lại");
	frameui.delay();
	var table = pnChild.table("tbOptions");
	table.doubleClickRow("Mã NN HktTest1");
	dlMain.buttonClick("Sửa");
	frameui.delay();
	pnChild.fieldSet("txtLabel", "NN HktTest2");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest2");
	dlMain.buttonClick("Xem lại");
	frameui.delay();

	// TEST SỬA: sửa giá trị ban đầu từ 1 thành 2 và lưu
	frameui.showDialogTest("TEST chức năng Sửa");
	frameui.delay();
	dlMain.buttonClick("Sửa");
	pnChild.fieldSet("txtLabel", "NN HktTest2");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest2");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Sửa thành công");
	frameui.delay();

	// TEST VIẾT LẠI: reset các trường
	frameui.showDialogTest("TEST chức năng Viết lại")
	frameui.delay();
	table.doubleClickRow("NN HktTest2");
	dlMain.buttonClick("Viết lại");

	// TEST THÊM MỚI 1 NN SO SÁNH VỚI NN BAN ĐẦU
	frameui.showDialogTest("TEST chức năng Thêm mới")
	frameui.delay();
	pnChild.fieldSet("txtLabel", "NN HktTest3");
	pnChild.fieldSet("txtCode", "Mã NN HktTest3");
	pnChild.fieldSet("txtDescription", "Miêu tả HktTest3");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Thêm mới thành công");
	frameui.delay();

	// TEST THAY ĐỔI VỊ TRÍ ƯU TIÊN
	frameui.showDialogTest("TEST chức năng Vị trí");
	frameui.delay();
	table.selectRow("Mã NN HktTest3");
	dlMain.buttonClickByName("btnUp")
	pnChild.buttonClick("Cập nhật")
	button.mouseClick();
	
	// TEST XÓA
	frameui.showDialogTest("TEST chức năng Xóa");
	frameui.delay();
	var table = pnChild.table("tbOptions");
	table.doubleClickRow("Mã NN HktTest1");
	dlMain.buttonClick("Sửa");
	dlMain.buttonClick("Xóa");
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");
	frameui.showDialogTest("Xóa thành công");
	frameui.delay();

	// Thoat. oke Test finish!
	frameui.showDialogTest("Chương trình TEST Ngôn Ngữ kết thúc!");
	frameui.delay();
	pnChild.deleteDataTest();
	dlMain.buttonClick("Thoát");

}

var robot = new Robot();
robot.add("TEST NgonNgu ", "", CreateNgonNgu);
console.clear();
robot.run();
robot.report();
