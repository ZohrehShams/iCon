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
import speedith.core.lang.SpiderDiagrams;
import speedith.core.lang.Zone;


/**
 *COP diagrams extend primary diagrams with arrows.
 *@author Zohreh Shams
 */
public class COPDiagram extends PrimarySpiderDiagram {

	private static final long serialVersionUID = 4312432316981735454L;
	public static final String SDTextArrowsAttribute = "arrows";
	public static final String TextCOPId = "COP";
	private  TreeSet<Arrow> arrows;
	

  COPDiagram(TreeSet<String> spiders, TreeMap<String, Region> habitats, TreeSet<Zone> shadedZones,
	TreeSet<Zone> presentZones, TreeSet<Arrow> arrows){
		super(spiders, habitats, shadedZones, presentZones);
		this.arrows = arrows == null ? new TreeSet<Arrow>() : arrows;		
	}
  
  
  COPDiagram(Collection<String> spiders, Map<String, Region> habitats, Collection<Zone> shadedZones, 
		  Collection<Zone> presentZones, Collection<Arrow> arrows){
	  
	  
      this(spiders == null ? null : new TreeSet<>(spiders),
              habitats == null ? null : new TreeMap<>(habitats),
              shadedZones == null ? null : new TreeSet<>(shadedZones),
              presentZones == null ? null : new TreeSet<>(presentZones),
              arrows == null ? null : new TreeSet<>(arrows)  );				
	}
  
  
  
  public static COPDiagram createCOPDiagram(Collection<String> spiders, Map<String, Region> habitats, Collection<Zone> shadedZones,
			Collection<Zone> presentZones, Collection<Arrow> arrows){
	    if ((spiders == null || spiders instanceof TreeSet)
	        && (habitats == null || habitats instanceof TreeMap)
	        && (shadedZones == null || shadedZones instanceof TreeSet)
	        && (presentZones == null || presentZones instanceof TreeSet)
	        && (arrows == null || arrows instanceof TreeSet)) {
	      return new COPDiagram(spiders == null ? null : (TreeSet<String>) spiders,
	                             habitats == null ? null : (TreeMap<String, Region>) habitats,
	                             shadedZones == null ? null : (TreeSet<Zone>) shadedZones,
	                             presentZones == null ? null : (TreeSet<Zone>) presentZones,
	                             arrows == null ? null : (TreeSet<Arrow>) arrows);
	    } else {
	      TreeSet<String> spidersCopy = spiders == null ? null : new TreeSet<>(spiders);
	      TreeMap<String, Region> habitatsCopy = habitats == null ? null : new TreeMap<>(habitats);
	      TreeSet<Zone> shadedZonesCopy = shadedZones == null ? null : new TreeSet<>(shadedZones);
	      TreeSet<Zone> presentZonesCopy = presentZones == null ? null : new TreeSet<>(presentZones);
	      TreeSet<Arrow> arrowsCopy = arrows == null ? null : new TreeSet<>(arrows);
	      return new COPDiagram(spidersCopy, habitatsCopy, shadedZonesCopy, presentZonesCopy, arrowsCopy);
	    }
	  }


  	
  	public PrimarySpiderDiagram getPrimarySpiderDiagram(){
  		return SpiderDiagrams.createPrimarySD(getSpiders(),getHabitats(),getShadedZones(),getPresentZones());
  	}
	
    public SortedSet<Arrow> getArrows() {
        return Collections.unmodifiableSortedSet(arrows);
    }
    

	
    public boolean containsArrow(Arrow arrow) {
        return arrows.contains(arrow);
    }
   
    
    // Only when the habitat of all spiders is ([],[]), we populates the dots with spiders.  
    public SortedSet<String> getDots(){
    	Zone zone = new Zone(new TreeSet<String>(), new TreeSet<String>());
    	for(String spider : getSpiders()){
    		Region habitat = getSpiderHabitat(spider);
    		if((habitat.getZonesCount() != 1) || (! habitat.sortedZones().first().equals(zone))){
    			return new TreeSet<String>();
    		}
    	}
    	return new TreeSet<String>(getSpiders());
    }
   
  
	public boolean arrowSourceContour(Arrow arrow){
		if (getAllContours().contains(arrow.arrowSource()))
			return true;
		else
			return false;
		}
	
	public boolean arrowTargetContour(Arrow arrow){
		if (getAllContours().contains(arrow.arrowTarget()))
			return true;
		else
			return false;
		}
	
	
	public boolean arrowSourceSpider(Arrow arrow){
		if (getSpiders().contains(arrow.arrowSource()))
			return true;
		else
			return false;
		}
	

