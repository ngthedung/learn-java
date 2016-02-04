package com.hkt.client.swingexp.app.nhansu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.poi.ss.formula.udf.UDFFinder;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.GenericOptionServiceClient;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextArea;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.ImageTool;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.DepartmentJComboBox;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.core.MyTabbedPaneUI;
import com.hkt.client.swingexp.app.hethong.PanelOption;
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
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
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
import com.hkt.module.config.generic.OptionGroup;
import com.hkt.module.hr.entity.Appointment;
import com.hkt.module.hr.entity.Appointment.Status;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.supplier.entity.Supplier;
import com.hkt.util.text.StringUtil;

/**
 * @name NHÂN SỰ MỚI
 * @author Phan Anh
 * @editdate 12/03/2015
 */

public class AccountIsUser extends MyPanel implements IDialogMain {
	public static String					permission;
	private ExtendJLabel					lbAvatar,	lbLoginId, lbPassword, lbRepeatPassword, lbEmail, lbChoiceOrganization, lbStartDate, lbLeaveDate, lbPosition, lbEmployeeCode, lbMobile, lbAddress, lbMessage;
	private ExtendJLabel					lbNickName, lbFullName, lbGender, lbBirthDay, lbWeight, lbHeight, lbCMT, lbMaritalStatus, lbHobby;
	private ExtendJTextField			txtNickName, txtLoginId, txtEmail,  txtMobile, txtAddress, txtFullName, txtHeight, txtWeight, txtCMT;
	private JPasswordField				txtPassword, txtRepatePassword;
	private MyJDateChooser				dcStartDate,  txtBirthDay;
	private JPanel								panelAvatar;
	private final JTabbedPane			tabbedPane;
	private DateFormat						df			= new SimpleDateFormat("dd/MM/yyyy");
	private UserEducationForm			userEducationForm;
	private UserWorkForm					userWorkForm;
	private UserRelationshipForm	userRelationshipForm;
	private ContactForm						contactForm;
	private BasicInformationPanel	basicInformation;
	private Account								account;
	
	private JComboBox							cboGender, cbMaritalStatus;
	private ExtendJTextArea				txtHobby;
	private List<Option>					options;
	private Contact								contact;
	private List<AccountGroup>		listSelectedDepartment;
	private boolean								restore	= true;
	private boolean								isNew;
	private String								lst;
	private boolean reset = false;

