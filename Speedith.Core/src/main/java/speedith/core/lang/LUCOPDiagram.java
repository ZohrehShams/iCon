package speedith.core.lang;

import static propity.util.Sets.equal;
import static speedith.core.i18n.Translations.i18n;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.swing.JOptionPane;
/**
 *COPDiagrams didn't allow labels for spiders. Assuming each spider has a unique name with which it is 
 *recognised, when we have both labelled and unlabelled spider, we need a label as well as name that helps 
 *distinguishing between labelled and unlabelled spiders. Same applies to the curves. LU stands for LabelledUnlabelled and it has:
 *two elements more than COP, which are two maps that allow mapping spiders and curves to their labels. 
 *one element more than LUSCOP, which is a map that allows mapping spider names to labels. 
 *@author Zohreh Shams
 */
public class LUCOPDiagram extends COPDiagram {

	//private static final long serialVersionUID = 2730965664523343438L;
	private static final long serialVersionUID = -7446718786338357310L;
	public static final String SDTextSpiderLabelsAttribute = "spiderLabels";
	public static final String SDTextCurveLabelsAttribute = "curveLabels";
	public static final String TextLUCOPId = "LUCOP";
	
	//A map from spiders unique name to optional labels
	protected  TreeMap<String,String>  spiderLabels;
	//A map from curves unique labels to optional names
	protected  TreeMap<String,String>  curveLabels;
	
	
	LUCOPDiagram(TreeSet<String> spiders, TreeMap<String, Region> habitats, TreeSet<Zone> shadedZones,
	TreeSet<Zone> presentZones, TreeSet<Arrow> arrows, TreeMap<String,String> spiderLabels, TreeMap<String,String> curveLabels){
		super(spiders, habitats, shadedZones, presentZones,arrows);
		this.spiderLabels  = spiderLabels  == null ? new TreeMap<String,String>() : spiderLabels;
		this.curveLabels  = curveLabels  == null ? new TreeMap<String,String>() : curveLabels;
	}
	
	
	LUCOPDiagram(Collection<String> spiders, Map<String, Region> habitats, Collection<Zone> shadedZones, 
		  Collection<Zone> presentZones, Collection<Arrow> arrows, Map<String,String> spiderLabels, 
		  Map<String,String> curveLabels){
	      super(spiders == null ? null : new TreeSet<>(spiders),
	              habitats == null ? null : new TreeMap<>(habitats),
	              shadedZones == null ? null : new TreeSet<>(shadedZones),
	              presentZones == null ? null : new TreeSet<>(presentZones),
	              arrows == null ? null : new TreeSet<>(arrows));
	      
	      this.spiderLabels = spiderLabels == null ? null : new TreeMap<String,String>(spiderLabels);	
	      this.curveLabels = curveLabels == null ? null : new TreeMap<String,String>(curveLabels);	

	}
	
	
//	public static LUCOPDiagram createLUCOPDiagram(TreeSet<String> spiders, TreeMap<String, Region> habitats, TreeSet<Zone> shadedZones,
//				TreeSet<Zone> presentZones, TreeSet<Arrow> arrows, TreeMap<String,String> spiderLabels,TreeMap<String,String> curveLabels){
//		return new LUCOPDiagram(spiders, habitats, shadedZones,presentZones, arrows, spiderLabels, curveLabels);
//	}
			
			
	
//	public static LUCOPDiagram createLUCOPDiagramNOCopy(Collection<String> spiders, Map<String, Region> habitats, Collection<Zone> shadedZones,
//			Collection<Zone> presentZones, Collection<Arrow> arrows, Map<String,String> spiderLabels, Map<String,String> curveLabels){
//		return new LUCOPDiagram(spiders, habitats, shadedZones,presentZones, arrows, spiderLabels,curveLabels);
//	}
	
	
	
