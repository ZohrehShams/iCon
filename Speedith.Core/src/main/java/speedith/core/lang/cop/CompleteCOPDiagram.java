package speedith.core.lang.cop;

import static propity.util.Sets.equal;
import static speedith.core.i18n.Translations.i18n;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.Region;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.Zone;

/**
 *LUCarCOPDiagrams didn't capture the equality and unknown equality between individuals (spiders). 
 *CompleteCOPDiagram stands for a LUCarCOPDiagram that accommodates this feature.
 *@author Zohreh Shams
 */
public class CompleteCOPDiagram extends LUCarCOPDiagram {

	private static final long serialVersionUID = 457884322115203832L;
	public static final String TextCompleteCOPId = "CompleteCOP";
	public static final String SDTextSpiderComparatorAttribute = "spiderEquality";
	private TreeSet<SpiderComparator> spiderComparators;


	
	public CompleteCOPDiagram(TreeSet<String> spiders, TreeMap<String, Region> habitats, TreeSet<Zone> shadedZones,
	TreeSet<Zone> presentZones, TreeSet<Arrow> arrows, TreeMap<String,String> spiderLabels, 
	TreeMap<String,String> curveLabels, TreeMap<Arrow,Cardinality> arrowCardinalities, 
	TreeSet<SpiderComparator> spiderComparators){
		super(spiders, habitats, shadedZones, presentZones,arrows, spiderLabels, curveLabels,arrowCardinalities);
		this.spiderComparators  = spiderComparators  == null ? new TreeSet<SpiderComparator>() : spiderComparators;
	}
	
	
	CompleteCOPDiagram(Collection<String> spiders, Map<String, Region> habitats, Collection<Zone> shadedZones, 
		  Collection<Zone> presentZones, Collection<Arrow> arrows, Map<String,String> spiderLabels, 
		  Map<String,String> curveLabels,Map<Arrow,Cardinality> arrowCardinalities, 
		  Collection<SpiderComparator> spiderComparators){
	      this(spiders == null ? null : new TreeSet<>(spiders),
	              habitats == null ? null : new TreeMap<>(habitats),
	              shadedZones == null ? null : new TreeSet<>(shadedZones),
	              presentZones == null ? null : new TreeSet<>(presentZones),
	              arrows == null ? null : new TreeSet<>(arrows),
	              spiderLabels == null ? null : new TreeMap<>(spiderLabels),
	              curveLabels == null ? null : new TreeMap<>(curveLabels),
	              arrowCardinalities == null ? null : new TreeMap<> (arrowCardinalities),
	              spiderComparators  == null ? null : new TreeSet<SpiderComparator>(spiderComparators));
	}
	
	
	public SortedSet<SpiderComparator> getSpiderComparators() {
        return Collections.unmodifiableSortedSet(spiderComparators);
    }
	
	
	public CompleteCOPDiagram updateSpiderComparators( Collection<SpiderComparator> spiderComparators){
		return new CompleteCOPDiagram(
	              getSpiders(),
	              getHabitats(),
	              getShadedZones(),
	              getPresentZones(),
	              getArrows(),
	              getSpiderLabels(),
	              getCurveLabels(),
	              getArrowCardinalities(),
	              spiderComparators);
	}
	
	@Override
	public PrimarySpiderDiagram addShading(Collection<Zone> zones) {
		TreeSet<Zone> newShadedZones = new TreeSet<>(getShadedZones());
		for (Zone newShadedZone : zones) {
			if (!newShadedZone.isValid(getAllContours())) {
	              throw new IllegalArgumentException("The zone '" + newShadedZone + "' is not valid in this diagram.");
			}	          		
		}
		
		newShadedZones.addAll(zones);
	
		return new CompleteCOPDiagram(
	              getSpiders(),
	              getHabitats(),
	              newShadedZones,
	              getPresentZones(),
	              getArrows(),
	              getSpiderLabels(),
	              getCurveLabels(),
	              getArrowCardinalities(),
	              spiderComparators);
		
	}
	
	
	
