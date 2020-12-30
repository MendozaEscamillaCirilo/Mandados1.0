package com.mandados.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.mandados.Entidades.RepartidoresEntity;

@Repository
public interface RepartidorRepository extends CrudRepository<RepartidoresEntity, Long>  {
    public List<RepartidoresEntity> findAll();
    public List<RepartidoresEntity>findByEstatus(boolean estatus);
    public RepartidoresEntity findByNombre(String nombre);
}
