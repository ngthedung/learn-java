ScriptRunner.require("robotExp.js");
function CreateMa(frameui) {
	frameui.showDialogTest("Chương trình bắt đầu chạy thử giao diện [Thêm mã tự sinh]");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightKhuVucBanHang");
	buttonMenu.mouseClick();
	var button = frameui.button("ThemMaTuSinh");
	button.mouseClick()

	var dlMain = frameui.dialog("dlThemMaTuSinh");
	var pnChild = dlMain.panel("ThemMaTuSinh");
	frameui.delay();
	
	//Thiết lập mã quay vòng theo ngày
	frameui.showDialogTest("Thiết lập mã Quay vòng [Theo ngày]");
	frameui.delay();
	var rdQuayVong = pnChild.radioButton("rdQuayVong");
	rdQuayVong.mouseClick();
	
	var cboTheo = pnChild.comboBox("cboTheo");
	cboTheo.selectItem("Theo ngày");
	dlMain.buttonClick("Đồng ý");
	frameui.showDialogTest("Thiết lập thành công!");
	frameui.delay();
	dlMain.buttonClick("Thoát");
	frameui.delay();
	
	//Thiết lập mã quay vòng theo tháng
	frameui.showDialogTest("Thiết lập mã Quay vòng [Theo tháng]");
	frameui.delay();
	var button = frameui.button("ThemMaTuSinh");
	button.mouseClick()

	var dlMain = frameui.dialog("dlThemMaTuSinh");
	var pnChild = dlMain.panel("ThemMaTuSinh");
	frameui.delay();
	var rdQuayVong = pnChild.radioButton("rdQuayVong");
	rdQuayVong.mouseClick();
	
	var cboTheo = pnChild.comboBox("cboTheo");
	cboTheo.selectItem("Theo tháng");
	dlMain.buttonClick("Đồng ý");
	frameui.showDialogTest("Thiết lập thành công!");
	frameui.delay();
	dlMain.buttonClick("Thoát");
	frameui.delay();
	
	//Thiết lập mã quay vòng theo năm
	frameui.showDialogTest("Thiết lập mã Quay vòng [Theo năm]");
	frameui.delay();
	var button = frameui.button("ThemMaTuSinh");
	button.mouseClick()

	var dlMain = frameui.dialog("dlThemMaTuSinh");
	var pnChild = dlMain.panel("ThemMaTuSinh");
	frameui.delay();
	var rdQuayVong = pnChild.radioButton("rdQuayVong");
	rdQuayVong.mouseClick();
	
	var cboTheo = pnChild.comboBox("cboTheo");
	cboTheo.selectItem("Theo năm");
	dlMain.buttonClick("Đồng ý");
	frameui.showDialogTest("Thiết lập thành công!");
	frameui.delay();
	dlMain.buttonClick("Thoát");
	frameui.delay();
	
	
	//Thiết lập mã tăng dần đều
	frameui.showDialogTest("Thiết lập mã [Tăng dần đều]");
	frameui.delay();
	var button = frameui.button("ThemMaTuSinh");
	button.mouseClick()

	var dlMain = frameui.dialog("dlThemMaTuSinh");
	var pnChild = dlMain.panel("ThemMaTuSinh");
	frameui.delay();
	var rdTangDanDeu = pnChild.radioButton("rdTangDanDeu");
	rdTangDanDeu.mouseClick();
	dlMain.buttonClick("Đồng ý");
	frameui.showDialogTest("Thiết lập thành công!");
	frameui.delay();
	dlMain.buttonClick("Thoát");
	frameui.delay();
	frameui.showDialogTest("Chương trình TEST giao diện [Thêm mã tự sinh] kết thúc!");
	frameui.delay();
}
var robot = new Robot();
robot.add("TEST ThemMaTuSinh", "", CreateMa);
console.clear();
robot.run();
robot.report();