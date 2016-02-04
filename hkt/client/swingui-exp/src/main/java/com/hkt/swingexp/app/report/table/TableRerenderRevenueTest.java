package com.hkt.swingexp.app.report.table;

import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.jfree.data.general.DefaultPieDataset;

import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.hethong.Processing;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.swingexp.app.report.entity.PanelTableCell;
import com.hkt.swingexp.app.report.entity.ReportRevenueTest;
import com.hkt.swingexp.app.report.entity.ReportRevenueTest.Type;
import com.hkt.swingexp.app.report.modeltable.TestModelTableReportRevenue;

public class TableRerenderRevenueTest extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{
	private PanelTableCell cell;
	private HashMap<String, String> conds;
	private String viewMoney;
	private String typeReport;
	private TestModelTableReportRevenue model;
	private Processing processing;
	private String dateEnd = null;
	private String dateStart = null;
	private String pathParent = "";
	private ImageIcon nodeIconAdd, nodeIconMulti, nodeIconPerson, nodeIconProduct, nodeIconInvoice;
//	private static ClientContext clientContext = ClientContext.getInstance();
//	private static AccountingServiceClient accountingServiceClient = clientContext.getBean(AccountingServiceClient.class);
	private boolean isCheck;
	private List<ReportRevenueTest> revenues;
	private HashMap<String, ReportRevenueTest> rowH;
	private boolean a = false;
	public TableRerenderRevenueTest(TestModelTableReportRevenue model, String typeReport, HashMap<String, String> hash,
		    Date dateStart, Date dateEnd, String viewMoney, boolean isCheck, HashMap<String, ReportRevenueTest> rowH) {
		nodeIconAdd = new ImageIcon(HomeScreen.class.getResource("icon/square_add_16.png"));
		nodeIconMulti = new ImageIcon(HomeScreen.class.getResource("icon/square_multi_16.png"));
		nodeIconPerson = new ImageIcon(HomeScreen.class.getResource("icon/user_16.png"));
		nodeIconProduct = new ImageIcon(HomeScreen.class.getResource("icon/square_product_16.png"));
		nodeIconInvoice = new ImageIcon(HomeScreen.class.getResource("icon/document_edit_16.png"));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendarStart = Calendar.getInstance();
		if (dateStart != null) {
			calendarStart.setTime(dateStart);
			calendarStart.set(Calendar.HOUR_OF_DAY, 00);
			calendarStart.set(Calendar.MINUTE, 00);
			calendarStart.set(Calendar.SECOND, 00);
		}
		Calendar calendarEnd = Calendar.getInstance();
		if (dateEnd != null) {
			calendarEnd.setTime(dateEnd);
			calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
			calendarEnd.set(Calendar.MINUTE, 59);
			calendarEnd.set(Calendar.SECOND, 59);
		}
		this.dateStart = dateFormat.format(calendarStart.getTime());
		this.dateEnd = dateFormat.format(calendarEnd.getTime());

		this.model = model;
		this.conds = hash;
		this.viewMoney = viewMoney;
		this.typeReport = typeReport;
		this.isCheck = isCheck;
		this.rowH = rowH;
		this.cell = new PanelTableCell();
		this.processing = new Processing();
	}
	
