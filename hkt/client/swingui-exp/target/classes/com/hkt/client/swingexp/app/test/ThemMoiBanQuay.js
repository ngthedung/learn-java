ScriptRunner.require("robotExp.js");

function CreateThemMoiBanQuay(frameui) {
	/**
	 * @ edit: Hạnh: Thêm if vào Test Lỗi
	 */
	frameui.showDialogTest("Chương trình TEST Thêm mới bàn/quầy");
	frameui.delay();

	var buttonMenu = frameui.button("menuRightKhuVucBanHang");
	buttonMenu.mouseClick();
	var button = frameui.button("ThemMoiBanQuay");
	button.mouseClick();

	var dlMain = frameui.dialog("dlThemMoiBanQuay");
	var pnMain = dlMain.panel("ThemMoiBanQuay");

	// TEST LƯU NHẬP ĐÚNG DỮ LIỆU CÁC TRƯỜNG
	frameui.showDialogTest("TEST chức năng Lưu");
	frameui.delay();
	pnMain.fieldSet("txtTenBan", "Tên bàn HktTest1");
	pnMain.fieldSet("txtMaBan", "Mã bàn HktTest1");
	var comboBoxKhuVuc = pnMain.comboBox("cbKhuVuc");
	comboBoxKhuVuc.selectItem("KV HktTest1");
	pnMain.fieldSet("txtMieuTa", "Miêu tả HktTest1");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Lưu thành công");
	frameui.delay();

	// BỎ TRỐNG CÁC TRƯỜNG VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa nhập dữ liệu các trường")) {
		frameui.delay();
		pnMain.fieldSet("txtMaBan", "");
		dlMain.buttonClick("Lưu");
	}

	// BỎ TRỐNG TRƯỜNG MÃ VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa nhập Mã Bàn/Quầy")) {
		frameui.delay();
		pnMain.fieldSet("txtTenBan", "Tên bàn HktTest2");
		pnMain.fieldSet("txtMaBan", "");
		var comboBoxKhuVuc = pnMain.comboBox("cbKhuVuc");
		comboBoxKhuVuc.selectItem("KV HktTest2");
		pnMain.fieldSet("txtMieuTa", "Miêu tả HktTest2");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// BỎ TRỐNG TRƯỜNG TÊN VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa nhập Tên Bàn/Quầy")) {
		frameui.delay();
		pnMain.fieldSet("txtTenBan", "");
		pnMain.fieldSet("txtMaBan", "Mã bàn HktTest2");
		var comboBoxKhuVuc = pnMain.comboBox("cbKhuVuc");
		comboBoxKhuVuc.selectItem("KV HktTest2");
		pnMain.fieldSet("txtMieuTa", "Miêu tả HktTest2");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// NHẬP TRÙNG MÃ VÀ LƯU
	if (frameui.showDialogTest("Lỗi nhập trùng Mã Bàn/Quầy")) {
		frameui.delay();
		pnMain.fieldSet("txtTenBan", "Tên bàn HktTest2");
		pnMain.fieldSet("txtMaBan", "Mã bàn HktTest1");
		var comboBoxKhuVuc = pnMain.comboBox("cbKhuVuc");
		comboBoxKhuVuc.selectItem("KV HktTest2");
		pnMain.fieldSet("txtMieuTa", "Miêu tả HktTest2");
		dlMain.buttonClick("Lưu");
	}

	// KẾT THÚC GIAO DIỆN THÊM B/Q
	frameui.showDialogTest("Thoát khỏi giao diện Thêm mới bàn/quầy");
	frameui.delay();
	dlMain.buttonClick("Thoát");

}

