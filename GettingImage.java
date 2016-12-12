import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.Image;
import java.awt.image.ColorModel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class GettingImage {
    
   public GettingImage() {
        
   }
    
   private static int[][] convertTo2D(BufferedImage image) {

      final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
      final int width = image.getWidth();
      final int height = image.getHeight();
      final boolean hasAlphaChannel = image.getAlphaRaster() != null;

      int[][] result = new int[height][width];
      if (hasAlphaChannel) {
         final int pixelLength = 4;
         for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
            int argb = 0;
            argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
            argb += ((int) pixels[pixel + 1] & 0xff); // blue
            argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
            argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
            result[row][col] = argb;
            col++;
            if (col == width) {
               col = 0;
               row++;
            }
         }
      } else {
         final int pixelLength = 3;
         for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
            int argb = 0;
            argb += -16777216; // 255 alpha
            argb += ((int) pixels[pixel] & 0xff); // blue
            argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
            argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
            result[row][col] = argb;
            col++;
            if (col == width) {
               col = 0;
               row++;
            }
         }
      }

      return result;
   }

   public static int[][] convertImageToInts(String fileName) throws IOException {
      String IMG = fileName;
      BufferedImage image = ImageIO.read(new File(IMG)); //ImageIO.read(PerformanceTest.class.getResource("12000X12000.jpg"));
    
      int[][] result = convertTo2D(image);
      return result;
   }

   public static double[] convertImageToDoubles(String fileName) throws IOException {
      int[][] pixels = convertImageToInts(fileName);
      double[] newPixels = new double[pixels.length * pixels[0].length];
      int newPixelIndex = 0;
      
      for (int x = 0; x < pixels.length; x++) {
         for (int y = 0; y < pixels[x].length; y++) {
            newPixels[newPixelIndex++] = (double)pixels[x][y];
         }
      }
      
      return newPixels;
   }

   public static Image getImageFromArray(int[] matrix, int width, int height) {
      DataBufferInt buffer = new DataBufferInt(matrix, matrix.length);

      int[] bandMasks = {0xFF0000, 0xFF00, 0xFF, 0xFF000000}; // ARGB (yes, ARGB, as the masks are R, G, B, A always) order
      WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, bandMasks, null);

      ColorModel cm = ColorModel.getRGBdefault();
      BufferedImage image = new BufferedImage(cm, raster, cm.isAlphaPremultiplied(), null);
      
      return image;
        /*
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        WritableRaster raster = (WritableRaster) image.getData();
        System.out.println("HEREED1:  " + width + " " + height + " " + pixels.length);
        width = 25;
        height = 25;
        for (int i = 0; i < pixels.length; i++) {
           if (pixels[i] > 255 || pixels[i] < 0) {
              pixels[i] = 255;
              System.out.print(pixels[i] + " ");
           }
        }
        raster.setPixels(0,0,width,height,pixels);
        System.out.println("HEREED2");
        image.setData(raster); 
        System.out.println("HEREED3");
        return image;
        */
    }

   public static int[] convertDoubleArrayToInt(double[] arr) {
      int[] iarr = new int[arr.length];
      for (int i = 0; i < iarr.length; i++) {
         iarr[i] = (int) Math.round(arr[i]);
      }
      
      return iarr;
   }

   public static void saveImage(Image image, String imageName) {
      try {
         // retrieve image
         BufferedImage bi = toBufferedImage(image);//getMyImage();
         File outputfile = new File(imageName);
         ImageIO.write(bi, "png", outputfile);
      } catch (IOException e) {
         System.out.println(e.toString());
      }
   }

   public static BufferedImage toBufferedImage(Image img) {
      if (img instanceof BufferedImage) {
         return (BufferedImage) img;
      }

      // Create a buffered image with transparency
      BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

      // Draw the image on to the buffered image
      Graphics2D bGr = bimage.createGraphics();
      bGr.drawImage(img, 0, 0, null);
      bGr.dispose();

      // Return the buffered image
      return bimage;
   }

   public static void main(String[] args) {
   
      try {
         for (int[] rows : convertImageToInts("0.png")) {
         for (int v : rows) {
            System.out.print(v + " ");
         }
         System.out.println();
      }
      } catch(Exception e) {
         System.out.println(e.toString());
      }
   }
}