define([
  'service/service',
  'module/Module',
  'ui/UIBean',
  'ui/UIPopup',
  'ui/UITable',
  'module/account/Account',
  'module/accounting/Invoice',
  'util/DateTime',
], function (service, module, UIBean, UIPopup, UITable, Account, Invoice, DateTime) {
  
  var UIPromotionUsed = UIBean.extend({
    label: 'Promotion Used',
    config: {
      beans: {
        promotionUsed : {
          fields : [
            { 
              field : 'customerLoginId', label : 'Customer Login Id', required: true, validator: { name: 'empty' },
              custom: {
                getDisplay: function(bean) {
                  return bean.customerLoginId == null ? null : bean.customerLoginId ;
                },
                
                set: function(bean, obj) { bean.customerLoginId = obj ; },
                
                autocomplete: {
                  search: function(val) {
                    var customers = service.CustomerService.findCustomerByCustomerLoginId(val).data ;
                    var result = [] ;
                    for(var i = 0; i < customers.length; i++) {
                      var customer = customers[i] ;
                      result[i] = { value: customer.loginId, label: customer.loginId } ;
                    }
                    return result ;
                  }
                }
              }
            },
            { field : "invoiceId", label : "Invoice Id", },
            { field : "usedDate", label : "Used Date",
              datepicker: {
                getDate: function(bean) {
                  return DateTime.fromDateTimeToDDMMYYYY(bean.usedDate) ;
                },
                setDate: function(date, bean) {
                  bean.usedDate = DateTime.fromDDMMYYYYToDateTime(date) ;
                },
              }
            },
            { 
              field : "expense", label : "Expense",
              defaultValue: 0, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { 
              field : "saving", label : "Saving",
              defaultValue: 0, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
          ],
          edit: {
            disable: true , 
            actions: [
              {
                action:'save', label: "Save", icon: "check", 
                onClick: function(thisUI) {
                  if(!thisUI.validate()) {
                    thisUI.render() ;
                    return ;
                  }
                  var promotionUsed = thisUI.getBean('promotionUsed') ;
                  service.PromotionService.savePromotionUsed(promotionUsed) ;
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
              },
            ],
          }
        }
      }
    },
    init: function(UIParent, promotionUsed, isNew) {
      this.UIParent = UIParent ;
      this.bind('promotionUsed', promotionUsed, true) ;
      var promotionUsedConfig = this.getBeanConfig('promotionUsed') ;
      promotionUsedConfig.disableEditAction(false) ;
      
      this.getBeanState('promotionUsed').editMode = true ;
      promotionUsedConfig.disableField('customerLoginId', !isNew) ;
      promotionUsedConfig.disableField('invoiceId', !isNew) ;
      return this ;
    },
  });
  
  var UIPromotionUsedsList = UITable.extend({
    label: 'PromotionUseds',
    config : {
      toolbar: {
        dflt: {
          actions: [
            { 
              action: "onNewPromotion", icon: "add", label: "New",
              onClick: function(thisUI) {
                var promotionUsed = {
                  promotionId : thisUI.promotion.id, 
                  usedDate : DateTime.getCurrentDate() 
                };
                UIPopup.activate(new UIPromotionUsed().init(thisUI, promotionUsed, true)) ;
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
        filter: {
          fields: [
            { label: 'Search By Customer LoginId', field: 'name', type: 'string', operator: 'LIKE' },
            { label: 'Search By Invoice Id', field: 'organization', type: 'string', operator: 'EQUAL' },
          ],
          onFilter: function(thisUI, query) {
            var result = service.PromotionService.searchPromotionUsed(query).data ;
            var promotionUseds = result.data ;
            thisUI.setBeans(promotionUseds) ;
            thisUI.renderRows() ;
          },
        }
      },
      bean: {
        fields: [
          { 
            label: 'Id', field: 'id', toggled: true, filterable: true,
            onClick: function(thisUI, row) { 
              var promotionUsed = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UIPromotionUsed().init(thisUI, promotionUsed, false)) ;
            }
          },
          { 
            label: 'Customer Login Id', field: 'customerLoginId', toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var promotionUsed = thisUI.getItemOnCurrentPage(row) ;
              var account = service.AccountService.getAccountByLoginId(promotionUsed.customerLoginId).data ;
              var uiDetail = thisUI.UIAccountDetail.init(thisUI, account, true) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
          { 
            label: 'Invoice Id', field: 'invoiceId', toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var promotionUsed = thisUI.getItemOnCurrentPage(row) ;
              var iDetail = service.AccountingService.getInvoiceDetail(promotionUsed.invoiceId).data;
              var uiDetail = new Invoice.UIInvoiceDetail().init(thisUI, iDetail, false, true) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
          { label: 'Used Date', field: 'usedDate', toggled: true, filterable: true },
          { label: 'Expense', field: 'expense', toggled: true, filterable: true },
          { label: 'Saving', field: 'saving', toggled: true, filterable: true },
        ],
        actions: [
          {
            icon: "edit", label: "Edit",
            onClick: function(thisUI, row) { 
              var promotionUsed = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UIPromotionUsed().init(thisUI, promotionUsed, false)) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) { 
              var promotionUsed = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.PromotionService.deletePromotionUsed(promotionUsed).data ;
              if (deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    init: function (UIParent, promotion) {
      this.viewStack = UIParent.viewStack ;
      this.UIAccountDetail = new Account.UIAccountDetail() ;
      
      this.promotion = promotion ;
      var promotionUseds = service.PromotionService.findPromotionUsedByPromotionId(promotion.id).data ;
      this.setBeans(promotionUseds) ;
      return this;
    },
   
    onRefresh: function() { 
      var promotionUseds = service.PromotionService.findPromotionUsedByPromotionId(this.promotion.id).data ;
      this.setBeans(promotionUseds) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
    
  });
  
  var PromotionUseds = {
    UIPromotionUsedsList : UIPromotionUsedsList
  };
  
  return PromotionUseds ;
});
