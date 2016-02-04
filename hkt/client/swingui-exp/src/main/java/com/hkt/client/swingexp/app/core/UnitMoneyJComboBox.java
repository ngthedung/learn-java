package com.hkt.client.swingexp.app.core;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.hkt.client.swingexp.model.GuiModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.module.config.locale.Currency;

public class UnitMoneyJComboBox extends JComboBox<Currency> implements IMyObserver{
  private List<Currency> currencys;

  public UnitMoneyJComboBox() {
    super();
    setFont(new Font("Tahoma", 0, 14));
    setOpaque(false);
    setPreferredSize(new Dimension(80, 23));

    loadData();
    final GuiModelManager<Currency> g= new GuiModelManager<Currency>();
    g.getObservable().addObserver(this);
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 3) {
          g.viewDialog(UnitMoneyJComboBox.this, Currency.class);
        }
      }
    });
  }

  private void loadData() {
    currencys = new ArrayList<Currency>();
    try {
      currencys = LocaleModelManager.getInstance().getCurrencies();
      setModel(new DefaultComboBoxModel(currencys.toArray()));
    } catch (Exception e) {
    }
  }

  public Currency getSelectedCurrency() {
    return (Currency) getSelectedItem();
  }

  public Currency getItemAt(int index) {
    return currencys.get(index);
  }

  public void setSelectedCurrency(String codeCurrency) {
    for (int i = 0; i < currencys.size(); i++) {
      if (currencys.get(i).getCode().equals(codeCurrency)) {
        setSelectedIndex(i);
        break;
      }
    }
  }

  @Override
  public void update(Object o, Object arg) {
   loadData();
  }
}
