define([ 
  'service/service',
  'ui/UIBean',
  'ui/UICollapsible',
  'module/restaurant/RecipeIngredients',
], function(service, UIBean, UICollapsible, RecipeIngredients) {
  
  var UIRecipe = UIBean.extend({
    label: "Recipe",
    config: {
      beans: {
        recipe: {
          label: 'Recipe',
          fields: [
//            { field: "organizationLoginId", label: "Org LoginId", required: true, validator: {name: "empty"},
//              custom: {
//                getDisplay: function(bean) {
//                  return bean.organizationLoginId == null ? null : bean.organizationLoginId; 
//                },
//                set: function(bean, obj) { bean.organizationLoginId = obj ; },
//                autocomplete: {
//                  search: function(val) {
//                    var accounts = service.AccountService.findAccountByLoginIdOrg(val).data ;
//                    var result = [] ;
//                    for(var i = 0; i < accounts.length; i++) {
//                      var account = accounts[i] ;
//                      result[i] = {value: account.loginId, label: account.loginId + '(' + account.email + ')'} ;
//                    }
//                    return result ;
//                  }
//                }
//              }
//            },
            { field: "name", label: "Name" },
            { field: "productCode", label: "Product Code", required: true, validator: {name: "empty"},
              custom: {
                getDisplay: function(bean) {
                  return bean.productCode == null ? null : bean.productCode;
                },
                set: function(bean, obj) { bean.productCode = obj ; },
                autocomplete: {
                  search: function(val) {
                    var products = service.ProductService.findProductByNameOrCode(val).data ;
                    var result = [] ;
                    for(var i = 0; i < products.length; i++) {
                      var product = products[i] ;
                      result[i] = {value: product.code, label: product.name + ' (' + product.code + ')'} ;
                    }
                    return result ;
                  }
                }
              }
            },
            { field: "description", label: "Description", textarea: {} },
          ],
        }
      }
    },
    
    init: function(recipe, isNew, readOnly) {
      this.bind('recipe', recipe, true) ;
      this.setReadOnly(readOnly);
      if (isNew) { this.getBeanState('recipe').editMode = true ; }
      return this ;
    },
    
  });
  
  var UIRecipeDetail = UICollapsible.extend({
    label: "Recipe Ingredients", 
    config: {
      actions: [
        {
          action: 'back', label: 'Back',
          onClick: function (thisUI) {
            if (thisUI.UIParent.back) {
              thisUI.UIParent.back(false) ;
            }
          }
        },
        {
          action: "save", label: "Save",
          onClick: function(thisUI) {
            if(!thisUI.UIRecipe.validate()) {
              thisUI.render() ;
              return ;
            }
            service.RestaurantService.saveRecipe(thisUI.recipe) ;
            console.log(service.RestaurantService.filterRecipe(null).data)
            if(thisUI.UIParent.back) thisUI.UIParent.back(true) ;
          }
        }
      ]
    },
    
    init: function (UIParent, recipe, isNew, readOnly) {
      this.isNew = isNew;
      this.clear() ;
      this.UIParent = UIParent ;
      
      if(readOnly) this.setActionHidden('save', true) ;
      
      this.recipe = recipe ;
      this.UIRecipe = new UIRecipe().init(recipe, isNew, readOnly) ;
      this.add(this.UIRecipe) ;
//      
//      var UIRecipeIngredientList = new RecipeIngredients.UIRecipeIngredientList().init(UIParent, recipe.id) ;
//      this.add(UIRecipeIngredientList) ;
    }
  });
  
  var Recipe = {
    UIRecipeDetail : UIRecipeDetail
  };
  
  return Recipe ;
});
