define([
  'jquery',
  'service/service',
  'test/Test',
  'test/Site',
  'test/Assert',
], function($, service, test, Site, Assert) {
  
  var CleanDB = new test.UnitTask({
    name: "CleanRestaurantModule",
    description: "Drop all the data in the restaurant module",
    units: [
      function() { 
        service.RestaurantService.cleanRestaurantDB() ;
      }
    ]
  }); 
  
  
  var RecipeApiCRUD = new test.UnitTask({
    name: "ProductPriceApiCRUD",
    description: "create/get/update/delete a recipe",
    units: [
      function() {
        console.log("RecipeApiCRUD: Start!!!") ;
        
        var recipes = service.RestaurantService.getRecipes().data ;
        Assert.assertEquals(1, recipes.length) ;
        
        console.log("RecipeApiCRUD: Finish!!!") ;
      }
    ]
  });
  
  var TableApiCRUD = new test.UnitTask({
    name: "ProductPriceApiCRUD",
    description: "create/get/update/delete a table",
    units: [
      function() {
        console.log("TableApiCRUD: Start!!!") ;
        
        var table = {
          organizationLoginId: "hkt", responsibleGroup : "", location : "Tang 1", name : "A02", description : ""
        };
        table = service.RestaurantService.saveTable(table).data ;
        Assert.assertEquals("hkt", table.organizationLoginId) ;
        
        var tables = service.RestaurantService.getTables().data ;
        Assert.assertEquals(2, tables.length) ;
        
        res = service.RestaurantService.deleteTable(table) ;
        Assert.assertEquals(true, res.data) ;
        
        tables = service.RestaurantService.getTables().data ;
        Assert.assertEquals(1, tables.length) ;
        
        console.log("TableApiCRUD: Finish!!!") ;
      }
    ]
  });
  
 
  var GoToRestaurantTablesScreen = new test.UnitTask({
    name: "GoToRestaurantTablesScreen",
    description: "Click on restaurant and then Tables in the navigation",
    units: [
      function() { Site.Navigation.clickMenuItem("Restaurant", "Tables") ; } ,
    ]
  });
  
  var CRUDTableTask = new test.UnitTask({
    name: "CRUDTableTask",
    description: "Create Retrive Update Delete Table Task",
    units: [
      //Create a Table
      function() { Site.Workspace.tableToolbar('Refresh').clickButton("New"); } ,
      function() {
        var form = Site.PopupPanel.formWithText("Name:") ;
        form.inputVal("organizationLoginId", "$@hkt");
        form.inputVal("name", "A002");
        form.inputVal("responsibleGroup", "");
        form.inputVal("description", "Tang phuc vu khac thuong");
      },
      function() { Site.PopupPanel.formWithText("Name:").clickButton("Save") ; },
      
      //Update a Table
      function() {
        var table = Site.Workspace.tableWithHeader("Name") ;
        var row   = table.tableRowWithText("A02") ;
        row.clickButton("Edit") ;
      },
      function() {
        var tableBlock = Site.Workspace.collapsibleBlock("Restaurant Table") ;
        tableBlock.clickButton("Edit");
        var form = tableBlock.formWithText("Org LoginId:") ;
        form.inputVal("responsibleGroup", "Nhom chiu  trach nghiem");
        form.inputVal("description", "Tang phuc vu khac hang vip");
      },
      function() {
        Site.Workspace.collapsibleBlock("Restaurant Table").clickButton("OK");
      },
      function() {  Site.Workspace.toolbarWith('Table Information').clickButton("Save") ; },
      
      //Delete a row in Tables
      function() {
        var table = Site.Workspace.tableWithHeader("Name") ;
        var row = table.tableRowWithText("A02") ;
        row.clickButton("Delete") ;
      },
      
      function() {  Site.Workspace.tableToolbar("Refresh").clickButton("Refresh") ; }
    ]
  });
  
  var GoToRestaurantRecipesScreen = new test.UnitTask({
    name: "GoToRestaurantRecipesScreen",
    description: "Click on restaurant and then Recipes in the navigation",
    units: [
      function() { Site.Navigation.clickMenuItem("Restaurant", "Recipes") ; } ,
    ]
  });
  
  var CRUDRecipeTask = new test.UnitTask({
    name: "CRUDRecipeTask",
    description: "Create Retrive Update Delete Recipe Task",
    units: [
      //Create a Recipe
      function() { Site.Workspace.tableToolbar('Refresh').clickButton("New"); } ,
      function() {
        var recipeBlock = Site.Workspace.collapsibleBlock("Recipe") ;
        var form = recipeBlock.formWithText("Org LoginId:") ;
        form.inputVal("organizationLoginId", "$@hkt");
        form.inputVal("name", "Mon trung opla");
        form.inputVal("productCode", "$@trung-ran");
        form.inputVal("recipe", "test");
        form.inputVal("description", "test");
      },
      function() {
        Site.Workspace.collapsibleBlock("Recipe").clickButton("OK");
      },
      
      function() {
        Site.Workspace.tableToolbar('Refresh').clickButton("New") ;
      },
      
      function() {
        var form = Site.PopupPanel.formWithText("Product Code:") ;
        form.inputVal("productCode", "$@trung");
        form.inputVal("quantity", 2);
        form.inputVal("percent", 2);
        Site.PopupPanel.formWithText("Product Code:").clickButton("Save");
      },
      
      function() {  Site.Workspace.toolbarWith('Recipe Ingredients').clickButton("Save") ; },
      
      //Update a Recipe
      function() {
        var table = Site.Workspace.tableWithHeader("Organization LoginId") ;
        var row   = table.tableRowWithText("Mon trung opla") ;
        row.clickButton("Recipe Ingredient") ;
      },
      
      function() {
        var block = Site.Workspace.collapsibleBlock("Recipe") ;
        block.toolbarWith("Edit").clickButton("Edit");
      },
      
      function() {
        var block = Site.Workspace.collapsibleBlock("Recipe") ;
        var form = block.formWithText("Org LoginId:") ;
        form.inputVal("name", "Mon trung opla(update)") ;
      },
      
      function() {
        var block = Site.Workspace.collapsibleBlock("Recipe") ;
        block.toolbarWith("OK").clickButton("OK");
      },
      
      function() {
        var table = Site.Workspace.tableWithHeader("Product Code") ;
        var row   = table.tableRowWithText("trung") ;
        row.clickButton("Edit") ;
      },
      
      function() {
        var form = Site.PopupPanel.formWithText("Product Code:") ;
        form.inputVal("unit", "quan (update)") ;
      },
      
      function() {  Site.PopupPanel.formWithText("Product Code:").clickButton("Save") ;  },
      
      function() {  Site.Workspace.toolbarWith('Recipe Ingredients').clickButton("Save") ; },
      
      //Search Recipe
      function() { Site.Workspace.tableToolbars('Refresh').clickButton("More Toolbars") ; } ,
      function() { Site.Workspace.tableToolbars('Filter').clickButton("More Filter Option") ; } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Product Code") ;
        controlGroup.inputVal('productCode', "Rau*") ;
      },
      function() { Site.Workspace.tableToolbars('Filter').clickButton("Filter") ; } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Product Code") ;
        controlGroup.inputVal('productCode', "") ;
      },
      function() { Site.Workspace.tableToolbars('Filter').clickButton("Filter") ; } ,
      function() { Site.Workspace.tableToolbars('Filter').clickButton("More Toolbars") ; } ,
      
      //Delete a row in Recipes
      function() {
        var table = Site.Workspace.tableWithHeader("Name") ;
        var row = table.tableRowWithText("Mon trung opla(update)") ;
        row.clickButton("Delete") ;
      },
    ]
  });
  
  var restaurant = {
    module: 'restaurant',
    
    CleanDB: CleanDB,
    
    createScenario: function(name) {
      var Scenario = new test.UnitTask({
        name: "RestaurantScenario " + name,
        description: "Create a minimum set of data for the restaurant module",
        units: [
          function() { 
            var jsonRes = 'scenario/' + name + '/restaurant.json' ;
            var data = service.Server.syncGETResource(jsonRes, 'json') ;
            if(data != null) {
              service.RestaurantService.createScenario(data) ;
            } else {
              console.log("No data for " + jsonRes) ;
            }
          }
        ]
      }); 
      return Scenario ;
    },
    
    Service: {
      api: [ TableApiCRUD, RecipeApiCRUD ]
    },
    
    UI: {
      
      Recipe: test.Suite.extend({
        name: 'Recipe',
        description: "create/update/delete/find Recipe" ,
        unitTasks: [
          GoToRestaurantRecipesScreen, CRUDRecipeTask
        ],
      }),
      
      Table: test.Suite.extend({
        name: 'Table',
        description: "create/update/delete/find table" ,
        unitTasks: [ 
          GoToRestaurantTablesScreen, CRUDTableTask
        ],
      })
      
    }
  };
  
  return restaurant ;
});
