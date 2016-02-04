package com.hkt.module.product;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.module.cms.entity.NodeAttribute;
import com.hkt.module.cms.entity.NodeAttributes;
import com.hkt.module.cms.entity.NodeDetail;
import com.hkt.module.core.search.IndexDocument;
import com.hkt.module.core.search.SearchPlugin;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductDetail;

@Component
public class ProductDetailSearchPlugin extends SearchPlugin<Product> {
  
  @Autowired
  ProductService          wService;
  
  public String getName() { return Product.class.getSimpleName() ; }
  
  public Class<Product> getType() { return Product.class ; } 
  
  public IndexDocument create(Product product) throws IOException {
    IndexDocument idoc = new IndexDocument() ;
    idoc.addTitle(getTitle(product)) ;
    idoc.addSummary("") ;
    idoc.addText(getText(product)) ;
    idoc.addSource(product) ;
    return idoc ;
  }
  
  private String getTitle(Product product) { 
    StringBuilder title = new StringBuilder() ;
    add(title, product.getCode()) ;
    add(title, product.getName()) ;
    return title.toString() ;
  }
  
  private String getText(Product product) {
    StringBuilder b = new StringBuilder() ;
    add(b, product.getName()) ;
    add(b, product.getCode()) ;
    add(b, product.getMaker()) ;
    ProductDetail productDetail = wService.getProductDetailByCode(product.getCode());
    NodeDetail nodeDetail = productDetail.getCmsNode();
    if(nodeDetail!=null){
      NodeAttributes nodeAttributes = nodeDetail.getAttributes();  
      for(Map.Entry<String, NodeAttribute> entry : nodeAttributes.entrySet()) {
        NodeAttribute nodeAttribute = entry.getValue();
        add(b, nodeAttribute.getName()) ;
        add(b, nodeAttribute.getType()) ;
        add(b, nodeAttribute.getValue()) ;
        add(b, nodeAttribute.getNote()) ;
      }
    }
    return b.toString() ;
  }
  
  private void add(StringBuilder b, Object obj) {
    if(obj == null) return ;
    if(obj instanceof String[]) {
      for(String val : (String[]) obj) {
        b.append(' ').append(val) ;
      }
    } else {
      b.append(' ').append(obj) ;
    }
  }
}