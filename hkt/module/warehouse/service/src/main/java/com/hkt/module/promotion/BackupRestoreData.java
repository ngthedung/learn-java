package com.hkt.module.promotion;


public class BackupRestoreData {

  public BackupRestoreData() {
  }

  public boolean tbBackupWin(String dbName, String dbUserName, String dbPassword, String path) {
    String hkt = 
        HKTFile.getDirectory("Database");
    // String hkt = "C:\Users\longnt\HKT Software 4.0\Database\mysql";

    path = "\"" + path + "\"";
      hkt = "\"" + hkt+ "/mysql/bin/mysqldump"+ "\"";
    

    String executeCmd = hkt + " -u" + dbUserName + " -p" + dbPassword + " --add-drop-database -B " + dbName + " -r "
        + path;
    Process runtimeProcess;
    try {
      runtimeProcess = Runtime.getRuntime().exec(executeCmd);
      new Thread(new SyncPipe(runtimeProcess.getErrorStream(), System.err)).start();
      new Thread(new SyncPipe(runtimeProcess.getInputStream(), System.out)).start();
      int processComplete = runtimeProcess.waitFor();
      if (processComplete == 0) {
        System.out.println("Backup created successfully");
        return true;
      } else {
        System.out.println("Could not create the backup");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }

  public boolean tbBackupLinux(String dbName, String dbUserName, String dbPassword, String path) {
    String hkt = "mysql";
    // String hkt = "C:/xampp/mysql/bin/mysqldump";
    path = "\"" + path + "\"";

    String executeCmd = hkt + " -u" + dbUserName + " -p" + dbPassword + " --add-drop-database -B " + dbName + " -r "
        + path;
    Process runtimeProcess;
    try {
      runtimeProcess = Runtime.getRuntime().exec(executeCmd);
      new Thread(new SyncPipe(runtimeProcess.getErrorStream(), System.err)).start();
      new Thread(new SyncPipe(runtimeProcess.getInputStream(), System.out)).start();
      int processComplete = runtimeProcess.waitFor();
      if (processComplete == 0) {
        System.out.println("Backup created successfully");
        return true;
      } else {
        System.out.println("Could not create the backup");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }

  public static boolean restoreDBWin(String dbName, String dbUserName, String dbPassword, String source) {
    String hkt = HKTFile.getDirectory("Database");
    // String hkt = "C:/xampp/mysql/bin/mysql";
    // String executeCmd = hkt + " -u" + dbUserName+ " -p" + dbPassword+" "+
    // dbName+ " < " + source ;
    source = "\"" + source + "\"";
//    if (hkt.indexOf(" ") > 0) {
      hkt = hkt+"/mysql/bin/mysql";
//    } else {
//      hkt = hkt+ "/mysql/bin/mysql";
//    }

    System.out.println(hkt);
    String executeCmd = hkt + " -u" + dbUserName + " -p" + dbPassword + " " + dbName + " < " + source;
    System.out.println(executeCmd);
    Process runtimeProcess;
    try {
      runtimeProcess = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/c", executeCmd });
      new Thread(new SyncPipe(runtimeProcess.getErrorStream(), System.err)).start();
      new Thread(new SyncPipe(runtimeProcess.getInputStream(), System.out)).start();
      int processComplete = runtimeProcess.waitFor();
      if (processComplete == 0) {
        System.out.println("Backup restored successfully");
        return true;
      } else {
        System.out.println("Could not restore the backup");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }

  public static boolean restoreDBLinux(String dbName, String dbUserName, String dbPassword, String source) {
    String hkt = "mysql";
    // String hkt = "C:/xampp/mysql/bin/mysql";
    // String executeCmd = hkt + " -u" + dbUserName+ " -p" + dbPassword+" "+
    // dbName+ " < " + source ;
    String executeCmd = hkt + " -u" + dbUserName + " -p" + dbPassword + " " + dbName + " < " + source;
    Process runtimeProcess;
    try {
      runtimeProcess = Runtime.getRuntime().exec(new String[] { executeCmd });
      new Thread(new SyncPipe(runtimeProcess.getErrorStream(), System.err)).start();
      new Thread(new SyncPipe(runtimeProcess.getInputStream(), System.out)).start();
      int processComplete = runtimeProcess.waitFor();
      if (processComplete == 0) {
        System.out.println("Backup restored successfully");
        return true;
      } else {
        System.out.println("Could not restore the backup");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }

  public static void main(String[] args) {
//    BackupRestoreData bb = new BackupRestoreData();
    // bb.tbBackup("hktdb", "root", "123456", "D:/dataBackup.sql");
    
    String executeCmd = "C:\\xampp\\mysql\\bin\\mysqldump -u root -proot --add-drop-database -B hktdb -r C:\\Users\\HKTC\\Desktop\\data.sql";
    System.out.println(executeCmd);
    Process runtimeProcess;
    try {
      runtimeProcess = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/c", executeCmd });
      new Thread(new SyncPipe(runtimeProcess.getErrorStream(), System.err)).start();
      new Thread(new SyncPipe(runtimeProcess.getInputStream(), System.out)).start();
      int processComplete = runtimeProcess.waitFor();
      if (processComplete == 0) {
        System.out.println("Backup restored successfully");
      } else {
        System.out.println("Could not restore the backup");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
