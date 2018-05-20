package speedith.ui;

import icircles.abstractDescription.AbstractSpider;

public class AbstractSpiderComparator implements Comparable<AbstractSpiderComparator>{
	
	private AbstractSpider m_comparable1;
	private AbstractSpider m_comparable2;
	private String m_quality;
	
	
	public AbstractSpiderComparator(){
		
	}
	
	
	public AbstractSpiderComparator(AbstractSpider comparable1, AbstractSpider comparable2, String quality){
		m_comparable1 = comparable1;
		m_comparable2 = comparable2;
		m_quality = quality;
	}
	
	
	public AbstractSpider getAbsComparable1(){
		return m_comparable1;
	}
	
	
	public AbstractSpider getAbsComparable2(){
		return m_comparable2;
	}
	
	public String getAbsQuality(){
		return m_quality;
	}
	

	@Override
	public int compareTo(AbstractSpiderComparator other) {
		int v;
		
		v = this.m_comparable1.compareTo(other.m_comparable1);
		if (v != 0) return v;
		
		v = this.m_comparable2.compareTo(other.m_comparable2);
		if (v != 0) return v;
		
		v = this.m_quality.compareTo(other.m_quality);
		if (v != 0) return v;
		
		return 0;
	}

}
