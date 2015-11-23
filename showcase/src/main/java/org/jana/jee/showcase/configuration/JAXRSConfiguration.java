/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jana.jee.showcase.configuration;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.jana.jee.showcase.boundary.MovieResource;
import org.jana.jee.showcase.exception.ValidationException;

/**
 *
 * @author gita
 */
@ApplicationPath(value = "/resources")
public class JAXRSConfiguration extends Application{
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(ValidationException.class);
        classes.add(MovieResource.class);
        /* add your additional JAX-RS classes here */
        return classes;
    }
}
