/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hkt.com.javaswing;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author MRG
 */
public class Null extends JFrame{

    public Null(){
        //Set layout kiểu null
        setLayout(null);
        setSize(400,300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Layout có thể thay đổi kích thước. Khi thay đổi kích thước các đối tượng không thay đổi theo
        setResizable(true);
        //Button không set size hoặc location
        JButton btnNoSet = new JButton("No set");
        //Button chỉ setSize
        JButton btnSize = new JButton("setSize");
        btnSize.setSize(100, 100);
        //Button setSize và location chồng lên btnOk
        JButton btnChongLenNhau = new JButton("Chồng lên nhau");
        btnChongLenNhau.setSize(200, 200);
        btnChongLenNhau.setLocation(50, 50);
        //Button chỉ setLocation
        JButton btnLocation = new JButton("Chỉ setLocation");
        btnLocation.setLocation(100, 100);
        //Button setBounds
        JButton btnBounds = new JButton("setBunds");
        btnBounds.setBounds(200, 100, 100, 300);
        //Thêm các button lên trên layout
        add(btnNoSet);
        add(btnBounds);
        add(btnSize);
        add(btnChongLenNhau);
        add(btnLocation);
    }

    public static void main(String[] args) {
        Null demo = new Null();
    }
}
