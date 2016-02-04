ScriptRunner.require("robotExp.js");

/*
 * @author Phan Anh
 */

function CreatePartnerIsUser(frameui) {
	frameui.showDialogTest("CHƯƠNG TRÌNH TEST ĐỐI TÁC CÁ NHÂN");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac");
	buttonMenu.mouseClick();
	var button = frameui.button("AddCustomer");
	button.mouseClick();
	var dlChoose = frameui.dialog("dialogAddCustome");
	var button = dlChoose.button("btnDN");
	button.mouseClick();

	var dlMain = frameui.dialog("dlPartnerIsUser");
	var pnMain = dlMain.panel("PartnerIsUser");

	frameui.showDialogTest("Chạy thử chức năng [Lưu]");
	frameui.delay();
	
	if(frameui.showDialogTest("[Lỗi] Chưa nhập tên đối tác")){
		frameui.delay();
		dlMain.buttonClickByName("btnSave");
		frameui.delay();
	}

	pnMain.fieldSet("txtLastName", "ĐT HktTest");
	pnMain.fieldSet("txtMobile", "046151541");
	pnMain.fieldSet("txtFax", "051548941");
	pnMain.fieldSet("txtAddress", "Address HktTest1");
	pnMain.fieldSet("txtHeight", "175");
	pnMain.fieldSet("txtWeight", "70");
	pnMain.fieldSet("txtCMT", "0126545113");
	pnMain.fieldSet("txtHobby", "Hobby HKT-Test");
	var cbMaritalStatus = pnMain.comboBox("cbMaritalStatus");
	cbMaritalStatus.selectItem("Độc thân");
	var cboGender = pnMain.comboBox("cboGender");
	cboGender.selectItem("Nam");
	frameui.delay();

	// TAB học vấn
	var tabPane = dlMain.tabbedPane("tabbedPane");
	tabPane.clickTab("Học vấn");
	
	if(frameui.showDialogTest("[Lỗi] Chưa nhập tên tên trường học")){
		frameui.delay();
		dlMain.buttonClickByName("btnAddUserEducation");
		frameui.delay();
	}
	pnMain.fieldSet("txtSchoolOrInstitude", "School HktTest1");
	pnMain.fieldSet("txtMajor", "Major HktTest1");
	pnMain.fieldSet("txtCertificate", "Certificate HktTest1");
	pnMain.fieldSet("txtLanguage", "Language HktTest1");
	pnMain.fieldSet("txtDescription", "Description HKT-Test1");
	dlMain.buttonClickByName("btnAddUserEducation");
	frameui.delay();

	// TAB kinh nghiệm làm việc
	var tabPane = dlMain.tabbedPane("tabbedPane");
	tabPane.clickTab("Kinh nghiệm làm việc");
	
	if(frameui.showDialogTest("[Lỗi] Chưa nhập tên công ty!")){
		frameui.delay();
		dlMain.buttonClickByName("btnAddUserWork");
		frameui.delay();
	}
	pnMain.fieldSet("txtOrganization", "Organization HktTest1");
	dlMain.buttonClickByName("btnAddUserWork");
	frameui.delay();
	pnMain.fieldSet("txtPosition", "Position HktTest1");
	pnMain.fieldSet("txtDescription", "Description HktTest1");
	dlMain.buttonClickByName("btnAddUserWork");
	frameui.delay();

	// TAB quan hệ
	var tabPane = dlMain.tabbedPane("tabbedPane");
	tabPane.clickTab("Quan hệ");
	if(frameui.showDialogTest("[Lỗi] Chưa nhập tên quan hệ")){
		frameui.delay();
		dlMain.buttonClickByName("btnAddUserRelationship");
		frameui.delay();
	}
	pnMain.fieldSet("txtRelation", "Relation HktTest1");
	dlMain.buttonClickByName("btnAddUserRelationship");
	frameui.delay();
	pnMain.fieldSet("txtFirstName", "FirstName HktTest1");
	pnMain.fieldSet("txtLastName", "LastName HktTest1");
	pnMain.fieldSet("txtOccupation", "Occupation HktTest1");
	var cboGenderRelationship = pnMain.comboBox("cboGenderRelationship");
	cboGenderRelationship.selectItem("Nam");
	dlMain.buttonClickByName("btnAddUserRelationship");
	frameui.delay();

	// TAB thông tin liên lạc
	var tabPane = dlMain.tabbedPane("tabbedPane");
	tabPane.clickTab("Thông tin liên lạc");
	if(frameui.showDialogTest("[Lỗi] Chưa nhập Thông tin địa chỉ")){
		frameui.delay();
		dlMain.buttonClickByName("btnAddContact");
		frameui.delay();
	}
	pnMain.fieldSet("txtAddressNumber", "Số 335 HktTest1");
	pnMain.fieldSet("txtXaPhuong", "Phường Dịch Vọng HktTest1");
	pnMain.fieldSet("txtStreet", "Cầu giấy HktTest1");
	pnMain.fieldSet("txtPhone", "09821154551");
	pnMain.fieldSet("txtMobile", "046151541");
	pnMain.fieldSet("txtFax", "051548941");
	pnMain.fieldSet("txtWebsite", "www.hkt.com/hkt_test_1");
	var cbCity = pnMain.comboBox("cbCity");
	cbCity.selectItem("Hà Nội");
	frameui.delay();
	dlMain.buttonClickByName("btnAddContact");

	// Lưu
	dlMain.buttonClickByName("btnSave");
	frameui.delay();

	dlMain.buttonClickByName("btnExit");
}

