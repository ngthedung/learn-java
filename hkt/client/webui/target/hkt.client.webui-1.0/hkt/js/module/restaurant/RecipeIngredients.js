define([
  'service/service',
  'ui/UIPopup',
  'ui/UITable',
  'ui/UIBean',
  'module/warehouse/Product'
], function(service, UIPopup, UITable, UIBean, Product ) {
  
  var UIRecipeIngredient = UIBean.extend({
    label: 'Recipe Ingredient Information',
    config:  {
      beans: {
        recipeIngredient: {
          label : "Recipe Ingredient Information",
          fields : [
            { field : "productCode", label : "Product Code",
              custom: {
                getDisplay: function(bean) {
                  return bean.productCode == null ? null : bean.productCode;
                },
                set: function(bean, obj) { bean.productCode = obj ; },
                
                autocomplete: {
                  search: function(val) {
                    var products = service.ProductService.findProductByCode(val).data ;
                    var result = [] ;
                    for(var i = 0; i < products.length; i++) {
                      var product = products[i] ;
                      result[i] = {value: product.code, label: product.code + ' (' + product.name + ')'} ;
                    }
                    return result ;
                  }
                }
              }
            },
            { field : "quantity", label : "Quantity", defaultValue: 0, required: true,
              validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { field : "percent", label : "Percent", defaultValue: 0, required: true,
              validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { field : "unit", label : "Unit",
              select: {
                getOptions: function(field, bean) {
                  var options = service.LocaleService.getProductUnits().data ;
                  var result = [] ;
                  for(var i = 0; i < options.length; i++) {
                    var option = options[i] ;
                    result[i] = { label: option.name , value: option.code } ;
                  }
                  return result ;
                }
              }
            },
          ],
          edit: {
            disable: true ,
            actions: [
              {
                action:'save', label: "Save", icon: "check",
                onClick: function(thisUI) {
                  if(!thisUI.validate()) {
                    thisUI.render() ;
                    return ;
                  }
                  
                  var recipeIngredient = thisUI.getBean('recipeIngredient') ;
                  
                  if(thisUI.isNew) thisUI.UIParent.save(recipeIngredient) ;
                  
                  if(thisUI.UIParent.onRefresh != null) {
                    thisUI.UIParent.onRefresh() ;
                  }
                  UIPopup.closePopup() ;
                }
              },
              {
                action:'cancel', label: "Cancel", icon: "back",
                onClick: function() { 
                  UIPopup.closePopup() ;
                }
              }
            ],
          }
        }
      }
    },
    
    init: function(UIParent, recipeIngredient, isNew) {
      this.UIParent = UIParent ;
      this.isNew = isNew
      this.bind('recipeIngredient', recipeIngredient, true) ;
      
      var recipeIngredientConfig = this.getBeanConfig('recipeIngredient') ;
      recipeIngredientConfig.disableEditAction(false) ;
      this.getBeanState('recipeIngredient').editMode = true ;
      
      recipeIngredientConfig.disableField('productCode', !isNew) ;
      
      return this ;
    }
    
  });
  
  var UIRecipeIngredientList = UITable.extend({
    label: "RecipeIngredients",
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewRecipeIngredient", icon: "add", label: "New", 
                onClick: function(thisUI) {
                  var recipeIngredient = { productCode : "" } ;
                  UIPopup.activate(new UIRecipeIngredient().init(thisUI, recipeIngredient, true)) ;
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
        },
      bean : {
        fields: [
          { label : 'Id', field : 'id', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var recipeIngredient = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UIRecipeIngredient().init(thisUI, recipeIngredient, false)) ;
            }
          },
          { label : 'Product Code', field : 'productCode', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var recipe = thisUI.getItemOnCurrentPage(row) ;
              var pDetail = service.WarehouseService.getProductDetailByCode(recipe.productCode).data ;
              var uiDetail = thisUI.UIProductDetail.init(thisUI, pDetail, false, false) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
          { label : 'Quantity', field : 'quantity', toggled : true, filterable : true, },
          { label : 'Percent', field : 'percent', toggled : true, filterable : true, },
          { label : 'Unit', field : 'unit', toggled : true, filterable : true, },
        ],
        actions: [
          {
            icon: "edit", label: "Edit",
            onClick: function(thisUI, row) {
              var recipeIngredient = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UIRecipeIngredient().init(thisUI, recipeIngredient, false)) ;
              thisUI.onEditBean(row) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) {
              thisUI.markDeletedItemOnCurrentPage(row) ;
            }
          }
        ]
      }
    },
    
    onRefresh: function() {
      this.setBeans(this.recipeIngredients) ;
      this.renderRows() ;
    },
    
    save: function (recipeIngredient) {
      this.recipeIngredients[this.recipeIngredients.length] = recipeIngredient ;
    },
    
    init: function (UIParent, recipeIngredients) {
      this.viewStack = UIParent.viewStack ;
      this.UIProductDetail = new Product.UIProductDetail() ;
      this.recipeIngredients = recipeIngredients ;
      this.setBeans(recipeIngredients) ;
      return this ;
    }
    
  });
  
  var RecipeIngredients = {
    UIRecipeIngredientList : UIRecipeIngredientList
  };
  
  return RecipeIngredients ;
});
