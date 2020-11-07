package com.mandados.Repository;

import java.util.List;

import com.mandados.Entidades.CategoriasEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends CrudRepository<CategoriasEntity, Long>  {
    public List<CategoriasEntity> findAll();
    public CategoriaRepository findByNombre(String nombre);
}