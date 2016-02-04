ScriptRunner.require("robotExp.js");

function CreateQuanLyNhaPhanPhoi(frameui) {
	/*
	 * TEST1
	 */
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac") ;
	buttonMenu.mouseClick();
	var button = frameui.button("QuanLyNhaPhanPhoi");
	button.mouseClick();
	
	var dlMain = frameui.dialog("dlQuanLyNhaPhanPhoi");
	var pnMain = dlMain.panel("QuanLyNhaPhanPhoi");
	/*
	 * Chuyển nhà phân phối rồi ấn nút hủy -> trở về ban đầu
	 */
	frameui.showDialogTest("Chuyển 'Nhóm NPP HktTest1/Tên NPP HktTest1'");
	var comboBoxTrai = pnMain.comboBox("cbNhaPhanPhoiHienTai");
	comboBoxTrai.selectItem("Nhóm NPP HktTest1");
	var comboBoxPhai = pnMain.comboBox("cbNhaPhanPhoiMoi");
	comboBoxPhai.selectItem("Nhóm NPP HktTest2");
	var tableTrai = pnMain.table("tbNhaPhanPhoiHienTai");
	tableTrai.doubleClickRow("Tên NPP HktTest1");
	var buttonAddOne = pnMain.button(">");
	buttonAddOne.mouseClick();
	//Ấn nút hủy trở về trạng thái ban đầu
	frameui.showDialogTest("Ấn nút hủy trở về trạng thái ban đầu");
	var buttonCancel = pnMain.button("btnCancel");
	buttonCancel.mouseClick();
	dlMain.buttonClick("Đồng ý");
	
	/*
	 * TEST2
	 */
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac") ;
	buttonMenu.mouseClick();
	var button = frameui.button("QuanLyNhaPhanPhoi");
	button.mouseClick();
	
	var dlMain = frameui.dialog("dlQuanLyNhaPhanPhoi");
	var pnMain = dlMain.panel("QuanLyNhaPhanPhoi");
	/*
	 * Chuyển nhà phân phối HktTest1 -> Nhóm HktTest2
	 */
	frameui.showDialogTest("Chuyển nhà phân phối HktTest1 -> Nhóm HktTest2");
	var comboBoxTrai = pnMain.comboBox("cbNhaPhanPhoiHienTai");
	comboBoxTrai.selectItem("Nhóm NPP HktTest1");
	var comboBoxPhai = pnMain.comboBox("cbNhaPhanPhoiMoi");
	comboBoxPhai.selectItem("Nhóm NPP HktTest2");
	var tableTrai = pnMain.table("tbNhaPhanPhoiHienTai");
	tableTrai.doubleClickRow("Tên NPP HktTest1");
	var buttonAddOne = pnMain.button(">");
	buttonAddOne.mouseClick();
	dlMain.buttonClick("Đồng ý");
	frameui.showDialogTest("Đồng ý đã lưu mọi thao tác");
	
	/*
	 * TEST3
	 */
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac") ;
	buttonMenu.mouseClick();
	var button = frameui.button("QuanLyNhaPhanPhoi");
	button.mouseClick();
	
	var dlMain = frameui.dialog("dlQuanLyNhaPhanPhoi");
	var pnMain = dlMain.panel("QuanLyNhaPhanPhoi");
	/*
	 * Chuyển nhà phân phối HktTest1 -> Nhóm HktTest1 (ban đầu)
	 */
	frameui.showDialogTest("Chuyển nhà phân phối HktTest1 -> Nhóm HktTest1 (ban đầu)");
	var comboBoxTrai = pnMain.comboBox("cbNhaPhanPhoiHienTai");
	comboBoxTrai.selectItem("Nhóm NPP HktTest1");
	var comboBoxPhai = pnMain.comboBox("cbNhaPhanPhoiMoi");
	comboBoxPhai.selectItem("Nhóm NPP HktTest2");
	var tablePhai = pnMain.table("tbNhaPhanPhoiMoi");
	tablePhai.doubleClickRow("Tên NPP HktTest1");
	var buttonAddOne = pnMain.button("<");
	buttonAddOne.mouseClick();
	dlMain.buttonClick("Đồng ý");
	frameui.showDialogTest("Đồng ý đã lưu mọi thao tác");
	
	/*
	 * TEST4
	 */
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac") ;
	buttonMenu.mouseClick();
	var button = frameui.button("QuanLyNhaPhanPhoi");
	button.mouseClick();
	
	var dlMain = frameui.dialog("dlQuanLyNhaPhanPhoi");
	var pnMain = dlMain.panel("QuanLyNhaPhanPhoi");
	/*
	 * Test nút chuyển tất cả trái qua phải
	 */
	frameui.showDialogTest("Test nút chuyển tất cả trái qua phải");
	var comboBoxTrai = pnMain.comboBox("cbNhaPhanPhoiHienTai");
	comboBoxTrai.selectItem("Nhóm NPP HktTest1");
	var comboBoxPhai = pnMain.comboBox("cbNhaPhanPhoiMoi");
	comboBoxPhai.selectItem("Nhóm NPP HktTest2");
	var buttonAddOne = pnMain.button(">>");
	buttonAddOne.mouseClick();
	dlMain.buttonClick("Đồng ý");
	frameui.showDialogTest("Đồng ý đã lưu mọi thao tác");
	
	/*
	 * TEST5
	 */
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac") ;
	buttonMenu.mouseClick();
	var button = frameui.button("QuanLyNhaPhanPhoi");
	button.mouseClick();
	
	var dlMain = frameui.dialog("dlQuanLyNhaPhanPhoi");
	var pnMain = dlMain.panel("QuanLyNhaPhanPhoi");
	/*
	 * Test nút chuyển tất cả phải qua trái
	 */
	frameui.showDialogTest("Test nút chuyển tất cả phải qua trái");
	var comboBoxTrai = pnMain.comboBox("cbNhaPhanPhoiHienTai");
	comboBoxTrai.selectItem("Nhóm NPP HktTest1");
	var comboBoxPhai = pnMain.comboBox("cbNhaPhanPhoiMoi");
	comboBoxPhai.selectItem("Nhóm NPP HktTest2");
	var buttonAddOne = pnMain.button("<<");
	buttonAddOne.mouseClick();
	dlMain.buttonClick("Đồng ý");
	frameui.showDialogTest("Đồng ý đã lưu mọi thao tác");
	
	/*
	 * Xóa toàn bộ dữ liệu test
	 */
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac") ;
	buttonMenu.mouseClick();
	var button = frameui.button("QuanLyNhaPhanPhoi");
	button.mouseClick();
	
	var dlMain = frameui.dialog("dlQuanLyNhaPhanPhoi");
	var pnMain = dlMain.panel("QuanLyNhaPhanPhoi");

	pnMain.deleteDataTest();
	dlMain.buttonClick("Thoát");
	frameui.showDialogTest("Đã xóa toàn bộ dữ liệu test xong");
}

var robot = new Robot();
robot.add("TEST QuanLyNhaPhanPhoi", "", CreateQuanLyNhaPhanPhoi);
console.clear();
robot.run();
robot.report();