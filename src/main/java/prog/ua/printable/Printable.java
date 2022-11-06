package prog.ua.printable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Printable {
    public static void printModifierPixels(int[][] pixels) {
        System.out.println("Start printable...");
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                System.out.print(pixels[i][j]);
            }
            System.out.println();
        }
        System.out.println("End printable...");
    }

    public static void printImageFromArray(int[][] pixels) {

        // Constructs a BufferedImage of one of the predefined image types.
        BufferedImage bufferedImage = new BufferedImage(pixels[0].length, pixels.length, BufferedImage.TYPE_INT_RGB);

        for (int i = pixels.length-1; i >=0; i--) {
            for (int j = pixels[i].length-1; j >=0; j--) {
                if (pixels[i][j] == 1) {
                    bufferedImage.setRGB(j, i, new Color(255, 80, 100).getRGB());
                } else if (pixels[i][j] == 2) {
                    bufferedImage.setRGB(j, i, new Color(100, 200, 240).getRGB());
                } else if (pixels[i][j] == 3) {
                    bufferedImage.setRGB(j, i, new Color(255, 150, 0).getRGB());
                } else {
                    bufferedImage.setRGB(j, i, new Color(255, 255, 255).getRGB());
                }
            }
        }

        // Save as PNG
        File file = new File("src\\main\\resources\\img_res\\result.png");
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
