package com.hkt.module.product.entity;

import com.hkt.module.cms.entity.NodeDetail;

public class ProductDetail {
  private Product product;
  private NodeDetail cmsNode;

  public Product getProduct() { return product; }

  public void setProduct(Product product) { this.product = product; }

  public NodeDetail getCmsNode() { return cmsNode; }

  public void setCmsNode(NodeDetail cmsNode) { this.cmsNode = cmsNode; }

}
