package speedith.core.lang;

import java.util.ArrayList;

/**
 * An interface for the transformation of CD.
 * @author Zohreh Shams
 */

public interface CDTransformer extends Transformer{
	
    public SpiderDiagram transform(ConceptDiagram cd, int diagramIndex, ArrayList<CompoundSpiderDiagram> parents, ArrayList<Integer> childIndices) throws TransformationException;


}
