package prog.ua.prog.core.model;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class Point {
    private int x;
    private int y;

    public void copyPoint(Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }
}
