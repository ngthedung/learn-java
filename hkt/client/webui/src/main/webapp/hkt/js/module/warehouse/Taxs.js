define([ 
  'service/service',
  'module/Module',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UIBreadcumbs',
  'module/warehouse/Tax'
], function(service, module, UITable, UIPopup, UIBreadcumbs, Tax) {
  
  var UITaxList = UITable.extend({
    label: "Taxs",
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewTax", icon: "add", label: "New", 
                onClick: function(thisUI) {
                  var tax = { name : "" } ;
                  UIPopup.activate(new Tax.UITax().init(thisUI, tax, true)) ;
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
          { label : 'Name', field : 'name', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var tax = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new Tax.UITax().init(thisUI, tax, false)) ;
            }
          },
          { label : 'Percent', field : 'percent', toggled : true, filterable : true, },
          { label : 'Description', field : 'description', toggled : true, filterable : true, }
        ],
        actions: [
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) {
              var tax = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.ProductService.deleteTax(tax).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    onRefresh: function() {
      var taxs = service.ProductService.getTaxs().data ;
      this.setBeans(taxs) ;
      this.renderRows() ;
    },
    
    init: function (viewStack) {
      this.viewStack = viewStack ;
      
      var taxs = service.ProductService.getTaxs().data ;
      this.setBeans(taxs) ;
      return this ;
    }
    
  });
  
  var UITaxs = module.UIScreen.extend({
    initialize: function (options) {
    },
    
    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UITaxList().init(this.viewStack);
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { }
    
  });
  
  return UITaxs ;
});
