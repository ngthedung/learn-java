package com.hkt.client.swingexp.app.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import com.hkt.client.swingexp.app.component.ButtonExit;
import com.hkt.client.swingexp.app.component.ButtonPrint;

public class PanelShowListInformation extends JPanel implements IPanelShowListInformation {
  
  private JPanel contentPanel, panel_PAGEEND, panel1;
  private JButton btnPrint, btnExit;
  private IPanelShowList iPanelShowList;
  private JPanel panelChild = null;
  public PanelShowListInformation(IPanelShowList content) {
    panelChild = this;
    setName("pnlDanhSachDoiTac");
    this.contentPanel = (JPanel) content;
    this.iPanelShowList = content;
    setLayout(new BorderLayout());
    
    panel1 = new JPanel();
    panel1.setLayout(new BorderLayout());
    addKeyBindings(panel1);
    
    btnPrint = new ButtonPrint();
    btnPrint.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        iPanelShowList.print();
      }
    });
    btnExit = new ButtonExit();
    btnExit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
          JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(panelChild);
          dialog.dispose();
      }
    });
    panel_PAGEEND = new JPanel();
    panel_PAGEEND.setBackground(new Color(255, 255, 255));
    panel_PAGEEND.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
    panel_PAGEEND.add(btnPrint);
    panel_PAGEEND.add(btnExit);
    
    panel1.add(contentPanel, BorderLayout.CENTER);
    panel1.add(panel_PAGEEND, BorderLayout.PAGE_END);
    
    add(panel1, BorderLayout.CENTER);
  }
  
  public void addKeyBindings(JComponent jc) {
    jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "ESC");
    jc.getActionMap().put("ESC", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(panelChild);
        dialog.dispose();
      }
    });
    
  }
  
}
