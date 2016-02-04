define([
    'jquery', 
    'underscore', 
    'backbone', 
    'service/service',
    'module/Module',
    'ui/UITable',
    'ui/UIPopup',
    'ui/UIBean',
    'ui/UIBreadcumbs',
    'module/account/Account'
], function($, _, Backbone, service, module, UITable, UIPopup, UIBean, UIBreadcumbs , Account) {
  
  var UIPointConversionRule = UIBean.extend({
    label: "PointConversionRule",
    config: {
      beans: {
        pointConversionRule: {
          label: 'PointConversionRule',
          fields: [
            { field: "name", label: "Name", required: true, validator: {name: "empty"}, },
            { field: "organizationLoginId", label: "Organization LoginId", required: true, validator: {name: "empty"},
              custom: {
                getDisplay: function(bean) {
                  return bean.organizationLoginId == null ? null : bean.organizationLoginId;
                },
                
                set: function(bean, obj) { bean.organizationLoginId = obj ; },
                
                autocomplete: {
                  search: function(val) {
                    var accounts = service.AccountService.findAccountByLoginIdOrg(val).data ;
                    var result = [] ;
                    for(var i = 0; i < accounts.length; i++) {
                      var account = accounts[i] ;
                      result[i] = {value: account.loginId, label: account.loginId + '(' + account.email + ')'} ;
                    }
                    return result ;
                  }
                }
              }
            },
            {
              field : "status", label : "Status" ,
              defaultValue: 'Active',
              select: {
                getOptions: function(field, bean) {
                  var options = [
                    { label: 'Active', value: 'Active' },
                    { label: 'InActive', value: 'InActive' }
                  ];
                  return options ;
                }
              }
            },
            
            
            { 
              field: "minPointToTrigger", label: "Min Point To Trigger", defaultValue: 0,
              validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { 
              field: "pointToCreditRatio", label: "Point To Credit Ratio", defaultValue: 0,
              validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            }
          ],
          edit: {
            disable: true , 
            actions: [
              {
                action:'save', label: "Save", icon: "check",
                onClick: function(thisUI, beanConfig, beanState) { 
                  if(!thisUI.validate()) {
                    thisUI.render() ;
                    return ;
                  }
                  var pointConversionRule = thisUI.getBean('pointConversionRule') ;
                  service.CustomerService.savePointConversionRatio(pointConversionRule) ;
                  if(thisUI.UIParent.onRefresh != null) {
                    thisUI.UIParent.onRefresh() ;
                  }
                  UIPopup.closePopup() ;
                }
              },
              {
                action:'cancel', label: "Cancel", icon: "back",
                onClick: function(thisUI, beanConfig, beanState) { 
                  UIPopup.closePopup() ;
                }
              }
            ],
          }
        }
      }
    },
    
    init: function(UIParent, pointConversionRule, isNew) {
      this.UIParent = UIParent;
      
      this.bind('pointConversionRule', pointConversionRule) ;
      var config = this.getBeanConfig('pointConversionRule') ;
      config.disableEditAction(false) ;
      
      this.getBeanState('pointConversionRule').editMode = true ; 
      config.disableField('name', !isNew) ;
      return this ;
    },
  });
  
  var UIPointConversionRuleList = UITable.extend({
    label: 'PointConversionRules',
    config : {
      toolbar: {
        dflt: {
          actions: [
            { 
              action: "onNewPointConversionRule", icon: "add", label: "New",
              onClick: function(thisUI) { 
                var  pointConversionRule = { name: "", organizationLoginId: "" } ;
                UIPopup.activate(new UIPointConversionRule().init(thisUI, pointConversionRule, true)) ;
              } 
            },
            { 
              action: "onRefresh", icon: "refresh", label: "Refresh", 
              onClick: function(thisUI) { 
                thisUI.onRefresh();
              } 
            }
          ]
        },
      },
      bean: {
        fields: [
          { label: 'Id', field: 'id', toggled: true, filterable: true },
          { label: 'Name', field: 'name', toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var pointConversionRule = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UIPointConversionRule().init(thisUI, pointConversionRule, false)) ;
            }
          },
          { label: 'Organization LoginId', field: 'organizationLoginId', toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var pointConversionRule = thisUI.getItemOnCurrentPage(row) ;
              var account = service.AccountService.getAccountByLoginId(pointConversionRule.organizationLoginId).data;
              var uiDetail = thisUI.UIAccountDetail.init(thisUI, account, true) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
          
          { label: 'Status', field: 'status', toggled: true, filterable: true },
          { label: 'Min Point To Trigger', field: 'minPointToTrigger', toggled: true, filterable: true },
          { label: 'Point To Credit Ratio', field: 'pointToCreditRatio', toggled: true, filterable: true },
        ],
        actions: [
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) { 
              var pointConversionRule = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.CustomerService.deletePointConversionRule(pointConversionRule).data ;
              
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    init: function (viewStack) {
      this.UIAccountDetail = new Account.UIAccountDetail() ;
      this.viewStack = viewStack ;

      var pointConversionRules = service.CustomerService.getPointConversionRules().data ;
      this.setBeans(pointConversionRules) ;
      return this;
    },
   
    onRefresh: function() { 
      var pointConversionRules = service.CustomerService.getPointConversionRules().data ;
      this.setBeans(pointConversionRules) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
  });
  
  var UIPointConversionRulesScreen = module.UIScreen.extend({
    initialize: function (options) { },
    
    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UIPointConversionRuleList().init(this.viewStack);
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { },
  });
  return UIPointConversionRulesScreen ;
});
