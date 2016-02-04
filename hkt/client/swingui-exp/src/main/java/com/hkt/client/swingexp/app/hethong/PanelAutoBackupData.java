package com.hkt.client.swingexp.app.hethong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialogPlus;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.promotion.AutoSave;
import com.hkt.module.promotion.HKTFile;
import com.hkt.module.promotion.entity.TimeOption;

@SuppressWarnings("serial")
public class PanelAutoBackupData extends JPanel implements IDialogMain {
  private MyJDateChooser      startDate, endDate;
  private JButton             btnOption, btnChoose;
  private JSpinner            spinner, spinnerQuantityFile, spinnerHourStart, spinnerMinute;
  private static final String LOCATION_URL = HKTFile.getDirectory("AutoSave");
  private JLabel              lbStart, lbEnd, lbHourStart, lbMinute, lbFile, lbLocation, lbOption, lbDistance,
      lbMessenger;
  private JTextField          txtLocation;
  private DateFormat          dateFormat   = new SimpleDateFormat("dd/MM/yyyy");
  private String              location     = "";
  private Date                dateStart;
  private Date                dateEnd;
  private int                 stepHour     = 0;
  private int                 stepSave     = 0;
  private int                 hourStart    = 1;
  private int                 minuteStart  = 1;
  private IDialogMain         dialogMain   = this;
  private long                idTimeOption = 0;
  private AutoSave            autoSave;
  public static String permission;

