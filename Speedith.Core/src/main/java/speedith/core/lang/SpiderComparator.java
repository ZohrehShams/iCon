package speedith.core.lang;

import static speedith.core.i18n.Translations.i18n;

import java.io.IOException;
import java.io.Serializable;

public class SpiderComparator implements Comparable<SpiderComparator>, Serializable{

	private static final long serialVersionUID = 7014512285098716390L;
	private String  comparable1;
	private String  comparable2;
	private String  quality;
	
	public SpiderComparator(String comparable1, String comparable2, String quality){
		this.comparable1 = comparable1;
		this.comparable2 = comparable2;
		this.quality = quality;
	}
	
	
	public String getComparable1(){
		return comparable1;
	}
	
	
	public String getComparable2(){
		return comparable2;
	}
	
	public String getQuality(){
		return quality;
	}
	
	
	public void toString(Appendable sb){
		try{
			if(sb == null){
				throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "sb"));
			}
            sb.append('(');
            SpiderDiagram.printString(sb, comparable1);
            sb.append(", ");
            SpiderDiagram.printString(sb, comparable2);
            sb.append(", ");
            SpiderDiagram.printString(sb, quality);
            sb.append(')');
		} catch (IOException ex){
			throw new RuntimeException(ex);
		}
	}


	@Override
	public int compareTo(SpiderComparator sc) {
		int v;
		
		v = this.comparable1.compareTo(sc.comparable1);
		if (v != 0) return v;
		
		v = this.comparable2.compareTo(sc.comparable2);
		if (v != 0) return v;
		
		v = this.quality.compareTo(sc.quality);
		if (v != 0) return v;

		return 0;
	}
	
	
	
	@Override 
	public boolean equals(Object o) {
		return (o instanceof SpiderComparator) && (this.compareTo((SpiderComparator) o) == 0);	
	}

}
