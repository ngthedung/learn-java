package com.hkt.client.swingexp.app.core;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.GenericOptionServiceClient;
import com.hkt.client.swing.widget.model.AutoCompleteItem;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.GuiModelManager;
import com.hkt.module.config.generic.Option;

public class TypeInvoiceJComboBox extends JComboBox<Option> {
  private ClientContext clientContext = ClientContext.getInstance();
  private GenericOptionServiceClient client = clientContext.getBean(GenericOptionServiceClient.class);
  private List<Option> options;

  public TypeInvoiceJComboBox() {
    super();
    setFont(new Font("Tahoma", 0, 14));
    setOpaque(false);
    setPreferredSize(new Dimension(200, 23));

    options = new ArrayList<Option>();
    try {
      options = client.getOptionGroup("accounting", "AccountingService", "type_invoice").getOptions();
      for (int i = 0; i < options.size();) {
        if (options.get(i).getCode().equals(AccountingModelManager.typeBanHang)
            || options.get(i).getCode().equals(AccountingModelManager.typeDatHang)
            || options.get(i).getCode().equals(AccountingModelManager.typeTraHang)) {
          options.remove(i);
        } else {
          i++;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    setModel(new DefaultComboBoxModel(options.toArray()));
    addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
        	System.out.println("???"+e.getButton());
         if(e.getButton()==3){
           new GuiModelManager<Option>().viewDialog(TypeInvoiceJComboBox.this,Option.class);
           resetData();
         }
        }
      });
  }
  
  private void resetData(){
    options = new ArrayList<Option>();
    try {
      options = client.getOptionGroup("accounting", "AccountingService", "type_invoice").getOptions();
      for (int i = 0; i < options.size();) {
        if (options.get(i).getCode().equals(AccountingModelManager.typeBanHang)
            || options.get(i).getCode().equals(AccountingModelManager.typeDatHang)
            || options.get(i).getCode().equals(AccountingModelManager.typeTraHang)) {
          options.remove(i);
        } else {
          i++;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    setModel(new DefaultComboBoxModel(options.toArray()));
  }

  @Override
  public Option getItemAt(int index) {
    return options.get(index);
  }

  public Option getSelectedType() {
    return (Option) getSelectedItem();
  }

}
