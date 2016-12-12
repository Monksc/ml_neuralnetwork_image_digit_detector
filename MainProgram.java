public class MainProgram {
    
    private static String dataFileName = "imageEncoderBigData.txt";
    
    private static String[] imageFileNames = new String[] {
        "0.png", "1.png", "2.png", "3.png", "4.png", 
        "5.png", "6.png", "7.png", "8.png", "9.png"
    };
    private static int[] ids = new int[] {
        0,1,2,3,4,5,6,7,8,9
    };
    
    private static String[] drawingImageFileNames = new String[] {
        "0drawing.png", "1drawing.png", "2drawing.png", "3drawing.png",
        "4drawing.png", "5drawing.png", "6drawing.png", "7drawing.png",
        "8drawing.png", "9drawing.png",
    };
    private static int[] drawingIDS = new int[] {
        0,1,2,3,4,5,6,7,8,9
    };
    
    private static String[] testImageFileNames = new String[] {
        "0test.png", "1test.png", "2test.png", "3test.png",
        "4test.png", "5test.png", "6test.png", "7test.png",
        "8test.png", "9test.png",
    };
    private static int[] testIDS = new int[] {
        0,1,2,3,4,5,6,7,8,9
    };
    
    private static String[] fileNameWithVersion;
    
    public static void main(String[] args) {
        DetectingImage detector = new DetectingImage(dataFileName);
    
        int amountOfDigits = 10;
        int amountOfVersions = 5;
        fileNameWithVersion = new String[amountOfDigits * amountOfVersions];
        int[] fileIdsWithVersion = new int[fileNameWithVersion.length];
        int index = 0;
        for (int v = 1; v <= amountOfVersions; v++) {
            for (int d = 0; d < amountOfDigits; d++) {
                fileIdsWithVersion[index] = d;
                fileNameWithVersion[index++] = d + "v" + v + ".png";
            }
        }
    
        int amountToTrain = 5;
        for (int i = 0; i < amountToTrain; i++) {
            detector.train(fileNameWithVersion, fileIdsWithVersion, 1, 0.5);
            detector.train(imageFileNames, ids, 1, 0.5);
            detector.train(drawingImageFileNames, drawingIDS, 1, 0.5);
        }
        
        System.out.println("FINISHED TRAINING");
        
        System.out.println("Test Text:");
        for (int i = 0; i < 10; i++) {
            System.out.println(i + " == " + detector.getIDOfImage(imageFileNames[i]));
        }
        
        System.out.println("\nTest drawings:");
        for (int i = 0; i < drawingImageFileNames.length; i++) {
            System.out.println(drawingIDS[i] + " == " + detector.getIDOfImage(drawingImageFileNames[i]));
        }
        
        System.out.println("\nTest Test:");
        for (int i = 0; i < testImageFileNames.length; i++) {
            System.out.println(testIDS[i] + " == " + detector.getIDOfImage(testImageFileNames[i]));
        }
        
        for (int i = 0; i < 10; i++) {
            detector.saveResultingImage(i + ".png", i + "saved.png");
        }
    }
}