package speedith.core.lang.cop;

import static propity.util.Sets.equal;
import static speedith.core.i18n.Translations.i18n;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;

/**
 * This class introduces arrow cardinality for arrows that belong to the concept diagrams.
 * @author Zohreh Shams
 */

public class CarCDiagram extends ConceptDiagram{

	private static final long serialVersionUID = 2455720597909942571L;
	public static final String CDCarTextBinaryId = "BinaryCarCD";
	public static final String CDCarTextId = "CarCD";
	public static final String CDTextArrowCardinalitiesAttribute = "cd_arrowCar";
	private  TreeMap<Arrow,Cardinality>  cd_arrowCardinalities;
	

	public CarCDiagram(ArrayList<PrimarySpiderDiagram> primaries, TreeSet<Arrow> arrows,TreeMap<Arrow,Cardinality> arrowCardinalities) {
		super(primaries, arrows);
		this.cd_arrowCardinalities  = arrowCardinalities  == null ? new TreeMap<Arrow,Cardinality>() : arrowCardinalities;
	}
	
	
    public CarCDiagram(Collection<PrimarySpiderDiagram> primaries, Collection<Arrow> arrows, Map<Arrow,Cardinality> arrowCardinalities) {
    	this(primaries == null ? null : new ArrayList<>(primaries),
    			arrows == null ? null : new TreeSet<>(arrows),
    			arrowCardinalities  == null ? null : new TreeMap<Arrow,Cardinality>(arrowCardinalities));
    }
    
	public SortedMap<Arrow,Cardinality> get_cd_ArrowCardinalities() {
        return Collections.unmodifiableSortedMap(cd_arrowCardinalities);
    }
	
	
	
	public CarCDiagram add_cd_ArrowCardinality(Arrow arrow, Cardinality cardinality) {
		
    	TreeMap<Arrow,Cardinality> newArrowCardinalities = new TreeMap<>(get_cd_ArrowCardinalities());

    	
    	if (! get_cd_Arrows().contains(arrow)){
            throw new IllegalArgumentException("The arrow does not exist in the diagram.");	
    	}
    	
    	if (get_cd_ArrowCardinalities().containsKey(arrow)){
    		throw new IllegalArgumentException("The arrow should not have a cardinality.");
    	}
			  
    	arrow.setCardinality(cardinality);
    	
    	if (arrow != null){
    		newArrowCardinalities.put(arrow, cardinality);
    	}
    	
    	return new CarCDiagram(getPrimaries(), get_cd_Arrows(),newArrowCardinalities);
    }
	
	
	
	protected boolean are_cd_ArrowCardinalitiesValid(){
		if (cd_arrowCardinalities == null || cd_arrowCardinalities.size() == 0){
			return true;
		}else {
			for (Arrow arrow : cd_arrowCardinalities.keySet()){
				if (! get_cd_Arrows().contains(arrow)){
					return false;
				}
			}
		}
		return true;
	}
	
	
	@Override
	public boolean isValid() {
		return (super.isValid()
	             && are_cd_ArrowCardinalitiesValid());
	}
	
	
	@Override
	public void toString(Appendable sb) throws IOException {
		 if (sb == null) {
	            throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "sb"));
	        }
		 	printId(sb);
	        sb.append(" {");
	        printArgs(sb);
	        sb.append(", ");
	        printArrows(sb);
	        sb.append(", ");
	        printArrowCardinalities(sb);
	        sb.append('}');
		
	}
	


    private static void printArrowCardinality(Appendable sb, Arrow arrow, Cardinality cardinality) throws IOException {
        sb.append('(');
        arrow.toString(sb);
        sb.append(", ");
        cardinality.toString(sb);
        sb.append(')');
    }
    
	protected void printArrowCardinalities(Appendable sb) throws IOException {
        sb.append(CDTextArrowCardinalitiesAttribute).append(" = ");
        sb.append('[');
        if (cd_arrowCardinalities != null && !cd_arrowCardinalities.isEmpty()) {
            Iterator<Entry<Arrow, Cardinality>> aCarIterator = cd_arrowCardinalities.entrySet().iterator();
            if (aCarIterator.hasNext()) {
                Entry<Arrow, Cardinality> arrowCardinality = aCarIterator.next();
                printArrowCardinality(sb, arrowCardinality.getKey(), arrowCardinality.getValue());
                while (aCarIterator.hasNext()) {
                	arrowCardinality = aCarIterator.next();
                	printArrowCardinality(sb.append(", "), arrowCardinality.getKey(), arrowCardinality.getValue());
                }
            }
        }
        sb.append(']');
    }
	
	
    private void printId(Appendable sb) throws IOException {
        switch (getPrimaryCount()) {
            case 2:
                sb.append(CDCarTextBinaryId);
                break;
            default:
                sb.append(CDCarTextId);
                break;
        }
    }
    
    
	@Override
	public boolean equals(Object other) {
		if (other instanceof CarCDiagram){
			CarCDiagram carCD = (CarCDiagram) other;
			return (super.equals(carCD) && 
					equal(get_cd_ArrowCardinalities() == null ? null : get_cd_ArrowCardinalities().entrySet(), 
	                		carCD.get_cd_ArrowCardinalities() == null ? null : carCD.get_cd_ArrowCardinalities().entrySet()));
		}
		return false;
	}

	
	
	@Override
	public boolean isSEquivalentTo(SpiderDiagram other) {
	      if (equals(other)) {
	          return true;
	      }
	      if (other instanceof CarCDiagram) {
	         CarCDiagram carCDiagram = (CarCDiagram) other;
	        
	         return (super.isSEquivalentTo(other) && 					
	        		equal(get_cd_ArrowCardinalities() == null ? null : get_cd_ArrowCardinalities().entrySet(), 
	        				carCDiagram.get_cd_ArrowCardinalities()  == null ? null : carCDiagram.get_cd_ArrowCardinalities() .entrySet()));
	      }
	      return false;
	}
    


}
