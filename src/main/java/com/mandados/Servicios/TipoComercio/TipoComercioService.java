package com.mandados.Servicios.TipoComercio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandados.Entidades.Tipos_comerciosEntity;

@Service
public class TipoComercioService implements ITipoComercioService{

	@Autowired
	private ITipoComercio data;
	
	@Override
	public List<Tipos_comerciosEntity> listar() {
		return (List<Tipos_comerciosEntity>)data.findAll();
	}

	@Override
	public Optional<Tipos_comerciosEntity> listarId(int id) {
		return data.findById(id);
	}

	@Override
	public int save(Tipos_comerciosEntity p) {
		Tipos_comerciosEntity comercio=data.save(p);
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