    public static LUCOPDiagram createLUCOPDiagram(Collection<String> spiders, Map<String, Region> habitats, Collection<Zone> shadedZones,
				Collection<Zone> presentZones, Collection<Arrow> arrows, Map<String,String> spiderLabels, Map<String,String> curveLabels){
		    if ((spiders == null || spiders instanceof TreeSet)
		        && (habitats == null || habitats instanceof TreeMap)
		        && (shadedZones == null || shadedZones instanceof TreeSet)
		        && (presentZones == null || presentZones instanceof TreeSet)
		        && (arrows == null || arrows instanceof TreeSet)
		        && (spiderLabels == null || spiderLabels instanceof TreeMap)
		        && (curveLabels == null || curveLabels instanceof TreeMap)) {
		      return new LUCOPDiagram(spiders == null ? null : (TreeSet<String>) spiders,
		                             habitats == null ? null : (TreeMap<String, Region>) habitats,
		                             shadedZones == null ? null : (TreeSet<Zone>) shadedZones,
		                             presentZones == null ? null : (TreeSet<Zone>) presentZones,
		                             arrows == null ? null : (TreeSet<Arrow>) arrows,
		                             spiderLabels == null ? null : (TreeMap<String, String>) spiderLabels,
		                             curveLabels == null ? null : (TreeMap<String, String>) curveLabels);
		    } else {
		      TreeSet<String> spidersCopy = spiders == null ? null : new TreeSet<>(spiders);
		      TreeMap<String, Region> habitatsCopy = habitats == null ? null : new TreeMap<>(habitats);
		      TreeSet<Zone> shadedZonesCopy = shadedZones == null ? null : new TreeSet<>(shadedZones);
		      TreeSet<Zone> presentZonesCopy = presentZones == null ? null : new TreeSet<>(presentZones);
		      TreeSet<Arrow> arrowsCopy = arrows == null ? null : new TreeSet<>(arrows);
		      TreeMap<String,String> spiderLabelsCopy = spiderLabels == null ? null : new TreeMap<>(spiderLabels);
		      TreeMap<String,String> curveLabelsCopy = curveLabels == null ? null : new TreeMap<>(curveLabels);
		      return new LUCOPDiagram(spidersCopy, habitatsCopy, shadedZonesCopy, presentZonesCopy, arrowsCopy, 
		    		  spiderLabelsCopy, curveLabelsCopy);
		    }
		  }
	
	
	public SortedMap<String,String> getSpiderLabels() {
	        return Collections.unmodifiableSortedMap(spiderLabels);
	    }
	
	public SortedMap<String,String> getCurveLabels() {
        return Collections.unmodifiableSortedMap(curveLabels);
    }
	
	
	public TreeMap<String,String> getSpiderLabelsMod() {
        return spiderLabels;
    }
	
	public TreeMap<String,String> getCurveLabelsMod() {
        return curveLabels;
    }
	    
	
	
