package prog.ua.prog.core.service;

import prog.ua.prog.core.model.ImageInPixels;

public interface ImageReadable {
    void readImage(String pathNameToImg);
    ImageInPixels createImgInPixels();
}
