package com.hkt.client.swingexp.app.core;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.account.entity.Profile;

@SuppressWarnings("serial")
public class PanelSortColumnTable extends JPanel implements IDialogResto {

  private List<JCheckBox> listCheckBox = new ArrayList<JCheckBox>();
  private List<String> listSelect = new ArrayList<String>();
  private JPanel panel = new JPanel();
  private JScrollPane scrollPane = new JScrollPane();
  private boolean flagSort = false;
  private int h = 12;
  int y = 12;
  private Profile profileTable;
  private String name;
  private List<String> listConfig = new ArrayList<String>();

  public List<String> getListSelect() {
    return listSelect;
  }

  public PanelSortColumnTable(List<String> list, String nameModel) {
    this.name = nameModel;
    setOpaque(false);
    panel.setLayout(null);
    boolean flagCheckBox = false;
    int index = 0;
    profileTable = AccountModelManager.getInstance().getProfileConfigTable();
    try {
      if (profileTable.get(nameModel) != null) {
        listConfig = (List<String>) profileTable.get(nameModel);
      }
    } catch (Exception e) {
    }
    for (String bean : list) {
      JCheckBox checkBoxProductGroup = new JCheckBox(bean);
      checkBoxProductGroup.setName(checkBoxProductGroup.getText());
      checkBoxProductGroup.setOpaque(false);
      if (listConfig.indexOf(bean) >= 0) {
        checkBoxProductGroup.setSelected(false);
        listSelect.add(bean);
      } else {
        checkBoxProductGroup.setSelected(true);
       
      }

      if (index < 3) {
        checkBoxProductGroup.setEnabled(false);
      }
      checkBoxProductGroup.addItemListener(new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
          JCheckBox jcb = (JCheckBox) e.getSource();
          if (!jcb.isSelected()) {
            listSelect.add(jcb.getText());
          } else {
            for (int i = 0; i < listSelect.size(); i++) {
              if (listSelect.get(i).equals(jcb.getText())) {
                listSelect.remove(i);
                break;
              }
            }
          }
        }
      });
      listCheckBox.add(checkBoxProductGroup);
      panel.add(checkBoxProductGroup);
      index++;
      if (!flagCheckBox) {
        if (index < 10) {
          h = h + 50;
        } else {
          if (index == 11) {
            h = h + 15;
          }
        }
        checkBoxProductGroup.setBounds(54, y, 180, 23);
      } else {
        checkBoxProductGroup.setBounds(285, y, 180, 23);
        y = y + 40;
      }
      flagCheckBox = !flagCheckBox;

    }
    if (listCheckBox.size() > 12) {
      setLayout(new GridLayout(1, 1));
      add(scrollPane);
      panel.setOpaque(false);
      scrollPane.getViewport().setOpaque(false);
      scrollPane.setOpaque(false);
      panel.setSize(new Dimension(getWidth() - 50, y));
      panel.setPreferredSize(new Dimension(getWidth() - 50, y));
      scrollPane.setViewportView(panel);
    } else {
      setLayout(new GridLayout(1, 1));
      add(panel);
      panel.setOpaque(false);

    }

  }

  public int getH() {
    return h + 200;
  }

  public boolean isFlagSort() {
    return flagSort;
  }

  @Override
  public void Save() throws Exception {
    flagSort = true;
    profileTable.put(name, listSelect);
    AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getLoginIdAdmin(),
        profileTable);
    ((JDialog) this.getRootPane().getParent()).dispose();
  }

}
