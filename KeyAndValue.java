import java.util.*;
public class KeyAndValue {
    
    private ArrayList<Integer> keys = new ArrayList<>();
    private ArrayList<Integer> values = new ArrayList<>();
    
    private int length = 0;
    
    public void add(int key) {
        
        for (int i = 0; i < length; i++) {
            if (key == keys.get(i)) {
                values.set(i, values.get(i) + 1);
                return;
            }
        }
        
        keys.add(key);
        values.add(1);
        length++;
    }
    
    public int getKeyForBiggestValue() {
        int biggestKey = -1;
        int biggestValue = -1;
        
        for (int i = 0; i < length; i++) {
            if (values.get(i) > biggestValue) {
                biggestKey = keys.get(i);
                biggestValue = values.get(i);
            }
        }
        
        return biggestKey;
    }
}