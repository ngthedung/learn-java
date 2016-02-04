package com.hkt.swingexp.app.report.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.jfree.data.general.DefaultPieDataset;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.AccountingServiceClient;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.hethong.Processing;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.swingexp.app.report.entity.InventoryReportEntity;
import com.hkt.swingexp.app.report.entity.PanelTableCell;
import com.hkt.swingexp.app.report.entity.ReportRevenue;
import com.hkt.swingexp.app.report.modeltable.TableModelReportInventory;

/**
 * @author: Phan Anh
 * @dateCreate: 26/01/2015
 * @LogError: 03/07/2015: Sửa lỗi hiển thị sai kho bỏ tích hiển thị tất cả
 * @LogAdd: 10/07/2015: Thêm điều kiện bỏ chọn thống kê SP bỏ tích
 */

public class TableRerenderInventory extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
	private PanelTableCell cell;
	private Processing processing;
	private TableModelReportInventory model;
	private String valueMoney;
	private Date startDate;
	private Date endDate;
	private String warehouse;
	private boolean showAll;
	private double valueQuantity = 1;
	private ImageIcon nodeIconAdd, nodeImageMulti, nodeIconProduct;
	private static ClientContext clientContext = ClientContext.getInstance();
	private static AccountingServiceClient accountingServiceClient = clientContext.getBean(AccountingServiceClient.class);
	private HashMap<String, InventoryReportEntity> rowH;

	public TableRerenderInventory(TableModelReportInventory model1, Date startDate, Date endDate, String valueMoney,
	    String warehouse, boolean showAll,HashMap<String, InventoryReportEntity> rowH) {
		this.rowH = rowH;
		nodeIconAdd = new ImageIcon(HomeScreen.class.getResource("icon/square_add_16.png"));
		nodeImageMulti = new ImageIcon(HomeScreen.class.getResource("icon/square_multi_16.png"));
		nodeIconProduct = new ImageIcon(HomeScreen.class.getResource("icon/square_product_16.png"));
		hashProduct = new HashMap<String, String>();
		this.model = model1;
		this.valueMoney = valueMoney;
		this.warehouse = warehouse;
		this.startDate = startDate;
		this.endDate = endDate;
		this.showAll = showAll;

		this.cell = new PanelTableCell();
		this.processing = new Processing();
	}

	public void mouseClick_CellTable() {
		Object parent = cell.getObject();
		if (parent instanceof InventoryReportEntity) {
			// ---------------START RUN TIME QUERY----------------
			long startTime = System.currentTimeMillis();
			// ---------------------------------------------------

			int index = 0;
			String tag = null;
			if (cell.getRow() < model.getRowCount() - 1) {
				index = ((InventoryReportEntity) model.getValueAt(cell.getRow() + 1, 0)).getIndex();
				tag = ((InventoryReportEntity) model.getValueAt(cell.getRow() + 1, 0)).getParent();
			}
			if (((InventoryReportEntity) parent).getType() == InventoryReportEntity.ORGANIZATION) {
				try {
					InventoryReportEntity inventoryEntity = (InventoryReportEntity) parent;
					if (index <= inventoryEntity.getIndex()) {
						int i = cell.getRow() + 1;
						if (inventoryEntity.getListChild() == null) {
							List<InventoryReportEntity> reportRestaurantEntities = new ArrayList<InventoryReportEntity>();
							reportRestaurantEntities.addAll(getProductTags(tag, index)); // Add
							if (!showAll) {
								for (int ep = 0; ep < reportRestaurantEntities.size();) {
									if (!reportRestaurantEntities.get(ep).isNotRowZero()) {
										reportRestaurantEntities.remove(ep);
									} else
										ep++;
								}
							}
							inventoryEntity.setListChild(reportRestaurantEntities);
						}
						model.addRow(inventoryEntity.getListChild(), i);
						inventoryEntity.getLabelIcon().setIcon(nodeImageMulti);
						inventoryEntity.setWiden(true);
					} else {
						model.removeRowReport(cell.getRow());
						inventoryEntity.getLabelIcon().setIcon(nodeIconAdd);
						inventoryEntity.setWiden(false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (((InventoryReportEntity) parent).getType() == InventoryReportEntity.PRODUCT_TAG) {
				try {
					InventoryReportEntity inventoryEntity = (InventoryReportEntity) parent;
					if (index <= inventoryEntity.getIndex()) {
						int i = cell.getRow() + 1;
						if (inventoryEntity.getListChild() == null) {
							List<InventoryReportEntity> inventoryReportEntities = new ArrayList<InventoryReportEntity>();
							inventoryReportEntities.addAll(getProductTags(inventoryEntity.getParent(), inventoryEntity.getIndex())); // Add
							if (!showAll) {
								for (int ep = 0; ep < inventoryReportEntities.size();) {
									if (!inventoryReportEntities.get(ep).isNotRowZero()) {
										inventoryReportEntities.remove(ep);
									} else
										ep++;
								}
							}
							inventoryEntity.setListChild(inventoryReportEntities);
						}
						model.addRow(inventoryEntity.getListChild(), i);
						inventoryEntity.getLabelIcon().setIcon(nodeImageMulti);
						inventoryEntity.setWiden(true);
					} else {
						model.removeRowReport(cell.getRow());
						inventoryEntity.getLabelIcon().setIcon(nodeIconAdd);
						inventoryEntity.setWiden(false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// ---------------RETURN TIME EXECUTE QUERY------------------
			long endTime = System.currentTimeMillis();
			NumberFormat formatter = new DecimalFormat("#0.00000");
			String time = formatter.format((endTime - startTime) / 1000d);
			System.out.println("###$$$ TIME RUN QUERY: " + time);
			// -----------------------------------------------------------
		}
	}
	private HashMap<String, String> hashProduct;

	private List<String> getProductCodeByTag(String tag) {
		List<String> list = new ArrayList<String>();
		try {
			SQLSelectQuery rQuery = new SQLSelectQuery();
			rQuery
			    .table(
			        "Product i JOIN product_producttag p JOIN warehouse_producttag w ON p.productId = i.id "
			            + "and w.id = p.productTagId").field("i.code AS `productCode`", "value")
			    .field("i.name AS `productName`", "value1").cond("w.tag like '" + tag + "%'");
			ReportTable[] reportTable = accountingServiceClient.reportQuery(new SQLSelectQuery[] { rQuery });
			List<Map<String, Object>> records = reportTable[0].getRecords();
			for (int i = 0; i < records.size(); i++) {
				Map<String, Object> record = records.get(i);
				list.add(record.get("value").toString());
				hashProduct.put(record.get("value").toString(), record.get("value1").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	private List<String> getProductCodeByTag1(String tag) {
		List<String> list = new ArrayList<String>();
		try {
			SQLSelectQuery rQuery = new SQLSelectQuery();
			rQuery
			    .table(
			        "Product i JOIN product_producttag p JOIN warehouse_producttag w ON p.productId = i.id "
			            + "and w.id = p.productTagId").field("i.code AS `productCode`", "value")
			    .cond("w.tag = '" + tag + "'");
			ReportTable[] reportTable = accountingServiceClient.reportQuery(new SQLSelectQuery[] { rQuery });
			List<Map<String, Object>> records = reportTable[0].getRecords();
			for (int i = 0; i < records.size(); i++) {
				Map<String, Object> record = records.get(i);
				list.add(record.get("value").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	private List<InventoryReportEntity> getProductTags(String tag, int index) throws Exception {
		System.out.println(rowH.size());
		Iterator<InventoryReportEntity> in = rowH.values().iterator();
		List<ProductTag> tags = ProductModelManager.getInstance().getRootTags();
		if (tag != null) {
			tags = ProductModelManager.getInstance().getChildProductTag(tag);
		}
		List<String> productCodes = getProductCodeByTag1(tag);
		if ((tags == null || tags.isEmpty()) && (productCodes == null || productCodes.isEmpty())) {
			return new ArrayList<InventoryReportEntity>();
		}
		List<InventoryReportEntity> rows = new ArrayList<InventoryReportEntity>();
		HashMap<String, InventoryReportEntity> row_final = new HashMap<String, InventoryReportEntity>();
		HashMap<String, List<String>> hashTag = new HashMap<String, List<String>>();
		for (ProductTag productTag : tags) {
			List<String> listCode = getProductCodeByTag(productTag.getTag());
			hashTag.put(productTag.getTag(), listCode);
		}
		long startTime1 = System.currentTimeMillis();
		while (in.hasNext()) {
			InventoryReportEntity i = in.next();
			// double price = 0;
			double SLDauKy = MyDouble.parseDouble(i.getCol2_SLDauKy());
			double GTDauKy = MyDouble.parseDouble(i.getCol3_GTDauKy());
			// if (GTDauKy < 0) {
			// GTDauKy = GTDauKy * (-1);
			// }
			double SLTang = MyDouble.parseDouble(i.getCol4_SLTang());
			double GTTang = MyDouble.parseDouble(i.getCol5_GTTang());
			double SLGiam = MyDouble.parseDouble(i.getCol6_SLGiam());
			double GTGiam = MyDouble.parseDouble(i.getCol7_GTGiam());
			double SLConLai = MyDouble.parseDouble(i.getSLConLai());
			double GTConLai = MyDouble.parseDouble(i.getGTConLai());
			for (ProductTag productTag : tags) {
				InventoryReportEntity row;
				if (hashTag.get(productTag.getTag()).indexOf(i.getProductCode()) >= 0) {
					if (row_final.containsKey(productTag.getTag())) {
						row = row_final.get(productTag.getTag());
						row.setCol2_SLDauKy(new MyDouble(MyDouble.parseDouble(row.getCol2_SLDauKy()) + SLDauKy).toString());
						row.setCol3_GTDauKy(new MyDouble(MyDouble.parseDouble(row.getCol3_GTDauKy()) + GTDauKy).toString());
						row.setCol4_SLTang(new MyDouble(MyDouble.parseDouble(row.getCol4_SLTang()) + SLTang).toString());
						row.setCol5_GTTang(new MyDouble(MyDouble.parseDouble(row.getCol5_GTTang()) + GTTang).toString());
						row.setCol6_SLGiam(new MyDouble(MyDouble.parseDouble(row.getCol6_SLGiam()) + SLGiam).toString());
						row.setCol7_GTGiam(new MyDouble(MyDouble.parseDouble(row.getCol7_GTGiam()) + GTGiam).toString());
						row.setSLConLai(new MyDouble(MyDouble.parseDouble(row.getSLConLai()) + SLConLai).toString());
						row.setGTConLai(new MyDouble(MyDouble.parseDouble(row.getGTConLai()) + GTConLai).toString());
					} else {
						row = new InventoryReportEntity(productTag.getLabel(), Double.toString(SLDauKy),
						    new MyDouble(GTDauKy).toString(), Double.toString(SLTang), new MyDouble(GTTang).toString(),
						    Double.toString(SLGiam), new MyDouble(GTGiam).toString(), Double.toString(SLConLai), new MyDouble(
						        GTConLai).toString());
						row_final.put(productTag.getTag(), row);
						row.setType(InventoryReportEntity.PRODUCT_TAG);
						row.setParent(productTag.getTag());
						row.setIndex(index + 1);
						rows.add(row);
					}

				}
			}
			if (productCodes != null && !productCodes.isEmpty()) {
				if (productCodes.indexOf(i.getProductCode()) >= 0) {
					InventoryReportEntity row = new InventoryReportEntity(hashProduct.get(i.getProductCode()),
					    Double.toString(SLDauKy), new MyDouble(GTDauKy).toString(), Double.toString(SLTang),
					    new MyDouble(GTTang).toString(), Double.toString(SLGiam), new MyDouble(GTGiam).toString(),
					    Double.toString(SLConLai), new MyDouble(GTConLai).toString());
					row.setType(InventoryReportEntity.PRODUCT);
					row.setParent(tag + (tag != null ? ":" : "") + i.getParent());
					row.setIndex(index + 1);
					rows.add(row);
				}
			}
		}
		long endTime = System.currentTimeMillis();
		NumberFormat formatter = new DecimalFormat("#0.00000");
		String time = formatter.format((endTime - startTime1) / 1000d);
		System.out.println("TOng thoi gian chay : " + time);
		Collections.sort(rows, new Comparator<InventoryReportEntity>() {
			@Override
			public int compare(InventoryReportEntity object1, InventoryReportEntity object2) {
				return object1.getCol1_DanhMuc().compareTo(object2.getCol1_DanhMuc());
			}
		});
		return rows;
	}

	private List<InventoryReportEntity> runQuery(SQLSelectQuery rQuery) throws Exception {
		List<InventoryReportEntity> listObjects = new ArrayList<InventoryReportEntity>();
		System.out.println(rQuery.createSQLQuery());
		ReportTable[] reportTable = accountingServiceClient.reportQuery(new SQLSelectQuery[] { rQuery });
		String[] field = new String[rQuery.getFields().size()];
		for (int i = 0; i < rQuery.getFields().size(); i++) {
			field[i] = "value" + i;
		}
		reportTable[0].dump(field);
		List<Map<String, Object>> records = reportTable[0].getRecords();
		for (int i = 0; i < records.size(); i++) {
			Map<String, Object> record = records.get(i);
			Object[] values = new Object[field.length];
			for (int j = 0; j < field.length; j++) {
				values[j] = record.get(field[j]);
			}
			InventoryReportEntity object = new InventoryReportEntity(values[1].toString(), values[2].toString(),
			    values[3].toString(), "", "", "", "", "", "");
			object.setParent(values[0].toString());
			listObjects.add(object);
		}
		return listObjects;
	}

	@Override
	public Object getCellEditorValue() {
		return cell.getObject();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
	    int row, int column) {
		JLabel labelIcon = new JLabel(" ");
		cell.setRow(row);
		cell.setIcon(labelIcon);
		String result = " ";
		if (column == 0) {
			((InventoryReportEntity) value).setLabelIcon(labelIcon);
			try {
				cell.setObject(value);
				if (((InventoryReportEntity) value).isWiden())
					labelIcon.setIcon(nodeImageMulti);
				else
					labelIcon.setIcon(nodeIconAdd);

				if (((InventoryReportEntity) value).getType() == InventoryReportEntity.PRODUCT_TAG) {
					String tag = ((InventoryReportEntity) value).getParent();
					String tagSplit[] = tag.split(":");
					for (int i = 0; i < tagSplit.length; i++) {
						result = result + "      ";
					}
					labelIcon.setText(result);
					cell.getText().setText(value.toString());
					((InventoryReportEntity) value).setColor(Color.black);
				} else {
					if (((InventoryReportEntity) value).getType() == InventoryReportEntity.PRODUCT) {
						valueQuantity = MyDouble.parseDouble(((InventoryReportEntity) value).getCol10_SLHienTai());
						if (valueQuantity < 0) {
							((InventoryReportEntity) value).setColor(new Color(255, 0, 0)); // Màu
							                                                                // đỏ
						} else {
							if (valueQuantity == 0) {
								((InventoryReportEntity) value).setColor(new Color(128, 100, 162)); // Màu
								                                                                    // tím
							} else {
								if (valueQuantity <= 3) {
									((InventoryReportEntity) value).setColor(new Color(0, 176, 80)); // Màu
									                                                                 // xanh
									                                                                 // lá
								} else {
									((InventoryReportEntity) value).setColor(Color.black);
								}
							}
						}

						String tag = ((InventoryReportEntity) value).getParent();
						String tagSplit[] = tag.split(":");
						for (int i = 0; i < tagSplit.length; i++) {
							result = result + "      ";
						}
						labelIcon.setText(result);
						labelIcon.setIcon(nodeIconProduct);
						cell.getText().setText(value.toString());
					} else {
						((InventoryReportEntity) value).setColor(Color.black);
						cell.getText().setText(value.toString());
					}
				}
				cell.getText().setForeground(((InventoryReportEntity) value).getColor());
			} catch (Exception e) {
				e.printStackTrace();
				cell.getText().setText("");
			}
		} else {
			cell.getText().setHorizontalAlignment(JLabel.RIGHT);
			cell.getText().setText(value.toString());
			cell.getText().setForeground(((InventoryReportEntity) table.getValueAt(row, 0)).getColor());
		}

		if (table.getSelectedRow() == row) {
			cell.getText().setFont(new Font(cell.getFont().getFontName(), Font.BOLD, 13));
		} else {
			cell.getText().setFont(new Font(cell.getFont().getFontName(), Font.PLAIN, 14));
		}

		return cell;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		boolean isProduct = false;
		JLabel labelIcon = ((InventoryReportEntity) value).getLabelIcon();
		cell.getText().setFont(new Font(cell.getFont().getFontName(), Font.BOLD, 13));
		cell.setRow(row);
		if (column == 0) {
			final Object c = value;
			try {
				cell.setObject(value);
				if ((((InventoryReportEntity) value).getType() == InventoryReportEntity.PRODUCT)) {
					isProduct = true;
					valueQuantity = MyDouble.parseDouble(((InventoryReportEntity) value).getCol10_SLHienTai());
				}
				cell.getText().setText(value.toString());

				processing.setVisible(true);
				new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {
						mouseClick_CellTable();
						return null;
					};

					@Override
					protected void done() {
						processing.setVisible(false);
						List<InventoryReportEntity> list = ((InventoryReportEntity) c).getListChild();
						DefaultPieDataset dataset = new DefaultPieDataset();
						String type = model.getReportStatistics().getColumnViewPie().toString();
						if (type.equals("SL đầu kỳ")) {
							for (InventoryReportEntity r : list) {
								Double d = (new MyDouble(r.getCol2_SLDauKy()).doubleValue()
								    / new MyDouble(((InventoryReportEntity) c).getCol2_SLDauKy()).doubleValue() + new MyDouble(
								    r.getCol2_SLDauKy()).doubleValue()
								    % new MyDouble(((InventoryReportEntity) c).getCol2_SLDauKy()).doubleValue()) * 100;
								dataset.setValue(r.getCol1_DanhMuc(), d);
							}
						} else {
							if (type.equals("GT đầu kỳ")) {
								for (InventoryReportEntity r : list) {
									Double d = (new MyDouble(r.getCol3_GTDauKy()).doubleValue()
									    / new MyDouble(((InventoryReportEntity) c).getCol3_GTDauKy()).doubleValue() + new MyDouble(
									    r.getCol3_GTDauKy()).doubleValue()
									    % new MyDouble(((InventoryReportEntity) c).getCol3_GTDauKy()).doubleValue()) * 100;
									dataset.setValue(r.getCol1_DanhMuc(), d);
								}
							} else {
								if (type.equals("SL tăng")) {
									for (InventoryReportEntity r : list) {
										Double d = (new MyDouble(r.getCol4_SLTang()).doubleValue()
										    / new MyDouble(((InventoryReportEntity) c).getCol4_SLTang()).doubleValue() + new MyDouble(
										    r.getCol4_SLTang()).doubleValue()
										    % new MyDouble(((InventoryReportEntity) c).getCol4_SLTang()).doubleValue()) * 100;
										dataset.setValue(r.getCol1_DanhMuc(), d);
									}
								} else {
									if (type.equals("GT tăng")) {
										for (InventoryReportEntity r : list) {
											Double d = (new MyDouble(r.getCol5_GTTang()).doubleValue()
											    / new MyDouble(((InventoryReportEntity) c).getCol5_GTTang()).doubleValue() + new MyDouble(
											    r.getCol5_GTTang()).doubleValue()
											    % new MyDouble(((InventoryReportEntity) c).getCol5_GTTang()).doubleValue()) * 100;
											dataset.setValue(r.getCol1_DanhMuc(), d);
										}
									} else {
										if (type.equals("SL giảm")) {
											for (InventoryReportEntity r : list) {
												Double d = (new MyDouble(r.getCol6_SLGiam()).doubleValue()
												    / new MyDouble(((InventoryReportEntity) c).getCol6_SLGiam()).doubleValue() + new MyDouble(
												    r.getCol6_SLGiam()).doubleValue()
												    % new MyDouble(((InventoryReportEntity) c).getCol6_SLGiam()).doubleValue()) * 100;
												dataset.setValue(r.getCol1_DanhMuc(), d);
											}
										} else {
											if (type.equals("GT giảm")) {
												for (InventoryReportEntity r : list) {
													Double d = (new MyDouble(r.getCol7_GTGiam()).doubleValue()
													    / new MyDouble(((InventoryReportEntity) c).getCol7_GTGiam()).doubleValue() + new MyDouble(
													    r.getCol7_GTGiam()).doubleValue()
													    % new MyDouble(((InventoryReportEntity) c).getCol7_GTGiam()).doubleValue()) * 100;
													dataset.setValue(r.getCol1_DanhMuc(), d);
												}
											} else {
												if (type.equals("SL cuối kỳ")) {
													for (InventoryReportEntity r : list) {
														Double d = (new MyDouble(r.getCol8_SLCuoiKy()).doubleValue()
														    / new MyDouble(((InventoryReportEntity) c).getCol8_SLCuoiKy()).doubleValue() + new MyDouble(
														    r.getCol8_SLCuoiKy()).doubleValue()
														    % new MyDouble(((InventoryReportEntity) c).getCol8_SLCuoiKy()).doubleValue()) * 100;
														dataset.setValue(r.getCol1_DanhMuc(), d);
													}
												} else {
													if (type.equals("GT cuối kỳ")) {
														for (InventoryReportEntity r : list) {
															Double d = (new MyDouble(r.getCol9_GTCuoiKy()).doubleValue()
															    / new MyDouble(((InventoryReportEntity) c).getCol9_GTCuoiKy()).doubleValue() + new MyDouble(
															    r.getCol9_GTCuoiKy()).doubleValue()
															    % new MyDouble(((InventoryReportEntity) c).getCol9_GTCuoiKy()).doubleValue()) * 100;
															dataset.setValue(r.getCol1_DanhMuc(), d);
														}
													} else {
														if (type.equals("SL hiện tại")) {
															for (InventoryReportEntity r : list) {
																Double d = (new MyDouble(r.getCol10_SLHienTai()).doubleValue()
																    / new MyDouble(((InventoryReportEntity) c).getCol10_SLHienTai()).doubleValue() + new MyDouble(
																    r.getCol10_SLHienTai()).doubleValue()
																    % new MyDouble(((InventoryReportEntity) c).getCol10_SLHienTai()).doubleValue()) * 100;
																dataset.setValue(r.getCol1_DanhMuc(), d);
															}
														} else {
															if (type.equals("GT hiện tại")) {
																for (InventoryReportEntity r : list) {
																	Double d = (new MyDouble(r.getCol11_GTHienTai()).doubleValue()
																	    / new MyDouble(((InventoryReportEntity) c).getCol11_GTHienTai()).doubleValue() + new MyDouble(
																	    r.getCol11_GTHienTai()).doubleValue()
																	    % new MyDouble(((InventoryReportEntity) c).getCol11_GTHienTai()).doubleValue()) * 100;
																	dataset.setValue(r.getCol1_DanhMuc(), d);
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
						model.getReportStatistics().viewChart(dataset);
					};
				}.execute();

			} catch (Exception e) {
				// e.printStackTrace();
				cell.getText().setText("");
			}
		} else {
			cell.getText().setHorizontalAlignment(JLabel.RIGHT);
			cell.getText().setText(value.toString());
		}
		cell.setIcon(labelIcon);

		if (isProduct) {
			if (valueQuantity < 0) {
				((InventoryReportEntity) value).setColor(new Color(255, 0, 0)); // Màu
				                                                                // đỏ
			} else {
				if (valueQuantity == 0) {
					((InventoryReportEntity) value).setColor(new Color(128, 100, 162)); // Màu
					                                                                    // tím
				} else {
					if (valueQuantity <= 3) {
						((InventoryReportEntity) value).setColor(new Color(0, 176, 80)); // Màu
						                                                                 // xanh
						                                                                 // lá
					} else {
						((InventoryReportEntity) value).setColor(Color.black);
					}
				}
			}
		} else {
			((InventoryReportEntity) value).setColor(Color.black);
		}
		cell.getText().setForeground(((InventoryReportEntity) value).getColor());

		return cell;
	}
}
