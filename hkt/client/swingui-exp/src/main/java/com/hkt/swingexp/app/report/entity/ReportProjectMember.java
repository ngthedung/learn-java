package com.hkt.swingexp.app.report.entity;

import java.util.List;

import javax.swing.JLabel;

public class ReportProjectMember {
	public static enum Type{
		ROOT, PARENT, CHILD, INVOICE
	};
	private String node;
	private String col1;
	private String col2;
	private String col3;
	private String col4;
	private String col5;
	private String xuly = "";
	private Type type;
	private String parent;
	private List<ReportProjectMember> listChild;
	private int index;
	private boolean widen = false;
	private boolean em = false;
	private JLabel labelIcon;
	
	
	public ReportProjectMember(String node, String col1, String col2, int index){
		this.node = node;
		this.col1 = col1;
		this.col2 = col2;
		this.index = index;
		labelIcon = new JLabel();
		labelIcon.setHorizontalTextPosition(JLabel.LEFT);
		labelIcon.setVerticalTextPosition(JLabel.CENTER);
	}
	
	public ReportProjectMember(String node, String col1, String col2, String col3, int index){
		this.node = node;
		this.col1 = col1;
		this.col2 = col2;
		this.col3 = col3;
		this.index = index;
		labelIcon = new JLabel();
		labelIcon.setHorizontalTextPosition(JLabel.LEFT);
		labelIcon.setVerticalTextPosition(JLabel.CENTER);
	}
	
	public ReportProjectMember(String node, String col1, String col2, String col3, String col4, int index ){
		this.node = node;
		this.col1 = col1;
		this.col2 = col2;
		this.col3 = col3;
		this.col4 = col4;
		this.index = index;
		labelIcon = new JLabel();
		labelIcon.setHorizontalTextPosition(JLabel.LEFT);
		labelIcon.setVerticalTextPosition(JLabel.CENTER);
	}
	public ReportProjectMember(){}
	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	public String getCol2() {
		return col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2;
	}

	public String getCol3() {
		return col3;
	}

	public void setCol3(String col3) {
		this.col3 = col3;
	}

	public String getCol4() {
		return col4;
	}

	public void setCol4(String col4) {
		this.col4 = col4;
	}

	public String getCol5() {
		return col5;
	}

	public void setCol5(String col5) {
		this.col5 = col5;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public List<ReportProjectMember> getListChild() {
		return listChild;
	}

	public void setListChild(List<ReportProjectMember> listChild) {
		this.listChild = listChild;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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
	public String toString(){
		return node;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	public String getXuly() {
		return xuly;
	}

	public void setXuly(String xuly) {
		this.xuly = xuly;
	}

	public boolean isEm() {
		return em;
	}

	public void setEm(boolean em) {
		this.em = em;
	}
	
	
}
