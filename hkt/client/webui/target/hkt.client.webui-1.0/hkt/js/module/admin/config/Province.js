define([ 
  'service/service',
  'ui/UICollapsible',
  'ui/UIBean',
  'ui/UIPopup',
  'module/admin/config/Cities'
], function( service, UICollapsible, UIBean, UIPopup, Cities ) {
  
  var UIProvince = UIBean.extend({
    label: "Province",
    config: {
      beans: {
        province: {
          label: 'province',
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
                onClick: function(thisUI) { 
                  var province = thisUI.getBean('province') ;
                  if(thisUI.isNew) thisUI.UIParent.saveProvince(province) ;
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
    
    init: function(UIParent, province, isNew) {
      this.UIParent = UIParent ;
      this.bind('province', province, true) ;
      this.isNew = isNew ;
      var config = this.getBeanConfig('province') ;
      
      config.disableField('index', !isNew) ;
      config.disableField('code', !isNew) ;
      
      config.disableEditAction(false) ;
      this.getBeanState('province').editMode = true ;
      
      return this ;
    },
    
    initWithDetail: function(province, readOnly) {
      this.bind('province', province) ;
      this.setReadOnly(readOnly);
      return this ;
    }
  });
  
  var UIProvinceDetail = UICollapsible.extend({
    label: "Province Information",
    config: {
      actions: [
        { 
          action: "back", label: "Back",
          onClick: function(thisUI) {
            if(thisUI.UIParent.back) thisUI.UIParent.back(false) ; 
          }
        },
        {
          action: "save", label: "Save",
          onClick: function(thisUI) {
            var province = thisUI.province;
            if(thisUI.UIParent.back) thisUI.UIParent.back(false) ;
          }
        }
      ]
    },
    
    init: function(UIParent, province, readOnly) {
      this.clear() ;
      
      this.UIParent = UIParent ;
      this.province = province ;
      
      if(province.cities == null) province.cities = [] ;
      
      this.add(new UIProvince().initWithDetail(province, readOnly), true) ;
      
      this.UICityList = new Cities.UICityList().init(province.cities) ;
      this.add(this.UICityList) ;
      return this ;
    },
    
  });
  
  var Province = {
    UIProvince : UIProvince,
    UIProvinceDetail : UIProvinceDetail
  };
  
  return Province ;
});
