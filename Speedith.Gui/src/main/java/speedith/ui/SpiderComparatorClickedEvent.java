package speedith.ui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import icircles.concreteDiagram.ConcreteDiagram;
import speedith.ui.concretes.ConcreteSpiderComparator;

/**
 * This is the event descriptor of the spider equality clicked event. <p>This event descriptor contains the clicked {@link
 * ConcreteSpiderComparator spiderComparator}.</p>
 *
 * @author Zohreh Shams
 */
public class SpiderComparatorClickedEvent extends DiagramClickEvent{
	
	private static final long serialVersionUID = -7959491404486631523L;
    private final ConcreteSpiderComparator spiderComparator;


    public SpiderComparatorClickedEvent(SpeedithCirclesPanel source, ConcreteDiagram diagram, MouseEvent clickInfo, Point diagramCoordinates, 
    		ConcreteSpiderComparator spiderComparator) {
        super(source, diagram, clickInfo, diagramCoordinates);
        if (spiderComparator == null) {
            throw new IllegalArgumentException(icircles.i18n.Translations.i18n("GERR_NULL_ARGUMENT", "arrow"));
        }
        this.spiderComparator = spiderComparator;
    }


    public ConcreteSpiderComparator getSpiderComparator() {
        return spiderComparator;
    }
    

}
