package com.hkt.client.swingexp.app.core;

import java.util.Vector;

import javax.swing.JTable;

@SuppressWarnings("serial")
public class TableObservable extends JTable implements IObservable{
  private boolean changed = false;
  private Vector<IMyObserver> obs;

  /** Construct an Observable with zero IMyObservers. */

  public TableObservable() {
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

  public void notifyObservers(Object arg, Object arg1) {

    Object[] arrLocal;

    synchronized (this) {

      if (!changed)
        return;
      arrLocal = obs.toArray();
      clearChanged();
    }

    for (int i = arrLocal.length - 1; i >= 0; i--)
      ((IMyObserver) arrLocal[i]).update(arg, arg1);
  }

  /**
   * Clears the IMyObserver list so that this object no longer has any IMyObservers.
   */
  public synchronized void deleteObservers() {
    obs.removeAllElements();
  }

  protected synchronized void setChanged() {
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
  
  public void change() {
    setChanged();
    notifyObservers("", "");
  }
}
