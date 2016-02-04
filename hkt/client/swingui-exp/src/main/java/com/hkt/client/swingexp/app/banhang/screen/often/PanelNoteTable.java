package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.restaurant.entity.NoteTable;
import com.hkt.module.restaurant.entity.Table;

public class PanelNoteTable extends JPanel implements IDialogResto {
	private JTable table;
	private Table tableE;
	private DefaultTableModel model;

	public PanelNoteTable(Table tableE) {
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.setRowHeight(25);
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		
		
		this.tableE = RestaurantModelManager.getInstance().getTableByCode(tableE.getCode());
		JScrollPane sc = new JScrollPane();
		sc.setViewportView(table);
		this.setLayout(new GridLayout());
		this.add(sc);
		String[] str = { "STT", "Tên", "SL", "Ghi chú" };
		model = new DefaultTableModel(str, 10){
			@Override
			public boolean isCellEditable(int row, int column) {
			  if(column==0){
			  	return false;
			  }else {
					return true;
				}
			}
		};
		table.setModel(model);
		table.getColumnModel().getColumn(0).setMaxWidth(50);
		table.getColumnModel().getColumn(2).setMaxWidth(70);
		
		for(int j = 1; j<= 10; j++){
			this.table.setValueAt(j, j-1, 0);
		}
		int i = 0;
		if (this.tableE.getNoteTables() != null) {
			for (NoteTable noteTable : this.tableE.getNoteTables()) {
				String[] rows = { String.valueOf(i+1), noteTable.getName(), noteTable.getQuantity(), noteTable.getDescription() };
				for(int j = 0; j< rows.length; j++){
					this.table.setValueAt(rows[j], i, j);
				}
				i++;
			}
		}
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			  if(table.getSelectedRow()==table.getRowCount()-1){
			  	String[] a = {String.valueOf(table.getRowCount()+1),"","",""};
			  	model.addRow(a);
			  }
			}
		});
		
	}

	@Override
	public void Save() throws Exception {
		List<NoteTable> noteTables = new ArrayList<NoteTable>();
		for (int i = 0; i < table.getRowCount(); i++) {
			if(table.getValueAt(i, 1)!=null && !table.getValueAt(i, 1).toString().trim().isEmpty()){
				NoteTable noteTable = new NoteTable();
				noteTable.setName(table.getValueAt(i, 1).toString());
				if(table.getValueAt(i,2)!=null){
noteTable.setQuantity(table.getValueAt(i, 2).toString());}
if(table.getValueAt(i,3)!=null){
				noteTable.setDescription(table.getValueAt(i, 3).toString());}
				noteTables.add(noteTable);
			}
			
		}
		tableE.setNoteTables(noteTables);
		tableE=RestaurantModelManager.getInstance().saveTable(tableE);
		DialogNotice.getInstace().setVisible(true);
	}

}
