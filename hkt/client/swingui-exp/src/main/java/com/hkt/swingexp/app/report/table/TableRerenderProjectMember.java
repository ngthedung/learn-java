package com.hkt.swingexp.app.report.table;

import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.AccountingServiceClient;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.hethong.Processing;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.hr.entity.Employee;
import com.hkt.swingexp.app.report.entity.PanelTableCell;
import com.hkt.swingexp.app.report.entity.ReportProjectMember;
import com.hkt.swingexp.app.report.entity.ReportProjectMember.Type;
import com.hkt.swingexp.app.report.modeltable.ModelTableReportProjectMember;
/* 
 * @author Bui Trong Hieu
 * */
public class TableRerenderProjectMember extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{
	
	private PanelTableCell cell;
	private HashMap<String, String> conds;
	private String viewMoney;
	private String typeReport;
	private ModelTableReportProjectMember model;
	private Processing processing;
	private String dateEnd = null;;
	private String dateStart = null;;
	private String pathParent = "";
	private ImageIcon nodeIconAdd, nodeIconMulti, nodeIconPerson, nodeIconInvoice;
	private String[] trangthai = {"Không xử lý", "Trả thưởng"};
	private static ClientContext clientContext = ClientContext.getInstance();
	private static AccountingServiceClient accountingServiceClient = clientContext.getBean(AccountingServiceClient.class);
	private JComponent component;
	private boolean isCheck;
	public TableRerenderProjectMember(ModelTableReportProjectMember model, String typeReport, HashMap<String, String> conds,Date dateStart, Date dateEnd,String viewMoney, boolean isCheck) {
		nodeIconAdd = new ImageIcon(HomeScreen.class.getResource("icon/square_add_16.png"));
		nodeIconMulti = new ImageIcon(HomeScreen.class.getResource("icon/square_multi_16.png"));
		nodeIconPerson = new ImageIcon(HomeScreen.class.getResource("icon/user_16.png"));
		nodeIconInvoice = new ImageIcon(HomeScreen.class.getResource("icon/document_edit_16.png"));
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendarStart = Calendar.getInstance();
		if(dateStart !=null){
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
		  	this.conds = conds;
		    this.viewMoney = viewMoney;
		    this.typeReport = typeReport;
		    this.isCheck = isCheck;
		    cell = new PanelTableCell();
				this.processing = new Processing();
			
	}
	@Override
	public Object getCellEditorValue() {
		if(component == cell)
			return cell.getObject();
		else
		{
			return ((JComboBox) component).getSelectedItem();
		}
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus,final int row,final int column) {
		
		cell.setRow(row);
		cell.setObject(value);
		if(column == 0)
		{
			
			JLabel labelIcon = new JLabel(" ");
			cell.setIcon(labelIcon);
	  		String result = " ";
				((ReportProjectMember) value).setLabelIcon(labelIcon);
	      try {
	          int index = ((ReportProjectMember) value).getIndex();

	          if(((ReportProjectMember) value).isWiden())
	        	  labelIcon.setIcon(nodeIconMulti);
	          else
	  					labelIcon.setIcon(nodeIconAdd);
	  	    	for(int l = 0; l < index; l++){
	  	  		result = result + "      ";
	  	    	}
	  	    	if (((ReportProjectMember) value).getType().equals(Type.CHILD))
							labelIcon.setIcon(nodeIconPerson); // Set Icon cho đối tác hoặc nhân viên
	  			if (((ReportProjectMember) value).getType().equals(Type.INVOICE))
	  				labelIcon.setIcon(nodeIconInvoice);
	  			String  parent = null;
	  				parent = ((ReportProjectMember) value).getParent();
	  			if(((ReportProjectMember) value).getType().equals(Type.PARENT) && conds.containsKey("employee") && ((ReportProjectMember) value).isEm() == true)
	  			{
	  					labelIcon.setIcon(nodeIconPerson);


	  			}
	  			  labelIcon.setText(result);
	  			  cell.getText().setText(value.toString());
	  			  
	        } catch (Exception e) {
	          e.printStackTrace();
	          cell.getText().setText("");
	        }
		}
		else if(column == model.getColumnCount()-1)
		{
			 try {
				 JComboBox<String> comboBox = new JComboBox<String>(new DefaultComboBoxModel<String>(trangthai));				 
					if(typeReport.equals("Thưởng") && (((ReportProjectMember) table.getValueAt(row, 0)).getLabelIcon()).getIcon().toString().equals(nodeIconPerson.toString()))
						comboBox.setEnabled(true);
					else
						comboBox.setEnabled(false);
					for(int i = 0; i< comboBox.getItemCount(); i++)
					{
						if(comboBox.getItemAt(i).toString().equals(value.toString())){
							comboBox.setSelectedIndex(i);
							
						}
					}
					return comboBox;
			  } catch (Exception e) {
			  }
		}
		else
		{
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
		cell.setRow(row);
	    cell.setObject(value);
	    component = cell;

	    if (column == 0) {
	    	JLabel labelIcon = ((ReportProjectMember) value).getLabelIcon();
	      try {
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
						};
					}.execute();
	      	
					cell.setIcon(labelIcon);
	      } catch (Exception e) {
	        e.printStackTrace();
	        cell.getText().setText("");
	      }
	    } 
	    else if(column == model.getColumnCount()-1)
	    {
		    try {
		    	
		    		JComboBox<String> comboBox = new JComboBox<String>(new DefaultComboBoxModel<String>(trangthai));
						if(typeReport.equals("Thưởng") &&(((ReportProjectMember) table.getValueAt(row, 0)).getLabelIcon()).getIcon().toString().equals(nodeIconPerson.toString()))
							comboBox.setEnabled(true);
						else
							comboBox.setEnabled(false);
						component = comboBox;
						for(int i = 0; i< comboBox.getItemCount(); i++)
						{
							if(comboBox.getItemAt(i).toString().equals(value.toString())){
								comboBox.setSelectedIndex(i);
								
							}
						}
						return comboBox;

			} catch (Exception e) {
			}
	    }
	    else {
	    	cell.getText().setHorizontalAlignment(JLabel.RIGHT);
	    }
	    cell.getText().setText(value.toString());
	  	cell.getText().setFont(new Font(cell.getFont().getFontName(), Font.BOLD, 13));
		return null;
	}

