package com.hkt.swingexp.app.report.entity;

import java.util.List;

import javax.swing.JLabel;

import com.hkt.client.swingexp.app.core.MyDouble;

public class ReportSalesEntity {
	public static final int					ORGANIZATION	= 0;
	public static final int					PRODUCT_TAG		= 1;
	public static final int					PRODUCT				= 2;

	private String									name;
	private String									productCode;
	private String									quantity;
	private String									totalPrice;
	private int											index;
	private int											type;
	private String 									path;
	private List<ReportSalesEntity>	listChild;
	private boolean									widen					= false;
	private JLabel									labelIcon;

	public ReportSalesEntity(String name, String productCode, String quantity, String totalPrice, int index) {
		this.name = name;
		this.productCode = productCode;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.index = index;
		
		labelIcon = new JLabel();
		labelIcon.setHorizontalTextPosition(JLabel.LEFT);
		labelIcon.setVerticalTextPosition(JLabel.CENTER);
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPriceAverage() {
		return new MyDouble(MyDouble.parseDouble(totalPrice)/MyDouble.parseDouble(quantity)).toString();
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ReportSalesEntity> getListChild() {
		return listChild;
	}

	public void setListChild(List<ReportSalesEntity> listChild) {
		this.listChild = listChild;
	}
	
	public boolean isWiden() {
		return widen;
	}

	public void setWiden(boolean widen) {
		this.widen = widen;
	}

	public JLabel getLabelIcon() {
		return labelIcon;
	}

	public void setLabelIcon(JLabel labelIcon) {
		this.labelIcon = labelIcon;
		this.labelIcon.setHorizontalTextPosition(JLabel.LEFT);
		this.labelIcon.setVerticalTextPosition(JLabel.CENTER);
	}

	@Override
	public String toString() {
		return name;
	}

}
