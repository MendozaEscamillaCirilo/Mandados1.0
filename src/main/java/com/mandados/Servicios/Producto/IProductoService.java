package com.mandados.Servicios.Producto;

import java.util.List;
import java.util.Optional;
import com.mandados.Entidades.ProductosEntity;

public interface IProductoService {
	public List<ProductosEntity>listar();
	public Optional<ProductosEntity>listarId(int d);
	public int save(ProductosEntity p);
	public void delete(int d);
}