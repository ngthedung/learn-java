package com.hkt.client.swingexp.app.convertdata.excel;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.config.locale.Currency;
import com.hkt.module.config.locale.ProductUnit;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductAttribute;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Recipe;
import com.hkt.module.restaurant.entity.RecipeIngredient;
import com.hkt.module.restaurant.entity.Table;

public class BackupTool {


  public BackupTool() {
  }

  public void runImport(String fileName) throws Exception {
    try {
      // Create the input stream from the xlsx/xls file
      FileInputStream fis = new FileInputStream(fileName);
      // Create Workbook instance for xlsx/xls file input stream
      Workbook workbook = null;
      if (fileName.toLowerCase().endsWith("xlsx")) {
        workbook = new XSSFWorkbook(fis);
      } else if (fileName.toLowerCase().endsWith("xls")) {
        workbook = new HSSFWorkbook(fis);
      }
      // Get the number of sheets in the xlsx file
      int numberOfSheets = workbook.getNumberOfSheets();
      // loop through each of the sheets
      for (int i = 0; i < numberOfSheets; i++) {
        Sheet sheet = workbook.getSheetAt(i);
        // map sheet to table
        MapTableDanhMuc(sheet);
      }
      for (int i = 0; i < numberOfSheets; i++) {
        Sheet sheet = workbook.getSheetAt(i);
        MapTableProduct(sheet);
      }
      for (int i = 0; i < numberOfSheets; i++) {
        Sheet sheet = workbook.getSheetAt(i);
        // map sheet to table
        MapTableDinhLuong(sheet);
      }
      for (int i = 0; i < numberOfSheets; i++) {
        Sheet sheet = workbook.getSheetAt(i);
        MapTableKhucVuc(sheet);
      }
      fis.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // sheet SP
  private void MapTableProduct(Sheet sheet) {
    System.out.println("Table : " + sheet.getSheetName());
    String tableName = sheet.getSheetName();
    List<String>headers = getListHeader(sheet);
    System.out.println(headers);
    if (tableName.equals("SP")) {
      Iterator<Row> rowIterator = sheet.iterator();
      int i = 1;
      while (rowIterator.hasNext()) {
      	System.out.println("Dong "+i);
      	i++;
        // Get the row object
        // Product product = new Product();
        Product product = new Product();
        product.setMaker("HKT");
        product.setProductAttributes(new ArrayList<ProductAttribute>());
        Row row = rowIterator.next();//
        // Every row has columns, get the column iterator and iterate over
        // them
        Iterator<Cell> cellIterator = row.cellIterator();
        if (row.getRowNum() > 0) {
          while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell.getRow().getRowNum() == 0) {
             // System.out.println("---- Column name:" + cell.getNumericCellValue());
            } else {
              String a1 = "";
              if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                a1 = String.valueOf(cell.getNumericCellValue());
              }else {
                if(cell.getCellType() == Cell.CELL_TYPE_STRING){
                  a1 = cell.getStringCellValue();
                }
                
              }
              if (!a1.trim().isEmpty()) {
                if (cell.getColumnIndex() == 1) {
                //  System.out.println("---- Name Product: " + cell.getStringCellValue());
                  product.setName(cell.getStringCellValue());
                  if (product.getName().trim().length() == 0) {
                    continue;
                  }
                }

                if (cell.getColumnIndex() == 2) {
                  try {

                    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                     // System.out.println("---- Code Product: " + cell.getNumericCellValue());
                      Product product2 = ProductModelManager.getInstance().getProductByCode(
                          String.valueOf(cell.getNumericCellValue()));
                      if (product2 == null) {
                        product.setCode(String.valueOf(cell.getNumericCellValue()));
                      } else {
                        product = product2;
                      }
                    } else {
                     // System.out.println("---- Code Product: " + cell.getStringCellValue());
                      Product product2 = ProductModelManager.getInstance().getProductByCode(cell.getStringCellValue());
                      if (product2 == null) {
                        product.setCode(cell.getStringCellValue());
                      } else {
                        product = product2;
                      }
                    }
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }
                // Product unit
                if (cell.getColumnIndex() == 3) {
                  try {
                 //   System.out.println("----Product Unit : " + cell.getStringCellValue());
                    if (cell.getStringCellValue() != null && !cell.getStringCellValue().trim().equals("")) {
                      ProductUnit productUnit = LocaleModelManager.getInstance().getProductUnitByCode(
                          cell.getStringCellValue());
                      if (productUnit == null) {
                        productUnit = new ProductUnit();
                        productUnit.setCode(cell.getStringCellValue());
                        productUnit.setName(cell.getStringCellValue());
                        productUnit.setRate(100);
                        productUnit = LocaleModelManager.getInstance().saveProductUnit(productUnit);
                      }
                      product.setUnit(productUnit.getCode());
                      product = ProductModelManager.getInstance().saveProduct(product);
                    } else {
                      ProductUnit productUnit = LocaleModelManager.getInstance().getProductUnitByDefault();

                      product.setUnit(productUnit.getCode());
                      product = ProductModelManager.getInstance().saveProduct(product);
                    }

                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }
                // Group product 1
                if (cell.getColumnIndex() == 4) {
                  if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals(""))
                  //  System.out.println("----Product Tag : " + cell.getStringCellValue());
                  try {
                    ProductTag productTag = ProductModelManager.getInstance().getProductTagByCode(
                        cell.getStringCellValue());
                    if (productTag == null) {
                      productTag = new ProductTag();
                      productTag.setCode(cell.getStringCellValue());
                      productTag.setLabel(cell.getStringCellValue());
                      productTag.setTag(cell.getStringCellValue());
                      productTag = ProductModelManager.getInstance().saveProductTag(productTag);
                    }
                    ProductAttribute a = new ProductAttribute();
                    a.setName(AccountingModelManager.product);
                 //   System.out.println(productTag.getTag()+"      ....");
                    a.setValue(productTag.getTag());
                    product.add(a);
                    product.add(productTag);
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }
                // Group product 2
                if (cell.getColumnIndex() == 5) {
                  if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals(""))
              //      System.out.println("----Product Tag 2: " + cell.getStringCellValue());
                  try {
                    ProductTag productTag = ProductModelManager.getInstance().getProductTagByCode(
                        cell.getStringCellValue());
                    if (productTag == null) {
                      productTag = new ProductTag();
                      productTag.setCode(cell.getStringCellValue());
                      productTag.setLabel(cell.getStringCellValue());
                      productTag.setTag(cell.getStringCellValue());
                      productTag = ProductModelManager.getInstance().saveProductTag(productTag);
                    }
                    ProductAttribute a = new ProductAttribute();
                    a.setName(AccountingModelManager.product);
                    a.setValue(productTag.getTag());
                    product.add(a);
                    product.add(productTag);
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }
                // Group product 3
                if (cell.getColumnIndex() == 6) {
                  if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals(""))
               //     System.out.println("----Product Tag 3 : " + cell.getStringCellValue());
                  try {
                    ProductTag productTag = ProductModelManager.getInstance().getProductTagByCode(
                        cell.getStringCellValue());
                    if (productTag == null) {
                      productTag = new ProductTag();
                      productTag.setCode(cell.getStringCellValue());
                      productTag.setLabel(cell.getStringCellValue());
                      productTag.setTag(cell.getStringCellValue());
                      productTag = ProductModelManager.getInstance().saveProductTag(productTag);
                    }
                    ProductAttribute a = new ProductAttribute();
                    a.setName(AccountingModelManager.product);
                    a.setValue(productTag.getTag());
                    product.add(a);
                    product.add(productTag);
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }
                // ProductPriceType
                if (cell.getColumnIndex() == 7) {
                  if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals(""))
          //          System.out.println("----ProductPriceType  : " + cell.getStringCellValue());
                  try {
                    ProductPriceType productPriceType = ProductModelManager.getInstance().getProductPriceTypeByType(
                        cell.getStringCellValue());
                    if (productPriceType == null) {
                      productPriceType = new ProductPriceType();
                      productPriceType.setType(cell.getStringCellValue());
                      productPriceType.setName(cell.getStringCellValue());
                      productPriceType = ProductModelManager.getInstance().saveProductPriceType(productPriceType);
                    }
                    if (product != null && productPriceType != null) {
                      ProductPrice productPrice = new ProductPrice();
                      productPrice.setProduct(product);
                      productPrice.setProductPriceType(productPriceType);

                      if (row.getCell(8).getCellType() == Cell.CELL_TYPE_NUMERIC
                          && row.getCell(8).getNumericCellValue() >= 0) {
                        productPrice.setPrice(row.getCell(8).getNumericCellValue());
                      } else {
                        productPrice.setPrice(0);
                      }

                      if (row.getCell(9).getStringCellValue() != null
                          && !row.getCell(9).getStringCellValue().equals("")) {
                        productPrice.setCurrencyUnit(row.getCell(9).getStringCellValue());
                      } else {
                        productPrice.setCurrencyUnit("VNĐ");
                      }

                      productPrice.setUnit(product.getUnit());
                      productPrice = ProductModelManager.getInstance().saveProductPrice(productPrice);
                    }
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }
                // ProductPriceType 2
                if (cell.getColumnIndex() == 10) {
                  if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals("")) {
            //        System.out.println("----ProductPriceType 2 : " + cell.getStringCellValue());
                    try {
                      ProductPriceType productPriceType = ProductModelManager.getInstance().getProductPriceTypeByType(
                          cell.getStringCellValue());
                      if (productPriceType == null) {
                        productPriceType = new ProductPriceType();
                        productPriceType.setType(cell.getStringCellValue());
                        productPriceType.setName(cell.getStringCellValue());
                        productPriceType = ProductModelManager.getInstance().saveProductPriceType(productPriceType);
                      }
                      if (product != null && productPriceType != null) {
                        ProductPrice productPrice = new ProductPrice();
                        productPrice.setProduct(product);
                        productPrice.setProductPriceType(productPriceType);

                        if (row.getCell(11).getNumericCellValue() >= 0) {
                          productPrice.setPrice(row.getCell(11).getNumericCellValue());
                        } else {
                          productPrice.setPrice(0);
                        }

                        if (row.getCell(12).getStringCellValue() != null
                            && !row.getCell(12).getStringCellValue().equals("")) {
                          productPrice.setCurrencyUnit(row.getCell(12).getStringCellValue());
                        } else {
                          productPrice.setCurrencyUnit("VNĐ");
                        }

                        productPrice.setUnit(product.getUnit());
                        productPrice = ProductModelManager.getInstance().saveProductPrice(productPrice);
                      }
                    } catch (Exception e) {
                      e.printStackTrace();
                    }
                  }
                }
                // ProductPriceType 3
                if (cell.getColumnIndex() == 13) {
                  if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals("")) {
         //           System.out.println("----ProductPriceType 3 : " + cell.getStringCellValue());
                    try {

                      ProductPriceType productPriceType = ProductModelManager.getInstance().getProductPriceTypeByType(
                          cell.getStringCellValue());
                      if (productPriceType == null) {
                        productPriceType = new ProductPriceType();
                        productPriceType.setType(cell.getStringCellValue());
                        productPriceType.setName(cell.getStringCellValue());
                        productPriceType = ProductModelManager.getInstance().saveProductPriceType(productPriceType);
                      }
                      if (product != null && productPriceType != null) {
                        ProductPrice productPrice = new ProductPrice();
                        productPrice.setProduct(product);
                        productPrice.setProductPriceType(productPriceType);

                        if (row.getCell(14).getNumericCellValue() >= 0) {
                          productPrice.setPrice(row.getCell(14).getNumericCellValue());
                        } else {
                          productPrice.setPrice(0);
                        }

                        if (row.getCell(15).getStringCellValue() != null
                            && !row.getCell(15).getStringCellValue().equals("")) {
                          productPrice.setCurrencyUnit(row.getCell(15).getStringCellValue());
                        } else {
                          productPrice.setCurrencyUnit("VNĐ");
                        }

                        productPrice.setUnit(product.getUnit());
                        productPrice = ProductModelManager.getInstance().saveProductPrice(productPrice);
                      }
                    } catch (Exception e) {
                      e.printStackTrace();
                    }
                  }
                }
                // ProductPriceType 4
                if (cell.getColumnIndex() == 16) {
                  if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals(""))
        //            System.out.println("----ProductPriceType 4 : " + cell.getStringCellValue());
                  try {

                    ProductPriceType productPriceType = ProductModelManager.getInstance().getProductPriceTypeByType(
                        cell.getStringCellValue());
                    if (productPriceType == null) {
                      productPriceType = new ProductPriceType();
                      productPriceType.setType(cell.getStringCellValue());
                      productPriceType.setName(cell.getStringCellValue());
                      productPriceType = ProductModelManager.getInstance().saveProductPriceType(productPriceType);
                    }
                    if (product != null && productPriceType != null) {
                      ProductPrice productPrice = new ProductPrice();
                      productPrice.setProduct(product);
                      productPrice.setProductPriceType(productPriceType);

                      if (row.getCell(17).getNumericCellValue() >= 0) {
                        productPrice.setPrice(row.getCell(17).getNumericCellValue());
                      } else {
                        productPrice.setPrice(0);
                      }

                      if (row.getCell(18).getStringCellValue() != null
                          && !row.getCell(18).getStringCellValue().equals("")) {
                        productPrice.setCurrencyUnit(row.getCell(18).getStringCellValue());
                      } else {
                        productPrice.setCurrencyUnit("VNĐ");
                      }

                      productPrice.setUnit(product.getUnit());
                      productPrice = ProductModelManager.getInstance().saveProductPrice(productPrice);
                    }
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }
                int column = cell.getColumnIndex(); // Lay vi tri cot cua cell                                        
                if(column == headers.size()-1){
                	 if(product!=null){
                			try {
                    		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    		product.setDescription(String.valueOf(cell.getNumericCellValue()));
                     	 }else{
                     		product.setDescription(cell.getStringCellValue());
                     	 }
                    	
                      } catch (Exception e) {	
                      	System.out.println("Loi "+product+"   "+cell.getCellType());
                      }
                	 }
                
                	
                }
              }
            }
          }
          try {
          	System.out.println(checkProduct(product));
            if (checkProduct(product)) {
            	product.setCalculateReport(true);
            	product.setUpdatePrice(true);
            	product= ProductModelManager.getInstance().saveProduct(product);
            	System.out.println("Ma :"+product.getCode());
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }else {
					System.out.println("ko dc roi");
				}
      }

    }
  }
  
  private List<String> getListHeader(Sheet sheet) {
    List<String> list = new ArrayList<String>();
    int row = 0;
    for (Row r : sheet) {
        if (row == 0) {
            for (Cell c : r) {
                list.add(String.valueOf(c));
            }
            break;
        }
        row++;
    }
    return list;
}

  // sheet DanhMuc
  private void MapTableDanhMuc(Sheet sheet) {
    System.out.println("Table : " + sheet.getSheetName());
    String tableName = sheet.getSheetName();
    if (tableName.startsWith("DanhMuc")) {
      Iterator<Row> rowIterator = sheet.iterator();
      while (rowIterator.hasNext()) {
        // Get the row object
        Row row = rowIterator.next();//
        // Every row has columns, get the column iterator and iterate over
        // them
        Iterator<Cell> cellIterator = row.cellIterator();
        if (row.getRowNum() > 0) {
          while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();

            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
              String a1 = "";
              if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                a1 = String.valueOf(cell.getNumericCellValue());
              }else {
                if(cell.getCellType() == Cell.CELL_TYPE_STRING){
                  a1 = cell.getStringCellValue();
                }
                
              }
              if (!a1.trim().isEmpty()) {
                if (cell.getRow().getRowNum() == 0) {
                  System.out.println("---- Column name:" + cell.getStringCellValue());
                } else {
                  if (cell.getColumnIndex() == 0) {
                    // Product unit
                    System.out.println("---- Column name:" + cell.getStringCellValue());
                    try {
                      ProductUnit productUnit = LocaleModelManager.getInstance().getProductUnitByCode(
                          cell.getStringCellValue());
                      if (productUnit == null) {
                        productUnit = new ProductUnit();
                        productUnit.setCode(cell.getStringCellValue());
                        productUnit.setName(cell.getStringCellValue());
                        productUnit.setRate(100);
                        productUnit = LocaleModelManager.getInstance().saveProductUnit(productUnit);
                      }
                    } catch (Exception e) {
                      e.printStackTrace();
                    }
                  }
                  // Price
                  if (cell.getColumnIndex() == 3) {
                    try {
                      System.out.println("---- Currency :" + cell.getStringCellValue());
                      Currency currency = LocaleModelManager.getInstance().getCurrencyByCode(cell.getStringCellValue());
                      if (currency == null) {
                        currency = new Currency();
                        currency.setCode(cell.getStringCellValue());
                        currency.setName(cell.getStringCellValue());
                        currency.setRate(100);
                        currency = LocaleModelManager.getInstance().saveCurrency(currency);
                      }
                    } catch (Exception e) {
                      e.printStackTrace();
                    }
                  }

                  // Product group
                  if (cell.getColumnIndex() == 7) {
                    try {
                      System.out.println("---- ProductTag :" + cell.getStringCellValue());
                      ProductTag productTag = ProductModelManager.getInstance().getProductTagByCode(
                          cell.getStringCellValue());
                      if (productTag == null) {
                        productTag = new ProductTag();
                      }
                      productTag.setLabel(cell.getStringCellValue());
                      productTag.setCode(cell.getStringCellValue());
                      if (row.getCell(9) == null) {
                        productTag.setParent(null);
                      } else {
                        System.out.println("---- parent :" + cell.getStringCellValue());
                        String parentCode = row.getCell(9).getStringCellValue();
                        ProductTag parent = ProductModelManager.getInstance().getProductTagByCode(parentCode);
                        productTag.setParent(parent);
                      }
                      productTag = ProductModelManager.getInstance().saveProductTag(productTag);
                    } catch (Exception e) {
                      e.printStackTrace();
                    }
                  }

                  // Price
                  if (cell.getColumnIndex() == 11) {
                    try {
                      ProductPriceType productPriceType = ProductModelManager.getInstance().getProductPriceTypeByType(
                          cell.getStringCellValue());
                      System.out.println("---- ProductPriceType :" + cell.getStringCellValue());
                      if (productPriceType == null) {
                        productPriceType = new ProductPriceType();
                        productPriceType.setType(cell.getStringCellValue());
                        productPriceType.setName(cell.getStringCellValue());
                        productPriceType = ProductModelManager.getInstance().saveProductPriceType(productPriceType);
                      }
                    } catch (Exception e) {
                      e.printStackTrace();
                    }
                  }
                }
              }
              break;
            case Cell.CELL_TYPE_NUMERIC:
              // if (cell.getColumnIndex() == 0) {
              // long productId = (long) cell.getNumericCellValue() +
              // constantProductId;
              // product.setId(productId);
              // }

            }
          }
        }
      }
    }

  }

  // sheet DinhLuong
  private void MapTableDinhLuong(Sheet sheet) {
    String tableName = sheet.getSheetName();
    if (tableName.equals("DinhLuong")) {
      Iterator<Row> rowIterator = sheet.iterator();
      while (rowIterator.hasNext()) {
        // Get the row object
        Row row = rowIterator.next();//
        // Every row has columns, get the column iterator and iterate over
        // them
        Iterator<Cell> cellIterator = row.cellIterator();
        if (row.getRowNum() > 0) {
          while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();

            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
              String a1 = "";
              if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                a1 = String.valueOf(cell.getNumericCellValue());
              }else {
                if(cell.getCellType() == Cell.CELL_TYPE_STRING){
                  a1 = cell.getStringCellValue();
                }
                
              }
              if (!a1.trim().isEmpty()) {
                if (cell.getRow().getRowNum() == 0) {
                  System.out.println("---- Column name:" + cell.getNumericCellValue());
                } else {
                  if (cell.getColumnIndex() == 1) {
                    System.out.println("---- productR:" + cell.getStringCellValue());

                    try {

                      // Product product =
                      // ProductModelManager.getInstance().getProductByCode(cell.getStringCellValue());
                      List<Product> lstPro = ProductModelManager.getInstance().findProductByName(
                          cell.getStringCellValue());
                      Product product = lstPro.get(0);
                      if (product == null) {
                        product = new Product();
                        product.setCode(cell.getStringCellValue());
                        product.setName(cell.getStringCellValue());
                        product.setMaker("HKT");
                        product = ProductModelManager.getInstance().saveProduct(product);
                      }
                      Recipe recipe = RestaurantModelManager.getInstance().getRecipeByProductCode(product.getCode());
                      if (recipe == null) {
                        recipe = new Recipe();
                        recipe.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
                        recipe.setProductCode(product.getCode());
                        recipe.setName(product.getName());
                        recipe.setRecipeIngredients(new ArrayList<RecipeIngredient>());
                        recipe = RestaurantModelManager.getInstance().saveRecipe(recipe);
                      }
                      // RecipeIngredient1 (nếu recipe đã tồn tại thì vẫn bị
                      // thay đổi theo file excel)
                      List<RecipeIngredient> recipeIngredients = new ArrayList<RecipeIngredient>();
                      int i;
                      for (i = 2; i < 21; i++) {
                        RecipeIngredient recipeIngredient = getRecipeIngredient(row, i);
                        if (recipeIngredient != null)
                          recipeIngredients.add(recipeIngredient);
                        i = i + 1;
                      }
                      recipe.setRecipeIngredients(recipeIngredients);
                      recipe = RestaurantModelManager.getInstance().saveRecipe(recipe);

                    } catch (Exception e) {
                      e.printStackTrace();

                    }
                  }

                }
              }
              break;
            case Cell.CELL_TYPE_NUMERIC:
              // if (cell.getColumnIndex() == 0) {
              // long productId = (long) cell.getNumericCellValue() +
              // constantProductId;
              // product.setId(productId);
              // }

            }
          }

        }
      }
    }

  }

  private boolean checkProduct(Product product) {
    if (product == null)
      return false;
    if (product.getName() == null)
      return false;
    if (product.getCode() == null)
      return false;
    if (product.getName().trim().length() == 0)
      return false;
    if (product.getCode().trim().length() == 0)
      return false;
    return true;
  }

  public RecipeIngredient getRecipeIngredient(Row row, int cellnumber) throws Exception {
    // && cellnumber % 3 == 0
    if (row.getCell(cellnumber) != null) {
      System.out.println("---- cellnumber:" + cellnumber);
      try {
        List<Product> lstProduct;
        Product p = new Product();
        if (row.getCell(cellnumber).getCellType() == Cell.CELL_TYPE_NUMERIC) {
          lstProduct = ProductModelManager.getInstance().findProductByName(String.valueOf(row.getCell(cellnumber)));
          p = lstProduct.get(0);
        } else {
          lstProduct = ProductModelManager.getInstance()
              .findProductByName(row.getCell(cellnumber).getStringCellValue());
          p = lstProduct.get(0);
        }

        if (p.getName().equals("") || p.getCode().equals("")) {
          if (row.getCell(cellnumber).getCellType() == Cell.CELL_TYPE_NUMERIC) {
            p.setName(String.valueOf(row.getCell(cellnumber)));
            p.setCode(String.valueOf(row.getCell(cellnumber)));
          } else {
            p.setName(row.getCell(cellnumber).getStringCellValue());
            p.setCode(row.getCell(cellnumber).getStringCellValue());
          }

          p.setMaker("HKT");
          p = ProductModelManager.getInstance().saveProduct(p);
        }
        RecipeIngredient ingredient = new RecipeIngredient();
        ingredient.setProductCode(p.getCode());
        int cellNum = cellnumber + 1;
        if (row.getCell(cellNum) != null)
          if (row.getCell(cellNum).getCellType() == Cell.CELL_TYPE_NUMERIC) {
            ingredient.setQuantity(row.getCell(cellNum).getNumericCellValue());
          } else {
            ingredient.setQuantity(Double.parseDouble(row.getCell(cellNum).getStringCellValue()));
          }
        ingredient.setUnit(p.getUnit());
        return ingredient;
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    return null;
  }

  // sheet KhuVuc
  private void MapTableKhucVuc(Sheet sheet) {
    String tableName = sheet.getSheetName();
    if (tableName.equals("KhuVuc")) {
      Iterator<Row> rowIterator = sheet.iterator();
      while (rowIterator.hasNext()) {
        // Get the row object
        Row row = rowIterator.next();//
        // Every row has columns, get the column iterator and iterate over
        // them
        Iterator<Cell> cellIterator = row.cellIterator();
        if (row.getRowNum() > 0) {
          while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            String a1 = "";
            if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
              a1 = String.valueOf(cell.getNumericCellValue());
            }else {
              a1 = cell.getStringCellValue();
            }
            if (!a1.trim().isEmpty()) {
              if (cell.getRow().getRowNum() == 0) {
                System.out.println("---- Column name:" + cell.getStringCellValue());
              } else {
                Location location = null;
                if (cell.getColumnIndex() == 1) {
                  try {
                    location = RestaurantModelManager.getInstance().getLocationByCode(cell.getStringCellValue());
                    if (location == null) {
                      location = new Location();
                      location.setCode(cell.getStringCellValue());
                      location.setName(cell.getStringCellValue());
                      location = RestaurantModelManager.getInstance().saveLocation(location);
                      System.out.println("location : " + location.getCode());
                    }
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }
                if (cell.getColumnIndex() == 2) {
                  try {
                    Table table = RestaurantModelManager.getInstance().getTableByCode(cell.getStringCellValue());
                    if (table == null) {
                      table = new Table();
                      table.setCode(cell.getStringCellValue());
                      table.setLocationCode(row.getCell(1).getStringCellValue());
                      table = RestaurantModelManager.getInstance().saveTable(table);
                      System.out.println("table : " + table.getCode());
                    }
                  } catch (Exception e) {
                    e.printStackTrace();
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
