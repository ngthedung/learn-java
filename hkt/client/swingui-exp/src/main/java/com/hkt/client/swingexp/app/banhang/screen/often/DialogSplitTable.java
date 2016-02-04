package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.PanelBackground;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialogPlus;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.restaurant.entity.Reservation;
import com.hkt.module.restaurant.entity.Table;

public class DialogSplitTable extends JPanel implements IDialogResto {
	private ExtendJButton		btnSplitAllTable, btnSplitTable;
	private PanelBackground	PanelMain;
	private Table						tableGross;
	private List<Table>			listSplitTables	= new ArrayList<Table>();
	private boolean					isSuccsess;

	public Table getTableGross() {
		return tableGross;
	}

	public DialogSplitTable(Table _table) {
		this.tableGross = _table;
		btnSplitAllTable = new ExtendJButton("Tách toàn bộ");
		btnSplitAllTable.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
		btnSplitTable = new ExtendJButton("Tách riêng lẻ");
		btnSplitTable.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
		PanelMain = new PanelBackground();

		drawLayout();
		addKeyBindings(PanelMain);
		btnSplitAllTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SplitAll();
			}
		});

		btnSplitTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Split();
			}
		});

	}

	public void drawLayout() {
		setOpaque(false);
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);

		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addGap(10, 10, 10).addComponent(btnSplitAllTable, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(40, 40, 40)
						.addComponent(btnSplitTable, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(10, 10, 10)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout
						.createSequentialGroup()
						.addGap(10, 10, 10)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnSplitAllTable, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnSplitTable, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(36, Short.MAX_VALUE)));

	}

	private void SplitAll() {
		for (int i = 0; i < tableGross.getReservations().size(); i++) {
			try {
				Table table = RestaurantModelManager.getInstance().getTableByCode(tableGross.getReservations().get(i).getDescription());
				table.setStatus(Table.Status.TableFree);
				table = RestaurantModelManager.getInstance().saveTable(table);
				listSplitTables.add(table);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (tableGross.getInvoiceCode() != null && !tableGross.getInvoiceCode().equals("")) {
			tableGross.setStatus(Table.Status.TableBusy);
		} else {
			tableGross.setStatus(Table.Status.TableFree);
		}
		List<Reservation> li = new ArrayList<Reservation>();
		tableGross.setReservations(li);
		try {
			tableGross=RestaurantModelManager.getInstance().saveTable(tableGross);
			setSuccsess(true);
			(((JDialog) getRootPane().getParent())).dispose();
		} catch (Exception e) {
			setSuccsess(false);
			e.printStackTrace();
		}
	}

	private void Split() {
		PanelSplitTable panel = new PanelSplitTable(tableGross);
		DialogResto dialog = new DialogResto(panel, false, 400, 320);
		dialog.setTitle("Tách riêng lẻ");
		dialog.setModal(true);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		if (panel.isSave()) {
			setSuccsess(true);
			listSplitTables = panel.getListSplitTables();
			(((JDialog) getRootPane().getParent())).dispose();
		} else
			setSuccsess(false);
	}

	public boolean isSuccsess() {
		return isSuccsess;
	}

	public void setSuccsess(boolean isSuccsess) {
		this.isSuccsess = isSuccsess;
	}

	private void addKeyBindings(JComponent jc) {
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "Esc");
		jc.getActionMap().put("Esc", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				(((JDialog) getRootPane().getParent())).dispose();
			}
		});
	}

	public List<Table> getListSplitTables() {
		return listSplitTables;
	}

	@Override
	public void Save() throws Exception {
	}

}