	@Override
	public LUCOPDiagram addLUSpider(String spiderName, Region habitat, String spiderLabel) {

		  TreeMap<String, Region> newHabitats = (getHabitats() == null) ? new TreeMap<String, Region>() : new TreeMap<>(getHabitats());
	      newHabitats.put(spiderName, habitat);
	      
	      TreeMap<String, String> newSpiderLabels = (getSpiderLabels() == null) ? new TreeMap<String, String>() : new TreeMap<>(getSpiderLabels());
	      newSpiderLabels.put(spiderName, spiderLabel);
	      
	      TreeSet<String> newSpiders;
	        if (spiders != null) {
	            if (spiders.contains(spiderName)) {
	                newSpiders = spiders;
	            } else {
	                newSpiders = new TreeSet<>(spiders); 
	                newSpiders.add(spiderName);
	            }
	        } else {
	            newSpiders = new TreeSet<>();
	            newSpiders.add(spiderName);
	        }
	    
	    //The new spider has unknown equality to all existing spiders
	    TreeSet<SpiderComparator> newSpiderComparators= (getSpiderComparators() == null) ? new TreeSet<SpiderComparator>() : new TreeSet<>(getSpiderComparators());    	
    	TreeSet<String> exixstingSpidersInHabitat = new TreeSet<String>();
    	for(Zone zone : habitat.sortedZones()){
    		exixstingSpidersInHabitat.addAll(getSpidersInZone(zone));
    	}
    	for(String spider: exixstingSpidersInHabitat){
    		if(! spiderName.equals(spider)){
    		newSpiderComparators.add(new SpiderComparator(spiderName,spider,"?"));}
    	}
	    
	    return new CompleteCOPDiagram(
	            newSpiders,
	            newHabitats,
	            getShadedZones(),
	            getPresentZones(),
	            getArrows(),
	            newSpiderLabels,
	            getCurveLabels(),
	            getArrowCardinalities(),
	            newSpiderComparators
	    		);	
	}
	
	
	@Override
	public COPDiagram deleteSpider(String... spiders) {
		  TreeSet<String> newSpiders = new TreeSet<>(getSpiders());
		  TreeMap<String, Region> newHabitats = new TreeMap<>(getHabitats());
		  TreeSet<Arrow> newArrows = new TreeSet<>(getArrows());
		  TreeMap<String,String> newSpiderLabels = new TreeMap<>(getSpiderLabels());
		  TreeMap<Arrow,Cardinality> newArrowCardinalities = new TreeMap<>(getArrowCardinalities());
		  TreeSet<SpiderComparator> newSpiderComparators = new TreeSet<>(spiderComparators);
		  
		  
		  for(String spider : spiders){
			  newSpiders.remove(spider);
			  newHabitats.remove(spider);
			  
		      for (Arrow arrow : getArrows()){
		          if(spider.equals(arrow.arrowSource()) || spider.equals(arrow.arrowTarget())){
		      		newArrows.remove(arrow);
		      		newArrowCardinalities.remove(arrow);
		          }
		        }
		     
		      newSpiderLabels.remove(spider); 
		      
		      for (SpiderComparator sc : spiderComparators){
		    	  if(spider.equals(sc.getComparable1()) ||spider.equals(sc.getComparable2())){
		    		  newSpiderComparators.remove(sc);
	            }
	          }
		      
		  }

	      return new CompleteCOPDiagram(
	              newSpiders,
	              newHabitats,
	              getShadedZones(),
	              getPresentZones(),
	              newArrows,
	              newSpiderLabels,
	              getCurveLabels(),
	              newArrowCardinalities,
	              newSpiderComparators);
	  }
	
	
	
