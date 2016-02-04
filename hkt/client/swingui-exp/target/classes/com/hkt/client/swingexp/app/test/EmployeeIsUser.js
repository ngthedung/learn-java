ScriptRunner.require("robotExp.js");

/*
 * @author Phan Anh
 */

function CreatedEmployeeIsUser(frameui) {
	//Thông tin TEST khởi tạo
	frameui.showDialogTest("CHƯƠNG TRÌNH TEST NHÂN VIÊN");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightNhanSu");
	buttonMenu.mouseClick();
	var button = frameui.button("EmployeeIsUser");
	button.mouseClick();

	var dlMain = frameui.dialog("dlEmployeeIsUser");
	var pnMain = dlMain.panel("EmployeeIsUser");

	frameui.showDialogTest("Chạy thử chức năng [Lưu]");
	frameui.delay();
	
	//Lỗi
	if(frameui.showDialogTest("[Lỗi] Chưa nhập tên nhân viên")){
		frameui.delay();
		dlMain.buttonClickByName("btnSave");
		frameui.delay();
	}
	//Thêm thông tin 1 nhân viên
	pnMain.fieldSet("txtLastName", "Họ đệm nhân viên");
	pnMain.fieldSet("txtFirstName", "Tên nhân viên HktTest1");
	pnMain.fieldSet("txtMobile", "0963677714");
	pnMain.fieldSet("txtAddress", "Địa chỉ HktTest1");
	pnMain.fieldSet("txtHeight", "175");
	pnMain.fieldSet("txtWeight", "70");
	pnMain.fieldSet("txtCMT", "12346789");
	pnMain.fieldSet("txtHobby", "Sở thích, đặc điểm cá nhân HktTest");
	var cbMaritalStatus = pnMain.comboBox("cbMaritalStatus");
	cbMaritalStatus.selectItem("Độc thân");
	var cboGender = pnMain.comboBox("cboGender");
	cboGender.selectItem("Nam");
	frameui.delay();

	// TAB học vấn
	var tabPane = dlMain.tabbedPane("tabbedPane");
	tabPane.clickTab("Học vấn");
	//Lỗi
	if(frameui.showDialogTest("[Lỗi] Chưa nhập tên tên trường học")){
		frameui.delay();
		dlMain.buttonClickByName("btnAddUserEducation");
		frameui.delay();
	}
	//Dòng 1
	pnMain.fieldSet("txtSchoolOrInstitude", "Trường học HktTest1");
	pnMain.fieldSet("txtMajor", "Chính HktTest1");
	pnMain.fieldSet("txtCertificate", "Bằng cấp HktTest1");
	pnMain.fieldSet("txtLanguage", "Ngôn ngữ HktTest1");
	pnMain.fieldSet("txtDescription", "Mô tả chi tiết HktTest1");
	dlMain.buttonClickByName("btnAddUserEducation");
	frameui.delay();

	// TAB kinh nghiệm làm việc
	var tabPane = dlMain.tabbedPane("tabbedPane");
	tabPane.clickTab("Kinh nghiệm làm việc");
	//Lỗi
	if(frameui.showDialogTest("[Lỗi] Chưa nhập tên công ty!")){
		frameui.delay();
		dlMain.buttonClickByName("btnAddUserWork");
		frameui.delay();
	}
	//Dòng 1
	pnMain.fieldSet("txtOrganization", "Doanh nghiệp HktTest1");
	dlMain.buttonClickByName("btnAddUserWork");
	frameui.delay();
	//Dòng 2
	pnMain.fieldSet("txtPosition", "Trụ sở công ty HktTest1");
	pnMain.fieldSet("txtDescription", "Mô tả chi tiết HktTest1");
	dlMain.buttonClickByName("btnAddUserWork");
	frameui.delay();

	// TAB quan hệ
	var tabPane = dlMain.tabbedPane("tabbedPane");
	tabPane.clickTab("Quan hệ");
	//Lỗi
	if(frameui.showDialogTest("[Lỗi] Chưa nhập tên quan hệ")){
		frameui.delay();
		dlMain.buttonClickByName("btnAddUserRelationship");
		frameui.delay();
	}
	//Dòng 1
	pnMain.fieldSet("txtRelation", "Mối quan hệ HktTest1");
	dlMain.buttonClickByName("btnAddUserRelationship");
	frameui.delay();
	//Dòng 2
	pnMain.fieldSet("txtFirstName", "Tên HktTest1");
	pnMain.fieldSet("txtLastName", "Họ đệm HktTest1");
	pnMain.fieldSet("txtOccupation", "Nghề nghiệp IT Cty HktTest1");
	var cboGenderRelationship = pnMain.comboBox("cboGenderRelationship");
	cboGenderRelationship.selectItem("Nam");
	dlMain.buttonClickByName("btnAddUserRelationship");
	frameui.delay();

	// TAB thông tin liên lạc
	var tabPane = dlMain.tabbedPane("tabbedPane");
	tabPane.clickTab("Thông tin liên lạc");
	//Lỗi
	if(frameui.showDialogTest("[Lỗi] Chưa nhập Thông tin địa chỉ")){
		frameui.delay();
		dlMain.buttonClickByName("btnAddContact");
		frameui.delay();
	}
	//Dòng 1
	pnMain.fieldSet("txtAddressNumber", "Phòng D1 - Tầng 09 - Tòa nhà TopCare HktTest1");
	pnMain.fieldSet("txtXaPhuong", "Phường Dịch Vọng HktTest1");
	pnMain.fieldSet("txtStreet", "Cầu giấy HktTest1");
	pnMain.fieldSet("txtPhone", "0437925193");
	pnMain.fieldSet("txtMobile", "0963677714");
	pnMain.fieldSet("txtFax", "0437925191");
	pnMain.fieldSet("txtWebsite", "www.HktConsultant.com/HktTest1");
	var cbCity = pnMain.comboBox("cbCity");
	cbCity.selectItem("Hà Nội");
	frameui.delay();
	dlMain.buttonClickByName("btnAddContact");

	dlMain.buttonClickByName("btnSave");
	frameui.delay();

	dlMain.buttonClickByName("btnExit");
	frameui.delay();
}

