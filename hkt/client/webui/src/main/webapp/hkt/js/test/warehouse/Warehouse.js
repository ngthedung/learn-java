define([
  'jquery',
  'service/service',
  'test/Test',
  'test/Site',
  'test/Assert',
], function($, service, test, Site, Assert, data, hktData) {
  var CleanDB = new test.UnitTask({
    name: "CleanWarehouseModule",
    description: "Drop all the data in the warehouse module",
    units: [
      function() { 
        service.WarehouseService.cleanWarehouseDB() ;
        service.ProductService.cleanProductDB() ;
        service.PromotionService.cleanPromotionDB() ;
      }
    ]
  }); 
  
  var ProductPriceTypeApiCRUD = new test.UnitTask({
    name: "ProductPriceTypeApiCRUD",
    description: "create/get/update/delete a productPriceType",
    units: [
      function() {
        console.log("ProductPriceTypeApiCRUD: Start!!!") ;
        
        var productPriceType = {
          type : "Bang gia vip", organizationLoginId: "hkt", description : ""
        };
        productPriceType = service.warehouseService.productPriceType(productPriceType).data ;
        Assert.assertEquals("hkt", productPriceType.organizationLoginId) ;
        
        var productPriceTypes = service.warehouseService.getProductPriceTypes().data ;
        Assert.assertEquals(2, productPriceTypes.length) ;
        
        res = service.warehouseService.deleteProductPriceType(productPriceType) ;
        Assert.assertEquals(true, res.data) ;
          
        productPriceTypes = service.warehouseService.getProductPriceTypes().data ;
        Assert.assertEquals(1, productPriceTypes.length) ;
        
        console.log("ProductPriceTypeApiCRUD: Finish!!!") ;
      }
    ]
  });

  var ProductApiCRUD = new test.UnitTask({
    name : "ProductCRUDTask",
    description : "test call create/get/update/delete an Product",
    units : [ 
      function() {
        var product = {
          code : "galaxyS5", name : "SamSung Galaxy S5", maker : "Apple", tags : ["electronic:mobile:phone"]
        };
        var cmsNode = {
          node: {
            name : "SamSung Galaxy S5", path : "hkt/SamSung Galaxy S5", mimeType : "warehouse/products", parentId : -1, 
            parentPath : "hkt"
          }
        };
        
        var productDetail = {
          product : product, cmsNode : cmsNode
        };
        service.WarehouseService.saveProductDetail(productDetail) ;
        productDetail = service.WarehouseService.getProductDetailByCode("galaxyS5").data;
        product = productDetail.product;
        Assert.assertEquals('galaxyS5', product.code) ;
        
        service.WarehouseService.deleteProductByCode(product.code) ;
        Assert.assertNull(service.WarehouseService.getProductDetailByCode("galaxyS5").data) ;
      } 
    ]
  });
  
  var GoToWarehouseProductsScreen = new test.UnitTask({
    name: "GoToWarehouseProductsScreen",
    description: "Click on warehouse and then Products in the navigation",
    units: [
      function() { Site.Navigation.clickMenuItem("Warehouses", "Products") ; } ,
    ]
  });
  
  var CreateIPhoneProductTask = new test.UnitTask({
    name: "CreateIPhoneProductTask",
    description: "Create IPhone Product",
    units: [
      function() { Site.Workspace.tableToolbar('Code').clickButton("New"); } ,
      function() {
        var productBlock = Site.Workspace.collapsibleBlock("Product") ;
        var form = productBlock.formWithText("Code:") ;
        form.inputVal("code", "iphone6s");
        form.inputVal("name", "iphone6s");
        form.inputVal("maker", "Apple");
      },
      function() {
        Site.Workspace.toolbarWith("Product").clickButton("Save");
      }
    ]
  });
  
  var EditIPhoneProductTask = new test.UnitTask({
    name: "EditIPhoneProductTask",
    description: "Try to update IPhone Product information",
    units: [
      function() { Site.Workspace.tableToolbars('Code').clickButton("More Toolbars") ; } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Filter") ;
        controlGroup.inputVal('code', "ipho*") ;
      },
      function() { Site.Workspace.tableToolbar('Filter').clickButton("Filter") ; } ,
      function() {
        var table = Site.Workspace.tableWithHeader("Code") ;
        var row   = table.tableRowWithText("iphone6s") ;
        row.clickLink("iphone6s") ;
      },
      function() {
        var productBlock = Site.Workspace.collapsibleBlock("Product") ;
        productBlock.clickButton("Edit");
      },
      function() {
        var productBlock = Site.Workspace.collapsibleBlock("Product") ;
        var form = productBlock.formWithText("Code:") ;
        form.inputVal("name", "iphone6s(Update)") ;
      },
      function() {
        var productBlock = Site.Workspace.collapsibleBlock("Product") ;
        productBlock.clickButton("OK");
      },
      function() {
        Site.Workspace.toolbarWith("Product").clickButton("Save");
      }
    ]
  });
  
  var DeleteIPhoneProductTask = new test.UnitTask({
    name: "DeleteIPhoneProductTask",
    description: "Try to delete IPhone Product information",
    units: [
      function() { Site.Workspace.tableToolbars('Code').clickButton("More Toolbars") ; } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Filter") ;
        controlGroup.inputVal('code', "ipho*") ;
      },
      function() { Site.Workspace.tableToolbar('Filter').clickButton("Filter") ; } ,
      function() {
        var table = Site.Workspace.tableWithHeader("Code") ;
        var row   = table.tableRowWithText("iphone6s") ;
        row.clickButton("Delete") ;
      },
    ]
  });
  
  var SearchProductTask = new test.UnitTask({
    name: "SearchProductTask",
    description: "Search product",
    units: [
      function() { Site.Workspace.tableToolbars('Code').clickButton("More Toolbars") ; } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Filter") ;
        controlGroup.inputVal('code', "ipho*") ;
      },
      function() { Site.Workspace.tableToolbar('Filter').clickButton("Filter") ; } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Filter") ;
        controlGroup.inputVal('code', "") ;
      },
      
      function() { Site.Workspace.tableToolbars('Filter').clickButton("More Filter Option") ; } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Name") ;
        controlGroup.inputVal('name', "Samsung*") ;
      },
      function() { Site.Workspace.tableToolbars('Filter').clickButton("Filter") ; } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Name") ;
        controlGroup.inputVal('name', "") ;
      },
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Maker") ;
        controlGroup.inputVal('maker', "Apple*") ;
      },
      function() { Site.Workspace.tableToolbars('Filter').clickButton("Filter") ; } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Maker") ;
        controlGroup.inputVal('maker', "") ;
      },
      function() { Site.Workspace.tableToolbars('Filter').clickButton("More Toolbars") ; }
    ]
  });
  
  var WarehouseApiCRUD = new test.UnitTask({
    name : "WarehouseCRUDTask",
    description : "test call create/get/update/delete an Warehouse",
    units : [ function() {
      console.log("WarehouseCRUDTask: Start!!!");
      var warehouse = {
        warehouseId: "hkt-kho-02", name: "Kho so 1", ownerId: "hkt", address: "123 Cau Giay, Quan Cau Giay, Ha Noi"
      };
      service.WarehouseService.saveWarehouse(warehouse);
      warehouse = service.WarehouseService.getWarehouseByWarehouseId("hkt-kho-02").data;
      
      Assert.assertEquals('hkt-kho-02', warehouse.warehouseId);
      
      service.WarehouseService.deleteWarehouseByWarehouseId(warehouse.warehouseId);
      
      warehouse1 = service.WarehouseService.getWarehouseByWarehouseId("hkt-kho-02").data;
      Assert.assertNull(warehouse1);
      console.log("WarehouseApiCRUDTask: Finish!!!");
    } ]
  });
  
  var GoToWarehouseWarehousesScreen = new test.UnitTask({
    name: "GoToWarehouseWarehousesScreen",
    description: "Click on warehouse and then Warehouses in the navigation",
    units: [
      function() { Site.Navigation.clickMenuItem("Warehouses", "Warehouses") ; } ,
    ]
  });
  
  var CRUDHktKhoWarehouseTask = new test.UnitTask({
    name: "CRUDHktKhoWarehouseTask",
    description: "Create Read Update Delete HktKho Warehouse",
    units: [
      //create 
      function() { Site.Workspace.tableToolbar('Warehouse Id').clickButton("New"); } ,
      function() {
        var form = Site.PopupPanel.formWithText("Warehouse Id:") ;
        form.inputVal("warehouseId", "hkt-kho-02");
        form.inputVal("name", "Kho Ban Hang");
        form.inputVal("ownerId", "$@hkt");
        form.inputVal("address", "335 Cau Giay, quan Cau Giay, Ha Noi");
      },
      function() {
        Site.PopupPanel.formWithText("Warehouse Id:").clickButton("Save");
      },
      
      //Update 
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Warehouse Id") ;
        controlGroup.inputVal('beanFilterField', "hkt-kho-02") ;
      },
      function() {
        var table = Site.Workspace.tableWithHeader("Warehouse Id") ;
        var row   = table.tableRowWithText("hkt-kho-02") ;
        row.clickLink("hkt-kho-02") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Warehouse Id:") ;
        form.inputVal("name", "Kho Tra Hang(Update)");
      },
      function() {
        Site.PopupPanel.formWithText("Warehouse Id:").clickButton("Save");
      },
      
      //DELETE
      function() {
        var controlGroup = Site.Workspace.toolbarWith("Warehouse Id") ;
        controlGroup.inputVal('beanFilterField', "hkt-kho-02") ;
      },
      function() {
        var table = Site.Workspace.tableWithHeader("Warehouse Id") ;
        var row   = table.tableRowWithText("hkt-kho-02") ;
        row.clickButton("Delete") ;
      },
      function() {
        var controlGroup = Site.Workspace.toolbarWith("Warehouse Id") ;
        controlGroup.inputVal('beanFilterField', "") ;
      },
    ]
  });
  
  var InInventoryApiCRUD = new test.UnitTask({
    name : "InInventoryApiCRUD",
    description : "test call create/get/update/delete an InInventory",
    units : [ function() {
      console.log("InInventoryApiCRUDTask: Start!!!");
      warehouse = service.WarehouseService.getWarehouseByWarehouseId("hkt-kho-01").data;
      
      var inInventoryDetail = {
        inInventory : {
          warehouseId: warehouse.warehouseId, name: "Nhap san pham SamSung", productCode: "Galasy S5",
          inDate: "10/05/2013 08:00:00 GMT+0700", description: "Van chuyen can than"
        },
        items: [
          { quantity: "1", checkinQuantity: "1", unit: "Man Hinh", validFromDate: "", expireDate: "" },
          { quantity: "1", checkinQuantity: "1", unit: "Khung Dien Thoai", validFromDate: "", expireDate: "" }
        ]
      };
      
      service.WarehouseService.saveInInventoryDetail(inInventoryDetail) ;
      var inInventoris = service.WarehouseService.findInInventoriesByWarehouseId(warehouse.warehouseId).data ;
      Assert.assertEquals(2, inInventoris.length) ;
      
      var inInventory = inInventoris[1];
      
      var result = service.WarehouseService.deleteInInventory(inInventory);
      Assert.assertTrue(result) ;
      
      var inInventoris = service.WarehouseService.findInInventoriesByWarehouseId(warehouse.warehouseId).data ;
      Assert.assertEquals(1, inInventoris.length) ;
      
      console.log("InInventoryApiCRUDTask: Finish!!!") ;
    } ]
  });
  
  var GoToInventoryScreen = new test.UnitTask({
    name: "GoToInventoryScreen",
    description: "Click on Warehouses and then Inventory in the navigation",
    units: [
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Warehouse Id") ;
        controlGroup.inputVal('beanFilterField', "hkt-kho-01") ;
      },
      function() {
        var table = Site.Workspace.tableWithHeader("Warehouse Id") ;
        var row   = table.tableRowWithText("hkt-kho-01") ;
        row.clickButton("Inventory") ;
      },
    ]
  });
  
  var CRUDInventoryTask = new test.UnitTask({
    name: "CRUDInventoryTask",
    description: "Create, Read, Updadte, Delete Inventory Task",
    units: [
      // create
      function() { Site.Workspace.tableToolbar('Refresh').clickButton("New") ; } ,
      function() {
        var form = Site.PopupPanel.formWithText("Warehouse Code:") ;
        form.inputVal("product", "$@iphone5s") ;
        form.inputVal("inStock", "20") ;
      },
      function() { Site.PopupPanel.formWithText("Warehouse Code:").clickButton("Save") ; },
      
      // edit
      function() {
        var table = Site.Workspace.tableWithHeader("Warehouse Code") ;
        var row   = table.tableRowWithText("20") ;
        row.clickButton("Edit Inventory") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Warehouse Code:") ;
        form.inputVal("product", "iphone5s") ;
        form.inputVal("inStock", "100") ;
      },
      function() { Site.PopupPanel.formWithText("Warehouse Code:").clickButton("Save") ; },
      
      // delete
      function() {
        var table = Site.Workspace.tableWithHeader("Warehouse Code") ;
        var row   = table.tableRowWithText("100") ;
        row.clickButton("Delete") ;
      },
    ]
  });
  
  var CRUDInInventoryTask = new test.UnitTask({
    name: "CRUDInventoryTask",
    description: "Create, Read, Updadte, Delete In Inventory Task",
    units: [
      // create
      function() { Site.Workspace.tableToolbar('Refresh').clickButton("New") ; } ,
      function() {
        var form = Site.PopupPanel.formWithText("Product:") ;
        form.inputVal("product", "$@iphone5s") ;
        form.inputVal("inStock", "20") ;
      },
      function() {
        Site.PopupPanel.formWithText("Product:").clickButton("Save"); 
      },
      
      //Edit
      function() {
        var table = Site.Workspace.tableWithHeader("Product Code") ;
        var row   = table.tableRowWithText("20") ;
        row.clickButton("Edit Inventory") ;
      },
      
      function() {
        var form = Site.PopupPanel.formWithText("Product:") ;
        form.inputVal("inStock", "60") ;
      },
      function() {
        Site.PopupPanel.formWithText("Product:").clickButton("Save"); 
      },
      
      //InInventory
      function() {
        var table = Site.Workspace.tableWithHeader("Product Code") ;
        var row   = table.tableRowWithText("60") ;
        row.clickButton("In Inventory") ;
      },
      function() { Site.Workspace.tableToolbar('Refresh').clickButton("New") ; },
      function() {
        var form = Site.PopupPanel.formWithText("Quantity:") ;
        form.inputVal("quantity", "5") ;
        form.inputVal("checkinQuantity", "5") ;
        form.inputVal("validFromDate", "10/05/2013 08:00:00 GMT+0700") ;
        form.inputVal("expireDate", "11/05/2013 08:00:00 GMT+0700") ;
        form.inputVal("total", "5") ;
        form.inputVal("finalCharge", "250000") ;
      },
      function() {
        Site.PopupPanel.formWithText("Quantity:").clickButton("Save"); 
      },
    //Edit InInventory
      function() {
        var table = Site.Workspace.tableWithHeader("Valid From Date") ;
        var row   = table.tableRowWithText("250000") ;
        row.clickButton("Edit") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Quantity:") ;
        form.inputVal("quantity", "10") ;
        form.inputVal("checkinQuantity", "10") ;
      },
      function() {
        Site.PopupPanel.formWithText("Quantity:").clickButton("Save"); 
      },
    //delete InInventory
      function() {
        var table = Site.Workspace.tableWithHeader("Valid From Date") ;
        var row   = table.tableRowWithText("250000") ;
        row.clickButton("Delete") ;
      },
      //back
      function() {
        Site.Workspace.toolbarWith('Warehouse').clickButton('Inventories') ;
      },
      //OutInventory
      function() {
        var table = Site.Workspace.tableWithHeader("Product Code") ;
        var row   = table.tableRowWithText("60") ;
        row.clickButton("Out Inventory") ;
      },
    ]
  });

  
  var OutInventoryApiCRUD = new test.UnitTask({
    name : "OutInventoryApiCRUD",
    description : "test call create/get/update/delete an OutInventory",
    units : [ function() {
      console.log("OutInventoryApiCRUDTask: Start!!!");
      warehouse = service.WarehouseService.getWarehouseByWarehouseId("hkt-kho-01").data;
      
      var outInventoryDetail = {
        outInventory : {
          warehouseId: warehouse.warehouseId, name: "Nhap san pham SamSung", productCode: "Galasy S5",
          outDate: "10/05/2013 08:00:00 GMT+0700", description: "Van chuyen can than"
        },
        items: [
          { quantity: "1", checkoutQuantity: "1", unit: "Man Hinh" },
          { quantity: "1", checkoutQuantity: "1", unit: "Khung Dien Thoai" }
        ]
      };
      
      service.WarehouseService.saveOutInventoryDetail(outInventoryDetail) ;
      var outInventoris = service.WarehouseService.findOutInventoriesByWarehouseId(warehouse.warehouseId).data ;
      Assert.assertEquals(2, outInventoris.length) ;
      
      var outInventory = outInventoris[1];
      
      var result = service.WarehouseService.deleteOutInventory(outInventory);
      Assert.assertTrue(result) ;
      
      var outInventoris = service.WarehouseService.findOutInventoriesByWarehouseId(warehouse.warehouseId).data ;
      Assert.assertEquals(1, outInventoris.length) ;
      
      console.log("OutInventoryApiCRUDTask: Finish!!!");
    } ]
  });
  
  var CRUDOutInventoryTask = new test.UnitTask({
    name: "CRUDOutInventoryTask",
    description: "Create Read Update Delete Out Inventory Task",
    units: [
      //create 
      function() { Site.Workspace.tableToolbar('Refresh').clickButton("New") ; },
      function() {
        var form = Site.PopupPanel.formWithText("InInventory Id:") ;
        form.inputVal("inInventoryId", "5") ;
        form.inputVal("quantity", "10") ;
        form.inputVal("checkoutQuantity", "10") ;
        form.inputVal("validFromDate", "19/12/2013 08:08:08 GMT+0700") ;
        form.inputVal("expireDate", "20/12/2013 08:08:08 GMT+0700") ;
        form.inputVal("total", "10") ;
        form.inputVal("finalCharge", "250000") ;
      },
      function() {
        Site.PopupPanel.formWithText("Quantity:").clickButton("Save");
      },
      //Edit OutInventory
      function() {
        var table = Site.Workspace.tableWithHeader("Valid From Date") ;
        var row   = table.tableRowWithText("19/12/2013 08:08:08 GMT+0700") ;
        row.clickButton("Edit") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("InInventory Id:") ;
        form.inputVal("finalCharge", "300000") ;
      },
      function() {
        Site.PopupPanel.formWithText("Quantity:").clickButton("Save");
      },
      //Delete OutInventory
      function() {
        var table = Site.Workspace.tableWithHeader("Valid From Date") ;
        var row   = table.tableRowWithText("19/12/2013 08:08:08 GMT+0700") ;
        row.clickButton("Delete") ;
      },
      //back
      function() {
        Site.Workspace.toolbarWith('Warehouse').clickButton('Inventories') ;
      },
      //Delete Inventory
      function() {
        var table = Site.Workspace.tableWithHeader("Product Code") ;
        var row   = table.tableRowWithText("60") ;
        row.clickButton("Delete") ;
      },
      //back
      function() {
        Site.Workspace.toolbarWith('Warehouse').clickButton('Warehouse') ;
      }
    ]
  });
  
  var TaxApiCRUD = new test.UnitTask({
    name: "ProductPriceApiCRUD",
    description: "create/get/update/delete a table",
    units: [
      function() {
        console.log("TableApiCRUD: Start!!!") ;
        var tax = {
          name: "Thue hai quan", percent: "10%"
        };
        tax = service.warehouseService.saveTax(tax).data ;
        Assert.assertEquals("Thue hai quan", tax.name) ;
        
        var taxs = service.warehouseService.getTaxs().data ;
        Assert.assertEquals(2, taxs.length) ;
        
        res = service.warehouseService.deleteTax(tax) ;
        Assert.assertEquals(true, res.data) ;
        
        taxs = service.warehouseService.getTaxs().data ;
        Assert.assertEquals(1, taxs.length) ;
        
        console.log("TableApiCRUD: Finish!!!") ;
      }
    ]
  });
  
  var GoToWarehouseProductPriceTypesScreen = new test.UnitTask({
    name: "GoToWarehouseProductPriceTypesScreen",
    description: "Click on warehouse and then ProductPriceTypes in the navigation",
    units: [
      function() { Site.Navigation.clickMenuItem("Warehouses", "PricesConfig") ; } ,
    ]
  });
  
  var CRUDProductPriceTypeTask = new test.UnitTask({
    name: "CRUDProductPriceTypeTask",
    description: "Create Retrive Update Delete ProductPriceType Task",
    units: [
      //Create a ProductPriceType
      function() { Site.Workspace.tableToolbar('Type').clickButton("New") ; } ,
      function() {
        var form = Site.PopupPanel.formWithText("Type:") ;
        form.inputVal("type", "Bang gia dac biet");
        form.inputVal("organizationLoginId", "$@hkt");
        form.inputVal("description", "Khang hang thuong suyen");
      },
      function() { Site.PopupPanel.formWithText("Type:").clickButton("Save") ; },
      
      //Update a ProductPriceType
      function() {
        var table = Site.Workspace.tableWithHeader("Type") ;
        var row   = table.tableRowWithText("Bang gia dac biet") ;
        row.clickLink("Bang gia dac biet") ;
      },
      
      function() {
        var form = Site.PopupPanel.formWithText("Type:") ;
        form.inputVal("type", "Bang gia dac biet(update)");
        form.inputVal("description", "Khang hang thuong suyen(update)");
      },
      
      function() { Site.PopupPanel.formWithText("Type:").clickButton("Save") ; },
      
      //Delete a row in ProductPriceTypes
      function() {
        var table = Site.Workspace.tableWithHeader("Type") ;
        var row = table.tableRowWithText("Bang gia dac biet(update)") ;
        row.clickButton("Delete") ;
      },
      
      function() {  Site.Workspace.tableToolbar("Type").clickButton("Refresh") ; },
      
      //Create Product Price
      function() {
        var table = Site.Workspace.tableWithHeader("Type") ;
        var row   = table.tableRowWithText("Bang gia USD") ;
        row.clickButton("Product Prices");
      },
      
      function() { Site.Workspace.tableToolbar('Refresh').clickButton("New") ; },
      function() {
        var form = Site.PopupPanel.formWithText("Unit:") ;
        form.inputVal("productPriceType", "$@Bang gia USD") ;
        form.inputVal("product", "$@iphone5s") ;
        form.inputVal("unit", "chiec") ;
        form.inputVal("price", "10000") ;
        form.inputVal("currencyUnit", "USD") ;
        form.inputVal("minQuantity", "1") ;
        form.inputVal("maxQuantity", "100") ;
        form.inputVal("tax", "$@VAT") ;
        form.inputVal("description", "hang xach tay Nhat Ban") ;
      },
      function() {  Site.PopupPanel.formWithText("Unit:").clickButton("Save") ;  },
      
      //Update Product Price
      function() {
        var table = Site.Workspace.tableWithHeader("Price") ;
        var row   = table.tableRowWithText("10000") ;
        row.clickButton("Edit");
      },
      function() {
        var form = Site.PopupPanel.formWithText("Unit:") ;
        form.inputVal("unit", "Hop") ;
        form.inputVal("minQuantity", "1") ;
        form.inputVal("maxQuantity", "200") ;
        form.inputVal("description", "hang xach tay Nhat Ban (update)") ;
      },
      function() {  Site.PopupPanel.formWithText("Unit:").clickButton("Save") ;  },
      
      //Delete Product Price
      function() {
        var table = Site.Workspace.tableWithHeader("Price") ;
        var row   = table.tableRowWithText("10000") ;
        row.clickButton("Delete");
      },
      //Back
      function() {  Site.Workspace.toolbarWith('Product Prices').clickButton("Product Prices Config") ; },
      //Create ProductTags
      function() { Site.Workspace.tableToolbar('Label').clickButton("New") ; } ,
      function() {
        var form = Site.PopupPanel.formWithText("Label:") ;
        form.inputVal("label", "Label-test");
        form.inputVal("tag", "Tag-test");
      },
      function() { Site.PopupPanel.formWithText("Label:").clickButton("Save") ; },
      //Edit ProductTags
      function() {
        var table = Site.Workspace.tableWithHeader("Label") ;
        var row   = table.tableRowWithText("Label-test") ;
        row.clickLink("Tag-test") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Label:") ;
        form.inputVal("label", "Label-edit");
        form.inputVal("tag", "Tag-edit");
      },
      function() { Site.PopupPanel.formWithText("Label:").clickButton("Save") ; },
    //Delete ProductTags
      function() {
        var table = Site.Workspace.tableWithHeader("Label") ;
        var row   = table.tableRowWithText("Label-edit") ;
        row.clickButton("Delete");
      },
    ]
  }); 
  
  var GoToTaxsScreen = new test.UnitTask({
    name: "GoToTaxsScreen",
    description: "Click on tax and then Taxs in the navigation",
    units: [
      function() { Site.Navigation.clickMenuItem("Warehouses", "Taxs") ; } ,
    ]
  });
  
  var CRUDTaxTask = new test.UnitTask({
    name: "CRUDTaxTask",
    description: "Create Retrive Update Delete Tax Task",
    units: [
      //Create a Tax
      function() { Site.Workspace.tableToolbar('Refresh').clickButton("New"); } ,
      function() {
        var form = Site.PopupPanel.formWithText("Name:") ;
        form.inputVal("name", "Thue nhu nhap ca nhan");
        form.inputVal("percent", 5);
      },
      function() { Site.PopupPanel.formWithText("Name:").clickButton("Save") ; },
      
      //Update a Tax
      function() {
        var table = Site.Workspace.tableWithHeader("Name") ;
        var row   = table.tableRowWithText("Thue nhu nhap ca nhan") ;
        row.clickLink("Thue nhu nhap ca nhan") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Name:") ;
        form.inputVal("name", "Thue nhu nhap ca nhan (update)");
        form.inputVal("percent", 10);
      },
      function() { Site.PopupPanel.formWithText("Name:").clickButton("Save") ; },
      
      //Delete a row in Tables
      function() {
        var table = Site.Workspace.tableWithHeader("Name") ;
        var row = table.tableRowWithText("Thue nhu nhap ca nhan (update)") ;
        row.clickButton("Delete") ;
      },
      
      function() {  Site.Workspace.tableToolbar("Refresh").clickButton("Refresh") ; }
    ]
  });
  
  var GoToWarehousePromotionScreen = new test.UnitTask({
    name: "GoToWarehousePromotionScreen",
    description: "Click on warehouse and then Promotion in the navigation",
    units: [
      function() { Site.Navigation.clickMenuItem("Warehouses", "Promotions") ; } ,
    ]
  });
  
  var CRUDPromotionTask = new test.UnitTask({
    name: "CRUDPromotionTask",
    description: "Create Retrive Update Delete Promotion Task",
    units: [
      //Create a Tax
      function() { Site.Workspace.tableToolbar('Refresh').clickButton("New"); } ,
      function() {
        var form = Site.Workspace.formWithText("From Date:") ;
        form.inputVal("name", "Khuyen Mai Demo");
        form.inputVal("organizationLoginId", "$@hkt");
        form.inputVal("fromDate", "07/01/2014 00:00:00 GMT+0700");
        form.inputVal("toDate", "07/01/2014 00:00:00 GMT+0700");
        form.inputVal("status", "Planned");
        form.inputVal("maxUsePerCustomer", "100");
        form.inputVal("discountRate", "100");
        form.inputVal("discount", "100");
        form.inputVal("appliedToMinExpense", "50");
        form.inputVal("maxExpense", "50");
        form.inputVal("currencyUnit", "$@VND");
        form.inputVal("description", "demo");
      },
      //Promotion customer target
      function() {
        Site.Workspace.tableToolbar('Customer Group').clickButton("New Customer Target");
        var form = Site.PopupPanel.formWithText("Customer Group:") ;
        form.inputVal("customerGroup", "hkt/employee/cntt");
        Site.PopupPanel.formWithText("Customer Group:").clickButton("Save");
      },
      
    //Promotion location target
      function() {
        Site.Workspace.tableToolbar('Location').clickButton("New Location Target");
        var form = Site.PopupPanel.formWithText("Location:") ;
        form.inputVal("location", "cau giay");
        Site.PopupPanel.formWithText("Location:").clickButton("Save");
      },
    //Promotion product target
      function() {
        Site.Workspace.tableToolbar('Product Type').clickButton("New Product Target");
        var form = Site.PopupPanel.formWithText("Product Identifier ID:") ;
        form.inputVal("productIdentifierId", "mobile:samsung:galaxy");
        form.inputVal("productTargetType", "ByProduct");
        Site.PopupPanel.formWithText("Product Identifier ID:").clickButton("Save");
      },

      //save all
      function() {
        Site.Workspace.toolbarWith('Promotions').clickButton("Save") ;
      },
      function() {  Site.Workspace.tableToolbar("Refresh").clickButton("Refresh") ; }
    ]
  });
  
  var warehouse = {
    module: 'warehouse',
    CleanDB: CleanDB,
    createScenario: function(name) {
      var Scenario = new test.UnitTask({
        name: "WarehouseScenario " + name,
        description: "Create a minimum set of data for the module",
        units: [
          function() { 
            var jsonRes = 'scenario/' + name + '/product.json' ;
            var data = service.Server.syncGETResource(jsonRes, 'json') ;
            if(data != null) {
              service.ProductService.createScenario(data) ;
            }
            
            var jsonRes = 'scenario/' + name + '/warehouse.json' ;
            var data = service.Server.syncGETResource(jsonRes, 'json') ;
            if(data != null) {
              service.WarehouseService.createScenario(data) ;
            }
            
            var jsonResPromotion = 'scenario/' + name + '/promotion-scenario.json' ;
            var dataPromotion = service.Server.syncGETResource(jsonResPromotion, 'json') ;
            if (dataPromotion != null) {
              service.PromotionService.createScenarioPromotion(dataPromotion) ;
            }
          }
        ]
      }); 
      return Scenario ;
    },
    
    Service: {
      api: [ 
        ProductApiCRUD, WarehouseApiCRUD, InInventoryApiCRUD , OutInventoryApiCRUD, ProductPriceTypeApiCRUD, 
        TaxApiCRUD
      ]
    },
    
    UI: {
      Product: test.Suite.extend({
        name: 'Product',
        description: "create/update/delete/find Product" ,
        unitTasks: [ 
          GoToWarehouseProductsScreen,CreateIPhoneProductTask,
          EditIPhoneProductTask, DeleteIPhoneProductTask, 
          SearchProductTask,
        ],
      }), 
      
      Warehouse: test.Suite.extend({
        name: 'Warehouse',
        description: "create/update/delete/find Warehouse" ,
        unitTasks: [ 
          GoToWarehouseWarehousesScreen,
          CRUDHktKhoWarehouseTask,
          GoToInventoryScreen, 
          CRUDInventoryTask,
          CRUDInInventoryTask, 
          CRUDOutInventoryTask
        ],
      }),
      
      ProductPriceType: test.Suite.extend({
        name: 'ProductPrice',
        description: "create/update/delete/find productPrice" ,
        unitTasks: [ 
          GoToWarehouseProductPriceTypesScreen, CRUDProductPriceTypeTask
        ],
      }),
      
      Tax: test.Suite.extend({
        name: 'Tax',
        description: "create/update/delete/find Tax" ,
        unitTasks: [
          GoToTaxsScreen, CRUDTaxTask
        ],
      }),
      
      Promotion: test.Suite.extend({
        name: 'Promotion',
        description: "create/update/delete/find Promotion" ,
        unitTasks: [
          GoToWarehousePromotionScreen, CRUDPromotionTask
        ],
      }),
    }
  };
  
  return warehouse ;
});
