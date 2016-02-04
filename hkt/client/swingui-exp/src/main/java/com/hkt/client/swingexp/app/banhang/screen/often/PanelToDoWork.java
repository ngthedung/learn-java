package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.hkt.client.swingexp.app.component.GroupLayoutPanel;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.hr.entity.Appointment;
import com.hkt.module.partner.customer.entity.Customer;

public class PanelToDoWork extends MyPanel implements IDialogMain{
	
	public static String permission;
	private JPanel jPanelDate;
	private JTextField txtngaydat;
	private JLabel jLabel;
	private JScrollPane jspTong;
	private PanelSchedule panelSchedule;
	private List<PanelSchedule> listPanel = new ArrayList<PanelSchedule>();
	private boolean restore = true;
	private int ngay;
	private InvoiceDetail invoiceDetail;

	public PanelToDoWork(InvoiceDetail invoiceDetail) {
		this.invoiceDetail = invoiceDetail;
		initComponents();
	}

	private void initComponents() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,1));
		setOpaque(false);
        jPanelDate = new javax.swing.JPanel();
        ngay =7;
        txtngaydat = new javax.swing.JTextField(String.valueOf(ngay));
        txtngaydat.setHorizontalAlignment(SwingConstants.RIGHT);
        txtngaydat.setFont(new java.awt.Font("Tahoma", 0, 14));
        txtngaydat.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			if(e.getKeyChar() == '\n')
			{
					ngay = Integer.valueOf(txtngaydat.getText());
					panelSchedule = new PanelSchedule(invoiceDetail, ngay);
					jspTong.setViewportView(panelSchedule.getjPaneltong());
			}	
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
        jLabel = new javax.swing.JLabel();
        jspTong = new javax.swing.JScrollPane();

//        jPanelDate.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelDate.setOpaque(false);
        jLabel.setText("Ngày");
        jLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
        javax.swing.GroupLayout jPanelDateLayout = new javax.swing.GroupLayout(jPanelDate);
        jPanelDate.setLayout(jPanelDateLayout);
        jPanelDateLayout.setHorizontalGroup(
            jPanelDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDateLayout.createSequentialGroup()
                .addComponent(txtngaydat, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
        );
        jPanelDateLayout.setVerticalGroup(
            jPanelDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtngaydat, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jspTong.setOpaque(false);
        jspTong.getViewport().setOpaque(false);
        jspTong.setBorder(null);
		panelSchedule = new PanelSchedule(invoiceDetail, ngay);
//		for( int i = 0; i < invoiceDetail.getItems().size(); i++)
//		{
//		
//			PanelSchedule panelSchedule = new PanelSchedule(invoiceDetail, ngay);
//			listPanel.add(panelSchedule);
//			
//		}
//		for(int i = 0; i< listPanel.size();i++)
//		{
//		PanelSchedule panelSchedule = listPanel.get(i);
//		panel.add(panelSchedule);
//		
//		}
		jspTong.setViewportView(panelSchedule.getjPaneltong());
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jspTong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(45, Short.MAX_VALUE)
                    .addComponent(jspTong, javax.swing.GroupLayout.PREFERRED_SIZE,480, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()
                    ))
        );
	}
	
	@Override
	public void save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return;
		} 
		else {
			if(panelSchedule.getScheduleWork().checkData())
			{
			if(restore)
			panelSchedule.getScheduleWork().btSaveActionPerformed();	
			}
			else
			{
				return;
			}
			}
		
	}

	@Override
	public void edit() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return;
		} else{

			setEdit(true);
	}
		
	}

	private void setEdit(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh() throws Exception {
		// TODO Auto-generated method stub
	}

}
