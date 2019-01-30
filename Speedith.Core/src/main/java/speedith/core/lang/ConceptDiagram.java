package speedith.core.lang;

import static speedith.core.i18n.Translations.i18n;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;


/**
 * A concept diagram is a collection of at least two primary spider diagrams with extra arrows that have their source and 
 * target in different primary diagrams. This class only implements the concept diagrams that are a collection of two primaries.
 * @author Zohreh Shams
 */
public class ConceptDiagram extends SpiderDiagram implements Serializable {
	
	private static final long serialVersionUID = -1111524460433130189L;
	public static final String CDTextBinaryId = "BinaryCD";
	public static final String CDTextId = "CD";
	public static final String CDTextArgAttribute = "cd_arg";
	public static final String CDTextArrowsAttribute = "cd_arrows";
	
	private ArrayList<PrimarySpiderDiagram> primaries;

	private  TreeSet<Arrow> cd_arrows;
	
	private boolean hashInvalid = true;
	private int hash;
	
	
    ConceptDiagram(ArrayList<PrimarySpiderDiagram> primaries, TreeSet<Arrow> arrows) {
        if (primaries == null) {
            throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "diagrmas"));
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

	
    public SortedSet<String> getAllContours() {
    	SortedSet<String> allContours = new TreeSet<String>();
        if (isValid()) {
        	for (PrimarySpiderDiagram psd : primaries){
        		allContours.addAll(psd.getAllContours());
        	}
            return allContours;
        } else {
            throw new UnsupportedOperationException();
        }
    }
    
    
    public SortedSet<String> getAllSpiders() {
    	SortedSet<String> allSpiders= new TreeSet<String>();
        if (isValid()) {
        	for (PrimarySpiderDiagram psd : primaries){
        		allSpiders.addAll(psd.getSpiders());
        	}
            return allSpiders;
        } else {
            throw new UnsupportedOperationException();
        }
    }
    
    
    public SortedMap<String,String> getAllCurveLabels() {
    	SortedMap<String,String> allCurveLabels= new TreeMap<String,String>();
        if (isValid()) {
        	for (PrimarySpiderDiagram psd : primaries){
        		CompleteCOPDiagram comp = (CompleteCOPDiagram) psd; 
        		allCurveLabels.putAll(comp.getCurveLabels());
        	}
            return allCurveLabels;
        } else {
            throw new UnsupportedOperationException();
        }
    }
    
    
    
    public SortedMap<String,String> getAllSpiderLabels() {
    	SortedMap<String,String> allSpiderLabels= new TreeMap<String,String>();
        if (isValid()) {
        	for (PrimarySpiderDiagram psd : primaries){
        		CompleteCOPDiagram comp = (CompleteCOPDiagram) psd; 
        		allSpiderLabels.putAll(comp.getSpiderLabels());
        	}
            return allSpiderLabels;
        } else {
            throw new UnsupportedOperationException();
        }
    }
    
 
    
    
	public boolean arrowSourceContour(Arrow arrow){
		if (getAllContours().contains(arrow.arrowSource()))
			return true;
		else
			return false;
		}
	
	public boolean arrowTargetContour(Arrow arrow){
		if (getAllContours().contains(arrow.arrowTarget()))
			return true;
		else
			return false;
		}
	
	
	public boolean arrowSourceSpider(Arrow arrow){
		if (getAllSpiders().contains(arrow.arrowSource()))
			return true;
		else
			return false;
		}
	

