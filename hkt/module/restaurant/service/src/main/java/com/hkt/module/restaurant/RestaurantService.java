package com.hkt.module.restaurant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.core.ServiceCallback;
import com.hkt.module.core.entity.AbstractPersistable.State;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Location.Status;
import com.hkt.module.restaurant.entity.Project;
import com.hkt.module.restaurant.entity.Recipe;
import com.hkt.module.restaurant.entity.RecipeIngredient;
import com.hkt.module.restaurant.entity.Shift;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.module.restaurant.repository.LocationRepository;
import com.hkt.module.restaurant.repository.ProjectRepository;
import com.hkt.module.restaurant.repository.RecipeRepository;
import com.hkt.module.restaurant.repository.ShiftRepository;
import com.hkt.module.restaurant.repository.TableRepository;
import com.hkt.module.restaurant.util.RestaurantScenario;

@Service("RestaurantService")
public class RestaurantService {

  @Autowired
  private RecipeRepository recipeRepository;

  @Autowired
  private TableRepository tableRepository;

  @Autowired
  private LocationRepository locationRepository;

  @Autowired
  private ProjectRepository projectRepository;
  
  @Autowired
  private ShiftRepository shiftRepository;

  @Transactional
  public Recipe saveRecipe(Recipe recipe) {
    if (recipe == null) {
      return null;
    }
    for (int i = 0; i < recipe.getRecipeIngredients().size();) {
      RecipeIngredient ingredient = recipe.getRecipeIngredients().get(i);
      if (ingredient.getPersistableState() == State.DELETED) {
        recipe.getRecipeIngredients().remove(i);
      } else {
        i++;
      }
    }
    return recipeRepository.save(recipe);
  }
  
  
  @Transactional
  public List<Project> findProjectByCode(String code){
  	return projectRepository.findProjectByCode(code);
  }
  
  @Transactional
  public Project getProjectById(long id){
  	return projectRepository.findOne(id);
  }
 
  @Transactional
  public List<Project> findProjectByParentCode(String code){
  	return projectRepository.findProjectByParentCode(code);
  }

  //khanhdd
  @Transactional
  public boolean deleteRecipe(Recipe recipe) throws Exception {
	  if(recipe == null){
		  return false;
	  }else {
		  if(recipe.isRecycleBin()){
			  return deleteRecipeCallBack(recipe, null);
		  }else {
			  recipe.setRecycleBin(true);			  
			  if(recipeRepository.save(recipe)!= null){
				  return true;
			  }else {
				return false;
			} 
		  }
	  }
  }

  @Transactional
  public boolean deleteRecipeCallBack(Recipe recipe, ServiceCallback<RestaurantService> callBack) throws Exception {
    if (callBack != null) {
      callBack.callback(this);
    }
    recipeRepository.delete(recipe);
    return true;
  }

  @Transactional(readOnly = true)
  public FilterResult<Recipe> filterRecipe(FilterQuery query) {
    if (query == null)
      query = new FilterQuery();
    return recipeRepository.search(query);
  }

  @Transactional(readOnly = true)
  public List<Table> getTables() {
    return (List<Table>) tableRepository.findByValueRecycleBin(false);
  }

  @Transactional(readOnly = true)
  public List<Location> getLocations() {
    return locationRepository.findLocationByStatus(Status.Active);
  }

  @Transactional(readOnly = true)
  public List<Location> findByAttribute(String name, String value) {
    return locationRepository.findByAttribute(name, value);
  }

  @Transactional
  public Location saveLocation(Location location) {
    return locationRepository.save(location);
  }
  
  @Transactional
  public Shift saveShift(Shift shift){
	  return shiftRepository.save(shift);
  }
  
  

  @Transactional
  public Location findLocationByCode(String locationCode) {
    return locationRepository.getLocationByCode(locationCode);
  }

  @Transactional
  public Project saveProject(Project project) {
    return projectRepository.save(project);
  }

  @Transactional
  public Project getProjectByCode(String code) {
    return projectRepository.getProjectByCode(code);
  }
  
  @Transactional
  public Shift getShiftByCode(String code){
	  return shiftRepository.getShiftByCode(code);
  }
  

