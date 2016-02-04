package com.hkt.client.swingexp.app.banhang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import org.jfree.data.general.DefaultPieDataset;

import com.hkt.client.swingexp.app.component.ExtendChartPanelReport;
import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.ExtendJCheckBox;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.GridLabelLayoutPabel;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.TableFillterSorter;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.AreaJComboBox;
import com.hkt.client.swingexp.app.core.CustomerGroupJComboBox;
import com.hkt.client.swingexp.app.core.DepartmentJComboBox;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogReport;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialogPlus;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.core.MyTabbedPaneUI;
import com.hkt.client.swingexp.app.core.ProductTagJComboBox;
import com.hkt.client.swingexp.app.core.TableJComboBox;
import com.hkt.client.swingexp.app.core.UnitMoneyJComboBox;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelOption;
import com.hkt.client.swingexp.app.hethong.Processing;
import com.hkt.client.swingexp.app.khachhang.OrganizationBasic;
import com.hkt.client.swingexp.app.khohang.PanelWarehouse;
import com.hkt.client.swingexp.app.print.ReportGeneral;
import com.hkt.client.swingexp.app.thuchi.PanelPaymentReceipt;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.GenericOptionModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.config.generic.Option;
import com.hkt.module.config.generic.OptionGroup;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.product.entity.Product;
import com.hkt.module.restaurant.entity.Project;
import com.hkt.module.restaurant.entity.ProjectMember;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.module.warehouse.entity.Warehouse;
import com.hkt.swingexp.app.report.entity.ReportSalesEntity;
import com.hkt.swingexp.app.report.modeltable.TableModelReportInventory;
import com.hkt.swingexp.app.report.modeltable.TableModelReportSales;
import com.hkt.swingexp.app.report.table.TableInventory;
import com.hkt.swingexp.app.report.table.TableReportBank;
import com.hkt.swingexp.app.report.table.TableReportForTime;
import com.hkt.swingexp.app.report.table.TableReportProjectMember;
import com.hkt.swingexp.app.report.table.TableReportRevenue;
import com.hkt.swingexp.app.report.table.TableReportSales;

/**
 * @author: Phan Anh
 * @dateCreate: 11/04/2014
 * @DateEdit: 13/10/2015- Bui Trong Hieu
 */

public class ReportStatistics extends JPanel implements IDialogReport {
	private JButton ketxuat2, ketxuat3;
	private TableReportProjectMember table;
	private JTable tableStatic;
	private JTabbedPane tabbedPane;
	private JScrollPane scrollPane;
	private ExtendJLabel lblOrganization, lblLabel1, lblLabel2;
	private ExtendJComboBox cbTypeReport, cbFilterByTime, cbFilterByType, cbValueMoney;
	private ExtendJComboBox cbMonth, cbYear;
	private ExtendJButton btnView;
	private ExtendJCheckBox chbShowAll = new ExtendJCheckBox("Tất cả kết quả");
	private MyJDateChooser dcDay1, dcDay2;
	private ExtendJComboBox cbOrganization1, cbOrganization2, cbOrganization3, cbOrganization4, cbOrganization5,
	    cbOrganization6, cbOrganization7;
	private DepartmentJComboBox cbDepartment1, cbDepartment2;
	private TableJComboBox cbTable;
	private CustomerGroupJComboBox cbGroupPartner;
	private AreaJComboBox cbLocation;
	private ProductTagJComboBox cbGroupProduct;
	private TextPopup<Product> txtProduct;
	private UnitMoneyJComboBox cbUnitMoney;
	private JComboBox cboStore;
	private TextPopup<Project> txtProject;
	private TextPopup<Employee> txtEmployee;
	private TextPopup<Customer> txtCustomer;
	private ExtendJLabel lblValueMoney, lblTypeReport, lblFilterByTime, lblFilterByType;
	private ExtendJTextField txtSearch;
	private TableFillterSorter tableFillterSorter;
	private Processing processing;
	public static String permission;
	private Calendar now = Calendar.getInstance();
	private int yearNow = now.get(Calendar.YEAR);
	private String nameOrganization = "";
	private boolean isCheck = false;
	private String[] byValueMoney = new String[3];
	private String[] byTypeReport = new String[4];
	private String[] onYear;
	private String[] byDay = new String[4];
	private String[] byMonth = new String[4];
	private String[] byYear = new String[4];
	private String[] byType = new String[7];
	private String[] onMonth = new String[12];
	private Profile profileRememberShowAll = new Profile();
	private TypeReport typeReport;
	private JPanel containerChart, panelPieChart, panelLineChart;
	private ExtendChartPanelReport chartPanel;
	private String valuePie = "Lãi(lỗ)";

	public static enum TypeReport {
		TKMuaHang, TKBanHang, TKThuChi, TKThoiGian, TKTonKho, TKDinhLuong, TKNhanVien, TKKeToan
	};

	/**
	 * Constructor class ReportStatistics
	 * 
	 * @param table
	 *          - truyền vào bảng hiển thị [Mặc định hiển thị bảng trống]
	 * @param nameReport
	 *          - truyền vào kiểu thống kê có các value sau: [TKThuChi ||
	 *          TKThoiGian || TKBanHang || TKMuaHang || TKTonKho || TKDinhLuong ||
	 *          TKNhanVien
	 */
	public ReportStatistics(JTable table, TypeReport nameReport) {
		typeReport = nameReport;
		try {
			Account accountOrganization = AccountModelManager.getInstance().getAccountByLoginId(
			    ManagerAuthenticate.getInstance().getOrganizationLoginId());
			nameOrganization = accountOrganization.getProfiles().getBasic().get(OrganizationBasic.NAME).toString();

			profileRememberShowAll = AccountModelManager.getInstance().getProfileConfigAdmin();
			if (profileRememberShowAll.size() >= 1) {
				if (profileRememberShowAll.containsKey("rememberShowAll")) {
					chbShowAll.setSelected(true);
				}
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}

		lblTypeReport = new ExtendJLabel("Kiểu thống kê");
		lblFilterByType = new ExtendJLabel("Thống kê theo");
		lblFilterByTime = new ExtendJLabel("Chi tiết TG");
		lblValueMoney = new ExtendJLabel("Tính theo");

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		scrollPane.setPreferredSize(new Dimension(100, 200));
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		panelPieChart = new JPanel(new BorderLayout(0, 0));
		panelPieChart.setOpaque(false);
		panelPieChart.setPreferredSize(new Dimension(400, 100));
		panelPieChart.setVisible(false);
		panelLineChart = new JPanel(new BorderLayout(0, 0));
		panelLineChart.setOpaque(false);
		panelLineChart.setVisible(false);
		containerChart = new JPanel(new BorderLayout(0, 0));
		containerChart.setBackground(Color.white);
		containerChart.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		containerChart.setVisible(false);
		containerChart.setPreferredSize(new Dimension(100, 200));
		containerChart.add(panelPieChart, BorderLayout.CENTER);
		// containerChart.add(panelLineChart, BorderLayout.CENTER);

		JPanel line_start = new JPanel();
		line_start.setOpaque(false);
		line_start.setLayout(new BorderLayout(20, 0));
		line_start.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		if (nameReport.equals(TypeReport.TKThuChi)) {
			line_start.add(PanelThongKeTrai_Type1(nameReport), BorderLayout.LINE_START);
			line_start.add(PanelThongKePhai(nameReport, null, false), BorderLayout.CENTER);
		} else {
			if (nameReport.equals(TypeReport.TKThoiGian)) {
				line_start.add(PanelThongKeTrai_Type1(nameReport), BorderLayout.LINE_START);
				line_start.add(PanelThongKeThoiGianPhai(), BorderLayout.CENTER);
			} else {
				if (nameReport.equals(TypeReport.TKBanHang)) {
					line_start.add(PanelThongKeTrai_Type2(nameReport), BorderLayout.LINE_START);
					line_start.add(PanelThongKePhai(nameReport, ActivityType.Receipt, false), BorderLayout.CENTER);
				} else {
					if (nameReport.equals(TypeReport.TKMuaHang)) {
						line_start.add(PanelThongKeTrai_Type2(nameReport), BorderLayout.LINE_START);
						line_start.add(PanelThongKePhai(nameReport, ActivityType.Payment, false), BorderLayout.CENTER);
					} else {
						if (nameReport.equals(TypeReport.TKTonKho)) {
							line_start.add(PanelThongKeTrai_Type2(nameReport), BorderLayout.LINE_START);
							line_start.add(PanelThongKePhai(nameReport, null, true), BorderLayout.CENTER);
						} else {
							if (nameReport.equals(TypeReport.TKDinhLuong)) {
								line_start.add(PanelThongKeTrai_Type2(nameReport), BorderLayout.LINE_START);
								line_start.add(PanelThongKePhai(nameReport, null, false), BorderLayout.CENTER);
							} else {
								if (nameReport.equals(TypeReport.TKNhanVien)) {
									line_start.add(PanelThongKeTrai_Type3(), BorderLayout.LINE_START);
									line_start.add(PanelThongKeNhanVienPhai(), BorderLayout.CENTER);
								} else {
									if (nameReport.equals(TypeReport.TKKeToan)) {
										line_start.add(PanelThongKeTrai_Type4(nameReport), BorderLayout.LINE_START);
										line_start.add(PanelThongKePhai(nameReport, null, false), BorderLayout.CENTER);
									} else {
									line_start.add(new JPanel(), BorderLayout.LINE_START);
									line_start.add(new JPanel(), BorderLayout.CENTER);
									}
								}
							}
						}
					}
				}
			}
		}
		// CONTAINER_CENTER line_center = new CONTAINER_CENTER();

		JPanel background = new JPanel();
		setOpaque(false);
		background.setOpaque(false);
		background.setLayout(new BorderLayout(0, 10));
		background.add(line_start, BorderLayout.PAGE_START);
		background.add(scrollPane, BorderLayout.CENTER);
		background.add(containerChart, BorderLayout.PAGE_END);

		this.setLayout(new BorderLayout(0, 0));
		this.add(background, BorderLayout.CENTER);
		HashMap<String, String> conds = new HashMap<String, String>();
//		TableReportRevenueTest table1 = new TableReportRevenueTest(conds, "", new Date(), new Date(), "", true);
		TableReportRevenue table1 = new TableReportRevenue(conds, "", new Date(), new Date(), "", true);
		scrollPane.setViewportView(table1);

		// TODO: Add thong ke do thi --- TEST
		table1.setReportGui(this);

	}

	@Override
	public void caculateReport() {
		try {
			processing.setVisible(true);
			new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() throws Exception {
					// Tính thờ gian query
					long startTime = System.currentTimeMillis();
					// Vào giao diện chạy luôn thống kê
					if (typeReport.equals(TypeReport.TKThuChi)) {
						btnView_EventHandlerThongKeThuChi(null);
					}
					if (typeReport.equals(TypeReport.TKThoiGian)) {
						btnView_EventHandlerThongKeThoiGian(null);
					}
					if (typeReport.equals(TypeReport.TKBanHang)) {
						btnView_EventHandlerThongKeMuaBanHang(new ActionEvent(ActivityType.Receipt, 0, ""));
					}
					if (typeReport.equals(TypeReport.TKMuaHang)) {
						btnView_EventHandlerThongKeMuaBanHang(new ActionEvent(ActivityType.Payment, 0, ""));
					}
					if (typeReport.equals(TypeReport.TKTonKho)) {
						btnView_EventHandlerThongKeTonKho_DinhLuong(new ActionEvent("true", 0, ""));
					}
					if (typeReport.equals(TypeReport.TKDinhLuong)) {
						btnView_EventHandlerThongKeTonKho_DinhLuong(new ActionEvent("false", 0, ""));
					}
					if (typeReport.equals(TypeReport.TKNhanVien)) {
						btnView_EventHandlerThongKeNhanVien(null);
					}
					if (typeReport.equals(TypeReport.TKKeToan)) {
						btnView_EventHandlerThongKeKeToan(null);
					}

					// Kết quả thời gian query:
					long endTime = System.currentTimeMillis();
					NumberFormat formatter = new DecimalFormat("#0.00000");
					String time = formatter.format((endTime - startTime) / 1000d);
					System.out.println("###$$$ TIME RUN QUERY: " + time);

					return null;
				};

				@Override
				protected void done() {
					processing.setVisible(false);
				};
			}.execute();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Quá trình thao tác phát sinh lỗi!" + ex.toString());
		}

	}

	// Sự kiện cho nút Print
	@Override
	public void Print() {
		try {
			ReportGeneral report = new ReportGeneral();
			report.setTable(tableStatic);
			String loginId = ManagerAuthenticate.getInstance().getOrganizationLoginId();
			String nameEnterprise = AccountModelManager.getInstance().getNameByLoginId(loginId);
			String address = AccountModelManager.getInstance().getAddressByLoginId(loginId);
			report.setEnterprise(nameEnterprise);
			report.setAddress(address);
			report.setLogoEnterprise(new ImageIcon());
			report.setReportName("Danh sách in");
			report.setVisible(true);
		} catch (Exception e) {
			String str = "Chức năng in lỗi!";
			PanelChoise pnPanel = new PanelChoise(str);
			pnPanel.setName("Xoa");
			DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
			dialog1.setName("dlXoa");
			dialog1.setTitle("In báo cáo");
			dialog1.setLocationRelativeTo(null);
			dialog1.setModal(true);
			dialog1.setVisible(true);
			e.printStackTrace();
		}
	}

