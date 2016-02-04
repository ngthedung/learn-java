define([
  'service/service',
  'module/Module', 
  'ui/UITable',
  'ui/UIBreadcumbs',
  'ui/UICollapsible',
  'module/warehouse/Inventories',
  'module/warehouse/Product'
], function(service, module, UITable, UIBreadcumbs, UICollapsible, Inventories, Product) {
  
  var UIInventory =  UICollapsible.extend({
    label: "Inventories",
    config: {
      actions: [
        {
          action: 'back', label: 'Back',
          onClick: function (thisUI) {
            if (thisUI.UIParent.back) {
              thisUI.UIParent.back(false) ;
            }
          }
        }
      ]
    },
    
    init: function (UIParent, product) {
      this.UIParent = UIParent ;
      this.clear() ;
      this.add(new Product.UIProduct().initViewOnly(UIParent, product), true) ;
      
      var UIInventoryList = new Inventories.UIInventoryList().init(UIParent, null, product.code) ;
      this.add(UIInventoryList) ;
    }
  });
  
  var UIProductList = UITable.extend({
    label: 'Products',
    config : {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNewProduct", icon: "add", label: "New", 
              onClick: function(thisUI) { 
                var pDetail = { 
                  product: {code: ""} ,
                  cmsNode: {} 
                } ;
                var uiDetail = thisUI.UIProductDetail.init(thisUI, pDetail, true, true) ;
                thisUI.viewStack.push(uiDetail) ;
              }
            },
            {
              action: "onRefresh", icon: "refresh", label: "Refresh",
              onClick: function(thisUI) {
                thisUI.onRefresh();
              } 
            }
          ]
        },
        filter: {
          fields: [
            { label: 'Search By Code', field: 'code',  operator: 'LIKE' },
            { label: 'Search By Name', field: 'name',  operator: 'LIKE' },
            { label: 'Search By Maker', field: 'maker', operator: 'EQUAL' }
          ],
          onFilter: function(thisUI, query) {
            var result = service.ProductService.filterProduct(query).data ;
            var products = result.data ;
            thisUI.setBeans(products) ;
            thisUI.renderRows() ;
          },
        },
        search: {
          onSearch: function(thisUI, query) {
            var searchResult = service.ProductService.searchProducts(query).data ;
            var beans = [] ;
            if(searchResult.records != null) {
              for(var i = 0; i < searchResult.records.length; i++) {
                beans[i] = searchResult.records[i].entity ;
              }
            }
            thisUI.setBeans(beans) ;
            thisUI.renderRows() ;
          },
        }
      },
      bean : { 
        fields: [
          { 
            label : 'Code', field : 'code', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var product = thisUI.getItemOnCurrentPage(row) ;
              var pDetail = service.ProductService.getProductDetailByCode(product.code).data ;
              var uiDetail = thisUI.UIProductDetail.init(thisUI, pDetail, false, true) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
          { label : 'Name', field : 'name', toggled : true, filterable : true },
          { label : 'Unit', field : 'unit', toggled : true, filterable : true },
          { label : 'Maker', field : 'maker', toggled : true, filterable : true },
          { label : 'Status', field : 'status', toggled : true, filterable : true }
        ],
        actions: [
          {
            icon: "bars", label: "Inventory",
            onClick: function(thisUI, row) { 
              var product = thisUI.getItemOnCurrentPage(row) ;
              thisUI.UIInventory.init(thisUI, product);
              thisUI.viewStack.push(thisUI.UIInventory) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) { 
              var product = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.ProductService.deleteProductByCode(product.code).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    init: function (viewStack) {
      this.viewStack = viewStack ;
      this.UIProductDetail = new Product.UIProductDetail() ;
      this.UIInventory = new UIInventory() ;
      
      var result = service.ProductService.filterProduct(null).data ;
      var products = result.data ;
      this.setBeans(products) ;
      return this ;
    },

    total:function(){
      return this.to;
    },
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
    
    onRefresh: function() {
      var result = service.ProductService.filterProduct(null).data ;
      var products = result.data ;
      this.setBeans(products) ;
      this.renderRows() ;
    }
  });
  
  var UIProductsScreen = module.UIScreen.extend({
    initialize: function (options) { },
    
    activate: function() {
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UIProductList().init(this.viewStack) ;
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { }
  });
  
  return UIProductsScreen ;
});
