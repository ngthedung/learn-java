ScriptRunner.require("robotExp.js");

function CreateTestCatalogProductTag(frameui) {
	
	frameui.showDialogTest("Chương trình TEST giao diện Quản lý nhóm hàng");
	
		// Click vào menu Hệ thống
		var buttonMenu = frameui.button("menuRightKhoHangHoa") ;
		buttonMenu.mouseClick();
		
		// Click vào button Tiền tệ
		var button = frameui.button("QuanLyNhomHang");
		button.mouseClick();
		 	
		var dlMain = frameui.dialog("dlNhomhang");
		var pnChild = dlMain.panel("QuanLyNhomHang");
		
		//Test chức năng [Lưu] Khi dữ liệu các trường quan trọng để trống
		frameui.showDialogTest("[Lỗi] Khi Dữ Liệu các trường để trống!");
		dlMain.buttonClick("Lưu");
		frameui.delay();
		frameui.showDialogTest("Thiếu các thông tin quan trọng");
		
		// Test chức năng [Lưu] Khi dữ liệu trường mã trống
		frameui.showDialogTest("[Lỗi] khi chưa nhập mã nhóm hàng");
		dlMain.buttonClick("Viết lại");
		pnChild.fieldSet("txtMaNhom", "");
		pnChild.fieldSet("txtTenNhom", "Nhóm cha HktTest4");
		pnChild.fieldSet("txtMieuTa", "Miêu tả nhóm HktTest4");
		dlMain.buttonClick("Lưu");
		frameui.delay();
		
		// Nhập đúng dữ liệu cho các trường (bản ghi 1)	
		frameui.showDialogTest("[Lưu] Khi Dữ Liệu các trường nhập đúng !");
		pnChild.fieldSet("txtMaNhom", "Mã nhóm HktTest1");
		pnChild.fieldSet("txtTenNhom", "Nhóm 1 HktTest1");
		pnChild.fieldSet("txtMieuTa", "Miêu tả nhóm HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.delay();
		frameui.showDialogTest(" Lưu thành công !");
		
		// Test chức năng [Lưu] khi dữ liệu trường mã bị trùng
		
		frameui.showDialogTest("[Lỗi] khi dữ liệu trường mã trùng.");
		dlMain.buttonClick("Viết lại");
		pnChild.fieldSet("txtMaNhom","Mã nhóm HktTest1");
		pnChild.fieldSet("txtTenNhom","Nhóm cha HktTest5");
		pnChild.fieldSet("txtMieuTa","Miêu tả nhóm HktTest5");
		dlMain.buttonClick("Lưu");
		frameui.delay();
		
		//Test chức năng Xem lại
		frameui.showDialogTest("Chạy thử chức năng xem lại ");
		dlMain.buttonClick("Viết lại");
		var table = pnChild.table("tbpnTable");
		table.doubleClickRow("Mã nhóm HktTest1");	
		frameui.delay();
		dlMain.buttonClick("Sửa");	
		frameui.delay();
		
		// Sửa lại các giá trị ban đầu 
		pnChild.fieldSet("txtTenNhom", "Nhóm cha HktTest6");
		frameui.delay();
		pnChild.fieldSet("txtMieuTa", "Miêu tả nhóm HktTest6");
		frameui.delay();
		dlMain.buttonClick("Xem lại");
		frameui.delay();
			
		//chương trình chạy chức năng Sửa + lưu
		frameui.showDialogTest("Chạy thử chức năng sửa");
		dlMain.buttonClick("Viết lại");
		var table = pnChild.table("tbpnTable");
		table.doubleClickRow("Mã nhóm HktTest1");	
		frameui.delay();
		dlMain.buttonClick("Sửa");	
		frameui.delay();
		pnChild.fieldSet("txtTenNhom", "Nhóm cha HktTest2");
		frameui.delay();
		pnChild.fieldSet("txtMieuTa", "Miêu tả nhóm HktTest2");
		frameui.delay();
		dlMain.buttonClick("Lưu");
		frameui.delay();
		frameui.showDialogTest("Sửa thành công !");	
		
		// Chạy chức năng reset lại giao diện
		table.doubleClickRow("Mã nhóm HktTest1");
		frameui.delay();
		dlMain.buttonClick("Viết lại");
		
		//Thêm mới 1 bản ghi
		frameui.showDialogTest("[Thêm mới] 1 nhóm hàng");
		pnChild.fieldSet("txtMaNhom", "Mã nhóm HktTest2");
		pnChild.fieldSet("txtTenNhom", "Nhóm 2 HktTest2");
		pnChild.fieldSet("txtMieuTa", "Miêu tả nhóm HktTest2");
		dlMain.buttonClick("Lưu");
		frameui.delay();
		frameui.showDialogTest(" Lưu thành công !");
		
	// chạy thử chức năng thay đổi vị trí
		frameui.showDialogTest("Chương trình chạy thử chức năng vị trí");
		table.selectRow("Mã nhóm HktTest2");
		frameui.delay();
		dlMain.buttonClickByName("btnUp")
		frameui.delay();
		pnChild.buttonClick("Cập nhật")
		frameui.delay();
		  
	// Chạy chức năng Xóa
		frameui.showDialogTest("Chạy thử chức năng xóa bản ghi");
		table.doubleClickRow("Mã nhóm HktTest1");
		frameui.delay();
		dlMain.buttonClick("Sửa");
		frameui.delay();
		dlMain.buttonClick("Xóa");
		var dlDelete = frameui.dialog("dlXoa");
		var pnDelete = dlDelete.panel("Xoa");
		dlDelete.buttonClick("Đồng ý");
		frameui.delay();	
		 
	//Thoat. oke Test finish!
		frameui.showDialogTest("Chương trình TEST Quản lý nhóm hàng thành công !");
		pnChild.deleteDataTest();
		dlMain.buttonClick("Thoát");
		
}
var robot = new Robot() ;
robot.add("TEST Nhóm hàng ", "",CreateTestCatalogProductTag);
console.clear() ;
robot.run() ;
robot.report() ;
