package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.hethong.PanelOption;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.GenericOptionModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.module.accounting.entity.Contributor;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.config.generic.Option;
import com.hkt.module.config.generic.OptionGroup;
import com.hkt.module.hr.entity.Employee;

@SuppressWarnings("serial")
public class PanelMemberServe extends MyPanel implements IDialogMain {

	private ExtendJLabel lblPosition, lblContributed, lblMember, lblDescription, lblMessager;
	private ExtendJTextField txtContributed, txtDescription;
	private TextPopup<Employee> txtMember;
	private InvoiceDetail invoiceDetail;
	private ExtendJComboBox cboPosition;
	private Contributor contributor;
	private TableModelContributor tableModel;
	private JTable table;
	private JScrollPane scrollPaneTable;
	private List<Contributor> contributors;
	private int index = 0;

	public PanelMemberServe(InvoiceDetail _invoiceDetail) {
		this.invoiceDetail = _invoiceDetail;
		try {
			init();
		} catch (Exception e) {
		}
	}

	public void init() throws Exception {
		lblMessager = new ExtendJLabel("");
		scrollPaneTable = new JScrollPane();
		scrollPaneTable.setOpaque(false);
		scrollPaneTable.getViewport().setOpaque(false);
		scrollPaneTable.setBorder(null);
		contributors = invoiceDetail.getContributors();
		tableModel = new TableModelContributor(contributors);
		table = new JTable(tableModel);
		table.setFont(new Font("Tahoma", 0, 14));
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		table.setRowHeight(23);
		table.getColumnModel().getColumn(0).setMaxWidth(50);
		scrollPaneTable.setViewportView(table);
		scrollPaneTable.getViewport().setBackground(Color.WHITE);

		lblPosition = new ExtendJLabel("Vị trí");
		lblContributed = new ExtendJLabel("Đóng góp");
		lblMember = new ExtendJLabel("Thành viên");
		lblDescription = new ExtendJLabel("Miêu tả");
		lblMessager = new ExtendJLabel("");

		cboPosition = new ExtendJComboBox();
		txtContributed = new ExtendJTextField();
		txtMember = new TextPopup<Employee>(Employee.class);

		// Đẩy danh sách NV add vào txtMember
		try {
			List<Employee> list1 = HRModelManager.getInstance().getEmployees();
			txtMember.setData(list1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		txtDescription = new ExtendJTextField();
		txtContributed.setHorizontalAlignment(JTextField.RIGHT);

		// Lấy Danh sách vị trí add vào ComboBox
		try {
			OptionGroup group = GenericOptionModelManager.getInstance().getOptionGroup("accounting", "AccountingService",
			    "contributor_role");
			List<Option> options = new ArrayList<Option>();
			if (group != null) {
				if (group.getOptions() != null) {
					options = group.getOptions();
				}
			}
			cboPosition.setModel(new DefaultComboBoxModel(options.toArray()));
		} catch (Exception e) {
		}
		cboPosition.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		if(e.getButton() == MouseEvent.BUTTON3)
	    		{
	    			PanelOption pnOption;
					try {
						pnOption = new PanelOption("accounting", "AccountingService", "contributor_role");
						pnOption.setName("ViTriPhongBan");
						DialogMain dialog = new DialogMain(pnOption);
						dialog.setIcon("donvi1.png");
						dialog.setName("dlViTriPhongBan");
						dialog.setTitle("Vị trí phòng ban");
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);
						try {
							OptionGroup group = GenericOptionModelManager.getInstance().getOptionGroup("accounting", "AccountingService",
							    "contributor_role");
							List<Option> options = new ArrayList<Option>();
							if (group != null) {
								if (group.getOptions() != null) {
									options = group.getOptions();
								}
							}
							cboPosition.setModel(new DefaultComboBoxModel(options.toArray()));
						} catch (Exception e1) {
						}
					} catch (Exception e1) {

						e1.printStackTrace();
					}
	    		}}
	    		});

		MyGroupLayout layout = new MyGroupLayout(this);
		layout.add(0, 0, lblMember);
		layout.add(0, 1, txtMember);

		layout.add(1, 0, lblPosition);
		layout.add(1, 1, cboPosition);
		layout.add(1, 2, lblContributed);
		layout.add(1, 3, txtContributed);

		layout.add(2, 0, lblDescription);
		layout.add(2, 1, txtDescription);
		layout.addMessenger(lblMessager);

		layout.add(3, 0, scrollPaneTable);
		layout.updateGui();
	}

	protected void tableOptionsMouseClicked(MouseEvent event) throws Exception {

	}

	private void setDataTable(List<Contributor> contributors) {
		tableModel = new TableModelContributor(contributors);
		table.setModel(tableModel);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 2) {
					contributor = (Contributor) table.getValueAt(table.getSelectedRow(), 1);
					setData(contributor);
				}
			}
		});
	}

	public void setData(Contributor contributor) {
		try {
			txtContributed.setText(contributor.getRole());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Phần cài đặt các phương thức [save]
	@Override
	public void save() throws Exception {
		if (contributor == null) {
			contributor = new Contributor();
			// TODO sét các giá trị vào trong contributor
			invoiceDetail.add(contributor);
		}
		invoiceDetail = AccountingModelManager.getInstance().saveInvoiceDetail(invoiceDetail);
	}

	// Phần cài đặt các phương thức [edit]
	@Override
	public void edit() throws Exception {
		setEnableCompoment(true);
	}

	// Phần cài đặt các phương thức [delete]
	@Override
	public void delete() throws Exception {
		for (int i = 0; i < invoiceDetail.getContributors().size(); i++) {
			if (invoiceDetail.getContributors().get(i).getId() == contributor.getId()) {
				invoiceDetail.getContributors().remove(i);
				break;
			}
		}
		AccountingModelManager.getInstance().saveInvoiceDetail(invoiceDetail);

	}

	// Phần cài đặt các phương thức [reset]
	@Override
	public void reset() throws Exception {
		setEnableCompoment(true);
		txtMember.setText("");
		txtContributed.setText("");
		txtDescription.setText("");

		lblMember.setForeground(Color.black);
		lblPosition.setForeground(Color.black);
		lblContributed.setForeground(Color.black);
		lblDescription.setForeground(Color.black);
		lblMessager.setText(" ");
	}

	// Phần cài đặt các phương thức [refresh]
	@Override
	public void refresh() throws Exception {
		// setData();
		setEnableCompoment(false);

	}

	// Phần cài đặt các phương thức [setEnableCompoment]
	private void setEnableCompoment(boolean value) {
		txtContributed.setEnabled(value);
		txtMember.setEnabled(value);
		txtDescription.setEnabled(value);
		cboPosition.setEnabled(value);
	}

}
