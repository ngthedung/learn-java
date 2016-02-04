define([
  'ui/UITable',
  'ui/UIBean',
  'ui/UIPopup',
], function(UITable, UIBean, UIPopup) {
  
  var UILocationTarget = UIBean.extend({
    label: 'Location Target',
    config: {
      beans: {
        locationTarget: {
          label: 'Location Target',
          fields: [
            { field: "location", label: "Location", required: true, validator: {name: "empty"} }
          ],
          edit: {
            disable: false , 
            actions: [
              {
                action:'save', label: "Save", icon: "check",
                onClick: function(thisUI) { 
                  if(!thisUI.validate()) {
                    thisUI.render() ;
                    return ;
                  }
                  var locationTarget = thisUI.getBean('locationTarget') ;
                  if(thisUI.isNew) thisUI.UIParent.save(locationTarget) ;
                  if(thisUI.UIParent.onRefresh != null) {
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
    
    init: function(UIParent, locationTarget, isNew) {
      this.isNew = isNew;
      this.UIParent = UIParent ;
      this.bind('locationTarget', locationTarget, true) ;
      if(isNew){ this.getBeanState('locationTarget').editMode = true ; }
      return this ;
    }
  });
  
  var UILocationTargetList = UITable.extend({
    label: 'Promotion Location Target',
    config : {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNewLocationTarget", icon: "add", label: "New Location Target", 
              onClick: function(thisUI) { 
                var LocationTarget = {
                    location : ""
                };
                UIPopup.activate(new UILocationTarget().init(thisUI, LocationTarget, true)) ;
              } 
            }
          ]
        }
      },
      bean: {
        fields: [
          { label : 'Location', field : 'location', toggled : true, filterable : true }
        ],
        actions: [
          {
            icon: "edit", label: "Edit Location Target",
            onClick: function (thisUI, row) { 
              var LocationTarget = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UILocationTarget().init(thisUI, LocationTarget, false)) ;
              thisUI.onEditBean(row) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function (thisUI, row) { 
              thisUI.markDeletedItemOnCurrentPage(row) ;
            }
          }
        ]
      }
    },
    
    save: function (locationTarget) {
      this.listLocationTarget[this.listLocationTarget.length] = locationTarget ; 
    },
    
    init: function(listLocationTarget) {
      this.listLocationTarget = listLocationTarget;
      this.setBeans(this.listLocationTarget) ;
      return this ;
    },
    
    onRefresh: function() { 
      this.setBeans(this.listLocationTarget) ;
      this.renderRows() ;
    },
  });
  
  var LocationTarget = {
      UILocationTargetList: UILocationTargetList
  };
  
  return LocationTarget ;
});
