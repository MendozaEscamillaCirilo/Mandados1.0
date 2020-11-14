package com.mandados.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.mandados.Entidades.ProductosEntity;
import com.mandados.Entidades.CategoriasEntity;
import com.mandados.Entidades.ComerciosEntity;

@Repository
public interface ProductoRepository extends CrudRepository<ProductosEntity, Long> {
    public List<ProductosEntity> findAll();
    public ProductosEntity findByNombre(String nombre);
    public List<ProductosEntity> findByComercio(ComerciosEntity comercio);
    public List<ProductosEntity> findByNombreLike(String nombre);
    public List<ProductosEntity> findByNombreStartsWith(String nombre);
    public List<ProductosEntity> findByCategoria(CategoriasEntity categoria);
    public List<ProductosEntity> findByNombreContaining(String nombre);
    public List<ProductosEntity> findByComercioAndNombreContaining(ComerciosEntity comercio, String nombre);

}