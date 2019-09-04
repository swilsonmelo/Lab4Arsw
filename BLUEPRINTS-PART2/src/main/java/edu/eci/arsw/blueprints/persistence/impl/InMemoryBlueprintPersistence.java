/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import java.awt.SystemTray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

/**
 *
 * @author hcadavid
 */
@Component
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] ptsbp1=new Point[]{new Point(130, 140),new Point(125, 115)};
        Blueprint bp1=new Blueprint("_authorname_", "_bpname_first_",ptsbp1);
        Point[] ptsbp2=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp2=new Blueprint("_authorname_", "_bpname_second_",ptsbp2);
        Point[] ptsbp3=new Point[]{new Point(112, 213),new Point(10, 1234)};
        Blueprint bp3=new Blueprint("_authorname_second_", "_bpname_",ptsbp3);
        Point[] ptsbp4=new Point[]{new Point(1234, 140),new Point(4123, 115)};
        Blueprint bp4=new Blueprint("_authorname_third_", "_bpname_",ptsbp4);
        blueprints.put(new Tuple<>(bp1.getAuthor(),bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);
        blueprints.put(new Tuple<>(bp4.getAuthor(),bp4.getName()), bp4);
        
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        Blueprint btr =  blueprints.get(new Tuple<>(author, bprintname));
        if(btr==null){
            throw new BlueprintNotFoundException("Blueprint Not Found");
        }
        return btr;
    }

    @Override
    public Set<Blueprint> getBluerintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> bluePrints = new HashSet<Blueprint>();
        for( Tuple t: blueprints.keySet() ){
            //System.out.println(t.getElem1().toString() + " " + author);
            if( t.getElem1().toString().equals(author) ){
                bluePrints.add(blueprints.get(t));
            }
        }
        if(bluePrints.size()==0){
            throw new BlueprintNotFoundException("Autor no existente");
        }
        return bluePrints;
	}

    @Override
    public Set<Blueprint> getAllBlueprints() {
        Set<Blueprint> bps = new HashSet<Blueprint>();
        for(Tuple t : blueprints.keySet()){
            bps.add(blueprints.get(t));
        }
        return bps;
    }

    
    
}
