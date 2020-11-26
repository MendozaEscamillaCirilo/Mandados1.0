package com.mandados.Servicios.Pedido;


import java.util.List;

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
	public int save(PedidosEntity p) {
		PedidosEntity pedido=data.save(p);
		if(!pedido.equals(null)){
			return 1;
		}
		return 0;
	}

	@Override
	public void delete(Long id) {
		data.deleteById(id);
	}

}
