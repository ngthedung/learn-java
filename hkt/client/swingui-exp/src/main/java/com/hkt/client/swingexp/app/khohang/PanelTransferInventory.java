
package com.hkt.client.swingexp.app.khohang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Panel;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import com.hkt.client.swing.widget.GridBagLayoutPanel;
import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.ExtendJCheckBox;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.core.IPanelShowList;

public class PanelTransferInventory extends JPanel implements IPanelShowList {
  
  public PanelTransferInventory() {
    
    setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createBevelBorder(BevelBorder.RAISED, null, new Color(204, 204, 204), null, null),
        "Phiếu chuyển kho", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
        new Font("Tahoma", 1, 14), new Color(126, 0, 0)));
    this.setBackground(new Color(255, 255, 255));
    setLayout(new BorderLayout());
    add(new PanelTransfer_LEFT(), BorderLayout.LINE_START);
    add(new PanelTransfer_RIGHT(), BorderLayout.CENTER);
  }
  
  private class PanelTransfer_LEFT extends JPanel {
    
    public PanelTransfer_LEFT() {
      setOpaque(false);
      setLayout(new BorderLayout());
      JLabel label_left = new ExtendJLabel("");
      label_left.setPreferredSize(new Dimension(10, 23));
      add(label_left, BorderLayout.LINE_START);
      add(new PanelTrasnfer_LEFT_CONTENT(), BorderLayout.CENTER);
    }
    
    private class PanelTrasnfer_LEFT_CONTENT extends JPanel {
      
      public PanelTrasnfer_LEFT_CONTENT() {
        setOpaque(false);
        setLayout(new BorderLayout());
        add(new PanelSearch_PAGE_START(), BorderLayout.PAGE_START);
        add(new PanelCategory_LINE_START(), BorderLayout.LINE_START);
        add(new PanelListProduct_LINE_END(), BorderLayout.CENTER);
      }
    }
    
    private class PanelSearch_PAGE_START extends JPanel {
      
      private JLabel lbTimKiem, lbSoLuong, lbDonGia;
      private JTextField txtTimKiem, txtSoLuong, txtDonGia;
      
      public PanelSearch_PAGE_START() {
        setOpaque(false);
        
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        
        lbTimKiem = new ExtendJLabel("Tìm kiếm ");
        lbTimKiem.setPreferredSize(new Dimension(80, 23));
        
        lbSoLuong = new ExtendJLabel("Số lg ");
        lbSoLuong.setPreferredSize(new Dimension(57, 23));
        
        lbDonGia = new ExtendJLabel("Đơn giá ");
        lbDonGia.setPreferredSize(new Dimension(67, 23));
        
        txtTimKiem = new ExtendJTextField();// 50,70
        txtSoLuong = new ExtendJTextField(5);
        txtDonGia = new ExtendJTextField(6);
        
        add(lbTimKiem);
        add(txtTimKiem);
        add(lbSoLuong);
        add(txtSoLuong);
        add(lbDonGia);
        add(txtDonGia);
      }
    }
    
    private class PanelCategory_LINE_START extends JPanel {
      
      public PanelCategory_LINE_START() {
        
        setOpaque(false);
        setLayout(new BorderLayout());
        add(new PanelCategory_1(), BorderLayout.PAGE_START);
        add(new PanelCategory_2(), BorderLayout.CENTER);
        add(new PanelCategory_3(), BorderLayout.PAGE_END);
      }
      
      private class PanelCategory_1 extends GridBagLayoutPanel {
        
        private JLabel lbNhomSP, lbBangGia, lbDVTien;
        private JComboBox cbNhomSP, cbBangGia, cbDVTien;
        private JCheckBox ckbGiaTrungBinh;
        
        public PanelCategory_1() {
          setBackground(Color.WHITE);
          
          lbNhomSP = new ExtendJLabel("Nhóm SP ");
          lbNhomSP.setPreferredSize(new Dimension(75, 23));
          
          lbBangGia = new ExtendJLabel("Bảng giá ");
          lbBangGia.setPreferredSize(new Dimension(75, 23));
          
          lbDVTien = new ExtendJLabel("ĐV Tiền ");
          lbDVTien.setPreferredSize(new Dimension(75, 23));
          
          ckbGiaTrungBinh = new ExtendJCheckBox("Lấy giá trung bình");
          
          Object[] data = { "Nhóm 1", "Nhóm 2", "Nhóm 3" };
          cbNhomSP = new ExtendJComboBox();
          cbNhomSP.setModel(new DefaultComboBoxModel(data));
          cbNhomSP.setPreferredSize(new Dimension(130, 23));
          
          add(0, 0, new JLabel(" "));
          
          add(1, 0, lbNhomSP);
          add(1, 1, cbNhomSP);
          
          Object[] dataBangGia = { "Bảng 1", "Bảng 2", "Bảng 3" };
          cbBangGia = new ExtendJComboBox();
          cbBangGia.setModel(new DefaultComboBoxModel(dataBangGia));
          cbBangGia.setPreferredSize(new Dimension(130, 23));
          
          add(2, 0, lbBangGia);
          add(2, 1, cbBangGia);
          
          Object[] dataMoney = { "VND", "USD", "EURO" };
          cbDVTien = new ExtendJComboBox();
          cbDVTien.setModel(new DefaultComboBoxModel(dataMoney));
          cbDVTien.setPreferredSize(new Dimension(130, 23));
          add(3, 0, lbDVTien);
          add(3, 1, cbDVTien);
          
          add(4, 0, ckbGiaTrungBinh, 2);
          
        }
      }
      
      private class PanelCategory_2 extends JPanel {
        
        public PanelCategory_2() {
          setBackground(Color.WHITE);
          setBorder(BorderFactory.createTitledBorder("Nhóm hàng hóa"));
        }
      }
      
      private class PanelCategory_3 extends JPanel {
        
        private JButton btnThemSP;
        
        public PanelCategory_3() {
          setBackground(Color.WHITE);
          btnThemSP = new ExtendJButton("Thêm SP");
          add(btnThemSP);
        }
      }
    }
    
    private class PanelListProduct_LINE_END extends JPanel {
      
      public PanelListProduct_LINE_END() {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, null, new Color(
            204, 204, 204), null, null), "PanelListProduct_LINE_END", TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14), new Color(126, 0, 0)));
        setOpaque(false);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Product list"));
        add(new ExtendJLabel("Danh sách nhóm sản phẩm"));
      }
    }
    
  }
  
  private class PanelTransfer_RIGHT extends JPanel {
    
    public PanelTransfer_RIGHT() {
      setOpaque(false);
      setLayout(new BorderLayout());
      
      JLabel label_left = new ExtendJLabel(" ");
      label_left.setPreferredSize(new Dimension(10, 23));
      
      JLabel label_right = new ExtendJLabel(" ");
      label_right.setPreferredSize(new Dimension(10, 23));
      
      add(label_left, BorderLayout.LINE_START);
      add(new PanelTransfer_RIGHT_Content(), BorderLayout.CENTER);
      add(label_right, BorderLayout.LINE_END);
    }
    
    private class PanelTransfer_RIGHT_Content extends JPanel {
      
      private JLabel lbTongTienXuat, lbTongTienNhap;
      private JTextField txtTongTienXuat, txtTongTienNhap;
      
      private JComboBox cbDonViTienXuat, cbDonViTienNhap;
      
      private Object[] dataDonViTien = { "VND", "USD" };
      
      public PanelTransfer_RIGHT_Content() {
        setOpaque(false);
        setLayout(new BorderLayout());
        lbTongTienXuat = new ExtendJLabel("Tổng Tiền");
        lbTongTienNhap = new ExtendJLabel("Tổng Tiên");
        txtTongTienXuat = new ExtendJTextField(35);
        txtTongTienNhap = new ExtendJTextField(42);
        
        cbDonViTienXuat = new ExtendJComboBox();
        cbDonViTienXuat.setModel(new DefaultComboBoxModel(dataDonViTien));
        cbDonViTienXuat.setPreferredSize(new Dimension(100, 23));
        
        cbDonViTienNhap = new ExtendJComboBox();
        cbDonViTienNhap.setModel(new DefaultComboBoxModel(dataDonViTien));
        cbDonViTienNhap.setPreferredSize(new Dimension(100, 23));
        
        JLabel label1 = new ExtendJLabel("");
        label1.setPreferredSize(new Dimension(10, 10));
        JLabel label2 = new ExtendJLabel("");
        label2.setPreferredSize(new Dimension(10, 10));
        
        JPanel panel_TongTien_Xuat = new JPanel();
        panel_TongTien_Xuat.setBackground(Color.WHITE);
        panel_TongTien_Xuat.setLayout(new BorderLayout());
        panel_TongTien_Xuat.add(label1, BorderLayout.PAGE_START);
        panel_TongTien_Xuat.add(lbTongTienXuat, BorderLayout.LINE_START);
        panel_TongTien_Xuat.add(txtTongTienXuat, BorderLayout.CENTER);
        panel_TongTien_Xuat.add(cbDonViTienXuat, BorderLayout.LINE_END);
        
        JPanel panel_TongTien_Nhap = new JPanel();
        panel_TongTien_Nhap.setBackground(Color.WHITE);
        panel_TongTien_Nhap.setLayout(new BorderLayout());
        panel_TongTien_Nhap.add(label2, BorderLayout.PAGE_START);
        panel_TongTien_Nhap.add(lbTongTienNhap, BorderLayout.LINE_START);
        panel_TongTien_Nhap.add(txtTongTienNhap, BorderLayout.CENTER);
        panel_TongTien_Nhap.add(cbDonViTienNhap, BorderLayout.LINE_END);
        
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        panel.add(panel_TongTien_Xuat, BorderLayout.PAGE_START);
        panel.add(new TableNhap(), BorderLayout.CENTER);
        panel.add(panel_TongTien_Nhap, BorderLayout.PAGE_END);
        
        add(new Infomation(), BorderLayout.PAGE_START);
        add(new TableXuat(), BorderLayout.CENTER);
        add(panel, BorderLayout.PAGE_END);
      }
    }
    
    private class Infomation extends GridBagLayoutPanel {
      
      private JLabel lbMaPhieu, lbTenNV, lbNoiXuat, lbNoiNhap, lbNhanVien, lbNgay;
      
      private JTextField txtMaPhieu, txtTenNV;
      private JComboBox cbNoiXuat, cbNoiNhap, cbNhanVien;
      private MyJDateChooser dcNgay;
      
      private Object[] dataDefault = new Object[] { "object1", "object2", "object3" };
      
      public Infomation() {
        
        setBackground(Color.WHITE);
        
        lbMaPhieu = new ExtendJLabel("Mã Phiếu");
        lbMaPhieu.setPreferredSize(new Dimension(86, 23));
        lbTenNV = new ExtendJLabel("Tên NV");
        lbTenNV.setPreferredSize(new Dimension(86, 23));
        lbNoiXuat = new ExtendJLabel("Nơi xuất");
        lbNoiXuat.setPreferredSize(new Dimension(86, 23));
        lbNoiNhap = new ExtendJLabel("Nơi nhập");
        lbNoiNhap.setPreferredSize(new Dimension(86, 23));
        lbNhanVien = new ExtendJLabel("Nhân viên");
        lbNhanVien.setPreferredSize(new Dimension(86, 23));
        lbNgay = new ExtendJLabel("Ngày");
        lbNgay.setPreferredSize(new Dimension(86, 23));
        
        txtMaPhieu = new ExtendJTextField(48);
        txtTenNV = new ExtendJTextField(48);
        
        cbNoiXuat = new ExtendJComboBox();
        cbNoiXuat.setModel(new DefaultComboBoxModel(dataDefault));
        
        cbNoiNhap = new ExtendJComboBox();
        cbNoiNhap.setModel(new DefaultComboBoxModel(dataDefault));
        
        cbNhanVien = new ExtendJComboBox();
        cbNhanVien.setModel(new DefaultComboBoxModel(dataDefault));
        
        dcNgay = new MyJDateChooser();
        
        Panel maphieu = new Panel();
        maphieu.setBackground(Color.WHITE);
        maphieu.setLayout(new BorderLayout());
        maphieu.add(lbMaPhieu, BorderLayout.LINE_START);
        maphieu.add(txtMaPhieu, BorderLayout.CENTER);
        
        Panel tenNV = new Panel();
        tenNV.setBackground(Color.WHITE);
        tenNV.setLayout(new BorderLayout());
        tenNV.add(lbTenNV, BorderLayout.LINE_START);
        tenNV.add(txtTenNV, BorderLayout.CENTER);
        
        Panel noiXuat = new Panel();
        noiXuat.setBackground(Color.WHITE);
        noiXuat.setLayout(new BorderLayout());
        noiXuat.add(lbNoiXuat, BorderLayout.LINE_START);
        noiXuat.add(cbNoiXuat, BorderLayout.CENTER);
        
        Panel noiNhap = new Panel();
        noiNhap.setBackground(Color.WHITE);
        noiNhap.setLayout(new BorderLayout());
        noiNhap.add(lbNoiNhap, BorderLayout.LINE_START);
        noiNhap.add(cbNoiNhap, BorderLayout.CENTER);
        
        Panel nhanNvien = new Panel();
        nhanNvien.setBackground(Color.WHITE);
        nhanNvien.setLayout(new BorderLayout());
        nhanNvien.add(lbNhanVien, BorderLayout.LINE_START);
        nhanNvien.add(cbNhanVien, BorderLayout.CENTER);
        
        Panel ngay = new Panel();
        ngay.setBackground(Color.WHITE);
        ngay.setLayout(new BorderLayout());
        ngay.add(lbNgay, BorderLayout.LINE_START);
        ngay.add(dcNgay, BorderLayout.CENTER);
        
        add(0, 0, maphieu);
        add(1, 0, tenNV);
        add(2, 0, noiXuat);
        add(3, 0, nhanNvien);
        add(4, 0, ngay);
        
        JLabel label = new ExtendJLabel("");
        label.setPreferredSize(new Dimension(10, 10));
        add(5, 0, label);
      }
    }
    
    private class TableXuat extends JPanel {
      
      private JTable tableXuat;
      private JScrollPane scrollTableXuat;
      private JLabel lbHangXuat;
      
      public TableXuat() {
        setOpaque(false);
        lbHangXuat = new ExtendJLabel("Hàng xuất");
        lbHangXuat.setPreferredSize(new Dimension(86, 23));
        lbHangXuat.setVerticalAlignment(SwingConstants.TOP);
        
        String[] columnNames = { "Sản phẩm", "Số lượng", "Đơn vị", "Đơn giá", "Loại Tiền", "Tổng tiền xuất" };
        Object[][] data = { { "Fried chicken", "1", "Chiếc", "1000", "USD", "10000" },
            { "Teriyaki", "1", "Chiếc", "1000", "USD", "10000" },
            { "Walnut ricotta", "1", "Chiếc", "1000", "USD", "10000" },
            { "Sandwich", "1", "Chiếc", "1000", "USD", "10000" }, { "Vodka", "1", "Chiếc", "1000", "USD", "10000" } };
        
        tableXuat = new JTable(data, columnNames);
        scrollTableXuat = new JScrollPane();
        scrollTableXuat.getViewport().setBackground(Color.white);
        scrollTableXuat.setViewportView(tableXuat);
        
        setLayout(new BorderLayout());
        add(lbHangXuat, BorderLayout.LINE_START);
        add(scrollTableXuat, BorderLayout.CENTER);
      }
    }
    
    private class TableNhap extends JPanel {
      
      private JTable tableNhap;
      private JLabel lbHangNhap;
      private JScrollPane scrollTableNhap;
      
      public TableNhap() {
        setOpaque(false);
        lbHangNhap = new ExtendJLabel("Hàng nhập");
        lbHangNhap.setPreferredSize(new Dimension(86, 23));
        lbHangNhap.setVerticalAlignment(SwingConstants.TOP);
        
        String[] columnNames = { "Sản phẩm", "Số lượng", "Đơn vị", "Đơn giá", "Loại Tiền", "Tổng tiền nhập" };
        Object[][] data = { { "Fried chicken", "1", "Chiếc", "1000", "USD", "10000" },
            { "Teriyaki", "1", "Chiếc", "1000", "USD", "10000" },
            { "Walnut ricotta", "1", "Chiếc", "1000", "USD", "10000" },
            { "Sandwich", "1", "Chiếc", "1000", "USD", "10000" }, { "Vodka", "1", "Chiếc", "1000", "USD", "10000" } };
        
        tableNhap = new JTable(data, columnNames);
        tableNhap.setPreferredScrollableViewportSize(new Dimension(200, 200));
        scrollTableNhap = new JScrollPane();
        scrollTableNhap.getViewport().setBackground(Color.white);
        scrollTableNhap.setViewportView(tableNhap);
        
        JLabel label = new ExtendJLabel(" ");
        label.setPreferredSize(new Dimension(10, 10));
        
        setLayout(new BorderLayout());
        
        add(label, BorderLayout.PAGE_START);
        add(lbHangNhap, BorderLayout.LINE_START);
        add(scrollTableNhap, BorderLayout.CENTER);
      }
    }
  }
  
  @Override
  public void print() {
    
  }
  
  @Override
  public void loadPanel() throws Exception {
    
  }
}
