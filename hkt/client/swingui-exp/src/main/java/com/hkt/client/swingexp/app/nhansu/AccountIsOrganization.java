package com.hkt.client.swingexp.app.nhansu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.hkt.client.swing.widget.GridBagLayoutPanel;
import com.hkt.client.swingexp.app.component.ButtonExit;
import com.hkt.client.swingexp.app.component.ButtonRefresh;
import com.hkt.client.swingexp.app.component.ButtonSave;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextArea;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.khachhang.BusinessRegistration;
import com.hkt.client.swingexp.app.khachhang.BusinessRegistrationForm;
import com.hkt.client.swingexp.app.khachhang.ContactForm;
import com.hkt.client.swingexp.app.khachhang.OrganizationBasic;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.account.entity.Profiles;

public class AccountIsOrganization extends JPanel {
  
  /** Account */
  private JLabel lbLoginId, lbPassword, lbRepeatPassword, lbEmail;
  private JTextField txtLoginId, txtPassword, txtRepatePassword, txtEmail;
  
  private JLabel lbAvatar;
  private Account account;
  private JButton btSave, btClear, btCancel, btSaveAndQuite;
  private JDialog dialog;
  
  private JPanel panelAvatar, panelBasicInfor, panelMain;
  
  private BusinessRegistrationForm businessRegistrationForm;
  private ContactForm contactForm;
  private AccountBasic accountBasic;
  
  private OrganizationBasicPanel organizationBasicPanel;
  
