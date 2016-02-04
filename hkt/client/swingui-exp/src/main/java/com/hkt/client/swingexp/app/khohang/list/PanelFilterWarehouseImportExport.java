package com.hkt.client.swingexp.app.khohang.list;

import java.awt.GridLayout;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJRadioButton;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.core.DialogList;
import com.hkt.client.swingexp.app.core.HRJComboBox;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.core.WarehousesJComboBox;
import com.hkt.client.swingexp.app.hethong.PaneSetDate;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.entity.Invoice.ActivityType;

public class PanelFilterWarehouseImportExport extends JPanel implements IDialogResto {
	private JLabel							lbStartDate, lbEndDate, lbEmployee, lbWarehouse;
	private MyJDateChooser			txtStartDate, txtEndDate;
	private WarehousesJComboBox	cbWarehouse;
	private HRJComboBox					cbEmployee;
	private ExtendJRadioButton	rbImport, rbExport, rbAll;
	private ButtonGroup					groupRbImportExport;

	public PanelFilterWarehouseImportExport() {
		lbStartDate = new ExtendJLabel("Từ ngày");
		lbEndDate = new ExtendJLabel("Đến ngày");
		lbEmployee = new ExtendJLabel("Nhân viên");
		lbWarehouse = new ExtendJLabel("Kho");

		txtStartDate = new MyJDateChooser();
		txtStartDate.setDate(new Date());
		txtEndDate = new MyJDateChooser();
		txtEndDate.setDate(new Date());

		cbEmployee = new HRJComboBox();
		cbWarehouse = new WarehousesJComboBox();

		rbImport = new ExtendJRadioButton("Phiếu nhập");
		rbExport = new ExtendJRadioButton("Phiếu xuất");
		rbAll = new ExtendJRadioButton("Tất cả");
		rbAll.setSelected(true);

		groupRbImportExport = new ButtonGroup();
		groupRbImportExport.add(rbExport);
		groupRbImportExport.add(rbImport);
		groupRbImportExport.add(rbAll);
		
		JPanel panel = new JPanel(new GridLayout(0,3));
		panel.setOpaque(false);
		panel.add(rbAll);
		panel.add(rbImport);
		panel.add(rbExport);

		MyGroupLayout layout = new MyGroupLayout(this);
		layout.add(0, 0, lbStartDate);
		layout.add(0, 1, txtStartDate);
		layout.add(0, 2, lbEndDate);
		layout.add(0, 3, txtEndDate);
		layout.add(1, 0, lbEmployee);
		layout.add(1, 1, cbEmployee);
		layout.add(2, 0, lbWarehouse);
		layout.add(2, 1, cbWarehouse);
		layout.add(3, 0, new ExtendJLabel("Hiển thị"));
		layout.add(3, 1, panel);
		layout.updateGui();
		Profile p = AccountModelManager.getInstance().getProfileConfigAdmin();
		if(p.get(PaneSetDate.DateXNKho)!=null){
			Calendar c= Calendar.getInstance();
			int i = 0;
			try {
	      i = Integer.parseInt(p.get(PaneSetDate.DateXNKho).toString())*-1;
      } catch (Exception e) {
	      i = 0;
      }
			c.add(Calendar.DAY_OF_MONTH, i);
			txtStartDate.setDate(c.getTime());
		}
	}

	@Override
	public void Save() throws Exception {
		ActivityType activityType = null; 
		String warehouseId = null;
		String titleDialog = "Danh sách phiếu nhập xuất kho";
		if (rbImport.isSelected()) {
			activityType = ActivityType.Payment;
			titleDialog = "Danh sách phiếu nhập kho";
		}
		if (rbExport.isSelected()) {
			activityType = ActivityType.Receipt;
			titleDialog = "Danh sách phiếu xuất kho";
		}
		if (cbWarehouse.getSelectedWarehouse() != null) {
			warehouseId = cbWarehouse.getSelectedWarehouse().getWarehouseId();
		}
		
		DialogList dialog = new DialogList(new TableWarehouseImportExport(txtStartDate.getDate(), txtEndDate.getDate(), warehouseId, activityType));
		dialog.setIcon("xnds1.png");
		dialog.setTitle(titleDialog);
		dialog.setModal(true);
		dialog.setVisible(true);
		dialog.setAlwaysOnTop(true);
		((JDialog) getRootPane().getParent()).dispose();
	}
}
