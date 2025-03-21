/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.npd.starspost.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author midas
 * @param <Model>
 * @param <ID>
 */
public interface BaseDao<BaseEntity, ID> {
	Optional<BaseEntity> find(ID id) throws SQLException;;
	List<BaseEntity> findAll() throws SQLException;
	Boolean save(BaseEntity entity) throws SQLException;
	Boolean update(BaseEntity entity) throws SQLException;
	Boolean delete(BaseEntity entity) throws SQLException;
}
