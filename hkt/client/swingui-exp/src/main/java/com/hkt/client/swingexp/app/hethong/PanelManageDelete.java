package com.hkt.client.swingexp.app.hethong;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.hethong.list.IComboBoxDelete;
import com.hkt.client.swingexp.app.hethong.list.TableModelDelete;
import com.hkt.client.swingexp.app.hethong.list.TableModelDeleteRight;
/*
 * author: longnt
 */

@SuppressWarnings("serial")
public class PanelManageDelete<E> extends JPanel implements IDialogResto {

	private ExtendJLabel lblTypePartnerCurrent, lblTypePartnerNew;
	private IComboBoxDelete<E> comboBoxDelete;
	private ExtendJLabel cbTypePartnerCurrent;
	private JTable tableCustomersLeft, tableCustomersRight;
	private TableModelDelete<E> tableModelDelete;
	private TableModelDeleteRight<E> tableModelDeleteRight;
	private JScrollPane scrollPane;
	private List<E> listProcess;
	@SuppressWarnings("unused")
	private ExtendJButton btnAddOne, btnAddAll, btnRemoveOne, btnRemoveAll, btnDelete;

	/**
	 * Create the PanelManagePartner Khởi tạo giao diện chia làm 4 phần tương
	 * ứng 4 class con
	 **/

	// Hàm khởi tạo class
	public PanelManageDelete(String name, List<E> listProcess,
			IComboBoxDelete<E> comboBoxDelete) {
		this.listProcess = listProcess;
		this.comboBoxDelete = comboBoxDelete;
		
		this.setLayout(new BorderLayout(10, 10));
		this.setOpaque(false);

		this.add(new Panel_LEFT(), BorderLayout.LINE_START);
		this.add(new Panel_CENTER(), BorderLayout.CENTER);
		this.add(new Panel_RIGHT(), BorderLayout.LINE_END);
		cbTypePartnerCurrent.setText(name);
		

	}

