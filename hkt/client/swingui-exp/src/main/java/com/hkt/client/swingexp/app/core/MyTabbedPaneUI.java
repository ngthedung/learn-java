package com.hkt.client.swingexp.app.core;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.plaf.synth.SynthContext;

//public class MyTabbedPaneUI extends BasicTabbedPaneUI {
//

//  private int          inclTab       = 4;
//  private int          anchoFocoH    = 4;
//  private int          anchoCarpetas = 18;
//  private Polygon      shape;
//  private final Insets borderInsets  = new Insets(0, 0, 0, 0);
//
//  public static ComponentUI createUI(JComponent c) {
//    return new MyTabbedPaneUI();
//  }
//
//  @Override
//  protected void installDefaults() {
//    super.installDefaults();
//    selectColor = new Color(250, 192, 192);
//    deSelectColor = new Color(197, 193, 168);
//    tabAreaInsets.right = anchoCarpetas;
//  }
//
//  @Override
//  protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
//  }
//
//  @Override
//  protected Insets getContentBorderInsets(int tabPlacement) {
//    return borderInsets;
//  }
//  
//  @Override
//  protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
//    g.setColor(Color.BLACK);
//    g.drawLine(x, y, x, y + h);
//    g.drawLine(x, y, x + w - (h / 2), y);
////    g.drawLine(x + w - (h / 2), y, x + w + (h / 2), y + h);
//
//    if (isSelected) {
//        g.setColor(Color.BLACK); 
////        g.drawLine(x + 1, y + 1, x + 1, y + h);
////        g.drawLine(x + 1, y + 1, x + w - (h / 2), y + 1);
//        g.drawLine(x + 25, y + 25, x + w - (h - 4), y + 25);
////        g.drawLine(x + w - (h / 2), y + 1, x + w + (h / 2), y + h);
//
////        g.setColor(shadow);
////        g.drawLine(x + w - (h / 2), y + 1, x + w + (h / 2) - 1, y + h);
//    }
//}
//

//

//

//  
  
//
//  @Override
//  protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
//    return 20 + inclTab + super.calculateTabWidth(tabPlacement, tabIndex, metrics);
//  }
//
//  @Override
//  protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
//    if (tabPlacement == LEFT || tabPlacement == RIGHT) {
//      return super.calculateTabHeight(tabPlacement, tabIndex, fontHeight);
//    } else {
//      return anchoFocoH + super.calculateTabHeight(tabPlacement, tabIndex, fontHeight);
//    }
//  }
//
//  @Override
//  protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect,
//      boolean isSelected) {
//    // if (tabPane.hasFocus() && isSelected) {
//    // g.setColor(UIManager.getColor("ScrollBar.thumbShadow"));
//    // g.drawPolygon(shape);
//    // }
//  }
//
//  protected Color hazAlfa(int fila) {
//    int alfa = 0;
//    if (fila >= 0) {
//      alfa = 50 + (fila > 7 ? 70 : 10 * fila);
//    }
//    return new Color(0, 0, 0, alfa);
//  }
//
//}



