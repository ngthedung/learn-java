define([ 
  'service/service',
  'ui/UITable',
  'ui/UIPopup',
  'module/admin/config/ProductUnit'
], function(service, UITable, UIPopup, ProductUnit) {
  
  var UIProductUnitList = UITable.extend({
    label: "ProductUnits",
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewProductUnit", icon: "add", label: "New ProductUnit", 
                onClick: function(thisUI) {
                  var productUnit = { name : "", rate: "", priority : "", description : "" } ;
                  UIPopup.activate(new ProductUnit.UIProductUnit().init(thisUI, productUnit, true)) ;
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
          { 
            label : 'Code', field : 'code', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var productUnit = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new ProductUnit.UIProductUnit().init(thisUI, productUnit, false)) ;
            }
          },
          { label : 'Name', field : 'name', toggled : true, filterable : true, },
          { label : 'Priority', field : 'priority', toggled : true },
          { label : 'Rate', field : 'rate', toggled : true },
          { label : 'Description', field : 'description', toggled : true, filterable : true, }
        ],
        actions: [
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) {
                thisUI.removeItemOnCurrentPage(row) ;
                thisUI.productUnits.splice(thisUI.productUnits.length - 1, 1) ;
            }
          }
        ]
      }
    },
    
    saveProductUnit: function (productUnit) {
      service.LocaleService.saveProductUnit(productUnit) ;
    },
    
    onRefresh: function() {
      var productUnits = service.LocaleService.getProductUnits().data ;
      this.setBeans(productUnits) ;
      this.renderRows() ;
    },
    
    init: function (productUnits) {
      var productUnits = service.LocaleService.getProductUnits().data ;
      this.setBeans(productUnits) ;
      return this ;
    }
    
  });
  
  var ProductUnits = {
      UIProductUnitList : UIProductUnitList
  }
  
  return ProductUnits ;
});
