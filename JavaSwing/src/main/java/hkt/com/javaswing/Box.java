/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hkt.com.javaswing;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author MRG
 */
public class Box extends JFrame{
    JPanel panel;
    public Box(){
        panel = new JPanel();
        setVisible(true);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        JButton btnOne = new JButton("One");
        JButton btnTwo = new JButton("Two");
        JButton btnThree = new JButton("Three");
        JButton btnFour = new JButton("Four");
        panel.add(btnOne);
        panel.add(btnTwo);
        panel.add(btnThree);
        panel.add(btnFour);
        this.add(panel);
    }
    
    public static void main(String[] args) {
        Box demo = new Box();
    }
}
