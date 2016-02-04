ScriptRunner.require("robotExp.js");

/*
 * @author Phan Anh
 */

function CreateSupplierIsUser(frameui) {
	frameui.showDialogTest("CHƯƠNG TRÌNH TEST NHÀ CUNG CẤP CÁ NHÂN");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightNhanSu");
	buttonMenu.mouseClick();
	var button = frameui.button("SupplierIsUser");
	button.mouseClick();

	var dlMain = frameui.dialog("dlSupplierIsUser");
	var pnMain = dlMain.panel("SupplierIsUser");

	frameui.showDialogTest("Chạy thử chức năng [Lưu]");
	frameui.delay();
	
	if(frameui.showDialogTest("[Lỗi] chưa nhập tên Nhà cung cấp!")){
		frameui.delay();
		dlMain.buttonClickByName("btnSave");
		frameui.delay();
	}
	// TAB thông tin cơ bản
	pnMain.fieldSet("txtLastName", "Nhà cung cấp CN HktTest");
	pnMain.fieldSet("txtMobile", "0123456789");
	pnMain.fieldSet("txtAddress", "Địa chỉ chi tiết HktTest");
	pnMain.fieldSet("txtFax", "04312345678");
	pnMain.fieldSet("txtHeight", "178");
	pnMain.fieldSet("txtWeight", "70");
	pnMain.fieldSet("txtCMT", "123456789");
	pnMain.fieldSet("txtHobby", "Sở thích đặc điểm cá nhân HktTest");
	var cbMaritalStatus = pnMain.comboBox("cbMaritalStatus");
	cbMaritalStatus.selectItem("Độc thân");
	var cboGender = pnMain.comboBox("cboGender");
	cboGender.selectItem("Nam");
	frameui.delay();

	// TAB học vấn
	var tabPane = dlMain.tabbedPane("tabbedPane");
	tabPane.clickTab("Học vấn");	
	if(frameui.showDialogTest("[Lỗi] chưa nhập tên Tên trường học!")){
		frameui.delay();
		dlMain.buttonClickByName("btnAddUserEducation");
		frameui.delay();
	}
	//Dòng 1
	pnMain.fieldSet("txtSchoolOrInstitude", "Trường học HktTest1");
	pnMain.fieldSet("txtMajor", "Chuyên môn HktTest1");
	pnMain.fieldSet("txtCertificate", "Bằng cấp HktTest1");
	pnMain.fieldSet("txtLanguage", "Ngôn ngữ HktTest1");
	pnMain.fieldSet("txtDescription", "Mô tả đặc điểm HKT-Test1");
	dlMain.buttonClickByName("btnAddUserEducation");
	frameui.delay();

	// TAB kinh nghiệm làm việc
	var tabPane = dlMain.tabbedPane("tabbedPane");
	tabPane.clickTab("Kinh nghiệm làm việc");
	if(frameui.showDialogTest("[Lỗi] chưa nhập tên Tên công ty!")){
		frameui.delay();
		dlMain.buttonClickByName("btnAddUserWork");
		frameui.delay();
	}
	//Dòng 1
	pnMain.fieldSet("txtOrganization", "Doanh nghiệp HktTest1");
	dlMain.buttonClickByName("btnAddUserWork");
	frameui.delay();
	//Dòng 2
	pnMain.fieldSet("txtPosition", "Vị trí công ty HktTest1");
	pnMain.fieldSet("txtDescription", "Mô tả chi tiết công ty HktTest1");
	dlMain.buttonClickByName("btnAddUserWork");
	frameui.delay();

	// TAB quan hệ
	var tabPane = dlMain.tabbedPane("tabbedPane");
	tabPane.clickTab("Quan hệ");
	if(frameui.showDialogTest("[Lỗi] chưa nhập tên Tên quan hệ!")){
		frameui.delay();
		dlMain.buttonClickByName("btnAddUserRelationship");
		frameui.delay();
	}
	//Dòng 1
	pnMain.fieldSet("txtRelation", "Mối quan hệ HktTest1");
	dlMain.buttonClickByName("btnAddUserRelationship");
	frameui.delay();
	//Dòng 2
	pnMain.fieldSet("txtFirstName", "Họ đệm người quen HktTest1");
	pnMain.fieldSet("txtLastName", "Tên người quen HktTest1");
	pnMain.fieldSet("txtOccupation", "Nghề nghiệp HktTest1");
	var cboGenderRelationship = pnMain.comboBox("cboGenderRelationship");
	cboGenderRelationship.selectItem("Nam");
	dlMain.buttonClickByName("btnAddUserRelationship");
	frameui.delay();

	// TAB thông tin liên lạc
	var tabPane = dlMain.tabbedPane("tabbedPane");
	tabPane.clickTab("Thông tin liên lạc");
	if(frameui.showDialogTest("[Lỗi] chưa nhập Thông tin địa chỉ")){
		frameui.delay();
		dlMain.buttonClickByName("btnAddContact");
		frameui.delay();
	}
	pnMain.fieldSet("txtAddressNumber", "Số 335 HktTest1");
	pnMain.fieldSet("txtXaPhuong", "Phường Dịch Vọng HktTest1");
	pnMain.fieldSet("txtStreet", "Cầu giấy HktTest1");
	pnMain.fieldSet("txtPhone", "043123456789");
	pnMain.fieldSet("txtMobile", "0123456789");
	pnMain.fieldSet("txtFax", "043123456789");
	pnMain.fieldSet("txtWebsite", "www.hktconsultants.com/HktTest1");
	var cbCity = pnMain.comboBox("cbCity");
	cbCity.selectItem("Hà Nội");
	frameui.delay();
	dlMain.buttonClickByName("btnAddContact");

	dlMain.buttonClickByName("btnSave");
	frameui.delay();

	dlMain.buttonClickByName("btnExit");
}

