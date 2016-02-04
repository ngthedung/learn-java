ScriptRunner.require("robotExp.js");

function manageGroupPartner(frameui){
	
		frameui.showDialogTest("Chương trình TEST giao diện Quản lý nhóm khách hàng");
		frameui.delay();
		var buttonMenu = frameui.button("menuRightKhachHangDoiTac") ;
		buttonMenu.mouseClick();
		
		var button = frameui.button("QLNhomKhachHang");
		button.mouseClick();
		 	
		var dlMain = frameui.dialog("dlGroupCustomer");
		var pnChild = dlMain.panel("QLNhomKhachHang");
		
		// Kiểm tra validate các trường quan trọng để trống
		frameui.showDialogTest("Chạy thử chức năng [Lưu]");
		frameui.delay();
		frameui.showDialogTest("[Lưu] khi để trống các trường!");
		frameui.delay();
		dlMain.buttonClick("Lưu");
		frameui.showDialogTest("[Lỗi] thiếu các thông tin quan trọng!");
		frameui.delay();
		
		frameui.showDialogTest("[Lỗi] chưa nhập Tên nhóm KH!");
		frameui.delay();
		dlMain.buttonClick("Viết lại");
		pnChild.fieldSet("txtLabel", "");
		dlMain.buttonClick("Lưu");
		frameui.delay();
		
		frameui.showDialogTest("[Lỗi] chưa nhập Mã nhóm KH!");
		frameui.delay();
		dlMain.buttonClick("Viết lại");
		pnChild.fieldSet("txtLabel", "Tên NKH HktTest1");
		pnChild.fieldSet("txtName", "");
		dlMain.buttonClick("Lưu");
		frameui.delay();		

		// Lưu khi nhập đúng dữ liệu các trường
		pnChild.fieldSet("txtName", "Mã NKH HktTest1");
		pnChild.fieldSet("txtLabel", "Tên NKH HktTest1");
		pnChild.fieldSet("txtDescription", "Miêu tả NKH HktTest1");
		dlMain.buttonClick("Lưu");
		frameui.showDialogTest("Lưu thành công !");
		frameui.delay();
		
		// Kiểm tra validate các trường dữ liệu khi trường mã trùng
//		frameui.showDialogTest("[Lưu] khi Mã NKH trùng!");
//		frameui.delay();
//		dlMain.buttonClick("Viết lại");
//		pnChild.fieldSet("txtName", "Mã NKH HktTest1");
//		pnChild.fieldSet("txtLabel", "Tên NKH HktTest2");
//		pnChild.fieldSet("txtDescription", "Miêu tả NKH HktTest2");
//		dlMain.buttonClick("Lưu");
//		frameui.delay();
//		frameui.showDialogTest("[Lỗi] trùng mã NKH!");
//		frameui.delay();
	
		// chạy thử chức năng xem lại
		frameui.showDialogTest("Chạy thử chức năng [Xem lại]");
		dlMain.buttonClick("Viết lại");
		var table = pnChild.table("accountGroupTable");
		table.doubleClickRow("Mã NKH HktTest1");	
		dlMain.buttonClick("Sửa");	
		frameui.delay();
		
		// Sửa lại các giá trị ban đầu từ 1 thành 2
		pnChild.fieldSet("txtLabel", "Tên NKH HktTest2");
		pnChild.fieldSet("txtDescription", "Miêu tả NKH HktTest2");
		dlMain.buttonClick("Xem lại");
		frameui.delay();
		
		// Chạy chức năng chỉnh sửa
		frameui.showDialogTest("Chạy thử chức năng [Sửa]");	
		dlMain.buttonClick("Viết lại");
		var table = pnChild.table("accountGroupTable");
		table.doubleClickRow("Mã NKH HktTest1");	
		dlMain.buttonClick("Sửa");
		pnChild.fieldSet("txtLabel", "Tên NKH HktTest2");
		pnChild.fieldSet("txtDescription", "Miêu tả NKH HktTest2");
		dlMain.buttonClick("Lưu");
		frameui.showDialogTest("Sửa thành công!");
		frameui.delay();
		
		// Chạy chức năng reset lại giao diện
		frameui.showDialogTest("Chạy thử chức năng [Viết lại]");
		table.doubleClickRow("Mã NKH HktTest1");	
		dlMain.buttonClick("Viết lại");
		frameui.delay();
		
		//Thêm mới 1 nhóm khách hàng
		pnChild.fieldSet("txtName", "Mã NKH HktTest2");
		pnChild.fieldSet("txtLabel", "Tên NKH HktTest2");
		pnChild.fieldSet("txtDescription", "Miêu tả NKH HktTest2");
		dlMain.buttonClick("Lưu");
		frameui.showDialogTest("Lưu thành công !");
		frameui.delay();
		
		// chạy thử chức năng thay đổi vị trí
		frameui.showDialogTest("Chạy thử chức năng vị trí");
		table.selectRow("Mã NKH HktTest2");
		dlMain.buttonClickByName("btnUp")
		frameui.delay();
		pnChild.buttonClick("Cập nhật")
		frameui.delay();
		dlMain.buttonClick("Thoát"); 
		
		// Chạy chức năng Xóa
		frameui.showDialogTest("Chạy thử chức năng [Xóa]");
		frameui.delay();
		frameui.showDialogTest("[Xóa] khi NKH trống!");
		frameui.delay();
		var table = pnChild.table("accountGroupTable");
		table.doubleClickRow("Mã NKH HktTest1");
		dlMain.buttonClick("Sửa");
		dlMain.buttonClick("Xóa");
		var dlDelete = frameui.dialog("dlXoa");
		var pnDelete = dlDelete.panel("Xoa");
		dlDelete.buttonClick("Đồng ý");
		pnChild.createDataTest();
		frameui.showDialogTest("[Xóa] thành công!");
		frameui.delay();
		dlMain.buttonClick("Thoát"); 
		frameui.showDialogTest("[Xóa] khi NKH có chứa Đối tác");
		frameui.delay();
		
		var button = frameui.button("QLNhomKhachHang");
		button.mouseClick();		 	
		var dlMain = frameui.dialog("dlGroupCustomer");
		var pnChild = dlMain.panel("QLNhomKhachHang");		
		var table = pnChild.table("accountGroupTable");
		table.doubleClickRow("Mã NKH HktTest1");
		dlMain.buttonClick("Sửa");
		dlMain.buttonClick("Xóa");
		frameui.delay();
		var dlDelete = frameui.dialog("dlXoa");
		var pnDelete = dlDelete.panel("Xoa");
		dlDelete.buttonClick("Đồng ý");
		var dlRemove = frameui.dialog("dlXoaKH");
		var pnRemove = dlRemove.panel("XoaKH");
		frameui.showDialogTest("Chuyển tất cả khách hàng sang nhóm Tên NKH HktTest2");
		frameui.delay();
		dlRemove.buttonClick(">>");
		frameui.delay();
		var comboBox = dlRemove.comboBox("cbDelete");
		comboBox.selectItem("Tên NKH HktTest2");
		frameui.delay();
		dlRemove.buttonClick("Đồng ý");
		frameui.showDialogTest("[Xóa thành công!]");
		frameui.delay();
		frameui.showDialogTest("Chương trình TEST Quản lý nhóm khách hàng thành công!");
		frameui.delay();
		pnChild.deleteDataTest();
		dlMain.buttonClick("Thoát");
}

var robot = new Robot() ;
robot.add("TEST Quản lý nhóm khách hàng", "", manageGroupPartner);
console.clear() ;
robot.run() ;
robot.report() ;