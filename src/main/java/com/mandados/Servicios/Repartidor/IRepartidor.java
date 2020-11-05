package com.mandados.Servicios.Repartidor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mandados.Entidades.RepartidoresEntity;

@Repository
public interface IRepartidor extends CrudRepository<RepartidoresEntity, Integer>{

}
