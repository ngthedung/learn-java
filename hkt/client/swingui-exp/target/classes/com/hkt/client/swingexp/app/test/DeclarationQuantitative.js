ScriptRunner.require("robotExp.js");
/*
 * @author Thangpm
 */
function CreateTestDeclarationQuantitative(frameui) {
  var buttonDeclarationQuantitative = frameui.button("menuRightKhoHangHoa");
  buttonDeclarationQuantitative.mouseClick();
  var button = frameui.button("declarationQuantitative");
  button.mouseClick();

  var dlMain = frameui.dialog("dlDeclarationQuantitative");
  var pnMain = dlMain.panel("declarationQuantitative");
  /*
   * KỊCH BẢN TEST ::: MENU VÀ DANH SÁCH MENU
   */
  frameui.showDialogTest("KỊCH BẢN TEST ĐỊNH LƯỢNG");
  frameui.delay();
  // Tạo DeclarationQuantitative01 với 2 SP HktTest1, 2 SP HktTest2 và 1 tùy chọn trên DeclarationQuantitative
  frameui.showDialogTest("Tạo DeclarationQuantitative-01 với 2 SP HktTest1, 2 SP HktTest2 và 1 tùy chọn");
  frameui.delay();
  // Chọn nhóm hàng
  var comboBoxNhomSP = pnMain.comboBox("comboGroup");
  comboBoxNhomSP.selectItem("Nhóm SP HktTest11");
  frameui.delay();
  // Bắt lỗi
  if (frameui.showDialogTest("[Lỗi] Chưa nhập sản phẩm cần định lượng")) {
    frameui.delay();
    dlMain.buttonClickByName("btnSave");
    frameui.delay();
  }
  pnMain.fieldSet("txtProductKB", "Sản phẩm HktTest1");
  // Bắt lỗi
  if (frameui.showDialogTest("[Lỗi] Chưa nhập tên định lượng")) {
    frameui.delay();
    dlMain.buttonClickByName("btnSave");
    frameui.delay();
  }
  // Đặt tên MENU
  pnMain.fieldSet("txtName", "DeclarationQuantitative HktTest1"); 
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
  // Chọn 2 SP HktTest2
  tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest2");
  tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest2");
  frameui.delay();
  // Chọn 2 SP HktTest3
  tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest3");
  tableDanhSachSanPham.doubleClickRow("Sản phẩm HktTest3");
  frameui.delay();
  // Lưu DeclarationQuantitative
  var buttonSave = dlMain.button("btnSave");
  buttonSave.mouseClick();
  // Ấn nút thoát
  var buttonExit = dlMain.button("btnExit");
  buttonExit.mouseClick();
  //
  // Kết thúc giao diện tạo DeclarationQuantitative
  //

  /*
   * VÀO DANH SÁCH
   */
  //	
  // //Xem lại phiếu đặt hàng và thêm 1 SP
  var buttonDeclarationQuantitative = frameui.button("menuRightKhoHangHoa");
  buttonDeclarationQuantitative.mouseClick();
  var button = frameui.button("dlListRecipe");
  button.mouseClick();
  frameui.delay();
  var dlList = frameui.dialog("dlListRecipe");
  var table = dlList.table("tbrecipe");
  frameui.delay();
  table.doubleClickRow("DeclarationQuantitative HktTest1");
  var dlMain = frameui.dialog("dlDeclarationQuantitative");
  var pnMain = dlMain.panel("declarationQuantitative");
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
  // Lưu DeclarationQuantitative
  var buttonSave = dlMain.button("btnSave");
  buttonSave.mouseClick();
  frameui.delay();
  // Ấn nút thoát
  var buttonExit = dlMain.button("btnExit");
  buttonExit.mouseClick();
  frameui.delay();
  // //Xem lại phiếu đặt hàng và thêm 1 SP
  var buttonDeclarationQuantitative = frameui.button("menuRightKhoHangHoa");
  buttonDeclarationQuantitative.mouseClick();
  var button = frameui.button("dlListRecipe");
  button.mouseClick();
  frameui.delay();
  var dlList = frameui.dialog("dlListRecipe");
  var table = dlList.table("tbrecipe");
  table.doubleClickRow("DeclarationQuantitative HktTest1");
  frameui.delay();
  var dlMain = frameui.dialog("dlDeclarationQuantitative");
  var pnMain = dlMain.panel("declarationQuantitative");
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
  //Xóa toàn bộ dữ liệu test
  frameui.showDialogTest("Xóa toàn bộ dữ liệu TEST"); 
  pnMain.deleteDataTest();
  frameui.delay();
  var buttonExit = dlMain.button("btnExit");
  buttonExit.mouseClick();
  frameui.delay();
  var btnThoat = dlList.button("btnExit");
  btnThoat.mouseClick();
  frameui.showDialogTest("Chương trình TEST giao diện DeclarationQuantitative kết thúc");
  frameui.delay();
}

var robot = new Robot();
robot.add("TEST DeclarationQuantitative", "", CreateTestDeclarationQuantitative);
console.clear();
robot.run();
robot.report();