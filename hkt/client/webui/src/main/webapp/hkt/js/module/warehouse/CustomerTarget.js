define([
  'ui/UITable',
  'ui/UIBean',
  'ui/UIPopup',
], function(UITable, UIBean, UIPopup) {
  
  var UICustomerTarget = UIBean.extend({
    label: 'Customer Target',
    config: {
      beans: {
        customerTarget: {
          label: 'Customer Target',
          fields: [
            { field: "customerGroup", label: "Customer Group", required: true, validator: {name: "empty"} }
          ],
          edit: {
            disable: false , 
            actions: [
              {
                action:'save', label: "Save", icon: "check",
                onClick: function(thisUI) { 
                  if(!thisUI.validate()) {
                    thisUI.render() ;
                    return ;
                  }
                  var customerTarget = thisUI.getBean('customerTarget') ;
                  if(thisUI.isNew) thisUI.UIParent.save(customerTarget) ;
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
    
    init: function(UIParent, customerTarget, isNew) {
      this.isNew = isNew;
      this.UIParent = UIParent ;
      this.bind('customerTarget', customerTarget, true) ;
      if(isNew){ this.getBeanState('customerTarget').editMode = true ; }
      return this ;
    }
  });
  
  var UICustomerTargetList = UITable.extend({
    label: 'Promotion Customer Target',
    config : {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNewCustomerTarget", icon: "add", label: "New Customer Target", 
              onClick: function(thisUI) { 
                var CustomerTarget = {
                    customerGroup : ""
                };
                UIPopup.activate(new UICustomerTarget().init(thisUI, CustomerTarget, true)) ;
              } 
            }
          ]
        }
      },
      bean: {
        fields: [
          { label : 'Customer Group', field : 'customerGroup', toggled : true, filterable : true }
        ],
        actions: [
          {
            icon: "edit", label: "Edit Customer Target",
            onClick: function (thisUI, row) { 
              var CustomerTarget = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UICustomerTarget().init(thisUI, CustomerTarget, false)) ;
              thisUI.onEditBean(row) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function (thisUI, row) { 
              thisUI.markDeletedItemOnCurrentPage(row) ;
            }
          }
        ]
      }
    },
    
    save: function (customerTarget) {
      this.listCustomerTarget[this.listCustomerTarget.length] = customerTarget ; 
    },
    
    init: function(listCustomerTarget) {
      this.listCustomerTarget = listCustomerTarget;
      this.setBeans(this.listCustomerTarget) ;
      return this ;
    },
    
    onRefresh: function() { 
      this.setBeans(this.listCustomerTarget) ;
      this.renderRows() ;
    },
  });
  
  var CustomerTarget = {
      UICustomerTargetList: UICustomerTargetList
  };
  
  return CustomerTarget ;
});
