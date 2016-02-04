package com.hkt.client.swingexp.app.nhansu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import com.hkt.client.swingexp.app.khachhang.BasicInformation;
import com.hkt.client.swingexp.app.khachhang.ContactForm;
import com.hkt.client.swingexp.app.khachhang.UserEducation;
import com.hkt.client.swingexp.app.khachhang.UserEducationForm;
import com.hkt.client.swingexp.app.khachhang.UserRelationship;
import com.hkt.client.swingexp.app.khachhang.UserRelationshipForm;
import com.hkt.client.swingexp.app.khachhang.UserWork;
import com.hkt.client.swingexp.app.khachhang.UserWorkForm;
import com.hkt.client.swingexp.app.khachhang.list.SupplierGroupJComboBox;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelManageCodes;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.GenericOptionModelManager;
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
import com.hkt.module.config.generic.Option;
import com.hkt.module.hr.entity.Appointment;
import com.hkt.module.hr.entity.Appointment.Status;
import com.hkt.module.partner.supplier.entity.Supplier;
import com.hkt.util.text.DateUtil;
import com.hkt.util.text.StringUtil;

/**
 * @name NHÀ CUNG CẤP
 * @author Phan Anh
 * @editdate 12/03/2015
 */

public class SupplierIsUser extends MyPanel implements IDialogMain {
	public static String permission;
	private ExtendJLabel lbAvatar, lbMobile, lbFax, lbAddress, lbEmail, lbGroupSupplier;
	private ExtendJTextField txtFax, txtAddress, txtMobile, txtEmail;
	private Account account;
	private final JTabbedPane tabbedPane;
	private JPanel panelAvatar;
	private UserEducationForm userEducationForm;
	private UserWorkForm userWorkForm;
	private UserRelationshipForm userRelationshipForm;
	private ContactForm contactForm;
	private BasicInformationPanel basicInformation;
	private JTextField txtNameSupplier, txtSupplierCode, txtHeight, txtWeight, txtCMT;
	private SupplierGroupJComboBox cbGroupSupplier;
	private JComboBox cboGender, cbMaritalStatus;
	private JTextArea txtHobby;
	private MyJDateChooser txtBirthDay;
	private JLabel lbMessage = new ExtendJLabel("");
	private boolean isNew;
	private AccountMembership accountMembership;
	private Supplier customerConfig;
	private Contact contact;
	private JLabel lbSupplierCode, lbNameSupplier, lbGender, lbBirthDay, lbWeight, lbHeight, lbCMT, lbMaritalStatus,
	    lbHobby;
	private boolean restore = true;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private boolean reset = false;
	private JCheckBox chbInteractive;

