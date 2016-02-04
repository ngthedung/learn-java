ScriptRunner.require("robotExp.js");

/*
 * @author Phan Anh
 */

function CreateSupplierIsOrganization(frameui) {
	frameui.showDialogTest("CHƯƠNG TRÌNH TEST NHÀ CUNG CẤP DOANH NGHIỆP");
	frameui.delay();
	var buttonMenu = frameui.button("menuRightNhanSu");
	buttonMenu.mouseClick();
	var button = frameui.button("SupplierIsOrganization");
	button.mouseClick();

	var dlMain = frameui.dialog("dlSupplierIsOrganization");
	var pnMain = dlMain.panel("SupplierIsOrganization");

	frameui.showDialogTest("Chạy thử chức năng [Lưu]");
	frameui.delay();
	
	if(frameui.showDialogTest("[Lỗi] chưa nhập tên Nhà cung cấp!")){
		frameui.delay();
		dlMain.buttonClickByName("btnSave");
		frameui.delay();
	}

	// TAB thông tin cơ bản
	pnMain.fieldSet("txtTenDoanhNghiep", "Tên doanh nghiệp HktTest");
	pnMain.fieldSet("txtFullName", "Tên đầy đủ doanh nghiệp HktTest");
	pnMain.fieldSet("txtPhone", "0123456789");
	pnMain.fieldSet("txtRegistrationCode", "0812345678");
	pnMain.fieldSet("txtAddress", "Địa chỉ chi tiết HktTest");
	pnMain.fieldSet("txtFax", "04312345678");
	pnMain.fieldSet("txtRepresentative", "Đại diện doanh nghiệp HktTest");
	pnMain.fieldSet("txtSlogan", "Tiêu chí - khẩu ngữ Cty HktTest");
	pnMain.fieldSet("txtDescription", "Mô tả đặc điểm chi tiết HktTest");	
	frameui.delay();

	// TAB đăng ký kinh doanh
	var tabPane = dlMain.tabbedPane("tabbedPane");
	tabPane.clickTab("Đăng kí kinh doanh");
	pnMain.fieldSet("txtFullNameVN", "Tên tiếng việt công ty HktTest1");
	pnMain.fieldSet("txtFullNameEN", "Tên tiếng anh công ty HktTest1");
	pnMain.fieldSet("txtPersionActive", "Người đại diện HktTest1");
	pnMain.fieldSet("txtDomain", "Lĩnh vực kinh doanh HktTest1");
	pnMain.fieldSet("txtCodeRegis", "Mã đăng kí HktTest1");
	pnMain.fieldSet("txtTaxCode", "Mã số thuế HktTest1");
	pnMain.fieldSet("txtCharterCapital", "Vốn điều lệ HktTest1");
	pnMain.fieldSet("txtlegalCapital", "Vốn pháp định HktTest1");
	pnMain.fieldSet("txtDescription", "Mô tả chi tiết HktTest1");
	dlMain.buttonClickByName("btnAddBusinessRegistration");
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
	pnMain.fieldSet("txtPhone", "09821154551");
	pnMain.fieldSet("txtMobile", "046151541");
	pnMain.fieldSet("txtFax", "051548941");
	pnMain.fieldSet("txtWebsite", "www.hktconsultants.com/HktTest1");
	var cbCity = pnMain.comboBox("cbCity");
	cbCity.selectItem("Hà Nội");
	dlMain.buttonClickByName("btnAddContact");
	frameui.delay();
	
	dlMain.buttonClickByName("btnSave");
	frameui.delay();
	dlMain.buttonClickByName("btnExit");
	frameui.delay();
}

