package com.hkt.client.rest.service;

import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.client.rest.RESTClient;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;
import com.hkt.module.partner.supplier.entity.Supplier;

@Component
public class SupplierServiceClient {
	@Autowired
	private RESTClient client;

	public boolean deleteAll() throws Exception {
		Request req = create("deleteAll");
		client.POST(req);
		return true;
	}

	public Supplier saveSupplier(Supplier supplier) throws Exception {
		Request req = create("saveSupplier");
		req.addParam("supplier", supplier);
		Response res = client.POST(req);
		return res.getDataAs(Supplier.class);
	}

	public boolean deleteSupplier(Supplier supplier) throws Exception {
		Request req = create("deleteSupplier");
		req.addParam("supplier", supplier);
		client.POST(req);
		return true;
	}

	public List<Supplier> getSuppliers(boolean interactive) throws Exception {
		Request req = create("searchSupplier");
		req.addParam("interactive", interactive);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Supplier>>() {
		});
	}
	
	public List<Supplier> findSupplierByCode(String code) throws Exception{
		Request req = create("findSupplierByCode");
		req.addParam("code", code);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Supplier>>() {
		});
	}
	
  public Supplier getBydLoginId(String loginId, String orgLoginId) throws Exception {
    Request req = create("getByOrgLoginIdAndLoginId");
    req.addParam("loginId", loginId);
    req.addParam("orgLoginId", orgLoginId);
    Response res = client.POST(req);
    return res.getDataAs(Supplier.class);
  }
  
  public Supplier getSupplierByCode(String code) throws Exception {
    Request req = create("getSupplierByCode");
    req.addParam("code", code);
    Response res = client.POST(req);
    return res.getDataAs(Supplier.class);
  }

	public List<Supplier> getAllSuppliers() throws Exception {
		Request req = create("findAllSuppliers");
		req.addParam("query", new FilterQuery());
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Supplier>>() {
		});
	}

	public void deleteTest(String test) throws Exception {
		Request req = create("deleteTest");
		req.addParam("test", test);
		client.POST(req);
	}

	Request create(String method) {
		return new Request("partner", "SupplierService", method);
	}
}
