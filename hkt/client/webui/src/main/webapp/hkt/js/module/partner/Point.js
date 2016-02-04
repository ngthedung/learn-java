define([
  'service/service',
  'ui/UICollapsible',
  'ui/UIBean',
  'module/partner/PointTransaction',
], function(service, UICollapsible, UIBean, PointTransaction) {  
  var UIPoint = UIBean.extend({
    label: "Point",
    config: {
      beans: {
        Point: {
          label: 'Point',
          fields: [
            { 
              field: "loginId",   label: "Login Id", required: true,
              validator: {name: "empty"} 
            },
            { 
              field: "point",   label: "Point", defaultValue: 0,
              validator: { 
                name: 'number', from: 0, errorMsg: "Expect a number > 0" 
              }
            }
          ]
        }
      }
    },
    
    initViewOnly: function (Point) {
      this.bind('Point', Point) ;
      this.setReadOnly(true) ;
      return this ;
    },
    
  });
  
  var UIPointDetail = UICollapsible.extend({
    label: "Point Detail",
    config: { },
    
    init: function(PointDetail) {
      this.clear() ;
      this.PointDetail = PointDetail ;
      this.UIPoint = new UIPoint().initViewOnly(PointDetail.point);
      this.add(this.UIPoint) ;
      
      this.PointTransaction = new PointTransaction.UIPointTransactionList().init(this, PointDetail) ;
      this.PointTransaction.init(this, PointDetail) ;
      this.add(this.PointTransaction) ;
      return this ;
    },
    
    onRefresh: function() {
      this.clear() ;
      var pointDetail = service.CustomerService.getPointDetailById(this.PointDetail.point.id).data;
      this.UIPoint = new UIPoint().initViewOnly(pointDetail.point);
      this.add(this.UIPoint) ;
      this.PointTransaction = new PointTransaction.UIPointTransactionList().init(this, pointDetail) ;
      this.add(this.PointTransaction) ;
      this.render() ;
    },
    
  });
  
  var Point = {
    UIPoint:       UIPoint,
    UIPointDetail: UIPointDetail
  };
    
  return Point ;
});
