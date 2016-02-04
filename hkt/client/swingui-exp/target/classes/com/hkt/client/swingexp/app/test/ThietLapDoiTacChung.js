ScriptRunner.require("robotExp.js");

function CreateThietLapDoiTacChung(frameui) {
	frameui.showDialogTest("Chương trình TEST giao diện [Thiết lập đối tác chung]");
	frameui.delay();
	/*
	 * TEST1
	 */
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac") ;
	buttonMenu.mouseClick();
	var button = frameui.button("ThietLapDoiTacChung");
	button.mouseClick();
	
	var dlMain = frameui.dialog("dlThietLapDoiTacChung");
	var pnMain = dlMain.panel("ThietLapDoiTacChung");
	/*
	 * Test nút > và X
	 */
	frameui.showDialogTest("Chọn nhân viên HktTest1 && Tạo mới khách hàng HktTest1");
	frameui.delay();
	var comboBoxTrai = pnMain.comboBox("cbTrai");
	comboBoxTrai.selectItem("Phòng ban");
	var comboBoxPhai = pnMain.comboBox("cbPhai");
	comboBoxPhai.selectItem("Khách hàng");
	var tableTrai = pnMain.table("tbTrai");
	tableTrai.doubleClickRow("HktTest1");
	var buttonAddOne = pnMain.button(">");
	buttonAddOne.mouseClick();
	
	//Ấn nút hủy trở về trạng thái ban đầu
	frameui.showDialogTest("Ấn nút < xóa bỏ khách hàng HktTest1");
	frameui.delay();
	var tablePhai = pnMain.table("tbPhai");
	tablePhai.doubleClickRow("HktTest1");
	var buttonRemoveOne = pnMain.button("X");
	buttonRemoveOne.mouseClick();
	dlMain.buttonClick("Đồng ý");
	
	/*
	 * TEST2
	 */
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac") ;
	buttonMenu.mouseClick();
	var button = frameui.button("ThietLapDoiTacChung");
	button.mouseClick();
	
	var dlMain = frameui.dialog("dlThietLapDoiTacChung");
	var pnMain = dlMain.panel("ThietLapDoiTacChung");
	/*
	 * Test nút >> và XX
	 */
	frameui.showDialogTest("Chọn toàn bộ nhân viên và tạo mới nhà phân phối");
	frameui.delay();
	var comboBoxTrai = pnMain.comboBox("cbTrai");
	comboBoxTrai.selectItem("Phòng ban");
	var comboBoxPhai = pnMain.comboBox("cbPhai");
	comboBoxPhai.selectItem("Nhà phân phối");
	var buttonAddAll = pnMain.button(">>");
	buttonAddAll.mouseClick();
	var buttonRemoveAll = pnMain.button("XX");
	buttonRemoveAll.mouseClick();
	dlMain.buttonClick("Đồng ý");
	
	/*
	 * TEST3
	 */
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac") ;
	buttonMenu.mouseClick();
	var button = frameui.button("ThietLapDoiTacChung");
	button.mouseClick();
	
	var dlMain = frameui.dialog("dlThietLapDoiTacChung");
	var pnMain = dlMain.panel("ThietLapDoiTacChung");
	/*
	 * Test nút > và lưu lại
	 */
	frameui.showDialogTest("Chọn nhân viên HktTest1 && Tạo mới khách hàng HktTest1");
	frameui.delay();
	var comboBoxTrai = pnMain.comboBox("cbTrai");
	comboBoxTrai.selectItem("Phòng ban");
	var comboBoxPhai = pnMain.comboBox("cbPhai");
	comboBoxPhai.selectItem("Khách hàng");
	var tableTrai = pnMain.table("tbTrai");
	tableTrai.doubleClickRow("HktTest1");
	var buttonAddOne = pnMain.button(">");
	buttonAddOne.mouseClick();
	dlMain.buttonClick("Đồng ý");
	frameui.showDialogTest("Đồng ý đã lưu mọi thao tác");
	
	/*
	 * TEST4
	 */
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac") ;
	buttonMenu.mouseClick();
	var button = frameui.button("ThietLapDoiTacChung");
	button.mouseClick();
	
	var dlMain = frameui.dialog("dlThietLapDoiTacChung");
	var pnMain = dlMain.panel("ThietLapDoiTacChung");
	/*
	 * Test nút >> và lưu lại
	 */
	frameui.showDialogTest("Chọn toàn bộ nhân viên và tạo mới nhà phân phối");
	frameui.delay();
	var comboBoxTrai = pnMain.comboBox("cbTrai");
	comboBoxTrai.selectItem("Phòng ban");
	var comboBoxPhai = pnMain.comboBox("cbPhai");
	comboBoxPhai.selectItem("Khách hàng");
	var buttonAddAll = pnMain.button(">>");
	buttonAddAll.mouseClick();
	dlMain.buttonClick("Đồng ý");
	frameui.showDialogTest("Đồng ý đã lưu mọi thao tác");
	
	/*
	 * Xóa toàn bộ dữ liệu test
	 */
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac") ;
	buttonMenu.mouseClick();
	var button = frameui.button("ThietLapDoiTacChung");
	button.mouseClick();
	
	var dlMain = frameui.dialog("dlThietLapDoiTacChung");
	var pnMain = dlMain.panel("ThietLapDoiTacChung");

	pnMain.deleteDataTest();
	dlMain.buttonClick("Thoát");
	frameui.showDialogTest("Đã xóa toàn bộ dữ liệu test xong");
}

var robot = new Robot();
robot.add("TEST ThietLapDoiTacChung", "", CreateThietLapDoiTacChung);
console.clear();
robot.run();
robot.report();