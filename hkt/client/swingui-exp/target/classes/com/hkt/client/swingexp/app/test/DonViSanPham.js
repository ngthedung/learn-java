ScriptRunner.require("robotExp.js");

function CreateDonViSanPham(frameui) {
	/**
	 * @ edit: Hạnh: Thêm if vào Test Lỗi
	 */
	frameui.showDialogTest("Chương trình TEST Đơn vị sản phẩm");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightHeThong");
	buttonMenu.mouseClick();
	var button = frameui.button("DonViSanPham");
	button.mouseClick();
	var dlMain = frameui.dialog("dlDonViSanPham");
	var pnChild = dlMain.panel("DonViSanPham");

	// TEST LƯU NHẬP ĐÚNG DỮ LIỆU CÁC TRƯỜNG
	frameui.showDialogTest("TEST chức năng Lưu");
	frameui.delay();
	pnChild.fieldSet("txtCode", "Mã ĐVSP HktTest1");
	pnChild.fieldSet("txtName", "Tên ĐVSP HktTest1");
	pnChild.fieldSet("txtRate", "1");
	pnChild.fieldSet("txtDescription", "Miêu tả ĐVSP HktTest1");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest(" Lưu thành công !");
	frameui.delay();

	// BỎ TRỐNG CÁC TRƯỜNG VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa nhập dữ liệu các trường")) {
		pnChild.fieldSet("txtCode", "");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// BỎ TRỐNG TRƯỜNG MÃ VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa nhập Mã ĐVSP")) {
		frameui.delay();
		pnChild.fieldSet("txtCode", "");
		pnChild.fieldSet("txtName", "Tên ĐVSP HktTest1");
		pnChild.fieldSet("txtRate", "1");
		pnChild.fieldSet("txtDescription", "Miêu tả ĐVSP HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// NHẬP TRÙNG MÃ VÀ LƯU
	if (frameui.showDialogTest("Lỗi nhập trùng Mã ")) {
		frameui.delay();
		pnChild.fieldSet("txtCode", "Mã ĐVSP HktTest1");
		pnChild.fieldSet("txtName", "Tên ĐVSP HktTest2");
		pnChild.fieldSet("txtRate", "1");
		pnChild.fieldSet("txtDescription", "Miêu tả ĐVSP HktTest2");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// BỎ TRỐNG TRƯỜNG ĐVSP VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa nhập Tên ĐVSP")) {
		frameui.delay();
		pnChild.fieldSet("txtCode", "Mã ĐVSP HktTest1");
		pnChild.fieldSet("txtName", "");
		pnChild.fieldSet("txtRate", "1");
		pnChild.fieldSet("txtDescription", "Miêu tả ĐVSP HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// BỎ TRỐNG TRƯỜNG TỶ LỆ VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa nhập Tỷ lệ")) {
		frameui.delay();
		pnChild.fieldSet("txtCode", "Mã ĐVSP HktTest2");
		pnChild.fieldSet("txtName", "Tên ĐVSP HktTest2");
		pnChild.fieldSet("txtRate", "");
		pnChild.fieldSet("txtDescription", "Miêu tả ĐVSP HktTest2");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// NHẬP SAI ĐỊNH DẠNG TỶ LỆ VÀ LƯU
	if (frameui.showDialogTest("Lỗi nhập định dạng Tỷ lệ ")) {
		frameui.delay();
		pnChild.fieldSet("txtCode", "Mã ĐVSP HktTest2");
		pnChild.fieldSet("txtName", "Tên ĐVSP HktTest2");
		pnChild.fieldSet("txtRate", "HktTest2");
		pnChild.fieldSet("txtDescription", "Miêu tả ĐVSP HktTest2");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// TEST XEM LẠI: sửa giá trị ban đầu từ 1 thành 2 và xem lại
	frameui.showDialogTest("TEST chức năng Xem lại");
	frameui.delay();
	dlMain.buttonClick("Viết lại");
	var table = pnChild.table("tbproductUnitTable");
	table.doubleClickRow("Mã ĐVSP HktTest1");
	dlMain.buttonClick("Sửa");
	pnChild.fieldSet("txtName", "Tên ĐVSP HktTest2");
	pnChild.fieldSet("txtDescription", "Miêu tả ĐVSP HktTest2");
	dlMain.buttonClick("Xem lại");
	frameui.delay();

	// TEST SỬA: sửa giá trị ban đầu từ 1 thành 2 và lưu
	frameui.showDialogTest("TEST chức năng Sửa");
	frameui.delay();
	dlMain.buttonClick("Sửa");
	pnChild.fieldSet("txtName", "Tên ĐVSP HktTest2");
	pnChild.fieldSet("txtRate", "2");
	pnChild.fieldSet("txtDescription", "Miêu tả ĐVSP HktTest2");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Sửa thành công!");
	frameui.delay();

	// TEST VIẾT LẠI: reset các trường
	frameui.showDialogTest("TEST chức năng Viết lại")
	frameui.delay();
	table.doubleClickRow("Mã ĐVSP HktTest1");
	dlMain.buttonClick("Viết lại");

	// TEST THÊM MỚI 1 TT SO SÁNH VỚI TT BAN ĐẦU
	frameui.showDialogTest("TEST chức năng Thêm mới")
	frameui.delay();
	pnChild.fieldSet("txtCode", "Mã ĐVSP HktTest3");
	pnChild.fieldSet("txtName", "Tên ĐVSP HktTest3");
	pnChild.fieldSet("txtRate", "3");
	pnChild.fieldSet("txtDescription", "Miêu tả ĐVSP HktTest3");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Thêm mới thành công !");
	frameui.delay();

	// chạy thử chức năng thay đổi vị trí
	frameui.showDialogTest("Chương trình chạy thử chức năng vị trí");
	frameui.delay();
	table.selectRow("Mã ĐVSP HktTest1");
	dlMain.buttonClickByName("btnUp")
	pnChild.buttonClick("Cập nhật")
	frameui.delay();

	// TEST THAY ĐỔI VỊ TRÍ ƯU TIÊN
	frameui.showDialogTest("TEST chức năng Vị trí");
	frameui.delay();
	var table = pnChild.table("tbproductUnitTable");
	table.doubleClickRow("Mã ĐVSP HktTest3");
	dlMain.buttonClick("Sửa");
	dlMain.buttonClick("Xóa");
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");

	// Thoat. oke Test finish!
	frameui.showDialogTest("Chương trình TEST Đơn vị sản phẩm kết thúc!");
	frameui.delay();
	pnChild.deleteDataTest();
	dlMain.buttonClick("Thoát");

}
var robot = new Robot();
robot.add("TEST Don Vi San Pham ", "", CreateDonViSanPham);
console.clear();
robot.run();
robot.report();
