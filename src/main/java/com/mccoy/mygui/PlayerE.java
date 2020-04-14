/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mccoy.mygui;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author rmccoy
 */
@Entity
public class PlayerE {
    @Id @GeneratedValue
    private Long id = null;
    private String name;
    private String archetype;
    private String position;
 
    public PlayerE(String name, String archetype, String position) {
        this.name = name;
        this.archetype = archetype;
        this.position = position;  
    }

    
}
