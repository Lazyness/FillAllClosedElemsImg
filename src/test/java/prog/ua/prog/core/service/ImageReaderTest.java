package prog.ua.prog.core.service;

import org.junit.jupiter.api.*;
import prog.ua.prog.core.Util;
import prog.ua.prog.core.Config;
import prog.ua.prog.core.model.ImageInPixels;
import prog.ua.prog.core.service.impl.ImageReader;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ImageReaderTest {
    private final ImageReadable imageReadable = new ImageReader();

    @Test
    @Order(1)
    void readImageShouldBeSuccessful() {
        String validImagePath = "src/main/resources/img_ex/figures_5.bmp";

        Assertions.assertDoesNotThrow(() -> imageReadable.readImage(validImagePath), "Reading image should not throw an exception");
    }

    @Test
    @Order(2)
    void readImageShouldBeFailure() {
        String invalidImagePath = "src/main/resources/img_ex/invalid.bmp";

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> imageReadable.readImage(invalidImagePath),
                "Reading image with invalid path should throw a RuntimeException");

        Assertions.assertEquals("Read img was not successful!", exception.getMessage(), "Exception message should match");
    }

    @Test
    @Order(3)
    void shouldReadImgAndReturnImgInPixels(){
        imageReadable.readImage("src/main/resources/img_ex/figures_5.bmp");

        // we received arr with pixels
        ImageInPixels imageInPixels = imageReadable.createImgInPixels();

        Assertions.assertArrayEquals(Util.getArrayFromString(Config.IMG_IN_PIXELS_NUM_5, imageInPixels), imageInPixels.getPixels());
    }

}