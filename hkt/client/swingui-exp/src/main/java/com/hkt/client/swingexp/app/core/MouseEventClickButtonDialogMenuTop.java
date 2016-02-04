package com.hkt.client.swingexp.app.core;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.hkt.client.swingexp.homescreen.HomeScreen;

public class MouseEventClickButtonDialogMenuTop extends MouseAdapter {
	 private ImageIcon imageIcon1, imageIcon2, imageIcon3;
	  private String image1="";
	  private String image2="";
	  private String image3="";
	  
	  
	  public MouseEventClickButtonDialogMenuTop(String image1, String image2, String image3) {
	      this.image1=image1;
	      this.image2=image2;
	      this.image3=image3;
	      getImage();
	  }
	  
	  @Override
	  public void mouseExited(MouseEvent e) {
	      JButton button = (JButton) e.getSource();
	      button.setOpaque(true);
	      button.setBackground(new Color(255, 255, 255));
	      button.setIcon(imageIcon1);

	  }

	  @Override
	  public void mouseEntered(MouseEvent e) {
	      JButton button = (JButton) e.getSource();
	      button.setOpaque(true);
	      button.setIcon(imageIcon2);
	      button.setBackground(new Color(248, 160, 60));
	      
	  }

	  @Override
	  public void mouseMoved(MouseEvent e) {
	      JButton button = (JButton) e.getSource();
	      button.setIcon(imageIcon2);
	      button.setBackground(new Color(248, 160, 60));    
	  }

	  @Override
	  public void mousePressed(MouseEvent e) {
	      JButton button = (JButton) e.getSource();
	      button.setOpaque(true);
	      button.setIcon(imageIcon3);
	      button.setMargin(new Insets(0, 2, 0, 0));
	      button.setBackground(new Color(238, 118, 3)); 
	      //click
	  }

	  @Override
	  public void mouseReleased(MouseEvent e) {
	      JButton button = (JButton) e.getSource();
	      button.setOpaque(true);
	      button.setIcon(imageIcon2);
	      button.setMargin(new Insets(0, 0, 0, 0));
	      button.setBackground(new Color(238, 118, 3)); 
	      //khi bỏ click chuot vẫn ở đó 
	  }
	  
	  private void getImage() {
	      try{
	          imageIcon1=new ImageIcon(HomeScreen.class.getResource("icon/home/"+image1));
	      }catch(Exception ex){
	          imageIcon1=null;
	      }
	      try{
	          imageIcon2=new ImageIcon(HomeScreen.class.getResource("icon/home/"+image2));
	      }catch(Exception ex){
	          imageIcon2=null;
	      }
	      try{
	          imageIcon3=new ImageIcon(HomeScreen.class.getResource("icon/home/"+image3));
	      }catch(Exception ex){
	          imageIcon3=null;
	      }
	  }
}
