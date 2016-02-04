package com.hkt.client.swingexp.app.core;

import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.hkt.client.swingexp.homescreen.HomeScreen;

public class MyFrame extends JFrame {

	private static MyFrame myFrame;

	public static MyFrame getInstance() {
		if (myFrame == null) {
			myFrame = new MyFrame();
		}
		return myFrame;
	}

	public static void setMyFrame(MyFrame myFrame) {
		MyFrame.myFrame = myFrame;
	}

	public MyFrame() throws HeadlessException {
		ImageIcon icon = new ImageIcon(HomeScreen.class.getResource("icon/icon.png"));
		Image im = icon.getImage();
		setIconImage(im);
		setName("MyFrame");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				try {
					Runtime.getRuntime().exec("taskkill /F /IM HKTSoft.exe");
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

		});
	}

}
