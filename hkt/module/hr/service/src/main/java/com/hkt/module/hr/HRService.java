package com.hkt.module.hr;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.account.AccountService;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.account.entity.AccountMembership.Status;
import com.hkt.module.cms.CMSService;
import com.hkt.module.cms.entity.Node;
import com.hkt.module.cms.entity.NodeAttribute;
import com.hkt.module.cms.entity.NodeAttributes;
import com.hkt.module.cms.entity.NodeDetail;
import com.hkt.module.core.ServiceCallback;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.hr.entity.Appointment;
import com.hkt.module.hr.entity.DailyWork;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.hr.entity.WorkPosition;
import com.hkt.module.hr.repository.AppointmentRepository;
import com.hkt.module.hr.repository.DailyWorkRepository;
import com.hkt.module.hr.repository.EmployeeRepository;
import com.hkt.module.hr.repository.WorkPositionRepository;
import com.hkt.module.hr.util.HRScenario;
import com.hkt.module.hr.util.HRScenario.EmployeeScenario;

@Service("HRService")
public class HRService {
	@Autowired
	private AccountService					accountService;

	@Autowired
	private CMSService							cmsService;

	@Autowired
	private WorkPositionRepository	workPositionRepo;

	@Autowired
	private EmployeeRepository			employeeRepo;

	@Autowired
	private DailyWorkRepository			dailyWorkRepo;

	@Autowired
	private AppointmentRepository		appointmentRepo;

	private String									cmsBasePath	= "organization";

	public AccountService getAccountService() {
		return this.accountService;
	}

	@Transactional(readOnly = true)
	public Employee getByOrgLoginIdAndLoginId(String loginId, String orgLoginId) {
		return employeeRepo.getByOrgLoginIdAndLoginId(loginId, orgLoginId);
	}

	@Transactional
	public Employee saveEmployee(Employee employee) {
		return employeeRepo.save(employee);
	}

	//khanhdd
	/** @param id */
	@Transactional
	public boolean deleteEmployeeById(Long id) throws Exception {
		Employee employee = employeeRepo.findOne(id);
		if(employee == null){
			return false;
		} else return deleteEmployeeCallBack(employee, null);
	}

	/**
	 * @param employee
	 * @param callBack
	 */
	//khanhdd
	@Transactional
	public boolean deleteEmployeeCallBack(Employee employee, ServiceCallback<HRService> callBack) throws Exception {
		if (callBack != null) {
			callBack.callback(this);
		}
		if (employee.getName().indexOf("test") >= 0) {
			try {
				accountService.deleteAccountByLoginId(employee.getLoginId());
			} catch (Exception e) {
			}
		}
		if(employee.isRecycleBin()){
			employeeRepo.delete(employee);
			return true;
		} else {
			employee.setRecycleBin(true);
			if(employeeRepo.save(employee) != null)
				return true;
			else return false;
		}
	}

	@Transactional(readOnly = true)
	public List<Employee> searchEmployee() {
		return employeeRepo.findByOrganizationLoginId("hkt");
	}

	@Transactional(readOnly = true)
	public WorkPosition getWorkPositionById(Long id) {
		return workPositionRepo.findOne(id);
	}

	@Transactional
	public WorkPosition saveWorkPosition(Employee employee, WorkPosition position) {
		if (employee.getId() == null | position == null) {
			return null;
		}
		position.setEmployeeId(employee.getId());
		position.setLoginId(employee.getLoginId());
		AccountMembership membership = accountService.getMembershipByAccountAndGroup(employee.getLoginId(), position.getGroup());
		if (membership == null) {
			membership = new AccountMembership();
			membership.setCapability(Capability.ADMIN);
			membership.setLoginId(employee.getLoginId());
			membership.setRole(position.getPositionTitle());
			membership.setStatus(Status.ACTIVE);
			membership.setGroupPath(position.getGroup());
			accountService.saveAccountMembership(membership);
			if (!position.isNew()) {
				accountService.deleteMembership(employee.getLoginId(), workPositionRepo.findOne(position.getId()).getGroup());
			}
		} else {
			if (membership.getRole() != position.getPositionTitle()) {
				membership.setRole(position.getPositionTitle());
				accountService.saveAccountMembership(membership);
			}
		}
		return workPositionRepo.save(position);
	}

	//khanhdd
	@Transactional
	public boolean deleteWorkPosition(WorkPosition position) {
		return deletePosition(position, null);
	}

	//khanhdd
	@Transactional
	public boolean deletePosition(WorkPosition position, ServiceCallback<HRService> callBack) {
		if (callBack != null) {
			callBack.callback(this);
		}
		
		if(position == null){
			return false;
		}else {
			if(position.isRecycleBin()){				
				dailyWorkRepo.deleteByPositionId(position.getId());
				workPositionRepo.delete(position);
				return true;
			}else {
				
				List<DailyWork> lstDailyWork = dailyWorkRepo.findByPositionId(position.getId());
				for(int i =0;i < lstDailyWork.size();i++){
					DailyWork d = lstDailyWork.get(i);
					d.setRecycleBin(true);
					dailyWorkRepo.save(d);
				}
				
				position.setRecycleBin(true);
				if(workPositionRepo.save(position)!=null){	
					return true;
				}else {
					return false;
				}	
			}
		}
	}

