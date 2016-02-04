package com.hkt.module.restaurant.util;

import java.util.List;

import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Recipe;
import com.hkt.module.restaurant.entity.RecipeIngredient;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.util.IOUtil;
import com.hkt.util.json.JSONReader;

public class RestaurantScenario {
  
  private Recipe recipe;
  private List<RecipeIngredient> recipeIngredients; 
  private List<Table> tables;
  private List<Location> locations;
  
  public Recipe getRecipe() { return recipe; }
  
  public void setRecipe(Recipe recipe) { this.recipe = recipe; }
  
  public List<RecipeIngredient> getRecipeIngredients() { return recipeIngredients; }
  
  public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) { this.recipeIngredients = recipeIngredients; }
  
  public List<Table> getTables() { return tables; }
  
  public void setTables(List<Table> tables) { this.tables = tables; }

  public List<Location> getLocations() { return locations; }

  public void setLocations(List<Location> locations) { this.locations = locations; }

  static public RestaurantScenario load(String res) throws Exception {
    JSONReader reader = new JSONReader(IOUtil.loadRes(res)) ;
    RestaurantScenario scenario = reader.read(RestaurantScenario.class) ;
    return scenario ;
  }
  
  static public RestaurantScenario loadTest() throws Exception {
    return load("classpath:scenario/restaurant.json") ;
  }
}