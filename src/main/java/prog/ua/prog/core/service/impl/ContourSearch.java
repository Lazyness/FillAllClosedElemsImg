package prog.ua.prog.core.service.impl;

import lombok.RequiredArgsConstructor;
import prog.ua.prog.core.exception.DanglingOutlineException;
import prog.ua.prog.core.model.ImageInPixels;
import prog.ua.prog.core.model.NavigateFlags;
import prog.ua.prog.core.model.Point;
import prog.ua.prog.core.service.ContourSearchService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * First step: start searching object that include number "1".
 * If u find num "1" u memorize coordinates and change num on +1 -> "2"
 * If u end on the start object coordinates, search starts from the beginning.
 * Increase the counter to count an object on image.
 * <p>
 * Step two: if is it next start, we can go from the first start and skip not only "0" but also "2" etc.
 * We do it because we do not want to skip the necessary objects.
 * And do it until that time when u cannot find the new objects (matrix went to the end)
 */
@RequiredArgsConstructor
public final class ContourSearch implements ContourSearchService {
    private final ImageInPixels imageInPixels;
    private final NavigateFlags navigateFlags = new NavigateFlags();
    private final Map<Integer, List<Point>> listSetPoints = new HashMap<>();
    private List<Point> tempListPoints = new ArrayList<>();
    private int counterObjects;
    private boolean isProcessHeadObject(Point originalLastPoint, Point tempPoint, boolean firstCatchPoint, int x, int y) {
        return originalLastPoint != null
                && tempPoint.getX() == x
                && tempPoint.getY() == y
                && firstCatchPoint;
    }
    private boolean isHotPixelOnContourObject(Point originalLastPoint, Point tempPoint, int[][] imagePixels, int x, int y) {
        return originalLastPoint != null
                && imagePixels[tempPoint.getY()][tempPoint.getX()] != 1
                && (tempPoint.getX() != x || tempPoint.getY() != y);
    }
    public Map<Integer, List<Point>> findContourObject() {
        Point tempPoint = Point.builder().build();
        int[][] imagePixels = imageInPixels.getPixels();
        boolean firstCatchPoint = false;
        int y = 0;

        while (y < imageInPixels.getHeight()) {
            int x = 0;
            tempPoint.setX(x);
            Point originalLastPoint = null;

            while (tempPoint.getX() < imageInPixels.getWidth()) {

                // when object find himself head
                if (isProcessHeadObject(originalLastPoint, tempPoint, firstCatchPoint, x, y)) {
                    processHeadObject(tempPoint);
                    firstCatchPoint = false;
                }

                if (imagePixels[tempPoint.getY()][tempPoint.getX()] == 1) {
                    originalLastPoint = Point.builder().y(tempPoint.getY()).x(tempPoint.getX()).build();
                    processHotPoint(originalLastPoint, tempPoint, imagePixels);
                    firstCatchPoint = true;
                }

                if (isHotPixelOnContourObject(originalLastPoint, tempPoint, imagePixels, x, y)) {
                    pixelPointController(tempPoint);
                }

                if (!firstCatchPoint) {
                    tempPoint.setX(++x);
                }
            }
            tempPoint.setY(++y);
        }
        return listSetPoints;
    }
    private void processHeadObject(Point tempPoint) {
        listSetPoints.put(counterObjects++, tempListPoints);
        navigateFlags.initFlags();
        tempListPoints = new ArrayList<>();
        pixelPointController(tempPoint);
    }
    private void processHotPoint(Point originalLastPoint, Point tempPoint, int[][] imagePixels) {
        navigateFlags.initFlags();
        tempPoint.copyPoint(originalLastPoint);
        tempListPoints.add(originalLastPoint);
        imagePixels[tempPoint.getY()][tempPoint.getX()]
                = listSetPoints.size() + imagePixels[tempPoint.getY()][tempPoint.getX()] + 1;
        pixelPointController(tempPoint);
    }
    private void pixelPointController(Point tempPoint) {
        int nextY = tempPoint.getY() - 1;
        int nextX = tempPoint.getX() + 1;
        int prevY = tempPoint.getY() + 1;
        int prevX = tempPoint.getX() - 1;

        // up
        if (navigateFlags.isUpFlag()) {
            tempPoint.setY(nextY);
            navigateFlags.setUpFlag(false);
        }
        // left
        else if (navigateFlags.isLeftFlag()) {
            tempPoint.setY(prevY); // return "origin" value y
            tempPoint.setX(nextX);
            navigateFlags.setLeftFlag(false);
        }
        // down
        else if (navigateFlags.isDownFlag()) {
            tempPoint.setY(prevY);
            tempPoint.setX(prevX); // return "origin" value x
            navigateFlags.setDownFlag(false);
        }
        // right
        else if (navigateFlags.isRightFlag()) {
            tempPoint.setY(nextY); // return "origin" value y
            tempPoint.setX(prevX);
            navigateFlags.setRightFlag(false);
        } else {
            throw new DanglingOutlineException("Contour was not continue!");
        }
    }

}
