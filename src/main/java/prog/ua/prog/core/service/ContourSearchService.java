package prog.ua.prog.core.service;

import prog.ua.prog.core.model.Point;

import java.util.List;
import java.util.Map;

public interface ContourSearchService {
    Map<Integer, List<Point>> findContourObject();
}
