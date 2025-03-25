/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.npd.starspost.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.npd.starspost.models.EventModel;
import org.npd.starspost.models.Model;

/**
 *
 * @author midas
 */
@Entity(name="app_event")
@Table(name="app_event", schema="stars")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventEntity extends BaseEntity<Model> implements Serializable {

    private static final long serialVersionUID = 2694737016002668828L;
    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVENTGENERATOR")
    //@SequenceGenerator(name = "EVENTGENERATOR",sequenceName = "app_event_id_seq",  allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="TYPE_EVENT")
    private String type;
    @Column(name="TIME_EVENT")
    private String time;
    @Column(name="DESCRIPTION")
    private String description;
    private int user_id;
    private String ip_address;

    public EventModel toEventModel() {
        return new EventModel(
            getType(),
            getTime(),
            getDescription(),
            getUser_id(),
            getIp_address()
            );
    }
    @Override
    public Model toModel() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public List<Model> toListModel(List<BaseEntity<Model>> entity) {
        // TODO Auto-generated method stub
        return null;
    }
}
