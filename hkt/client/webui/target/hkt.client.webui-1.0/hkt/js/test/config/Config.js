define([ 
  'jquery', 
  'service/service', 
  'test/Test', 
  'test/Site', 
  'test/Assert'
],function($, service, test, Site, Assert) {
  var CleanDB = new test.UnitTask({
    name: "CleanConfigModule",
    description: "Drop all the data in the config module",
    units: [
      function() { 
        service.LocaleService.cleanLocaleDB() ;
      }
    ]
  }); 
  
  var CountryApiCRUD = new test.UnitTask({
    name: "CountryApiCRUD",
    description: "create/get/update/delete a country",
    units: [
      function() { 
      }
    ]
  });
  
  var GoToCountriesScreen = new test.UnitTask({
    name: "GoToCurrenciesScreen",
    description: "Click on core and then Currencies in the navigation",
    units: [
      function() { Site.Navigation.clickMenuItem("Admin", "Configs") ; } ,
      function() { Site.Workspace.toolbarWith("Countries").clickButton("Countries") ; }
    ]
  });
  
  var CRUDCountryTask = new test.UnitTask({
    name: "CountryTask",
    description: "Create Retrive Update Delete Currency Task",
    units: [
      //Create a Country
      function() { Site.Workspace.tableToolbar('Refresh').clickButton("New") ; } ,
      function() {
        var countryBlock = Site.Workspace.collapsibleBlock("Country") ;
        var form = countryBlock.formWithText("Name:") ;
        form.inputVal("index", 2) ;
        form.inputVal("code", "JP") ;
        form.inputVal("name", "Japan") ;
        form.inputVal("flag", "Nhật bản") ;
        form.inputVal("description", "Nước Nhật") ;
      },
      
      function() { Site.Workspace.toolbarWith("Save").clickButton("Save") ; },
      
      //Delete a row in Tables
      function() {
        var table = Site.Workspace.tableWithHeader("Name") ;
        var row = table.tableRowWithText("Japan") ;
        row.clickButton("Delete") ;
      },
      
      //Edit a Country
      function() {
        var table = Site.Workspace.tableWithHeader("Name") ;
        var row = table.tableRowWithText("Việt Nam") ;
        row.clickLink("VN") ;
      },
      
      function() {
        var countryBlock = Site.Workspace.collapsibleBlock("Country") ;
        countryBlock.toolbarWith("Edit").clickButton("Edit") ;
        var form = countryBlock.formWithText("Name:") ;
        form.inputVal("flag", "Quốc kì Việt Nam (update)") ;
        form.inputVal("description", "Việt Nam (update)") ;
      },
      
      function() { Site.Workspace.tableToolbar('New City').clickButton("New") ; } ,
      function() {
        var form = Site.PopupPanel.formWithText("Name:") ;
        form.inputVal("index", 6) ;
        form.inputVal("code", "TT.Hue") ;
        form.inputVal("name", "Thừa Thiên Huế");
        form.inputVal("description", "Thành phố Thừa Thiên Huế");
      },
      function() { Site.PopupPanel.formWithText("Name:").clickButton("Save") ; },
      
      function() {
        var table = Site.Workspace.tableWithHeader("Name City") ;
        var row = table.tableRowWithText("Hà Nội") ;
        row.clickLink("HN") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Name:") ;
        form.inputVal("description", "Thủ Đô Hà Nội");
      },
      function() { Site.PopupPanel.formWithText("Name:").clickButton("Save") ; },
      
      function() { Site.Workspace.tableToolbar('New Province').clickButton("New") ; } ,
      function() {
        var form = Site.PopupPanel.formWithText("Name:") ;
        form.inputVal("index", 5) ;
        form.inputVal("code", "HT") ;
        form.inputVal("name", "Hà Tây");
        form.inputVal("description", "Tỉnh Hà Tây hướng tây của Hà Nội");
      },
      function() { Site.PopupPanel.formWithText("Name:").clickButton("Save") ; },

      function() { Site.Workspace.toolbarWith("Save").clickButton("Save") ; },
      //Review
      function() {
        var table = Site.Workspace.tableWithHeader("Name") ;
        var row = table.tableRowWithText("Việt Nam") ;
        row.clickLink("VN") ;
      },
      
      function() {
        var table = Site.Workspace.tableWithHeader("Name City") ;
        var row = table.tableRowWithText("Thừa Thiên Huế") ;
        row.clickButton("Delete") ;
      },
      
      function() {
        var table = Site.Workspace.tableWithHeader("Name Province") ;
        var row = table.tableRowWithText("Hà Tây") ;
        row.clickButton("Delete") ;
      },
      
      function() { Site.Workspace.toolbarWith("Save").clickButton("Save") ; },

    ]
  });
  
  var CurrencyApiCRUD = new test.UnitTask({
    name: "CurrencyApiCRUD",
    description: "create/get/update/delete a currency",
    units: [
      function() { 
      }
    ]
  });
  
  var GoToCurrenciesScreen = new test.UnitTask({
    name: "GoToCurrenciesScreen",
    description: "Click on core and then Currencies in the navigation",
    units: [
      function() { Site.Navigation.clickMenuItem("Admin", "Configs") ; } ,
      function() { Site.Workspace.toolbarWith("Countries").clickButton("Currencies") ; }
    ]
  });
  
  var CRUDCurrencyTask = new test.UnitTask({
    name: "CRUDCurrencyTask",
    description: "Create Retrive Update Delete Currency Task",
    units: [
      //Create a Currency
      function() { Site.Workspace.tableToolbar('Refresh').clickButton("New") ; } ,
      function() {
        var form = Site.PopupPanel.formWithText("Name:") ;
        form.inputVal("code", "APY") ;
        form.inputVal("name", "APY");
        form.inputVal("priority", 3);
        form.inputVal("rate", 3);
        form.inputVal("description", "Nhat");
      },
      function() { Site.PopupPanel.formWithText("Name:").clickButton("Save") ; },
      
      //Update a Currency
      function() {
        var table = Site.Workspace.tableWithHeader("Name") ;
        var row   = table.tableRowWithText("APY") ;
        row.clickLink("APY") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Name:") ;
        form.inputVal("description", "Đơn vị tiền tệ Quốc gia Nhật (Update)");
      },
      function() { Site.PopupPanel.formWithText("Name:").clickButton("Save") ; },
      
      function() {  Site.Workspace.tableToolbar("Refresh").clickButton("Refresh") ; }
    ]
  });
  
  var LanguageApiCRUD = new test.UnitTask({
    name: "LanguageApiCRUD",
    description: "create/get/update/delete a language",
    units: [
      function() { 
      }
    ]
  });
  
  var GoToLanguagesScreen = new test.UnitTask({
    name: "GoToLanguagesScreen",
    description: "Click on tax and then Languages in the navigation",
    units: [
      function() { Site.Navigation.clickMenuItem("Admin", "Configs") ; } ,
      function() { Site.Workspace.toolbarWith("Countries").clickButton("Languages") ; }
    ]
  });
  
  var CRUDLanguageTask = new test.UnitTask({
    name: "CRUDLanguageTask",
    description: "Create Retrive Update Delete Language Task",
    units: [
      //Create a Language
      function() { Site.Workspace.tableToolbar('Refresh').clickButton("New"); } ,
      function() {
        var form = Site.PopupPanel.formWithText("Name:") ;
        form.inputVal("priority", 4) ;
        form.inputVal("code", "jp") ;
        form.inputVal("label", "Japan");
        form.inputVal("description", "Quốc gia Nhật");
      },
      function() { Site.PopupPanel.formWithText("Name:").clickButton("Save") ; },
      
      //Update a Language
      function() {
        var table = Site.Workspace.tableWithHeader("Name") ;
        var row   = table.tableRowWithText("Japan") ;
        row.clickLink("jp") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Name:") ;
        form.inputVal("description", "Quốc gia Nhật (Update)");
      },
      function() { Site.PopupPanel.formWithText("Name:").clickButton("Save") ; },
      
      //Delete a row in Tables
      
      function() {  Site.Workspace.tableToolbar("Refresh").clickButton("Refresh") ; }
    ]
  });
  
  var config = {
    module: 'config',
    
    CleanDB: CleanDB,
    
    createScenario: function() {
      var Scenario = new test.UnitTask({
        name: "ConfigScenario ",
        description: "Create a minimum set of data for the module",
        units: [
          function() { 
            service.LocaleService.createScenario() ;
            service.GenericOptionService.createScenario() ;
          }
        ]
      }); 
      return Scenario ;
    },
    
    Service: {
      api: [
        CountryApiCRUD, CurrencyApiCRUD, LanguageApiCRUD
      ]
    },
    
    UI: {
      
      Country: test.Suite.extend({
        name: 'Tax',
        description: "create/update/delete/find Country" ,
        unitTasks: [
          GoToCountriesScreen, CRUDCountryTask
        ],
      }),
      
      Currency: test.Suite.extend({
        name: 'Tax',
        description: "create/update/delete/find Currency" ,
        unitTasks: [
          GoToCurrenciesScreen, CRUDCurrencyTask
        ],
      }),
      
      Language: test.Suite.extend({
        name: 'Tax',
        description: "create/update/delete/find Language" ,
        unitTasks: [
          GoToLanguagesScreen, CRUDLanguageTask
        ],
      }), 
    }
  };
  return config ;
});
