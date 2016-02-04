package com.hkt.client.swingexp.app.convertdata.excel;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FileFilterExtension extends FileFilter {
  private String[] extansion;

  public FileFilterExtension(String[] extansion) {
    this.extansion = extansion;
  }

  @Override
  public boolean accept(File f) {
    if (f.isDirectory()) {
      return true;
    }
    String name = f.getName().toLowerCase();
    for (String ext : extansion) {
      if (name.endsWith(ext)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String getDescription() {
    return "";
  }
  
}
