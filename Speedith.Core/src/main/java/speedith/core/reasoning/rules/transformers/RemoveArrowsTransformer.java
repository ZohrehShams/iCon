package speedith.core.reasoning.rules.transformers;

import speedith.core.lang.*;
import speedith.core.reasoning.args.ArrowArg;
import speedith.core.reasoning.args.SubDiagramIndexArg;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetChecks;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetExtraction;
import speedith.core.reasoning.ApplyStyle;
import speedith.core.reasoning.RuleApplicationException;

import java.util.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Removes an arrow from a diagram, the diagram has to be a COP diagram. This rule dosen't operate on null, and compound diagrams.
 * @author Zohreh Shams [zs315@cam.ac.uk]
 * */
public class RemoveArrowsTransformer extends IdTransformer {
	private final ApplyStyle applyStyle;
    private  List<ArrowArg> targetArrows;
    
    public RemoveArrowsTransformer(List<ArrowArg> targetArrows, ApplyStyle applyStyle ) {
        this.targetArrows = targetArrows;
        this.applyStyle = applyStyle;
    }
    
    
    
    @Override
    public SpiderDiagram transform(PrimarySpiderDiagram cop,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	COPDiagram cop1 = (COPDiagram) cop;
    	TreeSet<Arrow> newArrows = new TreeSet<>();
        int subDiagramIndex = targetArrows.get(0).getSubDiagramIndex();
    	if (diagramIndex == subDiagramIndex) {
    		if (!cop1.getArrowsMod().containsAll(getTargetArrows())) {
    			throw new TransformationException("The arrows to be removed do not exist in the target diagram");
    			}
    		cop1.getArrowsMod().removeAll(getTargetArrows());
    		newArrows = (TreeSet<Arrow>) cop1.getArrowsMod();
    		return COPDiagram.createCOPDiagram( cop1.getSpidersMod(), cop1.getHabitatsMod(), cop1.getShadedZonesMod(), cop1.getPresentZonesMod(), newArrows);
    	}
    	return cop;	
    }
    
    	
    private List<Arrow> getTargetArrows() {
        ArrayList<Arrow> arrows = new ArrayList<>();
        for (ArrowArg targetArrow : targetArrows) {
            arrows.add(targetArrow.getArrow());
        }
        return arrows;
    }
    
}
