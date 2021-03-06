package speedith.core.lang.cop;

import static propity.util.Sets.equal;
import static speedith.core.i18n.Translations.i18n;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.Region;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.Zone;

/**
 *LUCOPDiagrams didn't allow cardinality for arrows. LUCarCOPDiagram stands for a COP diagram that accommodate
 *both labelled/unlabelled spiders/curves and arrows with cardinality.
 *@author Zohreh Shams
 */
public class LUCarCOPDiagram extends LUCOPDiagram{

	private static final long serialVersionUID = -3191414047260849707L;
	public static final String TextLUCarCOPId = "LUCarCOP";
	public static final String SDTextArrowCardinalitiesAttribute = "arrowCar";
	
	
	//A map from arrows to their cardinalities
	private  TreeMap<Arrow,Cardinality>  arrowCardinalities;

	LUCarCOPDiagram(TreeSet<String> spiders, TreeMap<String, Region> habitats, TreeSet<Zone> shadedZones,
	TreeSet<Zone> presentZones, TreeSet<Arrow> arrows, TreeMap<String,String> spiderLabels, 
	TreeMap<String,String> curveLabels, TreeMap<Arrow,Cardinality> arrowCardinalities){
		super(spiders, habitats, shadedZones, presentZones,arrows, spiderLabels, curveLabels);
		this.arrowCardinalities  = arrowCardinalities  == null ? new TreeMap<Arrow,Cardinality>() : arrowCardinalities;
		for (Arrow arrow : getArrows()){
			if(this.arrowCardinalities.get(arrow) != null){
				arrow.setCardinality(this.arrowCardinalities.get(arrow));
			}
		}
	}
	
	
	LUCarCOPDiagram(Collection<String> spiders, Map<String, Region> habitats, Collection<Zone> shadedZones, 
		  Collection<Zone> presentZones, Collection<Arrow> arrows, Map<String,String> spiderLabels, 
		  Map<String,String> curveLabels,Map<Arrow,Cardinality> arrowCardinalities){
		
		this(spiders == null ? null : new TreeSet<>(spiders),
				  habitats == null ? null : new TreeMap<>(habitats),
	              shadedZones == null ? null : new TreeSet<>(shadedZones),
	              presentZones == null ? null : new TreeSet<>(presentZones),
	              arrows == null ? null : new TreeSet<>(arrows),
	              spiderLabels == null ? null : new TreeMap<>(spiderLabels),
	              curveLabels == null ? null : new TreeMap<>(curveLabels),
	              arrowCardinalities  == null ? null : new TreeMap<Arrow,Cardinality>(arrowCardinalities));	
	}
	
	
    public static LUCarCOPDiagram createLUCarCOPDiagram(Collection<String> spiders, Map<String, Region> habitats, Collection<Zone> shadedZones,
				Collection<Zone> presentZones, Collection<Arrow> arrows, Map<String,String> spiderLabels, Map<String,String> curveLabels,
				Map<Arrow,Cardinality> arrowCardinalities){
		    if ((spiders == null || spiders instanceof TreeSet)
		        && (habitats == null || habitats instanceof TreeMap)
		        && (shadedZones == null || shadedZones instanceof TreeSet)
		        && (presentZones == null || presentZones instanceof TreeSet)
		        && (arrows == null || arrows instanceof TreeSet)
		        && (spiderLabels == null || spiderLabels instanceof TreeMap)
		        && (curveLabels == null || curveLabels instanceof TreeMap)
		        && (arrowCardinalities == null || arrowCardinalities instanceof TreeMap)) {
		      return new LUCarCOPDiagram(spiders == null ? null : (TreeSet<String>) spiders,
		                             habitats == null ? null : (TreeMap<String, Region>) habitats,
		                             shadedZones == null ? null : (TreeSet<Zone>) shadedZones,
		                             presentZones == null ? null : (TreeSet<Zone>) presentZones,
		                             arrows == null ? null : (TreeSet<Arrow>) arrows,
		                             spiderLabels == null ? null : (TreeMap<String, String>) spiderLabels,
		                             curveLabels == null ? null : (TreeMap<String, String>) curveLabels,
		                             arrowCardinalities == null ? null : (TreeMap<Arrow,Cardinality>) arrowCardinalities);
		    } else {
		      TreeSet<String> spidersCopy = spiders == null ? null : new TreeSet<>(spiders);
		      TreeMap<String, Region> habitatsCopy = habitats == null ? null : new TreeMap<>(habitats);
		      TreeSet<Zone> shadedZonesCopy = shadedZones == null ? null : new TreeSet<>(shadedZones);
		      TreeSet<Zone> presentZonesCopy = presentZones == null ? null : new TreeSet<>(presentZones);
		      TreeSet<Arrow> arrowsCopy = arrows == null ? null : new TreeSet<>(arrows);
		      TreeMap<String,String> spiderLabelsCopy = spiderLabels == null ? null : new TreeMap<>(spiderLabels);
		      TreeMap<String,String> curveLabelsCopy = curveLabels == null ? null : new TreeMap<>(curveLabels);
		      TreeMap<Arrow,Cardinality> arrowCardinalitiesCopy = arrowCardinalities == null ? null : new TreeMap<>(arrowCardinalities);
		      return new LUCarCOPDiagram(spidersCopy, habitatsCopy, shadedZonesCopy, presentZonesCopy, arrowsCopy, 
		    		  spiderLabelsCopy, curveLabelsCopy, arrowCardinalitiesCopy);
		    }
		  }
	
	
	 public SortedMap<Arrow,Cardinality> getArrowCardinalities() {
		 return Collections.unmodifiableSortedMap(arrowCardinalities);
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
	      return new LUCarCOPDiagram(
	              getSpiders(),
	              getHabitats(),
	              newShadedZones,
	              getPresentZones(),
	              getArrows(),
	              getSpiderLabels(),
	              getCurveLabels(),
	              arrowCardinalities);
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
	      
	      return new LUCarCOPDiagram(
	              newSpiders,
	              newHabitats,
	              getShadedZones(),
	              getPresentZones(),
	              getArrows(),
	              newSpiderLabels,
	              getCurveLabels(),
	              arrowCardinalities);
	  }
	  
		
	  
