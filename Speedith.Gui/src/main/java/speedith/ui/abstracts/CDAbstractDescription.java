package speedith.ui.abstracts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;


public class CDAbstractDescription {
	
	ArrayList<COPAbstractDescription> cd_primaries;
	TreeSet<AbstractArrow>  cd_arrows;
	
	public CDAbstractDescription() {
    	cd_arrows = new TreeSet<AbstractArrow>();
    }

	public CDAbstractDescription(ArrayList<COPAbstractDescription> primaries) {
		cd_primaries = new ArrayList<COPAbstractDescription>(primaries);
		cd_arrows = new TreeSet<AbstractArrow>();
	}
	
	public ArrayList<COPAbstractDescription> getAbstractPrimaries(){
		return cd_primaries;
	}
	
	
	public void addArrow(AbstractArrow a){
    	cd_arrows.add(a);
    }
	
	public void addAllArrows(TreeSet<AbstractArrow> as){
    	cd_arrows.addAll(as);
    }
	
   
    public Iterator<AbstractArrow> getArrowIterator() {
        return cd_arrows.iterator();
    }
    
    
    public TreeSet<AbstractArrow> getCopyOfArrows() {
        return new TreeSet<AbstractArrow>(cd_arrows);
    }
	

}
