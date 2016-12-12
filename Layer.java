import java.util.ArrayList;
public class Layer {
    
    private Node[] nodes;
    
    public Layer(int layerSize, int nextLayerSize) {
        nodes = new Node[layerSize + 1];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node(nextLayerSize);   
        }
    }
    
    public ArrayList<Double> getArrayListOfConnections() {
        ArrayList<Double> connections = new ArrayList<Double>();
        for (Node n : nodes) {
            double[] con = n.getConnectionStrengths();
            for (double d : con) {
                connections.add(Math.abs(d));
            }
        }
        return connections;
    }
    
    public void prune(double lowest) {
        for (Node n : nodes) {
            n.prune(lowest);
        }
    }
    
    public void mutate() {
        for (Node n : nodes) {
            n.mutate();
        }
    }
    
    public void save() {
        for (Node n : nodes) {
            n.save();
        }
    }
    
    public void revert() {
        for (Node n : nodes) {
            n.revert();
        }
    }
    
    public double[] getResults(double[] inputs) {
        int nextLayerSize = nodes[0].nextLayerSize();
        
        double[] results = new double[nextLayerSize];
        
        for (int i = 0; i < nextLayerSize; i++) {
            for (int j = 0; j < nodes.length - 1; j++) {
                results[i] += nodes[j].getResult(inputs[j], i);
            }
            results[i] += nodes[nodes.length - 1].getResult(1, i);
        }
        
        return results;
    }
    public double[] getResultsWithDropOut(double[] inputs, double percent) {
        int nextLayerSize = nodes[0].nextLayerSize();
        
        double[] results = new double[nextLayerSize];
        
        for (int i = 0; i < nextLayerSize; i++) {
            for (int j = 0; j < nodes.length - 1; j++) {
                if (Math.random() >= percent) {
                    results[i] += nodes[j].getResult(inputs[j], i);
                }
            }
            results[i] += nodes[nodes.length - 1].getResult(1, i);
        }
        
        return results;
    }
    
    public Node[] getNodes() {
        return nodes;
    }
    
    public void setNode(Node n, int index) {
        nodes[index] = n;
    }
}