/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package cs.scrs.miner.dao.citations;


import cs.scrs.miner.dao.transaction.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import cs.scrs.miner.dao.user.User;
import java.io.Serializable;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "citation")
public class Citation {

    
    // Columns
    @EmbeddedId()
    private Key key;
    
    public Citation(){
        super();
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    
    
    @Embeddable
    public static class Key implements Serializable{
        
        @Column(name = "hashCiting")  
        private String hashCiting; //hash di chi cita
    
        @Column(name = "hashCited")
        private String hashCited; //hash di chi viene citato
        
        protected Key(){
        }
        
        public String getHashCiting() {
            return hashCiting;
        }

        public void setHashCiting(String hashCiting) {
            this.hashCiting = hashCiting;
        }

        public String getHashCited() {
            return hashCited;
        }

        public void setHashCited(String hashCited) {
            this.hashCited = hashCited;
        }
    }
	
}