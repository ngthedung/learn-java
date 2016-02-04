package com.hkt.client.swingexp.app.core;

public interface IObservable {
	
	void addObserver(IMyObserver o);

	void deleteObserver(IMyObserver o);

	void notifyIMyObservers();
}
