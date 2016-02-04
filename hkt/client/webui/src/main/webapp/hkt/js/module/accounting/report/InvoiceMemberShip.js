/**
 * Edit by Bui Trong Hieu
 */

define([
    'service/service',
    'ui/UIBean',
    'ui/UICollapsible',
    'module/accounting/InvoiceMemberShip'
  ], function(service, UIBean, UICollapsible,InvoiceMemberShip){
	
	var UIStatisticalFilters = UIBean.extend({
		  label: 'Lọc thống kê',
		  config: {
			  beans: {
				  invoice: {
					  label: 'Lọc thống kê',
					  name: 'invoice',
					  field: [
				                {
				                    field : "activityType", label : "Loại",
				                    defaultValue: "phongban",
				                    select: {
				                        getOptions: function(field, bean) {
				                          var options = [
				                            { label: "Phòng ban", value: 'phongban' },
				                            { label: "Nhân viên", value: 'nhanvien' },
				                          ];
				                          return options ;
				                        }
				                      }
				                      
				                    },
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
					  ],
					  edit: {
						  disable: false,
						  actions: [
						    {
						    	action: 'save', label: "Đồng ý", icon: "check",
						    	onClick: function(thisUI){
						    		var invoice = thisUI.getBean('invoice');
						    		 this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
						    		 var filters = InvoiceMemberShip.UIInvoiceMemberShip().init(this.viewStack, invoice);
						    		 this.viewStack.push(filters);
						    		 UIpopup.closePopup();
						    	}
						    },
						    {
						    	action: 'cancel', label: "Thoát", icon: "back",
						    	onClick: function(){
						    		UIPopup.closePopup();
						    	}
						    }
						    ]
					  }
				  }
			  }
		  }
	  });
});