	// Sự kiện cho nút đồ thị
	public void Chart() {
		PanelChartOptionReport panelChartOptionReport = null;
		DialogResto dialog = null;
		if (typeReport.equals(TypeReport.TKThuChi)) {
			panelChartOptionReport = new PanelChartOptionReport(typeReport, cbTypeReport.getSelectedItem().toString(),
			    panelPieChart.isVisible(), panelLineChart.isVisible(), valuePie);
			dialog = new DialogResto(panelChartOptionReport, false, 0, 280);
		} else {
			if (typeReport.equals(TypeReport.TKThoiGian)) {
				panelChartOptionReport = new PanelChartOptionReport(typeReport, cbTypeReport.getSelectedItem().toString(),
				    panelPieChart.isVisible(), panelLineChart.isVisible(), valuePie);
				dialog = new DialogResto(panelChartOptionReport, false, 0, 400);
			} else {
				if (typeReport.equals(TypeReport.TKBanHang) || typeReport.equals(TypeReport.TKMuaHang)) {
					panelChartOptionReport = new PanelChartOptionReport(typeReport, "", panelPieChart.isVisible(),
					    panelLineChart.isVisible(), valuePie);
					dialog = new DialogResto(panelChartOptionReport, false, 0, 250);
				} else {
					if (typeReport.equals(TypeReport.TKDinhLuong) || typeReport.equals(TypeReport.TKTonKho)) {
						panelChartOptionReport = new PanelChartOptionReport(typeReport, "", panelPieChart.isVisible(),
						    panelLineChart.isVisible(), valuePie);
						dialog = new DialogResto(panelChartOptionReport, false, 0, 400);
					} else {
						// Các loại thống kê khác: TK Dự án etc ...
					}
				}
			}
		}

		dialog.setName("dlChartOptionReport");
		dialog.setTitle("Thiết lập đồ thị");
		dialog.setVisible(true);
		dialog.setLocationRelativeTo(null);
		if (panelChartOptionReport.mouseClickSave()) {
			panelPieChart.setVisible(panelChartOptionReport.isViewPieChart());
			panelLineChart.setVisible(panelChartOptionReport.isViewLineChart());
			if (panelChartOptionReport.isViewLineChart() || panelChartOptionReport.isViewPieChart()) {
				containerChart.setVisible(true);
				valuePie = panelChartOptionReport.getValueSelectionPieChart();
			} else
				containerChart.setVisible(false);
		}
	}

	/**
	 * Giao diện sử dụng cho: [Thống kê thu chi] && [Thống kê thời gian]
	 * 
	 * @param nameReport
	 * @return: JPanel
	 */
	private JPanel PanelThongKeTrai_Type1(TypeReport nameReport) {
		PANELTOPLEFT panelMain = new PANELTOPLEFT();

		// TAB phòng ban
		JPanel panelTabDepartment = new JPanel();
		panelTabDepartment.setOpaque(false);
		panelTabDepartment.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 20));

		lblOrganization = new ExtendJLabel("Doanh nghiệp");
		lblLabel1 = new ExtendJLabel("Phòng ban");
		cbOrganization1 = new ExtendJComboBox();
		cbDepartment1 = new DepartmentJComboBox();
		cbOrganization1.setEnabled(false);
		cbOrganization1.addItem(nameOrganization);

		MyGroupLayout layout1 = new MyGroupLayout(panelTabDepartment);
		layout1.add(0, 0, lblOrganization);
		layout1.add(0, 1, cbOrganization1);
		layout1.add(1, 0, lblLabel1);
		layout1.add(1, 1, cbDepartment1);
		layout1.updateGui();

		// TAB sản phẩm
		JPanel panelTabProduct = new JPanel();
		panelTabProduct.setOpaque(false);
		panelTabProduct.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 20));

		lblOrganization = new ExtendJLabel("Doanh nghiệp");
		lblLabel1 = new ExtendJLabel("Nhóm SP");
		lblLabel2 = new ExtendJLabel("Sản phẩm");
		cbOrganization2 = new ExtendJComboBox();
		cbGroupProduct = new ProductTagJComboBox();
		txtProduct = new TextPopup<Product>(Product.class);
		try {
			txtProduct.setData(ProductModelManager.getInstance().filterProduct());
		} catch (Exception e) {
		}

		cbOrganization2.setEnabled(false);
		cbOrganization2.addItem(nameOrganization);

		cbGroupProduct.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cbGroupProduct.getSelectedProductTag() != null) {
					try {
						List<Product> products = ProductModelManager.getInstance().findByProductTag(
						    cbGroupProduct.getSelectedProductTag().getTag());
						txtProduct.setData(products);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						txtProduct.setData(ProductModelManager.getInstance().filterProduct());
					} catch (Exception e2) {
					}
				}
			}
		});

		MyGroupLayout layout2 = new MyGroupLayout(panelTabProduct);
		layout2.add(0, 0, lblOrganization);
		layout2.add(0, 1, cbOrganization2);
		layout2.add(1, 0, lblLabel1);
		layout2.add(1, 1, cbGroupProduct);
		layout2.add(1, 2, lblLabel2);
		layout2.add(1, 3, txtProduct);
		layout2.updateGui();

		// TAB khu vực
		JPanel panelTabLocation = new JPanel();
		panelTabLocation.setOpaque(false);
		panelTabLocation.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 20));

		lblOrganization = new ExtendJLabel("Doanh nghiệp");
		lblLabel1 = new ExtendJLabel("Khu vực");
		lblLabel2 = new ExtendJLabel("Bàn/quầy");
		cbOrganization3 = new ExtendJComboBox();
		cbTable = new TableJComboBox();
		cbLocation = new AreaJComboBox();
		cbOrganization3.setEnabled(false);
		cbOrganization3.addItem(nameOrganization);
		cbLocation.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cbLocation.getSelectedLocation() != null) {
					try {
						List<Table> tables = RestaurantModelManager.getInstance().findTableByLocation(
						    cbLocation.getSelectedLocation().getCode());
						cbTable.setData(tables);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					cbTable.resetData();
				}
			}
		});

		MyGroupLayout layout3 = new MyGroupLayout(panelTabLocation);
		layout3.add(0, 0, lblOrganization);
		layout3.add(0, 1, cbOrganization3);
		layout3.add(1, 0, lblLabel1);
		layout3.add(1, 1, cbLocation);
		layout3.add(1, 2, lblLabel2);
		layout3.add(1, 3, cbTable);
		layout3.updateGui();

		// TAB khách hàng
		JPanel panelTabPartner = new JPanel();
		panelTabPartner.setOpaque(false);
		panelTabPartner.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 20));

		lblOrganization = new ExtendJLabel("Doanh nghiệp");
		lblLabel1 = new ExtendJLabel("Nhóm KH");
		lblLabel2 = new ExtendJLabel("Khách hàng");
		cbOrganization4 = new ExtendJComboBox();
		cbGroupPartner = new CustomerGroupJComboBox();
		txtCustomer = new TextPopup<Customer>(Customer.class);
		try {
			txtCustomer.setData(CustomerModelManager.getInstance().getCustomers(false));
		} catch (Exception e) {
		}
		cbOrganization4.setEnabled(false);
		cbOrganization4.addItem(nameOrganization);
		cbGroupPartner.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cbGroupPartner.getSelectedGroupCustomer() != null) {
					try {
						List<AccountMembership> listMembership = AccountModelManager.getInstance().findMembershipByGroupPath(
						    cbGroupPartner.getSelectedGroupCustomer().getPath());
						List<Customer> listCus = new ArrayList<Customer>();
						for (AccountMembership accMem : listMembership) {
							Customer c = CustomerModelManager.getInstance().getBydLoginId(accMem.getLoginId());
							if (c != null) {
								listCus.add(c);
							}
						}
						txtCustomer.setData(listCus);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						txtCustomer.setData(CustomerModelManager.getInstance().getCustomers(false));
					} catch (Exception e2) {
					}
				}
			}
		});

		MyGroupLayout layout4 = new MyGroupLayout(panelTabPartner);
		layout4.add(0, 0, lblOrganization);
		layout4.add(0, 1, cbOrganization4);
		layout4.add(1, 0, lblLabel1);
		layout4.add(1, 1, cbGroupPartner);
		layout4.add(1, 2, lblLabel2);
		layout4.add(1, 3, txtCustomer);
		layout4.updateGui();

		// TAB nhân viên
		JPanel panelTabCashier = new JPanel();
		panelTabCashier.setOpaque(false);
		panelTabCashier.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 20));
		JCheckBox checkBox = new JCheckBox();
		checkBox.setSize(new Dimension(20, 20));
		checkBox.setMaximumSize(new Dimension(20, 20));
		checkBox.setOpaque(false);
		checkBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					isCheck = true;
				} else {
					isCheck = false;
				}

			}
		});
		if (nameReport == TypeReport.TKThoiGian) {
			checkBox.setEnabled(false);
		}
		lblOrganization = new ExtendJLabel("Doanh nghiệp");
		lblLabel1 = new ExtendJLabel("Phòng ban");
		lblLabel2 = new ExtendJLabel("Nhân viên");
		cbOrganization5 = new ExtendJComboBox();
		cbDepartment2 = new DepartmentJComboBox();
		txtEmployee = new TextPopup<Employee>(Employee.class);
		try {
			txtEmployee.setData(HRModelManager.getInstance().getEmployees());
		} catch (Exception e) {
		}
		cbOrganization5.setEnabled(false);
		cbOrganization5.addItem(nameOrganization);
		cbDepartment2.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cbDepartment2.getSelectedDepartment() != null) {
					try {
						List<AccountMembership> listMembership = AccountModelManager.getInstance().findMembershipByGroupPath(
						    cbDepartment2.getSelectedDepartment().getPath());
						List<Employee> employees = new ArrayList<Employee>();
						for (AccountMembership accMem : listMembership) {
							Employee employee = HRModelManager.getInstance().getBydLoginId(accMem.getLoginId());
							employees.add(employee);
						}
						txtEmployee.setData(employees);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						txtEmployee.setData(HRModelManager.getInstance().getEmployees());
					} catch (Exception e2) {
					}
				}
			}
		});
		PanelContainer pc = new PanelContainer(txtEmployee, checkBox);
		MyGroupLayout layout5 = new MyGroupLayout(panelTabCashier);
		layout5.add(0, 0, lblOrganization);
		layout5.add(0, 1, cbOrganization5);
		layout5.add(0, 2, new ExtendJLabel("Tính thu ngân"));
		layout5.add(0, 3, checkBox);
		layout5.add(1, 0, lblLabel1);
		layout5.add(1, 1, cbDepartment2);
		layout5.add(1, 2, lblLabel2);
		layout5.add(1, 3, txtEmployee);
		// layout5.add(1, 4, checkBox);
		layout5.updateGui();

		// TAB cửa hàng
		JPanel panelTabShop = new JPanel();
		panelTabShop.setOpaque(false);
		panelTabShop.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 20));

		lblOrganization = new ExtendJLabel("Doanh nghiệp");
		lblLabel1 = new ExtendJLabel("Loại hóa đơn");
		cbOrganization6 = new ExtendJComboBox();
		cboStore = new JComboBox();
		cbOrganization6.setEnabled(false);
		cbOrganization6.addItem(nameOrganization);
		cboStore.setFont(new java.awt.Font("Tahoma", 0, 12));
		try {
			OptionGroup optionGroup = GenericOptionModelManager.getInstance().getOptionGroup("accounting", "AccountingService", "type_invoice");
			List<Option> list = new ArrayList<Option>();
			list.addAll(optionGroup.getOptions());
			list.add(0, null);
			cboStore.setModel(new DefaultComboBoxModel(list.toArray()));
		} catch (Exception e) {
		}
		cboStore.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					try {
						DialogMain dialog = new DialogMain(new PanelOption("accounting", "AccountingService", "type_invoice"));
						dialog.setIcon("doanhnghiep1.png");
						dialog.setTitle("Loại hóa đơn");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);
						try {
							OptionGroup optionGroup = GenericOptionModelManager.getInstance().getOptionGroup("accounting", "AccountingService", "type_invoice");
							List<Option> list = new ArrayList<Option>();
							list.addAll(optionGroup.getOptions());
							list.add(0, null);
							cboStore.setModel(new DefaultComboBoxModel(list.toArray()));
						} catch (Exception e1) {
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		MyGroupLayout layout6 = new MyGroupLayout(panelTabShop);
		layout6.add(0, 0, lblOrganization);
		layout6.add(0, 1, cbOrganization6);
		layout6.add(1, 0, lblLabel1);
		layout6.add(1, 1, cboStore);
		layout6.updateGui();

		// TAB DỰ ÁN
		JPanel panelTabProject = new JPanel();
		panelTabProject.setOpaque(false);
		panelTabProject.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 20));

		lblOrganization = new ExtendJLabel("Doanh nghiệp");
		lblLabel1 = new ExtendJLabel("Dự án");
		cbOrganization7 = new ExtendJComboBox();
		txtProject = new TextPopup<Project>(Project.class);
		cbOrganization7.setEnabled(false);
		cbOrganization7.addItem(nameOrganization);
		try {
			txtProject.setData(RestaurantModelManager.getInstance().getAllProject());
		} catch (Exception e) {
		}

		MyGroupLayout layout7 = new MyGroupLayout(panelTabProject);
		layout7.add(0, 0, lblOrganization);
		layout7.add(0, 1, cbOrganization7);
		layout7.add(1, 0, lblLabel1);
		layout7.add(1, 1, txtProject);
		layout7.updateGui();
		
		
		panelMain.actionTabChangeListener();

		// Add các PANEL vào TABPANEL
		panelMain.addTabPanel("Phòng ban", panelTabDepartment);
		panelMain.addTabPanel("Sản phẩm", panelTabProduct);
		panelMain.addTabPanel("Khu vực", panelTabLocation);
		panelMain.addTabPanel("Khách hàng", panelTabPartner);
		panelMain.addTabPanel("Nhân viên", panelTabCashier);
		panelMain.addTabPanel("Hóa đơn", panelTabShop);
		panelMain.addTabPanel("Dự án", panelTabProject);

		if (Toolkit.getDefaultToolkit().getScreenSize().width <= 1024)
			panelMain.setPreferredSize(new Dimension(500, 130));
		else
			panelMain.setPreferredSize(new Dimension(700, 130));

		return panelMain;
	}
	
	private JPanel PanelThongKeTrai_Type4(TypeReport nameReport) {
		PANELTOPLEFT panelMain = new PANELTOPLEFT();
		
		// TAB Kế toán
		JPanel panelTabAccounting = new JPanel();
		panelTabAccounting.setOpaque(false);
		panelTabAccounting.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 20));

		lblOrganization = new ExtendJLabel("Doanh nghiệp");
		cbOrganization1 = new ExtendJComboBox();
		cbOrganization1.setEnabled(false);
		cbOrganization1.addItem(nameOrganization);

		MyGroupLayout layout8 = new MyGroupLayout(panelTabAccounting);
		layout8.add(0, 0, lblOrganization);
		layout8.add(0, 1, cbOrganization1);
		layout8.updateGui();
		
