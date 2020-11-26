package com.mandados.Servicios.Pedido;

import java.util.List;
import com.mandados.Entidades.PedidosEntity;

public interface IPedidoService {
	public List<PedidosEntity>listar();
	public int save(PedidosEntity p);
	public void delete(Long d);
}