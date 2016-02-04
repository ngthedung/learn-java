define([
  'jquery', 
  'underscore',
  'backbone',
  'service/service',
  'ui/UITable',
  'ui/UIBean',
  'ui/UIPopup'
], function($, _, Backbone, service, UITable, UIBean, UIPopup) {
  
  var UILanguage = UIBean.extend({
    label: "Language",
    config: {
      beans: {
        language: {
          label: 'Language',
          fields: [
            { field: "index", label: "Index" },
            { field: "code", label: "Code" },
            { field: "name", label: "Name" },
            { field: "description", label: "Description", textarea: {} }
          ],
          edit: {
            disable: true ,
            actions: [
              {
                action:'save', label: "Save", icon: "check",
                onClick: function(thisTable) { 
                  var language = thisTable.getBean('language') ;
                  service.LocaleService.saveLanguage(language) ;
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
    
    init: function(UIParent, language, isNew) {
      this.UIParent = UIParent ;
      this.bind('language', language, true) ;
      
      var config = this.getBeanConfig('language') ;
      config.disableEditAction(false) ;
      
      config.disableField('index', !isNew) ;
      config.disableField('code', !isNew) ;
      this.getBeanState('language').editMode = true ;
      
      return this ;
    },
  });
  
  var UILanguages = UITable.extend({
    label: "Languages",
    config: {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNew", icon: "add", label: "New", 
              onClick: function(thisTable) {
                var language = { name : "" } ;
                UIPopup.activate(new UILanguage().init(thisTable, language, true)) ;
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
        label: 'Language',
        fields: [
          { label : 'Index', field : 'index', toggled : true, filterable : true, },
          { 
            label : 'Code', field : 'code', toggled : true, filterable : true,
            onClick: function(thisTable, row) {
              var language = thisTable.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UILanguage().init(thisTable, language, false)) ;
            }
          },
          { field: "name", label: "Name", defaultValue: 'Language Name', toggled: true, filterable: true, },
          { label : 'Description', field : 'description', toggled : true, filterable : true, }
        ],
        actions: [
          {
            icon: "delete", label: "Delete",
            onClick: function(thisTable, row) {
              var language = thisTable.getItemOnCurrentPage(row) ;
              var deleted = service.LocaleService.deleteLanguage(language).data ;
              if(deleted) {
                thisTable.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    onRefresh: function() {
      var languages = service.LocaleService.getLanguages().data ;
      this.setBeans(languages) ;
      this.renderRows() ;
    },
    
    init: function () {
      var languages = service.LocaleService.getLanguages().data ;
      this.setBeans(languages) ;
      return this ;
    }
  });
  
  return UILanguages ;
});
