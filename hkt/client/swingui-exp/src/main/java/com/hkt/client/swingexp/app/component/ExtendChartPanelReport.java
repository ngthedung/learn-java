package com.hkt.client.swingexp.app.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.text.NumberFormat;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RectangleEdge;

public class ExtendChartPanelReport {
	private DefaultPieDataset	defaultPieDataset;
	private JFreeChart				freeChart;
	private ChartPanel        chartPanel;
	
	public ExtendChartPanelReport() {
		super();
	}
	
	public JPanel createChartPanel(String titleChart){
		freeChart = createPieChart(titleChart, defaultPieDataset);
		chartPanel = new ChartPanel(freeChart);
		return chartPanel;
	}
	
	public JFreeChart createPieChart(String titleChart, PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart(titleChart, dataset, true, true, false);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 10));
		plot.setNoDataMessage("No data available");
		plot.setCircular(true);
		
		plot.setOutlinePaint(null);
		plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 8));
		plot.setLabelGap(0.02);
		// Style Line Label Generator Focus Sector
		plot.setLabelLinkStyle(PieLabelLinkStyle.QUAD_CURVE);
		// Title Chart 
		TextTitle title = chart.getTitle();
		title.setFont(new Font("SansSerif", Font.BOLD, 12));
		title.setPaint(Color.GRAY);
		// Label Generator
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()));
		// Tool Tip Generator
		plot.setToolTipGenerator(new StandardPieToolTipGenerator("{0} = {2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()));
		// Legend Title
		LegendTitle legendTitle = chart.getLegend();
		legendTitle.setPosition(RectangleEdge.LEFT);
		
		plot.setOutlineStroke( new BasicStroke(10.5f));		
		plot.setLegendItemShape(new Rectangle(3,3));
		
		plot.setLabelOutlinePaint(null);
		plot.setLabelShadowPaint(null);
		plot.setLabelBackgroundPaint(Color.WHITE);
		plot.setBackgroundPaint(Color.WHITE);
		
		return chart;
	}
	
	public void setTitleChart(String title){
		freeChart.setTitle(title);
		
	}

	public DefaultPieDataset getDefaultPieDataset() {
		return defaultPieDataset;
	}

	public void setDefaultPieDataset(DefaultPieDataset defaultPieDataset) {
		this.defaultPieDataset = defaultPieDataset;
		if(freeChart != null){
			PiePlot plot = (PiePlot) freeChart.getPlot();
      plot.setDataset(defaultPieDataset);
		}
	}

	public JFreeChart getFreeChart() {
		return freeChart;
	}

	public void setFreeChart(JFreeChart freeChart) {
		this.freeChart = freeChart;
	}

	public ChartPanel getChartPanel() {
		return chartPanel;
	}
}
