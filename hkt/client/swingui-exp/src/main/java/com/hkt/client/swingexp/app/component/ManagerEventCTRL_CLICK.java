package com.hkt.client.swingexp.app.component;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.core.IDialogInformation;

/**
 * Quản lý các sự kiện control - click
 * 
 * @author BinLe
 */
public class ManagerEventCTRL_CLICK {
  
  private static ManagerEventCTRL_CLICK managerEventCTRL_CLICK;
  private boolean ctrlPress = false;
  
  public static ManagerEventCTRL_CLICK getInstance() {
    if (managerEventCTRL_CLICK == null) {
      managerEventCTRL_CLICK = new ManagerEventCTRL_CLICK();
    }
    return managerEventCTRL_CLICK;
  }
  
  private ManagerEventCTRL_CLICK() {
  }
  
  /*
   * thêm sự kiện với combobox và dialog tương ứng
   */
  
  public void addEvent(final IDialogInformation dialog, final JComboBox cb) {
    KeyAdapter keyAdapter = new KeyAdapter() {
      
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
          ctrlPress = true;
        }
      }
      
      @Override
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
          ctrlPress = false;
        }
      }
    };
    MouseAdapter mouseAdapter = new MouseAdapter() {
      
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() < 2 || !ctrlPress) {
          return;
        }
        if (dialog == null) {
          JOptionPane.showMessageDialog(null, "Không phát hiện giao diện cần trong hệ thống");
          return;
        }
        dialog.displayed();
        dialog.setVisible(true);
        ctrlPress = false;
      }
    };
    cb.addKeyListener(keyAdapter);
    cb.addMouseListener(mouseAdapter);
    
    ((JTextField) cb.getEditor().getEditorComponent()).addKeyListener(keyAdapter);
    ((JTextField) cb.getEditor().getEditorComponent()).addMouseListener(mouseAdapter);
    
  }
  
  public void addEvent(final IDialogInformation dialog, final JTextField textField) {
    KeyAdapter keyAdapter = new KeyAdapter() {
      
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
          ctrlPress = true;
        }
      }
      
      @Override
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
          ctrlPress = false;
        }
      }
    };
    MouseAdapter mouseAdapter = new MouseAdapter() {
      
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() < 2 || !ctrlPress) {
          return;
        }
        if (dialog == null) {
          JOptionPane.showMessageDialog(null, "Không phát hiện giao diện cần trong hệ thống");
          return;
        }
        dialog.displayed();
        dialog.setVisible(true);
        ctrlPress = false;
      }
    };
    textField.addKeyListener(keyAdapter);
    textField.addMouseListener(mouseAdapter);
    
  }
}
