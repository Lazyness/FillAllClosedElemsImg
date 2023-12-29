package prog.ua.prog.core.service.impl;

import prog.ua.prog.core.exception.InvalidPixelValuesException;
import prog.ua.prog.core.exception.ReadImageFileException;
import prog.ua.prog.core.model.ImageInPixels;
import prog.ua.prog.core.service.ImageReadable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

public final class ImageReader implements ImageReadable {
    private BufferedImage bufferedImage;
    public void readImage(String pathNameToImg) {
        try {
            bufferedImage = ImageIO.read(new File(
                    pathNameToImg));
        } catch (IOException e) {
            throw new ReadImageFileException("Read img was not successful!", e);
        }
    }

    private int[][] getModifierToNormalBlackWhitePixels(int[] pixels) {
        int[][] arrayPixel = new int[bufferedImage.getHeight()][bufferedImage.getWidth()];
        for (int x = 0; x < arrayPixel.length; x++) {
            for (int y = 0; y < arrayPixel[x].length; y++) {
                if (pixels[x * bufferedImage.getWidth() + y] == -1) {
                    arrayPixel[x][y] = 0;
                } else if (pixels[x * bufferedImage.getWidth() + y] == -16777216) {
                    arrayPixel[x][y] = 1;
                }
            }
        }
        return arrayPixel;
    }

    public ImageInPixels createImgInPixels() {
        int numPixels = bufferedImage.getWidth() * bufferedImage.getHeight();
        int[] rawPixels = new int[numPixels];

        PixelGrabber grabber = new PixelGrabber(bufferedImage, 0, 0,
                bufferedImage.getWidth(), bufferedImage.getHeight(), rawPixels,
                0, bufferedImage.getWidth());
        try {
            grabber.grabPixels();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InvalidPixelValuesException("Get values pixels was wrong!", e);
        }
        return ImageInPixels.builder()
                .height(bufferedImage.getHeight())
                .width(bufferedImage.getWidth())
                .pixels(getModifierToNormalBlackWhitePixels(rawPixels))
                .build();
    }

}
