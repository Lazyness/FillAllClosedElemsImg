package prog.ua.prog.core.service.impl;

import prog.ua.prog.core.exception.WriteImageFileException;
import prog.ua.prog.core.service.ImageWritable;
import prog.ua.prog.core.util.RandomNumber;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageWriter implements ImageWritable {
    /**
     * @param pixels   its matrix pixel that will be drawing.
     * @param fileName its name file with created color image.
     */
    public void printImageFromArray(int[][] pixels, String fileName) {
        // Save as PNG
        File file = new File(fileName);
        try {
            ImageIO.write(giveObjectsOnImgColor(pixels), "bmp", file);
        } catch (IOException e) {
            throw new WriteImageFileException("Problem with write image file!", e);
        }
    }

    private BufferedImage giveObjectsOnImgColor(int[][] pixels) {
        // Constructs a BufferedImage of one of the predefined image types.
        BufferedImage bufferedImage = new BufferedImage(pixels[0].length, pixels.length, BufferedImage.TYPE_INT_RGB);
        RandomNumber randomNumber = new RandomNumber();

        int red = randomNumber.randomValue(50, 255);
        int green = randomNumber.randomValue(70, 150);
        int blue = randomNumber.randomValue(40, 120);

        for (int i = pixels.length - 1; i >= 0; i--) {
            for (int j = pixels[i].length - 1; j >= 0; j--) {
                if (pixels[i][j] != 0) {

                    bufferedImage.setRGB(j, i, new Color(red, green, blue).getRGB());
                } else {
                    bufferedImage.setRGB(j, i, new Color(255, 255, 255).getRGB());
                }
            }
        }
        return bufferedImage;
    }
}
