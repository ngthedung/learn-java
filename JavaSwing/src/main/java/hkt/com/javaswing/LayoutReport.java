/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hkt.com.javaswing;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javafx.scene.layout.Border;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author MRG
 */
public class LayoutReport extends JFrame{
    JPanel paCenter, paTop, paBotton, paCenterGrid, paCenter_1, paCenter_2, paCenter_3, paCenter_4;
    
    public LayoutReport(){
        //Sử dụng Border tạo 3 phần PAGE_START chứa title, PAGE_END chứa version
        setLayout(new BorderLayout());
        setSize(700, 700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //End
        
        //Top và Botton chứa tiêu đề và version
        JLabel lbTitle = new JLabel("Phần mềm HKT");
        JLabel lbEnd = new JLabel("Ver: 1.0.0");
        
        //Sử dụng GridBag để căn giữa
        paTop = new JPanel();
        paTop.setLayout(new GridBagLayout());
        paTop.add(lbTitle);
        
        paBotton = new JPanel();
        paBotton.setLayout(new GridBagLayout());
        paBotton.add(lbEnd);
        
        add(paTop, BorderLayout.PAGE_START);
        add(paBotton, BorderLayout.PAGE_END);
        //End
        
        //Phần CENTER của Border sử dụng GridBag để căn giữa, sử dụng Grid chia làm 4 dòng và 2 cột
        paCenter = new JPanel();
        paCenterGrid = new JPanel();
        paCenter_1 = new JPanel();
        paCenter_2 = new JPanel();
        paCenter_3 = new JPanel();
        paCenter_4 = new JPanel();
        
        paCenter.setLayout(new GridBagLayout());
        paCenterGrid.setLayout(new GridLayout(4,2));
        paCenter_1.setLayout(new GridLayout(1,1));
        paCenter_2.setLayout(new GridLayout(1,2));
        paCenter_3.setLayout(new GridLayout(1,2));
        paCenter_3.setLayout(new GridLayout(1,1));
        
        JLabel lbDangNhap = new JLabel("ĐĂNG NHẬP");
        paCenter_1.add(lbDangNhap);
        
        JLabel lbHoTen = new JLabel("Họ và tên:");
        JTextField txtHoten = new JTextField();
        paCenter_2.add(lbHoTen);
        paCenter_2.add(txtHoten);
        
        JLabel lbMatKhau = new JLabel("Mật khẩu");
        JTextField txtMatKhau = new JTextField();
        paCenter_3.add(lbMatKhau);
        paCenter_3.add(txtMatKhau);
        
        JButton btnDangNhap = new JButton("Đăng nhập");
        paCenter_4.add(btnDangNhap);
        
        //Add 4 dòng là 4 panel Grid vào panel CenterGrid
        paCenterGrid.add(paCenter_1);
        paCenterGrid.add(paCenter_2);
        paCenterGrid.add(paCenter_3);
        paCenterGrid.add(paCenter_4);
        
        //Add panel CenterGird chứa 4 panel con là 4 dòng vào panel Center
        paCenter.add(paCenterGrid);
        
        //Add panel center vào vị trí CENTER của Border layout
        add(paCenter, BorderLayout.CENTER);
        //End
    }
    
    public static void main(String[] args) {
        LayoutReport demo = new LayoutReport();
    }
}