//		panelMain.actionTabChangeListener();
		
		panelMain.addTabPanel("Kế Toán", panelTabAccounting);		
	
		if (Toolkit.getDefaultToolkit().getScreenSize().width <= 1024)
			panelMain.setPreferredSize(new Dimension(500, 130));
		else
			panelMain.setPreferredSize(new Dimension(700, 130));	
	
		return panelMain;
	}

	/**
	 * Giao diện chung sử dụng cho: [Thống kê bán hàng] && [Thống kê mua hàng] &&
	 * [Thống kê kho hàng hóa] && [Thống kê định lượng]
	 * 
	 * @parameter: activeType = Receipt || Payment
	 */
	private JPanel PanelThongKeTrai_Type2(TypeReport typeReport) {

		PANELTOPLEFT panelMain = new PANELTOPLEFT();

		// TAB tìm kiếm
		JPanel panelTabSearch = new JPanel();
		panelTabSearch.setOpaque(false);
		panelTabSearch.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 20));

		lblOrganization = new ExtendJLabel("Doanh nghiệp");
		lblLabel1 = new ExtendJLabel("Tìm kiếm");
		lblLabel2 = new ExtendJLabel("Theo cột");
		ExtendJLabel lblShowAll = new ExtendJLabel("Hiển thị");

		txtSearch = new ExtendJTextField(); // TextBox tìm kiếm
		cbOrganization2 = new ExtendJComboBox(); // ComboBox tìm kiếm theo cột
		cbOrganization1 = new ExtendJComboBox();
		cbOrganization1.setEnabled(false);
		cbOrganization1.addItem(nameOrganization);

		JTable tableView = new JTable();
		if (typeReport.equals(TypeReport.TKBanHang) || typeReport.equals(TypeReport.TKMuaHang))
			tableView.setModel(new TableModelReportSales(typeReport, new ArrayList<ReportSalesEntity>()));
		else if (typeReport.equals(TypeReport.TKTonKho) || typeReport.equals(TypeReport.TKDinhLuong))
			tableView.setModel(new TableModelReportInventory());
		else
			tableView.setModel(new DefaultTableModel());
		this.tableStatic = tableView;
		tableFillterSorter = new TableFillterSorter(tableView);
		tableFillterSorter.createTableSorter();
		tableFillterSorter.createTableFillter();
		cbOrganization2.setModel(new DefaultComboBoxModel(tableFillterSorter.getHashtable().keySet().toArray()));
		cbOrganization2.setSelectedItem("Danh mục");

		MyGroupLayout layout1 = new MyGroupLayout(panelTabSearch);

		layout1.add(0, 0, lblOrganization);
		layout1.add(0, 1, cbOrganization1);
		layout1.add(0, 2, lblShowAll);
		layout1.add(0, 3, chbShowAll);
		layout1.add(1, 0, lblLabel1);
		layout1.add(1, 1, txtSearch);
		layout1.add(1, 2, lblLabel2);
		layout1.add(1, 3, cbOrganization2);
		layout1.updateGui();

		panelMain.addTabPanel("Tìm kiếm", panelTabSearch);

		txtSearch.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent evt) {
				txtSearchCaretUpdate(evt);
			}
		});

		cbOrganization2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				cbSearchItemStateChanged(evt);
			}
		});

		chbShowAll.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				if (chbShowAll.isSelected()) {
					Profile profileCheck = AccountModelManager.getInstance().getProfileConfigAdmin();
					profileCheck.put("rememberShowAll", "selected");
					AccountModelManager.getInstance().saveProfileConfig(
					    ManagerAuthenticate.getInstance().getOrganizationLoginId(), profileCheck);
				} else {
					if (profileRememberShowAll != null) {
						profileRememberShowAll.remove("rememberShowAll");
						AccountModelManager.getInstance().saveProfileConfig(
						    ManagerAuthenticate.getInstance().getOrganizationLoginId(), profileRememberShowAll);
					}
				}

			}
		});

		if (Toolkit.getDefaultToolkit().getScreenSize().width <= 1024)
			panelMain.setPreferredSize(new Dimension(500, 130));
		else
			panelMain.setPreferredSize(new Dimension(700, 130));

		return panelMain;
	}

	// Panel thêm các component con
	protected class PanelContainer extends JPanel {
		public PanelContainer(Component comp1, Component comp2) {
			this.setLayout(new BorderLayout(0, 5));
			this.setOpaque(false);
			this.add(comp1, BorderLayout.CENTER);
			comp1.setPreferredSize(new Dimension(95, 22));
			this.add(comp2, BorderLayout.LINE_END);
			comp2.setPreferredSize(new Dimension(95, 22));
		}
	}

	/*
	 * Giao diện thống kê sử dụng cho thống kê nhân viên dự án
	 */
	private Component PanelThongKeTrai_Type3() { // Designer by Bui Trong Hieu
		PANELTOPLEFT panelMain = new PANELTOPLEFT();
		JCheckBox checkBox;
		// TAB DỰ ÁN
		JPanel panelTabProject = new JPanel();
		panelTabProject.setOpaque(false);
		panelTabProject.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 20));

		lblOrganization = new ExtendJLabel("Doanh nghiệp");
		lblLabel1 = new ExtendJLabel("Dự án");
		cbOrganization2 = new ExtendJComboBox();
		checkBox = new JCheckBox("Tính NXNB", false);
		checkBox.setOpaque(false);
		cbOrganization2.setEnabled(false);
		cbOrganization2.addItem(nameOrganization);
		txtProject = new TextPopup<Project>(Project.class);
		try {
			txtProject.setData(RestaurantModelManager.getInstance().getAllProject());
		} catch (Exception e2) {
		}
		ketxuat2 = new JButton("Kết xuất");
		ketxuat2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < table.getRowCount(); i++) {
					try {
						table.getCellEditor(i, table.getColumnCount() - 1).stopCellEditing();
						if (table.getValueAt(i, table.getRowCount() - 1).toString().equals("Trả thưởng")) {
							PanelPaymentReceipt panel = new PanelPaymentReceipt(ActivityType.Payment);
							panel.setName("PhieuChiTien");
							panel.setTraThuong(MyDouble.valueOf(table.getValueAt(i, 2).toString()).toString(),
							    "Trả thưởng cho nhân viên", true);
							DialogMain dialog = new DialogMain(panel);
							dialog.getBtnExt().setVisible(true);
							dialog.getBtnExt().addActionListener(panel.getActionListener());
							dialog.setIcon("invoice1.png");
							dialog.setName("dlPhieuThuTien");
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Phiếu chi tiền");
							dialog.setVisible(true);
							table.clearSelection();
						}

					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		checkBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					isCheck = true;
				} else {
					isCheck = false;
				}

			}
		});
		PanelContainer pc = new PanelContainer(txtProject, checkBox);
		MyGroupLayout layout2 = new MyGroupLayout(panelTabProject);
		layout2.add(0, 0, lblOrganization);
		layout2.add(0, 1, cbOrganization2);
		layout2.add(1, 0, lblLabel1);
		layout2.add(1, 1, pc);
		layout2.add(1, 2, ketxuat2);
		layout2.updateGui();

		// TAB nhân viên
		JPanel panelProjectMember = new JPanel();
		panelProjectMember.setOpaque(false);
		panelProjectMember.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 20));

		lblOrganization = new ExtendJLabel("Doanh nghiệp");
		lblLabel1 = new ExtendJLabel("Phòng ban");
		lblLabel2 = new ExtendJLabel("Nhân viên");
		cbOrganization3 = new ExtendJComboBox();
		txtEmployee = new TextPopup<Employee>(Employee.class);
		try {
			txtEmployee.setData(HRModelManager.getInstance().getEmployees());
		} catch (Exception e) {
		}
		cbOrganization3.addItem(nameOrganization);
		try {
			List<Employee> employees = new ArrayList<Employee>();
			List<Project> projects = RestaurantModelManager.getInstance().getAllProject();
			for (Project project : projects) {
				List<ProjectMember> members = project.getProjectMembers();
				for (ProjectMember prMember : members) {
					Employee employee = HRModelManager.getInstance().getEmployeeByCode(prMember.getEmployeeCode());
					employees.add(employee);
				}

			}
			txtEmployee.setData(employees);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ketxuat3 = new JButton("Kết xuất");

		ketxuat3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < table.getRowCount(); i++) {
					try {
						table.getCellEditor(i, table.getColumnCount() - 1).stopCellEditing();
						if (table.getValueAt(i, table.getRowCount() - 1).toString().equals("Trả thưởng")) {
							PanelPaymentReceipt panel = new PanelPaymentReceipt(ActivityType.Payment);
							panel.setName("PhieuChiTien");
							panel.setTraThuong(MyDouble.valueOf(table.getValueAt(i, 2).toString()).toString(),
							    "Trả thưởng cho nhân viên", true);
							DialogMain dialog = new DialogMain(panel);
							dialog.getBtnExt().setVisible(true);
							dialog.getBtnExt().addActionListener(panel.getActionListener());
							dialog.setIcon("invoice1.png");
							dialog.setName("dlPhieuThuTien");
							dialog.setLocationRelativeTo(null);
							dialog.setTitle("Phiếu chi tiền");
							dialog.setVisible(true);
							table.clearSelection();
						}

					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});

		MyGroupLayout layout3 = new MyGroupLayout(panelProjectMember);
		layout3.add(0, 0, lblOrganization);
		layout3.add(0, 1, cbOrganization3);
		layout3.add(1, 0, lblLabel2);
		layout3.add(1, 1, txtEmployee);
		layout3.add(1, 2, ketxuat3);
		layout3.updateGui();

		panelMain.actionTabChangeListenerProjectMember();

		// Add các PANEL vào TABPANEL
		panelMain.addTabPanel("Dự án", panelTabProject);
		panelMain.addTabPanel("Nhân viên", panelProjectMember);

		if (Toolkit.getDefaultToolkit().getScreenSize().width <= 1024)
			panelMain.setPreferredSize(new Dimension(500, 130));
		else
			panelMain.setPreferredSize(new Dimension(700, 130));

		return panelMain;
	}

	/**
	 * Giao diện chung sử dụng cho: [Thống kê thu chi] && [Thống kê bán hàng] &&
	 * [Thống kê mua hàng] && [Thống kê kho hàng hóa] && [Thống kê định lượng]
	 * 
	 * @param report
	 * @param activityType
	 * @param isInventory
	 * @return
	 */
	private JPanel PanelThongKePhai(final TypeReport report, final ActivityType activityType, final boolean isInventory) {
		// Khởi tạo mảng dữ liệu dùng cho models
		byDay[0] = "Hôm nay";
		byDay[1] = "Hôm qua";
		byDay[2] = "Hôm kia";
		byDay[3] = "Hôm khác";

		byMonth[0] = "Tháng này";
		byMonth[1] = "Tháng trước";
		byMonth[2] = "Tháng kia";
		byMonth[3] = "Tháng khác";

		byYear[0] = "Năm nay";
		byYear[1] = "Năm trước";
		byYear[2] = "Năm kia";
		byYear[3] = "Năm khác";

		byType[0] = "Ngày";
		byType[1] = "Tháng";
		byType[2] = "Năm";
		byType[3] = "Khác";

		byValueMoney[0] = "Đơn vị";
		byValueMoney[1] = "Nghìn";
		byValueMoney[2] = "Triệu";

		onMonth[0] = "01";
		onMonth[1] = "02";
		onMonth[2] = "03";
		onMonth[3] = "04";
		onMonth[4] = "05";
		onMonth[5] = "06";
		onMonth[6] = "07";
		onMonth[7] = "08";
		onMonth[8] = "09";
		onMonth[9] = "10";
		onMonth[10] = "11";
		onMonth[11] = "12";

		try {
			String lessYearOnInvoice = AccountingModelManager.getInstance().getStartDateLessYear();
			int intervalYear;
			if (lessYearOnInvoice == null || lessYearOnInvoice.isEmpty() || lessYearOnInvoice.equals(""))
				intervalYear = 1;
			else
				intervalYear = yearNow - Integer.parseInt(lessYearOnInvoice) + 1;
			onYear = new String[intervalYear];
			for (int i = 0; i < intervalYear; i++) {
				onYear[i] = Integer.toString(yearNow);
				yearNow--;
			}
		} catch (Exception e1) {
			onYear = new String[5];
			onYear[0] = Integer.toString(yearNow);
			onYear[1] = Integer.toString(yearNow - 1);
			onYear[2] = Integer.toString(yearNow - 2);
			onYear[3] = Integer.toString(yearNow - 3);
			onYear[4] = Integer.toString(yearNow - 4);
		}

		// Khởi tạo và sắp xếp các components
		PANELTOPRIGHT panelMain = new PANELTOPRIGHT();
		processing = new Processing();

		cbTypeReport = new ExtendJComboBox();
		cbTypeReport.setFont(new Font("Tahoma", Font.BOLD, 14));
		cbFilterByTime = new ExtendJComboBox();
		cbFilterByType = new ExtendJComboBox();
		cbUnitMoney = new UnitMoneyJComboBox();
		cbUnitMoney.setEnabled(false);
		cbValueMoney = new ExtendJComboBox();

		dcDay1 = new MyJDateChooser();
		dcDay2 = new MyJDateChooser();
		cbMonth = new ExtendJComboBox();
		cbYear = new ExtendJComboBox();
		dcDay2.setDate(null);
		dcDay1.setDate(null);
		dcDay1.setEnabled(false);
		dcDay2.setEnabled(false);
		cbMonth.setEnabled(false);
		cbYear.setEnabled(false);

		dcDay2.setVisible(false);
		cbMonth.setVisible(false);

		// Thiết lập các models dữ liệu cho comboBox
		if (!report.equals(TypeReport.TKThuChi) && !report.equals(TypeReport.TKKeToan)) {
			lblTypeReport = new ExtendJLabel("Kho hàng");
			List<Warehouse> warehouses = new ArrayList<Warehouse>();
			try {
				List<Warehouse> ware = WarehouseModelManager.getInstance().findWarehouses();
				if (warehouses != null) {
					warehouses.addAll(ware);
					warehouses.add(0, null);
				}
			} catch (Exception e) {
			}
			DefaultComboBoxModel<Warehouse> modelTypeReport = new DefaultComboBoxModel(warehouses.toArray());
			cbTypeReport.setModel(modelTypeReport);
		} else {
			byTypeReport[0] = "Chi phí";
			byTypeReport[1] = "Doanh thu";
			byTypeReport[2] = "Thu - Chi - Lãi";
			byTypeReport[3] = "Thu - Chi - Công nợ";
			DefaultComboBoxModel<String> modelTypeReport = new DefaultComboBoxModel<String>(byTypeReport);
			cbTypeReport.setModel(modelTypeReport);
			cbTypeReport.setSelectedIndex(2);
		}
		DefaultComboBoxModel<String> modelValueMoney = new DefaultComboBoxModel<String>(byValueMoney);
		cbValueMoney.setModel(modelValueMoney);
		//cbValueMoney.setSelectedIndex(2);
		DefaultComboBoxModel<String> modelByTime = new DefaultComboBoxModel<String>(byDay);
		cbFilterByTime.setModel(modelByTime);
		DefaultComboBoxModel<String> modelByType = new DefaultComboBoxModel<String>(byType);
		cbFilterByType.setModel(modelByType);
		//cbFilterByType.setSelectedIndex(1);

		btnView = new ExtendJButton("Xem thống kê");
		btnView.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));

		JPanelLayout panel1 = new JPanelLayout(cbFilterByType, cbFilterByTime);
		final JPanelLayout jPanel1Border = new JPanelLayout(dcDay1, BorderLayout.CENTER, dcDay2, BorderLayout.LINE_END);
		final JPanelLayout jPanel2Border = new JPanelLayout(cbMonth, BorderLayout.LINE_START, cbYear, BorderLayout.CENTER);
		JPanelLayout panel2 = new JPanelLayout(jPanel1Border, jPanel2Border);
		JPanelLayout jPanel1Grid = new JPanelLayout(cbValueMoney, cbUnitMoney);
		JPanelLayout panel3 = new JPanelLayout(jPanel1Grid, btnView);

		if (!report.equals(TypeReport.TKKeToan)){
			panelMain.addRow(lblTypeReport, cbTypeReport);
		}
		panelMain.addRow(lblFilterByType, panel1);
		panelMain.addRow(lblFilterByTime, panel2);
		panelMain.addRow(lblValueMoney, panel3);
		if (profileRememberShowAll.get("valueIndex") != null
				&& profileRememberShowAll.get("valueIndex").equals(0)) {
			cbValueMoney.setSelectedIndex(0);
		} else {
			if (profileRememberShowAll.get("valueIndex") != null
					&& profileRememberShowAll.get("valueIndex").equals(1)) {
				cbValueMoney.setSelectedIndex(1);
			} else {
				cbValueMoney.setSelectedIndex(2);
			}
		}
		
		cbValueMoney.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				profileRememberShowAll.put("valueIndex", cbValueMoney.getSelectedIndex());
				AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(),
				    profileRememberShowAll);

			}
		});
   
		/*
		 * Các sự kiện cho components
		 */

		btnView.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					processing.setVisible(true);
					new SwingWorker<Void, Void>() {
						@Override
						protected Void doInBackground() throws Exception {
							// Tính thời gian query
							long startTime = System.currentTimeMillis();
//							if (report.equals(TypeReport.TKKeToan)) {
//								btnView_EventHandlerThongKeKeToan(e);
//							}
							if (report.equals(TypeReport.TKKeToan)){
								btnView_EventHandlerThongKeKeToan(e);
							}
							if (!report.equals(TypeReport.TKThuChi)) {
								if (activityType != null) {
									e.setSource(activityType);
									btnView_EventHandlerThongKeMuaBanHang(e);
								} else {
									e.setSource(isInventory);
									btnView_EventHandlerThongKeTonKho_DinhLuong(e);
								}

							} else {
								btnView_EventHandlerThongKeThuChi(e);
							}

							// Kết quả thời gian query:
							long endTime = System.currentTimeMillis();
							NumberFormat formatter = new DecimalFormat("#0.00000");
							String time = formatter.format((endTime - startTime) / 1000d);
							System.out.println("###$$$ TIME RUN QUERY: " + time);

							return null;
						};

						@Override
						protected void done() {
							processing.setVisible(false);
						};
					}.execute();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Quá trình thao tác phát sinh lỗi!" + ex.toString());
				}
			}
		});

		// ComboBox lọc điều kiện theo ngày - tháng - năm - khác
		cbFilterByType.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				profileRememberShowAll.put("valueIndexDay", cbFilterByType.getSelectedIndex());
				AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(),
				    profileRememberShowAll);
				DefaultComboBoxModel<String> modelByTime;
				// Nếu ngày => setModel comboBox cbFilterByTime hiển thị hôm nay - hôm
				// qua - ..
				if (cbFilterByType.getSelectedIndex() == 0) {
					modelByTime = new DefaultComboBoxModel<String>(byDay);
				} else {
					// Nếu tháng => setModel comboBox cbFilterByTime hiển thị tháng này -
					// tháng trước - ..
					if (cbFilterByType.getSelectedIndex() == 1) {
						modelByTime = new DefaultComboBoxModel<String>(byMonth);
					} else {
						// Nếu năm => setModel comboBox cbFilterByTime hiển thị năm nay -
						// năm trước - ...
						if (cbFilterByType.getSelectedIndex() == 2)
							modelByTime = new DefaultComboBoxModel<String>(byYear);
						else {
							String[] otherTime = { "", "", "", " " };
							modelByTime = new DefaultComboBoxModel<String>(otherTime);
						}
					}
				}

				cbFilterByTime.setModel(modelByTime);

				if (cbFilterByType.getSelectedIndex() == 3) {
					cbFilterByTime.setSelectedIndex(3);
					cbFilterByTime.setEnabled(false);
				} else {
					cbFilterByTime.setEnabled(true);

					cbMonth.setModel(new DefaultComboBoxModel<String>());
					cbYear.setModel(new DefaultComboBoxModel<String>());
					cbMonth.setEnabled(false);
					cbYear.setEnabled(false);

					dcDay2.setVisible(false);
					cbMonth.setVisible(false);

					dcDay1.setEnabled(false);
					dcDay2.setEnabled(false);
					dcDay1.setDate(null);
					dcDay2.setDate(null);
				}
			}
		});
		if (profileRememberShowAll.get("valueIndexDay") != null
				&& profileRememberShowAll.get("valueIndexDay").equals(0)) {
			cbFilterByType.setSelectedIndex(0);
		} else {
			if (profileRememberShowAll.get("valueIndexDay") != null
					&& profileRememberShowAll.get("valueIndexDay").equals(1)) {
				cbFilterByType.setSelectedIndex(1);
			} else {
				if (profileRememberShowAll.get("valueIndexDay") != null
						&& profileRememberShowAll.get("valueIndexDay").equals(2)) {
					cbFilterByType.setSelectedIndex(2);
				}else{
					cbFilterByType.setSelectedIndex(3);
				}
				
			}
		}
		// ComboBox lọc điều kiện theo thời gian nay - qua - kia - khác
		cbFilterByTime.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// Nếu chon comboBox cbFilterByTime: Khác
				if (cbFilterByTime.getSelectedIndex() == 3) {
					// Nếu chọn comboBox cbFilterByType: Ngày
					if (cbFilterByType.getSelectedIndex() == 0) {
						dcDay1.setEnabled(true);
						dcDay2.setEnabled(false);
						dcDay1.setDate(new Date());
						dcDay2.setDate(null);
					}
					// Nếu chọn comboBox cbFilterByType: Tháng
					if (cbFilterByType.getSelectedIndex() == 1) {
						cbMonth.setVisible(true);
						cbMonth.setPreferredSize(new Dimension(jPanel2Border.getWidth() / 2, cbMonth.getHeight()));

						DefaultComboBoxModel<String> modelOnMonth = new DefaultComboBoxModel<String>(onMonth);
						cbMonth.setModel(modelOnMonth);
						DefaultComboBoxModel<String> modelOnYear = new DefaultComboBoxModel<String>(onYear);
						cbYear.setModel(modelOnYear);
						cbMonth.setEnabled(true);
						cbYear.setEnabled(true);

						dcDay1.setEnabled(false);
						dcDay2.setEnabled(false);
						dcDay1.setDate(null);
						dcDay2.setDate(null);
					}
					// Nếu chọn comboBox cbFilterByType: Năm
					if (cbFilterByType.getSelectedIndex() == 2) {
						DefaultComboBoxModel<String> modelOnYear = new DefaultComboBoxModel<String>(onYear);
						cbYear.setModel(modelOnYear);
						cbMonth.setModel(new DefaultComboBoxModel<String>());
						cbMonth.setEnabled(false);
						cbYear.setEnabled(true);

						dcDay1.setEnabled(false);
						dcDay2.setEnabled(false);
						dcDay1.setDate(null);
						dcDay2.setDate(null);
					}
					// Nếu chọn comboBox cbFilterByType: Khác
					if (cbFilterByType.getSelectedIndex() == 3) {
						dcDay2.setVisible(true);
						dcDay2.setPreferredSize(new Dimension(jPanel1Border.getWidth() / 2, dcDay2.getHeight()));

						dcDay1.setEnabled(true);
						dcDay2.setEnabled(true);
						dcDay1.setDate(new Date());
						dcDay2.setDate(new Date());

						cbMonth.setEnabled(false);
						cbYear.setEnabled(false);
						cbMonth.setModel(new DefaultComboBoxModel<String>());
						cbYear.setModel(new DefaultComboBoxModel<String>());
					}
				} else {
					dcDay2.setVisible(false);
					cbMonth.setVisible(false);

					cbMonth.setModel(new DefaultComboBoxModel<String>());
					cbYear.setModel(new DefaultComboBoxModel<String>());
					cbMonth.setEnabled(false);
					cbYear.setEnabled(false);

					dcDay2.setDate(null);
					dcDay1.setDate(null);
					dcDay1.setEnabled(false);
					dcDay2.setEnabled(false);
				}
			}
		});

		if (report.equals(TypeReport.TKThuChi)) {
			cbTypeReport.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {

					if (cbTypeReport.getSelectedItem().toString().equals("Chi phí")) {
						tabbedPane.setEnabledAt(1, true);
						btnView.setEnabled(true);
					}

					if (cbTypeReport.getSelectedItem().toString().equals("Doanh thu")) {
						tabbedPane.setEnabledAt(1, true);
						btnView.setEnabled(true);
					}

					if (cbTypeReport.getSelectedItem().toString().equals("Thu - Chi - Lãi")) {
						tabbedPane.setEnabledAt(1, true);
						btnView.setEnabled(true);
					}

					if (cbTypeReport.getSelectedItem().toString().equals("Thu - Chi - Công nợ")) {
						tabbedPane.setEnabledAt(1, false);
						if (tabbedPane.getSelectedIndex() == 1) {
							btnView.setEnabled(false);
						}
					}
				}
			});
		}

		cbTypeReport.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					PanelWarehouse add;
					try {
						add = new PanelWarehouse();
						add.setName("ThemMoiKho");
						DialogMain dialog = new DialogMain(add);
						dialog.setIcon("soche1.png");
						dialog.setName("dlThemMoiKho");
						dialog.setTitle("Tạo kho hàng");
						dialog.setResizable(false);
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (cbTypeReport.getSelectedIndex() == 3) {
					if (tabbedPane.getSelectedIndex() == 2 || tabbedPane.getSelectedIndex() == 0
					    || tabbedPane.getSelectedIndex() == 3 || tabbedPane.getSelectedIndex() == 4
					    || tabbedPane.getSelectedIndex() == 5) {
						btnView.setEnabled(true);
					} else
						btnView.setEnabled(true);
				}
			}
		});

		if (Toolkit.getDefaultToolkit().getScreenSize().width <= 1024)
			panelMain.setPreferredSize(new Dimension(350, 130));
		else
			panelMain.setPreferredSize(new Dimension(250, 130));

		return panelMain;
	}

	/**
	 * Giai diện riêng cho [ProjectMember]
	 * */
	private JPanel PanelThongKeNhanVienPhai() {
		// Khởi tạo mảng dữ liệu dùng cho models
		byDay[0] = "Hôm nay";
		byDay[1] = "Hôm qua";
		byDay[2] = "Hôm kia";
		byDay[3] = "Hôm khác";

		byMonth[0] = "Tháng này";
		byMonth[1] = "Tháng trước";
		byMonth[2] = "Tháng kia";
		byMonth[3] = "Tháng khác";

		byYear[0] = "Năm nay";
		byYear[1] = "Năm trước";
		byYear[2] = "Năm kia";
		byYear[3] = "Năm khác";

		byType[0] = "Ngày";
		byType[1] = "Tháng";
		byType[2] = "Năm";
		byType[3] = "Khác";

		byValueMoney[0] = "Đơn vị";
		byValueMoney[1] = "Nghìn";
		byValueMoney[2] = "Triệu";

		onMonth[0] = "01";
		onMonth[1] = "02";
		onMonth[2] = "03";
		onMonth[3] = "04";
		onMonth[4] = "05";
		onMonth[5] = "06";
		onMonth[6] = "07";
		onMonth[7] = "08";
		onMonth[8] = "09";
		onMonth[9] = "10";
		onMonth[10] = "11";
		onMonth[11] = "12";

		try {
			String lessYearOnInvoice = AccountingModelManager.getInstance().getStartDateLessYear();
			int intervalYear;
			if (lessYearOnInvoice == null || lessYearOnInvoice.isEmpty() || lessYearOnInvoice.equals(""))
				intervalYear = 1;
			else
				intervalYear = yearNow - Integer.parseInt(lessYearOnInvoice) + 1;
			onYear = new String[intervalYear];
			for (int i = 0; i < intervalYear; i++) {
				onYear[i] = Integer.toString(yearNow);
				yearNow--;
			}
		} catch (Exception e1) {
			onYear = new String[5];
			onYear[0] = Integer.toString(yearNow);
			onYear[1] = Integer.toString(yearNow - 1);
			onYear[2] = Integer.toString(yearNow - 2);
			onYear[3] = Integer.toString(yearNow - 3);
			onYear[4] = Integer.toString(yearNow - 4);
		}

		// Khởi tạo và sắp xếp các components
		PANELTOPRIGHT panelMain = new PANELTOPRIGHT();
		processing = new Processing();

		cbTypeReport = new ExtendJComboBox();
		cbTypeReport.setFont(new Font("Tahoma", Font.BOLD, 14));
		cbFilterByTime = new ExtendJComboBox();
		cbFilterByType = new ExtendJComboBox();
		cbUnitMoney = new UnitMoneyJComboBox();
		cbUnitMoney.setEnabled(false);
		cbValueMoney = new ExtendJComboBox();

		dcDay1 = new MyJDateChooser();
		dcDay2 = new MyJDateChooser();
		cbMonth = new ExtendJComboBox();
		cbYear = new ExtendJComboBox();
		dcDay2.setDate(null);
		dcDay1.setDate(null);
		dcDay1.setEnabled(false);
		dcDay2.setEnabled(false);
		cbMonth.setEnabled(false);
		cbYear.setEnabled(false);

		dcDay2.setVisible(false);
		cbMonth.setVisible(false);

		// Thiết lập các models dữ liệu cho comboBox
		byTypeReport[0] = "Chi phí";
		byTypeReport[1] = "Doanh thu";
		byTypeReport[2] = "Doanh thu thuần";
		byTypeReport[3] = "Thưởng";
		DefaultComboBoxModel<String> modelTypeReport = new DefaultComboBoxModel<String>(byTypeReport);
		cbTypeReport.setModel(modelTypeReport);
		cbTypeReport.setSelectedIndex(2);
		DefaultComboBoxModel<String> modelValueMoney = new DefaultComboBoxModel<String>(byValueMoney);
		cbValueMoney.setModel(modelValueMoney);
		// cbValueMoney.setSelectedIndex(2);
		DefaultComboBoxModel<String> modelByTime = new DefaultComboBoxModel<String>(byDay);
		cbFilterByTime.setModel(modelByTime);
		DefaultComboBoxModel<String> modelByType = new DefaultComboBoxModel<String>(byType);
		cbFilterByType.setModel(modelByType);
	//	cbFilterByType.setSelectedIndex(1);

		btnView = new ExtendJButton("Xem thống kê");
		btnView.addMouseListener(new MouseEventClickButtonDialogPlus(null, null, null));

		JPanelLayout panel1 = new JPanelLayout(cbFilterByType, cbFilterByTime);
		final JPanelLayout jPanel1Border = new JPanelLayout(dcDay1, BorderLayout.CENTER, dcDay2, BorderLayout.LINE_END);
		final JPanelLayout jPanel2Border = new JPanelLayout(cbMonth, BorderLayout.LINE_START, cbYear, BorderLayout.CENTER);
		JPanelLayout panel2 = new JPanelLayout(jPanel1Border, jPanel2Border);
		JPanelLayout jPanel1Grid = new JPanelLayout(cbValueMoney, cbUnitMoney);
		JPanelLayout panel3 = new JPanelLayout(jPanel1Grid, btnView);

		panelMain.addRow(lblTypeReport, cbTypeReport);
		panelMain.addRow(lblFilterByType, panel1);
		panelMain.addRow(lblFilterByTime, panel2);
		panelMain.addRow(lblValueMoney, panel3);
		
		if (profileRememberShowAll.get("valueIndex") != null
				&& profileRememberShowAll.get("valueIndex").equals(0)) {
			cbValueMoney.setSelectedIndex(0);
		} else {
			if (profileRememberShowAll.get("valueIndex") != null
					&& profileRememberShowAll.get("valueIndex").equals(1)) {
				cbValueMoney.setSelectedIndex(1);
			} else {
				cbValueMoney.setSelectedIndex(2);
			}
		}
		
		cbValueMoney.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				profileRememberShowAll.put("valueIndex", cbValueMoney.getSelectedIndex());
				AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(),
				    profileRememberShowAll);

			}
		});
   
		/*
		 * Các sự kiện cho components
		 */

		btnView.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					processing.setVisible(true);
					new SwingWorker<Void, Void>() {
						@Override
						protected Void doInBackground() throws Exception {
							// Tính thời gian query
							long startTime = System.currentTimeMillis();
							btnView_EventHandlerThongKeNhanVien(e);
							// Kết quả thời gian query:
							long endTime = System.currentTimeMillis();
							NumberFormat formatter = new DecimalFormat("#0.00000");
							String time = formatter.format((endTime - startTime) / 1000d);
							System.out.println("###$$$ TIME RUN QUERY: " + time);
							return null;
						};

						@Override
						protected void done() {
							processing.setVisible(false);
						};
					}.execute();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Quá trình thao tác phát sinh lỗi!" + ex.toString());
				}
			}
		});

		// ComboBox lọc điều kiện theo ngày - tháng - năm - khác
		cbFilterByType.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				profileRememberShowAll.put("valueIndexDay", cbFilterByType.getSelectedIndex());
				AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(),
				    profileRememberShowAll);
				DefaultComboBoxModel<String> modelByTime;
				// Nếu ngày => setModel comboBox cbFilterByTime hiển thị hôm nay - hôm
				// qua - ..
				if (cbFilterByType.getSelectedIndex() == 0) {
					modelByTime = new DefaultComboBoxModel<String>(byDay);
				} else {
					// Nếu tháng => setModel comboBox cbFilterByTime hiển thị tháng này -
					// tháng trước - ..
					if (cbFilterByType.getSelectedIndex() == 1) {
						modelByTime = new DefaultComboBoxModel<String>(byMonth);
					} else {
						// Nếu năm => setModel comboBox cbFilterByTime hiển thị năm nay -
						// năm trước - ...
						if (cbFilterByType.getSelectedIndex() == 2)
							modelByTime = new DefaultComboBoxModel<String>(byYear);
						else {
							String[] otherTime = { "", "", "", " " };
							modelByTime = new DefaultComboBoxModel<String>(otherTime);
						}
					}
				}

				cbFilterByTime.setModel(modelByTime);

				if (cbFilterByType.getSelectedIndex() == 3) {
					cbFilterByTime.setSelectedIndex(3);
					cbFilterByTime.setEnabled(false);
				} else {
					cbFilterByTime.setEnabled(true);

					cbMonth.setModel(new DefaultComboBoxModel<String>());
					cbYear.setModel(new DefaultComboBoxModel<String>());
					cbMonth.setEnabled(false);
					cbYear.setEnabled(false);

					dcDay2.setVisible(false);
					cbMonth.setVisible(false);

					dcDay1.setEnabled(false);
					dcDay2.setEnabled(false);
					dcDay1.setDate(null);
					dcDay2.setDate(null);
				}
			}
		});
		// ComboBox lọc điều kiện theo ngày - tháng - năm - khác
		cbFilterByType.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				profileRememberShowAll.put("valueIndexDay", cbFilterByType.getSelectedIndex());
				AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(),
				    profileRememberShowAll);
				DefaultComboBoxModel<String> modelByTime;
				// Nếu ngày => setModel comboBox cbFilterByTime hiển thị hôm nay - hôm
				// qua - ..
				if (cbFilterByType.getSelectedIndex() == 0) {
					modelByTime = new DefaultComboBoxModel<String>(byDay);
				} else {
					// Nếu tháng => setModel comboBox cbFilterByTime hiển thị tháng này -
					// tháng trước - ..
					if (cbFilterByType.getSelectedIndex() == 1) {
						modelByTime = new DefaultComboBoxModel<String>(byMonth);
					} else {
						// Nếu năm => setModel comboBox cbFilterByTime hiển thị năm nay -
						// năm trước - ...
						if (cbFilterByType.getSelectedIndex() == 2)
							modelByTime = new DefaultComboBoxModel<String>(byYear);
						else {
							String[] otherTime = { "", "", "", " " };
							modelByTime = new DefaultComboBoxModel<String>(otherTime);
						}
					}
				}

				cbFilterByTime.setModel(modelByTime);

				if (cbFilterByType.getSelectedIndex() == 3) {
					cbFilterByTime.setSelectedIndex(3);
					cbFilterByTime.setEnabled(false);
				} else {
					cbFilterByTime.setEnabled(true);

					cbMonth.setModel(new DefaultComboBoxModel<String>());
					cbYear.setModel(new DefaultComboBoxModel<String>());
					cbMonth.setEnabled(false);
					cbYear.setEnabled(false);

					dcDay2.setVisible(false);
					cbMonth.setVisible(false);

					dcDay1.setEnabled(false);
					dcDay2.setEnabled(false);
					dcDay1.setDate(null);
					dcDay2.setDate(null);
				}
			}
		});
		if (profileRememberShowAll.get("valueIndexDay") != null
				&& profileRememberShowAll.get("valueIndexDay").equals(0)) {
			cbFilterByType.setSelectedIndex(0);
		} else {
			if (profileRememberShowAll.get("valueIndexDay") != null
					&& profileRememberShowAll.get("valueIndexDay").equals(1)) {
				cbFilterByType.setSelectedIndex(1);
			} else {
				if (profileRememberShowAll.get("valueIndexDay") != null
						&& profileRememberShowAll.get("valueIndexDay").equals(2)) {
					cbFilterByType.setSelectedIndex(2);
				}else{
					cbFilterByType.setSelectedIndex(3);
				}
				
			}
		}
		// ComboBox lọc điều kiện theo thời gian nay - qua - kia - khác
		cbFilterByTime.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// Nếu chon comboBox cbFilterByTime: Khác
				if (cbFilterByTime.getSelectedIndex() == 3) {
					// Nếu chọn comboBox cbFilterByType: Ngày
					if (cbFilterByType.getSelectedIndex() == 0) {
						dcDay1.setEnabled(true);
						dcDay2.setEnabled(false);
						dcDay1.setDate(new Date());
						dcDay2.setDate(null);
					}
					// Nếu chọn comboBox cbFilterByType: Tháng
					if (cbFilterByType.getSelectedIndex() == 1) {
						cbMonth.setVisible(true);
						cbMonth.setPreferredSize(new Dimension(jPanel2Border.getWidth() / 2, cbMonth.getHeight()));

						DefaultComboBoxModel<String> modelOnMonth = new DefaultComboBoxModel<String>(onMonth);
						cbMonth.setModel(modelOnMonth);
						DefaultComboBoxModel<String> modelOnYear = new DefaultComboBoxModel<String>(onYear);
						cbYear.setModel(modelOnYear);
						cbMonth.setEnabled(true);
						cbYear.setEnabled(true);

						dcDay1.setEnabled(false);
						dcDay2.setEnabled(false);
						dcDay1.setDate(null);
						dcDay2.setDate(null);
					}
					// Nếu chọn comboBox cbFilterByType: Năm
					if (cbFilterByType.getSelectedIndex() == 2) {
						DefaultComboBoxModel<String> modelOnYear = new DefaultComboBoxModel<String>(onYear);
						cbYear.setModel(modelOnYear);
						cbMonth.setModel(new DefaultComboBoxModel<String>());
						cbMonth.setEnabled(false);
						cbYear.setEnabled(true);

						dcDay1.setEnabled(false);
						dcDay2.setEnabled(false);
						dcDay1.setDate(null);
						dcDay2.setDate(null);
					}
					// Nếu chọn comboBox cbFilterByType: Khác
					if (cbFilterByType.getSelectedIndex() == 3) {
						dcDay2.setVisible(true);
						dcDay2.setPreferredSize(new Dimension(jPanel1Border.getWidth() / 2, dcDay2.getHeight()));

						dcDay1.setEnabled(true);
						dcDay2.setEnabled(true);
						dcDay1.setDate(new Date());
						dcDay2.setDate(new Date());

						cbMonth.setEnabled(false);
						cbYear.setEnabled(false);
						cbMonth.setModel(new DefaultComboBoxModel<String>());
						cbYear.setModel(new DefaultComboBoxModel<String>());
					}
				} else {
					dcDay2.setVisible(false);
					cbMonth.setVisible(false);

					cbMonth.setModel(new DefaultComboBoxModel<String>());
					cbYear.setModel(new DefaultComboBoxModel<String>());
					cbMonth.setEnabled(false);
					cbYear.setEnabled(false);

					dcDay2.setDate(null);
					dcDay1.setDate(null);
					dcDay1.setEnabled(false);
					dcDay2.setEnabled(false);
				}
			}
		});
		cbTypeReport.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {

				if (cbTypeReport.getSelectedItem().toString().equals("Chi phí")) {
					tabbedPane.setEnabledAt(1, true);
					btnView.setEnabled(true);
				}

				if (cbTypeReport.getSelectedItem().toString().equals("Doanh thu")) {
					tabbedPane.setEnabledAt(1, true);
					btnView.setEnabled(true);
				}

				if (cbTypeReport.getSelectedItem().toString().equals("Thu - Chi - Lãi")) {
					tabbedPane.setEnabledAt(1, true);
					btnView.setEnabled(true);
				}

				if (cbTypeReport.getSelectedItem().toString().equals("Thu - Chi - Công nợ")) {
					tabbedPane.setEnabledAt(1, false);
					if (tabbedPane.getSelectedIndex() == 1) {
						btnView.setEnabled(false);
					}
				}
			}
		});

		cbTypeReport.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					PanelWarehouse add;
					try {
						add = new PanelWarehouse();
						add.setName("ThemMoiKho");
						DialogMain dialog = new DialogMain(add);
						dialog.setIcon("soche1.png");
						dialog.setName("dlThemMoiKho");
						dialog.setTitle("Tạo kho hàng");
						dialog.setResizable(false);
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (cbTypeReport.getSelectedIndex() == 3) {
					btnView.setEnabled(true);
				}
			}
		});

		if (Toolkit.getDefaultToolkit().getScreenSize().width <= 1024)
			panelMain.setPreferredSize(new Dimension(350, 130));
		else
			panelMain.setPreferredSize(new Dimension(250, 130));

		return panelMain;
	}

	/**
	 * Giao diện riêng cho: [Thống kê thời gian]
	 */
	private JPanel PanelThongKeThoiGianPhai() {

		byTypeReport[0] = "Chi phí";
		byTypeReport[1] = "Doanh thu";
		byTypeReport[2] = "Thu - Chi - Lãi";
		byTypeReport[3] = "Thu - Chi - Công nợ";

		byType[0] = "Năm - Tháng";
		byType[1] = "Năm - Tháng - Ngày";
		byType[2] = "Năm - Tháng - Ngày - Ca";
		byType[3] = "Năm - Tháng - Ngày - Ca - Giờ";
		byType[4] = "Năm - Tháng - Ngày - Giờ";
		byType[5] = "Năm - Tuần";
		byType[6] = "Năm - Tuần - Giờ";

		byValueMoney[0] = "Đơn vị";
		byValueMoney[1] = "Nghìn";
		byValueMoney[2] = "Triệu";

		PANELTOPRIGHT panelMain = new PANELTOPRIGHT();
		processing = new Processing();

		cbTypeReport = new ExtendJComboBox();
		cbTypeReport.setFont(new Font("Tahoma", Font.BOLD, 14));
		cbFilterByType = new ExtendJComboBox();
		cbUnitMoney = new UnitMoneyJComboBox();
		cbUnitMoney.setEnabled(false);
		cbValueMoney = new ExtendJComboBox();

		DefaultComboBoxModel<String> modelTypeReport = new DefaultComboBoxModel<String>(byTypeReport);
		cbTypeReport.setModel(modelTypeReport);
		cbTypeReport.setSelectedIndex(2);
		DefaultComboBoxModel<String> modelByType = new DefaultComboBoxModel<String>(byType);
		cbFilterByType.setModel(modelByType);
		DefaultComboBoxModel<String> modelValueMoney = new DefaultComboBoxModel<String>(byValueMoney);
		cbValueMoney.setModel(modelValueMoney);
		//cbValueMoney.setSelectedIndex(2);
		btnView = new ExtendJButton("Xem thống kê");

		JPanelLayout jPanel1Grid = new JPanelLayout(cbValueMoney, cbUnitMoney);
		JPanelLayout panel1 = new JPanelLayout(jPanel1Grid, btnView);

		panelMain.addRow(lblTypeReport, cbTypeReport);
		panelMain.addRow(lblFilterByType, cbFilterByType);
		panelMain.addRow(lblValueMoney, panel1);
		if (profileRememberShowAll.get("valueIndex") != null
				&& profileRememberShowAll.get("valueIndex").equals(0)) {
			cbValueMoney.setSelectedIndex(0);
		} else {
			if (profileRememberShowAll.get("valueIndex") != null
					&& profileRememberShowAll.get("valueIndex").equals(1)) {
				cbValueMoney.setSelectedIndex(1);
			} else {
				cbValueMoney.setSelectedIndex(2);
			}
		}
		if (profileRememberShowAll.get("valueIndexDay") != null
				&& profileRememberShowAll.get("valueIndexDay").equals(0)) {
			cbFilterByType.setSelectedIndex(0);
		} else {
			if (profileRememberShowAll.get("valueIndexDay") != null
					&& profileRememberShowAll.get("valueIndexDay").equals(1)) {
				cbFilterByType.setSelectedIndex(1);
			} else {
				if (profileRememberShowAll.get("valueIndexDay") != null
						&& profileRememberShowAll.get("valueIndexDay").equals(2)) {
					cbFilterByType.setSelectedIndex(2);
				}else{
					cbFilterByType.setSelectedIndex(3);
				}
				
			}
		}
		cbValueMoney.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				profileRememberShowAll.put("valueIndex", cbValueMoney.getSelectedIndex());
				AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(),
				    profileRememberShowAll);

			}
		});
   
		/*
		 * Các sự kiện cho components
		 */
		btnView.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					processing.setVisible(true);
					new SwingWorker<Void, Void>() {
						@Override
						protected Void doInBackground() throws Exception {
							// Tính thờ gian query
							long startTime = System.currentTimeMillis();

							btnView_EventHandlerThongKeThoiGian(e);

							// Kết quả thời gian query:
							long endTime = System.currentTimeMillis();
							NumberFormat formatter = new DecimalFormat("#0.00000");
							String time = formatter.format((endTime - startTime) / 1000d);
							System.out.println("###$$$ TIME RUN QUERY: " + time);

							return null;
						};

						@Override
						protected void done() {
							processing.setVisible(false);
						};
					}.execute();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Quá trình thao tác phát sinh lỗi!" + ex.toString());
				}
			}
		});

		cbTypeReport.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {

				if (cbTypeReport.getSelectedItem().toString().equals("Chi phí")) {
					tabbedPane.setEnabledAt(1, true);
					btnView.setEnabled(true);
				}

				if (cbTypeReport.getSelectedItem().toString().equals("Doanh thu")) {
					tabbedPane.setEnabledAt(1, true);
					btnView.setEnabled(true);
				}

				if (cbTypeReport.getSelectedItem().toString().equals("Thu - Chi - Lãi")) {
					tabbedPane.setEnabledAt(1, true);
					btnView.setEnabled(true);
				}

				if (cbTypeReport.getSelectedItem().toString().equals("Thu - Chi - Công nợ")) {
					tabbedPane.setEnabledAt(1, false);
					if (tabbedPane.getSelectedIndex() == 1) {
						btnView.setEnabled(false);
					}
				}
			}
		});

		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (cbTypeReport.getSelectedIndex() == 3) {
					if (tabbedPane.getSelectedIndex() == 2 || tabbedPane.getSelectedIndex() == 0
					    || tabbedPane.getSelectedIndex() == 3 || tabbedPane.getSelectedIndex() == 4) {
						btnView.setEnabled(true);
					}
				}
			}
		});

		if (Toolkit.getDefaultToolkit().getScreenSize().width <= 1024)
			panelMain.setPreferredSize(new Dimension(350, 130));
		else
			panelMain.setPreferredSize(new Dimension(250, 130));

		return panelMain;
	}

	public void viewChart(DefaultPieDataset defaultPieDataset) {
		if (chartPanel == null) {
			chartPanel = new ExtendChartPanelReport();
		}
		chartPanel.setDefaultPieDataset(defaultPieDataset);
		if (chartPanel.getFreeChart() == null)
			panelPieChart.add(chartPanel.createChartPanel("Biểu đồ tròn (%)"), BorderLayout.CENTER);
		chartPanel.getChartPanel().updateUI();
		panelPieChart.updateUI();
		this.updateUI();
	}

	public String getColumnViewPie() {
		return valuePie;
	}

	private void btnView_EventHandlerThongKeThuChi(ActionEvent e) {
		HashMap<String, String> conds = new HashMap<String, String>();

		/*
		 * Truyền lọc theo thời gian
		 */
		Calendar calendarStart = Calendar.getInstance();
		Calendar calendarEnd = Calendar.getInstance();
		Date dateStart = calendarStart.getTime();
		Date dateEnd = calendarEnd.getTime();
		// Truyền điều kiện lọc theo ngày (chọn ngày-tháng-năm)
		if (cbFilterByType.getSelectedIndex() == 0) {
			switch (cbFilterByTime.getSelectedIndex()) {
			case 1: {
				calendarStart.add(Calendar.DAY_OF_MONTH, -1);
				dateStart = calendarStart.getTime();
				dateEnd = calendarStart.getTime();
			}
				break;
			case 2: {
				calendarStart.add(Calendar.DAY_OF_MONTH, -2);
				dateStart = calendarStart.getTime();
				dateEnd = calendarStart.getTime();
			}
				break;
			case 3: {
				dateStart = dcDay1.getDate();
				dateEnd = dcDay1.getDate();
			}
				break;
			default: {
				dateStart = calendarStart.getTime();
				dateEnd = calendarStart.getTime();
			}
			}
		}

		// Truyền điều kiện lọc theo tháng (chọn tháng-năm)
		if (cbFilterByType.getSelectedIndex() == 1) {
			calendarStart.set(Calendar.DAY_OF_MONTH, 1);
			switch (cbFilterByTime.getSelectedIndex()) {
			case 1: {
				calendarStart.add(Calendar.MONTH, -1);
				calendarEnd.set(Calendar.MONTH, calendarStart.get(Calendar.MONTH));
			}
				break;
			case 2: {
				calendarStart.add(Calendar.MONTH, -2);
				calendarEnd.set(Calendar.MONTH, calendarStart.get(Calendar.MONTH));
			}
				break;
			case 3: {
				calendarStart.set(Integer.parseInt(cbYear.getSelectedItem().toString()),
				    Integer.parseInt(cbMonth.getSelectedItem().toString()) - 1, 1);
				calendarEnd.set(Calendar.YEAR, calendarStart.get(Calendar.YEAR));
				calendarEnd.set(Calendar.MONTH, calendarStart.get(Calendar.MONTH));
			}
				break;
			}
			calendarEnd.set(Calendar.DAY_OF_MONTH, calendarEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
			dateStart = calendarStart.getTime();
			dateEnd = calendarEnd.getTime();
		}

		// Truyền điều kiện lọc theo năm (chọn năm)
		if (cbFilterByType.getSelectedIndex() == 2) {
			switch (cbFilterByTime.getSelectedIndex()) {
			case 1: {
				calendarStart.add(Calendar.YEAR, -1);
			}
				break;
			case 2: {
				calendarStart.add(Calendar.YEAR, -2);
			}
				break;
			case 3: {
				calendarStart.set(Calendar.YEAR, Integer.parseInt(cbYear.getSelectedItem().toString()));
			}
				break;
			}
			calendarStart.set(Calendar.MONTH, 0);
			calendarStart.set(Calendar.DAY_OF_MONTH, 1);
			calendarEnd.set(calendarStart.get(Calendar.YEAR), calendarStart.getActualMaximum(Calendar.MONTH),
			    calendarStart.getActualMaximum(Calendar.DAY_OF_MONTH));
			dateStart = calendarStart.getTime();
			dateEnd = calendarEnd.getTime();
		}
		// Truyền điều kiện lọc theo thời gian tùy chọn
		if (cbFilterByType.getSelectedIndex() == 3) {
			dateStart = dcDay1.getDate();
			dateEnd = dcDay2.getDate();
		}

		/*
		 * Truyền lọc theo các đối tượng
		 */
		// Truyền điều kiện lọc phòng ban
		if (tabbedPane.getSelectedIndex() == 0)
			if (cbDepartment1.getSelectedDepartment() != null)
				conds.put("department", cbDepartment1.getSelectedDepartment().getName());
			else
				conds.put("department", "");
		// Truyền điều kiện lọc sản phẩm
		if (tabbedPane.getSelectedIndex() == 1) {
			if (cbGroupProduct.getSelectedProductTag() != null)
				conds.put("product1", cbGroupProduct.getSelectedProductTag().getCode());
			else
				conds.put("product1", "");
			if (txtProduct.getItem() != null)
				conds.put("product2", txtProduct.getItem().getCode());
			else
				conds.put("product2", "");
		}
		// Truyền điều kiện lọc khu vực
		if (tabbedPane.getSelectedIndex() == 2) {
			if (cbLocation.getSelectedLocation() != null)
				conds.put("location1", cbLocation.getSelectedLocation().getCode());
			else
				conds.put("location1", "");
			if (cbTable.getSelectedTable() != null)
				conds.put("location2", cbTable.getSelectedTable().getCode());
			else
				conds.put("location2", "");
		}
		// Truyền điều kiện lọc khách hàng
		if (tabbedPane.getSelectedIndex() == 3) {
			if (cbGroupPartner.getSelectedGroupCustomer() != null)
				conds.put("partner1", cbGroupPartner.getSelectedGroupCustomer().getName());
			else
				conds.put("partner1", "");
			if (txtCustomer.getItem() != null)
				conds.put("partner2", txtCustomer.getItem().getLoginId());
			else
				conds.put("partner2", "");
		}
		// Truyền điều kiện lọc nhân viên
		if (tabbedPane.getSelectedIndex() == 4) {
			if (cbDepartment2.getSelectedDepartment() != null) {
				conds.put("cashier1", cbDepartment2.getSelectedDepartment().getName());
			} else
				conds.put("cashier1", "");
			if (txtEmployee.getItem() != null)
				conds.put("cashier2", txtEmployee.getItem().getLoginId());
			else
				conds.put("cashier2", "");
		}
		// Truyền điều kiện cửa hàng
		if (tabbedPane.getSelectedIndex() == 5) {
			if (cboStore.getSelectedItem() != null) {
				conds.put("store", ((Option) cboStore.getSelectedItem()).getCode());
			} else {
				conds.put("store", "");
			}
		}
		// Truyền điều kiện dự án
		if (tabbedPane.getSelectedIndex() == 6) {
			if (txtProject.getItem() != null) {
				conds.put("project", ((Project) txtProject.getItem()).getCode());
			} else {
				conds.put("project", "");
			}
		}
//		TableReportRevenueTest table = new TableReportRevenueTest(conds, cbTypeReport.getSelectedItem().toString(), dateStart,
//		    dateEnd, cbValueMoney.getSelectedItem().toString(), isCheck);
		TableReportRevenue table = new TableReportRevenue(conds, cbTypeReport.getSelectedItem().toString(), dateStart,
		    dateEnd, cbValueMoney.getSelectedItem().toString(), isCheck);


		this.tableStatic = table;
		scrollPane.setViewportView(table);

		// TODO: Add thong ke do thi --- TEST
		table.setReportGui(this);
	}
	
	private void btnView_EventHandlerThongKeKeToan(ActionEvent e){
		HashMap<String, String> conds = new HashMap<String, String>();

		/*
		 * Truyền lọc theo thời gian
		 */
		Calendar calendarStart = Calendar.getInstance();
		Calendar calendarEnd = Calendar.getInstance();
		Date dateStart = calendarStart.getTime();
		Date dateEnd = calendarEnd.getTime();
		// Truyền điều kiện lọc theo ngày (chọn ngày-tháng-năm)
		if (cbFilterByType.getSelectedIndex() == 0) {
			switch (cbFilterByTime.getSelectedIndex()) {
			case 1: {
				calendarStart.add(Calendar.DAY_OF_MONTH, -1);
				dateStart = calendarStart.getTime();
				dateEnd = calendarStart.getTime();
			}
				break;
			case 2: {
				calendarStart.add(Calendar.DAY_OF_MONTH, -2);
				dateStart = calendarStart.getTime();
				dateEnd = calendarStart.getTime();
			}
				break;
			case 3: {
				dateStart = dcDay1.getDate();
				dateEnd = dcDay1.getDate();
			}
				break;
			default: {
				dateStart = calendarStart.getTime();
				dateEnd = calendarStart.getTime();
			}
			}
		}

		// Truyền điều kiện lọc theo tháng (chọn tháng-năm)
		if (cbFilterByType.getSelectedIndex() == 1) {
			calendarStart.set(Calendar.DAY_OF_MONTH, 1);
			switch (cbFilterByTime.getSelectedIndex()) {
			case 1: {
				calendarStart.add(Calendar.MONTH, -1);
				calendarEnd.set(Calendar.MONTH, calendarStart.get(Calendar.MONTH));
			}
				break;
			case 2: {
				calendarStart.add(Calendar.MONTH, -2);
				calendarEnd.set(Calendar.MONTH, calendarStart.get(Calendar.MONTH));
			}
				break;
			case 3: {
				calendarStart.set(Integer.parseInt(cbYear.getSelectedItem().toString()),
				    Integer.parseInt(cbMonth.getSelectedItem().toString()) - 1, 1);
				calendarEnd.set(Calendar.YEAR, calendarStart.get(Calendar.YEAR));
				calendarEnd.set(Calendar.MONTH, calendarStart.get(Calendar.MONTH));
			}
				break;
			}
			calendarEnd.set(Calendar.DAY_OF_MONTH, calendarEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
			dateStart = calendarStart.getTime();
			dateEnd = calendarEnd.getTime();
		}

		// Truyền điều kiện lọc theo năm (chọn năm)
		if (cbFilterByType.getSelectedIndex() == 2) {
			switch (cbFilterByTime.getSelectedIndex()) {
			case 1: {
				calendarStart.add(Calendar.YEAR, -1);
			}
				break;
			case 2: {
				calendarStart.add(Calendar.YEAR, -2);
			}
				break;
			case 3: {
				calendarStart.set(Calendar.YEAR, Integer.parseInt(cbYear.getSelectedItem().toString()));
			}
				break;
			}
			calendarStart.set(Calendar.MONTH, 0);
			calendarStart.set(Calendar.DAY_OF_MONTH, 1);
			calendarEnd.set(calendarStart.get(Calendar.YEAR), calendarStart.getActualMaximum(Calendar.MONTH),
			    calendarStart.getActualMaximum(Calendar.DAY_OF_MONTH));
			dateStart = calendarStart.getTime();
			dateEnd = calendarEnd.getTime();
		}
		// Truyền điều kiện lọc theo thời gian tùy chọn
		if (cbFilterByType.getSelectedIndex() == 3) {
			dateStart = dcDay1.getDate();
			dateEnd = dcDay2.getDate();
		}
		conds.put("bank", "Thống kê kế toán");
//		TableReportRevenueTest table = new TableReportRevenueTest(conds, cbTypeReport.getSelectedItem().toString(), dateStart,
//		    dateEnd, cbValueMoney.getSelectedItem().toString(), isCheck);
		TableReportBank table = new TableReportBank(conds, cbTypeReport.getSelectedItem().toString(), dateStart,
		    dateEnd, cbValueMoney.getSelectedItem().toString(), isCheck);

		this.tableStatic = table;
		scrollPane.setViewportView(table);

		// TODO: Add thong ke do thi --- TEST
		table.setReportGui(this);
	}

	private void btnView_EventHandlerThongKeThoiGian(ActionEvent e) {
		HashMap<String, String> conds = new HashMap<String, String>();
		// Truyền điều kiện lọc phòng ban
		if (tabbedPane.getSelectedIndex() == 0)
			if (cbDepartment1.getSelectedDepartment() != null)
				conds.put("department", cbDepartment1.getSelectedDepartment().getName());
			else
				conds.put("department", "");
		// Truyền điều kiện lọc sản phẩm
		if (tabbedPane.getSelectedIndex() == 1) {
			if (cbGroupProduct.getSelectedProductTag() != null)
				conds.put("product1", cbGroupProduct.getSelectedProductTag().getCode());
			else
				conds.put("product1", "");
			if (txtProduct.getItem() != null)
				conds.put("product2", txtProduct.getItem().getCode());
			else
				conds.put("product2", "");
		}
		// Truyền điều kiện lọc khu vực
		if (tabbedPane.getSelectedIndex() == 2) {
			if (cbLocation.getSelectedLocation() != null)
				conds.put("location1", cbLocation.getSelectedLocation().getCode());
			else
				conds.put("location1", "");
			if (cbTable.getSelectedTable() != null)
				conds.put("location2", cbTable.getSelectedTable().getCode());
			else
				conds.put("location2", "");
		}
		// Truyền điều kiện lọc khách hàng
		if (tabbedPane.getSelectedIndex() == 3) {
			if (cbGroupPartner.getSelectedGroupCustomer() != null)
				conds.put("partner1", cbGroupPartner.getSelectedGroupCustomer().getName());
			else
				conds.put("partner1", "");
			if (txtCustomer.getItem() != null)
				conds.put("partner2", txtCustomer.getItem().getLoginId());
			else
				conds.put("partner2", "");
		}
		// Truyền điều kiện lọc nhân viên
		if (tabbedPane.getSelectedIndex() == 4) {
			if (cbDepartment2.getSelectedDepartment() != null)
				conds.put("cashier1", cbDepartment2.getSelectedDepartment().getName());
			else
				conds.put("cashier1", "");
			if (txtEmployee.getItem() != null)
				conds.put("cashier2", txtEmployee.getItem().getLoginId());
			else
				conds.put("cashier2", "");
		}
		// Truyền điều kiện lọc cửa hàng
		if (tabbedPane.getSelectedIndex() == 5) {
			if (cboStore.getSelectedItem() != null) {
				conds.put("store", ((Option) cboStore.getSelectedItem()).getCode());
			} else
				conds.put("store", "");
		}
		// Truyền điều kiện lọc dự án
		if (tabbedPane.getSelectedIndex() == 6) {
			if (txtProject.getItem() != null) {
				conds.put("project", ((Project) txtProject.getItem()).getCode());
			} else {
				conds.put("project", "");
			}
		}
		TableReportForTime table = new TableReportForTime(cbTypeReport.getSelectedIndex(),
		    cbFilterByType.getSelectedIndex(), conds, cbValueMoney.getSelectedItem().toString());
		this.tableStatic = table;
		scrollPane.setViewportView(table);
		
//		TableReportBank table = new TableReportBank(cbTypeReport.getSelectedIndex(),
//	    cbFilterByType.getSelectedIndex(), conds, cbValueMoney.getSelectedItem().toString());
//		this.tableStatic = table;
//		scrollPane.setViewportView(table);

		// TODO: Add thong ke do thi --- TEST
		table.setReportGui(this);
	}

	private void btnView_EventHandlerThongKeMuaBanHang(ActionEvent e) {
		/*
		 * Truyền lọc theo thời gian
		 */
		Calendar calendarStart = Calendar.getInstance();
		Calendar calendarEnd = Calendar.getInstance();
		Date dateStart = calendarStart.getTime();
		Date dateEnd = calendarEnd.getTime();

		// Truyền điều kiện lọc theo ngày (chọn ngày-tháng-năm)
		if (cbFilterByType.getSelectedIndex() == 0) {
			switch (cbFilterByTime.getSelectedIndex()) {
			case 1: {
				calendarStart.add(Calendar.DAY_OF_MONTH, -1);
				dateStart = calendarStart.getTime();
				dateEnd = calendarStart.getTime();
			}
				break;
			case 2: {
				calendarStart.add(Calendar.DAY_OF_MONTH, -2);
				dateStart = calendarStart.getTime();
				dateEnd = calendarStart.getTime();
			}
				break;
			case 3: {
				dateStart = dcDay1.getDate();
				dateEnd = dcDay1.getDate();
			}
				break;
			default: {
				dateStart = calendarStart.getTime();
				dateEnd = calendarStart.getTime();
			}
			}
		}

		// Truyền điều kiện lọc theo tháng (chọn tháng-năm)
		if (cbFilterByType.getSelectedIndex() == 1) {
			calendarStart.set(Calendar.DAY_OF_MONTH, 1);
			switch (cbFilterByTime.getSelectedIndex()) {
			case 1: {
				calendarStart.add(Calendar.MONTH, -1);
				calendarEnd.set(Calendar.MONTH, calendarStart.get(Calendar.MONTH));
			}
				break;
			case 2: {
				calendarStart.add(Calendar.MONTH, -2);
				calendarEnd.set(Calendar.MONTH, calendarStart.get(Calendar.MONTH));
			}
				break;
			case 3: {
				calendarStart.set(Integer.parseInt(cbYear.getSelectedItem().toString()),
				    Integer.parseInt(cbMonth.getSelectedItem().toString()) - 1, 1);
				calendarEnd.set(Calendar.YEAR, calendarStart.get(Calendar.YEAR));
				calendarEnd.set(Calendar.MONTH, calendarStart.get(Calendar.MONTH));
			}
				break;
			}
			calendarEnd.set(Calendar.DAY_OF_MONTH, calendarEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
			dateStart = calendarStart.getTime();
			dateEnd = calendarEnd.getTime();
		}

		// Truyền điều kiện lọc theo năm (chọn năm)
		if (cbFilterByType.getSelectedIndex() == 2) {
			switch (cbFilterByTime.getSelectedIndex()) {
			case 1: {
				calendarStart.add(Calendar.YEAR, -1);
			}
				break;
			case 2: {
				calendarStart.add(Calendar.YEAR, -2);
			}
				break;
			case 3: {
				calendarStart.set(Calendar.YEAR, Integer.parseInt(cbYear.getSelectedItem().toString()));
			}
				break;
			}
			calendarStart.set(Calendar.MONTH, 0);
			calendarStart.set(Calendar.DAY_OF_MONTH, 1);
			calendarEnd.set(calendarStart.get(Calendar.YEAR), calendarStart.getActualMaximum(Calendar.MONTH),
			    calendarStart.getActualMaximum(Calendar.DAY_OF_MONTH));
			dateStart = calendarStart.getTime();
			dateEnd = calendarEnd.getTime();
		}
		// Truyền điều kiện lọc theo thời gian tùy chọn
		if (cbFilterByType.getSelectedIndex() == 3) {
			dateStart = dcDay1.getDate();
			dateEnd = dcDay2.getDate();
		}

		String warehouse = null;
		if (cbTypeReport.getSelectedItem() != null) {
			warehouse = ((Warehouse) cbTypeReport.getSelectedItem()).getWarehouseId();
		}
		ActivityType activityType = (ActivityType) e.getSource();
		TableReportSales table = new TableReportSales(dateStart, dateEnd, cbValueMoney.getSelectedItem().toString(),
		    warehouse, activityType, chbShowAll.isSelected());
		this.tableStatic = table;
		tableFillterSorter = new TableFillterSorter(table);
		tableFillterSorter.createTableSorter();
		tableFillterSorter.createTableFillter();
		cbOrganization2.setModel(new DefaultComboBoxModel(tableFillterSorter.getHashtable().keySet().toArray()));
		scrollPane.setViewportView(table);

		// TODO: Add thong ke do thi --- TEST
		table.setReportGui(this);
	}

	private void btnView_EventHandlerThongKeTonKho_DinhLuong(ActionEvent e) {
		// Truyền lọc theo thời gian
		Calendar calendarStart = Calendar.getInstance();
		Calendar calendarEnd = Calendar.getInstance();
		Date dateStart = calendarStart.getTime();
		Date dateEnd = calendarEnd.getTime();

		// Truyền điều kiện lọc theo ngày (chọn ngày-tháng-năm)
		if (cbFilterByType.getSelectedIndex() == 0) {
			switch (cbFilterByTime.getSelectedIndex()) {
			case 1: {
				calendarStart.add(Calendar.DAY_OF_MONTH, -1);
				dateStart = calendarStart.getTime();
				dateEnd = calendarStart.getTime();
			}
				break;
			case 2: {
				calendarStart.add(Calendar.DAY_OF_MONTH, -2);
				dateStart = calendarStart.getTime();
				dateEnd = calendarStart.getTime();
			}
				break;
			case 3: {
				dateStart = dcDay1.getDate();
				dateEnd = dcDay1.getDate();
			}
				break;
			default: {
				dateStart = calendarStart.getTime();
				dateEnd = calendarStart.getTime();
			}
			}
		}

		// Truyền điều kiện lọc theo tháng (chọn tháng-năm)
		if (cbFilterByType.getSelectedIndex() == 1) {
			calendarStart.set(Calendar.DAY_OF_MONTH, 1);
			switch (cbFilterByTime.getSelectedIndex()) {
			case 1: {
				calendarStart.add(Calendar.MONTH, -1);
				calendarEnd.set(Calendar.MONTH, calendarStart.get(Calendar.MONTH));
			}
				break;
			case 2: {
				calendarStart.add(Calendar.MONTH, -2);
				calendarEnd.set(Calendar.MONTH, calendarStart.get(Calendar.MONTH));
			}
				break;
			case 3: {
				calendarStart.set(Integer.parseInt(cbYear.getSelectedItem().toString()),
				    Integer.parseInt(cbMonth.getSelectedItem().toString()) - 1, 1);
				calendarEnd.set(Calendar.YEAR, calendarStart.get(Calendar.YEAR));
				calendarEnd.set(Calendar.MONTH, calendarStart.get(Calendar.MONTH));
			}
				break;
			}
			calendarEnd.set(Calendar.DAY_OF_MONTH, calendarEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
			dateStart = calendarStart.getTime();
			dateEnd = calendarEnd.getTime();
		}

		// Truyền điều kiện lọc theo năm (chọn năm)
		if (cbFilterByType.getSelectedIndex() == 2) {
			switch (cbFilterByTime.getSelectedIndex()) {
			case 1: {
				calendarStart.add(Calendar.YEAR, -1);
			}
				break;
			case 2: {
				calendarStart.add(Calendar.YEAR, -2);
			}
				break;
			case 3: {
				calendarStart.set(Calendar.YEAR, Integer.parseInt(cbYear.getSelectedItem().toString()));
			}
				break;
			}
			calendarStart.set(Calendar.MONTH, 0);
			calendarStart.set(Calendar.DAY_OF_MONTH, 1);
			calendarEnd.set(calendarStart.get(Calendar.YEAR), calendarStart.getActualMaximum(Calendar.MONTH),
			    calendarStart.getActualMaximum(Calendar.DAY_OF_MONTH));
			dateStart = calendarStart.getTime();
			dateEnd = calendarEnd.getTime();
		}
		// Truyền điều kiện lọc theo thời gian tùy chọn
		if (cbFilterByType.getSelectedIndex() == 3) {
			dateStart = dcDay1.getDate();
			dateEnd = dcDay2.getDate();
		}

		String warehouse = null;
		if (cbTypeReport.getSelectedItem() != null) {
			warehouse = ((Warehouse) cbTypeReport.getSelectedItem()).getWarehouseId();
		}
		String isInventory = e.getSource().toString();
		TableInventory table = new TableInventory(isInventory, dateStart, dateEnd, cbValueMoney.getSelectedItem()
		    .toString(), warehouse, chbShowAll.isSelected());
		this.tableStatic = table;
		tableFillterSorter = new TableFillterSorter(table);
		tableFillterSorter.createTableSorter();
		tableFillterSorter.createTableFillter();
		cbOrganization2.setModel(new DefaultComboBoxModel(tableFillterSorter.getHashtable().keySet().toArray()));
		scrollPane.setViewportView(table);

		// TODO: Add thong ke do thi --- TEST
		table.setReportGui(this);
	}

	private void btnView_EventHandlerThongKeNhanVien(ActionEvent e) {
		HashMap<String, String> conds = new HashMap<String, String>();

		/*
		 * Truyền lọc theo thời gian
		 */
		Calendar calendarStart = Calendar.getInstance();
		Calendar calendarEnd = Calendar.getInstance();
		Date dateStart = calendarStart.getTime();
		Date dateEnd = calendarEnd.getTime();
		// Truyền điều kiện lọc theo ngày (chọn ngày-tháng-năm)
		if (cbFilterByType.getSelectedIndex() == 0) {
			switch (cbFilterByTime.getSelectedIndex()) {
			case 1: {
				calendarStart.add(Calendar.DAY_OF_MONTH, -1);
				dateStart = calendarStart.getTime();
				dateEnd = calendarStart.getTime();
			}
				break;
			case 2: {
				calendarStart.add(Calendar.DAY_OF_MONTH, -2);
				dateStart = calendarStart.getTime();
				dateEnd = calendarStart.getTime();
			}
				break;
			case 3: {
				dateStart = dcDay1.getDate();
				dateEnd = dcDay1.getDate();
			}
				break;
			default: {
				dateStart = calendarStart.getTime();
				dateEnd = calendarStart.getTime();
			}
			}
		}
		// Truyền điều kiện lọc theo tháng (chọn tháng-năm)
		if (cbFilterByType.getSelectedIndex() == 1) {
			calendarStart.set(Calendar.DAY_OF_MONTH, 1);
			switch (cbFilterByTime.getSelectedIndex()) {
			case 1: {
				calendarStart.add(Calendar.MONTH, -1);
				calendarEnd.set(Calendar.MONTH, calendarStart.get(Calendar.MONTH));
			}
				break;
			case 2: {
				calendarStart.add(Calendar.MONTH, -2);
				calendarEnd.set(Calendar.MONTH, calendarStart.get(Calendar.MONTH));
			}
				break;
			case 3: {
				calendarStart.set(Integer.parseInt(cbYear.getSelectedItem().toString()),
				    Integer.parseInt(cbMonth.getSelectedItem().toString()) - 1, 1);
				calendarEnd.set(Calendar.YEAR, calendarStart.get(Calendar.YEAR));
				calendarEnd.set(Calendar.MONTH, calendarStart.get(Calendar.MONTH));
			}
				break;
			}
			calendarEnd.set(Calendar.DAY_OF_MONTH, calendarEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
			dateStart = calendarStart.getTime();
			dateEnd = calendarEnd.getTime();
		}

		// Truyền điều kiện lọc theo năm (chọn năm)
		if (cbFilterByType.getSelectedIndex() == 2) {
			switch (cbFilterByTime.getSelectedIndex()) {
			case 1: {
				calendarStart.add(Calendar.YEAR, -1);
			}
				break;
			case 2: {
				calendarStart.add(Calendar.YEAR, -2);
			}
				break;
			case 3: {
				calendarStart.set(Calendar.YEAR, Integer.parseInt(cbYear.getSelectedItem().toString()));
			}
				break;
			}
			calendarStart.set(Calendar.MONTH, 0);
			calendarStart.set(Calendar.DAY_OF_MONTH, 1);
			calendarEnd.set(calendarStart.get(Calendar.YEAR), calendarStart.getActualMaximum(Calendar.MONTH),
			    calendarStart.getActualMaximum(Calendar.DAY_OF_MONTH));
			dateStart = calendarStart.getTime();
			dateEnd = calendarEnd.getTime();
		}
		// Truyền điều kiện lọc theo thời gian tùy chọn
		if (cbFilterByType.getSelectedIndex() == 3) {
			dateStart = dcDay1.getDate();
			dateEnd = dcDay2.getDate();
		}

		/*
		 * Truyền lọc theo các đối tượng
		 */
		// Truyền điều kiện lọc nhân viên
		if (tabbedPane.getSelectedIndex() == 1) {
			if (txtEmployee.getItem() != null)
				conds.put("employee", txtEmployee.getItem().getCode());
			else
				conds.put("employee", "");
		}

		// Truyền điều kiện dự án
		if (tabbedPane.getSelectedIndex() == 0) {
			if (txtProject.getItem() != null) {
				conds.put("project", ((Project) txtProject.getItem()).getCode());
			} else {
				conds.put("project", "");
			}
		}
		table = new TableReportProjectMember(conds, cbTypeReport.getSelectedItem().toString(), dateStart, dateEnd,
		    cbValueMoney.getSelectedItem().toString(), isCheck);
		scrollPane.setViewportView(table);

	}

	private void txtSearchCaretUpdate(CaretEvent evt) {
		searchData();
	}

	private void cbSearchItemStateChanged(ItemEvent evt) {
		searchData();
	}

	private void searchData() {
		tableFillterSorter.fillter(txtSearch.getText(), cbOrganization2.getSelectedItem().toString());
	}

	/**
	 * Panel TOP bên trái
	 */
	protected class PANELTOPLEFT extends JPanel {

		public PANELTOPLEFT() {
			UIManager.put("TabbedPane.contentOpaque", false);
			UIManager.put("TabbedPane.tabsOpaque", false);
			tabbedPane = new JTabbedPane();
			tabbedPane.setUI(new MyTabbedPaneUI());
			tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 14));
			tabbedPane.setForeground(Color.BLACK);
			tabbedPane.setBorder(null);

			tabbedPane.setOpaque(false);

			this.setOpaque(false);
			this.setLayout(new BorderLayout(0, 0));
			this.add(tabbedPane, BorderLayout.CENTER);
		}

		public void addTabPanel(String nameTab, JPanel panelTab) {
			panelTab.setOpaque(false);
			panelTab.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 20));
			tabbedPane.add(nameTab, panelTab);
		}

		public void actionTabChangeListener() {
			tabbedPane.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					cbDepartment1.setSelectedIndex(0);
					cbDepartment2.setSelectedIndex(0);
					txtEmployee.setItem(null);
					cbGroupProduct.setSelectedIndex(0);
					txtProduct.setItem(null);
					cbLocation.setSelectedIndex(0);
					cbTable.setSelectedIndex(0);
					cbGroupPartner.setSelectedIndex(0);
					txtCustomer.setItem(null);
					cboStore.setSelectedIndex(0);
					txtProject.setItem(null);
				}
			});
		}

		public void actionTabChangeListenerProjectMember() {
			tabbedPane.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					txtEmployee.setItem(null);
					txtProject.setItem(null);

				}
			});
		}
	}

	/**
	 * Panel TOP bên phải
	 */
	protected class PANELTOPRIGHT extends GridLabelLayoutPabel {
		private int index = 0;

		public PANELTOPRIGHT() {
			this.setOpaque(false);
			this.setPreferredSize(new Dimension(600, 120));
		}

		public void addRow(ExtendJLabel label, JComponent component) {
			this.add(index, 0, label);
			this.add(index, 1, component);
			index++;
		}
	}

	/**
	 * Panel CENTER nội dung (chứa bảng)
	 */
	// protected class CONTAINER_CENTER extends JPanel {
	// public CONTAINER_CENTER() {
	// scrollPane.getViewport().setOpaque(false);
	// scrollPane.setBorder(BorderFactory.createEmptyBorder());
	// // this.setPreferredSize(new Dimension(1000, 300));
	// this.setOpaque(false);
	// this.setLayout(new BorderLayout(0, 0));
	// this.setBorder(null);
	// this.setBorder(BorderFactory.createEmptyBorder());
	// this.add(scrollPane, BorderLayout.CENTER);
	// this.add(containerChart, BorderLayout.PAGE_END);
	// }
	// }

	/**
	 * Panel nhóm các components
	 */
	protected class JPanelLayout extends JPanel {
		public JPanelLayout(JComponent comp1, JComponent comp2) {
			this.setOpaque(false);
			this.setLayout(new GridLayout(1, 2, 2, 0));
			this.add(comp1);
			this.add(comp2);
		}

		public JPanelLayout(JComponent comp1, String pos1, JComponent comp2, String pos2) {
			this.setLayout(new BorderLayout(2, 0));
			this.add(comp1, pos1);
			this.add(comp2, pos2);
		}
	}

}
