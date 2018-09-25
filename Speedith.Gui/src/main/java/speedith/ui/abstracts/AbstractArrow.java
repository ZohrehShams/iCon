package speedith.ui.abstracts;

import com.fasterxml.jackson.annotation.JsonProperty;

import icircles.abstractDescription.AbstractCurve;
import icircles.abstractDescription.AbstractDiagram;
import icircles.abstractDescription.AbstractShape;
import icircles.abstractDescription.AbstractSpider;
import speedith.core.lang.Arrow;
import speedith.core.lang.Cardinality;

public class AbstractArrow implements Comparable<AbstractArrow>{
	
	
	private AbstractShape m_source;
	private AbstractShape m_target;
	
	private String m_type;
	private String m_label;
	
	private Cardinality cardinality;
	
	//These are to identify the type of arrow source (start) and target (end).  
	//private AbstractShape start,end; 
	

    public AbstractArrow(){
    	
    }
    
    public AbstractArrow(AbstractShape source, AbstractShape target, String type, String label){
		m_source = source;
		m_target = target;
		m_type = type;
		m_label = label;
    }
	


	public AbstractShape get_start() {
		return m_source; 
	}
	
	public AbstractShape get_end() {
		return m_target; 
	}

	
	
	public String get_type() {
		return m_type;
	}
	

	public String getLabel(){
		return m_label;
	}
	

    public void setLabel(String label) {
    	this.m_label = label;
    }
            
    
    public boolean sameStartObjectType(AbstractArrow one, AbstractArrow two){
    	boolean v;
    	
    	if ((one.get_start() instanceof AbstractSpider) && 
    		(two.get_start() instanceof AbstractSpider) ){
    		v = true;
    		return v;
    	}
    	
    	if ((one.get_start() instanceof AbstractCurve) && 
        		(two.get_start() instanceof AbstractCurve) ){
        		v = true;
        		return v;
        	}
    	
    	if ((one.get_start() instanceof AbstractDiagram) && 
        		(two.get_start() instanceof AbstractDiagram) ){
        		v = true;
        		return v;
        	}
    	
    	return false;
    }
    
    
    public boolean sameEndObjectType(AbstractArrow one, AbstractArrow two){
    	boolean v;
    	
    	if ((one.get_end() instanceof AbstractSpider) && 
    		(two.get_end() instanceof AbstractSpider) ){
    		v = true;
    		return v;
    	}
    	
    	if ((one.get_end() instanceof AbstractCurve) && 
        		(two.get_end() instanceof AbstractCurve) ){
        		v = true;
        		return v;
        	}
    	
    	if ((one.get_end() instanceof AbstractDiagram) && 
        		(two.get_end() instanceof AbstractDiagram) ){
        		v = true;
        		return v;
        	}
    	
    	return false;
    }
    
    
    
    public void setCardinality(Cardinality cardinal){
    	cardinality = cardinal;
    }
    
    
    public Cardinality getCardinality(){
    	return cardinality;
    }
    
    
	@Override
	public int compareTo(AbstractArrow other) {
		
		int v;
		
		if((sameStartObjectType(this,other)) && (sameEndObjectType(this,other))){
			
			v = this.m_source.compareTo(other.m_source);
			if (v != 0) return v;
			
			v = this.m_target.compareTo(other.m_target);
			if (v != 0) return v;
			
			v = this.m_type.compareTo(other.m_type);
			if (v != 0) return v;
			
			v = this.m_label.compareTo(other.m_label);
			if (v != 0) return v;
			
			
//			if((get_start() instanceof AbstractSpider) && (get_end() instanceof AbstractSpider))
//			{
//			v = this.m_source.compareTo(other.m_source);
//			if (v != 0) return v;
//			
//			v = this.m_target.compareTo(other.m_target);
//			if (v != 0) return v;
//			
//			v = this.m_type.compareTo(other.m_type);
//			if (v != 0) return v;
//			
//			v = this.m_label.compareTo(other.m_label);
//			if (v != 0) return v;}
//			
//			
//			if((get_start() instanceof AbstractCurve) && (get_end() instanceof AbstractCurve))
//			{
//			v = this.m_source.compareTo(other.m_source);
//			if (v != 0) return v;
//			
//			v = this.m_target.compareTo(other.m_target);
//			if (v != 0) return v;
//			
//			v = this.m_type.compareTo(other.m_type);
//			if (v != 0) return v;
//			
//			v = this.m_label.compareTo(other.m_label);
//			if (v != 0) return v;}
//			
//			
//			if((get_start() instanceof AbstractSpider) && (get_end() instanceof AbstractCurve))
//			{
//			v = this.m_source.compareTo(other.m_source);
//			if (v != 0) return v;
//			
//			v = this.m_target.compareTo(other.m_target);
//			if (v != 0) return v;
//			
//			v = this.m_type.compareTo(other.m_type);
//			if (v != 0) return v;
//			
//			v = this.m_label.compareTo(other.m_label);
//			if (v != 0) return v;}
//			
//			
//			if((get_start() instanceof AbstractCurve) && (get_end() instanceof AbstractSpider))
//			{
//			v = this.m_source.compareTo(other.m_source);
//			if (v != 0) return v;
//			
//			v = this.m_target.compareTo(other.m_target);
//			if (v != 0) return v;
//			
//			v = this.m_type.compareTo(other.m_type);
//			if (v != 0) return v;
//			
//			v = this.m_label.compareTo(other.m_label);
//			if (v != 0) return v;}
			
			
		}
		
		return 1;
		//Zohreh: If I get in trouble for comparison of two arrow when they don't have the same start let's say, 
		// I can say retun 1 is start is curve and -1 if start is spider
	}
    




	//Zohreh: I'm not so sure if I need the brackets, I guess it has to do with how these are passed to the diagram panels?
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		sb.append(m_source);
        sb.append(", ");
        sb.append(m_target);
        sb.append(", ");
        sb.append(m_type);
        sb.append(", ");
        sb.append(m_label);
        sb.append(')');
		return sb.toString();
	}
	
	


}