	@Transactional(readOnly = true)
	public List<WorkPosition> findWorkPositionByEmployeeId(Long employeeId) {
		return workPositionRepo.findByEmployeeId(employeeId);
	}

	@Transactional
	public DailyWork saveDailyWork(WorkPosition position, DailyWork dailyWork) {
		if (position.getId() == null | dailyWork == null) {
			return null;
		}
		dailyWork.setPositionId(position.getId());
		dailyWorkRepo.save(dailyWork);
		return dailyWork;
	}

	//khanhdd
	@Transactional
	public boolean deleteDailyWorkById(Long id) {
		DailyWork daily = dailyWorkRepo.findOne(id);
		return deleteDailyWork(daily, null);
	}

	@Transactional
	public boolean deleteDailyWork(DailyWork daily, ServiceCallback<HRService> callBack) {
		dailyWorkRepo.delete(daily);
		if (callBack != null) {
			callBack.callback(this);
		}
		return true;
	}

	@Transactional(readOnly = true)
	public List<DailyWork> findDailyWorkByPositionId(Long positionId) {
		return dailyWorkRepo.findByPositionId(positionId);
	}

	@Transactional
	public NodeDetail saveContract(Employee emp, NodeDetail nDetail) {
		if (nDetail == null)
			return null;
		Node node = nDetail.getNode();
		String contractFolder = getEmployeeContractPath(emp);
		String contractPath = contractFolder + "/" + node.getName();

		Node contractNode = cmsService.getNodeByPath(contractPath);
		if (contractNode == null) {
			Node contractFolderNode = cmsService.getNodeByPath(contractFolder);
			if (contractFolderNode == null) {
				contractFolderNode = cmsService.createIfNotExists(contractFolder);
			}
			cmsService.createNode(contractFolder, node, nDetail.getAttributes());
		} else {
			throw new RuntimeException("Fix This");
		}
		return nDetail;
	}

	public List<Node> getEmployeeContracts(Employee emp) {
		String contractFolder = getEmployeeContractPath(emp);
		return cmsService.getNodeChildrenByParentPath(contractFolder);
	}

	private String getEmployeeContractPath(Employee emp) {
		String folderPath = this.cmsBasePath + "/" + emp.getOrganizationLoginId() + "/hr/contract/employee/" + emp.getLoginId();
		return folderPath;
	}

	@Transactional
	public void createScenario(HRScenario scenario) throws Exception {
		for (EmployeeScenario empScenario : scenario.getEmployees()) {
			List<AccountMembership> memberships = empScenario.getMemberships();
			if (memberships != null) {
				for (AccountMembership accountMembership : memberships) {
					accountService.saveAccountMembership(accountMembership);
				}
			}
			NodeDetail contract = empScenario.getContract();
			if (contract != null) {
				NodeAttributes attrs = contract.getAttributes();
				for (Map.Entry<String, NodeAttribute> entry : attrs.entrySet()) {
					entry.getValue().setName(entry.getKey());
				}
			}
		}
	}

	@Transactional
	public void deleteAll() throws Exception {
		employeeRepo.deleteAll();
		workPositionRepo.deleteAll();
		dailyWorkRepo.deleteAll();
		appointmentRepo.deleteAll();
	}

	@Transactional(readOnly = true)
	public Employee getEmployeeByid(long id) {
		return employeeRepo.findOne(id);
	}

	@Transactional(readOnly = true)
	public Employee getEmployeeByCode(String code) {
		return employeeRepo.getEmpByCode(code);
	}

	@Transactional(readOnly = true)
	public WorkPosition getWorkPositionById(long id) {
		return workPositionRepo.findOne(id);
	}

	@Transactional
	public Appointment saveAppointment(Appointment appointment) {
		return appointmentRepo.save(appointment);
	}
	
	@Transactional
  public Appointment getAppointmentById(long id) {
    return appointmentRepo.findOne(id);
  }

	@Transactional
	public FilterResult<Appointment> searchAppointment(FilterQuery query) {
		if (query == null)
			query = new FilterQuery();
		return appointmentRepo.search(query);
	}

	//khanhdd
	@Transactional
	public boolean deleteAppointment(Appointment app) {
		Appointment appointment = appointmentRepo.findOne(app.getId());
		if(appointment == null){
			return false;
		} else return deleteAppointmentCallBack(appointment, null);
	}

	@Transactional
	public boolean deleteAppointmentCallBack(Appointment appointment, ServiceCallback<HRService> callBack) {
		if (callBack != null) {
			callBack.callback(this);
		}
		if(appointment.isRecycleBin()){
			appointmentRepo.delete(appointment);
			return true;
		} else {
			appointment.setRecycleBin(true);
			if(appointmentRepo.save(appointment) != null)
				return true;
			else return false;
		}
	}

	@Transactional
	public void deleteTest(String test) {
		employeeRepo.deleteTest(test);
		appointmentRepo.deleteTest(test);
	}

}