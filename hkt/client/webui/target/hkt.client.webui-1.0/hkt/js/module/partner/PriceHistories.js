define([
  'ui/UITable',
  'ui/UIBean',
  'ui/UIPopup',
  'service/service',
  'module/account/Account'
], function( UITable, UIBean, UIPopup, service, Account) {  
  var UIPriceHistoryList = UITable.extend({
    label: 'Price Histories',
    config : {
      toolbar: {
        dflt: {
          actions: [ ]
        }
      },
      bean: {
        fields: [
           { 
             label : 'Login ID', field : 'supplierLoginId', toggled : true, filterable : true,
             onClick: function(thisUI, row) {
               var priceHistoty = thisUI.getItemOnCurrentPage(row) ;
               var account = service.AccountService.getAccountByLoginId(priceHistoty.supplierLoginId).data;
               var uiDetail = thisUI.UIAccountDetail.init(thisUI, account, true) ;
               thisUI.viewStack.push(uiDetail) ;
             }
           },
           { label : 'Price', field : 'price', toggled : true }, 
           { label : 'Note', field : 'note', toggled : true }
        ]
      }
    },
    
    init: function(viewStack, listHistory) {
      this.setBeans(listHistory) ;
      this.UIAccountDetail = new Account.UIAccountDetail() ;
      this.listHistory = listHistory;
      this.viewStack = viewStack ;
      this.renderRows();
      return this ;
    },  
    
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
    
  });
  
  return UIPriceHistoryList ;
});
