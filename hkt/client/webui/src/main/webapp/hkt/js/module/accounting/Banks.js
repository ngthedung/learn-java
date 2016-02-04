define([ 
  'service/service',
  'module/Module',
  'ui/UIBean',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UIBreadcumbs',
], function(service, module, UIBean, UITable, UIPopup, UIBreadcumbs) {
  
  var UIBankAccount = UIBean.extend({
    label: "BankAccount",
    config: {
      beans: {
        bankaccount: {
          label: 'BankAccount',
          fields: [
            { field: "bankAccountId", label: "Bank Account Id", required: true, validator: {name: "empty"} },
            { field: "type", label: "Type", defaultValue: 'Money',
              select: {
                getOptions: function(field, bean) {
                  var options = [
                    { label: 'Money', value: 'Money' },
                    { label: 'CreditCard', value: 'CreditCard' }
                  ];
                  return options ;
                }
              }
            },
            { field: "currency", label: "Currency",
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
            { field: "address", label: "Address" }
          ],
          edit: {
            disable: true ,
            actions: [
              {
                action:'save', label: "Save", icon: "check",
                onClick: function(thisUI) { 
                  if (!thisUI.validate()) {
                    thisUI.render() ;
                    return ;
                  }
                  var bankaccount = thisUI.getBean('bankaccount') ;
                  service.AccountingService.saveBankAccount(bankaccount) ;
                  if (thisUI.UIParent.onRefresh != null) {
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
    
    init: function(UIParent, bankaccount, isNew) {
      this.UIParent = UIParent ;
      this.bind('bankaccount', bankaccount, true) ;
      
      var bankaccountConfig = this.getBeanConfig('bankaccount') ;
      
      bankaccountConfig.disableEditAction(false) ;
      this.getBeanState('bankaccount').editMode = true ;
      
      bankaccountConfig.disableField('bankAccountId', !isNew) ;
      return this ;
    }
  });
  
  var UIBankAccountList = UITable.extend({
    label: "BankAccounts",
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewBankAccount", icon: "add", label: "New", 
                onClick: function(thisUI) {
                  var bankAccount = {
                    bankAccountId : ""
                  } ;
                  UIPopup.activate(new UIBankAccount().init(thisUI, bankAccount, true)) ;
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
          { label : 'Bank Account Id', field : 'bankAccountId', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var bankAccount = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UIBankAccount().init(thisUI, bankAccount, false)) ;
            }
          },
          { label : 'Type', field : 'type', toggled : true, filterable : true, },
          { label : 'Currency', field : 'currency', toggled : true, filterable : true, },
          { label : 'Address', field : 'address', toggled : true, filterable : true },
        ],
        actions: [
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) {
              var bankaccount = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.AccountingService.deleteBankAccountByAccountId(bankaccount.bankAccountId).data ;
              if (deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    onRefresh: function() {
      var bankaccounts = service.AccountingService.findBankAccounts().data ;
      this.setBeans(bankaccounts) ;
      this.renderRows() ;
    },
    
    init: function (viewStack) {
      this.viewStack = viewStack ;
      var bankaccounts = service.AccountingService.findBankAccounts().data ;
      this.setBeans(bankaccounts) ;
      return this ;
    }
    
  });
  
  var UIBanks = module.UIScreen.extend({
    initialize: function (options) {
    },
    
    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UIBankAccountList().init(this.viewStack);
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { }
    
  });
  
  return UIBanks ;
});
