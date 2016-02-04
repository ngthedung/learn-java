define([ 
  'service/service',
  'ui/UICollapsible',
  'ui/UIBean',
  'module/admin/config/Provinces',
  'module/admin/config/Cities',
], function( service, UICollapsible, UIBean, Provinces, Cities ) {
  
  var UICountry = UIBean.extend({
    label: "Country",
    config: {
      beans: {
        country: {
          label: 'country',
          fields: [
            { field: "index", label: "Index" },
            { field: "code", label: "Code" },
            { field: "name", label: "Name" },
            { field: "flag", label: "Flag" },
            { field: "description", label: "Description", textarea: {} }
          ],
          edit: {
            disable: true , 
            actions: [
            ],
          }
        }
      }
    },
    
    init: function(country, isNew, readOnly) {
      this.bind('country', country, true) ;
      this.setReadOnly(readOnly);
      
      var config = this.getBeanConfig('country') ;
      config.disableEditAction(false) ;
      config.disableField('index', !isNew) ;
      config.disableField('code', !isNew) ;
      if (isNew) { this.getBeanState('country').editMode = true ; }
      
      return this ;
    }
    
  });
  
  var UICountryDetail = UICollapsible.extend({
    label: "Country Information",
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
            var country = thisUI.country;
            service.LocaleService.saveCountry(country) ;
            if(thisUI.UIParent.back) thisUI.UIParent.back(true) ;
          }
        }
      ]
    },
    
    init: function(UIParent, country, isNew) {
      this.clear() ;
      
      this.UIParent = UIParent ;
      this.country = country ;
      
      if(country.provinces == null) country.provinces = [] ;
      if(country.cities == null) country.cities = [] ;
      
      this.add(new UICountry().init(country, isNew, false)) ;
      
      this.UICityList = new Cities.UICityList().init(country.cities) ;
      this.add(this.UICityList) ;
      
      this.UIProvinceList = new Provinces.UIProvinceList().init(UIParent, country.provinces) ;
      this.add(this.UIProvinceList) ;
      
      return this ;
    }
    
  });
  
  var Country = {
    UICountry : UICountry,
    UICountryDetail : UICountryDetail
  };
  
  return Country ;
});
