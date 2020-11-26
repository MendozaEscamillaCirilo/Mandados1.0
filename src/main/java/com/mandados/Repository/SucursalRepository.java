package com.mandados.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import com.mandados.Entidades.SucursalesEntity;

public interface SucursalRepository extends CrudRepository<SucursalesEntity, Long>  {
    public List<SucursalesEntity> findAll();
}
