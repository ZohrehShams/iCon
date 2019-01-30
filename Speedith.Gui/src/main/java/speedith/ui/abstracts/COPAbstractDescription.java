package speedith.ui.abstracts;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import icircles.abstractDescription.*;


public class COPAbstractDescription extends AbstractDescription{

	TreeSet<AbstractArrow>  m_arrows;
	TreeSet<String> m_dots;
	
	public COPAbstractDescription() {
		super();
    	m_arrows = new TreeSet<AbstractArrow>();
    	m_dots = new TreeSet<String>();
    }

	public COPAbstractDescription(Set<AbstractCurve> contours, Set<AbstractBasicRegion> zones,
			Set<AbstractBasicRegion> shaded_zones) {
		super(contours, zones, shaded_zones);
		m_arrows = new TreeSet<AbstractArrow>();
		m_dots = new TreeSet<String>();
	}

	public COPAbstractDescription(Set<AbstractCurve> contours, Set<AbstractBasicRegion> zones) {
		super(contours, zones);
		m_arrows = new TreeSet<AbstractArrow>();
		m_dots = new TreeSet<String>();
	}
	
	public void addArrow(AbstractArrow a){
    	m_arrows.add(a);
    }
	
	public void addAllArrows(TreeSet<AbstractArrow>  as){
    	m_arrows.addAll(as);
    }
	
   
    public Iterator<AbstractArrow> getArrowIterator() {
        return m_arrows.iterator();
    }
    
    
    public TreeSet<AbstractArrow> getCopyOfArrows() {
        return new TreeSet<AbstractArrow>(m_arrows);
    }
	
    
    public void setDots(TreeSet<String> dots){
    	m_dots.addAll(dots);
    }
    
    
	public TreeSet<String> getDots(){
		return m_dots;
	}
}
