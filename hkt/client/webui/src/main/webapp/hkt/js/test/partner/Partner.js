define([
  'jquery',
  'service/service',
  'test/Test',
  'test/Site',
  'test/Assert',
], function($, service, test, Site, Assert) {
  var CleanDB = new test.UnitTask({
    name: "CleanPartnerModule",
    description: "Drop all the data in the partner module",
    units: [
      function() { 
        service.CustomerService.cleanPartnerDB() ;
        service.SupplierService.cleanPartnerDB() ;
      }
    ]
  }); 
  
  var CustomerApiCRUD = new test.UnitTask({
    name : "CustomerCRUDApiTask",
    description : "test call create/get/update/delete an Customer",
    units : [ function() {
      console.log("CustomerCRUDTask: Start!!!") ;
      var customer = {
        loginId : 'muoiot', organizationLoginId: 'hkt'
      };
      customer = service.CustomerService.saveCustomer(customer).data ;
      Assert.assertEquals('muoiot', customer.loginId) ;
      
      res = service.CustomerService.deleteCustomer(customer) ;
      Assert.assertEquals(true, res.data) ;
      console.log("CustomerApiCRUDTask: Finish!!!") ;
    }]
  });
  
  var SupplierApiCRUD = new test.UnitTask({
    name : "SupplierCRUDApiTask",
    description : "test call create/get/update/delete an Supplier",
    units : [ function() {
      console.log("SupplierCRUDTask: Start!!!") ;
      var supplier = {
        supplierLoginId: 'tranAnh', organizationLoginId: 'hkt'
      };
      supplier = service.SupplierService.saveSupplier(supplier).data ;
      Assert.assertEquals('tranAnh', supplier.supplierLoginId) ;
      
      res = service.SupplierService.deleteSupplier(supplier) ;
      Assert.assertEquals(true, res.data) ;
      console.log("SupplierApiCRUDTask: Finish!!!") ;
    }]
  });
  
  var PromotionApiCRUD = new test.UnitTask({
    name : "PromotionCURDApiTaks",
    description : "test call create/get/update/delete an Promotion",
    units : [ function() {
      console.log("PromotionCRUDTask: Start!!!") ;
      var promotion = {
        name: 'khuyen mai', organizationLoginId: 'hkt', status: 'Planned'
      };
      promotion = service.PromotionService.savePromotion(promotion).data ;
      Assert.assertEquals('khuyen mai', promotion.name) ;
      
      res = service.PromotionService.deletePromotion(promotion) ;
      Assert.assertEquals(true, res.data) ;
      console.log("PromotionApiCRUDTask: Finish!!!") ;
    }]
  });
  
  var GoToPartnerSupplierScreen = new test.UnitTask({
    name: "GoToPartnerSupplierScreen",
    description: "Click on partner and then supplier in the navigation",
    units: [
      function() { Site.Navigation.clickMenuItem("Partner", "Suppliers") ; } ,
    ]
  });
  
  var CreateSupplierTask = new test.UnitTask({
    name: "CreateSupplierTask",
    description: "Create Supplier",
    units: [
      function() { Site.Workspace.tableToolbar('Login Id').clickButton("New"); } ,
      function() {
        var form = Site.PopupPanel.formWithText("Login Id:") ;
        form.inputVal("loginId", "$@thang");
        form.inputVal("organizationLoginId", "$@hkt");
      },
      function() {
        Site.PopupPanel.formWithText("Login Id:").clickButton("Save");
      }
    ]
  });
  
  var LinkSupplierTask = new test.UnitTask({
    name: "LinkSupplierTask",
    description: "test link supplier",
    units: [
      function() {
        var table = Site.Workspace.tableWithHeader("Login Id") ;
        var row   = table.tableRowWithText("thang") ;
        row.clickLink("thang") ;
      },
      function() {
        Site.Workspace.toolbarWith("Back").clickButton("Back");
      },
      function() {
        var table = Site.Workspace.tableWithHeader("Login Id") ;
        var row   = table.tableRowWithText("thang") ;
        row.clickLink("hkt") ;
      },
      function() {
        Site.Workspace.toolbarWith("Back").clickButton("Back");
      }
    ]
  });
  
  var CreateSuppliedProductTask = new test.UnitTask({
    name: "CreateSupplierProductTask",
    description: "Create,edit,delete SuppliedProduct",
    units: [
      function() {
        var table = Site.Workspace.tableWithHeader("Login Id") ;
        var row   = table.tableRowWithText("thang") ;
        row.clickButton("Supplied Product") ;
      },
     function() { Site.Workspace.tableToolbar('Login Id').clickButton("New") ; } ,
     function() {
       var form = Site.PopupPanel.formWithText("Code:") ;
       form.inputVal("code", "PRO2434") ;
       form.inputVal("name", "samsung galaxy S IV") ;
       form.inputVal("lastPrice", "9000000") ;
       form.inputVal("note", "nhap dien thoai samsung galaxy s4") ;
     },
     function() {
       Site.PopupPanel.formWithText("Code:").clickButton("Save");
     },
     function() {
       var table = Site.Workspace.tableWithHeader("Code") ;
       var row   = table.tableRowWithText("PRO2434") ;
       row.clickButton("Edit") ;
     },
     function() {
       var form = Site.PopupPanel.formWithText("Code:") ;
       form.inputVal("lastPrice", "8500000") ;
       form.inputVal("note", "nhap dien thoai samsung galaxy s4 (edit)") ;
     },
     function() {
       Site.PopupPanel.formWithText("Code:").clickButton("Save");
     },
     function() {
       var table = Site.Workspace.tableWithHeader("Code") ;
       var row   = table.tableRowWithText("PRO2434") ;
       row.clickButton("Price History") ;
     },
     function() { Site.Workspace.toolbarWith("Supplied Product").clickButton("Supplied Product") ;},
     function() {
       var table = Site.Workspace.tableWithHeader("Code") ;
       var row   = table.tableRowWithText("PRO2434") ;
       row.clickButton("Delete") ;
     },
     function() { Site.Workspace.toolbarWith("Suppliers").clickButton("Suppliers") ;},
     function() {
       var table = Site.Workspace.tableWithHeader("Login Id") ;
       var row   = table.tableRowWithText("thang") ;
       row.clickButton("Delete") ;
     },
   ]
  });
  
  var GoToPartnerCustomerScreen = new test.UnitTask({
    name: "GoToPartnerCustomerScreen",
    description: "Click on partner and then customer in the navigation",
    units: [
      function() { Site.Navigation.clickMenuItem("Partner", "Customers") ; } ,
    ]
  });
  
  var CreateCustomerTask = new test.UnitTask({
    name: "CreateCustomerTask",
    description: "Create Customer",
    units: [
      function() { Site.Workspace.toolbarWith('Login Id').clickButton("New"); } ,
      function() {
        var form = Site.PopupPanel.formWithText("Login Id:") ;
        form.inputVal("loginId", "$@thang");
        form.inputVal("organizationLoginId", "$@hkt");
      },
      function() {
        Site.PopupPanel.formWithText("Login Id:").clickButton("Save");
      }
    ]
  });
  
  var CreateCustomerPointTask = new test.UnitTask({
    name: "CreateCustomerPointTask",
    description: "Create,edit,delete Point",
    units: [
      function() {
        var table = Site.Workspace.tableWithHeader("Login Id") ;
        var row   = table.tableRowWithText("thang") ;
        row.clickButton("Point") ;
      },
     function() { Site.Workspace.toolbarWith('Date Execute').clickButton("New Item") ; } ,
     function() {
       var form = Site.PopupPanel.formWithText("Point:") ;
       form.inputVal("pointConversionRuleId", "25") ;
       form.inputVal("point", "50") ;
       form.inputVal("description", "them point") ;
     },
     function() {
       Site.PopupPanel.formWithText("Point:").clickButton("Save");
     },
     function() {
       var table = Site.Workspace.tableWithHeader("Point Conversion Rule") ;
       var row   = table.tableRowWithText("25") ;
       row.clickButton("Edit PointTransaction Item") ;
     },
     function() {
       var form = Site.PopupPanel.formWithText("Point:") ;
       form.inputVal("description", "sua point") ;
     },
     function() {
       Site.PopupPanel.formWithText("Point:").clickButton("Save");
     },
     function() {
       var table = Site.Workspace.tableWithHeader("Point Conversion Rule") ;
       var row   = table.tableRowWithText("25") ;
       row.clickButton("Delete") ;
     },
     function() { Site.Workspace.toolbarWith("Customers").clickButton("Customers") ; },
   ]
  });
  
  var CreateCustomerCreditTask = new test.UnitTask({
    name: "CreateCustomerCreditTask",
    description: "Create,edit,delete Credit",
    units: [
      function() {
        var table = Site.Workspace.tableWithHeader("Login Id") ;
        var row   = table.tableRowWithText("thang") ;
        row.clickButton("Credit") ;
      },
     function() { Site.Workspace.toolbarWith('Date Execute').clickButton("New Item") ; } ,
     function() {
       var form = Site.PopupPanel.formWithText("Amount:") ;
       form.inputVal("amount", "50") ;
       form.inputVal("description", "them credit") ;
     },
     function() {
       Site.PopupPanel.formWithText("Amount:").clickButton("Save");
     },
     function() {
       var table = Site.Workspace.tableWithHeader("Amount") ;
       var row   = table.tableRowWithText("50") ;
       row.clickButton("Edit CreditTransaction Item") ;
     },
     function() {
       var form = Site.PopupPanel.formWithText("Amount:") 
       form.inputVal("description", "sua credit") ;
     },
     function() {
       Site.PopupPanel.formWithText("Amount:").clickButton("Save");
     },
     function() {
       var table = Site.Workspace.tableWithHeader("Amount") ;
       var row   = table.tableRowWithText("sua credit") ;
       row.clickButton("Delete") ;
     },
     function() { Site.Workspace.toolbarWith("Customers").clickButton("Customers") ; },
     function() {
       var table = Site.Workspace.tableWithHeader("Login Id") ;
       var row   = table.tableRowWithText("thang") ;
       row.clickButton("Delete") ;
     },
   ]
  });
  
  var GoToPointConversionRuleScreen = new test.UnitTask({
    name: "GoToPointConversionRuleScreen",
    description: "Click on partner and then PointConversionRule in the navigation",
    units: [
      function() { Site.Navigation.clickMenuItem("Partner", "Point rules") ; } ,
    ]
  });
  
  
  var CRUDPointConversionRuleTask = new test.UnitTask({
    name: "DeletePointConversionRuleTask",
    description: "Delete PointConversionRule Used",
    units: [
      //create
      function() {
        Site.Workspace.tableToolbar("Refresh").clickButton("New") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Name:") ;
        form.inputVal('name', "Tỷ lệ dành cho KH vip");
        form.inputVal('organizationLoginId', "$@hkt");
        form.inputVal('status', 'Active');
        form.inputVal('minPointToTrigger', '10000');
        form.inputVal('pointToCreditRatio', '0.001');
      },
      function() { Site.PopupPanel.formWithText("Name:").clickButton("Save") ; },
      
      //Edit
      function() {
        var table = Site.Workspace.tableWithHeader("Name") ;
        var row   = table.tableRowWithText("Tỷ lệ dành cho KH vip") ;
        row.clickLink("Tỷ lệ dành cho KH vip") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Name:") ;
        form.inputVal('status', 'InActive');
      },
      function() { Site.PopupPanel.formWithText("Name").clickButton("Save") ; },
      
      //Delete
      function() {
        var table = Site.Workspace.tableWithHeader("Name") ;
        var row   = table.tableRowWithText("Tỷ lệ dành cho KH vip") ;
        row.clickButton("Delete") ;
      },
    ]
  });
  
  var partner = {
    module: 'partner',
    
    CleanDB: CleanDB,
    
    createScenario: function(name) {
      var Scenario = new test.UnitTask({
        name: "PartnerScenario " + name,
        description: "Create a minimum set of data for the partner module",
        units: [
          function() { 
            var jsonRes = 'scenario/' + name + '/customer-scenario.json' ;
            var data = service.Server.syncGETResource(jsonRes, 'json') ;
            if(data != null) {
              service.CustomerService.createScenarioCustomer(data) ;
            }
            
            var jsonResSup = 'scenario/' + name + '/supplier-scenario.json' ;
            var dataSup = service.Server.syncGETResource(jsonResSup, 'json') ;
            if(dataSup != null) {
              service.SupplierService.createScenarioSupplier(dataSup) ;
            }
            
          }
        ]
      }); 
      return Scenario ;
    },
    
    Service: {
      api: [ CustomerApiCRUD , SupplierApiCRUD , PromotionApiCRUD ]
    },
    
    UI: {
      Supplier: test.Suite.extend({
        name: 'Supplier',
        description: "create/update/delete/find supplier" ,
        unitTasks: [
          GoToPartnerSupplierScreen, CreateSupplierTask,/* LinkSupplierTask,*/ CreateSuppliedProductTask
        ],
      }),
      
      Customers: test.Suite.extend({
        name: 'Customers',
        description: "create/update/delete/find customers" ,
        unitTasks: [
          GoToPartnerCustomerScreen, CreateCustomerTask, CreateCustomerPointTask, CreateCustomerCreditTask
        ],
      }),
      
      
      PointConversionRule: test.Suite.extend({
        name: 'PointConversionRule',
        discription: "create/ read/ update/ delete PointConversionRule",
        unitTasks: [
          GoToPointConversionRuleScreen, CRUDPointConversionRuleTask
        ],
      })
    }
  };
  
  return partner ;
});
