package prog.ua.prog.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ImageInPixels {
    private int width;
    private int height;
    private int[][] pixels;
}
