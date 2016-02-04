package com.hkt.client.swingexp.app.nhansu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextArea;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.ImageTool;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.core.MyTabbedPaneUI;
import com.hkt.client.swingexp.app.hethong.PanelRecybin;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.khachhang.BusinessRegistration;
import com.hkt.client.swingexp.app.khachhang.BusinessRegistrationForm;
import com.hkt.client.swingexp.app.khachhang.ContactForm;
import com.hkt.client.swingexp.app.khachhang.OrganizationBasic;
import com.hkt.client.swingexp.app.khachhang.list.SupplierGroupJComboBox;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelManageCodes;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.SupplierModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.account.entity.Contact;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.account.entity.Profiles;
import com.hkt.module.hr.entity.Appointment;
import com.hkt.module.hr.entity.Appointment.Status;
import com.hkt.module.partner.supplier.entity.Supplier;
import com.hkt.util.text.DateUtil;
import com.hkt.util.text.StringUtil;

/*
 * @author Phan Anh
 */

@SuppressWarnings("serial")
public class SupplierIsOrganization extends MyPanel implements IDialogMain {

	public static String permission;
	private JLabel lbPhone, lbFax, lbAddress, lbEmail, lbGroupSupplier;
	private JTextField txtPhone, txtEmail, txtFax, txtAddress, txtSlogan, txtRegistrationCode, txtRepresentative;
	@SuppressWarnings("rawtypes")
	private SupplierGroupJComboBox cbGroupSupplier;
	private JLabel lbAvatar;
	private JLabel lbName, lbFullName, lbSupplierCode, lbDescription, lbSologan, lbFoundedDate, lbTerminatedDate,
	    lbRegistrationCode, lbRepresentative;
	private MyJDateChooser txtFoundedDate, txtTerminatedDate;
	private Account account = null;
	private JLabel lbMessage = new ExtendJLabel("");
	private JPanel panelAvatar;
	private BusinessRegistrationForm businessRegistrationForm;
	private ContactForm contactForm;
	private OrganizationBasicPanel organizationBasicPanel;
	private boolean isNew;
	private JTextField txtName, txtFullName, txtSupplierCode;
	private JTextArea txtDescription;
	private Supplier customerConfig = null;
	private AccountMembership accountMembership;
	@SuppressWarnings("unused")
	private AccountGroup root = AccountModelManager.getInstance().getRootGroupDetail().getChildren().get(0);
	private Contact contact;
	private final JTabbedPane tabbedPane;
	private boolean restore = true;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private boolean reset = false;
	private JCheckBox chbInteractive;

