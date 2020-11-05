package com.mandados.Servicios.Sucursal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mandados.Entidades.SucursalesEntity;

@Repository
public interface ISucursal extends CrudRepository<SucursalesEntity, Integer>{

}
