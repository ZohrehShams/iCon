package speedith.core.lang;

import static propity.util.Sets.equal;
import static speedith.core.i18n.Translations.i18n;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JOptionPane;

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


	
	CompleteCOPDiagram(TreeSet<String> spiders, TreeMap<String, Region> habitats, TreeSet<Zone> shadedZones,
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
	      super(spiders == null ? null : new TreeSet<>(spiders),
	              habitats == null ? null : new TreeMap<>(habitats),
	              shadedZones == null ? null : new TreeSet<>(shadedZones),
	              presentZones == null ? null : new TreeSet<>(presentZones),
	              arrows == null ? null : new TreeSet<>(arrows),
	              spiderLabels == null ? null : new TreeMap<>(spiderLabels),
	              curveLabels == null ? null : new TreeMap<>(curveLabels),
	              arrowCardinalities == null ? null : new TreeMap<> (arrowCardinalities));
	      
	      this.spiderComparators  = spiderComparators  == null ? null : new TreeSet<SpiderComparator>(spiderComparators);	
	}
	
	
	public SortedSet<SpiderComparator> getSpiderComparators() {
        return Collections.unmodifiableSortedSet(spiderComparators);
    }
	
	
	public TreeSet<SpiderComparator> getSpiderComparatorsMod() {
        return spiderComparators;
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
		
		if (spiderName == null || spiderName.isEmpty()){
			JOptionPane.showMessageDialog(null,"The spider name cannot be null or empty.","Input error",JOptionPane.ERROR_MESSAGE);
		}
		
	    TreeMap<String, Region> newHabitats = (getHabitatsMod() == null) ? new TreeMap<String, Region>() : new TreeMap<>(getHabitatsMod());
	    newHabitats.put(spiderName, habitat);
	    
	    TreeMap<String, String> spiderLabels = (getSpiderLabels() == null) ? new TreeMap<String, String>() : new TreeMap<>(getSpiderLabelsMod());
	    spiderLabels.put(spiderName, spiderLabel);
	    
	    TreeSet<String> newSpiders = new TreeSet<>(getSpidersMod()); 
	    
	    if (getSpidersMod() != null) {
	    	if (getSpidersMod().contains(spiderName)) {
	    		JOptionPane.showMessageDialog(null,"The spider has to have a fresh name.","Input error",JOptionPane.ERROR_MESSAGE);
	    	}else{
	    		newSpiders.add(spiderName);
	    	}    	
	    }else{
	    	newSpiders = new TreeSet<>();
	    	newSpiders.add(spiderName);
	    }
	    
	    //Zohreh: the new spider has unknown equality to all existing spiders
	    TreeSet<SpiderComparator> newSpiderComparators= (getSpiderComparators() == null) ? new TreeSet<SpiderComparator>() : new TreeSet<>(getSpiderComparators());    	
    	TreeSet<String> exixstingSpidersInHabitat = new TreeSet<String>();
    	for(Zone zone : habitat.sortedZones()){
    		exixstingSpidersInHabitat.addAll(getSpidersInZone(zone));
    	}
    	for(String spider: exixstingSpidersInHabitat){
    		newSpiderComparators.add(new SpiderComparator(spiderName,spider,"?"));
    	}
	    
	    return new CompleteCOPDiagram(
	            newSpiders,
	            newHabitats,
	            getShadedZones(),
	            getPresentZones(),
	            getArrows(),
	            spiderLabels,
	            getCurveLabels(),
	            getArrowCardinalities(),
	            newSpiderComparators
	    		);	
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
