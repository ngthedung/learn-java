package com.hkt.client.swingexp.app.khachhang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialog;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.hethong.PanelCountry;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Contact;
import com.hkt.module.config.locale.City;
import com.hkt.module.config.locale.Country;
import com.hkt.util.text.StringUtil;

/** Process Information Contact */

public class ContactForm extends JPanel {
	private ContactTable	contactTable;
	private JScrollPane		scrollPaneTable;
	private List<Contact>	contactList;
	private Contact				contact;
	private ContactsPanel	contactsPanel;
	private JTextField		txtAddressNumber, txtXaPhuong, txtStreet, txtPhone, txtMobile, txtFax, txtEmail, txtWebsite;
	private JComboBox			cbCountry, cbCity;
	private JButton				btnAddContact, btnDeleteContact, btnSaveContact, btnResetContact;
	private boolean				flagEdit			= true;
	private JLabel				lbMessage;
	private JLabel				lbAddressNumber;
	private int						indexContact	= -1;
	private JPanel				panelButton;
	private Country				country;
	
	public ContactForm(Account account) throws Exception {
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));
		
		contactList = new ArrayList<Contact>();
		if (account != null) {
			if (account.getContacts() != null) {
				contactList = account.getContacts();
			}
		}
		
		contactTable = new ContactTable();
		contactTable.setName("contactTable");
		contactTable.setContacts(contactList);
		contactTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				contactTableMouseClicked(event);
			}
		});
		
		panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelButton.setOpaque(false);
		btnAddContact = new JButton("Thêm");
		btnAddContact.setPreferredSize(new Dimension(109, 35));
		btnAddContact.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/save1.png")));
		btnAddContact.addMouseListener(new MouseEventClickButtonDialog("save1.png", "save1.png", "save1.png"));
		btnAddContact.setName("btnAddContact");
		btnAddContact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btAddContactActionPerformed(e);
			}
		});
		
		btnDeleteContact = new JButton("Xóa");
		btnDeleteContact.setPreferredSize(new Dimension(109, 35));
		btnDeleteContact.setName("btnDeleteContact");
		btnDeleteContact.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/delete1.png")));
		btnDeleteContact.addMouseListener(new MouseEventClickButtonDialog("delete1.png", "delete1.png", "delete1.png"));
		btnDeleteContact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btDeleteContactActionPerformed(e);
			}
		});
		
		btnSaveContact = new JButton("Lưu");
		btnSaveContact.setPreferredSize(new Dimension(109, 35));
		btnSaveContact.setName("btnSaveContact");
		btnSaveContact.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/save1.png")));
		btnSaveContact.addMouseListener(new MouseEventClickButtonDialog("save1.png", "save1.png", "save1.png"));
		btnSaveContact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btSaveContactActionPerformed(e);
			}
		});
		
		btnResetContact = new JButton("Viết lại");
		btnResetContact.setPreferredSize(new Dimension(109, 35));
		btnResetContact.setName("btnResetContact");
		btnResetContact.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/reset1.png")));
		btnResetContact.addMouseListener(new MouseEventClickButtonDialog("reset1.png", "reset1.png", "reset1.png"));
		btnResetContact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btResetContactActionPerformed(e);
			}
		});
		
		panelButton.add(btnAddContact);
		panelButton.add(btnDeleteContact);
		panelButton.add(btnSaveContact);
		panelButton.add(btnResetContact);
		scrollPaneTable = new JScrollPane();
		scrollPaneTable.getViewport().setBackground(Color.white);
		scrollPaneTable.setViewportView(contactTable);
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		panel.add(panelButton, BorderLayout.PAGE_START);
		panel.add(scrollPaneTable, BorderLayout.CENTER);
		
		contactsPanel = new ContactsPanel(this);
		setEnabledButton(true, false, false);
		setLayout(new BorderLayout());
		add(contactsPanel, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);
		btnDeleteContact.setVisible(false);
		btnSaveContact.setVisible(false);
	}
	
	public List<Contact> getContactList() {
		return contactList;
	}
	
	public void setContactList(List<Contact> contactList) {
		this.contactList = contactList;
	}
	
	public void deleteContactFromTable(int indexContact) {
		this.contactList.remove(indexContact);
	}
	
	protected void contactTableMouseClicked(MouseEvent event) {
		if (contactTable.getSelectedRow() < 0) {
			return;
		}
		int click = 2;
		JTable ta = (JTable) event.getSource();
		String value = "";
		if (ta.getValueAt(ta.getSelectedRow(), ta.getSelectedColumn()) != null) {
			value = ta.getValueAt(ta.getSelectedRow(), ta.getSelectedColumn()).toString();
			if (value.indexOf("HktTest") >= 0 && event.getButton() == 3) {
				click = 1;
			}
		}
		if (event.getClickCount() >= click) {
			if (flagEdit) {
				contact = contactTable.getSelectedBean();
				setContact(contact);
				setIndexContact(contactList.indexOf(contact));
				setEnabledButton(false, true, true);
			}
		}
	}
	
	private void loadContaceTable() {
		contactTable.setContacts(contactList);
	}
	
	public void saveContactToTable(Contact contactSave, int indexContact) {
		contact = contactList.get(indexContact);
		contact.setLabel(contactSave.getLabel());
		contact.setAddressNumber(contactSave.getAddressNumber());
		contact.setStreet(contactSave.getStreet());
		contact.setDistrict(contactSave.getDistrict());
		contact.setCity(contactSave.getCity());
		contact.setCountry(contactSave.getCountry());
		contact.setPhone(contactSave.getPhone());
		contact.setMobile(contactSave.getMobile());
		contact.setFax(contactSave.getFax());
		contact.setEmail(contactSave.getEmail());
		contact.setWebsite(contactSave.getWebsite());
	}
	
	public void cleanContact() {
		contact = new Contact();
		setContact(contact);
		contactList = new ArrayList<Contact>();
		loadContaceTable();
	}
	
	class ContactsPanel extends JPanel {
		private ContactForm	contactForm;
		private JLabel			lbXaPhuong, lbStreet, lbCity, lbCountry, lbPhone, lbMobile, lbFax, lbEmail, lbWebsite;
		
		public ContactsPanel(ContactForm contactForm) throws Exception {
			setOpaque(false);
			this.contactForm = contactForm;
			lbAddressNumber = new JLabel();
			lbAddressNumber.setText("Địa chỉ");
			lbXaPhuong = new ExtendJLabel("Xã/Phường");
			lbStreet = new ExtendJLabel("Đường");
			lbCity = new ExtendJLabel("Tỉnh/TP");
			lbCountry = new ExtendJLabel("Quốc gia");
			lbPhone = new ExtendJLabel("ĐTCĐ");
			lbMobile = new ExtendJLabel("ĐTDĐ");
			lbFax = new ExtendJLabel("Fax");
			lbEmail = new ExtendJLabel("Email");
			lbWebsite = new ExtendJLabel("Website");
			lbMessage = new JLabel();
			
			txtAddressNumber = new ExtendJTextField();
			txtAddressNumber.setName("txtAddressNumber");
			txtXaPhuong = new ExtendJTextField();
			txtXaPhuong.setName("txtXaPhuong");
			txtStreet = new ExtendJTextField();
			txtStreet.setName("txtStreet");
			txtPhone = new ExtendJTextField();
			txtPhone.setName("txtPhone");
			txtMobile = new ExtendJTextField();
			txtMobile.setName("txtMobile");
			txtFax = new ExtendJTextField();
			txtFax.setName("txtFax");
			txtEmail = new ExtendJTextField();
			txtEmail.setName("txtEmail");
			txtWebsite = new ExtendJTextField();
			txtWebsite.setName("txtWebsite");
			
			cbCountry = new ExtendJComboBox();
			List<Country> countries = LocaleModelManager.getInstance().getCountries();
			cbCountry.setModel(new DefaultComboBoxModel(countries.toArray()));
			cbCountry.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					try {
						
						country = ((Country) cbCountry.getSelectedItem());
						if (country.getCities() == null) {
							country.setCities(new ArrayList<City>());
						}
						
						cbCity.setModel(new DefaultComboBoxModel(country.getCities().toArray()));
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			country = ((Country) cbCountry.getSelectedItem());
			if (country == null) {
				country = new Country();
			} else {
				if (country.getCities() == null) {
					country.setCities(new ArrayList<City>());
				}
			}
			
			cbCountry.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON3) {
						PanelCountry pnlCountry;
						
						try {
							pnlCountry = new PanelCountry();
							pnlCountry.setName("QuocGia");
							DialogMain dialog = new DialogMain(pnlCountry);
							dialog = new DialogMain(pnlCountry);
							dialog.setTitle("Quốc gia");
							dialog.setIcon("country1.png");
							dialog.setName("dlQuocGia");
							dialog.setModal(true);
							dialog.setVisible(true);
							try {
								cbCountry.setModel(new DefaultComboBoxModel(LocaleModelManager.getInstance().getCountries().toArray()));
							} catch (Exception e1) {
								cbCountry.setModel(new DefaultComboBoxModel());
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			
			cbCity = new ExtendJComboBox();
			cbCity.setName("cbCity");
			Country countryInCB = (Country) cbCountry.getSelectedItem();
			cbCity.setModel(new DefaultComboBoxModel(countryInCB.getCities().toArray()));
			
			MyGroupLayout groupLayout = new MyGroupLayout(this);
			groupLayout.add(0, 0, lbAddressNumber);
			groupLayout.add(0, 1, txtAddressNumber);
			groupLayout.add(0, 2, lbPhone);
			groupLayout.add(0, 3, txtPhone);
			
			groupLayout.add(1, 0, lbXaPhuong);
			groupLayout.add(1, 1, txtXaPhuong);
			groupLayout.add(1, 2, lbMobile);
			groupLayout.add(1, 3, txtMobile);
			
			groupLayout.add(2, 0, lbStreet);
			groupLayout.add(2, 1, txtStreet);
			groupLayout.add(2, 2, lbEmail);
			groupLayout.add(2, 3, txtEmail);
			
			groupLayout.add(3, 0, lbCountry);
			groupLayout.add(3, 1, cbCountry);
			groupLayout.add(3, 2, lbFax);
			groupLayout.add(3, 3, txtFax);
			
			groupLayout.add(4, 0, lbCity);
			groupLayout.add(4, 1, cbCity);
			groupLayout.add(4, 2, lbWebsite);
			groupLayout.add(4, 3, txtWebsite);
			groupLayout.addMessenger(lbMessage);
			
			groupLayout.updateGui();
		}
		
	}
	
	public void setEdit(boolean flag) {
		txtAddressNumber.setEditable(flag);
		txtEmail.setEditable(flag);
		txtFax.setEditable(flag);
		txtMobile.setEditable(flag);
		txtPhone.setEditable(flag);
		txtStreet.setEditable(flag);
		txtWebsite.setEditable(flag);
		txtXaPhuong.setEditable(flag);
		btnAddContact.setVisible(flag);
		btnResetContact.setVisible(flag);
		btnDeleteContact.setVisible(false);
		btnSaveContact.setVisible(false);
		cbCity.setEnabled(flag);
		cbCountry.setEnabled(flag);
		this.flagEdit = flag;
	}
	
	protected void btSaveContactActionPerformed(ActionEvent e) {
		if (indexContact < 0) {
			return;
		}
		contact = getContact();
		saveContactToTable(contact, indexContact);
		loadContaceTable();
		contact = new Contact();
		setContact(contact);
		setEnabledButton(true, false, false);
	}
	
	protected void btDeleteContactActionPerformed(ActionEvent e) {
		if (indexContact < 0) {
			return;
		}
		deleteContactFromTable(indexContact);
		loadContaceTable();
		contact = new Contact();
		setContact(contact);
		setEnabledButton(true, false, false);
	}
	
	protected void btResetContactActionPerformed(ActionEvent e) {
		txtAddressNumber.setText("");
		txtEmail.setText("");
		txtFax.setText("");
		txtMobile.setText("");
		txtPhone.setText("");
		txtStreet.setText("");
		txtWebsite.setText("");
		txtXaPhuong.setText("");
	}
	
	private boolean checkData() {
		if (txtAddressNumber.getText().trim().length() == 0) {
			txtAddressNumber.requestFocus();
			lbMessage.setText("Thiếu thông tin địa chỉ !");
			lbMessage.setForeground(Color.red);
			lbAddressNumber.setForeground(Color.red);
			return false;
		} else {
			lbAddressNumber.setForeground(Color.black);
			lbMessage.setText("");
		}
		// if (txtStreet.getText().trim().length() == 0) {
		// txtStreet.requestFocus();
		// lbMessage.setText("Thiếu thông tin đường !");
		// return false;
		// }
		return true;
	}
	
	protected void btAddContactActionPerformed(ActionEvent e) {
		if (checkData()) {
			contactList.add(getContact());
			loadContaceTable();
			contact = new Contact();
			setContact(contact);
			setEnabledButton(true, false, false);
		}
	}
	
	public Contact getContact() {
		contact = new Contact();
		contact.setAddressNumber(txtAddressNumber.getText());
		contact.setStreet(txtStreet.getText());
		contact.setCountry(((Country) cbCountry.getSelectedItem()).getName());
		contact.setCity(((City) cbCity.getSelectedItem()).getName());
		contact.setDistrict(txtXaPhuong.getText());
		String[] arrayPhone = txtPhone.getText().split(",");
		for (int i = 0; i < arrayPhone.length; i++) {
			arrayPhone[i] = arrayPhone[i].trim();
		}
		contact.setPhone(arrayPhone);
		
		String[] arrayMobile = txtMobile.getText().split(",");
		for (int i = 0; i < arrayMobile.length; i++) {
			arrayMobile[i] = arrayMobile[i].trim();
		}
		contact.setMobile(arrayMobile);
		
		String[] arrayFax = txtFax.getText().split(",");
		for (int i = 0; i < arrayFax.length; i++) {
			arrayFax[i] = arrayFax[i].trim();
		}
		contact.setFax(arrayFax);
		
		String[] arrayEmail = txtEmail.getText().split(",");
		for (int i = 0; i < arrayEmail.length; i++) {
			arrayEmail[i] = arrayEmail[i].trim();
		}
		contact.setEmail(arrayEmail);
		
		String[] arrayWebsite = txtWebsite.getText().split(",");
		for (int i = 0; i < arrayWebsite.length; i++) {
			arrayWebsite[i] = arrayWebsite[i].trim();
		}
		contact.setWebsite(arrayWebsite);
		
		return contact;
	}
	
	public void setContact(Contact contact) {
		this.contact = contact;
		txtAddressNumber.setText(contact.getAddressNumber());
		txtXaPhuong.setText(contact.getDistrict());
		txtStreet.setText(contact.getStreet());
		
		for (int i = 0; i < cbCountry.getItemCount(); i++) {
			if (cbCountry.getSelectedItem() instanceof Country && ((Country) cbCountry.getItemAt(i)).getName().equals(contact.getCountry())) {
				cbCountry.setSelectedIndex(i);
				break;
			}
		}
		
		for (int i = 0; i < cbCity.getItemCount(); i++) {
			if (cbCity.getSelectedItem() instanceof City && ((City) cbCity.getItemAt(i)).getName().equals(contact.getCity())) {
				cbCity.setSelectedIndex(i);
				break;
			}
		}
		
		txtPhone.setText(StringUtil.joinStringArray(contact.getPhone(), ","));
		txtMobile.setText(StringUtil.joinStringArray(contact.getMobile(), ","));
		txtFax.setText(StringUtil.joinStringArray(contact.getFax(), ","));
		txtEmail.setText(StringUtil.joinStringArray(contact.getEmail(), ","));
		txtWebsite.setText(StringUtil.joinStringArray(contact.getWebsite(), ","));
	}
	
	protected void setIndexContact(int index) {
		this.indexContact = index;
	}
	
	protected void setEnabledButton(boolean btnAdd, boolean btnSave, boolean btnDelete) {
		btnAddContact.setVisible(btnAdd);
		btnSaveContact.setVisible(btnSave);
		btnDeleteContact.setVisible(btnDelete);
	}
	
}