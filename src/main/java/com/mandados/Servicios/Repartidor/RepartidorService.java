package com.mandados.Servicios.Repartidor;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandados.Entidades.RepartidoresEntity;

@Service
public class RepartidorService implements IRepartidorService{

	@Autowired
	private IRepartidor data;
	
	@Override
	public List<RepartidoresEntity> listar() {
		return (List<RepartidoresEntity>)data.findAll();
	}

	@Override
	public int save(RepartidoresEntity p) {
		RepartidoresEntity repartidor=data.save(p);
		if(!repartidor.equals(null)){
			return 1;
		}
		return 0;
	}

	@Override
	public void delete(Long id) {
		data.deleteById(id);
	}

}
