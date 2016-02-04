package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


import com.hkt.client.swingexp.app.core.IDialogResto;

@SuppressWarnings("serial")
public class PanelHouseholdProducts extends JPanel implements IDialogResto {

	private JTable tableProduct;
	public PanelHouseholdProducts() {
		setOpaque(false);

		JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
		tableProduct = new JTable();

		tableProduct.setOpaque(false);
		tableProduct.setBackground(Color.white);
		tableProduct.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		tableProduct.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		tableProduct.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { "1" }, { "2" }, { "3" } }, new String[] {
						"STT", "Tên hàng hóa", "Số lượng", "Đơn giá",
						"Thành tiền" }));
		tableProduct.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		tableProduct.setRowHeight(23);
		tableProduct.getColumnModel().getColumn(0).setMaxWidth(50);
		tableProduct.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer(){

			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus, int row,
					int column) {
				JLabel label = new JLabel();
				label.setText(value.toString());
				label.setHorizontalAlignment(JLabel.RIGHT);
				return label;
				}
	    });

		for (int i = 0; i < 3; i++)
			for (int j = 2; i < 4; i++)
				tableProduct.getCellEditor(i, j).addCellEditorListener(
						new CellEditorListener() {

							@Override
							public void editingCanceled(ChangeEvent e) {
								return;

							}

							@Override
							public void editingStopped(ChangeEvent e) {
								try {
									Calculator(tableProduct);

								} catch (Exception e2) {

								}

							}

						});

		tableProduct.setSelectionBackground(new java.awt.Color(255, 255, 255));
		jScrollPane2.setViewportView(tableProduct);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 400,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 173,
				Short.MAX_VALUE));

		DefaultTableModel dtm = (DefaultTableModel) tableProduct.getModel();
		dtm.setRowCount(3);
		tableProduct.setModel(dtm);
		tableProduct.setRowHeight(23);
		tableProduct.setFont(new Font("Tomaho", Font.PLAIN, 14));
		DeleteKey();

	}

	public void Calculator(JTable tableProduct) {
		int slg = 0, dg = 0;
		slg = Integer.parseInt(tableProduct.getValueAt(
				tableProduct.getSelectedRow(), 2).toString());
		dg = Integer.parseInt(tableProduct.getValueAt(
				tableProduct.getSelectedRow(), 3).toString());
		if (slg != 0 && dg != 0) {

			int tt = slg * dg;
			tableProduct.setValueAt(tt, tableProduct.getSelectedRow(), 4);
		}

	}

	public void DeleteKey() {
		tableProduct.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_DELETE) {

					int selection = tableProduct.getSelectedRow();

					for (int i = 0; i <= 4; i++)

						tableProduct.setValueAt("", selection, i);

					DefaultTableModel dtm = (DefaultTableModel) tableProduct
							.getModel();
					dtm.removeRow(selection);
					dtm.addRow(new Object[] {});
					for (int i = 0; i < 3; i++)
						tableProduct.setValueAt(i + 1, i, 0);

				}

			}
		});
	}

	@Override
	public void Save() throws Exception {

	}
}