	public boolean arrowTargetSpider(Arrow arrow){
		if (getAllSpiders().contains(arrow.arrowTarget()))
			return true;
		else
			return false;
		}
		
	
	public PrimarySpiderDiagram sourceDiagram(Arrow arrow){
		for (PrimarySpiderDiagram psd : primaries){
			if(psd.getContours().contains(arrow.arrowSource()) || psd.getSpiders().contains(arrow.arrowSource())){
				return psd;
			}
		}
		return null;
	}
	
	
	public PrimarySpiderDiagram targetDiagram(Arrow arrow){
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


	public ConceptDiagram addArrow(Arrow arrow) {
	    	TreeSet<Arrow> newCDArrows = new TreeSet<Arrow>(get_cd_Arrows());
	    	if (arrow != null){
	    		if ((! cd_arrows.contains(arrow)) && (valid_CD_Arrow(arrow))) {
	    			newCDArrows.add(arrow);
	    		}
	    	}
	    	return new ConceptDiagram(primaries,newCDArrows);
	 }
	
	
	public ConceptDiagram addArrows(TreeSet<Arrow> arrows) {
    	TreeSet<Arrow> newCDArrows = new TreeSet<Arrow>(get_cd_Arrows());
    	for(Arrow arrow: arrows){
    		if (arrow != null){
    			if ((! cd_arrows.contains(arrow)) && (valid_CD_Arrow(arrow))){
    				newCDArrows.add(arrow);
    			}
    		}	
    	}
    	return new ConceptDiagram(primaries,newCDArrows);
 }
	
	
	
	
	//This method returns the cd arrow to and from a certain Primary diagram.
	public TreeSet<Arrow> get_cd_Arrows_Primary(PrimarySpiderDiagram psd){
		TreeSet<Arrow> primaryCDArrows = new TreeSet<Arrow>();

		for (Arrow arrow: cd_arrows){
			if ((sourceDiagram(arrow).equals(psd)) || (targetDiagram(arrow).equals(psd))){
				primaryCDArrows.add(arrow);
			}
		}
		
		return primaryCDArrows;
	}
	
	
	public SpiderDiagram deletePrimarySpiderDiagram(PrimarySpiderDiagram psd){
		ArrayList<PrimarySpiderDiagram> newPrimaries = new ArrayList<PrimarySpiderDiagram>(getPrimaries());
		TreeSet<Arrow> newCDArrows = new TreeSet<Arrow>(get_cd_Arrows());
		
		if (primaries.contains(psd)) {
			newPrimaries.remove(psd);
			for (Arrow arrow: cd_arrows){
				if ((sourceDiagram(arrow).equals(psd)) || (targetDiagram(arrow).equals(psd))){
					newCDArrows.remove(arrow);
				}
			}
		}else{
			throw new IllegalArgumentException("The Uniary diagram does not exist in the concept diagram.");
		}
    	
    	if(newPrimaries.size() == 1){
    		return newPrimaries.get(0);
    	}else{
    		return SpiderDiagrams.createConceptDiagram(newCDArrows, newPrimaries);
    	}
	}
	
	public SpiderDiagram addPrimarySpiderDiagram(PrimarySpiderDiagram psd){
		ArrayList<PrimarySpiderDiagram> newPrimaries = new ArrayList<PrimarySpiderDiagram>(getPrimaries());
		if (! primaries.contains(psd)) {
			newPrimaries.add(psd);
			ConceptDiagram cd = SpiderDiagrams.createConceptDiagram(cd_arrows, newPrimaries);
			if(cd.isValid()){
				return cd;
			}else {throw new IllegalArgumentException("The constructed concept diagram is not valid.");}
			
		}else 	{throw new IllegalArgumentException("The Uniary diagram already exists in the concept diagram.");}
	}
		

	
	@Override
	public SpiderDiagram transform(Transformer t, boolean trackParents) {
		if (t == null) {
            throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "t"));
        }
        return transform(t, this, 0, trackParents ? new ArrayList<CompoundSpiderDiagram>() : null, trackParents ? new ArrayList<Integer>() : null);
	}
	
	
	private static SpiderDiagram transform(Transformer t, ConceptDiagram cd, int subDiagramIndex, ArrayList<CompoundSpiderDiagram> parents, ArrayList<Integer> childIndices){
        SpiderDiagram transformedCD = ((CDTransformer) t).transform(cd, subDiagramIndex, parents, childIndices);
        if (transformedCD != null) {
        	return transformedCD;
        }else if (t.isDone()) {
        	return cd;
        }else{
		ArrayList<PrimarySpiderDiagram> transformedChildren = null;
		for(int primaryIndex = 0; primaryIndex < cd.getPrimaryCount(); ++primaryIndex){
			PrimarySpiderDiagram primary = cd.getPrimary(primaryIndex);
			transformedCD = t.transform((PrimarySpiderDiagram) primary, subDiagramIndex+1, parents, childIndices);
			
			if (transformedCD != null && !transformedCD.equals(primary)){
				if (transformedChildren == null) {
					transformedChildren = new ArrayList<>(cd.primaries);
				}
				transformedChildren.set(primaryIndex, (PrimarySpiderDiagram) transformedCD);
			}
			
            if (t.isDone()) {
                break;
            }
            subDiagramIndex += cd.getSubDiagramCount();
		}
		
		return transformedChildren == null ? cd: SpiderDiagrams.createConceptDiagram(cd.get_cd_Arrows(), transformedChildren, false);}
	}
	

    
    @Override
    public <T> T visit(DiagramVisitor<T> visitor, boolean trackParents) {
        if (visitor == null) {
            throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "visitor"));
        }
        visitor.init(this);
        if (!visitor.isDone()) {
            visitConceptD(
                    visitor,
                    this,
                    0,
                    trackParents ? new ArrayList<CompoundSpiderDiagram>() : null,
                    trackParents ? new ArrayList<Integer>() : null,
                    trackParents ? new ArrayList<Integer>() : null
            );
        }
        visitor.end();
        return visitor.getResult();
    }
    
    
    
    private static <T> boolean visitConceptD(DiagramVisitor<T> visitor, ConceptDiagram cd, int subDiagramIndex, ArrayList<CompoundSpiderDiagram> parents, ArrayList<Integer> childIndices, ArrayList<Integer> parentIndices) {
        visitor.visit(cd, subDiagramIndex, parents, childIndices, parentIndices);       
        
        if (cd.getPrimaryCount() > 0 && !visitor.isDone()) {
        	parentIndices.add(subDiagramIndex);
            
            subDiagramIndex++;	

            for (int childIndex = 0; childIndex < cd.getPrimaryCount(); ++childIndex) {
            	PrimarySpiderDiagram child = cd.getPrimary(childIndex);
            	
                childIndices.add(childIndex);
                
                final boolean visitResult = __visitSD(child, visitor, subDiagramIndex, parents, childIndices, parentIndices);
                
                childIndices.remove(childIndices.size() - 1);
                
                if (visitResult) {
                    return true;
                }

                subDiagramIndex += 1;
            }
            parentIndices.remove(parentIndices.size() - 1);
        }
        return visitor.isDone();
    }
    
    
    
    
    private static <T> boolean __visitSD(SpiderDiagram sd, DiagramVisitor<T> visitor, int subDiagramIndex, ArrayList<CompoundSpiderDiagram> parents, ArrayList<Integer> childIndices, ArrayList<Integer> parentIndices) {
            visitor.visit(sd, subDiagramIndex, parents, childIndices, parentIndices);
            return visitor.isDone();

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
		if (primaries.size() < 2){
			return false;
		}else{
		for (PrimarySpiderDiagram primarySpiderDiagram : primaries) {
            if ((! (primarySpiderDiagram instanceof CompleteCOPDiagram)) || (!primarySpiderDiagram.isValid())) {
                return false;
            }
        }return true;
        }
        
	}
    

	// The sets from a collection are pairwise disjoint if, and only if, the size of their union equals 
	//the sum of their sizes.
	private boolean areContoursIntersectionValid(){
		int allContoursSize = 0;
		SortedSet<String> union = new TreeSet<String>();
		
		for (PrimarySpiderDiagram primary : primaries){
			allContoursSize += primary.getAllContours().size();
			union.addAll(primary.getAllContours());
		}
		
		if(union.size() == allContoursSize){
			return true;
		}
		
		return false;	
	}
	
	
	private boolean areSpidersIntersectionValid(){
		int allSpidersSize = 0;
		SortedSet<String> union = new TreeSet<String>();
		
		for (PrimarySpiderDiagram primary : primaries){
			allSpidersSize += primary.getSpiders().size();
			union.addAll(primary.getSpiders());
		}
		
		if(union.size() == allSpidersSize){
			return true;
		}
		
		return false;	
	}
	
	
	@Override
	public boolean isValid() {
		return arePrimariesValid() &&
				areContoursIntersectionValid() &&
				areSpidersIntersectionValid() &&
				are_CD_ArrowsValid();
	}
		  		  
		  

  private void printArg(Appendable sb, int i) throws IOException {
	  sb.append(CDTextArgAttribute).append(Integer.toString(i)).append(" = ");
	  primaries.get(i - 1).toString(sb);  
  }
  
  
  protected void printArgs(Appendable sb) throws IOException {
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
		 	printId(sb);
	        sb.append(" {");
	        printArgs(sb);
	        sb.append(", ");
	        printArrows(sb);
	        sb.append('}');
		
	}
	


    private void printId(Appendable sb) throws IOException {
        switch (getPrimaryCount()) {
            case 2:
                sb.append(CDTextBinaryId );
                break;
            default:
                sb.append(CDTextId);
                break;
        }
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
            return  hashCode() == cd.hashCode() &&
            		primaries.equals(cd.getPrimaries()) &&
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
	
	
    @Override
    public boolean isSEquivalentTo(SpiderDiagram other) {
        if (equals(other)) {
            return true;
        }
        if (other instanceof ConceptDiagram) {
            ConceptDiagram cd = (ConceptDiagram) other;
            if(primaries.containsAll(cd.getPrimaries()) && cd.getPrimaries().containsAll(primaries) && 
            		cd_arrows.equals(cd.get_cd_Arrows())){
            	return true;
            }
        }
        return false;
    }
    
    
	@Override
	public Iterator<SpiderDiagram> iterator() {
		return new ConceptDiagramIterator(this);
	}
	
    private static class ConceptDiagramIterator implements Iterator<SpiderDiagram> {

    	private ArrayList<SpiderDiagram> iterationStack;
    	private Iterator<SpiderDiagram> it;
    	
    	public ConceptDiagramIterator(ConceptDiagram cdToIterateThrough){
    		if (cdToIterateThrough == null){
              throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "cdToIterateThrough"));
    		}
    		iterationStack = new ArrayList<>();
    		for (int i=0 ; i<= cdToIterateThrough.getPrimaryCount(); i++){
    			iterationStack.add(cdToIterateThrough.getSubDiagramAt(i));
    		}  
    		it = iterationStack.iterator();
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
				SpiderDiagram next = it.next();
				return next;
			}	
			return null;
			
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException(i18n("SD_ITER_REMOVE_NOT_SUPPORTED"));
		}
    	 
    	
    }


	

}
