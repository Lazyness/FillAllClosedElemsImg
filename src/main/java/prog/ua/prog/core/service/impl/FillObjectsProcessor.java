package prog.ua.prog.core.service.impl;

import lombok.RequiredArgsConstructor;
import prog.ua.prog.core.model.ImageInPixels;
import prog.ua.prog.core.model.NavigateFlags;
import prog.ua.prog.core.model.Point;
import prog.ua.prog.core.service.ExtremePointsContourSortService;
import prog.ua.prog.core.service.FillObjectsProcessingService;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class FillObjectsProcessor implements FillObjectsProcessingService {
    private final ImageInPixels imageInPixels;

    private void fillCell(Point startPoint, Point endPoint, int[][] imagePixels) {
        for (int i = 1; i < endPoint.getX() - startPoint.getX(); i++) {
            // set color
            imagePixels[startPoint.getY()][startPoint.getX() + i] = imagePixels[startPoint.getY()][startPoint.getX()];
        }
    }

    public void fillObjectsOnImage() {
        int[][] imagePixels = imageInPixels.getPixels();

        ExtremePointsContourSortService extremePointsContourSortService = new ExtremePointsContourSorter(imageInPixels);
        Map<Integer, List<List<Point>>> mapListStartAndEndPointInContourObjects
                = extremePointsContourSortService.getMapListStartAndEndPointInContourObjects();
        for (Map.Entry<Integer, List<List<Point>>> listEntryStartAndEndPointInObject : mapListStartAndEndPointInContourObjects.entrySet()) {
            List<List<Point>> listsOfPoints = listEntryStartAndEndPointInObject.getValue();
            for (List<Point> listsOfPoint : listsOfPoints) {
                int j = 0;
                while (j < listsOfPoint.size() - 1) {
                    Point startPoint = listsOfPoint.get(j);
                    Point endPoint = listsOfPoint.get(j + 1);
                    if (endPoint.getX() - startPoint.getX() != 1 && isValidContourRange(listsOfPoint, j)) {
                        fillCell(startPoint, endPoint, imagePixels);
                        j++;
                    }
                    j++;
                }
            }
        }
    }

    private boolean isValidContourRange(List<Point> listsOfPoint, int index) {
        NavigateFlags navigateFlagsForStartPoint = new NavigateFlags();
        NavigateFlags navigateFlagsForEndPoint = new NavigateFlags();

        navigateFlagsForStartPoint.resetFlags();
        navigateFlagsForEndPoint.resetFlags();

        int[][] imagePixels = imageInPixels.getPixels();

        Point startPoint = listsOfPoint.get(index);
        Point endPoint = listsOfPoint.get(index + 1);

        // check start point and end point
        if (isStartAndEndPointHaveValidPoints(imagePixels, startPoint, endPoint)) {
            return true;
        }

        checkCloseVerticalColorPoint(imagePixels, startPoint, startPoint.getX() + 1, navigateFlagsForStartPoint);

        checkCloseVerticalColorPoint(imagePixels, endPoint, endPoint.getX() - 1, navigateFlagsForEndPoint);

        if ((navigateFlagsForStartPoint.isUpFlag() && navigateFlagsForStartPoint.isDownFlag())
                || (navigateFlagsForEndPoint.isUpFlag() && navigateFlagsForEndPoint.isDownFlag())) {
            return true;
        } else if (navigateFlagsForStartPoint.isUpFlag() && navigateFlagsForEndPoint.isUpFlag()) {
            return isFillObjectUpPartContour(startPoint, endPoint, imagePixels);
        } else if (navigateFlagsForStartPoint.isDownFlag() && navigateFlagsForEndPoint.isDownFlag()) {
            return isFillObjectDownPartContour(startPoint, endPoint, imagePixels);
        } else return true;
    }

    private void checkCloseVerticalColorPoint(int[][] imagePixels, Point point, int navigateX, NavigateFlags navigateFlags) {
        if (imagePixels[point.getY()][navigateX] == 0) {
            if (imagePixels[point.getY() - 1][point.getX()] != 0) {
                navigateFlags.setUpFlag(true);
            }

            if (imagePixels[point.getY() + 1][point.getX()] != 0) {
                navigateFlags.setDownFlag(true);
            }
        }
    }

    private boolean isStartAndEndPointHaveValidPoints(int[][] imagePixels, Point startPoint, Point endPoint) {
        return (imagePixels[startPoint.getY()][startPoint.getX() - 1] == 0
                || imagePixels[startPoint.getY()][startPoint.getX() - 2] == 0)
                && (imagePixels[endPoint.getY()][endPoint.getX() + 1] == 0
                || imagePixels[endPoint.getY()][endPoint.getX() + 2] == 0);
    }

    private boolean isFillObjectUpPartContour(Point startPoint, Point endPoint, int[][] imagePixels) {
        return imagePixels[startPoint.getY() - 1][startPoint.getX() - 1] == 0
                || imagePixels[endPoint.getY() - 1][endPoint.getX() + 1] == 0;
    }

    private boolean isFillObjectDownPartContour(Point startPoint, Point endPoint, int[][] imagePixels) {
        return imagePixels[startPoint.getY() - 1][startPoint.getX() - 1] != 0
                && imagePixels[endPoint.getY() - 1][endPoint.getX() + 1] != 0;
    }
}
