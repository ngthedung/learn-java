package com.hkt.client.swingexp.app.core;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import com.hkt.client.swing.robot.console.JVMEnv;
import com.hkt.client.swing.robot.console.ShellConsole;
import com.hkt.module.core.script.ScriptRunner;
import com.hkt.util.IOUtil;

public class TestPanelExp extends JPanel {
  private Frame frame;
  private ShellConsole shellConsole = new ShellConsole();

  public TestPanelExp(Frame f) {
    this.frame = f;
    
    setLayout(new BorderLayout());

    JToolBar toolbar = new JToolBar();
    toolbar.setFloatable(false);
    String basePath = "file:src/main/resources/";
    String[] scripts = {
    		basePath + "script/hkt/Menu.js",
    		basePath + "script/hkt/Duan.js",
    		basePath + "script/hkt/TestPhongBanBoPhan.js",
    		basePath + "script/hkt/ThueHangHoa.js",
     		basePath + "script/hkt/QuanLyBangGia_BanHang.js",
    		basePath + "script/hkt/QuanLyNhomHang_KhoHangHoa.js",
    		basePath + "script/hkt/TienTe.js",
    		basePath + "script/hkt/NgonNgu.js",
    		basePath + "script/hkt/ThanhPho.js",
    		basePath + "script/hkt/QuocGia.js",
    		basePath + "script/hkt/TestCongViecHangNgay.js",    	
    		basePath + "script/hkt/DonViSanPham.js",
    		basePath + "script/hkt/TestPhieuThuTien.js",
    		basePath + "script/hkt/TestDungTrucTiepDiem.js",
    		basePath + "script/hkt/TestSuDungTraTruoc.js",
    		basePath + "script/hkt/TestDanhSachThuChiTien.js",
    		basePath + "script/hkt/TestKhuVucMoi.js",	
    		basePath + "script/hkt/TestMayInKhuVuc.js",
    		basePath + "script/hkt/TestBangGiaHienHanh.js", 
    		basePath + "script/hkt/TestQuanLyKhachHang_(NotDone).js",   		
    		basePath + "script/hkt/TestNhanVienNhomSanPham.js",
    		basePath + "script/hkt/QLNhomKhachHang.js",
    		basePath + "script/hkt/QLNhomNhaPhanPhoi.js",
    		basePath + "script/hkt/TestMayInSanPham.js",    		
    		basePath + "script/hkt/TestPhieuGiamGia_DSPhieuGiamGia.js",
    		basePath + "script/hkt/TestMayInSanPham_(NotDone).js",
    		basePath + "script/hkt/LapCoCheDiem.js",
    		basePath + "script/hkt/TestLapKhuyenMaiTang_(NotDone).js",
    		basePath + "script/hkt/TestTaoDoiTacDoanhNghiep_(NotDone).js",
    		basePath + "script/hkt/POSQuanLyBangGiaUnits.js",
    		basePath + "script/hkt/KhuyenMaiTangSanPham.js"};
    final JComboBox<String> scriptSelector = new JComboBox<String>(scripts);
    scriptSelector.setEditable(true);
    toolbar.add(scriptSelector);
    toolbar.add(new AbstractAction("Run") {
      public void actionPerformed(ActionEvent e) {
        Thread thread = new Thread() {
          public void run() {
            try {
              Map<String, Object> ctx = new HashMap<String, Object>();
              ctx.put("console", shellConsole);
              ctx.put("jvm", new JVMEnv());
              ctx.put("frame", frame);
              ScriptRunner scriptRunner = new ScriptRunner(".", ctx);
              InputStream is = IOUtil.loadRes((String) scriptSelector.getSelectedItem());
              String script = IOUtil.getStreamContentAsString(is, "UTF-8");
              scriptRunner.eval(script);

            } catch (Exception ex) {
              ex.printStackTrace();
            } finally {

            }
          }
        };
        thread.start();
      }
    });
    add(toolbar, BorderLayout.NORTH);
    add(shellConsole, BorderLayout.CENTER);
    setPreferredSize(new Dimension(600, 150));
  }
}