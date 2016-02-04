package com.hkt.client.swingexp.app.core;


import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.hkt.client.swingexp.homescreen.HomeScreen;

/**
 *
 * @author longnt
 */
public class MouseEventMenuSales extends MouseAdapter {

    private ImageIcon imageIcon1, imageIcon2, imageIcon3;
    private String pathSource = "icon/home/";
    private String image1 = "";
    private String image2 = "";
    private String image3 = "";
    private boolean choise = false;
    private static JButton bt =new JButton();
    public boolean isChoise() {
        return choise;
    }

    public void setChoise(boolean choise, JButton button) {
        this.choise = choise;
        if (choise) {
            button.setIcon(imageIcon3);
            button.setMargin(new Insets(5, 0, 0, 0));
        } else {
            button.setIcon(imageIcon1);
        }
    }

    public MouseEventMenuSales(String image1, String image2, String image3) {
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        getImage();
    }


    @Override
    public void mouseExited(MouseEvent e) {
    	JButton button = (JButton) e.getSource();
    	
    	if(bt.getName()==null){
    		if (choise) {
              return;
          }
          
          button.setIcon(imageIcon1);
    	}else {
    		if(bt.getName().toString().equals(button.getName().toString())){
    			bt.setIcon(imageIcon3);
    		}else {
    			button.setIcon(imageIcon1);
			}
			
		}
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (choise) {
            return;
        }
        JButton button = (JButton) e.getSource();
        button.setIcon(imageIcon2);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (choise) {
            return;
        }
        JButton button = (JButton) e.getSource();
        button.setIcon(imageIcon2);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    	JButton button = (JButton) e.getSource();
    	if(bt.getName()==null){
    		 button.setIcon(imageIcon3);
    		 button.setMargin(new Insets(5, 0, 0, 0));
           if (choise) {
               return;
           }
           
           bt =button;        
    	}else {
    		button.setIcon(imageIcon3);
    		button.setMargin(new Insets(5, 0, 0, 0));
    		
    		
    		bt.setMargin(new Insets(0, 0, 0, 0));
    		bt.setIcon(imageIcon1);
    		bt = button;
		}
    	
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        button.setMargin(new Insets(0, 0, 0, 0));
        if (choise) {
            return;
        }
        button.setIcon(imageIcon3);        
    }

    private void getImage() {
        try {
            imageIcon1 = new ImageIcon(HomeScreen.class.getResource(pathSource + image1));
        } catch (Exception ex) {
            imageIcon1 = null;
        }
        try {
            imageIcon2 = new ImageIcon(HomeScreen.class.getResource(pathSource + image2));
        } catch (Exception ex) {
            imageIcon2 = null;
        }
        try {
            imageIcon3 = new ImageIcon(HomeScreen.class.getResource(pathSource + image3));
        } catch (Exception ex) {
            imageIcon3 = null;
        }
    }
}

