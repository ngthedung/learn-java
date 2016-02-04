package com.hkt.client.swingexp.app.khachhang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.IMyObserver;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.module.accounting.entity.Bank;
import com.hkt.module.accounting.entity.BankTransaction;
import com.hkt.module.partner.customer.entity.Credit;


/** GIAO DIỆN ::: GIAO DỊCH NGÂN HÀNG */
@SuppressWarnings("serial")
public class PanelBankTransaction extends JPanel implements IDialogResto, IMyObserver {
	private ExtendJLabel lbTransactionCode, lbTotal_NO, lbTotal_CO, lbMessenger;
	private ExtendJTextField txtTransactionCode;
	private static ExtendJTextField txtTotal_CO, txtTotal_NO;
	private JScrollPane scrollPaneTable_CO, scrollPaneTable_NO;
	private JPanel panelTable, panelTop, panelCenter, panelBotton, panelBotton_CO, panelBotton_NO;
	public static String permission;
	private TextPopup<Bank> txtUpBank;
	private Credit credit;
	private static TableBankTransaction bankTransactionTable_CO, bankTransactionTable_NO;
	private static BankTransaction bankTransaction = new BankTransaction();
//	private boolean restore = true;
//	@SuppressWarnings("unused")
	private boolean check, check_save;
	List<Bank> lsBank ;
	Bank bank;
	private static int old_mouse_CO = 0;
	private static int new_mouse_CO = 0;
	private static int old_mouse_NO = 0;
	private static int new_mouse_NO = 0;
//	= AccountingModelManager.getInstance().getAllBank();
	
	public PanelBankTransaction() {
		init();
	}
	
