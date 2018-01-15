package icircles.abstractDescription;
/**
 * This class is merely introduced to allow abstract arrows to have a primary diagram (the box around a primary diagram) as their source 
 * and target as well as abstract spiders  and curves.
 * @author Zohreh Shams
 */

public class AbstractDiagram implements Comparable<AbstractShape>, AbstractShape{
	
	
	private String m_name;
	
	
	public AbstractDiagram(String name){
		m_name = name;
	}
	
	
	public String getName(){
		return m_name;
	}
	
	
	public void setName(String name) {
		this.m_name = name;
	}


	@Override
	public int compareTo(AbstractShape s) {
		AbstractDiagram other = (AbstractDiagram) s;
		return  m_name.compareTo(other.m_name);
	}
        

}