function EditSupplierIsUser(frameui) {
	frameui.showDialogTest("Chạy thử chức năng [Sửa]");
	var buttonMenu = frameui.button("menuRightNhanSu");
	buttonMenu.mouseClick();
	var button = frameui.button("ListSuppliers");
	button.mouseClick();

	var dlMain = frameui.dialog("dlListSuppliers");
	var tbListSuppliers = dlMain.table("tbListSuppliers");
	tbListSuppliers.doubleClickRow("Nhà cung cấp CN HktTest");

	var dlMainEdit = frameui.dialog("dlSupplierIsUser");
	var pnMain = dlMainEdit.panel("SupplierIsUser");
	dlMainEdit.buttonClickByName("btnEdit");

	pnMain.fieldSet("txtLastName", "Nhà cung cấp CN HktTest (Sửa)");
	pnMain.fieldSet("txtMobile", "01239999999");
	pnMain.fieldSet("txtAddress", "Địa chỉ chi tiết HktTest (Sửa)");
	pnMain.fieldSet("txtFax", "043999999");
	pnMain.fieldSet("txtHeight", "185");
	pnMain.fieldSet("txtWeight", "50");
	pnMain.fieldSet("txtCMT", "143999999999");
	pnMain.fieldSet("txtHobby", "Sở thích đặc điểm cá nhân HKT-Test (Sửa)");
	frameui.delay();
	
	// TAB học vấn
	var tabPane = dlMainEdit.tabbedPane("tabbedPane");
	tabPane.clickTab("Học vấn");
	var userEducationTable = dlMainEdit.table("userEducationTable");
	userEducationTable.doubleClickRow("Trường học HktTest1");
	frameui.delay();
	pnMain.fieldSet("txtSchoolOrInstitude", "Trường học HktTest1 (Sửa)");
	pnMain.fieldSet("txtMajor", "Chuyên môn HktTest1 (Sửa)");
	pnMain.fieldSet("txtCertificate", "Bằng cấp HktTest1 (Sửa)");
	pnMain.fieldSet("txtLanguage", "Ngôn ngữ HktTest1 (Sửa)");
	pnMain.fieldSet("txtDescription", "Mô tả đặc điểm chi tiết HktTest1 (Sửa)");
	frameui.delay();
	dlMainEdit.buttonClickByName("btnSaveUserEducation");
	
	// TAB kinh nghiệm làm việc
	var tabPane = dlMainEdit.tabbedPane("tabbedPane");
	tabPane.clickTab("Kinh nghiệm làm việc");
	var userWorkTable = dlMainEdit.table("userWorkTable");
	userWorkTable.doubleClickRow("Doanh nghiệp HktTest1");
	frameui.delay();
	pnMain.fieldSet("txtOrganization", "Doanh nghiệp HktTest1 (Sửa)");
	pnMain.fieldSet("txtPosition", "Vị trí công ty HktTest1 (Sửa)");
	pnMain.fieldSet("txtDescription", "Mô tả đặc điểm chi tiết HktTest1 (Sửa)");
	frameui.delay();
	dlMainEdit.buttonClickByName("btnSaveUserWork");

	// TAB quan hệ
	var tabPane = dlMainEdit.tabbedPane("tabbedPane");
	tabPane.clickTab("Quan hệ");
	var userRelationshipTable = dlMainEdit.table("userRelationshipTable");
	userRelationshipTable.doubleClickRow("Mối quan hệ HktTest1");
	frameui.delay();
	pnMain.fieldSet("txtRelation", "Mối quan hệ HktTest1 (Sửa)");
	pnMain.fieldSet("txtFirstName", "Họ đệm người quen biết HktTest1 (Sửa)");
	pnMain.fieldSet("txtLastName", "Tên người quen biết HktTest1 (Sửa)");
	pnMain.fieldSet("txtOccupation", "Nghề nghiệp HktTest1 (Sửa)");
	frameui.delay();
	dlMainEdit.buttonClickByName("btnSaveUserRelationship");
	
	// TAB thông tin liên lạc
	var tabPane = dlMainEdit.tabbedPane("tabbedPane");
	tabPane.clickTab("Thông tin liên lạc");
	var contactTable = dlMainEdit.table("contactTable");
	contactTable.doubleClickRow("Phường Dịch Vọng HktTest1");
	frameui.delay();
	pnMain.fieldSet("txtAddressNumber", "Số 335 HktTest1 (Sửa)");
	pnMain.fieldSet("txtXaPhuong", "Phường Dịch Vọng HktTest1 (Sửa)");
	pnMain.fieldSet("txtStreet", "Cầu giấy HktTest1 (Sửa)");
	pnMain.fieldSet("txtPhone", "04312345678");
	pnMain.fieldSet("txtMobile", "0123456789");
	pnMain.fieldSet("txtFax", "04312345678");
	pnMain.fieldSet("txtWebsite", "www.hktconsultants.com/HktTest1 (Sửa)");
	dlMainEdit.buttonClickByName("btnSaveContact");
	frameui.delay();

	dlMainEdit.buttonClickByName("btnSave");
	frameui.delay();
	//Thêm 50 bản ghi
	pnMain.createDataTest();
	frameui.delay();
	dlMainEdit.buttonClickByName("btnExit");
	frameui.delay();
	dlMain.buttonClick("Thoát");
	frameui.delay();
}

