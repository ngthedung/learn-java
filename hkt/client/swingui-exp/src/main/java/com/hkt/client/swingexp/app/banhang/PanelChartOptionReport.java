package com.hkt.client.swingexp.app.banhang;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.jdesktop.swingx.WrapLayout;

import com.hkt.client.swingexp.app.banhang.ReportStatistics.TypeReport;
import com.hkt.client.swingexp.app.component.ExtendJCheckBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJRadioButton;
import com.hkt.client.swingexp.app.core.IDialogResto;

public class PanelChartOptionReport extends JPanel implements IDialogResto {
	private ExtendJRadioButton	rboTongThu_Pie, rboThucThu_Pie, rboTongChi_Pie, rboThucChi_Pie, rboPhaiThu_Pie, rboPhaiChi_Pie, rboLaiLo_Pie;
	private ExtendJRadioButton	rboTongThu_Line, rboThucThu_Line, rboTongChi_Line, rboThucChi_Line, rboPhaiThu_Line, rboPhaiChi_Line, rboLaiLo_Line;
	private ExtendJCheckBox			chbTongThu_Line, chbThucThu_Line, chbTongChi_Line, chbThucChi_Line, chbPhaiThu_Line, chbPhaiChi_Line, chbLaiLo_Line;;
	private ExtendJRadioButton	rboSoLuongPie, rboDonGiaPie, rboTongThuChiPie;
	private ExtendJRadioButton	rboSLDauKyPie, rboGTDauKyPie, rboSLTangPie, rboGTTangPie, rboSLGiamPie, rboGTGiamPie, rboSLCuoiKyPie, rboGTCuoiKyPie, rboSLHienTaiPie, rboGTHienTaiPie;
	private ExtendJCheckBox			chbHienThiPieChart, chbHienThiLineChart, chbChartXMonthByAllYears;
	private ButtonGroup					buttonGroupPie, buttonGroupLine;
	private boolean							isSave	= false;
	private String							type;
	private TypeReport					typeReport;
	
