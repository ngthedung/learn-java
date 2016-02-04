package com.hkt.client.swingexp.app.khachhang;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.hkt.client.swing.widget.BeanBindingJTable;
import com.hkt.module.account.entity.Contact;
import com.hkt.util.text.StringUtil;

public class ContactTable extends BeanBindingJTable<Contact> {

  static String[]       HEADERS    = { "STT", "Địa chỉ", "Đường", "Xã phường", "Quốc gia", "Thành phố", "ĐTCĐ", "ĐTDĐ",
      "Fax", "Email", "Website"   };
  static String[]       PROPERTIES = { "label", "addressNumber", "street", "district", "country", "city", "phone",
      "mobile", "fax", "email", "website" };

  private List<Contact> contacts;

  public void setContacts(List<Contact> contacts) {
    if (contacts == null) {
      contacts = new ArrayList<Contact>();
    }
    this.contacts = contacts;
    init(HEADERS, PROPERTIES, contacts);
    setRowHeight(23);
    setOpaque(false);
    setFont(new Font("Tahoma", 0, 14));
    getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
    getColumnModel().getColumn(0).setMaxWidth(50);
    ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

  }

  protected Contact newBean() {
    return new Contact();
  }

  protected boolean isBeanEditableAt(int row, int col) {
    return false;
  }

  @Override
  public Object getBeanValueAt(int row, int column) {
    Contact contact = contacts.get(row);
    switch (column) {
      case 0:
        return row + 1;
      case 1:
        return contact.getAddressNumber();
      case 2:
        return contact.getStreet();
      case 3:
        return contact.getDistrict();
      case 4:
        return contact.getCountry();
      case 5:
        return contact.getCity();
      case 6:
        return StringUtil.joinStringArray(contact.getPhone(), ",");
      case 7:
        return StringUtil.joinStringArray(contact.getMobile(), ",");
      case 8:
        return StringUtil.joinStringArray(contact.getFax(), ",");
      case 9:
        return StringUtil.joinStringArray(contact.getEmail(), ",");
      case 10:
        return StringUtil.joinStringArray(contact.getWebsite(), ",");
    }
    return super.getBeanValueAt(row, column);
  }

}
