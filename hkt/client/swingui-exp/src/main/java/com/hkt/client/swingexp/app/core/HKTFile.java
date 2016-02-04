package com.hkt.client.swingexp.app.core;

import java.io.File;

import com.hkt.util.FileUtil;

public class HKTFile {
	private static String appName = "HKTSoft4.0";

	private static String defaultDirectory() {
		String OS = System.getProperty("os.name").toUpperCase();
		if (OS.contains("WIN")) {
			return "C:";
		} else if (OS.contains("MAC")) {
			return System.getProperty("user.home") + "/Library/Application " + "Support";
		} else if (OS.contains("NUX")) {
			return System.getProperty("user.home");
		}
		return System.getProperty("user.dir");
	}

	public static File getFile(String module, String nameFile) {
		String directory = defaultDirectory() + File.separator + appName + File.separator + module;
		if (!new File(directory).exists()) {
			new File(directory).mkdirs();
		}
		String path = directory + File.separator + nameFile;
		return new File(path);
	}

	public static String getDirectory(String module) {
		String directory = defaultDirectory() + File.separator + appName + File.separator + module;
		if (!new File(directory).exists()) {
			new File(directory).mkdirs();
		}
		return directory;
	}

	public static void deleteFilesForFolder(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			try {
				try {
	        FileUtil.removeIfExist(fileEntry.getAbsolutePath());
        } catch (Exception e) {
        }
			} catch (Exception e) {
			}

		}
	}

	public String getFilesFolder(final File folder) {
		try {
			return folder.listFiles()[0].getName();
		} catch (Exception e) {
			return "";
		}

	}
}
