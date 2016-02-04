package com.hkt.client.swingexp.app.core;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.hkt.client.swingexp.app.khachhang.PanelGroupCustomers;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.GuiModelManager;
import com.hkt.module.account.entity.AccountGroup;

public class CustomerGroupJComboBox extends JComboBox<AccountGroup> {
  private List<AccountGroup>   accountGroups;
  private boolean pressCtrl = false;

  public CustomerGroupJComboBox() {
		super();
		setFont(new Font("Tahoma", 0, 14));
		setOpaque(false);
    resetData();
    addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
         if(e.getButton()==3){
           new GuiModelManager<AccountGroup>().viewDialog(CustomerGroupJComboBox.this,AccountGroup.class);
           resetData();
         }
        }
      });
    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_CONTROL){
          pressCtrl = true;
        }
      }
      @Override
      public void keyReleased(KeyEvent e) {
        pressCtrl = false;
      }
    });
    
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() >= 2 && pressCtrl){
          try {
            PanelGroupCustomers panelGroupCustomers = new PanelGroupCustomers();
            DialogMain dialog = new DialogMain(panelGroupCustomers);
            dialog.setTitle("Quản lý nhóm khách hàng");
            dialog.setModal(true);
            dialog.setVisible(true);
            resetData();
          } catch(Exception ex){
            ex.printStackTrace();
          }
        }
      }
    });
  }
  
  public void resetData(){
//    holder = new ArrayList<AutoCompleteItem<AccountGroup>>();
//    holder.add(0, null);
    accountGroups = new ArrayList<AccountGroup>();
		try {
			AccountGroup rootGroupCustomer = CustomerModelManager.getInstance().getRootGroupCustomer();
			List<AccountGroup> getChildCustomer = AccountModelManager.getInstance().getAllChildrensByPath(rootGroupCustomer.getPath());
			if (getChildCustomer != null)
				accountGroups.addAll(getChildCustomer);
		} catch (Exception e) {
			e.printStackTrace();
		}

//    for (AccountGroup sel : accountGroups) {
//      holder.add(new AutoCompleteItem<AccountGroup>(sel.getLabel(), sel));
//    }
		accountGroups.add(0, null);
    setModel(new DefaultComboBoxModel(accountGroups.toArray()));
  }
  
  public void removeIndex(int index){
    accountGroups.remove(index);
    setModel(new DefaultComboBoxModel(accountGroups.toArray()));
  }

  public void setData(List<AccountGroup> list) {
    accountGroups = list;
    accountGroups.add(0, null);
    setModel(new DefaultComboBoxModel(accountGroups.toArray()));
  }

  @Override
  public AccountGroup getItemAt(int index) {
    return accountGroups.get(index);
  }

  public AccountGroup getSelectedGroupCustomer() {
    try {
      return (AccountGroup) getSelectedItem();
    } catch (Exception ex) {
      return null;
    }
  }
  
  public void setSelectCustomer(String path){
    for(int i = 1; i < accountGroups.size(); i++){
      if(accountGroups.get(i)!=null && accountGroups.get(i).getPath().equals(path)){
        setSelectedIndex(i);
        break;
      }
    }
  }
}
