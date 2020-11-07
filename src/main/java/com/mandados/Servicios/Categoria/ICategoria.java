package com.mandados.Servicios.Categoria;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mandados.Entidades.CategoriasEntity;

@Repository
public interface ICategoria extends CrudRepository<CategoriasEntity, Integer>{

}
