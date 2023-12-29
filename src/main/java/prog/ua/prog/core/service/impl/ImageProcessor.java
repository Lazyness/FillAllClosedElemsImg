package prog.ua.prog.core.service.impl;

import prog.ua.prog.core.model.ImageInPixels;
import prog.ua.prog.core.service.FillObjectsProcessingService;
import prog.ua.prog.core.service.ImageProcessingService;
import prog.ua.prog.core.service.ImageReadable;
import prog.ua.prog.core.service.ImageWritable;

public class ImageProcessor implements ImageProcessingService {
    private final ImageReadable imageReadable = new ImageReader();

    public void createColorObjectsOnImgWithContours(String pathNameToStartImg, String pathNameToGetColorImg) {
        imageReadable.readImage(pathNameToStartImg);

        // we received arr with pixels
        ImageInPixels imageInPixels = imageReadable.createImgInPixels();

        FillObjectsProcessingService fillObjectsProcessingService = new FillObjectsProcessor(imageInPixels);
        fillObjectsProcessingService.fillObjectsOnImage();

        ImageWritable imageWritable = new ImageWriter();
        imageWritable.printImageFromArray(imageInPixels.getPixels(), pathNameToGetColorImg);
    }

}
