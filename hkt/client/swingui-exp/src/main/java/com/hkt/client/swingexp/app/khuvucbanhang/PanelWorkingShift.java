package com.hkt.client.swingexp.app.khuvucbanhang;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextArea;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.core.StoreJComboBox;

import com.hkt.client.swingexp.app.hethong.PanelRecybin;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.khuvucbanhang.list.TableWorkingShift;

import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;

import com.hkt.module.restaurant.entity.Shift;
import com.hkt.util.text.DateUtil;

@SuppressWarnings("serial")
public class PanelWorkingShift extends JPanel implements IDialogMain {

	private JLabel lbCode, lbName, lbHourStart, lbHourEnd, lbDescription,
			lbMessenger, lbStore;
	private JTextField txtCode, txtName;
	private JTextArea txtDescription;
	private JSpinner hourStart, hourEnd;
	private TableWorkingShift table;
	private Shift shift = new Shift();
	private JScrollPane scrollPaneTable;
	public static String permission;
	@SuppressWarnings("unused")
	private int index = 0;
	private Date dateS, dateE;
	private StoreJComboBox cboStore;
	private boolean restore = true;
	private boolean isNew = true;
	private int hSOld, hEOld;
	private String storecombo, codeOld;

	public PanelWorkingShift() throws Exception {
		init();
		setOpaque(false);
		reset();
	}

