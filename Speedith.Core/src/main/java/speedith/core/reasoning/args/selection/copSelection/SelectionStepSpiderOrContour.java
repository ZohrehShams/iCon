/*
 *   Project: Speedith
 * 
 * File name: Class.java
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
package speedith.core.reasoning.args.selection.copSelection;

import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.args.selection.SelectionSequence;
import speedith.core.reasoning.args.selection.SelectionStep;
import speedith.core.reasoning.args.selection.SelectionStep.SelectionRejectionExplanation;

import java.util.List;
import java.util.Locale;

import static speedith.core.i18n.Translations.i18n;

/**
 *This selection step allows selecting a single contour or spider. 
 *It is used when adding an arrow to a diagram. 
 *@author Zohreh Shams
 */
public class SelectionStepSpiderOrContour extends SelectionStep {
	private final boolean skippable;

//    public SelectionStepSpiderOrContour() {
//    }
//
//  @Override
//  public boolean isFinished(SelectionSequence selection, int thisIndex) {
//      return false;
//  }
//
//  @Override
//  public boolean isSkippable(SelectionSequence selection, int thisIndex) {
//      return true;
//      }
	
    
    public SelectionStepSpiderOrContour(boolean skippable) {
    	this.skippable = skippable;
    }
    
    @Override
    public boolean isFinished(SelectionSequence selection, int thisIndex) {
        return isFinished(selection.getAcceptedSelectionsForStepAt(thisIndex)); 
    }

    private static boolean isFinished(List<RuleArg> sels) {
        return sels != null
                && sels.size() == 1
                && (sels.get(0) instanceof SpiderArg || sels.get(0) instanceof ContourArg); 
    }
    
    

    @Override
    public boolean isSkippable(SelectionSequence selection, int thisIndex) {
        return skippable || isFinished(selection, thisIndex);
    }
    
    



    @Override
    public String getInstruction(Locale locale, SelectionSequence selection, int thisIndex) {
        return i18n(locale, "SELECTION_STEP_MSG_SPIDER_CONTOUR");
    }

    @Override
    public SelectionRejectionExplanation acceptSelection(RuleArg selection, SelectionSequence selectionSeq, int thisIndex) {
        return null;
    }

    @Override
    public boolean cleanSelectionOnStart() {
        return false;
    }

    
    @Override
    public SelectionRejectionExplanation init(SelectionSequence selection, int thisIndex) {
        if (selection.getAcceptedSelectionsCount(thisIndex) > 0) {
            throw new IllegalStateException(i18n("SELSTEP_SINGLE_ELEMENT"));
        }
        return null;
    }
    


    @Override
    public int getSelectableElements() {
        return SelectionStep.SpidersContours;
    }
    
}
