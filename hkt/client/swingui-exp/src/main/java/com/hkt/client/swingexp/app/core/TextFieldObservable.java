package com.hkt.client.swingexp.app.core;

import java.util.Vector;

import javax.swing.JTextField;

public class TextFieldObservable extends JTextField implements IObservable{
  private boolean changed = false;
  private Vector<IMyObserver> obs;

  /** Construct an Observable with zero IMyObservers. */

  public TextFieldObservable() {
    obs = new Vector<IMyObserver>();
    
  }

  @Override
  public synchronized void addObserver(IMyObserver o) {
    if (o == null)
      throw new NullPointerException();
    if (!obs.contains(o)) {
      obs.addElement(o);
    }
  }

  @Override
  public synchronized void deleteObserver(IMyObserver o) {
    obs.removeElement(o);
  }

  @Override
  public void notifyIMyObservers() {
    notifyObservers(null, null);
  }

  public void notifyObservers(Object arg1,Object arg) {

    Object[] arrLocal;

    synchronized (this) {
      if (!changed)
        return;
      arrLocal = obs.toArray();
      clearChanged();
    }

    for (int i = arrLocal.length - 1; i >= 0; i--)
      ((IMyObserver) arrLocal[i]).update(arg1, arg);
  }

  /**
   * Clears the IMyObserver list so that this object no longer has any IMyObservers.
   */
  public synchronized void deleteObservers() {
    obs.removeAllElements();
  }

  public synchronized void setChanged() {
    changed = true;
  }

  protected synchronized void clearChanged() {
    changed = false;
  }

  public synchronized boolean hasChanged() {
    return changed;
  }

  public synchronized int countObservers() {
    return obs.size();
  }
}
