package speedith.core.lang;

import static speedith.core.i18n.Translations.i18n;

import java.io.IOException;
import java.io.Serializable;

public class Cardinality implements Serializable{
	
	private  Comparator comparator;
	private  int number;
	
	
 
	public Cardinality(String  comparator, String number) {
		this(Comparator.fromString(comparator), Integer.parseInt(number));
	}
	
	public Cardinality(Comparator  comparator, int number) {
		this.comparator = comparator;
		this.number = number;
	
	}	
	

	
	
	public Comparator getComparator(){
		return comparator;
	}
	
	public int getNumber(){
		return number;
	}
	
	
	
	@Override
	public String toString(){
		return comparator.toString() + Integer.toString(number);
//		return comparator.toString() + number;

	}
	

    public void toString(Appendable sb) {
        try {
            if (sb == null) {
                throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "sb"));
            }
            sb.append('(');
            SpiderDiagram.printString(sb, comparator.toString());
            sb.append(", ");
            SpiderDiagram.printString(sb,Integer.toString(number));
            sb.append(')');
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    
	@Override 
	public boolean equals(Object o) {
		return this.getComparator().equals(((Cardinality) o).getComparator()) 
				&& this.getNumber() == ((Cardinality) o).getNumber() ;	
	}

}
