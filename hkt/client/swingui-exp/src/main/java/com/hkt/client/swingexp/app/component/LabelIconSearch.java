
package com.hkt.client.swingexp.app.component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class LabelIconSearch extends JLabel {
  
  public LabelIconSearch() {
    setHorizontalAlignment(SwingConstants.LEFT);
    setIcon(new ImageIcon("src/icon/search.png"));
    setText("");
    setHorizontalTextPosition(SwingConstants.CENTER);
  }
}
