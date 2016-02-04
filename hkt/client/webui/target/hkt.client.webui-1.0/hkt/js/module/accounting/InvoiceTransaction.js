define([
  'service/service',
  'ui/UITable',
  'ui/UIBean',
  'ui/UIPopup',
  'util/DateTime',  
], function (service, UITable, UIBean, UIPopup, DateTime) {
  
  var UIInvoiceTransaction = UIBean.extend({
    label: 'Invoice Transaction',
    config: {
      beans: {
        transaction: {
          label: 'transaction',
          fields: [
            { field : 'createdBy', label : 'Cashier',
              custom : {
                getDisplay: function(bean) {
                  return bean.createdBy == null ? null : bean.createdBy;
                },
                set: function(bean, obj) { bean.createdBy = obj ;},
                
                autocomplete: {
                  search: function(val) {
                    var accounts = service.AccountService.findAccountByLoginIdUser(val).data ;
                    var result = [] ;
                    for (var i = 0; i < accounts.length; i++) {
                      var account = accounts[i] ;
                      result[i] = {value: account.loginId, label: account.loginId + '(' + account.email + ')'} ;
                    }
                    return result ;
                  }
                }
              }
            },
            { 
              field : 'bankAccountId', label : 'Bank Account Id',
              custom : {
                getDisplay: function(bean) {
                  return bean.bankAccountId == null ? null : bean.bankAccountId;
                },
                set: function(bean, obj) { bean.bankAccountId = obj ;},
                
                autocomplete: {
                  search: function (val) {
                    var bankAccounts = service.AccountingService.findBankAccountByBankLoginId(val).data ;
                    var result = [] ;
                    for (var i = 0; i < bankAccounts.length; i++) {
                      var bankAccount = bankAccounts[i] ;
                      result[i] = {
                        value: bankAccount.bankAccountId,
                        label: bankAccount.bankAccountId + ' (' + bankAccount.address + ')'} ;
                    }
                    return result ;
                  }
                }
              }
            },
            { field: "transactionType", label: "Transaction Type", defaultValue: 'Wire',
              select: {
                getOptions: function(field, bean) {
                  var options = [
                    { label: 'Wire', value: 'Wire' },
                    { label: 'Cash', value: 'Cash' },
                    { label: 'CreditCard', value: 'CreditCard' },
                    { label: 'CustomerCredit', value: 'CustomerCredit' }
                  ];
                  return options ;
                }
              }
            },
            { field : 'transactionDate', label : 'Transaction Date',
              datepicker: {
                getDate: function(bean) {
                  return DateTime.fromDateTimeToDDMMYYYY(bean.transactionDate) ;
                },
                setDate: function(date, bean) {
                  bean.transactionDate = DateTime.fromDDMMYYYYToDateTime(date) ;
                },
              }
            },
            { 
              field: "total", label: "Total",
              defaultValue: 0, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0"  }
            },
            { field : 'currencyUnit', label : 'Currency Unit',
              select: {
                getOptions: function(field, bean) {
                  var options = service.LocaleService.getCurrencies().data ;
                  var result = [] ;
                  for(var i = 0; i < options.length; i++) {
                    var option = options[i] ;
                    result[i] = { label: option.name , value: option.code } ;
                  }
                  return result ;
                }
              },
            },
            { field : 'description', label : 'Description', textarea : {} },
            { field : 'note', label : 'Note', textarea : {} },
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
                  var transaction = thisUI.getBean('transaction') ;
                  if(thisUI.isNew) thisUI.UIParent.save(transaction) ;
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
    
    init: function(UIInvoiceTransactionList, transaction, isNew) {
      this.isNew = isNew;
      this.UIParent = UIInvoiceTransactionList ;
      this.bind('transaction', transaction, true) ;
//      if(isNew){ this.getBeanState('transaction').editMode = true ; }
      var config = this.getBeanConfig('transaction') ;
      config.disableEditAction(false) ;
      this.getBeanState('transaction').editMode = true ;
      return this ;
    },
    total:function(){
        return 0;
      }
  });
  
  var UIInvoiceTransactionList = UITable.extend({
    label: 'Invoice Transaction',
    config : {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNewTransaction", icon: "add", label: "New Transaction", 
              onClick: function(thisUI) { 
                var transaction = {
                    bankAccountId : "", transactionDate : DateTime.getCurrentDate(), description : "", note : ""
                };
                UIPopup.activate(new UIInvoiceTransaction().init(thisUI, transaction, true)) ;
              } 
            }
          ]
        }
      },
      bean: {
        fields: [
          { label : 'Cashier', field : 'createdBy', toggled : true, filterable : true },
          { label : 'Bank Account Id', field : 'bankAccountId', toggled : true, filterable : true }, 
          { label : 'Transaction Type', field : 'transactionType', toggled : true, filterable : true },
          { label : 'Transaction Date', field : 'transactionDate', toggled : true},
          { label : 'Total', field : 'total', toggled : true},
          { label : 'Currency Unit', field : 'currencyUnit', toggled : true},
          { label : 'Description', field : 'description'},
          { label : 'Note', field : 'note'}
        ],
        actions: [
          {
            icon: "edit", label: "Edit Transaction",
            onClick: function (thisUI, row) { 
              var transaction = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UIInvoiceTransaction().init(thisUI, transaction, false)) ;
//              thisUI.onEditBean(row) ;
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
    
    save: function (transaction) {
      this.listInvoiceTransaction[this.listInvoiceTransaction.length] = transaction ; 
    },
    total:function(){
          return 0;
        },
    init: function(listTransaction) {
      this.listInvoiceTransaction = listTransaction;
      this.setBeans(this.listInvoiceTransaction) ;
      return this ;
    },
    
    onRefresh: function() { 
      this.setBeans(this.listInvoiceTransaction) ;
      this.renderRows() ;
    },
  });
  
  var InvoiceTransaction = {
    UIInvoiceTransactionList: UIInvoiceTransactionList
  };
  
  return InvoiceTransaction ;
});
