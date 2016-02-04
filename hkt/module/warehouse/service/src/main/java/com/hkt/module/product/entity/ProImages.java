package com.hkt.module.product.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProImages {
	private Map<String, ProductImage[]> holders = new HashMap<String, ProductImage[]>() ;
	
	public ProductImage[] allProductImages() {
	    List<ProductImage> holder = new ArrayList<ProductImage>() ;
	    for(ProductImage[] parray : holders.values()) {
	      for(ProductImage productImage : parray) 
	        holder.add(productImage) ;
	    }
	    return holder.toArray(new ProductImage[holder.size()]) ;
	  }
	  
	  private ProductImage getSingle(String name) {
		  ProductImage[]  productImage = holders.get(name) ; 
	    if(productImage == null) return null ;
	    return productImage[0] ;
	  }
	  
	  public void setSingle(String name, ProductImage productImage) { 
	    holders.put(name, new ProductImage[] { productImage }) ; 
	  }
}
