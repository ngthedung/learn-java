package com.hkt.module.restaurant.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.hkt.module.core.entity.AbstractPersistable;

@Entity
@Table(name = "restaurant_recipe")
public class Recipe extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;
  
  private String organizationLoginId;
  private String name;
  @NotNull
  private String productCode;

  @OneToMany(cascade = { CascadeType.ALL}, orphanRemoval = true , fetch = FetchType.EAGER)
  @JoinColumn(name="recipeId", referencedColumnName="id", nullable=false)
  private List<RecipeIngredient> recipeIngredients;
  private String recipe;
  private String description;

  public String getOrganizationLoginId() { return organizationLoginId ; }

  public void setOrganizationLoginId(String organizationLoginId) { this.organizationLoginId = organizationLoginId; }

  public String getName() { return name; }

  public void setName(String name) { this.name = name; }

  public String getProductCode() { return productCode; }

  public void setProductCode(String productCode) { this.productCode = productCode; }

  public List<RecipeIngredient> getRecipeIngredients() { return recipeIngredients; }

  public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) { this.recipeIngredients = recipeIngredients; }

  public void addIngredient(RecipeIngredient ingredient) {
    if (this.recipeIngredients == null) {
      this.recipeIngredients = new ArrayList<RecipeIngredient>();
    }
    recipeIngredients.add(ingredient);
  }

  public String getRecipe() { return recipe; }

  public void setRecipe(String recipe) { this.recipe = recipe; }

  public String getDescription() {  return description; }

  public void setDescription(String description) { this.description = description; }

  @Override
  public String toString() { return name; }
}
