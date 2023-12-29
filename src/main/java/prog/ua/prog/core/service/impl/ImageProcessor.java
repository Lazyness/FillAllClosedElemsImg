package prog.ua.prog.core.service.impl;

import prog.ua.prog.core.model.ImageInPixels;
import prog.ua.prog.core.service.FillObjectsProcessing;
import prog.ua.prog.core.service.ImageProcessing;
import prog.ua.prog.core.service.ImageReadable;
import prog.ua.prog.core.service.ImageWritable;

public class ImageProcessor implements ImageProcessing {
    private final ImageReadable imageReadable = new ImageReader();

    public void createColorObjectsOnImgWithContours(String pathNameToStartImg, String pathNameToGetColorImg) {
        imageReadable.readImage(pathNameToStartImg);

        // we received arr with pixels
        ImageInPixels imageInPixels = imageReadable.createImgInPixels();

        FillObjectsProcessing fillObjectsProcessing = new FillObjectsProcessor(imageInPixels);
        fillObjectsProcessing.fillObjectsOnImage();

        ImageWritable imageWritable = new ImageWriter();
        imageWritable.printImageFromArray(imageInPixels.getPixels(), pathNameToGetColorImg);
    }

}
