package com.hkt.client.rest.service;

import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.client.rest.RESTClient;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;
import com.hkt.module.hr.entity.Appointment;
import com.hkt.module.hr.entity.Employee;

@Component
public class HRServiceClient {
	@Autowired
	private RESTClient client;

	public List<Employee> searchEmployee() throws Exception {
		Request req = create("searchEmployee");
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Employee>>() {
		});
	}

	public Employee saveEmployee(Employee employee) throws Exception {
		Request req = create("saveEmployee");
		req.addParam("employee", employee);
		Response res = client.POST(req);
		return res.getDataAs(Employee.class);
	}

	public Employee getBydLoginId(String loginId, String orgLoginId) throws Exception {
		Request req = create("getByOrgLoginIdAndLoginId");
		req.addParam("loginId", loginId);
		req.addParam("orgLoginId", orgLoginId);
		Response res = client.POST(req);
		return res.getDataAs(Employee.class);
	}

	public Employee getEmployeeByCode(String code) throws Exception {
		Request req = create("getEmployeeByCode");
		req.addParam("code", code);
		Response res = client.POST(req);
		return res.getDataAs(Employee.class);
	}

	public Appointment saveAppointment(Appointment appointment) throws Exception {
		Request req = create("saveAppointment");
		req.addParam("appointment", appointment);
		Response res = client.POST(req);
		return res.getDataAs(Appointment.class);
	}

	public boolean deleteAppointment(Appointment appointment) throws Exception {
		Request req = create("deleteAppointment");
		req.addParam("appointment", appointment);
		client.POST(req);
		return true;
	}

	public FilterResult<Appointment> searchAppointment(FilterQuery query) throws Exception {
		Request req = create("searchAppointment");
		req.addParam("query", query);
		Response res = client.POST(req);
		return res.getDataAsByFilter(new TypeReference<FilterResult<Appointment>>() {
		});
	}

	public Appointment getAppointmentById(long id) throws Exception {
		Request req = create("getAppointmentById");
		req.addParam("id", id);
		Response res = client.POST(req);
		return res.getDataAs(Appointment.class);
	}

	public boolean deleteEmployeeById(long id) throws Exception {
		Request req = create("deleteEmployeeById");
		req.addParam("id", id);
		client.POST(req);
		return true;
	}

	public void deleteTest(String test) throws Exception {
		Request req = create("deleteTest");
		req.addParam("test", test);
		client.POST(req);
	}

	Request create(String method) {
		return new Request("hr", "HRService", method);
	}
}