	private void init() throws Exception {
		dateS = new Date();
		dateE = new Date();
		lbCode = new ExtendJLabel("Mã");
		lbName = new ExtendJLabel("Tên");
		lbHourStart = new ExtendJLabel("Bắt đầu");
		lbHourEnd = new ExtendJLabel("Kết thúc");
		lbDescription = new ExtendJLabel("Miêu tả");
		lbMessenger = new JLabel("");
		lbMessenger.setForeground(Color.RED);
		lbStore = new ExtendJLabel("Cửa hàng");

		txtCode = new ExtendJTextField();
		txtName = new ExtendJTextField();

		txtDescription = new ExtendJTextArea();
		txtDescription.setPreferredSize(new Dimension(50, 50));
		txtDescription.setName("txtDescription");
		txtDescription
				.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		cboStore = new StoreJComboBox();
		cboStore.setFont(new java.awt.Font("Tahoma", 0, 12));

		try {

			SpinnerModel smStart = new SpinnerDateModel();
			hourStart = new JSpinner(smStart);
			DateEditor editor = new JSpinner.DateEditor(hourStart, "HH:mm");
			hourStart.setEditor(editor);
			editor.getTextField().setHorizontalAlignment(SwingConstants.RIGHT);
			hourStart.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					Date date = (Date) ((JSpinner) e.getSource()).getValue();
					Calendar calender = Calendar.getInstance();
					calender.setTime(date);
					int hourS = calender.get(Calendar.HOUR_OF_DAY);
					int minuteS = calender.get(Calendar.MINUTE);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(dateS);
					calendar.set(Calendar.HOUR_OF_DAY, hourS);
					calendar.set(Calendar.MINUTE, minuteS);
					dateS = calendar.getTime();
				}
			});

			SpinnerModel smEnd = new SpinnerDateModel();
			hourEnd = new JSpinner(smEnd);
			DateEditor deditor = new JSpinner.DateEditor(hourEnd, "HH:mm");
			hourEnd.setEditor(deditor);
			deditor.getTextField().setHorizontalAlignment(SwingConstants.RIGHT);
			hourEnd.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					Date date = (Date) ((JSpinner) e.getSource()).getValue();
					Calendar calender = Calendar.getInstance();
					calender.setTime(date);
					int hourS = calender.get(Calendar.HOUR_OF_DAY);
					int minuteS = calender.get(Calendar.MINUTE);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(dateE);
					calendar.set(Calendar.HOUR_OF_DAY, hourS);
					calendar.set(Calendar.MINUTE, minuteS);
					dateE = calendar.getTime();

				}
			});

		} catch (Exception e) {
		}

		table = new TableWorkingShift(getShift());
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				try {
					tbshiftMouseClicked(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

		scrollPaneTable = new JScrollPane();
		scrollPaneTable.setOpaque(false);
		scrollPaneTable.getViewport().setOpaque(false);
		scrollPaneTable.setBorder(null);
		table.setPreferredScrollableViewportSize(new Dimension(300, 390));
		scrollPaneTable.setViewportView(table);

		MyGroupLayout layout = new MyGroupLayout(this);
		layout.add(0, 0, lbCode);
		layout.add(0, 1, txtCode);
		layout.add(0, 2, lbName);
		layout.add(0, 3, txtName);
		layout.add(1, 0, lbStore);
		layout.add(1, 1, cboStore);
		layout.add(2, 0, lbHourStart);
		layout.add(2, 1, hourStart);
		layout.add(2, 2, lbHourEnd);
		layout.add(2, 3, hourEnd);
		layout.add(3, 0, lbDescription);
		layout.add(3, 1, txtDescription);
		layout.addMessenger(lbMessenger);
		layout.add(4, 0, new JLabel());
		layout.add(4, 1, scrollPaneTable);
		layout.updateGui();
	}

	protected void tbshiftMouseClicked(MouseEvent event) throws Exception {
		shift = table.getSelectedBean();
		lbName.setForeground(new Color(51, 51, 51));
		lbCode.setForeground(new Color(51, 51, 51));
		lbHourEnd.setForeground(new Color(51, 51, 51));
		lbHourStart.setForeground(new Color(51, 51, 51));
		lbMessenger.setText("");
		int click = 2;
		if (shift.getName().indexOf("HktTest") > 0 && event.getButton() == 3) {
			click = 1;
		} else {
			shift = null;
		}
		if (event.getClickCount() >= click) {
			shift = table.getSelectedBean();
			setData();
			index = table.getSelectedRow();

			setEnableCompoment(false);
			((DialogMain) getRootPane().getParent()).showButton(false);
		}

	}

	private List<Shift> getShift() throws Exception {
		return RestaurantModelManager.getInstance().getAllShift();

	}

	@Override
	public void save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return;
		} else {

			shift = getData();

			if (!checkData()) {
				return;
			}

			if (restore) {
				if (shift != null) {
					RestaurantModelManager.getInstance().saveShift(shift);

				}

			}
			reset();
		}

	}

	private boolean checkData() {

		int countError = 0;
		boolean codeError = false;

		if (!txtName.getText().equals("") && txtName.getText() != null) {
			lbName.setForeground(new Color(51, 51, 51));
		} else {
			lbName.setForeground(Color.RED);
			countError++;
		}

		try {

			List<Shift> lstshifts = RestaurantModelManager.getInstance()
					.getAllShift();

			String strhStartNow = new SimpleDateFormat("HHmm").format(hourStart
					.getValue());
			int hStartnow = Integer.valueOf(strhStartNow);

			String strhEndNow = new SimpleDateFormat("HHmm").format(hourEnd
					.getValue());
			int hEndNow = Integer.valueOf(strhEndNow);

			if (hSOld != hStartnow || hEOld != hEndNow) {
				
				for (int i = 0; i < lstshifts.size(); i++) {

					String lststore = lstshifts.get(i).getStore();
					if (!codeOld.equals(lstshifts.get(i).getCode())){


					Date dstart = lstshifts.get(i).getHourStart();
					String strdstart = new SimpleDateFormat("HHmm")
							.format(dstart);
					int hStart = Integer.valueOf(strdstart);

					Date dend = lstshifts.get(i).getHourEnd();
					String strend = new SimpleDateFormat("HHmm").format(dend);
					int hEnd = Integer.valueOf(strend);

					if (cboStore.getSelectedItem() == null) {
						if (lstshifts.get(i).getStore() == null) {
							if (hStartnow < hEndNow && hStart < hEnd) {
								if (((hStartnow < hStart) && (hEndNow < hStart))
										|| ((hStartnow > hEnd) && (hEndNow > hEnd))) {
									lbHourStart.setForeground(Color.black);
									lbHourEnd.setForeground(Color.black);
								} else {
									lbHourEnd.setForeground(Color.RED);
									lbHourStart.setForeground(Color.RED);
									countError++;
									codeError = true;
								}
							} else if (hStartnow < hEndNow && hStart > hEnd) {
								if (((hStartnow < hStart) && (hEndNow < hStart))
										&& (hEndNow < hStart)) {
									lbHourStart.setForeground(Color.black);
									lbHourEnd.setForeground(Color.black);
								} else {
									lbHourEnd.setForeground(Color.RED);
									lbHourStart.setForeground(Color.RED);
									countError++;
									codeError = true;
								}
							} else if (hStartnow > hEndNow && hStart < hEnd) {
								if ((hStartnow > hEnd) && (hEndNow < hStart)) {
									lbHourStart.setForeground(Color.black);
									lbHourEnd.setForeground(Color.black);
								} else {
									lbHourEnd.setForeground(Color.RED);
									lbHourStart.setForeground(Color.RED);
									countError++;
									codeError = true;
								}
							} else {
								lbHourEnd.setForeground(Color.RED);
								lbHourStart.setForeground(Color.RED);
								countError++;
								codeError = true;
							}
						}

					} else {
						if (cboStore.getSelectedItem() != null) {

							storecombo = cboStore.getSelectedStore()
									.getCode().toString();
						}
							if (storecombo.equals(lststore)) {
								if (hStartnow < hEndNow && hStart < hEnd) {
									if (((hStartnow < hStart) && (hEndNow < hStart))
											|| ((hStartnow > hEnd) && (hEndNow > hEnd))) {
										lbHourStart.setForeground(Color.black);
										lbHourEnd.setForeground(Color.black);
									} else {
										lbHourEnd.setForeground(Color.RED);
										lbHourStart.setForeground(Color.RED);
										countError++;
										codeError = true;
									}
								} else if (hStartnow < hEndNow && hStart > hEnd) {
									if (((hStartnow < hStart) && (hEndNow < hStart))
											&& (hEndNow < hStart)) {
										lbHourStart.setForeground(Color.black);
										lbHourEnd.setForeground(Color.black);
									} else {
										lbHourEnd.setForeground(Color.RED);
										lbHourStart.setForeground(Color.RED);
										countError++;
										codeError = true;
									}
								} else if (hStartnow > hEndNow && hStart < hEnd) {
									if ((hStartnow > hEnd)
											&& (hEndNow < hStart)) {
										lbHourStart.setForeground(Color.black);
										lbHourEnd.setForeground(Color.black);
									} else {
										lbHourEnd.setForeground(Color.RED);
										lbHourStart.setForeground(Color.RED);
										countError++;
										codeError = true;
									}
								} else {
									lbHourEnd.setForeground(Color.RED);
									lbHourStart.setForeground(Color.RED);
									countError++;
									codeError = true;
								}
							
						} else {

							lbHourStart.setForeground(Color.black);
							lbHourEnd.setForeground(Color.black);
						}
					}
				}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Kiểm tra xem có bị trùng code hay không

		if (txtCode.isEnabled()) {
			if (isNew) {
				if (!txtCode.getText().equals("")
						&& !txtCode.getText().isEmpty()) {
					lbCode.setForeground(new Color(51, 51, 51));
					try {
						Shift s = RestaurantModelManager.getInstance()
								.getShiftByCode(txtCode.getText());
						if (s != null) {
							lbCode.setForeground(Color.RED);
							codeError = true;
							countError++;
							if (s.isRecycleBin()) {
								PanelRecybin panel = new PanelRecybin(
										"Mã đã bị xóa trước đó!",
										" Chọn ĐỒNG Ý để lấy lại thông tin ban đầu hoặc THOÁT và nhập mã khác!");
								panel.setName("Recybin");
								DialogResto dialog = new DialogResto(panel,
										false, 0, 120);
								dialog.setName("dlRecybin");
								dialog.setTitle("Thêm ca làm việc");
								dialog.setLocationRelativeTo(null);
								dialog.setModal(true);
								dialog.setVisible(true);
								if (panel.isDelete()) {
									restore = false;
									s.setRecycleBin(false);
									RestaurantModelManager.getInstance()
											.saveShift(s);
									reset();
									return true;
								}
							}
						}
					} catch (Exception e) {

						e.printStackTrace();
					}
				} else {
					lbCode.setForeground(Color.RED);
					countError++;
				}
			}
		}

		if (countError > 0) {
			if (codeError) {
				lbHourStart.setForeground(Color.RED);
				lbHourEnd.setForeground(Color.RED);
			}

			lbMessenger.setText("Vui lòng kiểm tra lỗi báo đỏ");
			return false;
		} else {
			lbHourStart.setForeground(Color.black);
			lbHourEnd.setForeground(Color.black);
			lbMessenger.setText("");
			return true;
		}

	}

	private Shift getData() {
		restore = true;
		try {
			if (shift == null) {
				shift = new Shift();
			}
			shift.setCode(txtCode.getText());
			shift.setName(txtName.getText());
			if (cboStore.getSelectedItem() != null) {
				shift.setStore(cboStore.getSelectedStore().getCode());
			}
			shift.setHourStart(dateS);
			shift.setHourEnd(dateE);

			shift.setDescription(txtDescription.getText());
			lbMessenger.setText(" ");
			return shift;
		} catch (Exception e) {
			lbMessenger.setVisible(true);
			lbMessenger.setText("Lỗi định dạng dữ liệu");
			e.printStackTrace();
			return new Shift();
		}
	}

	@Override
	public void edit() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return;
		} else {

			setEnableCompoment(true);
			txtCode.setEnabled(false);
			txtCode.setForeground(Color.black);
			cboStore.setEnabled(false);
		}

	}

	private void setEnableCompoment(boolean value) {
		txtName.setEnabled(value);
		txtCode.setEnabled(value);
		txtDescription.setEnabled(value);
		cboStore.setEnabled(value);
		hourStart.setEnabled(value);
		hourEnd.setEnabled(value);
	}

	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {
			String str = "Xóa ca làm việc ";
			PanelChoise pnPanel = new PanelChoise(str + shift + "?");
			pnPanel.setName("Xoa");
			DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
			dialog1.setName("dlXoa");
			dialog1.setTitle("Xóa ca làm việc");
			dialog1.setLocationRelativeTo(null);
			dialog1.setModal(true);
			dialog1.setVisible(true);

			if (pnPanel.isDelete()) {
				RestaurantModelManager.getInstance().deleteShift(shift);
				loadTable();
				reset();
			} else if (pnPanel.isDelete() == false) {
				reset();
			}
		} else {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		}

	}

	private void loadTable() throws Exception {
		shift.getCode();
		shift.getName();
		shift.getDescription();
		shift.getStore();
		table.setWorkingShift(getShift());

	}

	private void setData() {

		try {
			if (shift != null) {

				txtCode.setText(shift.getCode());
				txtName.setText(shift.getName());
				txtDescription.setText(shift.getDescription());
				if (shift.getStore() != null) {
					cboStore.setSelectStoreByCode(shift.getStore());
				} else {
					cboStore.setSelectedIndex(0);
				}
				
				codeOld = shift.getCode();
				hourStart.setValue(shift.getHourStart());
				hourEnd.setValue(shift.getHourEnd());

				Date DatehSOld = shift.getHourStart();
				Date DatehEOld = shift.getHourEnd();

				String str1 = new SimpleDateFormat("HHmm").format(DatehSOld);
				hSOld = Integer.valueOf(str1);
				String str2 = new SimpleDateFormat("HHmm").format(DatehEOld);
				hEOld = Integer.valueOf(str2);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reset() throws Exception {
		setEnableCompoment(true);
		txtName.setText("");
		txtCode.setText(DateUtil.asCompactDateTimeId(new Date()));
		txtDescription.setText("");
		lbName.setForeground(new Color(51, 51, 51));
		lbCode.setForeground(new Color(51, 51, 51));
		lbDescription.setForeground(new Color(51, 51, 51));
		lbHourEnd.setForeground(new Color(51, 51, 51));
		lbHourStart.setForeground(new Color(51, 51, 51));
		lbMessenger.setText(" ");
		if (cboStore.getSelectedItem() != null) {
			cboStore.setSelectedIndex(0);
		}
		hourStart.setValue(new Date());
		hourEnd.setValue(new Date());

		shift = new Shift();
		loadTable();

	}

	@Override
	public void refresh() throws Exception {
		setData();
		setEnableCompoment(false);
		lbName.setForeground(Color.black);
		lbCode.setForeground(Color.black);
		lbHourEnd.setForeground(Color.black);
		lbHourStart.setForeground(Color.black);
		lbMessenger.setText(" ");
		if (cboStore.getSelectedItem() != null) {
			cboStore.setSelectedIndex(0);
		}
	}

}
