package com.mandados.Servicios.Producto;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandados.Entidades.ProductosEntity;

@Service
public class ProductoService implements IProductoService{

	@Autowired
	private IProducto data;
	
	@Override
	public List<ProductosEntity> listar() {
		return (List<ProductosEntity>)data.findAll();
	}

	@Override
	public Optional<ProductosEntity> listarId(int id) {
		return data.findById(id);
	}

	@Override
	public int save(ProductosEntity p) {
		ProductosEntity producto=data.save(p);
		if(!producto.equals(null)){
			return 1;
		}
		return 0;
	}

	@Override
	public void delete(int id) {
		data.deleteById(id);
	}

}