	private void init() {
		scrollPaneTable_CO = new JScrollPane();
		scrollPaneTable_CO.setOpaque(false);
		scrollPaneTable_CO.getViewport().setOpaque(false);
		scrollPaneTable_CO.setBorder(null);
		
		scrollPaneTable_NO = new JScrollPane();
		scrollPaneTable_NO.setOpaque(false);
		scrollPaneTable_NO.getViewport().setOpaque(false);
		scrollPaneTable_NO.setBorder(null);

		bankTransactionTable_CO = new TableBankTransaction();
		bankTransactionTable_CO.setName("bankTransactionTable_CO");
		
		bankTransactionTable_NO = new TableBankTransaction();
		bankTransactionTable_NO.setName("bankTransactionTable_NO");
		
		bankTransactionTable_CO.setBankTransactions(new ArrayList<BankTransaction>(), true);
		scrollPaneTable_CO.setViewportView(bankTransactionTable_CO);
//		bankTransactionTable_CO.getColumnModel().getColumn(1).setCellRenderer(new MyRender());
//		bankTransactionTable_CO.getColumnModel().getColumn(1).setCellEditor(new MyEdittt());
		
		bankTransactionTable_NO.setBankTransactions(new ArrayList<BankTransaction>(), false);
		scrollPaneTable_NO.setViewportView(bankTransactionTable_NO);
		
		panelTable = new JPanel();
		panelTop = new JPanel();
		panelBotton = new JPanel();
		panelBotton_CO = new JPanel();
		panelBotton_NO = new JPanel();
		panelCenter = new JPanel();
		panelCenter.setLayout(new GridLayout(1, 2, 10, 0));
		panelTable.setLayout(new BorderLayout(0, 0));
		panelTop.setLayout(new FlowLayout(10, 10, 10));
		panelBotton.setLayout(new GridLayout(1, 2, 0, 10));
		panelBotton_CO.setLayout(new FlowLayout(10, 10, 10));
		panelBotton_NO.setLayout(new FlowLayout(10, 10, 10));
		panelCenter.add(scrollPaneTable_CO);
		panelCenter.add(scrollPaneTable_NO);
		
		
		lbTransactionCode = new ExtendJLabel("Mã giao dịch:");
		lbMessenger = new ExtendJLabel("");
		lbTotal_CO = new ExtendJLabel("Tổng tiền CÓ: ");
		lbTotal_NO = new ExtendJLabel("Tổng tiền NỢ: ");

		txtTransactionCode = new ExtendJTextField();
		txtTransactionCode.setName("txtTransactionCode");
		txtTotal_CO = new ExtendJTextField();
		txtTotal_CO.setName("txtTotal_CO");
		txtTotal_CO.setEditable(false);
		txtTotal_CO.setText("0 VNĐ");
		txtTotal_NO = new ExtendJTextField();
		txtTotal_NO.setName("txtTotal_NO");
		txtTotal_NO.setEditable(false);
		txtTotal_NO.setText("0 VNĐ");
		
		
		txtUpBank = new TextPopup<Bank>(Bank.class);
		try {
			txtUpBank.setData(AccountingModelManager.getInstance().getBanks(0));
		} catch (Exception e) {
		}
		txtUpBank.addObserver(this);
		
		panelTop.add(lbTransactionCode);
		panelTop.add(txtTransactionCode);
//		panelTop.add(txtUpBank);
		panelTop.add(lbMessenger);
		

		
		panelBotton_CO.add(lbTotal_CO);
		panelBotton_CO.add(txtTotal_CO);
		panelBotton_NO.add(lbTotal_NO);
		panelBotton_NO.add(txtTotal_NO);
		panelBotton.add(panelBotton_CO);
		panelBotton.add(panelBotton_NO);

		MyGroupLayout layout = new MyGroupLayout(this);
		panelTable.add(panelTop, BorderLayout.PAGE_START);
		panelTable.add(panelCenter, BorderLayout.CENTER);
		panelTable.add(panelBotton, BorderLayout.PAGE_END);
		layout.add(0, 0, panelTable);
		layout.updateGui();
		
		bankTransactionTable_CO.addMouseListener(new MouseAdapter(){
	    	@Override
	        public void mousePressed(MouseEvent event){
	    		new_mouse_CO = bankTransactionTable_CO.rowAtPoint(event.getPoint());
	            txtTotal_CO.setText(bankTransactionTable_CO.sum() + " VNĐ");
	        	if (new_mouse_CO != old_mouse_CO){
	        		old_mouse_CO = new_mouse_CO;
	        	}
	    		try {
	        	   addRow_CO(event);
	    		} catch (Exception e) {
					e.printStackTrace();
				}
	    	}
	    	
	    	public void mouseExited(MouseEvent event){
	    		updateTableCo();
	    	}
	    });
		
		
	    bankTransactionTable_NO.addMouseListener(new MouseAdapter(){
	    	@Override
	        public void mousePressed(MouseEvent event){
	    		new_mouse_NO = bankTransactionTable_NO.rowAtPoint(event.getPoint());
	            txtTotal_NO.setText(bankTransactionTable_NO.sum() + " VNĐ");
	        	if (new_mouse_NO != old_mouse_NO){
	        		old_mouse_NO = new_mouse_NO;
	        	}
	    		try {
	        	   addRow_NO(event);
	    		} catch (Exception e) {
					e.printStackTrace();
				}
	        }
	    	
	    	public void mouseExited(MouseEvent event){
	    		updateTableNo();
	    	}
	    });

	    
	    //Di chuột tự động update CODE tương ứng BankName
		addMouseMotionListener(new MouseMotionAdapter() {
	    	public void mouseMoved(MouseEvent event){
	    		updateTableNo();
	    		updateTableCo();
	    	}
		});
		
		bankTransactionTable_NO.addMouseMotionListener(new MouseMotionAdapter() {
	    	public void mouseMoved(MouseEvent event){
	    		updateTableNo();
	    		updateTableCo();
//	    		System.out.println(bankTransactionTable_NO.getValueAt(0, 3));
	    	}
		});
		
		bankTransactionTable_CO.addMouseMotionListener(new MouseMotionAdapter() {
	    	public void mouseMoved(MouseEvent event){
	    		updateTableNo();
	    		updateTableCo();
	    	}
		});
		
		scrollPaneTable_NO.addMouseMotionListener(new MouseMotionAdapter() {
	    	public void mouseMoved(MouseEvent event){
	    		updateTableNo();
	    		updateTableCo();
	    	}
		});
		//End Di chuột tự động update CODE tương ứng BankName
	}

