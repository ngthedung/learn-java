package com.hkt.client.swingexp.app.banhang.list;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.khohang.PanelRepositoryProducts2;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.module.product.entity.ProductTagList;

@SuppressWarnings("serial")
public class TableListProductPriceType extends TableObservable implements ITableMain {
  private List<Product> products;
  private List<ProductPriceType> productPriceTypes;
  private TableModelPricingTableUnit modelTable;
  private ProductTagList productTagList;

  public TableListProductPriceType(ProductTagList productTags) {
    try {
      this.productTagList = productTags;
      if (productTags != null) {
        products = ProductModelManager.getInstance().findByProductTags(productTagList);
      } else {
        products = ProductModelManager.getInstance().filterProduct();
      }
      productPriceTypes = ProductModelManager.getInstance().getProductPriceTypes();
    } catch (Exception e) {
      e.printStackTrace();
      products = new ArrayList<Product>();
    }
    modelTable = new TableModelPricingTableUnit(products, productPriceTypes);
    setModel(modelTable);
  }

  // Sự kiện khi click vào 1 dòng trong danh sách
  @Override
  public void click() {
    Product pro = (Product) this.getValueAt(this.getSelectedRow(), 0);
    try {
      PanelRepositoryProducts2 panel = new PanelRepositoryProducts2(pro);
      panel.setName("ChiTietHangHoa");
      DialogMain dialog = new DialogMain(panel);
      dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
      dialog.setTitle("Chi tiết hàng hóa");
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
      if (productTagList != null) {
        products = ProductModelManager.getInstance().findByProductTags(productTagList);
      } else {
        products = ProductModelManager.getInstance().filterProduct();
      }
    } catch (Exception e) {
      e.printStackTrace();
      products = new ArrayList<Product>();
    }
    change();
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
      modelTable.setData(products.subList(index, pageSize), productPriceTypes);
    } catch (Exception ex) {
      modelTable.setData(new ArrayList<Product>(), productPriceTypes);
    }
    return modelTable;

  }

  @Override
  public boolean delete() {
    return false;
  }
  
  @Override
  public boolean isDelete() {
    return false;
  }

}
