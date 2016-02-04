package com.hkt.client.swingexp.app.banhang.screen.often;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hkt.client.swingexp.app.core.IDialogResto;

public class PanelSeparationTable extends JPanel implements IDialogResto {
	private JLabel lbTable,lbGroupTable,lbMoney,lbUnitMoney,lbGTMoney;
	private JPanel Panel1,Panel2;
	public PanelSeparationTable(){
//		setOpaque(false);
		setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 615, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 355, Short.MAX_VALUE)
        );
		
		
	}

	
	@Override
	public void Save() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
