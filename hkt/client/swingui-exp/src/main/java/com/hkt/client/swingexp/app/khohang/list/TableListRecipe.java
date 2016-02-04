package com.hkt.client.swingexp.app.khohang.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.khohang.PanelRecipe;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.product.entity.Product;
import com.hkt.module.restaurant.entity.Recipe;

@SuppressWarnings("serial")
public class TableListRecipe extends TableObservable implements ITableMain {
	private List<Recipe> listRecipe;
	private TableModelRecipe mdTbRecipe;

	public TableListRecipe() {
		try {
			listRecipe = RestaurantModelManager.getInstance().getRecipes();
		} catch (Exception e) {
			e.printStackTrace();
			listRecipe = new ArrayList<Recipe>();
		}

		mdTbRecipe = new TableModelRecipe(new ArrayList<Recipe>());
		setModel(mdTbRecipe);

	}

	@Override
	public void click() {
		Recipe recipe = (Recipe) this.getValueAt(this.getSelectedRow(), 1);
		try {
			PanelRecipe declarationQuantitative = new PanelRecipe(
					true);
//			declarationQuantitative.setSize(100, 100);
//			declarationQuantitative.setPreferredSize(new Dimension(100, 100));
			declarationQuantitative.setData1(recipe);
			declarationQuantitative.setName("declarationQuantitative");
			DialogMain dialog = new DialogMain(declarationQuantitative, true);
			dialog.setName("dlDeclarationQuantitative");
			dialog.setIcon("ctquanti1.png");
			dialog.setTitle("Khai báo định lượng");
			dialog.showButton(false);
			dialog.setModal(true);
			dialog.setLocationRelativeTo(null);
//			dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment()
//					.getMaximumWindowBounds());
			dialog.setVisible(true);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		try {
			recipe = RestaurantModelManager.getInstance().getRecipeByProductCode(recipe.getProductCode());
			if (recipe == null || recipe.isRecycleBin()) {
				mdTbRecipe.removeRow(this.getSelectedRow());
			} else {
				mdTbRecipe.fireTableRowsUpdated(this.getSelectedRow(), this.getSelectedRow());
			}
		} catch (Exception e) {
			e.printStackTrace();
			listRecipe = new ArrayList<Recipe>();
		}
	}

	@Override
	public int getListSize() {
		return listRecipe.size();
	}

	@Override
	public List<Integer> getColumnSum() {
		return null;
	}

	@Override
	public DefaultTableModel loadTable(int index, int pageSize) {
		try {

			mdTbRecipe.setData(listRecipe.subList(index, pageSize), index);
		} catch (Exception ex) {
			mdTbRecipe = new TableModelRecipe(new ArrayList<Recipe>());
		}
		return mdTbRecipe;
	}

  @Override
  public boolean delete() {
	  if (UIConfigModelManager.getInstance().getPermission(
				PanelRecipe.permission) == Capability.ADMIN) {
			try {
				String str = "Xóa tất cả danh sách đã chọn ";
				PanelChoise panel = new PanelChoise(str + " ?");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				dialog.setTitle("Xóa định lượng");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					for (int i = 0; i < listRecipe.size();) {
						Recipe r = listRecipe.get(i);
						
						if (r.isRecycleBin()) {
							Product p = ProductModelManager.getInstance().getProductByCode(r.getProductCode());
			        p.setRecipe(false);
			        ProductModelManager.getInstance().saveProduct(p);
							r.setRecycleBin(true);
							listRecipe.remove(i);
							RestaurantModelManager.getInstance().deleteRecipe(r);
							
						} else {
							i++;
						}
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			change();

		} else {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return false;
		}
    return false;
  }
  
  @Override
  public boolean isDelete() {
    return true;
  }
}
