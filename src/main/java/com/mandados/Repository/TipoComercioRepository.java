package com.mandados.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.mandados.Entidades.TiposcomerciosEntity;

@Repository
public interface TipoComercioRepository extends CrudRepository<TiposcomerciosEntity, Long>  {
    public List<TiposcomerciosEntity> findAll();
    public TiposcomerciosEntity findByNombre(String nombre);
}