function EditSupplierIsOrganization(frameui) {
	frameui.showDialogTest("Chạy thử chức năng [Sửa]");
	var buttonMenu = frameui.button("menuRightNhanSu");
	buttonMenu.mouseClick();
	var button = frameui.button("ListSuppliers");
	button.mouseClick();

	var dlMain = frameui.dialog("dlListSuppliers");
	var tbListSuppliers = dlMain.table("tbListSuppliers");
	tbListSuppliers.doubleClickRow("Tên doanh nghiệp HktTest");

	var dlMainEdit = frameui.dialog("dlSupplierIsOrganization");
	var pnMain = dlMainEdit.panel("SupplierIsOrganization");
	dlMainEdit.buttonClickByName("btnEdit");

	pnMain.fieldSet("txtTenDoanhNghiep", "Tên doanh nghiệp HktTest (Sửa)");
	pnMain.fieldSet("txtFullName", "Tên đầy đủ doanh nghiệp HktTest (Sửa)");
	pnMain.fieldSet("txtPhone", "0123456789");
	pnMain.fieldSet("txtRegistrationCode", "08123456789");
	pnMain.fieldSet("txtAddress", "Địa chỉ doanh nghiệp HktTest (Sửa)");
	pnMain.fieldSet("txtFax", "04123456789");
	pnMain.fieldSet("txtRepresentative", "Đại diện DN HktTest (Sửa)");
	pnMain.fieldSet("txtSlogan", "Khẩu hiệu - tiêu chí công ty HktTest (Sửa)");
	pnMain.fieldSet("txtDescription", "Mô tả chi tiết đặc điểm HktTest (Sửa)");
	frameui.delay();
	
	// TAB đăng ký kinh doanh
	var tabPane = dlMainEdit.tabbedPane("tabbedPane");
	tabPane.clickTab("Đăng kí kinh doanh");
	var tableBusinessRegistration = dlMainEdit.table("tableBusinessRegistration");
	tableBusinessRegistration.doubleClickRow("Tên tiếng anh công ty HktTest1");
	frameui.delay();	
	pnMain.fieldSet("txtFullNameVN", "Tên tiếng việt công ty HktTest1 (Sửa)");
	pnMain.fieldSet("txtFullNameEN", "Tên tiếng anh công ty HktTest1 (Sửa)");
	pnMain.fieldSet("txtPersionActive", "Người đại diện HktTest1 (Sửa)");
	pnMain.fieldSet("txtDomain", "Lĩnh vực kinh doanh HktTest1 (Sửa)");
	pnMain.fieldSet("txtCodeRegis", "Mã đăng ký HktTest1 (Sửa)");
	pnMain.fieldSet("txtTaxCode", "Mã số thuế HktTest1 (Sửa)");
	pnMain.fieldSet("txtCharterCapital", "Vốn điều lệ HktTest1 (Sửa)");
	pnMain.fieldSet("txtlegalCapital", "Vốn pháp định HktTest1 (Sửa)");
	pnMain.fieldSet("txtDescription", "Mô tả chi tiết HktTest1 (Sửa)");
	dlMainEdit.buttonClickByName("btnSaveBusinessRegistration");
	frameui.delay();
	
	// TAB thông tin liên lạc
	var tabPane = dlMainEdit.tabbedPane("tabbedPane");
	tabPane.clickTab("Thông tin liên lạc");
	var contactTable = dlMainEdit.table("contactTable");
	contactTable.doubleClickRow("Phường Dịch Vọng HktTest1");
	frameui.delay();
	
	pnMain.fieldSet("txtAddressNumber", "Số 335 HktTest1 (Sửa)");
	pnMain.fieldSet("txtXaPhuong", "Phường Dịch Vọng HktTest1 (Sửa)");
	pnMain.fieldSet("txtStreet", "Cầu giấy HktTest1 (Sửa)");
	pnMain.fieldSet("txtPhone", "04387654321");
	pnMain.fieldSet("txtMobile", "087654321");
	pnMain.fieldSet("txtFax", "04387654321");
	pnMain.fieldSet("txtWebsite", "www.hktcónultants.com/HktTest1 (Sửa)");
	dlMainEdit.buttonClickByName("btnSaveContact");
	frameui.delay();
	
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

function DeleteSupplierIsOrganization(frameui) {
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
	tbListSuppliers.doubleClickRow("Tên doanh nghiệp HktTest (Sửa)");
	frameui.delay();
	
	var dlMainEdit = frameui.dialog("dlSupplierIsOrganization");
	var pnMain = dlMainEdit.panel("SupplierIsOrganization");
	
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
robot.add("TEST SupplierIsOrganization ", "", CreateSupplierIsOrganization);
robot.add("TEST EDIT SupplierIsOrganization ", "", EditSupplierIsOrganization);
robot.add("TEST DELETE SupplierIsOrganization ", "", DeleteSupplierIsOrganization);
console.clear();
robot.run();
robot.report();