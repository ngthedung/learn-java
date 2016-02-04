package com.hkt.client.swingexp.model;

import java.util.ArrayList;
import java.util.List;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.HRServiceClient;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.hr.entity.Appointment;
import com.hkt.module.hr.entity.Employee;

public class HRModelManager {
  private static HRModelManager instance;

  private ClientContext clientContext = ClientContext.getInstance();
  private HRServiceClient clientCore = clientContext.getBean(HRServiceClient.class);
  private AccountGroup accountGroupD;

  private HRModelManager() {
    try {
      accountGroupD = AccountModelManager.getInstance().getGroupByPath("hkt" + "/" + AccountModelManager.Department);
      if (accountGroupD == null) {
        AccountGroup rootOrganization = AccountModelManager.getInstance().getRootGroupDetail().getChildren().get(0);
        accountGroupD = new AccountGroup();
        accountGroupD.setLabel("Nhóm phòng ban");
        accountGroupD.setName(AccountModelManager.Department);
        accountGroupD.setOwner(ManagerAuthenticate.getInstance().getOrganizationLoginId());
        accountGroupD.setParent(rootOrganization);
        accountGroupD.setDescription("Nút gốc nhóm phòng ban");
        accountGroupD = AccountModelManager.getInstance().saveGroup(accountGroupD);
      }
    } catch (Exception ex) {
    }
  }
  
  

  public AccountGroup getRootDepartment() {
    return accountGroupD;
  }



  static public HRModelManager getInstance() {
    if (instance == null) {
      instance = new HRModelManager();
    }
    return instance;
  }
  

  public List<Employee> getEmployees() throws Exception {
    return clientCore.searchEmployee();
  }

  public Appointment saveAppointment(Appointment appointment) throws Exception {
    return clientCore.saveAppointment(appointment);
  }
  
  public Appointment getAppointmentById(long id){
    try {
      return clientCore.getAppointmentById(id);
    } catch (Exception e) {
      return null;
    }
   
  }

  public List<Appointment> searchAppointment() throws Exception {
    return clientCore.searchAppointment(new FilterQuery()).getData();
  }

  public List<Appointment> searchAppointmentById(FilterQuery query) throws Exception {
	    return clientCore.searchAppointment(query).getData();
	  }
  
  public boolean deleteAppointment(Appointment appointment) throws Exception {
    clientCore.deleteAppointment(appointment);
    return true;
  }

  public Employee saveEmployee(Employee employee) throws Exception {
    return clientCore.saveEmployee(employee);
  }
  
  public List<Employee> findEmployeesByAccountGroup(AccountGroup accountGroup) throws Exception {
    List<Employee> employees = new ArrayList<Employee>();
    List<AccountMembership> accountMembership = AccountModelManager.getInstance().findMembershipByGroupPath(accountGroup.getPath());
    for(AccountMembership am : accountMembership){
      Employee e = getBydLoginId(am.getLoginId());
      employees.add(e);
    }
    return employees;
  }
  
	
	public AccountGroup getDepartmentOther() {
    try {
      AccountGroup otherDepartment = AccountModelManager.getInstance().getGroupByPath(
          ManagerAuthenticate.getInstance().getOrganizationLoginId()+"/"+AccountModelManager.Department+"/department-other");
      if (otherDepartment == null) {
      	try {
      		List<AccountGroup> accountGroups = AccountModelManager.getInstance().findByName("department-other");
      		if(accountGroups.size()>0){
      			String sql = "DELETE FROM AccountGroup WHERE `name` = 'department-other'";
      			AccountingModelManager.getInstance().executeSQL(sql.toString());
      		}
      	
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
        otherDepartment = new AccountGroup();
        otherDepartment.setLabel("Phòng khác");
        otherDepartment.setName("department-other");
        otherDepartment.setOwner(ManagerAuthenticate.getInstance().getOrganizationLoginId());
        otherDepartment.setParent(accountGroupD);
        otherDepartment.setDescription("Nhóm phòng ban khác");
        otherDepartment = AccountModelManager.getInstance().saveGroup(otherDepartment);
      }
      return otherDepartment;
    } catch (Exception ex) {
    	
    	
      return null;
    }
  }
	
  public Employee getBydLoginId(String loginId) throws Exception {
    return clientCore.getBydLoginId(loginId, ManagerAuthenticate.getInstance().getOrganizationLoginId());
  }

  public Employee getEmployeeByCode(String code) throws Exception {
    return clientCore.getEmployeeByCode(code);
  }

  public boolean deleteEmployee(long id) throws Exception {
    return clientCore.deleteEmployeeById(id);
  }

  public void deleteTest(String test) throws Exception {
    clientCore.deleteTest(test);
  }
}
