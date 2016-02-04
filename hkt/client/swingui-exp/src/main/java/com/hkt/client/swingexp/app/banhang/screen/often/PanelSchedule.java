package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.hkt.client.swingexp.app.component.GroupLayoutPanel;
import com.hkt.module.accounting.entity.InvoiceDetail;

public class PanelSchedule extends JPanel{
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel3;
    private GroupLayoutPanel jPaneltong;
    private javax.swing.JTextField txtSoLuongTong;
	private String tensp;
    private InvoiceDetail invoiceDetail;
    private int ngay;
    private JScrollPane scrollPane;
    private PanelScheduleWork scheduleWork;
    private         int sltong;
    private int indexMouseClick = -1;
    private List<PanelSchedule> panelSchedules = new ArrayList<PanelSchedule>();
	public PanelSchedule(InvoiceDetail invoiceDetail, int ngay) {
		this.invoiceDetail = invoiceDetail;
		this.ngay = ngay;
    	 initCompoment();
	}

	public GroupLayoutPanel getjPaneltong() {
		return jPaneltong;
	}
	
	public PanelScheduleWork getScheduleWork() {
		return scheduleWork;
	}
	private void initCompoment() {
		jPaneltong = new GroupLayoutPanel();
		jPaneltong.removeAll();
		jPaneltong.loadLayout();
		setOpaque(false);

		//Lấy thông tin sản phẩm
		for(int i = 0; i< invoiceDetail.getItems().size();i++ )
		{
		jPaneltong.setLayout(new GridLayout(1, invoiceDetail.getItems().size(), 10, 1));
	    jPaneltong.setOpaque(false);
	    scrollPane = new JScrollPane();
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setSize(new Dimension(1000, 420));
		scrollPane.setPreferredSize(new Dimension(1000, 420));
		scrollPane.setOpaque(false);
		scrollPane.setBorder(null);
	    tensp = new String((i+1) +"-"+ invoiceDetail.getItems().get(i).getItemName());
   	 	jPanel3 = new javax.swing.JPanel();
   	 	jPanel3.setOpaque(true);
   	 	jPanel3.setName("jpanel3" + i);
   	 	jPanel3.setBackground(Color.WHITE);
//   	 	jPanel3.setLayout(new GridLayout(1, 3, 10, 1));
        jLabel1 = new javax.swing.JLabel();
        txtSoLuongTong = new javax.swing.JTextField();
        txtSoLuongTong.setText(String.valueOf((int) invoiceDetail.getItems().get(i).getQuantity()));
        txtSoLuongTong.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				JTextField field = (JTextField) arg0.getSource();
				if(arg0.getKeyChar() == '\n')
				{
//					int slTong = Integer.parseInt(txtSoLuongTong.getText());
//					PanelScheduleWork scheduleWork = new PanelScheduleWork(invoiceDetail, ngay, slTong);
//					scrollPane.setViewportView(scheduleWork.getLayoutPanel());
				}
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				JTextField field = (JTextField) arg0.getSource();
				if(arg0.getKeyChar() == '\n')
				{
//					for(int i = 0; i<jPaneltong.getComponentCount();i++)
//					{
//						System.out.println(((JPanel) jPaneltong.getComponent(i)).getName());
//					}
//					indexMouseClick = jPaneltong.getComponentZOrder(jPanel3);	
//					sltong = Integer.parseInt(txtSoLuongTong.getText());
//					jPanel3.getComponentCount();
//					System.out.println(jPanel3.getComponentCount());
				}
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        txtSoLuongTong.setHorizontalAlignment(SwingConstants.RIGHT);
        txtSoLuongTong.setFont(new java.awt.Font("Tahoma", 0, 14));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, tensp, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 0, 255)));
        jPanel3.setSize(new Dimension(595, 420));
        jPanel3.setPreferredSize(new Dimension(595, 420));
//        jPanel3.setLayout(new GridLayout(3, 1, 10, 1));
        jLabel1.setText("Tổng số");  
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12));
		scheduleWork = new PanelScheduleWork(invoiceDetail, ngay, Integer.valueOf(txtSoLuongTong.getText()));
		scrollPane.setViewportView(scheduleWork.getLayoutPanel());
        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, javax.swing.GroupLayout.Alignment.TRAILING, 200, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSoLuongTong, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSoLuongTong, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(220, Short.MAX_VALUE)
                )
        );	
	      jPaneltong.addComponent(jPanel3);
	      panelSchedules.add(this);
		}
		jPaneltong.updateUI();
	}

	public boolean checkData() {
		return true;
	}

	
}
