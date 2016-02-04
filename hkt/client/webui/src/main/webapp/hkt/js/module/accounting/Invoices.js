define([
  'jquery', 
  'underscore', 
  'backbone', 
  'service/service',
  'module/Module',
  'ui/UITable',
  'ui/UIBreadcumbs',
  'module/accounting/Invoice',
  'util/DateTime',
  'i18n',
  'ui/UIBean',
  'ui/UIPopup',
], function($, _, Backbone, service, module, UITable,UIBreadcumbs , Invoice, DateTime,i18n,UIBean,UIPopup) {
	
  var res = i18n.getResource('module/accounting/accounting');
	
  var UIInvoiceList = UITable.extend({
    label:  res('UIInvoiceList.label'),
    config : {
      toolbar: {
        dflt: {
          actions: [
            { 
              action: "onNewInvoice", icon: "add", label: "Thêm mới",
              onClick: function(thisUI) { 
                var iDetail = {
                 type: "", currencyUnit: "", description: "", note: "", startDate: DateTime.getCurrentDate(),
                 items: [],
                  transactions: [],
                  attributes: [],
                  contributors: []
                } ;
                var uiDetail = new Invoice.UIInvoiceDetail().init(thisUI, iDetail, true, false) ;
                thisUI.viewStack.push(uiDetail) ;
              } 
            },
            { 
              action: "onRefresh", icon: "refresh", label: "Xem lại", 
              onClick: function(thisUI) { 
                thisUI.onRefresh();
              } 
            }
          ]
        },
        filter: {
          fields: [
            { label: 'Search By Type',  field: 'type', operator: 'LIKE' },
            { label: 'Search By Invoice Code',  field: 'invoiceCode', operator: 'EQUAL' },
            { label: 'Search By Status', field: 'status',  type: 'string',  operator: 'EQUAL' }
          ],
          onFilter: function(thisTable, query) {
            var result = service.AccountingService.searchInvoice(query).data ;
            var invoices = result.data ;
            thisTable.setBeans(invoices) ;
            thisTable.renderRows() ;
          }
        },
        search: {
          onSearch: function(thisUI, query) {
            var searchResult = service.AccountingService.searchInvoice(query).data ;
           
            var invoices = searchResult.data ;
            thisUI.setBeans(invoices) ;
            thisUI.renderRows() ;
          }
        }
        
      },
      bean: {
        fields: [
          { label: res('UIInvoiceList.field.invoiceCode'), field: 'invoiceCode', toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var invoice = thisUI.getItemOnCurrentPage(row) ;
              var iDetail = service.AccountingService.getInvoiceDetail(invoice.id).data;
              var uiDetail = new Invoice.UIInvoiceDetail().init(thisUI, iDetail, false, false) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
          { label: res('UIInvoiceList.field.invoiceName'), field: 'invoiceName', toggled: true, filterable: true },
          { label: res('UIInvoiceList.field.activityType'), field: 'activityType', toggled: true, filterable: true },
          { label: res('UIInvoiceList.field.employeeCode'), field: 'employeeCode', toggled: true },
          { label: "Ngày TH", field: 'startDate', toggled: true },
          { label: res('UIInvoiceList.field.total'), field: 'total', toggled: true },
          { label: res('UIInvoiceList.field.discount'), field: 'discount', toggled: true },
          { label: res('UIInvoiceList.field.totalTax'), field: 'totalTax', toggled: true },
          { label: res('UIInvoiceList.field.finalCharge'), field: 'finalCharge', toggled: true },
          { label: res('UIInvoiceList.field.currencyUnit'), field: 'currencyUnit', toggled: true, filterable: true }
        ]
      }
    },
    
    init: function (viewStack, invoice) {
      this.viewStack = viewStack ;
      this.startDate = invoice.startDate;
      this.endDate = invoice.endDate;
      var d = DateTime.parseDate(this.startDate);
  	  d.setDate(d.getDate()-1);
  	  d.setHours(23);
  	  d.setMinutes(59);
  	  var d1 = DateTime.parseDate(this.endDate);
  	  d1.setHours(23);
  	  d1.setMinutes(59);
  	  this.query = { fields: [{ field: 'startDate', operator: 'GT', value: DateTime.formatDateSql(d)},
 	                          { field: 'startDate', operator: 'LT', value: DateTime.formatDateSql(d1)}]  } ;
  	  if(invoice.activityType == "Thu")
  	  {
  	    this.type = "Receipt";
  	    this.query = { fields: [{ field: 'startDate', operator: 'GT', value: DateTime.formatDateSql(d)},
 	                            { field: 'startDate', operator: 'LT', value: DateTime.formatDateSql(d1)},
 	                            { field: 'activityType', operator: 'STRINGEQ', value: this.type}]  } ;
  	  }
  	  if(invoice.activityType == "Chi"){
  	    this.type = "Payment";
  	    this.query = { fields: [{ field: 'startDate', operator: 'GT', value: DateTime.formatDateSql(d)},
	                           { field: 'startDate', operator: 'LT', value: DateTime.formatDateSql(d1)},
	                           { field: 'activityType', operator: 'STRINGEQ', value: this.type}]  } ;
  	  }

      var result = service.AccountingService.searchInvoice(this.query).data ;
      var invoices = result.data ;
      this.to = 0;
      for(var i = 0; i<invoices.length; i++)
	  {   
      this.to = this.to + invoices[i].finalCharge;
	  var activityType = invoices[i].activityType;
	  invoices[i].startDate = DateTime.fromDateTimeToDDMMYYYY(invoices[i].startDate);
	  if(activityType == "Receipt")
		  invoices[i].activityType = "Thu";
	  else
	  	  invoices[i].activityType = "Chi";
	  }
      this.setBeans(invoices) ;
      return this;
    },
    
    total:function(){
      return this.to;
    },
    
    
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
   
    onRefresh: function() { 
      var result = service.AccountingService.searchInvoice(this.query).data ;
      var invoices = result.data ;
      for(var i = 0; i<invoices.length; i++)
	  {   
	  var activityType = invoices[i].activityType;
	  if(activityType == "Receipt")
		  invoices[i].activityType = "Thu";
	  else
	  	  invoices[i].activityType = "Chi";
	  }
      this.setBeans(invoices) ;
      this.renderRows() ;
    }, 
  });
  
  var UIDate = UIBean.extend({
	    label: 'Lọc',
	    config: {
	      beans: {
	        invoice: {
	          label: 'Lọc',
	          name: 'invoice',
	          fields: [  
                { field : "startDate", label : "Từ ngày",
                  datepicker: {
                    getDate: function(bean) {
                      return DateTime.fromDateTimeToDDMMYYYY(bean.startDate) ;
                    },
                    setDate: function(date, bean) {
                      bean.startDate = DateTime.fromDDMMYYYYToDateTime(date) ;
                    },
                  }
                },
                { 
                  field : "endDate", label : "Đến ngày",
                  datepicker: {
                    getDate: function(bean) {
                      return DateTime.fromDateTimeToDDMMYYYY(bean.endDate) ;
                    },
                    setDate: function(date, bean) {
                      bean.endDate = DateTime.fromDDMMYYYYToDateTime(date) ;
                    },
                  }
                },
                {
                  field : "activityType", label : "Loại",
                  defaultValue: "Thu",
                  select: {
                      getOptions: function(field, bean) {
                        var options = [
                          { label: "Thu", value: 'Thu' },
                          { label: "Chi", value: 'Chi' },
                          { label: "Tất cả", value: 'All' }
                        ];
                        return options ;
                      }
                    }
                    
                  },
	          ],
	          edit: {
	              disable: false , 
	              actions: [
	                {
	                  action:'save', label: "Đồng ý", icon: "check",
	                  onClick: function(thisUI) { 
	                	  
	                	  var invoice = thisUI.getBean('invoice') ;
	                	  this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
	                	  thisUI.InvoiceList.init(this.viewStack, invoice);
	                      this.viewStack.push(thisUI.InvoiceList) ;
	                	  UIPopup.closePopup() ;
	                  }
	                },
	                {
	                  action:'cancel', label: "Thoát", icon: "back",
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
	      this.InvoiceList = new UIInvoiceList();
	      this.bind('invoice', attribute, true) ;
	      if(isNew){ this.getBeanState('invoice').editMode = true ; }
	      return this ;
	    }
	  });
  
  var UIInvoicesScreen = module.UIScreen.extend({
    initialize: function (options) { },
    
    activate: function() { 
    	var invoice = { startDate : DateTime.fromDateTimeToDDMMYYYY(DateTime.getCurrentDate()), 
    			endDate : DateTime.fromDateTimeToDDMMYYYY(DateTime.getCurrentDate()) };
        UIPopup.activate(new UIDate().init(this, invoice, true)) ;
     
    },
    
    deactivate: function() { },
  });
  return UIInvoicesScreen ;
});