	@Override
	public COPDiagram addArrow(Arrow arrow) {
	    	TreeSet<Arrow> newArrows = new TreeSet<Arrow>(getArrows());
	    	if (arrow != null){
	    		if ((! containsArrow(arrow)) && (validArrow(arrow))) {
	    			newArrows.add(arrow);
	    		}
	    	}
	    	return new CompleteCOPDiagram(getSpiders(), getHabitats(),getShadedZones(), getPresentZones(), 
	    			newArrows,getSpiderLabels(),getCurveLabels(),getArrowCardinalities(),spiderComparators);
	 }
	
	
	public COPDiagram removeArrow(Arrow arrow) {
    	TreeSet<Arrow> newArrows = new TreeSet<Arrow>(getArrows());
		TreeMap<Arrow,Cardinality> newCardinalities = new TreeMap<Arrow,Cardinality>(getArrowCardinalities());
    	if ((arrow != null) && (containsArrow(arrow))) {
    		newArrows.remove(arrow);
    		newCardinalities.remove(arrow);
    	}
    	return new CompleteCOPDiagram(getSpiders(), getHabitats(),getShadedZones(), getPresentZones(), 
    			newArrows,getSpiderLabels(),getCurveLabels(),newCardinalities,spiderComparators);
	}
	
	
	
	
	@Override
	public LUCarCOPDiagram addCardinality(Arrow arrow,Cardinality cardinality){
		  TreeMap<Arrow,Cardinality>  newArrowCardinalities = new TreeMap<>(getArrowCardinalities());
		  
		  if (! getArrows().contains(arrow)){
            throw new IllegalArgumentException("The arrow does not exist in the diagram.");
		  }
		  
		  if (getArrowCardinalities().containsKey(arrow)){
            throw new IllegalArgumentException("The arrow should not have a cardinality.");
		  }
		  
		  arrow.setCardinality(cardinality);
		  newArrowCardinalities.put(arrow,cardinality);
		  
		  
	      return new CompleteCOPDiagram(
	              getSpiders(),
	              getHabitats(),
	              getShadedZones(),
	              getPresentZones(),
	              getArrows(),
	              getSpiderLabels(),
	              getCurveLabels(),
	              newArrowCardinalities,
	              spiderComparators);  
	  }
	
	
	@Override
	public LUCarCOPDiagram deleteCardinality(Arrow arrow){
		  TreeMap<Arrow,Cardinality>  newArrowCardinalities = new TreeMap<>(getArrowCardinalities());
		  
		  if (! getArrows().contains(arrow)){
              throw new IllegalArgumentException("The arrow does not exist in the diagram.");
		  }
		  
		  if (! getArrowCardinalities().containsKey(arrow)){
              throw new IllegalArgumentException("The arrow should have a cardinality.");
		  }
		  
		  newArrowCardinalities.remove(arrow);
		  
		  
	      return new CompleteCOPDiagram(
	              getSpiders(),
	              getHabitats(),
	              getShadedZones(),
	              getPresentZones(),
	              getArrows(),
	              getSpiderLabels(),
	              getCurveLabels(),
	              newArrowCardinalities,
	              spiderComparators);  
	  }
	  
	  
	public boolean validSpiderComparator(SpiderComparator sc){
		if ((getSpiders().contains(sc.getComparable1())) &&
				(getSpiders().contains(sc.getComparable2())) &&
						((sc.getQuality().equals("="))||(sc.getQuality().equals("?")))){
			return true;	
		}else
			return false;
	}
	
	
	private boolean areSpiderComparatorsValid(){
		for (SpiderComparator sc : spiderComparators){
			if(! validSpiderComparator(sc)){
				return false;
			}
		}
		return true;
	}
	
	
	@Override
	protected boolean checkValid() {
		return (super.checkValid()
		             && areSpiderComparatorsValid());
	}   
	
	
	 
	  
	
	protected void printSpiderComparators(Appendable sb) {
	      try {
	          sb.append(SDTextSpiderComparatorAttribute).append(" = ");
	          printSpiderComparatorList(sb, spiderComparators);
	      } catch (IOException ex) {
	          throw new RuntimeException(ex);
	      }
	  }
	
	
	private void printSpiderComparatorList(Appendable sb, Collection<SpiderComparator> spiderComparators) throws IOException {
	      sb.append('[');
	      if (spiderComparators != null) {
	          Iterator<SpiderComparator> spIterator = spiderComparators.iterator();
	          if (spIterator.hasNext()) {
	              spIterator.next().toString(sb);
	              while (spIterator.hasNext()) {
	                  spIterator.next().toString(sb.append(", "));
	              }
	          }
	      }
	      sb.append(']');
	 }
	
	
	
	@Override
    public void toString(Appendable sb) throws IOException {
        if (sb == null) {
            throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "sb"));
        }
        sb.append(TextCompleteCOPId);
        sb.append(" {");
        printSpiders(sb);
        sb.append(", ");
        printHabitats(sb);
        sb.append(", ");
        printShadedZones(sb);
        if (getPresentZones() != null) {
            printPresentZones(sb.append(", "));
        }
        sb.append(", ");
        printArrows(sb);
        sb.append(", ");
        printSpiderLabels(sb);
        sb.append(", ");
        printCurveLabels(sb);
        sb.append(", ");
        printArrowCardinalities(sb);
        sb.append(", ");
        printSpiderComparators(sb);
        sb.append('}');
    }
	
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof CompleteCOPDiagram){
			CompleteCOPDiagram cCOP = (CompleteCOPDiagram) other;
			return (super.equals(cCOP) && 
					equal(getSpiderComparators() == null ? null : getSpiderComparators(), 
							cCOP.getSpiderComparators() == null ? null : cCOP.getSpiderComparators()));
		}
		return false;
	}
	
	
	@Override
	public boolean isSEquivalentTo(SpiderDiagram other) {
	      if (equals(other)) {
	          return true;
	      }
	      if (other instanceof CompleteCOPDiagram) {
	    	  CompleteCOPDiagram cCOP = (CompleteCOPDiagram) other;
	    	  
	         return (super.isSEquivalentTo(other) && 					
	        		equal(getSpiderComparators() == null ? null : getSpiderComparators(), 
             		cCOP.getSpiderComparators() == null ? null : cCOP.getSpiderComparators()));
	      }
	      return false;
	}
	

		
	  

	  
	

	
}
