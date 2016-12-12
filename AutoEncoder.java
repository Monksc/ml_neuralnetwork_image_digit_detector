public class AutoEncoder {
    
    private Network network;
    private String fileName;
    private int[] formation;
    private Cluster cluster;
    
    public AutoEncoder(String fileName, int[] formation) {
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
    
    public int getAmountCorrect(int questions) {
        int correct = 0;
        for (int i = 0; i < questions; i++) {
            int randomIndex = (int)(Math.random() * 10);
            if (randomIndex == getDigit(getIndexOfDigit(randomIndex))) {
                correct++;
            }
        }
        return correct;
    }
    
    public void trainCircle() {
        int index = (int)(Math.random() * 10);
        double[] number = getIndexOfDigit(index);
        double[] results = network.getResults(number, formation.length/2);
        
        cluster.add(new Point(results, index));
    }
    public void trainCircle(int amountOfTimes) {
        for (int i = 0; i < amountOfTimes; i++) {
            trainCircle();
        }
    }
    public void train() {
        int index = (int)(Math.random() * 10);
        double[] digit = getIndexOfDigit(index);
        network.train(digit, digit);
    }
    public void train(int amountOfTimes) {
        for (int i = 0; i < amountOfTimes; i++) {
            train();
        }
        saveData();
    } 
    
    public double[] getIndexOfDigit(int index) {
        switch (index) {
        case 0: return get0();
        case 1: return get1();
        case 2: return get2();
        case 3: return get3();
        case 4: return get4();
        case 5: return get5();
        case 6: return get6();
        case 7: return get7();
        case 8: return get8();
        case 9: return get9();
        }
        return null;
    }
    private double[] get1() {
        double[] one = new double[] {10,5,1,1,1,1,1,1,5,10};
        for (int i = 0; i < one.length; i++) {
            one[i] += Math.random() * 3;
        }
        return one;
    }
    private double[] get2() {
        double[] value = new double[] {1,10,1,1,1,1,1,1,10,1};
        for (int i = 0; i < value.length; i++) {
            value[i] += Math.random() * 3;
        }
        return value;
    }
    private double[] get3() {
        double[] value = new double[] {5,5,5,5,10,10,10,5,5,5};
        for (int i = 0; i < value.length; i++) {
            value[i] += Math.random() * 3;
        }
        return value;
    }
    private double[] get4() {
        double[] value = new double[] {1,1,1,1,1,1,1,1,1,1};
        for (int i = 0; i < value.length; i++) {
            value[i] += Math.random() * 3;
        }
        return value;
    }
    private double[] get5() {
        double[] value = new double[] {1,1,1,1,10,1,1,1,1,1};
        for (int i = 0; i < value.length; i++) {
            value[i] += Math.random() * 3;
        }
        return value;
    }
    private double[] get6() {
        double[] value = new double[] {1,1,1,1,1,1,1,1,10,10};
        for (int i = 0; i < value.length; i++) {
            value[i] += Math.random() * 3;
        }
        return value;
    }
    private double[] get7() {
        double[] value = new double[] {10,10,1,1,1,1,1,1,1,1};
        for (int i = 0; i < value.length; i++) {
            value[i] += Math.random() * 3;
        }
        return value;
    }
    private double[] get8() {
        double[] value = new double[] {1,2,3,4,5,6,7,8,9,10};
        for (int i = 0; i < value.length; i++) {
            value[i] += Math.random() * 3;
        }
        return value;
    }
    private double[] get9() {
        double[] value = new double[] {5,5,5,5,5,7,7,7,7,7};
        for (int i = 0; i < value.length; i++) {
            value[i] += Math.random() * 3;
        }
        return value;
    }
    private double[] get0() {
        double[] value = new double[] {0,0,0,0,0,0,0,0,0,0};
        for (int i = 0; i < value.length; i++) {
            value[i] += Math.random() * 3;
        }
        return value;
    }
    
    public void saveData() {
        SaveData.saveData(fileName, network.getLayers());
    }
    
    public static void main(String[] args) {
        int [] formation = new int[] {10,5,2,5,10};
        AutoEncoder encoder = new AutoEncoder("encoder1.txt", formation);
        
        encoder.train(1000);
        encoder.trainCircle(100);
        
        for (int i = 0; i< 10; i++) {
            System.out.println(i + " == " + encoder.getDigit(encoder.getIndexOfDigit(i)));;
        }
        
        int amountOfQuestion = 100;
        int amountCorrect = encoder.getAmountCorrect(amountOfQuestion);
        System.out.println(100 * ((double)amountCorrect / (double)amountOfQuestion ) + "% CORRECT");
    
        for (int i = 0; i < 10; i++) {
            System.out.println("\n" + i);
            double[] shortenedNumb = encoder.network.getResults(encoder.getIndexOfDigit(i), formation.length / 2);
            for (double d : shortenedNumb) {
                System.out.print(d + " ");
            }
            System.out.println();
        }
    }
}