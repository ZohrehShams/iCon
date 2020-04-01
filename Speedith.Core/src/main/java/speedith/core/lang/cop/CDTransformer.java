package speedith.core.lang.cop;

import java.util.ArrayList;

import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Transformer;

/**
 * An interface for the transformation of CD.
 * @author Zohreh Shams
 */

public interface CDTransformer extends Transformer{
	
    public SpiderDiagram transform(ConceptDiagram cd, int diagramIndex, ArrayList<CompoundSpiderDiagram> parents, ArrayList<Integer> childIndices) throws TransformationException;


}
