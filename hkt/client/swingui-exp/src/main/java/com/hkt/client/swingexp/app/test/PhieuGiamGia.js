ScriptRunner.require("robotExp.js");

function CreatePhieuGiamGia(frameui) {
	/**
	 * @ edit: Hạnh: Thêm if vào Test Lỗi
	 */
	frameui.showDialogTest("Chương trình TEST Phiếu giảm giá");
	frameui.delay();

	var buttonMenu = frameui.button("menuRightKhuyenMai");
	buttonMenu.mouseClick();
	var button = frameui.button("PhieuGiamGia");
	button.mouseClick();

	var dlMain = frameui.dialog("dlPhieuGiamGia");
	var pnMain = dlMain.panel("PhieuGiamGia");

	// TEST LƯU NHẬP ĐÚNG DỮ LIỆU CÁC TRƯỜNG
	frameui.showDialogTest("TEST chức năng Lưu");
	frameui.delay();
	pnMain.fieldSet("txtTenPhieu", "Tên PGG HktTest1");
	pnMain.fieldSet("txtMaPhieu", "Mã PGG HktTest1");
	var comboBoxDonViTien = pnMain.comboBox("cbDonViTien");
	comboBoxDonViTien.selectItem("TT HktTest1");
	pnMain.fieldSet("txtGiaTri", "50000");
	pnMain.fieldSet("txtSoLanDung", "3");
	pnMain.fieldSet("txtMieuTa", "Miêu tả PGG HktTest1");
	dlMain.buttonClick("Lưu");
	frameui.delay();
	frameui.showDialogTest("Lưu thành công !");
	frameui.delay();

	// BỎ TRỐNG CÁC TRƯỜNG VÀ LƯU
	if (frameui
			.showDialogTest("Lỗi chưa nhập dữ liệu các trường")) {
		frameui.delay();
		pnMain.fieldSet("txtMaPhieu", "");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// BỎ TRỐNG TRƯỜNG MÃ VÀ LƯU
	if (frameui.showDialogTest("Lỗi chưa nhập Mã PGG")) {
		frameui.delay();
		pnMain.fieldSet("txtTenPhieu", "Tên PGG HktTest1");
		pnMain.fieldSet("txtMaPhieu", "");
		pnMain.fieldSet("txtGiaTri", "50000");
		pnMain.fieldSet("txtSoLanDung", "3");
		pnMain.fieldSet("txtMieuTa", "Miêu tả PGG HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// NHẬP TRÙNG MÃ VÀ LƯU
	if (frameui
			.showDialogTest("Lỗi nhập trùng Mã PGG")) {
		frameui.delay();
		pnMain.fieldSet("txtTenPhieu", "Tên PGG HktTest1");
		pnMain.fieldSet("txtMaPhieu", "Mã PGG HktTest2");
		pnMain.fieldSet("txtGiaTri", "20000");
		pnMain.fieldSet("txtSoLanDung", "2");
		pnMain.fieldSet("txtMieuTa", "Miêu tả PGG HktTest2");
		dlMain.buttonClick("Lưu");
		frameui.showDialogTest("Mã bị trùng");
		frameui.delay();
	}

	// BỎ TRỐNG TRƯỜNG TÊN VÀ LƯU
	if (frameui
			.showDialogTest("Lỗi chưa nhập Tên PGG")) {
		frameui.delay();
		pnMain.fieldSet("txtTenPhieu", "");
		pnMain.fieldSet("txtMaPhieu", "Mã PGG HktTest1");
		pnMain.fieldSet("txtGiaTri", "50000");
		pnMain.fieldSet("txtSoLanDung", "3");
		pnMain.fieldSet("txtMieuTa", "Miêu tả PGG HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// BỎ TRỐNG TRƯỜNG TỶ LỆ VÀ LƯU
	if (frameui
			.showDialogTest("Lỗi chưa nhập Giá trị PGG")) {
		frameui.delay();
		pnMain.fieldSet("txtTenPhieu", "Tên PGG HktTest1");
		pnMain.fieldSet("txtMaPhieu", "Mã PGG HktTest1");
		pnMain.fieldSet("txtGiaTri", "");
		pnMain.fieldSet("txtSoLanDung", "3");
		pnMain.fieldSet("txtMieuTa", "Miêu tả PGG HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// NHẤP SAI ĐỊNH DẠNG GIÁ TRỊ VÀ LƯU
	if (frameui
			.showDialogTest("Lỗi nhập sai định dạng Giá trị PGG")) {
		frameui.delay();
		pnMain.fieldSet("txtTenPhieu", "Tên PGG HktTest1");
		pnMain.fieldSet("txtMaPhieu", "Mã PGG HktTest1");
		pnMain.fieldSet("txtGiaTri", "HktTest");
		pnMain.fieldSet("txtSoLanDung", "3");
		pnMain.fieldSet("txtMieuTa", "Miêu tả PGG HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// NHẤP SAI ĐỊNH DẠNG SL DÙNG VÀ LƯU
	if (frameui
			.showDialogTest("Lỗi nhập sai định dạng SL dùng")) {
		frameui.delay();
		pnMain.fieldSet("txtTenPhieu", "Tên PGG HktTest1");
		pnMain.fieldSet("txtMaPhieu", "Mã PGG HktTest1");
		pnMain.fieldSet("txtGiaTri", "50000");
		pnMain.fieldSet("txtSoLanDung", "HktTest");
		pnMain.fieldSet("txtMieuTa", "Miêu tả PGG HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// NHẤP SAI ĐỊNH DẠNG Số CT VÀ LƯU
	if (frameui
			.showDialogTest("Lỗi nhập sai định dạng Số CT")) {
		frameui.delay();
		pnMain.fieldSet("txtTenPhieu", "Tên PGG HktTest1");
		pnMain.fieldSet("txtMaPhieu", "Mã PGG HktTest1");
		pnMain.fieldSet("txtGiaTri", "50000");
		pnMain.fieldSet("txtSoLanDung", "5");
		pnMain.fieldSet("txtSoCT", "HktTest");
		pnMain.fieldSet("txtMieuTa", "Miêu tả PGG HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
	}

	// KẾT THÚC GIAO DIỆN PGG
	frameui.showDialogTest("Thoát khỏi giao diện PGG");
	frameui.delay();
	dlMain.buttonClick("Thoát");
	// Kết thúc giao diện thêm mới
}

function CreateDanhSachPhieuGiamGia(frameui) {

	// Vào giao diện DS PGG và click xem chi tiết
	frameui.showDialogTest("Xem chi tiết PGG vừa thêm mới");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightKhuyenMai");
	buttonMenu.mouseClick();
	var button = frameui.button("DanhSachPhieuGiamGia");
	button.mouseClick();
	var dlMain = frameui.dialog("dlDanhSachPhieuGiamGia");
	var tableQuanLyHangHoa = dlMain.table("tbDanhSachPhieuGiamGia");

	// CLICK VÀO PGG VỪA TẠO
	tableQuanLyHangHoa.doubleClickRow("Tên PGG HktTest1");
	var dlChild = frameui.dialog("dlPhieuGiamGia");
	var pnChild = dlChild.panel("PhieuGiamGia");

	// TEST XEM LẠI: sửa giá trị ban đầu từ 1 thành 2 và xem lại
	frameui.showDialogTest("TEST chức năng Xem lại");
	frameui.delay();
	dlChild.buttonClick("Sửa");
	pnChild.fieldSet("txtTenPhieu", "Tên PGG HktTest2");
	pnChild.fieldSet("txtGiaTri", "20000");
	var comboBoxDonViTien = pnChild.comboBox("cbDonViTien");
	comboBoxDonViTien.selectItem("TT HktTest1");
	pnChild.fieldSet("txtSoLanDung", "2");
	pnChild.fieldSet("txtMieuTa", "Miêu tả PGG HktTest2");
	var radioButton = pnChild.radioButton("rdoPhanTram");
	radioButton.mouseClick();
	pnChild.fieldSet("txtGiaTri", "20");
	dlChild.buttonClick("Xem lại");
	frameui.delay();

	// TEST SỬA: sửa giá trị ban đầu từ 1 thành 2 và lưu
	frameui.showDialogTest("TEST chức năng Sửa");
	frameui.delay();
	dlChild.buttonClick("Sửa");
	pnChild.fieldSet("txtTenPhieu", "Tên PGG HktTest2");
	pnChild.fieldSet("txtGiaTri", "20000");
	comboBoxDonViTien.selectItem("TT HktTest1");
	pnChild.fieldSet("txtSoLanDung", "2");
	pnChild.fieldSet("txtMieuTa", "Miêu tả PGG HktTest2");
	radioButton.mouseClick();
	pnChild.fieldSet("txtGiaTri", "20");
	dlChild.buttonClick("Lưu");
	frameui.delay();
	frameui.showDialogTest("Sửa thành công !");
	frameui.delay();
	dlChild.buttonClick("Thoát");

	// TEST VIẾT LẠI: reset các trường
	frameui.showDialogTest("TEST chức năng Viết lại")
	frameui.delay();
	tableQuanLyHangHoa.doubleClickRow("Mã PGG HktTest1");
	var dlChild = frameui.dialog("dlPhieuGiamGia");
	var pnChild = dlChild.panel("PhieuGiamGia");
	dlChild.buttonClick("Viết lại");
	frameui.delay();

	// TEST THÊM MỚI 1 BẢNG GIÁ SO SÁNH VỚI BẢNG GIÁ BAN ĐẦU
	frameui.showDialogTest("TEST chức năng Thêm mới")
	frameui.delay();
	pnChild.fieldSet("txtTenPhieu", "Tên PGG HktTest3");
	pnChild.fieldSet("txtMaPhieu", "Mã PGG HktTest3");
	pnChild.fieldSet("txtGiaTri", "30");
	var comboBoxDonViTien = pnChild.comboBox("cbDonViTien");
	comboBoxDonViTien.selectItem("TT HktTest1");
	pnChild.fieldSet("txtSoLanDung", "3");
	pnChild.fieldSet("txtMieuTa", "Miêu tả PGG HktTest3");
	dlChild.buttonClick("Lưu");
	frameui.delay();
	frameui.showDialogTest("Thêm mới thành công !");

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
	comboBox.selectItem("Mã phiếu");
	dlMain.fieldSet("txtSearch", "3");
	frameui.delay();

	// TEST XÓA
	frameui.showDialogTest("TEST chức năng Xóa");
	frameui.delay();
	tableQuanLyHangHoa.doubleClickRow("Mã PGG HktTest3");
	var dlChild = frameui.dialog("dlPhieuGiamGia");
	dlChild.buttonClick("Sửa");
	dlChild.buttonClick("Xóa");
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");
	frameui.showDialogTest("Xóa thành công!");
	frameui.delay();

	// THOÁT KHỎI GIAO DIỆN TEST VÀ XÓA DỮ LIỆU SAU KHI TEST
	frameui
			.showDialogTest("Chương trình TEST Quản lý bảng giá kết thúc!");
	frameui.delay();
	pnChild.deleteDataTest();
	dlChild.buttonClick("Thoát");
	dlMain.buttonClickByName("btnExit");
}

var robot = new Robot();
robot.add("TEST PhieuGiamGia ", "", CreatePhieuGiamGia);
robot.add("TEST DanhSachPhieuGiamGia ", "", CreateDanhSachPhieuGiamGia);
console.clear();
robot.run();
robot.report();