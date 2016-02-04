package com.hkt.swingexp.app.report.entity;

import java.awt.Color;
import java.util.List;

import javax.swing.JLabel;

import com.hkt.client.swingexp.app.core.MyDouble;

public class InventoryReportEntity {
	public static final int							ORGANIZATION		= 0;
	public static final int							PRODUCT_TAG			= 1;
	public static final int							PRODUCT					= 2;

	private String											col1_DanhMuc;
	private String											col2_SLDauKy		= "0";
	private String											col3_GTDauKy		= "0";
	private String											col4_SLTang			= "0";
	private String											col5_GTTang			= "0";
	private String											col6_SLGiam			= "0";
	private String											col7_GTGiam			= "0";
	private String											col8_SLCuoiKy		= "0";
	private String											col9_GTCuoiKy		= "0";
	private String											col10_SLHienTai	= "0";
	private String											col11_GTHienTai	= "0";
	private String											SLConLai				= "0";
	private String											GTConLai				= "0";
	private String											parent;
	private JLabel											labelIcon;
	private int													index;
	private int													type;
	private boolean											widen						= false;
	private List<InventoryReportEntity>	listChild;
	private Color												color;
	private String productCode;
	private boolean recipe;
	private double price;
	private String unit;
	

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isRecipe() {
  	return recipe;
  }

	public void setRecipe(boolean recipe) {
  	this.recipe = recipe;
  }

	public String getProductCode() {
  	return productCode;
  }

	public void setProductCode(String productCode) {
  	this.productCode = productCode;
  }

	public InventoryReportEntity(String col1_DanhMuc, String col2_SLDauKy, String col3_GTDauKy, String col4_SLTang, String col5_GTTang, String col6_SLGiam, String col7_GTGiam, String SLConLai, String GTConLai) {

		this.col1_DanhMuc = col1_DanhMuc;
		this.col2_SLDauKy = col2_SLDauKy;
		this.col3_GTDauKy = col3_GTDauKy;
		this.col4_SLTang = col4_SLTang;
		this.col5_GTTang = col5_GTTang;
		this.col6_SLGiam = col6_SLGiam;
		this.col7_GTGiam = col7_GTGiam;
		this.SLConLai = SLConLai;
		this.GTConLai = GTConLai;

		this.col8_SLCuoiKy = new MyDouble(MyDouble.parseDouble(col2_SLDauKy) + (MyDouble.parseDouble(col4_SLTang) - MyDouble.parseDouble(col6_SLGiam))).toString();
		this.col9_GTCuoiKy = new MyDouble(MyDouble.parseDouble(col3_GTDauKy) + (MyDouble.parseDouble(col5_GTTang) - MyDouble.parseDouble(col7_GTGiam))).toString();
		this.col10_SLHienTai = new MyDouble(MyDouble.parseDouble(col8_SLCuoiKy) + MyDouble.parseDouble(SLConLai)).toString();
		this.col11_GTHienTai = new MyDouble(MyDouble.parseDouble(col9_GTCuoiKy) + MyDouble.parseDouble(GTConLai)).toString();

		labelIcon = new JLabel();
		labelIcon.setHorizontalTextPosition(JLabel.LEFT);
		labelIcon.setVerticalTextPosition(JLabel.CENTER);
	}

	public String getCol1_DanhMuc() {
		return col1_DanhMuc;
	}

	public void setCol1_DanhMuc(String col1_DanhMuc) {
		this.col1_DanhMuc = col1_DanhMuc;
	}

	public String getCol2_SLDauKy() {
		return col2_SLDauKy;
	}

	public void setCol2_SLDauKy(String col2_SLDauKy) {
		this.col2_SLDauKy = col2_SLDauKy;
	}

	public String getCol3_GTDauKy() {
		return col3_GTDauKy;
	}

	public void setCol3_GTDauKy(String col3_GTDauKy) {
		this.col3_GTDauKy = col3_GTDauKy;
	}

	public String getCol4_SLTang() {
		return col4_SLTang;
	}

	public void setCol4_SLTang(String col4_SLTang) {
		this.col4_SLTang = col4_SLTang;
	}

	public String getCol5_GTTang() {
		return col5_GTTang;
	}

	public void setCol5_GTTang(String col5_GTTang) {
		this.col5_GTTang = col5_GTTang;
	}

	public String getCol6_SLGiam() {
		return col6_SLGiam;
	}

	public void setCol6_SLGiam(String col6_SLGiam) {
		this.col6_SLGiam = col6_SLGiam;
		this.col8_SLCuoiKy = new MyDouble(MyDouble.parseDouble(col2_SLDauKy) + (MyDouble.parseDouble(col4_SLTang) - MyDouble.parseDouble(col6_SLGiam))).toString();
	}

	public String getCol7_GTGiam() {
		return col7_GTGiam;
	}

	public void setCol7_GTGiam(String col7_GTGiam) {
		this.col7_GTGiam = col7_GTGiam;
		this.col9_GTCuoiKy = new MyDouble(MyDouble.parseDouble(col3_GTDauKy) + (MyDouble.parseDouble(col5_GTTang) - MyDouble.parseDouble(col7_GTGiam))).toString();
	}

	public String getCol8_SLCuoiKy() {
		return col8_SLCuoiKy;
	}

	public void setCol8_SLCuoiKy(String col8_SLCuoiKy) {
		this.col8_SLCuoiKy = col8_SLCuoiKy;
	}

	public String getCol9_GTCuoiKy() {
		return col9_GTCuoiKy;
	}

	public void setCol9_GTCuoiKy(String col9_GTCuoiKy) {
		this.col9_GTCuoiKy = col9_GTCuoiKy;
	}

	public String getCol10_SLHienTai() {
		return col10_SLHienTai;
	}

	public void setCol10_SLHienTai(String col10_SLHienTai) {
		this.col10_SLHienTai = col10_SLHienTai;
	}

	public String getCol11_GTHienTai() {
		return col11_GTHienTai;
	}

	public void setCol11_GTHienTai(String col11_GTHienTai) {
		this.col11_GTHienTai = col11_GTHienTai;
	}

	public String getSLConLai() {
		return SLConLai;
	}

	public void setSLConLai(String SLConLai) {
		this.SLConLai = SLConLai;
		this.col10_SLHienTai = new MyDouble(MyDouble.parseDouble(col8_SLCuoiKy) + MyDouble.parseDouble(SLConLai)).toString();
	}

	public String getGTConLai() {
		return GTConLai;
	}

	public void setGTConLai(String GTConLai) {
		this.GTConLai = GTConLai;
		this.col11_GTHienTai = new MyDouble(MyDouble.parseDouble(col9_GTCuoiKy) + MyDouble.parseDouble(GTConLai)).toString();
	}

	public boolean isNotRowZero() {
		if (MyDouble.parseDouble(col2_SLDauKy) != 0 || MyDouble.parseDouble(col3_GTDauKy) != 0 || MyDouble.parseDouble(col4_SLTang) != 0 || MyDouble.parseDouble(col5_GTTang) != 0 || MyDouble.parseDouble(col6_SLGiam) != 0 || MyDouble.parseDouble(col7_GTGiam) != 0
				|| MyDouble.parseDouble(col10_SLHienTai) != 0 || MyDouble.parseDouble(col11_GTHienTai) != 0)
			return true;
		else
			return false;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public List<InventoryReportEntity> getListChild() {
		return listChild;
	}

	public void setListChild(List<InventoryReportEntity> listChild) {
		this.listChild = listChild;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return col1_DanhMuc;
	}
}
