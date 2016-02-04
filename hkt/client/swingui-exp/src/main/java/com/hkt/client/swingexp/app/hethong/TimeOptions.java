package com.hkt.client.swingexp.app.hethong;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;

import org.hsqldb.lib.tar.RB;
import org.jfree.ui.Spinner;

import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJRadioButton;
import com.hkt.client.swingexp.app.component.GridLabelLayoutPabel;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.module.promotion.HKTFile;
import com.hkt.module.promotion.entity.Daily;
import com.hkt.module.promotion.entity.Monthly;
import com.hkt.module.promotion.entity.TimeOption;
import com.hkt.module.promotion.entity.TimeOptionDetail;
import com.hkt.module.promotion.entity.Weekly;
import com.hkt.module.promotion.entity.Yearly;

public class TimeOptions extends JPanel implements IDialogMain {
  private static final String LOCATION_URL = HKTFile.getDirectory("AutoSave");
  private ExtendJRadioButton  rdoDay, rdoWeek, rdoMonth, rdoYear, rdoADay, rdoEveDay, rdoDayInMonth, rdoDayWeekMonth,
      rdoAlways, rdoRepeat;
  private JSpinner            spinnerNgay, spinnerWeek, spinnerDayInMonth, spinnerMonth, spinnerDayYear;
  private JScrollPane         scrollPane;
  private JCheckBox           checkt2, checkt3, checkt4, checkt5, checkt6, checkt7, checktCN;
  private JComboBox           comboBoxDayOfWeek, comboBoxWeekOfMonth, cbbRepeatDay, cbbRepeatWeek;
  private List<JCheckBox>     checkBoxs;
  private List<Integer>       integers     = new ArrayList<Integer>();
  private IDialogMain         dialogMain;
  private long                timeOptionId = 0;

