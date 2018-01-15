package speedith.core.lang;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;


public class PDiagram extends LUCarCOPDiagram{
	
	public static final String TextLUCarCOPId = "PD";

	public PDiagram(Collection<String> spiders, Map<String, Region> habitats, Collection<Zone> shadedZones,
			Collection<Zone> presentZones, Collection<Arrow> arrows, Map<String, String> spiderLabels,
			Map<String, String> curveLabels, Map<Arrow, Cardinality> arrowCardinalities) {
		super(spiders, habitats, shadedZones, presentZones, arrows, spiderLabels, curveLabels, arrowCardinalities);
		assertSpidersAreValid();
		setSpiders("thing", new Region(new Zone(null, getContours())), "t");
	}

	public PDiagram(TreeSet<String> spiders, TreeMap<String, Region> habitats, TreeSet<Zone> shadedZones,
			TreeSet<Zone> presentZones, TreeSet<Arrow> arrows, TreeMap<String, String> spiderLabels,
			TreeMap<String, String> curveLabels, TreeMap<Arrow, Cardinality> arrowCardinalities) {
		super(spiders, habitats, shadedZones, presentZones, arrows, spiderLabels, curveLabels, arrowCardinalities);
		assertSpidersAreValid();
		setSpiders("thing", new Region(new Zone(null, getContours())), "t");
	}
	
	
    public static PDiagram createPDiagram(Collection<String> spiders, Map<String, Region> habitats, Collection<Zone> shadedZones,
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
		      return new PDiagram(spiders == null ? null : (TreeSet<String>) spiders,
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
		      return new PDiagram(spidersCopy, habitatsCopy, shadedZonesCopy, presentZonesCopy, arrowsCopy, 
		    		  spiderLabelsCopy, curveLabelsCopy, arrowCardinalitiesCopy);
		    }
		  }
    
    
    
    public void setSpiders(String spiderName, Region habitat, String spiderLabel){
    	TreeSet<String> newSpiders = new TreeSet<>();
	    newSpiders.add(spiderName);
	    this.spiders = newSpiders;
	      
	    TreeMap<String, Region> newHabitats = new TreeMap<String, Region>();
	    newHabitats.put(spiderName, habitat);
	    this.spiderHabitatsMap = newHabitats;
	      
	    TreeMap<String, String> spiderLabels = new TreeMap<String, String>();
	    spiderLabels.put(spiderName, spiderLabel);
	    this.spiderLabels = spiderLabels ;
	    
    }

	
	

	public  void assertSpidersAreValid(){
		if(spiders.size() != 0){
			throw new IllegalArgumentException("Property diagrams cannot have any spiders");
		}

	}
	
	


}
