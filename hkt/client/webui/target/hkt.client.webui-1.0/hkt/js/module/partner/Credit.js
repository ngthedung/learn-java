define([
  'service/service',
  'ui/UICollapsible',
  'ui/UIBean',
  'module/partner/CreditTransaction',
], function(service, UICollapsible, UIBean, CreditTransaction) {  
  var UICredit = UIBean.extend({
    label: "Credit",
    config: {
      beans: {
        Credit: {
          label: 'Credit',
          fields: [
            { 
              field: "loginId",   label: "Login Id", required: true,
              validator: {name: "empty"} 
            },
            { 
              field: "credit",   label: "Credit", defaultValue: 0,
              validator: { 
                name: 'number', from: 0, errorMsg: "Expect a number > 0" 
              }
            }
          ]
        }
      }
    },
    
    initViewOnly: function (Credit) {
      this.bind('Credit', Credit) ;
      this.setReadOnly(true) ;
      return this ;
    },
    
  });
  
  var UICreditDetail = UICollapsible.extend({
    label: "Credit Detail",
    config: { },
    
    init: function(CreditDetail) {
      this.clear() ;
      this.CreditDetail = CreditDetail ;
      this.UICredit = new UICredit().initViewOnly(CreditDetail.credit);
      this.add(this.UICredit) ;
      this.CreditTransaction = new CreditTransaction.UICreditTransactionList().init(this, CreditDetail) ;
      this.add(this.CreditTransaction) ;
      return this ;
    },
    
    onRefresh: function() {
      this.clear() ;
      this.CreditDetail = service.CustomerService.getCreditDetailById(this.CreditDetail.credit.id).data;
      this.UICredit = new UICredit().initViewOnly(this.CreditDetail.credit);
      this.add(this.UICredit) ;
      this.CreditTransaction = new CreditTransaction.UICreditTransactionList().init(this, this.CreditDetail) ;
      this.add(this.CreditTransaction) ;
      this.render() ;
    },
    
  });
  
  var Credit = {
      UICredit:       UICredit,
      UICreditDetail: UICreditDetail
  };
    
  return Credit ;
});
