package com.hkt.client.swingexp.app.banhang.screen.pos;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.hkt.client.swingexp.app.core.IDialogResto;

public class PanelGroupButton extends JPanel implements IDialogResto {
	private List<JButton> buttons; 
	
	public PanelGroupButton(List<JButton> buttons) {
		this.buttons = buttons;
		this.setOpaque(false);
		this.setLayout(new GridLayout(1, buttons.size(), 5, 5));
		for(JButton b : buttons){
			this.add(b);
		}		
	}
	
	public void setLayout(int row, int col, int width, int height){
		this.setLayout(new GridLayout(row<=0?1:row, col<=0?buttons.size():col, width, height));
		this.updateUI();
	}

	@Override
	public void Save() throws Exception {
		(((JDialog) getRootPane().getParent())).dispose();
	}

}
