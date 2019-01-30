package speedith.core.lang;

import static propity.util.Sets.equal;
import static speedith.core.i18n.Translations.i18n;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

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
	

	CarCDiagram(ArrayList<PrimarySpiderDiagram> primaries, TreeSet<Arrow> arrows,TreeMap<Arrow,Cardinality> arrowCardinalities) {
		super(primaries, arrows);
		this.cd_arrowCardinalities  = arrowCardinalities  == null ? new TreeMap<Arrow,Cardinality>() : arrowCardinalities;
	}
	
	
    CarCDiagram(Collection<PrimarySpiderDiagram> primaries, Collection<Arrow> arrows, Map<Arrow,Cardinality> arrowCardinalities) {
    	this(primaries == null ? null : new ArrayList<>(primaries),
    			arrows == null ? null : new TreeSet<>(arrows),
    			arrowCardinalities  == null ? null : new TreeMap<Arrow,Cardinality>(arrowCardinalities));
    }
    
	public SortedMap<Arrow,Cardinality> get_cd_ArrowCardinalities() {
        return Collections.unmodifiableSortedMap(cd_arrowCardinalities);
    }
	
	
	public TreeMap<Arrow, Cardinality> get_cd_ArrowCardinalitiesMod() {
    return cd_arrowCardinalities;
    }
	
	
	public CarCDiagram add_cd_ArrowCardinality(Arrow arrow, Cardinality cardinality) {
    	TreeMap<Arrow,Cardinality> newArrowCardinalities = get_cd_ArrowCardinalitiesMod();
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
	        sb.append('}');
		
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
