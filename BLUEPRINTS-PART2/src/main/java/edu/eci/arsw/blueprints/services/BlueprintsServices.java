/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import edu.eci.arsw.blueprints.services.filters.BlueprintsFilter;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 * @param <bpf>
 */
@Service
public class BlueprintsServices<bpf> {

    @Autowired
    @Qualifier("inMemoryBlueprintPersistence")
    BlueprintsPersistence bpp;

    @Autowired
    @Qualifier("redundancyFilter")
    BlueprintsFilter bpf;

    public void addNewBlueprint(Blueprint bp) {
        try {
            System.out.println(bpp);
            bpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Set<Blueprint> getAllBlueprints() {
        return null;
    }

    /**
     * 
     * @param author blueprint's author
     * @param name   blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        return bpp.getBlueprint(author, name);
    }

    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        return bpp.getBluerintsByAuthor(author);
    }

    /**
     * 
     * @param author blueprint's author
     * @param name   blueprint's name
     * @return the blueprint of the given name created by the given author with
     *         point filtered
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprintFiltered(String author, String name) throws BlueprintNotFoundException {
        Blueprint bp = bpp.getBlueprint(author, name);
        Blueprint bpfiltered;
        List<Point> ptf = bp.getPoints();
        Point[] pointsFiltered = bpf.filter(ptf);
        bpfiltered = new Blueprint(author, name, pointsFiltered);
        return bpfiltered;
    }

    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthorFiltered(String author) throws BlueprintNotFoundException {
        Set<Blueprint> bps = bpp.getBluerintsByAuthor(author);
        Set<Blueprint> bpsf = new HashSet<Blueprint>();
        for (Blueprint bp : bps) {
            List<Point> ptf = bp.getPoints();
            Point[] pointsFiltered = bpf.filter(ptf);
            Blueprint bpfiltered = new Blueprint(author, bp.getName(), pointsFiltered);
            bpsf.add(bpfiltered);
        }
        return bpsf;
    }

}
