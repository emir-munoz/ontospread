package org.ontospread.gui.listener;

import java.util.LinkedList;
import java.util.List;

public class Subject {

	List<Listener> listeners = new LinkedList<Listener>();

	public void register(Listener listener){
		listeners.add(listener);
	}
	public void unregister(Listener listener){
		listeners.remove(listener);
	}
	public void notifyListeners(Event event){
		for (Listener listener : listeners) {
			listener.notify(event);
		}
	}
}
