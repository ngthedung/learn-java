package com.hkt.client.swingexp.app.license;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.filechooser.FileSystemView;

import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.module.promotion.CheckOS;

public class ProductId {
  private static String sn;
  private static ProductId productId;

  public static ProductId getInstance() {
    if (productId == null) {
      productId = new ProductId();
    }
    return productId;
  }

  private ProductId() {
  }

  // /**
  // * lấy mã máy theo tên ổ
  // */
  // public static String getSerialNumber(String drive) {
  // String result = "";
  // if (CheckOS.isWindows()) {
  // try {
  // File file = File.createTempFile("realhowto", ".vbs");
  // file.deleteOnExit();
  // FileWriter fw = new java.io.FileWriter(file);
  //
  // String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
  // + "Set colDrives = objFSO.Drives\n"
  // + "Set objDrive = colDrives.item(\"" + drive + "\")\n" +
  // "Wscript.Echo objDrive.SerialNumber"; // see
  // // note
  // fw.write(vbs);
  // fw.close();
  // Process p = Runtime.getRuntime().exec("cscript //NoLogo " +
  // file.getPath());
  // BufferedReader input = new BufferedReader(new
  // InputStreamReader(p.getInputStream()));
  // String line;
  // while ((line = input.readLine()) != null) {
  // result += line;
  // }
  // input.close();
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  // } else if (CheckOS.isUnix()) {
  // result = RunCmd.getInstance().getLinuxSerial();
  // }
  // return result.trim();
  // }

  public String getserialNumber() {
    String sn = null;
    if (CheckOS.isWindows()) {
        HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set objWMIService = GetObject(\"winmgmts:\" & \"{impersonationLevel=impersonate}!\\\\\" & \".\" & \"\\root\\cimv2\") \n"
                    + "Set colDiskDrives = objWMIService.ExecQuery (\"Select * from Win32_DiskDrive where interfacetype like 'USB'\")\n"
                    + "Set objWMIService = GetObject(\"winmgmts:\\\\\" & strComputer & \"\\root\\cimv2\")\n"
                    + "For each objDiskDrive in colDiskDrives\n"
                    + "'Wscript.Echo \"Signature: \" & vbTab &  objDiskDrive.Signature\n"
                    + "sernum = Mid(sernum, InStrRev(sernum, \"\\\") + 1)\n"
                    + "sernum = Left(sernum, InStr(sernum, \"&\") - 1)"
                    + "wscript.echo sernum"
                    + "Next";
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            int k = 0;
            while ((line = input.readLine()) != null) {
                hashMap.put(k, line);
                k++;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File[] f = File.listRoots();
        int j = 0;
        for (int i = 0; i < f.length; i++) {
            if (fsv.getSystemDisplayName(f[i]).startsWith("HKT")) {
                sn = hashMap.get(j);
            }
            if (fsv.getSystemTypeDescription(f[i]).startsWith("Removable Disk")) {
                j++;
            }
        }
    } else if (CheckOS.isUnix()) {
        sn = RunCmd.getInstance().getLinuxSerial();
    }
    if (sn.length() > 8) {
        sn = sn.substring(0, 7);
    }
    return new Base32().byteArray_2_String32(sn.getBytes());
}

public String getSerialWindow() {
    String sn = "";
    String sn1 = "";
    if (CheckOS.isWindows()) {
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "On Error Resume Next \n"
                    + "strComputer = \".\"\n"
                    + "Set objWMIService = GetObject(\"winmgmts:\\\\\" & strComputer & \"\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery(\"Select * from Win32_PhysicalMemory\",,48)\n"
                    + "For Each objItem in colItems\n"
                    + "Wscript.Echo objItem.Capacity\n"
                    + "Next";
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                sn = sn + line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "On Error Resume Next \n"
                    + "strComputer = \".\"\n"
                    + "Set objWMIService = GetObject(\"winmgmts:\\\\\" & strComputer & \"\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery(\"Select * from Win32_Processor\")\n"
                    + "For Each objItem in colItems \n"
                    + "Wscript.Echo objItem.Revision \n"
                    + "Next";
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                sn1 = sn1 + line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sn1 = sn1 + sn;

    } else if (CheckOS.isUnix()) {
        sn1 = RunCmd.getInstance().getLinuxSerial();
    }
//    if (sn1.length() > 8) {
//        sn1 = sn1.substring(0, 7);
//    }
    //GU2DSOBUGI2TIOJWG2ZDSNQ7
    //System.out.println("sn1 " + sn1);
    String str = new Base32().byteArray_2_String32(getKey(sn1).getBytes());
    if (str.length() > 14) {
        str = str.substring(0, 14);
    }
    return str;
}

private String getKey(String str) {
    String row[] = str.split("");
    String c = "";
    double a = 1;
    double b = 1;
    for (int i = 0; i < row.length; i++) {
        try {
            if (Double.parseDouble(row[i]) != 0) {
                a = a * Double.parseDouble(row[i]);
                b = b + Double.parseDouble(row[i]);
            }
        } catch (Exception e) {
        }
    }
    c = String.valueOf(a * b);
    if (c.length() < 10) {
        for (int i = c.length(); i < 10; i++) {
            c = c + String.valueOf(i);
        }
    }
    return c;
}

  private static String getSerialLinux() {
    if (sn != null) {
      return sn;
    }

    OutputStream os = null;
    InputStream is = null;

    Runtime runtime = Runtime.getRuntime();
    Process process = null;
    try {
      process = runtime.exec("/sbin/udevadm info --query=property --name=sda");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    os = process.getOutputStream();
    is = process.getInputStream();

    try {
      os.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    String line = null;
    String marker = "ID_SERIAL=";
    try {
      while ((line = br.readLine()) != null) {
        if (line.indexOf(marker) != -1) {
          sn = line.split(marker)[1].trim();
          break;
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        is.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    if (sn == null) {
      throw new RuntimeException("Cannot find computer SN");
    }

    return sn;
  }

}
