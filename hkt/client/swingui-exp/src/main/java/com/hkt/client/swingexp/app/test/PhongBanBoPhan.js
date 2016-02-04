ScriptRunner.require("robotExp.js");

function CreatePhongBanBoPhan(frameui) {
	/**
	 * @ edit: Hạnh: Thêm if vào Test Lỗi
	 */
	frameui.showDialogTest("Chương trình TEST Phòng ban bộ phận");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightHeThong");
	buttonMenu.mouseClick();
	var button = frameui.button("PhongBanBoPhan");
	button.mouseClick();

	var dlMain = frameui.dialog("dlPhongBanBoPhan");
	var pnMain = dlMain.panel("PhongBanBoPhan");

	// TEST LƯU NHẬP ĐÚNG DỮ LIỆU CÁC TRƯỜNG
	frameui.showDialogTest("TEST chức năng Lưu");
	frameui.delay();
	pnMain.fieldSet("txtTen", "Tên HktTest1");
	pnMain.fieldSet("txtMa", "Mã HktTest1");
	pnMain.fieldSet("txtMieuTa", "Miêu tả HktTest1");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Lưu thành công");
	frameui.delay();

	// BỎ TRỐNG CÁC TRƯỜNG VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa nhập dữ liệu các trường")) {
		frameui.delay();
		pnMain.fieldSet("txtMa", "");
	}

	// BỎ TRỐNG TRƯỜNG MÃ VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa nhập Mã phòng ban")) {
		frameui.delay();
		pnMain.fieldSet("txtTen", "Tên HktTest2");
		pnMain.fieldSet("txtMa", "");
		pnMain.fieldSet("txtMieuTa", "Miêu tả HktTest2");
		dlMain.buttonClick("Lưu");
	}

	// BỎ TRỐNG TRƯỜNG TÊN VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa nhập Tên phòng ban")) {
		frameui.delay();
		pnMain.fieldSet("txtTen", "");
		pnMain.fieldSet("txtMa", "Mã HktTest2");
		pnMain.fieldSet("txtMieuTa", "Miêu tả HktTest2");
		dlMain.buttonClick("Lưu");
	}

	// NHẬP TRÙNG MÃ VÀ LƯU
	if (frameui.showDialogTest("Lỗi nhập trùng Mã phòng ban")) {
		frameui.delay();
		pnMain.fieldSet("txtTen", "Tên HktTest2");
		pnMain.fieldSet("txtMa", "Mã HktTest1");
		pnMain.fieldSet("txtMieuTa", "Miêu tả HktTest2");
		dlMain.buttonClick("Lưu");
	}

	// KẾT THÚC GIAO DIỆN PHÒNG BAN BỘ PHẬN
	frameui.showDialogTest("Thoát khỏi giao diện Phòng ban bộ phận");
	frameui.delay();
	dlMain.buttonClick("Thoát");
}

