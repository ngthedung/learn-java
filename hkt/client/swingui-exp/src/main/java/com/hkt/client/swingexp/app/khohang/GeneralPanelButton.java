package com.hkt.client.swingexp.app.khohang;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GeneralPanelButton extends JPanel implements IGeneralButtonPanel {
  public GeneralPanelButton() {
    setLayout(new FlowLayout(FlowLayout.RIGHT));
    add(CreateButton("Save"));
    add(CreateButton("Edit"));
    add(CreateButton("Clear"));
    add(CreateButton("Cancel"));
  }

  public JButton CreateButton(String name) {
    JButton button = new JButton(name);
    return button;
  }

  @Override
  public void save() {
    // TODO Auto-generated method stub

  }

  @Override
  public void edit() {
    // TODO Auto-generated method stub

  }

  @Override
  public void delete() {
    // TODO Auto-generated method stub

  }

  @Override
  public void exit() {
    // TODO Auto-generated method stub

  }

  @Override
  public void clear() {
    // TODO Auto-generated method stub

  }
}