	public SupplierIsUser(Supplier customerConfig, boolean isNew) throws Exception {
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

		basicInformation = new BasicInformationPanel();
		userEducationForm = new UserEducationForm(account);
		userWorkForm = new UserWorkForm(account);
		userRelationshipForm = new UserRelationshipForm(account);
		contactForm = new ContactForm(account);

		tabbedPane = new JTabbedPane();
		tabbedPane.setName("tabbedPane");
		tabbedPane.setUI(new MyTabbedPaneUI());
		tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 14));
		tabbedPane.setForeground(Color.BLACK);
		tabbedPane.setBorder(null);
		tabbedPane.setOpaque(false);

		tabbedPane.add(basicInformation, "Thông tin cơ bản");
		tabbedPane.add(userEducationForm, "Học vấn");
		tabbedPane.add(userWorkForm, "Kinh nghiệm làm việc");
		tabbedPane.add(userRelationshipForm, "Quan hệ");
		tabbedPane.add(contactForm, "Thông tin liên lạc");
		UIManager.put("TabbedPane.contentOpaque", false);
		UIManager.put("TabbedPane.tabsOpaque", false);

		this.add(tabbedPane, BorderLayout.CENTER);
		this.add(lbMessage, BorderLayout.PAGE_END);
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabbedPane.getSelectedIndex() == 0) {
					basicInformation.updateContact();
				}
				if (tabbedPane.getSelectedIndex() == 4 && contactForm.getContactList() != null) {
					if (contactForm.getContactList().size() > 0) {
						String[] arraymobile = txtMobile.getText().split(",");
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
						if (!txtMobile.getText().isEmpty())
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
		this.isNew = isNew;
		if (isNew == false) {
			setEdit(isNew);
		}
	}

	private boolean checkData() {
		if (txtSupplierCode.getText().trim().length() == 0) {
			lbMessage.setText("Phải nhập mã nhà cung cấp !");
			lbSupplierCode.setForeground(Color.red);
			lbMessage.setForeground(Color.red);
			return false;
		} else {
			if (isNew) {
				// Supplier sup = null;
				try {
					Supplier sup = SupplierModelManager.getInstance().getSupplierByCode(txtSupplierCode.getText().trim());
					if (sup != null) {
						lbMessage.setText("Mã nhà cung cấp đã tồn tại !");
						lbSupplierCode.setForeground(Color.red);
						lbMessage.setForeground(Color.red);
						if (sup.isRecycleBin()) {
							PanelRecybin panel = new PanelRecybin("Mã đã bị xóa trước đó!",
							    " Chọn ĐỒNG Ý để lấy lại thông tin ban đầu hoặc THOÁT và nhập mã khác!");
							panel.setName("Xoa");
							DialogResto dialog = new DialogResto(panel, false, 0, 120);
							dialog.setName("dlXoa");
							dialog.setTitle("Nhà cung cấp cá nhân");
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
				// } finally {
			}
		}
		lbSupplierCode.setForeground(Color.black);

		if (txtNameSupplier.getText().trim().length() == 0) {
			lbMessage.setText("Phải nhập tên nhà cung cấp !");
			lbNameSupplier.setForeground(Color.red);
			lbMessage.setForeground(Color.red);
			return false;
		} else {
			lbNameSupplier.setForeground(Color.black);
		}
		if (txtMobile.getText().trim().length() != 0) {
			String pattern = "([\\d,\\s])*";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(txtMobile.getText());
			if (!m.matches()) {
				lbMessage.setText("Kiểm tra lại định dạng SĐT (Phân cách bằng dấu phẩy) !");
				lbMobile.setForeground(Color.red);
				lbMessage.setForeground(Color.red);
				return false;
			}
		} else {
			lbMobile.setForeground(Color.black);
		}
		if (txtFax.getText().trim().length() != 0) {
			String pattern = "([\\d,\\s])*";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(txtFax.getText());
			if (!m.matches()) {
				lbMessage.setText("Kiểm tra lại định dạng số Fax (Phân cách bằng dấu phẩy)");
				lbFax.setForeground(Color.red);
				lbMessage.setForeground(Color.red);
				return false;
			}
		} else {
			lbFax.setForeground(Color.black);
		}
		if (txtEmail.getText().trim().length() == 0) {
			lbMessage.setText("Nhập thông tin email !");
			lbEmail.setForeground(Color.red);
			lbMessage.setForeground(Color.red);
			return false;
		} else {
			lbEmail.setForeground(Color.black);
		}
		if (txtHeight.getText().trim().length() > 0) {
			try {
				Double.parseDouble(txtHeight.getText().trim());
				lbHeight.setForeground(Color.black);
			} catch (Exception e) {
				lbHeight.setForeground(Color.red);
				lbMessage.setText("Dữ liệu chiều cao sai định dạng !");
				lbMessage.setForeground(Color.red);
				return false;
			}
		} else {
			lbHeight.setForeground(Color.black);
		}

		if (txtWeight.getText().trim().length() > 0) {
			try {
				Double.parseDouble(txtWeight.getText().trim());
				lbWeight.setForeground(Color.black);
			} catch (Exception e) {
				lbWeight.setForeground(Color.red);
				lbMessage.setText("Dữ liệu cân nặng sai định dạng !");
				lbMessage.setForeground(Color.red);
				return false;
			}
		} else {
			lbWeight.setForeground(Color.black);
		}
		lbMessage.setText("");
		return true;
	}

	protected void btSaveActionPerformed() throws Exception {
		restore = true;

		if (tabbedPane.getSelectedIndex() == 4) {
			basicInformation.updateContact();
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
		String[] arraymobile = txtMobile.getText().split(",");
		for (int i = 0; i < arraymobile.length; i++) {
			arraymobile[i] = arraymobile[i].trim();
		}
		contact.setMobile(arraymobile);

		if (isNew == true) {
			account = new Account();
			account.setLoginId(new Date().getTime() + "");
			account.setPassword("0000");
			account.setType(Type.USER);
			account.setLastLoginTime(new Date());
			if (!txtMobile.getText().isEmpty() || !txtFax.getText().isEmpty() || !txtAddress.getText().isEmpty())
				contactForm.getContactList().add(0, contact);
			account.setContacts(contactForm.getContactList());
		} else {
			if (account.getContacts().size() > 0) {
				account.getContacts().get(0).setMobile(contact.getMobile());
				account.getContacts().get(0).setFax(contact.getFax());
				account.getContacts().get(0).setAddressNumber(contact.getAddressNumber());
				if (txtMobile.getText().isEmpty() && txtFax.getText().isEmpty() && txtAddress.getText().isEmpty()) {
					account.getContacts().remove(0);
				}
			} else {
				if (!txtMobile.getText().isEmpty() || !txtAddress.getText().isEmpty() || !txtFax.getText().isEmpty())
					account.getContacts().add(0, contact);
			}
		}
		account.setEmail(txtEmail.getText());

		Profile profileBasic = new Profile();
		BasicInformation basic = basicInformation.getBasicInformation();

		profileBasic.put(BasicInformation.LAST_NAME, basic.getLastName());
		profileBasic.put(BasicInformation.GENDER, basic.getGender());
		profileBasic.put(BasicInformation.AVATAR, basic.getAvatar());
		profileBasic.put(BasicInformation.BIRTHDAY, basic.getBirthday());
		profileBasic.put(BasicInformation.WEIGHT, basic.getWeight());
		profileBasic.put(BasicInformation.HEIGHT, basic.getHeight());
		profileBasic.put(BasicInformation.PERSONAL_ID, basic.getPersonalId());
		profileBasic.put(BasicInformation.MARITAL_STATUS, basic.getMaritalStatus());
		profileBasic.put(BasicInformation.HOBBY, basic.getHobby());
		// Tạo sinh nhật nhà cung cấp
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int daydate = calendar.get(Calendar.DAY_OF_MONTH);
		int monthdate = calendar.get(Calendar.MONTH);
		int yeardate = calendar.get(Calendar.YEAR);
		try {
			Date birthday = df.parse(basic.getBirthday());
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
				if (account.getProfiles() == null || account.getProfiles().getBasic().get("Birthdaynhacungcap") == null) {
					if (basic.getBirthday() != null) {
						Appointment appointment = new Appointment();
						appointment.setName("Sinh nhật NCC " + basic.getLastName());
						appointment.setContent("Sinh nhật");
						appointment.setDateStart(birthday);
						appointment.setStatus(Status.UNACCOMPLISHED);
						appointment.setCreatedBy("nhacungcap");
						appointment = HRModelManager.getInstance().saveAppointment(appointment);
						profileBasic.put("Birthdaynhacungcap", appointment.getId());
					}
				} else {
					Appointment appointment = HRModelManager.getInstance().getAppointmentById(
					    Long.parseLong(account.getProfiles().getBasic().get("Birthdaynhacungcap").toString()));
					appointment.setDateStart(birthday);
					appointment = HRModelManager.getInstance().saveAppointment(appointment);
					profileBasic.put("Birthdaynhacungcap", appointment.getId());
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		// Kết thúc tạo sinh nhật nhà cung cấp
		List<UserEducation> userEducations = userEducationForm.getUserEducationList();
		Profile[] profileEducations = new Profile[userEducations.size()];
		if (userEducations.size() > 0) {
			for (int i = 0; i < profileEducations.length; i++) {
				Profile profile = new Profile();
				profile.put(UserEducation.SCHOOL_INSTITUTE, userEducations.get(i).getSchoolOrInstitute());
				profile.put(UserEducation.FROM, userEducations.get(i).getFrom());
				profile.put(UserEducation.TO, userEducations.get(i).getTo());
				profile.put(UserEducation.MAJOF, userEducations.get(i).getMajor());
				profile.put(UserEducation.CERTIFICATE, userEducations.get(i).getCertificate());
				profile.put(UserEducation.LANGUAGE, userEducations.get(i).getLanguage());
				profileEducations[i] = profile;
			}
		}

		List<UserWork> userWorks = userWorkForm.getUserWorkList();
		Profile[] profileUserWorks = new Profile[userWorks.size()];
		if (userWorks.size() > 0) {
			for (int i = 0; i < profileUserWorks.length; i++) {
				Profile profile = new Profile();
				profile.put(UserWork.ORGANIZATION, userWorks.get(i).getOrganization());
				profile.put(UserWork.FROM, userWorks.get(i).getFrom());
				profile.put(UserWork.TO, userWorks.get(i).getTo());
				profile.put(UserWork.POSITION, userWorks.get(i).getPosition());
				profile.put(UserWork.DESCRIPTION, userWorks.get(i).getDescription());
				profileUserWorks[i] = profile;
			}
		}

		List<UserRelationship> userRelaitonships = userRelationshipForm.getUserRelationshipList();
		Profile[] profileRelationships = new Profile[userRelaitonships.size()];
		if (userRelationshipForm.getAppointments() != null) {
			for (int i = 0; i < userRelationshipForm.getAppointments().size(); i++) {
				HRModelManager.getInstance().deleteAppointment(userRelationshipForm.getAppointments().get(i));
			}

		}
		if (userRelaitonships.size() > 0) {
			for (int i = 0; i < profileRelationships.length; i++) {
				Profile profile = new Profile();
				profile.put(UserRelationship.RELATION, userRelaitonships.get(i).getRelation());
				profile.put(UserRelationship.GENDER, userRelaitonships.get(i).getGender());
				profile.put(UserRelationship.FIRST_NAME, userRelaitonships.get(i).getFirstName());
				profile.put(UserRelationship.LAST_NAME, userRelaitonships.get(i).getLastName());
				profile.put(UserRelationship.BIRTH_DAY, userRelaitonships.get(i).getBirthday());
				profile.put(UserRelationship.OCCUPATION, userRelaitonships.get(i).getOccupation());
				profileRelationships[i] = profile;
				// Tạo sinh nhật quan hệ nhà cung cấp
				try {
					Date birthdayuserRelaitonships = df.parse(userRelaitonships.get(i).getBirthday());
					Calendar calendar3 = Calendar.getInstance();
					calendar3.setTime(birthdayuserRelaitonships);
					int daybirthdayuserRelaitonships = calendar3.get(Calendar.DAY_OF_MONTH);
					int monthbirthdayuserRelaitonships = calendar3.get(Calendar.MONTH);
					int yearbirthdayuserRelaitonships = calendar3.get(Calendar.YEAR);
					if (yearbirthdayuserRelaitonships < yeardate) {
						if (((monthbirthdayuserRelaitonships < monthdate) || (monthbirthdayuserRelaitonships >= monthdate && daybirthdayuserRelaitonships < daydate))) {
							yearbirthdayuserRelaitonships = yeardate + 1;
						} else {
							yearbirthdayuserRelaitonships = yeardate;
						}
					} else if (yearbirthdayuserRelaitonships == yeardate) {
						if (((monthbirthdayuserRelaitonships < monthdate) || (monthbirthdayuserRelaitonships == monthdate && daybirthdayuserRelaitonships < daydate))) {
							yearbirthdayuserRelaitonships = yeardate + 1;
						}
					}
					calendar3.set(Calendar.YEAR, yearbirthdayuserRelaitonships);
					calendar3.set(Calendar.HOUR_OF_DAY, 7);
					birthdayuserRelaitonships = calendar3.getTime();
					Profile p = AccountModelManager.getInstance().getProfileConfigAdmin();
					if (p.get("DailyWork") != null) {
						if (account.getProfiles() == null
						    || account.getProfiles().getBasic().get(userRelaitonships.get(i).getRelation()) == null) {
							if (userRelaitonships.get(i).getBirthday() != null) {
								Appointment appointment = new Appointment();
								appointment.setName("Sinh nhật " + userRelaitonships.get(i).getRelation() + " NCC "
								    + basic.getLastName());

								appointment.setDateStart(birthdayuserRelaitonships);
								appointment.setStatus(Status.UNACCOMPLISHED);
								appointment.setCreatedBy("nhacungcap");
								appointment = HRModelManager.getInstance().saveAppointment(appointment);
								profileBasic.put(userRelaitonships.get(i).getRelation(), appointment.getId());
							}
						} else {
							Appointment appointment = HRModelManager.getInstance().getAppointmentById(
							    Long.parseLong(account.getProfiles().getBasic().get(userRelaitonships.get(i).getRelation())
							        .toString()));
							appointment.setDateStart(birthdayuserRelaitonships);
							appointment = HRModelManager.getInstance().saveAppointment(appointment);
							profileBasic.put(userRelaitonships.get(i).getRelation(), appointment.getId());
						}
					}

				} catch (Exception e) {
					// TODO: handle exception
				}

				// Kết thúc tạo sinh nhật quan hệ nhà cung cấp
			}
		}

		Profiles profiles = new Profiles();
		profiles.setBasic(profileBasic);
		profiles.setUserEducations(profileEducations);
		profiles.setUserWorks(profileUserWorks);
		profiles.setUserRelationships(profileRelationships);
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
			supplier.setName(txtNameSupplier.getText());
			if (!txtMobile.getText().isEmpty())
				supplier.setMobile(txtMobile.getText());
			supplier.setLoginId(acc.getLoginId());
			supplier.setInteractive(chbInteractive.isSelected());
			supplier.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
			supplier.setAddress(txtAddress.getText());
			supplier.setBirthDay(txtBirthDay.getDate());
			supplier.setType("Cá nhân");
			SupplierModelManager.getInstance().saveSupplier(supplier);
		} else {
			if (customerConfig != null) {
				customerConfig.setName(txtNameSupplier.getText());
				if (!txtMobile.getText().isEmpty())
					customerConfig.setMobile(txtMobile.getText());
				customerConfig.setAddress(txtAddress.getText());
				customerConfig.setInteractive(chbInteractive.isSelected());
				customerConfig.setBirthDay(txtBirthDay.getDate());
				customerConfig.setType("Cá nhân");
				SupplierModelManager.getInstance().saveSupplier(customerConfig);
			}
		}
		btClearActionPerformed();
	}

	protected void btClearActionPerformed() {
		account = new Account();
		basicInformation.cleanAccountBasic();
		basicInformation.setBasicInformation(new BasicInformation());
		userEducationForm.cleanUserEducation();
		userWorkForm.cleanUserWork();
		userRelationshipForm.cleanUserRelationship();
		contactForm.cleanContact();
		lbAvatar.setIcon(null);
		lbAvatar.setText("Chọn ảnh");
		chbInteractive.setSelected(false);
		isNew = true;
		setEdit(isNew);
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
	
	public class BasicInformationPanel extends JPanel {

		private List<Option> dataGender, dataMaritalStatus;
		private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		private BasicInformation basicInformation;

		public BasicInformationPanel() throws Exception {
			createBeginTest();
			init();
			refeshData();
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void init() {
			setOpaque(false);
			setBorder(BorderFactory.createEmptyBorder(20, 5, 0, 5));
			lbMobile = new ExtendJLabel("ĐTDĐ");
			lbFax = new ExtendJLabel("Fax");
			lbAddress = new ExtendJLabel("Địa chỉ");
			lbEmail = new ExtendJLabel("Email");
			lbGroupSupplier = new ExtendJLabel("Nhóm NCC");
			lbSupplierCode = new ExtendJLabel("Mã NCC");
			lbNameSupplier = new ExtendJLabel("Tên NCC");
			lbGender = new ExtendJLabel("Giới tính");
			lbBirthDay = new ExtendJLabel("Ngày sinh");
			lbWeight = new ExtendJLabel("Cân nặng");
			lbHeight = new ExtendJLabel("Chiều cao");
			lbCMT = new ExtendJLabel("CMND");
			lbMaritalStatus = new ExtendJLabel("Hôn nhân");
			lbHobby = new ExtendJLabel("Sở thích");
			lbMessage.setForeground(Color.red);

			txtMobile = new ExtendJTextField();
			txtMobile.setName("txtMobile");
			txtFax = new ExtendJTextField();
			txtFax.setName("txtFax");
			txtAddress = new ExtendJTextField();
			txtAddress.setName("txtAddress");
			txtEmail = new ExtendJTextField();
			txtEmail.setName("txtEmail");
			txtSupplierCode = new ExtendJTextField();
			txtNameSupplier = new ExtendJTextField();
			txtNameSupplier.setName("txtLastName");
			txtBirthDay = new MyJDateChooser();
			txtBirthDay.setDate(null);
			txtBirthDay.setName("txtBirthDay");
			txtHeight = new ExtendJTextField();
			txtHeight.setName("txtHeight");
			txtWeight = new ExtendJTextField();
			txtWeight.setName("txtWeight");
			txtCMT = new ExtendJTextField();
			txtCMT.setName("txtCMT");
			txtHobby = new ExtendJTextArea(5, 30);
			txtHobby.setName("txtHobby");

			cbGroupSupplier = new SupplierGroupJComboBox();
			cbGroupSupplier.setName("cbGroupSupplier");
			cboGender = new ExtendJComboBox();
			cboGender.setName("cboGender");
			cbMaritalStatus = new ExtendJComboBox();
			cbMaritalStatus.setName("cbMaritalStatus");

			panelAvatar = new JPanel();
			panelAvatar.setOpaque(false);
			panelAvatar.setLayout(new GridLayout());
			lbAvatar = new ExtendJLabel("Chọn ảnh", SwingConstants.CENTER);
			lbAvatar.setBorder(BorderFactory.createEtchedBorder());
			panelAvatar.add(lbAvatar);

			MyGroupLayout groupLayout = new MyGroupLayout(this);

			groupLayout.add(0, 0, lbSupplierCode);
			groupLayout.add(0, 1, txtSupplierCode);

			groupLayout.add(1, 0, lbNameSupplier);
			groupLayout.add(1, 1, txtNameSupplier);

			groupLayout.add(2, 0, lbMobile);
			groupLayout.add(2, 1, txtMobile);

			groupLayout.add(3, 0, lbFax);
			groupLayout.add(3, 1, txtFax);
			groupLayout.add(3, 2, lbAddress);
			groupLayout.add(3, 3, txtAddress);

			groupLayout.add(4, 0, lbEmail);
			groupLayout.add(4, 1, txtEmail);
			groupLayout.add(4, 2, lbGroupSupplier);
			groupLayout.add(4, 3, cbGroupSupplier);

			groupLayout.add(5, 0, lbGender);
			JPanel p = new JPanel(new BorderLayout());
			p.setOpaque(false);
			chbInteractive = new JCheckBox("Có giao dịch");
			chbInteractive.setOpaque(false);
			cboGender.setSize(10, 18);
			cboGender.setPreferredSize(new Dimension(10, 18));
			JScrollPane sc = new  JScrollPane();
			sc.setViewportView(cboGender);
			p.add(sc, BorderLayout.CENTER);
			p.add(chbInteractive, BorderLayout.EAST);
			groupLayout.add(5, 1, p);
			groupLayout.add(5, 2, lbBirthDay);
			groupLayout.add(5, 3, txtBirthDay);

			groupLayout.add(6, 0, lbWeight);
			groupLayout.add(6, 1, txtWeight);
			groupLayout.add(6, 2, lbHeight);
			groupLayout.add(6, 3, txtHeight);

			groupLayout.add(7, 0, lbCMT);
			groupLayout.add(7, 1, txtCMT);
			groupLayout.add(7, 2, lbMaritalStatus);
			groupLayout.add(7, 3, cbMaritalStatus);

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setViewportView(txtHobby);
			groupLayout.add(8, 0, lbHobby);
			groupLayout.add(8, 1, scrollPane);
			groupLayout.addImage(panelAvatar);
			groupLayout.updateGui();

			lbAvatar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (txtNameSupplier.isEnabled())
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
			try {

				dataGender = new ArrayList<Option>();
				dataGender.add(new Option());
				dataGender.addAll(GenericOptionModelManager.getInstance().getOptionGroup("config", "LocaleService", "genders")
				    .getOptions());
				cboGender.setModel(new DefaultComboBoxModel(dataGender.toArray()));
				dataMaritalStatus = new ArrayList<Option>();
				dataMaritalStatus.add(new Option());
				dataMaritalStatus.addAll(GenericOptionModelManager.getInstance()
				    .getOptionGroup("config", "LocaleService", "marital_status").getOptions());
				cbMaritalStatus.setModel(new DefaultComboBoxModel(dataMaritalStatus.toArray()));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		public BasicInformation getBasicInformation() {
			basicInformation = new BasicInformation();
			basicInformation.setLastName(txtNameSupplier.getText());
			basicInformation.setGender(((Option) cboGender.getSelectedItem()).getCode());

			try {
				basicInformation.setBirthday(df.format(txtBirthDay.getDate()));
			} catch (Exception e) {
				basicInformation.setBirthday("");
			}
			ImageIcon image = (ImageIcon) lbAvatar.getIcon();
			if (image != null) {
				String urlImage = ImageTool.getInstance().encodeToString(image.getImage());
				basicInformation.setAvatar(urlImage);
			} else {
				basicInformation.setAvatar("");
			}

			basicInformation.setWeight(txtWeight.getText());
			basicInformation.setHeight(txtHeight.getText());
			basicInformation.setPersonalId(txtCMT.getText());
			basicInformation.setMaritalStatus(((Option) cbMaritalStatus.getSelectedItem()).getCode());
			basicInformation.setHobby(txtHobby.getText());

			return basicInformation;
		}

		public void setBasicInformation(BasicInformation basicInformation) {
			this.basicInformation = basicInformation;
			txtNameSupplier.setText(basicInformation.getLastName());

			try {
				txtBirthDay.setDate(df.parse(basicInformation.getBirthday()));
			} catch (Exception e) {
				txtBirthDay.setDate(null);
			}

			txtHeight.setText(String.valueOf(basicInformation.getHeight()));
			txtWeight.setText(String.valueOf(basicInformation.getWeight()));
			txtCMT.setText(basicInformation.getPersonalId());
			txtHobby.setText(basicInformation.getHobby());

			for (int i = 0; i < cboGender.getItemCount(); i++) {
				if (cboGender.getSelectedItem() instanceof Object
				    && ((Option) cboGender.getItemAt(i)).getCode().equals(basicInformation.getGender())) {
					cboGender.setSelectedIndex(i);
					break;
				}
			}

			for (int i = 0; i < cbMaritalStatus.getItemCount(); i++) {
				if (cbMaritalStatus.getSelectedItem() instanceof Object
				    && ((Option) cbMaritalStatus.getItemAt(i)).getCode().equals(basicInformation.getMaritalStatus())) {
					cbMaritalStatus.setSelectedIndex(i);
					break;
				}
			}
		}

		public void updateContact() {
			if (contactForm.getContactList() != null && contactForm.getContactList().size() > 0) {
				txtMobile.setText(StringUtil.joinStringArray(contactForm.getContactList().get(0).getMobile()));
				txtFax.setText(StringUtil.joinStringArray(contactForm.getContactList().get(0).getFax()));
				txtAddress.setText(contactForm.getContactList().get(0).getAddressNumber());
				if (!StringUtil.joinStringArray(contactForm.getContactList().get(0).getEmail()).isEmpty())
					txtEmail.setText(StringUtil.joinStringArray(contactForm.getContactList().get(0).getEmail()));
			}
			// else {
			// txtMobile.setText("");
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
					txtMobile.setText(StringUtil.joinStringArray(account.getContacts().get(0).getMobile()));
					txtFax.setText(StringUtil.joinStringArray(account.getContacts().get(0).getFax()));
					txtAddress.setText(account.getContacts().get(0).getAddressNumber());
				}

				try {
					String image = account.getProfiles().getBasic().get(BasicInformation.AVATAR).toString();
					ImageIcon icon = ImageTool.getInstance().decodeToImage(image);
					lbAvatar.setText("");
					lbAvatar.setIcon(icon);
				} catch (Exception e) {
					lbAvatar.setIcon(null);
					lbAvatar.setText("Chọn ảnh");
				}

				if (accountMembership != null) {
					String groupPath = accountMembership.getGroupPath();
					if (groupPath != null) {
						try {
							AccountGroup group = AccountModelManager.getInstance().getGroupByPath(groupPath);
							cbGroupSupplier.setSelectedItem(group);
						} catch (Exception e) {
							e.printStackTrace();
							cbGroupSupplier.setSelectedIndex(0);
						}
					}
				}

				if (account != null) {
					basicInformation = new BasicInformation();
					Profile profileBasic = account.getProfiles().getBasic();
					basicInformation.setLastName((String) profileBasic.get(BasicInformation.LAST_NAME));
					basicInformation.setGender((String) profileBasic.get(BasicInformation.GENDER));
					basicInformation.setBirthday((String) profileBasic.get(BasicInformation.BIRTHDAY));
					basicInformation.setAvatar((String) profileBasic.get(BasicInformation.AVATAR));
					basicInformation.setWeight(String.valueOf(profileBasic.get(BasicInformation.WEIGHT)));
					basicInformation.setHeight(String.valueOf(profileBasic.get(BasicInformation.HEIGHT)));
					basicInformation.setPersonalId((String) profileBasic.get(BasicInformation.PERSONAL_ID));
					basicInformation.setMaritalStatus((String) profileBasic.get(BasicInformation.MARITAL_STATUS));
					basicInformation.setHobby((String) profileBasic.get(BasicInformation.HOBBY));
					setBasicInformation(basicInformation);
				}
				if(customerConfig!=null){
					chbInteractive.setSelected(customerConfig.isInteractive());
				}
			}
		}

		public void cleanAccountBasic() {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String loginId = dateFormat.format(new Date());
			txtEmail.setText(loginId + "@hkt.com");
			txtSupplierCode.setText(loginId);
			txtMobile.setText("");
			txtAddress.setText("");
			txtFax.setText("");
		}
	}

	@Override
	public void save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.WRITE
		    || UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {
			if (checkData()) {
				if (restore)
					btSaveActionPerformed();
			} else {
				return;
			}
			if (reset == true)
				((DialogMain) getRootPane().getParent()).dispose();
		} else {

			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return;
		}
	}

	@Override
	public void edit() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.WRITE
		    || UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {
			reset = true;
			setEdit(true);
		} else {

			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return;
		}
	}

	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {
			if (account != null) {
				userRelationshipForm = new UserRelationshipForm(account);
				List<UserRelationship> userRelaitonships = userRelationshipForm.getUserRelationshipList();
				Profile[] profileRelationships = new Profile[userRelaitonships.size()];
				Supplier supplier = SupplierModelManager.getInstance().getBydLoginId(account.getLoginId());
				if (supplier != null) {
					String str = "Xóa nhà cung cấp ";
					PanelChoise pnPanel = new PanelChoise(str + supplier + "?");
					pnPanel.setName("Xoa");
					DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
					dialog1.setName("dlXoa");
					dialog1.setTitle("Xóa nhà cung cấp cá nhân");
					dialog1.setLocationRelativeTo(null);
					dialog1.setModal(true);
					dialog1.setVisible(true);
					if (pnPanel.isDelete() == true) {
						if (userRelaitonships.size() > 0) {
							for (int i = 0; i < profileRelationships.length; i++) {
								if (account.getProfiles().getUserRelationships()[i].get(userRelaitonships.get(i).getRelation()) != null) {
									Appointment appointment = HRModelManager.getInstance().getAppointmentById(
									    Long.parseLong(account.getProfiles().getUserRelationships()[i].get(
									        userRelaitonships.get(i).getRelation()).toString()));
									HRModelManager.getInstance().deleteAppointment(appointment);
								}
							}
						}
						if (account.getProfiles().getBasic().get("Birthdaynhacungcap") != null) {
							Appointment appointment = HRModelManager.getInstance().getAppointmentById(
							    Long.parseLong(account.getProfiles().getBasic().get("Birthdaynhacungcap").toString()));
							HRModelManager.getInstance().deleteAppointment(appointment);
						}
						if (SupplierModelManager.getInstance().deleteSupplier(supplier)) {
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
			((DialogMain) getRootPane().getParent()).dispose();
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
		basicInformation.refeshData();
		setEdit(false);
	}

	public void setEdit(boolean flag) {
		txtSupplierCode.setEnabled(false);
		txtMobile.setEnabled(flag);
		txtEmail.setEnabled(flag);
		txtFax.setEnabled(flag);
		txtAddress.setEnabled(flag);
		txtBirthDay.setEnabled(flag);
		txtCMT.setEnabled(flag);
		txtHobby.setEnabled(flag);
		txtNameSupplier.setEnabled(flag);
		txtWeight.setEnabled(flag);
		contactForm.setEdit(flag);
		userEducationForm.setEdit(flag);
		userRelationshipForm.setEdit(flag);
		userWorkForm.setEdit(flag);
		cbGroupSupplier.setEnabled(flag);
		cbMaritalStatus.setEnabled(flag);
		cboGender.setEnabled(flag);
		txtHeight.setEnabled(flag);
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
			account.setType(Type.USER);
			Contact contact = new Contact();
			String[] phone = { "012346789" + i };
			contact.setMobile(phone);
			account.addContact(contact);

			Profile profile = new Profile();
			profile.put(BasicInformation.LAST_NAME, "Tên NV HktTest" + i + 10);
			profile.put(BasicInformation.BIRTHDAY, "01/01/2014");
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
