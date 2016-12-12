public class IndexPoint {

    final private double[] minValues;
    final private double[] maxValues;

    private double[] currentValues;

    private double percentIncrement;
    private int length;

    public IndexPoint(double[] minValues, double[] maxValues, double percentIncrement) {
        this.minValues = minValues;
        this.maxValues = maxValues;
        
        this.currentValues = Helper.copyDoubleArray(minValues);
        
        this.percentIncrement = percentIncrement;
        this.length = maxValues.length;
    }
    
    public boolean increment() {
        
        for (int i = 0; i < length; i++) {
            if (currentValues[i] >= maxValues[i]) {
                currentValues[i] = minValues[i];
            } else {
                currentValues[i] += percentIncrement * (maxValues[i] - minValues[i]);
                return true;
            }
        }
        
        return false;
    }
    
    public double distanceTo(Point p) {
        Point myPoint = new Point(currentValues, 0);
        return myPoint.distanceTo(p);
    }
    
    public boolean closeEnoughTo(Point p) {
        Point myPoint = new Point(currentValues, 0);
        return myPoint.distanceTo(p) < getRadius();
    }
    
    public double getRadius() {
        return percentIncrement * (new Point(minValues, 0)).distanceTo(new Point(maxValues, 0));
    }
    
}