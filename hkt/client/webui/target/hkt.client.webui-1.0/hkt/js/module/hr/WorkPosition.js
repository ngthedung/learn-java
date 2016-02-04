define([
  'service/service',
  'ui/UIBean',
  'ui/UIPopup',
  'util/DateTime',
  'i18n',
], function(service, UIBean, UIPopup, DateTime, i18n) {
  var res = i18n.getResource('module/hr/hr');
  
  var UIWorkPosition = UIBean.extend({
    label: res('UIWorkPosition.label'),
    config:  {
      beans: {
        position: {
          label :res('UIWorkPosition.label'),
          fields : [
            { 
              field : "positionTitle", label : res('UIWorkPosition.field.positionTitle'),
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
            { field : "group", label : res('UIWorkPosition.field.group'),
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
            { field : "fromDate", label : res('UIWorkPosition.field.fromDate'),
              datepicker: {
                getDate: function(bean) {
                  return DateTime.fromDateTimeToDDMMYYYY(bean.fromDate) ;
                },
                setDate: function(date, bean) {
                  bean.fromDate = DateTime.fromDDMMYYYYToDateTime(date) ;
                },
              }
            },
            { field : "toDate", label : res('UIWorkPosition.field.toDate'),
              datepicker: {
                getDate: function(bean) {
                  return DateTime.fromDateTimeToDDMMYYYY(bean.toDate) ;
                },
                setDate: function(date, bean) {
                  bean.toDate = DateTime.fromDDMMYYYYToDateTime(date) ;
                },
              }
            },
            { field : "status", label : res('UIWorkPosition.field.status'),
              defaultValue: res('UIWorkPosition.field.status.planed'), required: true,
              select: {
                getOptions: function(field, bean) {
                  var options = [
                     { label: res('UIWorkPosition.field.status.planed'), value: 'Planed' },
                     { label: res('UIWorkPosition.field.status.active'), value: 'Active'},
                     { label: res('UIWorkPosition.field.status.ended'), value: 'Ended' }
                  ];
                  return options ;
                }
              }
            },
            { field : "salaryType", label : res('UIWorkPosition.field.salaryType'), 
              defaultValue: res('UIWorkPosition.field.salaryType.hourly'), required: true,
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
              }
            },
            { field : "salary", label : res('UIWorkPosition.field.salary'), defaultValue: 0,
              validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
          ],
          edit: {
            disable: true , 
            actions: [
              {
                action:'save', label: res('UIWorkPosition.action.save'), icon: "check",
                onClick: function(thisUI) {
                  if(!thisUI.validate()) {
                    thisUI.render() ;
                    return ;
                  }
                  var position = thisUI.getBean('position') ;
                  service.HRService.saveWorkPosition(thisUI.UIParent.employee, position) ;
                  if(thisUI.UIParent.onRefresh != null) {
                    thisUI.UIParent.onRefresh() ;
                  }
                  UIPopup.closePopup() ;
                }
              },
              {
                action:'cancel', label: res('UIWorkPosition.action.cancel'), icon: "back",
                onClick: function() { 
                  UIPopup.closePopup() ;
                }
              }
            ],
          }
        }
      }
    },
    
    initViewOnly: function(UIParent, position) {
      this.UIParent = UIParent ;
      this.bind('position', position) ;
      this.setReadOnly(true) ;
      return this ;
    },
    
    init: function(UIParent, position) {
      this.UIParent = UIParent ;
      this.bind('position', position, true) ;
      var positionConfig = this.getBeanConfig('position') ;
      positionConfig.disableEditAction(false) ;
      this.getBeanState('position').editMode = true ;
      return this ;
    }
    
  }) ;
  
  var WorkPosition = {
    UIWorkPosition : UIWorkPosition
  };
  
  return WorkPosition;
});
