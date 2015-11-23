/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jana.jee.showcase.control;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.Valid;
import org.jana.jee.showcase.entity.Movie;

/**
 *
 * @author gita
 */
public class MovieService {
    @PersistenceUnit(unitName = "default")
    private EntityManagerFactory emf;
    
    /**
     * 
     * @return 
     */
    public List<Movie> load() {
        return emf.createEntityManager().createNamedQuery("Movie.findAll").getResultList();
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public Movie find(String id) {
        return emf.createEntityManager().find(Movie.class, id);
    }

    /**
     * 
     * @param movie
     * @return 
     */
    public Movie create(@Valid Movie movie) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            return null;
        }
        return movie;
    }
    
    /**
     * 
     * @param movie
     * @return 
     */
    public Movie update(Movie movie) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(movie);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            return null;
        }
        return movie;
    }
    
    /**
     * 
     * @param movie
     * @return 
     */
    public int delete(Movie movie) {
        final EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            movie = em.find(Movie.class, movie.getIdImdb());
            em.remove(movie);
            em.getTransaction().commit();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            return -1;
        }
    }
    
}
