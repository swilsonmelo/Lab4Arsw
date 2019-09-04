/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

    @Autowired
    BlueprintsServices bps;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetRecursoBlueprints() {
        try{
            System.out.println(bps.getAllBlueprints());
            return new ResponseEntity<>(bps.getAllBlueprints(), HttpStatus.ACCEPTED);
        }catch(Exception e){
            return new ResponseEntity<>("Error - Not Found", HttpStatus.NOT_FOUND);
        }
    } 
    
    @RequestMapping(value = "/{author}" ,method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetRecursoBlueprintsByAuthor(@PathVariable String author) {
        try{
            System.out.println(bps.getBlueprintsByAuthor(author));
            return new ResponseEntity<>(bps.getBlueprintsByAuthor(author), HttpStatus.ACCEPTED);
        }catch(Exception e){
            return new ResponseEntity<>("Error - Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{author}/{name}" ,method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetRecursoBlueprint(@PathVariable String author, @PathVariable String name) {
        try{
            System.out.println(bps.getBlueprint(author, name));
            return new ResponseEntity<>(bps.getBlueprint(author, name), HttpStatus.ACCEPTED);
        }catch(Exception e){
            return new ResponseEntity<>("Error - Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> manejadorPostBlueprints(@RequestBody Blueprint bp) {
        try{
            bps.addNewBlueprint(bp);
            System.out.println(bps.getBlueprint(bp.getAuthor(), bp.getName()));
            return new ResponseEntity<>("Created",HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("Error - Not created", HttpStatus.FORBIDDEN);
        }
    }



    
}

