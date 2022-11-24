package prog.ua;

import prog.ua.exceptions.ExceptionNotFoundIndex;
import prog.ua.logic.Logic;
import prog.ua.printable.Printable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File(
                    "src\\main\\resources\\img_ex\\figures_4.bmp"));
            Printable.printModifierPixels(Logic.getModifierBlackWhitePixels(Logic.getPixels(image)));
            Logic.searchObjectsOnImage();
            int[][] resultArrayPixels = Logic.fillImageColor();
            Printable.printModifierPixels(resultArrayPixels);
            Printable.printImageFromArray(resultArrayPixels);
        } catch (IOException | ExceptionNotFoundIndex e) {
            e.printStackTrace();
        }
    }

}
