package com.mandados.Restaurante;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandados.Entidades.Restaurante;

@Service
public class RestauranteService implements IRestauranteService{

	@Autowired
	private IRestaurante data;
	
	@Override
	public List<Restaurante> listar() {
		return (List<Restaurante>)data.findAll();
	}

	@Override
	public Optional<Restaurante> listarId(int id) {
		return data.findById(id);
	}

	@Override
	public int save(Restaurante p) {
		Restaurante restaurante=data.save(p);
		if(!restaurante.equals(null)){
			return 1;
		}
		return 0;
	}

	@Override
	public void delete(int id) {
		data.deleteById(id);
	}

}
