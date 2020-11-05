package com.mandados.Servicios.Sucursal;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandados.Entidades.SucursalesEntity;

@Service
public class SucursalService implements ISucursalService{

	@Autowired
	private ISucursal data;
	
	@Override
	public List<SucursalesEntity> listar() {
		return (List<SucursalesEntity>)data.findAll();
	}

	@Override
	public Optional<SucursalesEntity> listarId(int id) {
		return data.findById(id);
	}

	@Override
	public int save(SucursalesEntity p) {
		SucursalesEntity sucursal=data.save(p);
		if(!sucursal.equals(null)){
			return 1;
		}
		return 0;
	}

	@Override
	public void delete(int id) {
		data.deleteById(id);
	}

}
