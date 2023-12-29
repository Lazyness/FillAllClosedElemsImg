package prog.ua.prog.core.service.impl;

import lombok.RequiredArgsConstructor;
import prog.ua.prog.core.model.ImageInPixels;
import prog.ua.prog.core.model.Point;
import prog.ua.prog.core.service.ContourSearching;
import prog.ua.prog.core.service.ExtremePointsContourSorting;

import java.util.*;

@RequiredArgsConstructor
public class ExtremePointsContourSorter implements ExtremePointsContourSorting {
    private final ImageInPixels imageInPixels;
    private List<Point> listPoints;
    private List<List<Point>> listSortedPoint;
    private final Map<Integer, List<List<Point>>> mapListStartAndEndPointInContourObjects = new HashMap<>();

    public Map<Integer, List<List<Point>>> getMapListStartAndEndPointInContourObjects() {
        ContourSearching contourSearching = new ContourSearch(imageInPixels);
        Map<Integer, List<Point>> listSetPoints = contourSearching.findContourObject();

        for (Map.Entry<Integer, List<Point>> listEntry : listSetPoints.entrySet()) {
            listSortedPoint = new ArrayList<>();
            listPoints = listEntry.getValue();
            sortByY();
            initListSetPointsObjectContour();
            sortByX();
            mapListStartAndEndPointInContourObjects.put(listEntry.getKey(), listSortedPoint);
        }
        return mapListStartAndEndPointInContourObjects;
    }
    private void sortByX() {
        for (List<Point> listSetPoints : listSortedPoint) {
            listSetPoints.sort(Comparator.comparingInt(Point::getX));
        }
    }

    private void initListSetPointsObjectContour() {
        int constY = listPoints.get(0).getY();
        List<Point> tempListPoints = new ArrayList<>();
        for (Point point : listPoints) {
            if (point.getY() != constY) {
                listSortedPoint.add(tempListPoints);
                constY = point.getY();
                tempListPoints = new ArrayList<>();
                tempListPoints.add(point);
                continue;
            }
            tempListPoints.add(point);
        }
        listSortedPoint.add(tempListPoints);
    }

    private void sortByY() {
        listPoints.sort(Comparator.comparingInt(Point::getY));
    }
}
