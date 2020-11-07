package com.mandados.Servicios.Categoria;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandados.Entidades.CategoriasEntity;

@Service
public class CategoriaService implements ICategoriaService{

	@Autowired
	private ICategoria data;
	
	@Override
	public List<CategoriasEntity> listar() {
		return (List<CategoriasEntity>)data.findAll();
	}

	@Override
	public Optional<CategoriasEntity> listarId(int id) {
		return data.findById(id);
	}

	@Override
	public int save(CategoriasEntity p) {
		CategoriasEntity categoria=data.save(p);
		if(!categoria.equals(null)){
			return 1;
		}
		return 0;
	}

	@Override
	public void delete(int id) {
		data.deleteById(id);
	}

}
