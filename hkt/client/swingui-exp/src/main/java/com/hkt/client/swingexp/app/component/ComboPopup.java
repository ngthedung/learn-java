package com.hkt.client.swingexp.app.component;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class ComboPopup<E> extends JComboBox<E> {
	private static final long serialVersionUID = 1L;
	private int widthD = 0;
	protected List<E> data = new ArrayList<E>();
	private PopupFilter<E> popupFilter;
	private boolean hiden, click;

	public ComboPopup() {
		setOpaque(true);
		this.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.removeMouseListener(this.getMouseListeners()[0]);
		System.out.println(this.getComponents()[0].getMouseListeners().length);
		for(int i = 0; i< this.getComponents()[0].getMouseListeners().length;){
			this.getComponents()[0].removeMouseListener(this.getComponents()[0].getMouseListeners()[i]);
		}
		System.out.println(this.getComponents()[0].getMouseListeners().length);
		
		this.getComponents()[0].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showView();
			}
		});
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showView();
			}
		});
		
		popupFilter = new PopupFilter(this);
	}

	public boolean isClick() {
		return click;
	}

	public List<E> getListCheck() {
		List<E> list = new ArrayList<E>();
		for (CheckP<E> e : popupFilter.getCheckPs()) {
			if (e.isSelected()) {
				if (e.getText().equals("Tất cả")) {
					list = new ArrayList<E>();
					return list;
				}
				list.add(e.getItem());
			}
		}
		if (list.isEmpty() && this.getItemCount() == 1) {
			for (E e : data) {
				if (e.toString().equals(this.getItemAt(0).toString())) {
					list.add(e);
				}
			}
		}
		return list;
	}

	public void setClick(boolean click) {
		this.click = click;
	}

	public void showView() {
		if(!this.isEnabled()){
			return;
		}
		if (!hiden || !click) {
			popupFilter.showPopup();
		} else {
			popupFilter.setVisible(false);
			String str = "";
			for (int i = 0; i < popupFilter.getCheckPs().size(); i++) {
				if (popupFilter.getCheckPs().get(i).isSelected()) {
					str = str + popupFilter.getCheckPs().get(i).getItem().toString() + ", ";
				}
			}
			try {
				String[] items = { str.substring(0, str.length() - 2) };
				this.setModel(new DefaultComboBoxModel(items));
			} catch (Exception e2) {
				this.setModel(new DefaultComboBoxModel());
			}
		}
		hiden = !hiden;
		click = true;
		this.requestFocus();
	}

	public void setData(List<E> list) {
		this.data = list;
	}

	public List<E> getData() {
		return data;
	}

	public class CheckP<E> extends JCheckBox {
		private E item;

		public CheckP(E item) {
			super(item.toString());
			this.item = item;
			setOpaque(false);
		}

		public E getItem() {
			return item;
		}

		public void setItem(E item) {
			this.item = item;
		}

	}

	public class PopupFilter<E> extends JPopupMenu {
		private static final long serialVersionUID = 1L;
		private ComboPopup<E> buttonPopup;
		private List<CheckP<E>> checkPs = new ArrayList<CheckP<E>>();

		public List<CheckP<E>> getCheckPs() {
			return checkPs;
		}

		public PopupFilter(ComboPopup<E> buttonPopup1) {
			this.buttonPopup = buttonPopup1;

			this.addPopupMenuListener(new PopupMenuListener() {

				@Override
				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				}

				@Override
				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

				}

				@Override
				public void popupMenuCanceled(PopupMenuEvent e) {
					String str = "";
					for (int i = 0; i < checkPs.size(); i++) {
						if (checkPs.get(i).isSelected()) {
							str = str + checkPs.get(i).getItem().toString() + ", ";
						}
					}
					try {
						String[] items = { str.substring(0, str.length() - 2) };
						buttonPopup.setModel(new DefaultComboBoxModel(items));
					} catch (Exception e2) {
						buttonPopup.setModel(new DefaultComboBoxModel());
					}

					buttonPopup.setClick(false);
					buttonPopup.requestFocus();
				}
			});
		}

		public void showPopup() {
			this.removeAll();
			this.setLayout(new GridLayout(buttonPopup.getData().size(), 1));
			if (checkPs.isEmpty()) {
				for (int i = 0; i < buttonPopup.getData().size(); i++) {
					CheckP<E> ck = new CheckP<E>(buttonPopup.getData().get(i));
					if (buttonPopup.getItemCount() == 1) {
						if (buttonPopup.getData().get(i).toString().equals(buttonPopup.getItemAt(0).toString())) {
							ck.setSelected(true);
						}
					}
					if (ck.getText().equals("Tất cả")) {
						ck.addItemListener(new ItemListener() {

							@Override
							public void itemStateChanged(ItemEvent e) {
								CheckP ck = (CheckP) e.getSource();
								if (ck.isSelected()) {
									for (int i = 0; i < checkPs.size(); i++) {
										checkPs.get(i).setSelected(true);
									}
								} else {
									for (int i = 0; i < checkPs.size(); i++) {
										checkPs.get(i).setSelected(false);
									}
								}
							}
						});
					}
					this.add(ck);
					checkPs.add(ck);
				}
			} else {
				for (int i = 0; i < checkPs.size(); i++) {
					this.add(checkPs.get(i));
				}
			}
			if (buttonPopup.isShowing()) {
				int wd = buttonPopup.getWidth();
				if (widthD != 0) {
					wd = widthD;
				}

				if (buttonPopup.getData().size() > 10) {
					setPopupSize(wd, 10 * 23);
				} else {
					setPopupSize(wd, buttonPopup.getData().size() * 23);
				}

				show(buttonPopup, 0, buttonPopup.getHeight());
			}
		}

	}

}
