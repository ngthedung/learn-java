package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.hkt.client.swingexp.app.banhang.screen.pos.PanelScreenSaleLocationPos;
import com.hkt.client.swingexp.app.component.ExtendJCheckBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.core.AreaJComboBox;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.util.text.DateUtil;

public class PanelOpenTableArea extends JPanel implements IDialogResto {
	private ButtonGroup				grpRadioButton;
	private JRadioButton			rdAddLocation, rdAddTable;

	private ExtendJLabel			lblNameLocation;
	private ExtendJTextField	txtNameLocation;

	private ExtendJLabel			lblOpenTable, lblCountTable, lblLabelTable, lblNumStart, lblMessager;
	private ExtendJCheckBox		chbLocationOther;
	private AreaJComboBox			cbLocationOther;
	private ExtendJTextField	txtCountTable, txtLabelTable, txtNumStart, txtNumEnd;

	private Location					location;
	private Table							table;
	private DateFormat				ds	= new SimpleDateFormat("yyyyMMddHHmmss");
	private boolean						isSave;
	public static String			permission;

	public boolean isSave() {
		return isSave;
	}

	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}

	public PanelOpenTableArea() {
		this.setLayout(new BorderLayout(0, 10));
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(500, 220));
		grpRadioButton = new ButtonGroup();

		this.add(PanelAddLocation(), BorderLayout.PAGE_START);
		this.add(PanelAddTable(), BorderLayout.CENTER);
		rdAddLocation.setSelected(true);
		txtNumEnd.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				try {
					Integer.parseInt(txtNumEnd.getText());
					txtNumEnd.setForeground(Color.black);
				} catch (Exception e2) {
					txtNumEnd.setForeground(Color.red);
				}

			}
		});
		txtNumStart.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				try {
					Integer.parseInt(txtNumStart.getText());
					txtNumStart.setForeground(Color.black);
				} catch (Exception e2) {
					txtNumStart.setForeground(Color.red);
				}

			}
		});

	}

	public JPanel PanelAddLocation() {
		JPanel panel = new JPanel();
		rdAddLocation = new JRadioButton("Thêm khu vực");
		rdAddLocation.setName("rdAddLocation");
		rdAddLocation.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdAddLocation.setForeground(new Color(51, 51, 51));
		rdAddLocation.setOpaque(false);
		grpRadioButton.add(rdAddLocation);

		lblNameLocation = new ExtendJLabel("Tên khu vực");
		txtNameLocation = new ExtendJTextField();
		txtNameLocation.setName("txtNameLocation");

		JPanel LINE1 = new JPanel();
		final JPanel LINE2 = new JPanel();

		panel.setLayout(new GridLayout(2, 1, 7, 7));
		LINE1.setLayout(new BorderLayout(0, 0));
		LINE2.setLayout(new BorderLayout(62, 0));
		LINE1.setOpaque(false);
		LINE2.setOpaque(false);

		LINE1.add(rdAddLocation, BorderLayout.LINE_START);
		LINE2.add(lblNameLocation, BorderLayout.LINE_START);
		LINE2.add(txtNameLocation, BorderLayout.CENTER);

		panel.add(LINE1);
		panel.add(LINE2);

		panel.setOpaque(false);
		panel.setPreferredSize(new Dimension(500, 60));
		setEnabledLocation(false);
		rdAddLocation.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (!rdAddLocation.isSelected()) {
					setEnabledLocation(false);
					cbLocationOther.setEnabled(false);
					cbLocationOther.resetData(true);
					resetTextLocation();
				} else {
					
					cbLocationOther.setEnabled(false);
					setEnabledLocation(true);
					resetTextTable();
				}
			}
		});
		return panel;
	}

	public JPanel PanelAddTable() {
		JPanel panel = new JPanel();
		rdAddTable = new JRadioButton("Thêm bàn/quầy");
		rdAddTable.setName("rdAddTable");
		rdAddTable.setOpaque(false);
		rdAddTable.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdAddTable.setForeground(new Color(51, 51, 51));
		grpRadioButton.add(rdAddTable);

		panel.setLayout(new GridLayout(6, 1, 5, 5));

		final JPanel LINE1 = new JPanel();
		JPanel LINE2 = new JPanel();
		JPanel LINE3 = new JPanel();
		JPanel LINE4 = new JPanel();
		JPanel LINE5 = new JPanel();

		lblOpenTable = new ExtendJLabel("Mở bàn/quầy khu");
		lblCountTable = new ExtendJLabel("Nhập số lượng bàn");
		lblLabelTable = new ExtendJLabel("Nhập ký hiệu bàn");
		lblNumStart = new ExtendJLabel("Đánh số từ");
		lblMessager = new ExtendJLabel("");
		lblMessager.setForeground(Color.RED);

		lblOpenTable.setPreferredSize(new Dimension(150, 22));
		lblCountTable.setPreferredSize(new Dimension(150, 22));
		lblLabelTable.setPreferredSize(new Dimension(150, 22));
		lblNumStart.setPreferredSize(new Dimension(140, 22));

		txtCountTable = new ExtendJTextField();
		txtCountTable.setName("txtCountTable");
		txtLabelTable = new ExtendJTextField();
		txtLabelTable.setName("txtLabelTable");
		txtNumStart = new ExtendJTextField();
		txtNumEnd = new ExtendJTextField();
		txtCountTable.setHorizontalAlignment(JTextField.RIGHT);
		txtNumStart.setHorizontalAlignment(JTextField.RIGHT);
		txtNumEnd.setHorizontalAlignment(JTextField.RIGHT);
		txtNumStart.setPreferredSize(new Dimension(80, 22));
		txtNumEnd.setEditable(false);

		txtNumStart.setText("1");

		cbLocationOther = new AreaJComboBox(true);
		cbLocationOther.setName("cbLocationOther");

		chbLocationOther = new ExtendJCheckBox("Khu vực khác");
		chbLocationOther.setName("chbLocationOther");

		LINE1.setLayout(new BorderLayout(0, 0));
		LINE1.setOpaque(false);
		LINE1.add(rdAddTable, BorderLayout.LINE_START);

		LINE2.setLayout(new BorderLayout(32, 0));
		LINE2.setOpaque(false);
		LINE2.add(chbLocationOther, BorderLayout.LINE_START);
		LINE2.add(cbLocationOther, BorderLayout.CENTER);

		LINE3.setLayout(new BorderLayout(0, 0));
		LINE3.setOpaque(false);
		LINE3.add(lblCountTable, BorderLayout.LINE_START);
		LINE3.add(txtCountTable, BorderLayout.CENTER);

		LINE4.setLayout(new BorderLayout(0, 0));
		LINE4.setOpaque(false);
		LINE4.add(lblLabelTable, BorderLayout.LINE_START);
		LINE4.add(txtLabelTable, BorderLayout.CENTER);

		LINE5.setLayout(new BorderLayout(11, 0));
		LINE5.setOpaque(false);
		LINE5.add(lblNumStart, BorderLayout.LINE_START);
		LINE5.add(txtNumStart, BorderLayout.CENTER);
		LINE5.add(txtNumEnd, BorderLayout.LINE_END);

		panel.add(LINE1);
		panel.add(LINE2);
		panel.add(LINE3);
		panel.add(LINE4);
		panel.add(LINE5);
		panel.add(lblMessager);

		panel.setOpaque(false);
		panel.setPreferredSize(new Dimension(500, 100));

		txtCountTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					int num = Integer.parseInt(txtCountTable.getText()) + Integer.parseInt(txtNumStart.getText()) - 1;
					txtNumEnd.setText(Integer.toString(num));
				} catch (Exception e2) {
				}

			}
		});

		txtNumStart.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					int num = Integer.parseInt(txtNumStart.getText()) + Integer.parseInt(txtCountTable.getText()) - 1;
					txtNumEnd.setText(Integer.toString(num));
				} catch (Exception e2) {
				}

			}
		});

		chbLocationOther.setSelected(false);
		cbLocationOther.setEnabled(false);
		chbLocationOther.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (chbLocationOther.isSelected()) {
					cbLocationOther.setEnabled(true);
				} else
					cbLocationOther.setEnabled(false);
			}
		});

		setEnabledTable(false);
		rdAddTable.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (!rdAddTable.isSelected()) {
					setEnabledTable(false);
					resetTextTable();
				} else {
					setEnabledTable(true);

					resetTextLocation();
				}
			}
		});
		return panel;
	}
	
	public void setComboBoxLocationOther(String locationCode){
		cbLocationOther.setSelectedLocation(locationCode);
	}

	private void setEnabledLocation(boolean value) {
		txtNameLocation.setEnabled(value);
	}

	private void setEnabledTable(boolean value) {
		chbLocationOther.setEnabled(value);
		txtCountTable.setEnabled(value);
		txtLabelTable.setEnabled(value);
		txtNumStart.setEnabled(value);
		txtNumEnd.setEnabled(value);
	}

	private void resetTextLocation() {
		txtNameLocation.setText("");
		lblMessager.setText("");
	}

	private void resetTextTable() {
		chbLocationOther.setSelected(false);
		try {
			cbLocationOther.setSelectedIndex(0);
		} catch (Exception ex) {
			cbLocationOther.setSelectedItem("");
		}
		txtCountTable.setText("");
		txtLabelTable.setText("");
		txtNumStart.setText("1");
		txtNumEnd.setText("");
		lblMessager.setText("");
	}

	private boolean checkData(String areaCheck) {
		if (areaCheck.equals("location")) {
			if (txtNameLocation.getText() != null && !txtNameLocation.getText().equals("")) {
				return true;
			} else {
				lblMessager.setText("Không được bỏ trống !");
				return false;
			}
		} else {
			if (areaCheck.equals("table")) {
				if (!txtCountTable.getText().equals("") && !txtNumStart.getText().equals("")) {
					try {
						Integer.parseInt(txtCountTable.getText());
						Integer.parseInt(txtNumStart.getText());
						return true;
					} catch (Exception ex) {
						lblMessager.setText("Kiểm tra lại dữ liệu nhập vào !");
						return false;
					}
				} else {
					lblMessager.setText("Không được bỏ trống !");
					return false;
				}
			} else
				return false;
		}
	}

	@Override
	public void Save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {
			if (rdAddLocation.isSelected()) {
				if (checkData("location")) {
					location = new Location();
					location.setCode(ds.format(new Date()));
					location.setName(txtNameLocation.getText());
					location = RestaurantModelManager.getInstance().saveLocation(location);
					if (location != null) {
						DialogNotice.getInstace().setVisible(true);
						resetTextLocation();
						setSave(true);
					}
				}
			} else {
				if (rdAddTable.isSelected()) {
					if (checkData("table")) {
						List<Table> tablesTemp = new ArrayList<Table>();
						int countTable = Integer.parseInt(txtCountTable.getText());
						int startTable = Integer.parseInt(txtNumStart.getText());
						boolean save = true;
						for (int i = 0; i < countTable; i++) {
							table = new Table();
							table.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
							table.setLocationCode(cbLocationOther.getSelectedLocation().getCode());
							table.setCode(DateUtil.asCompactDateTimeId(new Date()) + i);
							table.setName(txtLabelTable.getText() + (startTable + i));
							table = RestaurantModelManager.getInstance().saveTable(table);
							if (table == null) {
								save = false;
							} else {
								tablesTemp.add(table);
							}
						}
						if (save) {
							DialogNotice.getInstace().setVisible(true);
							resetTextTable();
							ScreenOften.getInstance().getPanelTable().refeshGui();
							PanelScreenSaleLocationPos.getInstance().refeshGuiTable(tablesTemp);
							setSave(true);
						} else {
							lblMessager.setText("Kiểm tra lại dữ liệu nhập vào !");
						}
					}
				} else {
					lblMessager.setText("");
					setSave(false);
				}
			}
		}
	}
}
