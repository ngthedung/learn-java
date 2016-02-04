define([
  'jquery', 
  'underscore', 
  'backbone',
  'module/Module',
  'ui/UIBreadcumbs',
  'service/service',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UICollapsible',
  'ui/UIBean',
  'module/account/Account',
  'util/DateTime',
  'i18n',
], function($, _, Backbone,module, UIBreadcumbs, service, UITable, UIPopup, UICollapsible, UIBean, Account,DateTime, i18n) { 
  
  var res = i18n.getResource('module/hr/hr');
  
  var UIEmployee = UIBean.extend({
    label: res('UIEmployee.label'),
    config: {
      beans: {
        employee: {
          label: res('UIEmployee.label'),
          fields: [
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
    
    init: function(employee) {
      this.bind('employee', employee) ;
      var employeeConfig = this.getBeanConfig('employee') ;
      employeeConfig.disableEditAction(false) ;
      this.getBeanState('employee').editMode = true ;     
      return this ;
    },
    save: function(account){
      var employee = this.getBean('employee') ;
      employee.loginId = account.loginId;
      employee = service.HRService.saveEmployee(employee).data;
      return employee;
    }
  });
  var WorkPositions = UITable.extend({
    label: res('UIWorkPosition.label'),
    config: {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNew", icon: "add", label: res('UIWorkPositionList.action.onNewPosition'),
              onClick: function(thisTable) { 
                thisTable.onAddBean() ;
              } 
            }
          ]
        },
        filter: {
          fields: [
            { label : 'Position Title', field : 'positionTitle', type: 'string', operator: 'LIKE'}
          ],
          onFilter: function(thisTable, query) {
            console.log('onFilter');
          },
        }
      },
      
      bean: {
        label: 'WorkPositions',
        fields: [
          { label : res('UIWorkPosition.field.positionTitle'), field : 'positionTitle',
            toggled : true, filterable : true,
            select: {
              getOptions: function(field, bean) {
                var optionGroup = service.GenericOptionService.getOptionGroup(
                    'hr', 'HRService', 'work-position').data ;
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
          { label : res('UIWorkPosition.field.group'), field : 'group', toggled : true, filterable : true ,
            custom: {
              getDisplay: function(bean) {
                return bean.group == null ? null : bean.group ;
              },
              set: function(bean, obj) { bean.group = obj ; },
              
              autocomplete: {
                search: function(val) {
                  var accountGroups = service.AccountService.findGroupByName(val).data ;
                  var result = [] ;
                  for(var i = 0; i < accountGroups.length; i++) {
                    var accGroup = accountGroups[i] ;
                    result[i] = {value: accGroup.path, label: accGroup.name + '(' + accGroup.path + ')'} ;
                  }
                  return result ;
                }
              }
            }
          },
          { label : res('UIWorkPosition.field.fromDate'), field : 'fromDate', toggled : true, filterable : true ,
            datepicker: {
              getDate: function(bean) {
                return DateTime.fromDateTimeToDDMMYYYY(bean.fromDate) ;
              },
              setDate: function(date, bean) {
                bean.fromDate = DateTime.fromDDMMYYYYToDateTime(date) ;
              },
            }
          },
          { label : res('UIWorkPosition.field.toDate'), field : 'toDate', toggled : true, filterable : true ,
            datepicker: {
              getDate: function(bean) {
                return DateTime.fromDateTimeToDDMMYYYY(bean.toDate) ;
              },
              setDate: function(date, bean) {
                bean.toDate = DateTime.fromDDMMYYYYToDateTime(date) ;
              },
            }
          },
          { label : res('UIWorkPosition.field.status'), field : 'status', toggled : true, filterable : true ,
            select: {
              getOptions: function(field, bean) {
                var options = [
                 { label: res('UIWorkPosition.field.status.planed'), value: 'Planed' },
                 { label: res('UIWorkPosition.field.status.active'), value: 'Active'},
                 { label: res('UIWorkPosition.field.status.ended'), value: 'Ended' }
                ];
                return options ;
              }
            },
            defaultValue: 'Planed',
          },
          { label : 'Salary Type', field : 'salaryType', toggled : true, filterable : true ,
            select: {
              getOptions: function(field, bean) {
                var options = [
                  { label: res('UIWorkPosition.field.salaryType.hourly'), value: 'Hourly' },
                  { label: res('UIWorkPosition.field.salaryType.daily'), value: 'Daily' },
                  { label: res('UIWorkPosition.field.salaryType.weekly'), value: 'Weekly' },
                  { label: res('UIWorkPosition.field.salaryType.monthly'), value: 'Monthly' },
                  { label: res('UIWorkPosition.field.salaryType.yearly'), value: 'Yearly' },
                  { label: res('UIWorkPosition.field.salaryType.na'), value: 'NA' }
                ];
                return options ;
              }
            },
            defaultValue: res('UIWorkPosition.field.salaryType.hourly'),
          },
          { label : res('UIWorkPosition.field.salaryType'), field : 'salary', toggled : true, filterable : true }
        ],
        actions:[
          {
            icon: "delete", label: "Delete",
            onClick: function(thisTable, row) { 
              thisTable.removeItemOnCurrentPage(row) ;
            }
          },
          {
            icon: "edit", label: "Modified",
            onClick: function(thisTable, row) { 
              thisTable.onEditBean(row) ;
            }
          }
        ]
      }
    },
    init: function(positions) {
      this.positions = positions;
      this.setBeans(positions) ;
      return this ;
    },
    
    save: function(employee){
      for(var i = 0; i< this.positions.length; i++){
        var position = this.positions[i];
        employee = service.HRService.saveWorkPosition(employee, position) ;
      }
    }
  });
  
  var UIEmployeeFull = UICollapsible.extend({
    label: "Employee Information",
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
            thisUI.commitChange() ;
            var account = thisUI.account;
            account = service.AccountService.saveAccount(account).data ;
            var employee = thisUI.UIEmployee.save(account);
            thisUI.WorkPositions.save(employee);
            if(thisUI.UIParent.back) thisUI.UIParent.back(true) ;
            
          }
        }
      ]
    },
    
    init: function(UIParent, account) {
      this.clear() ;
      this.UIParent = UIParent;
      this.account = account ;
      if(account.profiles == null) account.profiles = {} ;
      if(account.contacts == null) account.contacts = [] ;
      
      this.add(new Account.UIAccount().init(this, account, false)) ;
      if(account.profiles.basic == null) account.profiles.basic = {} ;
      if(account.profiles.userRelationships == null) account.profiles.userRelationships = [] ;
      if(account.profiles.userEducations == null) account.profiles.userEducations = [] ;
      if(account.profiles.userWorks == null) account.profiles.userWorks = [] ;
      var employee = {};
      this.UIEmployee = new UIEmployee().init(employee);
      this.add(this.UIEmployee) ;
      var positions = new Array();
      this.WorkPositions = new WorkPositions().init(positions);
      this.add(this.WorkPositions) ;
      this.add(new Account.UIUserBasic().init(account.profiles.basic, false)) ;
      this.UIUserEducation = new Account.UIUserEducation().init(account);
      this.add(this.UIUserEducation, true) ;
      this.UIUserWork = new Account.UIUserWork().init(account);
      this.add(this.UIUserWork, true) ;
      this.UIUserRelationship = new Account.UIUserRelationship().init(account);
      this.add(this.UIUserRelationship, true) ;
      this.commitChange = function() {
        this.UIUserEducation.commitChange() ;
        this.UIUserWork.commitChange() ;
        this.UIUserRelationship.commitChange() ;
        this.UIContact.commitChange() ;
        this.WorkPositions.commitChange() ;
      };
      this.UIContact = new Account.UIContact().init(account);
      this.add(this.UIContact, true) ;
      return this ;
    },
  });
  
  var EmployeeFull = {
      UIEmployeeFull: UIEmployeeFull,
    };
  
  return EmployeeFull ;
});
