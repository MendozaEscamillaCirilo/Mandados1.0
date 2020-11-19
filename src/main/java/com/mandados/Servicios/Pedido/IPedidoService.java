package com.mandados.Servicios.Pedido;

import java.util.List;
import java.util.Optional;
import com.mandados.Entidades.PedidosEntity;

public interface IPedidoService {
	public List<PedidosEntity>listar();
	public Optional<PedidosEntity>listarId(int d);
	public int save(PedidosEntity p);
	public void delete(int d);
}