		@Override
		public COPDiagram deleteSpider(String... spiders) {
			  TreeSet<String> newSpiders = new TreeSet<>(getSpiders());
			  TreeMap<String, Region> newHabitats = new TreeMap<>(getHabitats());
			  TreeSet<Arrow> newArrows = new TreeSet<>(getArrows());
			  TreeMap<String,String> newSpiderLabels = new TreeMap<>(getSpiderLabels());
			  TreeMap<Arrow,Cardinality> newArrowCardinalities = new TreeMap<>(arrowCardinalities);
			  
			  
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
			      
			  }

		      return new LUCarCOPDiagram(
		              newSpiders,
		              newHabitats,
		              getShadedZones(),
		              getPresentZones(),
		              newArrows,
		              newSpiderLabels,
		              getCurveLabels(),
		              newArrowCardinalities);
		  }
		
		
		@Override
		public COPDiagram addArrow(Arrow arrow) {
			TreeSet<Arrow> newArrows = new TreeSet<Arrow>(getArrows());
			if (arrow != null){
				if ((! containsArrow(arrow)) && (validArrow(arrow))) {
					newArrows.add(arrow);
				}
			}
	    	return new LUCarCOPDiagram(getSpiders(), getHabitats(), 
	    			getShadedZones(), getPresentZones(), newArrows,getSpiderLabels(),getCurveLabels(),getArrowCardinalities());
		}

		
		
		
		public LUCarCOPDiagram addArrowCardinality(Arrow arrow, Cardinality cardinality) {
	    	TreeMap<Arrow,Cardinality> newArrowCardinalities = new TreeMap<Arrow,Cardinality>(getArrowCardinalities());
	    	if (arrow != null){
	    		newArrowCardinalities.put(arrow, cardinality);
	    	}
	    	return new LUCarCOPDiagram(getSpiders(), getHabitats(), getShadedZones(), getPresentZones(), getArrows(),
	    			getSpiderLabels(),getCurveLabels(),newArrowCardinalities);
	    }
		
		
		
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
		  
		  
	      return new LUCarCOPDiagram(
	              getSpiders(),
	              getHabitats(),
	              getShadedZones(),
	              getPresentZones(),
	              getArrows(),
	              getSpiderLabels(),
	              getCurveLabels(),
	              newArrowCardinalities);  
	  }
	  
	  public LUCarCOPDiagram deleteCardinality(Arrow arrow){
		  TreeMap<Arrow,Cardinality>  newArrowCardinalities = new TreeMap<>(getArrowCardinalities());
		  
		  if (! getArrows().contains(arrow)){
              throw new IllegalArgumentException("The arrow does not exist in the diagram.");
		  }
		  
		  if (! getArrowCardinalities().containsKey(arrow)){
              throw new IllegalArgumentException("The arrow should have a cardinality.");
		  }
		  
		  newArrowCardinalities.remove(arrow);
		  
		  
	      return new LUCarCOPDiagram(
	              getSpiders(),
	              getHabitats(),
	              getShadedZones(),
	              getPresentZones(),
	              getArrows(),
	              getSpiderLabels(),
	              getCurveLabels(),
	              newArrowCardinalities);  
	  }
	  
	
	protected boolean areArrowCardinalitiesValid(){
		if (arrowCardinalities == null || arrowCardinalities.size() == 0){
			return true;
		}else {
			for (Arrow arrow : arrowCardinalities.keySet()){
				if (! getArrows().contains(arrow)){
					return false;
				}
			}
		}
		return true;
	}
	
	
	@Override
	protected boolean checkValid() {
		return (super.checkValid()
		             && areArrowCardinalitiesValid());
	}   
	
	
    private static void printArrowCardinality(Appendable sb, Arrow arrow, Cardinality cardinality) throws IOException {
        sb.append('(');
        arrow.toString(sb);
        sb.append(", ");
        cardinality.toString(sb);
        sb.append(')');
    }
    
	
	protected void printArrowCardinalities(Appendable sb) throws IOException {
        sb.append(SDTextArrowCardinalitiesAttribute).append(" = ");
        sb.append('[');
        if (arrowCardinalities != null && !arrowCardinalities.isEmpty()) {
            Iterator<Entry<Arrow, Cardinality>> aCarIterator = arrowCardinalities.entrySet().iterator();
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
	
	
	@Override
    public void toString(Appendable sb) throws IOException {
        if (sb == null) {
            throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "sb"));
        }
        sb.append(TextLUCarCOPId);
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
        sb.append('}');
    }
	
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof LUCarCOPDiagram){
			LUCarCOPDiagram luCarCOP = (LUCarCOPDiagram) other;
			return (super.equals(luCarCOP) && 
					equal(getArrowCardinalities() == null ? null : getArrowCardinalities().entrySet(), 
	                		luCarCOP.getArrowCardinalities() == null ? null : luCarCOP.getArrowCardinalities().entrySet()));
		}
		return false;
	}
	
	
	@Override
	public boolean isSEquivalentTo(SpiderDiagram other) {
	      if (equals(other)) {
	          return true;
	      }
	      if (other instanceof LUCarCOPDiagram) {
	         LUCarCOPDiagram luCarCOP = (LUCarCOPDiagram) other;
	        
	         return (super.isSEquivalentTo(other) && 					
	        		equal(getArrowCardinalities() == null ? null : getArrowCardinalities().entrySet(), 
             		luCarCOP.getArrowCardinalities() == null ? null : luCarCOP.getArrowCardinalities().entrySet()));
	      }
	      return false;
	}




}



