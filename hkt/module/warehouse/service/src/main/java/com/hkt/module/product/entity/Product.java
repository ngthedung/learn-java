package com.hkt.module.product.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.type.TypeReference;

import com.hkt.module.core.entity.AbstractPersistable;
import com.hkt.util.json.JSONSerializer;

@Entity
@Table(indexes = { @Index(columnList = "code") })
public class Product extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 1L;

	public enum Status {
		Active, InActive
	}

	public enum Type {
		Goods, Services
	}

	@NotNull
	private String name;
	@NotNull
	private String maker;
	private String image;
	@Transient
	private ProductImage proImage;
	private String unit;
	private String nameOther;
	private String description;
	private double price;
	private boolean updatePrice;
	private boolean recipe;
	@Column(name = "isCalculateReport", columnDefinition = "boolean default true", nullable = false)
	private boolean calculateReport = true;
	private Status status = Status.Active;
	private Type type = Type.Goods;
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REFRESH })
	@JoinTable(name = "Product_ProductTag", joinColumns = { @JoinColumn(name = "productId", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "productTagId", referencedColumnName = "id", nullable = false) })
	private List<ProductTag> productTags;

	@Embedded
	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "productId", nullable = false, updatable = false)
	@OrderColumn
	private List<ProductAttribute> productAttributes;

	public List<ProductAttribute> getProductAttributes() {
		return productAttributes;
	}

	public double getPrice() {
  	return price;
  }

	public void setPrice(double price) {
  	this.price = price;
  }

	public boolean isUpdatePrice() {
  	return updatePrice;
  }

	

	public boolean isRecipe() {
  	return recipe;
  }

	public void setRecipe(boolean recipe) {
  	this.recipe = recipe;
  }

	public void setUpdatePrice(boolean updatePrice) {
  	this.updatePrice = updatePrice;
  }



	public void setProductAttributes(List<ProductAttribute> productAttributes) {
		this.productAttributes = productAttributes;
	}

	public void add(ProductAttribute productTarget) {
		if (productAttributes == null) {
			productAttributes = new ArrayList<ProductAttribute>();
		}
		productAttributes.add(productTarget);
	}

	public void add(ProductTag productTag) {
		if (productTags == null) {
			productTags = new ArrayList<ProductTag>();
		}
		productTags.add(productTag);
	}

	public String getNameOther() {
		return nameOther;
	}

	public void setNameOther(String nameOther) {
		this.nameOther = nameOther;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCalculateReport() {
		return calculateReport;
	}

	public void setCalculateReport(boolean setCalculateReport) {
		this.calculateReport = setCalculateReport;
	}

	public String codeView() {
		if (getCode().indexOf(":") > 0) {
			return getCode().substring(getCode().indexOf(":") + 1);
		} else {
			return getCode();
		}
	}

	public Type getType() {
		return type;
	}

	public String getImage() {
		return image;
	}

	@JsonIgnore
	@Column(length = 64000)
	@Lob
	@Access(AccessType.PROPERTY)
	public String getProfilesData() throws IOException {
		if (proImage == null)
			return null;
		return JSONSerializer.INSTANCE.toString(proImage);
	}

	public void setProfilesData(String data) throws IOException {
		if (data == null || data.length() == 0)
			return;
		proImage = JSONSerializer.INSTANCE.fromString(data, new TypeReference<ProductImage>() {
		});
	}

	@Transient
	public ProductImage getProImage() {
		return proImage;
	}

	public void setProImage(ProductImage proImage) throws IOException {
		this.proImage = proImage;
		if (proImage == null)
			setProfilesData(null);
		else
			setProfilesData(JSONSerializer.INSTANCE.toString(proImage));
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public List<ProductTag> getProductTags() {
		return productTags;
	}

	public void setProductTags(List<ProductTag> productTags) {
		this.productTags = productTags;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return name;
	}

}
