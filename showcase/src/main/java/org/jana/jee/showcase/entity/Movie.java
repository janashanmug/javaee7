/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jana.jee.showcase.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gita
 */
@Entity
@Table(name = "MOVIE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Movie.findAll", query = "SELECT m FROM Movie m"),
    @NamedQuery(name = "Movie.findByIdImdb", query = "SELECT m FROM Movie m WHERE m.idImdb = :idImdb"),
    @NamedQuery(name = "Movie.findByTitle", query = "SELECT m FROM Movie m WHERE m.title = :title"),
    @NamedQuery(name = "Movie.findByRanking", query = "SELECT m FROM Movie m WHERE m.ranking = :ranking"),
    @NamedQuery(name = "Movie.findByRating", query = "SELECT m FROM Movie m WHERE m.rating = :rating"),
    @NamedQuery(name = "Movie.findByPosterUrl", query = "SELECT m FROM Movie m WHERE m.posterUrl = :posterUrl"),
    @NamedQuery(name = "Movie.findByRelease", query = "SELECT m FROM Movie m WHERE m.release = :release")})
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID_IMDB")
    private String idImdb;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TITLE")
    private String title;
    @Column(name = "RANKING")
    private Integer ranking;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "RATING")
    private Double rating;
    @Size(max = 250)
    @Column(name = "POSTER_URL")
    private String posterUrl;
    @Column(name = "RELEASE")
    private Integer release;
    
    public Movie() {
    }

    public Movie(String idImdb) {
        this.idImdb = idImdb;
    }

    public Movie(String idImdb, String title) {
        this.idImdb = idImdb;
        this.title = title;
    }

    public String getIdImdb() {
        return idImdb;
    }

    public void setIdImdb(String idImdb) {
        this.idImdb = idImdb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Integer getRelease() {
        return release;
    }

    public void setRelease(Integer release) {
        this.release = release;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idImdb != null ? idImdb.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movie)) {
            return false;
        }
        Movie other = (Movie) object;
        if ((this.idImdb == null && other.idImdb != null) || (this.idImdb != null && !this.idImdb.equals(other.idImdb))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jana.jee.showcase.entity.Movie[ idImdb=" + idImdb + " ]";
    }
    
}
