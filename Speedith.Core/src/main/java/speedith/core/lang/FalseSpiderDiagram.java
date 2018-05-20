package speedith.core.lang;

import static speedith.core.i18n.Translations.i18n;

import java.io.IOException;
import java.io.Serializable;

public class FalseSpiderDiagram extends NullSpiderDiagram implements Serializable{

	private static final long serialVersionUID = 2393036742756005960L;
	public static final String SDTextFalseId = "FalseSD";
	
    private FalseSpiderDiagram() {
    	super();
    }


	@Override
	public boolean equals(Object other) {
		return this == other || other instanceof FalseSpiderDiagram;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void toString(Appendable sb) throws IOException {
        if (sb == null) {
            throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "sb"));
        }
        sb.append(SDTextFalseId);

	}
	
    public static FalseSpiderDiagram getInstance() {
        return SingletonHolder.Instance;
    }
    
    
    private static class SingletonHolder {
        public static final FalseSpiderDiagram Instance = new FalseSpiderDiagram();
    }

}
