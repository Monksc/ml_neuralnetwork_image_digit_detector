import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
public class SaveData {

    public SaveData() {
        
    }
    
    public static void saveData(String fileName, Layer[] layers) {
        
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "utf-8"))) {
            
            for (Layer l : layers) {
                for (Node n : l.getNodes()) {
                    for (double d : n.getConnectionStrengths()) {
                        writer.write(d + "\n");
                    }
                }
            }
        }
        catch(Exception e) {
            
        }
    }
    
    public static Layer[] getData(String fileName, int[] formation) {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            
            ArrayList<String> lines = new ArrayList<String>();
            
            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
            
            int linesIndex = 0;
            Layer[] layers = new Layer[formation.length];
            for (int i = 0; i < layers.length; i++) {
                
                int nextFormationSize = 0;
                if (i < formation.length - 1) { nextFormationSize = formation[i+1]; }
                Layer layer = new Layer(formation[i], nextFormationSize);
                for (int l = 0; l < formation[i] + 1; l++) {
            
                    Node node = new Node(nextFormationSize);//formation[l+1]);
                    for (int n = 0; n < nextFormationSize; n++) {//formation[l+1]; n++) {
                        //Connection conn = new Connection(Double.parseDouble(lines.get(linesIndex++)), Math.random());
                        //neuron.setConnection(conn, n);
                        
                        double conn = Double.parseDouble(lines.get(linesIndex++));
                        node.setConnection(conn, n);
                    }
                    layer.setNode(node, l);
                }
                
                layers[i] = layer;
            }
            
            return layers;
        } catch(Exception e) {
            System.out.println(e.toString());
        }
        
        return null;
    }

}