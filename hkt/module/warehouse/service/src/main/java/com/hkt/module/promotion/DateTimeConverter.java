package com.hkt.module.promotion;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeConverter {

  private Calendar calendar;
  private DateFormat dateFormat;
  private Date date = null;

  protected void setup(String dateConvert) {
      dateFormat = new SimpleDateFormat("dd/MM/yyyy");
      try {
          date = (Date) dateFormat.parse(dateConvert);
      } catch (ParseException e) {
      }
      calendar = Calendar.getInstance();
      calendar.setTime(date);
  }

  public DateTimeConverter() {
  }

  public DateTimeConverter(Calendar calendar, DateFormat dateFormat) {
      this.calendar = calendar;
      this.dateFormat = dateFormat;
  }

  public int getDay(String date) {
      int day = 0;
      setup(date);
      day = calendar.get(Calendar.DATE);
      return day;
  }

  public int getDay(Calendar date) {
      int day = 0;
      day = date.get(Calendar.DATE);
      return day;
  }

  public int getMonth(String date) {
      int m = 0;
      setup(date);
      m = calendar.get(Calendar.MONTH) + 1;
      return m;
  }

  public int getMonth(Calendar date) {
      int m = 0;
      m = date.get(Calendar.MONTH) + 1;
      return m;
  }

  public int getYear(String date) {
      int y = 0;
      setup(date);
      y = calendar.get(Calendar.YEAR);
      return y;
  }

  public int getYear(Calendar date) {
      int y = 0;
      y = date.get(Calendar.YEAR);
      return y;
  }

  /**
   * Tong so tuan trong thang
   * nhap vao dd/MM/yyyy
   * @param date
   * @return 
   */
  public int getTotalWeekOfMonth(String date) {
      int week = 0;
      setup(date);
      week = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
      return week;
  }

  /**
   * Tong so tuan trong thang
   * nhap vao dd/MM/yyyy
   * @param date
   * @return 
   */
  public int getTotalWeekOfMonth(Calendar date) {
      int week = 0;
      week = date.getActualMaximum(Calendar.WEEK_OF_MONTH);
      return week;
  }

  /**
   * Tuan thu may trong thang
   * nhap vao dd/MM/yyyy
   * @param date
   * @return 
   */
  public int getWeekOfMonth(String date) {
      int week = 0;
      setup(date);
      week = calendar.get(Calendar.WEEK_OF_MONTH);
      return week;
  }

  /**
   * Tuan thu may trong thang
   * nhap vao dd/MM/yyyy
   * @param date
   * @return 
   */
  public int getWeekOfMonth(Calendar date) {
      int week = 0;
      week = date.get(Calendar.WEEK_OF_MONTH);
      return week;
  }

  /**
   * Tuan thu may trong nam
   * nhap vao dd/MM/yyyy
   * @param date
   * @return 
   */
  public int getWeekOfYear(String date) {
      int week = 0;
      setup(date);
      week = calendar.get((Calendar.WEEK_OF_YEAR));
      return week;
  }

  /**
   * Tuan thu may trong nam
   * nhap vao dd/MM/yyyy
   * @param date
   * @return 
   */
  public int getWeekOfYear(Calendar date) {
      int week = 0;
      week = date.get((Calendar.WEEK_OF_YEAR));
      return week;
  }

  /**
   * Tong so ngay trong nam
   * nhap vao dd/MM/yyyy
   * @param date
   * @return 
   */
  public int getTotalDayOfYear(String date) {
      int days = 0;
      setup(date);
      days = calendar.getActualMaximum((Calendar.DAY_OF_YEAR));
      return days;
  }

  /**
   * Tong so ngay trong nam
   * nhap vao dd/MM/yyyy
   * @param date
   * @return 
   */
  public int getTotalDayOfYear(Calendar date) {
      int days = 0;
      days = date.getActualMaximum((Calendar.DAY_OF_YEAR));
      return days;
  }

  /**
   * Tong so ngay trong thang
   * nhap vao dd/MM/yyyy
   * @param date
   * @return 
   */
  public int getTotalDayOfMonth(Calendar date) {
      int days = 0;
      days = date.getActualMaximum((Calendar.DAY_OF_MONTH));
      return days;
  }

  /**
   * Tong so ngay trong thang
   * nhap vao dd/MM/yyyy
   * @param date
   * @return 
   */
  public int getTotalDayOfMonth(String date) {
      int days = 0;
      setup(date);
      days = calendar.getActualMaximum((Calendar.DAY_OF_MONTH));
      return days;
  }

  /**
   * Lay so tuan hien tai
   * @param c
   * @return 
   */
  public int getCurrentWeek(Calendar c) {
      return c.get(Calendar.WEEK_OF_MONTH);
  }

  /**
   * Dem so thang giua 2 Date
   * VD: 04/2012 - 02/2012 = 2
   * @param c1
   * @param c2
   * c1 < c2
   * @return 
   */
  public int countMonthsBetweenTwoMonths(Calendar c1, Calendar c2) {
      int count = 0;
      DateFormat dateFormat1 = new SimpleDateFormat("MM/yyyy");
      try {
          String strDate1 = (c1.get(Calendar.MONTH) + 1) + "/" + c1.get(Calendar.YEAR);
          String strDate2 = (c2.get(Calendar.MONTH) + 1) + "/" + c2.get(Calendar.YEAR);
          Date date1 = dateFormat1.parse(strDate1);
          Date date2 = dateFormat1.parse(strDate2);
          c1.setTime(date1);
          c2.setTime(date2);
          while (c1.before(c2)) {
              c2.add(Calendar.MONTH, -1);
              count++;
          }
      } catch (ParseException e) {
      }

      return count;
  }

  /**
   * Đếm số ngày giữa 2 date
   * VD: Giữa 01/02/2012 và 01/02/2012 có 0 ngày
   *     Giữa 01/02/2012 và 02/02/2012 có 0 ngày
   *     Giữa 01/02/2012 và 03/02/2012 có 1 ngày
   * @param c1 : dd/MM/yyyy 00:00:00
   * @param c2 : dd/MM/yyyy 00:00:00
   * @return 
   */
  public long countDayBetweenTwoDate(Calendar c1, Calendar c2) {
      c1.set(Calendar.HOUR, 0);
      c1.set(Calendar.MINUTE, 0);
      c1.set(Calendar.SECOND, 0);
      c1.set(Calendar.MILLISECOND, 0);
      c2.set(Calendar.HOUR, 0);
      c2.set(Calendar.MINUTE, 0);
      c2.set(Calendar.SECOND, 0);
      c2.set(Calendar.MILLISECOND, 0);

      long milis1 = c1.getTimeInMillis();
      long milis2 = c2.getTimeInMillis();
      // Calculate difference in milliseconds
      long diff = Math.abs(milis2 - milis1);
      // Calculate difference in seconds
      long diffSeconds = diff / 1000;
      // Calculate difference in minutes
      long diffMinutes = diffSeconds / (60);
      // Calculate difference in hours
      long diffHours = diffMinutes / (60);
      // Calculate difference in days
      long diffDays = diffHours / (24);

      return diffDays;
  }

  /**
   * Dem so tuan giua 2 moc thoi gian
   * Lay thu 2 la dau tuan
   * @param c1
   * @param c2
   * @return 
   */
  public int countWeeksBetweenTwoMonths(Calendar c1, Calendar c2) {
      int count = 0;
      int firstweek = 0, firstday = 0, firstcut = 0;
      int lastweek = 0, lastday = 0, lastcut = 0;
      int totaldays = (int) countDayBetweenTwoDate(c1, c2);

      if (totaldays <= 7) {
          int i;
          count = 1;
          for (i = 0; i < totaldays; i++) {
              if (c1.before(c2)) {
                  c1.add(Calendar.DAY_OF_MONTH, 1);
              }
              if (c1.get(Calendar.DAY_OF_WEEK) == 2) {
                  count++;
              }
          }
      } else {
          firstday = c1.get(Calendar.DAY_OF_WEEK);
          lastday = c2.get(Calendar.DAY_OF_WEEK);
          if (firstday != 1) {
              firstweek = 1;
              firstcut = cutDay(c1, 1);
          }
          if (lastday != 1) {
              lastweek = 1;
              lastcut = cutDay(c2, -1);
          }
          count = ((totaldays - (firstcut + lastcut)) / 7) + firstweek + lastweek;
      }
      return count;
  }

  /**
   * Cut di so ngay de co duoc dau tuan
   * @param c1
   * @param n
   * @return 
   */
  public int cutDay(Calendar c1, int n) {
      int value = 0;
      Date d1 = c1.getTime();
      Calendar calendar1 = Calendar.getInstance();
      calendar1.setTime(d1);
      int day = c1.get(Calendar.DAY_OF_WEEK);
      if (n > 0) {
          while (day != 1) {
              calendar1.add(Calendar.DAY_OF_WEEK, n);
              day = calendar1.get(Calendar.DAY_OF_WEEK);
              value++;
          }
      } else {
          while (day != 2) {
              calendar1.add(Calendar.DAY_OF_WEEK, n);
              day = calendar1.get(Calendar.DAY_OF_WEEK);
              value++;
          }
          value = value == 0 ? value : value + 1;
      }
      return value;
  }

  /**
   * So sanh 2 Date
   * @param date
   * @return 
   */

  /*
   * so sánh 2 ngày kiểu string truy�?n vào (dd/MM/yyyy)
   * trả v�? -2 nếu ngày thứ nhất ko đúng định dạng
   * trả v�? 2 nếu ngày thứ hai ko đúng định dạng
   * trả v�? -1 nếu ngày thứ nhất < ngày thứ 2
   * trả v�? 1 nếu ngày thứ nhất > ngày thứ 2
   * trả v�? 0 nếu bằng nhau
   */

  public int compareDate(String date1, String date2) {

      dateFormat = new SimpleDateFormat("dd/MM/yyyy");
      Date d1 = null;
      Date d2 = null;
      try {
          d1 = dateFormat.parse(date1);
          d2 = dateFormat.parse(date2);
      } catch (ParseException ex) {
      }
      if (d1 == null) {
          return -2;
      }
      if (d2 == null) {
          return 2;
      }
      if (d1.before(d2)) {
          return -1;
      }
      if (d1.after(d2)) {
          return 1;
      }
      return 0;
  }

  /*
   * kiểm tra ngày truy�?n vào có phải là null hoặc không đúng định dang không
   */
  public boolean dateIsNull(String stringDate) {
      setup(stringDate);
      try {
          dateFormat.parse(stringDate);
          return false;
      } catch (Exception e) {
          return true;
      }
  }

  /*
   * kiểm tra ngày truy�?n vào có phải là null hoặc không đúng định dang không
   */
  public boolean dateIsNull(Calendar stringDate) {

      if (stringDate != null) {
          return false;
      }

      return true;

  }

  public String firstDateInMonth(String date) {
      setup(date);
      calendar.set(Calendar.DAY_OF_MONTH, 1);
      return dateFormat.format(calendar.getTime());
  }

  public String lastDateInMonth(String date) {
      setup(date);
      int lastDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
      calendar.set(Calendar.DAY_OF_MONTH, lastDate);
      return dateFormat.format(calendar.getTime());
  }
  /*
   * Lấy ngày có khoảng cách một số ngày với ngày truy�?n vào
   * Date: ngày truy�?n vào
   * space: số ngày cách so với ngày truy�?n vào
   */

  public String getDateSpace(String date, int spaceDay) {
      setup(date);
      calendar.add(Calendar.DAY_OF_MONTH, spaceDay);
      return dateFormat.format(calendar.getTime());
  }/*
   * Lấy ngày có khoảng cách một số ngày với ngày truy�?n vào
   * Date: ngày truy�?n vào
   * space: số ngày cách so với ngày truy�?n vào
   */


  public Calendar getDateSpace(Calendar date, int spaceDay) {
      date.add(Calendar.DAY_OF_MONTH, spaceDay);
      return date;
  }
  /*
   * Lấy ngày có khoảng cách một số tháng với ngày truy�?n vào
   * Date: ngày truy�?n vào
   * space: số tháng cách so với ngày truy�?n vào
   */

  public String getMonthSpace(String date, int spaceMonth) {
      setup(date);
      calendar.add(Calendar.MONTH, spaceMonth);
      return dateFormat.format(calendar.getTime());
  }
  /*
   * Lấy ngày có khoảng cách một số tháng với ngày truy�?n vào
   * Date: ngày truy�?n vào
   * space: số tháng cách so với ngày truy�?n vào
   */

  public Calendar getMonthSpace(Calendar date, int spaceMonth) {
      date.add(Calendar.MONTH, spaceMonth);
      return date;
  }
  /*
   * Lấy ngày có khoảng cách một số năm với ngày truy�?n vào
   * Date: ngày truy�?n vào
   * space: số năm cách so với ngày truy�?n vào
   */

  public String getYearSpace(String date, int spaceYear) {
      setup(date);
      calendar.add(Calendar.YEAR, spaceYear);
      return dateFormat.format(calendar.getTime());
  }
  /*
   * Lấy ngày có khoảng cách một số năm với ngày truy�?n vào
   * Date: ngày truy�?n vào
   * space: số năm cách so với ngày truy�?n vào
   */

  public Calendar getYearSpace(Calendar date, int spaceYear) {
      date.add(Calendar.YEAR, spaceYear);
      return date;
  }

  /**
   * So sanh c1 co la gia tri nam giua c2 va c3 khong
   * @param c1
   * @param c2
   * @param c3
   * @return 
   */
  public boolean compareBetweenDate(Calendar c1, Calendar c2, Calendar c3) {
      if (c1 == null) {
          return false;
      }
      if (c2 == null) {
          return false;
      }
      if (c3 == null) {
          return false;
      }
      Date d1 = c1.getTime();
      Date d2 = c2.getTime();
      Date d3 = c3.getTime();
      if (d1.after(d2) && d1.before(d3)) {
          return true;
      }
      return false;
  }

  /**
   * So sanh d1 co la gia tri nam giua d2 va d3 khong
   * @param d1
   * @param d2
   * @param d3
   * @return 
   */
  public boolean compareBetweenDate(Date d1, Date d2, Date d3) {
      if (d1 == null) {
          return false;
      }
      if (d2 == null) {
          return false;
      }
      if (d3 == null) {
          return false;
      }
      if (d1.after(d2) && d1.before(d3)) {
          return true;
      }
      return false;
  }

  /*
   * so sánh 2 ngày kiểu Calendar
   * trả v�? -2 nếu ngày thứ nhất ko đúng định dạng
   * trả v�? 2 nếu ngày thứ hai ko đúng định dạng
   * trả v�? -1 nếu ngày thứ nhất < ngày thứ 2
   * trả v�? 1 nếu ngày thứ nhất > ngày thứ 2
   * trả v�? 0 nếu bằng nhau
   */
  public int compareTwoDate(Calendar calendar1, Calendar calendar2) {
      String strDate1 = calendar1.get(Calendar.DAY_OF_MONTH)
              + "/" + (calendar1.get(Calendar.MONTH) + 1) + "/"
              + calendar1.get(Calendar.YEAR);
      String strDate2 = calendar2.get(Calendar.DAY_OF_MONTH)
              + "/" + (calendar2.get(Calendar.MONTH) + 1) + "/"
              + calendar2.get(Calendar.YEAR);
      return compareDate(strDate1, strDate2);
  }

  /**
   * so sánh 2 ngày kiểu Calendar
   * trả v�? -2 nếu ngày thứ nhất ko đúng định dạng
   * trả v�? 2 nếu ngày thứ hai ko đúng định dạng
   * trả v�? -1 nếu ngày thứ nhất < ngày thứ 2
   * trả v�? 1 nếu ngày thứ nhất > ngày thứ 2
   * trả v�? 0 nếu bằng nhau
   * @param date1
   * @param date2
   * @return 
   */
  public int compareTwoDate(Date date1, Date date2) {
      Calendar c1 = Calendar.getInstance();
      c1.setTime(date1);
      Calendar c2 = Calendar.getInstance();
      c2.setTime(date2);
      return compareTwoDate(c1, c2);
  }

  /**
   * Kiem tra 1 ngay co nam giua 1 khoang ngay khong (<= >=)
   * @param d1 StartDate
   * @param d2 FinishDate
   * @param d3 Date now
   * @return 
   */
  public boolean compareTwoDate(Date d1, Date d2, Date d3) {
      if ((compareTwoDate(d1, d3) <= 0) && (compareTwoDate(d2, d3) >= 0)) {
          return true;
      }
      return false;
  }

  /**
   * Kiem tra 1 gio nam giua 1 khoang gio
   * Tra ve 1 neu trong khoang, tra ve 0 neu ngoai khoang
   * @param c1 : Moc 1
   * @param c2 : Moc 2
   * @param c3 : Gio can check
   * @return 
   */
  public int compareBetweenTime(Calendar c1, Calendar c2, Calendar c3) {
      int hour1 = 0;
      hour1 = c1.get(Calendar.HOUR_OF_DAY);
      int minute1 = 0;
      minute1 = c1.get(Calendar.MINUTE);
      int second1 = 0;
      second1 = c1.get((Calendar.SECOND));
      int value1 = 0;
      value1 = hour1 * 3600 + minute1 * 60 + second1;

      hour1 = c2.get(Calendar.HOUR_OF_DAY);
      minute1 = c2.get(Calendar.MINUTE);
      second1 = c2.get((Calendar.SECOND));
      int value2 = 0;
      value2 = hour1 * 3600 + minute1 * 60 + second1;

      hour1 = c3.get(Calendar.HOUR_OF_DAY);
      minute1 = c3.get(Calendar.MINUTE);
      second1 = c3.get((Calendar.SECOND));
      int value3 = 0;
      value3 = hour1 * 3600 + minute1 * 60 + second1;

      if (value3 >= value1 && value3 <= value2) {
          return 1;
      }
      return 0;
  }

  /**
   * Kiem tra 1 gio nam giua 1 khoang gio
   * Tra ve 1 neu trong khoang, tra ve 0 neu ngoai khoang
   * @param c1 : Moc 1
   * @param c2 : Moc 2
   * @param c3 : Gio can check
   * @return 
   */
  public int compareBetweenTime(Date d1, Date d2, Date d3) {
      Calendar c1 = Calendar.getInstance();
      c1.setTime(d1);
      Calendar c2 = Calendar.getInstance();
      c2.setTime(d2);
      Calendar c3 = Calendar.getInstance();
      c3.setTime(d3);
      int hour1 = 0;
      hour1 = c1.get(Calendar.HOUR_OF_DAY);
      int minute1 = 0;
      minute1 = c1.get(Calendar.MINUTE);
      int second1 = 0;
      second1 = c1.get((Calendar.SECOND));
      int value1 = 0;
      value1 = hour1 * 3600 + minute1 * 60 + second1;

      hour1 = c2.get(Calendar.HOUR_OF_DAY);
      minute1 = c2.get(Calendar.MINUTE);
      second1 = c2.get((Calendar.SECOND));
      int value2 = 0;
      value2 = hour1 * 3600 + minute1 * 60 + second1;

      hour1 = c3.get(Calendar.HOUR_OF_DAY);
      minute1 = c3.get(Calendar.MINUTE);
      second1 = c3.get((Calendar.SECOND));
      int value3 = 0;
      value3 = hour1 * 3600 + minute1 * 60 + second1;

      if (value3 >= value1 && value3 <= value2) {
          return 1;
      }
      return 0;
  }

  /**
   * Check d1 before d2
   * @param d1: Date
   * @param d2: Date
   */
  public boolean checkDateBeforDate(Date d1, Date d2) {
      if (d1 == null) {
          return true;
      }
      if (d1 != null && d2 == null) {
          return false;
      }
      Calendar c = Calendar.getInstance();
      c.setTime(d1);
      int y = c.get(Calendar.YEAR);
      int m = c.get(Calendar.MONTH);
      int d = c.get(Calendar.DAY_OF_MONTH);
      Calendar cc = Calendar.getInstance();
      cc.setTime(d2);
      int yy = cc.get(Calendar.YEAR);
      int mm = cc.get(Calendar.MONTH);
      int dd = cc.get(Calendar.DAY_OF_MONTH);
      if (y > yy || (y == yy && m > mm) || (y == yy && m == mm && d >= dd)) {
          return false;
      }
      return true;
  }

  /**
   * Check d1 after d2
   * @param d1: Date
   * @param d2: Date
   * @return 
   */
  public boolean checkDateAfterDate(Date d1, Date d2) {
      if (d2 == null) {
          return false;
      }
      if (d2 != null && d1 == null) {
          return true;
      }
      Calendar c = Calendar.getInstance();
      c.setTime(d1);
      int y = c.get(Calendar.YEAR);
      int m = c.get(Calendar.MONTH);
      int d = c.get(Calendar.DAY_OF_MONTH);
      Calendar cc = Calendar.getInstance();
      cc.setTime(d2);
      int yy = cc.get(Calendar.YEAR);
      int mm = cc.get(Calendar.MONTH);
      int dd = cc.get(Calendar.DAY_OF_MONTH);
      if (y < yy || (y == yy && m < mm) || (y == yy && m == mm && d <= dd)) {
          return false;
      }
      return true;
  }

  /**
   * Kiem tra 1 ngay co la ngay thu n trong thang khong
   * VD: thu 6 dau tien, thu 6 thu 2....
   * @param calendar
   * @param m : Thu can kiem tra
   * @param n : level
   * @return 
   */
  public boolean isOrder(Calendar calendar, int m, int n) {
      if (n < 1 || n > 7) {
          return false;
      } else {
          int day = calendar.get(Calendar.DAY_OF_WEEK);
          if (day != m) {
              return false;
          } else {
              int calendarDay = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
              if (calendarDay == n) {
                  return true;
              }
              return false;
          }
      }
  }

  /**
   * Kiem tra xem 1 ngay trong tuan co la ngay cuoi cung trong thang khong
   * VD: ngay chu nhat cuoi cung cua thang, thu 2 cuoi cung cua thang...
   * @param calendar: ngay hien tai
   * @param day: CN, T2, T3, T4, T5, T6, T7
   * @return true neu dung
   *         false neu sai
   */
  public boolean isLastDay(Calendar calendar, int day) {
      Date d = calendar.getTime();
      Calendar c = Calendar.getInstance();
      c.setTime(d);
      c.set(Calendar.DAY_OF_WEEK, day);
      int level = c.get(Calendar.DAY_OF_WEEK_IN_MONTH);
      int maxLevel = c.getActualMaximum(Calendar.DAY_OF_WEEK_IN_MONTH);
      if (level == maxLevel) {
          return true;
      }
      return false;
  }

  /**
   * Lấy giá trị quý của date 
   * VD: ngày 10/2/2012 --> quý 1
   * @param date ngày truy�?n vào 
   * @return quý sau khi tính toán 
   */
  public int getQuarter(Date date) {
      Calendar c = Calendar.getInstance();
      c.setTime(date);
      int month = c.get(Calendar.MONTH) + 1;
      int quarter = (int) Math.ceil(month * 1.000 / 3);
      return quarter;
  }

  public int subDate(Date dateForm, Date dateTo) {
      int k = 0;
      Calendar c = Calendar.getInstance();
      c.setTime(dateForm);
      while (checkDateBeforDate(c.getTime(), dateTo)) {
          k++;
          c.add(Calendar.DATE, 1);
      }
      return k;
  }
}
