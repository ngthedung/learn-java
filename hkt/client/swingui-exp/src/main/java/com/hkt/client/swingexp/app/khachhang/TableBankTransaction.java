package com.hkt.client.swingexp.app.khachhang;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.AccountingServiceClient;
import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.IMyObserver;
import com.hkt.client.swingexp.app.core.IObservable;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.module.accounting.entity.Bank;
import com.hkt.module.accounting.entity.BankTransaction;

public class TableBankTransaction  extends BeanBindingJTable<BankTransaction> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[]							HEADERS_CO			= { "STT", "Mã", "Tên", "Tổng", "CÓ"};
//	private String[]							HEADERS_NO			= { "STT", "Tên", "Mã ngân hàng", "Tổng", "Có/Nợ"};
	private String[]							PROPERTIES	= { "id", "code", "name", "total", "type"};
	private AccountingServiceClient	clientCore	= ClientContext.getInstance().getBean(AccountingServiceClient.class);
	BankTransaction bankTransaction = new BankTransaction();
	List<BankTransaction> banks_temp;
	

	public void setBankTransactions(List<BankTransaction> banks, boolean type) {
		if (type) {
			init(HEADERS_CO, PROPERTIES, banks);
			setRowHeight(23);
			setFont(new Font("Tahoma", 0, 14));
			getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
			((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
			getColumnModel().getColumn(0).setMaxWidth(50);
			getColumnModel().getColumn(4).setMaxWidth(50);
			bankTransaction.setType(type);
			banks_temp = banks;
			addRow(type);
			getColumnModel().getColumn(1).setCellEditor(new MyEdit());
		} else {
			init(HEADERS_CO, PROPERTIES, banks);
			setRowHeight(23);
			setFont(new Font("Tahoma", 0, 14));
			getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
			((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
			getColumnModel().getColumn(0).setMaxWidth(50);
			getColumnModel().getColumn(4).setMaxWidth(50);
			banks_temp = banks;
			addRow(false);
			getColumnModel().getColumn(1).setCellEditor(new MyEdit());
		}
	}

	public TableBankTransaction(List<BankTransaction> banks, boolean type) {
		if (type) {
			init(HEADERS_CO, PROPERTIES, banks);
			setRowHeight(23);
			setFont(new Font("Tahoma", 0, 14));
			getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
			((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
			getColumnModel().getColumn(1).setCellEditor(new MyEdit());
		} else {
			init(HEADERS_CO, PROPERTIES, banks);
			setRowHeight(23);
			setFont(new Font("Tahoma", 0, 14));
			getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
			((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
			getColumnModel().getColumn(1).setCellEditor(new MyEdit());
		}
	}

	public TableBankTransaction() {
	}

	protected BankTransaction newBean() {
		return new BankTransaction();
	}

	protected void addRow(boolean type){
		BankTransaction bankTransactionAdd = new BankTransaction();
		bankTransactionAdd.setType(type);
		banks_temp.add(bankTransactionAdd);
		System.out.println(bankTransactionAdd.getName());
	}
	
	protected void showDialogOk(){
		DialogNotice.getInstace().setVisible(true);
	}
	
	protected long sum(){
		long sum = 0;
		for	(int i=0; i<getRowCount(); i++){
			sum += Long.parseLong(getValueAt(i, 3).toString());
		}		
		return sum;
	}
	
	protected void setList(int row, Object value){
		bankTransaction = banks_temp.get(row);
		bankTransaction.setCode(value.toString());
	}
	
	protected BankTransaction getBankTransaction(int row) throws Exception{
		return banks_temp.get(row);
	}
	
	protected void deleteAllRow(List<BankTransaction> banks, boolean type){
		setBankTransactions(banks, type);
	}
	
	protected void update(){
		fireTableDataChanged();
	}
	
	protected boolean isBeanEditableAt(int row, int col) {
		if (col == 0 || col == 4 || col == 2)
			return false;
		else
			return true;
	}
	

	public void saveIndex() {
		try {
			for (int i = 0; i < getBeans().size(); i++) {
				getBeans().get(i).setIndex(i);
				clientCore.saveBankTransaction((getBeans().get(i)));
			}
			DialogNotice.getInstace().setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


//class MyEdit1 extends JComboBox implements TableCellEditor{
//    
//    /**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	protected EventListenerList listenerList = new EventListenerList();
//    protected ChangeEvent changeEvent = new ChangeEvent(this);
//    List<Bank> lsBank = AccountingModelManager.getInstance().getBanks(0);
//
//    public MyEdit(){
//        super();
//        for (int i=0; i < lsBank.size(); i++){
//        	addItem(lsBank.get(i).getName() + "  " + lsBank.get(i).getCode());
//        }
//        addItem(new Bank());
//        addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                fireEditingStopped();
//            }
//        });
//    }
//
//    @Override
//    public void addCellEditorListener(CellEditorListener listener) {
//        listenerList.add(CellEditorListener.class, listener);
//    }
//
//    @Override
//    public void removeCellEditorListener(CellEditorListener listener) {
//        listenerList.remove(CellEditorListener.class, listener);
//    }
//    
//    protected void fireEditingStopped(){
//        CellEditorListener listener;
//        Object[] listeners = listenerList.getListenerList();
//        for(int i=0; i<listeners.length; i++){
//            if(listeners[i] == CellEditorListener.class){
//                listener = (CellEditorListener) listeners[i+1];
//                listener.editingStopped(changeEvent);
//            }
//        }
//    }
//
//    protected void fireEditingCanceled(){
//        CellEditorListener listener;
//        Object[] listeners = listenerList.getListenerList();
//        for(int i=0; i< listeners.length; i++){
//            if(listeners[i] == CellEditorListener.class){
//                listener = (CellEditorListener) listeners[i+1];
//                listener.editingCanceled(changeEvent);
//            }
//        }
//    }
//
//    @Override
//    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
//		if (value instanceof Integer) {
//			setSelectedIndex(((Integer) value).intValue());
//		}
//        return this;
//    }
//
//    @Override
//    public Object getCellEditorValue() {
//    	BankTransaction bankTransaction = new BankTransaction();
//    	bankTransaction.setName(lsBank.get(getSelectedIndex()).getName());
//    	if (getSelectedIndex() == (lsBank.size())){
//    		return "";
//    	} else {
//    		return lsBank.get(getSelectedIndex()).getName();
//    	}
//    }
//
//    @Override
//    public boolean isCellEditable(EventObject anEvent) {
//        return true;
//    }
//
//    @Override
//    public boolean shouldSelectCell(EventObject anEvent) {
//        return true;
//    }
//
//    @Override
//    public boolean stopCellEditing() {
//        fireEditingStopped();
//        return true;
//    }
//
//    @Override
//    public void cancelCellEditing() {
//        fireEditingCanceled();
//    }
// }

class MyEdit extends TextPopup<Bank> implements TableCellEditor, IMyObserver, IObservable{
	private static final long serialVersionUID = 1L;
	protected EventListenerList listenerList = new EventListenerList();
    protected ChangeEvent changeEvent = new ChangeEvent(this);
    Bank bank;

    @SuppressWarnings("unchecked")
	public MyEdit(){
        super(Bank.class);
        setData(AccountingModelManager.getInstance().getBanks(0));
		addObserver(this);
		addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }
    
 
    
	@Override
	public void update(Object o, Object arg) {
			if(o instanceof Bank){
				Bank b = (Bank)o;
				try {
					bank = AccountingModelManager.getInstance().getBankByCode(getItem().getCode());
		    } catch (Exception e2) {
		    	}
			}
		}
    @Override
    public void addCellEditorListener(CellEditorListener listener) {
        listenerList.add(CellEditorListener.class, listener);
    }

    @Override
    public void removeCellEditorListener(CellEditorListener listener) {
        listenerList.remove(CellEditorListener.class, listener);
    }
    
    protected void fireEditingStopped(){
        CellEditorListener listener;
        Object[] listeners = listenerList.getListenerList();
        for(int i=0; i<listeners.length; i++){
            if(listeners[i] == CellEditorListener.class){
                listener = (CellEditorListener) listeners[i+1];
                listener.editingStopped(changeEvent);
            }
        }
    }

    protected void fireEditingCanceled(){
        CellEditorListener listener;
        Object[] listeners = listenerList.getListenerList();
        for(int i=0; i< listeners.length; i++){
            if(listeners[i] == CellEditorListener.class){
                listener = (CellEditorListener) listeners[i+1];
                listener.editingCanceled(changeEvent);
            }
        }
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    	System.out.println("VALUEEEE   "  + value);
    	if (value instanceof Integer){
    		try {
				bank = AccountingModelManager.getInstance().getBankByCode(value.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
//    	if (getItem()!=null) {
//    		try {
//    			bank = AccountingModelManager.getInstance().getBankByCode(getItem().getCode());
//    	    if (bank == null) {
//    	    	bank.setCode("");
//    	    }
//    	    } catch (Exception ex) {
//    	    	ex.printStackTrace();
//    	    }
//    	    System.out.println("BANKKKKKK :::: " + bank.getCode());
//    	}
        return this;
    }

    @Override
    public Object getCellEditorValue() {
    	if (bank.getCode()==null){
    		return "";
    	} else {
    		return bank.getCode();
    	}
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        fireEditingStopped();
        return true;
    }

    @Override
    public void cancelCellEditing() {
        fireEditingCanceled();
    }
}