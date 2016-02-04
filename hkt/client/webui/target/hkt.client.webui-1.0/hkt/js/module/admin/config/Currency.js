define([ 
  'service/service',
  'ui/UIBean',
  'ui/UIPopup',
], function( service, UIBean, UIPopup ) {
  
  var UICurrency = UIBean.extend({
    label: "Currency",
    config: {
      beans: {
        currency: {
          label: 'Currency',
          fields: [
            { field: "code", label: "Code" },
            { field: "name", label: "Name" },
            { field: "priority", label: "Priority", defaultValue: 0,
              validator: { name: 'number', from: 0, errorMsg: "Expect a number" } },
            { field: "rate", label: "Rate", defaultValue: 0,
              validator: { name: 'number', from: 0, errorMsg: "Expect a number" } },
            { field: "description", label: "Description", textarea: {} }
          ],
          edit: {
            disable: true ,
            actions: [
              {
                action:'save', label: "Save", icon: "check",
                onClick: function(thisUI) { 
                  var currency = thisUI.getBean('currency') ;
                  if(thisUI.isNew) thisUI.UIParent.saveCurrency(currency) ;
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
    
    init: function(UIParent, currency, isNew) {
      this.UIParent = UIParent ;
      this.bind('currency', currency, true) ;
      this.isNew = isNew
      var currencyConfig = this.getBeanConfig('currency') ;
      
      currencyConfig.disableEditAction(false) ;
      currencyConfig.disableField('code', !isNew) ;
      
      this.getBeanState('currency').editMode = true ;
      
      return this ;
    },
  });
  
  var Currency = {
      UICurrency : UICurrency
  };
  
  return Currency ;
});
