package speedith.ui.concretes;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;

import icircles.abstractDescription.AbstractDescription;
import icircles.concreteDiagram.ConcreteDiagram;
import icircles.concreteDiagram.ConcreteDiagrams;
import icircles.util.CannotDrawException;
import speedith.ui.CDiagramCreator;
import speedith.ui.abstracts.CDAbstractDescription;

import java.util.Set;

public class ConcreteCDiagram implements ConcreteDiagrams{

	ArrayList<ConcreteCOPDiagram> cd_primaries;
	ArrayList<ConcreteArrow> cd_arrows;
	
	public ConcreteCDiagram(ArrayList<ConcreteCOPDiagram> cd_primaries,ArrayList<ConcreteArrow> cd_arrows){
		this.cd_arrows = cd_arrows;
		this.cd_primaries = cd_primaries;
	}
	
    public ArrayList<ConcreteArrow> getArrows() {
        return cd_arrows;
    }
    
    public int getArrowsCount(){
    	return cd_arrows.size();
    }
    
    public ArrayList<ConcreteCOPDiagram> getPrimaries() {
        return cd_primaries;
    }
    
    public Set<ConcreteCOPDiagram> getCOPs() {
        //return (Set<ConcreteCOPDiagram>) cd_primaries;
    	return new HashSet<ConcreteCOPDiagram>(cd_primaries);
    }
    
    
    public static ConcreteCDiagram makeConcreteDiagram(CDAbstractDescription ad, int size) throws CannotDrawException {
        CDiagramCreator dc = new CDiagramCreator(ad);
        return dc.createDiagram(size);
    }
    
    public void addConcreteArrows(ArrayList<ConcreteArrow> cdArrows){
    	cd_arrows.addAll(cdArrows);
    }
    
    
    public ConcreteArrow getArrowAtPoint(Point p) {
        for (ConcreteArrow arrow : this.cd_arrows) {
        	//Double ptSeg = Line2D.ptSegDist(arrow.get_xs(), arrow.get_ys(), arrow.get_xt(),arrow.get_yt(), p.getX() , p.getY());
        	Double ptSeg = Line2D.ptLineDist(arrow.get_xs(), arrow.get_ys(), arrow.get_xt(),arrow.get_yt(), p.getX() , p.getY());
        	//if ( (ptSeg < 0.0) && !(ptSeg > 0.0)){
        	  if ( Math.abs(ptSeg) < 4.0){
        		return arrow;
        	}
        }
        return null;
    }
    
    
    
	@Override
	public int getSize() {
		int size =0;
		for ( ConcreteCOPDiagram cop: this.cd_primaries){
			size += cop.getSize();
		}
		return size;
	}

	@Override
	public Rectangle2D.Double getBox() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
