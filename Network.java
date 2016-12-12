import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
public class Network {
    
    public static Scanner scanner = new Scanner(System.in);
    
    private Layer[] layers;
    
    public Network(int[] formation) {
        layers = new Layer[formation.length];
        
        for (int i = 0; i < layers.length - 1; i++) {
            layers[i] = new Layer(formation[i], formation[i+1]);
        }
        layers[layers.length - 1] = new Layer(formation[formation.length - 1], 0);
    }
    
    private double differanceSquared(double a, double b) {
        double differance = a - b;
        return differance * differance;
    }
    private double getDistance(double[] arr1, double[] arr2) {
        double total = 0;
        for (int i = 0; i < arr1.length; i++) {
            total += differanceSquared(arr1[i], arr2[i]);
        }
    
        return Math.sqrt(total);
    }
    public void train(double[] input, double[] results) {
        
        double lastDistance = getDistance(results, getResults(input));
        for (Layer l : layers) { l.save(); }
        boolean gotBetter = false;
        
        double percentChangedBy = Node.percentChangeBy;
        
        int count = 0;
        while (!gotBetter && lastDistance != 0) {
            
            for (Layer l : layers) { l.mutate(); }    
            
            double distance = getDistance(results, getResults(input));
        
            if (distance < lastDistance) {
                //System.out.println(distance + " < " + lastDistance);
                gotBetter = true;
            } else {
                for (Layer l : layers) { l.revert(); } 
                
                if (count > 10 && count % 10 == 0) {
                    Node.percentChangeBy *= 0.9;
                    if (count % 1000 == 0) {
                        for (Layer l : layers) { l.revert(); }
                        gotBetter = true; // even though it didnt
                    }
                }
            }
            
            count++;
        }
        
        if (Node.percentChangeBy == percentChangedBy) {
            Node.timesPercentChangeByChangedNeeded = Node.TOTAL_TIMES_CHANGED_NEEDED;
        } else {
            Node.timesPercentChangeByChangedNeeded--;
            if (Node.timesPercentChangeByChangedNeeded <= 0) {
                percentChangedBy *= 0.9;
                Node.timesPercentChangeByChangedNeeded = Node.TOTAL_TIMES_CHANGED_NEEDED;
            }
        }
        
        Node.percentChangeBy = percentChangedBy;
    }
    public void trainWithDropOut(double[] input, double[] results, double dropOutPercent) {
        
        double lastDistance = getDistance(results, getResultsWithDropOut(input, dropOutPercent));
        for (Layer l : layers) { l.save(); }
        boolean gotBetter = false;
        
        double percentChangedBy = Node.percentChangeBy;
        
        int count = 0;
        while (!gotBetter && lastDistance != 0) {
            
            for (Layer l : layers) { l.mutate(); }    
            
            double distance = getDistance(results, getResultsWithDropOut(input, dropOutPercent));
        
            if (distance < lastDistance) {
                //System.out.println(distance + " < " + lastDistance);
                gotBetter = true;
            } else {
                for (Layer l : layers) { l.revert(); } 
                
                if (count > 10 && count % 10 == 0) {
                    Node.percentChangeBy *= 0.9;
                    if (count % 1000 == 0) {
                        for (Layer l : layers) { l.revert(); }
                        gotBetter = true; // even though it didnt
                    }
                }
            }
            
            count++;
        }
        
        if (Node.percentChangeBy == percentChangedBy) {
            Node.timesPercentChangeByChangedNeeded = Node.TOTAL_TIMES_CHANGED_NEEDED;
        } else {
            Node.timesPercentChangeByChangedNeeded--;
            if (Node.timesPercentChangeByChangedNeeded <= 0) {
                percentChangedBy *= 0.9;
                Node.timesPercentChangeByChangedNeeded = Node.TOTAL_TIMES_CHANGED_NEEDED;
            }
        }
        
        Node.percentChangeBy = percentChangedBy;
    }
    
    private double[] getResultsWithDropOut(double[] input, double percent) {
        double[] results = input;
        
        for (int i = 0; i < layers.length - 1; i++) {
            results = layers[i].getResultsWithDropOut(results, percent);
        }
        
        return results;
    }
    public double[] getResults(double[] input) {
        double[] results = input;
        
        for (int i = 0; i < layers.length - 1; i++) {
            results = layers[i].getResults(results);
        }
        
        return results;
    }
    public double[] getResults(double[] input, int index) {
        double[] results = input;
        
        for (int i = 0; i < index; i++) {
            results = layers[i].getResults(results);
        }
        
        return results;
    }
    
    private void prune() {
        ArrayList<Double> connections = new ArrayList<Double>();
        for (Layer l : layers) {
            connections.addAll(l.getArrayListOfConnections());
        }
        
        Collections.sort(connections);
        
        double firstQuartile = connections.get(connections.size() / 4);
        double thirdQuartile = connections.get(3 * connections.size() / 4);
        double IQR = (thirdQuartile - firstQuartile); // should have 1.5 * 
        
        double lowest = firstQuartile - IQR;
        
        if (lowest > connections.get(0)) {
            int i = 1;
            while (i < connections.size() && connections.get(i) < lowest) {
                i++;
            }
            if (connections.get(i - 1) > 0) {
                System.out.println("\t\t\t\t\tDID PRUNE");
                for (Layer l : layers) {
                    l.prune(lowest);   
                }
            }
        }
    }
    public void sleep() {
        prune();
    }
    
    public void doRandomMutation() {
        for (Layer l : layers) {
            l.mutate();
        }
    }
    
    public void loadData(Layer[] newLayers) {
        for (int i = 0; i < layers.length; i++) {
            layers[i] = newLayers[i];
        }
    }
    
    public Layer[] getLayers() {
        return layers;
    }
}