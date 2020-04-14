/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mccoy.mygui;

import javax.persistence.*;

/**
 *
 * @author rmccoy
 */
public class PlayerService {
    
private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Test");
private EntityManager em = emf.createEntityManager();
private EntityTransaction tx = em.getTransaction();

public void createPlayerE(String name, String archetype, String position) {
    PlayerE playerE = new PlayerE(name, archetype, position);
    tx.begin();
    em.persist(playerE);
    tx.commit();
}


}
