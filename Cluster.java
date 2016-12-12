import java.util.*;
public class Cluster {
    
    private ArrayList<Point> points = new ArrayList<>();
    
    public Cluster(ArrayList<Point> points) {
        this.points = points;
    }
    
    public Cluster() {}
    
    public void add(Point p) {
        points.add(p);
    }
    
    public ArrayList<Circle> getCircles() {
        ArrayList<Circle> circles = new ArrayList<>();
        
        double[] maxValues = getMaxValues();
        double[] minValues = getMinValues();
        
        IndexPoint indexPoint = new IndexPoint(minValues, maxValues, 0.1);
        double radius = indexPoint.getRadius(); //(new Point(minValues, 0)).distanceTo(new Point(maxValues, 0)) / 10;
        
        do {
            
            /* Finding Valid Points and the best ID */
            KeyAndValue keyAndValue = new KeyAndValue();
            ArrayList<Point> pointsForCircle = new ArrayList<>();
            for (Point p : points) {
                if (indexPoint.distanceTo(p) < radius) {
                    pointsForCircle.add(p);
                    keyAndValue.add(p.ID);
                }
            }
            
            if (pointsForCircle.size() > 1) {
                double[] averagePointForCircle = new double[maxValues.length];
                for (int i = 0; i < averagePointForCircle.length; i++) {
                    double total = 0;
                    for (Point p : pointsForCircle) {
                        total += p.getValues()[i];
                    }
                    averagePointForCircle[i] = total / pointsForCircle.size();
                }
            
                Circle circle = new Circle(new Point(averagePointForCircle, keyAndValue.getKeyForBiggestValue()), radius);
                circles.add(circle);
            }
        } while(indexPoint.increment());
        
        return circles;
    }
    
    private double[] getMaxValues() {
        double[] values = copyDoubleArray(points.get(0).getValues());
        
        for (int i = 0; i < values.length; i++) {
            
            for (int j = 1, n = points.size(); j < n; j++) {
                if (points.get(j).getValues()[i] > values[i]) {
                    values[i] = points.get(j).getValues()[i];
                }
            }
        }
        
        return values;
    }
    
    private double[] getMinValues() {
        double[] values = copyDoubleArray(points.get(0).getValues());
        
        for (int i = 0; i < values.length; i++) {
            
            for (int j = 1, n = points.size(); j < n; j++) {
                if (points.get(j).getValues()[i] < values[i]) {
                    values[i] = points.get(j).getValues()[i];
                }
            }
        }
        
        return values;
    }
    
    private double[] copyDoubleArray(double[] d) {
        double[] r = new double[d.length];
        for (int i = 0; i < d.length; i++) {
            r[i] = d[i];
        }
        
        return r;
    }
    
    public int getID(double[] position) {
        /*
        ArrayList<Circle> circles = getCircles();
        
        System.out.println("CLUSTER.GETID: " + circles.size());
        Point p = new Point(position, 0);
        for (Circle c : circles) {
            if (c.contains(p)) {
                return c.point.ID;
            }
        }
        */
        
        Point p = new Point(position, -1);
        
        double[] distanceTo = new double[points.size()];
        for (int i = 0; i < distanceTo.length; i++) {
            distanceTo[i] = p.distanceTo(points.get(i));
        }
        
        double[] smallestValue = new double[]{distanceTo[0], distanceTo[1], distanceTo[2]};
        int[] smallestIndex = new int[] {0,1,2};
        for (int i = smallestValue.length; i < distanceTo.length; i++) {
            double v = distanceTo[i];
            int index = i;
            for (int j = 0; j < smallestValue.length; j++) {
                if (v < smallestValue[j]) {
                    double rep = smallestValue[j];
                    smallestValue[j] = v;
                    v = rep;
                    int repI = index;
                    index = smallestIndex[j];
                    smallestIndex[j] = repI;
                }
            }
        }
        
        int[] ids = new int[smallestValue.length];
        int[] amount = new int[ids.length];
        for (int i = 0; i < smallestValue.length; i++) {
            boolean contains = false;
            for (int j = 0; j < ids.length; j++) {
                if (ids[j] == points.get(smallestIndex[i]).ID) {
                    amount[j]++;
                    contains = true;
                }
            }
            if (!contains) {
                int j = 0;
                while (amount[j] != 0) { j++; }
                ids[j] = points.get(smallestIndex[i]).ID;
                amount[j] = 1;
            }
        }
        
        // finding largest amount
        int largestAmount = amount[0];
        int largestIndex = 0;
        for (int i = 1; i < amount.length; i++) {
            if (amount[i] > largestAmount) {
                largestAmount = amount[i];
                largestIndex = i;
            }
        }
        
        return ids[largestIndex];
        
        //return -1;
    }
}