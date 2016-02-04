package com.hkt.client.swingexp.app.component;

import java.lang.ref.WeakReference;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class WeakChangeListenerProxy implements ChangeListener {

  public WeakReference reference;

  public WeakChangeListenerProxy(ChangeListener listener) {
      this.reference = new WeakReference(listener);
  }

  public void stateChanged(ChangeEvent e) {
      ChangeListener actualListener = (ChangeListener)reference.get();
      if (actualListener != null) {
          actualListener.stateChanged(e);
      }
  }

}