function EditPartnerIsUser(frameui) {
	frameui.showDialogTest("Chạy thử chức năng [Sửa]");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac");
	buttonMenu.mouseClick();
	var button = frameui.button("listPartner");
	button.mouseClick();

	var dlMain = frameui.dialog("dllistPartner");
	var tblistPartner = dlMain.table("tblistPartner");
	tblistPartner.doubleClickRow("ĐT HktTest");

	// Data (Edit)
	var dlMainEdit = frameui.dialog("dlPartnerIsUser");
	var pnMain = dlMainEdit.panel("PartnerIsUser");
	dlMainEdit.buttonClickByName("btnEdit");

	pnMain.fieldSet("txtLastName", "ĐT HktTest (Edit)");
	pnMain.fieldSet("txtMobile", "876543");
	pnMain.fieldSet("txtFax", "345678");
	pnMain.fieldSet("txtAddress", "Address HktTest1 (Edit)");
	pnMain.fieldSet("txtHeight", "188");
	pnMain.fieldSet("txtWeight", "70");
	pnMain.fieldSet("txtCMT", "353433220");
	pnMain.fieldSet("txtHobby", "Hobby HKT-Test (Edit)");
	frameui.delay();

	// Hoc Van
	var tabPane = dlMainEdit.tabbedPane("tabbedPane");
	tabPane.clickTab("Học vấn");
	var userEducationTable = dlMainEdit.table("userEducationTable");
	userEducationTable.doubleClickRow("School HktTest1");
	frameui.delay();
	pnMain.fieldSet("txtSchoolOrInstitude", "School HktTest1 (Edit)");
	pnMain.fieldSet("txtMajor", "Major HktTest1 (Edit)");
	pnMain.fieldSet("txtCertificate", "Certificate HktTest1 (Edit)");
	pnMain.fieldSet("txtLanguage", "Language HktTest1 (Edit)");
	pnMain.fieldSet("txtDescription", "Description HKT-Test1 (Edit)");
	frameui.delay();
	dlMainEdit.buttonClickByName("btnSaveUserEducation");
	// Kinh nghiem lam viec
	var tabPane = dlMainEdit.tabbedPane("tabbedPane");
	tabPane.clickTab("Kinh nghiệm làm việc");
	var userWorkTable = dlMainEdit.table("userWorkTable");
	userWorkTable.doubleClickRow("Organization HktTest1");
	frameui.delay();
	pnMain.fieldSet("txtOrganization", "Organization HktTest1 (Edit)");
	pnMain.fieldSet("txtPosition", "Position HktTest1 (Edit)");
	pnMain.fieldSet("txtDescription", "Description HktTest1 (Edit)");
	frameui.delay();
	dlMainEdit.buttonClickByName("btnSaveUserWork");
	// //Quan hệ
	var tabPane = dlMainEdit.tabbedPane("tabbedPane");
	tabPane.clickTab("Quan hệ");
	var userRelationshipTable = dlMainEdit.table("userRelationshipTable");
	userRelationshipTable.doubleClickRow("Relation HktTest1");
	frameui.delay();
	pnMain.fieldSet("txtRelation", "Relation HktTest1 (Edit)");
	pnMain.fieldSet("txtFirstName", "FirstName HktTest1 (Edit)");
	pnMain.fieldSet("txtLastName", "LastName HktTest1 (Edit)");
	pnMain.fieldSet("txtOccupation", "Occupation HktTest1 (Edit)");
	frameui.delay();
	dlMainEdit.buttonClickByName("btnSaveUserRelationship");
	// Thông tin liên lạc
	var tabPane = dlMainEdit.tabbedPane("tabbedPane");
	tabPane.clickTab("Thông tin liên lạc");
	var contactTable = dlMainEdit.table("contactTable");
	contactTable.doubleClickRow("Phường Dịch Vọng HktTest1");
	frameui.delay();
	pnMain.fieldSet("txtAddressNumber", "Số 335 HktTest1 (Edit)");
	pnMain.fieldSet("txtXaPhuong", "Phường Dịch Vọng HktTest1 (Edit)");
	pnMain.fieldSet("txtStreet", "Cầu giấy HktTest1 (Edit)");
	pnMain.fieldSet("txtPhone", "065432");
	pnMain.fieldSet("txtMobile", "234567");
	pnMain.fieldSet("txtFax", "876543");
	pnMain.fieldSet("txtWebsite", "www.hkt.com/hkt_test_1 (Edit)");
	frameui.delay();
	dlMainEdit.buttonClickByName("btnSaveContact");

	//Lưu lại
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
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac");
	buttonMenu.mouseClick();
	var button = frameui.button("listPartner");
	button.mouseClick();

	var dlMain = frameui.dialog("dllistPartner");
	var tblistPartner = dlMain.table("tblistPartner");
	
	//Test chuyển trang
	frameui.showDialogTest("Sang trang 2");
	dlMain.buttonClick("2");
	frameui.delay();
	frameui.showDialogTest("Trở về trang đầu tiên");
	dlMain.buttonClick("<<");
	frameui.delay();
	
	//Test tìm kiếm 
	frameui.showDialogTest("[Tìm kiếm] Tìm theo tên đối tác");
	var comboBox = dlMain.comboBox("cbSearch");
	comboBox.selectItem("Tên đối tác");
	dlMain.fieldSet("txtSearch", "ĐT HktTest");
	frameui.delay();
	tblistPartner.doubleClickRow("ĐT HktTest (Edit)");
	frameui.delay();

	var dlMainEdit = frameui.dialog("dlPartnerIsUser");
	var pnMain = dlMainEdit.panel("PartnerIsUser");
	
	dlMainEdit.buttonClickByName("btnEdit");
	dlMainEdit.buttonClickByName("btnDelete");
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");
	frameui.showDialogTest("Xóa thành công 1 đối tác cá nhân");
	frameui.delay();
	frameui.showDialogTest("Xóa toàn bộ dữ liệu TEST");	
	pnMain.deleteDataTest();
	frameui.delay();
	dlMainEdit.buttonClickByName("btnExit");
	frameui.delay();
	dlMain.buttonClick("Thoát");

}

var robot = new Robot();
robot.add("TEST PartnerIsUser ", "", CreatePartnerIsUser);
robot.add("TEST EDIT PartnerIsUser ", "", EditPartnerIsUser);
robot.add("TEST DELETE PartnerIsUser ", "", DeletePartnerIsUser);
console.clear();
robot.run();
robot.report();