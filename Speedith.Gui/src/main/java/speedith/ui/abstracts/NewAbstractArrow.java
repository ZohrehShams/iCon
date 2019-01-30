package speedith.ui.abstracts;

import icircles.abstractDescription.AbstractBasicRegion;
import icircles.abstractDescription.AbstractCurve;
import icircles.abstractDescription.AbstractDiagram;
import icircles.abstractDescription.AbstractShape;
import icircles.abstractDescription.AbstractSpider;
import speedith.core.lang.Cardinality;


public class NewAbstractArrow implements Comparable<NewAbstractArrow>{
	
    private String sourceString;
    private String targetString;
    private String type;
    private String label;
    private AbstractBasicRegion abrSource;
    private AbstractBasicRegion abrTarget;
    private AbstractCurve cSource;
    private AbstractCurve cTarget;
    private Cardinality cardinality;
    
    
    private NewAbstractArrow(String sourceString, String targetString, String type, String label) {
    	this.sourceString = sourceString;
    	this.targetString = targetString;
    	this.type = type;
        this.label = label;  
    }

    
    public NewAbstractArrow(AbstractBasicRegion source, AbstractBasicRegion target, 
    		String sourceString, String targetString,String type, String label) {
        this(sourceString, targetString, type, label);
        abrSource = source;
        abrTarget = target;
    }
    
    
    public NewAbstractArrow(AbstractBasicRegion source, AbstractCurve target, 
    		String sourceString, String targetString,String type, String label) {
        this(sourceString, targetString, type, label);
        abrSource = source;
        cTarget = target;
    }


    public NewAbstractArrow(AbstractCurve source, AbstractCurve target, 
    		String sourceString, String targetString,String type, String label) {
        this(sourceString, targetString, type, label);
        cSource = source;
        cTarget = target;
    }
    

    public NewAbstractArrow(AbstractCurve source, AbstractBasicRegion target, 
    		String sourceString, String targetString,String type, String label) {
        this(sourceString, targetString, type, label);
        cSource = source;
        abrTarget = target;
    }

    

	public String get_source() {
		return sourceString; 
	}
	
	public String get_target() {
		return targetString; 
	}

	
	public String get_type() {
		return type;
	}
	

	public String getLabel(){
		return label;
	}
	

    public void setLabel(String label) {
    	this.label = label;
    }
    
    
	public Object getStart() {
		if (abrSource == null) {
            return cSource;
        } else {
            return abrSource;
        }
	}
	
	public Object getEnd() {
        if (abrTarget == null) {
            return cTarget;
        } else {
            return abrTarget;
        }
	}
	
	
	
    public void setCardinality(Cardinality cardinal){
    	cardinality = cardinal;
    }
    
    
    public Cardinality getCardinality(){
    	return cardinality;
    }
    
    

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		sb.append(sourceString);
        sb.append(", ");
        sb.append(targetString);
        sb.append(", ");
        sb.append(type);
        sb.append(", ");
        sb.append(label);
        sb.append(')');
		return sb.toString();
	}
	
	
	
	public boolean sameStartObjectType(NewAbstractArrow one, NewAbstractArrow two){
		boolean v;
	    	
    	if ((one.getStart() instanceof AbstractBasicRegion) && 
    		(two.getStart() instanceof AbstractBasicRegion)){
    		AbstractBasicRegion abrOne = (AbstractBasicRegion) one.getStart();
    		AbstractBasicRegion abrTwo = (AbstractBasicRegion) two.getStart();
    		if (abrOne.compareTo(abrTwo) == 0){
    		v = true;
    		return v;}
    	}
    	
    	if ((one.getStart() instanceof AbstractCurve) && 
        	(two.getStart() instanceof AbstractCurve)){
    		AbstractCurve cOne = (AbstractCurve) one.getStart();
    		AbstractCurve cTwo = (AbstractCurve) two.getStart();
    		if(cOne.compareTo(cTwo) == 0){
        		v = true;
        		return v;}
        	}
    	
    	return false;
	}
	 
	 
	 
	 public boolean sameEndObjectType(NewAbstractArrow one, NewAbstractArrow two){
		 boolean v;
	    	
    	if ((one.getEnd() instanceof AbstractBasicRegion) && 
    		(two.getEnd() instanceof AbstractBasicRegion) ){
    		AbstractBasicRegion abrOne = (AbstractBasicRegion) one.getEnd();
    		AbstractBasicRegion abrTwo = (AbstractBasicRegion) two.getEnd();
    		if (abrOne.compareTo(abrTwo) == 0){
    		v = true;
    		return v;}

    	}
    	
    	if ((one.getEnd() instanceof AbstractCurve) && 
        	(two.getEnd() instanceof AbstractCurve)){
    		AbstractCurve cOne = (AbstractCurve) one.getEnd();
    		AbstractCurve cTwo = (AbstractCurve) two.getEnd();
    		if(cOne.compareTo(cTwo) == 0){
        		v = true;
        		return v;}
        	}
    	
    	return false;
    }
	 
	 
		@Override
		public int compareTo(NewAbstractArrow other) {
			
			int v;
			
			if((sameStartObjectType(this,other)) && (sameEndObjectType(this,other))){
				v = this.type.compareTo(other.type);
				if (v != 0) return v;
				
				v = this.label.compareTo(other.label);
				if (v != 0) return v;			
			}
			
			return 1;
		}


	 
	 
    
    
}