    private static void addRow_CO(MouseEvent event) throws Exception{
//    	bankTransactionTable_CO.update();
    	if (bankTransactionTable_CO.getValueAt(bankTransactionTable_CO.getRowCount()-1, 1) != null
    			&& bankTransactionTable_CO.getValueAt(bankTransactionTable_CO.getRowCount()-1, 2) != null
    			&& bankTransactionTable_CO.getValueAt(bankTransactionTable_CO.getRowCount()-1, 3) != null){
        if (bankTransactionTable_CO.rowAtPoint(event.getPoint())==bankTransactionTable_CO.getRowCount()-1 && event.getClickCount() >1) {
        	bankTransactionTable_CO.addRow(true);
        }}
    	if (bankTransactionTable_CO.rowAtPoint(event.getPoint())!=0)
    	bankTransactionTable_CO.setValueAt("", bankTransactionTable_CO.getRowCount()-1, 1);
    }
    
    private static void addRow_NO(MouseEvent event) throws Exception{
//    	bankTransactionTable_NO.update();
//        txtTotal_NO.setText(bankTransactionTable_NO.sum() + " VNĐ");
        if (bankTransactionTable_NO.rowAtPoint(event.getPoint())==bankTransactionTable_NO.getRowCount()-1 && event.getClickCount() >1) {
        	bankTransactionTable_NO.addRow(false);
        }
    }
    
//    private static void autoUpdate(KeyEvent e){
//                if(!(e.getKeyChar() + "").equals("")){
//                	System.out.println(e.getKeyChar());
//                	bankTransactionTable_NO.update();
//                }
//   }
 
    
    private boolean checkData(){
		check = true;
		
		if (txtTransactionCode.getText().equals("")){
			lbTransactionCode.setForeground(Color.red);
			check = false;
		} else {
			lbTransactionCode.setForeground(Color.black);
		}
		
		
		if (txtTotal_CO.getText().substring(0, txtTotal_CO.getText().length()-4).startsWith("0")){
			lbTotal_CO.setForeground(Color.red);
			check = false;
		} else {
			lbTotal_CO.setForeground(Color.black);
		}
		
		if (txtTotal_NO.getText().substring(0, txtTotal_NO.getText().length()-4).startsWith("0")){
			lbTotal_NO.setForeground(Color.red);
			check = false;
		} else {
			lbTotal_NO.setForeground(Color.black);
		}
		
		
		if (!txtTotal_CO.getText().substring(0, txtTotal_CO.getText().length()-4).equals(txtTotal_NO.getText().substring(0, txtTotal_NO.getText().length()-4))){
			lbTotal_CO.setForeground(Color.red);
			lbTotal_NO.setForeground(Color.red);
			return false;
		}

		
		if (!check) {
			lbMessenger.setText("Dữ liệu vùng đỏ bị lỗi");
			lbMessenger.setForeground(Color.red);
//			lbMessenger.setVisible(true);
			return false;
		} else {
			lbMessenger.setText(" ");
			return true;
		}
    }

    
    private void reset(){
		bankTransactionTable_CO.deleteAllRow(new ArrayList<BankTransaction>(), true);
		bankTransactionTable_NO.deleteAllRow(new ArrayList<BankTransaction>(), false);
		txtTransactionCode.setText("");
		txtTotal_CO.setText("0 VNĐ");
		txtTotal_NO.setText("0 VNĐ");
		check_save = false;
    }
    
