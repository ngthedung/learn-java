define([
  'service/service',
  'ui/UICollapsible',
  'ui/UIBean',
  'module/accounting/InvoiceItem',
  'module/accounting/InvoiceTransaction',
  'module/accounting/Attribute',
  'module/accounting/Contributor',
  'util/DateTime',
  'i18n',
], function(service, UICollapsible, UIBean, InvoiceItem, InvoiceTransaction, Attribute, Contributor, DateTime, i18n) {
  
  var res = i18n.getResource('module/accounting/accounting');
  
  var UIInvoice = UIBean.extend({
    label: res('UIInvoice.label'),
    config: {
      beans: {
        invoice: {
          label: res('UIInvoice.label'),
          fields: [
            { 
              field: "type", label: res('UIInvoice.field.type'), validator: { name: "empty" } ,
              select: {
                getOptions: function(field, bean) {
                  var optionGroup = service.GenericOptionService.getOptionGroup(
                      'accounting', 'AccountingService', 'type_invoice').data ;
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
            { 
              field: "invoiceCode", label: res('UIInvoice.field.invoiceCode'), required: true,
              validator: { name: "empty" } 
            },
            {
              field : "activityType", label : res('UIInvoice.field.activityType'),
              defaultValue: res('UIInvoice.field.activityType.payment'),
              select: {
                getOptions: function(field, bean) {
                  var options = [
                    { label: res('UIInvoice.field.activityType.payment'), value: 'Payment' },
                    { label: res('UIInvoice.field.activityType.receipt'), value: 'Receipt' }
                  ];
                  return options ;
                }
              }
              
            },
            { 
              field: "total", label: res('UIInvoice.field.total'),
              defaultValue: 0,
              validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { 
              field: "discountRate", label: res('UIInvoice.field.discountRate'),
              defaultValue: 0, validator: { name: 'number', from: 0, to: 100, errorMsg: "Expect a number > 0" }
            },
            { 
              field: "discount", label: res('UIInvoice.field.discount'),
              defaultValue: 0, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { 
              field: "discountByItem", label: res('UIInvoice.field.discountByItem'),
              defaultValue: 0, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { 
              field: "totalTax", label: res('UIInvoice.field.totalTax'),
              defaultValue: 0, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { 
              field: "finalCharge", label: res('UIInvoice.field.finalCharge'),
              defaultValue: 0, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { 
              field: "totalPaid", label: res('UIInvoice.field.totalPaid'),
              defaultValue: 0, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { field : "currencyUnit", label : res('UIInvoice.field.currencyUnit'), validator: { name: "empty" },
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
            {
              field : "status", label : res('UIInvoice.field.status'),
              defaultValue: 'Paid',
              select: {
                getOptions: function(field, bean) {
                  var options = [
                    { label: res('UIInvoice.field.status.paid'), value: 'Paid' },
                    { label: res('UIInvoice.field.status.partiallyPaid'), value: 'PartiallyPaid' },
                    { label: res('UIInvoice.field.status.waitingPaid'), value: 'WaitingPaid' },
                  ];
                  return options ;
                }
              }
            },
            { field : "startDate", label : res('UIInvoice.field.startDate'),
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
              field : "endDate", label : res('UIInvoice.field.endDate'),
              datepicker: {
                getDate: function(bean) {
                  return DateTime.fromDateTimeToDDMMYYYY(bean.endDate) ;
                },
                setDate: function(date, bean) {
                  bean.endDate = DateTime.fromDDMMYYYYToDateTime(date) ;
                },
              }
            },
            { field : "note", label : res('UIInvoice.field.note'), type : "textarea" },
            { field : "description", label : res('UIInvoice.field.description'), textarea : {} },
          ],
        }
      }
    },
    
    init: function(invoice, isNew, readOnly) {
      this.bind('invoice', invoice, true) ;
      this.setReadOnly(readOnly);
      if (isNew) { this.getBeanState('invoice').editMode = true ; }
      return this ;
    }
    
  });
  
  var UIInvoiceDetail = UICollapsible.extend({
    label: res('UIInvoiceDetail.label'),
    config: {
      actions: [
        { 
          action: "back", label: res('UIInvoiceDetail.action.back'),
          onClick: function(thisUI) {
            if(thisUI.UIParent.back) thisUI.UIParent.back(false) ; 
          }
        },
        {
          action: "save", label: res('UIInvoiceDetail.action.save'),
          onClick: function(thisUI) {
            if(!thisUI.UIInvoice.validate()) {
              thisUI.render() ;
              return ;
            }
            var invoiceDetail = thisUI.invoiceDetail;
            if(invoiceDetail.activityType == "Thu")
            	{
            	thisUI.invoiceDetail.activityType = "Receipt";
            	}
            else
            	thisUI.invoiceDetail.activityType = "Payment";
            service.AccountingService.createInvoiceDetail(thisUI.invoiceDetail) ;
            if(thisUI.UIParent.back) thisUI.UIParent.back(true) ;
          }
        }
      ]
    },
    
    init: function(UIParent, invoiceDetail, isNew, readOnly) {
      this.clear() ;
      this.UIParent = UIParent ;
     // if(readOnly) this.setActionHidden('save', true) ;

      
      this.invoiceDetail = invoiceDetail ;
      this.UIInvoice = new UIInvoice().init(invoiceDetail, isNew, true);
      this.add(this.UIInvoice) ;
      
      this.Attribute = new Attribute.UIAttributeList().init(invoiceDetail.attributes);
      this.add(this.Attribute, true) ;
      
      this.Contributor = new Contributor.UIContributorList().init(UIParent.viewStack, invoiceDetail.contributors);
      this.add(this.Contributor, true) ; 
      
      this.InvoiceItem = new InvoiceItem.UIInvoiceItemList().init(this,  invoiceDetail.items)  ;
      this.add(this.InvoiceItem) ;
      
      this.InvoiceTransaction = new InvoiceTransaction.UIInvoiceTransactionList().init(invoiceDetail.transactions) ;
      this.add(this.InvoiceTransaction) ;
      
      return this ;
    }
  });
  
  var Invoice = {
    UIInvoiceDetail: UIInvoiceDetail
  };
  
  return Invoice ;
});
