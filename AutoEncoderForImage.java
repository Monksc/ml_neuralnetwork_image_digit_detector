import java.io.*; // Exception
public class AutoEncoderForImage {
    
    private Network network;
    private String fileName;
    private int[] formation;
    private Cluster cluster;
    
    public AutoEncoderForImage(String fileName, int[] formation) {
        network = new Network(formation);
        this.fileName = fileName;
        try { network.loadData(SaveData.getData(fileName, formation)); } catch(Exception e) { }
        this.formation = formation;
        cluster = new Cluster();
    }
    
    public int getDigit(double[] digitInformation) {
        double[] results = network.getResults(digitInformation, formation.length/2);
        
        return cluster.getID(results);
    }
    
    public void trainCircle(String fileName, int id, int amount) {
        try {
            double[] inputs = GettingImage.convertImageToDoubles(fileName);
            double[] results = network.getResults(inputs, formation.length/2);
            
            for (int i = 0; i < amount; i++) {
                cluster.add(new Point(results, id));
            }
        } catch(Exception e) {
            System.out.println("ERROR: AutoEncoderForImage.trainCircle: " + fileName + " " + e);
        }
    }
    public void train(String fileName) {
        try {
            double[] digit = GettingImage.convertImageToDoubles(fileName);
            network.train(digit, digit);
        } catch (IOException e) {
            System.out.println("ERROR: AutoEncoderForImage.train(String): " + e);
        }
    }
    public void train(String fileName, double dropOutPercentage) {
        try {
            double[] digit = GettingImage.convertImageToDoubles(fileName);
            network.trainWithDropOut(digit, digit, dropOutPercentage);
        } catch (IOException e) {
            System.out.println("ERROR: AutoEncoderForImage.train(String): " + e);
        }
    }
    
    public void saveData() {
        SaveData.saveData(fileName, network.getLayers());
    }
    
    public double[] getResult(double[] input) {
        return network.getResults(input);
    }
}