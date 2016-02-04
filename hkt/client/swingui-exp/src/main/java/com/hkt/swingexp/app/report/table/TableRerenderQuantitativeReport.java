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
import com.hkt.swingexp.app.report.entity.InventoryReportEntity;
import com.hkt.swingexp.app.report.entity.PanelTableCell;
import com.hkt.swingexp.app.report.modeltable.TableModelReportInventory;

/**
 * @author Phan Anh
 * @dateCreate 28/01/2015
 * 
 */

public class TableRerenderQuantitativeReport extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
	private PanelTableCell									cell;
	private Processing											processing;
	private TableModelReportInventory				model;
	private String													valueMoney;
	private Date														startDate, endDate;
	private String													warehouse;
	private boolean													showAll;
	private ImageIcon												nodeIconAdd, nodeImageMulti, nodeIconProduct;
	private static ClientContext						clientContext						= ClientContext.getInstance();
	private static AccountingServiceClient	accountingServiceClient	= clientContext.getBean(AccountingServiceClient.class);
	
	public TableRerenderQuantitativeReport(TableModelReportInventory model1, final Date startDate, final Date endDate, String valueMoney, String warehouse, boolean showAll) {
		nodeIconAdd = new ImageIcon(HomeScreen.class.getResource("icon/square_add_16.png"));
		nodeImageMulti = new ImageIcon(HomeScreen.class.getResource("icon/square_multi_16.png"));
		nodeIconProduct = new ImageIcon(HomeScreen.class.getResource("icon/square_product_16.png"));
		
		this.model = model1;
		this.valueMoney = valueMoney;
		this.startDate = startDate;
		this.endDate = endDate;
		this.warehouse = warehouse;
		this.showAll = showAll;
		
		this.cell = new PanelTableCell();
		this.processing = new Processing();
	}
	
	public void mouseClick_CellTable() {
		Object parent = cell.getObject();
		if (parent instanceof InventoryReportEntity) {
			
			/*********************************************************/
			/** ---------------START RUN TIME QUERY---------------- **/
			/*********************************************************/
			long startTime = System.currentTimeMillis();
			/** --------------------------------------------------- */
			
			int index = 0;
			String tag = null;
			if (cell.getRow() < model.getRowCount() - 1) {
				index = ((InventoryReportEntity) model.getValueAt(cell.getRow() + 1, 0)).getIndex();
				tag = ((InventoryReportEntity) model.getValueAt(cell.getRow() + 1, 0)).getParent();
			}
			System.out.println(tag);
			if (((InventoryReportEntity) parent).getType() == InventoryReportEntity.ORGANIZATION) {
				try {
					InventoryReportEntity inventoryEntity = (InventoryReportEntity) parent;
					if (index <= inventoryEntity.getIndex()) {
						int i = cell.getRow() + 1;
						if (inventoryEntity.getListChild() == null) {
							List<InventoryReportEntity> reportRestaurantEntities = new ArrayList<InventoryReportEntity>();
							reportRestaurantEntities.addAll(getProductTags(tag, index)); // Add All ProductTag
							reportRestaurantEntities.addAll(getProducts(inventoryEntity.getParent(), inventoryEntity.getIndex())); // Add All Product
							//if (!showAll) {
								for (int ep = 0; ep < reportRestaurantEntities.size();) {
									if (!reportRestaurantEntities.get(ep).isNotRowZero()
											||ProductModelManager.getInstance().getProductTagByCode(
													reportRestaurantEntities.get(ep).getParent()).getParentTag()!=null) {
										reportRestaurantEntities.remove(ep);
									} else
										ep++;
								}
							//}
							inventoryEntity.setListChild(reportRestaurantEntities);
						}
						model.addRow(inventoryEntity.getListChild(), i);
						inventoryEntity.getLabelIcon().setIcon(nodeImageMulti);
						inventoryEntity.setWiden(true);
					} else {
						System.out.println(cell.getRow());
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
							inventoryReportEntities.addAll(getProductTags(inventoryEntity.getParent(), inventoryEntity.getIndex()));
							inventoryReportEntities.addAll(getProducts(inventoryEntity.getParent(), inventoryEntity.getIndex()));
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
			
			/****************************************************************/
			/** ---------------RETURN TIME EXECUTE QUERY------------------ **/
			/****************************************************************/
			long endTime = System.currentTimeMillis();
			NumberFormat formatter = new DecimalFormat("#0.00000");
			String time = formatter.format((endTime - startTime) / 1000d);
			System.out.println("###$$$ TIME RUN QUERY: " + time);
			/** ---------------------------------------------------------- **/
		}
	}

  private List<InventoryReportEntity> getProductTags(String tag, int index) throws Exception {
    if (startDate == null)
      startDate = new Date();
    if (endDate == null)
      endDate = new Date();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar calendarNow = Calendar.getInstance();
    calendarNow.set(Calendar.HOUR_OF_DAY, 23);
    calendarNow.set(Calendar.MINUTE, 59);
    calendarNow.set(Calendar.SECOND, 59);

    Calendar calendarStart = Calendar.getInstance();
    calendarStart.setTime(startDate);
    calendarStart.set(Calendar.HOUR_OF_DAY, 00);
    calendarStart.set(Calendar.MINUTE, 00);
    calendarStart.set(Calendar.SECOND, 00);

    Calendar calendarEnd = Calendar.getInstance();
    calendarEnd.setTime(endDate);
    calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
    calendarEnd.set(Calendar.MINUTE, 59);
    calendarEnd.set(Calendar.SECOND, 59);

    SQLSelectQuery rQuery1 = new SQLSelectQuery();
    SQLSelectQuery rQuery2 = new SQLSelectQuery();
    SQLSelectQuery rQuery3 = new SQLSelectQuery();

    List<InventoryReportEntity> objects1 = null;
    List<InventoryReportEntity> objects2 = null;
    List<InventoryReportEntity> objects3 = null;

    /*********************************************
     ********************************************* 
     ******* [THỐNG KÊ ĐỊNH LƯỢNG TỔNG] **********
     ********************************************* 
     ********************************************* 
     */

    if (warehouse == null) {
      // Lấy giá trị và số lượng đầu kỳ
      rQuery1
          .table("WarehouseInventory i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
          .field("SUBSTRING_INDEX(a.value, ':', " + (index + 1) + ") AS `productTag`", "value0")
          .field("(SELECT w.label FROM warehouse_productTag w WHERE w.code = SUBSTRING_INDEX(`productTag`, ':', -1))", "value1")
          .field("COALESCE(SUM(i.increaseQuantity-i.reductionVitualQuantity), 0) AS `SLDauKy`", "value2")
          .field("COALESCE(SUM(i.increaseValue-i.reductionVitualValue), 0) AS `GTDauKy`", "value3")
          .cond("i.startDate <= '" + dateFormat.format(calendarStart.getTime()) + "'")
          .cond("a.name = '" + AccountingModelManager.product + "'")
          .cond("a.value IS NOT NULL")

          .cond("p.isCalculateReport = 1")
          .groupBy("`productTag`");
      if (tag != null) {
        rQuery1
        .cond("SUBSTRING_INDEX(a.value, ':', " + (index) + ") = '" + tag + "'")
        .cond("(LENGTH(SUBSTRING_INDEX(a.value, ':', " + (index + 1) + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(a.value, ':', " + (index + 1) + "), ':', '')))  >= " + (index));
      }

      // Lấy giá trị, số lượng tăng giảm trong kỳ
      rQuery2
          .table("WarehouseInventory i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
          .field("SUBSTRING_INDEX(a.value, ':', " + (index + 1) + ") AS `productTag`", "value0")
          .field("(SELECT w.label FROM warehouse_productTag w WHERE w.code = SUBSTRING_INDEX(`productTag`, ':', -1))", "value1")
          .field("COALESCE(SUM(i.increaseQuantity), 0) AS `SLTrongKyTang`", "value2")
          .field("COALESCE(SUM(i.increaseValue), 0) AS `GTTrongKyTang`", "value3")
          .field("COALESCE(SUM(i.reductionVitualQuantity), 0) AS `SLTrongKyGiam`", "value4")
          .field("COALESCE(SUM(i.reductionVitualValue), 0) AS `GTTrongKyGiam`", "value5")
          .cond("i.startDate >= '" + dateFormat.format(calendarStart.getTime()) + "'")
          .cond("i.startDate <= '" + dateFormat.format(calendarEnd.getTime()) + "'")
          .cond("a.name = '" + AccountingModelManager.product + "'")
          .cond("a.value IS NOT NULL")

          .cond("p.isCalculateReport = 1")
          .groupBy("`productTag`");
      if (tag != null) {
        rQuery2
        .cond("SUBSTRING_INDEX(a.value, ':', " + (index) + ") = '" + tag + "'")
        .cond("(LENGTH(SUBSTRING_INDEX(a.value, ':', " + (index + 1) + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(a.value, ':', " + (index + 1) + "), ':', '')))  >= " + (index));
      }

      // lấy số lượng và giá trị còn tại
      rQuery3
          .table("WarehouseInventory i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
          .field("SUBSTRING_INDEX(a.value, ':', " + (index + 1) + ") AS `productTag`", "value0")
          .field("(SELECT w.label FROM warehouse_productTag w WHERE w.code = SUBSTRING_INDEX(`productTag`, ':', -1))", "value1")
          .field("COALESCE(SUM(i.increaseQuantity-i.reductionVitualQuantity), 0) AS `SLConLai`", "value2")
          .field("COALESCE(SUM(i.increaseValue-i.reductionVitualValue), 0) AS `GTConLai`", "value3")
          .cond("i.startDate >= '" + dateFormat.format(calendarEnd.getTime()) + "'")
          .cond("i.startDate <= '" + dateFormat.format(calendarNow.getTime()) + "'")
          .cond("a.name = '" + AccountingModelManager.product + "'")
          .cond("a.value IS NOT NULL")

          .cond("p.isCalculateReport = 1")
          .groupBy("`productTag`");
      if (tag != null) {
        rQuery3
        .cond("SUBSTRING_INDEX(a.value, ':', " + (index) + ") = '" + tag + "'")
        .cond("(LENGTH(SUBSTRING_INDEX(a.value, ':', " + (index + 1) + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(a.value, ':', " + (index + 1) + "), ':', '')))  >= " + (index));
      }

      objects1 = runQuery(rQuery1);
      objects2 = runQuery(rQuery2);
      objects3 = runQuery(rQuery3);
    }

    /*********************************************
     ********************************************* 
     ********* [THỐNG KÊ ĐỊNH LƯỢNG LẺ] **********
     ********************************************* 
     ********************************************* 
     */
    else {
      // Lấy giá trị và số lượng đầu kỳ
      rQuery1
          .table("WarehouseProfile i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
          .field("SUBSTRING_INDEX(a.value, ':', " + (index + 1) + ") AS `productTag`", "value0")
          .field("(SELECT w.label FROM warehouse_productTag w WHERE w.code = SUBSTRING_INDEX(`productTag`, ':', -1))", "value1")
          .field("COALESCE(SUM(i.increaseQuantity-i.reductionVitualQuantity), 0) AS `SLDauKy`", "value2")
          .field("COALESCE(SUM(i.increaseValue-i.reductionVitualValue), 0) AS `GTDauKy`", "value3")
          .cond("i.startDate <= '" + dateFormat.format(calendarStart.getTime()) + "'")
          .cond("i.warehouseCode = '" + warehouse + "'")
          .cond("a.name = '" + AccountingModelManager.product + "'")
          .cond("a.value IS NOT NULL")

          .cond("p.isCalculateReport = 1")
          .groupBy("`productTag`");
      if (tag != null) {
        rQuery1
        .cond("SUBSTRING_INDEX(a.value, ':', " + (index) + ") = '" + tag + "'")
        .cond("(LENGTH(SUBSTRING_INDEX(a.value, ':', " + (index + 1) + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(a.value, ':', " + (index + 1) + "), ':', '')))  >= " + (index));
      }

      // Lấy giá trị, số lượng tăng giảm trong kỳ
      rQuery2
          .table("WarehouseProfile i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
          .field("SUBSTRING_INDEX(a.value, ':', " + (index + 1) + ") AS `productTag`", "value0")
          .field("(SELECT w.label FROM warehouse_productTag w WHERE w.code = SUBSTRING_INDEX(`productTag`, ':', -1))", "value1")
          .field("COALESCE(SUM(i.increaseQuantity), 0) AS `SLTrongKyTang`", "value2")
          .field("COALESCE(SUM(i.increaseValue), 0) AS `GTTrongKyTang`", "value3")
          .field("COALESCE(SUM(i.reductionVitualQuantity), 0) AS `SLTrongKyGiam`", "value4")
          .field("COALESCE(SUM(i.reductionVitualValue), 0) AS `GTTrongKyGiam`", "value5")
          .cond("i.startDate >= '" + dateFormat.format(calendarStart.getTime()) + "'")
          .cond("i.startDate <= '" + dateFormat.format(calendarEnd.getTime()) + "'")
          .cond("i.warehouseCode = '" + warehouse + "'")
          .cond("a.name = '" + AccountingModelManager.product + "'")
          .cond("a.value IS NOT NULL")

          .cond("p.isCalculateReport = 1")
          .groupBy("`productTag`");
      if (tag != null) {
        rQuery2
        .cond("SUBSTRING_INDEX(a.value, ':', " + (index) + ") = '" + tag + "'") 
        .cond("(LENGTH(SUBSTRING_INDEX(a.value, ':', " + (index + 1) + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(a.value, ':', " + (index + 1) + "), ':', '')))  >= " + (index));
      }

      // lấy số lượng và giá trị còn tại
      rQuery3
          .table("WarehouseProfile i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
          .field("SUBSTRING_INDEX(a.value, ':', " + (index + 1) + ") AS `productTag`", "value0")
          .field("(SELECT w.label FROM warehouse_productTag w WHERE w.code = SUBSTRING_INDEX(`productTag`, ':', -1))", "value1")
          .field("COALESCE(SUM(i.increaseQuantity-i.reductionVitualQuantity), 0) AS `SLConLai`", "value2")
          .field("COALESCE(SUM(i.increaseValue-i.reductionVitualValue), 0) AS `GTConLai`", "value3")
          .cond("i.startDate >= '" + dateFormat.format(calendarEnd.getTime()) + "'")
          .cond("i.startDate <= '" + dateFormat.format(calendarNow.getTime()) + "'")
          .cond("i.warehouseCode = '" + warehouse + "'")
          .cond("a.name = '" + AccountingModelManager.product + "'")
          .cond("a.value IS NOT NULL")

          .cond("p.isCalculateReport = 1")
          .groupBy("`productTag`");
      if (tag != null) {
        rQuery3
        .cond("SUBSTRING_INDEX(a.value, ':', " + (index) + ") = '" + tag + "'")
        .cond("(LENGTH(SUBSTRING_INDEX(a.value, ':', " + (index + 1) + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(a.value, ':', " + (index + 1) + "), ':', '')))  >= " + (index));
      }

      objects1 = runQuery(rQuery1);
      objects2 = runQuery(rQuery2);
      objects3 = runQuery(rQuery3);
    }

    HashMap<String, InventoryReportEntity> row_hash = new HashMap<String, InventoryReportEntity>();
    List<InventoryReportEntity> rows = new ArrayList<InventoryReportEntity>();

    if (objects1 != null && objects1.size() > 0) {
      for (int i = 0; i < objects1.size(); i++) {
        InventoryReportEntity obj = new InventoryReportEntity(objects1.get(i).getCol1_DanhMuc(), objects1.get(i)
            .getCol2_SLDauKy(), objects1.get(i).getCol3_GTDauKy(), "0", "0", "0", "0", "0", "0");
        obj.setParent(objects1.get(i).getParent());
        row_hash.put(objects1.get(i).getParent(), obj);
      }
    }
    if (objects2 != null && objects2.size() > 0) {
      for (int i = 0; i < objects2.size(); i++) {
        if (row_hash.containsKey(objects2.get(i).getParent())) {
          InventoryReportEntity r = row_hash.get(objects2.get(i).getParent());
          r.setCol4_SLTang(objects2.get(i).getCol2_SLDauKy());
          r.setCol5_GTTang(objects2.get(i).getCol3_GTDauKy());
          r.setCol6_SLGiam(objects2.get(i).getCol4_SLTang());
          r.setCol7_GTGiam(objects2.get(i).getCol5_GTTang());
        } else {
          InventoryReportEntity obj = new InventoryReportEntity(objects2.get(i).getCol1_DanhMuc(), "0", "0", objects2
              .get(i).getCol2_SLDauKy(), objects2.get(i).getCol3_GTDauKy(), objects2.get(i).getCol4_SLTang(), objects2
              .get(i).getCol5_GTTang(), "0", "0");
          obj.setParent(objects2.get(i).getParent());
          row_hash.put(objects2.get(i).getParent(), obj);
        }
      }
    }
    if (objects3 != null && objects3.size() > 0) {
      for (int i = 0; i < objects3.size(); i++) {
        if (row_hash.containsKey(objects3.get(i).getParent())) {
          InventoryReportEntity r = row_hash.get(objects3.get(i).getParent());
          r.setSLConLai(objects3.get(i).getCol2_SLDauKy());
          r.setGTConLai(objects3.get(i).getCol3_GTDauKy());
        } else {
          InventoryReportEntity obj = new InventoryReportEntity(objects3.get(i).getCol1_DanhMuc(), "0", "0", "0", "0",
              "0", "0", objects3.get(i).getCol2_SLDauKy(), objects3.get(i).getCol3_GTDauKy());
          obj.setParent(objects3.get(i).getParent());
          row_hash.put(objects3.get(i).getParent(), obj);
        }
      }
    }

    int rate = 1;
    if (valueMoney.equals("Nghìn")) {
      rate = 1000;
    } else if (valueMoney.equals("Triệu")) {
      rate = 1000000;
    }
    Iterator<InventoryReportEntity> in = row_hash.values().iterator();
    while (in.hasNext()) {
      InventoryReportEntity i = in.next();
      double SLDauKy = MyDouble.parseDouble(i.getCol2_SLDauKy());
      double GTDauKy = MyDouble.parseDouble(i.getCol3_GTDauKy()) / rate;
      double SLTang = MyDouble.parseDouble(i.getCol4_SLTang());
      double GTTang = MyDouble.parseDouble(i.getCol5_GTTang()) / rate;
      double SLGiam = MyDouble.parseDouble(i.getCol6_SLGiam());
      double GTGiam = MyDouble.parseDouble(i.getCol7_GTGiam()) / rate;
      double SLConLai = MyDouble.parseDouble(i.getSLConLai());
      double GTConLai = MyDouble.parseDouble(i.getGTConLai()) / rate;
      InventoryReportEntity row = new InventoryReportEntity(i.getCol1_DanhMuc(), Double.toString(SLDauKy),
          Double.toString(GTDauKy), Double.toString(SLTang), Double.toString(GTTang), Double.toString(SLGiam),
          Double.toString(GTGiam), Double.toString(SLConLai), Double.toString(GTConLai));
      row.setType(InventoryReportEntity.PRODUCT_TAG);
      row.setParent(i.getParent());
      row.setIndex(index + 1);
      rows.add(row);
    }

    Collections.sort(rows, new Comparator<InventoryReportEntity>() {
      @Override
      public int compare(InventoryReportEntity object1, InventoryReportEntity object2) {
          return object1.getCol1_DanhMuc().compareTo(object2.getCol1_DanhMuc());
      }
     } );	
    
    return rows;
  }

  private List<InventoryReportEntity> getProducts(String tag, int index) throws Exception {
    if (startDate == null)
      startDate = new Date();
    if (endDate == null)
      endDate = new Date();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar calendarNow = Calendar.getInstance();
    calendarNow.set(Calendar.HOUR_OF_DAY, 23);
    calendarNow.set(Calendar.MINUTE, 59);
    calendarNow.set(Calendar.SECOND, 59);

    Calendar calendarStart = Calendar.getInstance();
    calendarStart.setTime(startDate);
    calendarStart.set(Calendar.HOUR_OF_DAY, 00);
    calendarStart.set(Calendar.MINUTE, 00);
    calendarStart.set(Calendar.SECOND, 00);

    Calendar calendarEnd = Calendar.getInstance();
    calendarEnd.setTime(endDate);
    calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
    calendarEnd.set(Calendar.MINUTE, 59);
    calendarEnd.set(Calendar.SECOND, 59);

    SQLSelectQuery rQuery1 = new SQLSelectQuery();
    SQLSelectQuery rQuery2 = new SQLSelectQuery();
    SQLSelectQuery rQuery3 = new SQLSelectQuery();

    List<InventoryReportEntity> objects1 = null;
    List<InventoryReportEntity> objects2 = null;
    List<InventoryReportEntity> objects3 = null;

    /*********************************************
     ********************************************* 
     ********* [THỐNG KÊ ĐỊNH LƯỢNG TỔNG] ********
     ********************************************* 
     ********************************************* 
     */
    if (warehouse == null) {
      // Lấy giá trị và số lượng đầu kỳ
      rQuery1
          .table("WarehouseInventory i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
          .field("p.code AS `productCode`", "value0")
          .field("p.name AS `product`", "value1")
          .field("COALESCE(SUM(i.increaseQuantity-i.reductionVitualQuantity), 0) AS `SLDauKy`", "value2")
          .field("COALESCE(SUM(i.increaseValue-i.reductionVitualValue), 0) AS `GTDauKy`", "value3")
          .cond("i.startDate <= '" + dateFormat.format(calendarStart.getTime()) + "'")
          .cond("a.name = '" + AccountingModelManager.product + "'")

          .cond("p.isCalculateReport = 1")
          .groupBy("`productCode`");
      if (tag != null) {
        rQuery1.cond("a.value = '" + tag + "'");
      } else {
        rQuery1.cond("a.value IS NULL");
      }

      // Lấy giá trị, số lượng tăng giảm trong kỳ
      rQuery2
          .table("WarehouseInventory i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
          .field("p.code AS `productCode`", "value0").field("p.name AS `product`", "value1")
          .field("COALESCE(SUM(i.increaseQuantity), 0) AS `SLTrongKyTang`", "value2")
          .field("COALESCE(SUM(i.increaseValue), 0) AS `GTTrongKyTang`", "value3")
          .field("COALESCE(SUM(i.reductionVitualQuantity), 0) AS `SLTrongKyGiam`", "value4")
          .field("COALESCE(SUM(i.reductionVitualValue), 0) AS `GTTrongKyGiam`", "value5")
          .cond("i.startDate >= '" + dateFormat.format(calendarStart.getTime()) + "'")
          .cond("i.startDate <= '" + dateFormat.format(calendarEnd.getTime()) + "'")
          .cond("a.name = '" + AccountingModelManager.product + "'")

          .cond("p.isCalculateReport = 1")
          .groupBy("`productCode`");
      if (tag != null) {
        rQuery2.cond("a.value = '" + tag + "'");
      } else {
        rQuery2.cond("a.value IS NULL");
      }

      // lấy số lượng và giá trị còn tại
      rQuery3
          .table("WarehouseInventory i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
          .field("p.code AS `productCode`", "value0").field("p.name AS `product`", "value1")
          .field("COALESCE(SUM(i.increaseQuantity-i.reductionVitualQuantity), 0) AS `SLConLai`", "value2")
          .field("COALESCE(SUM(i.increaseValue-i.reductionVitualValue), 0) AS `GTConLai`", "value3")
          .cond("i.startDate >= '" + dateFormat.format(calendarEnd.getTime()) + "'")
          .cond("i.startDate <= '" + dateFormat.format(calendarNow.getTime()) + "'")
          .cond("a.name = '" + AccountingModelManager.product + "'")

          .cond("p.isCalculateReport = 1")
          .groupBy("`productCode`");
      if (tag != null) {
        rQuery3.cond("a.value = '" + tag + "'");
      } else {
        rQuery3.cond("a.value IS NULL");
      }

      objects1 = runQuery(rQuery1);
      objects2 = runQuery(rQuery2);
      objects3 = runQuery(rQuery3);
    }

    /*********************************************
     ********************************************* 
     ********* [THỐNG KÊ ĐỊNH LƯỢNG LẺ] **********
     ********************************************* 
     ********************************************* 
     */
    else {
      // Lấy giá trị và số lượng đầu kỳ
      rQuery1
          .table("WarehouseProfile i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
          .field("p.code AS `productCode`", "value0").field("p.name AS `product`", "value1")
          .field("COALESCE(SUM(i.increaseQuantity-i.reductionVitualQuantity), 0) AS `SLDauKy`", "value2")
          .field("COALESCE(SUM(i.increaseValue-i.reductionVitualValue), 0) AS `GTDauKy`", "value3")
          .cond("i.startDate <= '" + dateFormat.format(calendarStart.getTime()) + "'")
          .cond("i.warehouseCode = '" + warehouse + "'")
          .cond("a.name = '" + AccountingModelManager.product + "'")

          .cond("p.isCalculateReport = 1")
          .groupBy("`productCode`");
      if (tag != null) {
        rQuery1.cond("a.value = '" + tag + "'");
      } else {
        rQuery1.cond("a.value IS NULL");
      }

      // Lấy giá trị, số lượng tăng giảm trong kỳ
      rQuery2
          .table("WarehouseProfile i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
          .field("p.code AS `productCode`", "value0").field("p.name AS `product`", "value1")
          .field("COALESCE(SUM(i.increaseQuantity), 0) AS `SLTrongKyTang`", "value2")
          .field("COALESCE(SUM(i.increaseValue), 0) AS `GTTrongKyTang`", "value3")
          .field("COALESCE(SUM(i.reductionVitualQuantity), 0) AS `SLTrongKyGiam`", "value4")
          .field("COALESCE(SUM(i.reductionVitualValue), 0) AS `GTTrongKyGiam`", "value5")
          .cond("i.startDate >= '" + dateFormat.format(calendarStart.getTime()) + "'")
          .cond("i.startDate <= '" + dateFormat.format(calendarEnd.getTime()) + "'")
          .cond("i.warehouseCode = '" + warehouse + "'")
          .cond("a.name = '" + AccountingModelManager.product + "'")

          .cond("p.isCalculateReport = 1")
          .groupBy("`productCode`");
      if (tag != null) {
        rQuery2.cond("a.value = '" + tag + "'");
      } else {
        rQuery2.cond("a.value IS NULL");
      }

      // lấy số lượng và giá trị còn tại
      rQuery3
          .table("WarehouseProfile i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id")
          .field("p.code AS `productCode`", "value0")
          .field("p.name AS `product`", "value1")
          .field("COALESCE(SUM(i.increaseQuantity-i.reductionVitualQuantity), 0) AS `SLConLai`", "value2")
          .field("COALESCE(SUM(i.increaseValue-i.reductionVitualValue), 0) AS `GTConLai`", "value3")
          .cond("i.startDate >= '" + dateFormat.format(calendarEnd.getTime()) + "'")
          .cond("i.startDate <= '" + dateFormat.format(calendarNow.getTime()) + "'")
          .cond("i.warehouseCode = '" + warehouse + "'")
          .cond("a.name = '" + AccountingModelManager.product + "'")

          .cond("p.isCalculateReport = 1")
          .groupBy("`productCode`");
      if (tag != null) {
        rQuery3.cond("a.value = '" + tag + "'");
      } else {
        rQuery3.cond("a.value IS NULL");
      }

      objects1 = runQuery(rQuery1);
      objects2 = runQuery(rQuery2);
      objects3 = runQuery(rQuery3);
    }

    HashMap<String, InventoryReportEntity> row_hash = new HashMap<String, InventoryReportEntity>();
    List<InventoryReportEntity> rows = new ArrayList<InventoryReportEntity>();

    if (objects1 != null && objects1.size() > 0) {
      for (int i = 0; i < objects1.size(); i++) {
        row_hash.put(objects1.get(i).getParent(), new InventoryReportEntity(objects1.get(i).getCol1_DanhMuc(), objects1
            .get(i).getCol2_SLDauKy(), objects1.get(i).getCol3_GTDauKy(), "0", "0", "0", "0", "0", "0"));
      }
    }
    if (objects2 != null && objects2.size() > 0) {
      for (int i = 0; i < objects2.size(); i++) {
        if (row_hash.containsKey(objects2.get(i).getParent())) {
          InventoryReportEntity r = row_hash.get(objects2.get(i).getParent());
          r.setCol4_SLTang(objects2.get(i).getCol2_SLDauKy());
          r.setCol5_GTTang(objects2.get(i).getCol3_GTDauKy());
          r.setCol6_SLGiam(objects2.get(i).getCol4_SLTang());
          r.setCol7_GTGiam(objects2.get(i).getCol5_GTTang());
        } else {
          row_hash.put(objects2.get(i).getParent(), new InventoryReportEntity(objects2.get(i).getCol1_DanhMuc(), "0",
              "0", objects2.get(i).getCol2_SLDauKy(), objects2.get(i).getCol3_GTDauKy(), objects2.get(i)
                  .getCol4_SLTang(), objects2.get(i).getCol5_GTTang(), "0", "0"));
        }
      }
    }
    if (objects3 != null && objects3.size() > 0) {
      for (int i = 0; i < objects3.size(); i++) {
        if (row_hash.containsKey(objects3.get(i).getParent())) {
          InventoryReportEntity r = row_hash.get(objects3.get(i).getParent());
          r.setSLConLai(objects3.get(i).getCol2_SLDauKy());
          r.setGTConLai(objects3.get(i).getCol3_GTDauKy());
        } else {
          row_hash.put(objects3.get(i).getParent(), new InventoryReportEntity(objects3.get(i).getCol1_DanhMuc(), "0", "0", "0", "0", "0", "0", objects3.get(i).getCol2_SLDauKy(), objects3.get(i).getCol3_GTDauKy()));
        }
      }
    }

    int rate = 1;
    if (valueMoney.equals("Nghìn")) {
      rate = 1000;
    } else if (valueMoney.equals("Triệu")) {
      rate = 1000000;
    }
    Iterator<InventoryReportEntity> in = row_hash.values().iterator();
    while (in.hasNext()) {
      InventoryReportEntity i = in.next();
      double SLDauKy = MyDouble.parseDouble(i.getCol2_SLDauKy());
      double GTDauKy = MyDouble.parseDouble(i.getCol3_GTDauKy()) / rate;
      double SLTang = MyDouble.parseDouble(i.getCol4_SLTang());
      double GTTang = MyDouble.parseDouble(i.getCol5_GTTang()) / rate;
      double SLGiam = MyDouble.parseDouble(i.getCol6_SLGiam());
      double GTGiam = MyDouble.parseDouble(i.getCol7_GTGiam()) / rate;
      double SLConLai = MyDouble.parseDouble(i.getSLConLai());
      double GTConLai = MyDouble.parseDouble(i.getGTConLai()) / rate;
      InventoryReportEntity row = new InventoryReportEntity(i.getCol1_DanhMuc(), Double.toString(SLDauKy),
          Double.toString(GTDauKy), Double.toString(SLTang), Double.toString(GTTang), Double.toString(SLGiam),
          Double.toString(GTGiam), Double.toString(SLConLai), Double.toString(GTConLai));
      row.setType(InventoryReportEntity.PRODUCT);
      row.setParent(tag + (tag != null ? ":" : "") + i.getParent());
      row.setIndex(index + 1);
      rows.add(row);
    }

    Collections.sort(rows, new Comparator<InventoryReportEntity>() {
      @Override
      public int compare(InventoryReportEntity object1, InventoryReportEntity object2) {
          return object1.getCol1_DanhMuc().compareTo(object2.getCol1_DanhMuc());
      }
     } );	
    
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
      InventoryReportEntity object = null;
      if (rQuery.getFields().size() == 6)
        object = new InventoryReportEntity(values[1].toString(), values[2].toString(), values[3].toString(),
            values[4].toString(), values[5].toString(), "", "", "", "");
      else
        object = new InventoryReportEntity(values[1].toString(), values[2].toString(), values[3].toString(), "", "",
            "", "", "", "");
      object.setParent(values[0].toString());
      listObjects.add(object);
    }
    return listObjects;
  }

  @Override
  public Object getCellEditorValue() {
    return cell.getObject();
  }

  private double valueQuantity = 1;

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
              ((InventoryReportEntity) value).setColor(new Color(255, 0, 0)); // Màu đỏ
            } else {
              if (valueQuantity == 0) {
                ((InventoryReportEntity) value).setColor(new Color(128, 100, 162)); // Màu tím
              } else {
                if (valueQuantity <= 3) {
                  ((InventoryReportEntity) value).setColor(new Color(0, 176, 80)); // Màu xanh lá
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
						if(type.equals("SL đầu kỳ")){
							for(InventoryReportEntity r : list){
								Double d = (new MyDouble(r.getCol2_SLDauKy()).doubleValue()/new MyDouble(((InventoryReportEntity) c).getCol2_SLDauKy()).doubleValue()+new MyDouble(r.getCol2_SLDauKy()).doubleValue()%new MyDouble(((InventoryReportEntity) c).getCol2_SLDauKy()).doubleValue())*100;
								dataset.setValue(r.getCol1_DanhMuc(), d);
							}
						} else {
							if(type.equals("GT đầu kỳ")){
								for(InventoryReportEntity r : list){
									Double d = (new MyDouble(r.getCol3_GTDauKy()).doubleValue()/new MyDouble(((InventoryReportEntity) c).getCol3_GTDauKy()).doubleValue()+new MyDouble(r.getCol3_GTDauKy()).doubleValue()%new MyDouble(((InventoryReportEntity) c).getCol3_GTDauKy()).doubleValue())*100;
									dataset.setValue(r.getCol1_DanhMuc(), d);
								}
							} else {
								if(type.equals("SL tăng")){
									for(InventoryReportEntity r : list){
										Double d = (new MyDouble(r.getCol4_SLTang()).doubleValue()/new MyDouble(((InventoryReportEntity) c).getCol4_SLTang()).doubleValue()+new MyDouble(r.getCol4_SLTang()).doubleValue()%new MyDouble(((InventoryReportEntity) c).getCol4_SLTang()).doubleValue())*100;
										dataset.setValue(r.getCol1_DanhMuc(), d);
									}
								} else {
									if(type.equals("GT tăng")){
										for(InventoryReportEntity r : list){
											Double d = (new MyDouble(r.getCol5_GTTang()).doubleValue()/new MyDouble(((InventoryReportEntity) c).getCol5_GTTang()).doubleValue()+new MyDouble(r.getCol5_GTTang()).doubleValue()%new MyDouble(((InventoryReportEntity) c).getCol5_GTTang()).doubleValue())*100;
											dataset.setValue(r.getCol1_DanhMuc(), d);
										}
									} else {
										if(type.equals("SL giảm")){
											for(InventoryReportEntity r : list){
												Double d = (new MyDouble(r.getCol6_SLGiam()).doubleValue()/new MyDouble(((InventoryReportEntity) c).getCol6_SLGiam()).doubleValue()+new MyDouble(r.getCol6_SLGiam()).doubleValue()%new MyDouble(((InventoryReportEntity) c).getCol6_SLGiam()).doubleValue())*100;
												dataset.setValue(r.getCol1_DanhMuc(), d);
											}
										} else {
											if(type.equals("GT giảm")){
												for(InventoryReportEntity r : list){
													Double d = (new MyDouble(r.getCol7_GTGiam()).doubleValue()/new MyDouble(((InventoryReportEntity) c).getCol7_GTGiam()).doubleValue()+new MyDouble(r.getCol7_GTGiam()).doubleValue()%new MyDouble(((InventoryReportEntity) c).getCol7_GTGiam()).doubleValue())*100;
													dataset.setValue(r.getCol1_DanhMuc(), d);
												}
											} else {
												if(type.equals("SL cuối kỳ")){
													for(InventoryReportEntity r : list){
														Double d = (new MyDouble(r.getCol8_SLCuoiKy()).doubleValue()/new MyDouble(((InventoryReportEntity) c).getCol8_SLCuoiKy()).doubleValue()+new MyDouble(r.getCol8_SLCuoiKy()).doubleValue()%new MyDouble(((InventoryReportEntity) c).getCol8_SLCuoiKy()).doubleValue())*100;
														dataset.setValue(r.getCol1_DanhMuc(), d);
													}
												} else {
													if(type.equals("GT cuối kỳ")){
														for(InventoryReportEntity r : list){
															Double d = (new MyDouble(r.getCol9_GTCuoiKy()).doubleValue()/new MyDouble(((InventoryReportEntity) c).getCol9_GTCuoiKy()).doubleValue()+new MyDouble(r.getCol9_GTCuoiKy()).doubleValue()%new MyDouble(((InventoryReportEntity) c).getCol9_GTCuoiKy()).doubleValue())*100;
															dataset.setValue(r.getCol1_DanhMuc(), d);
														}
													} else {
														if(type.equals("SL hiện tại")){
															for(InventoryReportEntity r : list){
																Double d = (new MyDouble(r.getCol10_SLHienTai()).doubleValue()/new MyDouble(((InventoryReportEntity) c).getCol10_SLHienTai()).doubleValue()+new MyDouble(r.getCol10_SLHienTai()).doubleValue()%new MyDouble(((InventoryReportEntity) c).getCol10_SLHienTai()).doubleValue())*100;
																dataset.setValue(r.getCol1_DanhMuc(), d);
															}
														} else {
															if(type.equals("GT hiện tại")){
																for(InventoryReportEntity r : list){
																	Double d = (new MyDouble(r.getCol11_GTHienTai()).doubleValue()/new MyDouble(((InventoryReportEntity) c).getCol11_GTHienTai()).doubleValue()+new MyDouble(r.getCol11_GTHienTai()).doubleValue()%new MyDouble(((InventoryReportEntity) c).getCol11_GTHienTai()).doubleValue())*100;
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
        e.printStackTrace();
        cell.getText().setText(" ");
      }
    }
    cell.setIcon(labelIcon);

    if (isProduct) {
      if (valueQuantity < 0) {
        ((InventoryReportEntity) value).setColor(new Color(255, 0, 0)); // Màu đỏ
      } else {
        if (valueQuantity == 0) {
          ((InventoryReportEntity) value).setColor(new Color(128, 100, 162)); // Màu tím
        } else {
          if (valueQuantity <= 3) {
            ((InventoryReportEntity) value).setColor(new Color(0, 176, 80)); // Màu xanh lá
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
