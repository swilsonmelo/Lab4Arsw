package edu.eci.arsw.blueprints.services.filters.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.filters.BlueprintsFilter;

@Component("redundancyFilter")
public class BlueprintsRedundancyFilter implements BlueprintsFilter {

    @Override
    public Point[] filter(List<Point> points) {
        List<Point> newPoints = new ArrayList<Point>();
        for (int i = 0; i < points.size(); i++) {
            int pos = i + 1;
            newPoints.add(points.get(i));
            while (pos < points.size() && points.get(i).equals(points.get(pos))) {
                pos++;
            }
            i = pos-1;
        }
        Point[] np = new Point[newPoints.size()];
        for(int i = 0; i < newPoints.size(); i++) np[i] = newPoints.get(i);
        return np;
	}

}