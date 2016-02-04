define([ 
  'service/service',
  'ui/UIBean',
  'ui/UITable',
  'ui/UIPopup',
  'module/account/Account',
  'module/warehouse/Product'
], function(service, UIBean, UITable, UIPopup, Account, Product) {
  
  var UIProductPrice = UIBean.extend({
    label: "Product Price",
    config: {
      beans: {
        productPrice: {
          label: 'Product Price',
          fields: [
            { 
              field: "productPriceType",  label: "ProductPrice Type",
              custom: {
                getDisplay: function(bean) {
                  return bean.productPriceType == null ? null : bean.productPriceType.type ;
                },
                set: function(bean, obj) { bean.productPriceType = obj ;},
                
                autocomplete: {
                  search: function(val) {
                    var productPriceTypes = service.ProductService.searchByType(val).data ;
                    var result = [] ;
                    for(var i = 0; i < productPriceTypes.length; i++) {
                      var productPriceType = productPriceTypes[i] ;
                      result[i] = { value: productPriceType, label: productPriceType.type} ;
                    }
                    return result ;
                  }
                }
              }
            },
            { 
              field : "product", label : "Product", required: true, validator: {name: "empty"},
              custom: {
                getDisplay: function(bean) {
                  return bean.product == null ? null : bean.product.name;
                },
                set: function(bean, obj) { bean.product = obj ; },
                autocomplete: {
                  search: function(val) {
                    var products = service.ProductService.findProductByCode(val).data ;
                    var result = [] ;
                    for(var i = 0; i < products.length; i++) {
                      var product = products[i] ;
                      result[i] = {value: product, label: product.code + ' (' + product.name + ')'} ;
                    }
                    return result ;
                  }
                }
              }
            },
            { field: "unit", label: "Unit",
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
              },
              validator: { name: "empty" },
            },
            { field: "price", label: "Price", defaultValue: 0,
              validator: { 
                name: 'number', from: 0, errorMsg: "Expect a number from 0" 
              }
            },
            { field: "currencyUnit", label: "Currency Unit",
              select: {
                getOptions: function(field, bean) {
                  var optionGroup = service.GenericOptionService.getOptionGroup(
                      'config', 'LocaleService', 'currency').data ;
                  var options = optionGroup.options;
                  var result = [] ;
                  for(var i = 0; i < options.length; i++) {
                    var option = options[i] ;
                    result[i] = { label: option.label , value: option.code } ;
                  }
                  return result ;
                }
              }
            },
            { field: "minQuantity", label: "Min Quantity", defaultValue: 0,
              validator: { 
                name: 'number', from: 0, errorMsg: "Expect a number from 0" 
              }
            },
            { field: "maxQuantity", label: "Max Quantity", defaultValue: 0,
              validator: { 
                name: 'number', from: 0, errorMsg: "Expect a number from 0" 
              }
            },
            { field: "tax", label: "Tax",
              custom: {
                getDisplay: function(bean) {
                  return bean.tax == null ? null : bean.tax.name ;
                },
                set: function(bean, obj) { bean.tax = obj ; },
                autocomplete: {
                  search: function(val) {
                    var taxs = service.ProductService.findTaxByName(val).data ;
                    var result = [] ;
                    for(var i = 0; i < taxs.length; i++) {
                      var tax = taxs[i] ;
                      result[i] = {value: tax, label: tax.name + ' ( '+ tax.percent + ' )'} ;
                    }
                    return result ;
                  }
                }
              }
            },
            { field: "description", label: "Description", textarea: {} },
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
                  var productPrice = thisUI.getBean('productPrice') ;
                  service.ProductService.saveProductPrice(productPrice) ;
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
          },
        }
      }
    },
    
    init: function(UIParent, productPrice, isNew) {
      this.UIParent = UIParent ;
      this.bind('productPrice', productPrice, true) ;
      
      var productPriceConfig = this.getBeanConfig('productPrice') ;
      
      productPriceConfig.disableEditAction(false) ;
      this.getBeanState('productPrice').editMode = true ;
      return this ;
    }
  });
  
  var UIProductPriceList = UITable.extend({
    label: "Product Prices",
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewProductPrice", icon: "add", label: "New", 
                onClick: function(thisUI) {
                 // var taxs = service.ProductService.getTaxs().data ;
                  var productPrice = { 
                    organizationLoginId : thisUI.productPriceType.organizationLoginId
                  } ;
                  UIPopup.activate(new UIProductPrice().init(thisUI, productPrice, true)) ;
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
              var productPrice = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UIProductPrice().init(thisUI, productPrice, false)) ;
            }
          },
          { label : 'Organization LoginId', field : 'organizationLoginId', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var productPrice = thisUI.getItemOnCurrentPage(row) ;
              var account = service.AccountService.getAccountByLoginId(productPrice.organizationLoginId).data;
              var uiDetail = thisUI.UIAccountDetail.init(thisUI, account, true) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
          { label : 'Product', field : 'product', toggled : true, filterable : true,
            custom: {
              getDisplay: function(bean) {
                return bean.product == null ? null : bean.product.name ;
              },
              set: function(bean, obj) { bean.product = obj ;},
            }
          },
          { label : 'Unit', field : 'unit', toggled : true, filterable : true, },
          { label : 'Price', field : 'price', toggled : true, filterable : true },
          { label : 'Currency Unit', field : 'currencyUnit', toggled : true, filterable : true },
          { label : 'Min Quantity', field : 'minQuantity', toggled : true, filterable : true },
          { label : 'Max Quantity', field : 'maxQuantity', toggled : true, filterable : true },
          { label : 'Tax', field : 'tax', toggled : true, filterable : true ,
            custom: {
              getDisplay: function(bean) {
                return bean.tax == null ? null : bean.tax.name ;
              },
              set: function(bean, obj) { bean.tax = obj ;},
            }
          },
          { label : 'Description', field : 'description', toggled : true, filterable : true },
        ],
        actions: [
          {
            icon: "edit", label: "Edit",
            onClick: function(thisUI, row) {
              var productPrice = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UIProductPrice().init(thisUI, productPrice, false)) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) {
              var productPrice = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.ProductService.deleteProductPrice(productPrice).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    onRefresh: function() {
      var producePrices = service.ProductService.searchByProductPriceType(this.productPriceType.type).data ;
      this.setBeans(producePrices) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
    
    init: function (UIParent, productPriceType) {
      this.viewStack = UIParent.viewStack ;
      this.productPriceType = productPriceType ;
      this.UIAccountDetail = new Account.UIAccountDetail() ;
      this.UIProductDetail = new Product.UIProductDetail() ;
      
      var producePrices = service.ProductService.searchByProductPriceType(productPriceType.type).data ;
      this.setBeans(producePrices) ;
      return this ;
    }
    
  });
  
  var ProductPrices = {
    UIProductPriceList : UIProductPriceList
  };
  
  return ProductPrices 
});
