public class Helper {

    public static double[] copyDoubleArray(double[] arr) {
        double[] r = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            r[i] = arr[i];
        }
        
        return r;
    }
}