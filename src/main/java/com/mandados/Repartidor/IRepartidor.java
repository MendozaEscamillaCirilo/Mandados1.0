package com.mandados.Repartidor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mandados.Entidades.Repartidor;

@Repository
public interface IRepartidor extends CrudRepository<Repartidor, Integer>{

}