	public void mouseClick_CellTable(){
		Object parent = cell.getObject();
		if(parent instanceof ReportRevenueTest){
			// ---------------START RUN TIME QUERY----------------
			long startTime = System.currentTimeMillis();
			// ---------------------------------------------------
			int index = 0;
			if(cell.getRow() < model.getRowCount()-1){
				index = ((ReportRevenueTest) model.getValueAt(cell.getRow()+ 1, 0)).getIndex();
			}
			String path = ((ReportRevenueTest) parent).getPath();
			if(((ReportRevenueTest) parent).getType().equals(Type.ROOT)){
				try {
					ReportRevenueTest revenue = (ReportRevenueTest) parent;
					if(!a){
						int i = cell.getRow() +1;
						if(revenue.getListChild() == null){
							List<ReportRevenueTest> reportRevenues = new ArrayList<ReportRevenueTest>();
							reportRevenues = getParentGroup(revenue.getPath(), i);
							revenue.setListChild(reportRevenues);
						}
						model.addRowChild(revenue.getListChild(), i);
						revenue.getLabelIcon().setIcon(nodeIconMulti);
						revenue.setWiden(true);
					}
					else{
						model.removeRowReport(cell.getRow());
						revenue.getLabelIcon().setIcon(nodeIconAdd);
						revenue.setWiden(false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(((ReportRevenueTest) parent).getType().equals(Type.PARENT)){
				System.out.println("Type = Parent?");
				try {
					ReportRevenueTest dept = (ReportRevenueTest) parent;
					if(index <= dept.getIndex()){
						int i = cell.getRow() + 1;
						System.out.println(dept.getListChild());
						if(dept.getListChild() == null){
							
							List<ReportRevenueTest> reportRevenues = new ArrayList<ReportRevenueTest>();
							reportRevenues.addAll(getParentGroup(dept.getPath(), i));
							dept.setListChild(reportRevenues);
						}
						model.addRowChild(dept.getListChild(), i);
						dept.getLabelIcon().setIcon(nodeIconMulti);
						dept.setWiden(true);
					}
					else
					{
						model.removeRowReport(cell.getRow());
						dept.getLabelIcon().setIcon(nodeIconAdd);
						dept.setWiden(false);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			// ---------------RETURN TIME EXECUTE QUERY------------------
			long endTime = System.currentTimeMillis();
			NumberFormat formatter = new DecimalFormat("#0.00000");
			String time = formatter.format((endTime - startTime) / 1000d);
			System.out.println("###$$$ TIME RUN QUERY: " + time);
			// ----------------------------------------------------------
		}
	}
	
	private List<ReportRevenueTest> getParentGroup(String parent, int index) throws Exception {
		Iterator<ReportRevenueTest> in = rowH.values().iterator();
		System.out.println(rowH);
		List<ReportRevenueTest> rows = new ArrayList<ReportRevenueTest>();
		HashMap<String, ReportRevenueTest> row_final = new HashMap<String, ReportRevenueTest>();
		List<AccountGroup> accountGroups = AccountModelManager.getInstance().findByParentPath(parent);
		System.out.println(accountGroups);
		if(parent.equals("hkt"))
		{
		AccountGroup ag = new AccountGroup();
		double total = 0;
		double total0 = 0;
		double ratio = 0;
		for(int i = 0; i< accountGroups.size(); i++)
		{
			if(accountGroups.get(i).getLabel().equals("Nhóm phòng ban") && conds.containsKey("department"))
			{
				ag = accountGroups.get(i);
			}
		}
		ReportRevenueTest row;
		while(in.hasNext()){
			ReportRevenueTest rt = in.next();

			if(rt.getPath().startsWith(ag.getPath()) == true){
				total = total + MyDouble.parseDouble(rt.getColumn1());
				total0 = total0 + MyDouble.parseDouble(rt.getColumn2());
			}
		}
		ratio = (total0/total)* 100;
		row = new ReportRevenueTest(ag.getPath(), ag.getLabel(), Double.toString(total), Double.toString(total0), Double.toString(ratio), index+1);
		row_final.put(ag.getPath(), row);
		row.setType(Type.PARENT);
		row.setPath(ag.getPath());
		rows.add(row);
		}
		else{
			for(AccountGroup ag: accountGroups){
				double total = 0;
				double total0 = 0;
				double ratio = 0;
				ReportRevenueTest row;
				while(in.hasNext()){
					ReportRevenueTest rt = in.next();
//					if(rt.getPath().equals(ag.getPath()) == true)
//					{
//						System.out.println("có trung path hay khong?");
//						total = MyDouble.parseDouble(rt.getColumn1());
//						total0 = MyDouble.parseDouble(rt.getColumn2());
//					}
//					else
//					{
					if(rt.getPath().startsWith(ag.getPath()) == true){
						total = total + MyDouble.parseDouble(rt.getColumn1());
						total0 = total0 + MyDouble.parseDouble(rt.getColumn2());
//					}
					}
					ratio = (total0/total)* 100;
					row = new ReportRevenueTest(ag.getPath(), rt.getNode(), Double.toString(total), Double.toString(total0), Double.toString(ratio), index+1);
					System.out.println(row.getPath() + "***"+ row.getColumn1());
					row_final.put(ag.getPath(), row);
					row.setType(Type.PARENT);
					row.setPath(ag.getPath());
					rows.add(row);
				}
			
			}
		}
		
		long startTime1 = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		NumberFormat formatter = new DecimalFormat("#0.00000");
		String time = formatter.format((endTime - startTime1) / 1000d);
		System.out.println("TOng thoi gian chay : " + time);
		Collections.sort(rows, new Comparator<ReportRevenueTest>() {

			@Override
			public int compare(ReportRevenueTest o1, ReportRevenueTest o2) {
				return o1.getNode().compareTo(o2.getNode());
			}
			
		});
		return rows;
	}

	@Override
	public Object getCellEditorValue() {
		return cell.getObject();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		cell.setRow(row);
		cell.setObject(value);
		if (column == 0) {
			JLabel labelIcon = new JLabel(" ");
			cell.setIcon(labelIcon);
			String result = " ";
			((ReportRevenueTest) value).setLabelIcon(labelIcon);
			try {
				int index = ((ReportRevenueTest) value).getIndex();
				if (((ReportRevenueTest) value).isWiden())
					labelIcon.setIcon(nodeIconMulti);
				else
					labelIcon.setIcon(nodeIconAdd);

				for (int l = 0; l < index; l++) {
					result = result + "      ";
				}
				
				if (((ReportRevenueTest) value).getType().equals(Type.CHILD)) {
					if (conds.containsKey("product1"))
						labelIcon.setIcon(nodeIconProduct);
					else {
						if (conds.containsKey("location1")) {
							// Set Icon cho bàn nếu muốn if(...)
						} else {
							labelIcon.setIcon(nodeIconPerson); // Set Icon cho đối tác hoặc
																								 // nhân viên
						}
					}
				}
				if (((ReportRevenueTest) value).getType().equals(Type.INVOICE))
					labelIcon.setIcon(nodeIconInvoice);

				labelIcon.setText(result);
				cell.getText().setText(value.toString());

			} catch (Exception e) {
				e.printStackTrace();
				cell.getText().setText("");
			}
		} else {
			cell.getText().setHorizontalAlignment(JLabel.RIGHT);
			cell.getText().setText(value.toString());
		}

		if (table.getSelectedRow() == row) {
			cell.getText().setFont(new Font(cell.getFont().getFontName(), Font.BOLD, 13));
		} else {
			cell.getText().setFont(new Font(cell.getFont().getFontName(), Font.PLAIN, 14));
		}

		return cell;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		JLabel labelIcon = ((ReportRevenueTest) value).getLabelIcon();
		cell.getText().setFont(new Font(cell.getFont().getFontName(), Font.BOLD, 13));
		cell.setRow(row);
		final Object c = value;
		if(column == 0){
			try {
				cell.setObject(value);
				cell.getText().setText(value.toString());
				processing.setVisible(true);
				new SwingWorker<Void, Void>() {

					@Override
					protected Void doInBackground() throws Exception {
						mouseClick_CellTable();
						return null;
					};

					@Override
					protected void done() {
						processing.setVisible(false);
						List<ReportRevenueTest> list = ((ReportRevenueTest) c).getListChild();
						DefaultPieDataset dataset = new DefaultPieDataset();
						if (typeReport.equals("Doanh thu") || typeReport.equals("Chi phí")) {
							String type = model.getReportStatistics().getColumnViewPie().toString();
							if (type.equals("Tổng thu") || type.equals("Tổng chi")) {
								for (ReportRevenueTest r : list) {
									Double d = (new MyDouble(r.getColumn1()).doubleValue()
									    / new MyDouble(((ReportRevenueTest) c).getColumn1()).doubleValue() + new MyDouble(r.getColumn1())
									    .doubleValue() % new MyDouble(((ReportRevenueTest) c).getColumn1()).doubleValue()) * 100;
									dataset.setValue(r.getNode(), d);
								}
							} else {
								if (type.equals("Thực thu") || type.equals("Thực chi")) {
									for (ReportRevenueTest r : list) {
										Double d = (new MyDouble(r.getColumn2()).doubleValue()
										    / new MyDouble(((ReportRevenueTest) c).getColumn2()).doubleValue() + new MyDouble(r.getColumn2())
										    .doubleValue() % new MyDouble(((ReportRevenueTest) c).getColumn2()).doubleValue()) * 100;
										dataset.setValue(r.getNode(), d);
									}
								} else {
									if (type.equals("Tỉ lệ(%)")) {
										for (ReportRevenueTest r : list) {
											Double d = (new MyDouble(r.getColumn3()).doubleValue()
											    / new MyDouble(((ReportRevenueTest) c).getColumn3()).doubleValue() + new MyDouble(r.getColumn3())
											    .doubleValue() % new MyDouble(((ReportRevenueTest) c).getColumn3()).doubleValue()) * 100;
											dataset.setValue(r.getNode(), d);
										}
									}
								}
							}
						}
						if (typeReport.equals("Thu - Chi - Lãi")) {
							String type = model.getReportStatistics().getColumnViewPie().toString();
							if (type.equals("Thực thu")) {
								for (ReportRevenueTest r : list) {
									Double d = (new MyDouble(r.getColumn1()).doubleValue()
									    / new MyDouble(((ReportRevenueTest) c).getColumn1()).doubleValue() + new MyDouble(r.getColumn1())
									    .doubleValue() % new MyDouble(((ReportRevenueTest) c).getColumn1()).doubleValue()) * 100;
									dataset.setValue(r.getNode(), d);
								}
							} else {
								if (type.equals("Thực chi")) {
									for (ReportRevenueTest r : list) {
										Double d = (new MyDouble(r.getColumn2()).doubleValue()
										    / new MyDouble(((ReportRevenueTest) c).getColumn2()).doubleValue() + new MyDouble(r.getColumn2())
										    .doubleValue() % new MyDouble(((ReportRevenueTest) c).getColumn2()).doubleValue()) * 100;
										dataset.setValue(r.getNode(), d);
									}
								} else {
									if (type.equals("Lãi(lỗ)")) {
										for (ReportRevenueTest r : list) {
											Double d = (new MyDouble(r.getColumn3()).doubleValue()
											    / new MyDouble(((ReportRevenueTest) c).getColumn3()).doubleValue() + new MyDouble(r.getColumn3())
											    .doubleValue() % new MyDouble(((ReportRevenueTest) c).getColumn3()).doubleValue()) * 100;
											dataset.setValue(r.getNode(), d);
										}
									}
								}
							}
						}
						if (typeReport.equals("Thu - Chi - Công nợ")) {
							String type = model.getReportStatistics().getColumnViewPie().toString();
							if (type.equals("Tổng thu")) {
								for (ReportRevenueTest r : list) {
									Double d = (new MyDouble(r.getColumn1()).doubleValue()
									    / new MyDouble(((ReportRevenueTest) c).getColumn1()).doubleValue() + new MyDouble(r.getColumn1())
									    .doubleValue() % new MyDouble(((ReportRevenueTest) c).getColumn1()).doubleValue()) * 100;
									dataset.setValue(r.getNode(), d);
								}
							} else {
								if (type.equals("Tổng chi")) {
									for (ReportRevenueTest r : list) {
										Double d = (new MyDouble(r.getColumn2()).doubleValue()
										    / new MyDouble(((ReportRevenueTest) c).getColumn2()).doubleValue() + new MyDouble(r.getColumn2())
										    .doubleValue() % new MyDouble(((ReportRevenueTest) c).getColumn2()).doubleValue()) * 100;
										dataset.setValue(r.getNode(), d);
									}
								} else {
									if (type.equals("Thực thu")) {
										for (ReportRevenueTest r : list) {
											Double d = (new MyDouble(r.getColumn3()).doubleValue()
											    / new MyDouble(((ReportRevenueTest) c).getColumn3()).doubleValue() + new MyDouble(r.getColumn3())
											    .doubleValue() % new MyDouble(((ReportRevenueTest) c).getColumn3()).doubleValue()) * 100;
											dataset.setValue(r.getNode(), d);
										}
									} else {
										if (type.equals("Thực chi")) {
											for (ReportRevenueTest r : list) {
												Double d = (new MyDouble(r.getColumn4()).doubleValue()
												    / new MyDouble(((ReportRevenueTest) c).getColumn4()).doubleValue() + new MyDouble(
												    r.getColumn4()).doubleValue()
												    % new MyDouble(((ReportRevenueTest) c).getColumn4()).doubleValue()) * 100;
												dataset.setValue(r.getNode(), d);
											}
										} else {
											if (type.equals("Phải thu")) {
												for (ReportRevenueTest r : list) {
													Double d = (new MyDouble(r.getColumn5()).doubleValue()
													    / new MyDouble(((ReportRevenueTest) c).getColumn5()).doubleValue() + new MyDouble(
													    r.getColumn5()).doubleValue()
													    % new MyDouble(((ReportRevenueTest) c).getColumn5()).doubleValue()) * 100;
													dataset.setValue(r.getNode(), d);
												}
											} else {
												if (type.equals("Phải chi")) {
													for (ReportRevenueTest r : list) {
														Double d = (new MyDouble(r.getColumn6()).doubleValue()
														    / new MyDouble(((ReportRevenueTest) c).getColumn6()).doubleValue() + new MyDouble(
														    r.getColumn6()).doubleValue()
														    % new MyDouble(((ReportRevenueTest) c).getColumn6()).doubleValue()) * 100;
														dataset.setValue(r.getNode(), d);
													}
												} else {
													if (type.equals("Lãi(lỗ)")) {
														for (ReportRevenueTest r : list) {
															Double d = (new MyDouble(r.getColumn7()).doubleValue()
															    / new MyDouble(((ReportRevenueTest) c).getColumn7()).doubleValue() + new MyDouble(
															    r.getColumn7()).doubleValue()
															    % new MyDouble(((ReportRevenueTest) c).getColumn7()).doubleValue()) * 100;
															dataset.setValue(r.getNode(), d);
														}
													}
												}
											}
										}
									}
								}
							}
						}

						model.getReportStatistics().viewChart(dataset);
					};
					
				}.execute();
				
				} catch (Exception e) {
					e.printStackTrace();
					cell.getText().setText("");
			}
		}
		else{
			cell.getText().setHorizontalAlignment(JLabel.RIGHT);
			cell.getText().setText(value.toString());
		}
		cell.setIcon(labelIcon);
		return cell;
	}
	

}
