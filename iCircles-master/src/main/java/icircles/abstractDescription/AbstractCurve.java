package icircles.abstractDescription;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.log4j.Logger;

import java.util.TreeSet;

import org.apache.log4j.Level;

/**
 * This class allows multiple different circles to have the same label.
 * <p>Remark: we could still consider having String instead of CurveLabel.</p>
 * @author Matej Urbas [matej.urbas@gmail.com]
 */
public class AbstractCurve implements Comparable<AbstractShape>, AbstractShape {

    static Logger logger = Logger.getLogger(AbstractCurve.class.getName());

    static int id = 0;
    @JsonProperty(value="label")
    String m_label;
    int m_id;

    
	//Zohreh: for spiders Name is the unique Id with which the spider is identified, the label however could be empty, 
	//when the spider is an unlabelled one. Here m_label is already reserved as the unique name of the curves, so I have 
    //to use name and label wrong way around for now:
    //for curves Label is the unique Id with which the curve is identified, the Name however could be empty, 
	//when the curve is an unlabelled one.
	private String m_name;
	
	
    
    /**
     * Default constructor is needed for Jackson Databinding.
     */
    public AbstractCurve() {
    }

    public AbstractCurve(String label) {
        id++;
        m_id = id;
        m_label = label;
    }
    
    
	//Zohreh: The constructor the allows a name as well as a label for the curves. In theory this constructor
	//would have been enough to allow both labelled and unlabelled curves, but because of the backward 
	//compatibility the initial constructor is also kept.  
	public AbstractCurve(String label, String name){	
        id++;
        m_id = id;
		m_label= label;	
		m_name = name;
	}
	
	
	//Zohreh
	public String getName(){
		return m_name;
	}
    
 
	public void setName(String name) {
        this.m_name = name;
	}


    public String getLabel() {
        return m_label;
    }
    
    //Zohreh
    public int getId() {
        return m_id;
    }
    

    public AbstractCurve clone() {
        return new AbstractCurve(m_label);
    }

    
    @Override
    public int compareTo(AbstractShape s) {
    	AbstractCurve o = (AbstractCurve) s;
    	if(null == o) {
    		return 1; // null is less than anything
    	}

        int tmp = m_label.compareTo(o.m_label);
        if (tmp != 0) {
            return tmp;
        }
        int this_id = m_id;
        int other_id = o.m_id;
        return (this_id < other_id) ? -1 : (this_id == other_id) ? 0 : 1;
    }

    public String debug() {
	// Abuse of log4j

        StringBuilder sb = new StringBuilder();
	sb.append("contour(");
        sb.append(m_label);
	sb.append("_" + m_id + ")@");
	sb.append(hashCode());

	if(logger.getEffectiveLevel() == Level.DEBUG)
	    return sb.toString();

	// Level.ALL
        return new String();
    }

    public boolean matchesLabel(AbstractCurve c) {
        return m_label == c.m_label;
    }

    public String debugWithId() {
        return debug() + "_" + m_id;
    }

    public double checksum() {
	logger.debug("build checksum from " + m_label
		     + " (and not " + m_id + ")\ngiving "+m_label.hashCode());
        return m_label.hashCode() /* * m_id */;
    }
    
    
    

//    @Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + m_id;
//		result = prime * result + ((m_label == null) ? 0 : m_label.hashCode());
//		result = prime * result + ((m_name == null) ? 0 : m_name.hashCode());
//		return result;
//	}


	/**
     * Only ever used by test code
    public static void reset_id_counter() {
        id = 0;
        AbstractBasicRegion.clearLibrary();
        CurveLabel.clearLibrary();
    }
    */

    public String toString() {
        return m_label;
    }

}
