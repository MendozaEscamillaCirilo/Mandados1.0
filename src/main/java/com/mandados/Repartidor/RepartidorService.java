package com.mandados.Repartidor;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandados.Entidades.Repartidor;

@Service
public class RepartidorService implements IRepartidorService{

	@Autowired
	private IRepartidor data;
	
	@Override
	public List<Repartidor> listar() {
		return (List<Repartidor>)data.findAll();
	}

	@Override
	public Optional<Repartidor> listarId(int id) {
		return data.findById(id);
	}

	@Override
	public int save(Repartidor p) {
		Repartidor repartidor=data.save(p);
		if(!repartidor.equals(null)){
			return 1;
		}
		return 0;
	}

	@Override
	public void delete(int id) {
		data.deleteById(id);
	}

}
