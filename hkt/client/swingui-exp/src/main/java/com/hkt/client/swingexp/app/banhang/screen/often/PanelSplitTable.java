package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.restaurant.entity.Reservation;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.module.restaurant.entity.Table.Status;

public class PanelSplitTable extends JPanel implements IDialogResto {
	private JScrollPane	scrollPane;
	private JPanel			panel;
	private DrawTable		drawTable	= null;
	private Table				tableGross, tableSelected;
	private List<Table>	tables;
	private List<Table> listSplitTables = new ArrayList<Table>();;
	private boolean			isSave;

	public PanelSplitTable(Table _tableGross) {
		this.tableGross = _tableGross;
		tables = new ArrayList<Table>();
		init();
	}

	public void init() {
		scrollPane = new JScrollPane();
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(745, 400));
		scrollPane.setPreferredSize(new Dimension(750, 405));
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		for (int i = 0; i < getTablesGross().size(); i++) {
			final DrawTable table = new DrawTable(tables.get(i));
			panel.add(table);
		}
		scrollPane.setViewportView(panel);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(scrollPane, BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(400, 300));
	}

	public Table getTable(Table table) {
		return table;
	}

	public class DrawTable extends JPanel {
		private Table	table;
		private JPanel	imageTable, infoTable, moneyTable;
		private ExtendJLabel	lblNameTable, lblStatusTable, lblMoney, lblNumMoney, lblUnitMoney;

		public DrawTable(Table table) {
			imageTable = new JPanel();
			infoTable = new JPanel();
			moneyTable = new JPanel();

			this.table = table;
			String statusTable = "";
			if (table.getStatus().equals(Status.InActivate)) {
				statusTable = "Ẩn";
			} else {
				if (table.getStatus().equals(Status.TableBusy)) {
					statusTable = "Đang dùng";
				} else {
					if (table.getStatus().equals(Status.TableGross)) {
						statusTable = "Đang gộp";
					} else {
						statusTable = "Khác ...";
					}
				}
			}

			lblNameTable = new ExtendJLabel(table.getName());
			lblStatusTable = new ExtendJLabel(statusTable);
			lblMoney = new ExtendJLabel("Tiền: ");
			lblNumMoney = new ExtendJLabel("0");
			lblUnitMoney = new ExtendJLabel("VNĐ");

			lblUnitMoney.setPreferredSize(new Dimension(40, 22));
			lblMoney.setPreferredSize(new Dimension(50, 22));
			lblNumMoney.setPreferredSize(new Dimension(90, 22));
			lblStatusTable.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
			lblStatusTable.setPreferredSize(new Dimension(90, 12));
			lblNameTable.setPreferredSize(new Dimension(125, 25));
			lblNameTable.setHorizontalAlignment(JLabel.CENTER);
			lblStatusTable.setHorizontalAlignment(JLabel.RIGHT);

			imageTable.setPreferredSize(new Dimension(50, 60));

			infoTable.setLayout(new FlowLayout());
			infoTable.setOpaque(false);
			infoTable.setPreferredSize(new Dimension(130, 60));
			infoTable.add(lblNameTable);
			infoTable.add(lblStatusTable);

			moneyTable.setLayout(new BorderLayout(0, 0));
			moneyTable.setOpaque(false);
			moneyTable.add(lblMoney, BorderLayout.LINE_START);
			moneyTable.add(lblNumMoney, BorderLayout.CENTER);
			moneyTable.add(lblUnitMoney, BorderLayout.LINE_END);
			moneyTable.setPreferredSize(new Dimension(150, 30));

			this.setBackground(new Color(160, 82, 45));
			this.setBorder(BorderFactory.createLineBorder(new Color(160, 82, 45), 5));
			this.setLayout(new BorderLayout(0, 0));
			this.add(imageTable, BorderLayout.LINE_START);
			this.add(infoTable, BorderLayout.CENTER);
			this.add(moneyTable, BorderLayout.PAGE_END);
			this.setPreferredSize(new Dimension(180, 90));

			imageTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (drawTable != null && drawTable.getBackground().equals(Color.YELLOW)) {
						drawTable.setColorBackground(new Color(160, 82, 45));
					}
					DrawTable.this.setColorBackground(Color.YELLOW);
					tableSelected = DrawTable.this.getTable();
					drawTable = DrawTable.this;
				}
			});

			infoTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (drawTable != null && drawTable.getBackground().equals(Color.YELLOW)) {
						drawTable.setColorBackground(new Color(160, 82, 45));
					}
					DrawTable.this.setColorBackground(Color.YELLOW);
					tableSelected = DrawTable.this.getTable();
					drawTable = DrawTable.this;
				}
			});

			moneyTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (drawTable != null && drawTable.getBackground().equals(Color.YELLOW)) {
						drawTable.setColorBackground(new Color(160, 82, 45));
					}
					DrawTable.this.setColorBackground(Color.YELLOW);
					tableSelected = DrawTable.this.getTable();
					drawTable = DrawTable.this;
				}
			});

			lblNameTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (drawTable != null && drawTable.getBackground().equals(Color.YELLOW)) {
						drawTable.setColorBackground(new Color(160, 82, 45));
					}
					DrawTable.this.setColorBackground(Color.YELLOW);
					tableSelected = DrawTable.this.getTable();
					drawTable = DrawTable.this;
				}
			});

			lblMoney.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (drawTable != null && drawTable.getBackground().equals(Color.YELLOW)) {
						drawTable.setColorBackground(new Color(160, 82, 45));
					}
					DrawTable.this.setColorBackground(Color.YELLOW);
					tableSelected = DrawTable.this.getTable();
					drawTable = DrawTable.this;
				}
			});

			lblNumMoney.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (drawTable != null && drawTable.getBackground().equals(Color.YELLOW)) {
						drawTable.setColorBackground(new Color(160, 82, 45));
					}
					DrawTable.this.setColorBackground(Color.YELLOW);
					tableSelected = DrawTable.this.getTable();
					drawTable = DrawTable.this;
				}
			});

			lblStatusTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (drawTable != null && drawTable.getBackground().equals(Color.YELLOW)) {
						drawTable.setColorBackground(new Color(160, 82, 45));
					}
					DrawTable.this.setColorBackground(Color.YELLOW);
					tableSelected = DrawTable.this.getTable();
					drawTable = DrawTable.this;
				}
			});

			lblUnitMoney.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (drawTable != null && drawTable.getBackground().equals(Color.YELLOW)) {
						drawTable.setColorBackground(new Color(160, 82, 45));
					}
					DrawTable.this.setColorBackground(Color.YELLOW);
					tableSelected = DrawTable.this.getTable();
					drawTable = DrawTable.this;
				}
			});
		}

		public Table getTable() {
			return table;
		}

		public void setTable(Table table) {
			this.table = table;
		}

		public void setNameTable(String name) {
			lblNameTable.setText(name);
		}

		public void setStatusTable(String name) {
			lblStatusTable.setText(name);
		}

		public void setNumMoney(String name) {
			lblNumMoney.setText(name);
		}

		public void setColorBackground(Color color) {
			this.setBackground(color);
			this.setBorder(BorderFactory.createLineBorder(color, 5));
		}

	}

	private List<Table> getTablesGross() {
		tables.clear();
		for (int i = 0; i < tableGross.getReservations().size(); i++) {
			try {
				Table table = RestaurantModelManager.getInstance().getTableByCode(tableGross.getReservations().get(i).getDescription());
				tables.add(table);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tables;
	}

	@Override
	public void Save() throws Exception {
		if(tableSelected != null){
			for (int i = 0; i < tableGross.getReservations().size(); i++) {
				if (tableSelected.getCode().equals(tableGross.getReservations().get(i).getDescription())) {
					Table table = RestaurantModelManager.getInstance().getTableByCode(tableGross.getReservations().get(i).getDescription());
					table.setStatus(Table.Status.TableFree);
					table = RestaurantModelManager.getInstance().saveTable(table);
					listSplitTables.add(table);
					
					tableGross.getReservations().remove(i);
					RestaurantModelManager.getInstance().saveTable(tableGross);

					panel.remove(panel.getComponentZOrder(drawTable));
					panel.updateUI();
					setSave(true);
					break;
				}
			}
			if(tableGross.getReservations().size() == 0){
				if (tableGross.getInvoiceCode() != null && !tableGross.getInvoiceCode().equals("")) {
					tableGross.setStatus(Table.Status.TableBusy);
				} else {
					tableGross.setStatus(Table.Status.TableFree);
				}
				List<Reservation> li = new ArrayList<Reservation>();
				tableGross.setReservations(li);
				RestaurantModelManager.getInstance().saveTable(tableGross);
			}
		}
	}

	public boolean isSave() {
		return isSave;
	}

	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}

	public List<Table> getListSplitTables() {
		return listSplitTables;
	}

}
