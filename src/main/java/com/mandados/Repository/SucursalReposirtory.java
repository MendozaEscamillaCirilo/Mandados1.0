package com.mandados.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.mandados.Entidades.SucursalesEntity;

public interface SucursalReposirtory extends CrudRepository<SucursalesEntity, Long>  {
    public List<SucursalesEntity> findAll();
}
