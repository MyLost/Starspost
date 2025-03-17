/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.npd.starspost.dao;

import java.util.function.Consumer;

import edu.itstep.app.modules.entities.BaseEntity;
import edu.itstep.app.modules.models.Model;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
/**
 *
 * @author midas
 */
public abstract class AbstractDao {

	private Boolean statusTransaction;
	private static final String PERSISTANCE_UNITS = "starsPostDataSource";
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;

	public AbstractDao() {
		entityManagerFactory = Persistence.createEntityManagerFactory("starsPostDataSource");
		entityManager = entityManagerFactory.createEntityManager();
	}
	public void executeInsideTransaction(Consumer<EntityManager> action) {
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			action.accept(entityManager);
			tx.commit();
			statusTransaction = true;
		}
		catch (RuntimeException e) {
			statusTransaction = false;
			tx.rollback();
			e.printStackTrace();
		}finally {
			entityManager.close();
		}
	}

	public Boolean isTransactionSuccess(){
		return statusTransaction;
	}

	public Boolean getStatusTransaction() {
		return statusTransaction;
	}

	public static String getPERSISTANCE_UNITS() {
		return AbstractDao.PERSISTANCE_UNITS;
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		return AbstractDao.entityManagerFactory;
	}

	public static EntityManager getEntityManager() {
		return AbstractDao.entityManager;
	}

	public void executeTransaction(BaseEntity<Model> baseEntity) {
		executeInsideTransaction(entityManager -> entityManager.persist(baseEntity));
	}
}
