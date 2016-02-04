define([
  'jquery', 
  'underscore',
  'backbone',
  'service/service',
  'ui/UITable',
  'ui/UIBean',
  'ui/UIPopup'
], function($, _, Backbone, service, UITable, UIBean, UIPopup) {
  
  var UIOption = UIBean.extend({
    label: this.label,
    config: {
      beans: {
        option: {
          label: this.label,
          fields: [
            { field: "code", label: "Code" },
            { field: "label", label: "Name" },
            { field: "priority", label: "Priority" },
            { field: "description", label: "Description", textarea: {} }
          ],
          edit: {
            disable: true ,
            actions: [
              {
                action:'save', label: "Save", icon: "check",
                onClick: function(thisTable) { 
                  var option = thisTable.getBean('option') ;
                  var optionGroup = thisTable.UIParent.optionGroup;
                  if(thisTable.isNew){
                    optionGroup.options[optionGroup.options.length] = option;
                  }
                  service.GenericOptionService.saveOptionGroup(optionGroup) ;
                  if(thisTable.UIParent.onRefresh != null) {
                    thisTable.UIParent.onRefresh() ;
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
    
    init: function(UIParent, option, isNew) {
      this.label = UIParent.label;
      this.isNew = isNew;
      this.UIParent = UIParent ;
      this.bind('option', option, true) ;
      
      var config = this.getBeanConfig('option') ;
      config.disableEditAction(false) ;
      config.disableField('code', !isNew) ;
      this.getBeanState('option').editMode = true ;
      
      return this ;
    },
  });
  
  var UIOptionList = UITable.extend({
    label: this.label+"s",
    config: {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNew", icon: "add", label: "New", 
              onClick: function(thisTable) { 
                var option = { label : "" } ;
                UIPopup.activate(new UIOption().init(thisTable, option, true)) ;
              } 
            },
            {
              action: "onRefresh", icon: "refresh", label: "Refresh",
              onClick: function(thisTable) { 
                thisTable.onRefresh() ;
              }
            }
          ]
        }
      },
      
      bean: {
        label: this.label,
        fields: [
          { 
            label : 'Code', field : 'code', toggled : true, filterable : true,
            onClick: function(thisTable, row) {
              var option = thisTable.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UIOption().init(thisTable, option, false)) ;
            }
          },
          { label : 'Name', field : 'label', toggled : true, filterable : true, },
          { label : "Priority", field : "priority",  toggled: true, filterable: true, },
          { label : 'Description', field : 'description', toggled : true, filterable : true, }
        ],
        actions: [
          {
             icon: "delete", label: "Delete",
             onClick: function(thisTable, row) {
               var optionGroup = thisTable.optionGroup;
               service.GenericOptionService.deleteOption(optionGroup, row) ;
               thisTable.removeItemOnCurrentPage(row) ;
            }
          }
        ]
      }
    },
    
    onRefresh: function() {
      var options = this.optionGroup.options;
      this.setBeans(options) ;
      this.renderRows() ;
    },
    
    init: function ( moduleName, serviceName, beanName, label ) {
      this.label = label;
      this.optionGroup = service.GenericOptionService.getOptionGroup(moduleName,serviceName,beanName).data ;
      var options = this.optionGroup.options;
      this.setBeans(options) ;
      return this ;
    }
  });
  
  return UIOptionList ;
});