function EditEmployeeIsUser(frameui) {
	frameui.showDialogTest("Chạy thử chức năng [Sửa]");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightNhanSu");
	buttonMenu.mouseClick();
	var button = frameui.button("PanelListEmployee");
	button.mouseClick();

	var dlMain = frameui.dialog("dlPanelListEmployee");
	var tblistPartner = dlMain.table("tableEmployee");
	tblistPartner.doubleClickRow("Họ đệm nhân viên Tên nhân viên HktTest1");

	var dlMainEdit = frameui.dialog("dlEmployeeIsUser");
	var pnMain = dlMainEdit.panel("EmployeeIsUser");
	dlMainEdit.buttonClickByName("btnEdit");
	
	// TAB thông tin cơ bản
	pnMain.fieldSet("txtLastName", "Họ đệm NV HktTest (Sửa)");
	pnMain.fieldSet("txtFirstName", "Tên NV HktTest1 (Sửa)");
	pnMain.fieldSet("txtMobile", "0904894728");
	pnMain.fieldSet("txtAddress", "Địa chỉ HktTest1 (Sửa)");
	pnMain.fieldSet("txtHeight", "180");
	pnMain.fieldSet("txtWeight", "70");
	pnMain.fieldSet("txtCMT", "987654321");
	pnMain.fieldSet("txtHobby", "Sở thích, đặc điểm HktTest1 (Sửa)");
	frameui.delay();

	// TAB học vấn
	var tabPane = dlMainEdit.tabbedPane("tabbedPane");
	tabPane.clickTab("Học vấn");
	var userEducationTable = dlMainEdit.table("userEducationTable");
	userEducationTable.doubleClickRow("Trường học HktTest1");
	frameui.delay();
	pnMain.fieldSet("txtSchoolOrInstitude", "Trường học HktTest1 (Sửa)");
	pnMain.fieldSet("txtMajor", "Chính HktTest1 (Sửa)");
	pnMain.fieldSet("txtCertificate", "Bằng cấp HktTest1 (Sửa)");
	pnMain.fieldSet("txtLanguage", "Ngôn ngữ HktTest1 (Sửa)");
	pnMain.fieldSet("txtDescription", "Mô tả chi tiết HktTest1 (Sửa)");
	dlMainEdit.buttonClickByName("btnSaveUserEducation");
	frameui.delay();
	
	// TAB kinh nghiệm làm việc
	var tabPane = dlMainEdit.tabbedPane("tabbedPane");
	tabPane.clickTab("Kinh nghiệm làm việc");
	var userWorkTable = dlMainEdit.table("userWorkTable");
	userWorkTable.doubleClickRow("Doanh nghiệp HktTest1");
	frameui.delay();
	
	pnMain.fieldSet("txtOrganization", "Doanh nghiệp HktTest1 (Sửa)");
	pnMain.fieldSet("txtPosition", "Vị trí công ty - doanh nghiệp HktTest1 (Sửa)");
	pnMain.fieldSet("txtDescription", "Mô tả chi tiết công ty HktTest1 (Sửa)");
	dlMainEdit.buttonClickByName("btnSaveUserWork");
	frameui.delay();
	
	// TAB quan hệ
	var tabPane = dlMainEdit.tabbedPane("tabbedPane");
	tabPane.clickTab("Quan hệ");
	var userRelationshipTable = dlMainEdit.table("userRelationshipTable");
	userRelationshipTable.doubleClickRow("Mối quan hệ HktTest1");
	frameui.delay();
	
	pnMain.fieldSet("txtRelation", "Mối quan hệ HktTest1 (Sửa)");
	pnMain.fieldSet("txtFirstName", "Tên người uqan hệ HktTest1 (Sửa)");
	pnMain.fieldSet("txtLastName", "Họ đệm người quan hệ HktTest1 (Sửa)");
	pnMain.fieldSet("txtOccupation", "Nghề nghiệp HktTest1 (Sửa)");
	dlMainEdit.buttonClickByName("btnSaveUserRelationship");
	frameui.delay();
	
	// TAB thông tin liên lạc
	var tabPane = dlMainEdit.tabbedPane("tabbedPane");
	tabPane.clickTab("Thông tin liên lạc");
	var contactTable = dlMainEdit.table("contactTable");
	contactTable.doubleClickRow("Phường Dịch Vọng HktTest1");
	frameui.delay();
	pnMain.fieldSet("txtAddressNumber", "Địa chỉ nhà cụ thể HktTest1 (Sửa)");
	pnMain.fieldSet("txtXaPhuong", "Phường/Xã HktTest1 (Sửa)");
	pnMain.fieldSet("txtStreet", "Tên phố/đường HktTest1 (Sửa)");
	pnMain.fieldSet("txtPhone", "0437925191");
	pnMain.fieldSet("txtMobile", "0904894728");
	pnMain.fieldSet("txtFax", "0437925191");
	pnMain.fieldSet("txtWebsite", "www.HktConsultant.com/HktTest1 (Sửa)");
	dlMainEdit.buttonClickByName("btnSaveContact");
	frameui.delay();
	
	dlMainEdit.buttonClickByName("btnSave");
	frameui.delay();
	
	//Thêm 50 bản ghi
	pnMain.createDataTest();
	
	dlMainEdit.buttonClickByName("btnExit");
	dlMain.buttonClick("Thoát");
	frameui.delay();
}

