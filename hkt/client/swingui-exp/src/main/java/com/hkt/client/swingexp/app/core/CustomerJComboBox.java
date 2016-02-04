package com.hkt.client.swingexp.app.core;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.GuiModelManager;
import com.hkt.module.partner.customer.entity.Customer;

public class CustomerJComboBox extends JComboBox<Customer> {
  private List<Customer>        accounts;
  
  public CustomerJComboBox() {
    super();
    setFont(new Font("Tahoma", 0, 14));
    setOpaque(false);
    setPreferredSize(new Dimension(200, 23));
    resetData();
    addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
         if(e.getButton()==3){
           new GuiModelManager<Customer>().viewDialog(CustomerJComboBox.this,Customer.class);
           resetData();
         }
        }
      });
  }

  public void setData(List<Customer> listCus) {
    this.accounts = listCus;
    this.accounts.add(0,null);
    setModel(new DefaultComboBoxModel(accounts.toArray()));
  }
  
  public void removeIndex(int index){
    accounts.remove(index);
    setModel(new DefaultComboBoxModel(accounts.toArray()));
  }
  
  public void resetData(){
    accounts = new ArrayList<Customer>();
    try {
      accounts = CustomerModelManager.getInstance().getCustomers(false);
    } catch (Exception e) {
      e.printStackTrace();
    }
    accounts.add(0, null);
    setModel(new DefaultComboBoxModel(accounts.toArray()));
  }

  public Customer getSelectedValue() {
    return (Customer) getSelectedItem();
  }

  @Override
  public Customer getItemAt(int index) {
    return accounts.get(index);
  }

  public void setSelectItem(long loginId) {
    for (int i = 0; i < this.getItemCount(); i++) {
      if (this.getItemAt(i)!=null&&this.getItemAt(i).getId() == loginId) {
        this.setSelectedIndex(i);
        break;
      }
    }
  }
  
  public void setSelectItemByID(String loginId) {
	    for (int i = 0; i < this.getItemCount(); i++) {
	      if (this.getItemAt(i)!=null&&this.getItemAt(i).getId().toString() == loginId) {
	        this.setSelectedIndex(i);
	        break;
	      }
	    }
	  }

  public Customer getSelectedCustomer() {
    try {
      return (Customer) getSelectedItem();
    } catch (Exception ex) {
      return null;
    }
  }

}
