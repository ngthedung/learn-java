package com.hkt.client.swingexp.app.convertdata.excel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class ConvertDataExcel extends JDialog {
  private JButton btnChooseFile, btnDoConvert;
  private BackupTool backupTool;

  public ConvertDataExcel(java.awt.Frame parent, boolean modal) {
    setSize(300, 80);
    add(new MainUI());
  }

  private class MainUI extends JPanel {
    public MainUI() {
      setLayout(new FlowLayout(FlowLayout.LEADING));
      setBackground(Color.white);
      btnChooseFile = new JButton("Choose file for convert");
      btnChooseFile.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          doImportFromFile();

        }
      });
      add(btnChooseFile);
      btnDoConvert = new JButton("Convert");
      add(btnDoConvert);
      
      backupTool = new BackupTool();
    }
  }

  private void doImportFromFile() {
    int choose = JOptionPane.showConfirmDialog(null, "Đồng ý để tiếp tục!", "Khôi phục hệ thống",
        JOptionPane.YES_NO_OPTION);
    if (choose == JOptionPane.YES_OPTION) {
      JFileChooser fileChooser = new JFileChooser();
      String[] extension = {".xlsx", ".xls"};
      fileChooser.setFileFilter(new FileFilterExtension(extension));
      int i = fileChooser.showOpenDialog(this);
      if (i == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        final String fileURL = file.getAbsolutePath();
        try {
          new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
              backupTool.runImport(fileURL);
              return null;
            };

            @Override
            protected void done() {
              JOptionPane.showMessageDialog(null, "Thành công!");
            };
          }.execute();

        } catch (Exception ex) {
          this.setEnabled(true);
          JOptionPane.showMessageDialog(null, ex);
          JOptionPane.showMessageDialog(null, "Failed!");
        }
      }
    }
  }

  public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(new Runnable() {

      public void run() {
        ConvertDataExcel dialog = new ConvertDataExcel(new javax.swing.JFrame(), true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {

          @Override
          public void windowClosing(java.awt.event.WindowEvent e) {
            System.exit(0);
          }
        });
        dialog.setVisible(true);
      }
    });
  }
}
