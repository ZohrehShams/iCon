package speedith.core.lang.cop;

import static speedith.core.i18n.Translations.i18n;

import java.io.IOException;
import java.util.Objects;

import speedith.core.lang.SpiderDiagram;

import java.io.Serializable;
/**
 * Type arrow as a new component of COP is created here.
 * @author Zohreh Shams [zs315@cam.ac.uk]
 * */


public class Arrow implements Comparable<Arrow>, Serializable{
	
	private static final long serialVersionUID = 1766062030962182226L;
	private  String  source;
	private  String target;
	private  String arrowType;
	private  String label;
	
	private Cardinality cardinality;

	
	public Arrow(String  source, String target, String arrowType) {		 
		 this.source = source;
		 this.target = target;
		 this.arrowType = arrowType;
	  }
	
	
	
	public Arrow(String  source, String target, String arrowType,String label) {		 
		 this.source = source;
		 this.target = target;
		 this.arrowType = arrowType;
		 this.label=label;
	  }
	
	
	
	
	public String arrowSource()
	{ 
		return source;
	}
	
	
	public String arrowTarget()
	{ 
		return target;
	}
	
	
	public String arrowType()
	{ 
		return arrowType;
	}
	
	
	public String arrowLabel(){
		if (label == null){
			return "";
		}else
			return label;
	}
	
	 
	 
	public String arrowSource(Arrow arrow)
	{ 
		return arrow.source;
	}
	
	public String arrowTarget(Arrow arrow)
	{ 
		return arrow.target;
	}
	
	public String arrowType(Arrow arrow)
	{ 
		return arrow.arrowType;
	}
	
	
	public static boolean sameSource(Arrow one, Arrow two){
		if (one.source.equals(two.source)) 
			return true;
		else 
			return false;
	}

	
	public static boolean sameTarget(Arrow one, Arrow two){
		if (one.target.equals(two.target)) 
			return true;
		else return false;
		
	}
	
	
	public static boolean sameType(Arrow one, Arrow two){
		if (one.arrowType.equals(two.arrowType))
			return true;
		else return false;

	}
	
	
	public static boolean sameArrow(Arrow one, Arrow two){
		if ( (sameSource(one, two)) && (sameTarget(one, two)) && (sameType(one, two)))
			return true;
		else return false;

	}
	   

	
    public Arrow setCardinality(Cardinality cardinal){
    	//this.cardinality = new Cardinality(cardinal.getComparator(),cardinal.getNumber());
    	this.cardinality = cardinal;
    	return this;
    }
    
    
    public Cardinality getCardinality(){
    	return cardinality;
    }
    
    
	@Override
	public int compareTo(Arrow arrow) {
		int v;
		
		v = this.source.compareTo(arrow.source);
		if (v != 0) return v;
		
		v = this.target.compareTo(arrow.target);
		if (v != 0) return v;
		
		v = this.arrowType.compareTo(arrow.arrowType);
		if (v != 0) return v;
		
		return 0;
	}
	
	
	
	
    /**@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Arrow) {
            Arrow other = (Arrow) obj;
            return sameSource(this, other) && sameTarget(this, other) && sameType(this, other);
        }
        return false;
    }*/
	@Override 
	public boolean equals(Object o) {
		return (o instanceof Arrow) && (this.compareTo((Arrow) o) == 0);	
	}
	
	
	
    @Override
    public int hashCode() {
        return Objects.hash(source, target, arrowType);
    }
    
    
    
    public void toString(Appendable sb) {
        try {
            if (sb == null) {
                throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "sb"));
            }
            sb.append('(');
            SpiderDiagram.printString(sb, source);
            sb.append(", ");
            SpiderDiagram.printString(sb, target);
            sb.append(", ");
            SpiderDiagram.printString(sb, arrowType);
            if(label != null){
            	sb.append(", ");
            	SpiderDiagram.printString(sb, label);
            }
            sb.append(')');
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


	
}