    private void updateTableNo(){
    	if (check_save == false){
		txtTotal_NO.setText(bankTransactionTable_NO.sum() + " VNĐ");
		try {
			setBankCode(bankTransactionTable_NO, old_mouse_NO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		bankTransactionTable_NO.update();
    	}
    }
    
    private void updateTableCo(){
    	if (check_save == false){
		txtTotal_CO.setText(bankTransactionTable_CO.sum() + " VNĐ");
		try {
			setBankCode(bankTransactionTable_CO, old_mouse_CO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		bankTransactionTable_CO.update();
    	}
    }
    
    private void setBankCode( TableBankTransaction bankTransactionTable, int row) throws Exception{
			bankTransaction = bankTransactionTable.getBankTransaction(row);
//			lsBank = AccountingModelManager.getInstance().getByBankName(bankTransaction.getName());
//			lsBank = AccountingModelManager.getInstance().getBanks(0);

//			System.out.println(AccountingModelManager.getInstance().findByBankCode("110"));
//			for (int i=0; i<lsBank.size(); i++){
//				if (!lsBank.get(i).getName().equals(bankTransaction.getName())){
//					lsBank.remove(i);
//				}
//			}
//			if (bankTransaction.getBankCode()==null || bankTransaction.getBankCode()==""){
//				bankTransaction.setName("");
//				bankTransactionTable.setValueAt("", row, 2);
//			}else {
			try {
				bank = AccountingModelManager.getInstance().getBankByCode(bankTransaction.getCode());
			} catch (Exception ex){}
			if (bank == null){
				bankTransactionTable.setValueAt("", row, 2);
			}else {
//				System.out.println("INDEXXX " + lsBank.get(lsBank.size()-1));
//				bankTransactionTable.setList(row, lsBank.get(lsBank.size()-1).getName());
//				bankTransactionTable.setValueAt(lsBank.get(lsBank.size()-1).getName(), row, 2);
//				bankTransaction.setBankCode(lsBank.get(lsBank.size()-1).getParentCode());
//				bankTransactionTable.setList(row, bank.getName());
				bankTransactionTable.setValueAt(bank.getName(), row, 2);
				bankTransaction.setBankCode(bank.getParentCode());
				}
    }
	
	@Override
	public void Save() throws Exception {
		check_save = true;
		System.out.println(bankTransactionTable_CO.getValueAt(0, 2));
		bankTransactionTable_CO.update();
		bankTransactionTable_NO.update();
		if (checkData()){
			for (int i=0; i<bankTransactionTable_CO.getRowCount(); i++){
				bankTransaction = bankTransactionTable_CO.getBankTransaction(i);
				bankTransaction.setTransactionCode(txtTransactionCode.getText());
				try {
				if (bankTransaction.getName().equals(null) || bankTransaction.getCode().equals(null) 
						|| bankTransaction.getTotal() < 0){}
					AccountingModelManager.getInstance().saveBankTransaction(bankTransaction);
				} catch(Exception e) {
				}
				
			}
			for (int i=0; i<bankTransactionTable_NO.getRowCount(); i++){
				bankTransaction = bankTransactionTable_NO.getBankTransaction(i);
				bankTransaction.setTransactionCode(txtTransactionCode.getText());
				try {
				if (bankTransaction.getName().equals(null) || bankTransaction.getCode().equals(null) 
						|| bankTransaction.getTotal() < 0){}
					AccountingModelManager.getInstance().saveBankTransaction(bankTransaction);
				} catch(Exception e) {
				}
			}
			reset();
			bankTransactionTable_CO.showDialogOk();
//			init();
		}
	}

	@Override
	public void update(Object o, Object arg) {
		// TODO Auto-generated method stub
	}
}

@SuppressWarnings("serial")
class ExtendJLabel extends JLabel {
	
	public ExtendJLabel(String text) {
		setText(text);
		init();
	}
	
	public ExtendJLabel(String text, int horizontalAlignment) {
		setText(text);
		setHorizontalAlignment(horizontalAlignment);
		init();
	}
  
	public void init(){
		setFont(new Font("Tahoma", Font.BOLD, 14));
//		setPreferredSize(new Dimension(200, 23));
		setSize(86, 23);
	}
}