	// Định nghĩa mô tả cho phần bảng trái
	protected class Panel_LEFT extends JPanel {
		public Panel_LEFT() {
			init();
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void init() {
			this.setLayout(new BorderLayout(0, 15));
			this.setPreferredSize(new Dimension(550, 100));
			this.setOpaque(false);
			lblTypePartnerCurrent = new ExtendJLabel(
					"Danh sách đối tượng cần xử lý của:");
			cbTypePartnerCurrent = new ExtendJLabel("");
			PanelComboBox panelComboBox = new PanelComboBox(
					cbTypePartnerCurrent, lblTypePartnerCurrent);
			tableCustomersLeft = new JTable();
			tableCustomersLeft.setRowHeight(23);
			setFont(new Font("Tahoma", 0, 14));
			tableCustomersLeft.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
			try {
				tableModelDelete = new TableModelDelete(listProcess);
				tableCustomersLeft.setModel(tableModelDelete);
				tableCustomersLeft.getColumnModel().getColumn(0).setMaxWidth(50);
			} catch (Exception e) {
			}

			JCheckBox chBox = new JCheckBox();
			chBox.setHorizontalAlignment(JLabel.CENTER);
			chBox.addItemListener(new ItemListener() {
        
        @Override
        public void itemStateChanged(ItemEvent e) {
         JCheckBox ch = (JCheckBox)e.getSource();
          if(ch.isSelected()){
            btnAddAll.setEnabled(false);
            btnAddOne.setEnabled(false);
            btnRemoveAll.setEnabled(false);
            btnRemoveOne.setEnabled(false);
          }else {
            btnAddAll.setEnabled(true);
            btnAddOne.setEnabled(true);
            btnRemoveAll.setEnabled(true);
            btnRemoveOne.setEnabled(true);
          }
        }
      });
			tableCustomersLeft.getColumn("Xóa").setCellEditor(
					new DefaultCellEditor(chBox));
			tableCustomersLeft.getColumn("Xóa").setCellRenderer(
					new TableCellRenderer() {

						@Override
						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							JCheckBox chb = new JCheckBox();
							chb.setSelected(Boolean.parseBoolean(value
									.toString()));
							chb.setHorizontalAlignment(JCheckBox.CENTER);
							
							return chb;
						}
					});

			scrollPane = new JScrollPane();
			scrollPane.setViewportView(tableCustomersLeft);

			this.add(panelComboBox, BorderLayout.PAGE_START);
			this.add(scrollPane, BorderLayout.CENTER);
			tableCustomersLeft.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mouseClicked(MouseEvent e) {
			    if(tableCustomersLeft.getValueAt(tableCustomersLeft.getSelectedRow(), 2).toString().equals("true")){
			      btnAddAll.setEnabled(false);
			      btnAddOne.setEnabled(false);
			      btnRemoveAll.setEnabled(false);
			      btnRemoveOne.setEnabled(false);
			    }else {
			      btnAddAll.setEnabled(true);
            btnAddOne.setEnabled(true);
            btnRemoveAll.setEnabled(true);
            btnRemoveOne.setEnabled(true);
          }
			  }
      });
		}
	}

	// Định nghĩa mô tả cho phần nút trung tâm
	protected class Panel_CENTER extends JPanel {	

		public Panel_CENTER() {
			init();

			btnAddOne.addActionListener(new ActionListener() {
				@SuppressWarnings("unchecked")
				@Override
				public void actionPerformed(ActionEvent e) {
					if (tableCustomersLeft.getSelectedRow() >= 0) {
						tableModelDeleteRight.addRow((E) tableModelDelete
								.getDataVector().elementAt(
										tableCustomersLeft.getSelectedRow()));
						tableModelDelete.removeRow(tableCustomersLeft
								.getSelectedRow());
					}
				}
			});

			btnAddAll.addActionListener(new ActionListener() {
				@SuppressWarnings("unchecked")
				@Override
				public void actionPerformed(ActionEvent e) {
					tableModelDeleteRight.getDataVector().addAll(
							tableModelDelete.getDataVector());
					tableModelDeleteRight.fireTableDataChanged();
					tableModelDelete.removeAllData();
				}
			});

			btnRemoveOne.addActionListener(new ActionListener() {
				@SuppressWarnings("unchecked")
				@Override
				public void actionPerformed(ActionEvent e) {
					if (tableCustomersRight.getSelectedRow() >= 0) {
						tableModelDelete.addRow((E) tableModelDeleteRight
								.getDataVector().elementAt(
								    tableCustomersRight.getSelectedRow()));
						tableModelDeleteRight.removeRow(tableCustomersRight
								.getSelectedRow());
					}
				}
			});

			btnRemoveAll.addActionListener(new ActionListener() {
				@SuppressWarnings("unchecked")
				@Override
				public void actionPerformed(ActionEvent e) {
					tableModelDelete.addAllData(tableModelDeleteRight
							.getDataVector());
					tableModelDeleteRight.getDataVector().removeAllElements();
					tableModelDeleteRight.fireTableDataChanged();
				}
			});

//			btnDelete.addActionListener(new ActionListener() {
//
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					boolean flag = comboBoxDelete.delete(tableModelDelete
//							.getItemsSelect());
//					if (flag) {
//						tableModelDelete.deleteData();
//					}
//				}
//			});

		}

		public void init() {
			this.setLayout(new GridLayout(9, 1, 10, 14));
			this.setPreferredSize(new Dimension(50, 150));
			this.setOpaque(false);

			btnAddOne = new ExtendJButton(">");
			btnAddAll = new ExtendJButton(">>");
			btnRemoveOne = new ExtendJButton("<");
			btnRemoveAll = new ExtendJButton("<<");
			btnDelete = new ExtendJButton("Xóa");

			btnAddOne.setName(">");
			btnAddAll.setName(">>");
			btnRemoveOne.setName("<");
			btnRemoveAll.setName("<<");

			JLabel label1 = new JLabel("");
			label1.setPreferredSize(new Dimension(50, 50));

			this.add(label1);
			this.add(btnAddAll);
			this.add(btnAddOne);
			this.add(btnRemoveOne);
			this.add(btnRemoveAll);
			//this.add(btnDelete);
		}
	}

	// Định nghĩa mô tả cho phần bảng phải
	protected class Panel_RIGHT extends JPanel {
		public Panel_RIGHT() {
			init();

		}

		public void init() {
			this.setLayout(new BorderLayout(0, 10));
			this.setPreferredSize(new Dimension(550, 100));
			this.setOpaque(false);

			lblTypePartnerNew = new ExtendJLabel("Chọn nhóm mới");

			@SuppressWarnings("rawtypes")
			PanelComboBox panelComboBox = new PanelComboBox(
					(JComboBox) comboBoxDelete, lblTypePartnerNew);
			

			tableCustomersRight = new JTable();
			tableModelDeleteRight = new TableModelDeleteRight<E>(
					new ArrayList<E>());
			tableCustomersRight.setModel(tableModelDeleteRight);
			tableCustomersRight.getColumnModel().getColumn(0).setMaxWidth(50);
			tableCustomersRight.setRowHeight(23);
			setFont(new Font("Tahoma", 0, 14));
			tableCustomersRight.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
			tableCustomersRight.setName("tbDoiTacMoi");
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(tableCustomersRight);

			this.add(panelComboBox, BorderLayout.PAGE_START);
			this.add(scrollPane, BorderLayout.CENTER);
		}
	}

	// Tạo Panel chứa label và comboBox
	protected class PanelComboBox extends JPanel {
		private JComponent cbBox;
		private ExtendJLabel lblName;

		public PanelComboBox(JComponent comboBox, ExtendJLabel label) {
			this.cbBox = comboBox;
			comboBox.setName("cbDelete");
			this.lblName = label;			

			this.setLayout(new GridLayout(2, 1, 0, 0));
			this.setOpaque(false);

			this.add(lblName);
			this.add(cbBox);
		}

	}

	// Phương thức cho nút [Đồng ý]
	@SuppressWarnings("unchecked")
	@Override
	public void Save() throws Exception {
	  comboBoxDelete.delete(tableModelDelete
        .getItemsSelect());
		comboBoxDelete.changeGroup(tableModelDeleteRight.getDataVector());
		((JDialog) getRootPane().getParent()).dispose();
	}

}
