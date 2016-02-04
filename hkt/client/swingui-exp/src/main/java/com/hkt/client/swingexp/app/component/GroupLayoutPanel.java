package com.hkt.client.swingexp.app.component;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

public class GroupLayoutPanel extends JPanel {

  private GroupLayout layout = new GroupLayout(this);
  private ParallelGroup parallelGroupHorizon = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
  private SequentialGroup sequentialGroup = layout.createSequentialGroup();
  private ParallelGroup parallelGroupVertical = layout.createParallelGroup();
  public static final int PREFERRED_SIZE = GroupLayout.PREFERRED_SIZE;
  public static final int DEFAULT_SIZE = GroupLayout.DEFAULT_SIZE;

  public GroupLayoutPanel() {
    setLayout(layout);

  }
  
  public void loadLayout()
  {
	  layout = new GroupLayout(this);
	  parallelGroupHorizon = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
	  sequentialGroup = layout.createSequentialGroup();
	  parallelGroupVertical = layout.createParallelGroup();
	  setLayout(layout);
  }

  public void addComponent(Component comp) {
    
    sequentialGroup.addComponent(comp, PREFERRED_SIZE, PREFERRED_SIZE,
        PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
    parallelGroupVertical.addGroup(sequentialGroup);
    
    layout.setVerticalGroup(parallelGroupVertical);

    parallelGroupHorizon.addComponent(comp, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE);

    layout.setHorizontalGroup(parallelGroupHorizon);
  }
  
  
  
  public void addComponent(Component comp, int size) {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setOpaque(false);
    String str = " ";
    for(int i = 0; i< size; i++){
      str = str +" ";
    }
    panel.add(new JLabel(str), BorderLayout.WEST);
    panel.add(comp, BorderLayout.CENTER);
    sequentialGroup.addComponent(panel, PREFERRED_SIZE, PREFERRED_SIZE,
        PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
    parallelGroupVertical.addGroup(sequentialGroup);
    
    layout.setVerticalGroup(parallelGroupVertical);

    parallelGroupHorizon.addComponent(panel, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE);

    layout.setHorizontalGroup(parallelGroupHorizon);
  }

  public void createBorder() {
    setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
  }
}
