/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jana.jee.showcase.entity;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gita
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Message {

    public Message() {
    }

    public Message(Code code) {
        this.id = code.id;
        this.description = code.description;
        this.type = code.type;
    }
    
    public Message(Code code, String description) {
        this.id = code.id;
        this.description = description;
        this.type = code.type;
    }

    @Transient
    private int id;
    private String description;
    private Type type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    
    /**
     * 
     */
    public enum Type {
        ERROR,
        INFO;
    }
    
    /**
     * 
     */
    public enum Code {

        /**
         *
         */
        CONSTRAINT_VIOLATION(1001,Type.ERROR, ""),
        MOVIE_EXIST(2001,Type.ERROR, "Movie already exists, check identifier"),
        MOVIE_CREATED(2002,Type.INFO, "Movie created, refer link"),
        MOVIE_NOT_FOUND(2003,Type.ERROR, "Movie not found"),
        MOVIE_NOT_ACCEPTABLE(2004,Type.ERROR, "Movie could not be created");
        
        private final int id;
        private final Type type;
        private final String description;
        
        Code(int id, Type type, String description) {
            this.id = id;
            this.type = type;
            this.description = description;
        }
    }
    
}