	public PanelChartOptionReport(TypeReport _typeReport, String type, boolean isViewPieChart, boolean isViewLineChart, String valuePie) {
		this.type = type;
		this.typeReport = _typeReport;
		// Add component main panel
		this.setLayout(new BorderLayout(0, 5));
		this.setOpaque(false);
		this.add(drawContainerPieChart(_typeReport), BorderLayout.NORTH);
		this.add(drawContainerLineChart(_typeReport), BorderLayout.CENTER);
		
		chbHienThiPieChart.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				setEnableRadioButtonPieChart(chbHienThiPieChart.isSelected());
				if (typeReport.equals(TypeReport.TKThuChi) || typeReport.equals(TypeReport.TKThoiGian)) {
					if (buttonGroupPie.getSelection() == null)
						rboLaiLo_Pie.setSelected(true);
				} else {
					if (typeReport.equals(TypeReport.TKBanHang) || typeReport.equals(TypeReport.TKMuaHang)) {
						if (buttonGroupPie.getSelection() == null)
							rboSoLuongPie.setSelected(true);
					} else {
						if (typeReport.equals(TypeReport.TKTonKho) || typeReport.equals(TypeReport.TKDinhLuong)) {
							if (buttonGroupPie.getSelection() == null)
								rboSLDauKyPie.setSelected(true);
						} else {
							// Các loại thống kê khác: etc ...
						}
					}
				}
			}
		});
		
		chbHienThiLineChart.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				setEnableRadioButtonLineChart(chbHienThiLineChart.isSelected());
				if (buttonGroupLine.getSelection() == null)
					rboLaiLo_Line.setSelected(true);
			}
		});
		
		chbHienThiLineChart.setSelected(isViewLineChart);
		chbHienThiPieChart.setSelected(isViewPieChart);
		
		for (Enumeration<AbstractButton> rbButton = buttonGroupPie.getElements(); rbButton.hasMoreElements();) {
			AbstractButton button = rbButton.nextElement();
			if (button.getActionCommand().equals(valuePie)) {
				button.setSelected(true);
			}
		}
	}
	
	private JPanel drawContainerLineChart(TypeReport typeReport) {
		// Create component Line Chart
		JPanel panelLineChart = new JPanel();
		panelLineChart.setLayout(new BoxLayout(panelLineChart, BoxLayout.PAGE_AXIS));
		panelLineChart.setOpaque(false);
		panelLineChart.setBorder(BorderFactory.createTitledBorder("Đồ thị đường"));
		
		chbHienThiLineChart = new ExtendJCheckBox("Hiển thị");
		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel1.setOpaque(false);
		panel1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		panel1.add(chbHienThiLineChart);
		
		if (typeReport.equals(TypeReport.TKThoiGian)) {
			chbTongThu_Line = new ExtendJCheckBox("Tổng thu");
			chbThucThu_Line = new ExtendJCheckBox("Thực thu");
			chbTongChi_Line = new ExtendJCheckBox("Tổng chi");
			chbThucChi_Line = new ExtendJCheckBox("Thực chi");
			chbPhaiThu_Line = new ExtendJCheckBox("Phải thu");
			chbPhaiChi_Line = new ExtendJCheckBox("Phải chi");
			chbLaiLo_Line = new ExtendJCheckBox("Lãi(lỗ)");
			JPanel panelGroupRadioButton = new JPanel();
			panelGroupRadioButton.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 10));
			panelGroupRadioButton.setLayout(new GridLayout(2, 4, 0, 0));
			panelGroupRadioButton.setOpaque(false);
			panelGroupRadioButton.add(chbTongThu_Line);
			panelGroupRadioButton.add(chbThucThu_Line);
			panelGroupRadioButton.add(chbTongChi_Line);
			panelGroupRadioButton.add(chbThucChi_Line);
			panelGroupRadioButton.add(chbPhaiThu_Line);
			panelGroupRadioButton.add(chbPhaiChi_Line);
			panelGroupRadioButton.add(chbLaiLo_Line);
			chbChartXMonthByAllYears = new ExtendJCheckBox("Biểu diễn số liệu so sánh giữa các năm");
			chbChartXMonthByAllYears.setVisible(false);
			JPanel panel2 = new JPanel(new WrapLayout(WrapLayout.LEFT));
			panel2.setOpaque(false);
			panel2.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			panel2.add(chbChartXMonthByAllYears);
			panel2.add(new ExtendJLabel("2010"));
			panel2.add(new ExtendJLabel("2011"));
			panel2.add(new ExtendJLabel("2012"));
			panel2.add(new ExtendJLabel("2013"));
			panelLineChart.add(panel1);
			panelLineChart.add(panelGroupRadioButton);
			panelLineChart.add(panel2);
			setEnableCheckBoxLineChart(false);
		} else {
			rboTongThu_Line = new ExtendJRadioButton("Tổng thu");
			rboThucThu_Line = new ExtendJRadioButton("Thực thu");
			rboTongChi_Line = new ExtendJRadioButton("Tổng chi");
			rboThucChi_Line = new ExtendJRadioButton("Thực chi");
			rboPhaiThu_Line = new ExtendJRadioButton("Phải thu");
			rboPhaiChi_Line = new ExtendJRadioButton("Phải chi");
			rboLaiLo_Line = new ExtendJRadioButton("Lãi(lỗ)");
			buttonGroupLine = new ButtonGroup();
			addOptionButtonGroup(buttonGroupLine, rboTongThu_Line);
			addOptionButtonGroup(buttonGroupLine, rboThucThu_Line);
			addOptionButtonGroup(buttonGroupLine, rboTongChi_Line);
			addOptionButtonGroup(buttonGroupLine, rboThucChi_Line);
			addOptionButtonGroup(buttonGroupLine, rboPhaiThu_Line);
			addOptionButtonGroup(buttonGroupLine, rboPhaiChi_Line);
			addOptionButtonGroup(buttonGroupLine, rboLaiLo_Line);
			JPanel panelGroupRadioButton = new JPanel();
			panelGroupRadioButton.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 10));
			panelGroupRadioButton.setLayout(new GridLayout(2, 4, 0, 0));
			panelGroupRadioButton.setOpaque(false);
			panelGroupRadioButton.add(rboTongThu_Line);
			panelGroupRadioButton.add(rboThucThu_Line);
			panelGroupRadioButton.add(rboTongChi_Line);
			panelGroupRadioButton.add(rboThucChi_Line);
			panelGroupRadioButton.add(rboPhaiThu_Line);
			panelGroupRadioButton.add(rboPhaiChi_Line);
			panelGroupRadioButton.add(rboLaiLo_Line);
			panelLineChart.add(panel1);
			panelLineChart.add(panelGroupRadioButton);
			setEnableRadioButtonLineChart(false);
		}
		chbHienThiLineChart.setSelected(false);
		
		return panelLineChart;
	}
	
	private JPanel drawContainerPieChart(TypeReport typeReport) {
		// Create component Pie Chart
		JPanel panelPieChart = new JPanel();
		panelPieChart.setLayout(new BoxLayout(panelPieChart, BoxLayout.PAGE_AXIS));
		panelPieChart.setOpaque(false);
		panelPieChart.setBorder(BorderFactory.createTitledBorder("Đồ thị tròn"));
		
		chbHienThiPieChart = new ExtendJCheckBox("Hiển thị");
		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel1.setOpaque(false);
		panel1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		panel1.add(chbHienThiPieChart);
		panelPieChart.add(panel1);
		
		if (typeReport.equals(TypeReport.TKThuChi) || typeReport.equals(TypeReport.TKThoiGian)) {
			rboTongThu_Pie = new ExtendJRadioButton("Tổng thu");
			rboThucThu_Pie = new ExtendJRadioButton("Thực thu");
			rboTongChi_Pie = new ExtendJRadioButton("Tổng chi");
			rboThucChi_Pie = new ExtendJRadioButton("Thực chi");
			rboPhaiThu_Pie = new ExtendJRadioButton("Phải thu");
			rboPhaiChi_Pie = new ExtendJRadioButton("Phải chi");
			rboLaiLo_Pie = new ExtendJRadioButton("Lãi(lỗ)");
			buttonGroupPie = new ButtonGroup();
			addOptionButtonGroup(buttonGroupPie, rboTongThu_Pie);
			addOptionButtonGroup(buttonGroupPie, rboThucThu_Pie);
			addOptionButtonGroup(buttonGroupPie, rboTongChi_Pie);
			addOptionButtonGroup(buttonGroupPie, rboThucChi_Pie);
			addOptionButtonGroup(buttonGroupPie, rboPhaiThu_Pie);
			addOptionButtonGroup(buttonGroupPie, rboPhaiChi_Pie);
			addOptionButtonGroup(buttonGroupPie, rboLaiLo_Pie);
			JPanel panelGroupRadioButton = new JPanel();
			panelGroupRadioButton.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 10));
			panelGroupRadioButton.setLayout(new GridLayout(2, 4, 0, 0));
			panelGroupRadioButton.setOpaque(false);
			panelGroupRadioButton.add(rboTongThu_Pie);
			panelGroupRadioButton.add(rboThucThu_Pie);
			panelGroupRadioButton.add(rboTongChi_Pie);
			panelGroupRadioButton.add(rboThucChi_Pie);
			panelGroupRadioButton.add(rboPhaiThu_Pie);
			panelGroupRadioButton.add(rboPhaiChi_Pie);
			panelGroupRadioButton.add(rboLaiLo_Pie);
			panelPieChart.add(panelGroupRadioButton);
		} else {
			if (typeReport.equals(TypeReport.TKBanHang) || typeReport.equals(TypeReport.TKMuaHang)) {
				rboSoLuongPie = new ExtendJRadioButton("Số lượng");
				rboDonGiaPie = new ExtendJRadioButton("Đơn giá TB");
				rboTongThuChiPie = new ExtendJRadioButton(typeReport.equals(TypeReport.TKBanHang) ? "Tổng thu" : "Tổng chi");
				buttonGroupPie = new ButtonGroup();
				addOptionButtonGroup(buttonGroupPie, rboSoLuongPie);
				addOptionButtonGroup(buttonGroupPie, rboDonGiaPie);
				addOptionButtonGroup(buttonGroupPie, rboTongThuChiPie);
				JPanel panelGroupRadioButton = new JPanel();
				panelGroupRadioButton.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 10));
				panelGroupRadioButton.setLayout(new GridLayout(1, 4, 0, 0));
				panelGroupRadioButton.setOpaque(false);
				panelGroupRadioButton.add(rboSoLuongPie);
				panelGroupRadioButton.add(rboDonGiaPie);
				panelGroupRadioButton.add(rboTongThuChiPie);
				panelGroupRadioButton.add(new JLabel());
				panelPieChart.add(panelGroupRadioButton);
			} else {
				if (typeReport.equals(TypeReport.TKDinhLuong) || typeReport.equals(TypeReport.TKTonKho)) {
					rboSLDauKyPie = new ExtendJRadioButton("SL đầu kỳ");
					rboGTDauKyPie = new ExtendJRadioButton("GT đầu kỳ");
					rboSLTangPie = new ExtendJRadioButton("SL tăng");
					rboGTTangPie = new ExtendJRadioButton("GT tăng");
					rboSLGiamPie = new ExtendJRadioButton("SL giảm");
					rboGTGiamPie = new ExtendJRadioButton("GT giảm");
					rboSLCuoiKyPie = new ExtendJRadioButton("SL cuối kỳ");
					rboGTCuoiKyPie = new ExtendJRadioButton("GT cuối kỳ");
					rboSLHienTaiPie = new ExtendJRadioButton("SL hiện tại");
					rboGTHienTaiPie = new ExtendJRadioButton("GT hiện tại");
					buttonGroupPie = new ButtonGroup();
					addOptionButtonGroup(buttonGroupPie, rboSLDauKyPie);
					addOptionButtonGroup(buttonGroupPie, rboGTDauKyPie);
					addOptionButtonGroup(buttonGroupPie, rboSLTangPie);
					addOptionButtonGroup(buttonGroupPie, rboGTTangPie);
					addOptionButtonGroup(buttonGroupPie, rboSLGiamPie);
					addOptionButtonGroup(buttonGroupPie, rboGTGiamPie);
					addOptionButtonGroup(buttonGroupPie, rboSLCuoiKyPie);
					addOptionButtonGroup(buttonGroupPie, rboGTCuoiKyPie);
					addOptionButtonGroup(buttonGroupPie, rboSLHienTaiPie);
					addOptionButtonGroup(buttonGroupPie, rboGTHienTaiPie);
					JPanel panelGroupRadioButton = new JPanel();
					panelGroupRadioButton.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 10));
					panelGroupRadioButton.setLayout(new GridLayout(3, 4, 0, 0));
					panelGroupRadioButton.setOpaque(false);
					panelGroupRadioButton.add(rboSLDauKyPie);
					panelGroupRadioButton.add(rboGTDauKyPie);
					panelGroupRadioButton.add(rboSLTangPie);
					panelGroupRadioButton.add(rboGTTangPie);
					panelGroupRadioButton.add(rboSLGiamPie);
					panelGroupRadioButton.add(rboGTGiamPie);
					panelGroupRadioButton.add(rboSLCuoiKyPie);
					panelGroupRadioButton.add(rboGTCuoiKyPie);
					panelGroupRadioButton.add(rboSLHienTaiPie);
					panelGroupRadioButton.add(rboGTHienTaiPie);
					panelPieChart.add(panelGroupRadioButton);
				} else {
					// TK dự án chưa có ???
				}
			}
		}
		
		chbHienThiPieChart.setSelected(false);
		setEnableRadioButtonPieChart(false);
		
		return panelPieChart;
	}
	
	private void addOptionButtonGroup(ButtonGroup buttonGroup, JRadioButton radioButton) {
		radioButton.setActionCommand(radioButton.getText());
		buttonGroup.add(radioButton);
	}
	
	public String getValueSelectionPieChart() {
		return buttonGroupPie.getSelection().getActionCommand().toString();
	}
	
	private void setEnableRadioButtonPieChart(boolean value) {
		if (typeReport.equals(TypeReport.TKThoiGian) || typeReport.equals(TypeReport.TKThuChi)) {
			rboTongThu_Pie.setEnabled(value);
			rboThucThu_Pie.setEnabled(value);
			rboThucChi_Pie.setEnabled(value);
			rboTongChi_Pie.setEnabled(value);
			rboPhaiThu_Pie.setEnabled(value);
			rboPhaiChi_Pie.setEnabled(value);
			rboLaiLo_Pie.setEnabled(value);
			
			if (type.equals("Chi phí")) {
				rboTongThu_Pie.setEnabled(false);
				rboThucThu_Pie.setEnabled(false);
				rboPhaiThu_Pie.setEnabled(false);
				rboPhaiChi_Pie.setEnabled(false);
				rboLaiLo_Pie.setText("Tỉ lệ(%)");
			} else {
				if (type.equals("Doanh thu")) {
					rboTongChi_Pie.setEnabled(false);
					rboThucChi_Pie.setEnabled(false);
					rboPhaiThu_Pie.setEnabled(false);
					rboPhaiChi_Pie.setEnabled(false);
					rboLaiLo_Pie.setText("Tỉ lệ(%)");
				} else {
					if (type.equals("Thu - Chi - Lãi")) {
						rboTongThu_Pie.setEnabled(false);
						rboTongChi_Pie.setEnabled(false);
						rboPhaiThu_Pie.setEnabled(false);
						rboPhaiChi_Pie.setEnabled(false);
					}
					rboLaiLo_Pie.setText("Lãi(lỗ)");
				}
			}
		} else {
			if (typeReport.equals(TypeReport.TKBanHang) || typeReport.equals(TypeReport.TKMuaHang)) {
				rboSoLuongPie.setEnabled(value);
				rboDonGiaPie.setEnabled(value);
				rboTongThuChiPie.setEnabled(value);
			} else {
				if (typeReport.equals(TypeReport.TKTonKho) || typeReport.equals(TypeReport.TKDinhLuong)) {
					rboSLDauKyPie.setEnabled(value);
					rboGTDauKyPie.setEnabled(value);
					rboSLTangPie.setEnabled(value);
					rboGTTangPie.setEnabled(value);
					rboSLGiamPie.setEnabled(value);
					rboGTGiamPie.setEnabled(value);
					rboSLCuoiKyPie.setEnabled(value);
					rboGTCuoiKyPie.setEnabled(value);
					rboSLHienTaiPie.setEnabled(value);
					rboGTHienTaiPie.setEnabled(value);
				} else {
					// Các loại thống kế khác: etc...
				}
			}
		}
	}
	
	private void setEnableRadioButtonLineChart(boolean value) {
		rboTongThu_Line.setEnabled(value);
		rboThucThu_Line.setEnabled(value);
		rboThucChi_Line.setEnabled(value);
		rboTongChi_Line.setEnabled(value);
		rboPhaiThu_Line.setEnabled(value);
		rboPhaiChi_Line.setEnabled(value);
		rboLaiLo_Line.setEnabled(value);
	}
	
	private void setEnableCheckBoxLineChart(boolean value) {
		chbTongThu_Line.setEnabled(value);
		chbThucThu_Line.setEnabled(value);
		chbThucChi_Line.setEnabled(value);
		chbTongChi_Line.setEnabled(value);
		chbPhaiThu_Line.setEnabled(value);
		chbPhaiChi_Line.setEnabled(value);
		chbLaiLo_Line.setEnabled(value);
	}
	
	public void setVisibleChbXMonthByAllYears(boolean value) {
		chbChartXMonthByAllYears.setVisible(value);
	}
	
	public boolean isViewPieChart() {
		return chbHienThiPieChart.isSelected();
	}
	
	public boolean isViewLineChart() {
		return chbHienThiLineChart.isSelected();
	}
	
	public boolean mouseClickSave() {
		return isSave;
	}
	
	@Override
	public void Save() throws Exception {
		isSave = true;
		((JDialog) (this.getRootPane().getParent())).dispose();
	}
	
}
