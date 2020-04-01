/*
 *   Project: Speedith.Core
 * 
 * File name: CopyContour.java
 *    Author: Matej Urbas [matej.urbas@gmail.com]
 * 
 *  Copyright © 2012 Matej Urbas
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package speedith.core.reasoning.rules.cop;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

import speedith.core.i18n.Translations;
import speedith.core.lang.DiagramType;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.cop.ConceptDiagram;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.InferenceRule;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.RuleApplicationResult;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.args.SubDiagramIndexArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.copIns.SelectSpiderAndSingleSubDiagramInstruction;
import speedith.core.reasoning.rules.transformers.copTrans.CopySpiderCDTransformer;

/**
 * @author Zohreh Shams
 */
public class CopySpiderCD extends SimpleInferenceRule<MultipleRuleArgs> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String InferenceRuleName = "copy_spider";

    private static final Set<DiagramType> applicableTypes = EnumSet.of(DiagramType.EulerDiagram, DiagramType.SpiderDiagram, DiagramType.LUCOPDiagram);



    @Override
    public InferenceRule<MultipleRuleArgs> getInferenceRule() {
        return this;
    }

    @Override
    public String getInferenceName() {
        return InferenceRuleName;
    }

    @Override
    public String getDescription(Locale locale) {
        return Translations.i18n(locale, "COPY_SPIDER_CD_DESCRIPTION");
    }

    @Override
    public String getCategory(Locale locale) {
        return Translations.i18n(locale, "INF_RULE_CATEGORY_HETEROGENEOUS");
    }

    @Override
    public String getPrettyName(Locale locale) {
        return Translations.i18n(locale, "COPY_SPIDER_PRETTY_NAME");
    }

    @Override
    public Class<MultipleRuleArgs> getArgumentType() {
        return MultipleRuleArgs.class;
    }

    @Override
    public Set<DiagramType> getApplicableTypes() {
        return applicableTypes;
    }

    @Override
    public RuleApplicationInstruction<MultipleRuleArgs> getInstructions() {
        return new SelectSpiderAndSingleSubDiagramInstruction();
    }


    
    
    
    private SpiderArg getSpiderArg(MultipleRuleArgs args) throws RuleApplicationException {
    	if(args.get(0) instanceof SpiderArg){
    		return (SpiderArg) args.get(0);
    	}else{
        	throw new RuleApplicationException("The first argument is expected to be a spider.");
    	}
    }
    
    
    private SubDiagramIndexArg getTargetDiagramArg(MultipleRuleArgs args) throws RuleApplicationException {
    	if(args.get(1) instanceof SubDiagramIndexArg){
    		return (SubDiagramIndexArg) args.get(1);
    	}else{
        	throw new RuleApplicationException("The second argument is expected to be a subdiagram.");
    	}
    }
    

    
    
    @Override
    public RuleApplicationResult apply(RuleArg args, Goals goals) throws RuleApplicationException {
        MultipleRuleArgs ruleArgs = getTypedRuleArgs(args);
        MultipleRuleArgs.assertArgumentsNotEmpty(ruleArgs);
        SpiderArg spider = getSpiderArg(ruleArgs);
        SubDiagramIndexArg target = getTargetDiagramArg(ruleArgs);
        return apply(spider, target, goals);
    }
    
    
    
    // The parent of the diagram is either a concept diagram or a compound (conjunction for this rule). If it's  a concept diagram, 
    // we need the index of the grandparent that is a compound.
    private RuleApplicationResult apply(SpiderArg targetSpider, SubDiagramIndexArg targetDiagram,Goals goals) throws RuleApplicationException {
        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
        SpiderDiagram targetSubgoal = getSubgoal(targetSpider, goals);
        int indexOfParent = targetSubgoal.getParentIndexOf(targetSpider.getSubDiagramIndex());
        
        SpiderDiagram sd = targetSubgoal.getSubDiagramAt(indexOfParent);
        
        if(sd instanceof ConceptDiagram){
        	int indexOfGrandParent = targetSubgoal.getParentIndexOf(indexOfParent);
            newSubgoals[targetSpider.getSubgoalIndex()] = targetSubgoal.transform(new CopySpiderCDTransformer(indexOfGrandParent, targetSpider, targetDiagram));
        }else{
            newSubgoals[targetSpider.getSubgoalIndex()] = targetSubgoal.transform(new CopySpiderCDTransformer(indexOfParent, targetSpider, targetDiagram));
        }
        return createRuleApplicationResult(newSubgoals);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
