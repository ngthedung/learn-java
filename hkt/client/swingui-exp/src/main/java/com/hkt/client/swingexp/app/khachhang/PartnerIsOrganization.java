package com.hkt.client.swingexp.app.khachhang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;

import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextArea;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.ImageTool;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.CustomerGroupJComboBox;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.IMyObserver;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.core.MyTabbedPaneUI;
import com.hkt.client.swingexp.app.core.TypeOrganizationJComboBox;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelRecybin;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelManageCodes;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.GenericOptionModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.account.entity.Contact;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.account.entity.Profiles;
import com.hkt.module.config.generic.Option;
import com.hkt.module.hr.entity.Appointment;
import com.hkt.module.hr.entity.Appointment.Status;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.util.text.DateUtil;
import com.hkt.util.text.StringUtil;

/*
 * @author Phan Anh
 */

public class PartnerIsOrganization extends MyPanel implements IDialogMain, IMyObserver {
	public static String permission;
	private JLabel lblPhone, lbFax, lbAddress, lbEmail, lbChoiceOrganization;
	private JLabel lbName, lbFullName, lbOrganizationType, lbManager, lbSologan, lbPartnerCode, lbFoundedDate,
	    lbTerminatedDate, lbRegistrationCode, lbRepresentative, lbDescription, lbNote;
	private JTextField txtFax, txtAddress, txtEmail;
	private CustomerGroupJComboBox cbGroupPartner;
	private TextPopup<Customer> txtPhone;
	private JLabel lbAvatar;
	private final JTabbedPane tabbedPane;
	private Account account = null;
	private JLabel lbMessage = new ExtendJLabel("");
	private JPanel panelAvatar;
	private BusinessRegistrationForm businessRegistrationForm;
	private ContactForm contactForm;
	private OrganizationBasicPanel organizationBasicPanel;
	private boolean isNew;
	private JTextField txtName, txtFullName, txtSlogan, txtPartnerCode, txtRegistrationCode, txtRepresentative;
	private JComboBox cboManager;
	private TypeOrganizationJComboBox cboOrganizationType;
	private JTextArea txtDescription, txtNote;
	private MyJDateChooser txtFoundedDate, txtTerminatedDate;
	private AccountMembership accountMembership;
	private Customer customer;
	private Contact contact;
	private boolean restore = true;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private boolean reset = false;
	private JCheckBox chbInteractive;

