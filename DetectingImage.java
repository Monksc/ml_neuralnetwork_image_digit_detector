import java.io.*; // Excepiton
import java.awt.Image;
public class DetectingImage {

    private AutoEncoderForImage encoder;

    public DetectingImage(String fileName) {
        int [] formation = new int[] {625,100,10,100,625};
        encoder = new AutoEncoderForImage(fileName, formation);
    }
    
    public void train(String[] fileNames, int[] ids, int amountOfTimesTrained) {
        for (int i = 0; i < amountOfTimesTrained; i++) {
            for (int j = 0; j < fileNames.length; j++) {
                encoder.train(fileNames[j]);
            }
        }
        
        for (int i = 0; i < fileNames.length; i++) {
            encoder.trainCircle(fileNames[i], ids[i], 3);
        }
        
        encoder.saveData();
    }
    
    /**
     * Train with dropout
     */
    public void train(String[] fileNames, int[] ids, int amountOfTimesTrained, double dropOutPercent) {
        for (int i = 0; i < amountOfTimesTrained; i++) {
            for (int j = 0; j < fileNames.length; j++) {
                encoder.train(fileNames[j], dropOutPercent);
            }
        }
        
        for (int i = 0; i < fileNames.length; i++) {
            encoder.trainCircle(fileNames[i], ids[i], 1);
        }
        
        encoder.saveData();
    }

    
    public int getIDOfImage(String fileName) {
        try {
            double[] input = GettingImage.convertImageToDoubles(fileName);
            return encoder.getDigit(input);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        
        return -1;
    }
    
    public void saveResultingImage(String imageName, String savedImageName) {
        try {
            double[] result = encoder.getResult(GettingImage.convertImageToDoubles(imageName));
            int [] arr = GettingImage.convertDoubleArrayToInt(result);
            Image i = GettingImage.getImageFromArray(arr, 25,25);
            GettingImage.saveImage(i, savedImageName);
        } catch(Exception e) {
            System.out.println("DetectingImage.saveResultingImage(): " + e);
        }
    }
}