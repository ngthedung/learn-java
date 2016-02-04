package com.hkt.client.swingexp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.RestaurantServiceClient;
import com.hkt.client.swingexp.app.banhang.screen.often.ScreenOften;
import com.hkt.client.swingexp.app.banhang.screen.pos.PanelScreenSaleLocationPos;
import com.hkt.module.core.entity.AbstractPersistable;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Project;
import com.hkt.module.restaurant.entity.Recipe;
import com.hkt.module.restaurant.entity.Shift;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.module.warehouse.entity.WarehouseInventory;

public class RestaurantModelManager {

	private static RestaurantModelManager instance;
	private ClientContext clientContext = ClientContext.getInstance();
	private RestaurantServiceClient restaurantClient = clientContext.getBean(RestaurantServiceClient.class);
	public static String Project = "Dự án";
	private Project projectOther;
	private boolean recipe;
	private HashMap<String, Recipe> hashMap = new HashMap<String, Recipe>();

	public HashMap<String, Recipe> getRecipeByProductCode() {
		return hashMap;
	}

	public boolean isRecipe() {
		return recipe;
	}

	public void setRecipe(boolean recipe) {
		this.recipe = recipe;
	}

	private RestaurantModelManager() {
		try {
			projectOther = getProjectByCode("project-other");
			if (projectOther == null) {
				projectOther = new Project();
				projectOther.setName("Dự án khác");
				projectOther.setCode("project-other");
				projectOther = saveProject(projectOther);
			}
		} catch (Exception e) {
		}
		try {
			List<Recipe> recipes = getRecipes();
			for (Recipe c : recipes) {
				hashMap.put(c.getProductCode(), c);
			}
		} catch (Exception e) {
		}

	}

	public Project getProjectById(long id) {
		try {
			return restaurantClient.getProjectByCode(restaurantClient.getProjectById(id).getCode());
		} catch (Exception e) {
			return null;
		}
	}

	public List<Project> findProjectByCode(String code) {
		try {
			return restaurantClient.findProjectByCode(code);
		} catch (Exception e) {
			return new ArrayList<Project>();
		}
	}

	public void saveSettings() {
		ClientContext.getInstance();
	}

	public static RestaurantModelManager getInstance() {
		if (instance == null) {
			instance = new RestaurantModelManager();
		}
		return instance;
	}

	public Table getTableByCode(String name) {
		try {
			return restaurantClient.getTableByCode(name);
    } catch (Exception e) {
	    return null;
    }
		
	}

	public List<Table> getTables() throws Exception {
		List<Table> tables = restaurantClient.getTables();
		for (int i = 0; i < tables.size();) {
			if (tables.get(i).getStatus().equals(Table.Status.InActivate) || tables.get(i).getCode().equals("other")
			    || tables.get(i).getCode().equals("PaymentAfter")) {
				tables.remove(i);
			} else {
				i++;
			}
		}
		return tables;
	}

	public List<Table> findTableByLocation(String locationCode) throws Exception {
		List<Table> tables = restaurantClient.findTableByLocation(locationCode);
		for (int i = 0; i < tables.size();) {
			if (tables.get(i).getStatus().equals(Table.Status.InActivate) || tables.get(i).getCode().equals("other")
			    || tables.get(i).getCode().equals("PaymentAfter")) {
				tables.remove(i);
			} else {
				i++;
			}
		}
		return tables;
	}

	public List<Location> getLocations() {
		try {
			List<Location> locations = restaurantClient.getLocations();
			for (int i = 0; i < locations.size();) {
				if (locations.get(i).getCode().equals("other") || locations.get(i).getCode().equals("PaymentAfter")) {
					locations.remove(i);
				} else {
					i++;
				}
			}
			Collections.sort(locations, new Comparator<Location>() {
				@Override
				public int compare(Location it1, Location it2) {
					if (it1.getPriority() > it2.getPriority()) {
						return 1;
					} else {
						if (it1.getPriority() < it2.getPriority()) {
							return -1;
						} else {
							return 0;
						}
					}

				}
			});
			return locations;
		} catch (Exception e) {
			return new ArrayList<Location>();
		}

	}

	public List<Location> findByAttribute(String name, String value) throws Exception {
		return restaurantClient.findByAttribute(name, value);
	}

	public Location getLocationOther() {
		try {
			Location location = restaurantClient.getLocationByCode("other");
			if (location == null) {
				location = new Location();
				location.setName("Khác");
				location.setCode("other");
				return saveLocation(location);
			} else {
				return location;
			}
		} catch (Exception e) {
			return null;
		}

	}

	public Table getTableOther() {
		try {
			Table location = restaurantClient.getTableByCode("other");
			if (location == null) {
				location = new Table();
				location.setName("Khác");
				location.setCode("other");
				location.setLocationCode("other");
				return saveTable(location);
			} else {
				return location;
			}
		} catch (Exception e) {
			return null;
		}

	}

