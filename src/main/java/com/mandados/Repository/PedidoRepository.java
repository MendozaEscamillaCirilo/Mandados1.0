package com.mandados.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.mandados.Entidades.PedidosEntity;
import com.mandados.Entidades.RepartidoresEntity;

@Repository
public interface PedidoRepository extends CrudRepository<PedidosEntity, Long>  {
    public List<PedidosEntity> findAll();
    public List<PedidosEntity> findByRepartidor(RepartidoresEntity repartidor);
}
