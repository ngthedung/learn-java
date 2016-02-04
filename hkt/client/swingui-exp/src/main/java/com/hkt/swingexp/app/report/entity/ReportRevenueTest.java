package com.hkt.swingexp.app.report.entity;

import java.util.List;
import javax.swing.JLabel;

public class ReportRevenueTest {
	
	public static enum Type {
		ROOT, PARENT, CHILD, INVOICE
	};
	private String							node;
	private String							path;
	private String							column1;
	private String							column2;
	private String							column3;
	private String							column4;
	private String							column5;
	private String							column6;
	private String							column7;
	
	private Type								type;
	private String							parent;
	private List<ReportRevenueTest>	listChild;
	private int									index;
	private boolean							widen	= false;
	private JLabel							labelIcon;
	
	public ReportRevenueTest(String node, String column1, String column2, String column3, int index) {
		this.node = node;
		this.column1 = column1;
		this.column2 = column2;
		this.column3 = column3;
		this.index = index;

		labelIcon = new JLabel();
		labelIcon.setHorizontalTextPosition(JLabel.LEFT);
		labelIcon.setVerticalTextPosition(JLabel.CENTER);
	}
	public ReportRevenueTest(String path, String node, String column1, String column2, String column3, int index) {
		this.node = node;
		this.column1 = column1;
		this.column2 = column2;
		this.column3 = column3;
		this.path = path;
		this.index = index;
		labelIcon = new JLabel();
		labelIcon.setHorizontalTextPosition(JLabel.LEFT);
		labelIcon.setVerticalTextPosition(JLabel.CENTER);
	}
	public ReportRevenueTest(String node, String column1, String column2, String column3, String column4, String column5,String column6, String column7, int index) {
		this.node = node;
		this.column1 = column1;
		this.column2 = column2;
		this.column3 = column3;
		this.column4 = column4;
		this.column5 = column5;
		this.column6 = column6;
		this.column7 = column7;
		this.index = index;

		labelIcon = new JLabel();
		labelIcon.setHorizontalTextPosition(JLabel.LEFT);
		labelIcon.setVerticalTextPosition(JLabel.CENTER);
	}
	
	public ReportRevenueTest() {
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getColumn1() {
		return column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public String getColumn2() {
		return column2;
	}

	public void setColumn2(String column2) {
		this.column2 = column2;
	}

	public String getColumn3() {
		return column3;
	}

	public void setColumn3(String column3) {
		this.column3 = column3;
	}
	
	public String getColumn4() {
		return column4;
	}

	public void setColumn4(String column4) {
		this.column4 = column4;
	}
	
	public String getColumn5() {
		return column5;
	}

	public void setColumn5(String column5) {
		this.column5 = column5;
	}
	
	public String getColumn6() {
		return column6;
	}

	public void setColumn6(String column6) {
		this.column6 = column6;
	}
	
	public String getColumn7() {
		return column7;
	}

	public void setColumn7(String column7) {
		this.column7 = column7;
	}

	public int getIndex() {
		return index;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public void setIndex(int index) {
		this.index = index;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public List<ReportRevenueTest> getListChild() {
		return listChild;
	}

	public void setListChild(List<ReportRevenueTest> listChild) {
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
		return node;
	}
}
