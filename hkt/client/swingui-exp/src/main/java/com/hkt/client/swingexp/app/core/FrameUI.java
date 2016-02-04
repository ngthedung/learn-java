package com.hkt.client.swingexp.app.core;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.matcher.JButtonMatcher;
import org.fest.swing.fixture.ContainerFixture;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JButtonFixture;
import org.fest.swing.fixture.JCheckBoxFixture;
import org.fest.swing.fixture.JComboBoxFixture;
import org.fest.swing.fixture.JLabelFixture;
import org.fest.swing.fixture.JListFixture;
import org.fest.swing.fixture.JRadioButtonFixture;
import org.fest.swing.fixture.JTabbedPaneFixture;
import org.fest.swing.fixture.JTableFixture;

import com.hkt.client.swingexp.app.component.MyPanel;

public class FrameUI {
  private static FrameUI frameUI;
  
  public static final FrameUI getInstance(){
    if(frameUI==null){
      frameUI = new FrameUI(MyFrame.getInstance());
    }
    return frameUI;
  }
  
  private FrameFixture fmatcher;

  public FrameFixture getFmatcher() {
    return fmatcher;
  }

  public FrameUI(Frame frame) {
    this.fmatcher = new FrameFixture(frame);
  }

  public ContainerUI panel(String name) {
    return new ContainerUI(fmatcher.panel(name));
  }