  public TimeOptions(long timeOptionId) {
    this.timeOptionId = timeOptionId;
    rdoDay = new ExtendJRadioButton("Hàng ngày");
    rdoWeek = new ExtendJRadioButton("Hàng tuần");
    rdoMonth = new ExtendJRadioButton("Hàng tháng");
    rdoYear = new ExtendJRadioButton("Hàng năm");
    ButtonGroup buttonGroup = new ButtonGroup();
    buttonGroup.add(rdoDay);
    buttonGroup.add(rdoWeek);
    buttonGroup.add(rdoMonth);
    buttonGroup.add(rdoYear);

    rdoDay.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        scrollPane.setViewportView(new DayPanel());

      }
    });
    rdoWeek.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        scrollPane.setViewportView(new WeekPanel());
      }
    });
    rdoMonth.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        scrollPane.setViewportView(new MonthPanel());
      }
    });
    rdoYear.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        scrollPane.setViewportView(new YearPanel());
      }
    });

    setOpaque(false);
    setLayout(new BorderLayout());
    add(new leftPanel(), BorderLayout.WEST);
    scrollPane = new JScrollPane();
    scrollPane.getViewport().setOpaque(false);
    scrollPane.setOpaque(false);
    scrollPane.setViewportView(new rightPanel(null));
    add(scrollPane, BorderLayout.CENTER);
    try {
      refresh();
    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }

  private class leftPanel extends GridLabelLayoutPabel {

    public leftPanel() {
      setOpaque(false);
      setBorder(BorderFactory.createTitledBorder("Tùy chọn thời gian"));
      add(0, 0, rdoDay);
      add(1, 0, rdoWeek);
      add(2, 0, rdoMonth);
      add(3, 0, rdoYear);
    }
  }

  private class rightPanel extends JPanel {
    public rightPanel(JPanel panel) {
      if (panel != null) {
        panel.setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Chi tiết"));
        setOpaque(false);
        add(panel, BorderLayout.CENTER);
      }
    }
  }

  private class DayPanel extends JPanel {
    public DayPanel() {
      setOpaque(false);
      setLayout(new BorderLayout());
      JPanel panel = new JPanel();
      panel.setOpaque(false);
      panel.setLayout(new FlowLayout(FlowLayout.LEFT));
      spinnerNgay = new JSpinner();
      spinnerNgay.setValue(1);
      spinnerNgay.setPreferredSize(new Dimension(60, 23));
      rdoADay = new ExtendJRadioButton("Cách");
      rdoEveDay = new ExtendJRadioButton("Mỗi ngày");
      panel.add(rdoADay);
      panel.add(spinnerNgay);
      panel.add(new ExtendJLabel("Ngày"));
      panel.add(rdoEveDay);
      add(panel, BorderLayout.WEST);
      ButtonGroup buttonGroupDay = new ButtonGroup();
      buttonGroupDay.add(rdoADay);
      buttonGroupDay.add(rdoEveDay);
    }
  }

  private class WeekPanel extends JPanel {
    public WeekPanel() {
      setOpaque(false);
      setLayout(new GridLayout(5, 1));
      spinnerWeek = new JSpinner();
      spinnerWeek.setValue(1);
      spinnerWeek.setPreferredSize(new Dimension(60, 23));
      checkt2 = new JCheckBox("Thứ 2");
      checkt2.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          if (checkt2.isSelected() == true) {
            if (!integers.contains(2))
              integers.add(2);
          } else {
            for (int i = 0; i < integers.size(); i++) {
              if (integers.get(i) == 2) {
                integers.remove(i);
                i--;
              }
            }
          }

        }
      });

      checkt3 = new JCheckBox("Thứ 3");
      checkt3.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          if (checkt3.isSelected() == true) {

            if (!integers.contains(3))
              integers.add(3);
          } else {
            for (int i = 0; i < integers.size(); i++) {
              if (integers.get(i) == 3) {
                integers.remove(i);
                i--;
              }
            }
          }
        }
      });

      checkt4 = new JCheckBox("Thứ 4");
      checkt4.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          if (checkt4.isSelected() == true) {

            if (!integers.contains(4))
              integers.add(4);
          } else {
            for (int i = 0; i < integers.size(); i++) {
              if (integers.get(i) == 4) {
                integers.remove(i);
                i--;
              }
            }
          }
        }
      });
      checkt5 = new JCheckBox("Thứ 5");
      checkt5.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          if (checkt5.isSelected() == true) {

            if (!integers.contains(5))
              integers.add(5);
          } else {
            for (int i = 0; i < integers.size(); i++) {
              if (integers.get(i) == 5) {
                integers.remove(i);
                i--;
              }
            }
          }
        }
      });
      checkt6 = new JCheckBox("Thứ 6");
      checkt6.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          if (checkt6.isSelected() == true) {

            if (!integers.contains(6))
              integers.add(6);
          } else {
            for (int i = 0; i < integers.size(); i++) {
              if (integers.get(i) == 6) {
                integers.remove(i);
                i--;
              }
            }
          }
        }
      });
      checkt7 = new JCheckBox("Thứ 7");
      checkt7.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          if (checkt7.isSelected() == true) {

            if (!integers.contains(7))
              integers.add(7);
          } else {
            for (int i = 0; i < integers.size(); i++) {
              if (integers.get(i) == 7) {
                integers.remove(i);
                i--;
              }
            }
          }
        }
      });
      checktCN = new JCheckBox("Chủ nhật");
      checktCN.addItemListener(new ItemListener() {

        @Override
        public void itemStateChanged(ItemEvent e) {
          if (checktCN.isSelected() == true) {

            if (!integers.contains(1))
              integers.add(1);
          } else {
            for (int i = 0; i < integers.size(); i++) {
              if (integers.get(i) == 1) {
                integers.remove(i);
                i--;
              }
            }
          }
        }
      });

      checkBoxs = new ArrayList<JCheckBox>();
      checkBoxs.add(checkt2);
      checkBoxs.add(checkt3);
      checkBoxs.add(checkt4);
      checkBoxs.add(checkt5);
      checkBoxs.add(checkt6);
      checkBoxs.add(checkt7);
      checkBoxs.add(checktCN);

      JPanel panel = new JPanel();
      panel.setOpaque(false);
      panel.setLayout(new FlowLayout(FlowLayout.LEFT));
      panel.add(new JLabel("Cách"));
      panel.add(spinnerWeek);
      panel.add(new JLabel("Tuần, vào những ngày"));

      JPanel panel2 = new JPanel();
      panel2.setOpaque(false);
      panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
      for (int i = 0; i < checkBoxs.size(); i++) {
        checkBoxs.get(i).setOpaque(false);
        panel2.add(checkBoxs.get(i));
      }
      add(panel);
      add(panel2);
    }
  }

  private class MonthPanel extends JPanel {
    public MonthPanel() {
      setOpaque(false);
      rdoDayInMonth = new ExtendJRadioButton("Ngày");
      rdoDayWeekMonth = new ExtendJRadioButton("Thứ");
      ButtonGroup buttonGroupMonth = new ButtonGroup();
      buttonGroupMonth.add(rdoDayInMonth);
      buttonGroupMonth.add(rdoDayWeekMonth);
      spinnerDayInMonth = new JSpinner();
      spinnerDayInMonth.setValue(0);
      spinnerDayInMonth.setPreferredSize(new Dimension(60, 23));
      String[] strings = { "CN", "2", "3", "4", "5", "6", "7" };
      comboBoxDayOfWeek = new JComboBox(strings);
      String[] stringsMonth = { "Đầu tiên", "Thứ 2", "Thứ 3", "Thứ 4", "Cuối cùng" };
      comboBoxWeekOfMonth = new JComboBox(stringsMonth);
      GridLabelLayoutPabel panel1 = new GridLabelLayoutPabel();
      panel1.setOpaque(false);
      panel1.add(0, 0, rdoDayInMonth);
      panel1.add(0, 1, spinnerDayInMonth);
      panel1.add(1, 0, rdoDayWeekMonth);
      panel1.add(1, 1, comboBoxDayOfWeek);
      panel1.add(1, 2, comboBoxWeekOfMonth);

      spinnerMonth = new JSpinner();
      spinnerMonth.setValue(0);
      spinnerMonth.setPreferredSize(new Dimension(60, 23));
      JPanel panel2 = new JPanel();
      panel2.setOpaque(false);
      panel2.setLayout(new FlowLayout(FlowLayout.LEADING));
      JSeparator comp = new JSeparator();
      comp.setOrientation(1);
      comp.setPreferredSize(new Dimension(5, 50));
      panel2.add(comp);
      JLabel label = new JLabel("Cách");
      label.setFont(new Font("Tahoma", Font.BOLD, 14));
      panel2.add(label);
      panel2.add(spinnerMonth);
      panel2.add(new ExtendJLabel("Tháng"));

      add(panel1, BorderLayout.WEST);
      add(panel2, BorderLayout.EAST);
    }
  }

  private class YearPanel extends JPanel {
    public YearPanel() {
      setOpaque(false);
      rdoAlways = new ExtendJRadioButton("Vào ngày");
      rdoRepeat = new ExtendJRadioButton("Lặp lại theo");
      ButtonGroup buttonGroupMonth = new ButtonGroup();
      buttonGroupMonth.add(rdoAlways);
      buttonGroupMonth.add(rdoRepeat);
      spinnerDayYear = new JSpinner();
      spinnerDayYear.setValue(0);
      spinnerDayYear.setPreferredSize(new Dimension(40, 23));
      String[] strings = { "Ngày", "Các ngày trong tuần", "Các ngày cuối tuần" };
      cbbRepeatDay = new JComboBox(strings);
      String[] stringsMonth = { "Đầu tiên", "Thứ 2", "Thứ 3", "Thứ 4", "Cuối cùng" };
      cbbRepeatWeek = new JComboBox(stringsMonth);
      GridLabelLayoutPabel panel1 = new GridLabelLayoutPabel();
      panel1.setOpaque(false);
      panel1.add(0, 0, rdoAlways);
      panel1.add(0, 1, spinnerDayYear);
      panel1.add(1, 0, rdoRepeat);
      panel1.add(1, 1, cbbRepeatDay);
      panel1.add(1, 2, cbbRepeatWeek);

      spinnerMonth = new JSpinner();
      spinnerMonth.setValue(0);
      spinnerMonth.setPreferredSize(new Dimension(40, 23));
      JPanel panel2 = new JPanel();
      panel2.setOpaque(false);
      panel2.setLayout(new FlowLayout(FlowLayout.LEADING));
      JSeparator comp = new JSeparator();
      comp.setOrientation(1);
      comp.setPreferredSize(new Dimension(5, 50));
      panel2.add(comp);
      JLabel label = new JLabel("Tháng");
      label.setFont(new Font("Tahoma", Font.BOLD, 14));
      panel2.add(label);
      panel2.add(spinnerMonth);
      add(panel1, BorderLayout.WEST);
      add(panel2, BorderLayout.EAST);
    }
  }

  @Override
  public void save() throws Exception {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
    TimeOption timeOption;
    if (timeOptionId == 0) {
      timeOption = new TimeOption();
    } else {
      timeOption = PromotionModelManager.getInstance().findOneTimeOption(timeOptionId);
    }
    timeOption.setCode(dateFormat.format(new Date()));
    TimeOptionDetail timeOptionDetail = new TimeOptionDetail();
    timeOptionDetail.setTimeOption(timeOption);
    timeOption.setDailyChoose(false);
    timeOption.setWeeklyChoose(false);
    timeOption.setMonthlyChoose(false);
    timeOption.setYearlyChoose(false);
    if (rdoDay.isSelected() == true) {
      timeOption.setDailyChoose(true);
      Daily daily = new Daily();
      daily.setCode(dateFormat.format(new Date()));
      if (rdoEveDay.isSelected()) {
        daily.setEveryWeekDay(true);
        daily.setStep(0);
      } else {
        daily.setEveryWeekDay(false);
        daily.setStep(Integer.parseInt(spinnerNgay.getValue().toString()));
      }
      timeOptionDetail.setDaily(daily);
    } else if (rdoWeek.isSelected()) {
      timeOption.setWeeklyChoose(true);
      Weekly weekly = new Weekly();
      weekly.setCode(dateFormat.format(new Date()));
      weekly.setDaysOfWeek(integers.toString());
      weekly.setStep(Integer.parseInt(spinnerWeek.getValue().toString()));
      timeOptionDetail.setWeekly(weekly);
    } else if (rdoMonth.isSelected()) {
      timeOption.setMonthlyChoose(true);
      Monthly monthly = new Monthly();
      monthly.setCode(dateFormat.format(new Date()));
      monthly.setNoLevel(rdoDayInMonth.isSelected());
      monthly.setStep(Integer.parseInt(spinnerMonth.getValue().toString()));
      monthly.setDayRepeat(Integer.parseInt(spinnerDayInMonth.getValue().toString()));
      monthly.setDayOfWeek(comboBoxDayOfWeek.getSelectedIndex());
      monthly.setLevel(comboBoxWeekOfMonth.getSelectedIndex());
      timeOptionDetail.setMonthly(monthly);
    } else if (rdoYear.isSelected()) {
      timeOption.setYearlyChoose(true);
      Yearly yearly = new Yearly();
      yearly.setCode(dateFormat.format(new Date()));
      yearly.setEveryDay(rdoAlways.isSelected());
      yearly.setDayOfMonth(Integer.parseInt(spinnerDayYear.getValue().toString()));
      yearly.setMonth(Integer.parseInt(spinnerMonth.getValue().toString()));
      yearly.setLevel(cbbRepeatWeek.getSelectedIndex());
      yearly.setDayLevel(cbbRepeatDay.getSelectedIndex());
      timeOptionDetail.setYearly(yearly);
    }

    timeOptionDetail = PromotionModelManager.getInstance().saveTimeOptionDetail(timeOptionDetail);
    // set data to parent
    if (dialogMain instanceof PanelAutoBackupData) {
      ((PanelAutoBackupData) dialogMain).setTimeOption(timeOptionDetail.getTimeOption());
    }
    timeOptionId = timeOptionDetail.getTimeOption().getId();
    ((JDialog) getRootPane().getParent()).setVisible(false);
  }

  public long getTimeOptionId() {
    return timeOptionId;
  }

  @Override
  public void edit() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void delete() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void reset() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void refresh() throws Exception {
    if (timeOptionId > 0) {
      TimeOption timeOption = PromotionModelManager.getInstance().findOneTimeOption(timeOptionId);
      if (timeOption.isDailyChoose()) {
        rdoDay.setSelected(true);
        scrollPane.setViewportView(new DayPanel());
        List<Daily> dailies = PromotionModelManager.getInstance().getDailyByTimeOp(timeOptionId);
        Daily daily = dailies.get(dailies.size() - 1);
        if (daily.isEveryWeekDay()) {
          rdoEveDay.setSelected(true);
        } else {
          rdoADay.setSelected(true);
          spinnerNgay.setValue(daily.getStep());
        }

      } else if (timeOption.isWeeklyChoose()) {
        rdoWeek.setSelected(true);
        scrollPane.setViewportView(new WeekPanel());
        List<Weekly> weeklies = PromotionModelManager.getInstance().getWeeklyByTimeOp(timeOptionId);
        Weekly weekly = weeklies.get(weeklies.size() - 1);
        spinnerWeek.setValue(weekly.getStep());
        integers = weekly.dayOfWeeks();
        for (int i = 0; i < integers.size(); i++) {
          int d = integers.get(i);
          System.out.println("integers " + d);
          if (d == 2) {
            checkt2.setSelected(true);
          }
          if (d == 3) {
            checkt3.setSelected(true);
          }
          if (d == 4) {
            checkt4.setSelected(true);
          }
          if (d == 5) {
            checkt5.setSelected(true);
          }
          if (d == 6) {
            checkt6.setSelected(true);
          }
          if (d == 7) {
            checkt7.setSelected(true);
          }
          if (d == 1) {
            checktCN.setSelected(true);
          }

        }
      } else if (timeOption.isMonthlyChoose()) {
        rdoMonth.setSelected(true);
        scrollPane.setViewportView(new MonthPanel());
        List<Monthly> monthlies = PromotionModelManager.getInstance().getMonthlyByTimeOp(timeOptionId);
        Monthly monthly = monthlies.get(monthlies.size() - 1);
        if (monthly.isNoLevel()) {
          rdoDayInMonth.setSelected(true);
          spinnerDayInMonth.setValue(monthly.getDayRepeat());
        } else {
          rdoDayWeekMonth.setSelected(true);
          comboBoxDayOfWeek.setSelectedIndex(monthly.getDayOfWeek());
          comboBoxWeekOfMonth.setSelectedIndex(monthly.getLevel());
        }
        spinnerMonth.setValue(monthly.getStep());

      } else if (timeOption.isYearlyChoose()) {
        rdoYear.setSelected(true);
        scrollPane.setViewportView(new YearPanel());
        List<Yearly> yearlies = PromotionModelManager.getInstance().getYearlyByTimeOp(timeOptionId);
        Yearly yearly = yearlies.get(yearlies.size() - 1);
        if (yearly.isEveryDay()) {
          rdoAlways.setSelected(true);
          spinnerDayYear.setValue(yearly.getDayOfMonth());
        } else {
          rdoRepeat.setSelected(true);
          cbbRepeatWeek.setSelectedIndex(yearly.getLevel());
          cbbRepeatDay.setSelectedIndex(yearly.getDayLevel());
        }
        spinnerMonth.setValue(yearly.getMonth());
      }
    }

  }

  public void setParent(IDialogMain dialogMain) {
    this.dialogMain = dialogMain;
  }
}
