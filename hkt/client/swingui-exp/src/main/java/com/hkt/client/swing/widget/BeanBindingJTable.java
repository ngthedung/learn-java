package com.hkt.client.swing.widget;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.Expression;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialogPlus;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.util.BeanInspector;

abstract public class BeanBindingJTable<T> extends JTable {
	private static final long serialVersionUID = 1L;

	private JPopupMenu rowPopupMenu;
	protected List<T> beans;
	protected List<Expression> expressions = new ArrayList<Expression>();
	private String[] beanProperty;
	protected String[] columNames;
	@SuppressWarnings("rawtypes")
	protected Class[] columnType;
	BeanInspector<T> beanInspector;
	private boolean edit;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	public BeanBindingJTable() {
	}

	public BeanBindingJTable(String[] columns, String[] beanProperty, List<T> beanList) {
		init(columns, beanProperty, beanList);
	}

	@SuppressWarnings({ "unchecked", "serial" })
	protected void init(String[] columns, String[] beanProperty, List<T> beanList) {
		this.columNames = columns;
		this.beanProperty = beanProperty;
		this.beans = beanList;

		T sampleBean = newBean();
		beanInspector = BeanInspector.get(newBean().getClass());

		try {
			columnType = new Class[beanProperty.length];
			for (int i = 0; i < columnType.length; i++) {
				PropertyDescriptor descriptor = new PropertyDescriptor(beanProperty[i], sampleBean.getClass());
				columnType[i] = descriptor.getReadMethod().getReturnType();
				if (columnType[i] == boolean.class)
					columnType[i] = Boolean.class;
				else if (columnType[i] == int.class)
					columnType[i] = Integer.class;
				else if (columnType[i] == long.class)
					columnType[i] = Long.class;
				else if (columnType[i] == double.class)
					columnType[i] = Double.class;
				else if (columnType[i] == Date.class)
					columnType[i] = String.class;
				else if (columnType[i] == float.class)
					columnType[i] = Float.class;
			}
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}

		final AbstractTableModel model = new AbstractTableModel() {
			@SuppressWarnings("rawtypes")
			public Class getColumnClass(int column) {
				return getBeanPropertyClass(column);
			}

			public String getColumnName(int column) {
				return columNames[column];
			}

			public int getRowCount() {
				return beans.size();
			}

			public int getColumnCount() {
				return columNames.length;
			}

			public Object getValueAt(int rowIndex, int columnIndex) {
				return getBeanValueAt(rowIndex, columnIndex);
			}

			public boolean isCellEditable(int row, int col) {
				return isBeanEditableAt(row, col);
			}

			public void setValueAt(Object aValue, int row, int col) {
				setBeanValueAt(aValue, row, col);
			}
		};
		setModel(model);
	}

	abstract protected T newBean();

	@SuppressWarnings("rawtypes")
	public Class getBeanPropertyClass(int column) {
		return columnType[column];
	}

	abstract protected boolean isBeanEditableAt(int row, int col);

	public Object getBeanValueAt(int row, int column) {
		if (column == 0) {
			return row + 1;
		} else {
			T bean = beans.get(row);
			String property = beanProperty[column];
			if (property.toLowerCase().indexOf("date") >= 0) {
				try {
					return df.format(beanInspector.getValue(bean, property));
				} catch (Exception e) {
					return beanInspector.getValue(bean, property);
				}

			} else {
				return beanInspector.getValue(bean, property);
			}

		}

	}

	public void setBeanValueAt(Object value, int row, int column) {
		T bean = beans.get(row);
		String property = beanProperty[column];
		beanInspector.setValue(bean, property, value);
		onChangeBeanData(row, bean, property, value);
		fireTableDataChanged();
	}

	public T getSelectedBean() {
		int row = getSelectedRow();
		return beans.get(row);
	}

	public void onChangeBeanData(int row, T bean, String property, Object val) {
	}

	public boolean onAddRow() {
		return false;
	}

	public boolean onRemoveRow(T bean, int row) {
		return true;
	}

	public void fireTableDataChanged() {
		AbstractTableModel model = (AbstractTableModel) getModel();
		model.fireTableDataChanged();
	}

	public void addBean(T bean) {
		beans.add(bean);
		fireTableDataChanged();
	}

	public void removeBeanAt(int idx) {
		T bean = beans.get(idx);
		if (onRemoveRow(bean, idx)) {
			beans.remove(idx);
			fireTableDataChanged();
		}
	}

	public List<T> getBeans() {
		return this.beans;
	}

	public void setBeans(List<T> beans) {
		this.beans = beans;
		this.fireTableDataChanged();
	}

	public JPopupMenu createRowPopupMenu() {
		rowPopupMenu = new JPopupMenu();
		MouseListener popupListener = new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					rowPopupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		};
		addMouseListener(popupListener);