	public Location getLocationPaymentAfter() {
		try {
			Location location = restaurantClient.getLocationByCode("PaymentAfter");
			if (location == null) {
				location = new Location();
				location.setName("Bán nhanh");
				location.setCode("PaymentAfter");
				return saveLocation(location);
			} else {
				return location;
			}
		} catch (Exception e) {
			return null;
		}

	}

	public Table getTablePaymentAfter() {
		try {
			Table location = restaurantClient.getTableByCode("PaymentAfter");
			if (location == null) {
				location = new Table();
				location.setName("Bán nhanh");
				location.setCode("PaymentAfter");
				location.setLocationCode("PaymentAfter");
				return saveTable(location);
			} else {
				return location;
			}
		} catch (Exception e) {
			return null;
		}

	}

	public Location getLocationByCode(String locationCode) {
		try {
			return restaurantClient.getLocationByCode(locationCode);
		} catch (Exception e) {
			return null;
		}

	}

	public Table saveTable(Table table) throws Exception {
		table = restaurantClient.saveTable(table);
		return table;
	}

	public boolean deleteTable(Table table) throws Exception {
		restaurantClient.deleteTable(table);
		return true;
	}

	public Location saveLocation(Location location) throws Exception {
		location = restaurantClient.saveLocation(location);
		final Location loc = location;
		Thread t = new Thread() {
			public void run() {
				try {
					ScreenOften.getInstance().getPanelTable().refeshGui();
					PanelScreenSaleLocationPos.getInstance().refeshGuiLocation(loc);
				} catch (Exception e) {
					System.out.println("loi load Table");
				}

			};
		};
		t.start();
		return location;
	}

	public boolean deleteLocation(Location location) throws Exception {
		location.setPersistableState(AbstractPersistable.State.DELETED);
		final Location loc = location;
		restaurantClient.deleteLocation(location);
		Thread t = new Thread() {
			public void run() {
				try {
					ScreenOften.getInstance().getPanelTable().refeshGui();
					PanelScreenSaleLocationPos.getInstance().refeshGuiLocation(loc);
				} catch (Exception e) {
					System.out.println("loi load Table");
				}

			};
		};
		t.start();

		return true;
	}

	public List<Recipe> getRecipes() throws Exception {
		return restaurantClient.filterRecipe(new FilterQuery()).getData();
	}

	public Recipe saveRecipe(Recipe recipe) throws Exception {
		return restaurantClient.saveRecipe(recipe);
	}

	public void deleteRecipe(Recipe recipe) throws Exception {
		List<WarehouseInventory> warehouseInventories = WarehouseModelManager.getInstance().findInventoryByCreatedBy(
		    String.valueOf(recipe.getId()));
		for (int j = 0; j < warehouseInventories.size(); j++) {
			WarehouseModelManager.getInstance().deleteWarehouseInventory(warehouseInventories.get(j));
		}
		restaurantClient.deleteRecipe(recipe);
	}

	public Project saveProject(Project project) throws Exception {
		return restaurantClient.saveProject(project);
	}

	public Project getProjectByCode(String code) {
		try {
			return restaurantClient.getProjectByCode(code);
		} catch (Exception e) {
			return null;
		}

	}

	public void deleteProject(Project project) throws Exception {
		restaurantClient.deleteProject(project);
	}

	public List<Project> getAllProject() throws Exception {
		List<Project> projects = restaurantClient.getAllProject();
		for (Project p : projects) {
			p.setCode(p.codeView());
		}
		return projects;
	}

	public List<Project> findByStatus(String status) {
		try {
			return restaurantClient.findByStatus(status);
		} catch (Exception e) {
			return new ArrayList<Project>();
		}

	}

	public List<Project> findProjectByParentCode(String code) {
		try {
			return restaurantClient.findProjectByParentCode(code);
		} catch (Exception e) {
			return new ArrayList<Project>();
		}
	}

	public Shift saveShift(Shift shift) throws Exception {
		return restaurantClient.saveShift(shift);
	}

	public Shift getShiftByCode(String code) throws Exception {
		return restaurantClient.getShiftByCode(code);
	}

	public void deleteShift(Shift shift) throws Exception {
		restaurantClient.deleteShift(shift);
	}

	public List<Shift> getAllShift() throws Exception {
		return restaurantClient.getAllShift();
	}

	public void deleteTest(String test) throws Exception {
		restaurantClient.deleteTest(test);
	}

	public Recipe getRecipeByProductCode(String productCode) throws Exception {
		return restaurantClient.getRecipeByProductCode(productCode);
	}

	public Project getProjectOther() {
		try {
			return projectOther;
		} catch (Exception e) {
			return null;
		}
	}
}