  public AccountIsOrganization(JDialog dialog) throws Exception {
    setLayout(new BorderLayout());
    this.dialog = dialog;
    organizationBasicPanel = new OrganizationBasicPanel();
    
    panelAvatar = new JPanel();
    panelAvatar.setLayout(new GridLayout(1, 2));
    
    lbAvatar = new JLabel("Avatar Imgae", SwingConstants.CENTER);
    lbAvatar.setBorder(BorderFactory.createEtchedBorder());
    
    accountBasic = new AccountBasic();
    panelAvatar.add(accountBasic);
    panelAvatar.add(lbAvatar);
    
    panelBasicInfor = new JPanel();
    panelBasicInfor.setLayout(new BorderLayout());
    panelBasicInfor.add(organizationBasicPanel, BorderLayout.PAGE_START);
    panelBasicInfor.add(new JPanel(), BorderLayout.CENTER);
    panelBasicInfor.setBackground(Color.WHITE);
    
    panelMain = new JPanel();
    panelMain.setLayout(new BorderLayout());
    
    panelMain.add(panelAvatar, BorderLayout.PAGE_START);
    panelMain.add(panelBasicInfor, BorderLayout.CENTER);
    
    businessRegistrationForm = new BusinessRegistrationForm(account);
    contactForm = new ContactForm(account);
    
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.add(panelMain, "Thông tin cơ bản");
    tabbedPane.add(businessRegistrationForm, "Đăng kí kinh doanh");
    tabbedPane.add(contactForm, "Thông tin liên lạc");
    
    add(tabbedPane, BorderLayout.CENTER);
    
    btSave = new ButtonSave();
    btSave.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          btSaveActionPerformed(e);
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    });
    
    btClear = new ButtonRefresh();
    btClear.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btClearActionPerformed(e);
      }
    });
    
    btCancel = new ButtonExit();
    btCancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btCancelActionPerformed(e);
      }
    });
    
    btSaveAndQuite = new ButtonSave();
    btSaveAndQuite.setText("Save&Quite");
    btSaveAndQuite.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btSaveAndQuitelActionPerformed(e);
      }
    });
    
    JPanel panelControl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    panelControl.add(btSave);
    panelControl.add(btClear);
    panelControl.add(btCancel);
    panelControl.add(btSaveAndQuite);
    add(panelControl, BorderLayout.PAGE_END);
  }
  
  protected void btSaveActionPerformed(ActionEvent e) throws Exception {
    account = new Account();
    account.setLoginId(txtLoginId.getText());
    account.setPassword(txtPassword.getText());
    account.setType(Type.ORGANIZATION);
    account.setEmail(txtEmail.getText());
    account.setLastLoginTime(new Date());
    account.setContacts(contactForm.getContactList());
    
    Profile profileOrgBasic = new Profile();
    OrganizationBasic orgBasic = organizationBasicPanel.getOrganizationBasic();
    profileOrgBasic.put(OrganizationBasic.NAME, orgBasic.getName());
    profileOrgBasic.put(OrganizationBasic.FULLNAME, orgBasic.getFullName());
    profileOrgBasic.put(OrganizationBasic.ORGANIZATION_TYPE, orgBasic.getOrganizationType());
    profileOrgBasic.put(OrganizationBasic.SLOGAN, orgBasic.getSlogan());
    profileOrgBasic.put(OrganizationBasic.LOGOURL, orgBasic.getLogoURL());
    profileOrgBasic.put(OrganizationBasic.FOUNDED_DATE, orgBasic.getFoundedDate());
    profileOrgBasic.put(OrganizationBasic.TERMINATED_DATE, orgBasic.getTerminatedDate());
    profileOrgBasic.put(OrganizationBasic.REGISTRATION_CODE, orgBasic.getRegistrationCode());
    profileOrgBasic.put(OrganizationBasic.REPRESENTATIVE, orgBasic.getRepresentative());
    profileOrgBasic.put(OrganizationBasic.DESCRIPTION, orgBasic.getDescription());
    
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
    AccountModelManager.getInstance().saveAccount(account);
    btClearActionPerformed(e);
  }
  
  protected void btClearActionPerformed(ActionEvent e) {
    account = new Account();
    
    accountBasic.cleanAccountBasic();
    organizationBasicPanel.setOrganizationBasic(new OrganizationBasic());
    businessRegistrationForm.cleanBusinessRegistration();
    contactForm.cleanContact();
    
  }
  
  protected void btCancelActionPerformed(ActionEvent e) {
    dialog.dispose();
  }
  
  protected void btSaveAndQuitelActionPerformed(ActionEvent e) {
    
  }
  
  public class AccountBasic extends GridBagLayoutPanel {
    
    public AccountBasic() {
      setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder("Thông tin tài khoản")));
      lbLoginId = new ExtendJLabel("Tên đăng nhập: ");
      lbPassword = new ExtendJLabel("Mật khẩu: ");
      lbRepeatPassword = new ExtendJLabel("Nhập lại mật khẩu: ");
      lbEmail = new ExtendJLabel("Email: ");
      
      txtLoginId = new ExtendJTextField(27);
      txtPassword = new ExtendJTextField(27);
      txtRepatePassword = new ExtendJTextField(27);
      txtEmail = new ExtendJTextField(27);
      
      add(0, 0, lbLoginId);
      add(0, 1, txtLoginId);
      add(1, 0, lbPassword);
      add(1, 1, txtPassword);
      add(2, 0, lbRepeatPassword);
      add(2, 1, txtRepatePassword);
      add(3, 0, lbEmail);
      add(3, 1, txtEmail);
    }
    
    public void cleanAccountBasic() {
      txtLoginId.setText("");
      txtPassword.setText("");
      txtRepatePassword.setText("");
      txtEmail.setText("");
    }
  }
  
  public class OrganizationBasicPanel extends GridBagLayoutPanel {
    
    /** OrganizationBasic */
    private JLabel lbName, lbFullName, lbOrganizationType, lbManager, lbSologan, lbLogoURL, lbFoundedDate,
        lbTerminatedDate, lbRegistrationCode, lbRepresentative, lbDescription;
    private JTextField txtName, txtFullName, txtSlogan, txtLogoURL, txtRegistrationCode, txtRepresentative;
    private JComboBox cboOrganizationType, cboManager;
    private JTextArea txtDescription;
    private MyJDateChooser txtFoundedDate, txtTerminatedDate;
    
    Object[] dataOrganizationType = { "Cty", "Cafe", "Khác" };
    Object[] dataManager = { "Có", "Không" };
    
    private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    
    private OrganizationBasic organizationBasic;
    
    public OrganizationBasicPanel() {
      setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder("Thông tin cơ bản doanh nghiệp")));
      
      lbName = new ExtendJLabel("Tên doanh nghiệp:");
      lbFullName = new ExtendJLabel("Tên đây đủ:");
      lbOrganizationType = new ExtendJLabel("Mô hình:");
      lbManager = new ExtendJLabel("Quản lý:");
      lbSologan = new ExtendJLabel("Sologan:");
      lbLogoURL = new ExtendJLabel("Logo URL:");
      lbFoundedDate = new ExtendJLabel("Ngày thành lập:");
      lbTerminatedDate = new ExtendJLabel("Ngày kết thúc:");
      lbRegistrationCode = new ExtendJLabel("Mã số thuế:");
      lbRepresentative = new ExtendJLabel("Người đại diện");
      lbDescription = new ExtendJLabel("Miêu tả");
      
      txtName = new ExtendJTextField(27);
      txtFullName = new ExtendJTextField(27);
      
      cboOrganizationType = new ExtendJComboBox();
      cboOrganizationType.setModel(new DefaultComboBoxModel(dataOrganizationType));
      
      cboManager = new ExtendJComboBox();
      cboManager.setModel(new DefaultComboBoxModel(dataManager));
      
      txtSlogan = new ExtendJTextField(27);
      txtLogoURL = new ExtendJTextField(27);
      
      txtFoundedDate = new MyJDateChooser();
      txtTerminatedDate = new MyJDateChooser();
      
      txtRegistrationCode = new ExtendJTextField(27);
      txtRepresentative = new ExtendJTextField(27);
      
      txtDescription = new ExtendJTextArea(5, 30);
      txtDescription.setBorder(javax.swing.BorderFactory.createEtchedBorder());
      
      add(0, 0, lbName);
      add(0, 1, txtName);
      add(0, 2, lbFullName);
      add(0, 3, txtFullName);
      
      add(1, 0, lbOrganizationType);
      add(1, 1, cboOrganizationType);
      add(1, 2, lbManager);
      add(1, 3, cboManager);
      
      add(2, 0, lbSologan);
      add(2, 1, txtSlogan);
      add(2, 2, lbLogoURL);
      add(2, 3, txtLogoURL);
      
      add(3, 0, lbFoundedDate);
      add(3, 1, txtFoundedDate);
      add(3, 2, lbRegistrationCode);
      add(3, 3, txtRegistrationCode);
      
      add(4, 0, lbTerminatedDate);
      add(4, 1, txtTerminatedDate);
      add(4, 2, lbRepresentative);
      add(4, 3, txtRepresentative);
      
      add(5, 0, lbDescription);
      add(5, 1, txtDescription, 3);
    }
    
    public OrganizationBasic getOrganizationBasic() {
      organizationBasic = new OrganizationBasic();
      organizationBasic.setName(txtName.getText());
      organizationBasic.setFullName(txtFullName.getText());
      organizationBasic.setOrganizationType((String) cboOrganizationType.getSelectedItem());
      organizationBasic.setManager((String) cboManager.getSelectedItem());
      organizationBasic.setSlogan(txtSlogan.getText());
      organizationBasic.setLogoURL(txtLogoURL.getText());
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
      
      for (int i = 0; i < cboOrganizationType.getItemCount(); i++) {
        if (cboOrganizationType.getSelectedItem() instanceof Object
            && ((String) cboOrganizationType.getItemAt(i)).equals(organizationBasic.getOrganizationType())) {
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
      txtLogoURL.setText(organizationBasic.getLogoURL());
      
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
    }
    
  }
  
}
