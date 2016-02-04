package com.hkt.client.rest.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.client.rest.RESTClient;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Project;
import com.hkt.module.restaurant.entity.Recipe;
import com.hkt.module.restaurant.entity.Shift;
import com.hkt.module.restaurant.entity.Table;

@Component
public class RestaurantServiceClient {

	@Autowired
	private RESTClient client;

	public Table saveTable(Table table) throws Exception {
		Request req = create("saveTable");
		req.addParam("table", table);
		Response res = client.POST(req);
		return res.getDataAs(Table.class);
	}
	
	public List<Project> findProjectByCode(String code) throws Exception {
		Request req = create("findProjectByCode");
		req.addParam("code", code);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Project>>() {
		});
	}

	public Location saveLocation(Location location) throws Exception {
		Request req = create("saveLocation");
		req.addParam("location", location);
		Response res = client.POST(req);
		return res.getDataAs(Location.class);
	}

	public Location getLocationByCode(String locationCode) throws Exception {
		Request req = create("findLocationByCode");
		req.addParam("locationCode", locationCode);
		Response res = client.POST(req);
		return res.getDataAs(Location.class);
	}

	public Project getProjectById(long id) throws Exception {
		Request req = create("getProjectById");
		req.addParam("id", id);
		Response res = client.POST(req);
		return res.getDataAs(Project.class);
	}

	public List<Table> saveTables(List<Table> tables) throws Exception {
		Request req = create("saveTables");
		req.addParam("tables", tables);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Table>>() {
		});
	}

	public Table getTableByCode(String code) throws Exception {
		Request req = create("getTableByCode");
		req.addParam("code", code);
		Response res = client.POST(req);
		return res.getDataAs(Table.class);
	}

	public List<Table> getTables() throws Exception {
		Request req = create("getTables");
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Table>>() {
		});
	}

	public List<Table> findTableByLocation(String locationCode) throws Exception {
		Request req = create("findTableByLocation");
		req.addParam("locationCode", locationCode);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Table>>() {
		});
	}

	public List<Location> getLocations() throws Exception {
		Request req = create("getLocations");
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Location>>() {
		});
	}

	public List<Location> findByAttribute(String name, String value) throws Exception {
		Request req = create("findByAttribute");
		req.addParam("name", name);
		req.addParam("value", value);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Location>>() {
		});
	}

	public boolean deleteTable(Table table) throws Exception {
		Request req = create("deleteTable");
		req.addParam("table", table);
		client.POST(req);
		return true;
	}

	public FilterResult<Recipe> filterRecipe(FilterQuery query) throws Exception {
		Request req = create("filterRecipe");
		req.addParam("query", query);
		Response res = client.POST(req);
		return res.getDataAsByFilter(new TypeReference<FilterResult<Recipe>>() {
		});
	}

	public boolean deleteLocation(Location location) throws Exception {
		Request req = create("deleteLocation");
		req.addParam("location", location);
		client.POST(req);
		return true;
	}

	public HashMap<String, List<Table>> getTableMap() throws Exception {
		HashMap<String, List<Table>> tableMap = new HashMap<String, List<Table>>();
		List<Table> tables = getTables();
		for (Table table : tables) {
			String location = table.getLocationCode();
			List<Table> holder = tableMap.get(location);
			if (holder == null) {
				holder = new ArrayList<Table>();
				holder.add(table);
				tableMap.put(location, holder);
			} else {
				holder.add(table);
				tableMap.put(location, holder);
			}
		}
		return tableMap;
	}

	public Recipe saveRecipe(Recipe recipe) throws Exception {
		Request req = create("saveRecipe");
		req.addParam("recipe", recipe);
		Response res = client.POST(req);
		return res.getDataAs(Recipe.class);
	}

	public void deleteRecipe(Recipe recipe) throws Exception {
		Request req = create("deleteRecipe");
		req.addParam("recipe", recipe);
		client.POST(req);
	}

	public Project saveProject(Project project) throws Exception {
		Request req = create("saveProject");
		req.addParam("project", project);
		Response res = client.POST(req);
		return res.getDataAs(Project.class);
	}

	public Project getProjectByCode(String code) throws Exception {
		Request req = create("getProjectByCode");
		req.addParam("code", code);
		Response res = client.POST(req);
		return res.getDataAs(Project.class);
	}

	public void deleteProject(Project project) throws Exception {
		Request req = create("deleteProject");
		req.addParam("project", project);
		client.POST(req);
	}

	public List<Project> getAllProject() throws Exception {
		Request req = create("getAllProject");
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Project>>() {
		});
	}

	public Shift saveShift(Shift shift) throws Exception {
		Request req = create("saveShift");
		req.addParam("shift", shift);
		Response res = client.POST(req);
		return res.getDataAs(Shift.class);
	}

	public Shift getShiftByCode(String code) throws Exception {
		Request req = create("getShiftByCode");
		req.addParam("code", code);
		Response res = client.POST(req);
		return res.getDataAs(Shift.class);
	}

	public void deleteShift(Shift shift) throws Exception {
		Request req = create("deleteShift");
		req.addParam("shift", shift);
		client.POST(req);
	}

	public List<Shift> getAllShift() throws Exception {
		Request req = create("getAllShift");
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Shift>>() {
		});
	}

	public void deleteTest(String test) throws Exception {
		Request req = create("deleteTest");
		req.addParam("test", test);
		client.POST(req);
	}

	public Recipe getRecipeByProductCode(String productCode) throws Exception {
		Request req = create("getRecipeByProductCode");
		req.addParam("productCode", productCode);
		Response res = client.POST(req);
		return res.getDataAs(Recipe.class);
	}

	Request create(String method) {
		return new Request("restaurant", "RestaurantService", method);
	}

	public List<Project> findByStatus(String status) throws Exception {
		Request req = create("findByStatus");
		req.addParam("status", status);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Project>>() {
		});
	}

	public List<Project> findProjectByParentCode(String code) throws Exception {
		Request req = create("findProjectByParentCode");
		req.addParam("code", code);
		Response res = client.POST(req);
		return res.getDataAs(new TypeReference<List<Project>>() {
		});
	}
}