	@Override
	  public PrimarySpiderDiagram addSpider(String spider, Region habitat) {
		return addLUSpider(spider,habitat,"");

	}
	
	
	@Override
	public PrimarySpiderDiagram addNewSpider(String spider, Region habitat) {
		  if (getSpidersMod().contains(spider)){
			  throw new IllegalArgumentException("The spider has to have a fresh label.");
		  } else return addLUSpider(spider,habitat,""); 
	}

	
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
	              //newSpiders = getSpidersMod();
	        	  JOptionPane.showMessageDialog(null,"The spider has to have a fresh name.","Input error",JOptionPane.ERROR_MESSAGE);
	        	  //throw new IllegalArgumentException("The spider has to have a fresh name.");
	              
	          } else {
	              newSpiders.add(spiderName);
	          }
	      } else {
	          newSpiders = new TreeSet<>();
	          newSpiders.add(spiderName);
	      }
	
	      return new LUCOPDiagram(
	              newSpiders,
	              newHabitats,
	              getShadedZones(),
	              getPresentZones(),
	              getArrows(),
	              spiderLabels,
	              curveLabels);
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
	      return new LUCOPDiagram(
	              getSpiders(),
	              getHabitats(),
	              newShadedZones,
	              getPresentZones(),
	              getArrows(),
	              spiderLabels,
	              curveLabels);
	  }
	  
	  
	@Override
	public COPDiagram addArrow(Arrow arrow) {
	    	TreeSet<Arrow> newArrows = getArrowsMod();
	    	if (arrow != null){
	    		if ((! containsArrow(arrow)) && (validArrow(arrow))) {
	    			newArrows.add(arrow);
	    		}
	    	}
	    	//return new COPDiagram(getSpiders(), getHabitats(), getShadedZones(), getPresentZones(), newArrows);
	    	return new LUCOPDiagram(getSpidersMod(), getHabitatsMod(), 
	    			getShadedZonesMod(), getPresentZonesMod(), newArrows,spiderLabels,curveLabels);
	    }
	    
	    
	protected boolean areSpiderLabelsValid(){
		if (spiderLabels == null || spiderLabels.size() == 0){
			return true;
		}else{ 
			if ( spiderLabels.containsKey("")){
				return false;
			}
			
			for (String spider : spiderLabels.keySet()){
				if (! getSpiders().contains(spider)){
					return false;
				}	
			}
		}	
		return true;	
	}
	
	
	protected boolean areCurveLabelsValid(){
		if (curveLabels == null || curveLabels.size() == 0){
			return true;
		}else {
			if (curveLabels.containsKey("")){
				return false;
			}
			
			for (String curve : curveLabels.keySet()){
				if (! getContours().contains(curve)){
					return false;
				}
			}
	
		}
		return true;
		
	}
    
	  @Override
	  protected boolean checkValid() {
		  return (super.checkValid()
		             && areSpiderLabelsValid()
		             && areCurveLabelsValid());
//	      SortedSet<String> contours = getContours();
//	      return areHabitatZonesValid(contours)
//	             && areShadedZonesValid(contours)
//	             && arePresentZonesValid(contours)
//	             && areArrowsValid()
//	             && areSpiderLabelsValid()
//	             && areCurveLabelsValid();
	  }   	
	
	
    private static void printSpiderLabel(Appendable sb, String spiderName, String spiderLabel) throws IOException {
        sb.append('(');
        printString(sb, spiderName);
        sb.append(", ");
        printString(sb, spiderLabel);
        sb.append(')');
    }
	
	protected void printSpiderLabels(Appendable sb) throws IOException {
        sb.append(SDTextSpiderLabelsAttribute).append(" = ");
        sb.append('[');
        if (spiderLabels != null && !spiderLabels.isEmpty()) {
            Iterator<Entry<String, String>> spIterator = spiderLabels.entrySet().iterator();
            if (spIterator.hasNext()) {
                Entry<String, String> spiderLabel = spIterator.next();
                printSpiderLabel(sb, spiderLabel.getKey(), spiderLabel.getValue());
                while (spIterator.hasNext()) {
                	spiderLabel = spIterator.next();
                    printSpiderLabel(sb.append(", "), spiderLabel.getKey(), spiderLabel.getValue());
                }
            }
        }
        sb.append(']');
    }
	
	
    private static void printCurveLabel(Appendable sb, String curveName, String curveLabel) throws IOException {
        sb.append('(');
        printString(sb, curveName);
        sb.append(", ");
        printString(sb, curveLabel);
        sb.append(')');
    }
	
	protected void printCurveLabels(Appendable sb) throws IOException {
        sb.append(SDTextCurveLabelsAttribute).append(" = ");
        sb.append('[');
        if (curveLabels != null && !curveLabels.isEmpty()) {
            Iterator<Entry<String, String>> spIterator = curveLabels.entrySet().iterator();
            if (spIterator.hasNext()) {
                Entry<String, String> curveLabel = spIterator.next();
                printCurveLabel(sb, curveLabel.getKey(), curveLabel.getValue());
                while (spIterator.hasNext()) {
                	curveLabel = spIterator.next();
                    printCurveLabel(sb.append(", "), curveLabel.getKey(), curveLabel.getValue());
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
        sb.append(TextLUCOPId);
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
        sb.append('}');
    }
	
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof LUCOPDiagram){
			LUCOPDiagram luCOP = (LUCOPDiagram) other;
			return (super.equals(luCOP) && 
					equal(getSpiderLabels() == null ? null : getSpiderLabels().entrySet(), 
	                		luCOP.getSpiderLabels() == null ? null : luCOP.getSpiderLabels().entrySet()) &&
					equal(getCurveLabels() == null ? null : getCurveLabels().entrySet(), 
	                		luCOP.getCurveLabels() == null ? null : luCOP.getCurveLabels().entrySet())
					);
		}
		return false;
	}
	
	
	  @Override
	  public boolean isSEquivalentTo(SpiderDiagram other) {
	      if (equals(other)) {
	          return true;
	      }
	      if (other instanceof LUCOPDiagram) {
	         LUCOPDiagram luCOP = (LUCOPDiagram) other;

	         return (super.isSEquivalentTo(other) && 					
	        		equal(getSpiderLabels() == null ? null : getSpiderLabels().entrySet(), 
             		luCOP.getSpiderLabels() == null ? null : luCOP.getSpiderLabels().entrySet()) &&
				    equal(getCurveLabels() == null ? null : getCurveLabels().entrySet(), 
             		luCOP.getCurveLabels() == null ? null : luCOP.getCurveLabels().entrySet()));
	      }
	      return false;
	  }
	  
	  

	  
}
