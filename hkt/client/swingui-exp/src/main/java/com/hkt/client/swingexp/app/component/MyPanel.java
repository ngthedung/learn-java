package com.hkt.client.swingexp.app.component;

import javax.swing.JPanel;

public class MyPanel extends JPanel{
  
  public static boolean isTest;

  public MyPanel() {
    super();
  }
  
  public void createBeginTest(){
    MyPanel.isTest= false;
  }

  public void createDataTest(){
  }
  
  public void deleteDataTest(){
  }
}
