define([
  'service/service',
  'module/Module',
  'ui/UIPopup',
  'ui/UITable',
  'ui/UIBreadcumbs',
  'ui/UICollapsible',
  'module/warehouse/Promotion',
  'module/warehouse/PromotionUseds',
  'module/account/Account',
  'module/warehouse/Product',
  'util/DateTime',
], function (service, module, UIPopup, UITable, UIBreadcumbs, UICollapsible, Promotion, PromotionUseds,
    Account, Product, DateTime) {
  
  var UIPromotionUsed = UICollapsible.extend({
    label: "Promotion Useds", 
    config: {
      actions: [
        {
          action: 'back', label: 'Back',
          onClick: function (thisUI) {
            if (thisUI.UIParent.back) {
              thisUI.UIParent.back(false) ;
            }
          }
        },
      ]
    },
    
    init: function(UIParent, promotion) {
      this.UIParent = UIParent ;
      this.clear() ;
      this.add(new Promotion.UIPromotion().initViewOnly(UIParent, promotion), true) ;
      var UIPromotionUsedsList = new PromotionUseds.UIPromotionUsedsList();
      UIPromotionUsedsList.init(UIParent, promotion) ;
      this.add(UIPromotionUsedsList) ;
    }
  });
  
  var UIPromotionList = UITable.extend({
    label: 'Promotions',
    config : {
      toolbar: {
        dflt: {
          actions: [
            { 
              action: "onNewPromotion", icon: "add", label: "New",
              onClick: function(thisUI) {
                var promotion = {
                  name : "", organizationLoginId : "",fromDate : DateTime.getCurrentDate(), toDate :"", 
                  status : "", maxUsePerCustomer :"", discountRate : "", discount : "",
                  appliedToMinExpense : "", maxExpense : "",currencyUnit : "",
                  customerTargets : [], productTargets : [], locationTargets : []
                };
                var uiDetail = new Promotion.UIPromotionDetail().init(thisUI, promotion, true, false) ;
                thisUI.viewStack.push(uiDetail) ;
              } 
            },
            { 
              action: "onRefresh", icon: "refresh", label: "Refresh", 
              onClick: function(thisUI) {
                thisUI.onRefresh() ;
              } 
            }
          ]
        },
      },
      bean: {
        fields: [
          { 
            label: 'Id', field: 'id', toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var promotion = thisUI.getItemOnCurrentPage(row) ;
              var uiDetail = new Promotion.UIPromotionDetail().init(thisUI, promotion, false, false) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
          { label: 'Name', field: 'name', toggled: true, filterable: true },
          { 
            label: 'Org Login Id', field: 'organizationLoginId', toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var promotion = thisUI.getItemOnCurrentPage(row) ;
              var account = service.AccountService.getAccountByLoginId(promotion.organizationLoginId).data ;
              var uiDetail = thisUI.UIAccountDetail.init(thisUI, account, true) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
          { label: 'From Date', field: 'fromDate', toggled: true, filterable: true },
          { label: 'To Date', field: 'toDate', toggled: true, filterable: true },
          { label: 'Status', field: 'status', toggled: true, filterable: true },
          { label: 'MUPC', field: 'maxUsePerCustomer', toggled: true, filterable: true },
          { label: 'D Rate', field: 'discountRate', toggled: true, filterable: true },
          { label: 'Discount', field: 'discount', toggled: true, filterable: true },
          { label: 'Applied To Min Expense', field: 'appliedToMinExpense', toggled: true, filterable: true },
          { label: 'Max Expense', field: 'maxExpense', toggled: true, filterable: true },
          { label: 'Unit', field: 'currencyUnit', toggled: true, filterable: true },
          { label: 'Description', field: 'description' },
        ],
        actions: [
          {
            icon: "edit", label: "Edit Promotion",
            onClick: function(thisUI, row) {
              var promotion = thisUI.getItemOnCurrentPage(row) ;
              var uiDetail = new Promotion.UIPromotionDetail().init(thisUI, promotion, true, false) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
          {
            icon: "bars", label: "Promotion Used",
            onClick: function(thisUI, row) {
              var promotion = thisUI.getItemOnCurrentPage(row) ;
              thisUI.UIPromotionUsed.init(thisUI, promotion) ;
              thisUI.viewStack.push(thisUI.UIPromotionUsed) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) { 
              var promotion = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.PromotionService.deletePromotion(promotion).data ;
              if (deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    init: function (viewStack) {
      this.viewStack = viewStack ;
      this.UIPromotionUsed = new UIPromotionUsed() ;
      this.UIProductDetail = new Product.UIProductDetail() ;
      this.UIAccountDetail = new Account.UIAccountDetail() ;
      var promotions = service.PromotionService.getPromotions().data ;
      this.setBeans(promotions) ;
      return this;
    },
   
    onRefresh: function() { 
      var promotions = service.PromotionService.getPromotions().data ;
      this.setBeans(promotions) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if (refresh) {
        this.onRefresh() ;
      }
      this.viewStack.back() ;
    }
  });
  
  var UIPromotionsScreen = module.UIScreen.extend({
    initialize: function (options) { },
    
    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UIPromotionList().init(this.viewStack);
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { },
  });
  return UIPromotionsScreen ;
});
