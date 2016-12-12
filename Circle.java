public class Circle {
    
    public Point point;
    public double radius;
    
    public Circle(Point point, double radius) {
        this.point = point;
        this.radius = radius;
    }
    
    public String toString() {
        return "Point: " + point + " Radius: " + radius;
    }
    
    public boolean contains(Point p) {
        return point.distanceTo(p) <= radius;
    }
    
    public boolean touches(Circle c) {
        return point.distanceTo(c.point) < radius + c.radius;
    }
    
    public void joinWith(Circle c) {
        double newRadius = point.distanceTo(c.point) + c.radius;
        if (newRadius > radius) {
            radius = newRadius;
            
            double multiplier = radius/c.radius;
            double[] cValues = c.point.getValues();
            double[] myValues = point.getValues();
            double[] averageValues = new double[myValues.length];
            for (int i = 0; i < averageValues.length; i++) {
                averageValues[i] = (cValues[i]/multiplier + myValues[i]*multiplier) / (multiplier * multiplier);
            }
            
            point = new Point(averageValues, point.ID);
        }
    }
}