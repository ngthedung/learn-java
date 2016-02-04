package com.hkt.client.swingexp.app.banhang;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelRecybin;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.accounting.entity.Bank;
import com.hkt.util.text.DateUtil;

/** GIAO DIỆN ::: BANK */
@SuppressWarnings("serial")
public class PanelBank extends MyPanel implements IDialogMain {
	private ExtendJLabel lbCode, lbName, lbParent, lbDescription, lbMessenger;
	private ExtendJTextField txtCode, txtName, txtDescription;
	private ExtendJComboBox cbParent;
	private JScrollPane scrollPaneTable, scrollIndex;
	public static String permission;
	private BankTable bankTable;
	private Bank bank = new Bank();
	private boolean restore = true;
	@SuppressWarnings("unused")
	private boolean Flag;
	String temp = "";
	int iIndex = 0;

	public PanelBank() {
		init();
		reset();
	}

	private void init() {
		lbMessenger = new ExtendJLabel("");
		lbMessenger.setForeground(Color.red);
		scrollPaneTable = new JScrollPane();
		scrollPaneTable.setOpaque(false);
		scrollPaneTable.getViewport().setOpaque(false);
		scrollPaneTable.setBorder(null);

		bankTable = new BankTable();
		bankTable.setName("tbbankTable");

		bankTable.setBanks(getObjects(0));
		bankTable.setName("tbbankTable");
		bankTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				try {
					tableOptionsMouseClicked(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		bankTable.setPreferredScrollableViewportSize(new Dimension(200, 290));
		scrollPaneTable.setViewportView(bankTable);
		
		lbDescription = new ExtendJLabel("Miêu tả");
		txtDescription = new ExtendJTextField();
		txtDescription.setPreferredSize(new Dimension(250, 53));
		txtDescription.setName("txtDescription");

		lbCode = new ExtendJLabel("Mã");
		lbName = new ExtendJLabel("Chỉ tiêu");
		lbParent = new ExtendJLabel("Nhóm cha");
		lbDescription = new ExtendJLabel("Miêu tả");
		cbParent = new ExtendJComboBox();
		for (int i=0; i< getObjects(4).size(); i++){
			cbParent.addItem(getObjects(4).get(i).getName());
		}
		for (int i=0; i< getObjects(5).size(); i++){
			cbParent.addItem(getObjects(5).get(i).getName());
		}		
		for (int i=0; i< getObjects(6).size(); i++){
			cbParent.addItem(getObjects(6).get(i).getName());
		}		
		for (int i=0; i< getObjects(7).size(); i++){
			cbParent.addItem(getObjects(7).get(i).getName());
		}

		txtCode = new ExtendJTextField();
		txtCode.setHorizontalAlignment(JTextField.RIGHT);
		txtCode.setName("txtCode");

		txtName = new ExtendJTextField();
		txtName.setName("txtName");
		

		txtDescription = new ExtendJTextField();
		txtDescription.setName("txtDescription");

		MyGroupLayout layout = new MyGroupLayout(this);
		layout.add(0, 0, lbCode);
		layout.add(0, 1, txtCode);
		layout.add(0, 2, lbName);
		layout.add(0, 3, txtName);

		layout.add(1, 0, lbParent);
		layout.add(1, 1, cbParent);
		layout.add(1, 2, lbDescription);
		layout.add(1, 3, txtDescription);
		layout.addMessenger(lbMessenger);

		// Thêm button cập nhật index
		scrollIndex = new JScrollPane();
		scrollIndex.setOpaque(false);
		scrollIndex.getViewport().setOpaque(false);
		scrollIndex.setBorder(null);
		scrollIndex.setViewportView(bankTable.getPanleButton());

		// Giao diện Button cập nhật index đi cùng với scrollPaneTable
		layout.add(2, 0, scrollIndex);
		layout.add(2, 1, scrollPaneTable);
		layout.updateGui();
		// txtCode.addMouseListener(new MouseAdapter() {
		// @Override
		// public void mouseClicked(MouseEvent e) {
		// OpenVitualkeyboard.getInstancce().vitualTextKeyboard(txtCode);
		// }
		// });
	}

	protected void tableOptionsMouseClicked(MouseEvent event) throws Exception {
		bank = bankTable.getSelectedBean();
		if (event.getClickCount() >= 2) {
			setData();
			setEnableCompoment(false);
			((DialogMain) getRootPane().getParent()).showButton(false);
		}
		refresh();
	}
	
	private String getParentCodeFromComboBox(){
		temp = cbParent.getSelectedItem().toString();
		List<Bank> bank_temp = AccountingModelManager.getInstance().getByBankName(temp);
		if (bank_temp.size() > 1){
			int iCount = 0;
			if (bank_temp.get(iCount).getBankCode().length()<5){
				bank_temp.remove(iCount);
			}
		}
		return bank_temp.get(0).getBankCode();
	}
	
	private int getIndexParentCodeFromComboBox(String parentCode){
		Bank bank_temp = new Bank();
		try {
			bank_temp = AccountingModelManager.getInstance().findByBankCode(parentCode);
		} catch (Exception e) {}
		for (int i=0; i<cbParent.getItemCount(); i++){
			String [] arr = cbParent.getItemAt(i).toString().split(" - ");
			if (arr[arr.length-1].equals(bank_temp.getName())){
				iIndex = i;
			}
		}
		return iIndex;
	}

	private Bank getData() {
		restore = true;
		try {
			if (bank == null) {
				bank = new Bank();
				Flag = true;
			}
			bank.setCode(txtCode.getText());
			bank.setName(txtName.getText());
			bank.setShowBank(true);
			bank.setParentCode(getParentCodeFromComboBox());
			System.out.println("PARENTTTT " + bank.getParentCode());
			bank.setBankCode(getParentCodeFromComboBox() + "." + txtCode.getText());
			bank.setDescription(txtDescription.getText());
			lbMessenger.setText(" ");

			return bank;
		} catch (Exception e) {

			if (bank.getBankCode() == null | bank.getBankCode().isEmpty() | bank.getName() == null | bank.getName().isEmpty() | bank.getParentCode() == null | bank.getParentCode().isEmpty()) {

				if (bank.getBankCode().isEmpty()) {
					lbCode.setForeground(Color.red);
				} else {
					lbCode.setForeground(Color.black);
				}
				if (bank.getName().isEmpty()) {
					lbName.setForeground(Color.red);
				} else {
					lbName.setForeground(Color.black);
				}
				if (bank.getParentCode().isEmpty()){
					lbParent.setForeground(Color.black);
				} else {
					lbParent.setForeground(Color.red);
				}

				lbMessenger.setText("Dữ liệu vùng đỏ bị lỗi");
				lbMessenger.setForeground(Color.red);
				lbMessenger.setVisible(true);
				return new Bank();
			}

			lbMessenger.setText("Lỗi định dạng dữ liệu");
			lbMessenger.setForeground(Color.red);
			lbMessenger.setVisible(true);
			return new Bank();
		}
	}

	private void setData() {
		txtCode.setText(bank.getCode());
		txtName.setText(bank.getName());
		txtDescription.setText(bank.getDescription());
		cbParent.setSelectedIndex(getIndexParentCodeFromComboBox(bank.getParentCode()));
	}

	private boolean checkData() {
		boolean check = true;
		/** Kiểm tra trường mã khác trống hoặc khác null */
		if (txtCode.getText() == null || txtCode.getText().isEmpty()) {
			lbCode.setForeground(Color.red);
			check = false;
		} else {
			lbCode.setForeground(Color.black);
		}

		/** Kiểm tra trường tên khác trống hoặc khác null */
		if (txtName.getText() == null || txtName.getText().isEmpty()) {
			lbName.setForeground(Color.red);
			check = false;
		} else {
			lbName.setForeground(Color.black);
		}
		
		/** Kiểm tra trường % khác trống + khác null + khác định dạng chữ */
		// Kiểm tra trường mã đã tồn tại chưa?
//		if (txtCode.isEnabled()) {
//			try {
//				Bank t = AccountingModelManager.getInstance().getBankByCode(
//						txtCode.getText());
//				if (t != null) {
//					check = false;
//					lbCode.setForeground(Color.red);
//					if (t.isRecycleBin()) {
//						PanelRecybin panel = new PanelRecybin(
//								"Mã đã bị xóa trước đó!",
//								" Chọn ĐỒNG Ý để lấy lại thông tin ban đầu hoặc THOÁT và nhập mã khác!");
//						panel.setName("Xoa");
//						DialogResto dialog = new DialogResto(panel, false, 0,
//								120);
//						dialog.setName("dlXoa");
//						dialog.setTitle("Thuế hàng hóa");
//						dialog.setLocationRelativeTo(null);
//						dialog.setModal(true);
//						dialog.setVisible(true);
//						if (panel.isDelete()) {
//							restore = false;
//							t.setRecycleBin(false);
//							AccountingModelManager.getInstance().saveBank(t);
//							lbCode.setForeground(Color.black);
//							reset();
//							return true;
//						}
//					}
//				}
//			} catch (Exception e) {
//			}
//
//		}

		/** Thông báo kết quả kiểm tra ra màn hình nếu có lỗi */
		if (!check) {
			lbMessenger.setText("Dữ liệu vùng đỏ bị lỗi");
			lbMessenger.setForeground(Color.red);
			lbMessenger.setVisible(true);
			return false;
		} else {
			lbMessenger.setText(" ");
			return true;
		}
	}

	@Override
	public void save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {
//			// Kiểm tra dữ liệu sau trước khi đổ dữ liệu vào đối tượng
//			if (checkData()) {
//				if (restore){
//					getData();
//				if (bank != null) {
//					AccountingModelManager.getInstance().savebank(bank);
//					Flag = true;
//				}
//				reset();
//				}
//			}
			
			// Lấy dữ liệu đổ vào đối tượng
		      bank = getData();
		      // Kiểm tra các trường dữ liệu sau khi đổ dữ liệu vào
		      if (!checkData()) {
		        return;
		      }
		      if(restore){
		    	  if (bank != null) {
		    		  AccountingModelManager.getInstance().saveBank(bank);
		    	        reset();
		    	      }
		      } 
		}
//		System.out.println("AAAAAAAAAAAAAA   " + getParentCodeFromComboBox());
	}

	@Override
	public void edit() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {
			setEnableCompoment(true);
			txtCode.setEnabled(false);
			Flag = false;
			restore = true;
		}
	}

	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {
			String str = "Xóa thuế hàng hóa ";
			PanelChoise pnPanel = new PanelChoise(str + bank + "?");
			pnPanel.setName("Xoa");
			DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
			dialog1.setName("dlXoa");
			dialog1.setTitle("Xóa thuế hàng hóa");
			dialog1.setLocationRelativeTo(null);
			dialog1.setModal(true);
			dialog1.setVisible(true);

			if (pnPanel.isDelete()) {
				AccountingModelManager.getInstance().deleteBank(bank);
				lbMessenger.setText("");
			}
			reset();
		} else {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		}

	}

	@Override
	public void reset() {
		txtCode.setText("");
		txtName.setText("");
		txtDescription.setText("");

		lbCode.setForeground(Color.black);
		lbName.setForeground(Color.black);
		lbMessenger.setText(" ");
		bank = new Bank();
		loadTable();
		setEnableCompoment(true);
	}

	@Override
	public void refresh() throws Exception {
		setData();
		setEnableCompoment(false);
		lbCode.setForeground(Color.black);
		lbName.setForeground(Color.black);
		lbMessenger.setText(" ");
	}

	public List<Bank> getObjects(int type) {
		try {
			return AccountingModelManager.getInstance().getBanks(type);
		} catch (Exception e) {
			return new ArrayList<Bank>();
		}
	}

	public void setObject(Bank object) throws Exception {
		this.bank = object;
		if (object == null) {
			bank = new Bank();
		}
		setData();
	}

	private void loadTable() {
		bankTable.setBanks(getObjects(0));
	}

	private void setEnableCompoment(boolean value) {
		txtCode.setEnabled(value);
		txtName.setEnabled(value);
		txtDescription.setEnabled(value);
	}
	
	  @Override
	  public void createDataTest() {
		  
		  for (int i = 4; i <=25; i++){
		  bank = new Bank();
		  bank.setBankCode("Mã HktTest" + i);
		  bank.setName("Thuế HktTest" + i);
		  bank.setDescription("Miêu tả HktTest" + i);
		  try {
			AccountingModelManager.getInstance().saveBank(bank);
		} catch (Exception e) {
			e.printStackTrace();
		}
		  }
	  }

	@Override
	public void deleteDataTest() {
		if (!PanelTestAll.runAll) {
			DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
			if (DialogTest.getInstance().isTest()) {
				try {
//					for( int i = )
					AccountingModelManager.getInstance().deleteTest("HktTest");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
