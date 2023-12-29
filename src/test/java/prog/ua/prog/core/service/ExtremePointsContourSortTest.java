package prog.ua.prog.core.service;

import java.util.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import prog.ua.prog.core.Config;
import prog.ua.prog.core.Util;
import prog.ua.prog.core.model.ImageInPixels;
import prog.ua.prog.core.model.Point;
import prog.ua.prog.core.service.impl.ExtremePointsContourSorter;
import prog.ua.prog.core.service.impl.ImageReader;

class ExtremePointsContourSortTest {
    private final ImageReadable imageReadable = new ImageReader();

    @Test
    void testGetMapListStartAndEndPointInContourObjects() {
        imageReadable.readImage("src/main/resources/img_ex/figures_5.bmp");

        // we received arr with pixels
        ImageInPixels imageInPixels = imageReadable.createImgInPixels();

        Assertions.assertArrayEquals(Util.getArrayFromString(Config.IMG_IN_PIXELS_NUM_5, imageInPixels), imageInPixels.getPixels());

        // Initialize the object to be tested
        ExtremePointsContourSorting extremePointsContourSorting = new ExtremePointsContourSorter(imageInPixels);
        Map<Integer, List<List<Point>>> invokeResult = extremePointsContourSorting.getMapListStartAndEndPointInContourObjects();

        // Write the Assert code
        Assertions.assertEquals(Config.mapListStartAndEndPointInContourObjects, invokeResult);
    }
}