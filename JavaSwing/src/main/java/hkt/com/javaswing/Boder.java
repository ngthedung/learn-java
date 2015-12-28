/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hkt.com.javaswing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javafx.scene.layout.Border;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author MRG
 */
public class Boder extends JFrame{
    public Boder(){
//        setLayout(new BorderLayout());
        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton btnTren = new JButton("Trên");
        JButton btnDuoi = new JButton("Dưới");
        JButton btnTrai = new JButton("Trái");
        JButton btnPhai = new JButton("Phải");
        JButton btnGiua = new JButton("Giữa");
        //Em s
        btnGiua.setPreferredSize(new Dimension(300, 300));
        add(btnTren,  BorderLayout.PAGE_START);
        add(btnDuoi, BorderLayout.PAGE_END);
        add(btnTrai, BorderLayout.LINE_START);
        add(btnPhai, BorderLayout.LINE_END);
        add(btnGiua, BorderLayout.CENTER);
    }
    
    public static void main(String[] args) {
        Boder demo = new Boder();
    }
}
