package com.hkt.client.swingexp.app.core;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.toedter.calendar.JDateChooser;

/*
 * author: longnt
 */

public class MyGroupLayout extends GroupLayout {

	private Container host;
	private HashMap<Integer, List<Component>> hashMap = new HashMap<Integer, List<Component>>();
	private Component componentImage, comMessenger;
	private List<Component> list1, list2;
	private boolean shift;

	public MyGroupLayout(Container host) {
		super(host);
		this.host = host;
	}

	public void addImage(Component component) {
		componentImage = component;
	}

	public void addMessenger(Component component) {
		comMessenger = component;
	}

	public void add(int row, int index, Component component) {
		List<Component> list = hashMap.get(row);
		if (list == null) {
			list = new ArrayList<Component>();
		}
		try {
			list.add(index, component);
		} catch (Exception e) {
			list.add(component);
		}
		hashMap.put(row, list);
	}

	public JPanel getContainer() {
		return (JPanel) host;
	}

	public void updateGui() {
		ParallelGroup parallelGroupH = createParallelGroup(Alignment.LEADING);
		ParallelGroup parallelGroupV = createParallelGroup(Alignment.LEADING);
		SequentialGroup groupV = createSequentialGroup();
		parallelGroupV.addGroup(groupV);
		int behin = 0;
		list1 = new ArrayList<Component>();
		list2 = new ArrayList<Component>();

		if (componentImage != null) {
			behin = 3;
			hashMap.get(0).get(0).setFont(new java.awt.Font("Tahoma", Font.BOLD, 14));
			hashMap.get(1).get(0).setFont(new java.awt.Font("Tahoma", Font.BOLD, 14));
			hashMap.get(2).get(0).setFont(new java.awt.Font("Tahoma", Font.BOLD, 14));
			hashMap.get(0).get(1).setFont(new java.awt.Font("Tahoma", Font.PLAIN, 14));
			hashMap.get(1).get(1).setFont(new java.awt.Font("Tahoma", Font.PLAIN, 14));
			hashMap.get(2).get(1).setFont(new java.awt.Font("Tahoma", Font.PLAIN, 14));

			SequentialGroup groupH = createSequentialGroup();
			groupH
			    .addGroup(
			        createParallelGroup(Alignment.LEADING)
			            .addComponent(hashMap.get(0).get(0), PREFERRED_SIZE, 102, PREFERRED_SIZE)
			            .addComponent(hashMap.get(1).get(0), PREFERRED_SIZE, 102, PREFERRED_SIZE)
			            .addComponent(hashMap.get(2).get(0), PREFERRED_SIZE, 102, PREFERRED_SIZE))
			    .addGap(2, 2, 2)
			    .addGroup(
			        createParallelGroup(Alignment.LEADING)
			            .addComponent(hashMap.get(0).get(1), DEFAULT_SIZE, 142, Short.MAX_VALUE)
			            .addComponent(hashMap.get(1).get(1), DEFAULT_SIZE, 142, Short.MAX_VALUE)
			            .addComponent(hashMap.get(2).get(1), DEFAULT_SIZE, 142, Short.MAX_VALUE)).addGap(10, 10, 10)
			    .addComponent(componentImage, DEFAULT_SIZE, 246, Short.MAX_VALUE);
			parallelGroupH = parallelGroupH.addGroup(groupH);
			groupV = groupV.addGroup(
			    createParallelGroup(Alignment.LEADING)
			        .addGroup(
			            createSequentialGroup().addComponent(hashMap.get(0).get(0), PREFERRED_SIZE, 23, PREFERRED_SIZE)
			                .addGap(10, 10, 10).addComponent(hashMap.get(1).get(0), PREFERRED_SIZE, 23, PREFERRED_SIZE)
			                .addGap(10, 10, 10).addComponent(hashMap.get(2).get(0), PREFERRED_SIZE, 23, PREFERRED_SIZE))
			        .addGroup(
			            createSequentialGroup().addComponent(hashMap.get(0).get(1), PREFERRED_SIZE, 23, PREFERRED_SIZE)
			                .addGap(10, 10, 10).addComponent(hashMap.get(1).get(1), PREFERRED_SIZE, 23, PREFERRED_SIZE)
			                .addGap(10, 10, 10).addComponent(hashMap.get(2).get(1), PREFERRED_SIZE, 23, PREFERRED_SIZE))
			        .addComponent(componentImage, PREFERRED_SIZE, 89, PREFERRED_SIZE)).addGap(11, 11, 11);
		}
		for (int i = 0; i < hashMap.keySet().size(); i++) {
			List<Component> list = hashMap.get(i);
			if (list.size() > 1 && list.get(1).isEnabled()) {
				if ((list.get(1) instanceof MyJDateChooser)) {
					list1.add(((MyJDateChooser) list.get(1)).getDateEditor());
				} else {
					if ((list.get(1) instanceof JScrollPane)) {
						list1.add(((JScrollPane) list.get(1)).getViewport().getView());
					} else {
						list1.add(list.get(1));
					}
				}
			}
			if (list.size() == 4) {
				if (list.get(3).isEnabled()) {
					if ((list.get(3) instanceof MyJDateChooser)) {
						list2.add(((MyJDateChooser) list.get(3)).getDateEditor());
					} else {
						if ((list.get(1) instanceof JScrollPane)) {
							list2.add(((JScrollPane) list.get(3)).getViewport().getView());
						} else {
							list2.add(list.get(3));
						}
					}

				}
			}
		}
		list1.addAll(list2);
		for (Component c : list1) {
			c.setFocusTraversalKeysEnabled(false);
			c.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_TAB) {
						Component c = (Component) e.getSource();

						if (!shift) {
							try {
								list1.get(list1.indexOf(c) + 1).requestFocus();
								try {
									((JTextField) list1.get(list1.indexOf(c) + 1)).selectAll();
								} catch (Exception e2) {
								}
							} catch (Exception e2) {
								list1.get(0).requestFocus();
								try {
									((JTextField) list1.get(0)).selectAll();
								} catch (Exception e3) {
								}
							}
						} else {
							try {
								list1.get(list1.indexOf(c) - 1).requestFocus();
								try {
									((JTextField) list1.get(list1.indexOf(c) - 1)).selectAll();
								} catch (Exception e2) {
								}
							} catch (Exception e2) {
								list1.get(0).requestFocus();
								try {
									((JTextField) list1.get(0)).selectAll();
								} catch (Exception e3) {
								}
							}
						}
					}
					if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
						shift = true;
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
						shift = false;
					}
				}
			});
		}
		for (int i = behin; i < hashMap.keySet().size(); i++) {
			SequentialGroup groupH = createSequentialGroup();
			List<Component> list = hashMap.get(i);
			if (list != null) {

				ParallelGroup parallelGroup = createParallelGroup(Alignment.LEADING);
				boolean lable = true;
				int k = 0;
				if (list.size() < 4) {
					k = 1;
				}
				int size = list.size();
				for (Component c : list) {
					JScrollPane c1 = null;
					if (c instanceof JComboBox) {
						c.setSize(10, 21);
						c.setPreferredSize(new Dimension(10, 21));
						c1 = new JScrollPane();
						c1.setBorder(null);
						c1.setOpaque(false);
						c1.getViewport().setOpaque(false);
						c1.setViewportView(c);
					}
					if (c instanceof JScrollPane || size == 1) {
						parallelGroup = parallelGroup.addComponent(c, DEFAULT_SIZE, c.getHeight(), Short.MAX_VALUE);
						if (c instanceof JScrollPane && ((JScrollPane) c).getViewport().getView() instanceof JTextArea) {
							((JTextArea) ((JScrollPane) c).getViewport().getView()).addCaretListener(new CaretListener() {

								@Override
								public void caretUpdate(CaretEvent e) {
									JTextArea a = (JTextArea) e.getSource();
									a.setRows(a.getLineCount());
								}
							});
						}
					} else {
						if (c1 != null) {
							c = c1;
						}
						parallelGroup = parallelGroup.addComponent(c, PREFERRED_SIZE, 23, PREFERRED_SIZE);
					}

					if (lable) {
						if (c1 != null) {
							c = c1;
						}
						c.setFont(new java.awt.Font("Tahoma", Font.BOLD, 14));
						if (size > 1) {
							groupH = groupH.addComponent(c, PREFERRED_SIZE, 100, PREFERRED_SIZE).addGap(4, 4, 4);
						} else {
							groupH = groupH.addComponent(c, DEFAULT_SIZE, 156, Short.MAX_VALUE);
						}
					} else {
						c.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 14));
						if (c1 != null) {
							c = c1;
						}
						if (k == 0) {
							groupH = groupH.addComponent(c, DEFAULT_SIZE, 156, Short.MAX_VALUE).addGap(10, 10, 10);
						} else {
							groupH = groupH.addComponent(c, DEFAULT_SIZE, 156, Short.MAX_VALUE);
						}
						k++;
					}
					lable = !lable;

				}

				parallelGroupH = parallelGroupH.addGroup(groupH);
				groupV = groupV.addGroup(parallelGroup);
				groupV.addGap(10, 10, 10);

			}
		}
		if (comMessenger != null) {
			parallelGroupH = parallelGroupH.addComponent(comMessenger, DEFAULT_SIZE, 502, Short.MAX_VALUE);
			groupV = groupV.addComponent(comMessenger, PREFERRED_SIZE, 15, PREFERRED_SIZE);
		}
		setHorizontalGroup(parallelGroupH);
		setVerticalGroup(parallelGroupV);
		host.setLayout(this);
		if (host instanceof JPanel) {
			((JPanel) host).setOpaque(false);
		}
	}
}
