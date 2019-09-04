/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){            
        }                        
    }


    @Test

    public void getBlueprintByAuthorTest() throws BlueprintNotFoundException{
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices bps = ac.getBean(BlueprintsServices.class);

        Point[] pts1 = new Point[]{new Point(0, 0),new Point(10, 10) ,new Point(40,40)};
        Blueprint bp1 = new Blueprint("getBluePrint1", "thepaint1", pts1);
        
        bps.addNewBlueprint(bp1);

        Point[] pts2=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp2=new Blueprint("getBluePrint1", "thepaint2", pts2);

        bps.addNewBlueprint(bp2);

        Set<Blueprint> set1 = bps.getBlueprintsByAuthor("getBluePrint1");
        Set<Blueprint> set2 = new HashSet<Blueprint>();
        set2.add(bp1);
        set2.add(bp2);
        //System.out.println(set1.equals(set2));

        assertEquals(set1, set2);

    }
    
    @Test
    public void redundancyFilterTestGetBluePrintTest() throws BlueprintPersistenceException, BlueprintNotFoundException {    
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices bps = ac.getBean(BlueprintsServices.class);
        Point[] points = new Point[] { new Point(140, 140)
            ,new Point(115, 115)
            ,new Point(115, 115)
            ,new Point(115, 115)
            ,new Point(115, 115)
            ,new Point(115, 115)
            ,new Point(115, 115)
            };
        Blueprint bp =new Blueprint("redundancyFilter", "thepaint", points);

        Point[] pointFilter = new Point[] { new Point(140, 140)
            ,new Point(115, 115)
            };
        Blueprint bpFilter = new Blueprint("redundancyFilter", "thepaint", pointFilter);

        bps.addNewBlueprint(bp);
        
        //System.out.println(bps.getBlueprintFiltered(bp.getAuthor(), bp.getName()).equals(bpFilter));
        //System.out.println(bpFilter);
        
        assertEquals(bps.getBlueprintFiltered(bp.getAuthor(), bp.getName()), bpFilter);
    }

    @Test
    public void redundancyFilterTestGetBluePrintByAuthorTest() throws BlueprintPersistenceException, BlueprintNotFoundException {

        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices bps = ac.getBean(BlueprintsServices.class);

        Point[] points1 = new Point[] { new Point(140, 140)
            ,new Point(115, 115)
            ,new Point(115, 115)
            ,new Point(115, 115)
            ,new Point(115, 115)
            ,new Point(115, 115)
            ,new Point(115, 115)
            };
        Blueprint bp1 =new Blueprint("redundancyFilterAuthor", "thepaint1", points1);
        
        bps.addNewBlueprint(bp1);

        Point[] points2 = new Point[] { new Point(140, 140)
            ,new Point(115, 115)
            ,new Point(120, 120)
            ,new Point(120, 120)
            ,new Point(115, 115)
            ,new Point(115, 115)
            ,new Point(115, 115)
            };
        Blueprint bp2 =new Blueprint("redundancyFilterAuthor", "thepaint2", points2);
        
        bps.addNewBlueprint(bp2);

        Point[] points3 = new Point[] { new Point(140, 140)
            ,new Point(140, 140)
            ,new Point(120, 120)
            ,new Point(115, 115)
            ,new Point(115, 115)
            ,new Point(130, 130)
            ,new Point(130, 130)
            };
        Blueprint bp3 =new Blueprint("redundancyFilterAuthor", "thepaint3", points3);
        
        bps.addNewBlueprint(bp3);

        Point[] pointFilter1 = new Point[] { new Point(140, 140)
            ,new Point(115, 115)
            };
        Blueprint bpFilter1 =new Blueprint("redundancyFilterAuthor", "thepaint1", pointFilter1);

        Point[] pointFilter2 = new Point[] { new Point(140, 140)
            ,new Point(115, 115)
            ,new Point(120, 120)
            ,new Point(115, 115)
            };
        Blueprint bpFilter2 =new Blueprint("redundancyFilterAuthor", "thepaint2", pointFilter2);

        Point[] pointFilter3 = new Point[] { new Point(140, 140)
            ,new Point(120, 120)
            ,new Point(115, 115)
            ,new Point(130, 130)
            };
        Blueprint bpFilter3 =new Blueprint("redundancyFilterAuthor", "thepaint3", pointFilter3);
        
        Set<Blueprint> setBpFilter = new HashSet<Blueprint>();

        setBpFilter.add(bpFilter1);
        setBpFilter.add(bpFilter2);
        setBpFilter.add(bpFilter3);

        Set<Blueprint> setbBpFilterResult = bps.getBlueprintsByAuthorFiltered("redundancyFilterAuthor");
        List<Blueprint> arr1 = new ArrayList<Blueprint>();
        List<Blueprint> arr2 = new ArrayList<Blueprint>();
        for(Blueprint bp: setbBpFilterResult){
            arr1.add(bp);
        }
        for(Blueprint bp: setBpFilter){
            arr2.add(bp);
        }

        System.out.println(arr1.equals(arr2));
        assertEquals(arr1,arr2);
    }


    
}