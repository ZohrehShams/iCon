package speedith.ui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import icircles.concreteDiagram.ConcreteDiagram;

/**
 * This is the event descriptor of the {@link DiagramClickListener#arrowClicked(icircles.gui.ContourClickedEvent)
 * arrow clicked event}. <p>This event descriptor contains the clicked {@link
 * ConcreteArrow arrow}.</p>
 *
 * @author Zohreh Shams
 */
public class ArrowClickedEvent extends DiagramClickEvent{
	
	 /**
     * The clicked arrow.
     */
    private final ConcreteArrow arrow;


    public ArrowClickedEvent(SpeedithCirclesPanel source, ConcreteDiagram diagram, MouseEvent clickInfo, Point diagramCoordinates, ConcreteArrow arrow) {
        super(source, diagram, clickInfo, diagramCoordinates);
        if (arrow == null) {
            throw new IllegalArgumentException(icircles.i18n.Translations.i18n("GERR_NULL_ARGUMENT", "arrow"));
        }
        this.arrow = arrow;
    }

    /**
     * Returns the arrow that was clicked by the user.
     *
     * @return the arrow that was clicked by the user.
     */
    public ConcreteArrow getArrow() {
        return arrow;
    }
    
    /**
     * Returns the label of the arrow.
     * @return the label of the arrow.
     */
    public String getArrowLabel() {
        return arrow.aa.getLabel();
    }

}
