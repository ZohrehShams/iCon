package speedith.core.lang;

import static speedith.core.i18n.Translations.i18n;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * A concept diagram is a collection of at least two primary spider diagrams with extra arrows that have their source and 
 * target in different primary diagrams. This class only implements the concept diagrams that are a collection of two primaries.
 * @author Zohreh Shams
 */
public class ConceptDiagram extends SpiderDiagram implements Serializable {
	
	
	public static final String CDTextBinaryId = "BinaryCD";
	//public static final String CDTextNaryId = "NaryCD";
	public static final String CDTextArgAttribute = "cd_arg";
	public static final String CDTextArrowsAttribute = "cd_arrows";
	
//	private ArrayList<PrimarySpiderDiagram> primaries;
	private ArrayList<PrimarySpiderDiagram> primaries;

	private  TreeSet<Arrow> cd_arrows;
	
	private boolean hashInvalid = true;
	private int hash;
	
	
    ConceptDiagram(ArrayList<PrimarySpiderDiagram> primaries, TreeSet<Arrow> arrows) {
        if (primaries == null) {
            throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "diagrmas"));
        }
        if (arrows == null) {
            throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "arrows"));
        }
        this.primaries = primaries;
        this.cd_arrows = arrows== null ? new TreeSet<Arrow>() : arrows;
    }
    
    
    ConceptDiagram(Collection<PrimarySpiderDiagram> primaries, Collection<Arrow> arrows) {
    	this(primaries == null ? null : new ArrayList<>(primaries),
    			arrows == null ? null : new TreeSet<>(arrows));
    }
	
    
    public List<PrimarySpiderDiagram> getPrimaries() {
        return Collections.unmodifiableList(primaries);
    }
    
    
    //Zohreh: under investigation
    public List<SpiderDiagram> getPrimariesSD(){
    	ArrayList<SpiderDiagram> primariesSDList = new ArrayList<SpiderDiagram>();
    	primariesSDList.addAll(primaries);
    	return Collections.unmodifiableList(primariesSDList);
    }

   
    
    public SortedSet<Arrow> get_cd_Arrows() {
        return Collections.unmodifiableSortedSet(cd_arrows);
    }
    
    
    public TreeSet<Arrow> get_cd_ArrowsMod() {
        return cd_arrows;
    }
    
    
    public int getArrowCount(){
    	return cd_arrows.size();
    }
    
    public int getPrimaryCount(){
        return primaries.size();
    }
    
    public PrimarySpiderDiagram getPrimary(int index) {
        return primaries.get(index);
    }
    
    
	@Override
	public SpiderDiagram getSubDiagramAt(int index) {
        if (index == 0) {
            return this;
        } else if (index > 0) {
        	--index;
        	return primaries.get(index);
        }
        return null;
	}
	

	@Override
	public int getSubDiagramCount() {
		return getPrimaryCount()+1;
	}

	
	
	@Override
	public SpiderDiagram transform(Transformer t, boolean trackParents) {
        if (t == null) {
            throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "t"));
        }
        CDTransformer cdt = (CDTransformer) t;
        SpiderDiagram curTransform = cdt.transform(this, 0, new ArrayList<CompoundSpiderDiagram>(), new ArrayList<Integer>());
        return curTransform == null ? this : curTransform;
	}
	
	
	@Override
	public Iterator<SpiderDiagram> iterator() {
		return new ConceptDiagramIterator(this);
	}
	
	
	
	
	private PrimarySpiderDiagram sourceDiagram(Arrow arrow){
		//boolean found = false;
		for (PrimarySpiderDiagram psd : primaries){
			if(psd.getContours().contains(arrow.arrowSource()) || psd.getSpiders().contains(arrow.arrowSource())){
				return psd;
			}
		}
		return null;
	}
	
	
	private PrimarySpiderDiagram targetDiagram(Arrow arrow){
		for (PrimarySpiderDiagram psd : primaries){
			if(psd.getContours().contains(arrow.arrowTarget()) || psd.getSpiders().contains(arrow.arrowTarget())){
				return psd;
			}
		}
		return null;
	}

	
	
	private boolean sourceTargetSameDiagram(Arrow arrow){
		if (sourceDiagram(arrow) == targetDiagram(arrow)){
			return true;
		}
		return false;
		
	}

	public boolean valid_CD_Arrow(Arrow arrow){
		if (! sourceTargetSameDiagram(arrow)){
			return true;
		}
		return false;	
	}
	
	
	protected boolean are_CD_ArrowsValid(){
		for (Arrow arrow : this.cd_arrows){
			if (! valid_CD_Arrow(arrow)){
				return false;
			}
		}
		return true;
	}
	
	
	public boolean arePrimariesValid(){
		for (PrimarySpiderDiagram primarySpiderDiagram : primaries) {
            if ((! (primarySpiderDiagram instanceof LUCarCOPDiagram)) && (!primarySpiderDiagram.isValid())) {
                return false;
            }
        }
        return true;
	}
    

	@Override
	public boolean isValid() {
		return arePrimariesValid() && 
				are_CD_ArrowsValid();
	}
		  		  
		  

  private void printArg(Appendable sb, int i) throws IOException {
	  sb.append(CDTextArgAttribute).append(Integer.toString(i)).append(" = ");
	  primaries.get(i - 1).toString(sb);  
  }
  
  
  private void printArgs(Appendable sb) throws IOException {
	  if (primaries.size() > 0) {
	      printArg(sb, 1);
	      for (int i = 2; i <= primaries.size(); i++) {
	          printArg(sb.append(", "), i);
	      }
	  }
	}
  
  
  protected void printArrows(Appendable sb) {
      try {
          sb.append(CDTextArrowsAttribute).append(" = ");
          printArrowList(sb, cd_arrows);
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
		 	sb.append(CDTextBinaryId);
	        sb.append(" {");
	        printArgs(sb);
	        sb.append(", ");
	        printArrows(sb);
	        sb.append('}');
		
	}
	
	
    @Override
    public String toString() {
        try {
            final StringBuilder sb = new StringBuilder();
            toString(sb);
            return sb.toString();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
	
	
	@Override
	public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof ConceptDiagram) {
        	ConceptDiagram cd = (ConceptDiagram) other;
            return  primaries.equals(cd.getPrimaries()) &&
            		cd_arrows.equals(cd.get_cd_Arrows());
        } else {
            return false;
        }
	}
	

	@Override
	public int hashCode() {
        if (hashInvalid) {
            hash = cd_arrows == null ? 0 : cd_arrows.hashCode();
            if (primaries != null) {
                for (SpiderDiagram sd : primaries) {
                    hash += sd.hashCode();
                }
            }
            hashInvalid = false;
        }
        return hash;
	}
	
	
	//Zohreh: warning double check
    @Override
    public boolean isSEquivalentTo(SpiderDiagram other) {
        if (equals(other)) {
            return true;
        }
        if (other instanceof ConceptDiagram) {
            ConceptDiagram cd = (ConceptDiagram) other;
            //boolean operandsSame = operandsSemanticallyEquivalent(csd);
            if (primaries.equals(cd.getPrimaries()) && cd_arrows.equals(cd.get_cd_Arrows())) {
                return true;
            }
        }
        return false;
    }
    
    
    static class ConceptDiagramIterator implements Iterator<SpiderDiagram> {

        private ArrayList<SpiderDiagram> iterationStack;
        Iterator<SpiderDiagram> it;

        public ConceptDiagramIterator(SpiderDiagram cdToIterateThrough) {
            if (cdToIterateThrough == null) {
                throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "cdToIterateThrough"));
            }
            iterationStack = new ArrayList<>();
            iterationStack.add(cdToIterateThrough);
            iterationStack.add(cdToIterateThrough.getSubDiagramAt(1));
            iterationStack.add(cdToIterateThrough.getSubDiagramAt(2));
            it = iterationStack.iterator();
            System.out.println(it);
        }

		@Override
		public boolean hasNext() {
			if (it.hasNext()){
				return true;
			}
			return false;	
		}
		

		@Override
		public SpiderDiagram next() {
			while(it.hasNext()){
				return it.next();
			}	
			return null;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException(i18n("SD_ITER_REMOVE_NOT_SUPPORTED"));
		}
    	 
    	
    }


	

}
