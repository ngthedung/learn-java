define([
  'service/service',
  'ui/UITable',
  'ui/UIBean',
  'ui/UIPopup',
], function(service, UITable, UIBean, UIPopup) {
  
  var UIInvoiceItem = UIBean.extend({
    label: 'Invoice Item',
    config: {
      beans: {
        item: {
          label: 'item',
          fields : [
            { 
              field: "itemCode", label: "Item Code",
              required: true, validator: { name: 'empty' } 
            },
            { field : "itemName", label : "Item Name" },
            { 
              field: "quantity", label: "Quantity",
              defaultValue: 0, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { 
              field: "unitPrice", label: "Unit Price",
              defaultValue: 0, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { 
              field: "discountRate", label: "Discount Rate",
              defaultValue: 0,
              validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { 
              field: "discount", label: "Discount",
              defaultValue: 0,
              validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { 
              field: "discountByInvoice", label: "Discount By Invoice",
              defaultValue: 0,
              validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { 
              field: "taxRate",   label: "Tax Rate",
              defaultValue: 0,
              validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { 
              field: "tax",   label: "Tax",
              defaultValue: 0,
              validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { 
              field: "total",   label: "Total",
              defaultValue: 0, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { 
              field: "finalCharge",   label: "Final Charge",
              defaultValue: 0, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { field : "currencyUnit", label : "Currency Unit",
              select: {
                getOptions: function(field, bean) {
                  var options = service.LocaleService.getCurrencies().data ;
                  var result = [] ;
                  for(var i = 0; i < options.length; i++) {
                    var option = options[i] ;
                    result[i] = { label: option.name , value: option.code } ;
                  }
                  return result ;
                }
              },
            },
            { field : 'description', label : "Description", textarea : {} },
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
                  var item = thisUI.getBean('item');
                  if(thisUI.isNew) thisUI.UIParent.save(item) ;
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
  
    init: function(item, UIInvoiceItemList, isNew) {
      this.isNew = isNew ;
      this.UIParent = UIInvoiceItemList ;
      this.bind('item', item, true) ;
      this.getBeanState('item').editMode = true ; 
      return this ;
    },
    total:function(){
          return 0;
        }
  });
  
  var UIInvoiceItemList = UITable.extend({
    label: 'Invoice Item',
    
    config : {
      toolbar: {
        dflt: {
          actions: [
            {
              icon: "add", label: "New Item", 
              onClick: function(thisUI, row) { 
                var item = {
                  itemCode: "", itemName: ""
                };
                UIPopup.activate(new UIInvoiceItem().init(item, thisUI, true)) ;
              } 
            }
          ]
        }
      },
      bean: {
        fields: [
           { label : 'Item Code', field : 'itemCode', toggled : true, filterable : true}, 
           { label : 'Item Name', field : 'itemName', toggled : true, filterable : true }, 
           { label : 'Quantity', field : 'quantity', toggled : true, filterable : true },
           { label : 'UnitPrice', field : 'unitPrice', toggled : true, filterable : true },
           { label : 'Discount Rate', field : 'discountRate'},
           { label : 'Discount', field : 'discount', toggled : true, filterable : true },
           { label : 'Discount By Invoice', field : 'discountByInvoice', toggled : true, filterable : true },
           { label : 'Tax Rate', field : 'taxRate'},
           { label : 'Tax', field : 'tax', toggled : true, filterable : true },
           { label : 'Total', field : 'total', toggled : true, filterable : true },
           { label : 'Final Charge', field : 'finalCharge', toggled : true, filterable : true },
           { label : 'CurrencyUnit', field : 'currencyUnit', toggled : true, filterable : true },
           { label : 'Description', field : 'description'},
        ],
        actions: [
          {
            icon: "edit", label: "Edit Invoice Item",
            onClick: function (thisUI, row) {
              var item = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UIInvoiceItem().init(item, thisUI, false)) ;
              //thisUI.onEditBean(row) ;
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
    
    save: function (item) {
      this.listInvoiceItem[this.listInvoiceItem.length] = item ;
      
    },
    
    total:function(){
          return 0;
        },
    init: function(UIParent,items) {
      this.UIParent = UIParent;
      this.setBeans(items) ;
      this.listInvoiceItem = items;
      
      this.renderRows();
      return this ;
    },
    
    onRefresh: function() { 
      this.setBeans(this.listInvoiceItem) ;
      this.renderRows();
    },
    
  });
  
  var InvoiceItem = {
    UIInvoiceItemList: UIInvoiceItemList
  };
  
  return InvoiceItem ;
});
