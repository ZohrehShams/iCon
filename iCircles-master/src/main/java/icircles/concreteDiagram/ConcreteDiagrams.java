package icircles.concreteDiagram;

import java.awt.geom.Rectangle2D;

import icircles.abstractDescription.AbstractShape;

/**
 * This class is merely introduced to allow ConcreteDiagram, ConcreteCOPDiagram and ConcreteCDiagram  to be unified as concrete diagrams. 
 * @author Zohreh Shams
 */
public interface ConcreteDiagrams {

	Rectangle2D.Double getBox();
	int getSize();

}
