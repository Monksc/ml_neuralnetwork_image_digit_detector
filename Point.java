public class Point {

    private double[] values;

    public final int ID;

    public Point(double[] values, int id) {
        this.values = values;
        this.ID = id;
    }
    
    public double[] getValues() {
        return this.values;
    }
    
    public double distanceTo(Point p) {
        return Math.sqrt(sumDifferanceOfValueSquared(0, p));
    }
    private double sumDifferanceOfValueSquared(int index, Point p) {
        if (index == values.length) {
            return 0;
        }
        
        double differance = p.values[index] - this.values[index];
        return (differance * differance) + sumDifferanceOfValueSquared(index + 1, p);
    }
    
    public String toString() {
        String str = "ID: " + ID + " (";
        
        for (double v : values) {
            str = str + v + ", ";
        }
        
        str = str + ")";
        return str;
    }
    
    public static void main(String[] args) {
        Point a = new Point(new double[]{0,0,0}, 0);
        Point b = new Point(new double[]{1,1,1}, 1);
        
        System.out.println(a.distanceTo(b));
    }
}