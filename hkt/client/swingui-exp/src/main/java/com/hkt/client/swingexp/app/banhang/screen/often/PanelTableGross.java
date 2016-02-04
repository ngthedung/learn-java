package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialogPlus;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.restaurant.entity.Table;

@SuppressWarnings("serial")
public class PanelTableGross extends JPanel implements IDialogResto {

	private TableModeTableGross tableModel, tableModelSelected;
	private JTable tableL, tableR;
	private List<Table> tables, tablesSelected;
	private ExtendJButton btnAdd, btnRemove;
	private boolean isSave;
	private JScrollPane scrollPane;

	public boolean isSave() {
		return isSave;
	}

	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}

	/**
	 * Create the PanelMargeTable.
	 */
	public PanelTableGross() {
		tablesSelected = new ArrayList<Table>();
		getTables();
		this.setLayout(new BorderLayout(10, 10));
	    this.setOpaque(false);
	    init();
	    this.add(new Panel_LEFT(), BorderLayout.LINE_START);
	    this.add(new Panel_CENTER(), BorderLayout.CENTER);
	    this.add(new Panel_RIGHT(), BorderLayout.LINE_END);
		
	}

	private void init() {
		setOpaque(false);

		tableModel = new TableModeTableGross(tables);
		tableModelSelected = new TableModeTableGross(tablesSelected);

		tableL = new JTable(tableModel);
		tableL.setName("tableL");
		tableR = new JTable(tableModelSelected);
		setFont(new Font("Tahoma", 0, 14));
		tableR.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		tableL.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		tableR.setRowHeight(23);
		tableL.setRowHeight(23);
		tableL.getColumnModel().getColumn(0).setMaxWidth(50);
		tableR.getColumnModel().getColumn(0).setMaxWidth(50);
	}
		
	protected class Panel_LEFT extends JPanel{
		
		 public Panel_LEFT() {
		      init();
		 }
		 
		 public void init() {
			 this.setLayout(new BorderLayout(0, 10));
		      this.setPreferredSize(new Dimension(420, 100));
		      this.setOpaque(false);
		      
		      JLabel labelL = new JLabel();
		      labelL.setText("Danh sách bàn/quầy");

		      scrollPane = new JScrollPane();
		      scrollPane.setViewportView(tableL);
		      
		      this.add(labelL, BorderLayout.PAGE_START);
		      this.add(scrollPane, BorderLayout.CENTER);
		 } 
	}
	
	 protected class Panel_RIGHT extends JPanel {
		 public Panel_RIGHT() {
			 init();
		 }
		 
		 public void init() {
		      this.setLayout(new BorderLayout(0, 10));
		      this.setPreferredSize(new Dimension(420, 100));
		      this.setOpaque(false);
		      
		      JLabel labelR = new JLabel();
		      labelR.setText("Danh sách bàn/quầy cần gộp");
		      
		      scrollPane = new JScrollPane();
		      scrollPane.setViewportView(tableR);
		      
		      this.add(labelR, BorderLayout.PAGE_START);
		      this.add(scrollPane, BorderLayout.CENTER);
		 }
	 }

	
	 protected class Panel_CENTER extends JPanel {
		 public Panel_CENTER() {
		      init();
		      btnAdd.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						buttonAdd();
					}
				});
				btnRemove.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						buttonRemove();
					}
				});
		 }
		 public void init() {
			 JPanel pal = new JPanel();
			 pal.setSize(new Dimension(10, 160));
			 pal.setOpaque(false);
			 
			 JPanel pal1 = new JPanel();
			 pal1.setSize(new Dimension(10, 160));
			 pal1.setOpaque(false);
			 
			 this.setLayout(new GridLayout(7, 1, 10, 14));

		      this.setOpaque(false);
		      this.setPreferredSize(new Dimension(100, 20));
		      
				btnAdd = new ExtendJButton(">");
				btnAdd.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
				btnAdd.setName(">");
				btnRemove = new ExtendJButton("<");

				btnAdd.setPreferredSize(new Dimension(80, 45));
				btnRemove.setPreferredSize(new Dimension(80, 45));
				btnRemove.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
				this.add(pal);
				this.add(pal1);
				this.add(btnAdd, BorderLayout.BEFORE_FIRST_LINE);
				this.add(btnRemove, BorderLayout.PAGE_START);
			}
	 }


	private List<Table> getTables() {
		try {
			tables = RestaurantModelManager.getInstance().getTables();
//			 for(int i = 0; i < tables.size(); i++){
			// if(tables.get(i).getStatus().equals(Table.Status.InActivate)){
			// tables.remove(i);
			// }
			// }
			return tables;
		} catch (Exception e) {
			return tables = new ArrayList<Table>();
		}
	}

	private void buttonAdd() {
		try {
			Table row = ((Table) tableL.getValueAt(tableL.getSelectedRow(), 1));
			tablesSelected.add(row);
			tables.remove(row);

			loadTable(tables, tablesSelected);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void buttonRemove() {
		try {
			Table row = ((Table) tableR.getValueAt(tableR.getSelectedRow(), 1));
			tables.add(row);
			tablesSelected.remove(row);

			loadTable(tables, tablesSelected);
		} catch (Exception ex) {
		}
	}

	public void loadTable(List<Table> listTableLeft, List<Table> listTableRight) {
		tableModel = new TableModeTableGross(listTableLeft);
		tableModelSelected = new TableModeTableGross(listTableRight);
		tableL.setModel(tableModel);
		tableR.setModel(tableModelSelected);
		tableR.setRowHeight(23);
		tableL.setRowHeight(23);
		tableL.getColumnModel().getColumn(0).setMaxWidth(50);
		tableR.getColumnModel().getColumn(0).setMaxWidth(50);
	}

	@Override
	public void Save() throws Exception {
		try {
			PanelTableGroup panelTableGroup = new PanelTableGroup(tablesSelected);
			panelTableGroup.setName("panelTableGroup");
			DialogResto dialog = new DialogResto(panelTableGroup, false, 0, 130);
			dialog.dispose();
			dialog.setUndecorated(true);
			dialog.setName("dlTableGroup");
			dialog.setTitle("Gộp bàn/quầy");
			// dialog.setSize(new Dimension(575,270));
			dialog.setLocationRelativeTo(null);
			dialog.setModal(true);
			dialog.setVisible(true);
			if (panelTableGroup.isSave()) {
				setSave(true);
				((JDialog) getRootPane().getParent()).dispose();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
