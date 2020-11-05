package com.mandados.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.mandados.Entidades.Tipos_comerciosEntity;

@Repository
public interface TipoComercioRepository extends CrudRepository<Tipos_comerciosEntity, Long>  {
    public List<Tipos_comerciosEntity> findAll();
    public Tipos_comerciosEntity findByNombre(String nombre);
}