	public AccountIsUser(Account empConfig, boolean isNew) throws Exception {
		setLayout(new BorderLayout());
		setOpaque(false);

		if (empConfig != null) {
		
			this.account = empConfig;
		}

		GenericOptionServiceClient clientCore = ClientContext.getInstance().getBean(GenericOptionServiceClient.class);
		OptionGroup group = clientCore.getOptionGroup("accounting", "AccountingService", "contributor_role");
		options = new ArrayList<Option>();
		if (group != null) {
			if (group.getOptions() != null) {
				options = group.getOptions();
			}
		}

		basicInformation = new BasicInformationPanel();
		userEducationForm = new UserEducationForm(account);
		userWorkForm = new UserWorkForm(account);
		userRelationshipForm = new UserRelationshipForm(account);
		contactForm = new ContactForm(account);
		UIManager.put("TabbedPane.contentOpaque", false);
		UIManager.put("TabbedPane.tabsOpaque", false);
		tabbedPane = new JTabbedPane();
		tabbedPane.setName("tabbedPane");
		tabbedPane.setUI(new MyTabbedPaneUI());
		tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 14));
		tabbedPane.setForeground(Color.BLACK);
		tabbedPane.setBorder(null);
		tabbedPane.setOpaque(false);

		tabbedPane.add("Thông tin cơ bản", basicInformation);
		tabbedPane.add("Học vấn", userEducationForm);
		tabbedPane.add(userWorkForm, "Kinh nghiệm làm việc");
		tabbedPane.add(userRelationshipForm, "Quan hệ");
		tabbedPane.add(contactForm, "Thông tin liên lạc");
		add(tabbedPane, BorderLayout.CENTER);
		add(lbMessage, BorderLayout.PAGE_END);
		this.isNew = isNew;
		if (isNew == false) {
			setEditData(isNew);
		}

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
						if (!txtMobile.getText().isEmpty())
							contactForm.getContactList().get(0).setMobile(arraymobile);
						if (!txtAddress.getText().isEmpty())
							contactForm.getContactList().get(0).setAddressNumber(txtAddress.getText());
						if (!txtEmail.getText().isEmpty())
							contactForm.getContactList().get(0).setEmail(arrayEmail);
					}
				}
			}
		});
		basicInformation.updateContact();
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

		String[] arraymobile = txtMobile.getText().split(",");
		for (int i = 0; i < arraymobile.length; i++) {
			arraymobile[i] = arraymobile[i].trim();
		}
		contact.setMobile(arraymobile);

		if (isNew == true ) {
			account = new Account();
			account.setLoginId(txtLoginId.getText());
			account.setType(Type.USER);
			account.setLastLoginTime(new Date());
			if (!txtMobile.getText().isEmpty() || !txtAddress.getText().isEmpty())
				contactForm.getContactList().add(0, contact);
			account.setContacts(contactForm.getContactList());
		} else {
			if (account.getContacts().size() > 0) {
				account.getContacts().get(0).setMobile(contact.getMobile());
				account.getContacts().get(0).setEmail(contact.getEmail());
				account.getContacts().get(0).setAddressNumber(contact.getAddressNumber());
				if (txtMobile.getText().isEmpty() && txtAddress.getText().isEmpty() && txtEmail.getText().isEmpty()) {
					account.getContacts().remove(0);
				}
			} else {
				if (!txtMobile.getText().isEmpty() || !txtAddress.getText().isEmpty())
					account.getContacts().add(0, contact);
			}
		}

		account.setPassword(txtPassword.getText());
		account.setEmail(txtEmail.getText());

		Profile profileBasic = new Profile();
		BasicInformation basic = basicInformation.getBasicInformation();
		profileBasic.put(BasicInformation.FIRST_NAME, basic.getFirstName());
		profileBasic.put(BasicInformation.LAST_NAME, basic.getLastName());
		profileBasic.put(BasicInformation.GENDER, basic.getGender());
		profileBasic.put(BasicInformation.BIRTHDAY, basic.getBirthday());
		profileBasic.put(BasicInformation.AVATAR, basic.getAvatar());
		profileBasic.put(BasicInformation.WEIGHT, basic.getWeight());
		profileBasic.put(BasicInformation.HEIGHT, basic.getHeight());
		profileBasic.put(BasicInformation.PERSONAL_ID, basic.getPersonalId());
		profileBasic.put(BasicInformation.MARITAL_STATUS, basic.getMaritalStatus());
		profileBasic.put(BasicInformation.HOBBY, basic.getHobby());
		//Tạo sinh nhật nhân sự
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
				if (((monthbirthday < monthdate) || ((monthbirthday == monthdate) && (daybirthday < daydate)))) {
					yearbirthday = yeardate + 1;
				}
			}
			calendar2.set(Calendar.YEAR, yearbirthday);
			calendar2.set(Calendar.HOUR_OF_DAY, 7);
			birthday = calendar2.getTime();
			if (account.getProfiles() == null || account.getProfiles().getBasic().get("Birthdaynhansu") == null) {
				if (basic.getBirthday() != null) {
					Appointment appointment = new Appointment();
					appointment.setName("Sinh nhật nhân viên " + basic.getLastName());

					appointment.setDateStart(birthday);
					appointment.setStatus(Status.UNACCOMPLISHED);
					appointment.setCreatedBy("nhansu");
					appointment = HRModelManager.getInstance().saveAppointment(appointment);
					profileBasic.put("Birthdaynhansu", appointment.getId());
				}
			} else {
				Appointment appointment = HRModelManager.getInstance().getAppointmentById(Long.parseLong(account.getProfiles().getBasic().get("Birthdaynhansu").toString()));
				appointment.setDateStart(birthday);
				appointment = HRModelManager.getInstance().saveAppointment(appointment);
				profileBasic.put("Birthdaynhansu", appointment.getId());
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}

		//Kết thúc tạo sinh nhật nhà cung cấp
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
				//Tạo sinh nhật quan hệ nhà cung cấp
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

					if (account.getProfiles() == null || account.getProfiles().getBasic().get(userRelaitonships.get(i).getRelation()) == null) {
						if (userRelaitonships.get(i).getBirthday() != null) {
							Appointment appointment = new Appointment();
							appointment.setName("Sinh nhật " + userRelaitonships.get(i).getRelation() + " nhân viên " + basic.getLastName());
							appointment.setDateStart(birthdayuserRelaitonships);
							appointment.setStatus(Status.UNACCOMPLISHED);
							appointment.setCreatedBy("nhansu");
							appointment = HRModelManager.getInstance().saveAppointment(appointment);
							profileBasic.put(userRelaitonships.get(i).getRelation(), appointment.getId());
						}
					} else {
						Appointment appointment = HRModelManager.getInstance().getAppointmentById(Long.parseLong(account.getProfiles().getBasic().get(userRelaitonships.get(i).getRelation()).toString()));
						appointment.setDateStart(birthdayuserRelaitonships);
						appointment = HRModelManager.getInstance().saveAppointment(appointment);
						profileBasic.put(userRelaitonships.get(i).getRelation(), appointment.getId());
					}
				} catch (Exception e) {
						e.printStackTrace();
				}
			}
		}

		Profiles profiles = new Profiles();
		profiles.setBasic(profileBasic);
		profiles.setUserEducations(profileEducations);
		profiles.setUserWorks(profileUserWorks);
		profiles.setUserRelationships(profileRelationships);
		account.setProfiles(profiles);
		Account acc = AccountModelManager.getInstance().saveAccount(account);

		((JDialog)getRootPane().getParent()).dispose();
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
		isNew = true;
	
		setEditData(isNew);
		txtLoginId.setEnabled(isNew);
		dcStartDate.setDate(new Date());
		txtBirthDay.setDate(null);
	}

	public class BasicInformationPanel extends JPanel {
		private List<Option>			dataGender, dataMaritalStatus;
		private BasicInformation	basicInformation;
		
		public BasicInformationPanel() {
			init();
			refeshData();
		}

		public void init() {
			setOpaque(false);
			setBorder(BorderFactory.createEmptyBorder(20, 5, 0, 5));

			lbLoginId = new ExtendJLabel("Tài khoản");
			lbPassword = new ExtendJLabel("Mật khẩu");
			lbRepeatPassword = new ExtendJLabel("Nhập lại MK");
			lbEmail = new ExtendJLabel("Email");
			lbChoiceOrganization = new ExtendJLabel("Phòng ban");
			lbStartDate = new ExtendJLabel("Bắt đầu");
			lbLeaveDate = new ExtendJLabel("Kết thúc");
			lbPosition = new ExtendJLabel("Vị trí");
			lbEmployeeCode = new ExtendJLabel("Mã NV");
			lbNickName = new ExtendJLabel("Biệt danh");
			lbFullName = new ExtendJLabel("Họ & tên");
			lbGender = new ExtendJLabel("Giới tính");
			lbBirthDay = new ExtendJLabel("Ngày sinh");
			lbWeight = new ExtendJLabel("Cân nặng");
			lbHeight = new ExtendJLabel("Chiều cao");
			lbCMT = new ExtendJLabel("CMND");
			lbMaritalStatus = new ExtendJLabel("Hôn nhân");
			lbHobby = new ExtendJLabel("Sở thích");
			lbMobile = new ExtendJLabel("ĐTDĐ");
			lbAddress = new ExtendJLabel("Địa chỉ");
			lbMessage = new ExtendJLabel("");
			lbMessage.setForeground(Color.red);

			options.add(0, null);

			panelAvatar = new JPanel();
			panelAvatar.setOpaque(false);
			panelAvatar.setLayout(new BorderLayout());
			lbAvatar = new ExtendJLabel("Chọn ảnh", SwingConstants.CENTER);
			lbAvatar.setBorder(BorderFactory.createEtchedBorder());
			panelAvatar.add(lbAvatar);

			txtLoginId = new ExtendJTextField();
			txtPassword = new JPasswordField();
			txtRepatePassword = new JPasswordField();
			txtEmail = new ExtendJTextField();
			txtEmail.setName("txtEmail");
			dcStartDate = new MyJDateChooser();
			txtAddress = new ExtendJTextField();
			txtAddress.setName("txtAddress");
			txtMobile = new ExtendJTextField();
			txtMobile.setName("txtMobile");
			txtNickName = new ExtendJTextField();
			txtNickName.setName("txtNickName");
			txtFullName = new ExtendJTextField();
			txtFullName.setName("txtLastName");
			txtBirthDay = new MyJDateChooser();
			txtBirthDay.setDate(null);
			txtHeight = new ExtendJTextField();
			txtHeight.setName("txtHeight");
			txtWeight = new ExtendJTextField();
			txtWeight.setName("txtWeight");
			txtCMT = new ExtendJTextField();
			txtCMT.setName("txtCMT");
			txtHobby = new ExtendJTextArea();
			txtHobby.setName("txtHobby");
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setViewportView(txtHobby);

			cboGender = new ExtendJComboBox();
			cboGender.setName("cboGender");
			cbMaritalStatus = new ExtendJComboBox();
			cbMaritalStatus.setName("cbMaritalStatus");
			
			dataGender = new ArrayList<Option>();
			dataGender.add(new Option());
			dataMaritalStatus = new ArrayList<Option>();
			dataMaritalStatus.add(new Option());
			try {
				dataGender.addAll(GenericOptionModelManager.getInstance().getOptionGroup("config", "LocaleService", "genders").getOptions());
				dataMaritalStatus.addAll(GenericOptionModelManager.getInstance().getOptionGroup("config", "LocaleService", "marital_status").getOptions());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			cboGender.setModel(new DefaultComboBoxModel(dataGender.toArray()));
			cbMaritalStatus.setModel(new DefaultComboBoxModel(dataMaritalStatus.toArray()));

			
			lbAvatar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (txtFullName.isEnabled())
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

			MyGroupLayout layout = new MyGroupLayout(this);
			layout.add(0, 0, lbLoginId);
			layout.add(0, 1, txtLoginId);

			layout.add(1, 0, lbPassword);
			layout.add(1, 1, txtPassword);

			layout.add(2, 0, lbRepeatPassword);
			layout.add(2, 1, txtRepatePassword);

			layout.add(3, 0, lbEmail);
			layout.add(3, 1, txtEmail);
			layout.add(3, 2, lbStartDate);
			layout.add(3, 3, dcStartDate);


			layout.add(4, 0, lbFullName);
			layout.add(4, 1, txtFullName);
			layout.add(4, 2, lbBirthDay);
			layout.add(4, 3, txtBirthDay);

			layout.add(5, 0, lbNickName);
			layout.add(5, 1, txtNickName);
			layout.add(5, 2, lbHeight);
			layout.add(5, 3, txtHeight);

			layout.add(6, 0, lbMobile);
			layout.add(6, 1, txtMobile);
			layout.add(6, 2, lbWeight);
			layout.add(6, 3, txtWeight);

			layout.add(7, 0, lbAddress);
			layout.add(7, 1, txtAddress);
			layout.add(7, 2, lbMaritalStatus);
			layout.add(7, 3, cbMaritalStatus);

			layout.add(8, 0, lbCMT);
			layout.add(8, 1, txtCMT);
			layout.add(8, 2, lbGender);
			layout.add(8, 3, cboGender);

			layout.add(9, 0, lbHobby);
			layout.add(9, 1, scrollPane);

			layout.addImage(panelAvatar);
			layout.updateGui();
		}

		public BasicInformation getBasicInformation() {
			basicInformation = new BasicInformation();
			basicInformation.setFirstName(txtNickName.getText());
			basicInformation.setLastName(txtFullName.getText());
			basicInformation.setGender(((Option) cboGender.getSelectedItem()).getCode());
			try {
				if(txtBirthDay.getDate() != null)
					basicInformation.setBirthday(df.format(txtBirthDay.getDate()));
				else 
					basicInformation.setBirthday("");
			} catch (Exception e) {
				basicInformation.setBirthday("");
			}
			ImageIcon image = (ImageIcon) lbAvatar.getIcon();
			if (image != null) {
				String urlImage = ImageTool.getInstance().encodeToString(image.getImage());
				basicInformation.setAvatar(urlImage);
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
			txtFullName.setText(basicInformation.getLastName());
			txtNickName.setText(basicInformation.getFirstName());
			try {
				if(basicInformation.getBirthday() != null && !basicInformation.getBirthday().equals(""))
					txtBirthDay.setDate(df.parse(basicInformation.getBirthday()));
				else txtBirthDay.setDate(null);
			} catch (Exception e) {
				txtBirthDay.setDate(null);
			}

			txtHeight.setText(String.valueOf(basicInformation.getHeight()));
			txtWeight.setText(String.valueOf(basicInformation.getWeight()));
			txtCMT.setText(basicInformation.getPersonalId());
			txtHobby.setText(basicInformation.getHobby());

			for (int i = 0; i < cboGender.getItemCount(); i++) {
				if (cboGender.getSelectedItem() instanceof Object && ((Option) cboGender.getItemAt(i)).getCode().equals(basicInformation.getGender())) {
					cboGender.setSelectedIndex(i);
					break;
				}
			}

			for (int i = 0; i < cbMaritalStatus.getItemCount(); i++) {
				if (cbMaritalStatus.getSelectedItem() instanceof Object && ((Option) cbMaritalStatus.getItemAt(i)).getCode().equals(basicInformation.getMaritalStatus())) {
					cbMaritalStatus.setSelectedIndex(i);
					break;
				}
			}
		}

		private void refeshData() {
			if (account == null) {
				txtLoginId.setText(new Date().getTime() + "");
				txtPassword.setText("0000");
				txtRepatePassword.setText("0000");
				txtEmail.setText(new Date().getTime() + "@hkt.com");
			} else {
				txtLoginId.setText(account.getLoginId());
				txtPassword.setText(account.getPassword());
				txtRepatePassword.setText(account.getPassword());
				txtEmail.setText(account.getEmail());

				try {
					String image = account.getProfiles().getBasic().get(BasicInformation.AVATAR).toString();
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
		}

		private void updateContact() {
			try {
				if (contactForm.getContactList() != null && contactForm.getContactList().size() > 0) {
					txtMobile.setText(StringUtil.joinStringArray(contactForm.getContactList().get(0).getMobile()));
					txtAddress.setText(contactForm.getContactList().get(0).getAddressNumber());
					if (!StringUtil.joinStringArray(contactForm.getContactList().get(0).getEmail()).isEmpty())
						txtEmail.setText(StringUtil.joinStringArray(contactForm.getContactList().get(0).getEmail()));
					else
						tabbedPane.setSelectedIndex(0);
				} else {
					txtMobile.setText("");
					txtAddress.setText("");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}

		public void cleanAccountBasic() {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String loginId = dateFormat.format(new Date());
			txtLoginId.setText(loginId);
			txtPassword.setText("0000");
			txtRepatePassword.setText("0000");
			txtEmail.setText(loginId + "@hkt.com");
			txtMobile.setText("");
			txtAddress.setText("");
		}
	}

	private boolean checkData() {
		if (txtLoginId.getText().trim().length() == 0) {
			lbMessage.setText("Nhập thông tin tài khoản !");
			lbMessage.setForeground(Color.red);
			lbLoginId.setForeground(Color.red);
			return false;
		} else {
			try {
				if (isNew == true) {
					Account account = AccountModelManager.getInstance().getAccountByLoginId(txtLoginId.getText());
					if (account != null) {
						txtLoginId.requestFocus();
						lbMessage.setText("Tài khoản đã tồn tại !");
						lbMessage.setForeground(Color.red);
						lbLoginId.setForeground(Color.red);
						return false;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			lbLoginId.setForeground(Color.black);
		}
		if (txtPassword.getPassword().length == 0) {
			txtPassword.requestFocus();
			lbMessage.setText("Nhập mật khẩu !");
			lbMessage.setForeground(Color.red);
			lbPassword.setForeground(Color.red);
			return false;
		} else {
			lbPassword.setForeground(Color.black);
		}
		if (txtRepatePassword.getPassword().length == 0) {
			txtRepatePassword.requestFocus();
			lbMessage.setText("Nhập lại mật khẩu !");
			lbMessage.setForeground(Color.red);
			lbRepeatPassword.setForeground(Color.red);
			return false;
		} else {
			lbRepeatPassword.setForeground(Color.black);
		}
		if (!txtRepatePassword.getText().equals(txtPassword.getText())) {
			txtRepatePassword.requestFocus();
			lbMessage.setText("Mật khẩu không giống nhau !");
			lbMessage.setForeground(Color.red);
			lbRepeatPassword.setForeground(Color.red);
			lbPassword.setForeground(Color.red);
			return false;
		} else {
			lbRepeatPassword.setForeground(Color.black);
			lbPassword.setForeground(Color.black);
		}
		
		BasicInformation basic = basicInformation.getBasicInformation();
		if (basic.getLastName().trim().length() == 0) {
			lbMessage.setText("Nhập tên nhân viên !");
			lbMessage.setForeground(Color.red);
			lbFullName.setForeground(Color.red);
			return false;
		} else {
			lbFullName.setForeground(Color.black);
		}
		if (basic.getHeight().trim().length() > 0) {
			try {
				Integer.parseInt(basic.getHeight());
				lbHeight.setForeground(Color.black);
			} catch (Exception e) {
				lbMessage.setText("Dữ liệu chiều cao không hợp lệ !");
				lbMessage.setForeground(Color.red);
				lbHeight.setForeground(Color.red);
				return false;
			}
		} else {
			lbHeight.setForeground(Color.black);
		}
		if (basic.getWeight().trim().length() > 0) {
			try {
				Integer.parseInt(basic.getWeight());
				lbWeight.setForeground(Color.black);
			} catch (Exception e) {
				lbMessage.setText("Dữ liệu cân nặng không hợp lệ !");
				lbMessage.setForeground(Color.red);
				lbWeight.setForeground(Color.red);
				return false;
			}
		} else {
			lbWeight.setForeground(Color.black);
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
			if(reset == true)
				((DialogMain) getRootPane().getParent()).dispose();
		}
	}

	@Override
	public void edit() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
			return;
		}
		reset = true;
		setEditData(true);
		((DialogMain) getRootPane().getParent()).showButton(true);

	}

	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {

			String str = "Xóa cá nhân ";
			PanelChoise pnPanel1 = new PanelChoise(str + txtFullName.getText() + "?");
			pnPanel1.setName("Xoa");
			DialogResto dialog11 = new DialogResto(pnPanel1, false, 0, 80);
			dialog11.setName("dlXoa");
			dialog11.setTitle("Xóa nhân sự");
			dialog11.setLocationRelativeTo(null);
			dialog11.setModal(true);
			dialog11.setVisible(true);

			if (pnPanel1.isDelete()) {
				Employee employee = HRModelManager.getInstance().getBydLoginId(account.getLoginId());
				Customer customer = CustomerModelManager.getInstance().getBydLoginId(account.getLoginId());
				Supplier supplier = SupplierModelManager.getInstance().getBydLoginId(account.getLoginId());
				if((employee!=null && !employee.isRecycleBin())
						|| (customer!=null && !customer.isRecycleBin())
						|| (supplier!=null&&!supplier.isRecycleBin())){
					 PanelChoise pnPanel = new PanelChoise("Xóa nhân viên, khách hàng, nhà cung cấp đi kèm?");
		        pnPanel.setName("Xoa");
		        DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
		        dialog1.setName("dlXoa");
		        dialog1.setTitle("Xóa cá nhân");
		        dialog1.setLocationRelativeTo(null);
		        dialog1.setModal(true);
		        dialog1.setVisible(true);
		        if (pnPanel.isDelete()) {
							if(employee!=null){
								HRModelManager.getInstance().deleteEmployee(employee.getId());
							}
							if(customer!=null){
								CustomerModelManager.getInstance().deleteCustomer(customer);
							}
							if(supplier!=null){
								SupplierModelManager.getInstance().deleteSupplier(supplier);
							}
							AccountModelManager.getInstance().deleteAccountByLoginId(account.getLoginId());
							
		        }
				}
				else
				{
					AccountModelManager.getInstance().deleteAccountByLoginId(account.getLoginId());
				}
				((JDialog)getRootPane().getParent()).dispose();
				
			} else if (pnPanel1.isDelete() == false) {
				reset();
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
		basicInformation.refeshData();
		setEditData(false);
	}

	public void setEditData(boolean flag) {
		txtLoginId.setEnabled(false);
		txtEmail.setEnabled(flag);
		txtPassword.setEnabled(flag);
		txtRepatePassword.setEnabled(flag);
		txtFullName.setEnabled(flag);
		txtHeight.setEnabled(flag);
		txtWeight.setEnabled(flag);
		txtCMT.setEnabled(flag);
		txtMobile.setEnabled(flag);
		txtAddress.setEnabled(flag);
		txtBirthDay.setEnabled(flag);
		 
		cbMaritalStatus.setEnabled(flag);
		 
		cboGender.setEnabled(flag);
		txtHobby.setEnabled(flag);
		dcStartDate.setEnabled(flag);
		userEducationForm.setEdit(flag);
		userWorkForm.setEdit(flag);
		userRelationshipForm.setEdit(flag);
		contactForm.setEdit(flag);
	}

	@Override
	public void createBeginTest() {
		if (MyPanel.isTest) {
			// Tạo phòng ban bộ phận
			try {
				AccountGroup root = AccountModelManager.getInstance().getRootGroupDetail().getChildren().get(0);
				AccountGroup parent = new AccountGroup(root, AccountModelManager.Department);
				parent.setLabel(AccountModelManager.Department);
				if (AccountModelManager.getInstance().getGroupByPath(root.getPath() + "/" + AccountModelManager.Department) == null) {
					parent = AccountModelManager.getInstance().saveGroup(parent);
				} else {
					parent = AccountModelManager.getInstance().getGroupByPath(parent.getPath());
				}
				AccountGroup accountGroup = new AccountGroup();
				accountGroup.setName("Mã phòng ban HktTest1");
				accountGroup.setLabel("Tên phòng ban HktTest1");
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
		// Tạo 50 nhân viên test chuyển trang
		for (int i = 1; i <= 50; i++) {
			Account account = new Account();
			account.setLoginId("HktTest" + i);
			account.setPassword("HktTest" + i);
			account.setEmail("HktTest" + i + "@gmail.com");
			account.setType(Type.USER);

			Profile profile = new Profile();
			profile.put(BasicInformation.LAST_NAME, "Họ đệm NV HktTest" + i + 10);
			profile.put(BasicInformation.BIRTHDAY, "01/01/2014");
			Profiles profiles = new Profiles();
			profiles.setBasic(profile);

			try {
				account.setProfiles(profiles);
			} catch (IOException e) {
				e.printStackTrace();
			}

			Employee employee = new Employee();
			employee.setLoginId(account.getLoginId());
			employee.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
			employee.setCode("Mã nhân viên HktTest" + i);
			employee.setName("Tên nhân viên HktTest" + i);
			employee.setMobile("01234678" + i);
			try {
				account = AccountModelManager.getInstance().saveAccount(account);
				employee = HRModelManager.getInstance().saveEmployee(employee);
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
					HRModelManager.getInstance().deleteTest("HktTest");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