function DeleteSupplierIsUser(frameui) {
	frameui.showDialogTest("Chạy chức năng [TEST chuyển trang]");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightNhanSu");
	buttonMenu.mouseClick();
	frameui.delay();
	var button = frameui.button("ListSuppliers");
	button.mouseClick();
	frameui.delay();
	
	var dlMain = frameui.dialog("dlListSuppliers");
	var tbListSuppliers = dlMain.table("tbListSuppliers");
	frameui.delay();
	
	//Test chuyển trang
	frameui.showDialogTest("Sang trang 2");
	dlMain.buttonClick("2");
	frameui.delay();
	frameui.showDialogTest("Trở về trang đầu tiên");
	dlMain.buttonClick("<<");
	frameui.delay();
	
	//Test tìm kiếm 
	frameui.showDialogTest("[Tìm kiếm] Tìm theo Tên nhà cung cấp");
	var comboBox = dlMain.comboBox("cbSearch");
	comboBox.selectItem("Tên nhà cung cấp");
	dlMain.fieldSet("txtSearch", "Sửa");
	frameui.delay();
	tbListSuppliers.doubleClickRow("Nhà cung cấp CN HktTest (Sửa)");
	frameui.delay();
	
	var dlMainEdit = frameui.dialog("dlSupplierIsUser");
	var pnMain = dlMainEdit.panel("SupplierIsUser");
	
	dlMainEdit.buttonClickByName("btnEdit");
	dlMainEdit.buttonClickByName("btnDelete");
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");
	frameui.showDialogTest("Xóa thành công nhà cung cấp");
	frameui.delay();
	frameui.showDialogTest("Xóa toàn bộ dữ liệu TEST");	
	pnMain.deleteDataTest();
	frameui.delay();
	dlMainEdit.buttonClickByName("btnExit");
	frameui.delay();
	dlMain.buttonClick("Thoát");
}

var robot = new Robot();
robot.add("TEST SupplierIsUser ", "", CreateSupplierIsUser);
robot.add("TEST EDIT SupplierIsUser ", "", EditSupplierIsUser);
robot.add("TEST DELETE SupplierIsUser ", "", DeleteSupplierIsUser);
console.clear();
robot.run();
robot.report();