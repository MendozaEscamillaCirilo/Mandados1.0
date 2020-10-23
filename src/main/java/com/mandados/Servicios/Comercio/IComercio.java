package com.mandados.Servicios.Comercio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mandados.Entidades.ComerciosEntity;

@Repository
public interface IComercio extends CrudRepository<ComerciosEntity, Integer>{

}
