package com.hkt.client.swingexp.app.khohang;

import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.core.DialogList;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.khohang.list.TablePatternProduction;

public class PanelFilterPatternProduction extends JPanel implements IDialogResto {
	private ExtendJLabel lbStartDate, lbEndDate;
	private MyJDateChooser txtStartDate, txtEndDate;

	public PanelFilterPatternProduction() {
		lbStartDate = new ExtendJLabel("Từ ngày");
		lbEndDate = new ExtendJLabel("Đến ngày");

		txtStartDate = new MyJDateChooser();
		txtStartDate.setDate(new Date());
		txtEndDate = new MyJDateChooser();
		txtEndDate.setDate(new Date());

		MyGroupLayout layout = new MyGroupLayout(this);
		layout.add(0, 0, lbStartDate);
		layout.add(0, 1, txtStartDate);
		layout.add(1, 0, lbEndDate);
		layout.add(1, 1, txtEndDate);
		layout.updateGui();
	}

	@Override
	public void Save() throws Exception {
		DialogList dialog = new DialogList(new TablePatternProduction(txtStartDate.getDate(), txtEndDate.getDate()));
		dialog.setTitle("Danh sách sản xuất");
		dialog.setModal(true);
		dialog.setVisible(true);
		dialog.setAlwaysOnTop(true);
		((JDialog) getRootPane().getParent()).dispose();
	}

}
