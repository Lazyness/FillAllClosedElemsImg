package prog.ua.prog.core;

import prog.ua.prog.core.model.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {
    private Config() {
    }

    public final static String IMG_IN_PIXELS_NUM_5 = """
                    000000000000000000000000000000000000
                    000000000000000000000000000000000000
                    000000000000000000111100000000000000
                    000000000000000000100100000000000000
                    000000000000000000100111000000000000
                    000111111111000000100001000000000000
                    000100000001100000100001110000000000
                    000100000000100000100000010000000000
                    000100000000100000100000011100000000
                    000100000000100000100000000100000000
                    000100000000100000100000000111000000
                    000100000000100000100000000001000000
                    000100000000100000100000000001000000
                    000100000000100000111111111111000000
                    000100000000100000000000000000000000
                    000111111111100000000000000000000000
                    000000000000000000000000000000000000
                    000000000000000000000000000000000000
                    000000000000000000000000000000000000
                    000000000000000000000000000000000000
                    000000000000000000000000000000000000
                    000000000000000000000000000000000000
                    000000000000000000000000000000000000
                    000000000000000000000000000000000000
                    000000000000000000000000000000000000
                    000000000000000000000000000000000000
                    000000000000000000000000000000000000
            """;

    public static Map<Integer, List<List<Point>>> mapListStartAndEndPointInContourObjects = new HashMap<>() {{
        put(0, List.of(
                List.of(Point.builder().x(18).y(2).build(), Point.builder().x(19).y(2).build(), Point.builder().x(20).y(2).build(), Point.builder().x(21).y(2).build()),
                List.of(Point.builder().x(18).y(3).build(), Point.builder().x(21).y(3).build()),
                List.of(Point.builder().x(18).y(4).build(), Point.builder().x(21).y(4).build(), Point.builder().x(22).y(4).build(), Point.builder().x(23).y(4).build()),
                List.of(Point.builder().x(18).y(5).build(), Point.builder().x(23).y(5).build()),
                List.of(Point.builder().x(18).y(6).build(), Point.builder().x(23).y(6).build(), Point.builder().x(24).y(6).build(), Point.builder().x(25).y(6).build()),
                List.of(Point.builder().x(18).y(7).build(), Point.builder().x(25).y(7).build()),
                List.of(Point.builder().x(18).y(8).build(), Point.builder().x(25).y(8).build(), Point.builder().x(26).y(8).build(), Point.builder().x(27).y(8).build()),
                List.of(Point.builder().x(18).y(9).build(), Point.builder().x(27).y(9).build()),
                List.of(Point.builder().x(18).y(10).build(), Point.builder().x(27).y(10).build(), Point.builder().x(28).y(10).build(), Point.builder().x(29).y(10).build()),
                List.of(Point.builder().x(18).y(11).build(), Point.builder().x(29).y(11).build()),
                List.of(Point.builder().x(18).y(12).build(), Point.builder().x(29).y(12).build()),
                List.of(Point.builder().x(18).y(13).build(), Point.builder().x(19).y(13).build(), Point.builder().x(20).y(13).build(), Point.builder().x(21).y(13).build(),
                        Point.builder().x(22).y(13).build(), Point.builder().x(23).y(13).build(), Point.builder().x(24).y(13).build(), Point.builder().x(25).y(13).build(),
                        Point.builder().x(26).y(13).build(), Point.builder().x(27).y(13).build(), Point.builder().x(28).y(13).build(), Point.builder().x(29).y(13).build())
        ));
        put(1, List.of(
                List.of(Point.builder().x(3).y(5).build(), Point.builder().x(4).y(5).build(), Point.builder().x(5).y(5).build(), Point.builder().x(6).y(5).build(),
                        Point.builder().x(7).y(5).build(), Point.builder().x(8).y(5).build(), Point.builder().x(9).y(5).build(), Point.builder().x(10).y(5).build(),
                        Point.builder().x(11).y(5).build()),
                List.of(Point.builder().x(3).y(6).build(), Point.builder().x(11).y(6).build(), Point.builder().x(12).y(6).build()),
                List.of(Point.builder().x(3).y(7).build(), Point.builder().x(12).y(7).build()),
                List.of(Point.builder().x(3).y(8).build(), Point.builder().x(12).y(8).build()),
                List.of(Point.builder().x(3).y(9).build(), Point.builder().x(12).y(9).build()),
                List.of(Point.builder().x(3).y(10).build(), Point.builder().x(12).y(10).build()),
                List.of(Point.builder().x(3).y(11).build(), Point.builder().x(12).y(11).build()),
                List.of(Point.builder().x(3).y(12).build(), Point.builder().x(12).y(12).build()),
                List.of(Point.builder().x(3).y(13).build(), Point.builder().x(12).y(13).build()),
                List.of(Point.builder().x(3).y(14).build(), Point.builder().x(12).y(14).build()),
                List.of(Point.builder().x(3).y(15).build(), Point.builder().x(4).y(15).build(), Point.builder().x(5).y(15).build(), Point.builder().x(6).y(15).build(),
                        Point.builder().x(7).y(15).build(), Point.builder().x(8).y(15).build(), Point.builder().x(9).y(15).build(), Point.builder().x(10).y(15).build(),
                        Point.builder().x(11).y(15).build(), Point.builder().x(12).y(15).build())
        ));
        // Додайте інші значення за необхідності
    }};
}
