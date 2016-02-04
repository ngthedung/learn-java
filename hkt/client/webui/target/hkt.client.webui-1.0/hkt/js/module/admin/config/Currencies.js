define([ 
  'service/service',
  'ui/UITable',
  'ui/UIPopup',
  'module/admin/config/Currency'
], function(service, UITable, UIPopup, Currency) {
  
  var UICurrencyList = UITable.extend({
    label: "Currencies",
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewCurrency", icon: "add", label: "New Currency", 
                onClick: function(thisUI) {
                  var currency = { name : "", rate: "", priority : "", description : "" } ;
                  UIPopup.activate(new Currency.UICurrency().init(thisUI, currency, true)) ;
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
              var currency = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new Currency.UICurrency().init(thisUI, currency, false)) ;
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
                thisUI.currencies.splice(thisUI.currencies.length - 1, 1) ;
            }
          }
        ]
      }
    },
    
    saveCurrency: function (currency) {
      service.LocaleService.saveCurrency(currency) ;
    },
    
    onRefresh: function() {
      var currencies = service.LocaleService.getCurrencies().data ;
      this.setBeans(currencies) ;
      this.renderRows() ;
    },
    
    init: function (currencies) {
      var currencies = service.LocaleService.getCurrencies().data ;
      this.setBeans(currencies) ;
      return this ;
    }
    
  });
  
  var Currencies = {
      UICurrencyList : UICurrencyList
  }
  
  return Currencies ;
});
