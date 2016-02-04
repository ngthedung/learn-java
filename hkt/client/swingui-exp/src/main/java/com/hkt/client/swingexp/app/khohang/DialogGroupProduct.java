package com.hkt.client.swingexp.app.khohang;

import java.util.List;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductTag;

public class DialogGroupProduct extends JPanel implements IDialogResto {
	private JLabel lbchooseDelProduct;
	private boolean delete;
	private String nameProduct1;
	private List<Product> listProduct1;
	private List<ProductTag> listProductTag1;
	private ProductTag productTag1 = new ProductTag();
	
	public boolean DialogGroupProduct() {
		return delete;
	}

	public DialogGroupProduct(String NameProduct, List<Product> listProduct, List<ProductTag> listProductTag, ProductTag productTag) {
		nameProduct1=NameProduct;
		listProduct1=listProduct;
		listProductTag1=listProductTag;
		productTag1=productTag;
		setOpaque(false);
		lbchooseDelProduct = new javax.swing.JLabel();
		lbchooseDelProduct.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		lbchooseDelProduct.setText(NameProduct);
		lbchooseDelProduct.setHorizontalAlignment(JLabel.CENTER);
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				lbchooseDelProduct, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				lbchooseDelProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
				javax.swing.GroupLayout.PREFERRED_SIZE));
	}


	@Override
  public void Save() throws Exception {
		
		if((listProductTag1.size() == 0 && listProduct1.size() > 0)| (listProductTag1.size() != 0 && listProduct1.size() > 0)){
			delete = true;
			((JDialog) getRootPane().getParent()).dispose();
		}else{
			delete = true;
			((JDialog) getRootPane().getParent()).dispose();
			
		}
  }

}
