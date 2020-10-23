package com.mandados.Servicios.Comercio;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandados.Entidades.ComerciosEntity;

@Service
public class ComercioService implements IComercioService{

	@Autowired
	private IComercio data;
	
	@Override
	public List<ComerciosEntity> listar() {
		return (List<ComerciosEntity>)data.findAll();
	}

	@Override
	public Optional<ComerciosEntity> listarId(int id) {
		return data.findById(id);
	}

	@Override
	public int save(ComerciosEntity p) {
		ComerciosEntity comercio=data.save(p);
		if(!comercio.equals(null)){
			return 1;
		}
		return 0;
	}

	@Override
	public void delete(int id) {
		data.deleteById(id);
	}

}
