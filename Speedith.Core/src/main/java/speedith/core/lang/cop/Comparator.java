package speedith.core.lang.cop;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;


public enum Comparator {
	
	Geq(">="),
	Leq("<="),
	Eq("=");
	
	private final String name;
	
	private Comparator(String name) {
	    this.name = name;
	  }
	
	
	
	public String getName() {
		return name;
	}
	
	
	public static Comparator getGeq(){
		return Geq;
	}
	
	public static Comparator getLeq(){
		return Leq;
	}
	
	public static Comparator getEq(){
		return Eq;
	}
	
	
	@Override
	public String toString() {
		return this.getName();	
	}
	
	
	public boolean equals(String name) {
	    return getName().equals(name);
	}
	
	
	public static Comparator fromString(String comparatorName) {
		return ComparatorRegistry.KnownComparators.get(comparatorName);
    }
	
	
	public static Set<String> knownComparatorNames() {
	    return Collections.unmodifiableSet(ComparatorRegistry.KnownComparators.keySet());
	}
	
	
	private static class ComparatorRegistry {
		public static final HashMap<String, Comparator> KnownComparators = new HashMap<>();
		
		static {
			for (Comparator comparator : Comparator.values()) {
				KnownComparators.put(comparator.getName(), comparator);
			}
		}
		private ComparatorRegistry() {
		}
		
	}

		    


}
