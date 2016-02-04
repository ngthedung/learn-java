/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkt.client.swingexp.app.virtualkey.text;

import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author BinhMinh
 */
public class MouseEventKeypad extends MouseAdapter {

    private ImageIcon imageIcon1, imageIcon2;
//        , imageIcon3;
    private String pathSource = "";
    private String image1 = "";
    private String image2 = "";
//    private String image3="";
    //

    public MouseEventKeypad() {
    }

    public MouseEventKeypad(String pathSource, String image1, String image2) {
        this.pathSource = pathSource;
        this.image1 = image1;
        this.image2 = image2;
        getImage();
    }

    public MouseEventKeypad(String pathSource, String image1, String image2, String image3) {
//        this.pathSource = pathSource;
//        this.image1=image1;
//        this.image2=image2;
//        this.image3=image3;
//        getImage();
    }

    public MouseEventKeypad(ImageIcon imageIcon1, ImageIcon imageIcon2, ImageIcon imageIcon3) {
//        this.imageIcon1 = imageIcon1;
//        this.imageIcon2 = imageIcon2;
//        this.imageIcon3 = imageIcon3;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JButton button = (JButton) e.getSource();
//        button.setOpaque(true);
//        button.setBackground(new Color(255, 255, 255));
        button.setIcon(imageIcon1);

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JButton button = (JButton) e.getSource();
//        button.setOpaque(true);
        button.setIcon(imageIcon2);
//        button.setBackground(new Color(248, 160, 60));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        button.setIcon(imageIcon2);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        JButton button = (JButton) e.getSource();
//        button.setOpaque(true);
//        button.setIcon(imageIcon3);
        button.setIcon(imageIcon2);
        button.setMargin(new Insets(3, 3, 0, 0));
//        button.setBackground(new Color(238, 118, 3));

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        JButton button = (JButton) e.getSource();
//        button.setOpaque(true);
        button.setIcon(imageIcon2);
        button.setMargin(new Insets(0, 0, 0, 0));
//        button.setBackground(new Color(255, 255, 255));
    }

    private void getImage() {
        try {
            imageIcon1 = new ImageIcon(getClass().getResource(pathSource + image1));
        } catch (Exception ex) {
            imageIcon1 = null;
        }
        try {
            imageIcon2 = new ImageIcon(getClass().getResource(pathSource + image2));
        } catch (Exception ex) {
            imageIcon2 = null;
        }
//        try {
//            imageIcon3 = new ImageIcon(getClass().getResource(pathSource + image3));
//        } catch (Exception ex) {
//            imageIcon3 = null;
//        }
    }
}