public class MyTabbedPaneUI extends BasicTabbedPaneUI {
  //protected void paintContentBorder(Graphics g,int tabPlacement,int selectedIndex){}  
//private Color        selectColor;
//private Color        deSelectColor;
//  private int          inclTab       = 4;
//private int          anchoFocoH    = 4;
//private int          anchoCarpetas = 18;
//private Polygon      shape;
//private final Insets borderInsets  = new Insets(0, 0, 0, 0);
//protected Color hazAlfa(int fila) {
//int alfa = 0;
//if (fila >= 0) {
//  alfa = 50 + (fila > 7 ? 70 : 10 * fila);
//}
//return new Color(0, 0, 0, alfa);
//}
//@Override
//protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
//g.setColor(Color.BLACK);
//g.drawLine(x, y, x, y + h);
//g.drawLine(x, y, x + w - (h / 2), y);
//g.drawLine(x + w - (h / 2), y, x + w + (h / 2), y + h);
//
//if (isSelected) {
//    g.setColor(Color.BLACK); 
//    g.drawLine(x + 1, y + 1, x + 1, y + h);
//    g.drawLine(x + 1, y + 1, x + w - (h / 2), y + 1);
//    g.drawLine(x + 25, y + 25, x + w - (h - 4), y + 25);
//    g.drawLine(x + w - (h / 2), y + 1, x + w + (h / 2), y + h);
//
//    g.setColor(shadow);
//    g.drawLine(x + w - (h / 2), y + 1, x + w + (h / 2) - 1, y + h);
//}
//}
//@Override
//protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
//  Graphics2D g2D = (Graphics2D) g;
//  GradientPaint gradientShadow;
//  int xp[] = null; // Para la forma
//  int yp[] = null;
//  switch (tabPlacement) {
//    case LEFT:
//      xp = new int[] { x, x, x + w, x + w, x };
//      yp = new int[] { y, y + h - 3, y + h - 3, y, y };
//      gradientShadow = new GradientPaint(x, y, new Color(100, 100, 255), x, y + h, Color.ORANGE);
//      break;
//    case RIGHT:
//      xp = new int[] { x, x, x + w - 2, x + w - 2, x };
//      yp = new int[] { y, y + h - 3, y + h - 3, y, y };
//      gradientShadow = new GradientPaint(x, y, new Color(100, 100, 255), x, y + h, new Color(153, 186, 243));
//      break;
//    case BOTTOM:
//      xp = new int[] { x, x, x + 3, x + w - inclTab - 6, x + w - inclTab - 2, x + w - inclTab, x + w - 3, x };
//      yp = new int[] { y, y + h - 3, y + h, y + h, y + h - 1, y + h - 3, y, y };
//      gradientShadow = new GradientPaint(x, y, new Color(100, 100, 255), x, y + h, Color.BLUE);
//      break;
//    case TOP:
//    default:
//      xp = new int[] { x, x, x + 3, x + w - inclTab - 6, x + w - inclTab - 2, x + w - inclTab, x + w - inclTab, x };
//      yp = new int[] { y + h, y + 3, y, y, y + 1, y + 3, y + h, y + h };
//      gradientShadow = new GradientPaint(0, 0, Color.ORANGE, 0, y + h / 2, new Color(240, 255, 210));
//      break;
//  }
//  // ;
//  shape = new Polygon(xp, yp, xp.length);
//  if (isSelected) {
//    g2D.setColor(selectColor);
//    g2D.setPaint(gradientShadow);
//  } else {
//    if (tabPane.isEnabled() && tabPane.isEnabledAt(tabIndex)) {
//      g2D.setColor(deSelectColor);
//      GradientPaint gradientShadowTmp = new GradientPaint(0, 0, new Color(255, 255, 200), 0, y + h / 2, new Color(240, 255, 210));
//      g2D.setPaint(gradientShadowTmp);
//    } else {
//      GradientPaint gradientShadowTmp = new GradientPaint(0, 0, new Color(240, 255, 210), 0, y + 15 + h / 2, new Color(204, 204, 204));
//      g2D.setPaint(gradientShadowTmp);
//    }
//  }
////   selectColor = new Color(255, 255, 200);
////   deSelectColor = new Color(240, 255, 210);
//  g2D.fill(shape);
//  if (runCount > 1) {
//    g2D.setColor(hazAlfa(getRunForTab(tabPane.getTabCount(), tabIndex) - 1));
//    g2D.fill(shape);
//  }
//  g2D.fill(shape);
//}
//@Override
//protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
//  if (runCount > 1) {
//    int lines[] = new int[runCount];
//    for (int i = 0; i < runCount; i++) {
//      lines[i] = rects[tabRuns[i]].y + (tabPlacement == TOP ? maxTabHeight : 0);
//    }
//    Arrays.sort(lines);
//    if (tabPlacement == TOP) {
//      int fila = runCount;
//      for (int i = 0; i < lines.length - 1; i++, fila--) {
//        Polygon carp = new Polygon();
//        carp.addPoint(0, lines[i]);
//        carp.addPoint(tabPane.getWidth() - 2 * fila - 2, lines[i]);
//        carp.addPoint(tabPane.getWidth() - 2 * fila, lines[i] + 3);
//        if (i < lines.length - 2) {
//          carp.addPoint(tabPane.getWidth() - 2 * fila, lines[i + 1]);
//          carp.addPoint(0, lines[i + 1]);
//        } else {
//          carp.addPoint(tabPane.getWidth() - 2 * fila, lines[i] + rects[selectedIndex].height);
//          carp.addPoint(0, lines[i] + rects[selectedIndex].height);
//        }
//        carp.addPoint(0, lines[i]);
//        g.setColor(hazAlfa(fila));
//        g.fillPolygon(carp);
//        g.setColor(darkShadow.darker());
//        g.drawPolygon(carp);
//      }
//    } else {
//      int fila = 0;
//      for (int i = 0; i < lines.length - 1; i++, fila++) {
//        Polygon carp = new Polygon();
//        carp.addPoint(0, lines[i]);
//        carp.addPoint(tabPane.getWidth() - 2 * fila - 1, lines[i]);
//        carp.addPoint(tabPane.getWidth() - 2 * fila - 1, lines[i + 1] - 3);
//        carp.addPoint(tabPane.getWidth() - 2 * fila - 3, lines[i + 1]);
//        carp.addPoint(0, lines[i + 1]);
//        carp.addPoint(0, lines[i]);
//        g.setColor(hazAlfa(fila + 2));
//        g.fillPolygon(carp);
//        g.setColor(darkShadow.darker());
//        g.drawPolygon(carp);
//      }
//    }
//  }
//  super.paintTabArea(g, tabPlacement, selectedIndex);
//}
//@Override
//protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect,
//  boolean isSelected) {
//super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
//g.setFont(font);
//View v = getTextViewForTab(tabIndex);
//if (v != null) {
//  v.paint(g, textRect);
//} else {
//  int mnemIndex = tabPane.getDisplayedMnemonicIndexAt(tabIndex);
//  if (tabPane.isEnabled() && tabPane.isEnabledAt(tabIndex)) {
//    g.setColor(tabPane.getForegroundAt(tabIndex));
//    BasicGraphicsUtils.drawStringUnderlineCharAt(g, title, mnemIndex, textRect.x, textRect.y + metrics.getAscent());
//  } else { // tab disabled
//    g.setColor(Color.BLACK);
//    BasicGraphicsUtils.drawStringUnderlineCharAt(g, title, mnemIndex, textRect.x, textRect.y + metrics.getAscent());
//    g.setColor(tabPane.getBackgroundAt(tabIndex).darker());
//    BasicGraphicsUtils.drawStringUnderlineCharAt(g, title, mnemIndex, textRect.x - 1, textRect.y + metrics.getAscent() - 1);
//  }
//}
//}
//  @Override
//  protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect,
//      boolean isSelected) {
//    g.setFont(font);
//    View v = getTextViewForTab(tabIndex);
//    if (v != null) {
//      v.paint(g, textRect);
//    } else {
//      int mnemIndex = tabPane.getDisplayedMnemonicIndexAt(tabIndex);
//
//      if (tabPane.isEnabled() && tabPane.isEnabledAt(tabIndex)) {
//        Color fg = tabPane.getForegroundAt(tabIndex);
//        if (isSelected && (fg instanceof UIResource)) {
//          Color selectedFG = UIManager.getColor("TabbedPane.selectedForeground");
//          if (selectedFG != null) {
//            fg = selectedFG;
//          }
//        }
//        g.setColor(fg);
//        SwingUtilities2.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x, textRect.y + metrics.getAscent());
//
//      } else { // tab disabled
//        // PAY ATTENTION TO HERE
//        g.setColor(tabPane.getBackgroundAt(tabIndex).brighter());
//        SwingUtilities2.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x, textRect.y + metrics.getAscent());
//        g.setColor(tabPane.getBackgroundAt(tabIndex).darker());
//        SwingUtilities2.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x - 1, textRect.y + metrics.getAscent() - 1);
//      }
//    }
//  }
  protected void paintText(SynthContext ss, Graphics g, String text, int x, int y, int mnemonicIndex) {
    Graphics2D g2 = (Graphics2D) g;

    Object oldAAValue = g2.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

    g2.setFont(ss.getStyle().getFont(ss));
    AffineTransform tx = new AffineTransform();
    tx.rotate(90 * Math.PI / 180, x, y);
    g2.setTransform(tx);

    g2.drawString(text, x, y);

    // get metrics from the graphics
    FontMetrics metrics = g2.getFontMetrics(ss.getStyle().getFont(ss));
    // get the height of a line of text in this font and render context
    int hgt = metrics.getHeight();
    // get the advance of my text in this font and render context
    int adv = metrics.stringWidth(text);
    // calculate the size of a box to hold the text with some padding.
    Dimension size = new Dimension(adv + 2, hgt + 2);

    g2.setClip(0, 0, size.width, size.height);

    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, oldAAValue);
  }

//  @Override
//  protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect,
//      boolean isSelected) {
//    Color savedColor = g.getColor();
//    g.setColor(new Color(244, 164, 96));
//    g.fillRect(rects[tabIndex].x, rects[tabIndex].y, rects[tabIndex].width, rects[tabIndex].height);
//    g.setColor(Color.WHITE);
//    g.drawRect(rects[tabIndex].x, rects[tabIndex].y, rects[tabIndex].width, rects[tabIndex].height);
//    g.setColor(savedColor);
//    textRect.width = 150;
//    super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
//  }

//  @Override
//  protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
//    return 150;
//  }
//
//  private final Insets borderInsets = new Insets(0, 0, 0, 0);
//
//  @Override
//  protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
//  }
//
//  @Override
//  protected Insets getContentBorderInsets(int tabPlacement) {
//    return borderInsets;
//  }

}

