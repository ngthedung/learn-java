package com.hkt.client.swingexp.app.khuvucbanhang;

/**
 * author Khanhdd
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialog;
import com.hkt.client.swingexp.app.khuvucbanhang.list.PanelListDepartmentPrint;
import com.hkt.client.swingexp.app.khuvucbanhang.list.TableListDepartmentPrintMachine;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.PrintMachineModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.print.entity.PrintMachine;
import com.hkt.module.restaurant.entity.Table;


@SuppressWarnings("serial")
public class PrintMachineDepartment extends JPanel implements IDialogResto{

	private PanelTop panelTop;
	private PanelCenter panelCenter;
	private PanelListDepartmentPrint panelListDepartment;
	private IDialogResto iDialogResto;
	private TableListDepartmentPrintMachine tableDepartment;
	private List<AccountGroup> listAccGroup;
	private PrintMachine printMachine;
	private String template;
	private ExtendJComboBox cbPrinter, cbPaperSize;
	@SuppressWarnings("unused")
	private List<Table> listTable;
	public static String        permisson;
	
	public PrintMachineDepartment() {

		setLayout(new BorderLayout(0, 10));
		setOpaque(false);
		iDialogResto = this;
		panelTop = new PanelTop();		
		panelCenter = new PanelCenter();
		
		add(panelTop, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);
		
		printMachine = (PrintMachine)cbPrinter.getSelectedItem();
		tableDepartment.setAccountGroup(printMachine.getAccountGroups());
		tableDepartment.setName("tableDepartment");
		cbPaperSize.setSelectedItem((String)printMachine.getTemplate());
		listAccGroup = printMachine.getAccountGroups();
	}
	
	protected class PanelTop extends JPanel {
		private JPanel pnTop1, pnTop2, pnTop3; 
		@SuppressWarnings("unused")
		private ExtendJLabel lblPrinter, lblDescription, lblPaperSize;
		private ExtendJTextField txtDescription;
		private String[] paperSize = {PrintMachine.TEMPLATE_A4, PrintMachine.TEMPLATE_A5, PrintMachine.TEMPLATE_A6,
				PrintMachine.TEMPLATE_K57, PrintMachine.TEMPLATE_K80};
		
	public PanelTop() {
		super();
		setOpaque(false);
		setLayout(new GridLayout(3, 0, 0, 10));
		init();
		
		cbPrinter.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				printMachine = (PrintMachine)cbPrinter.getSelectedItem();
				tableDepartment.setAccountGroup(printMachine.getAccountGroups());
				cbPaperSize.setSelectedItem((String)printMachine.getTemplate());
				listAccGroup = printMachine.getAccountGroups();
			}
		});
		
		cbPaperSize.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				template = (String)cbPaperSize.getSelectedItem();
			}
		});		
	}
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void init(){
		pnTop1 = new JPanel();
		pnTop2 = new JPanel();
		pnTop3 = new JPanel();	
		JLabel lblPrinter, lblDescription, lblPaperSize;
		
		pnTop1.setOpaque(false);
		pnTop2.setOpaque(false);
		pnTop3.setOpaque(false);
				
		pnTop1.setLayout(new BorderLayout(0, 0));
		pnTop2.setLayout(new BorderLayout(0, 0));
		pnTop3.setLayout(new BorderLayout(0, 0));
		
		lblPrinter = new JLabel("Máy in");
		lblPrinter.setPreferredSize(new Dimension(130, 23));
		lblPrinter.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		lblDescription = new JLabel("Miêu tả");
		lblDescription.setPreferredSize(new Dimension(130, 23));
		lblDescription.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		lblPaperSize = new JLabel("Khổ giấy");
		lblPaperSize.setPreferredSize(new Dimension(130, 23));
		lblPaperSize.setFont(new Font("Tahoma", Font.BOLD, 14));

				
		cbPrinter = new ExtendJComboBox();
		cbPaperSize = new ExtendJComboBox();
		cbPrinter.setName("cbPrinter");
		cbPaperSize.setName("cbPaperSize");
				
		txtDescription = new ExtendJTextField();
		
		this.add(pnTop1);
		this.add(pnTop2);
		this.add(pnTop3);
		
		pnTop1.add(lblPrinter, BorderLayout.LINE_START);
		pnTop1.add(cbPrinter, BorderLayout.CENTER);
		pnTop2.add(lblPaperSize, BorderLayout.LINE_START);
		pnTop2.add(cbPaperSize, BorderLayout.CENTER);
		pnTop3.add(lblDescription, BorderLayout.LINE_START);
		pnTop3.add(txtDescription, BorderLayout.CENTER);

		
		try {
			List<PrintMachine> listPrintMachine = PrintMachineModelManager.getInstance().getPrintMachines();
			DefaultComboBoxModel<PrintMachine> modelPrintMachine = new DefaultComboBoxModel(listPrintMachine.toArray());
			cbPrinter.setModel(modelPrintMachine);
		} catch (Exception e) {
			e.printStackTrace();
		}
		DefaultComboBoxModel<String> modelPaperSize = new DefaultComboBoxModel<String>(paperSize);
		cbPaperSize.setModel(modelPaperSize);
		
		template = (String)cbPaperSize.getSelectedItem();
		printMachine = (PrintMachine)cbPrinter.getSelectedItem();
		}
		
	}
	
protected class PanelCenter extends JPanel {
		
		private JPanel pnCenter1, pnCenter2;
		@SuppressWarnings("unused")
		private ExtendJButton btnEdit;
		private JScrollPane scrollPaneTable;
		
		public PanelCenter(){
			super();
			setOpaque(false);
			setLayout(new BorderLayout(21, 0));
			init();
		}
		
		public void init(){
			pnCenter1 = new JPanel();
			pnCenter2 = new JPanel();
			JButton btnEdit;
			pnCenter1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
			pnCenter2.setLayout(new BorderLayout(0, 0));
			
			pnCenter1.setOpaque(false);
			pnCenter2.setOpaque(false);
			
			btnEdit = new JButton("Sửa");
			btnEdit.setName("btnEdit");
			btnEdit.setPreferredSize(new Dimension(109, 35));
			btnEdit.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/modify1.png")));
			btnEdit.addMouseListener(new MouseEventClickButtonDialog("modify1.png", "modify2.png", "modify3.png"));
			btnEdit.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
//						if (UIConfigModelManager.getInstance().getPermission(permisson) == Capability.READ) {
//						      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
//						      return;
//						    } else {
						panelListDepartment = new PanelListDepartmentPrint();
						panelListDepartment.setName("panel");
						if(listAccGroup == null){
		                	listAccGroup = new ArrayList<AccountGroup>();
		                }
		                panelListDepartment.getData(listAccGroup);
		                panelListDepartment.setParentForm(iDialogResto);
		                
		                if (Toolkit.getDefaultToolkit().getScreenSize().width <= 1024) {
		                DialogResto dialog = new DialogResto(panelListDepartment, false, 880, 450);
		                dialog.setName("dialog");
		                dialog.setTitle("Chọn phòng ban");
		                dialog.setIcon("mayin1.png");
		                dialog.setModal(true);
		                dialog.setLocationRelativeTo(null);
		                dialog.setVisible(true);
						    } else {
						    	DialogResto dialog = new DialogResto(panelListDepartment, false, 950, 450);
				                dialog.setName("dialog");
				                dialog.setTitle("Chọn phòng ban");
				                dialog.setIcon("mayin1.png");
				                dialog.setModal(true);
				                dialog.setLocationRelativeTo(null);
				                dialog.setVisible(true);
						    }
//						    }
		              } catch (Exception ex) { 
		                ex.printStackTrace();
		              }  
				}
			});
			tableDepartment = new TableListDepartmentPrintMachine(new ArrayList<AccountGroup>()); 

			scrollPaneTable = new JScrollPane();
			scrollPaneTable.setViewportView(tableDepartment);
			
			pnCenter1.add(btnEdit);
			pnCenter2.add(scrollPaneTable, BorderLayout.CENTER);
			
			this.add(pnCenter1, BorderLayout.WEST);
			this.add(pnCenter2, BorderLayout.CENTER);
		}
	}

public void loadTable(List<AccountGroup> listAccGroup){
	if(listAccGroup != null)
		this.listAccGroup = listAccGroup;
	else listAccGroup = new ArrayList<AccountGroup>();
	tableDepartment.setAccountGroup(listAccGroup);
}

private void setData(){		
	printMachine.setTemplate(template);
	printMachine.setAccountGroups(listAccGroup);
	printMachine.setModifiedTime(new Date());
}

@Override
public void Save() throws Exception {
//	if(UIConfigModelManager.getInstance().getPermission(getToolTipText())==Capability.READ){
//		JOptionPane.showMessageDialog(null,"Bạn chưa được cấp quyền này !");
//	}
	//if(printMachine != null && listLocation.size() != 0){
		setData();
		PrintMachineModelManager.getInstance().savePrintMachine(printMachine);
		DialogNotice.getInstace().setVisible(true);
	//}
	
}
	
}
