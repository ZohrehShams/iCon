package speedith.core.lang;

import static propity.util.Sets.equal;
import static speedith.core.i18n.Translations.i18n;

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

	public static final String CDTextBinaryId = "BinaryCarCD";
	public static final String CDTextArrowCardinalitiesAttribute = "cd_arrowCar";
	
	
	//A map from arrows to their cardinalities
	private  TreeMap<Arrow,Cardinality>  cd_arrowCardinalities;
	
	
	CarCDiagram(ArrayList<PrimarySpiderDiagram> primaries, TreeSet<Arrow> arrows,TreeMap<Arrow,Cardinality> arrowCardinalities) {
		super(primaries, arrows);
		this.cd_arrowCardinalities  = arrowCardinalities  == null ? new TreeMap<Arrow,Cardinality>() : arrowCardinalities;
	}
	
	
    CarCDiagram(Collection<PrimarySpiderDiagram> primaries, Collection<Arrow> arrows, Map<Arrow,Cardinality> arrowCardinalities) {
    	this(primaries == null ? null : new ArrayList<>(primaries),
    			arrows == null ? null : new TreeSet<>(arrows),
    			arrowCardinalities  == null ? null : new TreeMap<Arrow,Cardinality>(arrowCardinalities));
    	
//    	super(primaries, arrows);
//		this.cd_arrowCardinalities  = arrowCardinalities  == null ? null : new TreeMap<Arrow,Cardinality>(arrowCardinalities );	
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
	public boolean equals(Object other) {
		if (other instanceof CarCDiagram){
			CarCDiagram carCD = (CarCDiagram) other;
			return (super.equals(carCD) && 
					equal(get_cd_ArrowCardinalities() == null ? null : get_cd_ArrowCardinalities().entrySet(), 
	                		carCD.get_cd_ArrowCardinalities() == null ? null : carCD.get_cd_ArrowCardinalities().entrySet()));
		}
		return false;
	}

	
    


}
