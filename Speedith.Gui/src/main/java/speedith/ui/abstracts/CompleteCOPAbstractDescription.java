package speedith.ui;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import icircles.abstractDescription.AbstractBasicRegion;
import icircles.abstractDescription.AbstractCurve;

public class CompleteCOPAbstractDescription extends COPAbstractDescription {
	
	TreeSet<AbstractSpiderComparator>  m_spidersComparators;
	
	
	public CompleteCOPAbstractDescription(){
		super();
		m_spidersComparators = new TreeSet<AbstractSpiderComparator>();
	}
	
	
	public CompleteCOPAbstractDescription(Set<AbstractCurve> contours, Set<AbstractBasicRegion> zones,
			Set<AbstractBasicRegion> shaded_zones){
		super(contours, zones, shaded_zones);
		m_spidersComparators = new TreeSet<AbstractSpiderComparator>();	
	}
	
	public CompleteCOPAbstractDescription(Set<AbstractCurve> contours, Set<AbstractBasicRegion> zones){
		super(contours, zones);
		m_spidersComparators = new TreeSet<AbstractSpiderComparator>();	
	}
	
	
	public void addSpiderComparator(AbstractSpiderComparator asc){
		m_spidersComparators.add(asc);
    }
	
	public void addAllSpiderComparators(TreeSet<AbstractSpiderComparator>  tasc){
		m_spidersComparators.addAll(tasc);
    }
	
   
    public Iterator<AbstractSpiderComparator> getSpiderComparatorIterator() {
        return m_spidersComparators.iterator();
    }
	

}
