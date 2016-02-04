define([
  'service/service',
  'ui/UIPopup',
  'ui/UITable',
  'ui/UICollapsible',
  'module/hr/DailyWorks',
  'module/hr/Employee',
  'module/hr/WorkPosition',
  'module/account/Account',
  'i18n',
  'util/DateTime',
], function(service, UIPopup, UITable, UICollapsible, DailyWorks, Employee, WorkPosition, Account, i18n, DateTime) {
  var res = i18n.getResource('module/hr/hr');
  
  var UIDailyWorks = UICollapsible.extend({
    label: res('UIDailyWork.label'),
    config: {
      actions: [
        {
          action: 'back', label: res('UIContract.action.back'),
          onClick: function (thisUI) {
            if (thisUI.UIParent.back) {
              thisUI.UIParent.back(false) ;
            }
          }
        },
      ]
    },
    
    init: function (UIParent, employee, position) {
      this.UIParent = UIParent ;
      this.clear() ;
      this.add(new Employee.UIEmployee().initViewOnly(UIParent, employee), true);
      this.add(new WorkPosition.UIWorkPosition().initViewOnly(UIParent, position), true);
      
      var UIDailyWorkList = new DailyWorks.UIDailyWorkList() ;
      UIDailyWorkList.init(UIParent, employee, position) ;
      this.add(UIDailyWorkList) ;
    }
  });
  
  var UIWorkPositionList = UITable.extend({
    label: res('UIWorkPositionList.label'),
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewPosition", icon: "add", label: res('UIWorkPositionList.action.onNewPosition'), 
                onClick: function(thisUI) {
                  var position = {
                    employeeId: thisUI.employee.id, 
                    loginId: thisUI.employee.loginId, 
                    positionTitle : "",
                    fromDate : DateTime.getCurrentDate()
                  } ;
                  UIPopup.activate(new WorkPosition.UIWorkPosition().init(thisUI, position, true)) ;
                } 
              },
              {
                action: "onRefresh", icon: "refresh", label: res('UIWorkPositionList.action.onRefresh'),
                onClick: function(thisUI) { 
                  thisUI.onRefresh() ;
                }
              }
            ],
          },
        },
      bean : {
        fields: [
          { label : 'Login Id', field : 'loginId', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var workPosition = thisUI.getItemOnCurrentPage(row) ;
              var account = service.AccountService.getAccountByLoginId(workPosition.loginId).data ;
              
              var UIAccountDetail = new Account.UIAccountDetail() ;
              var uiDetail = UIAccountDetail.init(thisUI, account, true) ;
              thisUI.viewStack.push(uiDetail) ;
            },
          },
          { label : res('UIWorkPosition.field.positionTitle'), field : 'positionTitle', toggled : true, filterable : true }, 
          { label : res('UIWorkPosition.field.group'), field : 'group', toggled : true, filterable : true },
          { label : res('UIWorkPosition.field.fromDate'), field : 'fromDate', toggled : true, filterable : true },
          { label : res('UIWorkPosition.field.toDate'), field : 'toDate', toggled : true, filterable : true },
          { label : res('UIWorkPosition.field.status'), field : 'status', toggled : true, filterable : true },
          { label : res('UIWorkPosition.field.salaryType'), field : 'salaryType', toggled : true, filterable : true },
          { label : res('UIWorkPosition.field.salary'), field : 'salary', toggled : true, filterable : true },
        ],
        actions: [
          {
            icon: "edit", label: "Edit Position",
            onClick: function(thisUI, row) {
              var position = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new WorkPosition.UIWorkPosition().init(thisUI, position)) ;
            }
          },
          {
            icon: "bars", label: "DailyWorks",
            onClick: function(thisUI, row) {
              var position = thisUI.getItemOnCurrentPage(row) ;
              thisUI.UIDailyWorks.init(thisUI, thisUI.employee, position) ;
              thisUI.viewStack.push(thisUI.UIDailyWorks) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) {
              var position = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.HRService.deleteWorkPosition(position).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    onRefresh: function() {
      var positions = service.HRService.findWorkPositionByEmployeeId(this.employee.id).data ;
      this.setBeans(positions) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
    
    init: function (UIEmployeeList, employee) {
      this.employee = employee ;
      this.UIEmployeeList = UIEmployeeList ;
      this.UIDailyWorks = new UIDailyWorks() ;
      this.viewStack = UIEmployeeList.viewStack ;
      
      var positions = service.HRService.findWorkPositionByEmployeeId(this.employee.id).data ;
      this.setBeans(positions) ;
      this.renderRows() ;
    }
    
  });
  
  var WorkPositions = {
    UIWorkPositionList : UIWorkPositionList
  };
  
  return WorkPositions ;
});
