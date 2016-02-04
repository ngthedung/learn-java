ScriptRunner.require("robotExp.js");

function createLapKhuyenMai(frameui) {
	/**
	 * @ edit: Hạnh: Thêm if vào Test Lỗi
	 */
	frameui.showDialogTest("Chương trình TEST Lập khuyến mãi");
	frameui.delay();

	//KỊCH BẢN CHUẨN
	var buttonMenu = frameui.button("menuRightKhuyenMai");
	buttonMenu.mouseClick();
	var button = frameui.button("LapKhuyenMai");
	button.mouseClick();
	var dlMain = frameui.dialog("dlLapKM");
	var pnMain = dlMain.panel("LapKhuyenMai");
	dlMain.buttonClick("Lưu");
	frameui.showDialogTest("Thiếu các thông tin quan trọng");
	frameui.delay();
	dlMain.buttonClick("Viết lại");
	
	frameui.showDialogTest("Các bước thông tin chuẩn cần điền");
	frameui.delay();
	var chbTypeStore = pnMain.checkBox("chbTypeStore");
	chbTypeStore.mouseClick();
	pnMain.fieldSet("txtTenKM", "Khuyến mại HktTest1");
	pnMain.fieldSet("txtSoLanSuDung", "1");
	pnMain.fieldSet("txtKMPhanTram", "1");
	dlMain.buttonClick("Lưu");
	
	frameui.showDialogTest("Chương trình chạy thử chức năng xem và xem lại");
	frameui.delay();
	dlMain.buttonClick("Thoát");
	
	
	

	
//	// TEST LƯU NHẬP ĐÚNG DỮ LIỆU CÁC TRƯỜNG
//	frameui.showDialogTest("TEST chức năng Lưu");
//	frameui.delay();
//	// **chọn nhóm sp
//	var comboBoxGroupProduct = pnMain.comboBox("cbNhomSanPham");
//	comboBoxGroupProduct.selectItem("NSP HktTest1");
//	// **tick chọn nhóm sp
//	var checkBoxGroupProduct = pnMain.checkBox("chkNhomSanPham");
//	checkBoxGroupProduct.mouseClick();
//	// **tick chọn đối tác
//	var checkBoxPartner = pnMain.checkBox("chkDoiTac");
//	checkBoxPartner.mouseClick();
//	// **tick chọn nhóm khu vực
//	var checkBoxArea = pnMain.checkBox("ckKhuVuc");
//	checkBoxArea.mouseClick();
//
//	pnMain.fieldSet("txtTenKM", "Khuyến mại HktTest1");
//	pnMain.fieldSet("txtSoLanSuDung", "100");
//	pnMain.fieldSet("txtThoiGianBatDau", "11:00");
//	pnMain.fieldSet("txtThoiGianKetThuc", "12:00");
//
//	var radioButton2 = pnMain.radioButton("rdKMTien");
//	radioButton2.mouseClick();
//	pnMain.fieldSet("txtKMTien", "5");
//	pnMain.fieldSet("txtApDungTu", "10");
//
//	var comboBox1 = pnMain.comboBox("cbDonViTien");
//	comboBox1.selectItem("TT HktTest1");
//
//	var comboBox2 = pnMain.comboBox("cbDonViTien1");
//	comboBox2.selectItem("TT HktTest1");
//	pnMain.fieldSet("txtApDungDen", "20");
//
//	var comboBox3 = pnMain.comboBox("cbDonViTien2");
//	comboBox3.selectItem("TT HktTest1");
//	pnMain.fieldSet("txtGhiChu", "Ghi chú HktTest1");
//	dlMain.buttonClick("Lưu");
//	frameui.showDialogTest("Lưu thành công !");
//	frameui.delay();
//
//	// BỎ TRỐNG CÁC TRƯỜNG VÀ LƯU
//	if (frameui
//			.showDialogTest("Lỗi chưa nhập dữ liệu các trường")) {
//		frameui.delay();
//		dlMain.buttonClick("Lưu");
//		frameui.delay();
//	}
//
//	// TÍCH CHỌN KM THEO SP - BỎ TRỐNG TÊN KM VÀ LƯU
//	if (frameui.showDialogTest("Lỗi chưa nhập Tên KM")) {
//		frameui.delay();
//		comboBoxGroupProduct.selectItem("NSP HktTest1");
//		checkBoxGroupProduct.mouseClick();
//		checkBoxPartner.mouseClick();
//		checkBoxArea.mouseClick();
//		pnMain.fieldSet("txtSoLanSuDung", "100");
//		pnMain.fieldSet("txtKMTien", "5000000");
//		pnMain.fieldSet("txtApDungTu", "1000000");
//		pnMain.fieldSet("txtApDungDen", "5000000");
//		dlMain.buttonClick("Lưu");
//		frameui.delay();
//	}
//
//	// BỎ TRỐNG GIÁ TRỊ TIỀN KM VÀ LƯU
//	if (frameui
//			.showDialogTest("Lỗi chưa nhập giá trị tiền KM")) {
//		frameui.delay();
//		comboBoxGroupProduct.selectItem("NSP HktTest1");
//		checkBoxGroupProduct.mouseClick();
//		checkBoxPartner.mouseClick();
//		checkBoxArea.mouseClick();
//		pnMain.fieldSet("txtTenKM", "Khuyến mại HktTest1");
//		pnMain.fieldSet("txtSoLanSuDung", "100");
//		pnMain.fieldSet("txtApDungTu", "1000000");
//		pnMain.fieldSet("txtApDungDen", "5000000");
//		dlMain.buttonClick("Lưu");
//		frameui.delay();
//	}
//
//	// BỎ TRỐNG GIÁ TRỊ KM % VÀ LƯU
//	if (frameui
//			.showDialogTest("Lỗi chưa nhập giá trị %KM")) {
//		frameui.delay();
//		comboBoxGroupProduct.selectItem("NSP HktTest1");
//		checkBoxGroupProduct.mouseClick();
//		checkBoxPartner.mouseClick();
//		checkBoxArea.mouseClick();
//		pnMain.fieldSet("txtTenKM", "Khuyến mại HktTest1");
//		pnMain.fieldSet("txtSoLanSuDung", "100");
//		var radioButton1 = pnMain.radioButton("rdKMPhanTram");
//		radioButton1.mouseClick();
//		pnMain.fieldSet("txtApDungTu", "1000000");
//		pnMain.fieldSet("txtApDungDen", "5000000");
//		dlMain.buttonClick("Lưu");
//		frameui.delay();
//	}
//
//	// KẾT THÚC GIAO DIỆN LẬP KM
//	frameui.showDialogTest("Thoát khỏi giao diện Lập KM");
//	frameui.delay();
//	dlMain.buttonClick("Thoát");
//	// Kết thúc giao diện thêm mới
}

