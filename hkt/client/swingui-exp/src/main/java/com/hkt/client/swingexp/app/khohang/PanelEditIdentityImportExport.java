package com.hkt.client.swingexp.app.khohang;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.GridLabelLayoutPabel;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.core.entity.AbstractPersistable.State;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.IdentityProduct.ExportType;

public class PanelEditIdentityImportExport extends GridLabelLayoutPabel implements IDialogResto {
	private ExtendJTextField						txtProvider, txtPrice, txtQuantity;
	private MyJDateChooser							txtDateManufacture, txtDateExpiry;
	private IdentityProduct							identityProduct;
	private PanelWarehouseExportImport	panel;
	private List<IdentityProduct>				identityProducts;
	private double											quantityFix	= 0;
	private List<Long>									productIds	= new ArrayList<Long>();
	private InvoiceItem									invoiceItem;
	private ExtendJLabel								lbMessage;

	public PanelEditIdentityImportExport(IdentityProduct identitySelected, List<IdentityProduct> listIdentitys, InvoiceItem item, IDialogMain IParent) {
		this.setOpaque(false);
		this.panel = ((PanelWarehouseExportImport) IParent);
		this.identityProduct = identitySelected;
		this.identityProducts = listIdentitys;
		this.invoiceItem = item;
		
		txtProvider = new ExtendJTextField();
		txtDateManufacture = new MyJDateChooser();
		txtDateExpiry = new MyJDateChooser();
		txtQuantity = new ExtendJTextField();
		txtPrice = new ExtendJTextField();
		lbMessage = new ExtendJLabel("");
		lbMessage.setForeground(Color.red);
		
		ExtendJLabel lbProvider = new ExtendJLabel("Nhà cung cấp");
		ExtendJLabel lbDateManufacture = new ExtendJLabel("Ngày sản xuất");
		ExtendJLabel lbDateExpiry = new ExtendJLabel("Ngày hết hạn");
		ExtendJLabel lbQuantity = new ExtendJLabel("Số lượng");
		ExtendJLabel lbPrice = new ExtendJLabel("Giá SP");

		String productCode = identityProduct.getProductCode();
		double priceFix = identityProduct.getPrice();
		double quantityFix = 0;
		for (int i = 0; i < identityProducts.size(); i++) {
			if (productCode.equals(identityProducts.get(i).getProductCode()) && priceFix == identityProducts.get(i).getPrice()) {
				quantityFix++;
			}
		}
		this.quantityFix = quantityFix;

		txtQuantity.setText(quantityFix + "");
		txtQuantity.setHorizontalAlignment(JTextField.RIGHT);

		txtPrice.setEditable(false);
		txtPrice.setText(MyDouble.valueOf(identityProduct.getPrice()).toString());
		txtPrice.setHorizontalAlignment(JTextField.RIGHT);

		MyGroupLayout layout = new MyGroupLayout(this);
		layout.add(0, 0, lbProvider);
		layout.add(0, 1, txtProvider);
		layout.add(1, 0, lbDateManufacture);
		layout.add(1, 1, txtDateManufacture);
		layout.add(2, 0, lbDateExpiry);
		layout.add(2, 1, txtDateExpiry);
		layout.add(3, 0, lbQuantity);
		layout.add(3, 1, txtQuantity);
		layout.add(4, 0, lbPrice);
		layout.add(4, 1, txtPrice);
		layout.addMessenger(lbMessage);
		layout.updateGui();
		refresh();
	}

	public void save() {
		double quantity = 0;
		try {
			quantity = Double.parseDouble(txtQuantity.getText().trim());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Lỗi dữ liệu !");
		}
		if (quantity - quantityFix > 0) {
			List<InvoiceItem> items = panel.getInvoiceItems();
			if (items == null) {
				lbMessage.setText("Sản phẩm trong kho đã hết !");
				return;
			}
			double countCheck = 0;
			for (int i = 0; i < items.size(); i++) {
				if (identityProduct.getInvoiceItemIdExport() == items.get(i).getId()) {
					countCheck = items.get(i).getQuantity() - items.get(i).getQuantityReal();
				}
			}
			if (countCheck == 0) {
				lbMessage.setText("Sản phẩm trong kho đã hết !");
				return;
			}
			double countAlive = 0;
			for (int i = 0; i < identityProducts.size(); i++) {
				if (identityProducts.get(i).getInvoiceItemIdExport() == identityProduct.getInvoiceItemIdExport()) {
					countAlive++;
				}
			}
			if (countCheck + countAlive < quantity) {
				lbMessage.setText("Số lượng trong kho không đủ !");
				txtQuantity.setText(MyDouble.valueOf(countCheck + countAlive).toString());
				return;
			}
			for (int i = 0; i < countCheck; i++) {
				panel.addIdentityProduct(invoiceItem);
			}

		} else if (quantity - quantityFix < 0) {
			double quantityRemove = quantityFix - quantity;
			double count = 0;
			for (int i = 0; i < identityProducts.size(); i++) {
				if (identityProducts.get(i).getInvoiceItemIdExport() == invoiceItem.getId()) {
					if (identityProducts.get(i).getExportType() == ExportType.NotExport) {
						identityProducts.remove(i);
						invoiceItem.setQuantityReal(invoiceItem.getQuantityReal() - 1);
						count++;
					} else {
						identityProducts.get(i).setPersistableState(State.DELETED);
						invoiceItem.setQuantityReal(invoiceItem.getQuantityReal() - 1);
						List<InvoiceItem> items = panel.getInvoiceItems();
						boolean isAlive = false;
						for (InvoiceItem item : items) {
							if (item.getId() == invoiceItem.getId()) {
								isAlive = true;
								break;
							}
						}
						if (!isAlive) {
							items.add(invoiceItem);
						}
						count++;
					}
					if (count == quantityRemove) {
						break;
					}
				}
			}
		}

		for (int i = 0; i < identityProducts.size(); i++) {
			if (identityProducts.get(i).getInvoiceItemIdExport() == invoiceItem.getId()) {
				identityProducts.get(i).setProvider(txtProvider.getText());
				identityProducts.get(i).setDateExpiry(txtDateExpiry.getDate());
				identityProducts.get(i).setDateMenufacture(txtDateManufacture.getDate());
			}
		}
		exit();
	}

	public void exit() {
		panel.calculatorTotalTableViewRight();
		panel.refeshDataTableLeft();
		((JDialog) getRootPane().getParent()).dispose();
	}

	public void refresh() {
		String productCode = identityProduct.getProductCode();
		double price = identityProduct.getPrice();
		txtProvider.setText(identityProduct.getProvider());
		txtDateExpiry.setDate(identityProduct.getDateExpiry());
		txtDateManufacture.setDate(identityProduct.getDateMenufacture());
		txtPrice.setText(price + "");
		double count = 0;
		for (int i = 0; i < identityProducts.size(); i++) {
			if (identityProducts.get(i).getProductCode().equals(productCode) && identityProducts.get(i).getPrice() == price) {
				productIds.add(identityProducts.get(i).getId());
				count++;
			}
		}
		txtQuantity.setText(count + "");
	}

	@Override
	public void Save() throws Exception {
		save();
	}

}