  public PanelAutoBackupData() throws Exception {
    setOpaque(false);

    lbStart = new JLabel("Bắt đầu");
    lbEnd = new JLabel("Kết thúc");
    lbHourStart = new JLabel("Giờ bắt đầu");
    lbMinute = new JLabel("Phút");
    lbFile = new JLabel("Số file lưu");
    lbLocation = new JLabel("Vị trí lưu");
    lbOption = new JLabel("Mở rộng");
    lbDistance = new JLabel("Cách (giờ)");

    startDate = new MyJDateChooser();
    endDate = new MyJDateChooser();
    btnOption = new JButton("Tùy chọn");
    btnOption.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
    btnOption.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          TimeOptions options = new TimeOptions(idTimeOption);
          options.setParent(dialogMain);
          DialogMain dialogMain = new DialogMain(options, 220, 650);
          dialogMain.setTitle("Tùy chọn");
          dialogMain.setIcon("saoluu1.png");
          dialogMain.setModal(true);
          dialogMain.setLocationRelativeTo(null);
          dialogMain.setVisible(true);
        } catch (Exception e2) {
          e2.printStackTrace();
        }

      }
    });

    txtLocation = new JTextField();
    btnChoose = new JButton("Chọn");
    btnChoose.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
    btnChoose.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setPreferredSize(new Dimension(690, 310));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int choose = fileChooser.showOpenDialog(null);
        if (choose == JFileChooser.APPROVE_OPTION) {
          try {
            txtLocation.setText(fileChooser.getSelectedFile().getAbsolutePath());
          } catch (Exception ex) {
            txtLocation.setText("");
          }
        }
      }
    });

    spinner = new JSpinner();
    spinnerHourStart = new JSpinner();
    spinnerMinute = new JSpinner();
    spinnerQuantityFile = new JSpinner();
    lbMessenger = new JLabel();
    lbMessenger.setText("");

    checkHomeLocation();
    checkFileLocationInfo();

    MyGroupLayout layout = new MyGroupLayout(this);
    layout.add(0, 0, lbStart);
    layout.add(0, 1, startDate);
    layout.add(0, 2, lbEnd);
    layout.add(0, 3, endDate);

    layout.add(1, 0, lbHourStart);
    layout.add(1, 1, spinnerHourStart);
    layout.add(1, 2, lbMinute);
    layout.add(1, 3, spinnerMinute);

    layout.add(2, 0, lbFile);
    layout.add(2, 1, spinnerQuantityFile);
    layout.add(2, 2, lbDistance);
    layout.add(2, 3, spinner);

    layout.add(3, 0, lbLocation);
    layout.add(3, 1, txtLocation);
    layout.add(3, 2, btnChoose);

    layout.add(4, 0, lbOption);
    layout.add(4, 1, btnOption);
    layout.addMessenger(lbMessenger);

    layout.updateGui();
    getDataAutoSave();
    reset();
    
  }

  @Override
  public void save() throws Exception {
	    if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
	        JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
	        return;
	    }
	        	addData();
	        
  }

  @Override
  public void edit() throws Exception {
	    if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
	        JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
	        return;
	      } 
  }

  @Override
  public void delete() throws Exception {
	  if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {
    doActionDelete();
	    } else
	        JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
	      	return;

  }

  @Override
  public void reset() throws Exception {
    if (autoSave == null) {
      startDate.setDate(new Date());
      endDate.setDate(null);
      txtLocation.setText(HKTFile.getDirectory("Database"));
      spinner.setValue(2);
      spinnerHourStart.setValue(1);
      spinnerMinute.setValue(0);
      spinnerQuantityFile.setValue(1);
    } else {
      startDate.setDate(autoSave.getDateStart());
      endDate.setDate(autoSave.getDateEnd());
      txtLocation.setText(autoSave.getLocation());
      spinner.setValue(autoSave.getStepHour());
      spinnerHourStart.setValue(autoSave.getHourStart());
      spinnerMinute.setValue(autoSave.getMinuteStart());
      spinnerQuantityFile.setValue(autoSave.getQuantitySave());
      idTimeOption = autoSave.getIdTimeOption();
    }
  }

  @Override
  public void refresh() throws Exception {

  }

  private void addData() {
    if (!checkData()) {
      return;
    } else {
      getData();
      FileWriter fileWriter = null;
      try {

        File fileAutoSave = new File(LOCATION_URL + "/autoconf.txt");
        fileWriter = new FileWriter(fileAutoSave, false);
        BufferedWriter out = new BufferedWriter(fileWriter);
        String line = dateFormat.format(dateStart) + ";"
            + dateFormat.format((dateEnd == null ? new Date(Long.MAX_VALUE) : dateEnd)) + ";" + idTimeOption + ";"
            + stepHour + ";" + location + ";" + stepSave + ";" + hourStart + ";" + minuteStart;
        out.write(line);
        out.close();
        DialogNotice.getInstace().setVisible(true);
        getDataAutoSave();
      } catch (IOException ex) {
        ex.printStackTrace();
      } finally {
        try {
          reset();
          fileWriter.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }
  }

  private boolean checkData() {
    boolean check = true;
    if (startDate.getDate() == null) {
      check = false;
      lbStart.setForeground(Color.red);
    } else {
      lbStart.setForeground(new Color(51, 51, 51));
    }

    try {
      stepHour = Integer.parseInt(spinner.getValue().toString());
      hourStart = Integer.parseInt(spinnerHourStart.getValue().toString());
      minuteStart = Integer.parseInt(spinnerMinute.getValue().toString());
      stepSave = Integer.parseInt(spinnerQuantityFile.getValue().toString());

      if (stepHour >= 0 && stepHour <= 23) {
        lbDistance.setForeground(Color.black);
        lbMessenger.setText("");
      } else {
        check = false;
        lbDistance.setForeground(Color.red);
      }

      if (hourStart >= 0 && stepHour <= 23) {
        lbHourStart.setForeground(Color.black);
        lbMessenger.setText("");
      } else {
        check = false;
        lbHourStart.setForeground(Color.red);
      }

      if (minuteStart >= 0 && minuteStart <= 59) {
        lbMinute.setForeground(Color.black);
        lbMessenger.setText("");
      } else {
        check = false;
        lbMinute.setForeground(Color.red);
      }

      if (stepSave > 0) {
        lbFile.setForeground(Color.black);
        lbMessenger.setText("");
      } else {
        check = false;
        lbFile.setForeground(Color.red);
      }

    } catch (Exception e) {
      check = false;
    }

    if (txtLocation.getText().trim().length() == 0) {
      check = false;
      lbLocation.setForeground(Color.red);
    } else {
      lbLocation.setForeground(new Color(51, 51, 51));
    }

    if (!check) {
      lbMessenger.setText("Dữ liệu vùng đỏ bị lỗi!");
      lbMessenger.setForeground(Color.red);
      return false;
    } else {
      lbMessenger.setText("");
      return true;
    }
  }

  private void getData() {
    try {
      if (startDate.getDate() != null) {
        dateStart = startDate.getDate();
      }
      if (endDate.getDate() != null) {
        dateEnd = endDate.getDate();
      }
      location = txtLocation.getText();
      stepHour = Integer.parseInt(spinner.getValue().toString());
      stepSave = Integer.parseInt(spinnerQuantityFile.getValue().toString());
      hourStart = Integer.parseInt(spinnerHourStart.getValue().toString());
      minuteStart = Integer.parseInt(spinnerMinute.getValue().toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setTimeOption(TimeOption timeOption) {
    if (timeOption != null) {
      this.idTimeOption = timeOption.getId();
    }
  }

  @SuppressWarnings("resource")
private void checkFileLocationInfo() {
    File fileAutoSave = new File(LOCATION_URL + "/autoconf.txt");
    if (fileAutoSave.exists()) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(fileAutoSave));
        String line = "";
        while ((line = br.readLine()) != null) {
          String[] info = line.split(";");
          dateStart = dateFormat.parse(info[0]);
          dateEnd = dateFormat.parse(info[1]);
          idTimeOption = Integer.parseInt(info[2]);
          stepHour = Integer.parseInt(info[3]);
          location = info[4];
          stepSave = Integer.parseInt(info[5]);
          hourStart = Integer.parseInt(info[6]);
          minuteStart = Integer.parseInt(info[7]);
        }
        startDate.setDate(dateStart);
        endDate.setDate(dateEnd);
        spinner.setValue(stepHour);
        txtLocation.setText(location);
        spinnerQuantityFile.setValue(stepSave);
        spinnerHourStart.setValue(hourStart);
        spinnerMinute.setValue(minuteStart);
      } catch (ParseException ex) {
        ex.printStackTrace();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  private void checkHomeLocation() {
    File file = new File(LOCATION_URL);
    if (!file.exists()) {
      file.mkdirs();
    }
  }

  private void doActionDelete() {
    int choise = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn hủy tùy chọn?", "Hủy AutoSave",
        JOptionPane.YES_NO_OPTION);
    if (choise == JOptionPane.YES_OPTION) {
      String path = LOCATION_URL;
      File file = new File(path);
      if (file.exists()) {
        try {
          delete(file);
          JOptionPane.showMessageDialog(null, "Hủy thành công!");
          ((JDialog) getRootPane().getParent()).setVisible(false);
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        JOptionPane.showMessageDialog(null, "Không có tùy chọn nào!");
      }
    }
  }

  public void delete(File file) {
    try {
      if (file.isDirectory()) {
        // directory is empty, then delete it
        if (file.list().length == 0) {
          file.delete();
        } else {
          // list all the directory contents
          String files[] = file.list();
          for (String temp : files) {
            // construct the file structure
            File fileDelete = new File(file, temp);
            // recursive delete
            delete(fileDelete);
          }
          // check the directory again, if empty then delete it
          if (file.list().length == 0) {
            file.delete();
          }
        }
      } else {
        // if file, then delete it
        file.delete();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private AutoSave getDataAutoSave() {
    String url = LOCATION_URL + "/autoconf.txt";
    File file = new File(url);
    if (file.exists()) {
      BufferedReader br = null;
      try {
        autoSave = new AutoSave();
        br = new BufferedReader(new FileReader(file));
        String line = "";
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        while ((line = br.readLine()) != null) {
          String[] info = line.split(";");
          autoSave.setDateStart(dateFormat.parse(info[0]));
          autoSave.setDateEnd(dateFormat.parse(info[1]));
          autoSave.setIdTimeOption(Integer.parseInt(info[2]));
          autoSave.setStepHour(Integer.parseInt(info[3]));
          autoSave.setLocation(info[4]);
          autoSave.setQuantitySave(Integer.parseInt(info[5]));
          autoSave.setHourStart(Integer.parseInt(info[6]));
          autoSave.setMinuteStart(Integer.parseInt(info[7]));
        }
        return autoSave;
      } catch (ParseException ex) {
        ex.printStackTrace();
      } catch (IOException ex) {
        ex.printStackTrace();
      } finally {
        try {
          br.close();
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    }
    return null;
  }

}
