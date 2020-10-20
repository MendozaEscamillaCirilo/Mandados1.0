package com.mandados.Restaurante;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mandados.Entidades.Restaurante;

@Repository
public interface IRestaurante extends CrudRepository<Restaurante, Integer>{

}