	public boolean arrowTargetSpider(Arrow arrow){
		if (getSpiders().contains(arrow.arrowTarget()))
			return true;
		else
			return false;
		}
	
	
	public int getArrowsCount() {
	      return arrows.size();
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
      return new COPDiagram(
              getSpiders(),
              getHabitats(),
              newShadedZones,
              getPresentZones(),
              arrows);
  }
  
  
  @Override
  public PrimarySpiderDiagram addSpider(String spider, Region habitat) {
      TreeMap<String, Region> newHabitats = (getHabitats() == null) ? new TreeMap<String, Region>() : new TreeMap<>(getHabitats());
      newHabitats.put(spider, habitat);
      TreeSet<String> newSpiders = new TreeSet<String>();
      if (getSpiders() != null) {
          if (getSpiders().contains(spider)) {
              newSpiders = new TreeSet<String>(getSpiders());
          } else {
              newSpiders = new TreeSet<>(getSpiders()); 
              newSpiders.add(spider);
          }
      } else {
          newSpiders.add(spider);
      }
      
      return new COPDiagram(
              newSpiders,
              newHabitats,
              getShadedZones(),
              getPresentZones(),
              arrows);
  }
  
  
  
  
  
  public COPDiagram deleteSpider(String... spiders) {
	  TreeSet<String> newSpiders = new TreeSet<>(getSpiders());
	  TreeMap<String, Region> newHabitats = new TreeMap<>(getHabitats());
	  TreeSet<Arrow> newArrows = new TreeSet<>(arrows);
	  
	  for(String spider : spiders){
		  newSpiders.remove(spider);
		  newHabitats.remove(spider);
		  
	      for (Arrow arrow : arrows){
	          if(spider.equals(arrow.arrowSource()) || spider.equals(arrow.arrowTarget())){
	      		newArrows.remove(arrow);
	          }
	        }
	  }

      return new COPDiagram(
              newSpiders,
              newHabitats,
              getShadedZones(),
              getPresentZones(),
              newArrows);
  }
  
  
 
  public COPDiagram addArrow(Arrow arrow) {
	TreeSet<Arrow> newArrows = new TreeSet<Arrow>(getArrows());
  	if (arrow != null){
  		if ((! containsArrow(arrow)) && (validArrow(arrow))) {
  			newArrows.add(arrow);
  		}
  	}
  	return new COPDiagram(getSpiders(), getHabitats(), getShadedZones(), getPresentZones(), newArrows);
  } 
  
  

  public boolean validArrow(Arrow arrow){
  	if ((getContours().contains(arrow.arrowSource()) || getSpiders().contains(arrow.arrowSource())) && 
				(getContours().contains(arrow.arrowTarget()) || getSpiders().contains(arrow.arrowTarget())) &&
				(arrow.arrowType().equals("solid") || arrow.arrowType().equals("dashed"))){
  		return true;
  	}
  return false;
  }
  
  protected boolean areArrowsValid(){
	  for (Arrow arrow : this.arrows){
		  if (! validArrow(arrow)){
			  return false;
		  }
	  }	  
  return true;
 }
  
  @Override
  protected boolean checkValid() {
      return (super.checkValid() && areArrowsValid());
  }
  
  
  protected void printArrows(Appendable sb) {
      try {
          sb.append(SDTextArrowsAttribute).append(" = ");
          printArrowList(sb, arrows);
      } catch (IOException ex) {
          throw new RuntimeException(ex);
      }
  }
  
  
  private void printArrowList(Appendable sb, Collection<Arrow> arrows) throws IOException {
      sb.append('[');
      if (arrows != null) {
          Iterator<Arrow> spIterator = arrows.iterator();
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
      sb.append(TextCOPId);
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
      sb.append('}');
  }
  

  

	/** 
	@Override
	public boolean equals(COPDiagram other) {
		return (super(other) && other.getArrows() == this.getArrows());

	}**/
	@Override
	public boolean equals(Object other) {
		if (other instanceof COPDiagram){
			COPDiagram cop = (COPDiagram) other;
			return (super.equals(cop) && 
					equal(getArrows() == null ? null : getArrows(), 
	                		cop.getArrows() == null ? null : cop.getArrows()) 
					);
		}
		return false;
	}
	
  
  
  
  @Override
  public boolean isSEquivalentTo(SpiderDiagram other) {
      if (equals(other)) {
          return true;
      }
      if (other instanceof COPDiagram) {
         COPDiagram cop = (COPDiagram) other;

          return super.isSEquivalentTo(other) && (equal(getArrows(), cop.getArrows()));
      }
      return false;
  }
  

}
