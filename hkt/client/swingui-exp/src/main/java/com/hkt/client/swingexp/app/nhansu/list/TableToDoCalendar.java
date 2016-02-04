package com.hkt.client.swingexp.app.nhansu.list;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.HRServiceClient;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMainExt;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.khachhang.PartnerIsUser;
import com.hkt.client.swingexp.app.nhansu.PanelToDoCalendar;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterQuery.Operator;
import com.hkt.module.hr.entity.Appointment;
import com.hkt.module.hr.entity.Employee;

@SuppressWarnings("serial")
public class TableToDoCalendar extends TableObservable implements ITableMainExt {

	private List<Appointment> appointments;
	private TableModelToDoCalendar modelTable;
	private ClientContext clientContext = ClientContext.getInstance();
	private HRServiceClient client = clientContext.getBean(HRServiceClient.class);
	private int index, pageSize;
	private Date start;
	private Date end;
	private Employee employee;
	private String status;

	public TableToDoCalendar(Date start, Date end, Employee employee, String status) {
		this.start = start;
		this.end = end;
		this.employee = employee;
		this.status = status;
		FilterQuery filterQuery = new FilterQuery();
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 1);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(end);
		c1.set(Calendar.HOUR_OF_DAY, 23);
		c1.set(Calendar.MINUTE, 59);
		filterQuery.add("dateStart", FilterQuery.Operator.GT, c.getTime());
		filterQuery.add("dateStart", FilterQuery.Operator.LT, c1.getTime());
		if (employee != null) {
			filterQuery.add("employeeLoginId", FilterQuery.Operator.EQUAL, employee.getLoginId());
		}
		if (!status.equals("Tất cả")) {
			if (status.equals("Chưa làm")) {
				status = Appointment.Status.UNACCOMPLISHED.toString();
			} else {
				if (status.equals("Đang làm")) {
					status = Appointment.Status.ONGOING.toString();
				} else {
					status = Appointment.Status.COMPLETE.toString();
				}
			}
			filterQuery.add("status", Operator.EQUAL, status);
		}
		try {
			appointments = client.searchAppointment(filterQuery).getData();
		} catch (Exception e) {
			e.printStackTrace();
			appointments = new ArrayList<Appointment>();
		}
		modelTable = new TableModelToDoCalendar(new ArrayList<Appointment>());
		setModel(modelTable);

	}

	@Override
	public void click() {
		Appointment appoinment = (Appointment) this.getValueAt(this.getSelectedRow(), 1);
		try {
			PanelToDoCalendar panel = new PanelToDoCalendar(true);
			panel.setName("CongViecHangNgay1");
			panel.setData(appoinment);
			DialogMain dialog = new DialogMain(panel);
			dialog.setName("dlCongViecHangNgay1");
			dialog.setTitle("Theo dõi1 - CV hàng ngày");
			dialog.setIcon("congviec1.png");
			dialog.setModal(true);
			dialog.setLocationRelativeTo(null);
			dialog.showButton(false);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		FilterQuery filterQuery = new FilterQuery();
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 1);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(end);
		c1.set(Calendar.HOUR_OF_DAY, 23);
		c1.set(Calendar.MINUTE, 59);
		filterQuery.add("dateStart", FilterQuery.Operator.GT, c.getTime());
		filterQuery.add("dateStart", FilterQuery.Operator.LT, c1.getTime());

		if (employee != null) {
			filterQuery.add("employeeLoginId", FilterQuery.Operator.EQUAL, employee.getLoginId());
		}
		if (!status.equals("Tất cả")) {
			if (status.equals("Chưa làm")) {
				status = Appointment.Status.UNACCOMPLISHED.toString();
			} else {
				if (status.equals("Đang làm")) {
					status = Appointment.Status.ONGOING.toString();
				} else {
					status = Appointment.Status.COMPLETE.toString();
				}
			}
			filterQuery.add("status", Operator.EQUAL, status);
		}
		try {
			appointments = client.searchAppointment(filterQuery).getData();
		} catch (Exception e) {
			e.printStackTrace();
			appointments = new ArrayList<Appointment>();
		}
		appoinment = HRModelManager.getInstance().getAppointmentById(appoinment.getId());
		if (appoinment == null || appoinment.isRecycleBin()) {
			modelTable.removeRow(this.getSelectedRow());
		} else {
			modelTable.fireTableRowsUpdated(this.getSelectedRow(), this.getSelectedRow());
		}
		loadTable(index, pageSize);
	}

	@Override
	public int getListSize() {
		return appointments.size();
	}

	@Override
	public List<Integer> getColumnSum() {
		return null;
	}

	@Override
	public DefaultTableModel loadTable(int index, int pageSize) {
		this.index = index;
		this.pageSize = pageSize;
		try {
			modelTable.setData(appointments.subList(index, pageSize), index);
		} catch (Exception ex) {
			modelTable = new TableModelToDoCalendar(new ArrayList<Appointment>());
		}
		return modelTable;
	}

  @Override
  public boolean delete() {
	  if (UIConfigModelManager.getInstance().getPermission(
				PartnerIsUser.permission) == Capability.ADMIN) {
			try {
				String str = "Xóa tất cả danh sách đã chọn ";
				PanelChoise panel = new PanelChoise(str + " ?");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				dialog.setTitle("Xóa công việc hàng ngày");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					for (int i = 0; i < appointments.size();) {
						Appointment app = appointments.get(i);
						
						if (app.isRecycleBin()) {
							app.setRecycleBin(true);
							
							appointments.remove(i);
							 HRModelManager.getInstance().deleteAppointment(app);
							
						} else {
							i++;
						}
					}
				}

			} catch (Exception ex) {
			}
			
			change();
		} else {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return false;
		}
    return false;
  }
  
  @Override
  public boolean isDelete() {
    return true;
  }

@Override
public void loadDataByTime(Date startDate, Date endDate) {
	this.start = startDate;
	this.end = endDate;

	FilterQuery filterQuery = new FilterQuery();
	Calendar c = Calendar.getInstance();
	c.setTime(start);
	c.set(Calendar.HOUR_OF_DAY, 0);
	c.set(Calendar.MINUTE, 1);
	Calendar c1 = Calendar.getInstance();
	c1.setTime(end);
	c1.set(Calendar.HOUR_OF_DAY, 23);
	c1.set(Calendar.MINUTE, 59);
	filterQuery.add("dateStart", FilterQuery.Operator.GT, c.getTime());
	filterQuery.add("dateStart", FilterQuery.Operator.LT, c1.getTime());
	if (employee != null) {
		filterQuery.add("employeeLoginId", FilterQuery.Operator.EQUAL, employee.getLoginId());
	}
	if (!status.equals("Tất cả")) {
		if (status.equals("Chưa làm")) {
			status = Appointment.Status.UNACCOMPLISHED.toString();
		} else {
			if (status.equals("Đang làm")) {
				status = Appointment.Status.ONGOING.toString();
			} else {
				status = Appointment.Status.COMPLETE.toString();
			}
		}
		filterQuery.add("status", Operator.EQUAL, status);
	}
	try {
		appointments = client.searchAppointment(filterQuery).getData();
	} catch (Exception e) {
		e.printStackTrace();
		appointments = new ArrayList<Appointment>();
	}
	change();
}

@Override
public Date getDateStart() {
	return start;
}

@Override
public Date getDateEnd() {
	return end;
}

}
