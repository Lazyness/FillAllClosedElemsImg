package prog.ua.prog.core.service;

import org.junit.jupiter.api.Test;
import prog.ua.prog.core.service.impl.ImageProcessor;

import static org.junit.jupiter.api.Assertions.*;

class ImageProcessorTest {

    @Test
    void createColorObjectsOnImgWithContours() {
        ImageProcessing imageProcessing = new ImageProcessor();
        assertDoesNotThrow(() -> imageProcessing.createColorObjectsOnImgWithContours(
                "src/main/resources/img_ex/figures_10.bmp",
                "src/main/resources/img_res/result.bmp"));
    }
}