function CreateDanhSachBanQuay(frameui) {

	// Vào giao diện DS Bàn/Quầy và click xem chi tiết
	frameui.showDialogTest("Xem chi tiết Bàn/Quầy vừa thêm mới");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightKhuVucBanHang");
	buttonMenu.mouseClick();
	var button = frameui.button("DanhSachBanQuay");
	button.mouseClick();
	var dlMain = frameui.dialog("dlDanhSachBanQuay");
	var tableQuanLyHangHoa = dlMain.table("tbDanhSachBanQuay");

	// Click dòng vừa thêm có giá trị code = Mã HktTest1
	tableQuanLyHangHoa.doubleClickRow("Mã bàn HktTest1");
	frameui.delay();

	var dlChild = frameui.dialog("dlThemMoiBanQuay");
	var pnChild = dlChild.panel("ThemMoiBanQuay");

	// TEST XEM LẠI: sửa giá trị ban đầu từ 1 thành 2 và xem lại
	frameui.showDialogTest("TEST chức năng Xem lại");
	frameui.delay();
	dlChild.buttonClick("Sửa");
	pnChild.fieldSet("txtTenBan", "Tên bàn HktTest2");
	var comboBoxKhuVuc = pnChild.comboBox("cbKhuVuc");
	comboBoxKhuVuc.selectItem("KV HktTest2");
	pnChild.fieldSet("txtMieuTa", "Miêu tả HktTest2");
	dlChild.buttonClick("Xem lại");
	frameui.delay();

	// TEST SỬA: sửa giá trị ban đầu từ 1 thành 2 và lưu
	frameui.showDialogTest("TEST chức năng Sửa");
	frameui.delay();
	dlChild.buttonClick("Sửa");
	pnChild.fieldSet("txtTenBan", "Tên bàn HktTest2");
	var comboBoxKhuVuc = pnChild.comboBox("cbKhuVuc");
	comboBoxKhuVuc.selectItem("KV HktTest2");
	pnChild.fieldSet("txtMieuTa", "Miêu tả HktTest2");
	dlChild.buttonClick("Lưu");
	frameui.showDialogTest("Sửa thành công");
	dlChild.buttonClick("Thoát");

	// TEST VIẾT LẠI: reset các trường
	frameui.showDialogTest("TEST chức năng Viết lại")
	frameui.delay();
	tableQuanLyHangHoa.doubleClickRow("Mã bàn HktTest1");
	var dlChild = frameui.dialog("dlThemMoiBanQuay");
	var pnChild = dlChild.panel("ThemMoiBanQuay");
	dlChild.buttonClickByName("btnReset");

	// TEST THÊM MỚI 1 BQ SO SÁNH VỚI BQ BAN ĐẦU
	frameui.showDialogTest("TEST chức năng Thêm mới")
	frameui.delay();
	pnChild.fieldSet("txtTenBan", "Tên bàn HktTest3");
	pnChild.fieldSet("txtMaBan", "Mã bàn HktTest3");
	var comboBoxKhuVuc = pnChild.comboBox("cbKhuVuc");
	comboBoxKhuVuc.selectItem("KV HktTest3");
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
	frameui.delay();
	dlMain.buttonClick("<<");
	frameui.delay();

	// TEST TÌM KIẾM
	frameui.showDialogTest("TEST chức năng Tìm kiếm -> tìm theo tên");
	frameui.delay();
	var comboBox = dlMain.comboBox("cbSearch");
	comboBox.selectItem("Tên bàn");
	dlMain.fieldSet("txtSearch", "Tên bàn HktTest3");
	frameui.delay();

	// TEST XÓA
	frameui.showDialogTest("TEST chức năng Xóa");
	frameui.delay();
	tableQuanLyHangHoa.doubleClickRow("Tên bàn HktTest3");
	var dlChild = frameui.dialog("dlThemMoiBanQuay");
	dlChild.buttonClick("Sửa");
	dlChild.buttonClick("Xóa");
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");
	frameui.showDialogTest("Xóa thành công!");

	// THOÁT KHỎI GIAO DIỆN TEST VÀ XÓA DỮ LIỆU SAU KHI TEST
	frameui
			.showDialogTest("Chương trình TEST giao diện Thêm mới bàn/quầy kết thúc!");
	frameui.delay();
	pnChild.deleteDataTest();
	frameui.delay();
	dlChild.buttonClick("Thoát");
	dlMain.buttonClickByName("btnExit");
}

var robot = new Robot();
robot.add("TEST ThemMoiBanQuay ", "", CreateThemMoiBanQuay);
robot.add("TEST DanhSachBanQuay ", "", CreateDanhSachBanQuay);
console.clear();
robot.run();
robot.report();