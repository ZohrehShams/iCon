package speedith.ui;


import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.NullSpiderDiagram;
import speedith.core.lang.Operator;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.Region;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.SpiderDiagrams;
import speedith.core.lang.Zone;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.InferenceRule;
import speedith.core.reasoning.InferenceRules;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.RuleApplicationType;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.rules.cop.TRAddArrow;
import speedith.ui.selection.SelectionDialog;

public class TestingFormTRAddArrow extends javax.swing.JFrame {

    public TestingFormTRAddArrow() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        proofPanel1 = new ProofPanel(Goals.createGoalsFrom(SpeedithMainForm.getExampleFLUCOP()));
        
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Apply rule");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1))
            .addComponent(proofPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(proofPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    @SuppressWarnings("unchecked")
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        InferenceRule<RuleArg> addUnArrow = (InferenceRule<RuleArg>) InferenceRules.getInferenceRule(TRAddArrow.InferenceRuleName);
        RuleApplicationInstruction<?> instructions = addUnArrow.getProvider().getInstructions();
        SelectionDialog dsd = new SelectionDialog(this, true, proofPanel1.getLastGoals().getGoalAt(0), instructions.getSelectionSteps());
        dsd.setVisible(true);
        if (!dsd.isCancelled()) {
            RuleArg ruleArg = instructions.extractRuleArg(dsd.getSelection(), 0);
            try {
                proofPanel1.applyRule(addUnArrow, ruleArg, RuleApplicationType.INTERACTIVE,"");
            } catch (RuleApplicationException ex) {
                System.out.println("Error!" + ex.getMessage());
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TestingFormTRAddArrow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TestingFormTRAddArrow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TestingFormTRAddArrow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TestingFormTRAddArrow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new TestingFormTRAddArrow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private speedith.ui.ProofPanel proofPanel1;
    // End of variables declaration//GEN-END:variables

    // <editor-fold defaultstate="collapsed" desc="Spider Diagram Examples">
    /**
     * s1: A, B s2: AB
     *
     * @return
     */
    public static PrimarySpiderDiagram getSDExample1() {
        PrimarySpiderDiagram emptyPSD = SpiderDiagrams.createPrimarySD(null, null, null, null);
        Region s1Region = regionA_B__B_A();
        Region s2Region = regionAB();
        emptyPSD = emptyPSD.addSpider("s1", s1Region);
        return emptyPSD.addSpider("s2", s2Region);
    }

    /**
     * s1: A s2: AB
     *
     * @return
     */
    public static PrimarySpiderDiagram getSDExample5() {
        PrimarySpiderDiagram emptyPSD = SpiderDiagrams.createPrimarySD(null, null, null, null);
        Region s1Region = regionA_B();
        Region s2Region = regionAB();
        emptyPSD = emptyPSD.addSpider("s1", s1Region);
        return emptyPSD.addSpider("s2", s2Region);
    }

    /**
     * s1: B s2: AB
     *
     * @return
     */
    public static PrimarySpiderDiagram getSDExample6() {
        PrimarySpiderDiagram emptyPSD = SpiderDiagrams.createPrimarySD(null, null, null, null);
        Region s1Region = regionB_A();
        Region s2Region = regionAB();
        emptyPSD = emptyPSD.addSpider("s1", s1Region);
        return emptyPSD.addSpider("s2", s2Region);
    }

    /**
     * s1: A, AB s2: B, AB
     *
     * @return
     */
    public static PrimarySpiderDiagram getSDExample7() {
        PrimarySpiderDiagram emptyPSD = SpiderDiagrams.createPrimarySD(null, null, null, null);
        Region s1Region = regionA_B__AB();
        Region s2Region = regionB_A__AB();
        emptyPSD = emptyPSD.addSpider("s1", s1Region);
        return emptyPSD.addSpider("s2", s2Region);
    }

    /**
     * s1: B, AB s2: AB
     *
     * @return
     */
    public static PrimarySpiderDiagram getSDExample8() {
        PrimarySpiderDiagram emptyPSD = SpiderDiagrams.createPrimarySD(null, null, null, null);
        Region s1Region = regionB_A__AB();
        Region s2Region = regionAB();
        emptyPSD = emptyPSD.addSpider("s1", s1Region);
        return emptyPSD.addSpider("s2", s2Region);
    }

    /**
     * s1: B, AB s2: A, AB
     *
     * @return
     */
    public static PrimarySpiderDiagram getSDExample9() {
        PrimarySpiderDiagram emptyPSD = SpiderDiagrams.createPrimarySD(null, null, null, null);
        Region s1Region = regionB_A__AB();
        Region s2Region = regionA_B__AB();
        emptyPSD = emptyPSD.addSpider("s1", s1Region);
        return emptyPSD.addSpider("s2", s2Region);
    }

    /**
     * s1: A, AB s2: AB
     *
     * @return
     */
    public static PrimarySpiderDiagram getSDExample10() {
        PrimarySpiderDiagram emptyPSD = SpiderDiagrams.createPrimarySD(null, null, null, null);
        Region s1Region = regionA_B__AB();
        Region s2Region = regionAB();
        emptyPSD = emptyPSD.addSpider("s1", s1Region);
        return emptyPSD.addSpider("s2", s2Region);
    }

    public static CompoundSpiderDiagram getSDExample2() {
        PrimarySpiderDiagram psd1 = getSDExample1();
        PrimarySpiderDiagram psd2 = getSDExample1();
        CompoundSpiderDiagram csd = SpiderDiagrams.createCompoundSD(Operator.Equivalence, psd1, psd2);
        return csd;
    }

    public static NullSpiderDiagram getSDExample3() {
        return SpiderDiagrams.createNullSD();
    }

    public static CompoundSpiderDiagram getSDExample4() {
        PrimarySpiderDiagram sd1 = getSDExample1();
        SpiderDiagram sd2 = getSDExample2();
        CompoundSpiderDiagram csd = SpiderDiagrams.createCompoundSD(Operator.Conjunction, sd1, sd2);
        return csd;
    }

    private static Region regionA_B() {
        return new Region(zoneA_B());
    }

    private static Region regionA_B__AB() {
        return new Region(zoneA_B(), zoneAB());
    }

    private static Region regionA_B__B_A() {
        return new Region(zoneA_B(), zoneB_A());
    }

    private static Region regionB_A() {
        return new Region(zoneB_A());
    }

    private static Region regionB_A__AB() {
        return new Region(zoneB_A(), zoneAB());
    }

    private static Region regionAB() {
        return new Region(zoneAB());
    }

    private static Zone zoneAB() {
        return Zone.fromInContours("A", "B");
    }

    private static Zone zoneA_B() {
        return Zone.fromInContours("A").withOutContours("B");
    }

    private static Zone zoneB_A() {
        return Zone.fromInContours("B").withOutContours("A");
    }
    // </editor-fold>

}
