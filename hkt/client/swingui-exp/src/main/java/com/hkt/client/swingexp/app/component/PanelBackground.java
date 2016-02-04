package com.hkt.client.swingexp.app.component;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import com.hkt.client.swingexp.homescreen.HomeScreen;

public class PanelBackground extends javax.swing.JPanel {
	
	public static ImageIcon	backgroundIcon;
	private Image						bgimage;
	
	public PanelBackground() {
		if(backgroundIcon==null){
			backgroundIcon = new ImageIcon(HomeScreen.class.getResource("icon/KaraBackgroud.jpg"));
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon iic = new ImageTool().resize(backgroundIcon, this.getSize());
		bgimage = iic.getImage();
		g.drawImage(bgimage, 1, 1, null);
	}
	
}
