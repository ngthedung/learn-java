ScriptRunner.require("robotExp.js");
/*
 * @author Thangpm
 */
function CreateTestMenu(frameui) {
  var buttonMenu = frameui.button("menuRightKhuyenMai");
  buttonMenu.mouseClick();
  var button = frameui.button("Menu");
  button.mouseClick();

  var dlMain = frameui.dialog("dlMenu");
  var pnMain = dlMain.panel("Menu");
  /*
   * KỊCH BẢN TEST ::: MENU VÀ DANH SÁCH MENU
   */
  frameui.showDialogTest("KỊCH BẢN TEST MENU");
  frameui.delay();
  // Tạo Menu01 với 2 SP HktTest1, 2 SP HktTest2 và 1 tùy chọn trên Menu
  frameui.showDialogTest("Tạo Menu-01 với 2 SP HktTest1, 2 SP HktTest2 và 1 tùy chọn");
  frameui.delay();
  // Chọn nhóm hàng
  var comboBoxNhomSP = pnMain.comboBox("comboGroup");
  comboBoxNhomSP.selectItem("Nhóm SP HktTest11");
  frameui.delay();
  // Bắt lỗi
  if (frameui.showDialogTest("[Lỗi] Chưa nhập tên Menu")) {
    frameui.delay();
    dlMain.buttonClickByName("btnSave");
    frameui.delay();
  }
  // Đặt tên MENU
  pnMain.fieldSet("txtTen", "Menu HktTest1");
  // Bắt lỗi
  if (frameui.showDialogTest("[Lỗi] Chưa nhập giá Menu")) {
    frameui.delay();
    dlMain.buttonClickByName("btnSave");
    frameui.delay();
  }
  pnMain.fieldSet("txtTienBan", "5000000");
  // Bắt lỗi
  if (frameui.showDialogTest("[Lỗi] Chưa có sản phẩm")) {
    frameui.delay();
    dlMain.buttonClickByName("btnSave");
    frameui.delay();
  }
  // Nhấn nút + mở rộng các nhóm hàng
  var labelNut = pnMain.label("lblNut1");
  labelNut.mouseClick();
  //	
  var tableDanhSachSanPham = pnMain.table("tbDanhSachSanPham");
  // Chọn 2SP HktTest1 trong bảng danh sách
  var rboKhaiVi = pnMain.radioButton("checkbKhaiVi")
  rboKhaiVi.mouseClick();
  tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest1");
  tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest1");
  frameui.delay();
  // Chọn 2 SP HktTest2
  var rboMonChinh = pnMain.radioButton("checkbMonChinh")
  rboMonChinh.mouseClick();
  tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest2");
  tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest2");
  frameui.delay();
  // Chọn 2 SP HktTest3
  var rboTrangMieng = pnMain.radioButton("checkbTrangMieng")
  rboTrangMieng.mouseClick();
  tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest3");
  tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest3");
  frameui.delay();
  // Chọn 2 SP Tạo nhóm tùy chọn
  var rboTuyChon = pnMain.radioButton("checkbTuyChon")
  rboTuyChon.mouseClick();
  var btnOption = pnMain.button("btnOption");
  btnOption.mouseClick();
  // Tạo thông tin nhóm tùy chọn
  var dlOption = frameui.dialog("dlThemTuyChon");
  var pnOption = dlOption.panel("panelOption");
  // Bắt lỗi
  if (frameui.showDialogTest("[Lỗi] Chưa nhập tên tùy chọn")) {
    frameui.delay();
    dlOption.buttonClickByName("btnSave");
    frameui.delay();
  }
  pnOption.fieldSet("txtNameMenuItemRef", "Option HktTest");
  // Bắt lỗi
  pnOption.fieldSet("txtQuantity", "2");
  if (frameui.showDialogTest("[Lỗi] Chưa có sản phẩm")) {
    frameui.delay();
    dlOption.buttonClickByName("btnSave");
    frameui.delay();
  }
  // Nhấn nút + mở rộng các nhóm hàng
  var labelNut = pnOption.label("lblNut1");
  labelNut.mouseClick();
  //  
  var tableDanhSachSanPham = pnOption.table("tbDanhSachSanPham");
  // Chọn 2SP HktTest1 trong bảng danh sách
  tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest1");
  frameui.delay();
  // Chọn 2 SP HktTest2
  tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest2");
  frameui.delay();
  frameui.showDialogTest("Lưu thông tin tùy chọn");
  // Lưu tùy chọn
  var buttonLuu = dlOption.button("btnSave");
  buttonLuu.mouseClick();
  // xem lại tùy chọn
  var tableSPchinh = pnMain.table("tableSPchinh");
  tableSPchinh.doubleClickRow("Option HktTest");
  frameui.delay();
  // Lưu Menu
  var buttonSave = dlMain.button("btnSave");
  buttonSave.mouseClick();
  // Ấn nút thoát
  var buttonExit = dlMain.button("btnExit");
  buttonExit.mouseClick();
  //
  // Kết thúc giao diện tạo Menu
  //

  /*
   * VÀO DANH SÁCH
   */
  //	
  // //Xem lại phiếu đặt hàng và thêm 1 SP
  var buttonMenu = frameui.button("menuRightKhuyenMai");
  buttonMenu.mouseClick();
  var button = frameui.button("btnMenuList");
  button.mouseClick();
  frameui.delay();
  var dlList = frameui.dialog("dlTable");
  var table = dlList.table("tbQuanLyMenu");
  frameui.delay();
  table.doubleClickRow("Menu HktTest1");
  var dlMain = frameui.dialog("dlListMenu");
  var pnMain = dlMain.panel("pnListMenu");
  frameui.delay();
  var buttonEdit = dlMain.button("btnEdit");
  buttonEdit.mouseClick();
  // Thêm sản phẩm
  frameui.delay();
  var labelNut = pnMain.label("lblNut1");
  labelNut.mouseClick();
  var tableDanhSachSanPham = pnMain.table("tbDanhSachSanPham");
  tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest1");
  tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest1");
  tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest1");
  tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest1");
  frameui.delay();
  // Lưu Menu
  var buttonSave = dlMain.button("btnSave");
  buttonSave.mouseClick();
  frameui.delay();
  // Ấn nút thoát
  var buttonExit = dlMain.button("btnExit");
  buttonExit.mouseClick();
  frameui.delay();
  // //Xem lại phiếu đặt hàng và thêm 1 SP
  var buttonMenu = frameui.button("menuRightKhuyenMai");
  buttonMenu.mouseClick();
  var button = frameui.button("btnMenuList");
  button.mouseClick();
  frameui.delay();
  var dlList = frameui.dialog("dlTable");
  var table = dlList.table("tbQuanLyMenu");
  table.doubleClickRow("Menu HktTest1");
  frameui.delay();
  var dlMain = frameui.dialog("dlListMenu");
  var pnMain = dlMain.panel("pnListMenu");
  var buttonEdit = dlMain.button("btnEdit");
  buttonEdit.mouseClick();
  frameui.delay();
  var buttonDelete = dlMain.button("btnDelete");
  buttonDelete.mouseClick();
  frameui.delay();
  var dlDelete = frameui.dialog("dlXoa");
  var pnDelete = dlDelete.panel("Xoa");
  frameui.delay();
  dlDelete.buttonClick("Đồng ý");
  var buttonExit = dlMain.button("btnExit");
  buttonExit.mouseClick();
  frameui.delay();
  //Xóa toàn bộ dữ liệu test
  frameui.showDialogTest("Xóa toàn bộ dữ liệu TEST"); 
  pnMain.deleteDataTest();
  frameui.delay();
  frameui.showDialogTest("Chương trình TEST giao diện Menu kết thúc");
  var btnThoat = dlList.button("btnExit");
  btnThoat.mouseClick();
}

var robot = new Robot();
robot.add("TEST Menu", "", CreateTestMenu);
console.clear();
robot.run();
robot.report();