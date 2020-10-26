package com.mandados.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.mandados.Entidades.ComerciosEntity;

@Repository
public interface ComercioRepository extends CrudRepository<ComerciosEntity, Long>  {
    public List<ComerciosEntity> findAll();
    public ComerciosEntity findByNombre(String nombre);
}