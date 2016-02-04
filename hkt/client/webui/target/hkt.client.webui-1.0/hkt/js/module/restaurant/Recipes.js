define([ 
  'service/service',
  'module/Module',
  'ui/UIBean',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UIBreadcumbs',
  'ui/UICollapsible',
  'module/restaurant/Recipe',
  'module/warehouse/Product',
], function(service, module, UIBean, UITable, UIPopup, UIBreadcumbs, UICollapsible, Recipe, Product) {
  
  var UIRecipeList = UITable.extend({
    label: "Recipes",
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewRecipe", icon: "plus", label: "New", 
                onClick: function(thisUI) {
                  var recipe = {
                   name: ""
                  };
                  thisUI.UIRecipeDetail.init(thisUI, recipe, true, false) ;
                  thisUI.viewStack.push(thisUI.UIRecipeDetail) ;
                } 
              },
              {
                action: "onRefresh", icon: "refresh", label: "Refresh",
                onClick: function(thisUI) { 
                  thisUI.onRefresh() ;
                }
              }
            ],
          },
          filter: {
            fields: [
              { label: 'Search By Name',  field: 'name', operator: 'LIKE' },
              { label: 'Search By Product Code', field: 'productCode', operator: 'LIKE' }
            ],
            onFilter: function(thisTable, query) {
              var result = service.RestaurantService.filterRecipe(query).data ;
              var recipes = result.data ;
              thisTable.setBeans(recipes) ;
              thisTable.renderRows() ;
            }
          },
        },
      bean : {
        fields: [
          { label : 'Name', field : 'name', toggled : true, filterable : true,
        	onClick: function(thisUI, row) {
        		var recipe = thisUI.getItemOnCurrentPage(row) ;
        		thisUI.UIRecipeDetail.init(thisUI, recipe, false, false) ;
        		thisUI.viewStack.push(thisUI.UIRecipeDetail) ;
        	}  
          },
          { label : 'Product', field : 'productCode', toggled : true, filterable : true,},
          { label : 'Description', field : 'description', toggled : true, filterable : true },
        ],
        actions: [
          {
            icon: "edit", label: "Recipe Ingredient",
            onClick: function(thisUI, row) {
              var recipe = thisUI.getItemOnCurrentPage(row) ;
              thisUI.UIRecipeDetail.init(thisUI, recipe, false, false) ;
              thisUI.viewStack.push(thisUI.UIRecipeDetail) ;
              
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) {
              var recipe = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.RestaurantService.deleteRecipe(recipe).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    onRefresh: function() {
      var result = service.RestaurantService.filterRecipe(null).data ;
      var recipes = result.data ;
      this.setBeans(recipes) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
    
    init: function (viewStack) {
      this.viewStack = viewStack ;
      this.UIProductDetail = new Product.UIProductDetail() ;
      this.UIRecipeDetail = new Recipe.UIRecipeDetail() ;
      
      var resultRecipes = service.RestaurantService.filterRecipe(null).data ;
      var recipes = resultRecipes.data ;
      var resultProducts = service.ProductService.filterProduct(null).data ;
      var products = resultProducts.data ;
      for(var i = 0;i<recipes.length; i++)
    	  {
    	  var recipe = recipes[i];
          
          for(var j = 0; j<products.length;j++)
        	  {
        	  	var product = products[i];
        	  	if(product.code = recipe.productCode) recipes[i].productCode = product.name;
        	  }
    	  }
      this.setBeans(recipes) ;
      return this ;
    }
    
  });
  
  var UIRecipes = module.UIScreen.extend({
    initialize: function (options) {
    },
    
    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UIRecipeList().init(this.viewStack);
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { }
    
  });
  
  return UIRecipes ;
});
