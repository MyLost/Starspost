/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.npd.starspost.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.Query;
import org.npd.starspost.entities.EventEntity;

/**
 *
 * @author midas
 */
public class EventJPADao extends AbstractDao implements BaseDao<EventEntity, String> {

	private Boolean statusTransaction = false;

	public EventJPADao() {
		super();
	}

	@Override
	public Optional<EventEntity> find(String id) throws SQLException {
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("starsPostDataSource");
		//EntityManager em = emf.createEntityManager();
		Optional optional = Optional.ofNullable(getEntityManager().find(EventEntity.class, id));
		getEntityManager().close();
		return optional;
	}

	@Override
	public List<EventEntity> findAll() throws SQLException {
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("starsPostDataSource");
		//EntityManager em = emf.createEntityManager();
		Query query = getEntityManager().createQuery("SELECT a FROM app_event a");
		getEntityManager().close();
		return query.getResultList();
	}

	@Override
	public Boolean save(EventEntity eventEntity) throws SQLException {
		//logger.info("Here we have not entity manager " + getEntityManager());
		executeTransaction(eventEntity);
		//logger.info("do we here entity manager hire in eventJPA " + getEntityManager());
		//getEntityManager().persist(eventEntity);
		//getEntityManager().close();
		//return statusTransaction;
		return true;
	}

	@Override
	public Boolean update(EventEntity eventEntity) throws SQLException {
		executeInsideTransaction(em -> em.merge(eventEntity));
		return statusTransaction;
	}

	@Override
	public Boolean delete(EventEntity eventEntity) throws SQLException {
		executeInsideTransaction(em -> em.remove(eventEntity));
		return statusTransaction;
	}
}
