define([ 
  'service/service',
  'module/Module',
  'ui/UIBean',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UIBreadcumbs',
  'ui/UICollapsible',
  'module/warehouse/ProductPrices',
  'module/warehouse/ProductPriceType',
  'module/account/Account',
], function(service, module, UIBean, UITable, UIPopup, UIBreadcumbs, UICollapsible, ProductPrices, ProductPriceType, 
 Account) {
  
  var UIProductPrice = UICollapsible.extend({
    label: "Product Prices", 
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
      ]
    },
    
    init: function (UIParent, productPriceType) {
      this.clear() ;
      this.UIParent = UIParent ;
      
      this.UIProductPriceType = new ProductPriceType.UIProductPriceType().initViewOnly(productPriceType) ;
      this.add(this.UIProductPriceType, true) ;
      
      var UIProductPriceList = new ProductPrices.UIProductPriceList().init(UIParent, productPriceType) ;
      this.add(UIProductPriceList) ;
    }
  });
  
  var UIProductPriceTypeList = UITable.extend({
    label: "ProductPriceTypes",
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewProductPriceType", icon: "add", label: "New", 
                onClick: function(thisUI) {
                  var productPriceType = { type : "" } ;
                  UIPopup.activate(new ProductPriceType.UIProductPriceType().init(thisUI, productPriceType, true)) ;
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
          { label : 'Type', field : 'type', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var productPriceType = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new ProductPriceType.UIProductPriceType().init(thisUI, productPriceType, false)) ;
            }
          },
          { label : 'Organization LoginId', field : 'organizationLoginId', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var productPriceType = thisUI.getItemOnCurrentPage(row) ;
              var account = service.AccountService.getAccountByLoginId(productPriceType.organizationLoginId).data;
              var uiDetail = thisUI.UIAccountDetail.init(thisUI, account, true) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
          { label : 'Description', field : 'description', toggled : true, filterable : true, },
        ],
        actions: [
          {
            icon: "bars", label: "Product Prices",
            onClick: function(thisUI, row) {
              var productPrice = thisUI.getItemOnCurrentPage(row) ;
              thisUI.UIProductPrice.init(thisUI, productPrice) ;
              thisUI.viewStack.push(thisUI.UIProductPrice) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) {
              var productPriceType = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.ProductService.deleteProductPriceType(productPriceType).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    onRefresh: function() {
      var productPriceTypes = service.ProductService.getProductPriceTypes().data ;
      this.setBeans(productPriceTypes) ;
      this.renderRows() ;
    },
    
    init: function (viewStack) {
      this.viewStack = viewStack ;
      this.UIProductPrice = new UIProductPrice() ;
      this.UIAccountDetail = new Account.UIAccountDetail() ;
      
      var productPriceTypes = service.ProductService.getProductPriceTypes().data ;
      this.setBeans(productPriceTypes) ;
      return this ;
    }
    
  });
  
  var UIProductPriceTypes = {
      UIProductPriceTypeList: UIProductPriceTypeList
    };
  
  return UIProductPriceTypes ;
});
