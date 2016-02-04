package com.hkt.module.promotion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.Timer;

import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.promotion.entity.Daily;
import com.hkt.module.promotion.entity.Monthly;
import com.hkt.module.promotion.entity.TimeOption;
import com.hkt.module.promotion.entity.Yearly;

public class AutoRunBackup {
	private static final String LOCATION_URL = HKTFile.getDirectory("AutoSave");
	private AutoSave autoSave;
	private DateTimeConverter dateTimeConverter;
	private int fileNumCurrent = 0;
	private BackupRestoreData backupRestoreData;
	@Autowired
	PromotionService promotionService;

	public AutoRunBackup() {
		backupRestoreData = new BackupRestoreData();
	}

	private AutoSave getData() {
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

	public void startTimer() {
		Timer timer = new Timer(60000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					promotionService.findOneTimeOption(1);
					autoSave = getData();
					if (autoSave != null && validateTimeOption(autoSave.getIdTimeOption(), new Date(), autoSave.getDateStart(), autoSave.getDateEnd())) {
						Calendar calendar = Calendar.getInstance();
						int hour = calendar.get(Calendar.HOUR_OF_DAY);
						int minute = calendar.get(Calendar.MINUTE);
						int timeNow = hour * 60 + minute;
						int time = autoSave.getHourStart() * 60 + autoSave.getMinuteStart();
						int subTime = Math.abs((time - timeNow));
						if (subTime % (autoSave.getStepHour() * 60) == 0) {
							fileNumCurrent = readFileCurrent();
							if (fileNumCurrent == 0) {
								fileNumCurrent += 1;
								// run backup
								String localeFile = autoSave.getLocation().replace("\\", "/");
								if (!localeFile.endsWith("/")) {
									localeFile = localeFile + "/";
								}
								String url = localeFile + "autobak" + new SimpleDateFormat("HH").format(new Date()) + ".sql";
								CheckOS checkOS = new CheckOS();
								if (checkOS.isWindows()) {
									backupRestoreData.tbBackupWin("hktdb", "root", "root", url);
								} else if (checkOS.isUnix()) {
									backupRestoreData.tbBackupLinux("hktdb", "root", "root", url);
								}
							} else {
								if (fileNumCurrent + 1 > autoSave.getQuantitySave()) {
									fileNumCurrent = 1;
								} else {
									fileNumCurrent = fileNumCurrent + 1;
								}
								// run backup
								String url = LOCATION_URL + "/autobak" + fileNumCurrent + ".sql";
								CheckOS checkOS = new CheckOS();
								if (checkOS.isWindows()) {
									backupRestoreData.tbBackupWin("hktdb", "root", "root", url);
								} else if (checkOS.isUnix()) {
									backupRestoreData.tbBackupLinux("hktdb", "root", "root", url);
								}
							}
						}
					}

				} catch (Exception e2) {
					//System.out.println("Loi co so du lieu");
				}
			}
		});
		timer.start();
	}

	private int readFileCurrent() {
		String fileNum = LOCATION_URL + "/current.txt";
		int numCurrent = 0;
		File file = new File(fileNum);
		if (file.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line = "";
				while ((line = br.readLine()) != null) {
					numCurrent = Integer.parseInt(line);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				numCurrent = 0;
			}
		}
		return numCurrent;
	}

	public boolean validateTimeOption(long idTimeOption, Date dateNow, Date dateStart, Date dateFinish) {
		Calendar cNow = Calendar.getInstance();
		Calendar cStart = Calendar.getInstance();
		Calendar cFinish = Calendar.getInstance();

		cNow.setTime(dateNow);
		cStart.setTime(dateStart);
		cFinish.setTime(dateFinish);
		return validate(idTimeOption, cStart, cFinish, cNow);
	}

	public boolean validate(long idTimeOption, Calendar startDate, Calendar finishDate, Calendar currentDate) {
		try {

			TimeOption timeOption = promotionService.findOneTimeOption(idTimeOption);
			if (timeOption != null) {
				if (timeOption.isDailyChoose()) {
					List<Daily> list = promotionService.getDailyByTimeOp(idTimeOption);
					if (list != null && !list.isEmpty()) {
						Daily daily = list.get(0);
						return checkDailyLimit(daily, startDate, currentDate);
					} else {
						return false;
					}
				} else if (timeOption.isMonthlyChoose()) {
					List<Monthly> list = promotionService.getMonthlyByTimeOp(idTimeOption);
					if (list != null && !list.isEmpty()) {
						Monthly monthly = list.get(0);
						return checkMonthlyLimit(monthly, startDate, currentDate);
					} else {
						return false;
					}
				} else if (timeOption.isYearlyChoose()) {
					List<Yearly> list = promotionService.getYearlyByTimeOp(idTimeOption);
					if (list != null && !list.isEmpty()) {
						Yearly yearly = list.get(0);
						return checkYearlyLimit(yearly, startDate, currentDate);
					} else {
						return false;
					}
				}
			} else {
				return true;
			}
			return true;

		} catch (Exception e) {
			// e.printStackTrace();
		}
		return true;
	}

	protected boolean checkDailyLimit(Daily daily, Calendar startDate, Calendar current) {
		if (daily != null) { // Neu tuy chon hang ngay da dc
													// chon------------------------------
			if (daily.isEveryWeekDay()) { // Tra ve true neu tuy chon la hang
																		// ngay-------------
				return true;
			} else { // Nguoc lai neu tuy chon theo boi cua so
								// ngay----------------------------
				int num = daily.getStep();
				int days = (int) dateTimeConverter.countDayBetweenTwoDate(startDate, current);
				if (days % num == 0) { // Neu ngay tryen vao dung quy luat ap dung
					return true;
				} else { // Ngay tryen vao ko dung quy luat ap dung
					return false;
				}
			}
		} else {
			return true;
		}
	}

	/**
	 * Kiem tra ngay nao do co thuoc quy luat ap dung theo tuan hay khong
	 * 
	 * @param saleOff
	 * @param weekly
	 * @param startDate
	 * @param current
	 * @return
	 */

	public boolean checkMonthlyLimit(Monthly monthly, Calendar startDate, Calendar current) {
		if (monthly != null) {
			int calendarDay = current.get(Calendar.DAY_OF_MONTH);
			Date date = current.getTime();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int level = monthly.getLevel();
			int day = monthly.getDayRepeat();
			int step = monthly.getStep();
			int dayOfWeek = monthly.getDayOfWeek();
			int months = dateTimeConverter.countMonthsBetweenTwoMonths(startDate, calendar);
			boolean isNolevel = monthly.isNoLevel();
			if (isNolevel) {// Neu la tuy chon theo ngay
				if (((step == 0) || (months % step == 0)) && (day == calendarDay)) {
					return true;
				} else {
					return false;
				}
			} else {
				if (level == 5) {
					level = current.getActualMaximum(Calendar.DAY_OF_WEEK_IN_MONTH);
				}
				boolean value = dateTimeConverter.isOrder(current, dayOfWeek, level);
				if (((step == 0) || (months % step == 0)) && (value == true)) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			return true;
		}
	}

	/**
	 * Kiem tra tinh hop le cua nam
	 * 
	 * @param yearly
	 * @param startDate
	 * @param current
	 * @return
	 */
	public boolean checkYearlyLimit(Yearly yearly, Calendar startDate, Calendar current) {
		if (yearly != null) {
			int currentMonth = current.get(Calendar.MONTH) + 1;
			int dayOfWeekCurrent = current.get(Calendar.DAY_OF_WEEK);
			int currentDay = current.get(Calendar.DAY_OF_MONTH);
			int month = yearly.getMonth();
			boolean isEveryday = yearly.isEveryDay();
			if (month == currentMonth) {
				if (isEveryday) { // Neu tuy chon theo ngay
					int day = yearly.getDayOfMonth();
					if ((day == currentDay)) {
						return true;
					}
					return false;
				} else { // Neu tuy chon theo quy luat phan cap
					int level = yearly.getLevel();
					int dayLevel = yearly.getDayLevel();
					if (level == 5) {// Neu la tuy chon cuoi cung
						if (dayLevel == 0) {// Neu la tuy chon ngay thu level
							level = current.getActualMaximum(Calendar.DAY_OF_MONTH);
							return doValidateByDay(currentDay, level);
						} else if (dayLevel == 1) {// Neu la tuy chon cac ngay trong tuan
																				// thu level
							level = current.getActualMaximum(Calendar.WEEK_OF_MONTH);
							int levelWeekCurrent = current.get(Calendar.WEEK_OF_MONTH);// Tuan
																																					// thu
																																					// may
																																					// trong
																																					// thang
																																					// hien
																																					// tai
							return doValidateByEveryDay(levelWeekCurrent, level);
						} else if (dayLevel == 2) {// Neu la tuy chon 2 ngay cuoi tuan thu
																				// level
							if (dayOfWeekCurrent == 1 || dayOfWeekCurrent == 7) {
								if (dateTimeConverter.isLastDay(current, dayOfWeekCurrent)) {
									return true;
								} else {
									return false;
								}
							}
						}

					} else { // Khong phai tuy chon cuoi cung
						if (dayLevel == 0) {// Neu la tuy chon ngay thu level
							return doValidateByDay(currentDay, level);
						} else if (dayLevel == 1) {// Neu la tuy chon cac ngay trong tuan
																				// thu level
							int levelWeekCurrent = current.get(Calendar.WEEK_OF_MONTH);// Tuan
																																					// thu
																																					// may
																																					// trong
																																					// thang
																																					// hien
																																					// tai
							return doValidateByEveryDay(levelWeekCurrent, level);
						} else if (dayLevel == 2) {// Neu la tuy chon 2 ngay cuoi tuan thu
																				// level
							if (dayOfWeekCurrent == 1 || dayOfWeekCurrent == 7) {
								if (dateTimeConverter.isOrder(current, dayOfWeekCurrent, level)) {
									return true;
								} else {
									return false;
								}
							}
						}
					}
				}
			} else {
				return false;
			}
		} else {
			return true;
		}
		return false;
	}

	private boolean doValidateByDay(int currentDay, int level) {
		if (currentDay == level) { // Neu ngay hien tai trung voi quy luat phan cap
			return true;
		} else {
			return false;
		}
	}

	private boolean doValidateByEveryDay(int levelWeekCurrent, int level) {
		if (levelWeekCurrent == level) {
			return true;
		} else {
			return false;
		}
	}

}
