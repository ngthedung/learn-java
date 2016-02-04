define([
  'service/service',
  'ui/UITable',
  'ui/UIBean',
  'ui/UIPopup',
  'util/DateTime',
], function(service, UITable, UIBean, UIPopup, DateTime) {
  
  var UIPointTransaction = UIBean.extend({
    label: 'PointTransaction Item',
    config: {
      beans: {
        item: {
          label: 'item',
          fields : [
            { 
              field : "pointConversionRuleId", label : "Point Conversion Rule Id", required: true, 
              validator: {name: "empty"},
              autocomplete: {
                search: function(val) {
                  var ratios = service.CustomerService.findPointConversionRuleByName(val).data ;
                  var result = [] ;
                  for(var i = 0; i < ratios.length; i++) {
                    var ratio = ratios[i] ;
                    result[i] = {value: ratio.id, label: ratio.id + ' (' + ratio.name + ')'} ;
                  }
                  return result ;
                }
              }
            },
            { field : 'dateExecute', label : "Date Execute",
              datepicker: {
                getDate: function(bean) {
                  return DateTime.fromDateTimeToDDMMYYYY(bean.dateExecute) ;
                },
                setDate: function(date, bean) {
                  bean.dateExecute = DateTime.fromDDMMYYYYToDateTime(date) ;
                },
              }
            },
            { 
              field: "point",   label: "Point",
              validator: { 
                name: 'number', errorMsg: "Expect a number" 
              }
            },
            
            { field: "description",   label: "Description", textarea: {} }
          ],
          edit: {
            actions: [
              {
                action:'save', label: "Save", icon: "check",
                onClick: function(thisUI) { 
                  if(!thisUI.validate()) {
                    thisUI.render() ;
                    return ;
                  }
                  var item = thisUI.getBean('item');
                  thisUI.UIParent.save(item) ;
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
  
    init: function(item, UIPointTransactionList, isNew) {
      this.isNew = isNew ;
      this.UIParent = UIPointTransactionList ;
      this.bind('item', item, true) ;
      this.getBeanState('item').editMode = true ; 
      var itemConfig = this.getBeanConfig('item') ;
      itemConfig.disableField('pointConversionRuleId', !this.isNew) ;
      itemConfig.disableField('point', !this.isNew) ;
      return this ;
    },
  });
  
  var UIPointTransactionList = UITable.extend({
    label: 'PointTransaction Item',
    
    config : {
      toolbar: {
        dflt: {
          actions: [
            {
              icon: "add", label: "New Item", 
              onClick: function(thisUI, row) { 
                var item = {
                  pointConversionRuleId : "", dateExecute : DateTime.getCurrentDate(), point : ""
                };
                UIPopup.activate(new UIPointTransaction().init(item, thisUI, true)) ;
              } 
            }
          ]
        }
      },
      bean: {
        fields: [
           { label : 'Point Conversion Rule', field : 'pointConversionRuleId', toggled : true, filterable : true}, 
           { label : 'Date Execute', field : 'dateExecute', toggled : true, filterable : true }, 
           { label : 'Point', field : 'point', toggled : true, filterable : true },
           { label : 'Description', field : 'description', toggled : true },
        ],
        actions: [
          {
            icon: "edit", label: "Edit PointTransaction Item",
            onClick: function(thisUI, row) {
              var item = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UIPointTransaction().init(item, thisUI, false)) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) { 
              var item = thisUI.getItemOnCurrentPage(row) ;
              var deleted = 
                service.CustomerService.deletePointTransaction(item).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
                thisUI.UIParent.onRefresh();
              }
            }
          }
        ]
      }
    },
    
    save: function (item) {
      this.listPointTransaction[this.listPointTransaction.length] = item ;
      var listTransaction = new Array();
      listTransaction[0] = item;
      this.PointDetail.pointTransactions = listTransaction;
      service.CustomerService.savePointDetail(this.PointDetail);
      this.UIParent.onRefresh();
    },
    
    init: function(UIParent, PointDetail, Customer) {
      this.Customer = Customer;
      this.PointDetail = PointDetail;
      this.setBeans(PointDetail.pointTransactions) ;
      this.listPointTransaction = PointDetail.pointTransactions;
      this.UIParent = UIParent ;
      this.renderRows();
      return this ;
    },
    
    onRefresh: function() { 
      this.setBeans(this.listPointTransaction) ;
      this.renderRows();
    },
    
  });
  
  var PointTransaction = {
      UIPointTransactionList: UIPointTransactionList
  };
  
  return PointTransaction ;
});
