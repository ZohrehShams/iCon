/*
 *   Project: Speedith.Core
 * 
 * File name: SubDiagramIndexArg.java
 *    Author: Matej Urbas [matej.urbas@gmail.com]
 * 
 *  Copyright © 2011 Matej Urbas
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
package speedith.core.reasoning.args;

import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.reasoning.InferenceRule;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.args.copArgs.ArrowArg;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Along with the {@link SubgoalIndexArg subgoal index} this class also
 * provides the <span style="font-style:italic;">sub-diagram index</span> as an
 * additional argument to {@link InferenceRule inference rules}.
 * <p>The diagram index is the number assigned to every sub-diagram and
 * indicates its order of appearance (from left to right) in the whole spider
 * diagram (see {@link SpiderDiagram#getSubDiagramAt(int)}).</p>
 * <p>Note: the containing (parent) diagram takes a lower index than any of its
 * children.</p>
 * @author Matej Urbas [matej.urbas@gmail.com]
 */
public class SubDiagramIndexArg extends SubgoalIndexArg implements RuleArg, Serializable {
    private static final long serialVersionUID = 6981659648623954249L;
    // <editor-fold defaultstate="collapsed" desc="Fields">
    private final int subDiagramIndex;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructor">
    public SubDiagramIndexArg(int subgoalIndex, int subDiagramIndex) {
        super(subgoalIndex);
        this.subDiagramIndex = subDiagramIndex;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Public Properties">
    /**
     * Returns a value which indicates the index of a sub-diagram within a
     * spider diagram.
     * <p>The method {@link SpiderDiagram#getSubDiagramAt(int)} uses this index
     * to return a select part of a spider diagram.</p>
     * <p>Note that only {@link CompoundSpiderDiagram compound spider diagrams}
     * have sub-diagrams.</p>
     * @return a value which indicates the index of a sub-diagram within a
     * spider diagram.
     */
    public int getSubDiagramIndex() {
        return subDiagramIndex;
    }
    // </editor-fold>
    
    public static SubDiagramIndexArg getSubDiagramIndexArgFrom(RuleArg ruleArg) throws RuleApplicationException {
        if (!(ruleArg instanceof SubDiagramIndexArg)) {
            throw new RuleApplicationException("The selected argument is not a COP diagram.");
        }
        return (SubDiagramIndexArg)ruleArg;
    }
    
    
    public static int assertSameSubDiagramIndices(int previousSubDiagramIndex, SubDiagramIndexArg subArg) throws RuleApplicationException {
        if (previousSubDiagramIndex != -1 && previousSubDiagramIndex != subArg.getSubDiagramIndex()) {
            throw new RuleApplicationException("The COPs must be from the same concept diagram.");
        } else {
            previousSubDiagramIndex = subArg.getSubDiagramIndex();
        }
        return previousSubDiagramIndex;
    }
    
    public static ArrayList<SubDiagramIndexArg> getSubDiagramIndexArgsFrom(MultipleRuleArgs multipleRuleArgs) throws RuleApplicationException {
        ArrayList<SubDiagramIndexArg> subDiagramIndexArgs = new ArrayList<>();
        int subDiagramIndex = -1;
        int goalIndex = -1;
        for (RuleArg ruleArg : multipleRuleArgs) {
        	SubDiagramIndexArg subDiagramIndexArg = getSubDiagramIndexArgFrom(ruleArg);
            subDiagramIndex = assertSameSubDiagramIndices(subDiagramIndex, subDiagramIndexArg);
            goalIndex = assertSameGoalIndices(goalIndex,subDiagramIndexArg);
            subDiagramIndexArgs.add(subDiagramIndexArg);
        }
        return subDiagramIndexArgs;
    }
    
    
    
    
}