  public void delayOK() {
    try {
      Thread.sleep(2000);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
  
  public void delay() {
	    try {
	      Thread.sleep(1000);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }

	  }

  public boolean showDialogTest(String name) {
    if(name.toLowerCase().indexOf("lỗi")>=0){
      return false;
    }
    DialogTest.getInstance().show(name);
    if (!DialogTest.getInstance().isTest()) {
      fmatcher.cleanUp();
      MyFrame.getInstance().setVisible(true);
      MyPanel.isTest= false;
      return false;
    }else {
      return true;
    }
  }

  public ContainerUI panel(final Class<? extends JPanel> type) {
    GenericTypeMatcher<JPanel> matcher = new GenericTypeMatcher<JPanel>(JPanel.class) {
      protected boolean isMatching(JPanel component) {
        if (!type.isInstance(component))
          return false;
        return true;
      }
    };
    return new ContainerUI(fmatcher.panel(matcher));
  }

  public ContainerUI dialog(String name) {
    return new ContainerUI(fmatcher.dialog(name));
  }

  public TableUI table(String name) {
    return new TableUI(fmatcher.table(name));
  }

  public LableUI label() {
    return new LableUI(fmatcher.label());
  }

  public LableUI label(String name) {
    return new LableUI(fmatcher.label(name));
  }

  public ButtonUI button() {
    return new ButtonUI(fmatcher.button());
  }

  public ButtonUI button(String name) {
    return new ButtonUI(fmatcher.button(name));
  }

  public ListUI list() {
    return new ListUI(fmatcher.list());
  }

  public ListUI list(String name) {
    return new ListUI(fmatcher.list(name));
  }

  public CheckBoxUI checkBox() {
    return new CheckBoxUI(fmatcher.checkBox());
  }

  public CheckBoxUI checkBox(String name) {
    return new CheckBoxUI(fmatcher.checkBox(name));
  }

  public RadioButtonUI radioButton() {
    return new RadioButtonUI(fmatcher.radioButton());
  }

  public RadioButtonUI radioButton(String name) {
    return new RadioButtonUI(fmatcher.radioButton(name));
  }

  public ComboBoxUI comboBox() {
    return new ComboBoxUI(fmatcher.comboBox());
  }

  public ComboBoxUI comboBox(String name) {
    return new ComboBoxUI(fmatcher.comboBox(name));
  }

  public TableUI table() {
    return new TableUI(fmatcher.table());
  }

  public TabPaneUI tabbedPane(String name) {
    return new TabPaneUI(fmatcher.tabbedPane(name));
  }

  public TabPaneUI tabbedPane() {
    return new TabPaneUI(fmatcher.tabbedPane());
  }

  public ContainerUI dialog(final Class<? extends JDialog> type) {
    GenericTypeMatcher<JDialog> matcher = new GenericTypeMatcher<JDialog>(JDialog.class) {
      protected boolean isMatching(JDialog component) {
        if (!type.isInstance(component))
          return false;
        return true;
      }
    };
    return new ContainerUI(fmatcher.dialog(matcher));
  }

  public void destroy() {
    fmatcher.robot.cleanUpWithoutDisposingWindows();
  }

  static public class ContainerUI {
    ContainerFixture<?> matcher;
    

    public ContainerUI(ContainerFixture<?> matcher) {
      this.matcher = matcher;
    }

    public void createDataTest() {
      ((MyPanel)matcher.component()).createDataTest();
    }
    
    public void createBeginTest() {
      ((MyPanel)matcher.component()).deleteDataTest();
      ((MyPanel)matcher.component()).createBeginTest();
    }
    
    public void deleteDataTest() {
      ((MyPanel)matcher.component()).deleteDataTest();
    }

    public ContainerUI panel(String name) {
      return new ContainerUI(matcher.panel(name));
    }

    public TableUI table(String name) {
      return new TableUI(matcher.table(name));
    }

    public CheckBoxUI checkBox(String name) {
      return new CheckBoxUI(matcher.checkBox(name));
    }

    public RadioButtonUI radioButton(String name) {
      return new RadioButtonUI(matcher.radioButton(name));
    }

    public ButtonUI button(String name) {
      return new ButtonUI(matcher.button(name));
    }
    
    public LableUI label(String name) {
      return new LableUI(matcher.label(name));
    }

    public ComboBoxUI comboBox(String name) {
      return new ComboBoxUI(matcher.comboBox(name));
    }

    public TabPaneUI tabbedPane(String name) {
      return new TabPaneUI(matcher.tabbedPane(name));
    }

    public ListUI list(String name) {
      return new ListUI(matcher.list(name));
    }

    public ContainerUI buttonClick(String text) {
      matcher.button(JButtonMatcher.withText(text)).click();
      return this;
    }
    
    public ContainerUI buttonClickByName(String name) {
      matcher.button(JButtonMatcher.withName(name)).click();
      return this;
    }

    public ContainerUI fieldSet(String name, String text) {
      matcher.textBox(name).setText(text);
      return this;
    }
    
    public ContainerUI textClick(String name) {
      matcher.textBox(name).click();
      return this;
    }

    public ContainerUI dialogClose() {
      DialogFixture dFixture = (DialogFixture) matcher;
      dFixture.close();
      return this;
    }
  }

  static public class TableUI {
    JTableFixture matcher;

    public TableUI(JTableFixture matcher) {
      this.matcher = matcher;
    }

    public void doubleClickRow(String text) {
      matcher.selectCells(matcher.cell(text)).rightClick();
    }

    public TableUI selectRow(String text) {
      matcher.selectCells(matcher.cell(text));
      return this;
    }
  }

  static public class LableUI {
    JLabelFixture matcher;

    public LableUI(JLabelFixture matcher) {
      this.matcher = matcher;
    }

    public LableUI mouseClick() {
      matcher.click();
      return this;
    }
  }

  static public class ButtonUI {
    JButtonFixture matcher;

    public ButtonUI(JButtonFixture matcher) {
      this.matcher = matcher;
    }

    public ButtonUI mouseClick() {
      matcher.click();
      return this;
    }
  }

  static public class CheckBoxUI {
    JCheckBoxFixture matcher;

    public CheckBoxUI(JCheckBoxFixture matcher) {
      this.matcher = matcher;
    }

    public CheckBoxUI mouseClick() {
      matcher.click();
      return this;
    }
  }

  static public class RadioButtonUI {
    JRadioButtonFixture matcher;

    public RadioButtonUI(JRadioButtonFixture matcher) {
      this.matcher = matcher;
    }

    public RadioButtonUI mouseClick() {
      matcher.click();
      return this;
    }
  }

  static public class ComboBoxUI {
    JComboBoxFixture matcher;

    public ComboBoxUI(JComboBoxFixture matcher) {
      this.matcher = matcher;
    }

    public ComboBoxUI mouseClick() {
      matcher.click();
      return this;
    }

    public ComboBoxUI selectItem(String item) {
      matcher.selectItem(item);
      return this;
    }

  }

  static public class ListUI {
    JListFixture matcher;

    public ListUI(JListFixture matcher) {
      this.matcher = matcher;
    }

    public ListUI clickItem(String text) {
      matcher.clickItem(text);
      return this;
    }
  }

  static public class TabPaneUI {
    JTabbedPaneFixture matcher;

    public TabPaneUI(JTabbedPaneFixture matcher) {
      this.matcher = matcher;
    }

    public TabPaneUI clickTab(String text) {
      matcher.selectTab(text).click();
      return this;
    }
  }
}
