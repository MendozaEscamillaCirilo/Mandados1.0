package com.mandados.Servicios.Producto;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mandados.Entidades.ProductosEntity;

@Repository
public interface IProducto extends CrudRepository<ProductosEntity, Integer>{

}