function DeletePartnerIsUser(frameui) {
	frameui.showDialogTest("Chạy chức năng [TEST phân trang]");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightNhanSu");
	buttonMenu.mouseClick();
	var button = frameui.button("PanelListEmployee");
	button.mouseClick();

	var dlMain = frameui.dialog("dlPanelListEmployee");
	var tblistPartner = dlMain.table("tableEmployee");
	
	//Test chuyển trang
	frameui.showDialogTest("Sang trang 2");
	dlMain.buttonClick("2");
	frameui.delay();
	frameui.showDialogTest("Trở về trang đầu tiên");
	dlMain.buttonClick("<<");
	frameui.delay();
	
	//Test tìm kiếm 
	frameui.showDialogTest("[Tìm kiếm] Tìm theo Tên nhân viên");
	var comboBox = dlMain.comboBox("cbSearch");
	comboBox.selectItem("Tên nhân viên");
	dlMain.fieldSet("txtSearch", "Sửa");
	frameui.delay();
	tblistPartner.doubleClickRow("Họ đệm NV HktTest (Sửa) Tên NV HktTest1 (Sửa)");
	frameui.delay();

	var dlMainEdit = frameui.dialog("dlEmployeeIsUser");
	var pnMain = dlMainEdit.panel("EmployeeIsUser");
	
	dlMainEdit.buttonClickByName("btnEdit");
	dlMainEdit.buttonClickByName("btnDelete");
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");
	frameui.showDialogTest("Xóa thành công 1 nhân viên");
	frameui.delay();
	frameui.showDialogTest("Xóa toàn bộ dữ liệu TEST");	
	pnMain.deleteDataTest();
	frameui.delay();
	dlMainEdit.buttonClickByName("btnExit");
	frameui.delay();
	dlMain.buttonClick("Thoát");

}

var robot = new Robot();
robot.add("TEST CREATE EmployeeIsUser ", "", CreatedEmployeeIsUser);
robot.add("TEST EDIT EmployeeIsUser ", "", EditEmployeeIsUser);
robot.add("TEST DELETE PartnerIsUser ", "", DeletePartnerIsUser);
console.clear();
robot.run();
robot.report();