	public SupplierIsOrganization(Supplier customerConfig, boolean isNew) throws Exception {
		setLayout(new BorderLayout());
		setOpaque(false);

		if (customerConfig != null) {
			this.customerConfig = customerConfig;
			this.account = AccountModelManager.getInstance().getAccountByLoginId(customerConfig.getLoginId());
			List<AccountMembership> accountMemberships = AccountModelManager.getInstance().findMembershipByAccountLoginId(
			    account.getLoginId());
			if (accountMemberships.size() > 0) {
				this.accountMembership = accountMemberships.get(0);
			}
		}

		organizationBasicPanel = new OrganizationBasicPanel();
		businessRegistrationForm = new BusinessRegistrationForm(account);
		contactForm = new ContactForm(account);

		tabbedPane = new JTabbedPane();
		tabbedPane.setName("tabbedPane");
		tabbedPane.setUI(new MyTabbedPaneUI());
		tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 14));
		tabbedPane.setForeground(Color.BLACK);
		tabbedPane.setBorder(null);
		tabbedPane.setOpaque(false);

		tabbedPane.add(organizationBasicPanel, "Thông tin doanh nghiệp");
		tabbedPane.add(businessRegistrationForm, "Đăng kí kinh doanh");
		tabbedPane.add(contactForm, "Thông tin liên lạc");
		UIManager.put("TabbedPane.contentOpaque", false);
		UIManager.put("TabbedPane.tabsOpaque", false);

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
						String[] arraymobile = txtPhone.getText().split(",");
						for (int i = 0; i < arraymobile.length; i++) {
							arraymobile[i] = arraymobile[i].trim();
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
							contactForm.getContactList().get(0).setMobile(arraymobile);
						contactForm.getContactList().get(0).setAddressNumber(txtAddress.getText());
						if (!txtEmail.getText().isEmpty())
							contactForm.getContactList().get(0).setEmail(arrayEmail);
						if (!txtFax.getText().isEmpty())
							contactForm.getContactList().get(0).setFax(arrayFax);
					}
				}
			}
		});
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

		String[] arraymobile = txtPhone.getText().split(",");
		for (int i = 0; i < arraymobile.length; i++) {
			arraymobile[i] = arraymobile[i].trim();
		}
		contact.setMobile(arraymobile);

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
			if (account.getContacts().size() > 0) {
				account.getContacts().get(0).setMobile(contact.getMobile());
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
		}
		account.setEmail(txtEmail.getText());

		Profile profileOrgBasic = new Profile();
		OrganizationBasic orgBasic = organizationBasicPanel.getOrganizationBasic();

		profileOrgBasic.put(OrganizationBasic.NAME, orgBasic.getName());
		profileOrgBasic.put(OrganizationBasic.FULLNAME, orgBasic.getFullName());
		profileOrgBasic.put(OrganizationBasic.LOGOURL, orgBasic.getLogoURL());
		profileOrgBasic.put(OrganizationBasic.SLOGAN, orgBasic.getSlogan());
		profileOrgBasic.put(OrganizationBasic.FOUNDED_DATE, orgBasic.getFoundedDate());
		profileOrgBasic.put(OrganizationBasic.TERMINATED_DATE, orgBasic.getTerminatedDate());
		profileOrgBasic.put(OrganizationBasic.REGISTRATION_CODE, orgBasic.getRegistrationCode());
		profileOrgBasic.put(OrganizationBasic.REPRESENTATIVE, orgBasic.getRepresentative());
		profileOrgBasic.put(OrganizationBasic.DESCRIPTION, orgBasic.getDescription());
		// Tạo sinh nhật cho nhà cung cấp doanh nghiệp
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
				if (account.getProfiles() == null
				    || account.getProfiles().getBasic().get("Birthdaynhacungcapdoanhnghiep") == null) {
					if (orgBasic.getFoundedDate() != null) {
						Appointment appointment = new Appointment();
						appointment.setName("Sinh nhật NCC " + orgBasic.getName());

						appointment.setDateStart(birthday);
						appointment.setStatus(Status.UNACCOMPLISHED);
						appointment.setCreatedBy("nhacungcap");
						appointment = HRModelManager.getInstance().saveAppointment(appointment);
						profileOrgBasic.put("Birthdaynhacungcapdoanhnghiep", appointment.getId());
					}
				} else {
					Appointment appointment = HRModelManager.getInstance().getAppointmentById(
					    Long.parseLong(account.getProfiles().getBasic().get("Birthdaynhacungcapdoanhnghiep").toString()));
					appointment.setDateStart(birthday);
					appointment = HRModelManager.getInstance().saveAppointment(appointment);
					profileOrgBasic.put("Birthdaynhacungcapdoanhnghiep", appointment.getId());
				}
			}

		} catch (Exception e) {
		}
		// Kết thúc tạo sinh nhật cho nhà doanh nghiếp
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

		account.setProfiles(profiles);
		Account acc = AccountModelManager.getInstance().saveAccount(account);

		if (isNew) {
			if (cbGroupSupplier.getSelectedItem() != null) {
				accountMembership = new AccountMembership();
				accountMembership.setLoginId(acc.getLoginId());
				accountMembership.setRole(AccountModelManager.Supplier);
				accountMembership.setGroupPath(((AccountGroup) cbGroupSupplier.getSelectedItem()).getPath());
				AccountModelManager.getInstance().saveAccountMembership(accountMembership);
			}
		} else {
			if (accountMembership == null) {
				if (cbGroupSupplier.getSelectedItem() != null) {
					accountMembership = new AccountMembership();
					accountMembership.setLoginId(acc.getLoginId());
					accountMembership.setRole(AccountModelManager.Supplier);
					accountMembership.setGroupPath(((AccountGroup) cbGroupSupplier.getSelectedItem()).getPath());
					AccountModelManager.getInstance().saveAccountMembership(accountMembership);
				}
			} else {
				if (cbGroupSupplier.getSelectedItem() != null) {
					accountMembership.setGroupPath(((AccountGroup) cbGroupSupplier.getSelectedItem()).getPath());
					AccountModelManager.getInstance().saveAccountMembership(accountMembership);
				} else {
					AccountModelManager.getInstance().deleteMembershipByLoginIdAndGroupPath(acc.getLoginId(),
					    accountMembership.getGroupPath());
				}
			}
		}

		if (isNew == true) {
			Supplier supplier = new Supplier();
			supplier.setLoginId(acc.getLoginId());
			supplier.setInteractive(chbInteractive.isSelected());
			supplier.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
			supplier.setName(txtName.getText());
			try {
				String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.NhaCungCap, null, new Date(),
				    true);
				if (code != null) {
					supplier.setCode(code);
				} else {
					supplier.setCode(txtSupplierCode.getText());
				}
			} catch (Exception e) {
				supplier.setCode(txtSupplierCode.getText());
			}
			if (!txtPhone.getText().isEmpty())
				supplier.setMobile(txtPhone.getText());
			supplier.setAddress(txtAddress.getText());
			supplier.setBirthDay(txtFoundedDate.getDate());
			supplier.setType("Doanh nghiệp");
			SupplierModelManager.getInstance().saveSupplier(supplier);
		} else {

			if (customerConfig != null) {
				customerConfig.setName(txtName.getText());
				if (!txtPhone.getText().isEmpty())
					customerConfig.setMobile(txtPhone.getText());
				
				customerConfig.setInteractive(chbInteractive.isSelected());
				customerConfig.setAddress(txtAddress.getText());
				customerConfig.setBirthDay(txtFoundedDate.getDate());
				customerConfig.setType("Doanh nghiệp");
				SupplierModelManager.getInstance().saveSupplier(customerConfig);
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
		isNew = true;
		setEdit(isNew);
		txtSupplierCode.setEnabled(isNew);
		chbInteractive.setSelected(false);
		txtFoundedDate.setDate(new Date());
		loadCode();
	}

	private void loadCode() {
		try {
			String code = AccountingModelManager.getInstance().getCodeObject(PanelManageCodes.NhaCungCap, null, new Date(),
			    false);
			code = code.substring(code.indexOf(":") + 1);
			txtSupplierCode.setText(code);
			txtSupplierCode.setEnabled(false);
		} catch (Exception e) {
			txtSupplierCode.setText(DateUtil.asCompactDateTimeId(new Date()));
		}

	}

	public class OrganizationBasicPanel extends JPanel {

		private OrganizationBasic organizationBasic;
		private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		public OrganizationBasicPanel() {
			init();
			refeshData();
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		private void init() {
			setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(20, 5, 0, 5)));
			setOpaque(false);

			lbName = new ExtendJLabel("Nhà NCC");
			lbFullName = new ExtendJLabel("Tên đây đủ");
			lbSupplierCode = new ExtendJLabel("Mã NCC");
			lbDescription = new ExtendJLabel("Miêu tả");
			lbPhone = new ExtendJLabel("ĐTCĐ");
			lbFax = new ExtendJLabel("Fax");
			lbAddress = new ExtendJLabel("Địa chỉ");
			lbEmail = new ExtendJLabel("Email");
			lbGroupSupplier = new ExtendJLabel("Nhóm NCC");
			lbSologan = new ExtendJLabel("Sologan");
			lbFoundedDate = new ExtendJLabel("Bắt đầu");
			lbTerminatedDate = new ExtendJLabel("Kết thúc");
			lbRegistrationCode = new ExtendJLabel("Mã số thuế");
			lbRepresentative = new ExtendJLabel("Đại diện DN");
			lbMessage.setForeground(Color.RED);

			txtPhone = new ExtendJTextField();
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
			txtSupplierCode = new ExtendJTextField();
			txtDescription = new ExtendJTextArea();
			txtDescription.setName("txtDescription");
			txtSlogan = new ExtendJTextField();
			txtSlogan.setName("txtSlogan");
			txtFoundedDate = new MyJDateChooser();
			txtFoundedDate.setDate(null);
			txtTerminatedDate = new MyJDateChooser();
			txtTerminatedDate.setDate(null);
			txtRegistrationCode = new ExtendJTextField();
			txtRegistrationCode.setName("txtRegistrationCode");
			txtRepresentative = new ExtendJTextField();
			txtRepresentative.setName("txtRepresentative");

			cbGroupSupplier = new SupplierGroupJComboBox();

			panelAvatar = new JPanel();
			panelAvatar.setOpaque(false);
			panelAvatar.setLayout(new BorderLayout());
			lbAvatar = new JLabel("Chọn ảnh", SwingConstants.CENTER);
			lbAvatar.setBorder(BorderFactory.createEtchedBorder());
			panelAvatar.add(lbAvatar);

			MyGroupLayout groupLayout = new MyGroupLayout(this);
			groupLayout.add(0, 0, lbSupplierCode);
			groupLayout.add(0, 1, txtSupplierCode);

			groupLayout.add(1, 0, lbName);
			groupLayout.add(1, 1, txtName);

			groupLayout.add(2, 0, lbFullName);
			groupLayout.add(2, 1, txtFullName);

			groupLayout.add(3, 0, lbPhone);
			groupLayout.add(3, 1, txtPhone);
			groupLayout.add(3, 2, lbFax);
			groupLayout.add(3, 3, txtFax);

			groupLayout.add(4, 0, lbRegistrationCode);
			groupLayout.add(4, 1, txtRegistrationCode);
			groupLayout.add(4, 2, lbRepresentative);
			groupLayout.add(4, 3, txtRepresentative);

			groupLayout.add(5, 0, lbAddress);
			groupLayout.add(5, 1, txtAddress);
			groupLayout.add(5, 2, lbEmail);
			groupLayout.add(5, 3, txtEmail);

			groupLayout.add(6, 0, lbGroupSupplier);
			JPanel p = new JPanel(new BorderLayout());
			p.setOpaque(false);
			chbInteractive = new JCheckBox("Có giao dịch");
			chbInteractive.setOpaque(false);
			cbGroupSupplier.setSize(10, 18);
			cbGroupSupplier.setPreferredSize(new Dimension(10, 18));
			JScrollPane sc = new  JScrollPane();
			sc.setViewportView(cbGroupSupplier);
			p.add(sc, BorderLayout.CENTER);
			p.add(chbInteractive, BorderLayout.EAST);
			groupLayout.add(6, 1, p);
			groupLayout.add(6, 2, lbSologan);
			groupLayout.add(6, 3, txtSlogan);

			groupLayout.add(7, 0, lbFoundedDate);
			groupLayout.add(7, 1, txtFoundedDate);
			groupLayout.add(7, 2, lbTerminatedDate);
			groupLayout.add(7, 3, txtTerminatedDate);

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setViewportView(txtDescription);
			groupLayout.add(8, 0, lbDescription);
			groupLayout.add(8, 1, scrollPane);
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

		public OrganizationBasic getOrganizationBasic() {
			organizationBasic = new OrganizationBasic();
			organizationBasic.setName(txtName.getText());
			organizationBasic.setFullName(txtFullName.getText());
			organizationBasic.setSlogan(txtSlogan.getText());

			ImageIcon image = (ImageIcon) lbAvatar.getIcon();
			if (image != null) {
				try {
					String urlImage = ImageTool.getInstance().encodeToString(image.getImage());
					organizationBasic.setLogoURL(urlImage);
				} catch (Exception e) {
					organizationBasic.setLogoURL("");
				}
				
			} else
				organizationBasic.setLogoURL("");

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
			txtSlogan.setText(organizationBasic.getSlogan());
			txtRegistrationCode.setText(organizationBasic.getRegistrationCode());
			txtRepresentative.setText(organizationBasic.getRepresentative());
			txtDescription.setText(organizationBasic.getDescription());
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
		}

		public void updateContact() {
			if (contactForm.getContactList() != null && contactForm.getContactList().size() > 0) {
				txtPhone.setText(StringUtil.joinStringArray(contactForm.getContactList().get(0).getMobile()));
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

		public void refeshData() {
			if (account == null) {
				txtEmail.setText(new Date().getTime() + "@hkt.com");
				loadCode();
			} else {
				txtEmail.setText(account.getEmail());
				txtSupplierCode.setText(customerConfig.getCode());
				if (account.getContacts() != null && account.getContacts().size() > 0) {
					txtPhone.setText(StringUtil.joinStringArray(account.getContacts().get(0).getMobile()));
					txtFax.setText(StringUtil.joinStringArray(account.getContacts().get(0).getFax()));
					txtAddress.setText(account.getContacts().get(0).getAddressNumber());
				}

				if (accountMembership != null) {
					String groupPath = accountMembership.getGroupPath();
					if (groupPath != null) {
						try {
							AccountGroup group = AccountModelManager.getInstance().getGroupByPath(groupPath);
							cbGroupSupplier.setSelectedItem(group);
						} catch (Exception ex) {
							ex.printStackTrace();
							cbGroupSupplier.setSelectedIndex(0);
						}
					}
				}

				try {
					String image = account.getProfiles().getBasic().get(OrganizationBasic.LOGOURL).toString();
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
			}

			if (account != null) {
				organizationBasic = new OrganizationBasic();
				Profile profileBasic = account.getProfiles().getBasic();
				organizationBasic.setName((String) profileBasic.get(OrganizationBasic.NAME));
				organizationBasic.setFullName((String) profileBasic.get(OrganizationBasic.FULLNAME));
				organizationBasic.setSlogan((String) profileBasic.get(OrganizationBasic.SLOGAN));
				organizationBasic.setFoundedDate((String) profileBasic.get(OrganizationBasic.FOUNDED_DATE));
				organizationBasic.setTerminatedDate((String) profileBasic.get(OrganizationBasic.TERMINATED_DATE));
				organizationBasic.setRegistrationCode((String) profileBasic.get(OrganizationBasic.REGISTRATION_CODE));
				organizationBasic.setRepresentative((String) profileBasic.get(OrganizationBasic.REPRESENTATIVE));
				organizationBasic.setDescription((String) profileBasic.get(OrganizationBasic.DESCRIPTION));
				setOrganizationBasic(organizationBasic);
			}
		}

		public void cleanAccountBasic() {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String loginId = dateFormat.format(new Date());
			txtPhone.setText("");
			txtFax.setText("");
			txtAddress.setText("");
			txtEmail.setText(loginId + "@hkt.com");
			loadCode();
		}
	}

	private boolean checkData() {
		if (txtSupplierCode.getText().trim().length() == 0) {
			lbSupplierCode.setForeground(Color.red);
			lbMessage.setText("Phải nhập mã nhà cung cấp !");
			lbMessage.setForeground(Color.red);
			return false;
		} else {
			if (isNew) {
				// Supplier sup = null;
				try {
					Supplier sup = SupplierModelManager.getInstance().getSupplierByCode(txtSupplierCode.getText().trim());
					if (sup != null) {
						lbMessage.setText("Mã nhà cung cấp đã tồn tại !");
						lbMessage.setForeground(Color.red);
						lbSupplierCode.setForeground(Color.red);
						if (sup.isRecycleBin()) {
							PanelRecybin panel = new PanelRecybin("Mã đã bị xóa trước đó!",
							    " Chọn ĐỒNG Ý để lấy lại thông tin ban đầu hoặc THOÁT và nhập mã khác!");
							panel.setName("Xoa");
							DialogResto dialog = new DialogResto(panel, false, 0, 120);
							dialog.setName("dlXoa");
							dialog.setTitle("Nhà cung cấp doanh nghiệp");
							dialog.setLocationRelativeTo(null);
							dialog.setModal(true);
							dialog.setVisible(true);
							if (panel.isDelete()) {
								restore = false;
								sup.setRecycleBin(false);
								SupplierModelManager.getInstance().saveSupplier(sup);
								lbSupplierCode.setForeground(Color.black);
								lbMessage.setText("");
								reset();
								return true;
							}
						}
						return false;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				// finally {

			}
			lbSupplierCode.setForeground(Color.black);
		}
		if (txtName.getText().trim().length() == 0) {
			lbName.setForeground(Color.red);
			lbMessage.setText("Phải nhập tên nhà cung cấp !");
			lbMessage.setForeground(Color.red);
			return false;
		} else {
			lbName.setForeground(Color.black);
		}
		if (txtEmail.getText().trim().length() == 0) {
			lbEmail.setForeground(Color.red);
			lbMessage.setForeground(Color.red);
			lbMessage.setText("Nhập thông tin email !");
			return false;
		} else {
			lbEmail.setForeground(Color.black);
		}
		if (txtPhone.getText().trim().length() != 0) {
			String pattern = "([\\d,\\s])*";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(txtPhone.getText());
			if (!m.matches()) {
				lbMessage.setText("Kiểm tra lại định dạng SĐT (Phân cách bằng dấu phẩy) !");
				lbMessage.setForeground(Color.red);
				lbPhone.setForeground(Color.red);
				return false;
			}
		} else {
			lbPhone.setForeground(Color.black);
		}
		if (txtFax.getText().trim().length() != 0) {
			String pattern = "([\\d,\\s])*";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(txtFax.getText());
			if (!m.matches()) {
				lbMessage.setText("Kiểm tra lại định dạng số Fax (Phân cách bằng dấu phẩy)");
				lbMessage.setForeground(Color.red);
				lbFax.setForeground(Color.red);
				return false;
			}
		} else {
			lbFax.setForeground(Color.black);
		}
		lbMessage.setText("");
		return true;
	}

	@Override
	public void save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {
			if (checkData()) {
				if (restore)
					btSaveActionPerformed();
			} else {
				return;
			}
			if (reset == true)
				((JDialog) getRootPane().getParent()).dispose();
		}
	}

	@Override
	public void edit() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {
			reset = true;
			setEdit(true);
		}
	}

	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {
			if (account != null) {
				Supplier supplier = SupplierModelManager.getInstance().getBydLoginId(account.getLoginId());
				if (supplier != null) {
					String str = "Xóa nhà cung cấp ";
					PanelChoise pnPanel = new PanelChoise(str + supplier + "?");
					pnPanel.setName("Xoa");
					DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
					dialog1.setName("dlXoa");
					dialog1.setTitle("Xóa nhà cung cấp DN");
					dialog1.setLocationRelativeTo(null);
					dialog1.setModal(true);
					dialog1.setVisible(true);
					if (pnPanel.isDelete() == true) {
						if (account.getProfiles().getBasic().get("Birthdaynhacungcapdoanhnghiep") != null) {
							Appointment appointment = HRModelManager.getInstance().getAppointmentById(
							    Long.parseLong(account.getProfiles().getBasic().get("Birthdaynhacungcapdoanhnghiep").toString()));
							HRModelManager.getInstance().deleteAppointment(appointment);
						}
						if (SupplierModelManager.getInstance().deleteSupplier(supplier)) {
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
		setEdit(true);
		txtSupplierCode.setEnabled(true);
	}

	@Override
	public void refresh() throws Exception {
		organizationBasicPanel.refeshData();
		setEdit(false);
	}

	public void setEdit(boolean flag) {
		txtSupplierCode.setEnabled(false);
		txtPhone.setEnabled(flag);
		txtEmail.setEnabled(flag);
		txtFax.setEnabled(flag);
		txtAddress.setEnabled(flag);
		txtDescription.setEnabled(flag);
		txtFullName.setEnabled(flag);
		txtName.setEnabled(flag);
		txtSlogan.setEnabled(flag);
		txtRegistrationCode.setEnabled(flag);
		txtRepresentative.setEnabled(flag);
		txtTerminatedDate.setEnabled(flag);
		txtFoundedDate.setEnabled(flag);
		cbGroupSupplier.setEnabled(flag);
		contactForm.setEdit(flag);
		businessRegistrationForm.setEdit(flag);
		chbInteractive.setEnabled(flag);
	}

	@Override
	public void createBeginTest() {
		if (MyPanel.isTest) {
			// Tạo nhóm nhà cung cấp
			try {
				AccountGroup root = AccountModelManager.getInstance().getRootGroupDetail().getChildren().get(0);
				AccountGroup parent = new AccountGroup(root, AccountModelManager.Supplier);
				parent.setLabel(AccountModelManager.Supplier);
				if (AccountModelManager.getInstance().getGroupByPath(root.getPath() + "/" + AccountModelManager.Supplier) == null) {
					parent = AccountModelManager.getInstance().saveGroup(parent);
				} else {
					parent = AccountModelManager.getInstance().getGroupByPath(parent.getPath());
				}
				AccountGroup accountGroup = new AccountGroup();
				accountGroup.setName("Mã Nhóm NCC HktTest1");
				accountGroup.setLabel("Nhóm NCC HktTest1");
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
		// Tạo 50 nhà cung cấp test chuyển trang
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

			Supplier supplier = new Supplier();
			supplier.setLoginId(account.getLoginId());
			supplier.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
			supplier.setCode("Mã nhà cung cấp HktTest" + i);
			supplier.setName("Tên nhà cung cấp HktTest" + i);
			supplier.setMobile("01234678" + i);
			try {
				account = AccountModelManager.getInstance().saveAccount(account);
				supplier = SupplierModelManager.getInstance().saveSupplier(supplier);
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
					SupplierModelManager.getInstance().deleteTest("HktTest");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
