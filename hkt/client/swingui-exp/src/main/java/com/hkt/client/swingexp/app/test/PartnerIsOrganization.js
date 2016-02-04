ScriptRunner.require("robotExp.js");

/*
 * @author Phan Anh
 */

function CreatePartnerIsOrganization(frameui) {
	frameui.showDialogTest("CHƯƠNG TRÌNH TEST NHÀ CUNG CẤP DOANH NGHIỆP");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightKhachHangDoiTac");
	buttonMenu.mouseClick();
	var button = frameui.button("PartnerIsOrganization");
	button.mouseClick();

	var dlMain = frameui.dialog("dlPartnerIsOrganization");
	var pnMain = dlMain.panel("PartnerIsOrganization");
	
	frameui.showDialogTest("Chạy thử chức năng [Lưu]");
	frameui.delay();
	
	if(frameui.showDialogTest("[Lỗi] Thiếu tên đối tác")){
		dlMain.buttonClickByName("btnSave");
		frameui.delay();
	}
	
	pnMain.fieldSet("txtTenDoanhNghiep", "ĐT HktTest");
	pnMain.fieldSet("txtFullName", "DN ĐT HktTest");
	pnMain.fieldSet("txtPhone", "123456789");
	pnMain.fieldSet("txtFax", "111111");
	pnMain.fieldSet("txtAddress", "Address HktTest1");
	pnMain.fieldSet("txtSlogan", "Slogan HktTest");
	
	var cboOrganizationType = pnMain.comboBox("cboOrganizationType");
	cboOrganizationType.selectItem("Cafe");
	var cboManager = pnMain.comboBox("cboManager");
	cboManager.selectItem("Có");
	pnMain.fieldSet("txtRegistrationCode", "MST HktTest");
	pnMain.fieldSet("txtRepresentative", "ĐDDN HktTest");	
	pnMain.fieldSet("txtDescription", "Description HktTest");
	frameui.delay();
	
	//Đăng ký kinh doanh
	var tabPane = dlMain.tabbedPane("tabbedPane");
	tabPane.clickTab("Đăng kí kinh doanh");
	pnMain.fieldSet("txtFullNameVN", "TV HktTest1");
	pnMain.fieldSet("txtFullNameEN", "EN HktTest1");
	pnMain.fieldSet("txtPersionActive", "PersionActive HktTest1");
	pnMain.fieldSet("txtDomain", "Domain HktTest1");
	pnMain.fieldSet("txtCodeRegis", "CodeRegis HktTest1");
	pnMain.fieldSet("txtTaxCode", "TaxCode HktTest1");
	pnMain.fieldSet("txtCharterCapital", "CharterCapital HktTest1");
	pnMain.fieldSet("txtlegalCapital", "legalCapital HktTest1");
	pnMain.fieldSet("txtDescription", "Description HKT-Test1");

	dlMain.buttonClickByName("btnAddBusinessRegistration");
	frameui.delay();
	
	//Thông tin liên lạc
	var tabPane = dlMain.tabbedPane("tabbedPane");
	tabPane.clickTab("Thông tin liên lạc");
	if(frameui.showDialogTest("[Lỗi] Chưa nhập thông tin địa chỉ")){
		frameui.delay();
		dlMain.buttonClickByName("btnAddContact");
		frameui.delay();
	}
	
	pnMain.fieldSet("txtAddressNumber", "Số 335 HktTest1");
	pnMain.fieldSet("txtXaPhuong", "Phường Dịch Vọng HktTest1");
	pnMain.fieldSet("txtStreet", "Cầu giấy HktTest1");
	pnMain.fieldSet("txtPhone", "123456789");
	pnMain.fieldSet("txtMobile", "12345678");
	pnMain.fieldSet("txtFax", "0000000000");
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

//Chạy thử chức năng chỉnh sửa đối tác doanh nghiệp
function EditPartnerIsOrganization(frameui) {
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
	var dlMainEdit = frameui.dialog("dlPartnerIsOrganization");
	var pnMain = dlMainEdit.panel("PartnerIsOrganization");
	dlMainEdit.buttonClickByName("btnEdit");
	
	pnMain.fieldSet("txtTenDoanhNghiep", "ĐT HktTest (Edit)");
	pnMain.fieldSet("txtFullName", "DN ĐT HktTest (Edit)");
	pnMain.fieldSet("txtPhone", "987654321");
	pnMain.fieldSet("txtFax", "23456");
	pnMain.fieldSet("txtAddress", "Address HktTest1 (Edit)");
	pnMain.fieldSet("txtSlogan", "Slogan HktTest (Edit)");
	frameui.delay();
	
	//Dang ki kinh doanh
	var tabPane = dlMainEdit.tabbedPane("tabbedPane");
	tabPane.clickTab("Đăng kí kinh doanh");
	var tableBusinessRegistration = dlMainEdit.table("tableBusinessRegistration");
	tableBusinessRegistration.doubleClickRow("EN HktTest1");
	frameui.delay();	
	pnMain.fieldSet("txtFullNameVN", "TV HktTest1 (Edit)");
	pnMain.fieldSet("txtFullNameEN", "EN HktTest1 (Edit)");
	pnMain.fieldSet("txtPersionActive", "PersionActive HktTest1 (Edit)");
	pnMain.fieldSet("txtDomain", "Domain HktTest1 (Edit)");
	pnMain.fieldSet("txtCodeRegis", "CodeRegis HktTest1 (Edit)");
	pnMain.fieldSet("txtTaxCode", "TaxCode HktTest1 (Edit)");
	pnMain.fieldSet("txtCharterCapital", "CharterCapital HktTest1 (Edit)");
	pnMain.fieldSet("txtlegalCapital", "legalCapital HktTest1 (Edit)");
	pnMain.fieldSet("txtDescription", "Description HKT-Test1 (Edit)");

	dlMainEdit.buttonClickByName("btnSaveBusinessRegistration");
	frameui.delay();
	
	// Thông tin liên lạc
	var tabPane = dlMainEdit.tabbedPane("tabbedPane");
	tabPane.clickTab("Thông tin liên lạc");
	var contactTable = dlMainEdit.table("contactTable");
	contactTable.doubleClickRow("Phường Dịch Vọng HktTest1");
	frameui.delay();
	pnMain.fieldSet("txtAddressNumber", "Số 335 HktTest1 (Edit)");
	pnMain.fieldSet("txtXaPhuong", "Phường Dịch Vọng HktTest1 (Edit)");
	pnMain.fieldSet("txtStreet", "Cầu giấy HktTest1 (Edit)");
	pnMain.fieldSet("txtPhone", "876543");
	pnMain.fieldSet("txtMobile", "23456");
	pnMain.fieldSet("txtFax", "234567");
	pnMain.fieldSet("txtWebsite", "www.hkt.com/hkt_test_1 (Edit)");
	frameui.delay();
	dlMainEdit.buttonClickByName("btnSaveContact");
	
	// Save
	frameui.delay();
	dlMainEdit.buttonClickByName("btnSave");
	frameui.delay();
	//Thêm 50 bản ghi
	pnMain.createDataTest();
	dlMainEdit.buttonClickByName("btnExit");
	dlMain.buttonClick("Thoát");
	frameui.delay();
}

//Chạy thử chức năng xóa 1 đối tác doanh nghiệp
function DeletePartnerIsOrganization(frameui) {
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
	
	var dlMainEdit = frameui.dialog("dlPartnerIsOrganization");
	var pnMainEdit = dlMainEdit.panel("PartnerIsOrganization");
	
	dlMainEdit.buttonClickByName("btnEdit");
	dlMainEdit.buttonClickByName("btnDelete");
	var dlDelete = frameui.dialog("dlXoa");
	var pnDelete = dlDelete.panel("Xoa");
	dlDelete.buttonClick("Đồng ý");
	frameui.showDialogTest("Xóa thành công 1 đối tác doanh nghiệp");
	frameui.showDialogTest("Xóa toàn bộ dữ liệu TEST");	
	pnMainEdit.deleteDataTest();
	dlMainEdit.buttonClickByName("btnExit");
	frameui.delay();	
	dlMain.buttonClick("Thoát");
}

var robot = new Robot();
robot.add("TEST PartnerIsOrganization ", "", CreatePartnerIsOrganization);
robot.add("TEST EDIT PartnerIsOrganization ", "", EditPartnerIsOrganization);
robot.add("TEST DELETE PartnerIsOrganization ", "", DeletePartnerIsOrganization);
console.clear();
robot.run();
robot.report();