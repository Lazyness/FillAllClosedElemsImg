package prog.ua.prog.core.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prog.ua.prog.core.*;
import prog.ua.prog.core.model.ImageInPixels;
import prog.ua.prog.core.model.Point;
import prog.ua.prog.core.service.impl.ContourSearch;
import prog.ua.prog.core.service.impl.ImageReader;

import java.util.List;
import java.util.Map;

class ContourSearchTest {
    private final ImageReadable imageReadable = new ImageReader();

    @Test
    void findContourObjectShouldFindContour() {
        imageReadable.readImage("src/main/resources/img_ex/figures_5.bmp");

        // we received arr with pixels
        ImageInPixels imageInPixels = imageReadable.createImgInPixels();

        Assertions.assertArrayEquals(Util.getArrayFromString(Config.IMG_IN_PIXELS_NUM_5, imageInPixels), imageInPixels.getPixels());

        ContourSearching contourSearching = new ContourSearch(imageInPixels);

        Map<Integer, List<Point>> listPoints = contourSearching.findContourObject();

        Assertions.assertNotNull(listPoints, "The contour points should not be null");
    }
}