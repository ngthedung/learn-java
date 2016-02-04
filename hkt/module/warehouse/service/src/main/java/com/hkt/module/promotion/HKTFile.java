package com.hkt.module.promotion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class HKTFile {

  private static String appName = "HKTSoft4.0";

  private static String defaultDirectory() {
      String OS = System.getProperty("os.name").toUpperCase();
      if (OS.contains("WIN")) {
          return "C:";
      } else if (OS.contains("MAC")) {
          return System.getProperty("user.home") + "/Library/Application "
                  + "Support";
      } else if (OS.contains("NUX")) {
          return System.getProperty("user.home");
      }
      return System.getProperty("user.dir");
  }

  public static File getFile(String module, String nameFile) {
      String directory = defaultDirectory() + File.separator
              + appName + File.separator + module;
      if (!new File(directory).exists()) {
          new File(directory).mkdirs();
      }
      String path = directory + File.separator + nameFile;
      return new File(path);
  }

  public static String getDirectory(String module) {
      String directory = defaultDirectory() + File.separator
              + appName + File.separator + module;
      if (!new File(directory).exists()) {
          new File(directory).mkdirs();
      }
      return directory;
  }

  public InputStream getInputStream(URL url) {
      try {
          return url.openConnection().getInputStream();
      } catch (Exception ex) {
          return null;
      }
  }

  public void copy(URL url, String filePathDestination) {
      try {
          URLConnection urlc = url.openConnection();
          InputStream is = urlc.getInputStream();
          OutputStream os = new FileOutputStream(new File(filePathDestination));
          copyFile(is, os);
      } catch (Exception ex) {
          ex.printStackTrace();
      }
  }

  private void copyFile(InputStream inputStream, OutputStream outputStream) {
      try {
          byte[] bs = new byte[1024];
          int n = inputStream.read(bs);
          while (n > 0) {
              outputStream.write(bs);
              n = inputStream.read(bs);
          }
      } catch (Exception ex) {
          ex.printStackTrace();
      } finally {
          try {
              inputStream.close();
              outputStream.close();
          } catch (IOException ex) {
              ex.printStackTrace();
          }
      }
  }
}
