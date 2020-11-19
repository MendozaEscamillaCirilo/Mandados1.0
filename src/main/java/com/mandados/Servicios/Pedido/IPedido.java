package com.mandados.Servicios.Pedido;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mandados.Entidades.PedidosEntity;

@Repository
public interface IPedido extends CrudRepository<PedidosEntity, Integer>{

}
