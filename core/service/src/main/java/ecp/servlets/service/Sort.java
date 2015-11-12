package ecp.servlets.service;

import ecp.servlets.model.Person;
import java.util.Comparator;

public class Sort{
	public static Comparator<Person> ascending = new Comparator<Person>(){
		@Override	
		public int compare(Person p1,Person p2) {
			return p1.getGwa() < p2.getGwa() ? -1
            	: p1.getGwa() > p2.getGwa() ? 1 : 0;
		}
	};
	public static Comparator<Person> descending = new Comparator<Person>(){
		@Override
		public int compare(Person p1,Person p2){
			return p2.getGwa() < p1.getGwa() ? -1
            	: p2.getGwa() > p1.getGwa() ? 1 : 0;
	}
	};
}
