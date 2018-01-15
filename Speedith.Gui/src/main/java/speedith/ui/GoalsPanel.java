/*
 *   Project: Speedith
 * 
 * File name: GoalsPanel.java
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

/*
 * GoalsPanel.java
 *
 * Created on 26-Nov-2011, 12:08:41
 */
package speedith.ui;

import speedith.core.reasoning.Goals;

import java.awt.*;

import static speedith.i18n.Translations.i18n;

/**
 *
 * @author Matej Urbas [matej.urbas@gmail.com]
 */
public class GoalsPanel extends javax.swing.JPanel {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    private int step = 0;
    private Goals goals;
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Creates new form GoalsPanel
     */
    public GoalsPanel() {
        initComponents();
        this.pnlGoals.addSpiderDiagramClickListener(new SpiderDiagramClickListener() {

            public void spiderDiagramClicked(SpiderDiagramClickEvent e) {
                System.out.println(e.toString());
            }
        });
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Auto-generated Code">
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnlTitle = new javax.swing.JPanel();
        lblGoal = new javax.swing.JLabel();
        javax.swing.JSeparator jSeparator1 = new javax.swing.JSeparator();
        pnlGoals = new speedith.ui.SpiderDiagramPanel();

        setPreferredSize(new java.awt.Dimension(599, 265));
        setLayout(new java.awt.GridBagLayout());

        pnlTitle.setBackground(new java.awt.Color(85, 85, 85));
        pnlTitle.setMinimumSize(new java.awt.Dimension(0, 0));
        pnlTitle.setPreferredSize(new java.awt.Dimension(0, 0));

        lblGoal.setFont(new java.awt.Font("Dialog", Font.PLAIN, 14)); // NOI18N
        lblGoal.setForeground(new java.awt.Color(255, 255, 255));
        lblGoal.setText(getGoalLabel());
        lblGoal.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout pnlTitleLayout = new javax.swing.GroupLayout(pnlTitle);
        pnlTitle.setLayout(pnlTitleLayout);
        pnlTitleLayout.setHorizontalGroup(
            pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTitleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblGoal, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE))
        );
        pnlTitleLayout.setVerticalGroup(
            pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblGoal, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 0, 1);
        add(pnlTitle, gridBagConstraints);

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setMinimumSize(new java.awt.Dimension(0, 0));
        jSeparator1.setPreferredSize(new java.awt.Dimension(0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 1);
        add(jSeparator1, gridBagConstraints);

        pnlGoals.setMinimumSize(new java.awt.Dimension(0, 0));
        pnlGoals.setPreferredSize(new java.awt.Dimension(332, 230));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 1, 1);
        add(pnlGoals, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblGoal;
    private speedith.ui.SpiderDiagramPanel pnlGoals;
    private javax.swing.JPanel pnlTitle;
    // End of variables declaration//GEN-END:variables
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Public Properties">
    public int getReasoningStep() {
        return step;
    }

    public void setReasoningStep(int step) {
        if (this.step != step) {
            this.step = step;
            refreshGoalLabel();
        }
    }

    public Goals getGoals() {
        return goals;
    }

    public void setGoals(Goals goals) {
        if (this.goals != goals) {
            this.goals = goals;
            refreshGoals();
            refreshGoalLabel();
        }
    }

    /**
     * Returns the set of flags that determines which elements of the diagram
     * may be highlighted with the mouse. <p>This flag can be a (binary)
     * combination of the following flags: <ul> <li>{@link speedith.core.reasoning.args.selection.SelectionStep#Spiders}:
     * which indicates that spiders will be highlighted when the user hovers
     * over them.</li> <li>{@link speedith.core.reasoning.args.selection.SelectionStep#Zones}: which indicates that
     * zones will be highlighted when the user hovers over them.</li> <li>{@link speedith.core.reasoning.args.selection.SelectionStep#Contours}:
     * which indicates that circle contours will be highlighted when the user
     * hovers over them.</li> </ul></p> <p> The {@link speedith.core.reasoning.args.selection.SelectionStep#All} and {@link speedith.core.reasoning.args.selection.SelectionStep#None}
     * flags can also be used. These indicate that all diagram or no elements
     * (respectively) can be highlighted with the mouse.</p>
     *
     * @return the set of flags that determines which elements of the diagram
     * may be highlighted with the mouse.
     */
    public int getHighlightMode() {
        return this.pnlGoals.getHighlightMode();
    }

    /**
     * Sets the set of flags that determines which elements of the diagram may
     * be highlighted with the mouse. <p>See {@link SpiderDiagramPanel#setHighlightMode(int)}
     * for more information.</p>
     *
     * @param highlightMode the new set of flags that determines which elements
     * of the diagram may be highlighted with the mouse.
     */
    public void setHighlightMode(int highlightMode) {
        this.pnlGoals.setHighlightMode(highlightMode);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="UI refresh methods">
    private void refreshGoalLabel() {
        lblGoal.setText(getGoalLabel());
    }

    private String getGoalLabel() {
        if (goals == null || goals.getGoalsCount() < 1) {
            return i18n("GOALS_PANEL_NO_SUBGOALS");
        } else if (step == 0) {
            return i18n("GOALS_PANEL_INITIAL_GOAL");
        } else {
            return i18n("GOALS_PANEL_SUBGOAL", this.step);
        }
    }

    private void refreshGoals() {
        if (goals == null || goals.getGoalsCount() < 1) {
            this.pnlGoals.setDiagram(null);
        } else {
            this.pnlGoals.setDiagram(goals.getGoalAt(0));
        }
    }
    // </editor-fold>
}