		JMenuItem addField = new JMenuItem("Add Row");
		addField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (onAddRow())
					fireTableDataChanged();
			}
		});
		rowPopupMenu.add(addField);

		JMenuItem delField = new JMenuItem("Delete Row");
		delField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = getSelectedRow();
				T bean = beans.get(row);
				if (onRemoveRow(bean, row)) {
					beans.remove(row);
					fireTableDataChanged();
				}
			}
		});
		rowPopupMenu.add(delField);

		JMenuItem moveUp = new JMenuItem("Move Up");
		moveUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = getSelectedRow();
				if (row > 0) {
					T bean = beans.remove(row);
					beans.add(row - 1, bean);
					fireTableDataChanged();
				}
			}
		});
		rowPopupMenu.add(moveUp);

		JMenuItem moveDown = new JMenuItem("Move Down");
		moveDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = getSelectedRow();
				if (row < beans.size() - 1) {
					T bean = beans.remove(row);
					beans.add(row + 1, bean);
					fireTableDataChanged();
				}
			}
		});
		rowPopupMenu.add(moveDown);
		return rowPopupMenu;
	}

	public JPanel getPanleButton() {
		final JButton btn4 = new JButton("Cập nhật");
		btn4.setEnabled(false);
		JPanel panel2 = new JPanel();
		panel2.setOpaque(false);
		panel2.setSize(10, 10);
		panel2.setPreferredSize(new Dimension(10, 10));
		MyGroupLayout layout = new MyGroupLayout(panel2);
		JButton btn = new JButton("");
		btn.setName("btnTop");
		ImageIcon imageIcon = new ImageIcon(HomeScreen.class.getResource("icon/upAll.png"));
		btn.setIcon(imageIcon);
		//btn.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("")));
		btn.addMouseListener(new MouseEventClickButtonDialogPlus("upAll.png", "upAll.png", "upAll.png"));
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = getSelectedRow();
				if (row > 0) {
					edit = true;
					btn4.setEnabled(true);
					beans.add(0, beans.remove(row));
					fireTableDataChanged();
				  changeSelection(0, 0, false, false);
				}

			}
		});
		btn.setPreferredSize(new Dimension(94, 50));
		layout.add(0,0,btn);
		JButton btn1 = new JButton("");
		btn1.setName("btnUp");
		ImageIcon imageIcon1 = new ImageIcon(HomeScreen.class.getResource("icon/up.png"));
    btn1.setIcon(imageIcon1);
		btn1.addMouseListener(new MouseEventClickButtonDialogPlus("up.png", "up.png", "up.png"));
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = getSelectedRow();
				if (row > 0) {
					edit = true;
					btn4.setEnabled(true);
					beans.add(row - 1, beans.remove(row));
					fireTableDataChanged();
					changeSelection(row - 1, row - 1, false, false);
				}

			}
		});
		btn1.setPreferredSize(new Dimension(94, 50));
		layout.add(1,0,btn1);
		JButton btn2 = new JButton("");
		btn2.setName("btnDown");
		ImageIcon imageIcon2 = new ImageIcon(HomeScreen.class.getResource("icon/down.png"));
    btn2.setIcon(imageIcon2);
		btn2.addMouseListener(new MouseEventClickButtonDialogPlus("down.png", "down.png", "down.png"));
		btn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = getSelectedRow();
				if (row < beans.size() - 1) {
					edit = true;
					btn4.setEnabled(true);
					beans.add(row + 1, beans.remove(row));
					fireTableDataChanged();
					changeSelection(row + 1, row + 1, false, false);
				}
			}
		});
		btn2.setPreferredSize(new Dimension(94, 50));
		layout.add(2,0,btn2);
		JButton btn3 = new JButton("");
		ImageIcon imageIcon3 = new ImageIcon(HomeScreen.class.getResource("icon/downAll.png"));
    btn3.setIcon(imageIcon3);
		btn3.addMouseListener(new MouseEventClickButtonDialogPlus("downAll.png", "downAll.png", "downAll.png"));
		btn3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = getSelectedRow();
				if (row < beans.size() - 1) {
					edit = true;
					btn4.setEnabled(true);
					beans.add(beans.size() - 1, beans.remove(row));
					fireTableDataChanged();
					changeSelection(beans.size() - 1, beans.size() - 1, false, false);
				}

			}
		});
		btn3.setName("btnBottom");
		btn3.setPreferredSize(new Dimension(94, 50));
		layout.add(3,0,btn3);;

		btn4.setMargin(new Insets(0, 0, 0, 0));
		btn4.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
		btn4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				edit = false;
				btn4.setEnabled(false);
				saveIndex();
			}
		});
		btn4.setPreferredSize(new Dimension(94, 50));
		layout.add(4,0,btn4);
		
		btn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
		btn.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btn.setMaximumSize(new java.awt.Dimension(22, 22));
		btn.setMinimumSize(new java.awt.Dimension(22, 22));
		btn.setPreferredSize(new java.awt.Dimension(22, 22));
		
		btn1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    btn1.setMargin(new java.awt.Insets(0, 0, 0, 0));
    btn1.setMaximumSize(new java.awt.Dimension(22, 22));
    btn1.setMinimumSize(new java.awt.Dimension(22, 22));
    btn1.setPreferredSize(new java.awt.Dimension(22, 22));
    
    btn2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    btn2.setMargin(new java.awt.Insets(0, 0, 0, 0));
    btn2.setMaximumSize(new java.awt.Dimension(22, 22));
    btn2.setMinimumSize(new java.awt.Dimension(22, 22));
    btn2.setPreferredSize(new java.awt.Dimension(22, 22));
    
    btn3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    btn3.setMargin(new java.awt.Insets(0, 0, 0, 0));
    btn3.setMaximumSize(new java.awt.Dimension(22, 22));
    btn3.setMinimumSize(new java.awt.Dimension(22, 22));
    btn3.setPreferredSize(new java.awt.Dimension(22, 22));
    
    btn4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    btn4.setMargin(new java.awt.Insets(0, 0, 0, 0));
    btn4.setMaximumSize(new java.awt.Dimension(22, 22));
    btn4.setMinimumSize(new java.awt.Dimension(22, 22));
    btn4.setPreferredSize(new java.awt.Dimension(22, 22));
//    layout.add(5, 0, new JLabel());
//    layout.add(6, 0, new JLabel());
//    layout.add(7, 0, new JLabel());
		layout.updateGui();
		return panel2;
	}

	public void saveIndex() {

	}
	
	public void loadDeleteVirtual() {

  }

	public boolean isEdit() {
		return edit;
	}

}