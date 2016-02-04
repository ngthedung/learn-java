define([
  'ui/UITable',
  'ui/UIBean',
  'ui/UIPopup',
], function(UITable, UIBean, UIPopup) {
  
  var UIAttribute = UIBean.extend({
    label: 'Attribute',
    config: {
      beans: {
        attribute: {
          label: 'Attribute',
          fields: [
            { field: "name", label: "Name", required: true, validator: {name: "empty"} },
            { field: "value", label: "Value", required: true, validator: {name: "empty"} },
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
                  var attribute = thisUI.getBean('attribute') ;
                  if(thisUI.isNew) thisUI.UIParent.save(attribute) ;
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
    
    init: function(UIParent, attribute, isNew) {
      this.isNew = isNew;
      this.UIParent = UIParent ;
      this.bind('attribute', attribute, true) ;
      if(isNew){ this.getBeanState('attribute').editMode = true ; }
      return this ;
    }
  });
  
  var UIAttributeList = UITable.extend({
    label: 'Invoice Attribute',
    config : {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNewAttribute", icon: "add", label: "New Attribute", 
              onClick: function(thisUI) { 
                var Attribute = { loginId : "", role : "" };
                UIPopup.activate(new UIAttribute().init(thisUI, Attribute, true)) ;
              } 
            }
          ]
        }
      },
      bean: {
        fields: [
          { label : 'Name', field : 'name', toggled : true, filterable : true },
          { label : 'Value', field : 'value', toggled : true, filterable : true }
        ],
        actions: [
          {
            icon: "edit", label: "Edit Attribute",
            onClick: function (thisUI, row) { 
              var Attribute = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UIAttribute().init(thisUI, Attribute, false)) ;
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
    
    save: function (attribute) {
      this.listAttribute[this.listAttribute.length] = attribute ; 
    },
    
    init: function(listAttribute) {
      this.listAttribute = listAttribute;
      this.setBeans(this.listAttribute) ;
      return this ;
    },
    
    total:function(){
          return 0;
        },
    onRefresh: function() { 
      this.setBeans(this.listAttribute) ;
      this.renderRows() ;
    },
  });
  
  var Attribute = {
    UIAttributeList: UIAttributeList
  };
  
  return Attribute ;
});
