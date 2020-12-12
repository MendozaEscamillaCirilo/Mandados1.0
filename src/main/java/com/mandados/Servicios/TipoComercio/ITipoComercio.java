package com.mandados.Servicios.TipoComercio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mandados.Entidades.Tipos_comerciosEntity;

@Repository
public interface ITipoComercio extends CrudRepository<Tipos_comerciosEntity, Integer>{

}
