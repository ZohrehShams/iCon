package speedith.core.reasoning.rules.instructions.copIns;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import speedith.core.lang.Arrow;
import speedith.core.lang.Operator;
import speedith.core.lang.TransformationException;
import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.args.selection.SelectSingleSpiderStep;
import speedith.core.reasoning.args.selection.SelectionSequence;
import speedith.core.reasoning.args.selection.SelectionStep;
import speedith.core.reasoning.args.selection.SelectionStepAny;
import speedith.core.reasoning.args.selection.copSelection.SelectionStepSpiderOrContour;


public class TRAddArrowInstruction implements RuleApplicationInstruction<ArrowArg>{
	
	private final List<? extends SelectionStep> steps = Arrays.asList(new SelectionStepSpiderOrContour(false), new SelectionStepSpiderOrContour(false));

    private TRAddArrowInstruction() {
    }
    
    
    public static TRAddArrowInstruction getInstance() {
        return SingletonContainer.TheInstructions;
    }
    
    
    public List<? extends SelectionStep> getSelectionSteps() {
        return Collections.unmodifiableList(steps);
    }
    
    
    
    public ArrowArg extractRuleArg(SelectionSequence selectionSequence, int subgoalIndex){
    	
    	String source = null;
    	String target = null;
    	int subDiagramIndex = 0;
    	
    	RuleArg sourceArg = selectionSequence.getAcceptedSelectionsForStepAt(0).get(0);
    	RuleArg targetArg = selectionSequence.getAcceptedSelectionsForStepAt(1).get(0);
    	
    	if (sourceArg instanceof ContourArg) {
            ContourArg contourArg = (ContourArg) sourceArg;
            source =  contourArg.getContour();	
            subDiagramIndex= contourArg.getSubDiagramIndex();
    	}

        if (sourceArg instanceof SpiderArg) {
            SpiderArg spiderArg = (SpiderArg) sourceArg;
            source =  spiderArg.getSpider();
            subDiagramIndex= spiderArg.getSubDiagramIndex();
        }
        
        
    	if (targetArg instanceof ContourArg) {
            ContourArg contourArg = (ContourArg) targetArg;
            target =  contourArg.getContour();	
    	}

        if (targetArg instanceof SpiderArg) {
            SpiderArg spiderArg = (SpiderArg) targetArg;
            target =  spiderArg.getSpider();
        }
        
     
//		Scanner reader = new Scanner(System.in); 
//		System.out.println("Enter the type of arrow: ");
//		String type = reader.next();
        
        String type;
        type = JOptionPane.showInputDialog(null,"Enter the type of arrow:");
    	if ((! type.equals("solid")) && (! type.equals("dashed"))){
        	JOptionPane.showMessageDialog(null,"The type of arrow has to be solid or dashed.","Input error",JOptionPane.ERROR_MESSAGE);
			throw new TransformationException("The type of arrow has to be solid or dashed.");
    	}
        
        
        
        String label;
        label = JOptionPane.showInputDialog(null,"Enter the label of the arrow:");
        if (label.isEmpty()){
        	JOptionPane.showMessageDialog(null,"Arrow label cannot be empty.","Input error",JOptionPane.ERROR_MESSAGE);
        	throw new TransformationException("Arrow label cannot be empty.");
        }

			Arrow arrow = new Arrow(source,target,type,label);
	        return new ArrowArg(subgoalIndex,subDiagramIndex,arrow);

    	
    }
    
    
    
    
    private static final class SingletonContainer {
        private static final TRAddArrowInstruction TheInstructions = new TRAddArrowInstruction();
    }
}
