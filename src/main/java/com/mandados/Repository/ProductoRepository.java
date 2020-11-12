package com.mandados.Repository;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
// import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.mandados.Entidades.ProductosEntity;
import com.mandados.Entidades.ComerciosEntity;

@Repository
public interface ProductoRepository extends CrudRepository<ProductosEntity, Long> {
    public List<ProductosEntity> findAll();
    public ProductosEntity findByNombre(String nombre);
    public List<ProductosEntity> findByComercio(ComerciosEntity comercio);
    public List<ProductosEntity> findByNombreLike(String nombre);
    // @Query(value="select * from productos where nombre like %:nombre%", nativeQuery=true)
    // public List<ProductosEntity> buscarPorNombre(@Param("nombre") String nombre);
    // public List<ProductosEntity> indUsersByKeyword(@Param("keyword") String keyword);

}