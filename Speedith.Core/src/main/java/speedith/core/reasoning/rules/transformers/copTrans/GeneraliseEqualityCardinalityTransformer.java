package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.SpiderDiagrams;
import speedith.core.lang.TransformationException;
import speedith.core.lang.cop.Cardinality;
import speedith.core.lang.cop.Comparator;
import speedith.core.lang.cop.CompleteCOPDiagram;
import speedith.core.lang.cop.LUCOPDiagram;
import speedith.core.lang.cop.LUCarCOPDiagram;
import speedith.core.reasoning.args.copArgs.ArrowArg;

public class GeneraliseEqualityCardinalityTransformer extends IdTransformer{
	
	private ArrowArg arrowArg;
    private final boolean applyForward;

	
	public GeneraliseEqualityCardinalityTransformer(ArrowArg arrowArg,boolean applyForward)  {
	    this.arrowArg = arrowArg;
	    this.applyForward = applyForward;
	}

	
	@Override
    public SpiderDiagram transform(PrimarySpiderDiagram psd,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	
    	int subDiagramIndex = arrowArg.getSubDiagramIndex();
    	
    	PrimarySpiderDiagram temp = psd;
    	LUCarCOPDiagram luCarCop = (LUCarCOPDiagram) psd;
    
    	
    	if (diagramIndex == subDiagramIndex) {
 
        		assertDiagramContainTargetArrows(luCarCop);
        		
        		if (! luCarCop.getArrows().contains(arrowArg.getArrow())){
        			throw new TransformationException("The arrow does not exists in the diagram.");
        		}
        		
        		
        		if(! luCarCop.getArrowCardinalities().containsKey(arrowArg.getArrow())){
        			throw new TransformationException("The arrow should have a cardinality.");
        		}
        		
        		
        		Comparator sign = luCarCop.getArrowCardinalities().get(arrowArg.getArrow()).getComparator();
        		int num = luCarCop.getArrowCardinalities().get(arrowArg.getArrow()).getNumber();
           		if(! sign.equals("=")){
        			throw new TransformationException("The type of comparator has to be eqality.");
        		}
           		
            	
            	String number;
        		number = JOptionPane.showInputDialog(null,"Enter a natural number:");
        		if (Integer.parseInt(number) < 0){
                	JOptionPane.showMessageDialog(null,"Cardinality cannot be a negative number.","Input error",JOptionPane.ERROR_MESSAGE);
        			throw new TransformationException("Cardinality cannot be a negative number.");
            	}
        		if (Integer.parseInt(number) >= num){
                	JOptionPane.showMessageDialog(null,"New cardinality has to be smaller than the original one.","Input error",JOptionPane.ERROR_MESSAGE);
        			throw new TransformationException("New cardinality has to be smaller than the original one.");
            	}
        		
        		LUCarCOPDiagram newluCarCop = luCarCop.deleteCardinality(arrowArg.getArrow());

        		if (temp instanceof CompleteCOPDiagram){
        			CompleteCOPDiagram compCop = (CompleteCOPDiagram) temp;
        			CompleteCOPDiagram newcompCop = (CompleteCOPDiagram) compCop.deleteCardinality(arrowArg.getArrow());
        			return newcompCop.addCardinality(arrowArg.getArrow(),new Cardinality(">=",number));
        		}else{
        			return newluCarCop.addCardinality(arrowArg.getArrow(),new Cardinality(">=",number));
        		}
    	}
    	return psd;
    }
    
    
    
    private void assertDiagramContainTargetArrows(LUCOPDiagram currentDiagram) {
        if (!currentDiagram.getArrows().contains(arrowArg.getArrow())) {
            throw new TransformationException("The target diagram does not contain the target arrow(s).");
        }
    }

}
