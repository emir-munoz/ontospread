package org.ontospread.gui.listener;

public class Listener {

	public Listener(Subject s){
		s.register(this);
	}
	public void notify(Event e){
		System.out.println(e);
	};
	public static void main(String []args){
		Subject s = new Subject();
		Listener l1 = new Listener(s);
		Listener l2 = new Listener(s);
		s.notifyListeners(new Event());
	}
	

}
