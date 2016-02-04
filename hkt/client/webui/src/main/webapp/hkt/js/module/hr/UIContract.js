define([ 
  'service/service',
  'module/cms/UICMSNode',
  'i18n'
], function(service, UICMSNode, i18n) {
  var res = i18n.getResource('module/hr/hr');
  
  var UIContract = UICMSNode.extend({
    label: res('UIContract.label'),
    config: {
      label: res('UIContract.label'),
      actions: [
        { 
          action: "back", label: res('UIContract.action.back'),
          onClick: function(thisUI) {
            console.log("on click back") ;
          }
        },
        {
          action: "save", label: res('UIContract.action.save'),
          onClick: function(thisUI) {
            console.log("on click save") ;
          }
        }
      ]
    },
    
    init: function(UIParent, contractNodeDetail, isNew) {
      var template = service.CMSService.getTemplate("employee/contract").data ;
      var templateUI = JSON.parse(template.template) ;
      this.config.tabs = templateUI.tabs ;
      this.bind(contractNodeDetail) ;
      this.UIParent = UIParent ;
      return this ;
    },
  });
  
  return UIContract ;
});