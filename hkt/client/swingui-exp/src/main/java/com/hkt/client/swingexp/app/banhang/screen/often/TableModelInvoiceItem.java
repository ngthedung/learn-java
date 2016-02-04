package com.hkt.client.swingexp.app.banhang.screen.often;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.TableModelObservable;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.DefaultInvoiceCalculator;
import com.hkt.module.accounting.entity.Invoice.Status;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.promotion.entity.PromotionGiveUsed;
import com.hkt.module.promotion.entity.PromotionGiveaways;
import com.hkt.module.promotion.entity.PromotionUsed;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.IdentityProduct.ExportType;

public class TableModelInvoiceItem extends TableModelObservable {

	private boolean[] canEdit = new boolean[] { false, false, true, true, true, false, true };
	private String[] header = { "STT", "Tên SP", "Số lg", "Đơn giá", "%", "Tiền", "Tặng" };
	private TableModelPromotion tablePromotion;
	private InvoiceDetail invoiceDetail;
	private String loginId = "";
	private String pricingType = "";
	private InvoiceItem invoiceItem;
	private boolean update = true;
	private DateFormat dfCode = new SimpleDateFormat("yyyyMMddHHmmss");
	private Table table;
	private TableModelDiscountItem tableModelDiscountItem;
	private Profile profile;

	public String getCustomerLoginId() {
		return loginId;
	}

