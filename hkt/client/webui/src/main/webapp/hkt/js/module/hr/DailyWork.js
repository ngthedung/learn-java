define([
  'service/service',
  'ui/UIBean',
  'ui/UIPopup',
  'util/DateTime',
  'i18n',
], function(service, UIBean, UIPopup, DateTime, i18n) {
  var res = i18n.getResource('module/hr/hr');
  
  var UIDailyWork = UIBean.extend({
    label: res('UIDailyWork.label'),
    config:  {
      beans: {
        daily: {
          label : "Daily Work Information",
          fields : [
            { field: "loginId", label: res('UIDailyWork.field.loginId'), required: true, validator: {name: "empty"} },
            { field: "startTime", label: res('UIDailyWork.field.startTime'),
              datepicker: {
                getDate: function(bean) {
                  return DateTime.fromDateTimeToDDMMYYYY(bean.startTime) ;
                },
                setDate: function(date, bean) {
                  bean.startTime = DateTime.fromDDMMYYYYToDateTime(date) ;
                },
              }
            },
            { field: "endTime", label: res('UIDailyWork.field.endTime'),
              datepicker: {
                getDate: function(bean) {
                  return DateTime.fromDateTimeToDDMMYYYY(bean.endTime) ;
                },
                setDate: function(date, bean) {
                  bean.endTime = DateTime.fromDDMMYYYYToDateTime(date) ;
                },
              }
            },
            { field: "note", label: res('UIDailyWork.field.note'), textarea : {} }
          ],
          edit: {
            disable: true , 
            actions: [
              {
                action:'save', label: res('UIDailyWork.action.save'), icon: "check",
                onClick: function(thisUI) {
                  if(!thisUI.validate()) {
                    thisUI.render() ;
                    return ;
                  }
                  var daily = thisUI.getBean('daily') ;
                  service.HRService.saveDailyWork(thisUI.UIParent.position, daily) ;
                  if(thisUI.UIParent.onRefresh != null) {
                    thisUI.UIParent.onRefresh() ;
                  }
                  UIPopup.closePopup() ;
                }
              },
              {
                action:'cancel', label: res('UIDailyWork.action.cancel'), icon: "back",
                onClick: function() { 
                  UIPopup.closePopup() ;
                }
              }
            ],
          }
        }
      }
    },
    init: function(UIParent, daily) {
      this.UIParent = UIParent ;
      this.bind('daily', daily) ;
      var dailyConfig = this.getBeanConfig('daily') ;
      dailyConfig.disableEditAction(false) ;
      this.getBeanState('daily').editMode = true ;
      
      dailyConfig.disableField('positionId', true) ;
      dailyConfig.disableField('loginId', true) ;
      return this ;
    }
  }) ;
  
  var DailyWork = {
    UIDailyWork : UIDailyWork
  };
  
  return DailyWork ;
});
