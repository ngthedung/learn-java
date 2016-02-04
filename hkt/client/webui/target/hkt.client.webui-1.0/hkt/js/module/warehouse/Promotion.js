define([
  'service/service',
  'ui/UIBean',
  'ui/UICollapsible',
  'module/warehouse/CustomerTarget',
  'module/warehouse/LocationTarget',
  'module/warehouse/ProductTarget',
  'util/DateTime',
], function(service, UIBean, UICollapsible, CustomerTarget, LocationTarget, ProductTarget, DateTime) {
  
  var UIPromotion = UIBean.extend({
    label: 'Promotion',
    config: {
      beans: {
        promotion : {
          label: 'Promotion',
          fields : [
            { 
              field : 'name', label : 'Name', required: true, validator: { name: 'empty' }
            },
            { 
              field : "organizationLoginId", label : "Organization LoginId",
              required: true, validator: { name: 'empty' },
              custom: {
                getDisplay: function(bean) {
                  return bean.organizationLoginId == null ? null : bean.organizationLoginId;
                },
                set: function(bean, obj) { bean.organizationLoginId = obj ;},
                
                autocomplete: {
                  search: function(val) {
                    var accounts = service.AccountService.findAccountByLoginIdOrg(val).data ;
                    var result = [] ;
                    for(var i = 0; i < accounts.length; i++) {
                      var account = accounts[i] ;
                      result[i] = { value: account.loginId, label: account.loginId + '(' + account.email + ')'} ;
                    }
                    return result ;
                  }
                }
              }
            },
            { field : "fromDate", label : "From Date",
              datepicker: {
                getDate: function(bean) {
                  return DateTime.fromDateTimeToDDMMYYYY(bean.fromDate) ;
                },
                setDate: function(date, bean) {
                  bean.fromDate = DateTime.fromDDMMYYYYToDateTime(date) ;
                },
              }
            },
            { field : "toDate", label : "To Date",
              datepicker: {
                getDate: function(bean) {
                  return DateTime.fromDateTimeToDDMMYYYY(bean.toDate) ;
                },
                setDate: function(date, bean) {
                  bean.toDate = DateTime.fromDDMMYYYYToDateTime(date) ;
                },
              }
            },
            { 
              field : "status", label : "Status",
              defaultValue: 'Planned',
              select: {
                getOptions: function(field, bean) {
                  var options = [
                     { label: 'Planned', value: "Planned" },
                     { label: 'Active', value: "Active" },
                     { label: 'Expired', value: "Expired" }
                  ];
                  return options ;
                }
              }
            },
            { 
              field : "maxUsePerCustomer", label : "Max Use Per Customer",
              defaultValue: 0, required: true, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { 
              field : "discountRate", label : "Discount Rate",
              defaultValue: 0, required: true, validator: { name: 'number', from: 0, errorMsg: "Expect a number >= 0" }
            },
            { 
              field : "discount", label : "Discount",
              defaultValue: 0, required: true, validator: { name: 'number', from: 0, errorMsg: "Expect a number >= 0" }
            },
            { 
              field : "appliedToMinExpense", label : "Applied ToMin Expense",
              defaultValue: 0, required: true, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { 
              field : "maxExpense", label : "Max Expense",
              defaultValue: 0, required: true, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { field : "currencyUnit", label : "Currency Unit",
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
            { field : "description", label : "Description", textarea: {} },
          ],
        }
      }
    },
    init: function(promotion,isNew ,readOnly ) {
      this.bind('promotion', promotion, true) ;
      this.setReadOnly(readOnly);
      if (isNew) { this.getBeanState('promotion').editMode = true ; }
      return this ;
    },
    
    initViewOnly: function(UIParent, promotion) {
      this.UIParent = UIParent ;
      this.bind('promotion', promotion) ;
      this.getBeanState('promotion').readOnly = true ;
      return this ;
    }
    
  });
  
  var UIPromotionDetail = UICollapsible.extend({
      label: "Promotion",
      config: {
        actions: [
          { 
            action: "back", label: "Back",
            onClick: function(thisUI) {
              if(thisUI.UIParent.back) thisUI.UIParent.back(false) ; 
            }
          },
          {
            action: "save", label: "Save",
            onClick: function(thisUI) {
              if(!thisUI.UIPromotion.validate()) {
                thisUI.render() ;
                return ;
              }
              service.PromotionService.savePromotion(thisUI.promotionDetail) ;
              if(thisUI.UIParent.back) thisUI.UIParent.back(true) ;
            }
          }
        ]
      },
      
      init: function(UIParent, promotionDetail, isNew, readOnly) {
        this.isNew = isNew;
        this.clear() ;
        this.UIParent = UIParent ;
        if(readOnly) this.setActionHidden('save', true) ;
        
        this.promotionDetail = promotionDetail ;
        this.UIPromotion = new UIPromotion().init(promotionDetail, isNew, readOnly);
        this.add(this.UIPromotion) ;
        
        this.CustomerTarget = new CustomerTarget.UICustomerTargetList().init(promotionDetail.customerTargets);
        this.add(this.CustomerTarget, true) ;

        this.LocationTarget = new LocationTarget.UILocationTargetList().init(promotionDetail.locationTargets);
        this.add(this.LocationTarget, true) ;
        
        this.ProductTarget = new ProductTarget.UIProductTargetList().init(promotionDetail.productTargets);
        this.add(this.ProductTarget, true) ;
        
        return this ;
      }
  });
  
  var Promotion = {
      UIPromotionDetail: UIPromotionDetail,
      UIPromotion: UIPromotion
    };
    
    return Promotion ;
});