function createQuanLyKhuyenMai(frameui) {

	var buttonMenu = frameui.button("menuRightKhuyenMai");
	buttonMenu.mouseClick();
	var button = frameui.button("QuanLyKhuyenMai");
	button.mouseClick();
	var dlMain = frameui.dialog("dlQuanLyKhuyenMai");
	var tableQuanLyKhuyenMai = dlMain.table("tbQuanLyKhuyenMai");
	tableQuanLyKhuyenMai.doubleClickRow("Khuyến mại HktTest1");
	var dlChild = frameui.dialog("LapKhuyenMai");
	var pnChild = dlChild.panel("LapKhuyenMai");
	dlChild.buttonClick("Sửa");
	var chbTypeStore = pnChild.checkBox("chbTypeStore");
	chbTypeStore.mouseClick();
	pnChild.fieldSet("txtTenKM", "Khuyến mại HktTest2");
	pnChild.fieldSet("txtSoLanSuDung", "2");
	pnChild.fieldSet("txtKMPhanTram", "2");
	dlChild.buttonClick("Xem lại");
	
	frameui.showDialogTest("Chương trình chạy thử chức năng chỉnh sửa Khuyến mại HktTest1 thành Khuyến mại HktTest2");
	frameui.delay();
	dlChild.buttonClick("Sửa");
	pnChild.fieldSet("txtTenKM", "Khuyến mại HktTest2");
	pnChild.fieldSet("txtSoLanSuDung", "2");
	pnChild.fieldSet("txtKMPhanTram", "2");
	dlChild.buttonClick("Lưu");
	
	frameui.showDialogTest("Chương trình Thêm mới một khuyến mại");
	frameui.delay();
	dlChild.buttonClick("Thoát");
	tableQuanLyKhuyenMai.doubleClickRow("Khuyến mại HktTest2");
	var dlChild = frameui.dialog("LapKhuyenMai");
	var pnChild = dlChild.panel("LapKhuyenMai");
	dlChild.buttonClick("Viết lại");
	var chbTypeStore = pnChild.checkBox("chbTypeStore");
	chbTypeStore.mouseClick();
	pnChild.fieldSet("txtTenKM", "Khuyến mại HktTest3");
	pnChild.fieldSet("txtSoLanSuDung", "3");
	pnChild.fieldSet("txtKMPhanTram", "3");
	dlChild.buttonClick("Lưu");
	
	frameui.showDialogTest("Chương trình chạy thử chức năng xóa bản ghi Khuyến mại HktTest3 vừa tạo");
	frameui.delay();
	dlChild.buttonClick("Thoát");
	tableQuanLyKhuyenMai.doubleClickRow("Khuyến mại HktTest3");
	var dlChild = frameui.dialog("LapKhuyenMai");
	var pnChild = dlChild.panel("LapKhuyenMai");
	dlChild.buttonClick("Sửa");
	dlChild.buttonClick("Xóa");
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");
	dlChild.buttonClick("Thoát");
	
	frameui.showDialogTest("Chương trình chạy thử các kịch bản đặc biệt: test để trống các trường");
	frameui.delay();
	tableQuanLyKhuyenMai.doubleClickRow("Khuyến mại HktTest2");
	var dlChild = frameui.dialog("LapKhuyenMai");
	var pnChild = dlChild.panel("LapKhuyenMai");
	dlChild.buttonClick("Viết lại");
	var chbTypeStore = pnChild.checkBox("chbTypeStore");
	chbTypeStore.mouseClick();
	pnChild.fieldSet("txtTenKM", "");
	dlChild.buttonClick("Lưu");
	frameui.showDialogTest("Nhập thiếu Tên khuyến mãi");
	frameui.delay();
	pnChild.fieldSet("txtTenKM", "Khuyến mại HktTest1");
	dlChild.buttonClick("Lưu");
	frameui.showDialogTest("Nhập thiếu giá trị % khuyến mãi");
	frameui.delay();
	pnChild.fieldSet("txtKMPhanTram", "1");
	dlChild.buttonClick("Lưu");
	
	//KỊCH BẢN PHỤ
	frameui.showDialogTest("Chương trình chạy thử các kịch bản đặc biệt: test sai định dạng");
	frameui.delay();
	dlChild.buttonClick("Thoát");
	tableQuanLyKhuyenMai.doubleClickRow("Khuyến mại HktTest1");
	var dlChild = frameui.dialog("LapKhuyenMai");
	var pnChild = dlChild.panel("LapKhuyenMai");
	dlChild.buttonClick("Viết lại");
	var chbTypeStore = pnChild.checkBox("chbTypeStore");
	chbTypeStore.mouseClick();
	pnChild.fieldSet("txtTenKM", "Khuyến mại HktTest3");
	var radioButton = pnChild.radioButton("rdKMTien");
	radioButton.mouseClick();
	pnChild.fieldSet("txtKMTien", "HktTest");
	dlChild.buttonClick("Lưu");
	frameui.showDialogTest("Sai định dạng tiền khuyến mãi");
	frameui.delay();
	pnChild.fieldSet("txtKMTien", "3000000");
	dlChild.buttonClick("Lưu");
	dlChild.buttonClick("Thoát");
	pnChild.deleteDataTest();
	
	frameui.showDialogTest("Chương trình chạy thử Lập khuyến mại đã hoàn thành!");
	frameui.delay();
	frameui.showDialogTest("Chúc quý khách sử dụng thành công phần mềm!");
	frameui.delay();
	var dlMain = frameui.dialog("dlQuanLyKhuyenMai");
	dlMain.buttonClick("Thoát");
	
	
	
//
//	// TEST XEM LẠI: sửa giá trị ban đầu từ 1 thành 2 và xem lại
//	frameui.showDialogTest("TEST chức năng Xem lại");
//	frameui.delay();
//	dlChild.buttonClick("Sửa");
//	var checkBoxGroupProduct = pnChild.checkBox("chkNhomSanPham");
//	checkBoxGroupProduct.mouseClick();
//	var checkBoxProduct = pnChild.checkBox("chbSanPham");
//	checkBoxProduct.mouseClick();
//	var comboBoxProduct = pnChild.comboBox("cboSanPham");
//	comboBoxProduct.selectItem("HH HktTest1");
//	var comboBoxArea = pnChild.comboBox("cbKhuVuc");
//	comboBoxArea.selectItem("KV HktTest1");
////	var checkBoxTable = pnChild.checkBox("chbBan");
////	checkBoxTable.mouseClick();
////	var comboBoxTable = pnChild.comboBox("cboBan");
////	comboBoxTable.selectItem("Bàn HktTest1");
//	pnChild.fieldSet("txtTenKM", "Khuyến mại HktTest2");
//	pnChild.fieldSet("txtSoLanSuDung", "111");
//	pnChild.fieldSet("txtThoiGianBatDau", "1:00");
//	pnChild.fieldSet("txtThoiGianKetThuc", "10:00");
//	var radioDiscount = pnChild.radioButton("rdKMPhanTram");
//	radioDiscount.mouseClick();
//	pnChild.fieldSet("txtKMPhanTram", "30");
//	pnChild.fieldSet("txtApDungTu", "2000000");
//	var comboBox2 = pnChild.comboBox("cbDonViTien1");
//	comboBox2.selectItem("TT HktTest1");
//	pnChild.fieldSet("txtApDungDen", "5000000");
//	var comboBox3 = pnChild.comboBox("cbDonViTien2");
//	comboBox3.selectItem("TT HktTest1");
//	pnChild.fieldSet("txtGhiChu", "Ghi chú HktTest2");
//	dlChild.buttonClick("Xem lại");
//	frameui.delay();
//
//	// TEST SỬA: sửa giá trị ban đầu từ 1 thành 2 và lưu
//	frameui.showDialogTest("TEST chức năng Sửa");
//	frameui.delay();
//	dlChild.buttonClick("Sửa");
//	checkBoxGroupProduct.mouseClick();
//	checkBoxProduct.mouseClick();
//	comboBoxProduct.selectItem("HH HktTest1");
//	comboBoxArea.selectItem("KV HktTest1");
////	var checkBoxTable = pnChild.checkBox("chbBan");
////	checkBoxTable.mouseClick();
////	var comboBoxTable = pnChild.comboBox("cboBan");
////	comboBoxTable.selectItem("Bàn HktTest1");
//	pnChild.fieldSet("txtTenKM", "Khuyến mại HktTest2");
//	pnChild.fieldSet("txtSoLanSuDung", "111");
//	pnChild.fieldSet("txtThoiGianBatDau", "1:00");
//	pnChild.fieldSet("txtThoiGianKetThuc", "10:00");
//	radioDiscount.mouseClick();
//	pnChild.fieldSet("txtKMPhanTram", "30");
//	pnChild.fieldSet("txtApDungTu", "2000000");
//	comboBox2.selectItem("TT HktTest1");
//	pnChild.fieldSet("txtApDungDen", "5000000");
//	comboBox3.selectItem("TT HktTest1");
//	pnChild.fieldSet("txtGhiChu", "Ghi chú HktTest2");
//	dlChild.buttonClick("Lưu");
//	frameui.showDialogTest("Sửa thành công !");
//	frameui.delay();
//	dlChild.buttonClick("Thoát");
//
//	// TEST VIẾT LẠI: reset các trường
//	frameui.showDialogTest("TEST chức năng Viết lại")
//	frameui.delay();
//	tableQuanLyKhuyenMai.doubleClickRow("Khuyến mại HktTest2");
//	var dlChild = frameui.dialog("QuanLyKhuyenMai");
//	var pnChild = dlChild.panel("QuanLyKhuyenMai");
//	dlChild.buttonClick("Viết lại");
//
//	// TEST THÊM MỚI 1 KM SO SÁNH VỚI KM BAN ĐẦU
//	frameui.showDialogTest("TEST chức năng Thêm mới")
//	frameui.delay();
//	var checkBoxGroupPartner = pnChild.checkBox("chbGroupPartner");
//	checkBoxGroupPartner.mouseClick();
//	var checkBoxProduct = pnChild.checkBox("chbSanPham");
//	checkBoxProduct.mouseClick();
//	var checkBoxTable = pnChild.checkBox("chbBan");
//	checkBoxTable.mouseClick();
//	pnChild.fieldSet("txtTenKM", "Khuyến mại HktTest3");
//	pnChild.fieldSet("txtSoLanSuDung", "333");
//	pnChild.fieldSet("txtThoiGianBatDau", "3:00");
//	pnChild.fieldSet("txtThoiGianKetThuc", "13:00");
//	var radioDiscount = pnChild.radioButton("rdKMPhanTram");
//	radioDiscount.mouseClick();
//	pnChild.fieldSet("txtKMPhanTram", "33");
//	pnChild.fieldSet("txtApDungTu", "300000");
//	var comboBox2 = pnChild.comboBox("cbDonViTien1");
//	comboBox2.selectItem("TT HktTest1");
//	pnChild.fieldSet("txtApDungDen", "3000000");
//	var comboBox3 = pnChild.comboBox("cbDonViTien2");
//	comboBox3.selectItem("TT HktTest1");
//	pnChild.fieldSet("txtGhiChu", "Ghi chú HktTest3");
//	dlChild.buttonClick("Lưu");
//	frameui.delay();
//	frameui.showDialogTest("Thêm mới thành công !");
//	frameui.delay();
//
//	// THÊM 50 BẢN GHI TEST CHUYỂN TRANG
//	frameui.showDialogTest(" TEST chức năng Chuyển trang");
//	frameui.delay();
//	pnChild.createDataTest();
//	dlChild.buttonClick("Thoát");
//
//	// CLICK SANG TRANG 2
//	frameui.showDialogTest("Sang trang 2");
//	frameui.delay();
//	dlMain.buttonClick("2");
//	frameui.showDialogTest("Trở về trang đầu tiên");
//	dlMain.buttonClick("<<");
//	frameui.delay();
//
//	// TEST TÌM KIẾM
//	frameui.showDialogTest("TEST chức năng Tìm kiếm -> tìm theo mã");
//	frameui.delay();
//	var comboBox = dlMain.comboBox("cbSearch");
//	comboBox.selectItem("Tên KM");
//	dlMain.fieldSet("txtSearch", "3");
//	frameui.delay();
//
//	// TEST XÓA
//	frameui.showDialogTest("TEST chức năng Xóa");
//	frameui.delay();
//	tableQuanLyKhuyenMai.doubleClickRow("Khuyến mại HktTest3");
//	var dlChild = frameui.dialog("QuanLyKhuyenMai");
//	dlChild.buttonClick("Sửa");
//	dlChild.buttonClick("Xóa");
//	var dlDelete = frameui.dialog("dlXoa");
//	var pnDelete = dlDelete.panel("Xoa");
//	dlDelete.buttonClick("Đồng ý");
//	frameui.showDialogTest("Xóa thành công!");
//	frameui.delay();
//	
//	// THOÁT KHỎI GIAO DIỆN TEST VÀ XÓA DỮ LIỆU SAU KHI TEST
//	frameui
//			.showDialogTest("Chương trình TEST Lập khuyến mãi kết thúc!");
//	frameui.delay();
//	pnChild.deleteDataTest();
//	dlChild.buttonClick("Thoát");
//	dlMain.buttonClickByName("btnExit");
}

var robot = new Robot();
robot.add("TEST LapKhuyenMai ", "", createLapKhuyenMai);
robot.add("TEST QuanLykhuyenMai ", "", createQuanLyKhuyenMai);
console.clear();
robot.run();
robot.report();