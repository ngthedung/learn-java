package com.hkt.client.swingexp.app.hethong.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.hkt.client.swing.widget.model.AutoCompleteItem;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductTag;

@SuppressWarnings("serial")
public class GroupProductJComboBoxDelete extends JComboBox<ProductTag> implements IComboBoxDelete<Product> {

	private List<ProductTag> productTags;
	private ProductTag productTag;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GroupProductJComboBoxDelete(ProductTag productTag) {
		this.productTag = productTag;
		productTags = new ArrayList<ProductTag>();
		try {
			productTags = ProductModelManager.getInstance().getProductTags();
			for (int i = 0; i < productTags.size(); i++) {
				if (productTags.get(i).getTag().equals(productTag.getTag())) {
					productTags.remove(i);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		productTags.add(0, null);
		setModel(new DefaultComboBoxModel(productTags.toArray()));
	}

	@SuppressWarnings("unchecked")
	public ProductTag getSelectedProductTag() {
		try {
			return (ProductTag) getSelectedItem();
		} catch (Exception e) {
			return null;
		}

	}

	public void setSelectedProductTag(String codeProductTag) {
		for (int i = 0; i < productTags.size(); i++) {
			if (productTags.get(i).getCode().equals(codeProductTag)) {
				this.setSelectedIndex(i);
				break;
			}
		}
	}

	// public void setData(List<ProductTag> listProductTag) {
	// List<AutoCompleteItem<ProductTag>> holder = new
	// ArrayList<AutoCompleteItem<ProductTag>>();
	// this.productTag = listProductTag;
	//
	// for (ProductTag sel : listProductTag) {
	// holder.add(new AutoCompleteItem<ProductTag>(sel.getLabel(), sel));
	// }
	// setModel(new DefaultComboBoxModel(holder.toArray()));
	// }
	//
	// @Override
	// public ProductTag getItemAt(int index) {
	// return productTag.get(index);
	// }
	//
	// public ProductTag getSelectedType() {
	// return ((AutoCompleteItem<ProductTag>) getSelectedItem()).getValue();
	// }
	//
	// public void setSelectedProductTag(String codeProductTag) {
	// for (int i = 0; i < holder.size(); i++) {
	// if (holder.get(i).getValue().getCode().equals(codeProductTag)) {
	// this.setSelectedIndex(i);
	// break;
	// }
	// }
	// }

	@Override
	public boolean delete(List<Product> list) {
		try {
			for (int i = 0; i < list.size(); i++) {
				ProductModelManager.getInstance().deleteProductByCode(list.get(i).getCode());
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public void changeGroup(List<Product> list) {
		ProductTag tag = getSelectedProductTag();
		try {
			for (int i = 0; i < list.size(); i++) {
				Product product = list.get(i);
				if (tag != null) {
					product.add(tag);
				}
				for (int j = 0; j < product.getProductTags().size(); j++) {
					if (product.getProductTags().get(j).getTag().equals(productTag.getTag())) {
						product.getProductTags().remove(j);
						break;
					}
				}
				ProductModelManager.getInstance().saveProduct(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
