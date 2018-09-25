package speedith.core.reasoning.args;

import java.util.ArrayList;
import java.util.TreeSet;

import speedith.core.lang.Zone;
/**
 * This argument passes two  sets of contours that are in and out of a new contour that will be added to teh diagram.
 * @author Zohreh Shams [zs315@cam.ac.uk]
 */
public class ZonesInOutArg extends SubDiagramIndexArg{

	private final TreeSet<Zone> zonesIn;
	private final TreeSet<Zone> zonesOut;
	
	
    public ZonesInOutArg(int subgoalIndex, int primarySDIndex, TreeSet<Zone> zonesIn, TreeSet<Zone> zonesOut) {
        super(subgoalIndex, primarySDIndex);
        if ((zonesIn == null) && (zonesOut == null)) {
            throw new IllegalArgumentException("Both arguments cannot be null at the same time.");
        }
        this.zonesIn = zonesIn;
        this.zonesOut = zonesOut;
        
    }
    
    
    
    public TreeSet<Zone> getZonesIn(){
    	return zonesIn;
    }
    
    public TreeSet<Zone> getZonesOut(){
    	return zonesOut;
    }
	
}
