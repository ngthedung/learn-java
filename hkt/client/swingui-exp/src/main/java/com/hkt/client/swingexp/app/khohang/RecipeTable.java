
package com.hkt.client.swingexp.app.khohang;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.module.restaurant.entity.Recipe;

public class RecipeTable extends BeanBindingJTable<Recipe> {
  
  static String[] HEADERS = { "Id", "Organization Login", "Name", "Product Code", "Recipe", "Description" };
  static String[] PROPERTIES = { "id", "organizationLoginId", "name", "productCode", "recipe", "description" };
  
  public void setRecipes(List<Recipe> recipes) {
    init(HEADERS, PROPERTIES, recipes);
    setRowHeight(23);
    setFont(new Font("Tahoma", 0, 14));
    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
  }
  
  public RecipeTable() {
  }
  
  protected Recipe newBean() {
    return new Recipe();
  }
  
  protected boolean isBeanEditableAt(int row, int col) {
    return false;
  }
}