	public PartnerIsOrganization(Customer customerConfig, boolean isNew) throws Exception {
		setLayout(new BorderLayout());
		setOpaque(false);
		setName("PartnerIsOrganization");

		if (customerConfig != null) {
			this.account = AccountModelManager.getInstance().getAccountByLoginId(customerConfig.getLoginId());
			if (customerConfig.getCode() != null) {
				this.customer = customerConfig;
			}
			List<AccountMembership> accountMemberships = AccountModelManager.getInstance().findMembershipByAccountLoginId(
			    account.getLoginId());
			if (accountMemberships.size() > 0) {
				for(AccountMembership ac : accountMemberships){
					if(ac.getRole().equals(AccountModelManager.Customer)){
						this.accountMembership = ac;
					}
				}
				
			}
		}

		organizationBasicPanel = new OrganizationBasicPanel();
		businessRegistrationForm = new BusinessRegistrationForm(account);
		contactForm = new ContactForm(account);
		UIManager.put("TabbedPane.contentOpaque", false);
		UIManager.put("TabbedPane.tabsOpaque", false);
		tabbedPane = new JTabbedPane();
		tabbedPane.setUI(new MyTabbedPaneUI());
		tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 14));
		tabbedPane.setForeground(Color.BLACK);
		tabbedPane.setBorder(null);
		tabbedPane.setOpaque(false);
		tabbedPane.setName("tabbedPane");

		tabbedPane.add(organizationBasicPanel, "Thông tin doanh nghiệp");
		tabbedPane.add(businessRegistrationForm, "Đăng kí kinh doanh");
		tabbedPane.add(contactForm, "Thông tin liên lạc");
		add(tabbedPane, BorderLayout.CENTER);
		add(lbMessage, BorderLayout.PAGE_END);
		this.isNew = isNew;
		if (isNew == false) {
			setEdit(isNew);
		}

		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabbedPane.getSelectedIndex() == 0) {
					organizationBasicPanel.updateContact();
				}
				if (tabbedPane.getSelectedIndex() == 2 && contactForm.getContactList() != null) {
					if (contactForm.getContactList().size() > 0) {
						String[] arrayPhone = txtPhone.getText().split(",");
						for (int i = 0; i < arrayPhone.length; i++) {
							arrayPhone[i] = arrayPhone[i].trim();
						}
						String[] arrayEmail = txtEmail.getText().split(",");
						for (int i = 0; i < arrayEmail.length; i++) {
							arrayEmail[i] = arrayEmail[i].trim();
						}
						String[] arrayFax = txtFax.getText().split(",");
						for (int i = 0; i < arrayFax.length; i++) {
							arrayFax[i] = arrayFax[i].trim();
						}
						if (!txtPhone.getText().isEmpty())
							contactForm.getContactList().get(0).setPhone(arrayPhone);
						contactForm.getContactList().get(0).setAddressNumber(txtAddress.getText());
						if (!txtEmail.getText().isEmpty())
							contactForm.getContactList().get(0).setEmail(arrayEmail);
						if (!txtFax.getText().isEmpty())
							contactForm.getContactList().get(0).setFax(arrayFax);
					}
				}
			}
		});
		txtPhone.setData(CustomerModelManager.getInstance().getCustomers(false));
		txtPhone.addObserver(this);
		txtFoundedDate.setDate(new Date());
	}

	protected void btSaveActionPerformed() throws Exception {
		restore = true;
		if (tabbedPane.getSelectedIndex() == 2) {
			organizationBasicPanel.updateContact();
		}

		contact = new Contact();
		contact.setAddressNumber(txtAddress.getText());

		String[] arrayEmail = txtEmail.getText().split(",");
		for (int i = 0; i < arrayEmail.length; i++) {
			arrayEmail[i] = arrayEmail[i].trim();
		}
		contact.setEmail(arrayEmail);

		String[] arrayFax = txtFax.getText().split(",");
		for (int i = 0; i < arrayFax.length; i++) {
			arrayFax[i] = arrayFax[i].trim();
		}
		contact.setFax(arrayFax);

		String[] arrayPhone = txtPhone.getText().split(",");
		for (int i = 0; i < arrayPhone.length; i++) {
			arrayPhone[i] = arrayPhone[i].trim();
		}
		contact.setPhone(arrayPhone);

		if (isNew == true) {
			account = new Account();
			account.setLoginId(new Date().getTime() + "");
			account.setPassword("0000");
			account.setType(Type.ORGANIZATION);
			account.setLastLoginTime(new Date());
			if (!txtPhone.getText().isEmpty() || !txtFax.getText().isEmpty() || !txtAddress.getText().isEmpty())
				contactForm.getContactList().add(0, contact);
			account.setContacts(contactForm.getContactList());
		} else {
			try {
				if (account.getContacts().size() > 0) {
					account.getContacts().get(0).setPhone(contact.getPhone());
					account.getContacts().get(0).setFax(contact.getFax());
					account.getContacts().get(0).setEmail(contact.getEmail());
					account.getContacts().get(0).setAddressNumber(contact.getAddressNumber());
					if (txtPhone.getText().isEmpty() && txtFax.getText().isEmpty() && txtAddress.getText().isEmpty()) {
						account.getContacts().remove(0);
					}
				} else {
					if (!txtPhone.getText().isEmpty() || !txtAddress.getText().isEmpty() || !txtFax.getText().isEmpty())
						account.getContacts().add(0, contact);
				}
			} catch (Exception e) {
				List<Contact> contacts = new ArrayList<Contact>();
				contacts.add(contact);
				account.setContacts(contacts);
			}
		}

		account.setEmail(txtEmail.getText());
		Profile profileOrgBasic = new Profile();
		OrganizationBasic orgBasic = organizationBasicPanel.getOrganizationBasic();

		profileOrgBasic.put(OrganizationBasic.NAME, orgBasic.getName());
		profileOrgBasic.put(OrganizationBasic.FULLNAME, orgBasic.getFullName());
		profileOrgBasic.put(OrganizationBasic.ORGANIZATION_TYPE, orgBasic.getOrganizationType());
		profileOrgBasic.put(OrganizationBasic.MANAGER, orgBasic.getManager());
		profileOrgBasic.put(OrganizationBasic.SLOGAN, orgBasic.getSlogan());
		profileOrgBasic.put(OrganizationBasic.LOGOURL, orgBasic.getLogoURL());
		profileOrgBasic.put(OrganizationBasic.FOUNDED_DATE, orgBasic.getFoundedDate());
		profileOrgBasic.put(OrganizationBasic.TERMINATED_DATE, orgBasic.getTerminatedDate());
		profileOrgBasic.put(OrganizationBasic.REGISTRATION_CODE, orgBasic.getRegistrationCode());
		profileOrgBasic.put(OrganizationBasic.REPRESENTATIVE, orgBasic.getRepresentative());
		profileOrgBasic.put(OrganizationBasic.DESCRIPTION, orgBasic.getDescription());
		profileOrgBasic.put(OrganizationBasic.NOTE, orgBasic.getNote());

		// Tạo sinh nhật doanh nghiệp
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int daydate = calendar.get(Calendar.DAY_OF_MONTH);
		int monthdate = calendar.get(Calendar.MONTH);
		int yeardate = calendar.get(Calendar.YEAR);
		try {
			Date birthday = df.parse(orgBasic.getFoundedDate());
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(birthday);
			int daybirthday = calendar2.get(Calendar.DAY_OF_MONTH);
			int monthbirthday = calendar2.get(Calendar.MONTH);
			int yearbirthday = calendar2.get(Calendar.YEAR);
			if (yearbirthday < yeardate) {
				if (((monthbirthday < monthdate) || (monthbirthday >= monthdate && daybirthday < daydate))) {
					yearbirthday = yeardate + 1;
				} else {
					yearbirthday = yeardate;
				}
			} else if (yearbirthday == yeardate) {
				if (((monthbirthday < monthdate) || (monthbirthday == monthdate && daybirthday < daydate))) {
					yearbirthday = yeardate + 1;
				}
			}
			calendar2.set(Calendar.YEAR, yearbirthday);
			calendar2.set(Calendar.HOUR_OF_DAY, 7);
			birthday = calendar2.getTime();
			Profile p = AccountModelManager.getInstance().getProfileConfigAdmin();
			if (p.get("DailyWork") != null) {
				if (account.getProfiles() == null || account.getProfiles().getBasic().get("Birthdaydoanhnghiep") == null) {
					if (orgBasic.getFoundedDate() != null) {
						Appointment appointment = new Appointment();
						appointment.setName("Sinh nhật DN " + orgBasic.getName());
						// appointment.setContent("Sinh nhật");
						appointment.setDateStart(birthday);
						appointment.setStatus(Status.UNACCOMPLISHED);
						appointment.setCreatedBy("khachhang");
						appointment = HRModelManager.getInstance().saveAppointment(appointment);
						profileOrgBasic.put("Birthdaydoanhnghiep", appointment.getId());
					}
				} else {
					Appointment appointment = HRModelManager.getInstance().getAppointmentById(
					    Long.parseLong(account.getProfiles().getBasic().get("Birthdaydoanhnghiep").toString()));
					appointment.setDateStart(birthday);
					appointment = HRModelManager.getInstance().saveAppointment(appointment);
					profileOrgBasic.put("Birthdaydoanhnghiep", appointment.getId());
				}
			}

		} catch (Exception e) {
		}
		// Kết thúc tạo sinh nhật doanh nghiệp
		List<BusinessRegistration> businessRegistrations = businessRegistrationForm.getBusinessRegistrationList();
		Profile[] profileBusinessRegiss = new Profile[businessRegistrations.size()];
		if (businessRegistrations.size() > 0) {
			for (int i = 0; i < profileBusinessRegiss.length; i++) {
				Profile profile = new Profile();
				profile.put(BusinessRegistration.FULL_nameVN, businessRegistrations.get(i).getFullNameVN());
				profile.put(BusinessRegistration.FULL_nameEN, businessRegistrations.get(i).getFullNameEN());
				profile.put(BusinessRegistration.REGISTRATION_CODE, businessRegistrations.get(i).getRegistrationCode());
				profile.put(BusinessRegistration.TAX_REGISTRATION_CODE, businessRegistrations.get(i).getTaxRegistrationCode());
				profile.put(BusinessRegistration.REPRESENTATIVE, businessRegistrations.get(i).getRepresentative());
				profile.put(BusinessRegistration.CHAPTER_CAPITAL, businessRegistrations.get(i).getCharterCapital());
				profile.put(BusinessRegistration.LEGAL_CAPITAL, businessRegistrations.get(i).getLegalCapital());
				profile.put(BusinessRegistration.DOMAIN, businessRegistrations.get(i).getDomain());
				profileBusinessRegiss[i] = profile;
			}
		}

		Profiles profiles = new Profiles();
		profiles.setBasic(profileOrgBasic);
		profiles.setBusinessRegistrations(profileBusinessRegiss);

		if (account.getLoginId().equals(ManagerAuthenticate.getInstance().getOrganizationLoginId())) {
			profiles.setConfig(AccountModelManager.getInstance().getProfileConfigAdmin());
		}
		account.setProfiles(profiles);
		Account acc = AccountModelManager.getInstance().saveAccount(account);

		if (isNew) {
			if (cbGroupPartner.getSelectedGroupCustomer() != null) {
				accountMembership = new AccountMembership();
				accountMembership.setLoginId(acc.getLoginId());
				accountMembership.setRole(AccountModelManager.Customer);
				accountMembership.setGroupPath(cbGroupPartner.getSelectedGroupCustomer().getPath());
				AccountModelManager.getInstance().saveAccountMembership(accountMembership);
			}
		} else {
			if (accountMembership == null) {
				if (cbGroupPartner.getSelectedGroupCustomer() != null) {
					accountMembership = new AccountMembership();
					accountMembership.setLoginId(acc.getLoginId());
					accountMembership.setRole(AccountModelManager.Customer);
					accountMembership.setGroupPath(cbGroupPartner.getSelectedGroupCustomer().getPath());
					AccountModelManager.getInstance().saveAccountMembership(accountMembership);
				}
			} else {
				if (cbGroupPartner.getSelectedGroupCustomer() != null) {
					accountMembership.setGroupPath(cbGroupPartner.getSelectedGroupCustomer().getPath());
					AccountModelManager.getInstance().saveAccountMembership(accountMembership);
				} else {
					AccountModelManager.getInstance().deleteMembershipByLoginIdAndGroupPath(acc.getLoginId(),
					    accountMembership.getGroupPath());
				}
			}
		}

		if (isNew == true) {
			Customer customer = new Customer();
			customer.setLoginId(acc.getLoginId());
			customer.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
			customer.setName(txtName.getText());
			customer.setInteractive(chbInteractive.isSelected());
			try {
				String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.KhachHang, null, new Date(),
				    true);
				if (code != null) {
					customer.setCode(code);
				} else {
					customer.setCode(txtPartnerCode.getText());
				}
			} catch (Exception e) {
				customer.setCode(txtPartnerCode.getText());
			}

			if (!txtPhone.getText().isEmpty())
				customer.setMobile(txtPhone.getText());
			customer.setAddress(txtAddress.getText());
			customer.setBirthDay(txtFoundedDate.getDate());
			customer.setType("Doanh nghiệp");
			customer.setDescription(txtNote.getText());
			CustomerModelManager.getInstance().saveCustomer(customer);
		} else {
			if (customer != null) {
				customer.setName(txtName.getText());
				if (!txtPhone.getText().isEmpty())
					customer.setMobile(txtPhone.getText());

				customer.setAddress(txtAddress.getText());
				customer.setInteractive(chbInteractive.isSelected());
				customer.setBirthDay(txtFoundedDate.getDate());
				customer.setType("Doanh nghiệp");
				customer.setDescription(txtNote.getText());
				CustomerModelManager.getInstance().saveCustomer(customer);
			}
		}
		btClearActionPerformed();
	}

	protected void btClearActionPerformed() {
		account = new Account();
		organizationBasicPanel.cleanAccountBasic();
		organizationBasicPanel.setOrganizationBasic(new OrganizationBasic());
		businessRegistrationForm.cleanBusinessRegistration();
		contactForm.cleanContact();
		lbAvatar.setIcon(null);
		lbAvatar.setText("Chọn ảnh");
		txtFoundedDate.setDate(new Date());
		chbInteractive.setSelected(false);
		isNew = true;
		setEdit(isNew);
		loadCode();
		txtNote.setText("");
		try {
			txtPhone.setData(CustomerModelManager.getInstance().getCustomers(false));
		} catch (Exception e) {
		}
	}

	public class OrganizationBasicPanel extends JPanel {

		private List<Option> dataOrganizationType;
		private Object[] dataManager = { "Có", "Không" };
		private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		private OrganizationBasic organizationBasic;

		public OrganizationBasicPanel() {
			createBeginTest();
			init();
			refeshData();
		}

		private void init() {
			setOpaque(false);
			setBorder(BorderFactory.createEmptyBorder(20, 5, 0, 5));

			lblPhone = new ExtendJLabel("ĐTCĐ");
			lbFax = new ExtendJLabel("Fax");
			lbAddress = new ExtendJLabel("Địa chỉ");
			lbEmail = new ExtendJLabel("Email");
			lbChoiceOrganization = new ExtendJLabel("Nhóm");
			lbName = new ExtendJLabel("Tên DN");
			lbFullName = new ExtendJLabel("Tên đây đủ");
			lbOrganizationType = new ExtendJLabel("Mô hình");
			lbManager = new ExtendJLabel("Quản lý");
			lbSologan = new ExtendJLabel("Slogan");
			lbPartnerCode = new ExtendJLabel("Mã DN");
			lbFoundedDate = new ExtendJLabel("Bắt đầu");
			lbTerminatedDate = new ExtendJLabel("Kết thúc");
			lbRegistrationCode = new ExtendJLabel("Mã số thuế");
			lbRepresentative = new ExtendJLabel("Đại diện DN");
			lbDescription = new ExtendJLabel("Miêu tả");
			lbNote = new ExtendJLabel("Ghi chú");
			lbMessage.setForeground(Color.red);

			txtPhone = new TextPopup<Customer>(Customer.class);
			txtPhone.setName("txtPhone");
			txtFax = new ExtendJTextField();
			txtFax.setName("txtFax");
			txtAddress = new ExtendJTextField();
			txtAddress.setName("txtAddress");
			txtEmail = new ExtendJTextField();
			txtEmail.setName("txtEmail");
			txtName = new ExtendJTextField();
			txtName.setName("txtTenDoanhNghiep");
			txtFullName = new ExtendJTextField();
			txtFullName.setName("txtFullName");
			txtSlogan = new ExtendJTextField();
			txtSlogan.setName("txtSlogan");
			txtPartnerCode = new ExtendJTextField();
			txtFoundedDate = new MyJDateChooser();
			txtFoundedDate.setName("txtFoundedDate");
			txtFoundedDate.setDate(null);
			txtTerminatedDate = new MyJDateChooser();
			txtTerminatedDate.setDate(null);
			txtRegistrationCode = new ExtendJTextField();
			txtRegistrationCode.setName("txtRegistrationCode");
			txtRepresentative = new ExtendJTextField();
			txtRepresentative.setName("txtRepresentative");
			txtDescription = new ExtendJTextArea();
			txtDescription.setRows(3);
			txtDescription.setWrapStyleWord(true);
			txtDescription.setLineWrap(true);
			txtDescription.setText("");

			txtNote = new ExtendJTextArea();
			txtDescription.setName("txtDescription");

			cbGroupPartner = new CustomerGroupJComboBox();
			cbGroupPartner.setName("cbGroupPartner");
			cboOrganizationType = new TypeOrganizationJComboBox();
			cboOrganizationType.setName("cboOrganizationType");
			cboManager= new JComboBox();
			cboManager.setModel(new DefaultComboBoxModel(dataManager));
			panelAvatar = new JPanel();
			panelAvatar.setOpaque(false);
			panelAvatar.setLayout(new BorderLayout());
			lbAvatar = new JLabel("Chọn ảnh", SwingConstants.CENTER);
			lbAvatar.setBorder(BorderFactory.createEtchedBorder());
			panelAvatar.add(lbAvatar);

			MyGroupLayout groupLayout = new MyGroupLayout(this);
			groupLayout.add(0, 0, lbPartnerCode);
			groupLayout.add(0, 1, txtPartnerCode);

			groupLayout.add(1, 0, lbName);
			groupLayout.add(1, 1, txtName);

			groupLayout.add(2, 0, lblPhone);
			groupLayout.add(2, 1, txtPhone);

			groupLayout.add(3, 0, lbFax);
			groupLayout.add(3, 1, txtFax);
			groupLayout.add(3, 2, lbEmail);
			groupLayout.add(3, 3, txtEmail);

			groupLayout.add(4, 0, lbChoiceOrganization);
			groupLayout.add(4, 1, cbGroupPartner);
			groupLayout.add(4, 2, lbOrganizationType);
			groupLayout.add(4, 3, cboOrganizationType);

			groupLayout.add(5, 0, lbAddress);
			groupLayout.add(5, 1, txtAddress);
			groupLayout.add(5, 2, lbSologan);
			groupLayout.add(5, 3, txtSlogan);

			groupLayout.add(6, 0, lbManager);
			JPanel p = new JPanel(new BorderLayout());
			p.setOpaque(false);
			chbInteractive = new JCheckBox("Có giao dịch");
			chbInteractive.setOpaque(false);
			cboManager.setSize(10, 18);
			cboManager.setPreferredSize(new Dimension(10, 18));
			JScrollPane sc = new  JScrollPane();
			sc.setViewportView(cboManager);
			p.add(sc, BorderLayout.CENTER);
			p.add(chbInteractive, BorderLayout.EAST);
			groupLayout.add(6, 1, p);
			groupLayout.add(6, 2, lbFoundedDate);
			groupLayout.add(6, 3, txtFoundedDate);

			groupLayout.add(7, 0, lbRegistrationCode);
			groupLayout.add(7, 1, txtRegistrationCode);
			groupLayout.add(7, 2, lbTerminatedDate);
			groupLayout.add(7, 3, txtTerminatedDate);

			groupLayout.add(8, 0, lbRepresentative);
			groupLayout.add(8, 1, txtRepresentative);
			groupLayout.add(8, 2, lbFullName);
			groupLayout.add(8, 3, txtFullName);

			JScrollPane scrollPane = new JScrollPane(txtDescription);
			// scrollPane.setSize(100, 50);
			// scrollPane.setPreferredSize(scrollPane.getSize());
			// scrollPane.setMaximumSize(scrollPane.getSize());
			// scrollPane.addComponentListener(new ComponentAdapter() {
			// @Override
			// public void componentResized(ComponentEvent e) {
			// System.out.println(((JScrollPane)e.getSource()).getSize().height);
			// ((JScrollPane)e.getSource()).setPreferredSize(((JScrollPane)e.getSource()).getSize());
			// }
			// });
			// scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			DefaultCaret defaultCaret = (DefaultCaret) txtDescription.getCaret();
			defaultCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
			JScrollBar vbar = scrollPane.getVerticalScrollBar();
			vbar.setValue(vbar.getMaximum());
			vbar.paint(vbar.getGraphics());
			txtDescription.scrollRectToVisible(txtDescription.getVisibleRect());
			txtDescription.paint(txtDescription.getGraphics());
			groupLayout.add(9, 0, lbDescription);
			groupLayout.add(9, 1, scrollPane);

			JScrollPane scrollPane1 = new JScrollPane(txtNote);
			groupLayout.add(10, 0, lbNote);
			groupLayout.add(10, 1, scrollPane1);
			groupLayout.addImage(panelAvatar);
			groupLayout.updateGui();
			

			lbAvatar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (txtName.isEnabled())
						if (e.getClickCount() >= 2) {
							ImageIcon image = ImageTool.getInstance().getImage();
							if (image != null) {
								lbAvatar.setText("");
								image = ImageTool.getInstance().resize(image, new Dimension(150, 100));
								lbAvatar.setIcon(image);
							} else {
								lbAvatar.setIcon(null);
								lbAvatar.setText("Chọn ảnh");
							}
						}
				}
			});
		}

		public void refeshData() {
			if (account == null) {
				txtEmail.setText(new Date().getTime() + "@hkt.com");
				loadCode();
			} else {
				if (account.getContacts() != null && account.getContacts().size() > 0) {
					txtPhone.setText(StringUtil.joinStringArray(account.getContacts().get(0).getPhone()));
					txtFax.setText(StringUtil.joinStringArray(account.getContacts().get(0).getFax()));
					txtAddress.setText(account.getContacts().get(0).getAddressNumber());
				}
				txtEmail.setText(account.getEmail());
				if (customer != null) {
					txtPartnerCode.setText(customer.codeView());
				} else {
					txtPartnerCode.setText(account.getLoginId());
				}

				try {
					String image = (String) account.getProfiles().getBasic().get(OrganizationBasic.LOGOURL);
					if (image != null) {
						ImageIcon icon = ImageTool.getInstance().decodeToImage(image);
						lbAvatar.setText("");
						lbAvatar.setIcon(icon);
					} else {
						lbAvatar.setIcon(null);
						lbAvatar.setText("Chọn ảnh");
					}
				} catch (Exception e) {
					lbAvatar.setIcon(null);
					lbAvatar.setText("Chọn ảnh");
				}

				if (accountMembership != null) {
					cbGroupPartner.setSelectCustomer(accountMembership.getGroupPath());
				}
			}

			if (account != null) {
				organizationBasic = new OrganizationBasic();
				Profile profileBasic = account.getProfiles().getBasic();
				organizationBasic.setName((String) profileBasic.get(OrganizationBasic.NAME));
				organizationBasic.setFullName((String) profileBasic.get(OrganizationBasic.FULLNAME));
				organizationBasic.setOrganizationType((String) profileBasic.get(OrganizationBasic.ORGANIZATION_TYPE));
				organizationBasic.setManager((String) profileBasic.get(OrganizationBasic.MANAGER));
				organizationBasic.setSlogan((String) profileBasic.get(OrganizationBasic.SLOGAN));
				organizationBasic.setFoundedDate((String) profileBasic.get(OrganizationBasic.FOUNDED_DATE));
				organizationBasic.setTerminatedDate((String) profileBasic.get(OrganizationBasic.TERMINATED_DATE));
				organizationBasic.setRegistrationCode((String) profileBasic.get(OrganizationBasic.REGISTRATION_CODE));
				organizationBasic.setRepresentative((String) profileBasic.get(OrganizationBasic.REPRESENTATIVE));
				organizationBasic.setDescription((String) profileBasic.get(OrganizationBasic.DESCRIPTION));
				setOrganizationBasic(organizationBasic);
			}
			if(customer!=null){
				chbInteractive.setSelected(customer.isInteractive());
			}
		}

		public OrganizationBasic getOrganizationBasic() {
			organizationBasic = new OrganizationBasic();
			organizationBasic.setName(txtName.getText());
			organizationBasic.setFullName(txtFullName.getText());
			if (cboOrganizationType.getSelectedItem() != null) {
				organizationBasic.setOrganizationType(((Option) cboOrganizationType.getSelectedItem()).getCode());
			}

			ImageIcon image = (ImageIcon) lbAvatar.getIcon();
			if (image != null && image.getImage() != null) {
				String urlImage = ImageTool.getInstance().encodeToString(image.getImage());
				organizationBasic.setLogoURL(urlImage);
			} else {
				organizationBasic.setLogoURL("");
			}

			if ((String) cboManager.getSelectedItem() == "Có") {
				organizationBasic.setManager("Có");
			} else {
				organizationBasic.setManager("Không");
			}

			organizationBasic.setSlogan(txtSlogan.getText());

			try {
				organizationBasic.setFoundedDate(df.format(txtFoundedDate.getDate()));
			} catch (Exception e) {
				organizationBasic.setFoundedDate("");
			}

			try {
				organizationBasic.setTerminatedDate(df.format(txtTerminatedDate.getDate()));
			} catch (Exception e) {
				organizationBasic.setTerminatedDate("");
			}
			organizationBasic.setRegistrationCode(txtRegistrationCode.getText());
			organizationBasic.setRepresentative(txtRepresentative.getText());
			organizationBasic.setDescription(txtDescription.getText());
			return organizationBasic;
		}

		public void setOrganizationBasic(OrganizationBasic organizationBasic) {
			this.organizationBasic = organizationBasic;
			txtName.setText(organizationBasic.getName());
			txtFullName.setText(organizationBasic.getFullName());
			for (int i = 1; i < cboOrganizationType.getItemCount(); i++) {
				if ((((Option) cboOrganizationType.getItemAt(i)).getCode()).equals(organizationBasic.getOrganizationType())) {
					cboOrganizationType.setSelectedIndex(i);
					break;
				}
			}

			for (int i = 0; i < cboManager.getItemCount(); i++) {
				if (cboManager.getSelectedItem() instanceof Object
				    && ((String) cboManager.getItemAt(i)).equals(organizationBasic.getManager())) {
					cboManager.setSelectedIndex(i);
					break;
				}
			}

			txtSlogan.setText(organizationBasic.getSlogan());

			try {
				txtFoundedDate.setDate(df.parse(organizationBasic.getFoundedDate()));
			} catch (Exception e) {
				txtFoundedDate.setDate(null);
			}

			try {
				txtTerminatedDate.setDate(df.parse(organizationBasic.getTerminatedDate()));
			} catch (Exception e) {
				txtTerminatedDate.setDate(null);
			}

			txtRegistrationCode.setText(organizationBasic.getRegistrationCode());
			txtRepresentative.setText(organizationBasic.getRepresentative());
			txtDescription.setText(organizationBasic.getDescription());
			if (customer != null) {
				txtNote.setText(customer.getDescription());
			} else {
				txtNote.setText("");
			}
		}

		public void updateContact() {
			if (contactForm.getContactList() != null && contactForm.getContactList().size() > 0) {
				txtPhone.setText(StringUtil.joinStringArray(contactForm.getContactList().get(0).getPhone()));
				txtFax.setText(StringUtil.joinStringArray(contactForm.getContactList().get(0).getFax()));
				txtAddress.setText(contactForm.getContactList().get(0).getAddressNumber());
				if (!StringUtil.joinStringArray(contactForm.getContactList().get(0).getEmail()).isEmpty())
					txtEmail.setText(StringUtil.joinStringArray(contactForm.getContactList().get(0).getEmail()));
			}
			// else {
			// txtPhone.setText("");
			// txtFax.setText("");
			// txtAddress.setText("");
			// }
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void cleanAccountBasic() {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String loginId = dateFormat.format(new Date());
			loadCode();
			txtEmail.setText(loginId + "@hkt.com");
			txtPhone.setText("");
			txtFax.setText("");
			txtAddress.setText("");
			cboManager.setModel(new DefaultComboBoxModel(dataManager));
		}

	}

	private void loadCode() {
		try {
			String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.KhachHang, null, new Date(),
			    false);
			code = code.substring(code.indexOf(":") + 1);
			txtPartnerCode.setText(code);
			txtPartnerCode.setEnabled(false);
		} catch (Exception e) {
			txtPartnerCode.setText(DateUtil.asCompactDateTimeId(new Date()));
		}

	}

	private boolean checkData() {
		if (txtPartnerCode.getText().trim().length() == 0) {
			lbPartnerCode.setForeground(Color.red);
			lbMessage.setText("Phải nhập mã đối tác !");
			return false;
		} else {
			try {
				if (isNew == true) {
					Customer cus = CustomerModelManager.getInstance().getCustomerByCode(txtPartnerCode.getText().trim());

					if (cus != null) {
						txtPartnerCode.requestFocus();
						lbPartnerCode.setForeground(Color.red);
						lbMessage.setText("Mã khách hàng đã tồn tại !");
						if (cus.isRecycleBin()) {
							PanelRecybin panel = new PanelRecybin("Mã đã bị xóa trước đó!",
							    " Chọn ĐỒNG Ý để lấy lại thông tin ban đầu hoặc THOÁT và nhập mã khác!");
							panel.setName("Xoa");
							DialogResto dialog = new DialogResto(panel, false, 0, 120);
							dialog.setName("dlXoa");
							dialog.setTitle("Khách hàng doanh nghiệp");
							dialog.setLocationRelativeTo(null);
							dialog.setModal(true);
							dialog.setVisible(true);
							if (panel.isDelete()) {
								restore = false;
								cus.setRecycleBin(false);
								CustomerModelManager.getInstance().saveCustomer(cus);
								lbPartnerCode.setForeground(Color.black);
								lbMessage.setText("");
								reset();
								return true;
							}

						}
						return false;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			lbPartnerCode.setForeground(Color.black);
		}
		if (txtName.getText().trim().length() == 0) {
			lbName.setForeground(Color.red);
			lbMessage.setText("Phải nhập tên đối tác !");
			return false;
		} else {
			lbName.setForeground(Color.black);

		}
		if (txtPhone.getText().trim().length() != 0) {
			String pattern = "([\\d,\\s])*";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(txtPhone.getText());
			if (!m.matches()) {
				lblPhone.setForeground(Color.red);
				lbMessage.setText("Kiểm tra lại định dạng SĐT (Phân cách bằng dấu phẩy) !");
				return false;
			} else {
				lblPhone.setForeground(Color.black);
			}

		}
		if (txtFax.getText().trim().length() != 0) {
			String pattern = "([\\d,\\s])*";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(txtFax.getText());
			if (!m.matches()) {
				lbFax.setForeground(Color.red);
				lbMessage.setText("Kiểm tra lại định dạng số Fax (Phân cách bằng dấu phẩy)");
				return false;
			} else {
				lbFax.setForeground(Color.black);
			}
		}
		if (txtEmail.getText().trim().length() == 0) {
			lbEmail.setForeground(Color.red);
			lbMessage.setText("Nhập thông tin email !");
			return false;
		} else {
			lbEmail.setForeground(Color.black);
		}
		lbMessage.setText("");
		return true;
	}

	@Override
	public void save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return;
		} else {
			if (checkData()) {
				if (restore)
					btSaveActionPerformed();
			} else {
				return;
			}
			if (reset == true)
				((DialogMain) getRootPane().getParent()).dispose();
		}
	}

	@Override
	public void edit() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return;
		} else {
			reset = true;
			setEdit(true);
		}
	}

	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {
			if (account != null) {
				Customer customer = CustomerModelManager.getInstance().getBydLoginId(account.getLoginId());
				if (customer != null) {
					String str = "Xóa khách hàng ";
					PanelChoise pnPanel = new PanelChoise(str + customer + "?");
					pnPanel.setName("Xoa");
					DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
					dialog1.setName("dlXoa");
					dialog1.setTitle("Xóa khách hàng doanh nghiệp");
					dialog1.setLocationRelativeTo(null);
					dialog1.setModal(true);
					dialog1.setVisible(true);
					if (pnPanel.isDelete() == true) {
						if (account.getProfiles().getBasic().get("Birthdaydoanhnghiep") != null) {
							Appointment appointment = HRModelManager.getInstance().getAppointmentById(
							    Long.parseLong(account.getProfiles().getBasic().get("Birthdaydoanhnghiep").toString()));
							HRModelManager.getInstance().deleteAppointment(appointment);
						}
						if (CustomerModelManager.getInstance().deleteCustomer(customer)) {
							AccountModelManager.getInstance().deleteAccountByLoginId(account.getLoginId());
							lbMessage.setText("");
							btClearActionPerformed();
							((DialogMain) getRootPane().getParent()).dispose();
						} else
							lbMessage.setText("Lỗi chưa xóa được !");

					} else if (pnPanel.isDelete() == false) {
						return;
					}
				}
			} else {
				lbMessage.setText("Không có dữ liệu !");
			}

		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
		}
	}

	@Override
	public void reset() throws Exception {
		btClearActionPerformed();

	}

	@Override
	public void refresh() throws Exception {
		organizationBasicPanel.refeshData();
		setEdit(false);
	}

	public void setEdit(boolean flag) {
		txtNote.setEnabled(flag);
		txtPartnerCode.setEnabled(false);
		txtPhone.setEnabled(flag);
		txtEmail.setEnabled(flag);
		txtFax.setEnabled(flag);
		txtAddress.setEnabled(flag);
		txtDescription.setEnabled(flag);
		txtFoundedDate.setEnabled(flag);
		txtFullName.setEnabled(flag);
		txtName.setEnabled(flag);
		txtRegistrationCode.setEnabled(flag);
		txtSlogan.setEnabled(flag);
		txtTerminatedDate.setEnabled(flag);
		cbGroupPartner.setEnabled(flag);
		cboManager.setEnabled(flag);
		txtRepresentative.setEnabled(flag);
		cboOrganizationType.setEnabled(flag);
		contactForm.setEdit(flag);
		businessRegistrationForm.setEdit(flag);
		chbInteractive.setEnabled(flag);
	}

	@Override
	public void createBeginTest() {
		if (MyPanel.isTest) {
			// Tạo nhóm đối tác
			try {
				AccountGroup root = AccountModelManager.getInstance().getRootGroupDetail().getChildren().get(0);
				AccountGroup parent = new AccountGroup(root, AccountModelManager.Customer);
				parent.setLabel(AccountModelManager.Customer);
				if (AccountModelManager.getInstance().getGroupByPath(root.getPath() + "/" + AccountModelManager.Customer) == null) {
					parent = AccountModelManager.getInstance().saveGroup(parent);
				} else {
					parent = AccountModelManager.getInstance().getGroupByPath(parent.getPath());
				}
				AccountGroup accountGroup = new AccountGroup();
				accountGroup.setName("Mã ĐT HktTest1");
				accountGroup.setLabel("NĐT HktTest1");
				accountGroup.setParent(parent);

				AccountModelManager.getInstance().saveGroup(accountGroup);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		super.createBeginTest();
	}

	@Override
	public void createDataTest() {
		// Tạo 50 đối tác test chuyển trang
		for (int i = 1; i <= 50; i++) {
			Account account = new Account();
			account.setLoginId("HktTest" + i);
			account.setPassword("HktTest" + i);
			account.setEmail("HktTest" + i + "@gmail.com");
			account.setType(Type.ORGANIZATION);
			Contact contact = new Contact();
			String[] phone = { "012346789" + i };
			contact.setPhone(phone);
			account.addContact(contact);

			Profile profile = new Profile();
			profile.put(OrganizationBasic.NAME, "Tên đối tác HktTest" + i + 10);
			profile.put(OrganizationBasic.FOUNDED_DATE, "01/01/2014");
			Profiles profiles = new Profiles();
			profiles.setBasic(profile);
			try {
				account.setProfiles(profiles);
			} catch (IOException e) {
				e.printStackTrace();
			}

			Customer customer = new Customer();
			customer.setLoginId(account.getLoginId());
			customer.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
			customer.setCode("Mã đối tác HktTest" + i);
			customer.setName("Tên đối tác HktTest" + i);
			customer.setMobile("01234678" + i);
			try {
				account = AccountModelManager.getInstance().saveAccount(account);
				customer = CustomerModelManager.getInstance().saveCustomer(customer);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void deleteDataTest() {
		if (!PanelTestAll.runAll) {
			DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
			if (DialogTest.getInstance().isTest()) {
				try {
					AccountModelManager.getInstance().deleteTest("HktTest");
					CustomerModelManager.getInstance().deleteTest("HktTest");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void update(Object o, Object arg) {
		if (o instanceof Customer) {
			Customer customer = (Customer) o;
			try {
				this.account = AccountModelManager.getInstance().getAccountByLoginId(customer.getLoginId());
				if (customer.getCode() != null) {
					this.customer = customer;
					List<AccountMembership> accountMemberships = AccountModelManager.getInstance()
					    .findMembershipByAccountLoginId(account.getLoginId());
					if (accountMemberships.size() > 0) {
						this.accountMembership = accountMemberships.get(0);
					}
					organizationBasicPanel.refeshData();
					setEdit(false);
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
	}
}
