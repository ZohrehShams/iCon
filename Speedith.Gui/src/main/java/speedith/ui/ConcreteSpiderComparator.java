package speedith.ui;

public class ConcreteSpiderComparator {

    private double xs;
    private double ys;
    private double xt;
    private double yt;
    private AbstractSpiderComparator asc;
    
    public ConcreteSpiderComparator(double xs, double ys, double xt, double yt, AbstractSpiderComparator asc) {
        this.xs = xs;
        this.ys = ys;
        this.xt = xt;
        this.yt = yt;
        this.asc = asc;
    }
    
    
    public double get_xs(){
    	return xs;
    }
    
    
    public double get_ys(){
    	return ys;
    }
    
    
    public double get_xt(){
    	return xt;
    }
    
    
    public double get_yt(){
    	return yt;
    }
    
    
    public AbstractSpiderComparator get_asc(){
    	return asc;
    }
    
    
    public double getLabelXPosition() {
        return (xs + xt)  / 2;
    }

    
    public double getLabelYPosition() {
    	return (ys + yt)  / 2;
    }
    
    
    
}
