package speedith.core.reasoning.args.copArgs;

import speedith.core.lang.SpiderComparator;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SubDiagramIndexArg;

/**
 * Contains the spider comparator that should be passed to an inference rule.
 * @author Zohreh Shams [zs315@cam.ac.uk]
 */
public class SpiderComparatorArg extends SubDiagramIndexArg{
	private static final long serialVersionUID = -5354388036931868356L;
	private final SpiderComparator spiderComparator;

    public SpiderComparatorArg(int subgoalIndex, int primarySDIndex, SpiderComparator spiderComparator) {
        super(subgoalIndex, primarySDIndex);
        if (spiderComparator == null) {
            throw new IllegalArgumentException(speedith.core.i18n.Translations.i18n("GERR_EMPTY_ARGUMENT", "spiderComparator"));
        }
        this.spiderComparator = spiderComparator;
    }


    public SpiderComparator getSpiderComparator() {
        return spiderComparator;
    }
    

    public static SpiderComparatorArg getSpiderComparatorArgFrom(RuleArg ruleArg) throws RuleApplicationException {
        if (!(ruleArg instanceof SpiderComparatorArg)) {
            throw new RuleApplicationException("This rule takes a spider comparator as argument.");
        }
        return (SpiderComparatorArg)ruleArg;
    }
    
   
}

