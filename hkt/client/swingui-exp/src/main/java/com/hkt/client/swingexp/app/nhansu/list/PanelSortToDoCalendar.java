package com.hkt.client.swingexp.app.nhansu.list;

import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.core.DialogList;
import com.hkt.client.swingexp.app.core.HRJComboBox;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;

@SuppressWarnings("serial")
public class PanelSortToDoCalendar extends JPanel implements IDialogResto {
	private ExtendJLabel		lbStartDate, lbEndDate, lbEmployee, lbStatus;
	private MyJDateChooser	txtStartDate, txtEndDate;
	private ExtendJComboBox	cbStatus;
	private HRJComboBox			cbEmployee;

	private String[]				listStatus	= { "Tất cả", "Chưa làm", "Đang làm", "Hoàn thành" };

	// Hàm tạo cho Class
	@SuppressWarnings("unchecked")
	public PanelSortToDoCalendar() {
		lbStartDate = new ExtendJLabel("Từ ngày");
		lbEndDate = new ExtendJLabel("Đến");
		lbEmployee = new ExtendJLabel("Nhân viên");
		lbStatus = new ExtendJLabel("Trạng thái");

		txtStartDate = new MyJDateChooser();
		txtStartDate.setDate(new Date());
		txtEndDate = new MyJDateChooser();
		txtEndDate.setDate(new Date());

		cbEmployee = new HRJComboBox();
		cbEmployee.setName("cbEmployee");
		cbStatus = new ExtendJComboBox();
		cbStatus.setName("cbStatus");
		cbStatus.setModel(new DefaultComboBoxModel<String>(listStatus));

		getContentPanel();
		setSize(500, 300);
	}

	private void getContentPanel() {
		setOpaque(false);
		GroupLayout panelBackgroundRestoLayout = new GroupLayout(this);
		this.setLayout(panelBackgroundRestoLayout);
		panelBackgroundRestoLayout.setHorizontalGroup(panelBackgroundRestoLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING,
				panelBackgroundRestoLayout.createSequentialGroup().addGroup(
						panelBackgroundRestoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(panelBackgroundRestoLayout.createSequentialGroup().addComponent(lbStartDate, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE).addGap(15, 15, 15).addComponent(txtStartDate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								//	                            .addComponent(txtStartDate, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE)
										.addGap(15, 15, 15).addComponent(lbEndDate, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE).addGap(15, 15, 15).addComponent(txtEndDate, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
								.addGroup(panelBackgroundRestoLayout.createSequentialGroup().addComponent(lbEmployee).addGap(12, 12, 12).addComponent(cbEmployee, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
								.addGroup(panelBackgroundRestoLayout.createSequentialGroup().addComponent(lbStatus).addGap(10, 10, 10).addComponent(cbStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)))));

		panelBackgroundRestoLayout.setVerticalGroup(panelBackgroundRestoLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				panelBackgroundRestoLayout
						.createSequentialGroup()
						.addGroup(
								panelBackgroundRestoLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lbStartDate, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE).addComponent(txtStartDate, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
										.addComponent(lbEndDate, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE).addComponent(txtEndDate, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(
								panelBackgroundRestoLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lbEmployee, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
										.addGroup(panelBackgroundRestoLayout.createSequentialGroup().addGap(1, 1, 1).addComponent(cbEmployee, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGap(18, 18, 18)
						.addGroup(
								panelBackgroundRestoLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lbStatus, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
										.addGroup(panelBackgroundRestoLayout.createSequentialGroup().addGap(1, 1, 1).addComponent(cbStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))).addContainerGap()));
	}

	// Phương thức save cho nút [Đồng ý]
	@Override
	public void Save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(getToolTipText()) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
		} else {
			TableToDoCalendar table = new TableToDoCalendar(txtStartDate.getDate(), txtEndDate.getDate(), cbEmployee.getSelectedEmployee(), cbStatus.getSelectedItem().toString());
			table.setName("tbCongViecHangNgay");
			DialogList dialog = new DialogList(table);
			dialog.setIcon("congviecnu1.png");
			dialog.setTitle("Theo dõi - CV hàng ngày");
			dialog.setName("dlCongViecHangNgay");
			dialog.setModal(true);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			((JDialog) getRootPane().getParent()).dispose();
		}
	}

}
