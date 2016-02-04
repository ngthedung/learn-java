package com.hkt.client.swingexp.app.khohang.list;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.khohang.PanelRepositoryProducts2;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductTagList;

@SuppressWarnings("serial")
public class TableListProduct extends TableObservable implements ITableMain {
	private List<Product> products;
	private TableModelProduct modelTable;
	private ProductTagList productTagList;

	public TableListProduct(ProductTagList productTags) {
		try {
			this.productTagList = productTags;
			if (productTags != null) {
				products = ProductModelManager.getInstance().findByProductTags(
						productTagList);
			} else {
				products = ProductModelManager.getInstance().filterProduct();
			}
		} catch (Exception e) {
			e.printStackTrace();
			products = new ArrayList<Product>();
		}
		modelTable = new TableModelProduct(new ArrayList<Product>());
		setModel(modelTable);

	}

	// Sự kiện khi click vào 1 dòng trong danh sách
	@Override
	public void click() {
		Product pro = (Product) this.getValueAt(this.getSelectedRow(), 2);
		try {
			PanelRepositoryProducts2 panel = new PanelRepositoryProducts2(pro);
			panel.setName("ChiTietHangHoa");
			DialogMain dialog = new DialogMain(panel, true);
			dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
			dialog.setTitle("Thêm mới hàng hóa");
			dialog.setIcon("hanghoa1.png");
			dialog.setName("dlChiTietHangHoa");
			dialog.setResizable(false);
			dialog.setModal(true);
			dialog.setLocationRelativeTo(null);
			dialog.showButton(false);
			dialog.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		try {
			pro = ProductModelManager.getInstance().getProductByCode(pro.getCode());
			if (pro == null || pro.isRecycleBin()) {
				modelTable.removeRow(this.getSelectedRow());
			} else {
				modelTable.fireTableRowsUpdated(this.getSelectedRow(), this.getSelectedRow());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	// Lấy size của danh sách truyền vào
	@Override
	public int getListSize() {
		return products.size();
	}

	@Override
	public List<Integer> getColumnSum() {
		return null;
	}

	// Hàm gọi load lại dữ liệu lên bảng
	@Override
	public DefaultTableModel loadTable(int index, int pageSize) {
		try {
			modelTable.setData(products.subList(index, pageSize), index);
		} catch (Exception ex) {
			modelTable.setData(new ArrayList<Product>(), 0);
		}
		return modelTable;

	}

	@Override
	public boolean delete() {

		if (UIConfigModelManager.getInstance().getPermission(
				PanelRepositoryProducts2.permission) == Capability.ADMIN) {
			try {
				String str = "Xóa tất cả danh sách đã chọn ";
				PanelChoise panel = new PanelChoise(str + " ?");
				panel.setName("Xoa");
				DialogResto dialog = new DialogResto(panel, false, 0, 80);
				dialog.setName("dlXoa");
				dialog.setTitle("Xóa hàng hóa");
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (panel.isDelete()) {
					for (int i = 0; i < products.size();) {
						Product p = products.get(i);
						if (p.isRecycleBin()) {
							p.setRecycleBin(true);
							products.remove(i);
							ProductModelManager.getInstance()
									.deleteProductByCode(p.getCode());

						} else {
							i++;
						}
					}

				}

			} catch (Exception ex) {
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
