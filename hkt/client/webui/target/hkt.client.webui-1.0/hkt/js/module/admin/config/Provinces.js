define([ 
  'service/service',
  'ui/UIBreadcumbs',
  'ui/UITable',
  'ui/UIPopup',
  'module/admin/config/Province',
], function(service, UIBreadcumbs, UITable, UIPopup, Province ) {
  
  var UIProvinceList = UITable.extend({
    label: "Provinces",
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewProvince", icon: "add", label: "New Province", 
                onClick: function(thisUI) {
                  var province = { name : "", description: "" } ;
                  UIPopup.activate(new Province.UIProvince().init(thisUI, province, true)) ;
                } 
              },
              {
                action: "onRefresh", icon: "refresh", label: "Refresh",
                onClick: function(thisUI) { 
                  thisUI.onRefresh() ;
                }
              }
            ],
          },
        },
      bean : {
        fields: [
          { label : 'Index', field : 'index', toggled : true, filterable : true, },
          { 
            label : 'Code', field : 'code', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var province = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new Province.UIProvince().init(thisUI, province, false)) ;
            }
          },
          { label : 'Name Province', field : 'name', toggled : true, filterable : true, },
          { label : 'Description', field : 'description', toggled : true, filterable : true, }
        ],
        actions: [
          {
            icon: "bars", label: "City",
            onClick: function(thisUI, row) {
              var province = thisUI.getItemOnCurrentPage(row) ;
              var UIProvinceDetail = thisUI.UIProvinceDetail.init(thisUI, province, true) ;
              thisUI.viewStack.push(UIProvinceDetail) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) {
              thisUI.removeItemOnCurrentPage(row) ;
              thisUI.provinces.splice(thisUI.provinces.length - 1, 1) ;
            }
          }
        ]
      }
    },
    
    saveProvince: function (province) {
      this.provinces[this.provinces.length] = province ;
    },
    
    onRefresh: function() {
      this.setBeans(this.provinces) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
    
    init: function (UIParent, provinces) {
      this.UIParent = UIParent ;
      this.viewStack = UIParent.viewStack ;
      this.UIProvinceDetail = new Province.UIProvinceDetail() ;
      
      this.provinces = provinces ;
      this.setBeans(provinces) ;
      return this ;
    }
    
  });
  
  var Provinces = {
    UIProvinceList : UIProvinceList
  }
  
  return Provinces ;
});
