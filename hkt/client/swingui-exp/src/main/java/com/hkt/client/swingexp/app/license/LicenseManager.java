package com.hkt.client.swingexp.app.license;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.HKTFile;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.account.entity.Profile;

public class LicenseManager {
  private String diskDriveIp;
  private HashMap<String, KeyActivateLicense> keys = new HashMap<String, KeyActivateLicense>();
  private String url;
  private String data = "Data";
  private static LicenseManager instance;
  private Profile p;
  private int time;
  private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
  private String keyMachine ;

  public int getTime(){
  	keyMachine = ProductId.getInstance().getSerialWindow();
    p = AccountModelManager.getInstance().getProfileConfigAdmin();
    if (p.get(keyMachine) == null) {
      Calendar c = Calendar.getInstance();
      c.add(Calendar.DAY_OF_MONTH, 30);
      p.put(keyMachine, df.format(c.getTime()));
      AccountModelManager.getInstance()
          .saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(), p);
    }else {
      Calendar c = Calendar.getInstance();
      try {
        Date d = df.parse(p.get(keyMachine).toString());
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d);
        time = c.get(Calendar.DAY_OF_YEAR)-c1.get(Calendar.DAY_OF_YEAR);
      } catch (Exception e) {
      }
      
    }
    System.out.println(time);
    return time;
  }
  static public enum module {
    Warehouse, Restaurant, Promotion, MenuVoucher, Report, Point
  };

  public static LicenseManager getInstance() {
    if (instance == null) {
      instance = new LicenseManager();
    }
    return instance;
  }

  private LicenseManager() {
  	keyMachine = ProductId.getInstance().getSerialWindow();
    p = AccountModelManager.getInstance().getProfileConfigAdmin();
    if (p.get(keyMachine) == null) {
      Calendar c = Calendar.getInstance();
      c.add(Calendar.DAY_OF_MONTH, 30);
      p.put(keyMachine, df.format(c.getTime()));
      AccountModelManager.getInstance()
          .saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(), p);
    }
      else {
      try {
        if(p.get(keyMachine).toString().endsWith("/2017")){
          Calendar c = Calendar.getInstance();
          c.add(Calendar.DAY_OF_MONTH, 30);
          p.put(keyMachine, df.format(c.getTime()));
          AccountModelManager.getInstance()
              .saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(), p);
        }
      } catch (Exception e) {
      }
      
    }
    try {
      File file = HKTFile.getFile("DataBase", "");
      url = file.getAbsolutePath();
      keys = getKeyActivateLicenses();
      diskDriveIp =  ProductId.getInstance().getserialNumber();
    } catch (Exception e) {
      diskDriveIp =  ProductId.getInstance().getSerialWindow();
    }
  }

  public boolean isAdmin() {
    try {
      String diskDriveIp1;
      try {
        diskDriveIp1 = new Base32().byteArray_2_String32(ProductId.getInstance().getserialNumber().getBytes());
      } catch (Exception e) {
        diskDriveIp1 = new Base32().byteArray_2_String32(ProductId.getInstance().getSerialWindow().getBytes());
      }

      KeyActivateLicense keyActivateLicense = keys.get(diskDriveIp1);
      return keyActivateLicense.isAdmin();
    } catch (Exception e) {
     return false;
    }
    
  }
  
  public String getLience(){
	  try {
		  KeyActivateLicense keyActivateLicense = keys.get(diskDriveIp);
		  return keyActivateLicense.getLicense();
	} catch (Exception e) {
		return "";
	}
	 
  }

  /**
   * Kiểm trả license của module
   * 
   * @param moduleCode
   * @return
   */
  public boolean getLicense(String moduleCode) {
//     return true;
  //  System.out.println("long "+p.get(keyMachine));
    boolean a = false;
    try {
     // System.out.println(df.parse(p.get(keyMachine).toString()).after(new Date()));
      if (df.parse(p.get(keyMachine).toString()).after(new Date())) {
        a = true;
      }
    } catch (Exception e) {
     
    }
    System.out.println(a+"   "+p.get(keyMachine).toString());
//    return true;
    if (!a) {
     // System.out.println(diskDriveIp+"  hien ");
      KeyActivateLicense keyActivateLicense = keys.get(diskDriveIp);
      
      if (keyActivateLicense != null
          && keyActivateLicense.getModuleCodes().toString().indexOf(moduleCode) >= 0
          && (keyActivateLicense.getEndDate() == null || keyActivateLicense.getEndDate().getTime() >= new Date()
              .getTime())) {
      	System.out.println(keyActivateLicense.getEndDate().getTime());
        return true;
      } else {
        return false;
      }
    } else { 
      return true;
    }

  }

  public void registryLicense() {
    DialogLicense license = new DialogLicense();
	DialogResto dialog = new DialogResto(license, false, 0, 130);
	dialog.setName("dlInfo");
	dialog.setIcon("key1.png");
	dialog.setLocationRelativeTo(null);
	dialog.setTitle("Bản quyền");
	dialog.setVisible(true);
  }

  @SuppressWarnings("unchecked")
public HashMap<String, KeyActivateLicense> getKeyActivateLicenses() throws Exception {
    HashMap<String, KeyActivateLicense> keys = new HashMap<String, KeyActivateLicense>();
    FileInputStream fStream = new FileInputStream(url + "/" + data);
    ObjectInputStream oStream = new ObjectInputStream(fStream);
    try {
      while (oStream.available() != -1) {
        keys = (HashMap<String, KeyActivateLicense>) oStream.readObject();
      }
    } catch (Exception e) {
    }
    fStream.close();
    return keys;
  }

  public void saveKeyActivate(HashMap<String, KeyActivateLicense> keyActivateLicenses){
    try {
      FileOutputStream fStream = new FileOutputStream(url + "/" + data);
      ObjectOutputStream oStream = new ObjectOutputStream(fStream);
      oStream.writeObject(keyActivateLicenses);
      oStream.reset();

      fStream.close();
      keys = getKeyActivateLicenses();
    } catch (Exception e) {
      e.printStackTrace();
    }
   
  }
}
