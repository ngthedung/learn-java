define([
  'service/service',
  'ui/UITable',
  'ui/UIBean',
  'ui/UIPopup',
  'util/DateTime',
], function(service, UITable, UIBean, UIPopup, DateTime) {
  
  var UICreditTransaction = UIBean.extend({
    label: 'CreditTransaction Item',
    config: {
      beans: {
        item: {
          label: 'item',
          fields : [
            { field : 'dateExecute', label : "Date Execute",
              datepicker: {
                getDate: function(bean) {
                  return DateTime.fromDateTimeToDDMMYYYY(bean.dateExecute) ;
                },
                setDate: function(date, bean) {
                  bean.dateExecute = DateTime.fromDDMMYYYYToDateTime(date) ;
                },
              }
            },
            { 
              field: "amount",   label: "Amount",
              validator: { 
                name: 'number', errorMsg: "Expect a number" 
              }
            },
            
            { field: "description",   label: "Description", textarea: {} }
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
                  var item = thisUI.getBean('item');
                  thisUI.UIParent.save(item) ;
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
  
    init: function(item, UICreditTransactionList, isNew) {
      this.isNew = isNew ;
      this.UIParent = UICreditTransactionList ;
      this.bind('item', item, true) ;
      this.getBeanState('item').editMode = true ; 
      var itemConfig = this.getBeanConfig('item') ;
      itemConfig.disableField('amount', !this.isNew) ;
      return this ;
    },
  });
  
  var UICreditTransactionList = UITable.extend({
    label: 'CreditTransaction Item',
    
    config : {
      toolbar: {
        dflt: {
          actions: [
            {
              icon: "add", label: "New Item", 
              onClick: function(thisUI, row) { 
                var item = {
                  dateExecute: DateTime.getCurrentDate(), amount: ""
                };
                UIPopup.activate(new UICreditTransaction().init(item, thisUI, true)) ;
              } 
            }
          ]
        }
      },
      bean: {
        fields: [
           { label : 'Date Execute', field : 'dateExecute', toggled : true, filterable : true }, 
           { label : 'Amount', field : 'amount', toggled : true, filterable : true },
           { label : 'Description', field : 'description', toggled : true },
        ],
        actions: [
          {
            icon: "edit", label: "Edit CreditTransaction Item",
            onClick: function(thisUI, row) {
              var item = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UICreditTransaction().init(item, thisUI, false)) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) { 
              var item = thisUI.getItemOnCurrentPage(row) ;
              var deleted = 
                service.CustomerService.deleteCreditTransaction(item).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
                thisUI.UIParent.onRefresh();
              }
            }
          }
        ]
      }
    },
    
    save: function (item) {
      this.listCreditTransaction[this.listCreditTransaction.length] = item ;
      var listTransaction = new Array();
      listTransaction[0] = item;
      this.CreditDetail.creditTransactions = listTransaction;
      service.CustomerService.saveCreditDetail(this.CreditDetail) ;
      this.UIParent.onRefresh();
    },
    
    init: function(UIParent, CreditDetail) {
      this.CreditDetail = CreditDetail;
      this.setBeans(CreditDetail.creditTransactions) ;
      this.listCreditTransaction = CreditDetail.creditTransactions;
      this.UIParent = UIParent ;
      this.renderRows();
      return this ;
    },
    
    onRefresh: function() { 
      this.setBeans(this.listCreditTransaction) ;
      this.renderRows();
    },
    
  });
  
  var CreditTransaction = {
      UICreditTransactionList: UICreditTransactionList
  };
  
  return CreditTransaction ;
});