  //khanhdd
  @Transactional
  public void deleteProject(Project project) {
	  if(project!=null){
		  if(project.isRecycleBin()){
				projectRepository.delete(project);
		  }else {
				project.setRecycleBin(true);				
				projectRepository.save(project);	
		   }
	  }
  }
  
  @Transactional
  public boolean deleteShift(Shift shift) throws Exception{
	  
	  if(shift==null){
		  return false;
	  }else {
		if(shift.isRecycleBin()){
			return deleteShiftCallBack(shift, null);			
		}else {
			shift.setRecycleBin(true);			
		    if(shiftRepository.save(shift)!=null){
		    	return true;
		    }else {
				return false;
			}			
		}
	}
  }
  
  @Transactional
  public List<Project> getAllProject() {
    return (List<Project>) projectRepository.findByValueRecycleBin(false);
  }
  
  @Transactional
  public List<Project> findByStatus(String status) {
    return  projectRepository.findByStatus(status, false);
  }
  
  @Transactional
  public List<Shift> getAllShift(){
	  return (List<Shift>) shiftRepository.findByValueRecycleBin(false);
  }

  @Transactional
  public Table getTableByCode(String code) {
    return tableRepository.getTableByCode(code);
  }

  @Transactional
  public Table saveTable(Table table) {
    return tableRepository.save(table);
  }

  @Transactional
  public List<Table> findTableByLocation(String locationCode) {
    return tableRepository.findByLocationCode(locationCode);
  }

  @Transactional
  public List<Table> saveTables(List<Table> tables) {
    return (List<Table>) tableRepository.save(tables);
  }

  //khanhdd
  @Transactional
  public boolean deleteTable(Table table) throws Exception {
	  if(table==null){
		  return false;
	  }else {
		if(table.isRecycleBin()){
			return deleteTableCallBack(table, null);			
		}else {
			table.setRecycleBin(true);			
		    if(tableRepository.save(table)!=null){
		    	return true;
		    }else {
				return false;
			}			
		}
	}
    
  }
  //khanhdd
  @Transactional
  public boolean deleteLocation(Location location) throws Exception {
	  if(location == null){
		  return false;
	  }else {		  
		  if(location.isRecycleBin()){
			  return deleteLocationCallBack(location, null);
		  }else {
			  location.setRecycleBin(true);			  
			  if(locationRepository.save(location) !=null){
				  return true;
			  }else {
				 return false;
			}
		  }
	  }
  }

  @Transactional
  public void createScenario(RestaurantScenario scenario) throws Exception {

    Recipe recipe = scenario.getRecipe();

    List<RecipeIngredient> recipeIngredients = scenario.getRecipeIngredients();
    recipe.setRecipeIngredients(recipeIngredients);
    recipeRepository.save(recipe);
    List<Table> tables = scenario.getTables();
    saveTables(tables);
    List<Location> locations = scenario.getLocations();
    locationRepository.save(locations);
  }

  @Transactional
  public boolean deleteTableCallBack(Table table, ServiceCallback<RestaurantService> callBack) throws Exception {
    if (callBack != null) {
      callBack.callback(this);
    }
    tableRepository.delete(table);
    return true;
  }
  
  @Transactional
  public boolean deleteLocationCallBack(Location location, ServiceCallback<RestaurantService> callBack) throws Exception {
    if (callBack != null) {
      callBack.callback(this);
    }
    locationRepository.delete(location);
    return true;
  }
  
  @Transactional
  public boolean deleteShiftCallBack(Shift shift, ServiceCallback<RestaurantService> callBack) throws Exception {
    if (callBack != null) {
      callBack.callback(this);
    }
    shiftRepository.delete(shift);
    return true;
  }
  
  @Transactional(readOnly = true)
  public Recipe getRecipeByProductCode(String productCode){
    return recipeRepository.getRecipeByProductCode(productCode);
  }

  @Transactional
  public void deleteAll() throws Exception {
    recipeRepository.deleteAll();
    tableRepository.deleteAll();
    locationRepository.deleteAll();
  }

  @Transactional
  public void deleteTest(String test) {
    recipeRepository.deleteTest(test);
    tableRepository.deleteTest(test);
    locationRepository.deleteTest(test);
    projectRepository.deleteTest(test);
  }

}
