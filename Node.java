public class Node {
    
    public static double percentChange = 0.001;
    public static double percentChangeBy = 0.1;
    public static final int TOTAL_TIMES_CHANGED_NEEDED = 10;
    public static int timesPercentChangeByChangedNeeded = 10; // when it gets to 0 percentChangeBy it *= 0.9
    
    private double[] connectionStrength;
    private double[] backupConnectionStrength;
    
    public Node(int nextLayerSize) {
        connectionStrength = new double[nextLayerSize];
        backupConnectionStrength = new double[nextLayerSize];
        
        for (int i = 0; i < nextLayerSize; i++) {
            connectionStrength[i] = Math.random(); 
            backupConnectionStrength[i] = connectionStrength[i];
        }
    }
    
    public int nextLayerSize() {
        return connectionStrength.length;
    }
    
    public double getResult(double input, int index) {
        return input * connectionStrength[index];
    }
    
    public void setConnection(double conn, int index) {
        connectionStrength[index] = conn;
    }
    
    public double[] getConnectionStrengths() {
        return connectionStrength;
    }
    
    public void prune(double lowest) {
        for (int i = 0; i < connectionStrength.length; i++) {
            if (connectionStrength[i] < lowest) {
                connectionStrength[i] = 0;
            }
        }
    }
    
    public void mutate() {
        for (int i = 0; i < connectionStrength.length; i++) {
            if (Math.random() < percentChange) {
                // might want to take out d *
                connectionStrength[i] += (connectionStrength[i] * percentChangeBy) * (Math.random() - 0.5);
            }
        }
    }
    
    public void save() {
        backupConnectionStrength = connectionStrength.clone();
    }
    
    public void revert() {
        connectionStrength = backupConnectionStrength.clone();
    }
    
}