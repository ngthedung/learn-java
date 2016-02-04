define([
  'service/service',
  'ui/UIPopup',
  'ui/UITable',
  'module/hr/DailyWork',
  'module/hr/WorkPosition',
  'module/account/Account',
  'i18n',
  'util/DateTime',
], function(service,  UIPopup, UITable, DailyWork, WorkPosition, Account, i18n, DateTime) {
  var res = i18n.getResource('module/hr/hr');
  
  var UIDailyWorkList = UITable.extend({
    label: res('UIDailyWorkList.label'),
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewDailyWork", icon: "add", label: res('UIDailyWorkList.action.onNewDailyWork'),
                onClick: function(thisUI) {
                  var daily = {
                    positionId: thisUI.position.id, loginId: thisUI.position.loginId,
                    startTime : DateTime.getCurrentDate(), endTime: "", note: ""
                  } ;
                  UIPopup.activate(new DailyWork.UIDailyWork().init(thisUI, daily)) ;
                } 
              },
              {
                action: "onRefresh", icon: "refresh", label: res('UIDailyWorkList.action.onRefresh'),
                onClick: function(thisUI) { 
                  thisUI.onRefresh() ;
                }
              }
            ],
          },
        },
      bean : {
        fields: [
          { label : 'Id', field : 'id', toggled : true, filterable : true, },
          { label : res('UIDailyWork.field.loginId'), field : 'loginId', toggled : true, filterable : true, 
            onClick: function(thisUI, row) {
              
              var dailyWork = thisUI.getItemOnCurrentPage(row) ;
              var account = service.AccountService.getAccountByLoginId(dailyWork.loginId).data;
              
              var UIAccountDetail = new Account.UIAccountDetail() ;
              var uiDetail = UIAccountDetail.init(thisUI, account, true) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
          { label : res('UIDailyWork.field.startTime'), field : 'startTime', toggled : true, filterable : true },
          { label : res('UIDailyWork.field.endTime'), field : 'endTime', toggled : true, filterable : true },
          { label : res('UIDailyWork.field.note'), field : 'note', toggled : true, filterable : true },
        ],
        actions: [
          {
            icon: "edit", label: "Edit DailyWork",
            onClick: function(thisUI, row) {
              var daily = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate( new DailyWork.UIDailyWork().init(thisUI, daily) ) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) {
              var daily = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.HRService.deleteDailyWorkById(daily.id).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    onRefresh: function() {
      var dailys = service.HRService.findDailyWorkByPositionId(this.position.id).data ;
      this.setBeans(dailys) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
    
    init: function (UIWorkPosition, employee, position) {
      this.employee = employee ;
      this.position = position ;
      this.UIWorkPosition = UIWorkPosition ;
      this.viewStack = UIWorkPosition.viewStack ;
      var dailys = service.HRService.findDailyWorkByPositionId(this.position.id).data ;
      this.setBeans(dailys) ;
      this.renderRows() ;
    }
    
  });
  
  var DailyWorks = {
    UIDailyWorkList : UIDailyWorkList
  };
  
  return DailyWorks ;
});
