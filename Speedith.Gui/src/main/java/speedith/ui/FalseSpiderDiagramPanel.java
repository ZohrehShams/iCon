package speedith.ui;

import java.awt.Color;
import java.awt.Font;

public class FalseSpiderDiagramPanel extends javax.swing.JPanel {
	
	// <editor-fold defaultstate="collapsed" desc="Fields">
    private boolean highlighting = false;
    private static final Color DefaultColor = new Color(0, 0, 0);
    private static final Color HighlightColor = new Color(0xff, 0, 0);
    private static final String FalseSymbol = "\u22A5";
    //private static final String FalseSymbol = "\u29C4";
    private Font defaultFont;
    private Font highlightFont;
    // </editor-fold>


    public FalseSpiderDiagramPanel() {
        this(null);
    }

    public FalseSpiderDiagramPanel(Font font) {
        initComponents();
        if (font == null) {
            font = getFont();
        }
        resetFont(font);
    }

    
    private void initComponents() {

        lblNullSD = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(25, 33));
        setLayout(new java.awt.BorderLayout());

        lblNullSD.setForeground(DefaultColor);
        lblNullSD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNullSD.setText(FalseSymbol);
        
        lblNullSD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onLabelClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                onMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                onMouseEntered(evt);
            }
        });
        add(lblNullSD, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void onMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onMouseExited
        if (isHighlighting()) {
            applyNoHighlight();
        }
    }//GEN-LAST:event_onMouseExited

    private void onMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onMouseEntered
        if (isHighlighting()) {
            applyHighlight();
        }
    }//GEN-LAST:event_onMouseEntered

    private void onLabelClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onLabelClicked
        dispatchEvent(evt);
    }//GEN-LAST:event_onLabelClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblNullSD;
    // End of variables declaration//GEN-END:variables

    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    public void setHighlighting(boolean enable) {
        if (enable != highlighting) {
            this.highlighting = enable;
            if (!this.highlighting) {
                applyNoHighlight();
            }
        }
    }

    public boolean isHighlighting() {
        return highlighting;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Overrides">
    @Override
    public void setFont(Font font) {
        super.setFont(font);
        resetFont(font);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Private Helper Methods">
    private void applyNoHighlight() {
        lblNullSD.setFont(defaultFont);
        lblNullSD.setForeground(DefaultColor);
    }

    private void applyHighlight() {
        lblNullSD.setFont(highlightFont);
        lblNullSD.setForeground(HighlightColor);
    }

    private void resetFont(Font font) {
        if (font == null) {
            // NOTE: Maybe we should get the default font? Something like:
            // UIManager.getDefaults().getFont("Label.font");
            font = new Font(Font.DIALOG, Font.PLAIN, 12);
        }
        defaultFont = font.deriveFont(24f);
        highlightFont = font.deriveFont(Font.BOLD, 26f);
        if (lblNullSD != null) {
            lblNullSD.setFont(defaultFont);
        }
    }
    // </editor-fold>

}