	protected void mouseClick_CellTable() {
		long startTime = System.currentTimeMillis();
		Object parent = cell.getObject();
		int indexRowAfter = 0;
		if(cell.getRow() < model.getRowCount()-1)
			indexRowAfter = ((ReportProjectMember) model.getValueAt(cell.getRow()+1, 0)).getIndex();
		if(((ReportProjectMember) parent).getType().equals(Type.ROOT)){
			try {
				ReportProjectMember rowCurrent = (ReportProjectMember) parent;
				if(indexRowAfter <= rowCurrent.getIndex()){
					int i = cell.getRow() +1;
					if(rowCurrent.getListChild() == null){
						List<ReportProjectMember> list = new ArrayList<ReportProjectMember>();
						list = getParentByPath(null);
						rowCurrent.setListChild(list);
					}
					model.addRowChild(rowCurrent.getListChild(), i);
					rowCurrent.getLabelIcon().setIcon(nodeIconMulti);
					rowCurrent.setWiden(true);
				}
				else{
					model.removeRowReport(cell.getRow());
					rowCurrent.getLabelIcon().setIcon(nodeIconAdd);
					rowCurrent.setWiden(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(((ReportProjectMember) parent).getType().equals(Type.PARENT)){
			try {
				ReportProjectMember rowCurrent = (ReportProjectMember) parent;
				if(indexRowAfter <= rowCurrent.getIndex()){
					int i = cell.getRow() +1;
					if(rowCurrent.getListChild() == null){
						List<ReportProjectMember> list = new ArrayList<ReportProjectMember>();
							if(conds.containsKey("employee")){
								list = getParentByPath(rowCurrent);
							} else {
								if(conds.containsKey("project")){
									list = getParentByPath(rowCurrent);
									list.addAll(getChildByPath(rowCurrent));
								}
							}
						rowCurrent.setListChild(list);
					}
					model.addRowChild(rowCurrent.getListChild(), i);
					rowCurrent.getLabelIcon().setIcon(nodeIconMulti);
					rowCurrent.setWiden(true);

				} else{
					model.removeRowReport(cell.getRow());
					rowCurrent.getLabelIcon().setIcon(nodeIconAdd);
					rowCurrent.setWiden(false);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 //---------------RETURN TIME EXECUTE QUERY------------------
		long endTime = System.currentTimeMillis();
		NumberFormat formatter = new DecimalFormat("#0.00000");
		String time = formatter.format((endTime - startTime)/1000d);
		System.out.println("###$$$ TIME RUN QUERY: "+ time);
	}

	  private List<ReportProjectMember> getChildByPath(
			ReportProjectMember rpm) throws Exception{
		  List<ReportProjectMember> nodes = new ArrayList<ReportProjectMember>();
		  String parent = null;
		  int index = 1;
		  if(rpm !=null){
			  parent = rpm.getParent();
			  index = index + rpm.getIndex();
		  }
		  String sql;
		  if(conds.containsKey("project") && isCheck == false)
			  sql = "i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' AND ";
		  else
			  sql = "";
			  SQLSelectQuery rQuery = new SQLSelectQuery();
			  if(typeReport.equals("Doanh thu"))
				  rQuery = query_Child_DoanhThu_ChiPhi(rpm, parent, index, ActivityType.Receipt, sql);
			  if(typeReport.equals("Chi phí"))
				  rQuery = query_Child_DoanhThu_ChiPhi(rpm, parent, index, ActivityType.Payment, sql);
			  if(typeReport.equals("Doanh thu thuần"))
				  rQuery = query_Child_ThuChiLai(rpm, parent, index, false, sql);
			  if(typeReport.equals("Thưởng"))
				  rQuery = query_Child_ThuChiLai(rpm, parent, index, true, sql);
			  System.out.println(rQuery.createSQLQuery());
			  ReportTable[] reportTable = accountingServiceClient.reportQuery(new SQLSelectQuery[] { rQuery });
				reportTable[0].dump(new String[] { "node", "final", "total", "parent", "percent" });
				String[] field = new String[] { "node", "final", "total", "parent", "percent" };
				List<Map<String, Object>> records = reportTable[0].getRecords();
				for(int i = 0; i<records.size();i++)
				{
					Map<String, Object> record = records.get(i);
					Object[] values = new Object[field.length];
					for(int j = 0; j< field.length;j++){
							values[j] = record.get(field[j]);
					}
					double sumFinalCharge;
					double sumTotalTransaction;
					double ratio;
					double doanhthuthuan = 0;
					if (viewMoney.equals("Nghìn")) {
						sumFinalCharge = Double.parseDouble(values[1].toString()) / 1000;
						sumTotalTransaction = Double.parseDouble(values[2].toString()) / 1000;
					} else if (viewMoney.equals("Triệu")) {
						sumFinalCharge = Double.parseDouble(values[1].toString()) / 1000000;
						sumTotalTransaction = Double.parseDouble(values[2].toString()) / 1000000;
					} else {
						sumFinalCharge = Double.parseDouble(values[1].toString());
						sumTotalTransaction = Double.parseDouble(values[2].toString());
					}
					doanhthuthuan = sumFinalCharge- sumTotalTransaction;
//					if(typeReport.equals("Thưởng"))
//						ratio = (doanhthuthuan)*(Double.valueOf(values[4].toString())/100);
//					else
//						ratio = Double.valueOf(values[4].toString());
//						if(ratio<=0)
//							ratio = 0;
//						else
//							ratio = (double)Math.round(ratio*1000)/1000;
					ReportProjectMember n;
					if (typeReport.equals("Doanh thu thuần"))
						 n = new ReportProjectMember(values[0].toString(), MyDouble.valueOf(sumFinalCharge).toString(), MyDouble.valueOf(sumTotalTransaction).toString(),MyDouble.valueOf(doanhthuthuan).toString(), MyDouble.valueOf(values[4].toString()).toString(), index);
					else if(typeReport.equals("Thưởng"))
					{
						ratio = (doanhthuthuan)*(Double.valueOf(values[4].toString())/100);
						n = new ReportProjectMember(values[0].toString(), MyDouble.valueOf(doanhthuthuan).toString(), MyDouble.valueOf(ratio).toString(),MyDouble.valueOf(values[4].toString()).toString(), index);
					}
					else
						n = new ReportProjectMember(values[0].toString(), MyDouble.valueOf(sumFinalCharge).toString(), MyDouble.valueOf(sumTotalTransaction).toString(),MyDouble.valueOf(values[4].toString()).toString(), index);
					n.setType(Type.CHILD);
					if(parent !=null){
						if(parent.indexOf("/") > 0){
							n.setParent(parent + "/");
						} else if(parent.indexOf(":") > 0){
							n.setParent(parent + ":");
						} else{
							if(values[3] !=null){
								n.setParent(values[3].toString());
							}
							else
								n.setParent("");
						}
					} else
						n.setParent(pathParent);
					nodes.add(n);
				}
		return nodes;
	}
	private SQLSelectQuery query_Child_ThuChiLai(ReportProjectMember rpm,
			String parent, int index, boolean b, String sql) {
		SQLSelectQuery rQuery = new SQLSelectQuery();
		if(b == false){
		/**TAB dự án*/
		if(conds.containsKey("project")){
			rQuery.table("((SELECT " +
				     "a.eCode AS `eCode1`, " +
				     "b.final AS `final`, " +
				     "b.eCode AS `eCode2`, " +
				     "a.total AS `total` ,  " +
				     "a.percent AS `percent1` , " +
				     "b.percent AS `percent2` " +
	       "FROM " +
	       		 "(SELECT " +
					"SUM(i.total*i.currencyRate*(rpm.percent/100)) AS `total` , rpm.employeeCode as `eCode`, rpm.percent as `percent` " +
				   "FROM " +
				   "InvoiceTransaction i " +
				   "INNER JOIN " +
				   "restaurant_project rp " +
				   "ON " +
				   "SUBSTRING_INDEX(i.projectCode,'/', -1) = rp.code "+
				   "INNER JOIN " +
				   "restaurant_projectmember rpm " +
				   "ON " +
				   "rp.id = rpm.projectId " +
				   "WHERE " +
				   sql +
				    "i.ActivityType = 'Payment' AND " 
				   + "i.transactionDate >= '" + dateStart + "' AND " 
				   + "i.transactionDate <= '" + dateEnd + "' and "
				  + "i.projectCode = '"+ parent +"' group by `eCode`  ) AS `a` " + 
	       		 "RIGHT JOIN " + 
	       		 "(SELECT " +
				   "SUM(i.total*i.currencyRate*(rpm.percent/100)) AS `final` , rpm.employeeCode as `eCode`, rpm.percent as `percent`" +
				   "FROM " +
				   "InvoiceTransaction i " +
				   "INNER JOIN " +
				   "restaurant_project rp " +
				   "ON " +
				   "SUBSTRING_INDEX(i.projectCode,'/', -1) = rp.code "+
				   "INNER JOIN " +
				   "restaurant_projectmember rpm " +
				   "ON " +
				   "rp.id = rpm.projectId " +
				   "WHERE " +
				  sql +
				   "i.ActivityType = 'Receipt' AND " 
				   +"i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "' and "
				 + "i.projectCode = '"+ parent +"' group by `eCode` ) AS `b` ON a.eCode = b.eCode)) AS `e` ORDER BY `node`" )
				.field("(CASE WHEN (e.eCode1 IS NULL) THEN e.eCode2 ELSE e.eCode1 END) AS `eCode3`")
				.field("(SELECT e.name FROM employee e WHERE e.code = `eCode3` ) AS `node`", "node")
				.field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
				.field("COALESCE(e.total, 0) AS `totalPayment`", "total")
				.field("(CASE WHEN (e.percent1 IS NULL) THEN e.percent2 ELSE e.percent1 END) AS `percent`", "percent");
		}
		return rQuery;
		}
		else{
			if(conds.containsKey("project")){
				rQuery.table("((SELECT " +
					     "a.eCode AS `eCode1`, " +
					     "b.final AS `final`, " +
					     "b.eCode AS `eCode2`, " +
					     "a.total AS `total` ,  " +
					     "a.percent AS `percent1` , " +
					     "b.percent AS `percent2` " +
		       "FROM " +
		       		 "(SELECT " +
						"SUM(i.total*i.currencyRate) AS `total` , rpm.employeeCode as `eCode`, rpm.directAward as `percent` " +
					   "FROM " +
					   "InvoiceTransaction i " +
					   "INNER JOIN " +
					   "restaurant_project rp " +
					   "ON " +
					   "SUBSTRING_INDEX(i.projectCode,'/', -1) = rp.code "+
					   "INNER JOIN " +
					   "restaurant_projectmember rpm " +
					   "ON " +
					   "rp.id = rpm.projectId " +
					   "WHERE " +
					 sql +
					   "i.ActivityType = 'Payment' AND " + 
					   "i.transactionDate >= '" + dateStart + "' AND " + 
					   "i.transactionDate <= '" + dateEnd + "' and "
					  + "i.projectCode = '"+ parent +"' group by `eCode`  ) AS `a` " + 
		       		 "RIGHT JOIN " + 
		       		 "(SELECT " +
					   "SUM(i.total*i.currencyRate) AS `final` , rpm.employeeCode as `eCode`, rpm.directAward as `percent`" +
					   "FROM " +
					   "InvoiceTransaction i " +
					   "INNER JOIN " +
					   "restaurant_project rp " +
					   "ON " +
					   "SUBSTRING_INDEX(i.projectCode,'/', -1) = rp.code "+
					   "INNER JOIN " +
					   "restaurant_projectmember rpm " +
					   "ON " +
					   "rp.id = rpm.projectId " +
					   "WHERE " +
					  sql +
					   "i.ActivityType = 'Receipt' AND " + 
					   "i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "' and "
					 + "i.projectCode = '"+ parent +"' group by `eCode` ) AS `b` ON a.eCode = b.eCode)) AS `e` ORDER BY `node` " )
					.field("(CASE WHEN (e.eCode1 IS NULL) THEN e.eCode2 ELSE e.eCode1 END) AS `eCode3`")
					.field("(SELECT e.name FROM employee e WHERE e.code = `eCode3` ) AS `node`", "node")
					.field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
					.field("COALESCE(e.total, 0) AS `totalPayment`", "total")
					.field("(CASE WHEN (e.percent1 IS NULL) THEN e.percent2 ELSE e.percent1 END) AS `percent`", "percent");
			}
			return rQuery;
		}

	}
	private SQLSelectQuery query_Child_DoanhThu_ChiPhi(ReportProjectMember rpm,
			String parent, int index, ActivityType activityType, String sql) {
		SQLSelectQuery rQuery = new SQLSelectQuery();
		/**TAB dự án*/
		if(conds.containsKey("project")){
			rQuery.table("((SELECT " +
				     "a.eCode AS `eCode1`, " +
				     "b.final AS `final`, " +
				     "b.eCode AS `eCode2`, " +
				     "a.total AS `total` ,  " +
				     "a.percent AS `percent1` , " +
				     "b.percent AS `percent2` " +
	       "FROM " +
	       		 "(SELECT " +
					"SUM(i.total*i.currencyRate*(rpm.percent/100)) AS `total` , rpm.employeeCode as `eCode`, rpm.percent as `percent` " +
				   "FROM " +
				   "InvoiceDetail i " +
				   "INNER JOIN " +
				   "restaurant_project rp " +
				   "ON " +
				   "SUBSTRING_INDEX(i.projectCode,'/', -1) = rp.code "+
				   "INNER JOIN " +
				   "restaurant_projectmember rpm " +
				   "ON " +
				   "rp.id = rpm.projectId " +
				   "WHERE " +
				  sql 
				   + "i.ActivityType = 'Receipt' AND " 
				   +"i.startDate >= '" + dateStart + "' AND " 
				   +"i.startDate <= '" + dateEnd + "' and "
				  + "i.projectCode = '"+ parent +"' group by `eCode` " 
				  +") AS `a` " +
	       		 "RIGHT JOIN " + 
	       		 "(SELECT " +
				   "SUM(i.total*i.currencyRate*(rpm.percent/100)) AS `final` , rpm.employeeCode as `eCode`, rpm.percent as `percent`" +
				   "FROM " +
				   "InvoiceTransaction i " +
				   "INNER JOIN " +
				   "restaurant_project rp " +
				   "ON " +
				   "SUBSTRING_INDEX(i.projectCode,'/', -1) = rp.code "+
				   "INNER JOIN " +
				   "restaurant_projectmember rpm " +
				   "ON " +
				   "rp.id = rpm.projectId " +
				   "WHERE " +
				  sql +
				    "i.ActivityType = 'Receipt' AND " 
				   + "i.transactionDate >= '" + dateStart + "' AND i.transactionDate <= '" + dateEnd + "' and "
				   + "i.projectCode = '"+ parent +"' group by `eCode`" 
				   + " ) AS `b` ON a.eCode = b.eCode)) AS `e` ORDER BY `node` " )
				.field("(CASE WHEN (e.eCode1 IS NULL) THEN e.eCode2 ELSE e.eCode1 END) AS `eCode3`")
				.field("(SELECT e.name FROM employee e WHERE e.code = `eCode3` ) AS `node`", "node")
				.field("COALESCE(e.final, 0) AS `totalReceipt`", "final")
				.field("COALESCE(e.total, 0) AS `totalPayment`", "total")
				.field("(CASE WHEN (e.percent1 IS NULL) THEN e.percent2 ELSE e.percent1 END) AS `percent`", "percent");
		}
		return rQuery;
	}
	/**
	   * @param truyền vào nhóm cha được click
	   * @return trả về danh sách các nhóm con của nó
	   */
	private List<ReportProjectMember> getParentByPath(ReportProjectMember rpm)  throws Exception{
		List<ReportProjectMember> nodes = new ArrayList<ReportProjectMember>();
		String  parent = null;
		int index = 1;
		if(rpm !=null){
			parent = rpm.getParent();
			index = index + rpm.getIndex();
		}
		String sql1 = "";
		String sql2 = "";
		if(conds.containsKey("project") && isCheck == false)
		{
			sql1= "t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' AND " ;
			sql2= "i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' AND " ;
		}
		else
		{
			sql1 = "";
			sql2 = ""; 
		}
		
		/**
		 ********************************************************** 
		 *  PARENT: THỐNG KÊ DOANH THU || CHI PHÍ || DOANH THU THUẦN|| THƯỞNG  *
		 **********************************************************  
		 */
		SQLSelectQuery rQuery = new SQLSelectQuery();
		if(typeReport.equals("Doanh thu"))
			rQuery = query_Parent_DoanhThu_ChiPhi(parent, index, ActivityType.Receipt, sql1, sql2);
		if(typeReport.equals("Chi phí"))
				rQuery = query_Parent_DoanhThu_ChiPhi(parent, index, ActivityType.Payment, sql1, sql2);
		if(typeReport.equals("Doanh thu thuần"))
			rQuery = query_Parent_ThuChiLai(parent, index, false, sql1);
		if(typeReport.equals("Thưởng"))
			rQuery = query_Parent_ThuChiLai(parent, index, false, sql1);
			System.out.println(rQuery.createSQLQuery() );
			ReportTable[] reportTable = accountingServiceClient.reportQuery(new SQLSelectQuery[] { rQuery });
			String[] field;
				reportTable[0].dump(new String[] { "node", "final", "total", "parent", "percent", "finaldirectAward", "totaldirectAward" });
				field = new String[] { "node", "final", "total", "parent", "percent",  "finaldirectAward", "totaldirectAward"};
			List<Map<String, Object>> records = reportTable[0].getRecords();
			for(int i = 0; i< records.size(); i++){
				Map<String, Object> record = records.get(i);
				Object[] values = new Object[field.length];
				for(int j = 0; j<field.length;j++)
				{
						values[j] = record.get(field[j]);
				}
				double sumFinalCharge;
				double sumTotalTransaction;
				double ratio = 0;
				double sumFinalDirectAward = 0;
				double sumTotalDirectAward = 0;
				double doanhthuthuan = 0;
				
				if (viewMoney.equals("Nghìn")) {
					sumFinalCharge = Double.parseDouble(values[1].toString()) / 1000;
					sumTotalTransaction = Double.parseDouble(values[2].toString()) / 1000;
					if(conds.containsKey("employee") && typeReport.equals("Thưởng"))
					{
						if(parent ==null)
						{
						sumFinalDirectAward = Double.parseDouble(values[5].toString()) / 1000;
						sumTotalDirectAward = Double.parseDouble(values[6].toString()) / 1000;
						}
						else
						{
							sumFinalDirectAward = 0;
							sumTotalDirectAward = 0;
						}
					}
				} else if (viewMoney.equals("Triệu")) {
					sumFinalCharge = Double.parseDouble(values[1].toString()) / 1000000;
					sumTotalTransaction = Double.parseDouble(values[2].toString()) / 1000000;
					if(conds.containsKey("employee") && typeReport.equals("Thưởng"))
					{
						sumFinalDirectAward = Double.parseDouble(values[5].toString()) / 1000000;
						sumTotalDirectAward = Double.parseDouble(values[6].toString()) / 1000000;
					}
				} else {
					
					sumFinalCharge = Double.parseDouble(values[1].toString());
					sumTotalTransaction = Double.parseDouble(values[2].toString());
					if(conds.containsKey("employee") && typeReport.equals("Thưởng"))
					{
						if(parent ==null)
						{
						sumFinalDirectAward = Double.parseDouble(values[5].toString()) / 1000;
						sumTotalDirectAward = Double.parseDouble(values[6].toString()) / 1000;
						}
						else
						{
							sumFinalDirectAward = 0;
							sumTotalDirectAward = 0;
						}
					}
				}
				doanhthuthuan = sumFinalCharge- sumTotalTransaction;
				ReportProjectMember row;
					if(typeReport.equals("Doanh thu thuần"))
						ratio = (doanhthuthuan / sumFinalCharge) * 100;
					else
						ratio = (sumTotalTransaction/sumFinalCharge) * 100;
					if(ratio<=0)
						ratio = 0;
					else
						ratio = (double)Math.round(ratio*1000)/1000;
					if (values[0] != null) {
						if(typeReport.equals("Doanh thu thuần") )
						{
							if(conds.containsKey("project"))
								row = new ReportProjectMember(values[0].toString(), MyDouble.valueOf((sumFinalCharge)).toString(),  MyDouble.valueOf((sumTotalTransaction)).toString(),  MyDouble.valueOf((doanhthuthuan)).toString(),  MyDouble.valueOf((ratio)).toString(), index);			
							else
									row = new ReportProjectMember(values[0].toString(),  MyDouble.valueOf((sumFinalCharge)).toString(),  MyDouble.valueOf((sumTotalTransaction)).toString(),  MyDouble.valueOf((doanhthuthuan)).toString(), index);			
						}
						else if(typeReport.equals("Thưởng")){
							if(conds.containsKey("project"))
							{
								row = new ReportProjectMember(values[0].toString(),  MyDouble.valueOf((doanhthuthuan)).toString(), null, null, index);			
							}
							else
							{
								row = new ReportProjectMember(values[0].toString(),  MyDouble.valueOf((doanhthuthuan)).toString(),  MyDouble.valueOf((sumFinalDirectAward- sumTotalDirectAward)).toString(), index);	
							}
						}
						else {
							if(conds.containsKey("project"))
							{
								row = new ReportProjectMember(values[0].toString(),  MyDouble.valueOf((sumFinalCharge)).toString(),  MyDouble.valueOf((sumTotalTransaction)).toString(),  MyDouble.valueOf((ratio)).toString(), null, index);			
							}
							else
								row = new ReportProjectMember(values[0].toString(),  MyDouble.valueOf((sumFinalCharge)).toString(),  MyDouble.valueOf((sumTotalTransaction)).toString(), null, index);			
						}
						if(values[3].toString() !=null)
						{
							row.setParent(values[3].toString());
							row.setType(Type.PARENT);
							pathParent = values[3].toString();
							row.setEm(false);
						}
			  				Employee e = HRModelManager.getInstance().getEmployeeByCode(values[3].toString());
			  				if(e != null)
			  					row.setEm(true);
			  				else
			  					row.setEm(false);

						if (sumFinalCharge != 0 || sumTotalTransaction != 0) {
								nodes.add(row);
						}
					}	
			}
			return nodes;
	}

	private SQLSelectQuery query_Parent_ThuChiLai(String parent, int index,
			boolean b, String sql1) {
		SQLSelectQuery rQuery = new SQLSelectQuery();
		String valueCode = "";
		if(conds.containsKey("project"))
			valueCode = "projectCode";
		else if(conds.containsKey("employee"))
			valueCode = "employeeCode";
		
		/** TAB dự án */
		if (conds.containsKey("project")) {
		//Trường hợp CLICK: ROOT
			if (parent == null) {
				rQuery.table("((SELECT " +
					     "a.path1 AS `path1`, " +
					     "b.final AS `final`, " +
					     "b.path1 AS `path2`, " +
					     "a.total AS `total` " +
		       "FROM " +
		       		 "((SELECT "
										+ "SUBSTRING_INDEX(t."+ valueCode +", '/', 2) AS `path1`, "
										+ "SUM(t.total * t.currencyRate) AS `total` "
										+ "FROM InvoiceTransaction t "
										+ "WHERE "
										+ sql1
										+ "t.ActivityType = 'Payment' AND "
										+ "t.transactionDate >= '" + dateStart + "' AND "
										+ "t.transactionDate <= '" + dateEnd + "' AND "
									+ "(LENGTH(SUBSTRING_INDEX(t."+ valueCode +", '/', 2)) - LENGTH(REPLACE(SUBSTRING_INDEX(t."+ valueCode +", '/', 2), '/', '')))  >= 1 "
										+ "GROUP BY `path1`) AS `a` " + 
		       		 "RIGHT JOIN " + 
		       		 "(SELECT " 
									  + "SUBSTRING_INDEX(t."+ valueCode +", '/', 2) AS `path1`, " 
									  + "SUM(t.total * t.currencyRate) AS `final` " 
									  + "FROM InvoiceTransaction t " 
									  + "WHERE "
									  + sql1
									  + "t.ActivityType = 'Receipt' AND " 
									  + "t.transactionDate >= '" + dateStart + "' AND " 
									  + "t.transactionDate <= '" + dateEnd + "' AND " 
								  + "(LENGTH(SUBSTRING_INDEX(t."+ valueCode +", '/', 2)) - LENGTH(REPLACE(SUBSTRING_INDEX(t."+ valueCode +", '/', 2), '/', '')))  >= 1 GROUP BY `path1`) AS `b` ON a.path1 = b.path1)) " + 
		       "UNION " +
		       "(SELECT " +
					  	"a.path1 AS `path1`, " +
					    "b.final AS `final`, " +
					    "b.path1 AS `path2`, " +
					    "a.total AS `total` " +
					 "FROM " +
					 		"((SELECT "
										+ "SUBSTRING_INDEX(t."+ valueCode +", '/', 2) AS `path1`, "
										+ "SUM(t.total * t.currencyRate) AS `total` "
										+ "FROM InvoiceTransaction t "
										+ "WHERE "
										+ sql1
										+ "t.ActivityType = 'Payment' AND "
										+ "t.transactionDate >= '" + dateStart + "' AND "
										+ "t.transactionDate <= '" + dateEnd + "' AND "
									+ "(LENGTH(SUBSTRING_INDEX(t."+ valueCode +", '/', 2)) - LENGTH(REPLACE(SUBSTRING_INDEX(t."+ valueCode +", '/', 2), '/', '')))  >= 1 "
										+ "GROUP BY `path1`) AS `a` " + 
					 		"LEFT JOIN " + 
		  				"(SELECT " 
									  + "SUBSTRING_INDEX(t."+ valueCode +", '/', 2) AS `path1`, " 
									  + "SUM(t.total * t.currencyRate) AS `final` " 
									  + "FROM InvoiceTransaction t " 
									  + "WHERE "
									  + sql1
									  + "t.ActivityType = 'Receipt' AND " 
									  + "t.transactionDate >= '" + dateStart + "' AND " 
									  + "t.transactionDate <= '" + dateEnd + "' AND " 
								  + "(LENGTH(SUBSTRING_INDEX(t."+ valueCode +", '/', 2)) - LENGTH(REPLACE(SUBSTRING_INDEX(t."+ valueCode +", '/', 2), '/', '')))  >= 1 GROUP BY `path1`) AS `b` ON a.path1 = b.path1))) AS `e` ORDER BY `node` ")
				.field("(CASE WHEN (e.path1 IS NULL) THEN e.path2 ELSE e.path1 END) AS `path3`", "parent")
				.field("(SELECT p.name FROM  restaurant_project p WHERE p.code = SUBSTRING_INDEX(path3, '/', -1)) AS `node`", "node")
				.field("COALESCE(e.final, 0) AS `finalRec`", "final")
				.field("COALESCE(e.total, 0) AS `finalPay`", "total");
			}
			//Trường hợp CLICK: PARENT 
			else {	
				rQuery.table("((SELECT " +
					     "a.path1 AS `path1`, " +
					     "b.final AS `final`, " +
					     "b.path1 AS `path2`, " +
					     "a.total AS `total` " +
		       "FROM " +
		       		 "((SELECT "
										+ "SUBSTRING_INDEX(t."+ valueCode +", '/', " + (index + 1) + ") AS `path1`, "
										+ "SUM(t.total * t.currencyRate) AS `total` "
										+ "FROM InvoiceTransaction t "
										+ "WHERE "
										+ sql1
										+ "t.ActivityType = 'Payment' AND "
										+ "t.transactionDate >= '" + dateStart + "' AND "
										+ "t.transactionDate <= '" + dateEnd + "' AND "
										+ "SUBSTRING_INDEX(t."+ valueCode +", '/', " + (index + 1) + ") LIKE '"+ parent +"%' AND "
										+ "(LENGTH(SUBSTRING_INDEX(t."+ valueCode +", '/', " + (index + 1) + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(t."+ valueCode +", '/', " + (index + 1) + "), '/', '')))  >= " + index + " "
										+ "GROUP BY `path1`) AS `a` " + 
		       		 "RIGHT JOIN " + 
		       		 "(SELECT SUBSTRING_INDEX(t."+ valueCode +", '/', " + (index + 1) + ") AS `path1`, " 
								    + "SUM(t.total * t.currencyRate) AS `final` " 
								    + "FROM InvoiceTransaction t " 
								    + "WHERE "
								    + sql1
								    + "t.ActivityType = 'Receipt' AND " 
								    + "t.transactionDate >= '" + dateStart + "' AND " 
								    + "t.transactionDate <= '" + dateEnd + "' AND " 
								    + "SUBSTRING_INDEX(t."+ valueCode +", '/', " + (index + 1) + ") LIKE '"+ parent +"%' AND " 
								    + "(LENGTH(SUBSTRING_INDEX(t."+ valueCode +", '/', " + (index + 1) + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(t."+ valueCode +", '/', " + (index + 1) + "), '/', '')))  >= " + index + " " 
									  + "GROUP BY `path1`) AS `b` ON b.path1 = a.path1)) " + 
		       "UNION " +
		       "(SELECT " +
					  	"a.path1 AS `path1`, " +
					    "b.final AS `final`, " +
					    "b.path1 AS `path2`, " +
					    "a.total AS `total` " +
					 "FROM " +
					 		"((SELECT "
										+ "SUBSTRING_INDEX(t."+ valueCode +", '/', " + (index + 1) + ") AS `path1`, "
										+ "SUM(t.total * t.currencyRate) AS `total` "
										+ "FROM InvoiceTransaction t "
										+ "WHERE "
										+ sql1
										+ "t.ActivityType = 'Payment' AND "
										+ "t.transactionDate >= '" + dateStart + "' AND "
										+ "t.transactionDate <= '" + dateEnd + "' AND "
										+ "SUBSTRING_INDEX(t."+ valueCode +", '/', " + (index + 1) + ") LIKE '"+ parent +"%' AND "
										+ "(LENGTH(SUBSTRING_INDEX(t."+ valueCode +", '/', " + (index + 1) + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(t."+ valueCode +", '/', " + (index + 1) + "), '/', '')))  >= " + index + " "
										+ "GROUP BY `path1`) AS `a` " + 
					 		"LEFT JOIN " + 
					 		"(SELECT SUBSTRING_INDEX(t."+ valueCode +", '/', " + (index + 1) + ") AS `path1`, " 
								    + "SUM(t.total * t.currencyRate) AS `final` " 
								    + "FROM InvoiceTransaction t " 
								    + "WHERE "
								    + sql1
								    + "t.ActivityType = 'Receipt' AND " 
								    + "t.transactionDate >= '" + dateStart + "' AND " 
								    + "t.transactionDate <= '" + dateEnd + "' AND " 
								    + "SUBSTRING_INDEX(t."+ valueCode +", '/', " + (index + 1) + ") LIKE '"+ parent +"%' AND " 
								    + "(LENGTH(SUBSTRING_INDEX(t."+ valueCode +", '/', " + (index + 1) + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(t."+ valueCode +", '/', " + (index + 1) + "), '/', '')))  >= " + index + " " 
									  + "GROUP BY `path1`) AS `b` ON b.path1 = a.path1))) AS `e` ORDER BY `node` ")			
				.field("(CASE WHEN (e.path1 IS NULL) THEN e.path2 ELSE e.path1 END) AS `path3`", "parent")
				.field("(SELECT p.name FROM  restaurant_project p WHERE p.code = SUBSTRING_INDEX(path3, '/', -1)) AS `node`", "node")
				.field("COALESCE(e.final, 0) AS `finalRec`", "final")
				.field("COALESCE(e.total, 0) AS `finalPay`", "total");		
			}
		}
		
		/** TAB nhân viên */
		if (conds.containsKey("employee")) {
		//Trường hợp CLICK: ROOT
			if (parent == null) {
				rQuery.table("((SELECT "
						+ "a.total  as `total`, b.final as `final`, "
						+ "(CASE WHEN (a.path1 IS NULL) THEN b.path1 ELSE a.path1 END) AS `path3` "
						+ "FROM "
						+ "(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, "
						+ "SUM(t.total * t.currencyRate) AS `total` "
						+ " FROM InvoiceTransaction t "
						+ "WHERE "
						+ "t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' AND "
						+ "t.ActivityType = 'Payment' AND t.transactionDate >= '2015-01-01 00:00:00' AND t.transactionDate <= '2015-12-31 23:59:59' "
						+ "GROUP BY  path1 ) AS `a` "
						+ "RIGHT JOIN "
						+ "(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, "
						+ "SUM(t.total * t.currencyRate) AS `final` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE "
						+ "t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' AND "
						+ "t.ActivityType = 'Receipt' AND t.transactionDate >= '2015-01-01 00:00:00' AND t.transactionDate <= '2015-12-31 23:59:59' "
						+ "GROUP BY  path1 ) AS `b` on a.path1 = b.path1) "
						+ "UNION "
						+ "(SELECT "
						+ "a.total  as `total`, b.final as `final`, "
						+ "(CASE WHEN (a.path1 IS NULL) THEN b.path1 ELSE a.path1 END) AS `path3` "
						+ "FROM "
						+ "(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, "
						+ "SUM(t.total * t.currencyRate) AS `total` "
						+ " FROM InvoiceTransaction t "
						+ "WHERE "
						+ "t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' AND "
						+ "t.ActivityType = 'Payment' AND t.transactionDate >= '2015-01-01 00:00:00' AND t.transactionDate <= '2015-12-31 23:59:59' "
						+ "GROUP BY  path1 ) AS `a` "
						+ "LEFT JOIN "
						+ "(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, "
						+ "SUM(t.total * t.currencyRate) AS `final` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE "
						+ "t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' AND "
						+ "t.ActivityType = 'Receipt' AND t.transactionDate >= '2015-01-01 00:00:00' AND t.transactionDate <= '2015-12-31 23:59:59' "
						+ "GROUP BY  path1 ) AS `b` on a.path1 = b.path1))  AS `ec` "
						+ "INNER JOIN restaurant_project rp ON ec.path3 = rp.code "
						+ "INNER JOIN restaurant_projectMember rpm ON rpm.projectId = rp.id "
						+ "WHERE ec.path3 NOT LIKE 'project-other' "
						+ "GROUP BY rpm.employeeCode ORDER BY `node`"
						)
				.field("rpm.employeeCode AS `code3`", "parent")
				.field("(SELECT e.name FROM employee e WHERE e.code = `code3` ) AS `node`", "node")
				.field("COALESCE(sum(ec.final * (rpm.percent/100)), 0) as `final`", "final")
				.field("COALESCE(sum(ec.total * (rpm.percent/100)), 0) as `total`", "total")
				.field("COALESCE(sum(ec.final * (rpm.directAward/100)), 0) as `final1`", "finaldirectAward")
				.field("COALESCE(sum(ec.total * (rpm.directAward/100)), 0) as `total1`", "totaldirectAward");
			}
			//Trường hợp CLICK: PARENT 
			else {	
				rQuery.table("((SELECT "
						+ "a.total  as `total`, b.final as `final`, "
						+ "(CASE WHEN (a.path1 IS NULL) THEN b.path1 ELSE a.path1 END) AS `path3` "
						+ "FROM "
						+ "(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, "
						+ "SUM(t.total * t.currencyRate) AS `total` "
						+ "FROM InvoiceTransactiON t "
						+ "WHERE "
						+ "t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' AND "
						+ "t.ActivityType = 'Payment' AND t.transactiONDate >= '2015-01-01 00:00:00' AND t.transactiONDate <= '2015-12-31 23:59:59' "
						+ "GROUP BY  path1 "
						+ ") AS `a` "
						+ "RIGHT JOIN "
						+ "(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, "
						+ "SUM(t.total * t.currencyRate) AS `final` "
						+ "FROM InvoiceTransactiON t "
						+ "WHERE "
						+ "t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' AND "
						+ "t.ActivityType = 'Receipt' AND t.transactiONDate >= '2015-01-01 00:00:00' AND t.transactiONDate <= '2015-12-31 23:59:59' "
						+ "GROUP BY  path1 "
						+ ") AS `b` ON a.path1 = b.path1) "
						+ " UNION "
						+ "(SELECT "
						+ " "
						+ "a.total  as `total`, b.final as `final`, "
						+ "(CASE WHEN (a.path1 IS NULL) THEN b.path1 ELSE a.path1 END) AS `path3`"
						+ "FROM "
						+ "(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, "
						+ "SUM(t.total * t.currencyRate) AS `total` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE "
						+ "t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' AND "
						+ "t.ActivityType = 'Payment' AND t.transactiONDate >= '2015-01-01 00:00:00' AND t.transactiONDate <= '2015-12-31 23:59:59' "
						+ "GROUP BY  path1 ) AS `a` "
						+ "LEFT JOIN "
						+ "(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, "
						+ "SUM(t.total * t.currencyRate) AS `final` "
						+ "FROM InvoiceTransactiON t "
						+ "WHERE "
						+ "t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' AND "
						+ "t.ActivityType = 'Receipt' AND t.transactiONDate >= '2015-01-01 00:00:00' AND t.transactiONDate <= '2015-12-31 23:59:59' "
						+ "GROUP BY  path1 ) AS `b` ON a.path1 = b.path1) ) AS `ec1` "
						+ "INNER JOIN "
						+ "(SELECT rp.code AS `path1`, rp.name AS `name`, "
						+ "(SELECT e.name FROM employee e WHERE e.code = rpm.employeeCode ) AS `node`, "
						+ "rpm.directAward AS `directAward`, "
						+ "rpm.employeeCode as `ecode` "
						+ "from restaurant_project rp "
						+ "INNER JOIN "
						+ "restaurant_projectMember rpm "
						+ "ON rpm.projectId = rp.id "
						+ "WHERE rpm.employeeCode = '" + parent+ "') AS `ec2`"
						+ "ON ec1.path3 = ec2.path1"
						+ " GROUP BY (CASE WHEN (ec1.path3 IS NULL) THEN ec2.path1 ELSE ec1.path3 END) ORDER BY `node`"
						)
					  .field("(CASE WHEN (ec1.path3 IS NULL) THEN ec2.path1 ELSE ec1.path3 END) AS `path4`", "parent")
					  .field("(SELECT p.name FROM  restaurant_project p WHERE p.code = `path4`) AS `node`", "node")
					  .field("COALESCE(SUM(ec1.final), 0) as `final`", "final")
					  .field("COALESCE(SUM(ec1.total), 0) as `total`", "total");
				
			}
		}
		return rQuery;
	}

	private SQLSelectQuery query_Parent_DoanhThu_ChiPhi(String parent,
			int index, ActivityType activityType, String sql1, String sql2) {
		SQLSelectQuery rQuery = new SQLSelectQuery();
		String valueCode = "";
		if(conds.containsKey("project"))
			valueCode = "projectCode";
		else if(conds.containsKey("employee"))
			valueCode = "employeeCode";
		
		/** TAB dự án*/ // ĐÃ SỬA
		if (conds.containsKey("project")) {
			//Trường hợp CLICK: ROOT
			if (parent == null) {
				rQuery.table("(SELECT " +
									   "SUBSTRING_INDEX(i."+ valueCode +", '/', 2) AS `path1`, " +
									   "SUM(i.finalCharge * i.currencyRate) AS `finalCharge` " +
									   " FROM " +
									   " InvoiceDetail i " +
									   " WHERE " +
									  sql2 +
									   " i.ActivityType = '"+ activityType +"' AND " + 
									   " i.startDate >= '" + dateStart + "' AND " + 
									   " i.startDate <= '" + dateEnd + "' AND " +
									   "(LENGTH(SUBSTRING_INDEX(i."+ valueCode +", '/', 2)) - LENGTH(REPLACE(SUBSTRING_INDEX(i."+ valueCode +", '/', 2), '/', '')))  >= 1 " +
									   "GROUP BY `path1`) AS `a` " + 
									   		"JOIN " +
									   "(SELECT " +
									   "SUBSTRING_INDEX(t."+ valueCode +", '/', 2) AS `path1`, " +
									   "SUM(t.total * t.currencyRate) AS `totalTransaction` " +
									   "FROM " +
									   "InvoiceTransaction t " +
									   "WHERE " +
									   sql1+
									   "t.ActivityType = '"+ activityType +"' AND " + 
									   "t.transactionDate >= '" + dateStart + "' AND " +
									   "t.transactionDate <= '" + dateEnd + "' AND " +
									   "(LENGTH(SUBSTRING_INDEX(t."+ valueCode +", '/', 2)) - LENGTH(REPLACE(SUBSTRING_INDEX(t."+ valueCode +", '/', 2), '/', '')))  >= 1 " +
										 "GROUP BY `path1`) AS `b` ON b.path1 = a.path1 ORDER BY `node`")
				.field("a.path1", "parent")
				.field("(SELECT p.name FROM  restaurant_project p WHERE p.code = SUBSTRING_INDEX(a.path1, '/', -1)) AS `node`", "node")
				.field("COALESCE(a.finalCharge,0)", "final")
				.field("COALESCE(b.totalTransaction,0)", "total");
			} 
			//Trường hợp CLICK: PARENT 
			else {
				rQuery.table("(SELECT " +
						 "SUBSTRING_INDEX(i."+ valueCode +", '/', " + (index + 1) + ") AS `path1`, " +
					   "SUM(i.finalCharge * i.currencyRate) AS `finalCharge` " +
					   "FROM " +
					   "InvoiceDetail i " +
					   "WHERE " +
					   sql2+
					   "i.ActivityType = '"+ activityType +"' AND " + 
					   "i.startDate >= '" + dateStart + "' AND " + 
					   "i.startDate <= '" + dateEnd + "' AND " +
					   "SUBSTRING_INDEX(i."+ valueCode +", '/', " + (index + 1) + ") LIKE '"+ parent +"%' AND " + 
					   "(LENGTH(SUBSTRING_INDEX(i."+ valueCode +", '/', " + (index + 1) + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(i."+ valueCode +", '/', " + (index + 1) + "), '/', '')))  >= " + index + " " +
					   "GROUP BY `path1`) AS `a` " + 
					   			"JOIN " +
					   "(SELECT " +
					   "SUBSTRING_INDEX(t."+ valueCode +", '/', " + (index + 1) + ") AS `path1`, " +
					   "SUM(t.total * t.currencyRate) AS `totalTransaction` " +
					   "FROM " +
					   "InvoiceTransaction t " +
					   "WHERE " +
					  sql1+
					   "t.ActivityType = '"+ activityType +"' AND " + 
					   "t.transactionDate >= '" + dateStart + "' AND " +
					   "t.transactionDate <= '" + dateEnd + "' AND " +
					   "SUBSTRING_INDEX(t."+ valueCode +", '/', " + (index + 1) + ") LIKE '"+ parent +"%' AND " + 
					   "(LENGTH(SUBSTRING_INDEX(t."+ valueCode +", '/', " + (index + 1) + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(t."+ valueCode +", '/', " + (index + 1) + "), '/', '')))  >= " + index + " " +
						 "GROUP BY `path1`) AS `b` ON b.path1 = a.path1 ORDER BY `node`")
				.field("a.path1", "parent")
				.field("(SELECT p.name FROM restaurant_project p WHERE p.code = SUBSTRING_INDEX(a.path1, '/', -1)) AS `node`", "node")
				.field("COALESCE(a.finalCharge,0)", "final")
				.field("COALESCE(b.totalTransaction,0)", "total");			
			}
		}
		/** TAB nhân viên */
		if (conds.containsKey("employee")) {
		//Trường hợp CLICK: ROOT
			if (parent == null) {
				rQuery.table("((SELECT "
						+ "a.total  as `total`, b.final as `final`, "
						+ "(CASE WHEN (a.path1 IS NULL) THEN b.path1 ELSE a.path1 END) AS `path3` "
						+ "FROM "
						+ "(SELECT SUBSTRING_INDEX(i.projectCode, '/', -1) AS `path1`, "
						+ "SUM(i.total * i.currencyRate) AS `total` "
						+ " FROM InvoiceDetail i  "
						+ "WHERE "
						+ "i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' AND "
						+ "i.ActivityType = '" + activityType+ "' AND i.startDate >= '2015-01-01 00:00:00' AND i.startDate <= '2015-12-31 23:59:59' "
						+ "GROUP BY  path1 ) AS `a` "
						+ "RIGHT JOIN "
						+ "(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, "
						+ "SUM(t.total * t.currencyRate) AS `final` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE "
						+ "t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' AND "
						+ "t.ActivityType = '" + activityType+ "' AND t.transactionDate >= '2015-01-01 00:00:00' AND t.transactionDate <= '2015-12-31 23:59:59' "
						+ "GROUP BY  path1 ) AS `b` on a.path1 = b.path1) "
						+ "UNION "
						+ "(SELECT "
						+ "a.total  as `total`, b.final as `final`, "
						+ "(CASE WHEN (a.path1 IS NULL) THEN b.path1 ELSE a.path1 END) AS `path3` "
						+ "FROM "
						+ "(SELECT SUBSTRING_INDEX(i.projectCode, '/', -1) AS `path1`, "
						+ "SUM(i.total * i.currencyRate) AS `total` "
						+ " FROM InvoiceDetail i "
						+ "WHERE "
						+ "i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' AND "
						+ "i.ActivityType = '" + activityType+ "' AND i.startDate >= '2015-01-01 00:00:00' AND i.startDate <= '2015-12-31 23:59:59' "
						+ "GROUP BY  path1 ) AS `a` "
						+ "LEFT JOIN "
						+ "(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, "
						+ "SUM(t.total * t.currencyRate) AS `final` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE "
						+ "t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' AND "
						+ "t.ActivityType = '" + activityType+ "' AND t.transactionDate >= '2015-01-01 00:00:00' AND t.transactionDate <= '2015-12-31 23:59:59' "
						+ "GROUP BY  path1 ) AS `b` on a.path1 = b.path1))  AS `ec` "
						+ "INNER JOIN restaurant_project rp ON ec.path3 = rp.code "
						+ "INNER JOIN restaurant_projectMember rpm ON rpm.projectId = rp.id "
						+ "WHERE ec.path3 NOT LIKE 'project-other' "
						+ "GROUP BY rpm.employeeCode ORDER BY `node`"
						)
				.field("rpm.employeeCode AS `code3`", "parent")
				.field("(SELECT e.name FROM employee e WHERE e.code = `code3` ) AS `node`", "node")
				.field("COALESCE(sum(ec.final * (rpm.percent/100)), 0) as `final`", "final")
				.field("COALESCE(sum(ec.total * (rpm.percent/100)), 0) as `total`", "total");
			}
			//Trường hợp CLICK: PARENT 
			else {	
				rQuery.table("((SELECT "
						+ "a.total  as `total`, b.final as `final`, "
						+ "(CASE WHEN (a.path1 IS NULL) THEN b.path1 ELSE a.path1 END) AS `path3` "
						+ "FROM "
						+ "(SELECT SUBSTRING_INDEX(i.projectCode, '/', -1) AS `path1`, "
						+ "SUM(i.total * i.currencyRate) AS `total` "
						+ "FROM InvoiceDetail i "
						+ "WHERE "
						+ "i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' AND "
						+ "i.ActivityType = '" + activityType+ "' AND i.startDate >= '2015-01-01 00:00:00' AND i.startDate <= '2015-12-31 23:59:59' "
						+ "GROUP BY  path1 "
						+ ") AS `a` "
						+ "RIGHT JOIN "
						+ "(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, "
						+ "SUM(t.total * t.currencyRate) AS `final` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE "
						+ "t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' AND "
						+ "t.ActivityType = '" + activityType+ "' AND t.transactiONDate >= '2015-01-01 00:00:00' AND t.transactiONDate <= '2015-12-31 23:59:59' "
						+ "GROUP BY  path1 "
						+ ") AS `b` ON a.path1 = b.path1) "
						+ " UNION "
						+ "(SELECT "
						+ " "
						+ "a.total  as `total`, b.final as `final`, "
						+ "(CASE WHEN (a.path1 IS NULL) THEN b.path1 ELSE a.path1 END) AS `path3`"
						+ "FROM "
						+ "(SELECT SUBSTRING_INDEX(i.projectCode, '/', -1) AS `path1`, "
						+ "SUM(i.total * i.currencyRate) AS `total` "
						+ "FROM InvoiceDetail i "
						+ "WHERE "
						+ "i.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' AND "
						+ "i.ActivityType = '" + activityType+ "' AND i.startDate >= '2015-01-01 00:00:00' AND i.startDate <= '2015-12-31 23:59:59' "
						+ "GROUP BY  path1 ) AS `a` "
						+ "LEFT JOIN "
						+ "(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, "
						+ "SUM(t.total * t.currencyRate) AS `final` "
						+ "FROM InvoiceTransaction t "
						+ "WHERE "
						+ "t.type NOT LIKE '"+ AccountingModelManager.typeSanXuat +"' AND "
						+ "t.ActivityType = '" + activityType+ "' AND t.transactiONDate >= '2015-01-01 00:00:00' AND t.transactiONDate <= '2015-12-31 23:59:59' "
						+ "GROUP BY  path1 ) AS `b` ON a.path1 = b.path1) ) AS `ec1` "
						+ "INNER JOIN "
						+ "(SELECT rp.code AS `path1`, rp.name AS `name`, "
						+ "(SELECT e.name FROM employee e WHERE e.code = rpm.employeeCode ) AS `node`, "
						+ "rpm.percent AS `percent`, "
						+ "rpm.employeeCode as `ecode` "
						+ "from restaurant_project rp "
						+ "INNER JOIN "
						+ "restaurant_projectMember rpm "
						+ "ON rpm.projectId = rp.id "
						+ "WHERE rpm.employeeCode = '" + parent+ "') AS `ec2`"
						+ "ON ec1.path3 = ec2.path1"
						+ " GROUP BY (CASE WHEN (ec1.path3 IS NULL) THEN ec2.path1 ELSE ec1.path3 END) ORDER BY `node`"
						)
					  .field("(CASE WHEN (ec1.path3 IS NULL) THEN ec2.path1 ELSE ec1.path3 END) AS `path4`", "parent")
					  .field("(SELECT p.name FROM  restaurant_project p WHERE p.code = `path4`) AS `node`", "node")
					  .field("COALESCE(SUM(ec1.final), 0) as `final`", "final")
					  .field("COALESCE(SUM(ec1.total), 0) as `total`", "total");	
			}
		}
		return rQuery;
	}
	public ModelTableReportProjectMember getModel() {
		return model;
	}
	public PanelTableCell getCell() {
		return cell;
	}
	
}
