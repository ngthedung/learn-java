package com.hkt.client.swingexp.app.hethong;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.LocaleServiceClient;
import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.module.config.locale.City;
import com.hkt.module.config.locale.Country;

@SuppressWarnings("serial")
public class CityTable extends BeanBindingJTable<City> {

  static String[] HEADERS = { "STT", "Mã", "Tên", "Miêu tả" };
  static String[] PROPERTIES = { "priority", "code", "name", "description" };
  DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
  private List<City> cities;
  private Country country;

  private LocaleServiceClient clientCore = ClientContext.getInstance().getBean(LocaleServiceClient.class);

  public void setCitiess(Country country) {
    this.country = country;
    this.cities = country.getCities();
    init(HEADERS, PROPERTIES, cities);
    setRowHeight(23);
    setOpaque(false);
    setFont(new Font("Tahoma", 0, 14));
    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.LEFT);
    this.getColumn("STT").setCellRenderer(centerRenderer);
    getColumnModel().getColumn(0).setMaxWidth(50);
  }

  public CityTable() {
  }

  protected City newBean() {
    return new City();
  }

  protected boolean isBeanEditableAt(int row, int col) {
    return false;
  }

  @Override
  public void saveIndex() {
    try {
      for (int i = 0; i < getBeans().size(); i++) {
        getBeans().get(i).setPriority(i);
        getBeans().get(i).setIndex(i);
      }
      country.setCities(getBeans());
      clientCore.saveCountry(country);
      DialogNotice.getInstace().setVisible(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
