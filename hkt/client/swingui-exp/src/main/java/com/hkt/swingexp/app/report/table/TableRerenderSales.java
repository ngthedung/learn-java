package com.hkt.swingexp.app.report.table;

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

import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.hethong.Processing;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.swingexp.app.report.entity.ReportSalesEntity;
import com.hkt.swingexp.app.report.entity.PanelTableCell;
import com.hkt.swingexp.app.report.entity.ReportRevenue;
import com.hkt.swingexp.app.report.entity.ReportSalesEntity;
import com.hkt.swingexp.app.report.modeltable.TableModelReportSales;

/**
 * @edit: Phan Anh
 * @date: 11/07/2014
 * @edit: 3/4/2015
 */

public class TableRerenderSales extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
	private PanelTableCell cell;
	private Processing processing;
	private TableModelReportSales model;
	private ImageIcon nodeIconAdd, nodeImageMulti, nodeIconProduct;
	private boolean showAll;
	private List<ReportSalesEntity> salesEntityRemoveZero;
	private boolean a = false;
	private HashMap<String, ReportSalesEntity> rowH;

	public TableRerenderSales(TableModelReportSales model, Date startDate, Date endDate, String valueMoney,
	    String warehouse, ActivityType activityType, boolean showAll, HashMap<String, ReportSalesEntity> rowH) {
		nodeIconAdd = new ImageIcon(HomeScreen.class.getResource("icon/square_add_16.png"));
		nodeImageMulti = new ImageIcon(HomeScreen.class.getResource("icon/square_multi_16.png"));
		nodeIconProduct = new ImageIcon(HomeScreen.class.getResource("icon/square_product_16.png"));
		this.rowH = rowH;
		this.model = model;
		this.showAll = showAll;
		hashProduct = new HashMap<String, String>();
		this.cell = new PanelTableCell();
		this.processing = new Processing();
	}

	public void mouseClick_CellTable() {
		Object parent = cell.getObject();
		if (parent instanceof ReportSalesEntity) {
			// ---------------START RUN TIME QUERY----------------
			long startTime = System.currentTimeMillis();
			// ---------------------------------------------------

			int index = 0;
			if (cell.getRow() < model.getRowCount() - 1) {
				index = ((ReportSalesEntity) model.getValueAt(cell.getRow() + 1, 0)).getIndex();
			}
			if (((ReportSalesEntity) parent).getType() == ReportSalesEntity.ORGANIZATION) {
				try {
					ReportSalesEntity salesEntity = (ReportSalesEntity) parent;
					if (!a) {
						int i = cell.getRow() + 1;
						if (salesEntity.getListChild() == null) {
							List<ReportSalesEntity> reportRestaurantEntities = new ArrayList<ReportSalesEntity>();
							reportRestaurantEntities = getParentGroup(salesEntity.getPath(), i);
							salesEntity.setListChild(reportRestaurantEntities);
						}
						model.addRowChild(salesEntity.getListChild(), i);
						salesEntity.getLabelIcon().setIcon(nodeImageMulti);
						salesEntity.setWiden(true);
						a = true;
					} else {
						model.removeRowReport(cell.getRow());
						salesEntity.getLabelIcon().setIcon(nodeIconAdd);
						salesEntity.setWiden(false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			if (((ReportSalesEntity) parent).getType() == ReportSalesEntity.PRODUCT_TAG) {
				try {
					ReportSalesEntity dept = (ReportSalesEntity) parent;
					if (index <= dept.getIndex()) {
						int i = cell.getRow() + 1;
						if (dept.getListChild() == null) {
							List<ReportSalesEntity> reportRestaurantEntities = new ArrayList<ReportSalesEntity>();
							reportRestaurantEntities.addAll(getParentGroup(dept.getPath(), i));
							List<ReportSalesEntity> productsByTag = new ArrayList<ReportSalesEntity>();
							if (!showAll) {
								salesEntityRemoveZero = new ArrayList<ReportSalesEntity>();
								for (int re = 0; re < productsByTag.size(); re++) {
									if (MyDouble.parseDouble(productsByTag.get(re).getTotalPrice()) != 0.0
									    || MyDouble.parseDouble(productsByTag.get(re).getQuantity()) != 0.0) {
										salesEntityRemoveZero.add(productsByTag.get(re));
									}
								}
								reportRestaurantEntities.addAll(salesEntityRemoveZero);
							} else
								reportRestaurantEntities.addAll(productsByTag);
							model.addRowChild(reportRestaurantEntities, i);
							dept.setListChild(reportRestaurantEntities);
						} else {
							model.addRowChild(dept.getListChild(), i);
						}
						dept.getLabelIcon().setIcon(nodeImageMulti);
						dept.setWiden(true);
					} else {
						model.removeRowReport(cell.getRow());
						dept.getLabelIcon().setIcon(nodeIconAdd);
						dept.setWiden(false);
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
			// ----------------------------------------------------------
		}
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
			((ReportSalesEntity) value).setLabelIcon(labelIcon);
			try {
				cell.setObject(value);
				if (((ReportSalesEntity) value).isWiden())
					labelIcon.setIcon(nodeImageMulti);
				else
					labelIcon.setIcon(nodeIconAdd);

				if (((ReportSalesEntity) value).getType() == ReportSalesEntity.PRODUCT_TAG) {
					String tag = ((ReportSalesEntity) value).getPath();
					String tagSplit[] = tag.split(":");
					for (int i = 0; i < tagSplit.length; i++) {
						result = result + "      ";
					}
					labelIcon.setText(result);
					cell.getText().setText(value.toString());
				} else {
					if (((ReportSalesEntity) value).getType() == ReportSalesEntity.PRODUCT) {
						String tag = ((ReportSalesEntity) value).getPath();
						String tagSplit[] = tag.split(":");
						for (int i = 0; i < tagSplit.length; i++) {
							result = result + "      ";
						}
						labelIcon.setText(result);
						labelIcon.setIcon(nodeIconProduct);
						cell.getText().setText(value.toString());
					} else {
						cell.getText().setText(value.toString());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				cell.getText().setText("");
			}
		} else {
			cell.getText().setHorizontalAlignment(JLabel.RIGHT);
			cell.getText().setText(value.toString());
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
		JLabel labelIcon = ((ReportSalesEntity) value).getLabelIcon();
		cell.getText().setFont(new Font(cell.getFont().getFontName(), Font.BOLD, 13));
		cell.setRow(row);
		final Object c = value;
		if (column == 0) {
			try {
				cell.setObject(value);
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
						List<ReportSalesEntity> list = ((ReportSalesEntity) c).getListChild();
						if (list == null) {
							list = new ArrayList<ReportSalesEntity>();
						}
						DefaultPieDataset dataset = new DefaultPieDataset();
						String type = model.getReportStatistics().getColumnViewPie().toString();
						if (type.equals("Số lượng")) {
							for (ReportSalesEntity r : list) {
								Double d = (new MyDouble(r.getQuantity()).doubleValue()
								    / new MyDouble(((ReportSalesEntity) c).getQuantity()).doubleValue() + new MyDouble(r.getQuantity())
								    .doubleValue() % new MyDouble(((ReportSalesEntity) c).getQuantity()).doubleValue()) * 100;
								dataset.setValue(r.getName(), d);
							}
						} else {
							if (type.equals("Đơn giá TB")) {
								for (ReportSalesEntity r : list) {
									Double d = (new MyDouble(r.getPriceAverage()).doubleValue()
									    / new MyDouble(((ReportSalesEntity) c).getPriceAverage()).doubleValue() + new MyDouble(
									    r.getPriceAverage()).doubleValue()
									    % new MyDouble(((ReportSalesEntity) c).getPriceAverage()).doubleValue()) * 100;
									dataset.setValue(r.getName(), d);
								}
							} else {
								if (type.equals("Tổng thu") || type.equals("Tổng chi")) {
									for (ReportSalesEntity r : list) {
										Double d = (new MyDouble(r.getTotalPrice()).doubleValue()
										    / new MyDouble(((ReportSalesEntity) c).getTotalPrice()).doubleValue() + new MyDouble(
										    r.getTotalPrice()).doubleValue()
										    % new MyDouble(((ReportSalesEntity) c).getTotalPrice()).doubleValue()) * 100;
										dataset.setValue(r.getName(), d);
									}
								}
							}
						}
						model.getReportStatistics().viewChart(dataset);
					};
				}.execute();

			} catch (Exception e) {
				e.printStackTrace();
				cell.getText().setText("");
			}
		} else {
			cell.getText().setHorizontalAlignment(JLabel.RIGHT);
			cell.getText().setText(value.toString());
		}
		cell.setIcon(labelIcon);
		return cell;
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
			ReportTable[] reportTable = AccountingModelManager.getInstance().reportQuery(new SQLSelectQuery[] { rQuery });
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
			ReportTable[] reportTable = AccountingModelManager.getInstance().reportQuery(new SQLSelectQuery[] { rQuery });
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

	private List<ReportSalesEntity> getParentGroup(String tag, int index) throws Exception {
		System.out.println(rowH.size());
		Iterator<ReportSalesEntity> in = rowH.values().iterator();
		List<ProductTag> tags = ProductModelManager.getInstance().getRootTags();
		if (tag != null) {
			tags = ProductModelManager.getInstance().getChildProductTag(tag);
		}
		List<String> productCodes = getProductCodeByTag1(tag);
		if ((tags == null || tags.isEmpty()) && (productCodes == null || productCodes.isEmpty())) {
			return new ArrayList<ReportSalesEntity>();
		}
		List<ReportSalesEntity> rows = new ArrayList<ReportSalesEntity>();
		HashMap<String, ReportSalesEntity> row_final = new HashMap<String, ReportSalesEntity>();
		HashMap<String, List<String>> hashTag = new HashMap<String, List<String>>();
		for (ProductTag productTag : tags) {
			List<String> listCode = getProductCodeByTag(productTag.getTag());
			hashTag.put(productTag.getTag(), listCode);
		}
		long startTime1 = System.currentTimeMillis();
		while (in.hasNext()) {
			ReportSalesEntity i = in.next();
			double SLTang = MyDouble.parseDouble(i.getQuantity());
			double GTT = MyDouble.parseDouble(i.getTotalPrice());
			for (ProductTag productTag : tags) {
				ReportSalesEntity row;
				if (hashTag.get(productTag.getTag()).indexOf(i.getProductCode()) >= 0) {
					if (row_final.containsKey(productTag.getTag())) {
						row = row_final.get(productTag.getTag());
						row.setQuantity(new MyDouble(MyDouble.parseDouble(row.getQuantity()) + SLTang).toString());
						row.setTotalPrice(new MyDouble(MyDouble.parseDouble(row.getTotalPrice()) + GTT).toString());
					} else {
						row = new ReportSalesEntity(productTag.getLabel(), "", new MyDouble(SLTang).toString(),
						    new MyDouble(GTT).toString(), index + 1);
						row_final.put(productTag.getTag(), row);
						row.setType(ReportSalesEntity.PRODUCT_TAG);
						row.setPath(productTag.getTag());
						rows.add(row);
					}

				}
			}
			if (productCodes != null && !productCodes.isEmpty()) {
				if (productCodes.indexOf(i.getProductCode()) >= 0) {
					ReportSalesEntity row = new ReportSalesEntity(hashProduct.get(i.getProductCode()), i.getProductCode(),
					    new MyDouble(SLTang).toString(), new MyDouble(GTT).toString(), index + 1);
					row.setType(ReportSalesEntity.PRODUCT);
					row.setPath(tag + (tag != null ? ":" : "") + i.getPath());
					rows.add(row);
				}
			}
		}
		long endTime = System.currentTimeMillis();
		NumberFormat formatter = new DecimalFormat("#0.00000");
		String time = formatter.format((endTime - startTime1) / 1000d);
		System.out.println("TOng thoi gian chay : " + time);
		Collections.sort(rows, new Comparator<ReportSalesEntity>() {
			@Override
			public int compare(ReportSalesEntity object1, ReportSalesEntity object2) {
				return object1.getName().compareTo(object2.getName());
			}
		});
		return rows;
	}

}
