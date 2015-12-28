/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hkt.com.javaswing;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author MRG
 */
public class GridBag extends JFrame{
    JPanel panel;
    
    public GridBag(){
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
                setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton btn1 = new JButton("Tự động căn chỉnh");
        JButton btn2 = new JButton("Tự động căn chỉnh");
        JButton btn3 = new JButton("Tự động căn chỉnh");
        JButton btn4 = new JButton("Tự động căn chỉnh");
        JButton btn5 = new JButton("Tự động căn chỉnh");
        panel.add(btn1);
        panel.add(btn2);
        panel.add(btn3);
        panel.add(btn4);
        panel.add(btn5);
        this.add(panel);
    }
    
    public static void main(String[] args) {
        GridBag demo = new GridBag();
    }
}