	public TableModelInvoiceItem(InvoiceDetail invoiceDetail) {
		this.invoiceDetail = invoiceDetail;
		dataVector = convertToVector(invoiceDetail.getItems().toArray());

		tablePromotion = new TableModelPromotion(new Hashtable<String, PromotionProduct>());
		tableModelDiscountItem = new TableModelDiscountItem(invoiceDetail);
		addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				try {
					if (invoiceItem != null && update) {
						loadPromotion();
						invoiceItem = null;
						changeCaculate(null, "");
					}

				} catch (Exception e2) {
				}
			}
		});
		profile = AccountModelManager.getInstance().getProfileConfigAdmin();
	}

	public TableModelDiscountItem getTableModelDiscountItem() {
		return tableModelDiscountItem;
	}

	public TableModelPromotion getTablePromotion() {
		return tablePromotion;
	}

	public void setInfoInvoice(String customerLoginId, String pricingType, Table table) {
		this.table = table;
		this.loginId = customerLoginId;
		this.pricingType = pricingType;
		changeCaculate(null, "");
	}

	public void loadPromotionProduct() {
		for (InvoiceItem invoiceItem : invoiceDetail.getItems()) {
			this.invoiceItem = invoiceItem;
			loadPromotion();
		}
		changeCaculate(null, "");
		this.invoiceItem = null;
	}

	public void changeCaculate(Object object, Object object1) {
		setChanged();
		notifyObservers(object, object1);
	}

	public void setData(InvoiceDetail invoiceDetail) {
		if (invoiceDetail.getStatus() == null) {
			invoiceDetail.setStatus(Status.WaitingPaid);
		}
		this.invoiceDetail = invoiceDetail;
		if (profile.get("PaymentSlip") == null || invoiceDetail.getStatus().equals(Status.Paid)) {
			dataVector = convertToVector(invoiceDetail.getItems().toArray());
		} else {
			List<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>();
			for (InvoiceItem invoiceItem : invoiceDetail.getItems()) {
				if (!invoiceItem.getStatus().equals(AccountingModelManager.isPayment)) {
					invoiceItems.add(invoiceItem);
				}
			}
			dataVector = convertToVector(invoiceItems.toArray());
		}

		tableModelDiscountItem.setData(invoiceDetail);
		fireTableDataChanged();
		Hashtable<String, PromotionProduct> products = new Hashtable<String, PromotionProduct>();
		try {
			List<PromotionUsed> promotionUseds = PromotionModelManager.getInstance().findPromotionUsedByInvoiceId(
			    invoiceDetail.getId());
			for (PromotionUsed promotionUsed : promotionUseds) {
				PromotionProduct product = new PromotionProduct(promotionUsed);
				products.put(promotionUsed.getCode(), product);
			}
		} catch (Exception e) {
		}
		updateIndex();
	}

	public void updateItem(InvoiceItem invoiceItem) {
		this.invoiceItem = invoiceItem;
		int row = dataVector.indexOf(invoiceItem) + 1;
		InvoiceItem rowVector1 = AccountingModelManager.getInstance().getInvoiceItem(invoiceDetail,
		    invoiceItem.getProductCode(), AccountingModelManager.isForRent);
		if (rowVector1 != null) {
			rowVector1.setTotal(rowVector1.getUnitPrice() * rowVector1.getQuantity());
			fireTableRowsUpdated(row, row);
		}
		changeCaculate(null, "");
		loadPromotion();
		fireTableDataChanged();
		updateIndex();
		tableModelDiscountItem.setData(invoiceDetail);

	}

	@Override
	public void removeRow(int row) {
		InvoiceItem in = (InvoiceItem) dataVector.get(row);
		List<IdentityProduct> identityProducts = new ArrayList<IdentityProduct>();
		try {
			identityProducts = WarehouseModelManager.getInstance().getByInvoiceCode(invoiceDetail.getInvoiceCode());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		if (identityProducts.size() > 0) {
			for (IdentityProduct identityProduct : identityProducts) {
				if (identityProduct.getExportType() == ExportType.Export) {
					JOptionPane.showMessageDialog(null, "Sản phẩm này không thể xóa. lô hàng đã đc xuất");
					return;
				}
			}
		}
		try {
			tablePromotion.removeValue(in.getItemName() + invoiceDetail.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		invoiceDetail.getItems().remove(in);
		invoiceDetail.calculate(new DefaultInvoiceCalculator());
		super.removeRow(row);
		changeCaculate(null, "");
		tableModelDiscountItem.setData(invoiceDetail);
		fireTableDataChanged();
	}

	public void deleteAllIdentityProductExport(InvoiceDetail invoice) {

		try {
			List<IdentityProduct> products = WarehouseModelManager.getInstance().getByInvoiceExportCode(
			    invoice.getInvoiceCode());
			for (IdentityProduct identityProduct : products) {
				WarehouseModelManager.getInstance().deleteIdentityProductExport(identityProduct);
			}
		} catch (Exception e) {
		}
	}

	public void addItem(InvoiceItem invoiceItem) {
		this.invoiceItem = invoiceItem;
		this.invoiceDetail.add(invoiceItem);
		try {
			if (!invoiceItem.getStatus().equals(AccountingModelManager.isForRent)) {
				ProductPrice p = ProductModelManager.getInstance().getByProductAndProductPriceType(
				    invoiceItem.getProductCode(), pricingType, invoiceItem.getCurrencyUnit());
				invoiceItem.setUnitPrice((p != null) ? p.getPrice() : 0);
			} else {
				ProductPrice p = ProductModelManager.getInstance().getByProductAndProductPriceType(
				    invoiceItem.getProductCode(), pricingType, invoiceItem.getCurrencyUnit());
				invoiceItem.setUnitPrice((p != null) ? new MyDouble(p.getPrice() * 0.1).doubleValue() : 0);
				invoiceItem.setTotal((p != null) ? new MyDouble(p.getPrice() * 0.1).doubleValue() : 0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		loadPromotion();
		if (!invoiceItem.getStatus().equals(AccountingModelManager.isForRent)) {
			dataVector.insertElementAt(invoiceItem, 0);
			fireTableRowsInserted(0, 0);
			// dataVector.addElement(invoiceItem);
			// fireTableRowsInserted(getRowCount(), getRowCount());
		} else {
			dataVector.insertElementAt(invoiceItem, 1);
			fireTableRowsInserted(1, 1);
		}

		updateIndex();
		tableModelDiscountItem.setData(invoiceDetail);
	}

	public void addItem(InvoiceItem invoiceItem, double price) {
		this.invoiceItem = invoiceItem;
		this.invoiceDetail.add(invoiceItem);
		invoiceItem.setUnitPrice(price);
		invoiceItem.setTotal(invoiceItem.getQuantity() * price);
		loadPromotion();
		if (!invoiceItem.getStatus().equals(AccountingModelManager.isForRent)) {
			dataVector.insertElementAt(invoiceItem, 0);
			fireTableRowsInserted(0, 0);
		} else {
			dataVector.insertElementAt(invoiceItem, 1);
			fireTableRowsInserted(1, 1);
		}

		updateIndex();
		tableModelDiscountItem.setData(invoiceDetail);
	}

	private void loadPromotion() {
		try {
			InvoiceItemPromotion invoiceItemPromotion = AccountingModelManager.getInstance().getInvoiceItemPromotion(
			    dataVector, this.invoiceItem.getProductCode());

			double total = invoiceItemPromotion.getQuantity() * invoiceItem.getUnitPrice();
			String code = "", codeLocation = "", storecode = "";
			if (table != null) {
				code = table.getCode();
				codeLocation = table.getLocationCode();
			}
			if (invoiceDetail.getStoreCode() != null) {
				storecode = invoiceDetail.getStoreCode();
			}
			loadPromotionGive(invoiceItem);
			double promotion = PromotionModelManager.getInstance().getPromotionProduct(code, codeLocation, loginId,
			    invoiceItem.getProductCode(), total, storecode);

			if (promotion > 0) {
				PromotionUsed product = PromotionModelManager.getInstance().getPromotionUsed(
				    invoiceItem.getItemName() + invoiceDetail.getId());

				if (product == null) {
					product = new PromotionUsed();
					product.setDescription(invoiceItemPromotion.getName());
					product.setUsedDate(new Date());
					product.setCode(invoiceItemPromotion.getName() + invoiceDetail.getId());
					product.setPromotionId(0);
					product.setQuantity(invoiceItemPromotion.getQuantity());
					product.setSaving(promotion);
					product.setExpense(invoiceItemPromotion.getQuantity() * promotion);
					product.setInvoiceId(invoiceDetail.getId());
					product = PromotionModelManager.getInstance().savePromotionUsed(product);
					// tablePromotion.addItem(invoiceItemPromotion.getName() +
					// invoiceDetail.getId(), promotionUsed);
					// tablePromotion.fireTableDataChanged();
					if (PromotionModelManager.getInstance().isPromotionProductDiscount) {
						invoiceItem.setDiscountRate(promotion / invoiceItem.getUnitPrice() * 100d);
					} else {
						invoiceItem.setDiscountRate(promotion / invoiceItemPromotion.getQuantity() / invoiceItem.getUnitPrice()
						    * 100d);
					}

				} else {
					product.setQuantity(invoiceItemPromotion.getQuantity());
					if (PromotionModelManager.getInstance().isPromotionProductDiscount) {
						product.setSaving(promotion);
						product.setExpense(promotion * invoiceItemPromotion.getQuantity());
						invoiceItem.setDiscountRate(promotion / invoiceItem.getUnitPrice() * 100d);
					} else {
						invoiceItem.setDiscountRate(promotion / invoiceItemPromotion.getQuantity() / invoiceItem.getUnitPrice()
						    * 100d);
						product.setSaving(promotion / invoiceItemPromotion.getQuantity());
						product.setExpense(promotion);
					}

					PromotionModelManager.getInstance().savePromotionUsed(product);
					// tablePromotion.updateValuePromotion(product);
				}
			} else {
				tablePromotion.removeValue(invoiceItem.getItemName() + invoiceDetail.getId());
			}

		} catch (Exception e) {
			System.out.println("Lỗi dòng 144 TableModelInvoiceItem");
		}
	}

	public InvoiceDetail caculator() {
		return this.invoiceDetail.calculate(new DefaultInvoiceCalculator());
	}

	public InvoiceDetail getInvoiceDetail() {
		return this.invoiceDetail;
	}

	@Override
	public int getColumnCount() {
		return header == null ? 0 : header.length;
	}

	@Override
	public String getColumnName(int column) {
		return header[column];
	}

	public void setCanEdit(boolean[] canEdit) {
		this.canEdit = canEdit;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return canEdit[column];
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		if (column == 2) {
			InvoiceItem rowVector = (InvoiceItem) dataVector.elementAt(row);
			rowVector.setQuantity(MyDouble.parseDouble(aValue.toString()));
			rowVector.setTotal(rowVector.getUnitPrice() * rowVector.getQuantity());
			dataVector.set(row, rowVector);
			this.invoiceItem = rowVector;
			fireTableRowsUpdated(row, row);
			if (profile.get("ForRent") != null) {
				InvoiceItem rowVector1 = (InvoiceItem) dataVector.elementAt(row + 1);
				if (rowVector1.getStatus().equals(AccountingModelManager.isForRent)) {
					rowVector1.setQuantity(MyDouble.parseDouble(aValue.toString()));
					rowVector1.setTotal(rowVector1.getUnitPrice() * rowVector1.getQuantity());
					fireTableRowsUpdated(row + 1, row + 1);
				}
			}
			
			tableModelDiscountItem.setData(invoiceDetail);
			changeCaculate("", null);
		} else {
			if (column == 3) {
				InvoiceItem rowVector = (InvoiceItem) dataVector.elementAt(row);
				if (!rowVector.getStatus().equals(AccountingModelManager.isPromotion)) {
					rowVector.setUnitPrice(MyDouble.parseDouble(aValue.toString()));
					rowVector.setTotal(rowVector.getUnitPrice() * rowVector.getQuantity());
				}
				dataVector.set(row, rowVector);

				this.invoiceItem = rowVector;
				loadPromotion();
				fireTableRowsUpdated(row, row);
				//invoiceDetail.calculate(new DefaultInvoiceCalculator());
				tableModelDiscountItem.setData(invoiceDetail);
				changeCaculate("", null);
			} else {
				if (column == 4) {
					InvoiceItem rowVector = (InvoiceItem) dataVector.elementAt(row);
					if (!rowVector.getStatus().equals(AccountingModelManager.isPromotion)) {
						if (MyDouble.parseDouble(aValue.toString()) > 0) {
							rowVector.setDiscountRate(MyDouble.parseDouble(aValue.toString()));
						} else {
							rowVector.setDiscount(0);
							rowVector.setDiscountRate(0);
						}
					}
					dataVector.set(row, rowVector);

					this.invoiceItem = rowVector;
					loadPromotion();
					fireTableRowsUpdated(row, row);
					tableModelDiscountItem.setData(invoiceDetail);
					changeCaculate("", null);
				} else {
					if (column == 6) {
						boolean a = Boolean.parseBoolean(aValue.toString());
						InvoiceItem rowVector = (InvoiceItem) dataVector.elementAt(row);
						this.invoiceItem = rowVector;
						loadPromotion();
						if (a) {
							rowVector.setUnitPrice(0);
							rowVector.setStatus(AccountingModelManager.isPromotion);
						} else {
							try {
								ProductPrice p = ProductModelManager.getInstance().getByProductAndProductPriceType(
								    invoiceItem.getProductCode(), pricingType, invoiceItem.getCurrencyUnit());
								invoiceItem.setUnitPrice(p.getPrice());
								rowVector.setUnitPrice(p.getPrice());
								rowVector.setStatus(AccountingModelManager.isRecord);
							} catch (Exception e) {
								rowVector.setStatus(AccountingModelManager.isRecord);
							}

						}
						changeCaculate(null, "");
						fireTableDataChanged();
						updateIndex();
					} else {
						super.setValueAt(aValue, row, column);
					}
				}
			}
		}
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
			return row + 1;
		case 1:
			try {
				return ((InvoiceItem) dataVector.get(row));
			} catch (Exception e) {
				return "";
			}

		case 2:
			try {
				if (((InvoiceItem) dataVector.get(row)).getStatus().equals(AccountingModelManager.isForRent)) {
					return MyDouble.valueOf(((InvoiceItem) dataVector.get(row - 1)).getQuantity()).toString();
				} else {
					return MyDouble.valueOf(((InvoiceItem) dataVector.get(row)).getQuantity()).toString();
				}

			} catch (Exception e) {
				return "";
			}

		case 3:
			try {
				// if (((InvoiceItem)
				// dataVector.get(row)).getStatus().equals(AccountingModelManager.isForRent))
				// {
				// return MyDouble.valueOf(((InvoiceItem)
				// dataVector.get(row-1)).getUnitPrice()*0.1).toString();
				// } else {
				return MyDouble.valueOf(((InvoiceItem) dataVector.get(row)).getUnitPrice()).toString();
				// }

			} catch (Exception e) {
				return "";
			}

		case 4:
			try {
				return MyDouble.valueOf(((InvoiceItem) dataVector.get(row)).getDiscountRate()).toString();
			} catch (Exception e) {
				return "";
			}

		case 5:
			try {
				// if (((InvoiceItem)
				// dataVector.get(row)).getStatus().equals(AccountingModelManager.isForRent))
				// {
				// return MyDouble.valueOf(
				// (((InvoiceItem) dataVector.get(row-1)).getTotal() - ((InvoiceItem)
				// dataVector.get(row-1)).getDiscount())*0.1)
				// .toString();
				// } else {
				return MyDouble.valueOf(
				    ((InvoiceItem) dataVector.get(row)).getTotal() - ((InvoiceItem) dataVector.get(row)).getDiscount())
				    .toString();
				// }

			} catch (Exception e) {
				return "";
			}
		case 6:
			try {
				return ((InvoiceItem) dataVector.get(row)).getStatus().equals(AccountingModelManager.isPromotion);
			} catch (Exception e) {
				return "";
			}

		default:
			return "";
		}
	}

	@SuppressWarnings({ "unchecked" })
	private void updateIndex() {
		if (profile.get("ForRent") == null) {
			Collections.sort(dataVector, new Comparator<InvoiceItem>() {
				@Override
				public int compare(InvoiceItem it1, InvoiceItem it2) {

					if (it2.getStatus().equals(it1.getStatus())) {
						if (it1.getId() != null && it2.getId() != null) {
							if (it1.getId() < it2.getId()) {
								return 1;
							} else {
								return -1;
							}
						} else {
							return 0;
						}

					}
					if (it2.getStatus().equals(AccountingModelManager.isRecord)
					    && !it1.getStatus().equals(AccountingModelManager.isRecord)) {
						return 1;
					} else {
						if (it2.getStatus().equals(AccountingModelManager.isPromotion)
						    && !it1.getStatus().equals(AccountingModelManager.isRecord)) {
							return 1;
						} else {
							if (it2.getStatus().equals(AccountingModelManager.isKitchen)
							    && !it1.getStatus().equals(AccountingModelManager.isPromotion)
							    && !it1.getStatus().equals(AccountingModelManager.isRecord)) {
								return 1;
							} else {
								if (it2.getStatus().equals(AccountingModelManager.isPayment)
								    && it1.getStatus().equals(AccountingModelManager.isCance)) {
									return 1;
								} else {
									if (it1.getStatus().equals(AccountingModelManager.isCance)
									    && !it2.getStatus().equals(AccountingModelManager.isCance)) {
										return 1;
									} else {
										return -1;
									}
								}
							}

						}
					}
				}
			});
		}
	}

	public void updateStatus(String status) {
		updateStatusItem(status);
		if (!status.equals(AccountingModelManager.isPayment)) {
			changeCaculate(null, "");
		}
		if (profile.get("PaymentSlip") == null || invoiceDetail.getStatus().equals(Status.Paid)) {
			dataVector = convertToVector(invoiceDetail.getItems().toArray());
		} else {
			List<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>();
			for (InvoiceItem invoiceItem : invoiceDetail.getItems()) {
				if (!invoiceItem.getStatus().equals(AccountingModelManager.isPayment)) {
					invoiceItems.add(invoiceItem);
				}
			}
			dataVector = convertToVector(invoiceItems.toArray());
		}
		fireTableDataChanged();
		updateIndex();
	}

	private void updateStatusItem(String status) {
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		for (int i = 0; i < invoiceDetail.getItems().size();) {
			if (!invoiceDetail.getItems().get(i).getItemCode().startsWith("menu")
			    && invoiceDetail.getItems().get(i).getStatus().equals(status)) {
				if (hashMap.get(invoiceDetail.getItems().get(i).getItemName()) != null
						&&!invoiceDetail.getItems().get(i).getProductCode().equals("kara")) {
					int index = hashMap.get(invoiceDetail.getItems().get(i).getItemName());
					invoiceDetail
					    .getItems()
					    .get(index)
					    .setQuantity(
					        invoiceDetail.getItems().get(index).getQuantity() + invoiceDetail.getItems().get(i).getQuantity());
					invoiceDetail
					    .getItems()
					    .get(index)
					    .setTotal(
					        invoiceDetail.getItems().get(index).getQuantity()
					            * invoiceDetail.getItems().get(index).getUnitPrice());
					invoiceDetail.getItems().remove(i);
				} else {
					hashMap.put(invoiceDetail.getItems().get(i).getItemName(), i);
					i++;
				}
			} else {
				i++;
			}
		}
	}

	private void loadPromotionGive(InvoiceItem invoiceItem) throws Exception {
		update = false;
		boolean insert = false;
		InvoiceItemPromotion invoiceItemPromotion = AccountingModelManager.getInstance().getInvoiceItemPromotion(
		    dataVector, invoiceItem.getProductCode());
		PromotionGiveaways promotionGiveaways = PromotionModelManager.getInstance().getPromotionGiveawaysByProductCode(
		    invoiceItem.getProductCode());
		if (promotionGiveaways != null && !promotionGiveaways.isRecycleBin()) {
			double b = promotionGiveaways.getQuantityBuy() + promotionGiveaways.getQuantityGive();
			if (b <= invoiceItemPromotion.getQuantity()) {
				long quantity = MyDouble.valueOf(invoiceItemPromotion.getQuantity() / b).longValue();
				double q = quantity * promotionGiveaways.getQuantityGive();
				PromotionGiveUsed p = PromotionModelManager.getInstance().getPromotionByInvoiceItems(
				    invoiceItemPromotion.getInvoiceItems());
				if (p == null) {
					p = new PromotionGiveUsed();
					p.setUsedDate(new Date());
					p.setInvoiceCode(invoiceDetail.getInvoiceCode());
					p.setInvoiceItemCode(invoiceItem.getItemCode());
					p.setQuantity(0);
					p.setProductName(invoiceItem.getItemName());
					p.setPrice(invoiceItem.getUnitPrice());
					p.setInvoiceId(invoiceDetail.getId());
					p.setPromotionGiveawaysId(promotionGiveaways.getId());
					p.setProductCode(invoiceItem.getProductCode());
				}
				if (p.getQuantity() != q) {
					double sl = q - p.getQuantity();
					p.setQuantity(q);
					p = PromotionModelManager.getInstance().savePromotionGiveUsed(p);
					InvoiceItem bean = AccountingModelManager.getInstance().getInvoiceItem(invoiceDetail,
					    invoiceItem.getProductCode(), AccountingModelManager.isPromotion);
					if (bean == null) {
						bean = new InvoiceItem();
						bean.setItemName(invoiceItem.getItemName());
						bean.setItemCode(dfCode.format(new Date()));
						bean.setUnitPrice(0);
						bean.setTotal(0);
						bean.setCurrencyUnit(invoiceItem.getCurrencyUnit());
						bean.setActivityType(invoiceItem.getActivityType());
						bean.setStartDate(invoiceItem.getStartDate());
						bean.setCurrencyRate(invoiceItem.getCurrencyRate());
						bean.setProductCode(invoiceItem.getProductCode());
						bean.setStatus(AccountingModelManager.isPromotion);
						invoiceDetail.add(bean);
						insert = true;
					}
					bean.setQuantity(q);
					if (invoiceItem.getQuantity() > sl) {
						invoiceItem.setQuantity(invoiceItem.getQuantity() - sl);
					} else {
						sl = sl - invoiceItem.getQuantity();
						invoiceDetail.remove(invoiceItem);
						for (InvoiceItem it : invoiceItemPromotion.getInvoiceItems()) {
							if (!it.getStatus().equals(AccountingModelManager.isPromotion) && it.getId() != invoiceItem.getId()) {
								if (sl > 0) {
									if (it.getQuantity() > sl) {
										it.setQuantity(it.getQuantity() - sl);
									} else {
										sl = sl - it.getQuantity();
										invoiceDetail.remove(it);
									}
								}
							}
						}
					}
					if (insert) {
						int row = getRowCount();
						dataVector.insertElementAt(bean, row);
						fireTableRowsInserted(row, row);
					} else {
						fireTableDataChanged();
					}
				}
			} else {
				PromotionGiveUsed p = PromotionModelManager.getInstance().getPromotionByInvoiceItems(
				    invoiceItemPromotion.getInvoiceItems());
				if (p != null) {
					PromotionModelManager.getInstance().deletePromotionGiveUsed(p);
				}
			}
		}
		update = true;
	}

}