function CreateDanhSachPhongBanBoPhan(frameui) {

	// Vào giao diện DS Bàn/Quầy và click xem chi tiết
	frameui.showDialogTest("Xem chi tiết Phòng ban vừa thêm mới");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightHeThong");
	buttonMenu.mouseClick();
	var button = frameui.button("DanhSachPhongBanBoPhan");
	button.mouseClick();
	var dlMain = frameui.dialog("dlDanhSachPhongBanBoPhan");
	var tableQuanLyHangHoa = dlMain.table("tbDanhSachPhongBanBoPhan");

	// Click dòng vừa thêm có giá trị code = Mã HktTest1

	tableQuanLyHangHoa.doubleClickRow("Mã HktTest1");
	frameui.delay();

	var dlChild = frameui.dialog("dlPhongBanBoPhan");
	var pnChild = dlChild.panel("PhongBanBoPhan");

	// TEST XEM LẠI: sửa giá trị ban đầu từ 1 thành 2 và xem lại
	frameui.showDialogTest("TEST chức năng Xem lại");
	frameui.delay();
	dlChild.buttonClick("Sửa");
	var comboBoxPhongTrucThuoc = pnChild.comboBox("cbPhongTrucThuoc");
	comboBoxPhongTrucThuoc.selectItem("Tên HktTest1");
	pnChild.fieldSet("txtTen", "Tên HktTest2");
	pnChild.fieldSet("txtMieuTa", "Miêu tả HktTest2");
	dlChild.buttonClick("Xem lại");
	frameui.delay();

	// TEST SỬA: sửa giá trị ban đầu từ 1 thành 2 và lưu
	frameui.showDialogTest("TEST chức năng Sửa");
	frameui.delay();
	dlChild.buttonClick("Sửa");
	pnChild.fieldSet("txtTen", "Tên HktTest2");
	pnChild.fieldSet("txtMieuTa", "Miêu tả HktTest2");
	dlChild.buttonClick("Lưu");
	frameui.delay();
	frameui.showDialogTest("Sửa thành công");
	dlChild.buttonClick("Thoát");

	// TEST VIẾT LẠI: reset các trường
	frameui.showDialogTest("TEST chức năng Viết lại")
	frameui.delay();
	tableQuanLyHangHoa.doubleClickRow("Mã HktTest1");
	var dlChild = frameui.dialog("dlPhongBanBoPhan");
	var pnChild = dlChild.panel("PhongBanBoPhan");
	dlChild.buttonClick("Viết lại");
	
	// TEST THÊM MỚI 1 PB SO SÁNH VỚI PB BAN ĐẦU
	frameui.showDialogTest("TEST chức năng Thêm mới")
	frameui.delay();
	pnChild.fieldSet("txtTen", "Tên HktTest3");
	pnChild.fieldSet("txtMa", "Mã HktTest3");
	pnChild.fieldSet("txtMieuTa", "Miêu tả HktTest3");
	dlChild.buttonClick("Lưu");
	frameui.showDialogTest("Thêm mới thành công");
	frameui.delay();

	// THÊM 50 BẢN GHI TEST CHUYỂN TRANG
	frameui.showDialogTest(" TEST chức năng Chuyển trang");
	frameui.delay();
	pnChild.createDataTest();
	dlChild.buttonClick("Thoát");

	// CLICK SANG TRANG 2
	frameui.showDialogTest("Sang trang 2");
	frameui.delay();
	dlMain.buttonClick("2");
	frameui.delay();
	frameui.showDialogTest("Trở về trang đầu tiên");
	dlMain.buttonClick("<<");
	frameui.delay();

	// TEST TÌM KIẾM
	frameui.showDialogTest("TEST chức năng Tìm kiếm -> tìm theo mã");
	frameui.delay();
	var comboBox = dlMain.comboBox("cbSearch");
	comboBox.selectItem("Mã");
	dlMain.fieldSet("txtSearch", "3");
	frameui.delay();

	// TEST XÓA
	frameui.showDialogTest("TEST chức năng Xóa");
	frameui.delay();
	tableQuanLyHangHoa.doubleClickRow("Mã HktTest3");
	var dlChild = frameui.dialog("dlPhongBanBoPhan");
	dlChild.buttonClick("Sửa");
	dlChild.buttonClick("Xóa");
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");
	frameui.showDialogTest("Xóa thành công");
	
	// THOÁT KHỎI GIAO DIỆN TEST VÀ XÓA DỮ LIỆU SAU KHI TEST
	frameui
			.showDialogTest("Chương trình TEST giao diện Phòng ban bộ phận kết thúc!");
	frameui.delay();
	pnChild.deleteDataTest();
	dlMain.buttonClickByName("btnExit");
}

var robot = new Robot();
robot.add("TEST PhongBanBoPhan ", "", CreatePhongBanBoPhan);
robot.add("TEST DanhSachPhongBanBoPhan ", "", CreateDanhSachPhongBanBoPhan);
console.clear();
robot.run();
robot.report();