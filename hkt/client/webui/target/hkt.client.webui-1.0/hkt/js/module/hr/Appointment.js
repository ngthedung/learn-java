define([
  'service/service',
  'ui/UIPopup',
  'ui/UIBean',
  'util/DateTime',
], function(service, UIPopup, UIBean, DateTime) {
  
  var UIAppointment= UIBean.extend({
    label: "Appointment",
    config: {
      beans: {
        appointment: {
          label: 'Appointment',
          fields: [
            { field: "orgLoginId", label: "Org Login Id", 
              custom: {
                getDisplay: function(bean) { return bean.orgLoginId == null ? null : bean.orgLoginId; },
                set: function(bean, obj) { bean.orgLoginId = obj ; },
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
            { field: "groupPath", label: "Group" },
            { field: "employeeLoginId", label: "Employee Login Id", 
              custom: {
                getDisplay: function(bean) {
                  return bean.employeeLoginId == null ? null : bean.employeeLoginId;
                },
                set: function(bean, obj) { bean.employeeLoginId = obj ;},
                
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
            { field: "partnerLoginId", label: "Partner" },
            { field: "name", label: "Job title", required: true, validator: {name: "empty"} },
            { field: "content",   label: "Content", textarea: {}, toggled: true, filterable: true},
            { field: 'date', label: "Date", toggled: true, filterable: true,
              datepicker: { 
                getDate: function(bean) {
                  if (bean.date == null) { bean.date = DateTime.getCurrentDate(); }
                  return DateTime.fromDateTimeToDDMMYYYY(bean.date) ;
                },
                setDate: function(date, bean) {
                  bean.date = DateTime.fromDDMMYYYYToDateTime(date) ;
                },
              } 
            },
            {
              field : "status", label : "Status",
              defaultValue: "UNACCOMPLISHED",
              select: {
                getOptions: function(field, bean) {
                  var options = [
                    { label: "UNACCOMPLISHED", value: 'UNACCOMPLISHED' },
                    { label: "ONGOING", value: 'ONGOING' },
                    { label: "COMPLETE", value: 'COMPLETE' }
                  ];
                  return options ;
                }
              }              
            },
            { field: "description", label: "Description", textarea: {}, toggled: true, filterable: true}
          ],
          edit: {
            disable: true , 
            actions: [
              {
                action:'save', label: "Save", icon: "check",
                onClick: function (thisUI) { 
                  if (!thisUI.validate()) {
                    thisUI.render() ;
                    return ;
                  }
                  var appointment = thisUI.getBean('appointment') ;
                  service.HRService.saveAppointment(appointment) ;
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
              }
            ],
          }
        }
      }
    },
    
    init: function (UIParent, appointment, isNew) {
      this.UIParent = UIParent ;
      this.bind('appointment', appointment) ;
      
      var appointmentConfig = this.getBeanConfig('appointment') ;
      appointmentConfig.disableEditAction(false) ;
      this.getBeanState('appointment').editMode = true ;
      
//      appointmentConfig.disableField('loginId', !isNew) ;
      return this ;
    },
    
    initViewOnly: function (UIParent, appointment) {
      this.UIParent = UIParent ;
      this.bind('appointment', appointment) ;
      this.getBeanState('appointment').readOnly = true ;
      return this ;
    }
  });
  
  var Appointment = {
    UIAppointment : UIAppointment
  };
  
  return Appointment;
});
