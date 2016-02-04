package com.hkt.client.swingexp.app.banhang.screen.often;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.restaurant.entity.Table;

@SuppressWarnings("serial")
public class PanelReserveTable extends JPanel implements IDialogResto {
	private JLabel lbZone;
	private JLabel lbTable;
	private JLabel lbNote;
	private JTextField txtZone;
	private JTextField txtTable;
	private JScrollPane jScrollPane1;
	private JTextArea txtareaNote;
	private TableEat table;

	public PanelReserveTable(TableEat tableEat) {
		this.table = tableEat;
		setOpaque(false);
		lbZone = new javax.swing.JLabel();
		lbTable = new javax.swing.JLabel();
		lbNote = new javax.swing.JLabel();
		txtZone = new javax.swing.JTextField();
		txtTable = new javax.swing.JTextField();
		jScrollPane1 = new javax.swing.JScrollPane();
		txtareaNote = new javax.swing.JTextArea();

		lbZone.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lbZone.setText("Khu vực");

		lbTable.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lbTable.setText("Bàn");

		lbNote.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lbNote.setText("Ghi chú");

		txtTable.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				actionPerformed(evt);
			}
		});

		txtareaNote.setColumns(20);
		txtareaNote.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
		txtareaNote.setRows(5);
		jScrollPane1.setViewportView(txtareaNote);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(lbZone)
												.addComponent(lbTable)
												.addComponent(lbNote))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														jScrollPane1,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														250, Short.MAX_VALUE)
												.addComponent(txtTable)
												.addComponent(txtZone))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(9, 9, 9)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														txtZone,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														23,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(lbZone))
								.addGap(15, 15, 15)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														txtTable,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														23,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(lbTable))
								.addGap(19, 19, 19)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		lbNote)
																.addGap(0,
																		91,
																		Short.MAX_VALUE))
												.addComponent(jScrollPane1))
								.addContainerGap()));
		try {
		  txtZone.setText(RestaurantModelManager.getInstance().getLocationByCode(tableEat.getTable().getLocationCode()).getName());
    } catch (Exception e) {
    }
		
		txtZone.setEditable(false);
		txtTable.setText(tableEat.getTable().getName());
//		txtTable.setHorizontalAlignment(JTextField.RIGHT);
		txtTable.setEditable(false);

	}

	@Override
	public void Save() throws Exception {
		table.getTable().setDescription(txtareaNote.getText());
		table.getTable().setStatus(Table.Status.TableSet);
		RestaurantModelManager.getInstance().saveTable(table.getTable());
		((JDialog) getRootPane().getParent()).dispose();
	}
}
