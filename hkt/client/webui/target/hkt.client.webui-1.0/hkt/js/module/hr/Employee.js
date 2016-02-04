define([ 
  'util/DateTime',
  'service/service',
  'ui/UIPopup',
  'ui/UIBean',
  'ui/UICollapsible',
  'i18n',
], function(DateTime, service, UIPopup, UIBean, UICollapsible, i18n) {
  var res = i18n.getResource('module/hr/hr');
  
  var UIEmployee = UIBean.extend({
    label: res('UIEmployee.label'),
    config: {
      beans: {
        employee: {
          label: res('UIEmployee.label'),
          fields: [
            { 
              field: "loginId",  label: res('UIEmployee.field.loginId'),
              custom: {
                getDisplay: function(bean) {
                  return bean.loginId == null ? null : bean.loginId;
                },
                set: function(bean, obj) { bean.loginId = obj ;},
                
                autocomplete: {
                  search: function(val, bean) {
                    var accounts = service.AccountService.findAccountByLoginIdUser(val).data ;
                    var result = [] ;
                    for(var i = 0; i < accounts.length; i++) {
                      var account = accounts[i] ;
                      result[i] = { 
                        value: account.loginId, 
                        label: account.loginId +
                        ' ( ' + account.profiles.basic.lastName  +' ' + account.profiles.basic.firstName + ' )'
                      } ;
                    }
                    return result ;
                  }
                }
              }
            },
            { 
              field: "organizationLoginId",  label: res('UIEmployee.field.organizationLoginId'),
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
            { field: "startDate", label: res('UIEmployee.field.startDate'),
              datepicker: { 
                getDate: function(bean) {
                  return DateTime.fromDateTimeToDDMMYYYY(bean.startDate) ;
                },
                setDate: function(date, bean) {
                  bean.startDate = DateTime.fromDDMMYYYYToDateTime(date) ;
                },
              } 
            },
            { field: "leaveDate", label: res('UIEmployee.field.leaveDate'),
              datepicker: { 
                getDate: function(bean) {
                  return DateTime.fromDateTimeToDDMMYYYY(bean.leaveDate) ;
                },
                setDate: function(date, bean) {
                  bean.leaveDate = DateTime.fromDDMMYYYYToDateTime(date) ;
                },
              } 
            }
          ],
          edit: {
            disable: true ,
            actions: [
            ],
          },
        }
      }
    },
    
    initViewOnly: function (employee) {
      this.bind('employee', employee) ;
      this.setReadOnly(true);
      return this ;
    },
    
    init: function(employee, isNew) {
      this.bind('employee', employee) ;
      var employeeConfig = this.getBeanConfig('employee') ;
      employeeConfig.disableEditAction(false) ;
      this.getBeanState('employee').editMode = true ;
      
      employeeConfig.disableField('loginId', !isNew) ;
      employeeConfig.disableField('organizationLoginId', !isNew) ;
      
      return this ;
    }
  });
  
  var UIEmployeeDetail = UICollapsible.extend({
    label: res('UIEmployeeDetail.label'),
    config: {
      actions: [
        { 
          action: "back", label: res('UIEmployeeDetail.action.back'),
          onClick: function(thisUI) {
            if(thisUI.UIParent.back) thisUI.UIParent.back(false) ; 
          }
        },
        {
          action: "save", label: res('UIEmployeeDetail.action.save'),
          onClick: function(thisUI) {
            var employee = thisUI.employee ;
            console.log(employee);
            service.HRService.saveEmployee(employee) ;
            if(thisUI.UIParent.back) thisUI.UIParent.back(true) ;
          }
        }
      ]
    },
    
    init: function(UIParent, employee, isNew) {
      this.clear() ;
      this.UIParent = UIParent ;
      this.employee = employee ;
      this.add(new UIEmployee().init(employee, isNew)) ;
      return this ;
    },
  });
  
  var Employee = {
    UIEmployee : UIEmployee,
    UIEmployeeDetail : UIEmployeeDetail
  };
  
  return Employee ;
});
