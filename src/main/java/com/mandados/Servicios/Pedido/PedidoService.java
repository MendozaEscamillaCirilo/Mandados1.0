package com.mandados.Servicios.Pedido;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandados.Entidades.PedidosEntity;

@Service
public class PedidoService implements IPedidoService{

	@Autowired
	private IPedido data;
	
	@Override
	public List<PedidosEntity> listar() {
		return (List<PedidosEntity>)data.findAll();
	}

	@Override
	public Optional<PedidosEntity> listarId(int id) {
		return data.findById(id);
	}

	@Override
	public int save(PedidosEntity p) {
		PedidosEntity pedido=data.save(p);
		if(!pedido.equals(null)){
			return 1;
		}
		return 0;
	}

	@Override
	public void delete(int id) {
		data.deleteById(id);
	}

}
