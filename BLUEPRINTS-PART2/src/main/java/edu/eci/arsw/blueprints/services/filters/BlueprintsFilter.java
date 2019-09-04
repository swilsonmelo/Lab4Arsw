package edu.eci.arsw.blueprints.services.filters;

import java.util.List;
import java.util.Set;

import edu.eci.arsw.blueprints.model.Point;

public interface BlueprintsFilter {
	
	public Point[] filter(List<Point> blueprints);

}