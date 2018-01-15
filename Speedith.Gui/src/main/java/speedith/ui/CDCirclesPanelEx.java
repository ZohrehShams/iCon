package speedith.ui;

import javax.swing.JPanel;

import icircles.concreteDiagram.ConcreteDiagrams;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

public class CDCirclesPanelEx extends ACirclesPanelEx{
	
//public class CDCirclesPanelEx extends JPanel{
	
	//private ConcreteCDiagram diagram;
	
    public CDCirclesPanelEx(ConcreteCDiagram diagram) {
    	super(diagram);
    	//super();
    	//this.diagram =diagram;
//    	for (ConcreteCOPDiagram cond: diagram.getPrimaries()){
//    		ConcreteCOPDiagram conDiagram = (ConcreteCOPDiagram) cond;
//    		this.add(new ACirclesPanelEx(conDiagram));
    		//this.add(new SpeedithCirclesPanel(conDiagram));
    		
    	//}

    }

    public CDCirclesPanelEx() {
        this(null);
    }
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        ConcreteCDiagram cDiagram = (ConcreteCDiagram) diagram;
        Line2D.Double tmpArrow = new Line2D.Double();
        
    	for (ConcreteArrow a : cDiagram.getArrows()){
    		
    		if(a.aa.get_type().equals("solid")){
        		//Line2D.Double tmpArrow = new Line2D.Double();
        		//g2d.setStroke(DEFAULT_CONTOUR_STROKE);
    			
        		g2d.setColor(Color.GREEN);  	                		
        		//System.out.println("X1=" + a.x_s+ "Y1=" +a.y_s+ "X2=" + a.x_t + "Y2=" + a.y_t);

        		
        		tmpArrow = a.getScaledArrow(scaleFactor);
        		g2d.draw(tmpArrow);
        		
        		//System.out.println("X1=" + tmpArrow.getX1() + "Y1=" +tmpArrow.getY1() + "X2=" + tmpArrow.getX2() + "Y2=" + tmpArrow.getY2());
        		
        		double dy = tmpArrow.y2  - tmpArrow.y1;
        		double dx = tmpArrow.x2  - tmpArrow.x1;
        		
        		double phi = Math.toRadians(40);
        		
        		int barb = 20;
        		
        		double theta = Math.atan2(dy, dx);
        		
        		double x, y, rho = theta + phi;
        		
                for(int j = 0; j < 2; j++)
                {
                    x = tmpArrow.x2 - barb * Math.cos(rho);
                    y = tmpArrow.y2 - barb * Math.sin(rho);
                    g2d.draw(new Line2D.Double(tmpArrow.x2, tmpArrow.y2, x, y));
                    rho = theta - phi;
                }
    		}
    		
    		if(a.aa.get_type().equals("dashed")){
        		//Line2D.Double tmpArrow = new Line2D.Double();
        		g2d.setStroke(dashed);
        		g2d.setColor(Color.black);  	
        		tmpArrow = a.getScaledArrow(scaleFactor);
        		g2d.draw(tmpArrow);
        		
        		double dy = tmpArrow.y2  - tmpArrow.y1;
        		double dx = tmpArrow.x2  - tmpArrow.x1;
        		
        		double phi = Math.toRadians(40);
        		
        		int barb = 20;
        		
        		double theta = Math.atan2(dy, dx);
        		
        		double x, y, rho = theta + phi;
        		
                for(int j = 0; j < 2; j++)
                {
                    x = tmpArrow.x2 - barb * Math.cos(rho);
                    y = tmpArrow.y2 - barb * Math.sin(rho);
                    g2d.draw(new Line2D.Double(tmpArrow.x2, tmpArrow.y2, x, y));
                    rho = theta - phi;
                }
    		}
    		
    		
    		
            if (a.aa.getLabel() != null) {
            	g.setFont(new Font("Helvetica", Font.BOLD,  12));
            	if (a.aa.getCardinality() == null){
                    g2d.drawString(a.aa.getLabel(),
                            (int) (a.getLabelXPosition() * trans.getScaleX())+5,
                            (int) (a.getLabelYPosition() * trans.getScaleY()));
            	}else{
                    g2d.drawString(a.aa.getLabel()+a.aa.getCardinality().toString(),
                            (int) (a.getLabelXPosition() * trans.getScaleX())+5,
                            (int) (a.getLabelYPosition() * trans.getScaleY()));
            	}
            }  		
    	}
    }

}
